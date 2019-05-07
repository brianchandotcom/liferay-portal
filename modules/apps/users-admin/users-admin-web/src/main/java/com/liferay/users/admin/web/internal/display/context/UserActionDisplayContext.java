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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.users.admin.user.action.contributor.UserActionContributor;
import com.liferay.users.admin.web.internal.constants.UsersAdminWebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class UserActionDisplayContext {

	public UserActionDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest, User user, User selUser) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_user = user;
		_selUser = selUser;
	}

	public UserActionContributor[] getFilteredUserActionContributors() {
		UserActionContributor[] userActionContributors =
			(UserActionContributor[])_httpServletRequest.getAttribute(
				UsersAdminWebKeys.USER_ACTION_CONTRIBUTORS);

		if (userActionContributors == null) {
			return new UserActionContributor[0];
		}

		return ArrayUtil.filter(
			userActionContributors,
			userActionContributor -> userActionContributor.isShow(
				_liferayPortletRequest, _user, _selUser));
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final User _selUser;
	private final User _user;

}