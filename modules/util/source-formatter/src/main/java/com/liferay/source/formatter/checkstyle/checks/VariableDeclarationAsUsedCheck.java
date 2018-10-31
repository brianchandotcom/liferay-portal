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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class VariableDeclarationAsUsedCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> variableDefinitionDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.VARIABLE_DEF);

		if (variableDefinitionDetailASTList.isEmpty()) {
			return;
		}

		List<DetailAST> identDetailASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST variableDefinitionDetailAST :
				variableDefinitionDetailASTList) {

			_checkAsUsed(
				detailAST, variableDefinitionDetailAST, identDetailASTList);
		}
	}

	private void _checkAsUsed(
		DetailAST detailAST, DetailAST variableDefinitionDetailAST,
		List<DetailAST> identDetailASTList) {

		List<String> identValues = _getIdentValues(variableDefinitionDetailAST);

		if (DetailASTUtil.hasParentWithTokenType(
				variableDefinitionDetailAST, TokenTypes.FOR_EACH_CLAUSE,
				TokenTypes.FOR_INIT) ||
			_containsMethodName(
				variableDefinitionDetailAST,
				"_?(add|channel|close|create|delete|execute|open|post|put|" +
					"register|resolve|send|transform|unzip|update|zip)" +
						"([A-Z].*)?",
				"currentTimeMillis", "nextVersion", "toString") ||
			_containsVariableType(
				variableDefinitionDetailAST, identValues, "File")) {

			return;
		}

		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		String variableName = nameDetailAST.getText();

		int endLineNumber = DetailASTUtil.getEndLineNumber(
			variableDefinitionDetailAST);

		DetailAST firstDependentIdentDetailAST =
			_getFirstDependentIdentDetailAST(
				variableName, identValues, identDetailASTList,
				endLineNumber + 1);

		if (firstDependentIdentDetailAST == null) {
			return;
		}

		DetailAST lastBranchingStatementDetailAST =
			_getLastBranchingStatementDetailAST(
				detailAST, endLineNumber,
				_getClosestParentLineNumber(
					firstDependentIdentDetailAST, endLineNumber));

		if (lastBranchingStatementDetailAST != null) {
			log(
				variableDefinitionDetailAST, _MSG_DECLARE_VARIABLE_AS_USED,
				variableName, lastBranchingStatementDetailAST.getText(),
				lastBranchingStatementDetailAST.getLineNo());
		}
	}

	private boolean _containsMethodName(
		DetailAST variableDefinitionDetailAST, String... methodNameRegexArray) {

		List<DetailAST> methodCallDetailASTList =
			DetailASTUtil.getAllChildTokens(
				variableDefinitionDetailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			String methodName = DetailASTUtil.getMethodName(
				methodCallDetailAST);

			for (String methodNameRegex : methodNameRegexArray) {
				if (methodName.matches(methodNameRegex)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _containsVariableType(
		DetailAST variableDefinitionDetailAST, List<String> identValues,
		String... variableTypeNames) {

		for (String name : identValues) {
			if (ArrayUtil.contains(
					variableTypeNames,
					DetailASTUtil.getVariableTypeName(
						variableDefinitionDetailAST, name, false))) {

				return true;
			}
		}

		return false;
	}

	private int _getClosestParentLineNumber(
		DetailAST firstNameDetailAST, int lineNumber) {

		int closestLineNumber = firstNameDetailAST.getLineNo();

		DetailAST parentDetailAST = firstNameDetailAST.getParent();

		while (true) {
			if (parentDetailAST.getLineNo() <= lineNumber) {
				return closestLineNumber;
			}

			closestLineNumber = parentDetailAST.getLineNo();

			parentDetailAST = parentDetailAST.getParent();
		}
	}

	private DetailAST _getFirstDependentIdentDetailAST(
		String variableName, List<String> identValues,
		List<DetailAST> identDetailASTList, int start) {

		for (DetailAST identDetailAST : identDetailASTList) {
			if (identDetailAST.getLineNo() < start) {
				continue;
			}

			String curName = identDetailAST.getText();

			if (curName.equals(variableName)) {
				return identDetailAST;
			}

			if (curName.equals("actionRequest") ||
				curName.equals("portletRequest") ||
				curName.equals("resourceRequest") ||
				!identValues.contains(curName)) {

				continue;
			}

			DetailAST parentDetailAST = identDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.DOT) {
				return identDetailAST;
			}

			if (identDetailAST.getPreviousSibling() != null) {
				continue;
			}

			DetailAST grandParentDetailAST = parentDetailAST.getParent();

			if (grandParentDetailAST.getType() == TokenTypes.METHOD_CALL) {
				DetailAST nextSiblingDetailAST =
					identDetailAST.getNextSibling();

				if (nextSiblingDetailAST.getType() == TokenTypes.IDENT) {
					String methodName = nextSiblingDetailAST.getText();

					if (methodName.matches("get[A-Z].*")) {
						continue;
					}
				}
			}

			return identDetailAST;
		}

		return null;
	}

	private List<String> _getIdentValues(
		DetailAST variableDefinitionDetailAST) {

		List<String> identValues = new ArrayList<>();

		List<DetailAST> identDetailASTList = DetailASTUtil.getAllChildTokens(
			variableDefinitionDetailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			String identValue = identDetailAST.getText();

			if (!Character.isUpperCase(identValue.charAt(0))) {
				identValues.add(identValue);
			}
		}

		return identValues;
	}

	private DetailAST _getLastBranchingStatementDetailAST(
		DetailAST detailAST, int start, int end) {

		DetailAST lastBranchingStatementDetailAST = null;

		List<DetailAST> branchingStatementDetailASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.LITERAL_BREAK,
				TokenTypes.LITERAL_CONTINUE, TokenTypes.LITERAL_RETURN);

		for (DetailAST branchingStatementDetailAST :
				branchingStatementDetailASTList) {

			int lineNumber = DetailASTUtil.getEndLineNumber(
				branchingStatementDetailAST);

			if ((start >= lineNumber) || (end <= lineNumber)) {
				continue;
			}

			DetailAST branchedStatementDetailAST = null;

			if ((branchingStatementDetailAST.getType() ==
					TokenTypes.LITERAL_BREAK) ||
				(branchingStatementDetailAST.getType() ==
					TokenTypes.LITERAL_CONTINUE)) {

				branchedStatementDetailAST =
					DetailASTUtil.getParentWithTokenType(
						branchingStatementDetailAST, TokenTypes.LITERAL_DO,
						TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE);
			}
			else {
				branchedStatementDetailAST =
					DetailASTUtil.getParentWithTokenType(
						branchingStatementDetailAST, TokenTypes.CTOR_DEF,
						TokenTypes.LAMBDA, TokenTypes.METHOD_DEF);
			}

			if ((branchedStatementDetailAST.getLineNo() < start) &&
				((lastBranchingStatementDetailAST == null) ||
				 (branchingStatementDetailAST.getLineNo() >
					 lastBranchingStatementDetailAST.getLineNo()))) {

				lastBranchingStatementDetailAST = branchingStatementDetailAST;
			}
		}

		return lastBranchingStatementDetailAST;
	}

	private static final String _MSG_DECLARE_VARIABLE_AS_USED =
		"variable.declare.as.used";

}