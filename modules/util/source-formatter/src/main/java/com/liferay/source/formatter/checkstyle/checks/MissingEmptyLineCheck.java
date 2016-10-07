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

	public static final String MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE =
		"empty.line.missing.after.variable.reference";

	public static final String MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE =
		"empty.line.missing.before.variable.use";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST firstChildAST = detailAST.getFirstChild();

		if ((firstChildAST == null) ||
			(firstChildAST.getType() == TokenTypes.DOT)) {

			return;
		}

		DetailAST parentAST = detailAST.getParent();

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

		_checkMissingEmptyLineAfterReferencingVariable(
			parentAST, nameAST.getText(), DetailASTUtil.getEndLine(detailAST));
		_checkMissingEmptyLineBetweenAssigningAndUsingVariable(
			parentAST, nameAST.getText(), DetailASTUtil.getEndLine(detailAST));
	}

	private void _checkMissingEmptyLineAfterReferencingVariable(
		DetailAST detailAST, String name, int endLine) {

		boolean isReferenced = false;

		DetailAST nextSibling = detailAST.getNextSibling();

		while (true) {
			if ((nextSibling == null) ||
				(nextSibling.getType() != TokenTypes.SEMI)) {

				return;
			}

			nextSibling = nextSibling.getNextSibling();

			if ((nextSibling == null) ||
				((nextSibling.getType() != TokenTypes.EXPR) &&
				 (nextSibling.getType() != TokenTypes.VARIABLE_DEF))) {

				return;
			}

			boolean expressionReferencesVariable = false;

			List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
				nextSibling, true, TokenTypes.IDENT);

			for (DetailAST identAST : identASTList) {
				String identName = identAST.getText();

				if (identName.equals(name)) {
					expressionReferencesVariable = true;
				}
			}

			if (!expressionReferencesVariable) {
				if (isReferenced) {
					int startLineNextExpression = DetailASTUtil.getStartLine(
						nextSibling);

					if ((endLine + 1) == startLineNextExpression) {
						log(
							startLineNextExpression,
							MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE,
							startLineNextExpression, name);
					}
				}

				return;
			}

			isReferenced = true;

			endLine = DetailASTUtil.getEndLine(nextSibling);

			nextSibling = nextSibling.getNextSibling();
		}
	}

	private void _checkMissingEmptyLineBetweenAssigningAndUsingVariable(
		DetailAST detailAST, String name, int endLine) {

		DetailAST nextSibling = detailAST.getNextSibling();

		if ((nextSibling == null) ||
			(nextSibling.getType() != TokenTypes.SEMI)) {

			return;
		}

		nextSibling = nextSibling.getNextSibling();

		if (nextSibling == null) {
			return;
		}

		int startLineNextExpression = DetailASTUtil.getStartLine(nextSibling);

		if ((endLine + 1) != startLineNextExpression) {
			return;
		}

		if (_isExpressionAssignsVariable(nextSibling, name)) {
			return;
		}

		List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
			nextSibling, true, TokenTypes.IDENT);

		for (DetailAST identAST : identASTList) {
			String identName = identAST.getText();

			if (identName.equals(name)) {
				log(
					startLineNextExpression,
					MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE, name);
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