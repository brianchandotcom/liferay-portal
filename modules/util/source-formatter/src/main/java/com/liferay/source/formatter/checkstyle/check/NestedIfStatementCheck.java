/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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