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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class LineEndCharacterCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.GENERIC_START, TokenTypes.LPAREN, TokenTypes.TYPECAST
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.LPAREN) {
			DetailAST parentAST = detailAST.getParent();

			if ((parentAST.getType() == TokenTypes.ANNOTATION) ||
				(parentAST.getType() == TokenTypes.CTOR_CALL) ||
				(parentAST.getType() == TokenTypes.CTOR_DEF) ||
				(parentAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) ||
				(parentAST.getType() == TokenTypes.LAMBDA) ||
				(parentAST.getType() == TokenTypes.LITERAL_CATCH) ||
				(parentAST.getType() == TokenTypes.LITERAL_NEW) ||
				(parentAST.getType() == TokenTypes.METHOD_DEF) ||
				(parentAST.getType() == TokenTypes.SUPER_CTOR_CALL)) {

				return;
			}
		}

		if (_isAtLineEnd(detailAST)) {
			log(
				detailAST.getLineNo(), _MSG_INCORRECT_END_LINE_CHARACTER,
				detailAST.getText());
		}
	}

	private boolean _isAtLineEnd(DetailAST detailAST) {
		String line = getLine(detailAST.getLineNo() - 1);

		String text = detailAST.getText();

		if (line.endsWith(text) &&
			((detailAST.getColumnNo() + text.length()) == line.length())) {

			return true;
		}

		return false;
	}

	private static final String _MSG_INCORRECT_END_LINE_CHARACTER =
		"end.line.character.incorrect";

}