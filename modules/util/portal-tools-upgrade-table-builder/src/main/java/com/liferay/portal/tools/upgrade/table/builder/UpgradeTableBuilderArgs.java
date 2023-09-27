/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String getReleaseInfoVersion() {
		return _releaseInfoVersion;
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

	public void setReleaseInfoVersion(String releaseInfoVersion) {
		_releaseInfoVersion = releaseInfoVersion;
	}

	public void setUpgradeTableDirName(String upgradeTableDirName) {
		_upgradeTableDirName = upgradeTableDirName;
	}

	private String _baseDirName = BASE_DIR_NAME;
	private boolean _osgiModule = OSGI_MODULE;
	private String _releaseInfoVersion;
	private String _upgradeTableDirName;

}