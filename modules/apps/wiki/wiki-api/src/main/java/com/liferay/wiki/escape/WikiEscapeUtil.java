/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.escape;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Iván Zaera
 */
public class WikiEscapeUtil {

	public static String escapeName(String name) {
		return StringUtil.replace(name, _UNESCAPED_CHARS, _ESCAPED_CHARS);
	}

	public static String unescapeName(String name) {
		return StringUtil.replace(name, _ESCAPED_CHARS, _UNESCAPED_CHARS);
	}

	private static final String[] _ESCAPED_CHARS = {
		"<AMPERSAND>", "<APOSTROPHE>", "<AT>", "<CLOSE_BRACKET>",
		"<CLOSE_PARENTHESIS>", "<COLON>", "<COMMA>", "<DOLLAR>", "<EQUAL>",
		"<EXCLAMATION>", "<OPEN_BRACKET>", "<OPEN_PARENTHESIS>", "<PLUS>",
		"<POUND>", "<QUESTION>", "<SEMICOLON>", "<SLASH>", "<STAR>"
	};

	private static final String[] _UNESCAPED_CHARS = {
		StringPool.AMPERSAND, StringPool.APOSTROPHE, StringPool.AT,
		StringPool.CLOSE_BRACKET, StringPool.CLOSE_PARENTHESIS,
		StringPool.COLON, StringPool.COMMA, StringPool.DOLLAR, StringPool.EQUAL,
		StringPool.EXCLAMATION, StringPool.OPEN_BRACKET,
		StringPool.OPEN_PARENTHESIS, StringPool.PLUS, StringPool.POUND,
		StringPool.QUESTION, StringPool.SEMICOLON, StringPool.SLASH,
		StringPool.STAR
	};

}