/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.poller.PollerHeader;

/**
 * @author Edward Han
 */
public class PollerRequestHandlerUtil {

	public static PollerRequestHandler getPollerRequestHandler() {
		return _pollerRequestHandler;
	}

	public static PollerHeader getPollerRequestHeader(
		String pollerRequestString) {

		return getPollerRequestHandler().getPollerRequestHeader(
			pollerRequestString);
	}

	public static JSONObject processRequest(
			String path, String pollerRequestString)
		throws Exception {

		return getPollerRequestHandler().processRequest(
			path, pollerRequestString);
	}

	public void setPollerRequestHandler(
		PollerRequestHandler pollerRequestHandler) {

		_pollerRequestHandler = pollerRequestHandler;
	}

	private static PollerRequestHandler _pollerRequestHandler;

}