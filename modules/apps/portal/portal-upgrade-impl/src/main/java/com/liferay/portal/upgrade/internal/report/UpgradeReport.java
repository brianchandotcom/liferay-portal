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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.internal.release.osgi.commands.ReleaseManagerOSGiCommands;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.felix.cm.PersistenceManager;
import org.apache.logging.log4j.ThreadContext;

/**
 * @author Sam Ziemer
 */
public class UpgradeReport {

	public UpgradeReport() {
		_initialBuildNumber = _getBuildNumber();
		_initialSchemaVersion = _getSchemaVersion();
		_initialTableCounts = _getTableCounts();
	}

	public void addErrorMessage(String loggerName, String message) {
		Map<String, Integer> errorMessages = _errorMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = errorMessages.computeIfAbsent(message, key -> 0);

		occurrences++;

		errorMessages.put(message, occurrences);
	}

	public void addEventMessage(String loggerName, String message) {
		List<String> eventMessages = _eventMessages.computeIfAbsent(
			loggerName, key -> new ArrayList<>());

		eventMessages.add(message);
	}

	public void addWarningMessage(String loggerName, String message) {
		Map<String, Integer> warningMessages = _warningMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int count = warningMessages.computeIfAbsent(message, key -> 0);

		count++;

		warningMessages.put(message, count);
	}

	public void filterMessages() {
		for (String filteredClassName : _FILTERED_CLASS_NAMES) {
			_errorMessages.remove(filteredClassName);
			_warningMessages.remove(filteredClassName);
		}
	}

	public void generateReport(
		PersistenceManager persistenceManager,
		ReleaseManagerOSGiCommands releaseManagerOSGiCommands) {

		filterMessages();

		_persistenceManager = persistenceManager;

		String dateInfo = _getDateInfo();
		String timeInfo = _getUpgradeTimeInfo();
		String portalVersionsInfo = _getPortalVersionsInfo();
		String dialectInfo = _getDialectInfo();
		String propertiesInfo = _getPropertiesInfo();
		String dlStorageInfo = _getDLStorageInfo();
		String databaseTablesInfo = _getDatabaseTablesInfo();
		String upgradeProcessesInfo = _getUpgradeProcessesInfo();
		String errorLogEventsInfo = _getLogEventsInfo("errors");
		String warningLogEventsInfo = _getLogEventsInfo("warnings");
		String releaseManagerOSGiInfo = (releaseManagerOSGiCommands == null) ? StringPool.BLANK : releaseManagerOSGiCommands.check();

		try {
			File reportFile = _getReportFile();

			FileUtil.write(
				reportFile,
				StringUtil.merge(
					new String[] {
						dateInfo, timeInfo, portalVersionsInfo, dialectInfo,
						propertiesInfo, dlStorageInfo, databaseTablesInfo,
						upgradeProcessesInfo, errorLogEventsInfo,
						warningLogEventsInfo, releaseManagerOSGiInfo
					},
					StringPool.NEW_LINE + StringPool.NEW_LINE));

			if (_log.isInfoEnabled()) {
				_log.info(
					"Upgrade report generated in " +
						reportFile.getAbsolutePath());
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to generate the upgrade report", ioException);
		}

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			ThreadContext.put("upgrade.report.date", dateInfo);
			ThreadContext.put("upgrade.report.time", timeInfo);
			ThreadContext.put("upgrade.report.portalVersion", portalVersionsInfo);
			ThreadContext.put("upgrade.report.dialect", dialectInfo);
			ThreadContext.put("upgrade.report.properties", propertiesInfo);
			ThreadContext.put("upgrade.report.dlstorage", dlStorageInfo);
			ThreadContext.put("upgrade.report.databasetables", databaseTablesInfo);
			ThreadContext.put("upgrade.report.upgradeprocesses", upgradeProcessesInfo);
			ThreadContext.put("upgrade.report.errorlogevents", errorLogEventsInfo);
			ThreadContext.put("upgrade.report.warninglogevents", warningLogEventsInfo);
			ThreadContext.put("upgrade.report.releasemanagerOSGi", releaseManagerOSGiInfo);

			if (_log.isInfoEnabled()) {
				_log.info("Upgrade report generated");
			}

			ThreadContext.clearMap();
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
				_log.warn("Unable to get build number", exception);
			}
		}

