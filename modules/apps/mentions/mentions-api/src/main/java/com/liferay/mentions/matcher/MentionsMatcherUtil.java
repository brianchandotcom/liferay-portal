/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.matcher;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 */
public class MentionsMatcherUtil {

	public static String getScreenNameRegularExpression() {
		return _SCREEN_NAME_REGULAR_EXPRESSION;
	}

	private static final String _SCREEN_NAME_REGULAR_EXPRESSION;

	static {
		String specialCharacters = PropsUtil.get(
			PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS);

		String quotedSpecialCharacters = StringUtil.replace(
			specialCharacters,
			new char[] {
				CharPool.AMPERSAND, CharPool.CARET, CharPool.CLOSE_BRACKET,
				CharPool.DASH, CharPool.OPEN_BRACKET
			},
			new String[] {"\\&", "\\^", "\\]", "\\-", "\\["});

		_SCREEN_NAME_REGULAR_EXPRESSION = String.format(
			"(?:\\w|[%s])(?:\\w|\\d|[%s])*", quotedSpecialCharacters,
			quotedSpecialCharacters);
	}

}