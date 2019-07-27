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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
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

		for (DetailAST variableDefinitionDetailAST :
				variableDefinitionDetailASTList) {

			_checkAsUsed(detailAST, variableDefinitionDetailAST);
		}
	}

	private void _checkAsUsed(
		DetailAST detailAST, DetailAST variableDefinitionDetailAST) {

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

		List<DetailAST> identDetailASTList = _getIdentDetailASTList(
			variableDefinitionDetailAST);

		DetailAST firstDependentIdentDetailAST =
			_getFirstDependentIdentDetailAST(
				variableName, identValues, identDetailASTList);

		if (firstDependentIdentDetailAST == null) {
			return;
		}

		int endLineNumber = DetailASTUtil.getEndLineNumber(
			variableDefinitionDetailAST);

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

		FileContents fileContents = getFileContents();

		String fileName = fileContents.getFileName();

		if (fileName.endsWith("Test.java")) {
			return;
		}

		DetailAST assignMethodCallDetailAST = _getAssignMethodCallDetailAST(
			variableDefinitionDetailAST);

		if (assignMethodCallDetailAST == null) {
			return;
		}

		DetailAST elistDetailAST = assignMethodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		int parameterCount = 0;

		DetailAST childDetailAST = elistDetailAST.getFirstChild();

		while (true) {
			if (childDetailAST == null) {
				break;
			}

			if (childDetailAST.getType() == TokenTypes.COMMA) {
				childDetailAST = childDetailAST.getNextSibling();

				continue;
			}

			if (childDetailAST.getType() != TokenTypes.EXPR) {
				return;
			}

			parameterCount++;

			if (parameterCount > 1) {
				return;
			}

			DetailAST grandChildDetailAST = childDetailAST.getFirstChild();

			if (grandChildDetailAST.getType() != TokenTypes.IDENT) {
				return;
			}

			childDetailAST = childDetailAST.getNextSibling();
		}

		DetailAST firstChildDetailAST =
			assignMethodCallDetailAST.getFirstChild();

		FullIdent fullIdent = FullIdent.createFullIdent(firstChildDetailAST);

		String methodName = fullIdent.getText();

		if (!methodName.matches("(?i)([\\w.]*\\.)?get" + variableName)) {
			return;
		}

		DetailAST identDetailAST = null;

		for (DetailAST curIdentDetailAST : identDetailASTList) {
			if ((curIdentDetailAST.getLineNo() <= nameDetailAST.getLineNo()) ||
				!variableName.equals(curIdentDetailAST.getText())) {

				continue;
			}

			if (identDetailAST != null) {
				return;
			}

			identDetailAST = curIdentDetailAST;

			DetailAST parentDetailAST = identDetailAST.getParent();

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				return;
			}
		}

		if ((identDetailAST == null) ||
			(firstDependentIdentDetailAST.getLineNo() <
				identDetailAST.getLineNo())) {

			return;
		}

		DetailAST parentDetailAST = DetailASTUtil.getParentWithTokenType(
			identDetailAST, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE,
			TokenTypes.LITERAL_TRY, TokenTypes.LITERAL_SYNCHRONIZED,
			TokenTypes.LAMBDA);

		if ((parentDetailAST != null) &&
			(parentDetailAST.getLineNo() >=
				variableDefinitionDetailAST.getLineNo())) {

			return;
		}

		int emptyLineCount = 0;

		for (int i = variableDefinitionDetailAST.getLineNo();
			 i <= identDetailAST.getLineNo(); i++) {

			if (Validator.isNull(getLine(i - 1))) {
				emptyLineCount++;
			}
		}

		if (emptyLineCount < 2) {
			log(
				variableDefinitionDetailAST,
				_MSG_VARIABLE_DECLARTION_NOT_NEEDED, variableName,
				identDetailAST.getLineNo());
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

	private DetailAST _getAssignMethodCallDetailAST(
		DetailAST variableDefinitionDetailAST) {

		DetailAST assignDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return null;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return null;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST != null) &&
			(firstChildDetailAST.getType() == TokenTypes.METHOD_CALL)) {

			return firstChildDetailAST;
		}

		return null;
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
		List<DetailAST> identDetailASTList) {

		for (DetailAST identDetailAST : identDetailASTList) {
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

	private List<DetailAST> _getIdentDetailASTList(
		DetailAST variableDefinitionDetailAST) {

		List<DetailAST> identDetailASTList = new ArrayList<>();

		DetailAST nextSiblingDetailAST =
			variableDefinitionDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				return identDetailASTList;
			}

			identDetailASTList.addAll(
				DetailASTUtil.getAllChildTokens(
					nextSiblingDetailAST, true, TokenTypes.IDENT));

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}
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

			if ((branchedStatementDetailAST != null) &&
				(branchedStatementDetailAST.getLineNo() < start) &&
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

	private static final String _MSG_VARIABLE_DECLARTION_NOT_NEEDED =
		"variable.declaration.not.needed";

}