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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class JavadocCheck extends AbstractCheck {

	public static final String MSG_EMPTY_LINE = "javadoc.empty.line";

	public static final String MSG_INCORRECT_FIRST_LINE =
		"javadoc.incorrect.first.line";

	public static final String MSG_INCORRECT_LAST_LINE =
		"javadoc.incorrect.last.line";

	public static final String MSG_MULTIPLE_JAVADOC = "javadoc.multiple";

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.ANNOTATION_DEF, TokenTypes.ANNOTATION_FIELD_DEF,
			TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF,
			TokenTypes.ENUM_CONSTANT_DEF, TokenTypes.ENUM_DEF,
			TokenTypes.INTERFACE_DEF, TokenTypes.METHOD_DEF,
			TokenTypes.PACKAGE_DEF, TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		TextBlock javadoc = fileContents.getJavadocBefore(
			detailAST.getLineNo());

		if (javadoc == null) {
			return;
		}

		_checkJavadoc(javadoc);

		javadoc = fileContents.getJavadocBefore(javadoc.getStartLineNo());

		if (javadoc != null) {
			DetailAST nameAST = detailAST.findFirstToken(TokenTypes.IDENT);

			log(detailAST.getLineNo(), MSG_MULTIPLE_JAVADOC, nameAST.getText());
		}
	}

	private void _checkJavadoc(TextBlock javadoc) {
		String[] text = javadoc.getText();

		_checkLine(javadoc, text, 1, "/**", MSG_INCORRECT_FIRST_LINE, true);
		_checkLine(javadoc, text, 2, StringPool.STAR, MSG_EMPTY_LINE, false);
		_checkLine(
			javadoc, text, text.length - 1, StringPool.STAR, MSG_EMPTY_LINE,
			false);
		_checkLine(
			javadoc, text, text.length, "*/", MSG_INCORRECT_LAST_LINE, true);
	}

	private void _checkLine(
		TextBlock javadoc, String[] text, int lineNumber, String expectedValue,
		String message, boolean match) {

		String line = StringUtil.trim(text[lineNumber - 1]);

		if ((match && !line.equals(expectedValue)) ||
			(!match && line.equals(expectedValue))) {

			log(javadoc.getStartLineNo() + lineNumber - 1, message);
		}
	}

}