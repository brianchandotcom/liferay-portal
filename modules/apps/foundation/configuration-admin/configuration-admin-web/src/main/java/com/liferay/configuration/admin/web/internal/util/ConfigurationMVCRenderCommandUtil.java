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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.constants.ConfigurationAdminPortletKeys;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pei-Jung Lan
 */
public class ConfigurationMVCRenderCommandUtil {

	public static MVCRenderCommand
		getEditConfigurationMVCRenderCommand(String pid) {

		return _editConfigurationMVCRenderCommandMap.getService(pid);
	}

	public static MVCRenderCommand
		getViewFactoryInstancesMVCRenderCommand(String factoryPid) {

		return _viewFactoryInstancesMVCRenderCommandMap.getService(factoryPid);
	}

	private static final ServiceTrackerMap<String, MVCRenderCommand>
		_editConfigurationMVCRenderCommandMap;
	private static final ServiceTrackerMap<String, MVCRenderCommand>
		_viewFactoryInstancesMVCRenderCommandMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationMVCRenderCommandUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String editConfigurationFilterString =
			"(&(javax.portlet.name=" +
				ConfigurationAdminPortletKeys.SYSTEM_SETTINGS + ")" +
					"(mvc.command.name=/edit_configuration)" +
						"(configurationPid=*))";

		_editConfigurationMVCRenderCommandMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, MVCRenderCommand.class,
				editConfigurationFilterString,
				(serviceReference, emitter) -> emitter.emit(
					(String)serviceReference.getProperty("configurationPid")));

		String viewFactoryInstancesFilterString =
			"(&(javax.portlet.name=" +
				ConfigurationAdminPortletKeys.SYSTEM_SETTINGS + ")" +
					"(mvc.command.name=/view_factory_instances)" +
						"(configurationPid=*))";

		_viewFactoryInstancesMVCRenderCommandMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, MVCRenderCommand.class,
				viewFactoryInstancesFilterString,
				(serviceReference, emitter) -> emitter.emit(
					(String)serviceReference.getProperty("configurationPid")));
	}

}