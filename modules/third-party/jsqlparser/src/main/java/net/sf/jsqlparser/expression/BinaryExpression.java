/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.jsqlparser.expression;

import java.util.Deque;
import java.util.LinkedList;

import net.sf.jsqlparser.parser.ASTNodeAccessImpl;

/**
 * A basic class for binary expressions, that is expressions having a left member and a right member
 * which are in turn expressions.
 */
public abstract class BinaryExpression extends ASTNodeAccessImpl implements Expression {

    private Expression leftExpression;
    private Expression rightExpression;
//    private boolean not = false;

    public BinaryExpression() {
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public void setLeftExpression(Expression expression) {
        leftExpression = expression;
    }

    public void setRightExpression(Expression expression) {
        rightExpression = expression;
    }

//    public void setNot() {
//        not = true;
//    }
//    
//    public void removeNot() {
//        not = false;
//    }
// 
//    public boolean isNot() {
//        return not;
//    }
    @Override
    public String toString() {
		StringBuilder sb = new StringBuilder();

		Deque<BinaryExpression> deque = new LinkedList<>();

		deque.push(this);

		Expression expression = getLeftExpression();

		while(!deque.isEmpty()) {
			if (expression instanceof BinaryExpression) {
				BinaryExpression binaryExpression =
					(BinaryExpression)expression;

				deque.push(binaryExpression);

				expression = binaryExpression.getLeftExpression();
			}
			else {
				sb.append(expression.toString());

				BinaryExpression binaryExpression = deque.pop();

				sb.append(' ');
				sb.append(binaryExpression.getStringExpression());
				sb.append(' ');

				expression = binaryExpression.getRightExpression();
			}
		}

		sb.append(expression.toString());

		return sb.toString();
    }

    public abstract String getStringExpression();

}
/* @generated */