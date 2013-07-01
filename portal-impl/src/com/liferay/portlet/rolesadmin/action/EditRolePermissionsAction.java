/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.rolesadmin.action;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.RolePermissionsException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.security.permission.comparator.ActionComparator;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Connor McKay
 */
public class EditRolePermissionsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("actions")) {
				updateActions(actionRequest, actionResponse);
			}
			else if (cmd.equals("delete_permission")) {
				deletePermission(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchRoleException ||
				e instanceof PrincipalException ||
				e instanceof RolePermissionsException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.roles_admin.error");
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
			getForward(
				renderRequest, "portlet.roles_admin.edit_role_permissions"));
	}

	protected void deletePermission(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long roleId = ParamUtil.getLong(actionRequest, "roleId");
		String name = ParamUtil.getString(actionRequest, "name");
		int scope = ParamUtil.getInteger(actionRequest, "scope");
		String primKey = ParamUtil.getString(actionRequest, "primKey");
		String actionId = ParamUtil.getString(actionRequest, "actionId");

		Role role = RoleLocalServiceUtil.getRole(roleId);

		String roleName = role.getName();

		if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
			roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
			roleName.equals(RoleConstants.ORGANIZATION_OWNER) ||
			roleName.equals(RoleConstants.OWNER) ||
			roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
			roleName.equals(RoleConstants.SITE_OWNER)) {

			throw new RolePermissionsException(roleName);
		}

		if (ResourceBlockLocalServiceUtil.isSupported(name)) {
			if (scope == ResourceConstants.SCOPE_GROUP) {
				ResourceBlockServiceUtil.removeGroupScopePermission(
					themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(),
					GetterUtil.getLong(primKey), name, roleId, actionId);
			}
			else {
				ResourceBlockServiceUtil.removeCompanyScopePermission(
					themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(),
					name, roleId, actionId);
			}
		}
		else {
			ResourcePermissionServiceUtil.removeResourcePermission(
				themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(),
				name, scope, primKey, roleId, actionId);
		}

		// Send redirect

		SessionMessages.add(actionRequest, "permissionDeleted");

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	protected void updateAction(
			Role role, long groupId, String selResource, String actionId,
			boolean selected, int scope, String[] groupIds)
		throws Exception {

		long companyId = role.getCompanyId();
		long roleId = role.getRoleId();

		if (selected) {
			if (scope == ResourceConstants.SCOPE_COMPANY) {
				ResourcePermissionServiceUtil.addResourcePermission(
					groupId, companyId, selResource, scope,
					String.valueOf(role.getCompanyId()), roleId, actionId);
			}
			else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
				ResourcePermissionServiceUtil.addResourcePermission(
					groupId, companyId, selResource,
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					roleId, actionId);
			}
			else if (scope == ResourceConstants.SCOPE_GROUP) {
				ResourcePermissionServiceUtil.removeResourcePermissions(
					groupId, companyId, selResource,
					ResourceConstants.SCOPE_GROUP, roleId, actionId);

				for (String curGroupId : groupIds) {
					ResourcePermissionServiceUtil.addResourcePermission(
						groupId, companyId, selResource,
						ResourceConstants.SCOPE_GROUP, curGroupId, roleId,
						actionId);
				}
			}
		}
		else {

			// Remove company, group template, and group permissions

			ResourcePermissionServiceUtil.removeResourcePermissions(
				groupId, companyId, selResource,
				ResourceConstants.SCOPE_COMPANY, roleId, actionId);

			ResourcePermissionServiceUtil.removeResourcePermissions(
				groupId, companyId, selResource,
				ResourceConstants.SCOPE_GROUP_TEMPLATE, roleId, actionId);

			ResourcePermissionServiceUtil.removeResourcePermissions(
				groupId, companyId, selResource, ResourceConstants.SCOPE_GROUP,
				roleId, actionId);
		}
	}

	protected void updateActions(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		Role role = RoleLocalServiceUtil.getRole(roleId);

		String roleName = role.getName();

		if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
			roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
			roleName.equals(RoleConstants.ORGANIZATION_OWNER) ||
			roleName.equals(RoleConstants.OWNER) ||
			roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
			roleName.equals(RoleConstants.SITE_OWNER)) {

			throw new RolePermissionsException(roleName);
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");
		String[] modelResources = StringUtil.split(
			ParamUtil.getString(actionRequest, "modelResources"));

		Map<String, List<String>> resourceActionsMap =
			new HashMap<String, List<String>>();

		for (String modelResource : modelResources) {
			resourceActionsMap.put(
				modelResource,
				ResourceActionsUtil.getResourceActions(null, modelResource));
		}

		if (Validator.isNotNull(portletResource)) {
			resourceActionsMap.put(
				portletResource,
				ResourceActionsUtil.getResourceActions(portletResource, null));
		}

		String[] selectedTargets = StringUtil.split(
			ParamUtil.getString(actionRequest, "selectedTargets"));

		for (Map.Entry<String, List<String>> entry :
				resourceActionsMap.entrySet()) {

			String selResource = entry.getKey();
			List<String> actions = entry.getValue();

			actions = ListUtil.sort(
				actions, new ActionComparator(themeDisplay.getLocale()));

			for (String actionId : actions) {
				String target = selResource + actionId;

				boolean selected = ArrayUtil.contains(selectedTargets, target);

				String[] groupIds = StringUtil.split(
					ParamUtil.getString(actionRequest, "groupIds" + target));

				groupIds = ArrayUtil.distinct(groupIds);

				int scope = ResourceConstants.SCOPE_COMPANY;

				if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
					(role.getType() == RoleConstants.TYPE_PROVIDER) ||
					(role.getType() == RoleConstants.TYPE_SITE)) {

					scope = ResourceConstants.SCOPE_GROUP_TEMPLATE;
				}
				else {
					if (groupIds.length > 0) {
						scope = ResourceConstants.SCOPE_GROUP;
					}
				}

				if (ResourceBlockLocalServiceUtil.isSupported(selResource)) {
					updateActions_Blocks(
						role, themeDisplay.getScopeGroupId(), selResource,
						actionId, selected, scope, groupIds);
				}
				else {
					updateAction(
						role, themeDisplay.getScopeGroupId(), selResource,
						actionId, selected, scope, groupIds);
				}
			}
		}

		// Send redirect

		SessionMessages.add(actionRequest, "permissionsUpdated");

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			redirect = redirect + "&" + Constants.CMD + "=" + Constants.VIEW;

			actionResponse.sendRedirect(redirect);
		}
	}

	protected void updateActions_Blocks(
			Role role, long scopeGroupId, String selResource, String actionId,
			boolean selected, int scope, String[] groupIds)
		throws Exception {

		long companyId = role.getCompanyId();
		long roleId = role.getRoleId();

		if (selected) {
			if (scope == ResourceConstants.SCOPE_GROUP) {
				ResourceBlockServiceUtil.removeAllGroupScopePermissions(
					scopeGroupId, companyId, selResource, roleId, actionId);
				ResourceBlockServiceUtil.removeCompanyScopePermission(
					scopeGroupId, companyId, selResource, roleId, actionId);

				for (String groupId : groupIds) {
					ResourceBlockServiceUtil.addGroupScopePermission(
						scopeGroupId, companyId, GetterUtil.getLong(groupId),
						selResource, roleId, actionId);
				}
			}
			else {
				ResourceBlockServiceUtil.removeAllGroupScopePermissions(
					scopeGroupId, companyId, selResource, roleId, actionId);
				ResourceBlockServiceUtil.addCompanyScopePermission(
					scopeGroupId, companyId, selResource, roleId, actionId);
			}
		}
		else {
			ResourceBlockServiceUtil.removeAllGroupScopePermissions(
				scopeGroupId, companyId, selResource, roleId, actionId);
			ResourceBlockServiceUtil.removeCompanyScopePermission(
				scopeGroupId, companyId, selResource, roleId, actionId);
		}
	}

}