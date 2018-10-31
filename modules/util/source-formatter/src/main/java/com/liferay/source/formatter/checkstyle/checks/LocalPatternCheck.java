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
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class LocalPatternCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (!ScopeUtil.isLocalVariableDef(detailAST)) {
			return;
		}

		List<DetailAST> methodCallDetailASTList = DetailASTUtil.getMethodCalls(
			detailAST, "Pattern", "compile");

		if (methodCallDetailASTList.isEmpty()) {
			return;
		}

		DetailAST methodCallDetailAST = methodCallDetailASTList.get(0);

		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		DetailAST expressionDetailAST = elistDetailAST.findFirstToken(
			TokenTypes.EXPR);

		List<DetailAST> childDetailASTList = DetailASTUtil.getAllChildTokens(
			expressionDetailAST, true, DetailASTUtil.ALL_TYPES);

		for (DetailAST childDetailAST : childDetailASTList) {
			if ((childDetailAST.getType() != TokenTypes.PLUS) &&
				(childDetailAST.getType() != TokenTypes.STRING_LITERAL)) {

				return;
			}
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		log(detailAST, _MSG_LOCAL_PATTERN, nameDetailAST.getText());
	}

	private static final String _MSG_LOCAL_PATTERN = "pattern.local";

}