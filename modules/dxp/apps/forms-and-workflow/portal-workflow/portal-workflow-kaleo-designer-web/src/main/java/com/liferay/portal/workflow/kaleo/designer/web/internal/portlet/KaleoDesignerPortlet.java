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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.KaleoDesignerDisplayContext;
import com.liferay.portal.workflow.kaleo.exception.DuplicateKaleoDefinitionNameException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.autopropagated-parameters=availableFields",
		"com.liferay.portlet.autopropagated-parameters=availablePropertyModels",
		"com.liferay.portlet.autopropagated-parameters=kaleoProcessId",
		"com.liferay.portlet.autopropagated-parameters=openerWindowName",
		"com.liferay.portlet.autopropagated-parameters=portletResourceNamespace",
		"com.liferay.portlet.autopropagated-parameters=propertiesSaveCallback",
		"com.liferay.portlet.autopropagated-parameters=refreshOpenerOnClose",
		"com.liferay.portlet.autopropagated-parameters=saveCallback",
		"com.liferay.portlet.autopropagated-parameters=uiScope",
		"com.liferay.portlet.css-class-wrapper=kaleo-designer-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/designer/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Kaleo Designer Web",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/designer/view.jsp",
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,power-user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class KaleoDesignerPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (!SessionErrors.contains(
				renderRequest, DuplicateKaleoDefinitionNameException.class)) {

			try {
				setKaleoDefinitionVersionRenderRequestAttribute(
					renderRequest, renderResponse);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("kaleoDefinitionVersions")) {
				serveKaleoDefinitionVersions(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("roles")) {
				serveRoles(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("users")) {
				serveUsers(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected Integer[] getRoleTypesObj(int type) {
		if ((type == RoleConstants.TYPE_ORGANIZATION) ||
			(type == RoleConstants.TYPE_REGULAR) ||
			(type == RoleConstants.TYPE_SITE)) {

			return new Integer[] {type};
		}
		else {
			return new Integer[0];
		}
	}

	protected void serveKaleoDefinitionVersions(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		String name = ParamUtil.getString(resourceRequest, "name");
		String draftVersion = ParamUtil.getString(
			resourceRequest, "draftVersion");
		String position = ParamUtil.getString(resourceRequest, "position");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(name)) {
			KaleoDefinitionVersion kaleoDefinitionVersion = null;

			if (position.equals("latest")) {
				kaleoDefinitionVersion =
					_kaleoDefinitionVersionLocalService.
						getLatestKaleoDefinitionVersion(companyId, name);
			}
			else {
				KaleoDefinitionVersion[] kaleoDefinitionVersions =
					_kaleoDefinitionVersionLocalService.
						getKaleoDefinitionVersionsPrevAndNext(
							companyId, name, draftVersion);

				if (position.equals("prev")) {
					kaleoDefinitionVersion = kaleoDefinitionVersions[0];
				}
				else if (position.equals("next")) {
					kaleoDefinitionVersion = kaleoDefinitionVersions[2];
				}

				if (kaleoDefinitionVersion == null) {
					kaleoDefinitionVersion = kaleoDefinitionVersions[1];
				}
			}

			jsonObject.put("content", kaleoDefinitionVersion.getContent());
			jsonObject.put("draftVersion", kaleoDefinitionVersion.getVersion());
			jsonObject.put("name", kaleoDefinitionVersion.getName());
			jsonObject.put(
				"title",
				LocalizationUtil.getLocalizationMap(
					kaleoDefinitionVersion.getTitle()));
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveRoles(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<Role> roles = new ArrayList<>();

		long[] roleIds = ParamUtil.getLongValues(resourceRequest, "roleIds");

		if (ArrayUtil.isNotEmpty(roleIds)) {
			roles = _roleLocalService.getRoles(roleIds);
		}
		else {
			String keywords = ParamUtil.getString(resourceRequest, "keywords");
			int type = ParamUtil.getInteger(resourceRequest, "type");

			roles = _roleLocalService.search(
				themeDisplay.getCompanyId(), keywords, getRoleTypesObj(type), 0,
				SearchContainer.DEFAULT_DELTA, new RoleNameComparator());
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Role role : roles) {
			if (!RolePermissionUtil.contains(
					themeDisplay.getPermissionChecker(), role.getRoleId(),
					ActionKeys.VIEW)) {

				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("name", role.getName());
			jsonObject.put("roleId", role.getRoleId());

			jsonArray.put(jsonObject);
		}

		writeJSON(resourceRequest, resourceResponse, jsonArray);
	}

	protected void serveUsers(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<User> users = new ArrayList<>();

		long[] userIds = ParamUtil.getLongValues(resourceRequest, "userIds");

		if (ArrayUtil.isNotEmpty(userIds)) {
			for (int i = 0; i < userIds.length; i++) {
				User user = _userLocalService.fetchUser(userIds[i]);

				if (user != null) {
					users.add(user);
				}
			}
		}
		else {
			String keywords = ParamUtil.getString(resourceRequest, "keywords");

			users = _userLocalService.search(
				themeDisplay.getCompanyId(), keywords,
				WorkflowConstants.STATUS_APPROVED,
				new LinkedHashMap<String, Object>(), 0,
				SearchContainer.DEFAULT_DELTA, new UserFirstNameComparator());
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User user : users) {
			if (!UserPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), user.getUserId(),
					ActionKeys.VIEW)) {

				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("emailAddress", user.getEmailAddress());
			jsonObject.put("fullName", user.getFullName());
			jsonObject.put("screenName", user.getScreenName());
			jsonObject.put("userId", user.getUserId());

			jsonArray.put(jsonObject);
		}

		writeJSON(resourceRequest, resourceResponse, jsonArray);
	}

	@Reference(unbind = "-")
	protected void setKaleoDefinitionVersionLocalService(
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService) {

		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
	}

	protected void setKaleoDefinitionVersionRenderRequestAttribute(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_kaleoDesignerDisplayContext == null) {
			_kaleoDesignerDisplayContext = new KaleoDesignerDisplayContext(
				renderRequest, renderResponse, _userLocalService);
		}

		renderRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DESIGNER_DISPLAY_CONTEXT,
			_kaleoDesignerDisplayContext);

		String name = ParamUtil.getString(renderRequest, "name");
		String draftVersion = ParamUtil.getString(
			renderRequest, "draftVersion");

		if (Validator.isNull(name)) {
			return;
		}

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), name, draftVersion);

		renderRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION,
			kaleoDefinitionVersion);
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDesignerPortlet.class);

	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private KaleoDesignerDisplayContext _kaleoDesignerDisplayContext;

	@Reference
	private Portal _portal;

	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}