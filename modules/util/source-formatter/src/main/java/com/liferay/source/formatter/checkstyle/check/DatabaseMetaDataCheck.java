/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Kevin Lee
 */
public class DatabaseMetaDataCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		List<String> importNames = getImportNames(detailAST);

		if (!importNames.contains("java.sql.DatabaseMetaData") ||
			!Objects.equals(getMethodName(detailAST), "getIndexInfo")) {

			return;
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("/com/liferay/portal/dao/db/") &&
			absolutePath.endsWith("DB.java")) {

			DetailAST methodDefinitionDetailAST = getParentWithTokenType(
				detailAST, TokenTypes.METHOD_DEF);

			if ((methodDefinitionDetailAST != null) &&
				Objects.equals(
					getName(methodDefinitionDetailAST), "getIndexResultSet")) {

				return;
			}
		}

		String variableName = getVariableName(detailAST);

		if (variableName == null) {
			return;
		}

		DetailAST variableDefinitionDetailAST = getVariableDefinitionDetailAST(
			detailAST, variableName);

		if (variableDefinitionDetailAST == null) {
			return;
		}

		if (Objects.equals(
				getVariableTypeName(
					variableDefinitionDetailAST, variableName, false),
				"DatabaseMetaData")) {

			log(detailAST, _MSG_REPLACE_GET_INDEX_INFO, variableName);
		}
	}

	private static final String _MSG_REPLACE_GET_INDEX_INFO =
		"replace.get.index.info";

}