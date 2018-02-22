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
 * @author Peter Shin
 */
public class ConstructorMissingEmptyLineCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST statementsAST = detailAST.findFirstToken(TokenTypes.SLIST);

		if (statementsAST == null) {
			return;
		}

		List<String> parameterNames = DetailASTUtil.getParameterNames(
			detailAST);

		if (parameterNames.isEmpty()) {
			return;
		}

		DetailAST nextExpressionAST = statementsAST.getFirstChild();

		if (!_isExpressionAssignsParameter(nextExpressionAST, parameterNames)) {
			return;
		}

		int endLine = DetailASTUtil.getEndLine(nextExpressionAST);

		while (true) {
			nextExpressionAST = nextExpressionAST.getNextSibling();

			nextExpressionAST = nextExpressionAST.getNextSibling();

			if ((nextExpressionAST != null) &&
				(nextExpressionAST.getType() == TokenTypes.RCURLY)) {

				return;
			}

			if (!_isExpressionAssignsParameter(
					nextExpressionAST, parameterNames)) {

				int startLine = DetailASTUtil.getStartLine(nextExpressionAST);

				if ((endLine + 1) != startLine) {
					return;
				}

				log(startLine, _MSG_MISSING_EMPTY_LINE, startLine);

				return;
			}

			endLine = DetailASTUtil.getEndLine(nextExpressionAST);
		}
	}

	private boolean _isExpressionAssignsParameter(
		DetailAST expressionAST, List<String> parameters) {

		if ((expressionAST == null) ||
			(expressionAST.getType() != TokenTypes.EXPR)) {

			return false;
		}

		DetailAST childAST = expressionAST.getFirstChild();

		if (childAST.getType() != TokenTypes.ASSIGN) {
			return false;
		}

		if (childAST.getChildCount() != 2) {
			return false;
		}

		DetailAST firstChildAST = childAST.getFirstChild();
		DetailAST lastChildAST = childAST.getLastChild();

		if ((firstChildAST.getType() != TokenTypes.IDENT) ||
			(lastChildAST.getType() != TokenTypes.IDENT)) {

			return false;
		}

		if (!parameters.contains(lastChildAST.getText())) {
			return false;
		}

		DetailAST nextSiblingAST = expressionAST.getNextSibling();

		if (nextSiblingAST.getType() != TokenTypes.SEMI) {
			return false;
		}

		return true;
	}

	private static final String _MSG_MISSING_EMPTY_LINE = "empty.line.missing";

}