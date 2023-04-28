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

import java.util.ArrayList;
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

			return _fillGroupsPage(
				_filterGroups(groups), groups.size(), classNameIds, companyId,
				keywords, groupParams, start, end);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

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

	private static List<Group> _fillGroupsPage(
			List<Group> groups, int pageSize, long[] classNameIds,
			long companyId, String keywords,
			LinkedHashMap<String, Object> groupParams, int start, int end)
		throws PortalException {

		int delta = end - start;

		int groupsCount = getGroupsCount(companyId, classNameIds, keywords);

		int pageCount =
			(groupsCount / delta) + (((groupsCount % delta) == 0) ? 0 : 1);

		int pageIndex = (int)Math.ceil((double)start / delta);

		while ((groups.size() < pageSize) && (pageIndex < pageCount)) {
			pageIndex++;

			start += delta;
			end += delta;

			List<Group> remainigFilteredGroups = _filterGroups(
				GroupLocalServiceUtil.search(
					companyId, classNameIds, keywords, groupParams, start, end,
					null));

			int difference = pageSize - groups.size();

			groups.addAll(
				ListUtil.subList(remainigFilteredGroups, 0, difference));
		}

		return groups;
	}

	private static List<Group> _filterGroups(List<Group> groups)
		throws PortalException {

		List<Group> filteredGroups = new ArrayList<>();

		PermissionChecker permissionChecker =
			GuestOrUserUtil.getPermissionChecker();

		for (Group group : groups) {
			if (group.isCompany() ||
				permissionChecker.isGroupAdmin(group.getGroupId()) ||
				GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.VIEW)) {

				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupItemSelectorGroupUtil.class);

}