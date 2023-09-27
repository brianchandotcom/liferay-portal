/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Peter Shin
 */
public class ConstructorMissingEmptyLineCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST statementsDetailAST = detailAST.findFirstToken(
			TokenTypes.SLIST);

		if (statementsDetailAST == null) {
			return;
		}

		List<String> parameterNames = getParameterNames(detailAST);

		if (parameterNames.isEmpty()) {
			return;
		}

		DetailAST nextExpressionDetailAST = statementsDetailAST.getFirstChild();

		if (!_isExpressionAssignsParameter(
				nextExpressionDetailAST, parameterNames)) {

			return;
		}

		int endLineNumber = getEndLineNumber(nextExpressionDetailAST);

		while (true) {
			nextExpressionDetailAST = nextExpressionDetailAST.getNextSibling();

			nextExpressionDetailAST = nextExpressionDetailAST.getNextSibling();

			if ((nextExpressionDetailAST != null) &&
				(nextExpressionDetailAST.getType() == TokenTypes.RCURLY)) {

				return;
			}

			if (!_isExpressionAssignsParameter(
					nextExpressionDetailAST, parameterNames)) {

				int startLineNumber = getStartLineNumber(
					nextExpressionDetailAST);

				if ((endLineNumber + 1) != startLineNumber) {
					return;
				}

				log(startLineNumber, _MSG_MISSING_EMPTY_LINE, startLineNumber);

				return;
			}

			endLineNumber = getEndLineNumber(nextExpressionDetailAST);
		}
	}

	private boolean _isExpressionAssignsParameter(
		DetailAST expressionDetailAST, List<String> parameters) {

		if ((expressionDetailAST == null) ||
			(expressionDetailAST.getType() != TokenTypes.EXPR)) {

			return false;
		}

		DetailAST childDetailAST = expressionDetailAST.getFirstChild();

		if ((childDetailAST.getType() != TokenTypes.ASSIGN) ||
			(childDetailAST.getChildCount() != 2)) {

			return false;
		}

		DetailAST firstChildDetailAST = childDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			if (firstChildDetailAST.getChildCount() != 2) {
				return false;
			}

			DetailAST detailAST1 = firstChildDetailAST.getFirstChild();
			DetailAST detailAST2 = firstChildDetailAST.getLastChild();

			if ((detailAST1.getType() != TokenTypes.LITERAL_THIS) ||
				(detailAST2.getType() != TokenTypes.IDENT)) {

				return false;
			}
		}

		DetailAST lastChildDetailAST = childDetailAST.getLastChild();

		if (lastChildDetailAST.getType() == TokenTypes.IDENT) {
			String text = lastChildDetailAST.getText();

			if (!parameters.contains(text) && !text.matches("^[A-Z0-9_]+$")) {
				return false;
			}
		}

		DetailAST nextSiblingDetailAST = expressionDetailAST.getNextSibling();

		if (nextSiblingDetailAST.getType() != TokenTypes.SEMI) {
			return false;
		}

		return true;
	}

	private static final String _MSG_MISSING_EMPTY_LINE = "empty.line.missing";

}