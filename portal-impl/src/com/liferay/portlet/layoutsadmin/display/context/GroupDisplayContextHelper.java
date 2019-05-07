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

package com.liferay.portlet.layoutsadmin.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Máté Thurzó
 */
public class GroupDisplayContextHelper {

	public GroupDisplayContextHelper(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		if (!PropsValues.STAGING_LIVE_GROUP_LOCKING_ENABLED &&
			(getSelGroup() != null)) {

			_group = getSelGroup();

			return _group;
		}

		if (getStagingGroup() != null) {
			_group = getStagingGroup();
		}
		else {
			_group = getLiveGroup();
		}

		if (_group == null) {
			_group = getSelGroup();
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

		if (_liveGroup == null) {
			_liveGroup = group;
		}

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

		long groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

		_selGroup = GroupLocalServiceUtil.fetchGroup(groupId);

		if (_selGroup == null) {
			_selGroup = (Group)_httpServletRequest.getAttribute(WebKeys.GROUP);
		}

		if (_selGroup == null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
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
	private final HttpServletRequest _httpServletRequest;
	private Group _liveGroup;
	private Long _liveGroupId;
	private Group _selGroup;
	private Group _stagingGroup;
	private Long _stagingGroupId;

}