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

package com.liferay.users.admin.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.users.admin.web.constants.UsersAdminPortletKeys;

import java.io.PrintWriter;

import java.util.LinkedHashMap;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/get_users_count"
	},
	service = MVCResourceCommand.class
)
public class GetUsersCountMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			PrintWriter printWriter = resourceResponse.getWriter();

			printWriter.write(getText(resourceRequest, resourceResponse));

			return true;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected int getOrganizationUsersCount(
			long companyId, long[] organizationIds, int status)
		throws Exception {

		int count = 0;

		for (long organizationId : organizationIds) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<>();

			params.put("usersOrgs", organizationId);

			count+= _userLocalService.searchCount(
				companyId, null, status, params);
		}

		return count;
	}

	protected String getText(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getOriginalServletRequest(
			PortalUtil.getHttpServletRequest(resourceRequest));

		long companyId = PortalUtil.getCompanyId(request);

		String className = ParamUtil.getString(request, "className");
		long[] ids = StringUtil.split(ParamUtil.getString(request, "ids"), 0L);
		int status = ParamUtil.getInteger(request, "status");

		int count = 0;

		if (className.equals(Organization.class.getName())) {
			count = getOrganizationUsersCount(companyId, ids, status);
		}
		else if (className.equals(UserGroup.class.getName())) {
			count = getUserGroupUsersCount(companyId, ids, status);
		}

		return String.valueOf(count);
	}

	protected int getUserGroupUsersCount(
			long companyId, long[] userGroupIds, int status)
		throws Exception {

		int count = 0;

		for (long userGroupId : userGroupIds) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<>();

			params.put("usersUserGroups", userGroupId);

			count+= _userLocalService.searchCount(
				companyId, null, status, params);
		}

		return count;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile UserLocalService _userLocalService;

}