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

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.web.internal.application.list.ProductMenuAppPanelApp;
import com.liferay.app.builder.web.internal.application.list.ProductMenuAppPanelCategory;
import com.liferay.app.builder.web.internal.constants.AppBuilderPanelCategoryKeys;
import com.liferay.app.builder.web.internal.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.web.internal.portlet.ProductMenuAppPortlet;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "com.app.builder.deploy.type=productMenu",
	service = AppDeployer.class
)
public class ProductMenuAppDeployer implements AppDeployer {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void deploy(long appId) throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		String appName = appBuilderApp.getName(
			LocaleThreadLocal.getDefaultLocale());

		_deployAppPortlet(appId, appName);

		_deployAppPanelApp(appId);

		_deployAppPanelCategory(appId, appName);

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.DEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		List<ServiceRegistration> serviceRegistrations =
			_serviceRegistrationsMap.get(appId);

		if (serviceRegistrations == null) {
			return;
		}

		for (ServiceRegistration serviceRegistration : serviceRegistrations) {
			serviceRegistration.unregister();
		}

		_serviceRegistrationsMap.remove(appId);

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.UNDEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	private void _addServiceRegistration(
		long appId, ServiceRegistration serviceRegistration) {

		List<ServiceRegistration> serviceRegistrations =
			_serviceRegistrationsMap.get(appId);

		if (serviceRegistrations == null) {
			_serviceRegistrationsMap.put(
				appId, new ArrayList<>(Arrays.asList(serviceRegistration)));
		}
		else {
			serviceRegistrations.add(serviceRegistration);
		}
	}

	private void _deployAppPanelApp(long appId) {
		Dictionary properties = new HashMapDictionary() {
			{
				put("panel.app.order:Integer", 100);
				put("panel.category.key", _getPanelCategoryKey(appId));
			}
		};

		_addServiceRegistration(
			appId,
			_bundleContext.registerService(
				PanelApp.class,
				new ProductMenuAppPanelApp(_getPortletName(appId)),
				properties));
	}

	private void _deployAppPanelCategory(long appId, String appName) {
		Dictionary properties = new HashMapDictionary() {
			{
				put("key", _getPanelCategoryKey(appId));
				put("label", appName);
				put("panel.category.key", PanelCategoryKeys.CONTROL_PANEL);
				put("panel.category.order:Integer", 600);
			}
		};

		_addServiceRegistration(
			appId,
			_bundleContext.registerService(
				PanelCategory.class,
				new ProductMenuAppPanelCategory(properties), properties));
	}

	private void _deployAppPortlet(long appId, String appName) {
		_addServiceRegistration(
			appId,
			_bundleContext.registerService(
				Portlet.class, new ProductMenuAppPortlet(),
				new HashMapDictionary() {
					{
						put("com.liferay.portlet.add-default-resource", true);
						put(
							"com.liferay.portlet.display-category",
							"category.hidden");
						put("com.liferay.portlet.use-default-template", "true");
						put("javax.portlet.display-name", appName);
						put("javax.portlet.name", _getPortletName(appId));
						put(
							"javax.portlet.init-param.template-path",
							"/META-INF/resources/");
						put(
							"javax.portlet.init-param.view-template",
							"/view.jsp");
						put(
							"javax.portlet.security-role-ref",
							"administrator,guest,power-user,user ");
						put("javax.portlet.supports.mime-type", "text/html ");
					}
				}));
	}

	private String _getPanelCategoryKey(long appId) {
		return AppBuilderPanelCategoryKeys.CONTROL_PANEL_APP_BUILDER_APP +
			appId;
	}

	private String _getPortletName(long appId) {
		return AppBuilderPortletKeys.PRODUCT_MENU_APP + "_" + appId;
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private BundleContext _bundleContext;
	private final ConcurrentHashMap<Long, List<ServiceRegistration>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();

}