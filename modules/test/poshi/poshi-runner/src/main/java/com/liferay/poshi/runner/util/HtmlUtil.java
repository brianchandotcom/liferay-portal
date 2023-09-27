/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 * @author Michael Hashimoto
 */
public class HtmlUtil {

	public static String escape(String text) {
		return StringEscapeUtils.escapeHtml(text);
	}

	public static String escapeJS(String js) {
		return StringEscapeUtils.escapeJavaScript(js);
	}

}