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

/**
 * @author Brian Wing Shun Chan
 * @author Drew Brokke
 */

public class RolesAdminPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			Role role = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				role = updateRole(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRole(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (role != null) {
				redirect = HttpUtil.setParameter(
					redirect, actionResponse.getNamespace() + "roleId",
					role.getRoleId());
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.roles_admin.error");
			}
			else if (e instanceof DuplicateRoleException ||
					 e instanceof NoSuchRoleException ||
					 e instanceof RequiredRoleException ||
					 e instanceof RoleNameException) {

				SessionErrors.add(actionRequest, e.getClass());

				if (cmd.equals(Constants.DELETE)) {
					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getRole(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchRoleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.roles_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.roles_admin.edit_role"));
	}

	public void deleteRole(ActionRequest actionRequest) throws Exception {
		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		RoleServiceUtil.deleteRole(roleId);
	}

	public Role updateRole(ActionRequest actionRequest) throws Exception {
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

}