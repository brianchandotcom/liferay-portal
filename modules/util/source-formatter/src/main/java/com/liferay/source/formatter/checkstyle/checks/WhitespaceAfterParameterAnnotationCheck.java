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
public class WhitespaceAfterParameterAnnotationCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.PARAMETER_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		if (modifiersAST == null) {
			return;
		}

		DetailAST annotationAST = modifiersAST.findFirstToken(
			TokenTypes.ANNOTATION);

		if (annotationAST == null) {
			return;
		}

		DetailAST rparenAST = annotationAST.findFirstToken(TokenTypes.RPAREN);

		if (rparenAST == null) {
			return;
		}

		String line = getLine(rparenAST.getLineNo() - 1);

		if ((rparenAST.getColumnNo() + 1) >= line.length()) {
			return;
		}

		char c = line.charAt(rparenAST.getColumnNo() + 1);

		if (!Character.isWhitespace(c)) {
			log(
				rparenAST.getLineNo(), _MSG_MISSING_WHITESPACE,
				rparenAST.getText());
		}
	}

	private static final String _MSG_MISSING_WHITESPACE = "whitespace.missing";

}