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

package com.liferay.item.selector.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author István András Dézsi
 */
public class GroupItemSelectorGroupUtil {

	public static List<Group> getGroups(
			long companyId, long[] classNameIds, String keywords,
			LinkedHashMap<String, Object> groupParams, int start, int end)
		throws PortalException {

		try {
			List<Group> groups = GroupLocalServiceUtil.search(
				companyId, classNameIds, keywords, groupParams, start, end,
				null);

			return ListUtil.filter(
				groups,
				(startIndex, endIndex) -> GroupLocalServiceUtil.search(
					companyId, classNameIds, keywords, groupParams, startIndex,
					endIndex, null),
				() -> getGroupsCount(companyId, classNameIds, keywords),
				group -> _hasViewPermission(group), start, end);
		}
		catch (Exception exception) {
			_log.error(exception);

			return Collections.emptyList();
		}
	}

	public static int getGroupsCount(
		long companyId, long[] classNameIds, String keywords) {

		return GroupServiceUtil.searchCount(
			companyId, classNameIds, keywords,
			LinkedHashMapBuilder.<String, Object>put(
				"actionId", ActionKeys.VIEW
			).put(
				"site", Boolean.TRUE
			).build());
	}

	private static boolean _hasViewPermission(Group group) {
		try {
			PermissionChecker permissionChecker =
				GuestOrUserUtil.getPermissionChecker();

			if (group.isCompany() ||
				permissionChecker.isGroupAdmin(group.getGroupId()) ||
				GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.VIEW)) {

				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupItemSelectorGroupUtil.class);

}