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

package com.liferay.portal.upgrade.util;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.version.Version;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

/**
 * @author Luis Ortiz
 */
public class DBUpgradeStatus {

	public static void addErrorMessage(String loggerName, String message) {
		Map<String, Integer> errorMessages = _errorMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = errorMessages.computeIfAbsent(message, key -> 0);

		occurrences++;

		errorMessages.put(message, occurrences);
	}

	public static void addUpgradeProcessMessage(
		String loggerName, String message) {

		List<String> eventMessages = _upgradeProcessMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		eventMessages.add(message);
	}

	public static void addWarningMessage(String loggerName, String message) {
		Map<String, Integer> warningMessages = _warningMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int count = warningMessages.computeIfAbsent(message, key -> 0);

		count++;

		warningMessages.put(message, count);
	}

	public static Map<String, Map<String, Integer>> getErrorMessages() {
		_filterMessages();

		return _errorMessages;
	}

	public static String getFinalSchemaVersion(String bundleSymbolicName) {
		ModuleSchemaVersions moduleSchemaVersions =
			_moduleSchemaVersionsMap.get(bundleSymbolicName);

		return moduleSchemaVersions.getFinalSchemaVersion();
	}

	public static String getInitialSchemaVersion(String bundleSymbolicName) {
		ModuleSchemaVersions moduleSchemaVersions =
			_moduleSchemaVersionsMap.get(bundleSymbolicName);

		return moduleSchemaVersions.getInitialSchemaVersion();
	}

	public static Map<String, ArrayList<String>> getUpgradeProcessMessages() {
		return _upgradeProcessMessages;
	}

	public static String getUpgradeType() {
		return _upgradeType;
	}

	public static Map<String, Map<String, Integer>> getWarningMessages() {
		_filterMessages();

		return _warningMessages;
	}

	public static void setNoUpgradesEnabled() {
		_upgradeType = "Not enabled";
	}

	public static void upgradeFinished() {
		_setFinalSchemaVersion();
		_calculateTypeOfUpgrade();
	}

	private static void _browseReleaseTable(
		UnsafeBiConsumer<ModuleSchemaVersions, String, Exception>
			unsafeBiConsumer) {

		DataSource dataSource = InfrastructureUtil.getDataSource();

		if (dataSource == null) {
			return;
		}

		try (Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion from Release_")) {

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String servletContextName = resultSet.getString(
					"servletContextName");
				String schemaVersion = resultSet.getString("schemaVersion");

				ModuleSchemaVersions moduleSchemaVersions =
					_moduleSchemaVersionsMap.get(servletContextName);

				if (moduleSchemaVersions == null) {
					moduleSchemaVersions = new ModuleSchemaVersions(null);

					_moduleSchemaVersionsMap.put(
						servletContextName, moduleSchemaVersions);
				}

				unsafeBiConsumer.accept(moduleSchemaVersions, schemaVersion);
			}
		}
		catch (SQLException sqlException) {
			_log.error("Unable to get schema version", sqlException);
		}
		catch (Exception exception) {
			_log.error("Unable to process Release_ table", exception);
		}
	}

	private static void _calculateTypeOfUpgrade() {
		String upgradeType = "No upgrade";

		for (Map.Entry<String, ModuleSchemaVersions> servlet :
				_moduleSchemaVersionsMap.entrySet()) {

			ModuleSchemaVersions schemaVersions = servlet.getValue();

			if (schemaVersions.getInitialSchemaVersion() == null) {
				continue;
			}

			Version initialVersion = Version.parseVersion(
				schemaVersions.getInitialSchemaVersion());
			Version finalVersion = Version.parseVersion(
				schemaVersions.getFinalSchemaVersion());

			if (initialVersion.getMajor() < finalVersion.getMajor()) {
				upgradeType = "Major";

				break;
			}

			if (initialVersion.getMinor() < finalVersion.getMinor()) {
				upgradeType = "Minor";

				continue;
			}

			if (initialVersion.getMicro() < finalVersion.getMicro()) {
				if (upgradeType.compareTo("Minor") != 0) {
					upgradeType = "Micro";
				}

				continue;
			}

			String initialQualifier = initialVersion.getQualifier();
			String finalQualifier = finalVersion.getQualifier();

			if (!initialQualifier.isEmpty() && finalQualifier.isEmpty() &&
				(upgradeType.compareTo("Minor") != 0)) {

				upgradeType = "Micro";
			}
		}

		_upgradeType = upgradeType;
	}

	private static void _filterMessages() {
		if (!_filtered) {
			for (String filteredClassName : _FILTERED_CLASS_NAMES) {
				_errorMessages.remove(filteredClassName);
				_warningMessages.remove(filteredClassName);
			}

			_filtered = true;
		}
	}

	private static void _setFinalSchemaVersion() {
		_browseReleaseTable(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions.setFinalSchemaVersion(schemaVersion));
	}

	private static void _setInitialSchemaVersion() {
		_browseReleaseTable(
			(moduleSchemaVersions, schemaVersion) ->
				moduleSchemaVersions.setInitialSchemaVersion(schemaVersion));
	}

	private static final String[] _FILTERED_CLASS_NAMES = {
		"com.liferay.portal.search.elasticsearch7.internal.sidecar." +
			"SidecarManager"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DBUpgradeStatus.class);

	private static final Map<String, Map<String, Integer>> _errorMessages =
		new ConcurrentHashMap<>();
	private static boolean _filtered;
	private static final Map<String, ModuleSchemaVersions>
		_moduleSchemaVersionsMap = new HashMap<>();
	private static final Map<String, ArrayList<String>>
		_upgradeProcessMessages = new ConcurrentHashMap<>();
	private static String _upgradeType = "Not calculated";
	private static final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

	static {
		_setInitialSchemaVersion();
	}

	private static class ModuleSchemaVersions {

		public ModuleSchemaVersions(String initialSchemaVersion) {
			_initialSchemaVersion = initialSchemaVersion;
		}

		public String getFinalSchemaVersion() {
			return _finalSchemaVersion;
		}

		public String getInitialSchemaVersion() {
			return _initialSchemaVersion;
		}

		public void setFinalSchemaVersion(String finalSchemaVersion) {
			_finalSchemaVersion = finalSchemaVersion;
		}

		public void setInitialSchemaVersion(String expectedSchemaVersion) {
			_initialSchemaVersion = expectedSchemaVersion;
		}

		private String _finalSchemaVersion;
		private String _initialSchemaVersion;

	}

}