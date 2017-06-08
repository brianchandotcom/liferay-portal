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

package com.liferay.portal.kernel.deploy.staging;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan Park
 */
public class StagingDeployUtil {

	public static StagingDeployDir getDir(String name) {
		return getInstance()._getDir(name);
	}

	public static StagingDeployUtil getInstance() {
		PortalRuntimePermission.checkGetBeanProperty(StagingDeployUtil.class);

		return _instance;
	}

	public static void registerDir(StagingDeployDir stagingDeployDir) {
		getInstance()._registerDir(stagingDeployDir);
	}

	public static void unregisterDir(String name) {
		getInstance()._unregisterDir(name);
	}

	private StagingDeployUtil() {
		_stagingDeployDirs = new HashMap<>();
	}

	private StagingDeployDir _getDir(String name) {
		return _stagingDeployDirs.get(name);
	}

	private void _registerDir(StagingDeployDir stagingDeployDir) {
		_stagingDeployDirs.put(stagingDeployDir.getName(), stagingDeployDir);

		stagingDeployDir.start();
	}

	private void _unregisterDir(String name) {
		StagingDeployDir stagingDeployDir = _stagingDeployDirs.remove(name);

		if (stagingDeployDir != null) {
			stagingDeployDir.stop();
		}
	}

	private static final StagingDeployUtil _instance = new StagingDeployUtil();

	private final Map<String, StagingDeployDir> _stagingDeployDirs;

}