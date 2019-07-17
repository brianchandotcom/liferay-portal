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

package com.liferay.site.memberships.web.internal.portlet.action;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

/**
 * @author Eudaldo Alonso
 */
public class ActionUtil {

	public static List<Organization> getOrganizations(ResourceRequest request)
		throws Exception {

		long[] organizationIds = ParamUtil.getLongValues(request, "rowIds");

		List<Organization> organizations = new ArrayList<>();

		for (long organizationId : organizationIds) {
			organizations.add(
				OrganizationLocalServiceUtil.getOrganization(organizationId));
		}

		return organizations;
	}

	public static List<User> getUsers(ResourceRequest request)
		throws Exception {

		long[] userIds = ParamUtil.getLongValues(request, "rowIds");

		List<User> users = new ArrayList<>();

		for (long userId : userIds) {
			users.add(UserLocalServiceUtil.getUser(userId));
		}

		return users;
	}

}