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
import java.util.Objects;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class StringCastCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, "toString");

		outerLoop:
		for (DetailAST methodCallAST : methodCallASTList) {
			DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

			List<DetailAST> nameASTList = DetailASTUtil.getAllChildTokens(
				dotAST, false, TokenTypes.IDENT);

			DetailAST variableNameAST = nameASTList.get(0);

			Set<String> variableTypeNames = DetailASTUtil.getVariableTypeNames(
				detailAST, variableNameAST.getText());

			if (variableTypeNames.isEmpty()) {
				continue;
			}

			for (String variableTypeName : variableTypeNames) {
				if (!Objects.equals(variableTypeName, "String")) {
					continue outerLoop;
				}
			}

			log(methodCallAST.getLineNo(), _MSG_UNNEEDED_STRING_CAST);
		}
	}

	private static final String _MSG_UNNEEDED_STRING_CAST =
		"string.cast.unneeded";

}