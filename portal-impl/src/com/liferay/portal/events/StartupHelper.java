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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.patcher.PatcherUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcessUtil;

import java.sql.Connection;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class StartupHelper {

	public boolean isDBNew() {
		return _dbNew;
	}

	public boolean isStartupFinished() {
		return _startupFinished;
	}

	public boolean isUpgraded() {
		return _upgraded;
	}

	public boolean isUpgrading() {
		return _upgrading;
	}

	public boolean isVerified() {
		return _verified;
	}

	public void printPatchLevel() {
		if (_log.isInfoEnabled() && !PatcherUtil.hasInconsistentPatchLevels()) {
			String installedPatches = StringUtil.merge(
				PatcherUtil.getInstalledPatches(), StringPool.COMMA_AND_SPACE);

			if (Validator.isNull(installedPatches)) {
				_log.info("There are no patches installed");
			}
			else {
				_log.info(
					"The following patches are installed: " + installedPatches);
			}
		}
	}

	public void setDbNew(boolean dbNew) {
		_dbNew = dbNew;
	}

	public void setDropIndexes(boolean dropIndexes) {
		_dropIndexes = dropIndexes;
	}

	public void setStartupFinished(boolean startupFinished) {
		_startupFinished = startupFinished;
	}

	public void updateIndexes() {
		updateIndexes(_dropIndexes);
	}

	public void updateIndexes(boolean dropIndexes) {
		DB db = DBManagerUtil.getDB();

		Connection connection = null;

		try {
			connection = DataAccess.getConnection();

			updateIndexes(db, connection, dropIndexes);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}
	}

	public void updateIndexes(
		DB db, Connection connection, boolean dropIndexes) {

		try {
			Thread currentThread = Thread.currentThread();

			ClassLoader classLoader = currentThread.getContextClassLoader();

			String tablesSQL = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/portal-tables.sql");

			String indexesSQL = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/sql/dependencies/indexes.sql");

			db.updateIndexes(connection, tablesSQL, indexesSQL, dropIndexes);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	public void upgradeProcess(int buildNumber) throws UpgradeException {
		_upgrading = true;

		try {
			List<UpgradeProcess> upgradeProcesses =
				UpgradeProcessUtil.initUpgradeProcesses(
					PortalClassLoaderUtil.getClassLoader(),
					_UPGRADE_PROCESS_CLASS_NAMES);

			_upgraded = UpgradeProcessUtil.upgradeProcess(
				buildNumber, upgradeProcesses);
		}
		finally {
			_upgrading = false;
		}
	}

	public void verifyProcess(boolean verified) throws VerifyException {
		_verified = VerifyProcessUtil.verifyProcess(_upgraded, verified);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #verifyProcess(boolean)}
	 */
	@Deprecated
	public void verifyProcess(boolean newBuildNumber, boolean verified)
		throws VerifyException {

		verifyProcess(verified);
	}

	public void verifyRequiredSchemaVersion() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Check the portal's required schema version");
		}

		if (!PortalUpgradeProcess.isInRequiredSchemaVersion(
				DataAccess.getConnection())) {

			Version currentSchemaVersion =
				PortalUpgradeProcess.getCurrentSchemaVersion(
					DataAccess.getConnection());

			Version requiredSchemaVersion =
				PortalUpgradeProcess.getRequiredSchemaVersion();

			String msg;

			if (currentSchemaVersion.compareTo(requiredSchemaVersion) < 0) {
				msg =
					"You must first upgrade the portal to the required " +
						"schema version " + requiredSchemaVersion;
			}
			else {
				msg =
					"Current portal schema version " + currentSchemaVersion +
						" requires a newer version of Liferay";
			}

			System.out.println(msg);

			throw new RuntimeException(msg);
		}

		if (!PortalUpgradeProcess.isInLatestSchemaVersion(
				DataAccess.getConnection())) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Execute the upgrade tool first if you need to upgrade " +
						"the portal to the latest schema version");
			}
		}
	}

	protected String[] getUpgradeProcessClassNames(String key) {

		// We would normally call PropsUtil#getArray(String) to return a String
		// array based on a comma delimited value. However, there is a bug with
		// Apache Commons Configuration where multi-line comma delimited values
		// do not interpolate properly (i.e. cannot be referenced by other
		// properties). The workaround to the bug is to escape commas with a
		// back slash. To get the configured String array, we have to call
		// PropsUtil#get(String) and manually split the value into a String
		// array instead of simply calling PropsUtil#getArray(String).

		return StringUtil.split(GetterUtil.getString(PropsUtil.get(key)));
	}

	private static final String[] _UPGRADE_PROCESS_CLASS_NAMES = {
		"com.liferay.portal.upgrade.UpgradeProcess_7_0_0",
		"com.liferay.portal.upgrade.UpgradeProcess_7_0_1",
		"com.liferay.portal.upgrade.UpgradeProcess_7_0_3",
		"com.liferay.portal.upgrade.UpgradeProcess_7_0_5",
		"com.liferay.portal.upgrade.UpgradeProcess_7_0_6",
		"com.liferay.portal.upgrade.PortalUpgradeProcess"
	};

	private static final Log _log = LogFactoryUtil.getLog(StartupHelper.class);

	private boolean _dbNew;
	private boolean _dropIndexes;
	private boolean _startupFinished;
	private boolean _upgraded;
	private boolean _upgrading;
	private boolean _verified;

}