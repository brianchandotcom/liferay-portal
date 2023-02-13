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
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

		Date date = _getDate();
		Map<String, TableCounts> databaseTablesInfo = _getDatabaseTableCounts();
		Map<String, Integer> upgradeProcesses =
			_getLongestRunningUpgradeProcesses();
		String releaseManagerOSGiInfo =
			(releaseManagerOSGiCommands == null) ? StringPool.BLANK :
				releaseManagerOSGiCommands.check();

		_sortedErrorMessages = _getSortedLogEvents("errors");
		_sortedWarningMessages = _getSortedLogEvents("warnings");

		_setPortalVersionsInfo();
		_setDatabaseInfo();
		_setRootDir();

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			_setDateInfoLogContext(date);
			_setUpgradeTimeInfoLogContext();
			_setPortalVersionsInfoLogContext();
			_setDialectInfoLogContext();
			_setPropertiesInfoLogContext();
			_setDLStorageInfoLogContext();
			_setDatabaseTablesInfoLogContext(databaseTablesInfo);
			_setUpgradeProcessesInfoLogContext(upgradeProcesses);
			_setLogEventsInfoLogContext("errors");
			_setLogEventsInfoLogContext("warnings");
			_setReleaseManagerOSGiInfoLogContext(releaseManagerOSGiInfo);
		}

		File reportFile = null;

		try {
			reportFile = _getReportFile();

			FileUtil.write(
				reportFile,
				StringUtil.merge(
					new String[] {
						_getDateInfoUpgradeReport(date),
						_getUpgradeTimeInfoUpgradeReport(),
						_getPortalVersionsInfoUpgradeReport(),
						_getDialectInfoUpgradeReport(),
						_getPropertiesInfoUpgradeReport(),
						_getDLStorageInfoUpgradeReport(),
						_getDatabaseTablesInfoUpgradeReport(databaseTablesInfo),
						_getUpgradeProcessesInfoUpgradeReport(upgradeProcesses),
						_getLogEventsInfoUpgradeReport("errors"),
						_getLogEventsInfoUpgradeReport("warnings"),
						releaseManagerOSGiInfo
					},
					StringPool.NEW_LINE + StringPool.NEW_LINE));

			if (_log.isInfoEnabled()) {
				_log.info(
					"Upgrade report generated in " +
						reportFile.getAbsolutePath());
			}
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to generate the upgrade report in " +
					reportFile.getAbsolutePath(),
				ioException);
		}
		finally {
			if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
				ThreadContext.clearMap();
			}
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

	private Map<String, TableCounts> _getDatabaseTableCounts() {
		Map<String, Integer> finalTableCounts = _getTableCounts();

		if ((_initialTableCounts == null) || (finalTableCounts == null)) {
			return null;
		}

		List<String> tableNames = new ArrayList<>();

		tableNames.addAll(_initialTableCounts.keySet());
		tableNames.addAll(finalTableCounts.keySet());

		Map<String, TableCounts> tableCounts = new LinkedHashMap<>();

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

			tableCounts.put(tableName, new TableCounts(initialRows, finalRows));
		}

		return tableCounts;
	}

	private String _getDatabaseTablesInfoUpgradeReport(
		Map<String, TableCounts> tableCounts) {

		if (tableCounts == null) {
			return "Unable to get database tables size";
		}

		StringBundler sb = new StringBundler(tableCounts.size() + 3);

		String format = "%-30s %20s %20s\n";

		sb.append("Tables in database sorted by initial number of rows:\n");
		sb.append(
			String.format(
				format, "Table name", "Rows (initial)", "Rows (final)"));
		sb.append(String.format(format, _UNDERLINE, _UNDERLINE, _UNDERLINE));

		for (Map.Entry<String, TableCounts> entry : tableCounts.entrySet()) {
			String tableName = entry.getKey();
			TableCounts tableCount = entry.getValue();

			sb.append(
				String.format(
					format, tableName, tableCount.getInitialCount(),
					tableCount.getFinalCount()));
		}

		return sb.toString();
	}

	private Date _getDate() {
		Calendar calendar = Calendar.getInstance();

		return calendar.getTime();
	}

	private String _getDateInfoUpgradeReport(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"EEE, MMM dd, yyyy hh:mm:ss z");

		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		return String.format("Date: %s\n", simpleDateFormat.format(date));
	}

	private String _getDialectInfoUpgradeReport() {
		return StringBundler.concat(
			"Using ", _dbType, " version ", _dbVersion, StringPool.NEW_LINE);
	}

	private String _getDLStorageInfoUpgradeReport() {
		if (!StringUtil.endsWith(
				PropsValues.DL_STORE_IMPL, "FileSystemStore")) {

			return "Check your external repository to know the document " +
				"library storage size";
		}

		if (_rootDir == null) {
			return "Unable to determine the document library storage size " +
				"because the property \"rootDir\" was not set\n";
		}

		return "The document library storage size is " + _getDLStorageSize();
	}

	private String _getDLStorageSize() {
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

		return StringBundler.concat(
			String.format("%." + 2 + "f", bytes), StringPool.SPACE,
			dictionary[index]);
	}

	private String _getLogContextSectionKey(String section) {
		return _LOG_CONTEXT_PREFIX + section;
	}

	private String _getLogEventsInfoUpgradeReport(String type) {
		Map<String, Map<String, Integer>> sortedEvents = null;

		if (type.equals("errors")) {
			sortedEvents = _sortedErrorMessages;
		}
		else {
			sortedEvents = _sortedWarningMessages;
		}

		Set<Map.Entry<String, Map<String, Integer>>> entrySet =
			sortedEvents.entrySet();

		if (entrySet.isEmpty()) {
			return StringBundler.concat("No ", type, " thrown during upgrade");
		}

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.upperCaseFirstLetter(type));
		sb.append(" thrown during upgrade process\n");

		for (Map.Entry<String, Map<String, Integer>> entry :
				sortedEvents.entrySet()) {

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

	private Map<String, Integer> _getLongestRunningUpgradeProcesses() {
		List<String> messages = _eventMessages.get(
			UpgradeProcess.class.getName());

		if (ListUtil.isEmpty(messages)) {
			return new HashMap<>();
		}

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

		Map<String, Integer> reducedMap = new LinkedHashMap<>();

		int count = 0;

		for (Map.Entry<String, Integer> entry : _sort(map)) {
			reducedMap.put(entry.getKey(), entry.getValue());

			count++;

			if (count >= _UPGRADE_PROCESSES_COUNT) {
				break;
			}
		}

		return reducedMap;
	}

	private String _getPortalVersionsInfoUpgradeReport() {
		return StringBundler.concat(
			_getReleaseInfo(
				_initialBuildNumber, _initialSchemaVersion, "initial"),
			StringPool.NEW_LINE,
			_getReleaseInfo(_finalBuildNumber, _finalSchemaVersion, "final"),
			StringPool.NEW_LINE,
			_getReleaseInfo(
				_expectedBuildNumber, _expectedSchemaVersion, "expected"));
	}

	private String _getPropertiesInfoUpgradeReport() {
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
					"AdvancedFileSystemStore") &&
			(_rootDir == null)) {

			sb.append("The configuration \"rootDir\" is required. ");
			sb.append("Configure it in ");
			sb.append(_CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE);
			sb.append(".config");
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

	private Map<String, Map<String, Integer>> _getSortedLogEvents(String type) {
		List<Map.Entry<String, Map<String, Integer>>> entries =
			new ArrayList<>();

		if (type.equals("errors")) {
			entries.addAll(_errorMessages.entrySet());
		}
		else {
			entries.addAll(_warningMessages.entrySet());
		}

		ListUtil.sort(
			entries,
			Collections.reverseOrder(
				Map.Entry.comparingByValue(
					Comparator.comparingInt(Map::size))));

		Map<String, Map<String, Integer>> sortedEvents = new LinkedHashMap<>(
			entries.size());

		for (Map.Entry<String, Map<String, Integer>> entry : entries) {
			sortedEvents.put(entry.getKey(), entry.getValue());
		}

		return sortedEvents;
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

	private String _getUpgradeProcessesInfoUpgradeReport(
		Map<String, Integer> upgradeProcesses) {

		if (upgradeProcesses.isEmpty()) {
			return "No upgrade processes registered";
		}

		StringBundler sb = new StringBundler();

		sb.append("Top ");
		sb.append(_UPGRADE_PROCESSES_COUNT);
		sb.append(" longest running upgrade processes:\n");

		for (Map.Entry<String, Integer> entry : upgradeProcesses.entrySet()) {
			sb.append(StringPool.TAB);
			sb.append(entry.getKey());
			sb.append(" took ");
			sb.append(entry.getValue());
			sb.append(" ms to complete\n");
		}

		return sb.toString();
	}

	private String _getUpgradeTimeInfoUpgradeReport() {
		return String.format(
			"Upgrade completed in %s seconds",
			DBUpgrader.getUpgradeTime() / Time.SECOND);
	}

	private void _setDatabaseInfo() {
		DB db = DBManagerUtil.getDB();

		_dbType = String.valueOf(db.getDBType());

		_dbVersion =
			db.getMajorVersion() + StringPool.PERIOD + db.getMinorVersion();
	}

	private void _setDatabaseTablesInfoLogContext(
		Map<String, TableCounts> tableCounts) {

		if (tableCounts == null) {
			ThreadContext.put(
				_getLogContextSectionKey("databaseTables"),
				"Unable to get database tables size");

			return;
		}

		StringBundler sb = new StringBundler(tableCounts.size() + 2);

		sb.append(StringPool.OPEN_BRACKET);

		int count = 0;

		for (Map.Entry<String, TableCounts> tableCount :
				tableCounts.entrySet()) {

			TableCounts currentTableCounts = tableCount.getValue();

			sb.append(tableCount.getKey());
			sb.append(StringPool.COLON);
			sb.append(currentTableCounts.getInitialCount());
			sb.append(StringPool.COLON);
			sb.append(currentTableCounts.getFinalCount());

			count++;

			if (count < tableCounts.size()) {
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			}
		}

		sb.append(StringPool.CLOSE_BRACKET);

		ThreadContext.put(
			_getLogContextSectionKey("databaseTables"), sb.toString());
	}

	private void _setDateInfoLogContext(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"EEE, MMM dd, yyyy hh:mm:ss z");

		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		ThreadContext.put(
			_getLogContextSectionKey("date"), simpleDateFormat.format(date));
	}

	private void _setDialectInfoLogContext() {
		ThreadContext.put(
			_getLogContextSectionKey("dialect"),
			_dbType + StringPool.SPACE + _dbVersion);
	}

	private void _setDLStorageInfoLogContext() {
		if (!StringUtil.endsWith(
				PropsValues.DL_STORE_IMPL, "FileSystemStore")) {

			ThreadContext.put(
				_getLogContextSectionKey("dlstorage"), "Check externally");
		}

		if (_rootDir == null) {
			ThreadContext.put(
				_getLogContextSectionKey("dlstorage"),
				"Unable to determine. \"rootDir\" was not set");
		}

		String size = _getDLStorageSize();

		ThreadContext.put(_getLogContextSectionKey("dlstorage"), size);
	}

	private void _setLogEventsInfoLogContext(String type) {
		Map<String, Map<String, Integer>> sortedEvents;

		if (type.equals("errors")) {
			sortedEvents = _sortedErrorMessages;
		}
		else {
			sortedEvents = _sortedWarningMessages;
		}

		Set<Map.Entry<String, Map<String, Integer>>> entrySet =
			sortedEvents.entrySet();

		if (entrySet.isEmpty()) {
			ThreadContext.put(
				_getLogContextSectionKey(type + "LogEvents"),
				"No " + type + " thrown during upgrade");

			return;
		}

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		int count = 0;

		for (Map.Entry<String, Map<String, Integer>> entry :
				sortedEvents.entrySet()) {

			sb.append(entry.getKey());
			sb.append(StringPool.COLON);
			sb.append(StringPool.OPEN_BRACKET);

			int subcount = 0;

			Map<String, Integer> value = entry.getValue();

			for (Map.Entry<String, Integer> valueEntry : _sort(value)) {
				sb.append(valueEntry.getValue());
				sb.append(StringPool.COLON);
				sb.append(valueEntry.getKey());

				subcount++;

				if (subcount < value.size()) {
					sb.append(StringPool.COMMA);
				}
			}

			sb.append(StringPool.CLOSE_BRACKET);

			count++;

			if (count < sortedEvents.size()) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append(StringPool.CLOSE_BRACKET);

		ThreadContext.put(
			_getLogContextSectionKey(type + "LogEvents"), sb.toString());
	}

	private void _setPortalVersionsInfo() {
		_finalBuildNumber = _getBuildNumber();
		_finalSchemaVersion = _getSchemaVersion();
		_expectedBuildNumber = ReleaseInfo.getBuildNumber();
		_expectedSchemaVersion = String.valueOf(
			PortalUpgradeProcess.getLatestSchemaVersion());
	}

	private void _setPortalVersionsInfoLogContext() {
		ThreadContext.put(
			_getLogContextSectionKey("initialPortalBuildNumber"),
			(_initialBuildNumber != 0) ? String.valueOf(_initialBuildNumber) :
				"Unable to determine");
		ThreadContext.put(
			_getLogContextSectionKey("initialPortalSchemaVersion"),
			(_initialSchemaVersion != null) ? _initialSchemaVersion :
				"Unable to determine");
		ThreadContext.put(
			_getLogContextSectionKey("finalPortalBuildNumber"),
			(_finalBuildNumber != 0) ? String.valueOf(_finalBuildNumber) :
				"Unable to determine");
		ThreadContext.put(
			_getLogContextSectionKey("finalPortalSchemaVersion"),
			(_finalSchemaVersion != null) ? _finalSchemaVersion :
				"Unable to determine");
		ThreadContext.put(
			_getLogContextSectionKey("expectedPortalBuildNumber"),
			(_expectedBuildNumber != 0) ? String.valueOf(_expectedBuildNumber) :
				"Unable to determine");

		ThreadContext.put(
			_getLogContextSectionKey("expectedPortalSchemaVersion"),
			(_expectedSchemaVersion != null) ? _expectedSchemaVersion :
				"Unable to determine");
	}

	private void _setPropertiesInfoLogContext() {
		ThreadContext.put(
			_getLogContextSectionKey("properties.liferay.home"),
			PropsValues.LIFERAY_HOME);

		ThreadContext.put(
			_getLogContextSectionKey("properties.locales"),
			Arrays.toString(PropsValues.LOCALES));

		ThreadContext.put(
			_getLogContextSectionKey("properties.locales.enabled"),
			Arrays.toString(PropsValues.LOCALES_ENABLED));

		ThreadContext.put(
			_getLogContextSectionKey("properties." + PropsKeys.DL_STORE_IMPL),
			PropsValues.DL_STORE_IMPL);

		ThreadContext.put(
			_getLogContextSectionKey("rootDir"),
			(_rootDir != null) ? _rootDir : "Undefined");
	}

	private void _setReleaseManagerOSGiInfoLogContext(
		String releaseManagerOSGiInfo) {

		ThreadContext.put(
			_getLogContextSectionKey("releasemanagerOSGi"),
			releaseManagerOSGiInfo);
	}

	private void _setRootDir() {
		if (StringUtil.equals(
				PropsValues.DL_STORE_IMPL,
				"com.liferay.portal.store.file.system." +
					"AdvancedFileSystemStore")) {

			_rootDir = _getRootDir(
				_CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE);
		}
		else if (StringUtil.equals(
					PropsValues.DL_STORE_IMPL,
					"com.liferay.portal.store.file.system.FileSystemStore")) {

			_rootDir = _getRootDir(_CONFIGURATION_PID_FILE_SYSTEM_STORE);

			if (_rootDir == null) {
				_rootDir = PropsValues.LIFERAY_HOME + "/data/document_library";
			}
		}
	}

	private void _setUpgradeProcessesInfoLogContext(
		Map<String, Integer> upgradeProcesses) {

		if (upgradeProcesses.isEmpty()) {
			ThreadContext.put(
				_getLogContextSectionKey("longestUpgradeProcesses"),
				"No upgrade processes registered");

			return;
		}

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_BRACKET);

		int size = upgradeProcesses.size();

		for (Map.Entry<String, Integer> entry : upgradeProcesses.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.COLON);
			sb.append(entry.getValue());
			sb.append(" ms");

			size--;

			if (size > 0) {
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			}
		}

		sb.append(StringPool.CLOSE_BRACKET);

		ThreadContext.put(
			_getLogContextSectionKey("longestUpgradeProcesses"), sb.toString());
	}

	private void _setUpgradeTimeInfoLogContext() {
		ThreadContext.put(
			_getLogContextSectionKey("time"),
			String.format(
				"%s seconds", DBUpgrader.getUpgradeTime() / Time.SECOND));
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

	private static final String _LOG_CONTEXT_PREFIX = "upgrade.report.";

	private static final String _UNDERLINE = "--------------";

	private static final int _UPGRADE_PROCESSES_COUNT = 20;

	private static final Log _log = LogFactoryUtil.getLog(UpgradeReport.class);

	private String _dbType;
	private String _dbVersion;
	private final Map<String, Map<String, Integer>> _errorMessages =
		new ConcurrentHashMap<>();
	private final Map<String, ArrayList<String>> _eventMessages =
		new ConcurrentHashMap<>();
	private int _expectedBuildNumber;
	private String _expectedSchemaVersion;
	private int _finalBuildNumber;
	private String _finalSchemaVersion;
	private final int _initialBuildNumber;
	private final String _initialSchemaVersion;
	private final Map<String, Integer> _initialTableCounts;
	private PersistenceManager _persistenceManager;
	private String _rootDir;
	private Map<String, Map<String, Integer>> _sortedErrorMessages;
	private Map<String, Map<String, Integer>> _sortedWarningMessages;
	private final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

	private class TableCounts {

		public TableCounts(String initialCount, String finalCount) {
			_initialCount = initialCount;
			_finalCount = finalCount;
		}

		public String getFinalCount() {
			return _finalCount;
		}

		public String getInitialCount() {
			return _initialCount;
		}

		private final String _finalCount;
		private final String _initialCount;

	}

}