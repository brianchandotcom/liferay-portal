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
 * @author Hugo Huijser
 */
public class FilterStringWhitespaceCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkMethod(detailAST, "ServiceTrackerFactory", "open");
		_checkMethod(detailAST, "WaiterUtil", "waitForFilter");
	}

	private void _checkFilterStringAssign(
		DetailAST assignDetailAST, String filterStringVariableName) {

		DetailAST nameDetailAST = null;

		DetailAST parentDetailAST = assignDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
			nameDetailAST = parentDetailAST.findFirstToken(TokenTypes.IDENT);
		}
		else {
			nameDetailAST = assignDetailAST.findFirstToken(TokenTypes.IDENT);
		}

		String name = nameDetailAST.getText();

		if (!name.equals(filterStringVariableName)) {
			return;
		}

		List<DetailAST> literalStringDetailASTList =
			DetailASTUtil.getAllChildTokens(
				assignDetailAST, true, TokenTypes.STRING_LITERAL);

		for (DetailAST literalStringDetailAST : literalStringDetailASTList) {
			String literalStringValue = literalStringDetailAST.getText();

			if (literalStringValue.contains(" = ")) {
				log(nameDetailAST, _MSG_INCORRECT_WHITESPACE, name);

				return;
			}
		}
	}

	private void _checkMethod(
		DetailAST detailAST, String className, String methodName) {

		List<DetailAST> methodCallDetailASTList = DetailASTUtil.getMethodCalls(
			detailAST, className, methodName);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			String filterStringVariableName = _getFilterStringVariableName(
				methodCallDetailAST);

			if (filterStringVariableName == null) {
				continue;
			}

			List<DetailAST> assignDetailASTList =
				DetailASTUtil.getAllChildTokens(
					detailAST, true, TokenTypes.ASSIGN);

			for (DetailAST assignDetailAST : assignDetailASTList) {
				_checkFilterStringAssign(
					assignDetailAST, filterStringVariableName);
			}
		}
	}

	private String _getFilterStringVariableName(DetailAST methodCallDetailAST) {
		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = DetailASTUtil.getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		if (exprDetailASTList.size() < 2) {
			return null;
		}

		DetailAST secondParameterDetailAST = exprDetailASTList.get(1);

		DetailAST firstChildDetailAST =
			secondParameterDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return null;
		}

		return firstChildDetailAST.getText();
	}

	private static final String _MSG_INCORRECT_WHITESPACE =
		"whitespace.incorrect";

}