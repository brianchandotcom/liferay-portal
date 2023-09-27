/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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