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

package com.liferay.portlet.configuration.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.NoSuchResourceException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.ResourcePrimKeyException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.web.internal.constants.PortletConfigurationPortletKeys;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationPermissionsDisplayContext {

	public PortletConfigurationPermissionsDisplayContext(
			HttpServletRequest request)
		throws PortalException {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long resourceGroupId = ParamUtil.getLong(_request, "resourceGroupId");

		if (resourceGroupId == 0) {
			resourceGroupId = themeDisplay.getScopeGroupId();
		}

		long groupId = resourceGroupId;

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Layout selLayout = null;

		if (Objects.equals(getModelResource(), Layout.class.getName())) {
			selLayout = LayoutLocalServiceUtil.getLayout(
				GetterUtil.getLong(getResourcePrimKey()));

			group = selLayout.getGroup();

			groupId = group.getGroupId();
		}

		_selLayout = selLayout;
		_group = group;
		_groupId = groupId;
	}

	public PortletURL getDefinePermissionsURL() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletURL liferayPortletURL =
			(LiferayPortletURL)PortletProviderUtil.getPortletURL(
				_request, Role.class.getName(), PortletProvider.Action.MANAGE);

		liferayPortletURL.setParameter(Constants.CMD, Constants.VIEW);
		liferayPortletURL.setParameter("backURL", themeDisplay.getURLCurrent());
		liferayPortletURL.setPortletMode(PortletMode.VIEW);
		liferayPortletURL.setRefererPlid(themeDisplay.getPlid());

		liferayPortletURL.setWindowState(LiferayWindowState.POP_UP);

		return liferayPortletURL;
	}

	public Group getGroup() {
		return _group;
	}

	public long getGroupId() {
		return _groupId;
	}

	public PortletURL getIteratorURL() throws Exception {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			_request, PortletConfigurationPortletKeys.PORTLET_CONFIGURATION,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_permissions.jsp");
		portletURL.setParameter(
			"returnToFullPageURL", _getReturnToFullPageURL());
		portletURL.setParameter(
			"portletConfiguration", Boolean.TRUE.toString());
		portletURL.setParameter("portletResource", _getPortletResource());
		portletURL.setParameter("resourcePrimKey", getResourcePrimKey());

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	public String getModelResource() {
		if (_modelResource != null) {
			return _modelResource;
		}

		_modelResource = ParamUtil.getString(_request, "modelResource");

		if (Validator.isNotNull(_modelResource)) {
			return _modelResource;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), _getPortletResource());

		_modelResource = portlet.getRootPortletId();

		return _modelResource;
	}

	public String getModelResourceDescription() {
		if (_modelResourceDescription != null) {
			return _modelResourceDescription;
		}

		_modelResourceDescription = ParamUtil.getString(
			_request, "modelResourceDescription");

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Validator.isNotNull(getModelResource())) {
			return _modelResourceDescription;
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), _getPortletResource());

		ServletContext servletContext =
			_request.getSession().getServletContext();

		_modelResourceDescription = PortalUtil.getPortletTitle(
			portlet, servletContext, themeDisplay.getLocale());

		return _modelResourceDescription;
	}

	public Resource getResource() throws PortalException {
		if (_resource != null) {
			return _resource;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (ResourceBlockLocalServiceUtil.isSupported(getModelResource())) {
				ResourceBlockLocalServiceUtil.verifyResourceBlockId(
					themeDisplay.getCompanyId(), getModelResource(),
					Long.valueOf(getResourcePrimKey()));
			}
			else {
				int count =
					ResourcePermissionLocalServiceUtil.
						getResourcePermissionsCount(
							themeDisplay.getCompanyId(), getModelResource(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							getResourcePrimKey());

				if (count == 0) {
					throw new NoSuchResourceException();
				}
			}

			_resource = ResourceLocalServiceUtil.getResource(
				themeDisplay.getCompanyId(), getModelResource(),
				ResourceConstants.SCOPE_INDIVIDUAL, getResourcePrimKey());
		}
		catch (NoSuchResourceException nsre) {
			boolean portletActions = Validator.isNull(getModelResource());

			ResourceLocalServiceUtil.addResources(
				themeDisplay.getCompanyId(), getGroupId(), 0,
				getModelResource(), getResourcePrimKey(), portletActions, true,
				true);

			_resource = ResourceLocalServiceUtil.getResource(
				themeDisplay.getCompanyId(), getModelResource(),
				ResourceConstants.SCOPE_INDIVIDUAL, getResourcePrimKey());
		}

		return _resource;
	}

	public String getResourcePrimKey() throws ResourcePrimKeyException {
		if (_resourcePrimKey != null) {
			return _resourcePrimKey;
		}

		_resourcePrimKey = ParamUtil.getString(_request, "resourcePrimKey");

		if (Validator.isNull(_resourcePrimKey)) {
			throw new ResourcePrimKeyException();
		}

		return _resourcePrimKey;
	}

	public int[] getRoleTypes() {
		if (_roleTypes != null) {
			return _roleTypes;
		}

		String roleTypesParam = _getRoleTypesParam();

		if (Validator.isNotNull(roleTypesParam)) {
			_roleTypes = StringUtil.split(roleTypesParam, 0);
		}

		if (_roleTypes != null) {
			return _roleTypes;
		}

		_roleTypes = RoleConstants.TYPES_REGULAR_AND_SITE;

		if (ResourceActionsUtil.isPortalModelResource(getModelResource())) {
			if (Objects.equals(
					getModelResource(), Organization.class.getName()) ||
				Objects.equals(getModelResource(), User.class.getName())) {

				_roleTypes = RoleConstants.TYPES_ORGANIZATION_AND_REGULAR;
			}
			else {
				_roleTypes = RoleConstants.TYPES_REGULAR;
			}

			return _roleTypes;
		}

		if (_group == null) {
			return _roleTypes;
		}

		Group parentGroup = null;

		if (_group.isLayout()) {
			parentGroup = GroupLocalServiceUtil.fetchGroup(
				_group.getParentGroupId());
		}

		if (parentGroup == null) {
			if (_group.isOrganization()) {
				_roleTypes =
					RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;
			}
			else if (_group.isUser()) {
				_roleTypes = RoleConstants.TYPES_REGULAR;
			}
		}
		else {
			if (parentGroup.isOrganization()) {
				_roleTypes =
					RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE;
			}
			else if (parentGroup.isUser()) {
				_roleTypes = RoleConstants.TYPES_REGULAR;
			}
		}

		return _roleTypes;
	}

	public Layout getSelLayout() {
		return _selLayout;
	}

	public PortletURL getUpdateRolePermissionsURL()
		throws ResourcePrimKeyException {

		int cur = ParamUtil.getInteger(
			_request, SearchContainer.DEFAULT_CUR_PARAM);
		int delta = ParamUtil.getInteger(
			_request, SearchContainer.DEFAULT_DELTA_PARAM);

		long resourceGroupId = ParamUtil.getLong(_request, "resourceGroupId");

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_request, PortletConfigurationPortletKeys.PORTLET_CONFIGURATION,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "updateRolePermissions");
		portletURL.setParameter("mvcPath", "/edit_permissions.jsp");
		portletURL.setParameter("cur", String.valueOf(cur));
		portletURL.setParameter("delta", String.valueOf(delta));
		portletURL.setParameter(
			"returnToFullPageURL", _getReturnToFullPageURL());
		portletURL.setParameter(
			"portletConfiguration", Boolean.TRUE.toString());
		portletURL.setParameter("portletResource", _getPortletResource());
		portletURL.setParameter("modelResource", getModelResource());
		portletURL.setParameter(
			"modelResourceDescription", getModelResourceDescription());
		portletURL.setParameter(
			"resourceGroupId", String.valueOf(resourceGroupId));
		portletURL.setParameter("resourcePrimKey", getResourcePrimKey());
		portletURL.setParameter("roleTypes", _getRoleTypesParam());

		return portletURL;
	}

	private String _getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(_request, "portletResource");

		return _portletResource;
	}

	private String _getReturnToFullPageURL() {
		if (_returnToFullPageURL != null) {
			return _returnToFullPageURL;
		}

		_returnToFullPageURL = ParamUtil.getString(
			_request, "returnToFullPageURL");

		return _returnToFullPageURL;
	}

	private String _getRoleTypesParam() {
		if (_roleTypesParam != null) {
			return _roleTypesParam;
		}

		_roleTypesParam = ParamUtil.getString(_request, "roleTypes");

		return _roleTypesParam;
	}

	private Group _group;
	private final long _groupId;
	private String _modelResource;
	private String _modelResourceDescription;
	private String _portletResource;
	private final HttpServletRequest _request;
	private Resource _resource;
	private String _resourcePrimKey;
	private String _returnToFullPageURL;
	private int[] _roleTypes;
	private String _roleTypesParam;
	private final Layout _selLayout;

}