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
		List<DetailAST> variableDefASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.VARIABLE_DEF);

		if (variableDefASTList.isEmpty()) {
			return;
		}

		List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
			detailAST, true, TokenTypes.IDENT);

		for (DetailAST variableDefAST : variableDefASTList) {
			_checkAsUsed(detailAST, variableDefAST, identASTList);
		}
	}

	private void _checkAsUsed(
		DetailAST detailAST, DetailAST variableDefAST,
		List<DetailAST> identASTList) {

		if (DetailASTUtil.hasParentWithTokenType(
				variableDefAST, TokenTypes.FOR_INIT) ||
			_containsMethodName(
				variableDefAST, "currentTimeMillis",
				"(add|create|delete|post|register|update)([A-Z].*)?",
				"toString")) {

			return;
		}

		DetailAST nameAST = variableDefAST.findFirstToken(TokenTypes.IDENT);

		String variableName = nameAST.getText();

		int endLineNumber = DetailASTUtil.getEndLineNumber(variableDefAST);

		DetailAST firstDependentIdentAST = _findFirstDependentIdentAST(
			variableName, _getIdentValues(variableDefAST), identASTList,
			endLineNumber + 1);

		if (firstDependentIdentAST == null) {
			return;
		}

		DetailAST lastBranchingStatementAST = _getLastBranchingStatementAST(
			detailAST, endLineNumber,
			_getClosestParentLineNumber(firstDependentIdentAST, endLineNumber));

		if (lastBranchingStatementAST != null) {
			log(
				variableDefAST, _MSG_DECLARE_VARIABLE_AS_USED, variableName,
				lastBranchingStatementAST.getText(),
				lastBranchingStatementAST.getLineNo());
		}
	}

	private boolean _containsMethodName(
		DetailAST variableDefAST, String... methodNameRegexArray) {

		List<DetailAST> methodCallASTList = DetailASTUtil.getAllChildTokens(
			variableDefAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallAST : methodCallASTList) {
			String methodName = DetailASTUtil.getMethodName(methodCallAST);

			for (String methodNameRegex : methodNameRegexArray) {
				if (methodName.matches(methodNameRegex)) {
					return true;
				}
			}
		}

		return false;
	}

	private DetailAST _findFirstDependentIdentAST(
		String variableName, List<String> identValues,
		List<DetailAST> identASTList, int start) {

		for (DetailAST identAST : identASTList) {
			if (identAST.getLineNo() < start) {
				continue;
			}

			String curName = identAST.getText();

			if (curName.equals(variableName)) {
				return identAST;
			}

			if (curName.equals("actionRequest") ||
				curName.equals("portletRequest") ||
				curName.equals("resourceRequest") ||
				!identValues.contains(curName)) {

				continue;
			}

			DetailAST parentAST = identAST.getParent();

			if (parentAST.getType() != TokenTypes.DOT) {
				return identAST;
			}

			if (identAST.getPreviousSibling() != null) {
				continue;
			}

			DetailAST grandParentAST = parentAST.getParent();

			if (grandParentAST.getType() == TokenTypes.METHOD_CALL) {
				DetailAST nextSiblingAST = identAST.getNextSibling();

				if (nextSiblingAST.getType() == TokenTypes.IDENT) {
					String methodName = nextSiblingAST.getText();

					if (methodName.matches("get[A-Z].*")) {
						continue;
					}
				}
			}

			return identAST;
		}

		return null;
	}

	private int _getClosestParentLineNumber(
		DetailAST firstNameAST, int lineNumber) {

		int closestLineNumber = firstNameAST.getLineNo();

		DetailAST parentAST = firstNameAST.getParent();

		while (true) {
			if (parentAST.getLineNo() <= lineNumber) {
				return closestLineNumber;
			}

			closestLineNumber = parentAST.getLineNo();

			parentAST = parentAST.getParent();
		}
	}

	private List<String> _getIdentValues(DetailAST variableDefAST) {
		List<String> identValues = new ArrayList<>();

		List<DetailAST> identASTList = DetailASTUtil.getAllChildTokens(
			variableDefAST, true, TokenTypes.IDENT);

		for (DetailAST identAST : identASTList) {
			String identValue = identAST.getText();

			if (!Character.isUpperCase(identValue.charAt(0))) {
				identValues.add(identValue);
			}
		}

		return identValues;
	}

	private DetailAST _getLastBranchingStatementAST(
		DetailAST detailAST, int start, int end) {

		DetailAST lastBranchingStatementAST = null;

		List<DetailAST> branchingStatementASTList =
			DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.LITERAL_BREAK,
				TokenTypes.LITERAL_CONTINUE, TokenTypes.LITERAL_RETURN);

		for (DetailAST branchingStatementAST : branchingStatementASTList) {
			int lineNumber = DetailASTUtil.getEndLineNumber(
				branchingStatementAST);

			if ((start >= lineNumber) || (end <= lineNumber)) {
				continue;
			}

			DetailAST branchedStatementAST = null;

			if ((branchingStatementAST.getType() == TokenTypes.LITERAL_BREAK) ||
				(branchingStatementAST.getType() ==
					TokenTypes.LITERAL_CONTINUE)) {

				branchedStatementAST = DetailASTUtil.getParentWithTokenType(
					branchingStatementAST, TokenTypes.LITERAL_DO,
					TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE);
			}
			else {
				branchedStatementAST = DetailASTUtil.getParentWithTokenType(
					branchingStatementAST, TokenTypes.CTOR_DEF,
					TokenTypes.LAMBDA, TokenTypes.METHOD_DEF);
			}

			if ((branchedStatementAST.getLineNo() < start) &&
				((lastBranchingStatementAST == null) ||
				 (branchingStatementAST.getLineNo() >
					 lastBranchingStatementAST.getLineNo()))) {

				lastBranchingStatementAST = branchingStatementAST;
			}
		}

		return lastBranchingStatementAST;
	}

	private static final String _MSG_DECLARE_VARIABLE_AS_USED =
		"variable.declare.as.used";

}