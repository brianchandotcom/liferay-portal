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

package com.liferay.lcs.util;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Ivica Cardic
 */
public class LCSConnectionManagerUtil {

	public static LCSConnectionManager getLCSConnectionManager() {
		return _lcsConnectionManager;
	}

	public static Map<String, String> getLCSConnectionMetadata() {
		return getLCSConnectionManager().getLCSConnectionMetadata();
	}

	public static boolean isLCSGatewayAvailable() {
		return getLCSConnectionManager().isLCSGatewayAvailable();
	}

	public static boolean isReady() {
		return getLCSConnectionManager().isReady();
	}

	public static Future<?> start() {
		return getLCSConnectionManager().start();
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private static LCSConnectionManager _lcsConnectionManager;

}