/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * @author István András Dézsi
 */
public class PreupgradeFileSystemStoreVerifyUtil {

	public static String getDictionaryString(
			Connection connection, String configurationPid)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select dictionary from Configuration_ where configurationId " +
					"= ?")) {

			preparedStatement.setString(1, configurationPid);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString(1);
				}
			}
		}

		return StringPool.BLANK;
	}

	public static Path getFileSystemStoreRootDir(Connection connection) {
		String fileSystemStoreRootDirPath = getFileSystemStoreRootDirPath(
			connection);

		if (fileSystemStoreRootDirPath == null) {
			return null;
		}

		Path fileSystemStoreRootDir = Paths.get(fileSystemStoreRootDirPath);

		if (!Files.exists(fileSystemStoreRootDir)) {
			_log.error(
				"File system store root directory does not exist: " +
					fileSystemStoreRootDir);

			return null;
		}

		if (!fileSystemStoreRootDir.isAbsolute()) {
			fileSystemStoreRootDir = Paths.get(
				PropsValues.LIFERAY_HOME, fileSystemStoreRootDirPath);
		}

		return fileSystemStoreRootDir;
	}

	public static String getFileSystemStoreRootDirPath(Connection connection) {
		String configurationPid = null;

		if (StringUtil.equals(
				PropsValues.DL_STORE_IMPL, _ADVANCED_FILE_SYSTEM_STORE)) {

			configurationPid = _CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE;
		}
		else if (StringUtil.equals(
					PropsValues.DL_STORE_IMPL, _FILE_SYSTEM_STORE)) {

			configurationPid = _CONFIGURATION_PID_FILE_SYSTEM_STORE;
		}
		else {
			return null;
		}

		String rootDir = null;

		try {
			Dictionary<String, String> dictionary = parseDictionaryString(
				getDictionaryString(connection, configurationPid));

			rootDir = dictionary.get("rootDir");
		}
		catch (Exception exception) {
			_log.error(
				"Unable to get file system store root directory path",
				exception);

			return null;
		}

		if (Validator.isNull(rootDir)) {
			rootDir = PropsValues.LIFERAY_HOME + "/data/document_library";
		}

		return rootDir;
	}

	public static boolean isFileSystemStore() {
		if (StringUtil.equals(
				PropsValues.DL_STORE_IMPL, _ADVANCED_FILE_SYSTEM_STORE) ||
			StringUtil.equals(PropsValues.DL_STORE_IMPL, _FILE_SYSTEM_STORE)) {

			return true;
		}

		return false;
	}

	public static Dictionary<String, String> parseDictionaryString(
		String dictionaryString) {

		Dictionary<String, String> dictionary = new Hashtable<>();

		if (Validator.isNull(dictionaryString)) {
			return dictionary;
		}

		for (String line : dictionaryString.split("\n")) {
			line = line.trim();

			if (line.isEmpty() || line.startsWith(":")) {
				continue;
			}

			int equalsIndex = line.indexOf('=');

			if (equalsIndex <= 0) {
				continue;
			}

			String key = line.substring(0, equalsIndex);

			String value = line.substring(equalsIndex + 1);

			if (value.startsWith("\"") && value.endsWith("\"") &&
				(value.length() > 1)) {

				value = value.substring(1, value.length() - 1);
			}

			dictionary.put(key, value);
		}

		return dictionary;
	}

	private static final String _ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.AdvancedFileSystemStore";

	private static final String _CONFIGURATION_PID_ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"AdvancedFileSystemStoreConfiguration";

	private static final String _CONFIGURATION_PID_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.configuration." +
			"FileSystemStoreConfiguration";

	private static final String _FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.FileSystemStore";

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeFileSystemStoreVerifyUtil.class);

}