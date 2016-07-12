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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.server.manager.internal.constants.JSONKeys;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Cindy Li
 */
@Component(
	immediate = true, property = {"server.manager.executor.path=/server"},
	service = Executor.class
)
public class ServerExecutor extends BaseExecutor {

	@Override
	public void executeRead(
			HttpServletRequest request, JSONObject responseJSONObject,
			Queue<String> arguments)
		throws Exception {

		ExecutorPathResolver executorPathResolver = new ExecutorPathResolver(
			_executorServiceRegistry.getAvailableExecutorPaths());

		List<String> paths = executorPathResolver.getNextExecutorsPaths(_path);

		responseJSONObject.put(
			JSONKeys.OUTPUT,
			"Valid paths are " + StringUtil.merge(paths, ", "));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_path = MapUtil.getString(properties, "server.manager.executor.path");
	}

	@Reference
	private ExecutorServiceRegistry _executorServiceRegistry;

	private String _path;

}