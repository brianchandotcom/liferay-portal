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

		if (!ArrayUtil.contains(
				_LITERAL_OR_NUM_TYPES, firstChildDetailAST.getType())) {

			return;
		}

		DetailAST firstGrandChildDetailAST =
			firstChildDetailAST.getFirstChild();

		if ((firstGrandChildDetailAST != null) &&
			!ArrayUtil.contains(
				_LITERAL_OR_NUM_TYPES, firstGrandChildDetailAST.getType())) {

			return;
		}

		DetailAST secondChildDetailAST = firstChildDetailAST.getNextSibling();

		if (!ArrayUtil.contains(
				_LITERAL_OR_NUM_TYPES, secondChildDetailAST.getType())) {

			log(
				firstChildDetailAST, _MSG_LITERAL_OR_NUM_LEFT_ARGUMENT,
				_getStringValue(firstChildDetailAST));
		}
	}

	private String _getStringValue(DetailAST detailAST) {
		if ((detailAST.getType() == TokenTypes.UNARY_MINUS) ||
			(detailAST.getType() == TokenTypes.UNARY_PLUS)) {

			DetailAST firstChildDetailAST = detailAST.getFirstChild();

			return detailAST.getText() + firstChildDetailAST.getText();
		}

		return detailAST.getText();
	}

	private static final int[] _LITERAL_OR_NUM_TYPES = {
		TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_INT,
		TokenTypes.NUM_LONG, TokenTypes.STRING_LITERAL, TokenTypes.UNARY_MINUS,
		TokenTypes.UNARY_PLUS
	};

	private static final String _MSG_LITERAL_OR_NUM_LEFT_ARGUMENT =
		"left.argument.literal.or.num";

}