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

package com.liferay.sync.engine.util;

import com.liferay.sync.engine.documentlibrary.model.SyncContext;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Shinn Lok
 */
public class ServerInfo {

	public static boolean isCompatible(SyncContext syncContext) {
		return isCompatible(syncContext.getPluginVersion(), 4);
	}

	public static boolean supportsDeviceRegistration(long syncAccountId) {
		return isCompatible(syncAccountId, 7);
	}

	public static boolean supportsPartialDownloads(long syncAccountId) {
		return isCompatible(syncAccountId, 5);
	}

	public static boolean supportsRetrieveFromCache(long syncAccountId) {
		return isCompatible(syncAccountId, 5);
	}

	protected static boolean isCompatible(
		long syncAccountId, int minimumVersion) {

		Boolean compatible = _compatibilityMap.get(
			syncAccountId + "#" + minimumVersion);

		if (compatible != null) {
			return compatible;
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		String pluginVersion = syncAccount.getPluginVersion();

		if (pluginVersion == null) {
			return false;
		}

		compatible = isCompatible(pluginVersion, minimumVersion);

		_compatibilityMap.put(syncAccountId + "#" + minimumVersion, compatible);

		return compatible;
	}

	protected static boolean isCompatible(
		String pluginVersion, int minimumVersion) {

		Matcher matcher = _pattern.matcher(pluginVersion);

		if (!matcher.find()) {
			return false;
		}

		if (pluginVersion.startsWith("6.2") &&
			(Integer.parseInt(matcher.group(1)) < minimumVersion)) {

			return false;
		}

		return true;
	}

	private static final Map<String, Boolean> _compatibilityMap =
		new HashMap<>();
	private static final Pattern _pattern = Pattern.compile(
		"(?:[0-9]+\\.){3}([0-9]+)");

}