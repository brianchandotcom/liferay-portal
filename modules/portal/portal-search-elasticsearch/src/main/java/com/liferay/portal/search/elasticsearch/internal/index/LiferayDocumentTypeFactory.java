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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.internal.util.LogUtil;
import com.liferay.portal.search.elasticsearch.settings.TypeMappingsHelper;

import java.io.IOException;
import java.io.InputStream;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class LiferayDocumentTypeFactory implements TypeMappingsHelper {

	public LiferayDocumentTypeFactory(
		String indexName, IndicesAdminClient indicesAdminClient) {

		_indexName = indexName;
		_indicesAdminClient = indicesAdminClient;
	}

	@Override
	public void addTypeMappings(String source) {
		PutMappingRequestBuilder putMappingRequestBuilder =
			_indicesAdminClient.preparePutMapping(_indexName);

		putMappingRequestBuilder.setSource(source);
		putMappingRequestBuilder.setType(LiferayTypeMappingsConstants.TYPE);

		PutMappingResponse putMappingResponse = putMappingRequestBuilder.get();

		try {
			LogUtil.logActionResponse(_log, putMappingResponse);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void createOptionalDefaultTypeMappings() {
		String name = StringUtil.replace(
			LiferayTypeMappingsConstants.FILE, ".json",
			"-optional-defaults.json");

		addTypeMappings(_read(name));
	}

	public void createRequiredDefaultAnalyzers(Settings.Builder builder) {
		builder.loadFromSource(_read(IndexSettingsConstants.FILE));
	}

	public void createRequiredDefaultTypeMappings(
		CreateIndexRequestBuilder createIndexRequestBuilder) {

		createIndexRequestBuilder.addMapping(
			LiferayTypeMappingsConstants.TYPE,
			_read(LiferayTypeMappingsConstants.FILE));
	}

	private String _read(String name) {
		Class<?> clazz = getClass();

		try (InputStream inputStream = clazz.getResourceAsStream(name)) {
			return StringUtil.read(inputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayDocumentTypeFactory.class);

	private final String _indexName;
	private final IndicesAdminClient _indicesAdminClient;

}