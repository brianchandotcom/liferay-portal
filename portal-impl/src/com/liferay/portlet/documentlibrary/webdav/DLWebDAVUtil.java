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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Iván Zaera
 */
public class DLWebDAVUtil {

	private static final String _FORBIDDEN_CHAR = StringPool.CARET;

	public static String escapeRawTitle(String title) {
		return StringUtil.replace(title, StringPool.SLASH, _FORBIDDEN_CHAR);
	}

	public static String escapeURLTitle(String title) {
		return HttpUtil.encodeURL(escapeRawTitle(title), true);
	}

	public static boolean isRepresentableTitle(String title) {
		return !title.contains(_FORBIDDEN_CHAR);
	}

	public static String unescapeRawTitle(String escapedTitle) {
		return StringUtil.replace(
			escapedTitle, _FORBIDDEN_CHAR, StringPool.SLASH);
	}

}