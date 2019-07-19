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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Drew Brokke
 */
public class GroupUtil {

	public static long[] getGroupIds(
		GroupLocalService groupLocalService, User user, String scope) {

		try {
			if (scope.equals(UADConstants.SCOPE_PERSONAL_SITE)) {
				Group userGroup = groupLocalService.getUserGroup(
					user.getCompanyId(), user.getUserId());

				return new long[] {userGroup.getGroupId()};
			}

			if (scope.equals(UADConstants.SCOPE_REGULAR_SITES)) {
				List<Group> allGroups = new ArrayList<>();

				List<Group> liveGroups = groupLocalService.getGroups(
					user.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true);

				allGroups.addAll(liveGroups);

				for (Group group : liveGroups) {
					Group stagingGroup = group.getStagingGroup();

					if (stagingGroup != null) {
						allGroups.add(stagingGroup);
					}
				}

				return ListUtil.toLongArray(allGroups, Group.GROUP_ID_ACCESSOR);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(GroupUtil.class);

}