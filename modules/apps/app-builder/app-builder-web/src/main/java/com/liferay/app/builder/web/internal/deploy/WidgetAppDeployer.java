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

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.web.internal.portlet.AppPortlet;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "app.builder.deploy.type=widget",
	service = AppDeployer.class
)
public class WidgetAppDeployer extends BaseAppDeployer {

	@Override
	public void deploy(long appId) throws Exception {
		AppBuilderApp appBuilderApp =
			appBuilderAppLocalService.getAppBuilderApp(appId);

		appBuilderApp.setActive(true);

		_serviceRegistrationsMap.computeIfAbsent(
			appId,
			key -> new ServiceRegistration<?>[] {
				_deployPortlet(
					appBuilderApp, _getAppName(appBuilderApp, null),
					_getPortletName(appId, null), true, true),
				_deployPortlet(
					appBuilderApp, _getAppName(appBuilderApp, "Form View"),
					_getPortletName(appId, "form_view"), true, false),
				_deployPortlet(
					appBuilderApp, _getAppName(appBuilderApp, "Table View"),
					_getPortletName(appId, "table_view"), false, true)
			});

		appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		undeploy(appBuilderAppLocalService, appId, _serviceRegistrationsMap);
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();

		_serviceRegistrationsMap.clear();
	}

	private ServiceRegistration<?> _deployPortlet(
		AppBuilderApp appBuilderApp, String appName, String portletName,
		boolean showFormView, boolean showTableView) {

		return deployPortlet(
			new AppPortlet(
				appBuilderApp, appBuilderAppPortletTabServiceTrackerMap,
				"widget", appName,
				appPortletMVCResourceCommandServiceTrackerMap, portletName,
				showFormView, showTableView),
			HashMapBuilder.<String, Object>put(
				"com.liferay.portlet.display-category", "category.app_builder"
			).build());
	}

	private String _getAppName(AppBuilderApp appBuilderApp, String suffix) {
		StringBundler sb = new StringBundler(5);

		sb.append(appBuilderApp.getName(LocaleThreadLocal.getDefaultLocale()));

		if (suffix != null) {
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(suffix);
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

	private String _getPortletName(long appId, String suffix) {
		StringBundler sb = new StringBundler(5);

		sb.append(AppBuilderPortletKeys.WIDGET_APP);
		sb.append(StringPool.UNDERLINE);
		sb.append(appId);

		if (suffix != null) {
			sb.append(StringPool.UNDERLINE);
			sb.append(suffix);
		}

		return sb.toString();
	}

	private final ConcurrentHashMap<Long, ServiceRegistration<?>[]>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();

}