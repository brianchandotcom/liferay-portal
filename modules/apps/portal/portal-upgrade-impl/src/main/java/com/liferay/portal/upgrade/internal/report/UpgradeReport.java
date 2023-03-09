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
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
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

		Map<String, Object> reportData = _getReportData(
			releaseManagerOSGiCommands);

		if (PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			_printLogContextReport(reportData);
		}

		File reportFile = null;

		try {
			reportFile = _getReportFile();

			FileUtil.write(
				reportFile,
				StringUtil.merge(
					new String[] {_generateReport(reportData)},
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

	private String _generateReport(Map<String, Object> reportData) {
		StringBundler sb = new StringBundler();

		for (Map.Entry<String, Object> entry : reportData.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof List<?>) {
				String header = _getReportHeaderFromKey(key);

				sb.append(header);

				List<Object> elements = (List<Object>)value;

				if (elements.isEmpty()) {
					sb.append(": Nothing registered");
					sb.append(StringPool.NEW_LINE);
				}
				else {
					sb.append(StringPool.NEW_LINE);
					sb.append(
						ListUtil.toString(
							Collections.nCopies(
								header.length(), StringPool.MINUS),
							StringPool.NULL, StringPool.BLANK));
					sb.append(StringPool.NEW_LINE);

					for (Object object : (List<Object>)value) {
						sb.append(object.toString());
						sb.append(StringPool.NEW_LINE);
					}
				}
			}
			else if (value instanceof Map<?, ?>) {
				sb.append(_printContextMap(key, (Map<?, ?>)value));
			}
			else {
				sb.append(_getReportSimpleValueLine(key, value));
				sb.append(StringPool.NEW_LINE);
			}

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
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

	private List<TableCounts> _getDatabaseTableCounts() {
		Map<String, Integer> finalTableCounts = _getTableCounts();

		if ((_initialTableCounts == null) || (finalTableCounts == null)) {
			return null;
		}

		List<TableCounts> tableCounts = new ArrayList<>();

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

			tableCounts.add(new TableCounts(tableName, initialRows, finalRows));
		}

		return tableCounts;
	}

	private String _getDate() {
		Calendar calendar = Calendar.getInstance();

		Date date = calendar.getTime();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"EEE, MMM dd, yyyy hh:mm:ss z");

		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		return simpleDateFormat.format(date);
	}

	private String _getDialectInfo() {
		DB db = DBManagerUtil.getDB();

		return StringBundler.concat(
			db.getDBType(), StringPool.SPACE, db.getMajorVersion(),
			StringPool.PERIOD, db.getMinorVersion());
	}

	private String _getDLStorageInfoNew() {
		if (!StringUtil.endsWith(
				PropsValues.DL_STORE_IMPL, "FileSystemStore")) {

			return "Check externally";
		}

		if (_rootDir == null) {
			return "Unable to determine. \"rootDir\" was not set";
		}

		return _getDLStorageSize();
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

	private List<RunningProcess> _getLongestRunningUpgradeProcessesList() {
		List<String> messages = _eventMessages.get(
			UpgradeProcess.class.getName());

		if (ListUtil.isEmpty(messages)) {
			return new ArrayList<>();
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

		ArrayList<RunningProcess> reducedMap = new ArrayList<>();

		int count = 0;

		for (Map.Entry<String, Integer> entry : _sort(map)) {
			String key = entry.getKey();
			int value = entry.getValue();

			reducedMap.add(new RunningProcess(key, String.valueOf(value)));

			count++;

			if (count >= _UPGRADE_PROCESSES_COUNT) {
				break;
			}
		}

		return reducedMap;
	}

	private Map<String, String> _getPortalVersionInfo() {
		return LinkedHashMapBuilder.put(
			"expected.build.number",
			() -> {
				int expectedBuildNumber = ReleaseInfo.getBuildNumber();

				if (expectedBuildNumber != 0) {
					return String.valueOf(expectedBuildNumber);
				}

				return "Unable to determine";
			}
		).put(
			"expected.schema.version",
			() -> {
				String expectedSchemaVersion = String.valueOf(
					PortalUpgradeProcess.getLatestSchemaVersion());

				if (expectedSchemaVersion != null) {
					return expectedSchemaVersion;
				}

				return "Unable to determine";
			}
		).put(
			"final.build.number",
			() -> {
				int finalBuildNumber = _getBuildNumber();

				if (finalBuildNumber != 0) {
					return String.valueOf(finalBuildNumber);
				}

				return "Unable to determine";
			}
		).put(
			"final.schema.version",
			() -> {
				String finalSchemaVersion = _getSchemaVersion();

				if (finalSchemaVersion != null) {
					return finalSchemaVersion;
				}

				return "Unable to determine";
			}
		).put(
			"initial.build.number",
			(_initialBuildNumber != 0) ? String.valueOf(_initialBuildNumber) :
				"Unable to determine"
		).put(
			"initial.schema.version",
			(_initialSchemaVersion != null) ? _initialSchemaVersion :
				"Unable to determine"
		).build();
	}

	private Map<String, String> _getPropertiesInfo() {
		_setRootDir();

		return LinkedHashMapBuilder.put(
			PropsKeys.DL_STORE_IMPL, PropsValues.DL_STORE_IMPL
		).put(
			"liferay.home", PropsValues.LIFERAY_HOME
		).put(
			"locales", Arrays.toString(PropsValues.LOCALES)
		).put(
			"locales.enabled", Arrays.toString(PropsValues.LOCALES_ENABLED)
		).put(
			"rootDir", (_rootDir != null) ? _rootDir : "Undefined"
		).build();
	}

	private Map<String, Object> _getReportData(
		ReleaseManagerOSGiCommands releaseManagerOSGiCommands) {

		return LinkedHashMapBuilder.<String, Object>put(
			_PROPERTY_KEY, _getPropertiesInfo()
		).put(
			_TABLES_KEY + ".initial.final.rows", _getDatabaseTableCounts()
		).put(
			"database.version", _getDialectInfo()
		).put(
			"document.library.storage.size", _getDLStorageInfoNew()
		).put(
			"errors", _getSortedLogEvents("errors")
		).put(
			"execution.date", _getDate()
		).put(
			"execution.time", _getUpgradeExecutionTime()
		).put(
			"longest.upgrade.processes",
			_getLongestRunningUpgradeProcessesList()
		).put(
			"osgi.status",
			() -> {
				if (releaseManagerOSGiCommands == null) {
					return "Not possible to check upgrades status";
				}

				String check = releaseManagerOSGiCommands.check();

				if (check.isEmpty()) {
					return "There are no pending upgrades";
				}

				return check;
			}
		).put(
			"portal", _getPortalVersionInfo()
		).put(
			"warnings", _getSortedLogEvents("warnings")
		).build();
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

	private String _getReportHeaderFromKey(String key) {
		if (key.startsWith(_PROPERTY_KEY)) {
			return StringUtil.replaceFirst(
				StringUtil.upperCaseFirstLetter(key), '.', ' ');
		}

		if (key.startsWith(_TABLES_KEY)) {
			return String.format(
				TableCounts.FORMAT, "Table Name", "Initial Rows", "Final Rows");
		}

		return StringUtil.replace(
			StringUtil.upperCaseFirstLetter(key), '.', ' ');
	}

	private String _getReportSimpleValueLine(String key, Object value) {
		return StringBundler.concat(
			_getReportHeaderFromKey(key), StringPool.COLON, StringPool.SPACE,
			value.toString());
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

	private List<EventMessage> _getSortedLogEvents(String type) {
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

		List<EventMessage> list = new ArrayList<>();

		for (Map.Entry<String, Map<String, Integer>> entry :
				sortedEvents.entrySet()) {

			EventMessage event = new EventMessage(entry.getKey());

			list.add(event);

			Map<String, Integer> value = entry.getValue();

			for (Map.Entry<String, Integer> innerEvent : value.entrySet()) {
				event.addEvent(innerEvent.getKey(), innerEvent.getValue());
			}
		}

		return list;
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

	private String _getUpgradeExecutionTime() {
		return String.format(
			"%s seconds", DBUpgrader.getUpgradeTime() / Time.SECOND);
	}

	private String _printContextMap(String key, Map<?, ?> map) {
		StringBundler sb = new StringBundler();

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			Object innerKey = entry.getKey();

			sb.append(
				_getReportSimpleValueLine(
					key + StringPool.PERIOD + innerKey, entry.getValue()));

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private void _printLogContextReport(Map<String, Object> reportData) {
		_logContext = true;

		try {
			for (Map.Entry<String, Object> entry : reportData.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (value instanceof Map<?, ?>) {
					_setContextMap(
						_getLogContextSectionKey(key), (Map<?, ?>)value);
				}
				else {
					ThreadContext.put(
						_getLogContextSectionKey(key), value.toString());
				}
			}
		}
		finally {
			_logContext = false;
		}
	}

	private void _setContextMap(String key, Map<?, ?> map) {
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			Object innerKey = entry.getKey();
			String value = String.valueOf(entry.getValue());

			ThreadContext.put(
				key + StringPool.PERIOD + innerKey.toString(), value);
		}
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

	private static final String _PROPERTY_KEY = "property";

	private static final String _TABLES_KEY = "tables";

	private static final int _UPGRADE_PROCESSES_COUNT = 20;

	private static final Log _log = LogFactoryUtil.getLog(UpgradeReport.class);

	private static boolean _logContext;

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

	private static class EventMessage {

		public EventMessage(String clazz) {
			_clazz = clazz;
		}

		public void addEvent(String message, int occurrences) {
			_counts.add(new Counts(message, occurrences));
		}

		@Override
		public String toString() {
			if (_logContext) {
				return _clazz + StringPool.COLON + _counts.toString();
			}

			StringBundler sb = new StringBundler();

			sb.append("Class name: ");
			sb.append(_clazz);
			sb.append(StringPool.NEW_LINE);

			for (Counts counter : _counts) {
				sb.append(StringPool.TAB);
				sb.append(counter.toString());
				sb.append(StringPool.NEW_LINE);
			}

			return sb.toString();
		}

		private final String _clazz;
		private final List<Counts> _counts = new ArrayList<>();

		private class Counts {

			public Counts(String message, int occurrences) {
				_message = message;
				_occurrences = occurrences;
			}

			@Override
			public String toString() {
				if (_logContext) {
					return _occurrences + StringPool.COLON + _message;
				}

				return StringBundler.concat(
					_occurrences, " occurrences of the following event: ",
					_message);
			}

			private final int _occurrences;
			private final String _message;

		}

	}

	private class RunningProcess {

		public RunningProcess(String process, String time) {
			_process = process;
			_time = time;
		}

		@Override
		public String toString() {
			if (_logContext) {
				return StringBundler.concat(
					_process, StringPool.COLON, _time, " ms");
			}

			return StringBundler.concat(
				StringPool.TAB, _process, " took ", _time, " ms to complete");
		}

		private final String _process;
		private final String _time;

	}

	private class TableCounts {

		public static final String FORMAT = "%-30s %20s %20s";

		public TableCounts(
			String tableName, String initialCount, String finalCount) {

			_tableName = tableName;
			_initialCount = initialCount;
			_finalCount = finalCount;
		}

		@Override
		public String toString() {
			if (_logContext) {
				return StringBundler.concat(
					_tableName, StringPool.COLON, _initialCount,
					StringPool.COLON, _finalCount);
			}

			return String.format(
				FORMAT, _tableName, _initialCount, _finalCount);
		}

		private final String _finalCount;
		private final String _initialCount;
		private final String _tableName;

	}

}