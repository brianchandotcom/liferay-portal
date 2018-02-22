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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class MissingEmptyLineCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
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

		DetailAST previousIdentAST = null;
		boolean referenced = false;

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
					previousIdentAST = identAST;
				}
			}

			if (!expressionReferencesVariable) {
				if (!referenced) {
					return;
				}

				int startLineNextExpression = DetailASTUtil.getStartLine(
					nextSibling);

				if ((endLine + 1) != startLineNextExpression) {
					return;
				}

				if (_isReferencesNewVariable(previousIdentAST)) {
					if (_hasAssignTokenType(previousIdentAST) ||
						_isNestedMethodCall(previousIdentAST) ||
						_isReferencesNewVariableSetter(previousIdentAST)) {

						return;
					}
				}

				log(
					startLineNextExpression,
					_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE,
					startLineNextExpression, name);

				return;
			}

			referenced = true;

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
					_MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE, name);
			}
		}
	}

	private boolean _hasAssignTokenType(DetailAST identAST) {
		if (identAST == null) {
			return false;
		}

		DetailAST parentAST = identAST.getParent();

		while (parentAST != null) {
			if (parentAST.getType() == TokenTypes.ASSIGN) {
				return true;
			}

			if (parentAST.getType() == TokenTypes.SEMI) {
				return false;
			}

			parentAST = parentAST.getParent();
		}

		return false;
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

	private boolean _isNestedMethodCall(DetailAST identAST) {
		if (identAST == null) {
			return false;
		}

		DetailAST parentAST = identAST.getParent();

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if ((parentAST == null) ||
			(parentAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		parentAST = parentAST.getParent();

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if ((parentAST == null) ||
			(parentAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		return true;
	}

	private boolean _isReferencesNewVariable(DetailAST identAST) {
		if (identAST == null) {
			return false;
		}

		DetailAST parentAST = identAST.getParent();

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if ((parentAST == null) ||
			(parentAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		DetailAST firstChild = parentAST.getFirstChild();

		if ((firstChild == null) || (firstChild.getType() != TokenTypes.DOT)) {
			return false;
		}

		firstChild = firstChild.getFirstChild();

		if ((firstChild == null) ||
			(firstChild.getType() != TokenTypes.IDENT)) {

			return false;
		}

		if (Objects.equals(firstChild.getText(), identAST.getText())) {
			return false;
		}

		return true;
	}

	private boolean _isReferencesNewVariableSetter(DetailAST identAST) {
		if (identAST == null) {
			return false;
		}

		DetailAST parentAST = identAST.getParent();

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if (parentAST != null) {
			parentAST = parentAST.getParent();
		}

		if ((parentAST == null) ||
			(parentAST.getType() != TokenTypes.METHOD_CALL)) {

			return false;
		}

		DetailAST firstChild = parentAST.getFirstChild();

		if ((firstChild == null) || (firstChild.getType() != TokenTypes.DOT)) {
			return false;
		}

		firstChild = firstChild.getFirstChild();

		if ((firstChild == null) ||
			(firstChild.getType() != TokenTypes.IDENT)) {

			return false;
		}

		DetailAST detailAST = firstChild.getNextSibling();

		if ((detailAST == null) || (detailAST.getType() != TokenTypes.IDENT)) {
			return false;
		}

		if (!StringUtil.startsWith(detailAST.getText(), "set")) {
			return false;
		}

		return true;
	}

	private static final String
		_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE =
			"empty.line.missing.after.variable.reference";

	private static final String _MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE =
		"empty.line.missing.before.variable.use";

}