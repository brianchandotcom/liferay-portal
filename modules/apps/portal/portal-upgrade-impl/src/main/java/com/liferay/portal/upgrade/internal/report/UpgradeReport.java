/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.report;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder;
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

import org.apache.commons.io.FileUtils;
import org.apache.felix.cm.PersistenceManager;
import org.apache.logging.log4j.ThreadContext;

/**
 * @author Sam Ziemer
 */
public class UpgradeReport {

	public UpgradeReport() {
		_initialBuildNumber = _getBuildNumber();
		_initialTableCounts = _getTableCounts();
	}

	public void generateReport(UpgradeRecorder upgradeRecorder) {
		if (_log.isInfoEnabled()) {
			_log.info("Starting upgrade report generation");
		}

		Map<String, Object> reportData = _getReportData(upgradeRecorder);

		_printToLogContext(reportData);
		_writeToFile(reportData);
	}

	private int _getBuildNumber() {
		try (Connection connection = DataAccess.getConnection()) {
			return PortalUpgradeProcess.getCurrentBuildNumber(connection);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get build number", exception);
			}
		}

		return 0;
	}

	private List<MessagesPrinter> _getMessagesPrinters(
		Map<String, Map<String, Integer>> map1) {

		List<MessagesPrinter> messagesPrinters = new ArrayList<>();

		List<Map.Entry<String, Map<String, Integer>>> list = new ArrayList<>();

		list.addAll(map1.entrySet());

		ListUtil.sort(
			list,
			Collections.reverseOrder(
				Map.Entry.comparingByValue(
					Comparator.comparingInt(Map::size))));

		for (Map.Entry<String, Map<String, Integer>> entry1 : list) {
			MessagesPrinter messagesPrinter = new MessagesPrinter(
				entry1.getKey());

			messagesPrinters.add(messagesPrinter);

			Map<String, Integer> map2 = entry1.getValue();

			for (Map.Entry<String, Integer> entry2 : map2.entrySet()) {
				messagesPrinter.addMessagePrinter(
					entry2.getKey(), entry2.getValue());
			}
		}

		return messagesPrinters;
	}

	private Map<String, Object> _getReportData(
		UpgradeRecorder upgradeRecorder) {

		return LinkedHashMapBuilder.<String, Object>put(
			"execution.date",
			() -> {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"EEE, MMM dd, yyyy hh:mm:ss z");

				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

				Calendar calendar = Calendar.getInstance();

				return simpleDateFormat.format(calendar.getTime());
			}
		).put(
			"execution.time",
			(DBUpgrader.getUpgradeTime() / Time.SECOND) + " seconds"
		).put(
			"portal",
			LinkedHashMapBuilder.put(
				"initial.build.number",
				(_initialBuildNumber != 0) ?
					String.valueOf(_initialBuildNumber) : "Unable to determine"
			).put(
				"initial.schema.version",
				() -> {
					String initialSchemaVersion =
						upgradeRecorder.getInitialSchemaVersion(
							ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

					if ((initialSchemaVersion != null) &&
						!initialSchemaVersion.equals("0.0.0")) {

						return initialSchemaVersion;
					}

					return "Unable to determine";
				}
			).put(
				"final.build.number",
				() -> {
					int buildNumber = _getBuildNumber();

					if (buildNumber != 0) {
						return String.valueOf(buildNumber);
					}

					return "Unable to determine";
				}
			).put(
				"final.schema.version",
				() -> {
					String schemaVersion =
						upgradeRecorder.getFinalSchemaVersion(
							ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

					if (schemaVersion != null) {
						return schemaVersion;
					}

					return "Unable to determine";
				}
			).put(
				"expected.build.number",
				() -> {
					int buildNumber = ReleaseInfo.getBuildNumber();

					if (buildNumber != 0) {
						return String.valueOf(buildNumber);
					}

					return "Unable to determine";
				}
			).put(
				"expected.schema.version",
				() -> {
					String schemaVersion = String.valueOf(
						PortalUpgradeProcess.getLatestSchemaVersion());

					if (schemaVersion != null) {
						return schemaVersion;
					}

					return "Unable to determine";
				}
			).build()
		).put(
			"type", upgradeRecorder.getType()
		).put(
			"result", upgradeRecorder.getResult()
		).put(
			"status",
			() -> {
				ReleaseManager releaseManager = _releaseManagerSnapshot.get();

				if (releaseManager == null) {
					return "Upgrade failed to complete";
				}

				String statusMessage = releaseManager.getStatusMessage(false);

				if (statusMessage.isEmpty()) {
					return "There are no pending upgrades";
				}

				return statusMessage;
			}
		).put(
			"database.version",
			() -> {
				DB db = DBManagerUtil.getDB();

				return StringBundler.concat(
					db.getDBType(), StringPool.SPACE, db.getMajorVersion(),
					StringPool.PERIOD, db.getMinorVersion());
			}
		).put(
			"property",
			() -> {
				if (StringUtil.equals(
						PropsValues.DL_STORE_IMPL,
						"com.liferay.portal.store.file.system." +
							"AdvancedFileSystemStore")) {

					_rootDir = _getRootDir(
						_CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE);
				}
				else if (StringUtil.equals(
							PropsValues.DL_STORE_IMPL,
							"com.liferay.portal.store.file.system." +
								"FileSystemStore")) {

					_rootDir = _getRootDir(
						_CONFIGURATION_PID_FILE_SYSTEM_STORE);

					if (_rootDir == null) {
						_rootDir =
							PropsValues.LIFERAY_HOME + "/data/document_library";
					}
				}

				return LinkedHashMapBuilder.<String, Object>put(
					"liferay.home", PropsValues.LIFERAY_HOME
				).put(
					"locales", Arrays.toString(PropsValues.LOCALES)
				).put(
					"locales.enabled",
					Arrays.toString(PropsValues.LOCALES_ENABLED)
				).put(
					PropsKeys.DL_STORE_IMPL, PropsValues.DL_STORE_IMPL
				).put(
					"rootDir", (_rootDir != null) ? _rootDir : "Undefined"
				).build();
			}
		).put(
			"document.library.storage.size",
			() -> {
				if (!StringUtil.endsWith(
						PropsValues.DL_STORE_IMPL, "FileSystemStore")) {

					return "Check externally";
				}

				if (_rootDir == null) {
					return "Unable to determine. Document library " +
						"\"rootDir\" was not set";
				}

				_dlSize = 0;

				try {
					_dlSizeThread.start();
					_dlSizeThread.join(
						PropsValues.UPGRADE_REPORT_DL_STORAGE_SIZE_TIMEOUT *
							Time.SECOND);
				}
				catch (Exception exception) {
					_log.error(
						"Unable to determine the document library size",
						exception);

					return "Unable to determine";
				}

				if (_dlSizeThread.isAlive()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to determine the document library size. " +
								"Increase the timeout or check it manually.");
					}

					return "Unable to determine";
				}

				return LanguageUtil.formatStorageSize(_dlSize, LocaleUtil.US);
			}
		).put(
			"tables.initial.final.rows",
			() -> {
				Map<String, Integer> finalTableCounts = _getTableCounts();

				if ((finalTableCounts == null) ||
					(_initialTableCounts == null)) {

					return null;
				}

				List<TablePrinter> tablePrinters = new ArrayList<>();

				List<String> tableNames = new ArrayList<>();

				tableNames.addAll(_initialTableCounts.keySet());
				tableNames.addAll(finalTableCounts.keySet());

				ListUtil.distinct(
					tableNames,
					(tableName1, tableName2) -> {
						int initialTableCount1 =
							_initialTableCounts.getOrDefault(tableName1, 0);
						int initialTableCount2 =
							_initialTableCounts.getOrDefault(tableName2, 0);

						if (initialTableCount1 != initialTableCount2) {
							return initialTableCount2 - initialTableCount1;
						}

						return tableName1.compareTo(tableName2);
					});

				for (String tableName : tableNames) {
					int finalTableCount = finalTableCounts.getOrDefault(
						tableName, -1);
					int initialTableCount = _initialTableCounts.getOrDefault(
						tableName, -1);

					if ((finalTableCount <= 0) && (initialTableCount <= 0)) {
						continue;
					}

					tablePrinters.add(
						new TablePrinter(
							(finalTableCount >= 0) ?
								String.valueOf(finalTableCount) :
									StringPool.DASH,
							(initialTableCount >= 0) ?
								String.valueOf(initialTableCount) :
									StringPool.DASH,
							tableName));
				}

				return tablePrinters;
			}
		).put(
			"longest.upgrade.processes",
			() -> {
				Map<String, ArrayList<String>> eventMessages =
					upgradeRecorder.getUpgradeProcessMessages();

				List<String> messages = eventMessages.get(
					UpgradeProcess.class.getName());

				if (ListUtil.isEmpty(messages)) {
					return new ArrayList<>();
				}

				Map<String, Integer> upgradeProcessDurations = new HashMap<>();

				for (String message : messages) {
					int startIndex = message.indexOf("com.");

					int endIndex = message.indexOf(
						StringPool.SPACE, startIndex);

					String className = message.substring(startIndex, endIndex);

					if (className.equals(
							PortalUpgradeProcess.class.getName())) {

						continue;
					}

					startIndex = message.indexOf(
						StringPool.SPACE, endIndex + 1);

					endIndex = message.indexOf(
						StringPool.SPACE, startIndex + 1);

					upgradeProcessDurations.put(
						className,
						GetterUtil.getInteger(
							message.substring(startIndex, endIndex)));
				}

				List<RunningUpgradeProcess> longestRunningUpgradeProcesses =
					new ArrayList<>();

				int count = 0;

				for (Map.Entry<String, Integer> entry :
						ListUtil.sort(
							new ArrayList<>(upgradeProcessDurations.entrySet()),
							Collections.reverseOrder(
								Map.Entry.comparingByValue(
									Integer::compare)))) {

					longestRunningUpgradeProcesses.add(
						new RunningUpgradeProcess(
							String.valueOf(entry.getValue()), entry.getKey()));

					count++;

					if (count >= _UPGRADE_PROCESSES_COUNT) {
						break;
					}
				}

				return longestRunningUpgradeProcesses;
			}
		).put(
			"errors", _getMessagesPrinters(upgradeRecorder.getErrorMessages())
		).put(
			"warnings",
			_getMessagesPrinters(upgradeRecorder.getWarningMessages())
		).build();
	}

	private File _getReportFile() {
		File reportsDir;

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

	private String _getReportHeader(String key) {
		if (key.startsWith("property.")) {
			return StringUtil.replaceFirst(
				StringUtil.upperCaseFirstLetter(key), '.', ' ');
		}

		if (key.startsWith("tables.")) {
			return String.format(
				TablePrinter.FORMAT, "Table Name", "Initial Rows",
				"Final Rows");
		}

		return StringUtil.replace(
			StringUtil.upperCaseFirstLetter(key), '.', ' ');
	}

	private String _getReportLine(String key, Object value) {
		return StringBundler.concat(
			_getReportHeader(key), StringPool.COLON, StringPool.SPACE, value);
	}

	private String _getRootDir(String dlStoreConfigurationPid) {
		try {
			PersistenceManager persistenceManager =
				_persistenceManagerSnapshot.get();

			Dictionary<String, String> configurations = persistenceManager.load(
				dlStoreConfigurationPid);

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

	private void _printToLogContext(Map<String, Object> reportData) {
		if (!PropsValues.UPGRADE_LOG_CONTEXT_ENABLED) {
			return;
		}

		_logContext = true;

		try {
			for (Map.Entry<String, Object> entry1 : reportData.entrySet()) {
				String key = "upgrade.report." + entry1.getKey();

				Object value = entry1.getValue();

				if (value instanceof Map<?, ?>) {
					Map<?, ?> map = (Map<?, ?>)value;

					for (Map.Entry<?, ?> entry2 : map.entrySet()) {
						ThreadContext.put(
							key + StringPool.PERIOD + entry2.getKey(),
							String.valueOf(entry2.getValue()));
					}
				}
				else {
					ThreadContext.put(key, String.valueOf(value));
				}
			}
		}
		finally {
			_logContext = false;
		}
	}

	private void _writeToFile(Map<String, Object> reportData) {
		StringBundler sb = new StringBundler();

		for (Map.Entry<String, Object> entry1 : reportData.entrySet()) {
			String key = entry1.getKey();
			Object value = entry1.getValue();

			if (value instanceof List<?>) {
				String reportHeader = _getReportHeader(key);

				sb.append(reportHeader);

				List<Object> objects = (List<Object>)value;

				if (objects.isEmpty()) {
					sb.append(": Nothing registered");
					sb.append(StringPool.NEW_LINE);
				}
				else {
					sb.append(StringPool.NEW_LINE);
					sb.append(
						ListUtil.toString(
							Collections.nCopies(
								reportHeader.length(), StringPool.MINUS),
							StringPool.NULL, StringPool.BLANK));
					sb.append(StringPool.NEW_LINE);

					for (Object object : (List<Object>)value) {
						sb.append(object.toString());
						sb.append(StringPool.NEW_LINE);
					}
				}
			}
			else if (value instanceof Map<?, ?>) {
				Map<?, ?> map = (Map<?, ?>)value;

				for (Map.Entry<?, ?> entry2 : map.entrySet()) {
					sb.append(
						_getReportLine(
							key + StringPool.PERIOD + entry2.getKey(),
							entry2.getValue()));
					sb.append(StringPool.NEW_LINE);
				}
			}
			else {
				sb.append(_getReportLine(key, value));
				sb.append(StringPool.NEW_LINE);
			}

			sb.append(StringPool.NEW_LINE);
		}

		File reportFile = null;

		try {
			reportFile = _getReportFile();

			FileUtil.write(
				reportFile,
				StringUtil.merge(
					new String[] {sb.toString()},
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

	private static final String _CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"AdvancedFileSystemStoreConfiguration";

	private static final String _CONFIGURATION_PID_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"FileSystemStoreConfiguration";

	private static final int _UPGRADE_PROCESSES_COUNT = 20;

	private static final Log _log = LogFactoryUtil.getLog(UpgradeReport.class);

	private static boolean _logContext;
	private static final Snapshot<PersistenceManager>
		_persistenceManagerSnapshot = new Snapshot<>(
			UpgradeReport.class, PersistenceManager.class);
	private static final Snapshot<ReleaseManager> _releaseManagerSnapshot =
		new Snapshot<>(UpgradeReport.class, ReleaseManager.class);

	private double _dlSize;
	private final Thread _dlSizeThread = new DLSizeThread();
	private final int _initialBuildNumber;
	private final Map<String, Integer> _initialTableCounts;
	private String _rootDir;

	private class DLSizeThread extends Thread {

		@Override
		public void run() {
			_dlSize = FileUtils.sizeOfDirectory(new File(_rootDir));
		}

	}

	private class MessagesPrinter {

		public MessagesPrinter(String className) {
			_className = className;
		}

		public void addMessagePrinter(String message, int occurrences) {
			_messagePrinters.add(new MessagePrinter(message, occurrences));
		}

		@Override
		public String toString() {
			if (_logContext) {
				return _className + StringPool.COLON +
					_messagePrinters.toString();
			}

			StringBundler sb = new StringBundler();

			sb.append("Class name: ");
			sb.append(_className);
			sb.append(StringPool.NEW_LINE);

			for (MessagePrinter messagePrinter : _messagePrinters) {
				sb.append(StringPool.TAB);
				sb.append(messagePrinter.toString());
				sb.append(StringPool.NEW_LINE);
			}

			return sb.toString();
		}

		private final String _className;
		private final List<MessagePrinter> _messagePrinters = new ArrayList<>();

		private class MessagePrinter {

			public MessagePrinter(String message, int occurrences) {
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

			private final String _message;
			private final int _occurrences;

		}

	}

	private class RunningUpgradeProcess {

		public RunningUpgradeProcess(
			String timeDescription, String upgradeProcessClassName) {

			_timeDescription = timeDescription;
			_upgradeProcessClassName = upgradeProcessClassName;
		}

		@Override
		public String toString() {
			if (_logContext) {
				return StringBundler.concat(
					_upgradeProcessClassName, StringPool.COLON,
					_timeDescription, " ms");
			}

			return StringBundler.concat(
				StringPool.TAB, _upgradeProcessClassName, " took ",
				_timeDescription, " ms to complete");
		}

		private final String _timeDescription;
		private final String _upgradeProcessClassName;

	}

	private class TablePrinter {

		public static final String FORMAT = "%-30s %20s %20s";

		public TablePrinter(
			String finalTableCount, String initialTableCount,
			String tableName) {

			_finalTableCount = finalTableCount;
			_initialTableCount = initialTableCount;
			_tableName = tableName;
		}

		@Override
		public String toString() {
			if (_logContext) {
				return StringBundler.concat(
					_tableName, StringPool.COLON, _initialTableCount,
					StringPool.COLON, _finalTableCount);
			}

			return String.format(
				FORMAT, _tableName, _initialTableCount, _finalTableCount);
		}

		private final String _finalTableCount;
		private final String _initialTableCount;
		private final String _tableName;

	}

}