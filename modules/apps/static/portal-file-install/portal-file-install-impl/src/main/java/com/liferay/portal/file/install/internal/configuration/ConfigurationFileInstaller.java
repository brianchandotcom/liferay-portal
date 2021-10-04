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

package com.liferay.portal.file.install.internal.configuration;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.internal.DirectoryWatcher;
import com.liferay.portal.file.install.internal.properties.ConfigurationProperties;
import com.liferay.portal.file.install.internal.properties.ConfigurationPropertiesFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.URI;
import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Matthew Tambara
 */
public class ConfigurationFileInstaller implements FileInstaller {

	public ConfigurationFileInstaller(
		ConfigurationAdmin configurationAdmin, String encoding) {

		_configurationAdmin = configurationAdmin;
		_encoding = encoding;
	}

	@Override
	public boolean canTransformURL(File file) {
		String name = file.getName();

		if (name.endsWith(".config")) {
			return true;
		}
		else if (name.endsWith(".cfg")) {
			if (PropsValues.MODULE_FRAMEWORK_FILE_INSTALL_CFG_ENABLED) {
				return true;
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to install .cfg file " + file +
						", please use .config file instead.");
			}
		}

		return false;
	}

	@Override
	public URL transformURL(File file) throws Exception {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		ConfigurationProperties configurationProperties =
			ConfigurationPropertiesFactory.create(file, _encoding);

		for (String key : configurationProperties.keySet()) {
			dictionary.put(key, configurationProperties.get(key));
		}

		String[] pid = _parsePid(file.getName());

		Configuration configuration = _getConfiguration(
			_toConfigKey(file), pid[0], pid[1]);

		Dictionary<String, Object> properties = configuration.getProperties();

		Dictionary<String, Object> old = null;

		if (properties != null) {
			old = new HashMapDictionary<>();

			Enumeration<String> enumeration = properties.keys();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();

				old.put(key, properties.get(key));
			}
		}

		String oldFileName = null;

		if (old != null) {
			oldFileName = (String)old.remove(DirectoryWatcher.FILENAME);

			old.remove(Constants.SERVICE_PID);
			old.remove(ConfigurationAdmin.SERVICE_FACTORYPID);

			if ((dictionary.get(ConfigurationAdmin.SERVICE_BUNDLELOCATION) ==
					null) &&
				Objects.equals(
					StringPool.QUESTION,
					old.get(ConfigurationAdmin.SERVICE_BUNDLELOCATION))) {

				old.remove(ConfigurationAdmin.SERVICE_BUNDLELOCATION);
			}
		}

		String currentFileName = _toConfigKey(file);

		if (!_equals(dictionary, old) ||
			!Objects.equals(oldFileName, currentFileName)) {

			dictionary.put(DirectoryWatcher.FILENAME, currentFileName);

			String logString = StringPool.BLANK;

			if (pid[1] != null) {
				logString = StringPool.TILDE + pid[1];
			}

			if (old == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Creating configuration from ", pid[0], logString,
							".config"));
				}
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Updating configuration from ", pid[0], logString,
							".config"));
				}
			}

			configuration.update(dictionary);
		}

		return null;
	}

	@Override
	public void uninstall(File file) throws Exception {
		String[] pid = _parsePid(file.getName());

		String logString = StringPool.BLANK;

		if (pid[1] != null) {
			logString = StringPool.TILDE + pid[1];
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Deleting configuration from ", pid[0], logString,
					".config"));
		}

		Configuration configuration = _getConfiguration(
			_toConfigKey(file), pid[0], pid[1]);

		configuration.delete();
	}

	private boolean _equals(
		Dictionary<String, Object> newDictionary,
		Dictionary<String, Object> oldDictionary) {

		if (oldDictionary == null) {
			return false;
		}

		Enumeration<String> enumeration = newDictionary.keys();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			Object newValue = newDictionary.get(key);
			Object oldValue = oldDictionary.remove(key);

			if (!Objects.equals(newValue, oldValue) &&
				!Objects.deepEquals(newValue, oldValue)) {

				return false;
			}
		}

		if (oldDictionary.isEmpty()) {
			return true;
		}

		return false;
	}

	private String _escapeFilterValue(String string) {
		string = StringUtil.replace(string, "[(]", "\\\\(");
		string = StringUtil.replace(string, "[)]", "\\\\)");
		string = StringUtil.replace(string, "[=]", "\\\\=");

		return StringUtil.replace(string, "[\\*]", "\\\\*");
	}

	private Configuration _findExistingConfiguration(String fileName)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, DirectoryWatcher.FILENAME,
				StringPool.EQUAL, _escapeFilterValue(fileName),
				StringPool.CLOSE_PARENTHESIS));

		if ((configurations != null) && (configurations.length > 0)) {
			return configurations[0];
		}

		return null;
	}

	private Configuration _getConfiguration(
			String fileName, String pid, String name)
		throws Exception {

		Configuration configuration = _findExistingConfiguration(fileName);

		if (configuration != null) {
			return configuration;
		}

		if (name != null) {
			return _configurationAdmin.getFactoryConfiguration(
				pid, name, StringPool.QUESTION);
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private String[] _parsePid(String path) {
		String pid = path.substring(0, path.lastIndexOf(CharPool.PERIOD));

		int index = pid.indexOf(CharPool.TILDE);

		if (index <= 0) {
			index = pid.indexOf(CharPool.UNDERLINE);

			if (index <= 0) {
				index = pid.indexOf(CharPool.DASH);
			}
		}

		if (index > 0) {
			String name = pid.substring(index + 1);

			pid = pid.substring(0, index);

			return new String[] {pid, name};
		}

		return new String[] {pid, null};
	}

	private String _toConfigKey(File file) {
		file = file.getAbsoluteFile();

		URI uri = file.toURI();

		return uri.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationFileInstaller.class);

	private final ConfigurationAdmin _configurationAdmin;
	private final String _encoding;

}