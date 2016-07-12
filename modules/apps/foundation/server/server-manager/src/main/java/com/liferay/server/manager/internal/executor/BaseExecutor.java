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

import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public abstract class BaseExecutor implements Executor {

	@Override
	public void executeCreate(
			HttpServletRequest request, JSONObject responseJSONObject,
			Queue<String> arguments)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void executeDelete(
			HttpServletRequest request, JSONObject responseJSONObject,
			Queue<String> arguments)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void executeRead(
			HttpServletRequest request, JSONObject responseJSONObject,
			Queue<String> arguments)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void executeUpdate(
			HttpServletRequest request, JSONObject responseJSONObject,
			Queue<String> arguments)
		throws Exception {

		throw new UnsupportedOperationException();
	}

}