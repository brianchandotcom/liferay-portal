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
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class JSONNamingCheck extends AbstractCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		String typeName = DetailASTUtil.getTypeName(detailAST);

		if (typeName.equals("boolean") || typeName.equals("void")) {
			return;
		}

		String name = _getName(detailAST);
		String tokenTypeName = _getTokenTypeName(detailAST);

		_checkName(
			name, typeName, tokenTypeName, "String", "JSON", "Json",
			detailAST.getLineNo());
		_checkName(
			name, typeName, tokenTypeName, "JSONArray", "JSONArray",
			"JsonArray", detailAST.getLineNo());
		_checkName(
			name, typeName, tokenTypeName, "JSONObject", "JSONObject",
			"JsonObject", detailAST.getLineNo());
	}

	private void _checkName(
		String name, String typeName, String tokenTypeName, String type,
		String reservedNameEnding, String incorrectNameEnding, int lineNo) {

		String lowerCaseName = StringUtil.toLowerCase(name);

		if (!lowerCaseName.endsWith(
				StringUtil.toLowerCase(reservedNameEnding))) {

			return;
		}

		if (!typeName.equals(type) && !typeName.endsWith("." + type)) {
			String lowerCaseTypeName = StringUtil.toLowerCase(typeName);

			if (!lowerCaseTypeName.endsWith(StringUtil.toLowerCase(type))) {
				log(
					lineNo, _MSG_RESERVED_VARIABLE_NAME, tokenTypeName,
					reservedNameEnding, type);
			}

			return;
		}

		if (name.endsWith(incorrectNameEnding)) {
			log(
				lineNo, _MSG_RENAME_VARIABLE,
				StringUtil.toLowerCase(tokenTypeName), name,
				StringUtil.replaceLast(
					name, incorrectNameEnding, reservedNameEnding));
		}
	}

	private String _getName(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		return nameAST.getText();
	}

	private String _getTokenTypeName(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.METHOD_DEF) {
			return "Method";
		}

		if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
			return "Parameter";
		}

		return "Variable";
	}

	private static final String _MSG_RENAME_VARIABLE = "variable.rename";

	private static final String _MSG_RESERVED_VARIABLE_NAME =
		"variable.name.reserved";

}