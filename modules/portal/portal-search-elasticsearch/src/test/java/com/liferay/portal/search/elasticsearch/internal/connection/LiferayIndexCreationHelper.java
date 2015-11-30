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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.internal.index.IndexSettingsConstants;
import com.liferay.portal.search.elasticsearch.internal.index.LiferayTypeMappingsConstants;

import java.io.IOException;
import java.io.InputStream;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.common.settings.ImmutableSettings;

/**
 * @author André de Oliveira
 */
public class LiferayIndexCreationHelper implements IndexCreationHelper {

	@Override
	public void contribute(
		CreateIndexRequestBuilder createIndexRequestBuilder) {

		createIndexRequestBuilder.addMapping(
			LiferayTypeMappingsConstants.TYPE,
			read(LiferayTypeMappingsConstants.FILE));

		ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();

		builder.loadFromSource(read(IndexSettingsConstants.FILE));

		createIndexRequestBuilder.setSettings(builder);
	}

	private String read(String file) {
		try (InputStream inputStream =
				LiferayIndexCreationHelper.class.getResourceAsStream(file)) {

			return StringUtil.read(inputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}