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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class ServerInfo {

	public static boolean isCompatible(SyncContext syncContext) {
		return isCompatible(
			syncContext.getPluginVersion(), "6.2.0.4", "7.0.0.1");
	}

	public static boolean supportsDeviceRegistration(long syncAccountId) {
		return isCompatible(syncAccountId, "6.2.0.7", "7.0.0.1");
	}

	public static boolean supportsModuleFramework(long syncAccountId) {
		return isCompatible(syncAccountId, "7.0.0.1");
	}

	public static boolean supportsPartialDownloads(long syncAccountId) {
		return isCompatible(syncAccountId, "6.2.0.5", "7.0.0.1");
	}

	public static boolean supportsRetrieveFromCache(long syncAccountId) {
		return isCompatible(syncAccountId, "6.2.0.5", "7.0.0.1");
	}

	protected static int getPluginMajorVersion(String pluginVersion) {
		String[] pluginVersionParts = pluginVersion.split("\\.");

		return Integer.valueOf(pluginVersionParts[0] + pluginVersionParts[1]);
	}

	protected static int getPluginPatchVersion(String pluginVersion) {
		return Integer.valueOf(pluginVersion.split("\\.")[3]);
	}

	protected static boolean isCompatible(
		long syncAccountId, String... pluginMinimumVersions) {

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		return isCompatible(
			syncAccount.getPluginVersion(), pluginMinimumVersions);
	}

	protected static boolean isCompatible(
		String pluginVersion, String... pluginMinimumVersions) {

		if (pluginVersion == null) {
			return false;
		}

		String key = pluginVersion + Arrays.toString(pluginMinimumVersions);

		Boolean compatible = _compatibilityMap.get(key);

		if (compatible != null) {
			return compatible;
		}

		for (String pluginMinimumVersion : pluginMinimumVersions) {
			if (getPluginMajorVersion(pluginVersion) !=
					getPluginMajorVersion(pluginMinimumVersion)) {

				continue;
			}

			if (getPluginPatchVersion(pluginVersion) >=
					getPluginPatchVersion(pluginMinimumVersion)) {

				_compatibilityMap.put(key, true);

				return true;
			}
		}

		_compatibilityMap.put(key, false);

		return false;
	}

	private static final Map<String, Boolean> _compatibilityMap =
		new HashMap<>();

}