/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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