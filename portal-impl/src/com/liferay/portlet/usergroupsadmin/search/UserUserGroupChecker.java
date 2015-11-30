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

package com.liferay.portlet.usergroupsadmin.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Charles May
 */
public class UserUserGroupChecker extends EmptyOnClickRowChecker {

	public UserUserGroupChecker(
		RenderResponse renderResponse, UserGroup userGroup) {

		super(renderResponse);

		_userGroup = userGroup;
	}

	@Override
	public boolean isChecked(Object obj) {
		return hasUserGroupUser(obj);
	}

	@Override
	public boolean isDisabled(Object obj) {
		return hasUserGroupUser(obj);
	}

	protected boolean hasUserGroupUser(Object obj) {
		User user = (User)obj;

		try {
			return UserLocalServiceUtil.hasUserGroupUser(
				_userGroup.getUserGroupId(), user.getUserId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserUserGroupChecker.class);

	private final UserGroup _userGroup;

}