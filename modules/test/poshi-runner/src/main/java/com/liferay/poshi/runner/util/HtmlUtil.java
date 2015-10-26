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