		return 0;
	}

	private String _getDatabaseTablesInfo() {
		Map<String, Integer> finalTableCounts = _getTableCounts();

		if ((_initialTableCounts == null) || (finalTableCounts == null)) {
			return "Unable to get database tables size";
		}

		StringBundler sb = new StringBundler(finalTableCounts.size() + 3);

		String format = "%-30s %20s %20s\n";

		sb.append("Tables in database sorted by initial number of rows:\n");
		sb.append(
			String.format(
				format, "Table name", "Rows (initial)", "Rows (final)"));
		sb.append(String.format(format, _UNDERLINE, _UNDERLINE, _UNDERLINE));

		List<String> tableNames = new ArrayList<>();

		tableNames.addAll(_initialTableCounts.keySet());
		tableNames.addAll(finalTableCounts.keySet());

		ListUtil.distinct(
			tableNames,
			(tableNameA, tableNameB) -> {
				int countA = _initialTableCounts.getOrDefault(tableNameA, 0);
				int countB = _initialTableCounts.getOrDefault(tableNameB, 0);

				if (countA != countB) {
					return countB - countA;
				}

				return tableNameA.compareTo(tableNameB);
			});

		for (String tableName : tableNames) {
			int initialCount = _initialTableCounts.getOrDefault(tableName, -1);
			int finalCount = finalTableCounts.getOrDefault(tableName, -1);

			if ((initialCount <= 0) && (finalCount <= 0)) {
				continue;
			}

			String initialRows =
				(initialCount >= 0) ? String.valueOf(initialCount) :
					StringPool.DASH;

			String finalRows =
				(finalCount >= 0) ? String.valueOf(finalCount) :
					StringPool.DASH;

			sb.append(String.format(format, tableName, initialRows, finalRows));
		}

		return sb.toString();
	}

	private String _getDateInfo() {
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"EEE, MMM dd, yyyy hh:mm:ss z");

		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		return String.format(
			"Date: %s\n", simpleDateFormat.format(calendar.getTime()));
	}

	private String _getDialectInfo() {
		DB db = DBManagerUtil.getDB();

		return StringBundler.concat(
			"Using ", db.getDBType(), " version ", db.getMajorVersion(),
			StringPool.PERIOD, db.getMinorVersion(), StringPool.NEW_LINE);
	}

	private String _getDLStorageInfo() {
		if (!StringUtil.endsWith(
				PropsValues.DL_STORE_IMPL, "FileSystemStore")) {

			return "Check your external repository to know the document " +
				"library storage size";
		}

		if (_rootDir == null) {
			return "Unable to determine the document library storage size " +
				"because the property \"rootDir\" was not set\n";
		}

		double bytes = 0;

		try {
			bytes = FileUtils.sizeOfDirectory(new File(_rootDir));
		}
		catch (Exception exception) {
			return exception.getMessage();
		}

		String[] dictionary = {"bytes", "KB", "MB", "GB", "TB", "PB"};

		int index = 0;

		for (index = 0; index < dictionary.length; index++) {
			if (bytes < 1024) {
				break;
			}

			bytes = bytes / 1024;
		}

		String size = StringBundler.concat(
			String.format("%." + 2 + "f", bytes), StringPool.SPACE,
			dictionary[index]);

		return "The document library storage size is " + size;
	}

	private String _getLogEventsInfo(String type) {
		List<Map.Entry<String, Map<String, Integer>>> entries =
			new ArrayList<>();

		if (type.equals("errors")) {
			entries.addAll(_errorMessages.entrySet());
		}
		else {
			entries.addAll(_warningMessages.entrySet());
		}

		if (entries.isEmpty()) {
			return StringBundler.concat("No ", type, " thrown during upgrade");
		}

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.upperCaseFirstLetter(type));
		sb.append(" thrown during upgrade process\n");

		for (Map.Entry<String, Map<String, Integer>> entry :
				ListUtil.sort(
					entries,
					Collections.reverseOrder(
						Map.Entry.comparingByValue(
							Comparator.comparingInt(Map::size))))) {

			sb.append("Class name: ");
			sb.append(entry.getKey());
			sb.append(StringPool.NEW_LINE);

			for (Map.Entry<String, Integer> valueEntry :
					_sort(entry.getValue())) {

				sb.append(StringPool.TAB);
				sb.append(valueEntry.getValue());
				sb.append(" occurrences of the following ");
				sb.append(type);
				sb.append(": ");
				sb.append(valueEntry.getKey());
				sb.append(StringPool.NEW_LINE);
			}

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _getPortalVersionsInfo() {
		return StringBundler.concat(
			_getReleaseInfo(
				_initialBuildNumber, _initialSchemaVersion, "initial"),
			StringPool.NEW_LINE,
			_getReleaseInfo(_getBuildNumber(), _getSchemaVersion(), "final"),
			StringPool.NEW_LINE,
			_getReleaseInfo(
				ReleaseInfo.getBuildNumber(),
				String.valueOf(PortalUpgradeProcess.getLatestSchemaVersion()),
				"expected"));
	}

	private String _getPropertiesInfo() {
		StringBuffer sb = new StringBuffer(12);

		sb.append("liferay.home=" + PropsValues.LIFERAY_HOME);
		sb.append("\nlocales=" + Arrays.toString(PropsValues.LOCALES));
		sb.append(
			"\nlocales.enabled=" +
				Arrays.toString(PropsValues.LOCALES_ENABLED));
		sb.append(StringPool.NEW_LINE);

		sb.append(
			PropsKeys.DL_STORE_IMPL + StringPool.EQUAL +
				PropsValues.DL_STORE_IMPL);

		sb.append(StringPool.NEW_LINE);

		if (StringUtil.equals(
				PropsValues.DL_STORE_IMPL,
				"com.liferay.portal.store.file.system." +
					"AdvancedFileSystemStore")) {

			_rootDir = _getRootDir(
				_CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE);

			if (_rootDir == null) {
				sb.append("The configuration \"rootDir\" is required. ");
				sb.append("Configure it in ");
				sb.append(_CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE);
				sb.append(".config");
			}
		}
		else if (StringUtil.equals(
					PropsValues.DL_STORE_IMPL,
					"com.liferay.portal.store.file.system.FileSystemStore")) {

			_rootDir = _getRootDir(_CONFIGURATION_PID_FILE_SYSTEM_STORE);

			if (_rootDir == null) {
				_rootDir = PropsValues.LIFERAY_HOME + "/data/document_library";
			}
		}

		if (_rootDir != null) {
			sb.append("rootDir=" + _rootDir);
		}

		return sb.toString();
	}

	private String _getReleaseInfo(
		int buildNumber, String schemaVersion, String type) {

		StringBuffer sb = new StringBuffer();

		if (buildNumber != 0) {
			sb.append(StringUtil.upperCaseFirstLetter(type));
			sb.append(" portal build number: ");
			sb.append(buildNumber);
		}
		else {
			sb.append("Unable to determine ");
			sb.append(type);
			sb.append(" portal build number");
		}

		sb.append(StringPool.NEW_LINE);

		if (schemaVersion != null) {
			sb.append(StringUtil.upperCaseFirstLetter(type));
			sb.append(" portal schema version: ");
			sb.append(schemaVersion);
		}
		else {
			sb.append("Unable to determine ");
			sb.append(type);
			sb.append(" portal schema version");
		}

		return sb.toString();
	}

	private File _getReportFile() {
		File reportsDir = null;

		if (DBUpgrader.isUpgradeClient()) {
			reportsDir = new File(".", "reports");
		}
		else {
			reportsDir = new File(PropsValues.LIFERAY_HOME, "reports");
		}

		if ((reportsDir != null) && !reportsDir.exists()) {
			reportsDir.mkdirs();
		}

		File reportFile = new File(reportsDir, "upgrade_report.info");

		if (reportFile.exists()) {
			String reportFileName = reportFile.getName();

			reportFile.renameTo(
				new File(
					reportsDir,
					reportFileName + "." + reportFile.lastModified()));

			reportFile = new File(reportsDir, reportFileName);
		}

		return reportFile;
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
				_log.warn(
					"Unable to get document library store root dir",
					ioException);
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
				_log.warn("Unable to get schema version", sqlException);
			}
		}

		return null;
	}

	private Map<String, Integer> _getTableCounts() {
		try (Connection connection = DataAccess.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			try (ResultSet resultSet1 = databaseMetaData.getTables(
					dbInspector.getCatalog(), dbInspector.getSchema(), null,
					new String[] {"TABLE"})) {

				Map<String, Integer> tableCounts = new HashMap<>();

				while (resultSet1.next()) {
					String tableName = resultSet1.getString("TABLE_NAME");

					try (PreparedStatement preparedStatement =
							connection.prepareStatement(
								"select count(*) from " + tableName);
						ResultSet resultSet2 =
							preparedStatement.executeQuery()) {

						if (resultSet2.next()) {
							tableCounts.put(tableName, resultSet2.getInt(1));
						}
					}
					catch (SQLException sqlException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to retrieve data from " + tableName,
								sqlException);
						}
					}
				}

				return tableCounts;
			}
		}
		catch (SQLException sqlException) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqlException);
			}

			return null;
		}
	}

	private String _getUpgradeProcessesInfo() {
		List<String> messages = _eventMessages.get(
			UpgradeProcess.class.getName());

		if (ListUtil.isEmpty(messages)) {
			return "No upgrade processes registered";
		}

		StringBundler sb = new StringBundler();

		sb.append("Top ");
		sb.append(_UPGRADE_PROCESSES_COUNT);
		sb.append(" longest running upgrade processes:\n");

		Map<String, Integer> map = new HashMap<>();

		for (String message : messages) {
			int startIndex = message.indexOf("com.");

			int endIndex = message.indexOf(StringPool.SPACE, startIndex);

			String className = message.substring(startIndex, endIndex);

			if (className.equals(PortalUpgradeProcess.class.getName())) {
				continue;
			}

			startIndex = message.indexOf(StringPool.SPACE, endIndex + 1);

			endIndex = message.indexOf(StringPool.SPACE, startIndex + 1);

			map.put(
				className,
				GetterUtil.getInteger(message.substring(startIndex, endIndex)));
		}

		int count = 0;

		for (Map.Entry<String, Integer> entry : _sort(map)) {
			sb.append(StringPool.TAB);
			sb.append(entry.getKey());
			sb.append(" took ");
			sb.append(entry.getValue());
			sb.append(" ms to complete\n");

			count++;

			if (count >= _UPGRADE_PROCESSES_COUNT) {
				break;
			}
		}

		return sb.toString();
	}

	private String _getUpgradeTimeInfo() {
		return String.format(
			"Upgrade completed in %s seconds",
			DBUpgrader.getUpgradeTime() / Time.SECOND);
	}

	private List<Map.Entry<String, Integer>> _sort(Map<String, Integer> map) {
		return ListUtil.sort(
			new ArrayList<>(map.entrySet()),
			Collections.reverseOrder(
				Map.Entry.comparingByValue(Integer::compare)));
	}

	private static final String _CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"AdvancedFileSystemStoreConfiguration";

	private static final String _CONFIGURATION_PID_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"FileSystemStoreConfiguration";

	private static final String[] _FILTERED_CLASS_NAMES = {
		"com.liferay.portal.search.elasticsearch7.internal.sidecar." +
			"SidecarManager"
	};

	private static final String _UNDERLINE = "--------------";

	private static final int _UPGRADE_PROCESSES_COUNT = 20;

	private static final Log _log = LogFactoryUtil.getLog(UpgradeReport.class);

	private final Map<String, Map<String, Integer>> _errorMessages =
		new ConcurrentHashMap<>();
	private final Map<String, ArrayList<String>> _eventMessages =
		new ConcurrentHashMap<>();
	private final int _initialBuildNumber;
	private final String _initialSchemaVersion;
	private final Map<String, Integer> _initialTableCounts;
	private PersistenceManager _persistenceManager;
	private String _rootDir;
	private final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

}