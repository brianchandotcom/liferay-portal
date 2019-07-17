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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(immediate = true, service = RecentGroupManager.class)
public class RecentGroupManager {

	public void addRecentGroup(
		HttpServletRequest httpServletRequest, Group group) {

		addRecentGroup(httpServletRequest, group.getGroupId());
	}

	public void addRecentGroup(
		HttpServletRequest httpServletRequest, long groupId) {

		long liveGroupId = _getLiveGroupId(groupId);

		if (liveGroupId <= 0) {
			return;
		}

		Group liveGroup = _groupLocalService.fetchGroup(liveGroupId);

		if (liveGroup.isLayoutPrototype() || liveGroup.isLayoutSetPrototype()) {
			return;
		}

		String value = _getRecentGroupsValue(httpServletRequest);

		List<Long> groupIds = ListUtil.fromArray(
			ArrayUtil.toLongArray(StringUtil.split(value, 0L)));

		groupIds.remove(liveGroupId);

		groupIds.add(0, liveGroupId);

		_setRecentGroupsValue(httpServletRequest, StringUtil.merge(groupIds));
	}

	public List<Group> getRecentGroups(HttpServletRequest httpServletRequest) {
		String value = _getRecentGroupsValue(httpServletRequest);

		try {
			PortletRequest portletRequest =
				(PortletRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			return getRecentGroups(value, portletRequest);
		}
		catch (Exception e) {
			_log.error("Unable to get recent groups", e);
		}

		return Collections.emptyList();
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getRecentGroups(String, PortletRequest)}
	 */
	@Deprecated
	protected List<Group> getRecentGroups(String value) {
		long[] groupIds = StringUtil.split(value, 0L);

		if (ArrayUtil.isEmpty(groupIds)) {
			return Collections.emptyList();
		}

		List<Group> groups = new ArrayList<>(groupIds.length);

		for (long groupId : groupIds) {
			Group group = _groupLocalService.fetchGroup(groupId);

			if (!_groupLocalService.isLiveGroupActive(group)) {
				continue;
			}

			groups.add(group);
		}

		return groups;
	}

	protected List<Group> getRecentGroups(
			String value, PortletRequest portletRequest)
		throws Exception {

		long[] groupIds = StringUtil.split(value, 0L);

		if (ArrayUtil.isEmpty(groupIds)) {
			return Collections.emptyList();
		}

		List<Group> groups = new ArrayList<>(groupIds.length);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(
				_portal.getUser(portletRequest));

		for (long groupId : groupIds) {
			Group group = _groupLocalService.fetchGroup(groupId);

			if (!_groupLocalService.isLiveGroupActive(group)) {
				continue;
			}

			if (!group.isCompany()) {
				Layout layout = _layoutLocalService.fetchFirstLayout(
					group.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if (layout == null) {
					layout = _layoutLocalService.fetchFirstLayout(
						group.getGroupId(), true,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

					if ((layout == null) ||
						!LayoutPermissionUtil.contains(
							permissionChecker, layout, true, ActionKeys.VIEW)) {

						continue;
					}
				}
			}

			String groupURL = _groupURLProvider.getGroupURL(
				group, portletRequest);

			if (Validator.isNull(groupURL)) {
				continue;
			}

			groups.add(group);
		}

		return groups;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private long _getLiveGroupId(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return 0;
		}

		if (!group.isStagedRemotely() && group.isStagingGroup()) {
			return group.getLiveGroupId();
		}

		return groupId;
	}

	private String _getRecentGroupsValue(
		HttpServletRequest httpServletRequest) {

		return SessionClicks.get(httpServletRequest, _KEY_RECENT_GROUPS, null);
	}

	private void _setRecentGroupsValue(
		HttpServletRequest httpServletRequest, String value) {

		SessionClicks.put(httpServletRequest, _KEY_RECENT_GROUPS, value);
	}

	private static final String _KEY_RECENT_GROUPS =
		"com.liferay.site.util_recentGroups";

	private static final Log _log = LogFactoryUtil.getLog(
		RecentGroupManager.class);

	private GroupLocalService _groupLocalService;

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}