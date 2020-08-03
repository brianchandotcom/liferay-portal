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

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.app.builder.web.internal.constants.AppBuilderWebKeys;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Gabriel Albuquerque
 */
public class AppPortlet extends MVCPortlet {

	public AppPortlet(
		AppBuilderApp appBuilderApp,
		ServiceTrackerMap<String, AppBuilderAppPortletTab>
			appBuilderAppPortletTabServiceTrackerMap,
		String appDeploymentType, String appName,
		ServiceTrackerMap<String, List<ServiceWrapper<MVCResourceCommand>>>
			appPortletMVCResourceCommandServiceTrackerMap,
		String portletName) {

		this(
			appBuilderApp, appBuilderAppPortletTabServiceTrackerMap,
			appDeploymentType, appName,
			appPortletMVCResourceCommandServiceTrackerMap, portletName, true,
			true);
	}

	public AppPortlet(
		AppBuilderApp appBuilderApp,
		ServiceTrackerMap<String, AppBuilderAppPortletTab>
			appBuilderAppPortletTabServiceTrackerMap,
		String appDeploymentType, String appName,
		ServiceTrackerMap<String, List<ServiceWrapper<MVCResourceCommand>>>
			appPortletMVCResourceCommandServiceTrackerMap,
		String portletName, boolean showFormView, boolean showTableView) {

		_appBuilderApp = appBuilderApp;
		_appBuilderAppPortletTabServiceTrackerMap =
			appBuilderAppPortletTabServiceTrackerMap;
		_appDeploymentType = appDeploymentType;
		_appName = appName;
		_appPortletMVCResourceCommandServiceTrackerMap =
			appPortletMVCResourceCommandServiceTrackerMap;
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

		AppBuilderAppPortletTab appBuilderAppPortletTab =
			_appBuilderAppPortletTabServiceTrackerMap.getService(
				_appBuilderApp.getScope());

		renderRequest.setAttribute(
			AppBuilderWebKeys.APP_TAB,
			HashMapBuilder.<String, Object>put(
				"editEntryPoint", appBuilderAppPortletTab.getEditEntryPoint()
			).put(
				"listEntryPoint", appBuilderAppPortletTab.getListEntryPoint()
			).put(
				"viewEntryPoint", appBuilderAppPortletTab.getViewEntryPoint()
			).build());

		renderRequest.setAttribute(
			AppBuilderWebKeys.APP_TAB_CONTEXT,
			appBuilderAppPortletTab.getAppBuilderAppPortletTabContext(
				_appBuilderApp,
				ParamUtil.getLong(renderRequest, "dataRecordId")));

		String locale = ParamUtil.get(
			renderRequest, "locale", StringPool.BLANK);

		if (Validator.isNotNull(locale)) {
			renderRequest.setAttribute(
				WebKeys.LOCALE, LocaleUtil.fromLanguageId(locale));
		}

		renderRequest.setAttribute(
			AppBuilderWebKeys.SHOW_FORM_VIEW, _showFormView);
		renderRequest.setAttribute(
			AppBuilderWebKeys.SHOW_TABLE_VIEW, _showTableView);

		super.render(renderRequest, renderResponse);
	}

	@Override
	protected boolean callResourceMethod(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			checkPermissions(resourceRequest);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		Map<String, MVCResourceCommand> mvcResourceCommandMap =
			_getMVCResourceCommands(_appBuilderApp);

		MVCResourceCommand mvcResourceCommand = mvcResourceCommandMap.get(
			GetterUtil.getString(resourceRequest.getResourceID()));

		if (!Objects.isNull(mvcResourceCommand)) {
			mvcResourceCommand.serveResource(resourceRequest, resourceResponse);

			return true;
		}

		return super.callResourceMethod(resourceRequest, resourceResponse);
	}

	private Map<String, MVCResourceCommand> _getMVCResourceCommands(
		AppBuilderApp appBuilderApp) {

		Map<String, MVCResourceCommand> mvcResourceCommandMap =
			_getMVCResourceCommands(AppBuilderAppConstants.SCOPE_STANDARD);

		if (!Objects.equals(
				appBuilderApp.getScope(),
				AppBuilderAppConstants.SCOPE_STANDARD)) {

			mvcResourceCommandMap.putAll(
				_getMVCResourceCommands(appBuilderApp.getScope()));
		}

		return mvcResourceCommandMap;
	}

	private Map<String, MVCResourceCommand> _getMVCResourceCommands(
		String scope) {

		return Stream.of(
			_appPortletMVCResourceCommandServiceTrackerMap.getService(scope)
		).filter(
			Objects::nonNull
		).flatMap(
			List::stream
		).collect(
			Collectors.toMap(
				serviceWrapper -> MapUtil.getString(
					serviceWrapper.getProperties(), "mvc.command.name"),
				ServiceWrapper::getService)
		);
	}

	private final AppBuilderApp _appBuilderApp;
	private final ServiceTrackerMap<String, AppBuilderAppPortletTab>
		_appBuilderAppPortletTabServiceTrackerMap;
	private final String _appDeploymentType;
	private final String _appName;
	private final ServiceTrackerMap
		<String, List<ServiceWrapper<MVCResourceCommand>>>
			_appPortletMVCResourceCommandServiceTrackerMap;
	private final String _portletName;
	private final boolean _showFormView;
	private final boolean _showTableView;
	private final String _viewTemplate;

}