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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class FilterStringWhitespaceCheck extends AbstractCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		_checkMethod(detailAST, "ServiceTrackerFactory", "open");
		_checkMethod(detailAST, "WaiterUtil", "waitForFilter");
	}

	private void _checkFilterStringAssign(
		DetailAST assignAST, String filterStringVariableName) {

		DetailAST nameAST = null;

		DetailAST parentAST = assignAST.getParent();

		if (parentAST.getType() == TokenTypes.VARIABLE_DEF) {
			nameAST = parentAST.findFirstToken(TokenTypes.IDENT);
		}
		else {
			nameAST = assignAST.findFirstToken(TokenTypes.IDENT);
		}

		String name = nameAST.getText();

		if (!name.equals(filterStringVariableName)) {
			return;
		}

		List<DetailAST> literalStringASTList = DetailASTUtil.getAllChildTokens(
			assignAST, true, TokenTypes.STRING_LITERAL);

		for (DetailAST literalStringAST : literalStringASTList) {
			String literalStringValue = literalStringAST.getText();

			if (literalStringValue.contains(" = ")) {
				log(nameAST.getLineNo(), _MSG_INCORRECT_WHITESPACE, name);

				return;
			}
		}
	}

	private void _checkMethod(
		DetailAST detailAST, String className, String methodName) {

		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, className, methodName);

		for (DetailAST methodCallAST : methodCallASTList) {
			String filterStringVariableName = _getFilterStringVariableName(
				methodCallAST);

			if (filterStringVariableName == null) {
				continue;
			}

			List<DetailAST> assignASTList = DetailASTUtil.getAllChildTokens(
				detailAST, true, TokenTypes.ASSIGN);

			for (DetailAST assignAST : assignASTList) {
				_checkFilterStringAssign(assignAST, filterStringVariableName);
			}
		}
	}

	private String _getFilterStringVariableName(DetailAST methodCallAST) {
		DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
			elistAST, false, TokenTypes.EXPR);

		if (exprASTList.size() < 2) {
			return null;
		}

		DetailAST secondParameterAST = exprASTList.get(1);

		DetailAST firstChildAST = secondParameterAST.getFirstChild();

		if (firstChildAST.getType() != TokenTypes.IDENT) {
			return null;
		}

		return firstChildAST.getText();
	}

	private static final String _MSG_INCORRECT_WHITESPACE =
		"whitespace.incorrect";

}