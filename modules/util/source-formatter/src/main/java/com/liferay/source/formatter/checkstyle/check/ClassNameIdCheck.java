/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Alan Huang
 */
public class ClassNameIdCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String absolutePath = getAbsolutePath();

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/")) {

			return;
		}

		String className = JavaSourceUtil.getClassName(absolutePath);

		if (className.equals("ClassNameLocalServiceImpl") ||
			className.equals("StagedModelType") ||
			className.endsWith("Criterion") ||
			className.endsWith("DisplayContext") ||
			className.endsWith("ModelImpl") || className.endsWith("Tag")) {

			return;
		}

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST != null) {
			return;
		}

		List<DetailAST> variableDefDetailASTList = getAllChildTokens(
			detailAST.findFirstToken(TokenTypes.OBJBLOCK), false,
			TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefDetailAST : variableDefDetailASTList) {
			if (!StringUtil.equalsIgnoreCase(
					getTypeName(variableDefDetailAST, false, false, false),
					"long")) {

				continue;
			}

			String variableName = getName(variableDefDetailAST);

			if (variableName.contains("ClassNameId") ||
				variableName.contains("classNameId")) {

				log(
					variableDefDetailAST, _MSG_AVOID_VARIABLE_NAME,
					variableName);
			}
		}
	}

	private static final String _MSG_AVOID_VARIABLE_NAME =
		"variable.name.avoid";

}