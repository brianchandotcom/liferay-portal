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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class UserSiteMembershipChecker extends EmptyOnClickRowChecker {

	public UserSiteMembershipChecker(
		RenderResponse renderResponse, Group group) {

		super(renderResponse);

		_group = group;
	}

	@Override
	public boolean isChecked(Object object) {
		User user = null;

		if (object instanceof User) {
			user = (User)object;
		}
		else if (object instanceof Object[]) {
			user = (User)((Object[])object)[0];
		}
		else {
			throw new IllegalArgumentException(object + " is not a user");
		}

		try {
			return UserLocalServiceUtil.hasGroupUser(
				_group.getGroupId(), user.getUserId());
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return false;
		}
	}

	@Override
	public boolean isDisabled(Object object) {
		User user = (User)object;

		try {
			if (isChecked(user) ||
				!SiteMembershipPolicyUtil.isMembershipAllowed(
					user.getUserId(), _group.getGroupId())) {

				return true;
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return super.isDisabled(object);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserSiteMembershipChecker.class);

	private final Group _group;

}