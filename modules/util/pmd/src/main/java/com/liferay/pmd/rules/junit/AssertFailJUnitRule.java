/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.pmd.rules.junit;

import java.util.List;

import net.sourceforge.pmd.lang.ast.Node;
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
	public Object visit(
		ASTStatementExpression astStatementExpression, Object data) {

		if (!_isAssertFailStatement(astStatementExpression)) {
			return data;
		}

		ASTTryStatement astTryStatement =
			astStatementExpression.getFirstParentOfType(ASTTryStatement.class);

		if (astTryStatement == null) {
			addViolation(data, astStatementExpression);

			return data;
		}

		ASTCatchStatement astCatchStatement =
			astStatementExpression.getFirstParentOfType(
				ASTCatchStatement.class);

		if (astCatchStatement != null) {
			addViolation(data, astStatementExpression);

			return data;
		}

		ASTBlock astBlock = astTryStatement.getFirstChildOfType(ASTBlock.class);

		List<ASTStatementExpression> astStatementExpressions =
			astBlock.findDescendantsOfType(ASTStatementExpression.class);

		ASTStatementExpression lastASTStatementExpression =
			astStatementExpressions.get(astStatementExpressions.size() - 1);

		if (!lastASTStatementExpression.equals(astStatementExpression)) {
			addViolation(data, astStatementExpression);

			return data;
		}

		return data;
	}

	private boolean _isAssertFailStatement(
		ASTStatementExpression astStatementExpression) {

		if ((astStatementExpression == null) ||
			(astStatementExpression.jjtGetNumChildren() == 0)) {

			return false;
		}

		Node node = astStatementExpression.jjtGetChild(0);

		if (!(node instanceof ASTPrimaryExpression)) {
			return false;
		}

		ASTPrimaryExpression astPrimaryExpression = (ASTPrimaryExpression)node;

		if (astPrimaryExpression.jjtGetNumChildren() == 0) {
			return false;
		}

		node = astPrimaryExpression.jjtGetChild(0);

		if (!(node instanceof ASTPrimaryPrefix)) {
			return false;
		}

		ASTPrimaryPrefix astPrimaryPrefix = (ASTPrimaryPrefix)node;

		if (astPrimaryPrefix.jjtGetNumChildren() == 0) {
			return false;
		}

		node = astPrimaryPrefix.jjtGetChild(0);

		if (!(node instanceof ASTName)) {
			return false;
		}

		ASTName astName = (ASTName)node;

		String image = astName.getImage();

		if ((image != null) &&
			(image.equals("fail") || image.equals("Assert.fail"))) {

			return true;
		}

		return false;
	}

}