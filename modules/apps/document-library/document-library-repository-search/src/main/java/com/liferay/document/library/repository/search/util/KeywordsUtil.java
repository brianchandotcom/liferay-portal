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

package com.liferay.document.library.repository.search.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Mirco Tamburini
 * @author Josiah Goh
 */
public class KeywordsUtil {

	public static final String[] SPECIAL = {
		"+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^", "\"", "~",
		"*", "?", ":", "\\"
	};

	public static String escape(String text) {
		for (int i = SPECIAL.length - 1; i >= 0; i--) {
			text = StringUtil.replace(
				text, SPECIAL[i], StringPool.BACK_SLASH + SPECIAL[i]);
		}

		return text;
	}

	public static String toFuzzy(String keywords) {
		if (keywords == null) {
			return null;
		}

		if (!keywords.endsWith(StringPool.TILDE)) {
			keywords = keywords + StringPool.TILDE;
		}

		return keywords;
	}

	public static String toWildcard(String keywords) {
		if (keywords == null) {
			return null;
		}

		if (!keywords.endsWith(StringPool.STAR)) {
			keywords = keywords + StringPool.STAR;
		}

		return keywords;
	}

}