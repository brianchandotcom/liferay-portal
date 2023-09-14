/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Qi Zhang
 */
public class MethodEqualsCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (!StringUtil.equals(getMethodName(detailAST), "equals")) {
			return;
		}

		DetailAST firstChildDetailAST = detailAST.findFirstToken(
			TokenTypes.DOT);

		if (firstChildDetailAST == null) {
			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
			return;
		}

		log(detailAST, _MSG_USE_OBJECTS_EQUALS);
	}

	private static final String _MSG_USE_OBJECTS_EQUALS = "objects.equals.use";

}