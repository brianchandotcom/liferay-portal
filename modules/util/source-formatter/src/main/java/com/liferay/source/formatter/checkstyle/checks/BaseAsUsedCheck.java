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
		DetailAST detailAST, String variableName,
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

		int endLineNumber = getEndLineNumber(detailAST);

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
			(parentDetailAST.getLineNo() >= detailAST.getLineNo())) {

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
			detailAST, _MSG_INLINE_VARIABLE, variableName,
			identDetailAST.getLineNo());
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

}