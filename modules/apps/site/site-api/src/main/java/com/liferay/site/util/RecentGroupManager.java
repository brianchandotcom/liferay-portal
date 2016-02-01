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

package com.liferay.site.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.util.SessionClicks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 *
 * @author Julio Camarero
 */
@Component(immediate = true, service = RecentGroupManager.class)
public class RecentGroupManager {

	public void addRecentGroup(HttpServletRequest request, Group group) {
		addRecentGroup(request, group.getGroupId());
	}

	public void addRecentGroup(HttpServletRequest request, long groupId) {
		String value = _getRecentGroupsValue(request);

		List<Long> groupIds = ListUtil.fromArray(
			ArrayUtil.toLongArray(StringUtil.split(value, 0L)));

		groupIds.remove(groupId);

		groupIds.add(0, groupId);

		_setRecentGroupsValue(request, StringUtil.merge(groupIds));
	}

	public List<Group> getRecentGroups(HttpServletRequest request) {
		String value = _getRecentGroupsValue(request);

		return _getRecentGroups(value);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private List<Group> _getRecentGroups(String value) {
		long[] groupIds = StringUtil.split(value, 0L);

		if (ArrayUtil.isEmpty(groupIds)) {
			return Collections.emptyList();
		}

		List<Group> groups = new ArrayList<>(groupIds.length);

		for (long groupId : groupIds) {
			Group group = _groupLocalService.fetchGroup(groupId);

			if (!group.isActive()) {
				continue;
			}

			groups.add(group);
		}

		return groups;
	}

	private String _getRecentGroupsValue(HttpServletRequest request) {
		return SessionClicks.get(request, _KEY_RECENT_GROUPS, null);
	}

	private void _setRecentGroupsValue(
		HttpServletRequest request, String value) {

		SessionClicks.put(request, _KEY_RECENT_GROUPS, value);
	}

	private static final String _KEY_RECENT_GROUPS =
		"com.liferay.site.admin.web.util_recentGroups";

	private GroupLocalService _groupLocalService;

}