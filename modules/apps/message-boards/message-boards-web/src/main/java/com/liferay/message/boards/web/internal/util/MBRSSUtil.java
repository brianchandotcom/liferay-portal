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

package com.liferay.message.boards.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Sergio González
 */
public class MBRSSUtil {

	public static String getRSSURL(
		long plid, long categoryId, long threadId, long userId,
		ThemeDisplay themeDisplay) {

		StringBundler sb = new StringBundler(10);

		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathMain());
		sb.append("/message_boards/rss?plid=");
		sb.append(plid);

		if (categoryId > 0) {
			sb.append("&mbCategoryId=");
			sb.append(categoryId);
		}
		else {
			sb.append("&groupId=");
			sb.append(themeDisplay.getScopeGroupId());
		}

		if (threadId > 0) {
			sb.append("&threadId=");
			sb.append(threadId);
		}

		if (userId > 0) {
			sb.append("&userId=");
			sb.append(userId);
		}

		return sb.toString();
	}

}