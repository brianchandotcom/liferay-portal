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

package com.liferay.portal.search.elasticsearch7.internal.settings;

import org.apache.commons.lang.StringUtils;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsException;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @author André de Oliveira
 */
public class SettingsBuilder {

	public SettingsBuilder(Settings.Builder builder) {
		_builder = builder;
	}

	public Settings build() {
		return _builder.build();
	}

	public Settings.Builder getBuilder() {
		return _builder;
	}

	public void loadFromSource(String source) {
		if (StringUtils.isBlank(source)) {
			return;
		}

		try {
			_builder.loadFromSource(source, XContentType.JSON);
		}
		catch (SettingsException se) {
			_builder.loadFromSource(source, XContentType.YAML);
		}
	}

	public void put(String key, boolean value) {
		_builder.put(key, value);
	}

	public void put(String key, String value) {
		if (!StringUtils.isBlank(value)) {
			_builder.put(key, value);
		}
	}

	public void putList(String setting, String... values) {
		_builder.putList(setting, values);
	}

	private final Settings.Builder _builder;

}