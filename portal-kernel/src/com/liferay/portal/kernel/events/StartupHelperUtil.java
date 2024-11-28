/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.upgrade.UpgradeException;

/**
 * @author Mariano Álvaro Sáiz
 */
public class StartupHelperUtil {

	public static void initResourceActions() {
		_startupHelper.initResourceActions();
	}

	public static boolean isDBNew() {
		return _startupHelper.isDBNew();
	}

	public static boolean isDBWarmed() {
		return _startupHelper.isDBWarmed();
	}

	public static boolean isNewRelease() {
		return _startupHelper.isNewRelease();
	}

	public static boolean isUpgrading() {
		return _startupHelper.isUpgrading();
	}

	public static void printPatchLevel() {
		_startupHelper.printPatchLevel();
	}

	public static void setDBNew(boolean dbNew) {
		_startupHelper.setDBNew(dbNew);
	}

	public static void setNewRelease(boolean newRelease) {
		_startupHelper.setNewRelease(newRelease);
	}

	public static void setUpgrading(boolean upgrading) {
		_startupHelper.setUpgrading(upgrading);
	}

	public static void upgradeProcess(int buildNumber) throws UpgradeException {
		_startupHelper.upgradeProcess(buildNumber);
	}

	public static void verifyRequiredSchemaVersion() throws Exception {
		_startupHelper.verifyRequiredSchemaVersion();
	}

	public void setStartupHelper(StartupHelper startupHelper) {
		_startupHelper = startupHelper;
	}

	private static StartupHelper _startupHelper;

}