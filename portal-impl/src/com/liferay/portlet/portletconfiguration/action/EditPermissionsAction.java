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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionPropagator;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PermissionServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
 * @author Connor McKay
 */
public class EditPermissionsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			actionRequest = ActionUtil.getWrappedActionRequest(
				actionRequest, null);

			updateRolePermissions(actionRequest);

			addSuccessMessage(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(
					actionRequest, "portlet.portlet_configuration.error");
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

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			renderRequest, "resourceGroupId", themeDisplay.getScopeGroupId());

		String portletResource = ParamUtil.getString(
			renderRequest, "portletResource");
		String modelResource = ParamUtil.getString(
			renderRequest, "modelResource");
		String resourcePrimKey = ParamUtil.getString(
			renderRequest, "resourcePrimKey");

		String selResource = portletResource;

		if (Validator.isNotNull(modelResource)) {
			selResource = modelResource;
		}

		try {
			PermissionServiceUtil.checkPermission(
				groupId, selResource, resourcePrimKey);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			setForward(renderRequest, "portlet.portlet_configuration.error");
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletResource);

		if (portlet != null) {
			renderResponse.setTitle(
				ActionUtil.getTitle(portlet, renderRequest));
		}

		renderRequest = ActionUtil.getWrappedRenderRequest(renderRequest, null);

		return actionMapping.findForward(
			getForward(
				renderRequest,
				"portlet.portlet_configuration.edit_permissions"));
	}

	protected String[] getActionIds(
		ActionRequest actionRequest, long roleId, boolean includePreselected) {

		List<String> actionIds = getActionIdsList(
			actionRequest, roleId, includePreselected);

		return actionIds.toArray(new String[actionIds.size()]);
	}

	protected List<String> getActionIdsList(
		ActionRequest actionRequest, long roleId, boolean includePreselected) {

		List<String> actionIds = new ArrayList<String>();

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(roleId + "_ACTION_")) {
				int pos = name.indexOf("_ACTION_");

				String actionId = name.substring(pos + 8);

				actionIds.add(actionId);
			}
			else if (includePreselected &&
					 name.startsWith(roleId + "_PRESELECTED_")) {

				int pos = name.indexOf("_PRESELECTED_");

				String actionId = name.substring(pos + 13);

				actionIds.add(actionId);
			}
		}

		return actionIds;
	}

	protected void updateRolePermissions(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");
		String modelResource = ParamUtil.getString(
			actionRequest, "modelResource");
		long[] roleIds = StringUtil.split(
			ParamUtil.getString(
				actionRequest, "rolesSearchContainerPrimaryKeys"), 0L);

		String selResource = PortletConstants.getRootPortletId(portletResource);

		if (Validator.isNotNull(modelResource)) {
			selResource = modelResource;
		}

		long resourceGroupId = ParamUtil.getLong(
			actionRequest, "resourceGroupId", themeDisplay.getScopeGroupId());
		String resourcePrimKey = ParamUtil.getString(
			actionRequest, "resourcePrimKey");

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		if (ResourceBlockLocalServiceUtil.isSupported(selResource)) {
			for (long roleId : roleIds) {
				List<String> actionIds = getActionIdsList(
					actionRequest, roleId, true);

				roleIdsToActionIds.put(
					roleId, actionIds.toArray(new String[actionIds.size()]));
			}

			ResourceBlockServiceUtil.setIndividualScopePermissions(
				themeDisplay.getCompanyId(), resourceGroupId, selResource,
				GetterUtil.getLong(resourcePrimKey), roleIdsToActionIds);
		}
		else {
			for (long roleId : roleIds) {
				String[] actionIds = getActionIds(actionRequest, roleId, false);

				roleIdsToActionIds.put(roleId, actionIds);
			}

			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
				resourceGroupId, themeDisplay.getCompanyId(), selResource,
				resourcePrimKey, roleIdsToActionIds);
		}

		int pos = resourcePrimKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

		if (pos != -1) {
			long plid = GetterUtil.getLong(resourcePrimKey.substring(0, pos));

			Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

			if (layout != null) {
				layout.setModifiedDate(new Date());

				LayoutLocalServiceUtil.updateLayout(layout);

				CacheUtil.clearCache(layout.getCompanyId());
			}
		}

		if (PropsValues.PERMISSIONS_PROPAGATION_ENABLED) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletResource);

			PermissionPropagator permissionPropagator =
				portlet.getPermissionPropagatorInstance();

			if (permissionPropagator != null) {
				permissionPropagator.propagateRolePermissions(
					actionRequest, modelResource, resourcePrimKey, roleIds);
			}
		}
	}

}