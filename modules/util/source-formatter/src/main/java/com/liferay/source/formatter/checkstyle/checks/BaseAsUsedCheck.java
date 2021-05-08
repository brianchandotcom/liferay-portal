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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public abstract class BaseAsUsedCheck extends BaseCheck {

	protected void checkInline(
		DetailAST assignDetailAST, String variableName,
		DetailAST assignMethodCallDetailAST, DetailAST identDetailAST,
		List<DetailAST> dependentIdentDetailASTList) {

		if ((assignMethodCallDetailAST == null) ||
			!variableName.equals(identDetailAST.getText()) ||
			_isInsideMockitoMethodCall(identDetailAST)) {

			return;
		}

		DetailAST parentDetailAST = identDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.LNOT) {
			parentDetailAST = parentDetailAST.getParent();
		}

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		int endLineNumber = getEndLineNumber(assignDetailAST);

		for (DetailAST dependentIdentDetailAST : dependentIdentDetailASTList) {
			if (variableName.equals(dependentIdentDetailAST.getText()) &&
				!equals(dependentIdentDetailAST, identDetailAST) &&
				(dependentIdentDetailAST.getLineNo() > endLineNumber)) {

				return;
			}
		}

		if (_hasChainStyle(
				assignMethodCallDetailAST, "build", "create.*", "map", "put")) {

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

			if (!_matchesGetOrSetCall(
					assignMethodCallDetailAST, identDetailAST, variableName)) {

				return;
			}
		}

		parentDetailAST = getParentWithTokenType(
			identDetailAST, TokenTypes.LAMBDA, TokenTypes.LITERAL_DO,
			TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_NEW,
			TokenTypes.LITERAL_SYNCHRONIZED, TokenTypes.LITERAL_TRY,
			TokenTypes.LITERAL_WHILE);

		if ((parentDetailAST != null) &&
			(parentDetailAST.getLineNo() >= assignDetailAST.getLineNo())) {

			return;
		}

		int emptyLineCount = 0;

		for (int i = endLineNumber; i <= identDetailAST.getLineNo(); i++) {
			if (Validator.isNull(getLine(i - 1))) {
				emptyLineCount++;

				if (emptyLineCount > 1) {
					return;
				}
			}
		}

		log(
			assignDetailAST, _MSG_INLINE_VARIABLE, variableName,
			identDetailAST.getLineNo());
	}

	protected void checkMoveAfterBranchingStatement(
		DetailAST detailAST, DetailAST assignDetailAST, String variableName,
		DetailAST firstDependentIdentDetailAST) {

		int endLineNumber = getEndLineNumber(assignDetailAST);

		DetailAST lastBranchingStatementDetailAST =
			_getLastBranchingStatementDetailAST(
				detailAST, endLineNumber,
				_getClosestParentLineNumber(
					firstDependentIdentDetailAST, endLineNumber));

		if (lastBranchingStatementDetailAST != null) {
			log(
				assignDetailAST, _MSG_MOVE_VARIABLE_AFTER_BRANCHING_STATEMENT,
				variableName, lastBranchingStatementDetailAST.getText(),
				lastBranchingStatementDetailAST.getLineNo());
		}
	}

	protected void checkMoveInsideIfStatement(
		DetailAST assignDetailAST, DetailAST nameDetailAST, String variableName,
		DetailAST firstDependentIdentDetailAST,
		DetailAST lastDependentIdentDetailAST) {

		DetailAST ifStatementDetailAST = _getIfStatementDetailAST(
			firstDependentIdentDetailAST, getEndLineNumber(assignDetailAST));

		if (ifStatementDetailAST == null) {
			return;
		}

		DetailAST parentDetailAST = getParentWithTokenType(
			ifStatementDetailAST, TokenTypes.LAMBDA, TokenTypes.LITERAL_DO,
			TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_NEW,
			TokenTypes.LITERAL_SYNCHRONIZED, TokenTypes.LITERAL_TRY,
			TokenTypes.LITERAL_WHILE);

		if ((parentDetailAST != null) &&
			(parentDetailAST.getLineNo() >= assignDetailAST.getLineNo())) {

			return;
		}

		DetailAST slistDetailAST = ifStatementDetailAST.findFirstToken(
			TokenTypes.SLIST);

		if (getEndLineNumber(slistDetailAST) >
				lastDependentIdentDetailAST.getLineNo()) {

			log(
				nameDetailAST, _MSG_MOVE_VARIABLE_INSIDE_IF_STATEMENT,
				variableName, ifStatementDetailAST.getLineNo());
		}
	}

	protected boolean checkMoveStatement(DetailAST detailAST) {
		if (_containsMethodName(
				detailAST,
				StringBundler.concat(
					"_?(add|channel|close|copy|create|delete|execute|import|",
					"increment|manage|next|open|post|put|read|register|",
					"resolve|run|send|test|transform|unzip|update|upsert|zip)",
					"([A-Z].*)?"),
				"currentTimeMillis", "nextVersion", "toString") ||
			_containsVariableType(detailAST, "ActionQueue", "File")) {

			return false;
		}

		return true;
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
		DetailAST methodCallDetailAST, String... methodNameRegexArray) {

		int startLineNumber = getStartLineNumber(methodCallDetailAST);

		String line = getLine(startLineNumber - 1);

		if (!line.endsWith("(") || (ToolsUtil.getLevel(line) != 1)) {
			return false;
		}

		for (String methodNameRegex : methodNameRegexArray) {
			if (!line.matches(".*[\\.>]" + methodNameRegex + "\\(")) {
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

	private boolean _isInsideMockitoMethodCall(DetailAST detailAST) {
		DetailAST methodCallDetailAST = getParentWithTokenType(
			detailAST, TokenTypes.METHOD_CALL);

		if (methodCallDetailAST == null) {
			return false;
		}

		List<DetailAST> identDetailASTList = getAllChildTokens(
			methodCallDetailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTList) {
			if (Objects.equals(identDetailAST.getText(), "Mockito")) {
				return true;
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

	private boolean _matchesGetOrSetCall(
		DetailAST assignMethodCallDetailAST, DetailAST identDetailAST,
		String variableName) {

		String methodName = getMethodName(assignMethodCallDetailAST);

		if (methodName.matches("(?i)_?get" + variableName)) {
			return true;
		}

		DetailAST parentDetailAST = identDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return false;
		}

		parentDetailAST = parentDetailAST.getParent();

		if ((parentDetailAST.getType() != TokenTypes.ELIST) ||
			(parentDetailAST.getChildCount() != 1)) {

			return false;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return false;
		}

		methodName = getMethodName(parentDetailAST);

		if (methodName.matches("(?i)_?set" + variableName)) {
			return true;
		}

		return false;
	}

	private static final String _MSG_INLINE_VARIABLE = "variable.inline";

	private static final String _MSG_MOVE_VARIABLE_AFTER_BRANCHING_STATEMENT =
		"variable.move.after.branching.statement";

	private static final String _MSG_MOVE_VARIABLE_INSIDE_IF_STATEMENT =
		"variable.move.inside.if.statement";

}