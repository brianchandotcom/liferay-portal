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

package com.liferay.social.bookmarks.taglib.internal.util;

import com.liferay.portal.kernel.util.HtmlUtil;

/**
 * @author Alejandro Tardín
 */
public class SocialBookmarksTagUtil {

	public static String getClickJSCall(
		String className, long classPK, String type, String shareURL,
		String url) {

		return String.format(
			"socialBookmarks_handleItemClick('%s', %d, '%s', '%s', '%s');",
			HtmlUtil.escapeJS(className), classPK, HtmlUtil.escapeJS(type),
			HtmlUtil.escapeJS(shareURL), HtmlUtil.escapeJS(url));
	}

}