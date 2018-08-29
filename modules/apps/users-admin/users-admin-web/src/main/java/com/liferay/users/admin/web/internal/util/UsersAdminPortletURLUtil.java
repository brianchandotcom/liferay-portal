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

package com.liferay.users.admin.web.internal.util;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.OrganizationServiceUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

/**
 * @author Samuel Trong Tran
 */
public class UsersAdminPortletURLUtil {

	public static String createParentOrganizationViewTreeURL(
		long organizationId, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		long parentOrganizationId =
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

		try {
			Organization organization =
				OrganizationServiceUtil.fetchOrganization(organizationId);

			if (!organization.isRoot()) {
				Organization parentOrganization =
					organization.getParentOrganization();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)portletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				PermissionChecker permissionChecker =
					themeDisplay.getPermissionChecker();

				if (OrganizationPermissionUtil.contains(
						permissionChecker, parentOrganization,
						ActionKeys.VIEW)) {

					parentOrganizationId =
						parentOrganization.getOrganizationId();
				}
			}
		}
		catch (Exception e) {
		}

		return _createOrganizationViewTreeURL(
			parentOrganizationId, portletResponse);
	}

	private static String _createOrganizationViewTreeURL(
		long organizationId, PortletResponse portletResponse) {

		RenderResponse renderResponse = (RenderResponse)portletResponse;

		RenderURL renderURL = renderResponse.createRenderURL();

		renderURL.setParameter("mvcRenderCommandName", "/users_admin/view");
		renderURL.setParameter("toolbarItem", "view-all-organizations");
		renderURL.setParameter(
			"organizationId", String.valueOf(organizationId));

		String usersListView = UserConstants.LIST_VIEW_TREE;

		if (organizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			usersListView = UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS;
		}

		renderURL.setParameter("usersListView", usersListView);

		return String.valueOf(renderURL);
	}

}