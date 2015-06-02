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

package com.liferay.portlet.rolesadmin;

import com.liferay.portal.DuplicateRoleException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.RequiredRoleException;
import com.liferay.portal.RoleNameException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Drew Brokke
 */

public class RolesAdminPortlet extends MVCPortlet {

	public void deleteRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		RoleServiceUtil.deleteRole(roleId);
	}

	public Role updateRole(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		String name = ParamUtil.getString(actionRequest, "name");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		int type = ParamUtil.getInteger(
			actionRequest, "type", RoleConstants.TYPE_REGULAR);
		String subtype = ParamUtil.getString(actionRequest, "subtype");
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Role.class.getName(), actionRequest);

		if (roleId <= 0) {

			// Add role

			return RoleServiceUtil.addRole(
				null, 0, name, titleMap, descriptionMap, type, subtype,
				serviceContext);
		}
		else {

			// Update role

			return RoleServiceUtil.updateRole(
				roleId, name, titleMap, descriptionMap, subtype,
				serviceContext);
		}
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
			renderRequest, PrincipalException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchRoleException.class.getName())) {

			include(
				"/html/portlet/roles_admin/error.jsp", renderRequest,
				renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest,
					RequiredRoleException.class.getName())) {

			include(
				"/html/portlet/roles_admin/view.jsp", renderRequest,
				renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DuplicateRoleException ||
			cause instanceof NoSuchRoleException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredRoleException ||
			cause instanceof RoleNameException) {

			return true;
		}

		return false;
	}

}