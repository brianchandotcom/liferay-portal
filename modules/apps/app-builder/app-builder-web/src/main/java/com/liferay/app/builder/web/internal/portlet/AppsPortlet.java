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

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.constants.AppBuilderWebKeys;
import com.liferay.app.builder.portlet.tab.AppBuilderAppsPortletTab;
import com.liferay.app.builder.web.internal.configuration.AppBuilderConfiguration;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	configurationPid = "com.liferay.app.builder.web.internal.configuration.AppBuilderConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view_apps.jsp",
		"javax.portlet.name=" + AppBuilderPortletKeys.APPS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class AppsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Object> appsTabs = new LinkedHashMap<>();

		for (String appBuilderAppsPortletTabName :
				_appBuilderAppsPortletTabTrackerMap.keySet()) {

			AppBuilderAppsPortletTab appBuilderAppsPortletTab =
				_appBuilderAppsPortletTabTrackerMap.getService(
					appBuilderAppsPortletTabName);

			appsTabs.put(
				appBuilderAppsPortletTabName,
				HashMapBuilder.<String, Object>put(
					"editEntryPoint",
					appBuilderAppsPortletTab.getEditEntryPoint()
				).put(
					"label",
					appBuilderAppsPortletTab.getLabel(themeDisplay.getLocale())
				).put(
					"listEntryPoint",
					appBuilderAppsPortletTab.getListEntryPoint()
				).build());
		}

		renderRequest.setAttribute(AppBuilderWebKeys.APPS_TABS, appsTabs);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_appBuilderAppsPortletTabTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, AppBuilderAppsPortletTab.class,
				"app.builder.apps.tabs.name");

		_appBuilderConfiguration = ConfigurableUtil.createConfigurable(
			AppBuilderConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_appBuilderAppsPortletTabTrackerMap.close();
	}

	private ServiceTrackerMap<String, AppBuilderAppsPortletTab>
		_appBuilderAppsPortletTabTrackerMap;
	private volatile AppBuilderConfiguration _appBuilderConfiguration;

	@Reference
	private NPMResolver _npmResolver;

}