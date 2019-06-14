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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class MissingEmptyLineCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.ASSIGN, TokenTypes.METHOD_CALL, TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			_checkMissingEmptyLineAfterMethodCall(detailAST);

			return;
		}

		if (detailAST.getType() == TokenTypes.VARIABLE_DEF) {
			_checkMissingEmptyLineAfterVariableDef(detailAST, "ThemeDisplay");

			_checkMissingEmptyLineBeforeVariableDef(detailAST);

			return;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() == TokenTypes.DOT)) {

			return;
		}

		String variableName = _getVariableName(detailAST);

		if (variableName == null) {
			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		_checkMissingEmptyLineAfterReferencingVariable(
			parentDetailAST, variableName,
			DetailASTUtil.getEndLineNumber(detailAST));
		_checkMissingEmptyLineBetweenAssigningAndUsingVariable(
			parentDetailAST, variableName,
			DetailASTUtil.getEndLineNumber(detailAST));
	}

	private void _checkMissingEmptyLineAfterMethodCall(DetailAST detailAST) {
		String variableName = DetailASTUtil.getVariableName(detailAST);

		if (variableName == null) {
			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

		if (nextSiblingDetailAST == null) {
			return;
		}

		int endLineNumber = DetailASTUtil.getEndLineNumber(detailAST);

		int nextExpressionStartLineNumber = DetailASTUtil.getStartLineNumber(
			nextSiblingDetailAST);

		if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
			return;
		}

		if (nextSiblingDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildDetailAST =
				nextSiblingDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) &&
				variableName.equals(
					DetailASTUtil.getVariableName(firstChildDetailAST))) {

				return;
			}
		}

		if (_containsVariableName(nextSiblingDetailAST, variableName)) {
			log(
				endLineNumber, _MSG_MISSING_EMPTY_LINE_AFTER_METHOD_CALL,
				endLineNumber);
		}
	}

	private void _checkMissingEmptyLineAfterReferencingVariable(
		DetailAST detailAST, String variableName, int endLineNumber) {

		String lastAssignedVariableName = null;
		DetailAST previousDetailAST = null;
		boolean referenced = false;

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		while (true) {
			if ((nextSiblingDetailAST == null) ||
				(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				return;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if ((nextSiblingDetailAST == null) ||
				((nextSiblingDetailAST.getType() != TokenTypes.EXPR) &&
				 (nextSiblingDetailAST.getType() != TokenTypes.VARIABLE_DEF))) {

				return;
			}

			if (!_containsVariableName(nextSiblingDetailAST, variableName)) {
				if (!referenced) {
					return;
				}

				int nextExpressionStartLineNumber =
					DetailASTUtil.getStartLineNumber(nextSiblingDetailAST);

				if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
					return;
				}

				if (_containsVariableName(
						previousDetailAST, lastAssignedVariableName) &&
					_containsVariableName(
						nextSiblingDetailAST, lastAssignedVariableName)) {

					return;
				}

				String methodCallVariableName = _getMethodCallVariableName(
					nextSiblingDetailAST);

				if (!_hasFollowingMethodCallWithVariableName(
						nextSiblingDetailAST, methodCallVariableName,
						variableName)) {

					log(
						nextExpressionStartLineNumber,
						_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE,
						nextExpressionStartLineNumber, variableName);
				}

				return;
			}

			List<DetailAST> assignDetailASTList =
				DetailASTUtil.getAllChildTokens(
					nextSiblingDetailAST, false, TokenTypes.ASSIGN);

			if (assignDetailASTList.size() == 1) {
				lastAssignedVariableName = _getVariableName(
					assignDetailASTList.get(0));
			}

			referenced = true;

			endLineNumber = DetailASTUtil.getEndLineNumber(
				nextSiblingDetailAST);

			previousDetailAST = nextSiblingDetailAST;

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}
	}

	private void _checkMissingEmptyLineAfterVariableDef(
		DetailAST detailAST, String variableTypeName) {

		if (detailAST.findFirstToken(TokenTypes.ASSIGN) == null) {
			return;
		}

		DetailAST identDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		if (variableTypeName.equals(
				DetailASTUtil.getVariableTypeName(
					detailAST, identDetailAST.getText(), false))) {

			String nextLine = StringUtil.trim(
				getLine(DetailASTUtil.getEndLineNumber(detailAST)));

			if (Validator.isNotNull(nextLine) && !nextLine.startsWith("}")) {
				log(
					detailAST,
					_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_DEFINITION,
					variableTypeName);
			}
		}
	}

	private void _checkMissingEmptyLineBeforeVariableDef(
		DetailAST variableDefinitionDetailAST) {

		DetailAST nextSiblingDetailAST =
			variableDefinitionDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		if (_hasPrecedingVariableDef(variableDefinitionDetailAST)) {
			return;
		}

		List<DetailAST> adjacentVariableDefinitionDetailASTList =
			_getAdjacentVariableDefinitionDetailASTList(
				variableDefinitionDetailAST);

		if (adjacentVariableDefinitionDetailASTList.size() <= 1) {
			return;
		}

		DetailAST lastVariableDefinitionDetailAST =
			adjacentVariableDefinitionDetailASTList.get(
				adjacentVariableDefinitionDetailASTList.size() - 1);

		List<DetailAST> identDetailASTList =
			_getFollowingStatementsIdentDetailASTList(
				lastVariableDefinitionDetailAST);

		if (_containsVariableName(
				identDetailASTList, variableDefinitionDetailAST)) {

			return;
		}

		DetailAST firstReferencedVariableDetailAST = null;

		for (int i = 1; i < adjacentVariableDefinitionDetailASTList.size();
			 i++) {

			DetailAST curVariableDefinitionDetailAST =
				adjacentVariableDefinitionDetailASTList.get(i);

			if (_containsVariableName(
					identDetailASTList, curVariableDefinitionDetailAST)) {

				if (firstReferencedVariableDetailAST == null) {
					firstReferencedVariableDetailAST =
						curVariableDefinitionDetailAST;
				}

				continue;
			}

			if (firstReferencedVariableDetailAST != null) {
				return;
			}
		}

		if (firstReferencedVariableDetailAST != null) {
			DetailAST nameDetailAST =
				firstReferencedVariableDetailAST.findFirstToken(
					TokenTypes.IDENT);

			log(
				firstReferencedVariableDetailAST,
				_MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_DEFINITION,
				nameDetailAST.getText());
		}
	}

	private void _checkMissingEmptyLineBetweenAssigningAndUsingVariable(
		DetailAST detailAST, String name, int endLineNumber) {

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

		if (nextSiblingDetailAST == null) {
			return;
		}

		int nextExpressionStartLineNumber = DetailASTUtil.getStartLineNumber(
			nextSiblingDetailAST);

		if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
			return;
		}

		List<DetailAST> identDetailASTList = DetailASTUtil.getAllChildTokens(
			nextSiblingDetailAST, true, TokenTypes.IDENT);

		boolean nextVariableUsesVariable = false;

		for (DetailAST identDetailAST : identDetailASTList) {
			DetailAST parentDetailAST = identDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.ASSIGN) {
				return;
			}

			String identName = identDetailAST.getText();

			if (identName.equals(name)) {
				nextVariableUsesVariable = true;
			}
		}

		if (nextVariableUsesVariable) {
			log(
				nextExpressionStartLineNumber,
				_MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE, name);
		}
	}

	private boolean _containsVariableName(
		DetailAST detailAST, String variableName) {

		List<DetailAST> identDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		return _containsVariableName(identDetailASTList, variableName);
	}

	private boolean _containsVariableName(
		List<DetailAST> identDetailASTList,
		DetailAST variableDefinitionDetailAST) {

		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (nameDetailAST == null) {
			return false;
		}

		return _containsVariableName(
			identDetailASTList, nameDetailAST.getText());
	}

	private boolean _containsVariableName(
		List<DetailAST> identDetailASTList, String variableName) {

		if (variableName == null) {
			return false;
		}

		for (DetailAST identDetailAST : identDetailASTList) {
			if (variableName.equals(identDetailAST.getText())) {
				return true;
			}
		}

		return false;
	}

	private List<DetailAST> _getAdjacentVariableDefinitionDetailASTList(
		DetailAST variableDefinitionDetailAST) {

		List<DetailAST> variableDefinitionDetailASTList = new ArrayList<>();

		variableDefinitionDetailASTList.add(variableDefinitionDetailAST);

		DetailAST followingStatementDetailAST = _getFollowingStatementDetailAST(
			variableDefinitionDetailAST, false);

		while (true) {
			if ((followingStatementDetailAST == null) ||
				(followingStatementDetailAST.getType() !=
					TokenTypes.VARIABLE_DEF)) {

				return variableDefinitionDetailASTList;
			}

			variableDefinitionDetailASTList.add(followingStatementDetailAST);

			followingStatementDetailAST = _getFollowingStatementDetailAST(
				followingStatementDetailAST, false);
		}
	}

	private DetailAST _getFollowingStatementDetailAST(
		DetailAST detailAST, boolean allowDividingEmptyLine) {

		int endLineNumber = DetailASTUtil.getEndLineNumber(detailAST);

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				return null;
			}

			int nextStartLineNumber = DetailASTUtil.getStartLineNumber(
				nextSiblingDetailAST);

			if (nextStartLineNumber <= endLineNumber) {
				nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

				continue;
			}

			if (allowDividingEmptyLine ||
				(nextStartLineNumber == (endLineNumber + 1))) {

				return nextSiblingDetailAST;
			}

			return null;
		}
	}

	private List<DetailAST> _getFollowingStatementsIdentDetailASTList(
		DetailAST variableDefinitionDetailAST) {

		List<DetailAST> identDetailASTList = new ArrayList<>();

		DetailAST followingStatementDetailAST = _getFollowingStatementDetailAST(
			variableDefinitionDetailAST, true);

		while (followingStatementDetailAST != null) {
			identDetailASTList.addAll(
				DetailASTUtil.getAllChildTokens(
					followingStatementDetailAST, true, TokenTypes.IDENT));

			followingStatementDetailAST = _getFollowingStatementDetailAST(
				followingStatementDetailAST, false);
		}

		return identDetailASTList;
	}

	private String _getMethodCallVariableName(DetailAST detailAST) {
		if (detailAST.getType() != TokenTypes.EXPR) {
			return null;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.DOT) {
			return null;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
			return firstChildDetailAST.getText();
		}

		return null;
	}

	private String _getVariableName(DetailAST assignDetailAST) {
		DetailAST nameDetailAST = null;

		DetailAST parentDetailAST = assignDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			nameDetailAST = assignDetailAST.findFirstToken(TokenTypes.IDENT);
		}
		else if (parentDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
			nameDetailAST = parentDetailAST.findFirstToken(TokenTypes.IDENT);
		}

		if (nameDetailAST != null) {
			return nameDetailAST.getText();
		}

		return null;
	}

	private boolean _hasFollowingMethodCallWithVariableName(
		DetailAST detailAST, String methodCallVariableName,
		String variableName) {

		if (methodCallVariableName == null) {
			return false;
		}

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return false;
		}

		nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

		if (nextSiblingDetailAST == null) {
			return false;
		}

		int endLineNumber = DetailASTUtil.getEndLineNumber(detailAST);
		int nextExpressionStartLineNumber = DetailASTUtil.getStartLineNumber(
			nextSiblingDetailAST);

		if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
			return false;
		}

		String nextMethodCallVariableName = _getMethodCallVariableName(
			nextSiblingDetailAST);

		if (!methodCallVariableName.equals(nextMethodCallVariableName)) {
			return false;
		}

		if (_containsVariableName(nextSiblingDetailAST, variableName)) {
			return true;
		}

		return _hasFollowingMethodCallWithVariableName(
			nextSiblingDetailAST, methodCallVariableName, variableName);
	}

	private boolean _hasPrecedingVariableDef(
		DetailAST variableDefinitionDetailAST) {

		DetailAST previousSiblingDetailAST =
			variableDefinitionDetailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return false;
		}

		previousSiblingDetailAST =
			previousSiblingDetailAST.getPreviousSibling();

		if (previousSiblingDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return false;
		}

		if ((DetailASTUtil.getEndLineNumber(previousSiblingDetailAST) + 1) ==
				DetailASTUtil.getStartLineNumber(variableDefinitionDetailAST)) {

			return true;
		}

		return false;
	}

	private static final String _MSG_MISSING_EMPTY_LINE_AFTER_METHOD_CALL =
		"empty.line.missing.after.method.call";

	private static final String
		_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_DEFINITION =
			"empty.line.missing.after.variable.definition";

	private static final String
		_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE =
			"empty.line.missing.after.variable.reference";

	private static final String
		_MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_DEFINITION =
			"empty.line.missing.before.variable.definition";

	private static final String _MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE =
		"empty.line.missing.before.variable.use";

}