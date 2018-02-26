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

import java.net.URL;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

/**
 * @author André de Oliveira
 */
public class PluginManagerFactoryImpl implements PluginManagerFactory {

	public PluginManagerFactoryImpl(Settings settings) {
		_settings = settings;
	}

	@Override
	public PluginManager createPluginManager() {
		return doCreatePluginManager(null);
	}

	@Override
	public PluginManager createPluginManager(PluginZip pluginZip) {
		return doCreatePluginManager(pluginZip.getURL());
	}

	protected PluginManager doCreatePluginManager(URL url) {
		return new PluginManagerImpl(new Environment(_settings, null), url);
	}

	private final Settings _settings;

}