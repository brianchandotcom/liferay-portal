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

package com.liferay.portal.tools.upgrade.table.builder;

/**
 * @author Andrea Di Giorgi
 */
public class UpgradeTableBuilderArgs {

	public static final String BASE_DIR_NAME = "./";

	public static final boolean OSGI_MODULE = true;

	public String getBaseDirName() {
		return _baseDirName;
	}

	public String getUpgradeTableDirName() {
		return _upgradeTableDirName;
	}

	public boolean isOsgiModule() {
		return _osgiModule;
	}

	public void setBaseDirName(String baseDirName) {
		_baseDirName = baseDirName;
	}

	public void setOsgiModule(boolean osgiModule) {
		_osgiModule = osgiModule;
	}

	public void setUpgradeTableDirName(String upgradeTableDirName) {
		_upgradeTableDirName = upgradeTableDirName;
	}

	private String _baseDirName = BASE_DIR_NAME;
	private boolean _osgiModule = OSGI_MODULE;
	private String _upgradeTableDirName;

}