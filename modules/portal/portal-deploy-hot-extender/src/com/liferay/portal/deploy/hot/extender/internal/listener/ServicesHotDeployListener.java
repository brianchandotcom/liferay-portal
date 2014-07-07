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

package com.liferay.portal.deploy.hot.extender.internal.listener;

import com.liferay.portal.deploy.hot.PluginPackageHotDeployListener;
import com.liferay.portal.deploy.hot.extender.internal.event.ModuleHotDeployEvent;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;

import java.util.List;

/**
 * @author Miguel Pastor
 */
public class ServicesHotDeployListener extends PluginPackageHotDeployListener {

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		ModuleHotDeployEvent moduleHotDeployEvent =
			(ModuleHotDeployEvent)hotDeployEvent;

		if (!moduleHotDeployEvent.hasBundleHeader("Liferay-Service")) {
			return;
		}

		super.invokeDeploy(hotDeployEvent);

		try {
			processServiceResourceActions(hotDeployEvent);
		}
		catch (SystemException se) {
			throw new HotDeployException(
				"Unable to process service resource actions for " +
					hotDeployEvent.getServletContextName(),
				se);
		}
	}

	@Override
	protected void initPortletProps(ClassLoader classLoader) throws Exception {

		// Do not initialize the properties

	}

	protected void processServiceResourceActions(
		HotDeployEvent hotDeployEvent) {

		Configuration configuration = ConfigurationFactoryUtil.getConfiguration(
			hotDeployEvent.getContextClassLoader(), "portlet");

		String resourceActionsConfigs = configuration.get(
			PropsKeys.RESOURCE_ACTIONS_CONFIGS);

		String[] resourceActionConfigs = resourceActionsConfigs.split(",");

		for (String resourceActionConfig : resourceActionConfigs) {
			try {
				ResourceActionsUtil.read(
					hotDeployEvent.getServletContextName(),
					hotDeployEvent.getContextClassLoader(),
					resourceActionConfig);
			}
			catch (Exception e) {
				_log.error(
					"Unable to process resource config actions defined in " +
						resourceActionConfig,
					e);
			}
		}

		String portletIds = configuration.get("portlet.ids");

		String[] portletsIds = portletIds.split(",");

		for (String portletId : portletsIds) {
			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(portletId);

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(portletId);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portletId, portletActions);

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServicesHotDeployListener.class);

}