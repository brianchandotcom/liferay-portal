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

package com.liferay.portal.upgrade.internal.report;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.felix.cm.PersistenceManager;

/**
 * @author Sam Ziemer
 */
public class UpgradeReport {

	public UpgradeReport(PersistenceManager persistenceManager) {
		_persistenceManager = persistenceManager;

		_initialBuildNumber = _getBuildNumber();
		_initialSchemaVersion = _getSchemaVersion();
	}

	public void addErrorMessage(String loggerName, String message) {
		List<String> errorMessages = _errorMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		errorMessages.add(message);
	}

	public void addEventMessage(String loggerName, String message) {
		List<String> eventMessages = _eventMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		eventMessages.add(message);
	}

	public void addWarningMessage(String loggerName, String message) {
		List<String> warningMessages = _warningMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		warningMessages.add(message);
	}

	public void generateReport() {
		File logFile = _getLogFile();

		StringBuffer sb = new StringBuffer(3);

		sb.append(_getPortalVersions());
		sb.append(_getDialectInfo());
		sb.append(_getProperties());

		try {
			FileUtil.write(logFile, sb.toString());
		}
		catch (IOException ioException) {
			_log.error("Unable to generate the upgrade report");
		}
	}

	private int _getBuildNumber() {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select buildNumber from Release_ where releaseId = " +
					ReleaseConstants.DEFAULT_ID)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("buildNumber");
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get build number");
			}
		}

		return 0;
	}

	private String _getDialectInfo() {
		StringBuffer sb = new StringBuffer(7);

		DB db = DBManagerUtil.getDB();

		sb.append("Using ");
		sb.append(db.getDBType());
		sb.append(" version ");
		sb.append(db.getMajorVersion());
		sb.append(StringPool.PERIOD);
		sb.append(db.getMinorVersion());
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private File _getLogFile() {
		File reportsDir = new File(".", "reports");

		if ((reportsDir != null) && !reportsDir.exists()) {
			reportsDir.mkdirs();
		}

		File reportFile = new File(reportsDir, "upgrade_report.info");

		if (reportFile.exists()) {
			String logFileName = reportFile.getName();

			reportFile.renameTo(
				new File(
					PropsValues.LIFERAY_HOME,
					logFileName + "." + reportFile.lastModified()));

			reportFile = new File(PropsValues.LIFERAY_HOME, logFileName);
		}

		return reportFile;
	}

	private String _getPortalVersions() {
		Version expectedSchemaVersion =
			PortalUpgradeProcess.getLatestSchemaVersion();

		StringBuffer sb = new StringBuffer();

		sb.append(
			_getReleaseInfo(
				_initialBuildNumber, _initialSchemaVersion, "initial"));
		sb.append(StringPool.NEW_LINE);
		sb.append(
			_getReleaseInfo(_getBuildNumber(), _getSchemaVersion(), "final"));
		sb.append(StringPool.NEW_LINE);
		sb.append(
			_getReleaseInfo(
				ReleaseInfo.getBuildNumber(), expectedSchemaVersion.toString(),
				"expected"));
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private String _getProperties() {
		StringBuffer sb = new StringBuffer(10);

		String dlStore = PropsValues.DL_STORE_IMPL;

		sb.append(PropsKeys.DL_STORE_IMPL + StringPool.EQUAL + dlStore);

		sb.append(StringPool.NEW_LINE);

		String rootDir = null;

		if (dlStore.equals(
				"com.liferay.portal.store.file.system." +
					"AdvancedFileSystemStore")) {

			rootDir = _getRootDir(
				_ADVANCED_FILE_SYSTEM_STORE_CONFIGURATION_PID);

			if (rootDir == null) {
				sb.append("rootDir was required. Configure it in ");
				sb.append(_ADVANCED_FILE_SYSTEM_STORE_CONFIGURATION_PID);
				sb.append(".config");
			}
		}
		else if (dlStore.equals(
					"com.liferay.portal.store.file.system.FileSystemStore")) {

			rootDir = _getRootDir(_FILE_SYSTEM_STORE_CONFIGURATION_PID);

			if (rootDir == null) {
				sb.append("rootDir was not set, default directory was used");
			}
		}

		if (rootDir != null) {
			sb.append("rootDir=" + rootDir);
		}

		sb.append(StringPool.NEW_LINE);

		sb.append(
			"locales.enabled=" + Arrays.toString(PropsValues.LOCALES_ENABLED));
		sb.append(StringPool.NEW_LINE);
		sb.append("locales=" + Arrays.toString(PropsValues.LOCALES));
		sb.append(StringPool.NEW_LINE);
		sb.append("liferay.home=" + PropsValues.LIFERAY_HOME);
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private String _getReleaseInfo(
		int buildNumber, String schemaVersion, String type) {

		StringBuffer sb = new StringBuffer();

		if (buildNumber != 0) {
			sb.append(StringUtil.upperCaseFirstLetter(type));
			sb.append(" Portal build number: ");
			sb.append(buildNumber);
		}
		else {
			sb.append("Unable to determine ");
			sb.append(type);
			sb.append(" Portal build number");
		}

		sb.append(StringPool.NEW_LINE);

		if (schemaVersion != null) {
			sb.append(StringUtil.upperCaseFirstLetter(type));
			sb.append(" Portal schema version: ");
			sb.append(schemaVersion);
		}
		else {
			sb.append("Unable to determine ");
			sb.append(type);
			sb.append(" Portal schema version");
		}

		return sb.toString();
	}

	private String _getRootDir(String dlStoreConfigurationPid) {
		try {
			Dictionary<String, String> configurations =
				_persistenceManager.load(dlStoreConfigurationPid);

			if (configurations != null) {
				return configurations.get("rootDir");
			}
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get DL store root dir");
			}
		}

		return null;
	}

	private String _getSchemaVersion() {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select schemaVersion from Release_ where releaseId = " +
					ReleaseConstants.DEFAULT_ID)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getString("schemaVersion");
			}
		}
		catch (SQLException sqlException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get schema version");
			}
		}

		return null;
	}

	private static final String _ADVANCED_FILE_SYSTEM_STORE_CONFIGURATION_PID =
		"com.liferay.portal.store.file.system.configuration." +
			"AdvancedFileSystemStoreConfiguration";

	private static final String _FILE_SYSTEM_STORE_CONFIGURATION_PID =
		"com.liferay.portal.store.file.system.configuration." +
			"FileSystemStoreConfiguration";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeReport.class);

	private final Map<String, ArrayList<String>> _errorMessages =
		new ConcurrentHashMap<>();
	private final Map<String, ArrayList<String>> _eventMessages =
		new ConcurrentHashMap<>();
	private final int _initialBuildNumber;
	private final String _initialSchemaVersion;
	private final PersistenceManager _persistenceManager;
	private final Map<String, ArrayList<String>> _warningMessages =
		new ConcurrentHashMap<>();

}