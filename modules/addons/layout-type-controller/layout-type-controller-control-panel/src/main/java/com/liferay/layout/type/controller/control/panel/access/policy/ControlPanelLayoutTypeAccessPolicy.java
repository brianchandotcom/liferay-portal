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

package com.liferay.layout.type.controller.control.panel.access.policy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypeAccessPolicy;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.DefaultLayoutTypeAccessPolicyImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"layout.type=" + LayoutConstants.TYPE_CONTROL_PANEL},
	service = LayoutTypeAccessPolicy.class
)
public class ControlPanelLayoutTypeAccessPolicy
	extends DefaultLayoutTypeAccessPolicyImpl {

	@Override
	public void checkAccessAllowedToPortlet(
			HttpServletRequest request, Layout layout, Portlet portlet)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PortletPermissionUtil.hasControlPanelAccessPermission(
				permissionChecker, themeDisplay.getScopeGroupId(), portlet)) {

			return;
		}

		if (isAccessGrantedByRuntimePortlet(request)) {
			return;
		}

		if (isAccessGrantedByPortletAuthenticationToken(
				request, layout, portlet)) {

			return;
		}

		throw new PrincipalException(
			"User does not have permission to access Control Panel portlet " +
				portlet.getPortletId());
	}

	@Override
	public boolean isAddLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isCustomizeLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isDeleteLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isUpdateLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

	@Override
	public boolean isViewLayoutAllowed(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return false;
	}

}