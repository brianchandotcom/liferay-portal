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

package com.liferay.portal.search.internal.contributor.document;

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