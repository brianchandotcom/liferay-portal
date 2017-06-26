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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

import java.util.Optional;

import org.elasticsearch.Version;
import org.elasticsearch.common.cli.Terminal;
import org.elasticsearch.common.cli.Terminal.Verbosity;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 */
public class EmbeddedElasticsearchPluginManager {

	public EmbeddedElasticsearchPluginManager(
		String pluginName, String pluginsPathString,
		PluginManagerFactory pluginManagerFactory,
		PluginZipFactory pluginZipFactory) {

		_pluginName = pluginName;
		_pluginsPathString = pluginsPathString;
		_pluginManagerFactory = pluginManagerFactory;
		_pluginZipFactory = pluginZipFactory;
	}

	public void install() throws IOException {
		if (isAlreadyInstalled()) {
			return;
		}

		PluginZip pluginZip = createPluginZip();

		try {
			downloadAndExtract(pluginZip);
		}
		finally {
			pluginZip.delete();
		}
	}

	public void removeObsoletePlugin() throws IOException {
		PluginManager pluginManager =
			_pluginManagerFactory.createPluginManager();

		Optional<Path> pathOptional = getInstalledPluginPath(pluginManager);

		if (!pathOptional.isPresent()) {
			return;
		}

		if (pluginManager.isCurrentVersion(pathOptional.get())) {
			return;
		}

		pluginManager.removePlugin(_pluginName, getTerminal());
	}

	protected PluginZip createPluginZip() throws IOException {
		return _pluginZipFactory.createPluginZip(
			"/plugins/" + _pluginName + "-" + Version.CURRENT + ".zip");
	}

	protected void downloadAndExtract(PluginZip pluginZip) throws IOException {
		File file = new File(_pluginsPathString);

		file.mkdirs();

		PluginManager pluginManager = _pluginManagerFactory.createPluginManager(
			pluginZip);

		try {
			pluginManager.downloadAndExtract(_pluginName, getTerminal(), true);
		}
		catch (IOException ioe) {
			if (!handle(ioe)) {
				throw ioe;
			}
		}
	}

	protected Optional<Path> getInstalledPluginPath(PluginManager pluginManager)
		throws IOException {

		Path[] paths = pluginManager.getInstalledPluginsPaths();

		if (paths != null) {
			for (Path path : paths) {
				if (path.endsWith(_pluginName)) {
					return Optional.of(path);
				}
			}
		}

		return Optional.empty();
	}

	protected Terminal getTerminal() {
		Terminal terminal = Terminal.DEFAULT;

		terminal.verbosity(Verbosity.SILENT);

		return terminal;
	}

	protected boolean handle(IOException ioe) {
		String message = ioe.getMessage();

		if (message == null) {
			return false;
		}

		if (message.contains(
				"already exists. To update the plugin, uninstall it first")) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping plugin " + _pluginName +
						" because it is already installed",
					ioe);
			}

			return true;
		}

		return false;
	}

	protected boolean isAlreadyInstalled() throws IOException {
		PluginManager pluginManager =
			_pluginManagerFactory.createPluginManager();

		Optional<Path> pathOptional = getInstalledPluginPath(pluginManager);

		if (pathOptional.isPresent()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchPluginManager.class);

	private final PluginManagerFactory _pluginManagerFactory;
	private final String _pluginName;
	private final String _pluginsPathString;
	private final PluginZipFactory _pluginZipFactory;

}