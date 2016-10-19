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

package com.liferay.mentions.matcher;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 */
public class MentionsMatcherUtil {

	public static String getScreenNameRegularExpression() {
		String specialCharacters = PropsUtil.get(
			PropsKeys.USERS_SCREEN_NAME_SPECIAL_CHARACTERS);

		String quotedSpecialCharacters = StringUtil.replace(
			specialCharacters,
			new String[] {
				StringPool.AMPERSAND, StringPool.CARET,
				StringPool.CLOSE_BRACKET, StringPool.DASH,
				StringPool.OPEN_BRACKET
			},
			new String[] {"\\&", "\\^", "\\]", "\\-", "\\["});

		return String.format(
			"(?:\\w|[%s])(?:\\w|\\d|[%s])*", quotedSpecialCharacters,
			quotedSpecialCharacters);
	}

}