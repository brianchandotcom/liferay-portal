/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class TransactionalTestRuleCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.IMPORT};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String line = getLine(detailAST.getLineNo());

		if (!line.contains(
				"import com.liferay.portal.test.rule.TransactionalTestRule;")) {

			return;
		}

		String absolutePath = getAbsolutePath();

		if (absolutePath.endsWith("StagedModelDataHandlerTest.java")) {
			log(detailAST, _MSG_INVALID_IMPORT);
		}
	}

	private static final String _MSG_INVALID_IMPORT = "import.invalid";

}