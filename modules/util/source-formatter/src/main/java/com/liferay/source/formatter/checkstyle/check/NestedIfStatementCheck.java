/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class NestedIfStatementCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_IF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.LITERAL_ELSE) {
			return;
		}

		int closingCurlyBraceLineNumber = _getClosingCurlyBraceLineNumber(
			detailAST);

		if (closingCurlyBraceLineNumber == -1) {
			return;
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		DetailAST firstChildDetailAST = lastChildDetailAST.getFirstChild();

		if ((firstChildDetailAST.getType() != TokenTypes.LITERAL_IF) ||
			(getHiddenBefore(firstChildDetailAST) != null)) {

			return;
		}

		DetailAST exprDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.EXPR);

		if (exprDetailAST == null) {
			return;
		}

		for (String text : getNames(exprDetailAST, true)) {
			if (Objects.equals(text, "_log") ||
				Objects.equals(text, "_logger") ||
				Objects.equals(text, "log") || Objects.equals(text, "logger")) {

				return;
			}
		}

		int closingCurlyBraceInnerIfStatementLineNumber =
			_getClosingCurlyBraceLineNumber(firstChildDetailAST);

		if ((closingCurlyBraceLineNumber - 1) ==
				closingCurlyBraceInnerIfStatementLineNumber) {

			log(detailAST, _MSG_COMBINE_IF_STATEMENTS);
		}
	}

	private int _getClosingCurlyBraceLineNumber(DetailAST literalIfDetailAST) {
		DetailAST lastChildDetailAST = literalIfDetailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.SLIST) {
			return -1;
		}

		lastChildDetailAST = lastChildDetailAST.getLastChild();

		if (lastChildDetailAST.getType() == TokenTypes.RCURLY) {
			return lastChildDetailAST.getLineNo();
		}

		return -1;
	}

	private static final String _MSG_COMBINE_IF_STATEMENTS =
		"if.statements.combine";

}