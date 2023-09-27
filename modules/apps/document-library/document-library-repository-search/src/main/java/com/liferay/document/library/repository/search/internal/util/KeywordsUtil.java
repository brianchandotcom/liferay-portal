/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.search.internal.util;

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