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
import com.liferay.app.builder.web.internal.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.web.internal.portlet.WidgetAppPortlet;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "app.builder.deploy.type=widget",
	service = AppDeployer.class
)
public class WidgetAppDeployer implements AppDeployer {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void deploy(long appId) throws Exception {
		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		_serviceRegistrationsMap.computeIfAbsent(
			appId,
			key -> _deployAppPortlet(
				appId,
				appBuilderApp.getName(LocaleThreadLocal.getDefaultLocale()),
				AppBuilderPortletKeys.WIDGET_APP + "_" + appId));

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.DEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrationsMap.remove(appId);

		if (serviceRegistration == null) {
			return;
		}

		serviceRegistration.unregister();

		AppBuilderApp appBuilderApp =
			_appBuilderAppLocalService.getAppBuilderApp(appId);

		appBuilderApp.setStatus(
			AppBuilderAppConstants.Status.UNDEPLOYED.getValue());

		_appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	private ServiceRegistration<?> _deployAppPortlet(
		long appId, String appName, String portletName) {

		return _bundleContext.registerService(
			Portlet.class, new WidgetAppPortlet(appId),
			new HashMapDictionary<String, Object>() {
				{
					put("com.liferay.portlet.add-default-resource", true);
					put(
						"com.liferay.portlet.display-category",
						"category.collaboration");
					put("com.liferay.portlet.use-default-template", "true");
					put("javax.portlet.display-name", appName);
					put("javax.portlet.name", portletName);
					put(
						"javax.portlet.init-param.template-path",
						"/META-INF/resources/");
					put(
						"javax.portlet.init-param.view-template",
						"/view_entries.jsp");
					put(
						"javax.portlet.security-role-ref",
						"administrator,guest,power-user,user");
					put("javax.portlet.supports.mime-type", "text/html");
				}
			});
	}

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private BundleContext _bundleContext;
	private final ConcurrentHashMap<Long, ServiceRegistration<?>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();

}