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

package com.liferay.portlet.layoutsadmin.context;

import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mate Thurzo
 */
public class GroupDisplayContextHelper {

	public GroupDisplayContextHelper(HttpServletRequest request) {
		_request = request;
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		if (getStagingGroup() != null) {
			_group = getStagingGroup();
		}
		else {
			_group = getLiveGroup();
		}

		return _group;
	}

	public Long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		Group group = getGroup();

		if (group != null) {
			_groupId = group.getGroupId();
		}

		return _groupId;
	}

	public UnicodeProperties getGroupTypeSettings() {
		if (_groupTypeSettings != null) {
			return _groupTypeSettings;
		}

		Group group = getGroup();

		if (group != null) {
			_groupTypeSettings = group.getTypeSettingsProperties();
		}
		else {
			_groupTypeSettings = new UnicodeProperties();
		}

		return _groupTypeSettings;
	}

	public Group getLiveGroup() {
		if (_liveGroup != null) {
			return _liveGroup;
		}

		Group group = getSelGroup();

		if (group == null) {
			return null;
		}

		_liveGroup = StagingUtil.getLiveGroup(group.getGroupId());

		return _liveGroup;
	}

	public Long getLiveGroupId() {
		if (_liveGroupId != null) {
			return _liveGroupId;
		}

		Group liveGroup = getLiveGroup();

		if (liveGroup != null) {
			_liveGroupId = liveGroup.getGroupId();
		}

		return _liveGroupId;
	}

	public Group getSelGroup() {
		if (_selGroup != null) {
			return _selGroup;
		}

		long groupId = ParamUtil.getLong(_request, "groupId");

		_selGroup = GroupLocalServiceUtil.fetchGroup(groupId);

		if (_selGroup == null) {
			_selGroup = (Group)_request.getAttribute(WebKeys.GROUP);
		}

		if (_selGroup == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_selGroup = themeDisplay.getScopeGroup();
		}

		return _selGroup;
	}

	public Group getStagingGroup() {
		if (_stagingGroup != null) {
			return _stagingGroup;
		}

		Group group = getSelGroup();

		if (group == null) {
			return null;
		}

		_stagingGroup = StagingUtil.getStagingGroup(group.getGroupId());

		return _stagingGroup;
	}

	public Long getStagingGroupId() {
		if (_stagingGroupId != null) {
			return _stagingGroupId;
		}

		Group stagingGroup = getStagingGroup();

		if (stagingGroup != null) {
			_stagingGroupId = stagingGroup.getGroupId();
		}

		return _stagingGroupId;
	}

	private Group _group;
	private Long _groupId;
	private UnicodeProperties _groupTypeSettings;
	private Group _liveGroup;
	private Long _liveGroupId;
	private final HttpServletRequest _request;
	private Group _selGroup;
	private Group _stagingGroup;
	private Long _stagingGroupId;

}