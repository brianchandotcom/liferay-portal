/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Eduardo Lundgren
 */
public class AggregateUtil {

	public static String updateRelativeURLs(String content, String baseURL) {
		content = StringUtil.replace(
			content, _CSS_PATH_TYPES, _CSS_PATH_PLACEHOLDERS);

		content = StringUtil.replace(
			content,
			new String[] {"[$RELATIVE_1$]", "[$RELATIVE_2$]", "[$RELATIVE_3$]"},
			new String[] {
				"url('" + baseURL, "url(\"" + baseURL, "url(" + baseURL
			});

		content = StringUtil.replace(
			content, _CSS_PATH_PLACEHOLDERS, _CSS_PATH_TYPES);

		return content;
	}

	private AggregateUtil() {
	}

	private static final String[] _CSS_PATH_PLACEHOLDERS = {
		"[$EMPTY_1$]", "[$EMPTY_2$]", "[$EMPTY_3$]", "[$TOKEN_1$]",
		"[$TOKEN_2$]", "[$TOKEN_3$]", "[$ABSOLUTE_1$]", "[$ABSOLUTE_2$]",
		"[$ABSOLUTE_3$]", "[$ABSOLUTE_4$]", "[$ABSOLUTE_5$]", "[$ABSOLUTE_6$]",
		"[$ABSOLUTE_7$]", "[$ABSOLUTE_8$]", "[$ABSOLUTE_9$]", "[$ABSOLUTE_10$]",
		"[$ABSOLUTE_11$]", "[$ABSOLUTE_12$]", "[$RELATIVE_1$]",
		"[$RELATIVE_2$]", "[$RELATIVE_3$]"
	};

	private static final String[] _CSS_PATH_TYPES = {
		"url('')", "url(\"\")", "url()", "url('@", "url(\"@", "url(@",
		"url('http://", "url(\"http://", "url(http://", "url('https://",
		"url(\"https://", "url(https://", "url('/", "url(\"/", "url(/",
		"url('data:", "url(\"data:", "url(data:", "url('", "url(\"", "url("
	};

}