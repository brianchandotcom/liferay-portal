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

package com.liferay.portal.search.elasticsearch6.internal.connection;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

import java.util.Map;

import org.elasticsearch.Version;

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

	public void install() throws Exception {
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

	public void removeObsoletePlugin() throws Exception {
		PluginManager pluginManager =
			_pluginManagerFactory.createPluginManager();

		Path path = getInstalledPluginPath(pluginManager);

		if (path == null) {
			return;
		}

		if (pluginManager.isCurrentVersion(path)) {
			return;
		}

		pluginManager.remove(_pluginName);
	}

	protected PluginZip createPluginZip() throws IOException {
		return _pluginZipFactory.createPluginZip(
			StringBundler.concat(
				"/plugins/", getPluginZipFileName(_pluginName), "-",
				Version.CURRENT, ".zip"));
	}

	protected void downloadAndExtract(PluginZip pluginZip) throws Exception {
		File file = new File(_pluginsPathString);

		file.mkdirs();

		PluginManager pluginManager = _pluginManagerFactory.createPluginManager(
			pluginZip);

		try {
			pluginManager.install(_pluginName);
		}
		catch (IOException ioe) {
			if (!handle(ioe)) {
				throw ioe;
			}
		}
	}

	protected Path getInstalledPluginPath(PluginManager pluginManager)
		throws IOException {

		for (Path path : pluginManager.getInstalledPluginsPaths()) {
			if (path.endsWith(_pluginName)) {
				return path;
			}
		}

		return null;
	}

	protected String getPluginZipFileName(String pluginName) {
		return _pluginZipFileNames.get(pluginName);
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

		Path path = getInstalledPluginPath(pluginManager);

		if (path == null) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbeddedElasticsearchPluginManager.class);

	private final PluginManagerFactory _pluginManagerFactory;
	private final String _pluginName;
	private final String _pluginsPathString;
	private final PluginZipFactory _pluginZipFactory;
	private final Map<String, String> _pluginZipFileNames = HashMapBuilder.put(
		"analysis-icu", "org.elasticsearch.plugin.analysis.icu"
	).put(
		"analysis-kuromoji", "org.elasticsearch.plugin.analysis.kuromoji"
	).put(
		"analysis-smartcn", "org.elasticsearch.plugin.analysis.smartcn"
	).put(
		"analysis-stempel", "org.elasticsearch.plugin.analysis.stempel"
	).build();

}