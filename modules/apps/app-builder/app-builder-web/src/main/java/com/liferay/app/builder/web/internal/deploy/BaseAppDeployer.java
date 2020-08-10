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

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.web.internal.portlet.AppPortlet;
import com.liferay.application.list.PanelApp;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseAppDeployer implements AppDeployer {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		appBuilderAppPortletTabServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, AppBuilderAppPortletTab.class,
				"app.builder.app.tab.name");
		appPortletMVCResourceCommandServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, MVCResourceCommand.class,
				"app.builder.app.scope",
				ServiceTrackerCustomizerFactory.
					<MVCResourceCommand>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		appBuilderAppPortletTabServiceTrackerMap.close();
		appPortletMVCResourceCommandServiceTrackerMap.close();
	}

	protected ServiceRegistration<?> deployLayoutTypeAccessPolicy(
		LayoutTypeAccessPolicy layoutTypeAccessPolicy,
		Dictionary<String, Object> properties) {

		return _bundleContext.registerService(
			LayoutTypeAccessPolicy.class, layoutTypeAccessPolicy, properties);
	}

	protected ServiceRegistration<?> deployLayoutTypeController(
		LayoutTypeController layoutTypeController,
		Dictionary<String, Object> properties) {

		return _bundleContext.registerService(
			LayoutTypeController.class, layoutTypeController, properties);
	}

	protected ServiceRegistration<?> deployPanelApp(
		PanelApp panelApp, Dictionary<String, Object> properties) {

		return _bundleContext.registerService(
			PanelApp.class, panelApp, properties);
	}

	protected ServiceRegistration<?> deployPortlet(
		AppPortlet appPortlet, Map<String, Object> customProperties) {

		return _bundleContext.registerService(
			Portlet.class, appPortlet,
			appPortlet.getProperties(customProperties));
	}

	@Reference
	protected AppBuilderAppLocalService appBuilderAppLocalService;

	protected ServiceTrackerMap<String, AppBuilderAppPortletTab>
		appBuilderAppPortletTabServiceTrackerMap;
	protected ServiceTrackerMap
		<String, List<ServiceWrapper<MVCResourceCommand>>>
			appPortletMVCResourceCommandServiceTrackerMap;

	private BundleContext _bundleContext;

}