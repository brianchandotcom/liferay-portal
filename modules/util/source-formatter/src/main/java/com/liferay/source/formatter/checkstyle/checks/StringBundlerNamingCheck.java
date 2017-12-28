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

/**
 * @author Hugo Huijser
 */
public class StringBundlerNamingCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String typeName = DetailASTUtil.getTypeName(detailAST);

		if (!typeName.equals("StringBundler")) {
			return;
		}

		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (modifiersAST.branchContains(TokenTypes.LITERAL_PROTECTED) ||
			modifiersAST.branchContains(TokenTypes.LITERAL_PUBLIC)) {

			return;
		}

		String name = _getName(detailAST);

		if (!name.matches("_?(sb|.*SB)([0-9]*)?")) {
			log(
				detailAST.getLineNo(), _MSG_INCORRECT_VARIABLE_NAME,
				_getTokenTypeName(detailAST), name);
		}
	}

	private String _getName(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		return nameAST.getText();
	}

	private String _getTokenTypeName(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
			return "parameter";
		}

		return "variable";
	}

	private static final String _MSG_INCORRECT_VARIABLE_NAME =
		"variable.name.incorrect";

}