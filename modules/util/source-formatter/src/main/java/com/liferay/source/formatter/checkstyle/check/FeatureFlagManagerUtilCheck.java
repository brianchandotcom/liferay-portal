/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Anderson Luiz
 */
public class FeatureFlagManagerUtilCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST methodCallDetailAST = detailAST.findFirstToken(
			TokenTypes.DOT);

		if (methodCallDetailAST == null) {
			return;
		}

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			methodCallDetailAST, false, TokenTypes.IDENT);

		if (methodCallDetailASTList.size() != 2) {
			return;
		}

		String className = methodCallDetailASTList.get(
			0
		).getText();
		String methodName = methodCallDetailASTList.get(
			1
		).getText();

		if (!Objects.equals(className, "FeatureFlagManagerUtil") ||
			!Objects.equals(methodName, "isEnabled")) {

			return;
		}

		detailAST = detailAST.findFirstToken(TokenTypes.ELIST);

		if (detailAST == null) {
			return;
		}

		if (detailAST.getChildCount(TokenTypes.EXPR) == 1) {
			log(detailAST, _MSG_AVOID_METHOD_CALL);
		}
	}

	private static final String _MSG_AVOID_METHOD_CALL = "avoid.method.call";

}