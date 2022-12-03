package arvoreavl;

public class Arvore {

    private No noraiz;

    public Arvore(No noraiz) {
        this.noraiz = noraiz;
    }

    public boolean isEmpty() {
        if (noraiz == null) {
            return true;
        }
        return false;
    }

    public void imprimirArvore() {
        if (this.noraiz == null) {
            System.out.println("Arvore vazia");
        } else {
            imprimirArvore(this.noraiz);
        }
    }

    private void imprimirArvore(No node) {
        if (node.getEsquerda() != null) {
            imprimirArvore(node.getEsquerda());
        }
        if (node.getDireita() != null) {
            imprimirArvore(node.getDireita());
        }
        System.out.print(node.getValor() + "-");
        try {
            System.out.println("No Esquerda: " + node.getEsquerda().getValor());
            System.out.println("No Direita: " + node.getDireita().getValor());
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    public void inserir(int valor) {
        No n = new No(valor);
        inserir(this.noraiz, n);
    }

    public void inserir(No aComparar, No aInserir) {

        //alterar o método para uma árvore AVL
        if (aComparar == null) {
            this.noraiz = aInserir;
        } else {
            if (aInserir.getValor() < aComparar.getValor()) {
		if (aComparar.getEsquerda() == null) {
                    aComparar.setEsquerda(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

                } else {
                    inserir(aComparar.getEsquerda(), aInserir);
                }
                //Verifica se o valor a ser inserido é maior que o no corrente da árvore, se sim vai para subarvore direita 
            } else if (aInserir.getValor() > aComparar.getValor()) {
                //Se tiver elemento no no direito continua a busca 
                if (aComparar.getDireita() == null) {
                    aComparar.setDireita(aInserir);
                    aInserir.setPai(aComparar);
                    verificarBalanceamento(aComparar);

		} else  {
                    //Se nodo direito vazio insere o novo no aqui 
                    inserir(aComparar.getDireita(), aInserir);

                }
            }
        }
    }
    
    private void setBalanceamento(No no) {
	no.setBalanceamento(altura(no.getDireita()) - altura(no.getEsquerda()));
    }
    
    public void verificarBalanceamento(No atual) {
        setBalanceamento(atual);
	int balanceamento = atual.getBalanceamento();

	if (balanceamento == -2) {

            if (altura(atual.getEsquerda().getEsquerda()) >= altura(atual.getEsquerda().getDireita())) {
                atual = rotacaoDireita(atual);

            } else {
                atual = duplaRotacaoEsquerdaDireita(atual);
            }

	} else if (balanceamento == 2) {
                    
            if (altura(atual.getDireita().getDireita()) >= altura(atual.getDireita().getEsquerda())) {
                atual = rotacaoEsquerda(atual);
            } else {
                atual = duplaRotacaoDireitaEsquerda(atual);
            }
        }

        if (atual.getPai() != null) {
            verificarBalanceamento(atual.getPai());
        } else {
            this.noraiz = atual;
	}
    }
    
    public No rotacaoDireita(No inicial) {

	No esquerda = inicial.getEsquerda();
	esquerda.setPai(inicial.getPai());

	inicial.setEsquerda(esquerda.getDireita());

	if (inicial.getEsquerda() != null) {
            inicial.getEsquerda().setPai(inicial);
	}

	esquerda.setDireita(inicial);
            inicial.setPai(esquerda);

	if (esquerda.getPai() != null) {

            if (esquerda.getPai().getDireita() == inicial) {
		esquerda.getPai().setDireita(esquerda);
			
            } else if (esquerda.getPai().getEsquerda() == inicial) {
		esquerda.getPai().setEsquerda(esquerda);
            }
	}

	setBalanceamento(inicial);
	setBalanceamento(esquerda);

	return esquerda;
    }
    
    public No rotacaoEsquerda(No inicial) {

	No direita = inicial.getDireita();
	direita.setPai(inicial.getPai());

	inicial.setDireita(direita.getEsquerda());

	if (inicial.getDireita() != null) {
            inicial.getDireita().setPai(inicial);
	}

	direita.setEsquerda(inicial);
            inicial.setPai(direita);

	if (direita.getPai() != null) {

            if (direita.getPai().getEsquerda() == inicial) {
		direita.getPai().setEsquerda(direita);
			
            } else if (direita.getPai().getDireita() == inicial) {
		direita.getPai().setDireita(direita);
            }
	}

	setBalanceamento(inicial);
	setBalanceamento(direita);

	return direita;
    }
    
    public No duplaRotacaoEsquerdaDireita(No inicial) {
	inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
	return rotacaoDireita(inicial);
    }
    
    public No duplaRotacaoDireitaEsquerda(No inicial) {
	inicial.setDireita(rotacaoDireita(inicial.getDireita()));
	return rotacaoEsquerda(inicial);
    }
    
    private int altura(No atual) {
	if (atual == null) {
            return -1;
	}

	if (atual.getEsquerda() == null && atual.getDireita() == null) {
            return 0;
		
	} else if (atual.getEsquerda() == null) {
            return 1 + altura(atual.getDireita());
		
	} else if (atual.getDireita() == null) {
            return 1 + altura(atual.getEsquerda());
		
	} else {
            return 1 + Math.max(altura(atual.getEsquerda()), altura(atual.getDireita()));
	}
    }

    public No remover(int valor) throws Exception {
        return remover(this.noraiz, valor);
    }

    private No remover(No atual, int valor) {

        //alterar o método para uma árvore AVL
        
        if (this.noraiz == null) {
            System.out.println("Árvore vazia");
        } else {
            if (valor < atual.getValor()) {
                remover(atual.getEsquerda(), valor);
            } else if (valor > atual.getValor()) {
                remover(atual.getDireita(), valor);
            } else if (atual.getEsquerda() != null && atual.getDireita() != null) {
                /*2 filhos*/
                System.out.println("  Removeu No " + atual.getValor());
                atual.setValor(encontraMinimo(atual.getDireita()).getValor());
                atual.setDireita(removeMinimo(atual.getDireita()));
            } else {
                System.out.println("  Removeu No " + atual.getValor());
                removerNoEncontrado(atual);
            }

        }
        return atual;
    }
    
    public void removerNoEncontrado(No aRemover) {
        No r;

        if (aRemover.getEsquerda() == null || aRemover.getDireita() == null) {

            if (aRemover.getPai() == null) {
                this.noraiz = null;
                aRemover = null;
                return;
            }
            r = aRemover;

        } else {
            r = sucessor(aRemover);
            aRemover.setValor(r.getValor());
	}

	No p;
	if (r.getEsquerda() != null) {
            p = r.getEsquerda();
	} else {
            p = r.getDireita();
	}

	if (p != null) {
            p.setPai(r.getPai());
	}

	if (r.getPai() == null) {
            this.noraiz = p;
	} else {
            if (r == r.getPai().getEsquerda()) {
		r.getPai().setEsquerda(p);
            } else {
		r.getPai().setDireita(p);
            }
                verificarBalanceamento(r.getPai());
        }
        r = null;
    }
        
    public No sucessor(No q) {
	if (q.getDireita() != null) {
            No r = q.getDireita();
            while (r.getEsquerda() != null) {
                r = r.getEsquerda();
            }
            return r;
	} else {
            No p = q.getPai();
            while (p != null && q == p.getDireita()) {
		q = p;
		p = q.getPai();
            }
            return p;
	}
    }

    private No removeMinimo(No valor) {
        if (valor == null) {
            System.out.println("  ERRO ");
        } else if (valor.getEsquerda() != null) {
            valor.setEsquerda(removeMinimo(valor.getEsquerda()));
            return valor;
        } else {
            return valor.getDireita();
        }
        return null;
    }

    private No encontraMinimo(No valor) {
        if (valor != null) {
            while (valor.getEsquerda() != null) {
                valor = valor.getEsquerda();
            }
        }
        return valor;
    }
}
