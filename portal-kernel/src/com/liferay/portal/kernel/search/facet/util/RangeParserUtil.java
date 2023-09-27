/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.facet.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Raymond Augé
 */
public class RangeParserUtil {

	public static String[] parserRange(String range) {
		range = StringUtil.replace(
			range,
			new char[] {CharPool.OPEN_CURLY_BRACE, CharPool.CLOSE_CURLY_BRACE},
			new char[] {CharPool.OPEN_BRACKET, CharPool.CLOSE_BRACKET});

		int x = range.indexOf(StringPool.OPEN_BRACKET);
		int y = range.indexOf(" TO ");
		int z = range.indexOf(StringPool.CLOSE_BRACKET);

		if ((x < 0) || (y < 0) || (z < 0)) {
			return new String[] {null, null};
		}

		String lower = range.substring(x + 1, y);
		String upper = range.substring(y + 4, z);

		return new String[] {lower.trim(), upper.trim()};
	}

}