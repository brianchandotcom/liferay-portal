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

package com.liferay.portal.search.elasticsearch.internal.index;

import com.liferay.portal.search.elasticsearch.settings.IndexSettingsHelper;

import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class IndexSettingsHelperImpl implements IndexSettingsHelper {

	public IndexSettingsHelperImpl(Settings.Builder builder) {
		_builder = builder;
	}

	@Override
	public void put(String setting, String value) {
		_builder.put(setting, value);
	}

	private final Settings.Builder _builder;

}