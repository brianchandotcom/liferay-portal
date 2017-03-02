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

	public static final String MSG_RENAME_VARIABLE = "variable.rename";

	public static final String MSG_RESERVED_VARIABLE_NAME =
		"variable.name.reserved";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		String name = _getVariableName(detailAST);
		String typeName = DetailASTUtil.getTypeName(detailAST);

		_checkVariable(
			name, typeName, "String", "JSON", "Json", detailAST.getLineNo());
		_checkVariable(
			name, typeName, "JSONArray", "JSONArray", "JsonArray",
			detailAST.getLineNo());
		_checkVariable(
			name, typeName, "JSONObject", "JSONObject", "JsonObject",
			detailAST.getLineNo());
	}

	private void _checkVariable(
		String name, String typeName, String type, String reservedNameEnding,
		String incorrectNameEnding, int lineNo) {

		String lowerCaseName = StringUtil.toLowerCase(name);

		if (!lowerCaseName.endsWith(
				StringUtil.toLowerCase(reservedNameEnding))) {

			return;
		}

		if (!typeName.equals(type) && !typeName.endsWith("." + type)) {
			String lowerCaseTypeName = StringUtil.toLowerCase(typeName);

			if (!lowerCaseTypeName.endsWith(StringUtil.toLowerCase(type))) {
				log(
					lineNo, MSG_RESERVED_VARIABLE_NAME, reservedNameEnding,
					type);
			}

			return;
		}

		if (name.endsWith(incorrectNameEnding)) {
			log(
				lineNo, MSG_RENAME_VARIABLE, name,
				StringUtil.replaceLast(
					name, incorrectNameEnding, reservedNameEnding));
		}
	}

	private String _getVariableName(DetailAST detailAST) {
		DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

		return nameAST.getText();
	}

}