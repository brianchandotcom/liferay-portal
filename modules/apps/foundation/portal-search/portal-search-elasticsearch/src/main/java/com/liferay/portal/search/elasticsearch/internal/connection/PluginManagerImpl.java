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

package com.liferay.portal.search.elasticsearch.internal.connection;

import java.io.IOException;

import java.net.URL;

import java.nio.file.Path;

import org.elasticsearch.common.cli.Terminal;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.PluginInfo;
import org.elasticsearch.plugins.PluginManager.OutputMode;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 */
public class PluginManagerImpl implements PluginManager {

	public PluginManagerImpl(
		Environment environment, URL url, OutputMode outputMode,
		TimeValue timeout) {

		_pluginManager = new org.elasticsearch.plugins.PluginManager(
			environment, url, outputMode, timeout);
	}

	@Override
	public void downloadAndExtract(
			String name, Terminal terminal, boolean batch)
		throws IOException {

		_pluginManager.downloadAndExtract(name, terminal, batch);
	}

	@Override
	public Path[] getInstalledPluginsPaths() throws IOException {
		return _pluginManager.getListInstalledPlugins();
	}

	@Override
	public boolean isCurrentVersion(Path path) throws IOException {
		try {
			PluginInfo.readFromProperties(path);

			return true;
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
	public void removePlugin(String name, Terminal terminal)
		throws IOException {

		_pluginManager.removePlugin(name, terminal);
	}

	private final org.elasticsearch.plugins.PluginManager _pluginManager;

}