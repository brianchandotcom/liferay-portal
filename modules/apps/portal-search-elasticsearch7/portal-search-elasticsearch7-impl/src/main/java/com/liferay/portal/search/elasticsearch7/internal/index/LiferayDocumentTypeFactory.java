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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.IndexSettingsConstants;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.LiferayTypeMappingsConstants;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch7.settings.TypeMappingsHelper;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentType;

/**
 * @author André de Oliveira
 */
public class LiferayDocumentTypeFactory
	implements com.liferay.portal.search.spi.settings.TypeMappingsHelper,
			   TypeMappingsHelper {

	public LiferayDocumentTypeFactory(
		IndicesClient indicesClient, JSONFactory jsonFactory) {

		_indicesClient = indicesClient;
		_jsonFactory = jsonFactory;
	}

	@Override
	public void addTypeMappings(String indexName, String source) {
		PutMappingRequest putMappingRequest = new PutMappingRequest(indexName);

		putMappingRequest.source(
			_mergeDynamicTemplates(
				source, indexName,
				LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE),
			XContentType.JSON);

		try {
			ActionResponse actionResponse = _indicesClient.putMapping(
				putMappingRequest, RequestOptions.DEFAULT);

			SearchLogHelperUtil.logActionResponse(_log, actionResponse);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public void createLiferayDocumentTypeMappings(
		CreateIndexRequest createIndexRequest, String mappings) {

		createIndexRequest.mapping(
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE, mappings,
			XContentType.JSON);
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
		SettingsBuilder settingsBuilder = new SettingsBuilder(builder);

		String requiredDefaultAnalyzers = ResourceUtil.getResourceAsString(
			getClass(), IndexSettingsConstants.INDEX_SETTINGS_FILE_NAME);

		settingsBuilder.loadFromSource(requiredDefaultAnalyzers);
	}

	public void createRequiredDefaultTypeMappings(
		CreateIndexRequest createIndexRequest) {

		String requiredDefaultMappings = ResourceUtil.getResourceAsString(
			getClass(),
			LiferayTypeMappingsConstants.
				LIFERAY_DOCUMENT_TYPE_MAPPING_FILE_NAME);

		createLiferayDocumentTypeMappings(
			createIndexRequest, requiredDefaultMappings);
	}

	protected JSONObject createJSONObject(String mappings) {
		try {
			return _jsonFactory.createJSONObject(mappings);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	protected String getMappings(String indexName, String typeName) {
		GetMappingsRequest getMappingsRequest = new GetMappingsRequest();

		getMappingsRequest.indices(indexName);

		GetMappingsResponse getMappingsResponse = null;

		try {
			getMappingsResponse = _indicesClient.getMapping(
				getMappingsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		Map<String, MappingMetadata> mappings = getMappingsResponse.mappings();

		MappingMetadata mappingMetadata = mappings.get(indexName);

		CompressedXContent compressedXContent = mappingMetadata.source();

		return compressedXContent.toString();
	}

	private JSONArray _merge(JSONArray jsonArray1, JSONArray jsonArray2) {
		LinkedHashMap<String, JSONObject> linkedHashMap = new LinkedHashMap<>();

		_putAll(linkedHashMap, jsonArray1);

		_putAll(linkedHashMap, jsonArray2);

		JSONArray jsonArray3 = _jsonFactory.createJSONArray();

		JSONObject defaultTemplateJSONObject = null;

		for (Map.Entry<String, JSONObject> entry : linkedHashMap.entrySet()) {
			String key = entry.getKey();

			if (key.equals("template_")) {
				defaultTemplateJSONObject = entry.getValue();
			}
			else {
				jsonArray3.put(entry.getValue());
			}
		}

		if (defaultTemplateJSONObject != null) {
			jsonArray3.put(defaultTemplateJSONObject);
		}

		return jsonArray3;
	}

	private String _mergeDynamicTemplates(
		String source, String indexName, String typeName) {

		JSONObject sourceJSONObject = createJSONObject(source);

		JSONObject sourceTypeJSONObject = sourceJSONObject;

		if (sourceJSONObject.has(typeName)) {
			sourceTypeJSONObject = sourceJSONObject.getJSONObject(typeName);
		}

		JSONArray sourceTypeTemplatesJSONArray =
			sourceTypeJSONObject.getJSONArray("dynamic_templates");

		if (sourceTypeTemplatesJSONArray == null) {
			return sourceJSONObject.toString();
		}

		JSONObject mappingsJSONObject = createJSONObject(
			getMappings(indexName, typeName));

		JSONObject mappingsTypeJSONObject = mappingsJSONObject;

		if (mappingsJSONObject.has(typeName)) {
			mappingsTypeJSONObject = mappingsJSONObject.getJSONObject(typeName);
		}

		JSONArray typeTemplatesJSONArray = mappingsTypeJSONObject.getJSONArray(
			"dynamic_templates");

		sourceTypeJSONObject.put(
			"dynamic_templates",
			_merge(typeTemplatesJSONArray, sourceTypeTemplatesJSONArray));

		return sourceJSONObject.toString();
	}

	private void _putAll(Map<String, JSONObject> map, JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray namesJSONArray = jsonObject.names();

			String name = (String)namesJSONArray.get(0);

			map.put(name, jsonObject);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayDocumentTypeFactory.class);

	private final IndicesClient _indicesClient;
	private final JSONFactory _jsonFactory;

}