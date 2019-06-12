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

package com.liferay.portal.events;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.verify.VerifyException;

import java.sql.Connection;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class StartupHelperUtil {

	public static StartupHelper getStartupHelper() {
		return _startupHelper;
	}

	public static boolean isDBNew() {
		return getStartupHelper().isDBNew();
	}

	public static boolean isStartupFinished() {
		return getStartupHelper().isStartupFinished();
	}

	public static boolean isUpgraded() {
		return getStartupHelper().isUpgraded();
	}

	public static boolean isUpgrading() {
		return getStartupHelper().isUpgrading();
	}

	public static boolean isVerified() {
		return getStartupHelper().isVerified();
	}

	public static void printPatchLevel() {
		getStartupHelper().printPatchLevel();
	}

	public static void setDbNew(boolean dbNew) {
		getStartupHelper().setDbNew(dbNew);
	}

	public static void setDropIndexes(boolean dropIndexes) {
		getStartupHelper().setDropIndexes(dropIndexes);
	}

	public static void setStartupFinished(boolean startupFinished) {
		getStartupHelper().setStartupFinished(startupFinished);
	}

	public static void updateIndexes() {
		getStartupHelper().updateIndexes();
	}

	public static void updateIndexes(boolean dropIndexes) {
		getStartupHelper().updateIndexes(dropIndexes);
	}

	public static void updateIndexes(
		DB db, Connection connection, boolean dropIndexes) {

		getStartupHelper().updateIndexes(db, connection, dropIndexes);
	}

	public static void upgradeProcess(int buildNumber) throws UpgradeException {
		getStartupHelper().upgradeProcess(buildNumber);
	}

	public static void verifyProcess(boolean verified) throws VerifyException {
		getStartupHelper().verifyProcess(verified);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #verifyProcess(boolean)}
	 */
	@Deprecated
	public static void verifyProcess(boolean newBuildNumber, boolean verified)
		throws VerifyException {

		getStartupHelper().verifyProcess(newBuildNumber, verified);
	}

	public static void verifyRequiredSchemaVersion() throws Exception {
		_startupHelper.verifyRequiredSchemaVersion();
	}

	public void setStartupHelper(StartupHelper startupHelper) {
		_startupHelper = startupHelper;
	}

	private static StartupHelper _startupHelper;

}