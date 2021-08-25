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

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		Map<String, Integer> errorMessages = _errorMessages.computeIfAbsent(
			loggerName, key -> new ConcurrentHashMap<>());

		int occurrences = errorMessages.computeIfAbsent(message, key -> 1);

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

		int occurrences = warningMessages.computeIfAbsent(message, key -> 1);

		occurrences++;

		warningMessages.put(message, occurrences);
	}

	public void generateReport() {
		try {
			FileUtil.write(
				_getReportFile(),
				StringBundler.concat(
					_getPortalVersions(), _getDialectInfo(), _getProperties(),
					_getDocLibSize(), _getLogEvents("errors"),
					_getLogEvents("warnings")));
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
		DB db = DBManagerUtil.getDB();

		return StringBundler.concat(
			"Using ", db.getDBType(), " version ", db.getMajorVersion(),
			StringPool.PERIOD, db.getMinorVersion(), StringPool.NEW_LINE);
	}

	private String _getDocLibSize() {
		if (_dlStore.equals("com.liferay.portal.store.db.DBStore")) {
			return StringPool.BLANK;
		}
		else if (_dlStore.contains("FileSystemStore")) {
			if ((_rootDir == null) &&
				_dlStore.equals(
					"com.liferay.portal.store.file.system." +
						"AdvancedFileSystemStore")) {

				return StringBundler.concat(
					"\"rootDir\" was not set, unable to determine the size of ",
					"the document library", StringPool.NEW_LINE);
			}

			final AtomicLong length = new AtomicLong(0);

			String docLibPath =
				PropsValues.LIFERAY_HOME + "/data/document_library";

			if (_rootDir != null) {
				docLibPath = _rootDir + "/data/document_library";
			}

			try {
				Files.walkFileTree(
					Paths.get(docLibPath),
					new SimpleFileVisitor<Path>() {

						@Override
						public FileVisitResult postVisitDirectory(
							Path dir, IOException ioException) {

							if ((ioException != null) &&
								_log.isDebugEnabled()) {

								_log.debug(
									StringBundler.concat(
										"Unable to traverse: ", dir, " (",
										ioException,
										StringPool.CLOSE_PARENTHESIS));
							}

							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFile(
							Path file,
							BasicFileAttributes basicFileAttributes) {

							length.addAndGet(basicFileAttributes.size());

							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(
							Path file, IOException ioException) {

							if (_log.isDebugEnabled()) {
								_log.debug(
									StringBundler.concat(
										"Unable to visit: ", file, " (",
										ioException,
										StringPool.CLOSE_PARENTHESIS));
							}

							return FileVisitResult.CONTINUE;
						}

					});
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to determine the size of the document library");
				}
			}

			double bytes = length.get();

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

			return StringBundler.concat(
				"The Document Library is ", size, StringPool.NEW_LINE);
		}

		return "Please check the size of the Liferay Document Library in " +
			"your cloud storage";
	}

	private String _getLogEvents(String type) {
		StringBundler sb = new StringBundler();

		sb.append(StringUtil.upperCaseFirstLetter(type));
		sb.append(" thrown during upgrade process");
		sb.append(StringPool.NEW_LINE);

		Set<Map.Entry<String, Map<String, Integer>>> entrySet;

		if (type.equals("errors")) {
			entrySet = _errorMessages.entrySet();
		}
		else {
			entrySet = _warningMessages.entrySet();
		}

		Stream<Map.Entry<String, Map<String, Integer>>> entrySetStream =
			entrySet.stream();

		Map<String, Map<String, Integer>> sortedErrors = entrySetStream.sorted(
			Collections.reverseOrder(
				Map.Entry.comparingByValue(
					new Comparator<Map<String, Integer>>() {

						@Override
						public int compare(
							Map<String, Integer> o1, Map<String, Integer> o2) {

							return Integer.compare(o1.size(), o2.size());
						}

					}))
		).collect(
			Collectors.toMap(
				Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
				LinkedHashMap::new)
		);

		for (Map.Entry<String, Map<String, Integer>> entry :
				sortedErrors.entrySet()) {

			sb.append("ClassName: ");
			sb.append(entry.getKey());
			sb.append(StringPool.NEW_LINE);

			Map<String, Integer> submap = entry.getValue();

			Set<Map.Entry<String, Integer>> submapEntrySet = submap.entrySet();

			Stream<Map.Entry<String, Integer>> submapEntrySetStream =
				submapEntrySet.stream();

			Map<String, Integer> submapSorted = submapEntrySetStream.sorted(
				Collections.reverseOrder(
					Map.Entry.comparingByValue(
						new Comparator<Integer>() {

							@Override
							public int compare(Integer o1, Integer o2) {
								return Integer.compare(o1, o2);
							}

						}))
			).collect(
				Collectors.toMap(
					Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
					LinkedHashMap::new)
			);

			for (Map.Entry<String, Integer> valueEntry :
					submapSorted.entrySet()) {

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

		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private String _getPortalVersions() {
		Version latestSchemaVersion =
			PortalUpgradeProcess.getLatestSchemaVersion();

		return StringBundler.concat(
			_getReleaseInfo(
				_initialBuildNumber, _initialSchemaVersion, "initial"),
			StringPool.NEW_LINE,
			_getReleaseInfo(_getBuildNumber(), _getSchemaVersion(), "final"),
			StringPool.NEW_LINE,
			_getReleaseInfo(
				ReleaseInfo.getBuildNumber(), latestSchemaVersion.toString(),
				"expected"),
			StringPool.NEW_LINE);
	}

	private String _getProperties() {
		StringBuffer sb = new StringBuffer(12);

		sb.append("liferay.home=" + PropsValues.LIFERAY_HOME);
		sb.append("\nlocales=" + Arrays.toString(PropsValues.LOCALES));
		sb.append(
			"\nlocales.enabled=" +
				Arrays.toString(PropsValues.LOCALES_ENABLED));
		sb.append(StringPool.NEW_LINE);

		_dlStore = PropsValues.DL_STORE_IMPL;

		sb.append(PropsKeys.DL_STORE_IMPL + StringPool.EQUAL + _dlStore);

		sb.append(StringPool.NEW_LINE);

		if (_dlStore.equals(
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
		else if (_dlStore.equals(
					"com.liferay.portal.store.file.system.FileSystemStore")) {

			_rootDir = _getRootDir(_CONFIGURATION_PID_FILE_SYSTEM_STORE);

			if (_rootDir == null) {
				sb.append("Using the default directory because the ");
				sb.append("configuration \"rootDir\" was not set");
			}
		}

		if (_rootDir != null) {
			sb.append("rootDir=" + _rootDir);
		}

		sb.append(StringPool.NEW_LINE);

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
				_log.warn("Unable to get document library store root dir");
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

	private static final String _CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"AdvancedFileSystemStoreConfiguration";

	private static final String _CONFIGURATION_PID_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"FileSystemStoreConfiguration";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeReport.class);

	private String _dlStore;
	private final Map<String, Map<String, Integer>> _errorMessages =
		new ConcurrentHashMap<>();
	private final Map<String, ArrayList<String>> _eventMessages =
		new ConcurrentHashMap<>();
	private final int _initialBuildNumber;
	private final String _initialSchemaVersion;
	private final PersistenceManager _persistenceManager;
	private String _rootDir;
	private final Map<String, Map<String, Integer>> _warningMessages =
		new ConcurrentHashMap<>();

}