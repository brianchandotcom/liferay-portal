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

package com.liferay.app.builder.web.internal.portlet;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.web.internal.constants.AppBuilderWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Gabriel Albuquerque
 */
public class AppPortlet extends MVCPortlet {

	public AppPortlet(
		AppBuilderApp appBuilderApp, String appDeploymentType, String appName,
		String portletName) {

		this(
			appBuilderApp, appDeploymentType, appName, portletName, true, true);
	}

	public AppPortlet(
		AppBuilderApp appBuilderApp, String appDeploymentType, String appName,
		String portletName, boolean showFormView, boolean showTableView) {

		_appBuilderApp = appBuilderApp;
		_appDeploymentType = appDeploymentType;
		_appName = appName;
		_portletName = portletName;
		_showFormView = showFormView;
		_showTableView = showTableView;
		_viewTemplate = showTableView ? "/view_entries.jsp" : "/edit_entry.jsp";
	}

	public Dictionary<String, Object> getProperties(
		Map<String, Object> customProperties) {

		HashMapDictionary<String, Object> properties =
			new HashMapDictionary<String, Object>() {
				{
					put("com.liferay.portlet.add-default-resource", true);
					put(
						"com.liferay.portlet.display-category",
						"category.hidden");
					put(
						"com.liferay.portlet.header-portlet-css",
						"/css/main.css");
					put("com.liferay.portlet.use-default-template", true);
					put("javax.portlet.display-name", _appName);
					put("javax.portlet.name", _portletName);
					put(
						"javax.portlet.init-param.template-path",
						"/META-INF/resources/");
					put(
						"javax.portlet.init-param.view-template",
						_viewTemplate);
					put(
						"javax.portlet.security-role-ref",
						"administrator,guest,power-user,user");
				}
			};

		properties.putAll(customProperties);

		return properties;
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(AppBuilderWebKeys.APP, _appBuilderApp);
		renderRequest.setAttribute(
			AppBuilderWebKeys.APP_DEPLOYMENT_TYPE, _appDeploymentType);
		renderRequest.setAttribute(
			AppBuilderWebKeys.SHOW_FORM_VIEW, _showFormView);
		renderRequest.setAttribute(
			AppBuilderWebKeys.SHOW_TABLE_VIEW, _showTableView);

		super.render(renderRequest, renderResponse);
	}

	private final AppBuilderApp _appBuilderApp;
	private final String _appDeploymentType;
	private final String _appName;
	private final String _portletName;
	private final boolean _showFormView;
	private final boolean _showTableView;
	private final String _viewTemplate;

}