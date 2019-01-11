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
public class OperatorOrderCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT, TokenTypes.LE,
			TokenTypes.LT, TokenTypes.NOT_EQUAL
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChildDetailAST = detailAST.getFirstChild();
		String message = "";

		if (!ArrayUtil.contains(
				_UNARY_MINUS_OR_UNARY_PLUS, firstChildDetailAST.getType())) {

			if (!ArrayUtil.contains(
					_LITERAL_OR_NUM_TYPES, firstChildDetailAST.getType())) {

				return;
			}
		}
		else {
			DetailAST nameDetailAST = firstChildDetailAST.getFirstChild();

			message = firstChildDetailAST.getText() + nameDetailAST.getText();
		}

		DetailAST secondChildDetailAST = firstChildDetailAST.getNextSibling();

		if (ArrayUtil.contains(
				_UNARY_MINUS_OR_UNARY_PLUS, secondChildDetailAST.getType())) {

			return;
		}

		if (!ArrayUtil.contains(
				_LITERAL_OR_NUM_TYPES, secondChildDetailAST.getType())) {

			if (message.isEmpty()) {
				message = firstChildDetailAST.getText();
			}

			log(
				firstChildDetailAST, _MSG_LITERAL_OR_NUM_LEFT_ARGUMENT,
				message);
		}
	}

	private static final int[] _LITERAL_OR_NUM_TYPES = {
		TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_INT,
		TokenTypes.NUM_LONG, TokenTypes.STRING_LITERAL
	};

	private static final String _MSG_LITERAL_OR_NUM_LEFT_ARGUMENT =
		"left.argument.literal.or.num";

	private static final int[] _UNARY_MINUS_OR_UNARY_PLUS = {
		TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS
	};

}