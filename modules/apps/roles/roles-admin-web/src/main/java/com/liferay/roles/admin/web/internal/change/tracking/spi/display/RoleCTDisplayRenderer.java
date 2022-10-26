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

package com.liferay.roles.admin.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.UserPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = CTDisplayRenderer.class)
public class RoleCTDisplayRenderer extends BaseCTDisplayRenderer<Role> {

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, Role role) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_userPermission.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getUserId(),
				ActionKeys.UPDATE)) {

			return null;
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, RolesAdminPortletKeys.ROLES_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_role.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"roleId", role.getRoleId()
		).buildString();
	}

	@Override
	public Class<Role> getModelClass() {
		return Role.class;
	}

	@Override
	public String getTitle(Locale locale, Role role) {
		return role.getTitle(locale);
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Role> displayBuilder) {
		Role role = displayBuilder.getModel();

		displayBuilder.display(
			"title", role.getTitle(displayBuilder.getLocale())
		).display(
			"name", role.getName()
		).display(
			"type", role.getTypeLabel()
		).display(
			"description", role.getDescription(displayBuilder.getLocale())
		);
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserPermission _userPermission;

}