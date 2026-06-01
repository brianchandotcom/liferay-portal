/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.constants.DLFriendlyURLConstants;

/**
 * @author Dante Wang
 */
public class FriendlyURLUtil {

	public static Group fetchFriendlyURLGroup(
		long companyId, String groupFriendlyURL) {

		if (groupFriendlyURL == null) {
			return null;
		}

		Group group = GroupLocalServiceUtil.fetchFriendlyURLGroup(
			companyId, groupFriendlyURL);

		if (group != null) {
			return group;
		}

		User user = UserLocalServiceUtil.fetchUserByScreenName(
			companyId, groupFriendlyURL.substring(1));

		if (user != null) {
			return user.getGroup();
		}

		return null;
	}

	public static String parseGroupFriendlyURL(String path) {
		if ((path == null) || path.equals(StringPool.SLASH)) {
			return null;
		}

		String groupFriendlyURL = path;

		int index = path.indexOf(CharPool.SLASH, 1);

		if (index != -1) {
			String pathSuffix = path.substring(index);

			if (pathSuffix.startsWith(
					DLFriendlyURLConstants.PATH_PREFIX_DOCUMENT)) {

				String documentRelativePath = pathSuffix.substring(
					DLFriendlyURLConstants.PATH_PREFIX_DOCUMENT.length() - 1);

				index = documentRelativePath.indexOf(CharPool.SLASH, 1);

				if (index == -1) {
					groupFriendlyURL = documentRelativePath;
				}
				else {
					groupFriendlyURL = documentRelativePath.substring(0, index);
				}
			}
			else {
				groupFriendlyURL = path.substring(0, index);
			}
		}

		return groupFriendlyURL;
	}

}