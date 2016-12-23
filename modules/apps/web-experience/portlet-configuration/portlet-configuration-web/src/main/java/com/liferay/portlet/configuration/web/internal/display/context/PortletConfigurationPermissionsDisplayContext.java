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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.web.internal.constants.PortletConfigurationPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationPermissionsDisplayContext {

	public PortletConfigurationPermissionsDisplayContext(
		HttpServletRequest request) {

		_request = request;
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

		return _modelResource;
	}

	public String getModelResourceDescription() {
		if (_modelResourceDescription != null) {
			return _modelResourceDescription;
		}

		_modelResourceDescription = ParamUtil.getString(
			_request, "modelResourceDescription");

		return _modelResourceDescription;
	}

	public String getResourcePrimKey() {
		if (_resourcePrimKey != null) {
			return _resourcePrimKey;
		}

		_resourcePrimKey = ParamUtil.getString(_request, "resourcePrimKey");

		return _resourcePrimKey;
	}

	public PortletURL getUpdateRolePermissionsURL() {
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

	private String _modelResource;
	private String _modelResourceDescription;
	private String _portletResource;
	private final HttpServletRequest _request;
	private String _resourcePrimKey;
	private String _returnToFullPageURL;
	private String _roleTypesParam;

}