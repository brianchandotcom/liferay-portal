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

import com.liferay.portal.search.elasticsearch.settings.ClientSettingsHelper;

import java.util.ArrayList;
import java.util.Collection;

import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class ClientSettingsHelperImpl implements ClientSettingsHelper {

	public ClientSettingsHelperImpl(Settings.Builder builder) {
		_builder = builder;
	}

	@Override
	public void addPlugin(String plugin) {
		_plugins.add(plugin);
	}

	public Collection<String> getPlugins() {
		return _plugins;
	}

	public Settings.Builder getSettingsBuilder() {
		return _builder;
	}

	@Override
	public void put(String setting, String value) {
		_builder.put(setting, value);
	}

	@Override
	public void putArray(String setting, String... values) {
		_builder.putArray(setting, values);
	}

	private final Settings.Builder _builder;
	private final ArrayList<String> _plugins = new ArrayList<>(1);

}