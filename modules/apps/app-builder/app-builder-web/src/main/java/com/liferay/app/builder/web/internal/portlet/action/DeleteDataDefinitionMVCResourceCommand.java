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

package com.liferay.app.builder.web.internal.portlet.action;

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.portlet.tab.AppBuilderAppsPortletTab;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Optional;

import javax.portlet.ResourceRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AppBuilderPortletKeys.OBJECTS,
		"mvc.command.name=/objects/delete_data_definition"
	},
	service = MVCResourceCommand.class
)
public class DeleteDataDefinitionMVCResourceCommand
	extends BaseAppBuilderMVCResourceCommand<Void> {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_appBuilderAppsPortletTabTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, AppBuilderAppsPortletTab.class,
				"app.builder.apps.tabs.name");
	}

	@Deactivate
	protected void deactivate() {
		_appBuilderAppsPortletTabTrackerMap.close();
	}

	@Override
	protected Optional<Void> doTransactionalCommand(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DataDefinitionResource appResource = DataDefinitionResource.builder(
		).user(
			themeDisplay.getUser()
		).build();

		appResource.deleteDataDefinition(
			ParamUtil.getLong(resourceRequest, "dataDefinitionId"));

		List<AppBuilderApp> appBuilderApps =
			_appBuilderAppLocalService.getAppBuilderApps(
				ParamUtil.getLong(resourceRequest, "dataDefinitionId"));

		for (AppBuilderAppsPortletTab appBuilderAppsPortletTab :
				_appBuilderAppsPortletTabTrackerMap.values()) {

			for (AppBuilderApp appBuilderApp : appBuilderApps) {
				appBuilderAppsPortletTab.deleteApp(
					appBuilderApp.getAppBuilderAppId(), themeDisplay.getUser());
			}
		}

		return Optional.empty();
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private ServiceTrackerMap<String, AppBuilderAppsPortletTab>
		_appBuilderAppsPortletTabTrackerMap;

}