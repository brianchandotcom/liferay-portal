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
import java.util.Stack;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

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
        Deque<BinaryExpression> stack = new LinkedList<>();
        Deque<String> queue = new LinkedList<>();

		Expression expression = this;

		BinaryExpression binaryExpression = null;

		do {
			if (expression instanceof BinaryExpression) {
				stack.push((BinaryExpression)expression);

				binaryExpression = (BinaryExpression)expression;

				expression = binaryExpression.getLeftExpression();
			}
			else {
				queue.offer(expression.toString());

				binaryExpression = stack.pop();

				queue.offer(
					StringBundler.concat(
						StringPool.SPACE,
						binaryExpression.getStringExpression(),
						StringPool.SPACE));

				expression = binaryExpression.getRightExpression();
			}
		}
		while(!stack.isEmpty());

		queue.offer(expression.toString());

		StringBundler sb = new StringBundler(queue.size());
		queue.forEach(sb::append);

        return sb.toString();
    }

    public abstract String getStringExpression();

}
/* @generated */