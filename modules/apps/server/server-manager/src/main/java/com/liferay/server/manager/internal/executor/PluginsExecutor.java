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

package com.liferay.server.manager.internal.executor;

import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.server.manager.internal.constants.JSONKeys;

import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, property = "server.manager.executor.path=/plugins",
	service = Executor.class
)
public class PluginsExecutor extends BaseExecutor {

	@Override
	public void executeCreate(
			HttpServletRequest httpServletRequest,
			JSONObject responseJSONObject, Queue<String> arguments)
		throws Exception {

		_pluginExecutor.executeCreate(
			httpServletRequest, responseJSONObject, arguments);
	}

	@Override
	public void executeRead(
		HttpServletRequest httpServletRequest, JSONObject responseJSONObject,
		Queue<String> arguments) {

		JSONArray pluginPackagesJSONArray = JSONFactoryUtil.createJSONArray();

		List<PluginPackage> pluginPackages =
			DeployManagerUtil.getInstalledPluginPackages();

		for (PluginPackage pluginPackage : pluginPackages) {
			pluginPackagesJSONArray.put(pluginPackage.getContext());
		}

		responseJSONObject.put(JSONKeys.OUTPUT, pluginPackagesJSONArray);
	}

	private final Executor _pluginExecutor = new PluginExecutor();

}