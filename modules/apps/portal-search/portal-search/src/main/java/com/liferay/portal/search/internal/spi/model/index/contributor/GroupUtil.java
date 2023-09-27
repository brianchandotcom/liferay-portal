/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;

/**
 * @author Michael C. Han
 */
public class GroupUtil {

	public static Group fetchSiteGroup(
		GroupLocalService groupLocalService, long groupId) {

		Group group = groupLocalService.fetchGroup(groupId);

		if ((group != null) && group.isLayout()) {
			group = group.getParentGroup();
		}

		return group;
	}

	public static long getSiteGroupId(
		GroupLocalService groupLocalService, long groupId) {

		Group group = fetchSiteGroup(groupLocalService, groupId);

		if (group == null) {
			return groupId;
		}

		return group.getGroupId();
	}

}