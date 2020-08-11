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

package com.liferay.portal.file.install.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.internal.properties.TypedProperties;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;

/**
 * @author Matthew Tambara
 */
public class ConfigInstaller implements ConfigurationListener, FileInstaller {

	public ConfigInstaller(
		BundleContext bundleContext, ConfigurationAdmin configurationAdmin,
		FileInstallImplBundleActivator fileInstall) {

		_bundleContext = bundleContext;
		_configurationAdmin = configurationAdmin;
		_fileInstall = fileInstall;

		String encoding = _bundleContext.getProperty(
			DirectoryWatcher.CONFIG_ENCODING);

		if (encoding == null) {
			encoding = StringPool.UTF8;
		}

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
	public void configurationEvent(ConfigurationEvent configurationEvent) {
		int type = configurationEvent.getType();

		if (type == ConfigurationEvent.CM_UPDATED) {
			try {
				Configuration configuration =
					_configurationAdmin.getConfiguration(
						configurationEvent.getPid(), StringPool.QUESTION);

				Dictionary<String, Object> dictionary =
					configuration.getProperties();

				String fileName = null;

				if (dictionary != null) {
					fileName = (String)dictionary.get(
						DirectoryWatcher.FILENAME);
				}

				File file = null;

				if (fileName != null) {
					file = _fromConfigKey(fileName);
				}

				if ((file != null) && file.isFile()) {
					_pidToFile.put(configuration.getPid(), fileName);
					TypedProperties typedProperties = new TypedProperties();

					try (InputStream inputStream = new FileInputStream(file);
						Reader reader = new InputStreamReader(
							inputStream, _encoding)) {

						typedProperties.load(reader);
					}

					List<String> propertiesToRemove = new ArrayList<>();

					for (String key : typedProperties.keySet()) {
						if ((dictionary.get(key) == null) &&
							!Objects.equals(Constants.SERVICE_PID, key) &&
							!Objects.equals(
								ConfigurationAdmin.SERVICE_FACTORYPID, key) &&
							!Objects.equals(DirectoryWatcher.FILENAME, key)) {

							propertiesToRemove.add(key);
						}
					}

					Enumeration<String> enumeration = dictionary.keys();

					while (enumeration.hasMoreElements()) {
						String key = enumeration.nextElement();

						if (!Objects.equals(Constants.SERVICE_PID, key) &&
							!Objects.equals(
								ConfigurationAdmin.SERVICE_FACTORYPID, key) &&
							!Objects.equals(DirectoryWatcher.FILENAME, key)) {

							Object value = dictionary.get(key);

							typedProperties.put(key, value);
						}
					}

					for (String key : propertiesToRemove) {
						typedProperties.remove(key);
					}

					try (OutputStream outputStream = new FileOutputStream(file);
						Writer writer = new OutputStreamWriter(
							outputStream, _encoding)) {

						typedProperties.save(writer);
					}

					_fileInstall.updateChecksum(file);
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to save configuration", exception);
				}
			}
		}
		else if (type == ConfigurationEvent.CM_DELETED) {
			try {
				String fileName = _pidToFile.remove(
					configurationEvent.getPid());

				File file = null;

				if (fileName != null) {
					file = _fromConfigKey(fileName);
				}

				if ((file != null) && file.isFile() && !file.delete()) {
					throw new IOException("Unable to delete file: " + file);
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to delete configuration file", exception);
				}
			}
		}
	}

	public void destroy() {
		_serviceRegistration.unregister();
	}

	public void init() {
		_serviceRegistration = _bundleContext.registerService(
			new String[] {
				ConfigurationListener.class.getName(),
				FileInstaller.class.getName()
			},
			this, null);

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(null);

			if (configurations != null) {
				for (Configuration configuration : configurations) {
					Dictionary<String, Object> dictionary =
						configuration.getProperties();

					String fileName = null;

					if (dictionary != null) {
						fileName = (String)dictionary.get(
							DirectoryWatcher.FILENAME);
					}

					if (fileName != null) {
						_pidToFile.put(configuration.getPid(), fileName);
					}
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to initialize configurations list", exception);
			}
		}
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

	private File _fromConfigKey(String key) {
		return new File(URI.create(key));
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
		ConfigInstaller.class);

	private final BundleContext _bundleContext;
	private final ConfigurationAdmin _configurationAdmin;
	private final String _encoding;
	private final FileInstallImplBundleActivator _fileInstall;
	private final Map<String, String> _pidToFile = new HashMap<>();
	private ServiceRegistration<?> _serviceRegistration;

}