/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.pmd.rules.junit;

import java.util.List;

import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTCatchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTStatementExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTryStatement;
import net.sourceforge.pmd.lang.java.rule.junit.AbstractJUnitRule;

/**
 * @author Cristina González
 */
public class AssertFailJUnitRule extends AbstractJUnitRule {

	@Override
	public Object visit(
		ASTClassOrInterfaceDeclaration astClassOrInterfaceDeclaration,
		Object data) {

		if (astClassOrInterfaceDeclaration.isInterface()) {
			return data;
		}

		return super.visit(astClassOrInterfaceDeclaration, data);
	}

	@Override
	public Object visit(ASTStatementExpression expression, Object data) {
		if (isAssertFailStatement(expression)) {
			ASTTryStatement tryStatement = expression.getFirstParentOfType(
				ASTTryStatement.class);

			if (tryStatement == null) {
				addViolation(data, expression);

				return data;
			}

			ASTCatchStatement catchStatement = expression.getFirstParentOfType(
				ASTCatchStatement.class);

			if (catchStatement != null) {
				addViolation(data, expression);

				return data;
			}

			ASTBlock tryBlock = tryStatement.getFirstChildOfType(
				ASTBlock.class);

			List<ASTStatementExpression> statementExpressions =
				tryBlock.findDescendantsOfType(ASTStatementExpression.class);

			ASTStatementExpression lastStatementExpression =
				statementExpressions.get(statementExpressions.size() - 1);

			if (!lastStatementExpression.equals(expression)) {
				addViolation(data, expression);

				return data;
			}
		}

		return data;
	}

	/**
	 * Tells if the expression is an assert.fail statement
	 */
	private boolean isAssertFailStatement(ASTStatementExpression expression) {
		if (expression!= null && expression.jjtGetNumChildren()>0 &&
			(expression.jjtGetChild(0) instanceof ASTPrimaryExpression)) {

			ASTPrimaryExpression astPrimaryExpression =
				(ASTPrimaryExpression)expression.jjtGetChild(0);

			if (astPrimaryExpression.jjtGetNumChildren()> 0 &&
				astPrimaryExpression.jjtGetChild(0)
					instanceof ASTPrimaryPrefix) {

				ASTPrimaryPrefix pp =
					(ASTPrimaryPrefix)astPrimaryExpression.jjtGetChild(0);

				if (pp.jjtGetNumChildren()>0 &&
					pp.jjtGetChild(0) instanceof ASTName) {

					String img = ((ASTName)pp.jjtGetChild(0)).getImage();

					if ((img != null) &&
						(img.equals("fail") || img.equals("Assert.fail"))) {

						return true;
					}
				}
			}
		}

		return false;
	}

}