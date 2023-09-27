/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Hugo Huijser
 */
public class JavadocCheck extends BaseCheck {

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
	protected void doVisitToken(DetailAST detailAST) {
		FileContents fileContents = getFileContents();

		TextBlock javadoc = fileContents.getJavadocBefore(
			detailAST.getLineNo());

		if ((javadoc == null) || _containsCopyright(javadoc)) {
			return;
		}

		_checkJavadoc(javadoc);

		javadoc = fileContents.getJavadocBefore(javadoc.getStartLineNo());

		if (javadoc != null) {
			DetailAST nameDetailAST = detailAST.findFirstToken(
				TokenTypes.IDENT);

			Object[] arguments = null;

			if (nameDetailAST == null) {
				arguments = new Object[] {_getClassName()};
			}
			else {
				arguments = new Object[] {nameDetailAST.getText()};
			}

			log(detailAST, _MSG_MULTIPLE_JAVADOC, arguments);
		}
	}

	private void _checkJavadoc(TextBlock javadoc) {
		String[] text = javadoc.getText();

		if (text.length == 1) {
			return;
		}

		_checkLine(javadoc, text, 1, "/**", _MSG_INCORRECT_FIRST_LINE, true);
		_checkLine(javadoc, text, 2, StringPool.STAR, _MSG_EMPTY_LINE, false);
		_checkLine(
			javadoc, text, text.length - 1, StringPool.STAR, _MSG_EMPTY_LINE,
			false);
		_checkLine(
			javadoc, text, text.length, "*/", _MSG_INCORRECT_LAST_LINE, true);
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

	private boolean _containsCopyright(TextBlock javadoc) {
		int startLineNo = javadoc.getStartLineNo();

		if ((startLineNo == 1) || (startLineNo == 2)) {
			String[] text = javadoc.getText();

			for (String line : text) {
				if (line.contains("SPDX-FileCopyrightText:")) {
					return true;
				}
			}
		}

		return false;
	}

	private String _getClassName() {
		String absolutePath = getAbsolutePath();

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		return absolutePath.substring(pos + 1, absolutePath.length() - 5);
	}

	private static final String _MSG_EMPTY_LINE = "javadoc.empty.line";

	private static final String _MSG_INCORRECT_FIRST_LINE =
		"javadoc.incorrect.first.line";

	private static final String _MSG_INCORRECT_LAST_LINE =
		"javadoc.incorrect.last.line";

	private static final String _MSG_MULTIPLE_JAVADOC = "javadoc.multiple";

}