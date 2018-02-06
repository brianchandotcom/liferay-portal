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
		HttpServletRequest request, LiferayPortletRequest liferayPortletRequest,
		User user, User selUser) {

		UserActionContributor[] userActionContributors =
			(UserActionContributor[])request.getAttribute(
				UsersAdminWebKeys.USER_ACTION_CONTRIBUTORS);

		if (userActionContributors == null) {
			_filteredUserActionContributors = new UserActionContributor[0];
		}
		else {
			_filteredUserActionContributors = ArrayUtil.filter(
				userActionContributors,
				userActionContributor -> userActionContributor.isShow(
					liferayPortletRequest, user, selUser));
		}
	}

	public UserActionContributor[] getFilteredUserActionContributors() {
		return _filteredUserActionContributors;
	}

	private final UserActionContributor[] _filteredUserActionContributors;

}