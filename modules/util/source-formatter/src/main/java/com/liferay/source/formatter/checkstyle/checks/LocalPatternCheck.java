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
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class LocalPatternCheck extends AbstractCheck {

	public static final String MSG_LOCAL_PATTERN = "pattern.local";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		if (!ScopeUtils.isLocalVariableDef(detailAST)) {
			return;
		}

		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, "Pattern", "compile");

		if (methodCallASTList.isEmpty()) {
			return;
		}

		DetailAST methodCallAST = methodCallASTList.get(0);

		DetailAST elistAST = methodCallAST.findFirstToken(TokenTypes.ELIST);

		DetailAST expressionAST = elistAST.findFirstToken(TokenTypes.EXPR);

		List<DetailAST> childASTList = DetailASTUtil.getAllChildTokens(
			expressionAST, true, DetailASTUtil.ALL_TYPES);

		for (DetailAST childAST : childASTList) {
			if ((childAST.getType() != TokenTypes.PLUS) &&
				(childAST.getType() != TokenTypes.STRING_LITERAL)) {

				return;
			}
		}

		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		log(detailAST.getLineNo(), MSG_LOCAL_PATTERN, nameAST.getText());
	}

}