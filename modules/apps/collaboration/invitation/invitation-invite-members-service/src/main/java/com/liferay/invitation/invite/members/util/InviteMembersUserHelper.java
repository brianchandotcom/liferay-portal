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

package com.liferay.invitation.invite.members.util;

import com.liferay.portal.dao.orm.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = InviteMembersUserHelper.class)
public class InviteMembersUserHelper {

	public List<User> getAvailableUsers(
			long companyId, long groupId, String keywords, int start, int end)
		throws Exception {

		LinkedHashMap usersParams = new LinkedHashMap();

		usersParams.put(
			"usersInvited",
			new CustomSQLParam(
				CustomSQLUtil.get(
					getClass(),
					"com.liferay.portal.service.persistence.UserFinder." +
						"filterByUsersGroupsGroupId"),
				groupId));

		return _userLocalService.search(
			companyId, keywords, WorkflowConstants.STATUS_APPROVED, usersParams,
			start, end, new UserFirstNameComparator(true));
	}

	public int getAvailableUsersCount(
			long companyId, long groupId, String keywords)
		throws Exception {

		LinkedHashMap usersParams = new LinkedHashMap();

		usersParams.put(
			"usersInvited",
			new CustomSQLParam(
				CustomSQLUtil.get(
					getClass(),
					"com.liferay.portal.service.persistence.UserFinder." +
						"filterByUsersGroupsGroupId"),
				groupId));

		return _userLocalService.searchCount(
			companyId, keywords, WorkflowConstants.STATUS_APPROVED,
			usersParams);
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private UserLocalService _userLocalService;

}