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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicyUtil;

import javax.portlet.RenderResponse;

/**
 * @author Charles May
 * @author Pei-Jung Lan
 */
public class UnsetUserUserGroupChecker extends EmptyOnClickRowChecker {

	public UnsetUserUserGroupChecker(
		RenderResponse renderResponse, UserGroup userGroup) {

		super(renderResponse);

		_userGroup = userGroup;
	}

	@Override
	public boolean isDisabled(Object obj) {
		User user = (User)obj;

		try {
			if (UserGroupMembershipPolicyUtil.isMembershipRequired(
					user.getUserId(), _userGroup.getUserGroupId())) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return super.isDisabled(obj);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnsetUserUserGroupChecker.class);

	private final UserGroup _userGroup;

}