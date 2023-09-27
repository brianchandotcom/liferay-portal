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
public class TernaryOperatorCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.QUESTION};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		_checkTernaryExpression(detailAST, detailAST.getFirstChild());

		DetailAST colonDetailAST = detailAST.findFirstToken(TokenTypes.COLON);

		_checkTernaryExpression(detailAST, colonDetailAST.getNextSibling());
		_checkTernaryExpression(detailAST, colonDetailAST.getPreviousSibling());
	}

	private void _checkTernaryExpression(
		DetailAST questionDetailAST, DetailAST expressionDetailAST) {

		while (true) {
			if (expressionDetailAST.getType() == TokenTypes.LPAREN) {
				expressionDetailAST = expressionDetailAST.getNextSibling();
			}
			else if (expressionDetailAST.getType() == TokenTypes.RPAREN) {
				expressionDetailAST = expressionDetailAST.getPreviousSibling();
			}
			else {
				break;
			}
		}

		if (getStartLineNumber(expressionDetailAST) != getEndLineNumber(
				expressionDetailAST)) {

			log(questionDetailAST, _MSG_AVOID_TERNARY_OPERATOR);
		}
	}

	private static final String _MSG_AVOID_TERNARY_OPERATOR =
		"ternary.operator.avoid";

}