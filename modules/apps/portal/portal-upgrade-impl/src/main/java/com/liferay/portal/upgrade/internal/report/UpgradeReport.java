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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
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
		File file = new File(PropsValues.LIFERAY_HOME, "upgrade_report.info");

		try {
			FileUtil.write(file, _getLiferayVersions());

			FileUtil.write(file, _getProperties(), false, true);
		}
		catch (IOException ioException) {
		}
	}

	private int _getInitialBuildNumber() {
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
		}

		return -1;
	}

	private String _getLiferayVersions() {
		StringBuffer sb = new StringBuffer(6);

		if (_initialBuildNumber != -1) {
			sb.append("Initial Version of Liferay: ");
			sb.append(_initialBuildNumber);
			sb.append(StringPool.NEW_LINE);
		}
		else {
			sb.append("Unable to determine initial version of Liferay.");
			sb.append(StringPool.NEW_LINE);
		}

		sb.append("Final Version of Liferay: ");
		sb.append(ReleaseInfo.getBuildNumber());
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private String _getProperties() {
		StringBuffer sb = new StringBuffer(10);

		String dlStore = PropsValues.DL_STORE_IMPL;

		sb.append(PropsKeys.DL_STORE_IMPL + StringPool.EQUAL + dlStore);

		sb.append(StringPool.NEW_LINE);

		if (dlStore.contains("FileSystemStore")) {
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

	private void _setInitialBuildNumber() {
		_initialBuildNumber = _getInitialBuildNumber();
	}

	private final Map<String, ArrayList<String>> _errorMessages =
		new ConcurrentHashMap<>();
	private final Map<String, ArrayList<String>> _eventMessages =
		new ConcurrentHashMap<>();
	private int _initialBuildNumber = -1;
	private final Map<String, ArrayList<String>> _warningMessages =
		new ConcurrentHashMap<>();

}