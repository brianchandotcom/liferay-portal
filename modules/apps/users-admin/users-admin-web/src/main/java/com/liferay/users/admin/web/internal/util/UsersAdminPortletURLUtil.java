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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;

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
			PortletResponse portletResponse)
		throws PortalException {

		return createParentOrganizationViewTreeURL(
			OrganizationLocalServiceUtil.fetchOrganization(organizationId),
			portletRequest, portletResponse);
	}

	public static String createParentOrganizationViewTreeURL(
			Organization organization, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws PortalException {

		if ((organization != null) && !organization.isRoot()) {
			long parentOrganizationId = organization.getParentOrganizationId();

			if (OrganizationPermissionUtil.contains(
					PermissionThreadLocal.getPermissionChecker(),
					parentOrganizationId, ActionKeys.VIEW)) {

				return _createOrganizationViewTreeURL(
					parentOrganizationId, portletResponse);
			}
		}

		return _createOrganizationViewTreeURL(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			portletResponse);
	}

	private static String _createOrganizationViewTreeURL(
		long organizationId, PortletResponse portletResponse) {

		RenderResponse renderResponse = (RenderResponse)portletResponse;

		RenderURL renderURL = renderResponse.createRenderURL();

		renderURL.setParameter("mvcRenderCommandName", "/users_admin/view");
		renderURL.setParameter("toolbarItem", "view-all-organizations");

		if (organizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			renderURL.setParameter(
				"usersListView", UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS);
		}
		else {
			renderURL.setParameter(
				"organizationId", String.valueOf(organizationId));
			renderURL.setParameter(
				"usersListView", UserConstants.LIST_VIEW_TREE);
		}

		return String.valueOf(renderURL);
	}

}