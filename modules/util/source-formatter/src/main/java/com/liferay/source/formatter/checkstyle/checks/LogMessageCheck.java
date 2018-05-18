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
public class LogMessageCheck extends MessageCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkMethod(detailAST, "_log", "debug");
		_checkMethod(detailAST, "_log", "error");
		_checkMethod(detailAST, "_log", "info");
		_checkMethod(detailAST, "_log", "trace");
		_checkMethod(detailAST, "_log", "warn");
	}

	private void _checkMethod(
		DetailAST detailAST, String variableName, String methodName) {

		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, variableName, methodName);

		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

			List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
				elistAST, false, TokenTypes.EXPR);

			for (DetailAST exprAST : exprASTList) {
				checkMessage(
					getLiteralStringValue(exprAST), exprAST.getLineNo());
			}
		}
	}

}