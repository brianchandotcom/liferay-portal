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

	public static void deregister() {
		getLCSConnectionManager().deregister();
	}

	public static LCSConnectionManager getLCSConnectionManager() {
		return _lcsConnectionManager;
	}

	public static Map<String, String> getLCSConnectionMetadata() {
		return getLCSConnectionManager().getLCSConnectionMetadata();
	}

	public static boolean isHandshakeExpired() {
		return getLCSConnectionManager().isHandshakeExpired();
	}

	public static boolean isLCSGatewayAvailable() {
		return getLCSConnectionManager().isLCSGatewayAvailable();
	}

	public static boolean isPending() {
		return getLCSConnectionManager().isPending();
	}

	public static boolean isReady() {
		return getLCSConnectionManager().isReady();
	}

	public static Future<?> restart() {
		return getLCSConnectionManager().restart();
	}

	public static void setHandshakeExpired(boolean handshakeExpired) {
		getLCSConnectionManager().setHandshakeExpired(handshakeExpired);
	}

	public static void setPending(boolean pending) {
		getLCSConnectionManager().setPending(pending);
	}

	public static void setReady(boolean ready) {
		getLCSConnectionManager().setReady(ready);
	}

	public static Future<?> start() {
		return getLCSConnectionManager().start();
	}

	public static Future<?> stop() {
		return getLCSConnectionManager().stop();
	}

	public static Future<?> stop(
		boolean deregister, boolean serverManuallyShutdown) {

		return getLCSConnectionManager().stop(
			deregister, serverManuallyShutdown);
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private static LCSConnectionManager _lcsConnectionManager;

}