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

package com.liferay.sync.engine.documentlibrary.util;

import com.liferay.sync.engine.util.ServerInfo;

/**
 * @author Dennis Ju
 */
public class ServerUtil {

	public static String getURLPath(
		boolean supportsModuleFramework, String urlPath) {

		if (supportsModuleFramework) {
			urlPath = urlPath.replace("/sync-web/", "/o/sync/");

			return urlPath.replace("/sync-web.", "/sync.");
		}

		return urlPath;
	}

	public static String getURLPath(long syncAccountId, String urlPath) {
		return getURLPath(
			ServerInfo.supportsModuleFramework(syncAccountId), urlPath);
	}

	public static String getURLPath(String pluginVersion, String urlPath) {
		return getURLPath(
			ServerInfo.supportsModuleFramework(pluginVersion), urlPath);
	}

}