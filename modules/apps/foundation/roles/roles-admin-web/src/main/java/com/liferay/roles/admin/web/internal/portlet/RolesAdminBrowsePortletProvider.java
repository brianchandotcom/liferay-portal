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

package com.liferay.roles.admin.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.BrowsePortletProvider;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.kernel.model.Role"},
	service = BrowsePortletProvider.class
)
public class RolesAdminBrowsePortletProvider
	extends BasePortletProvider implements BrowsePortletProvider {

	@Override
	public String getPortletName() {
		return RolesAdminPortletKeys.ROLES_ADMIN;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException {

		PortletURL portletURL = super.getPortletURL(request);

		portletURL.setParameter("mvcPath", "/select_role.jsp");

		return portletURL;
	}

}