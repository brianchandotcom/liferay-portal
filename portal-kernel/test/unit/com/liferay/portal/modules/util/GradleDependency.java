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

package com.liferay.portal.modules.util;

import aQute.bnd.version.Version;

/**
 * @author Andrea Di Giorgi
 */
public class GradleDependency {

	public GradleDependency(
		String dependency, String configuration, String moduleGroup,
		String moduleName, String moduleVersion) {

		_dependency = dependency;
		_configuration = configuration;
		_moduleGroup = moduleGroup;
		_moduleName = moduleName;

		if (moduleVersion.equals(_VERSION_DEFAULT)) {
			_moduleVersion = Version.HIGHEST;
		}
		else {
			_moduleVersion = Version.parseVersion(moduleVersion);
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

	public Version getModuleVersion() {
		return _moduleVersion;
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
	private final Version _moduleVersion;

}