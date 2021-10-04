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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

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
			_checkMissingEmptyLinesAroundMethodCall(detailAST);

			return;
		}

		if (detailAST.getType() == TokenTypes.VARIABLE_DEF) {
			DetailAST parentDetailAST = detailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.SLIST) {
				_checkMissingEmptyLineAfterVariableDef(
					detailAST, "ThemeDisplay");
				_checkMissingEmptyLineBeforeVariableDef(detailAST);
			}

			return;
		}

		_checkMissingEmptyLineBeforeAssign(detailAST);

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
			parentDetailAST, variableName, getEndLineNumber(detailAST));
		_checkMissingEmptyLineBetweenAssigningAndUsingVariable(
			parentDetailAST, variableName, getEndLineNumber(detailAST));
	}

	private void _checkMissingEmptyLineAfterMethodCall(
		DetailAST detailAST, String variableName,
		DetailAST nextSiblingDetailAST) {

		if (nextSiblingDetailAST == null) {
			return;
		}

		int endLineNumber = getEndLineNumber(detailAST);

		int nextExpressionStartLineNumber = getStartLineNumber(
			nextSiblingDetailAST);

		if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
			return;
		}

		if (nextSiblingDetailAST.getType() == TokenTypes.EXPR) {
			List<String> enforceEmptyLineAfterMethodNames = getAttributeValues(
				_ENFORCE_EMPTY_LINE_AFTER_METHOD_NAMES);

			String methodName = getMethodName(detailAST);

			if (enforceEmptyLineAfterMethodNames.contains(
					StringBundler.concat(
						getVariableTypeName(detailAST, variableName, false),
						StringPool.PERIOD, methodName))) {

				log(
					endLineNumber, _MSG_MISSING_EMPTY_LINE_AFTER_METHOD_NAME,
					StringBundler.concat(
						variableName, StringPool.PERIOD, methodName));
			}

			DetailAST firstChildDetailAST =
				nextSiblingDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) &&
				variableName.equals(getVariableName(firstChildDetailAST))) {

				return;
			}
		}

		if (_containsVariableName(nextSiblingDetailAST, variableName)) {
			log(
				endLineNumber, _MSG_MISSING_EMPTY_LINE_LINE_NUMBER, "after",
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
				hasPrecedingPlaceholder(nextSiblingDetailAST) ||
				((nextSiblingDetailAST.getType() != TokenTypes.EXPR) &&
				 (nextSiblingDetailAST.getType() != TokenTypes.VARIABLE_DEF))) {

				return;
			}

			if (!_containsVariableName(nextSiblingDetailAST, variableName)) {
				if (!referenced) {
					return;
				}

				int nextExpressionStartLineNumber = getStartLineNumber(
					nextSiblingDetailAST);

				if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
					return;
				}

				if (!_containsVariableName(
						previousDetailAST, lastAssignedVariableName) ||
					!_containsVariableName(
						nextSiblingDetailAST, lastAssignedVariableName)) {

					log(
						nextExpressionStartLineNumber,
						_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE,
						nextExpressionStartLineNumber, variableName);
				}

				return;
			}

			List<DetailAST> assignDetailASTList = getAllChildTokens(
				nextSiblingDetailAST, false, TokenTypes.ASSIGN);

			if (assignDetailASTList.size() == 1) {
				lastAssignedVariableName = _getVariableName(
					assignDetailASTList.get(0));
			}

			referenced = true;

			endLineNumber = getEndLineNumber(nextSiblingDetailAST);

			previousDetailAST = nextSiblingDetailAST;

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}
	}

	private void _checkMissingEmptyLineAfterVariableDef(
		DetailAST detailAST, String variableTypeName) {

		if (detailAST.findFirstToken(TokenTypes.ASSIGN) == null) {
			return;
		}

		int endLineNumber = getEndLineNumber(detailAST);

		String nextLine = getLine(endLineNumber);

		if (Validator.isNull(nextLine)) {
			return;
		}

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(getHiddenBefore(nextSiblingDetailAST) != null) ||
			(nextSiblingDetailAST.getType() == TokenTypes.RCURLY)) {

			return;
		}

		DetailAST identDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		if (variableTypeName.equals(
				getVariableTypeName(
					detailAST, identDetailAST.getText(), false))) {

			log(
				detailAST, _MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_DEFINITION,
				variableTypeName);

			return;
		}

		if (nextSiblingDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
			return;
		}

		if (nextSiblingDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildDetailAST =
				nextSiblingDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.ASSIGN) {
				return;
			}
		}

		log(
			endLineNumber, _MSG_MISSING_EMPTY_LINE_LINE_NUMBER, "after",
			endLineNumber);
	}

	private void _checkMissingEmptyLineBeforeAssign(DetailAST assignDetailAST) {
		DetailAST parentDetailAST = assignDetailAST.getParent();

		DetailAST nextSiblingDetailAST = parentDetailAST.getNextSibling();

		if ((nextSiblingDetailAST == null) ||
			(nextSiblingDetailAST.getType() != TokenTypes.SEMI) ||
			_hasPrecedingVariableDef(parentDetailAST)) {

			return;
		}

		List<DetailAST> adjacentAssignDetailASTList =
			_getAdjacentAssignDetailASTList(assignDetailAST);

		if (adjacentAssignDetailASTList.size() <= 1) {
			return;
		}

		DetailAST lastAssignDetailAST = adjacentAssignDetailASTList.get(
			adjacentAssignDetailASTList.size() - 1);

		List<DetailAST> identDetailASTList =
			_getFollowingStatementsIdentDetailASTList(
				lastAssignDetailAST.getParent());

		if (_containsVariableName(identDetailASTList, assignDetailAST)) {
			return;
		}

		DetailAST firstReferencedAssignDetailAST = null;

		for (int i = 1; i < adjacentAssignDetailASTList.size(); i++) {
			DetailAST curAssignDetailAST = adjacentAssignDetailASTList.get(i);

			if (_containsVariableName(identDetailASTList, curAssignDetailAST)) {
				if (firstReferencedAssignDetailAST == null) {
					firstReferencedAssignDetailAST = curAssignDetailAST;
				}

				continue;
			}

			if (firstReferencedAssignDetailAST != null) {
				return;
			}
		}

		if (firstReferencedAssignDetailAST != null) {
			DetailAST nameDetailAST =
				firstReferencedAssignDetailAST.findFirstToken(TokenTypes.IDENT);

			if (nameDetailAST == null) {
				parentDetailAST = firstReferencedAssignDetailAST.getParent();

				nameDetailAST = parentDetailAST.findFirstToken(
					TokenTypes.IDENT);
			}

			log(
				firstReferencedAssignDetailAST,
				_MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_ASSIGN,
				nameDetailAST.getText());
		}
	}

	private void _checkMissingEmptyLineBeforeMethodCall(
		DetailAST detailAST, String variableName,
		DetailAST previousSiblingDetailAST) {

		if (previousSiblingDetailAST == null) {
			return;
		}

		int startLineNumber = getStartLineNumber(detailAST);

		int previousExpressionEndLineNumber = getEndLineNumber(
			previousSiblingDetailAST);

		if ((previousExpressionEndLineNumber + 1) != startLineNumber) {
			return;
		}

		if (previousSiblingDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildDetailAST =
				previousSiblingDetailAST.getFirstChild();

			if ((firstChildDetailAST.getType() == TokenTypes.METHOD_CALL) &&
				variableName.equals(getVariableName(firstChildDetailAST))) {

				return;
			}
		}

		if (_containsVariableName(previousSiblingDetailAST, variableName)) {
			log(
				startLineNumber, _MSG_MISSING_EMPTY_LINE_LINE_NUMBER, "before",
				startLineNumber);
		}
	}

	private void _checkMissingEmptyLineBeforeVariableDef(DetailAST detailAST) {
		if (getHiddenBefore(detailAST) != null) {
			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		int startLineNumber = getStartLineNumber(detailAST);

		String previousLine = getLine(startLineNumber - 2);

		if (Validator.isNull(previousLine)) {
			return;
		}

		DetailAST previousSiblingDetailAST = detailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		previousSiblingDetailAST =
			previousSiblingDetailAST.getPreviousSibling();

		if (previousSiblingDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
			return;
		}

		if (previousSiblingDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildDetailAST =
				previousSiblingDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.ASSIGN) {
				return;
			}
		}

		log(
			startLineNumber, _MSG_MISSING_EMPTY_LINE_LINE_NUMBER, "before",
			startLineNumber);
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

		int nextExpressionStartLineNumber = getStartLineNumber(
			nextSiblingDetailAST);

		if ((endLineNumber + 1) != nextExpressionStartLineNumber) {
			return;
		}

		List<DetailAST> identDetailASTList = getAllChildTokens(
			nextSiblingDetailAST, true, TokenTypes.IDENT);

		boolean nextVariableUsesVariable = false;

		for (DetailAST identDetailAST : identDetailASTList) {
			String identName = identDetailAST.getText();

			if (!identName.equals(name)) {
				continue;
			}

			DetailAST parentDetailAST = identDetailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.ASSIGN) &&
				(identDetailAST.getPreviousSibling() == null)) {

				return;
			}

			nextVariableUsesVariable = true;
		}

		if (nextVariableUsesVariable) {
			log(
				nextExpressionStartLineNumber,
				_MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE, name);
		}
	}

	private void _checkMissingEmptyLinesAroundMethodCall(DetailAST detailAST) {
		String variableName = getVariableName(detailAST);

		if ((variableName == null) ||
			Character.isUpperCase(variableName.charAt(0))) {

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

		_checkMissingEmptyLineAfterMethodCall(
			detailAST, variableName, nextSiblingDetailAST.getNextSibling());

		DetailAST previousSiblingDetailAST =
			parentDetailAST.getPreviousSibling();

		if ((previousSiblingDetailAST == null) ||
			(previousSiblingDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		_checkMissingEmptyLineBeforeMethodCall(
			detailAST, variableName,
			previousSiblingDetailAST.getPreviousSibling());
	}

	private boolean _containsVariableName(
		DetailAST detailAST, String variableName) {

		List<DetailAST> identDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		return _containsVariableName(identDetailASTList, variableName);
	}

	private boolean _containsVariableName(
		List<DetailAST> identDetailASTList, DetailAST assignDetailAST) {

		String variableName = _getVariableName(assignDetailAST);

		if (variableName == null) {
			return false;
		}

		return _containsVariableName(identDetailASTList, variableName);
	}

	private boolean _containsVariableName(
		List<DetailAST> identDetailASTList, String variableName) {

		if (variableName == null) {
			return false;
		}

		for (DetailAST identDetailAST : identDetailASTList) {
			if (!isMethodNameDetailAST(identDetailAST) &&
				variableName.equals(identDetailAST.getText())) {

				return true;
			}
		}

		return false;
	}

	private List<DetailAST> _getAdjacentAssignDetailASTList(
		DetailAST assignDetailAST) {

		List<DetailAST> assignDetailASTList = new ArrayList<>();

		assignDetailASTList.add(assignDetailAST);

		DetailAST followingStatementDetailAST = _getFollowingStatementDetailAST(
			assignDetailAST.getParent(), false);

		while (true) {
			if (followingStatementDetailAST == null) {
				return assignDetailASTList;
			}

			DetailAST followingAssignDetailAST =
				followingStatementDetailAST.findFirstToken(TokenTypes.ASSIGN);

			if (followingAssignDetailAST == null) {
				return assignDetailASTList;
			}

			assignDetailASTList.add(followingAssignDetailAST);

			followingStatementDetailAST = _getFollowingStatementDetailAST(
				followingStatementDetailAST, false);
		}
	}

	private DetailAST _getFollowingStatementDetailAST(
		DetailAST detailAST, boolean allowDividingEmptyLine) {

		int endLineNumber = getEndLineNumber(detailAST);

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				return null;
			}

			int nextStartLineNumber = getStartLineNumber(nextSiblingDetailAST);

			if (nextStartLineNumber <= endLineNumber) {
				nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

				continue;
			}

			if (nextStartLineNumber == (endLineNumber + 1)) {
				return nextSiblingDetailAST;
			}

			if (hasPrecedingPlaceholder(nextSiblingDetailAST)) {
				return null;
			}

			if (allowDividingEmptyLine) {
				return nextSiblingDetailAST;
			}

			return null;
		}
	}

	private List<DetailAST> _getFollowingStatementsIdentDetailASTList(
		DetailAST detailAST) {

		List<DetailAST> identDetailASTList = new ArrayList<>();

		DetailAST followingStatementDetailAST = _getFollowingStatementDetailAST(
			detailAST, true);

		while (followingStatementDetailAST != null) {
			identDetailASTList.addAll(
				getAllChildTokens(
					followingStatementDetailAST, true, TokenTypes.IDENT));

			followingStatementDetailAST = _getFollowingStatementDetailAST(
				followingStatementDetailAST, false);
		}

		return identDetailASTList;
	}

	private String _getVariableName(DetailAST assignDetailAST) {
		DetailAST parentDetailAST = assignDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
				return firstChildDetailAST.getText();
			}
		}
		else if (parentDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
			DetailAST nameDetailAST = parentDetailAST.findFirstToken(
				TokenTypes.IDENT);

			return nameDetailAST.getText();
		}

		return null;
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

		if ((getEndLineNumber(previousSiblingDetailAST) + 1) ==
				getStartLineNumber(variableDefinitionDetailAST)) {

			return true;
		}

		return false;
	}

	private static final String _ENFORCE_EMPTY_LINE_AFTER_METHOD_NAMES =
		"enforceEmptyLineAfterMethodNames";

	private static final String _MSG_MISSING_EMPTY_LINE_AFTER_METHOD_NAME =
		"empty.line.missing.after.method.name";

	private static final String
		_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_DEFINITION =
			"empty.line.missing.after.variable.definition";

	private static final String
		_MSG_MISSING_EMPTY_LINE_AFTER_VARIABLE_REFERENCE =
			"empty.line.missing.after.variable.reference";

	private static final String _MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_ASSIGN =
		"empty.line.missing.before.variable.assign";

	private static final String _MSG_MISSING_EMPTY_LINE_BEFORE_VARIABLE_USE =
		"empty.line.missing.before.variable.use";

	private static final String _MSG_MISSING_EMPTY_LINE_LINE_NUMBER =
		"empty.line.missing.line.number";

}