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
import com.liferay.portal.file.install.internal.properties.TypedProperties;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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

		if (name.endsWith(".cfg") || name.endsWith(".config")) {
			return true;
		}

		return false;
	}

	@Override
	public URL transformURL(File file) throws Exception {
		_setConfig(file);

		return null;
	}

	@Override
	public void uninstall(File file) throws Exception {
		String[] pid = _parsePid(file.getName());

		String logString = StringPool.BLANK;

		if (pid[1] != null) {
			logString = StringPool.DASH + pid[1];
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Deleting configuration from ", pid[0], logString, ".cfg"));
		}

		Configuration configuration = _getConfiguration(
			_toConfigKey(file), pid[0], pid[1]);

		configuration.delete();
	}

	private static boolean _equals(
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

		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(DirectoryWatcher.FILENAME);
		sb.append(StringPool.EQUAL);
		sb.append(_escapeFilterValue(fileName));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			sb.toString());

		if ((configurations != null) && (configurations.length > 0)) {
			return configurations[0];
		}

		return null;
	}

	private Configuration _getConfiguration(
			String fileName, String pid, String factoryPid)
		throws Exception {

		Configuration configuration = _findExistingConfiguration(fileName);

		if (configuration != null) {
			return configuration;
		}

		if (factoryPid != null) {
			return _configurationAdmin.createFactoryConfiguration(
				pid, StringPool.QUESTION);
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private String[] _parsePid(String path) {
		String pid = path.substring(0, path.lastIndexOf(CharPool.PERIOD));

		int index = pid.indexOf(CharPool.DASH);

		if (index > 0) {
			String factoryPid = pid.substring(index + 1);

			pid = pid.substring(0, index);

			return new String[] {pid, factoryPid};
		}

		return new String[] {pid, null};
	}

	private boolean _setConfig(File file) throws Exception {
		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		try (InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, _encoding)) {

			TypedProperties typedProperties = new TypedProperties();

			typedProperties.load(reader);

			for (String key : typedProperties.keySet()) {
				dictionary.put(key, typedProperties.get(key));
			}
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

		if (old != null) {
			old.remove(DirectoryWatcher.FILENAME);
			old.remove(Constants.SERVICE_PID);
			old.remove(ConfigurationAdmin.SERVICE_FACTORYPID);
		}

		if (!_equals(dictionary, old)) {
			dictionary.put(DirectoryWatcher.FILENAME, _toConfigKey(file));

			String logString = StringPool.BLANK;

			if (pid[1] != null) {
				logString = StringPool.DASH + pid[1];
			}

			if (old == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Creating configuration from ", pid[0], logString,
							".cfg"));
				}
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Updating configuration from ", pid[0], logString,
							".cfg"));
				}
			}

			configuration.update(dictionary);

			return true;
		}

		return false;
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