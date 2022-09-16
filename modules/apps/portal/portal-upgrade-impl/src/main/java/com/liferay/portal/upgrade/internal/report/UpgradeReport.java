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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.felix.cm.PersistenceManager;

/**
 * @author Sam Ziemer
 */
public class UpgradeReport {

	public UpgradeReport() {
		_stopWatch.start();

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

	public void generateReport(
		PersistenceManager persistenceManager,
		ReleaseManagerOSGiCommands releaseManagerOSGiCommands) {

		_stopWatch.stop();

		_persistenceManager = persistenceManager;

		try {
			FileUtil.write(
				_getReportFile(),
				StringUtil.merge(
					new String[] {
						_getDateInfo(), _getUpgradeTimeInfo(),
						_getPortalVersionsInfo(), _getDialectInfo(),
						_getPropertiesInfo(), _getDLStorageInfo(),
						_getDatabaseTablesInfo(), _getUpgradeProcessesInfo(),
						_getLogEventsInfo("errors"),
						_getLogEventsInfo("warnings"),
						releaseManagerOSGiCommands.check()
					},
					StringPool.NEW_LINE + StringPool.NEW_LINE));
		}
		catch (IOException ioException) {
			_log.error("Unable to generate the upgrade report", ioException);
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

		Set<String> tableNames = new HashSet<>();

		tableNames.addAll(_initialTableCounts.keySet());
		tableNames.addAll(finalTableCounts.keySet());

		StringBundler sb = new StringBundler(finalTableCounts.size() + 3);

		String format = "%-30s %20s %20s\n";

		sb.append("Tables in database sorted by initial number of rows:\n");
		sb.append(
			String.format(
				format, "Table name", "Rows (initial)", "Rows (final)"));
		sb.append(String.format(format, _UNDERLINE, _UNDERLINE, _UNDERLINE));

		Stream<String> stream = tableNames.stream();

		stream.filter(
			tableName -> {
				int initialCount = _initialTableCounts.getOrDefault(
					tableName, 0);
				int finalCount = finalTableCounts.getOrDefault(tableName, 0);

				return (initialCount > 0) || (finalCount > 0);
			}
		).sorted(
			(a, b) -> {
				int countA = _initialTableCounts.getOrDefault(a, 0);
				int countB = _initialTableCounts.getOrDefault(b, 0);

				if (countA == countB) {
					return a.compareTo(b);
				}

				return countB - countA;
			}
		).forEach(
			tableName -> {
				int initialCount = _initialTableCounts.getOrDefault(
					tableName, -1);

				String initialRows =
					(initialCount >= 0) ? String.valueOf(initialCount) :
						StringPool.DASH;

				int finalCount = finalTableCounts.getOrDefault(tableName, -1);

				String finalRows =
					(finalCount >= 0) ? String.valueOf(finalCount) :
						StringPool.DASH;

				sb.append(
					String.format(format, tableName, initialRows, finalRows));
			}
		);

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
		Set<Map.Entry<String, Map<String, Integer>>> entrySet;

		if (type.equals("errors")) {
			entrySet = _errorMessages.entrySet();
		}
		else {
			entrySet = _warningMessages.entrySet();
		}

		if (entrySet.isEmpty()) {
			return StringBundler.concat("No ", type, " thrown during upgrade");
		}

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.upperCaseFirstLetter(type));
		sb.append(" thrown during upgrade process\n");

		Stream<Map.Entry<String, Map<String, Integer>>> stream =
			entrySet.stream();

		Map<String, Map<String, Integer>> sortedErrors = stream.sorted(
			Collections.reverseOrder(
				Map.Entry.comparingByValue(
					new Comparator<Map<String, Integer>>() {

						@Override
						public int compare(
							Map<String, Integer> object1,
							Map<String, Integer> object2) {

							return Integer.compare(
								object1.size(), object2.size());
						}

					}))
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, Map.Entry::getValue,
				(object1, object2) -> object2, LinkedHashMap::new)
		);

		for (Map.Entry<String, Map<String, Integer>> entry :
				sortedErrors.entrySet()) {

			sb.append("Class name: ");
			sb.append(entry.getKey());
			sb.append(StringPool.NEW_LINE);

			Map<String, Integer> value = _sort(entry.getValue());

			for (Map.Entry<String, Integer> valueEntry : value.entrySet()) {
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
		File reportsDir = new File(".", "reports");

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

		map = _sort(map);

		int count = 0;

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
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
			_stopWatch.getTime() / Time.SECOND);
	}

	private Map<String, Integer> _sort(Map<String, Integer> map) {
		Set<Map.Entry<String, Integer>> set = map.entrySet();

		Stream<Map.Entry<String, Integer>> stream = set.stream();

		return stream.sorted(
			Collections.reverseOrder(
				Map.Entry.comparingByValue(
					new Comparator<Integer>() {

						@Override
						public int compare(Integer object1, Integer object2) {
							return Integer.compare(object1, object2);
						}

					}))
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, Map.Entry::getValue,
				(object1, object2) -> object2, LinkedHashMap::new)
		);
	}

	private static final String _CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"AdvancedFileSystemStoreConfiguration";

	private static final String _CONFIGURATION_PID_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"FileSystemStoreConfiguration";

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
	private final StopWatch _stopWatch = new StopWatch();
	private final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

}