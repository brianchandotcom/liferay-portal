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
public class ReleaseInfo {

	public static final int getBuildNumber() {
		return _BUILD_NUMBER;
	}

	public static final String getVersion() {
		return _VERSION;
	}

	public static boolean isServerCompatible(
		long syncAccountId, int minimumVersion) {

		Boolean serverCompatible = _serverCompatibilityMap.get(
			syncAccountId + "#" + minimumVersion);

		if (serverCompatible != null) {
			return serverCompatible;
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncAccountId);

		serverCompatible = isServerCompatible(
			syncAccount.getPluginVersion(), minimumVersion);

		_serverCompatibilityMap.put(
			syncAccountId + "#" + minimumVersion, serverCompatible);

		return serverCompatible;
	}

	public static boolean isServerCompatible(SyncContext syncContext) {
		return isServerCompatible(syncContext.getPluginVersion(), 4);
	}

	protected static boolean isServerCompatible(
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

	private static final String _BUILD = "3011";

	private static final int _BUILD_NUMBER = Integer.parseInt(_BUILD);

	private static final String _VERSION = "3.0.11";

	private static final Pattern _pattern = Pattern.compile(
		"(?:[0-9]+\\.){3}([0-9]+)");
	private static final Map<String, Boolean> _serverCompatibilityMap =
		new HashMap<>();

}