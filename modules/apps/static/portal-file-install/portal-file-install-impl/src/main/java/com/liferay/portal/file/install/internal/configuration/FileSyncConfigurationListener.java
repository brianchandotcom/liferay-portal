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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.file.install.internal.DirectoryWatcher;
import com.liferay.portal.file.install.internal.FileInstallImplBundleActivator;
import com.liferay.portal.file.install.internal.properties.TypedProperties;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

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

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;

/**
 * @author Shuyang Zhou
 */
public class FileSyncConfigurationListener implements ConfigurationListener {

	public FileSyncConfigurationListener(
		ConfigurationAdmin configurationAdmin,
		FileInstallImplBundleActivator fileInstallImplBundleActivator,
		String encoding) {

		_configurationAdmin = configurationAdmin;
		_fileInstallImplBundleActivator = fileInstallImplBundleActivator;
		_encoding = encoding;

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

					_fileInstallImplBundleActivator.updateChecksum(file);
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

	private File _fromConfigKey(String key) {
		return new File(URI.create(key));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileSyncConfigurationListener.class);

	private final ConfigurationAdmin _configurationAdmin;
	private final String _encoding;
	private final FileInstallImplBundleActivator
		_fileInstallImplBundleActivator;
	private final Map<String, String> _pidToFile = new HashMap<>();

}