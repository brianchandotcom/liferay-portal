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

import com.liferay.portal.search.elasticsearch.internal.util.ResourceUtil;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import org.elasticsearch.common.cli.Terminal;
import org.elasticsearch.common.cli.Terminal.Verbosity;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.PluginManager;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 */
public class EmbeddedElasticsearchPluginManager {

	public static void installPlugin(
			String pluginName, String resourceName, Class<?> clazz,
			Settings settings)
		throws IOException {

		File file = new File(settings.get("path.plugins"));

		file.mkdirs();

		File tempFile = ResourceUtil.getResourceAsTempFile(clazz, resourceName);

		try {
			URI uri = tempFile.toURI();

			URL url = uri.toURL();

			PluginManager pluginManager = new PluginManager(
				new Environment(settings), url, PluginManager.OutputMode.SILENT,
				TimeValue.timeValueMinutes(1));

			Terminal terminal = Terminal.DEFAULT;

			terminal.verbosity(Verbosity.SILENT);

			pluginManager.removePlugin(pluginName, terminal);

			pluginManager.downloadAndExtract(pluginName, terminal, true);
		}
		finally {
			tempFile.delete();
		}
	}

}