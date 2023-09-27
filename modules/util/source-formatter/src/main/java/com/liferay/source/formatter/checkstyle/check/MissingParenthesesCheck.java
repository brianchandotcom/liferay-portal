/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.util.ArrayUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class MissingParenthesesCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return ArrayUtil.append(
			CONDITIONAL_OPERATOR_TOKEN_TYPES, TokenTypes.QUESTION);
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (detailAST.getType() == TokenTypes.QUESTION) {
			if (ArrayUtil.contains(
					RELATIONAL_OPERATOR_TOKEN_TYPES,
					firstChildDetailAST.getType())) {

				log(
					firstChildDetailAST.getFirstChild(),
					_MSG_MISSING_PARENTHESES_2, "left", detailAST.getText());
			}

			return;
		}

		if ((firstChildDetailAST.getType() != detailAST.getType()) &&
			ArrayUtil.contains(
				CONDITIONAL_OPERATOR_TOKEN_TYPES,
				firstChildDetailAST.getType())) {

			log(
				firstChildDetailAST.getFirstChild(), _MSG_MISSING_PARENTHESES_1,
				firstChildDetailAST.getText(), detailAST.getText());
		}

		if (ArrayUtil.contains(
				RELATIONAL_OPERATOR_TOKEN_TYPES,
				firstChildDetailAST.getType())) {

			log(
				firstChildDetailAST.getFirstChild(), _MSG_MISSING_PARENTHESES_2,
				"left", detailAST.getText());
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if (ArrayUtil.contains(
				RELATIONAL_OPERATOR_TOKEN_TYPES,
				lastChildDetailAST.getType())) {

			log(
				lastChildDetailAST.getFirstChild(), _MSG_MISSING_PARENTHESES_2,
				"right", detailAST.getText());
		}
	}

	private static final String _MSG_MISSING_PARENTHESES_1 =
		"parentheses.missing.1";

	private static final String _MSG_MISSING_PARENTHESES_2 =
		"parentheses.missing.2";

}