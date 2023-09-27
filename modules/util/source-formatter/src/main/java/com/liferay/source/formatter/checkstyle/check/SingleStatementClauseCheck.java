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
public class SingleStatementClauseCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_IF,
			TokenTypes.LITERAL_WHILE
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if ((lastChildDetailAST.getType() == TokenTypes.LITERAL_RETURN) ||
			(lastChildDetailAST.getType() == TokenTypes.SEMI)) {

			log(detailAST, _MSG_MISSING_BRACES, detailAST.getText());
		}
	}

	private static final String _MSG_MISSING_BRACES = "braces.missing";

}