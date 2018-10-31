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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

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
			DetailAST parentDetailAST = detailAST.getParent();

			if ((parentDetailAST.getType() == TokenTypes.ANNOTATION) ||
				(parentDetailAST.getType() == TokenTypes.CTOR_CALL) ||
				(parentDetailAST.getType() == TokenTypes.CTOR_DEF) ||
				(parentDetailAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) ||
				(parentDetailAST.getType() == TokenTypes.LAMBDA) ||
				(parentDetailAST.getType() == TokenTypes.LITERAL_CATCH) ||
				(parentDetailAST.getType() == TokenTypes.LITERAL_NEW) ||
				(parentDetailAST.getType() == TokenTypes.METHOD_DEF) ||
				(parentDetailAST.getType() == TokenTypes.SUPER_CTOR_CALL)) {

				return;
			}
		}

		if (DetailASTUtil.isAtLineEnd(
				detailAST, getLine(detailAST.getLineNo() - 1))) {

			log(
				detailAST, _MSG_INCORRECT_END_LINE_CHARACTER,
				detailAST.getText());
		}
	}

	private static final String _MSG_INCORRECT_END_LINE_CHARACTER =
		"end.line.character.incorrect";

}