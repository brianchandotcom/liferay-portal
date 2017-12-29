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

import com.liferay.petra.string.CharPool;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class WhitespaceAroundGenericsCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.GENERIC_END, TokenTypes.GENERIC_START};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (!DetailASTUtil.hasParentWithTokenType(detailAST, TokenTypes.TYPE)) {
			return;
		}

		String line = getLine(detailAST.getLineNo() - 1);

		if (detailAST.getType() == TokenTypes.GENERIC_END) {
			if ((detailAST.getColumnNo() + 1) >= line.length()) {
				return;
			}

			char c = line.charAt(detailAST.getColumnNo() + 1);

			if ((c != CharPool.CLOSE_PARENTHESIS) && (c != CharPool.COMMA) &&
				(c != CharPool.PERIOD) && (c != CharPool.GREATER_THAN) &&
				(c != CharPool.OPEN_BRACKET) && (c != CharPool.SPACE)) {

				log(
					detailAST.getLineNo(), _MSG_MISSING_WHITESPACE,
					detailAST.getText());
			}
		}
		else {
			char c = line.charAt(detailAST.getColumnNo() - 1);

			if (c == CharPool.SPACE) {
				log(
					detailAST.getLineNo(), _MSG_REDUNDANT_WHITESPACE,
					detailAST.getText());
			}
		}
	}

	private static final String _MSG_MISSING_WHITESPACE = "whitespace.missing";

	private static final String _MSG_REDUNDANT_WHITESPACE =
		"whitespace.redundant";

}