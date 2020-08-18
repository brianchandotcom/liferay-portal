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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
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
		List<DetailAST> variableDefinitionDetailASTList = getAllChildTokens(
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

		if (hasParentWithTokenType(
				variableDefinitionDetailAST, TokenTypes.FOR_EACH_CLAUSE,
				TokenTypes.FOR_INIT)) {

			return;
		}

		DetailAST nameDetailAST = variableDefinitionDetailAST.findFirstToken(
			TokenTypes.IDENT);

		List<DetailAST> dependentIdentDetailASTList =
			getDependentIdentDetailASTList(
				variableDefinitionDetailAST,
				variableDefinitionDetailAST.getLineNo());

		if (dependentIdentDetailASTList.isEmpty()) {
			return;
		}

		String variableName = nameDetailAST.getText();

		DetailAST firstDependentIdentDetailAST =
			dependentIdentDetailASTList.get(0);

		if (!_containsMethodName(
				variableDefinitionDetailAST,
				StringBundler.concat(
					"_?(add|channel|close|copy|create|delete|execute|import|",
					"manage|next|open|post|put|read|register|resolve|run|send|",
					"test|transform|unzip|update|zip)([A-Z].*)?"),
				"currentTimeMillis", "nextVersion", "toString") &&
			!_containsVariableType(
				variableDefinitionDetailAST, "ActionQueue", "File")) {

			_checkMoveAfterBranchingStatement(
				detailAST, variableDefinitionDetailAST, variableName,
				firstDependentIdentDetailAST);

			_checkMoveInsideIfStatement(
				variableDefinitionDetailAST, nameDetailAST, variableName,
				dependentIdentDetailASTList);
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.endsWith("Test.java")) {
			return;
		}

		_checkInline(
			variableDefinitionDetailAST, nameDetailAST, variableName,
			firstDependentIdentDetailAST);
	}

	private void _checkInline(
		DetailAST variableDefinitionDetailAST, DetailAST nameDetailAST,
		String variableName, DetailAST firstDependentIdentDetailAST) {

		DetailAST assignMethodCallDetailAST = _getAssignMethodCallDetailAST(
			variableDefinitionDetailAST);

		if (assignMethodCallDetailAST == null) {
			return;
		}

		List<DetailAST> followingIdentDetailASTList = new ArrayList<>();

		DetailAST nextSiblingDetailAST =
			variableDefinitionDetailAST.getNextSibling();

		while (true) {
			if (nextSiblingDetailAST == null) {
				break;
			}

			followingIdentDetailASTList.addAll(
				getAllChildTokens(
					nextSiblingDetailAST, true, TokenTypes.IDENT));

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();
		}

		DetailAST identDetailAST = null;

		for (DetailAST curIdentDetailAST : followingIdentDetailASTList) {
			if ((curIdentDetailAST.getLineNo() <= nameDetailAST.getLineNo()) ||
				!variableName.equals(curIdentDetailAST.getText())) {

				continue;
			}

			if (identDetailAST != null) {
				return;
			}

			identDetailAST = curIdentDetailAST;

			DetailAST parentDetailAST = identDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.LNOT) {
				parentDetailAST = parentDetailAST.getParent();
			}

			if (parentDetailAST.getType() != TokenTypes.EXPR) {
				return;
			}
		}

		if ((identDetailAST == null) ||
			(firstDependentIdentDetailAST.getLineNo() <
				identDetailAST.getLineNo())) {

			return;
		}

		if (_hasChainStyle(assignMethodCallDetailAST, "build", "map", "put")) {
			if (_isInsideStatementClause(identDetailAST)) {
				return;
			}
		}
		else {
			if ((getStartLineNumber(assignMethodCallDetailAST) !=
					getEndLineNumber(assignMethodCallDetailAST)) ||
				(_isInsideStatementClause(identDetailAST) &&
				 hasParentWithTokenType(
					 identDetailAST, RELATIONAL_OPERATOR_TOKEN_TYPES))) {

				return;
			}

			DetailAST firstChildDetailAST =
				assignMethodCallDetailAST.getFirstChild();

			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			String methodName = fullIdent.getText();

			if (!methodName.matches("(?i)([\\w.]*\\.)?get" + variableName)) {
				return;
			}
		}

		DetailAST parentDetailAST = getParentWithTokenType(
			identDetailAST, TokenTypes.LAMBDA, TokenTypes.LITERAL_DO,
			TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_NEW,
			TokenTypes.LITERAL_SYNCHRONIZED, TokenTypes.LITERAL_TRY,
			TokenTypes.LITERAL_WHILE);

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

				if (emptyLineCount > 1) {
					return;
				}
			}
		}

		log(
			variableDefinitionDetailAST, _MSG_VARIABLE_DECLARATION_NOT_NEEDED,
			variableName, identDetailAST.getLineNo());
	}

	private void _checkMoveAfterBranchingStatement(
		DetailAST detailAST, DetailAST variableDefinitionDetailAST,
		String variableName, DetailAST firstDependentIdentDetailAST) {

		int endLineNumber = getEndLineNumber(variableDefinitionDetailAST);

		DetailAST lastBranchingStatementDetailAST =
			_getLastBranchingStatementDetailAST(
				detailAST, endLineNumber,
				_getClosestParentLineNumber(
					firstDependentIdentDetailAST, endLineNumber));

		if (lastBranchingStatementDetailAST != null) {
			log(
				variableDefinitionDetailAST,
				_MSG_VARIABLE_DECLARATION_MOVE_AFTER_BRANCHING_STATEMENT,
				variableName, lastBranchingStatementDetailAST.getText(),
				lastBranchingStatementDetailAST.getLineNo());
		}
	}

	private void _checkMoveInsideIfStatement(
		DetailAST variableDefinitionDetailAST, DetailAST nameDetailAST,
		String variableName, List<DetailAST> dependentIdentDetailASTList) {

		DetailAST ifStatementDetailAST = _getIfStatementDetailAST(
			dependentIdentDetailASTList.get(0),
			getEndLineNumber(variableDefinitionDetailAST));

		if (ifStatementDetailAST == null) {
			return;
		}

		DetailAST parentDetailAST = getParentWithTokenType(
			ifStatementDetailAST, TokenTypes.LAMBDA, TokenTypes.LITERAL_DO,
			TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_NEW,
			TokenTypes.LITERAL_SYNCHRONIZED, TokenTypes.LITERAL_TRY,
			TokenTypes.LITERAL_WHILE);

		if ((parentDetailAST != null) &&
			(parentDetailAST.getLineNo() >=
				variableDefinitionDetailAST.getLineNo())) {

			return;
		}

		DetailAST slistDetailAST = ifStatementDetailAST.findFirstToken(
			TokenTypes.SLIST);

		DetailAST lastDependentIdentDetailAST = dependentIdentDetailASTList.get(
			dependentIdentDetailASTList.size() - 1);

		if (getEndLineNumber(slistDetailAST) >
				lastDependentIdentDetailAST.getLineNo()) {

			log(
				nameDetailAST,
				_MSG_VARIABLE_DECLARATION_MOVE_INSIDE_IF_STATEMENT,
				variableName, ifStatementDetailAST.getLineNo());
		}
	}

	private boolean _containsMethodName(
		DetailAST variableDefinitionDetailAST, String... methodNameRegexArray) {

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			variableDefinitionDetailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			String methodName = getMethodName(methodCallDetailAST);

			for (String methodNameRegex : methodNameRegexArray) {
				if (methodName.matches(methodNameRegex)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _containsVariableType(
		DetailAST variableDefinitionDetailAST, String... variableTypeNames) {

		List<DetailAST> identDetailASTList = getAllChildTokens(
			variableDefinitionDetailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			if (ArrayUtil.contains(
					variableTypeNames,
					getVariableTypeName(
						identDetailAST, identDetailAST.getText(), false))) {

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

	private DetailAST _getIfStatementDetailAST(
		DetailAST detailAST, int lineNumber) {

		DetailAST ifStatementDetailAST = null;

		DetailAST slistDetailAST = getParentWithTokenType(
			detailAST, TokenTypes.SLIST);

		while (true) {
			if ((slistDetailAST == null) ||
				(slistDetailAST.getLineNo() < lineNumber)) {

				return ifStatementDetailAST;
			}

			DetailAST parentDetailAST = slistDetailAST.getParent();

			if (parentDetailAST.getType() == TokenTypes.LITERAL_IF) {
				ifStatementDetailAST = parentDetailAST;
			}

			slistDetailAST = getParentWithTokenType(
				slistDetailAST, TokenTypes.SLIST);
		}
	}

	private DetailAST _getLastBranchingStatementDetailAST(
		DetailAST detailAST, int start, int end) {

		DetailAST lastBranchingStatementDetailAST = null;

		List<DetailAST> branchingStatementDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.LITERAL_BREAK,
			TokenTypes.LITERAL_CONTINUE, TokenTypes.LITERAL_RETURN);

		for (DetailAST branchingStatementDetailAST :
				branchingStatementDetailASTList) {

			int lineNumber = getEndLineNumber(branchingStatementDetailAST);

			if ((start >= lineNumber) || (end <= lineNumber)) {
				continue;
			}

			DetailAST branchedStatementDetailAST = null;

			if ((branchingStatementDetailAST.getType() ==
					TokenTypes.LITERAL_BREAK) ||
				(branchingStatementDetailAST.getType() ==
					TokenTypes.LITERAL_CONTINUE)) {

				branchedStatementDetailAST = getParentWithTokenType(
					branchingStatementDetailAST, TokenTypes.LITERAL_DO,
					TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE);
			}
			else {
				branchedStatementDetailAST = getParentWithTokenType(
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

	private boolean _hasChainStyle(
		DetailAST methodCallDetailAST, String... methodNames) {

		int startLineNumber = getStartLineNumber(methodCallDetailAST);

		String line = getLine(startLineNumber - 1);

		if (!line.endsWith("(") || (ToolsUtil.getLevel(line) != 1)) {
			return false;
		}

		for (String methodName : methodNames) {
			if (!line.endsWith("." + methodName + "(")) {
				continue;
			}

			int level = 1;

			for (int i = startLineNumber + 1;
				 i <= getEndLineNumber(methodCallDetailAST); i++) {

				line = StringUtil.trim(getLine(i - 1));

				if (line.startsWith(").") && (level == 1)) {
					return true;
				}

				level += ToolsUtil.getLevel(line);
			}
		}

		return false;
	}

	private boolean _isInsideStatementClause(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		while (true) {
			DetailAST grandParentDetailAST = parentDetailAST.getParent();

			if (grandParentDetailAST == null) {
				return false;
			}

			if (grandParentDetailAST.getType() == TokenTypes.LITERAL_FOR) {
				if ((parentDetailAST.getType() == TokenTypes.FOR_CONDITION) ||
					(parentDetailAST.getType() == TokenTypes.FOR_EACH_CLAUSE) ||
					(parentDetailAST.getType() == TokenTypes.FOR_INIT) ||
					(parentDetailAST.getType() == TokenTypes.FOR_ITERATOR)) {

					return true;
				}

				return false;
			}

			if (grandParentDetailAST.getType() == TokenTypes.LITERAL_TRY) {
				if (parentDetailAST.getType() ==
						TokenTypes.RESOURCE_SPECIFICATION) {

					return true;
				}

				return false;
			}

			if (grandParentDetailAST.getType() == TokenTypes.LITERAL_WHILE) {
				if (parentDetailAST.getType() == TokenTypes.EXPR) {
					return true;
				}

				return false;
			}

			parentDetailAST = grandParentDetailAST;
		}
	}

	private static final String
		_MSG_VARIABLE_DECLARATION_MOVE_AFTER_BRANCHING_STATEMENT =
			"variable.declaration.move.after.branching.statement";

	private static final String
		_MSG_VARIABLE_DECLARATION_MOVE_INSIDE_IF_STATEMENT =
			"variable.declaration.move.inside.if.statement";

	private static final String _MSG_VARIABLE_DECLARATION_NOT_NEEDED =
		"variable.declaration.not.needed";

}