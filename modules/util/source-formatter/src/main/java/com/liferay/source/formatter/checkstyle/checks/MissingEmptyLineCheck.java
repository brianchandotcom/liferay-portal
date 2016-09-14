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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MissingEmptyLineCheck extends AbstractCheck {

	public static final String MSG_MISSING_EMPTY_LINE = "empty.line.missing";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		checkMissingLine(detailAST);
	}

	protected void checkMissingLine(DetailAST detailAST) {
		DetailAST firstChildAST = detailAST.getFirstChild();

		if ((firstChildAST == null) ||
			(firstChildAST.getType() == TokenTypes.DOT)) {

			return;
		}

		DetailAST parentAST = detailAST.getParent();

		DetailAST nextSibling = parentAST.getNextSibling();

		if ((nextSibling == null) ||
			(nextSibling.getType() != TokenTypes.SEMI)) {

			return;
		}

		nextSibling = nextSibling.getNextSibling();

		if (nextSibling == null) {
			return;
		}

		DetailAST nameAST = null;

		if (parentAST.getType() == TokenTypes.EXPR) {
			nameAST = detailAST.findFirstToken(TokenTypes.IDENT);
		}
		else if (parentAST.getType() == TokenTypes.VARIABLE_DEF) {
			nameAST = parentAST.findFirstToken(TokenTypes.IDENT);
		}

		if (nameAST == null) {
			return;
		}

		int endLine = DetailASTUtil.getEndLine(detailAST);
		int startLineNextExpression = DetailASTUtil.getStartLine(nextSibling);

		if ((endLine + 1) != startLineNextExpression) {
			return;
		}

		String name = nameAST.getText();

		if (_isExpressionAssignsVariable(nextSibling, name)) {
			return;
		}

		List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
			nextSibling, TokenTypes.IDENT, true);

		for (DetailAST identAST : identASTList) {
			String identName = identAST.getText();

			if (identName.equals(name)) {
				log(startLineNextExpression, MSG_MISSING_EMPTY_LINE, name);
			}
		}
	}

	private boolean _isExpressionAssignsVariable(
		DetailAST detailAST, String name) {

		if (detailAST.getType() != TokenTypes.EXPR) {
			return false;
		}

		DetailAST childAST = detailAST.getFirstChild();

		if (childAST.getType() != TokenTypes.ASSIGN) {
			return false;
		}

		DetailAST expressionNameAST = childAST.findFirstToken(TokenTypes.IDENT);

		if (expressionNameAST == null) {
			return false;
		}

		String expressionName = expressionNameAST.getText();

		if (expressionName.equals(name)) {
			return true;
		}

		return false;
	}

}