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

package com.liferay.portlet.sitesadmin.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Edward Han
 */
public class UserGroupTeamChecker extends EmptyOnClickRowChecker {

	public UserGroupTeamChecker(RenderResponse renderResponse, Team team) {
		super(renderResponse);

		_team = team;
	}

	@Override
	public boolean isChecked(Object obj) {
		return hasTeamUserGroup(obj);
	}

	@Override
	public boolean isDisabled(Object obj) {
		return hasTeamUserGroup(obj);
	}

	protected boolean hasTeamUserGroup(Object obj) {
		UserGroup userGroup = (UserGroup)obj;

		try {
			return UserGroupLocalServiceUtil.hasTeamUserGroup(
				_team.getTeamId(), userGroup.getUserGroupId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupTeamChecker.class);

	private final Team _team;

}