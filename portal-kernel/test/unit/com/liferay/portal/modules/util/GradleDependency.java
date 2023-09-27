/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.modules.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Andrea Di Giorgi
 */
public class GradleDependency {

	public GradleDependency(
		String dependency, String configuration, String moduleGroup,
		String moduleName, String moduleVersion, boolean projectDependency) {

		_dependency = dependency;
		_configuration = configuration;
		_moduleGroup = moduleGroup;
		_moduleName = moduleName;

		if (moduleVersion.equals(_VERSION_DEFAULT)) {
			_moduleVersion = StringBundler.concat(
				Integer.MAX_VALUE, StringPool.PERIOD, Integer.MAX_VALUE,
				StringPool.PERIOD, Integer.MAX_VALUE);

			_projectDependency = true;
		}
		else {
			_moduleVersion = moduleVersion;
			_projectDependency = projectDependency;
		}
	}

	public String getConfiguration() {
		return _configuration;
	}

	public String getModuleGroup() {
		return _moduleGroup;
	}

	public String getModuleName() {
		return _moduleName;
	}

	public String getModuleVersion() {
		return _moduleVersion;
	}

	public boolean isProjectDependency() {
		return _projectDependency;
	}

	@Override
	public String toString() {
		return _dependency;
	}

	private static final String _VERSION_DEFAULT = "default";

	private final String _configuration;
	private final String _dependency;
	private final String _moduleGroup;
	private final String _moduleName;
	private final String _moduleVersion;
	private final boolean _projectDependency;

}