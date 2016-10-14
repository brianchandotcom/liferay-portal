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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class OperatorOrderCheck extends AbstractCheck {

	public static final String MSG_LITERAL_OR_NUM_LEFT_ARGUMENT =
		"left.argument.literal.or.num";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.EQUAL, TokenTypes.GE, TokenTypes.GT, TokenTypes.LE,
			TokenTypes.LT, TokenTypes.NOT_EQUAL
		};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		DetailAST firstChild = detailAST.getFirstChild();

		if (!ArrayUtil.contains(_LITERAL_OR_NUM_TYPES, firstChild.getType())) {
			return;
		}

		DetailAST secondChild = firstChild.getNextSibling();

		if (!ArrayUtil.contains(_LITERAL_OR_NUM_TYPES, secondChild.getType())) {
			log(
				firstChild.getLineNo(), MSG_LITERAL_OR_NUM_LEFT_ARGUMENT,
				firstChild.getText());
		}
	}

	private static final int[] _LITERAL_OR_NUM_TYPES = {
		TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_INT,
		TokenTypes.NUM_LONG, TokenTypes.STRING_LITERAL
	};

}