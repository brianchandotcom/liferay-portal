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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MissingEmptyLineCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.ASSIGN, TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			_checkMissingEmptyLineAfterMethodCall(detailAST);

			return;
		}

		DetailAST firstChildAST = detailAST.getFirstChild();

		if ((firstChildAST == null) ||
			(firstChildAST.getType() == TokenTypes.DOT)) {

			return;
		}

		String variableName = _getVariableName(detailAST);

		if (variableName == null) {
			return;
		}

		DetailAST parentAST = detailAST.getParent();

		_checkMissingEmptyLineAfterReferencingVariable(
			parentAST, variableName, DetailASTUtil.getEndLineNumber(detailAST));
		_checkMissingEmptyLineBetweenAssigningAndUsingVariable(
			parentAST, variableName, DetailASTUtil.getEndLineNumber(detailAST));
	}

	private void _checkMissingEmptyLineAfterMethodCall(DetailAST detailAST) {
		String variableName = DetailASTUtil.getVariableName(detailAST);

		if (variableName == null) {
			return;
		}

		DetailAST parentAST = detailAST.getParent();

		if (parentAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST nextSiblingAST = parentAST.getNextSibling();

		if ((nextSiblingAST == null) ||
			(nextSiblingAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		nextSiblingAST = nextSiblingAST.getNextSibling();

		if (nextSiblingAST == null) {
			return;
		}

		int endLineNumber = DetailASTUtil.getEndLineNumber(detailAST);

		int nextExpressionStartLineNumber = DetailASTUtil.getStartLineNumber(
			nextSiblingAST);

		if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
			return;
		}

		if (nextSiblingAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildAST = nextSiblingAST.getFirstChild();

			if ((firstChildAST.getType() == TokenTypes.METHOD_CALL) &&
				variableName.equals(
					DetailASTUtil.getVariableName(firstChildAST))) {

				return;
			}
		}

		if (_containsVariableName(nextSiblingAST, variableName)) {
			log(
				endLineNumber, _MSG_MISSING_EMPTY_LINE_AFTER_METHOD_CALL,
				endLineNumber);
		}
	}

	private void _checkMissingEmptyLineAfterReferencingVariable(
		DetailAST detailAST, String variableName, int endLineNumber) {

		String lastAssignedVariableName = null;
		DetailAST previousAST = null;
		boolean referenced = false;

		DetailAST nextSiblingAST = detailAST.getNextSibling();

		while (true) {
			if ((nextSiblingAST == null) ||
				(nextSiblingAST.getType() != TokenTypes.SEMI)) {

				return;
			}

			nextSiblingAST = nextSiblingAST.getNextSibling();

			if ((nextSiblingAST == null) ||
				((nextSiblingAST.getType() != TokenTypes.EXPR) &&
				 (nextSiblingAST.getType() != TokenTypes.VARIABLE_DEF))) {

				return;
			}

			if (!_containsVariableName(nextSiblingAST, variableName)) {
				if (!referenced) {
					return;
				}

				int nextExpressionStartLineNumber =
					DetailASTUtil.getStartLineNumber(nextSiblingAST);

				if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
					return;
				}

				if (_containsVariableName(
						previousAST, lastAssignedVariableName) &&
					_containsVariableName(
						nextSiblingAST, lastAssignedVariableName)) {

					return;
				}

				log(
					nextExpressionStartLineNumber,
					_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE,
					nextExpressionStartLineNumber, variableName);

				return;
			}

			List<DetailAST> assignASTList = DetailASTUtil.getAllChildTokens(
				nextSiblingAST, false, TokenTypes.ASSIGN);

			if (assignASTList.size() == 1) {
				lastAssignedVariableName = _getVariableName(
					assignASTList.get(0));
			}

			referenced = true;

			endLineNumber = DetailASTUtil.getEndLineNumber(nextSiblingAST);

			previousAST = nextSiblingAST;

			nextSiblingAST = nextSiblingAST.getNextSibling();
		}
	}

	private void _checkMissingEmptyLineBetweenAssigningAndUsingVariable(
		DetailAST detailAST, String name, int endLine) {

		DetailAST nextSiblingAST = detailAST.getNextSibling();

		if ((nextSiblingAST == null) ||
			(nextSiblingAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		nextSiblingAST = nextSiblingAST.getNextSibling();

		if (nextSiblingAST == null) {
			return;
		}

		int nextExpressionStartLineNumber = DetailASTUtil.getStartLineNumber(
			nextSiblingAST);

		if ((endLine + 1) != nextExpressionStartLineNumber) {
			return;
		}

		if (_isExpressionAssignsVariable(nextSiblingAST, name)) {
			return;
		}

		List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
			nextSiblingAST, true, TokenTypes.IDENT);

		for (DetailAST identAST : identASTList) {
			String identName = identAST.getText();

			if (identName.equals(name)) {
				log(
					nextExpressionStartLineNumber,
					_MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE, name);
			}
		}
	}

	private boolean _containsVariableName(
		DetailAST detailAST, String variableName) {

		if (variableName == null) {
			return false;
		}

		List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST identAST : identASTList) {
			String identName = identAST.getText();

			if (identName.equals(variableName)) {
				return true;
			}
		}

		return false;
	}

	private String _getVariableName(DetailAST assignAST) {
		DetailAST nameAST = null;

		DetailAST parentAST = assignAST.getParent();

		if (parentAST.getType() == TokenTypes.EXPR) {
			nameAST = assignAST.findFirstToken(TokenTypes.IDENT);
		}
		else if (parentAST.getType() == TokenTypes.VARIABLE_DEF) {
			nameAST = parentAST.findFirstToken(TokenTypes.IDENT);
		}

		if (nameAST != null) {
			return nameAST.getText();
		}

		return null;
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

	private static final String _MSG_MISSING_EMPTY_LINE_AFTER_METHOD_CALL =
		"empty.line.missing.after.method.call";

	private static final String
		_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE =
			"empty.line.missing.after.variable.reference";

	private static final String _MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE =
		"empty.line.missing.before.variable.use";

}