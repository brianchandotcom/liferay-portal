/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.auto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class AutoDeployUtil {

	public static AutoDeployDir getDir(String name) {
		return _autoDeployUtil._getDir(name);
	}

	public static AutoDeployUtil getInstance() {
		return _autoDeployUtil;
	}

	public static void registerDir(AutoDeployDir autoDeployDir) {
		_autoDeployUtil._registerDir(autoDeployDir);
	}

	public static void unregisterDir(String name) {
		_autoDeployUtil._unregisterDir(name);
	}

	private AutoDeployUtil() {
	}

	private AutoDeployDir _getDir(String name) {
		return _autoDeployDirs.get(name);
	}

	private void _registerDir(AutoDeployDir autoDeployDir) {
		_autoDeployDirs.put(autoDeployDir.getName(), autoDeployDir);

		autoDeployDir.start();
	}

	private void _unregisterDir(String name) {
		AutoDeployDir autoDeployDir = _autoDeployDirs.remove(name);

		if (autoDeployDir != null) {
			autoDeployDir.stop();
		}
	}

	private static final AutoDeployUtil _autoDeployUtil = new AutoDeployUtil();

	private final Map<String, AutoDeployDir> _autoDeployDirs = new HashMap<>();

}