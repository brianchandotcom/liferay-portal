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
import com.liferay.portal.search.elasticsearch.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch.settings.TypeMappingsHelper;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class LiferayDocumentTypeFactory implements TypeMappingsHelper {

	public LiferayDocumentTypeFactory(IndicesAdminClient indicesAdminClient) {
		_indicesAdminClient = indicesAdminClient;
	}

	@Override
	public void addTypeMappings(String indexName, String source) {
		PutMappingRequestBuilder putMappingRequestBuilder =
			_indicesAdminClient.preparePutMapping(indexName);

		putMappingRequestBuilder.setSource(source);
		putMappingRequestBuilder.setType(
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		PutMappingResponse putMappingResponse = putMappingRequestBuilder.get();

		try {
			LogUtil.logActionResponse(_log, putMappingResponse);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void createLiferayDocumentTypeMappings(
		CreateIndexRequestBuilder createIndexRequestBuilder, String mappings) {

		createIndexRequestBuilder.addMapping(
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE, mappings);
	}

	public void createOptionalDefaultTypeMappings(String indexName) {
		String name = StringUtil.replace(
			LiferayTypeMappingsConstants.
				LIFERAY_DOCUMENT_TYPE_MAPPING_FILE_NAME,
			".json", "-optional-defaults.json");

		String optionalDefaultTypeMappings = ResourceUtil.getResourceAsString(
			getClass(), name);

		addTypeMappings(indexName, optionalDefaultTypeMappings);
	}

	public void createRequiredDefaultAnalyzers(Settings.Builder builder) {
		String requiredDefaultAnalyzers = ResourceUtil.getResourceAsString(
			getClass(), IndexSettingsConstants.INDEX_SETTINGS_FILE_NAME);

		builder.loadFromSource(requiredDefaultAnalyzers);
	}

	public void createRequiredDefaultTypeMappings(
		CreateIndexRequestBuilder createIndexRequestBuilder) {

		String requiredDefaultMappings = ResourceUtil.getResourceAsString(
			getClass(),
			LiferayTypeMappingsConstants.
				LIFERAY_DOCUMENT_TYPE_MAPPING_FILE_NAME);

		createLiferayDocumentTypeMappings(
			createIndexRequestBuilder, requiredDefaultMappings);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayDocumentTypeFactory.class);

	private final IndicesAdminClient _indicesAdminClient;

}