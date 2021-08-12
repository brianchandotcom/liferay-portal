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
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sam Ziemer
 */
public class UpgradeReport {

	public UpgradeReport() {
		_setInitialBuildNumber();

		if ((_initialBuildNumber != -1) &&
			(_initialBuildNumber > ReleaseInfo.RELEASE_7_0_0_BUILD_NUMBER)) {

			_setInitialSchemaVersion();
		}
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

		sb.append(_getLiferayVersions());
		sb.append(_getDialectInfo());
		sb.append(_getProperties());

		try {
			FileUtil.write(logFile, sb.toString());
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to generate report");
			}
		}
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

	private String _getLiferayVersions() {
		StringBuffer sb = new StringBuffer(10);

		String currentSchemaVersion = _getSchemaVersion();

		if (_initialBuildNumber != -1) {
			sb.append("Initial version of Liferay: ");
			sb.append(_initialBuildNumber);

			if (_initialSchemaVersion != null) {
				sb.append(" and initial schema version ");
				sb.append(_initialSchemaVersion);
			}
		}
		else {
			sb.append("Unable to determine initial version of Liferay.");
		}

		sb.append(StringPool.NEW_LINE);

		sb.append("Final version of Liferay ");
		sb.append(ReleaseInfo.getBuildNumber());

		if (currentSchemaVersion != null) {
			sb.append(" and final schema version ");
			sb.append(currentSchemaVersion);
		}

		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private File _getLogFile() {
		File logFile = new File(
			PropsValues.LIFERAY_HOME, "upgrade_report.info");

		if (logFile.exists()) {
			String logFileName = logFile.getName();

			logFile.renameTo(
				new File(
					PropsValues.LIFERAY_HOME,
					logFileName + "." + logFile.lastModified()));

			logFile = new File(PropsValues.LIFERAY_HOME, logFileName);
		}

		return logFile;
	}

	private String _getProperties() {
		StringBuffer sb = new StringBuffer(10);

		String dlStore = PropsValues.DL_STORE_IMPL;

		sb.append(PropsKeys.DL_STORE_IMPL + StringPool.EQUAL + dlStore);

		sb.append(StringPool.NEW_LINE);

		if (dlStore.contains("AdvancedFileSystemStore")) {
			sb.append(
				"Please check your OSGi configuration files to ensure " +
					"rootDir has been set properly");

			sb.append(StringPool.NEW_LINE);
		}

		sb.append(
			"locales.enabled=" + Arrays.toString(PropsValues.LOCALES_ENABLED));
		sb.append(StringPool.NEW_LINE);
		sb.append("locales=" + Arrays.toString(PropsValues.LOCALES));
		sb.append(StringPool.NEW_LINE);
		sb.append("liferay.home=" + PropsValues.LIFERAY_HOME);
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
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
				_log.warn("Unable to get schemaVersion");
			}
		}

		return null;
	}

	private void _setInitialBuildNumber() {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select buildNumber from Release_ where releaseId = " +
					ReleaseConstants.DEFAULT_ID)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				_initialBuildNumber = resultSet.getInt("buildNumber");
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get initial build Number and schema version");
			}
		}
	}

	private void _setInitialSchemaVersion() {
		_initialSchemaVersion = _getSchemaVersion();
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeReport.class);

	private final Map<String, ArrayList<String>> _errorMessages =
		new ConcurrentHashMap<>();
	private final Map<String, ArrayList<String>> _eventMessages =
		new ConcurrentHashMap<>();
	private int _initialBuildNumber = -1;
	private String _initialSchemaVersion;
	private final Map<String, ArrayList<String>> _warningMessages =
		new ConcurrentHashMap<>();

}