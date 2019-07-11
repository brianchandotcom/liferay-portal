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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;

import java.io.IOException;

import java.lang.reflect.Constructor;

import java.net.URL;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.collections.IteratorUtils;

import org.elasticsearch.Version;
import org.elasticsearch.cli.Terminal;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.PluginCli;
import org.elasticsearch.plugins.PluginInfo;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 */
public class PluginManagerImpl implements PluginManager {

	public PluginManagerImpl(Environment environment, URL url) {
		_environment = environment;
		_url = url;
	}

	@Override
	public Path[] getInstalledPluginsPaths() throws IOException {
		if (!Files.exists(_environment.pluginsFile())) {
			return new Path[0];
		}

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				_environment.pluginsFile())) {

			return (Path[])IteratorUtils.toArray(
				directoryStream.iterator(), Path.class);
		}
	}

	@Override
	public void install(String name) throws Exception {
		Settings settings = _environment.settings();

		String[] args = {
			"install", "file://" + _url.getPath(),
			"-Epath.home=" + settings.get("path.home"), "--batch", "--silent"
		};

		try {
			PluginJarConflictCheckSuppression.execute(
				() -> {
					try {
						main(args);
					}
					catch (Exception e) {
						throw new SystemException(e);
					}
				});
		}
		catch (SystemException se) {
			throw (Exception)se.getCause();
		}
	}

	@Override
	public boolean isCurrentVersion(Path path) throws IOException {
		try {
			PluginInfo pluginInfo = PluginInfo.readFromProperties(path);

			Version pluginVersion = pluginInfo.getElasticsearchVersion();

			return pluginVersion.equals(Version.CURRENT);
		}
		catch (IllegalArgumentException iae) {
			String message = iae.getMessage();

			if ((message != null) && message.contains("designed for version")) {
				return false;
			}

			throw iae;
		}
	}

	@Override
	public void remove(String name) throws Exception {
		Settings settings = _environment.settings();

		String[] args = {
			"remove", name, "-Epath.home=" + settings.get("path.home"),
			"--silent"
		};

		main(args);
	}

	protected void main(String... args) throws Exception {
		String key = "es.path.conf";

		String value = System.getProperty(key);

		if (value == null) {
			System.setProperty(key, StringPool.BLANK);
		}

		Constructor<PluginCli> constructor =
			PluginCli.class.getDeclaredConstructor();

		constructor.setAccessible(true);

		PluginCli pluginCli = constructor.newInstance();

		pluginCli.main(args, Terminal.DEFAULT);
	}

	private final Environment _environment;
	private final URL _url;

}