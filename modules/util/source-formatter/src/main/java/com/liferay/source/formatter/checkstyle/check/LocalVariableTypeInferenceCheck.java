/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * @author Alan Huang
 */
public class LocalVariableTypeInferenceCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PARAMETER_DEF, TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if ((detailAST.getType() == TokenTypes.VARIABLE_DEF) &&
			!ScopeUtil.isLocalVariableDef(detailAST)) {

			return;
		}

		String typeName = getTypeName(detailAST, false);

		if (typeName.equals("var")) {
			log(detailAST, _MSG_AVOID_VAR);
		}
	}

	private static final String _MSG_AVOID_VAR = "var.avoid";

}