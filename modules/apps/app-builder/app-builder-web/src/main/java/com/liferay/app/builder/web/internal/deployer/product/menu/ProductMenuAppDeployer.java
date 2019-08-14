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

package com.liferay.app.builder.web.internal.deployer.product.menu;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.service.PortletLocalService;
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
	immediate = true, property = "com.app.builder.deployment.type=productMenu",
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

		_deployAppPortlet(
			appId, appBuilderApp.getName(LocaleThreadLocal.getDefaultLocale()));

		_deployAppPanelApp(appId);

		_deployAppPanelCategory(appId);

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.DEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		List<ServiceRegistration> serviceRegistrations =
			_productMenuAppsMap.get(appId);

		if (serviceRegistrations == null) {
			return;
		}

		for (ServiceRegistration serviceRegistration : serviceRegistrations) {
			serviceRegistration.unregister();
		}

		_productMenuAppsMap.remove(appId);

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.UNDEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	private void _addToMap(
		long appId, ServiceRegistration serviceRegistration) {

		if (_productMenuAppsMap.containsKey(appId)) {
			List<ServiceRegistration> serviceRegistrations =
				_productMenuAppsMap.get(appId);

			serviceRegistrations.add(serviceRegistration);
		}
		else {
			_productMenuAppsMap.put(
				appId, new ArrayList<>(Arrays.asList(serviceRegistration)));
		}
	}

	private void _deployAppPanelApp(long appId) {
		com.liferay.portal.kernel.model.Portlet portlet =
			_portletLocalService.getPortletById(
				String.format(_PORTLET_ID, appId));

		ProductMenuAppPanelApp productMenuAppPanelApp =
			new ProductMenuAppPanelApp(portlet);

		Dictionary properties = new HashMapDictionary() {
			{
				put("panel.app.order:Integer", 100);
				put(
					"panel.category.key",
					"product_menu.productMenuApp_" + appId);
			}
		};

		_addToMap(
			appId,
			_bundleContext.registerService(
				PanelApp.class, productMenuAppPanelApp, properties));
	}

	private void _deployAppPanelCategory(long appId) {
		Dictionary properties = new HashMapDictionary() {
			{
				put("panel.category.key", PanelCategoryKeys.CONTROL_PANEL);
				put("panel.category.order:Integer", 600);
				put("key", "product_menu.productMenuApp_" + appId);
				put("label", "Product Menu App" + appId);
			}
		};

		_addToMap(
			appId,
			_bundleContext.registerService(
				PanelCategory.class,
				new ProductMenuAppPanelCategory(properties), properties));
	}

	private void _deployAppPortlet(long appId, String appName) {
		Dictionary properties = new HashMapDictionary() {
			{
				put("com.liferay.portlet.add-default-resource", true);
				put("com.liferay.portlet.display-category", "category.hidden");
				put("com.liferay.portlet.use-default-template", "true");
				put("javax.portlet.display-name", appName);
				put("javax.portlet.name", String.format(_PORTLET_ID, appId));
				put(
					"javax.portlet.init-param.template-path",
					"/META-INF/resources/");
				put("javax.portlet.init-param.view-template", "/view.jsp");
				put(
					"javax.portlet.security-role-ref",
					"administrator,guest,power-user,user ");
				put("javax.portlet.supports.mime-type", "text/html ");
			}
		};

		_addToMap(
			appId,
			_bundleContext.registerService(
				Portlet.class, new ProductMenuAppPortlet(), properties));
	}

	private static final String _PORTLET_ID = "ProductMenuApp%s";

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private BundleContext _bundleContext;

	@Reference
	private PortletLocalService _portletLocalService;

	private final ConcurrentHashMap<Long, List<ServiceRegistration>>
		_productMenuAppsMap = new ConcurrentHashMap<>();

}