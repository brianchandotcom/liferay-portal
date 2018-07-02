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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.util.PropsValues;

/**
 * @author Iván Zaera
 */
public class DLWebDAVUtil {

	public static String escapeRawTitle(String title) {
		return StringUtil.replace(
			title, CharPool.SLASH, PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR);
	}

	public static String escapeURLTitle(String title) {
		return URLCodec.encodeURL(escapeRawTitle(title), true);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static String getRepresentableTitle(String title, int i) {
		return StringUtil.replace(
			title, PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR,
			StringPool.UNDERLINE + String.valueOf(i) + StringPool.UNDERLINE);
	}

	public static boolean isRepresentableTitle(String title) {
		return !title.contains(PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR);
	}

	public static String unescapeRawTitle(String escapedTitle) {
		return StringUtil.replace(
			escapedTitle, PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR,
			StringPool.SLASH);
	}

}