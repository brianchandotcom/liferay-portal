/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * @author Hugo Huijser
 */
public class JSONNamingCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if ((detailAST.getType() == TokenTypes.METHOD_DEF) &&
			AnnotationUtil.containsAnnotation(detailAST, "Override")) {

			return;
		}

		String typeName = getTypeName(detailAST, false);

		if (typeName.equals("boolean") || typeName.equals("void")) {
			return;
		}

		String name = getName(detailAST);
		String tokenTypeName = _getTokenTypeName(detailAST);

		_checkName(
			name, typeName, tokenTypeName, "String", "JSON", "Json",
			detailAST.getLineNo(), _TOKEN_TYPE_NAMES);
		_checkName(
			name, typeName, tokenTypeName, "JSONArray", "JSONArray",
			"JsonArray", detailAST.getLineNo(), _TOKEN_TYPE_NAMES);
		_checkName(
			name, typeName, tokenTypeName, "JSONObject", "JSONObject",
			"JsonObject", detailAST.getLineNo(), _TOKEN_TYPE_NAMES);

		_checkName(
			name, typeName, tokenTypeName, "JSON", "JSONString",
			detailAST.getLineNo(), new String[] {_TOKEN_TYPE_NAME_VARIABLE});
		_checkName(
			name, typeName, tokenTypeName, "JSON", "JsonString",
			detailAST.getLineNo(), new String[] {_TOKEN_TYPE_NAME_VARIABLE});
	}

	private void _checkName(
		String name, String typeName, String tokenTypeName,
		String validNameEnding, String incorrectNameEnding, int lineNo,
		String[] checkTokenTypeNames) {

		if (name.endsWith(incorrectNameEnding) &&
			!StringUtil.endsWith(name, typeName) &&
			ArrayUtil.contains(checkTokenTypeNames, tokenTypeName)) {

			log(
				lineNo, _MSG_RENAME_VARIABLE,
				StringUtil.toLowerCase(tokenTypeName), name,
				StringUtil.replaceLast(
					name, incorrectNameEnding, validNameEnding));
		}
	}

	private void _checkName(
		String name, String typeName, String tokenTypeName, String type,
		String reservedNameEnding, String incorrectNameEnding, int lineNo,
		String[] checkTokenTypeNames) {

		String lowerCaseName = StringUtil.toLowerCase(name);

		if (!lowerCaseName.endsWith(
				StringUtil.toLowerCase(incorrectNameEnding))) {

			return;
		}

		if (!typeName.equals(type) && !typeName.endsWith("." + type)) {
			if (tokenTypeName.equals("Method") && !name.startsWith("get")) {
				return;
			}

			String lowerCaseTypeName = StringUtil.toLowerCase(typeName);

			if (!lowerCaseTypeName.endsWith(StringUtil.toLowerCase(type))) {
				log(
					lineNo, _MSG_RESERVED_VARIABLE_NAME, tokenTypeName,
					reservedNameEnding, type);
			}

			return;
		}

		_checkName(
			name, typeName, tokenTypeName, reservedNameEnding,
			incorrectNameEnding, lineNo, checkTokenTypeNames);
	}

	private String _getTokenTypeName(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.METHOD_DEF) {
			return _TOKEN_TYPE_NAME_METHOD;
		}

		if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
			return _TOKEN_TYPE_NAME_PARAMETER;
		}

		return _TOKEN_TYPE_NAME_VARIABLE;
	}

	private static final String _MSG_RENAME_VARIABLE = "variable.rename";

	private static final String _MSG_RESERVED_VARIABLE_NAME =
		"variable.name.reserved";

	private static final String _TOKEN_TYPE_NAME_METHOD = "Method";

	private static final String _TOKEN_TYPE_NAME_PARAMETER = "Parameter";

	private static final String _TOKEN_TYPE_NAME_VARIABLE = "Variable";

	private static final String[] _TOKEN_TYPE_NAMES = {
		_TOKEN_TYPE_NAME_METHOD, _TOKEN_TYPE_NAME_PARAMETER,
		_TOKEN_TYPE_NAME_VARIABLE
	};

}