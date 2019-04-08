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

package com.liferay.source.formatter.checkstyle.checks;

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
			_CONDITIONAL_OPERATOR_TOKEN_TYPES, TokenTypes.QUESTION);
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		if (detailAST.getType() == TokenTypes.QUESTION) {
			if (ArrayUtil.contains(
					_RELATIONAL_OPERATOR_TOKEN_TYPES,
					firstChildDetailAST.getType())) {

				log(
					firstChildDetailAST.getFirstChild(),
					_MSG_MISSING_PARENTHESES_2, "left", detailAST.getText());
			}

			return;
		}

		if ((firstChildDetailAST.getType() != detailAST.getType()) &&
			ArrayUtil.contains(
				_CONDITIONAL_OPERATOR_TOKEN_TYPES,
				firstChildDetailAST.getType())) {

			log(
				firstChildDetailAST.getFirstChild(), _MSG_MISSING_PARENTHESES_1,
				firstChildDetailAST.getText(), detailAST.getText());
		}

		if (ArrayUtil.contains(
				_RELATIONAL_OPERATOR_TOKEN_TYPES,
				firstChildDetailAST.getType())) {

			log(
				firstChildDetailAST.getFirstChild(), _MSG_MISSING_PARENTHESES_2,
				"left", detailAST.getText());
		}

		DetailAST lastChildDetailAST = detailAST.getLastChild();

		if (ArrayUtil.contains(
				_RELATIONAL_OPERATOR_TOKEN_TYPES,
				lastChildDetailAST.getType())) {

			log(
				lastChildDetailAST.getFirstChild(), _MSG_MISSING_PARENTHESES_2,
				"right", detailAST.getText());
		}
	}

	private static final int[] _CONDITIONAL_OPERATOR_TOKEN_TYPES = {
		TokenTypes.BAND, TokenTypes.BOR, TokenTypes.BXOR, TokenTypes.LAND,
		TokenTypes.LOR
	};

	private static final String _MSG_MISSING_PARENTHESES_1 =
		"parentheses.missing.1";

	private static final String _MSG_MISSING_PARENTHESES_2 =
		"parentheses.missing.2";

	private static final int[] _RELATIONAL_OPERATOR_TOKEN_TYPES = {
		TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT, TokenTypes.LE,
		TokenTypes.LT, TokenTypes.NOT_EQUAL
	};

}