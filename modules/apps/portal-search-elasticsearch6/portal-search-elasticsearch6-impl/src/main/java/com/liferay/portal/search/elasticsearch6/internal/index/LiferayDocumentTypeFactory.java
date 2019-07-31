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

package com.liferay.portal.search.elasticsearch6.internal.index;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch6.internal.settings.SettingsBuilder;
import com.liferay.portal.search.elasticsearch6.internal.util.LogUtil;
import com.liferay.portal.search.elasticsearch6.internal.util.ResourceUtil;
import com.liferay.portal.search.elasticsearch6.settings.TypeMappingsHelper;

import java.util.LinkedHashMap;
import java.util.Map;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * @author André de Oliveira
 */
public class LiferayDocumentTypeFactory implements TypeMappingsHelper {

	public LiferayDocumentTypeFactory(
		IndicesAdminClient indicesAdminClient, JSONFactory jsonFactory) {

		_indicesAdminClient = indicesAdminClient;
		_jsonFactory = jsonFactory;
	}

	@Override
	public void addTypeMappings(String indexName, String source) {
		PutMappingRequestBuilder putMappingRequestBuilder =
			_indicesAdminClient.preparePutMapping(indexName);

		putMappingRequestBuilder.setSource(
			mergeDynamicTemplates(
				source, indexName,
				LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE),
			XContentType.JSON);
		putMappingRequestBuilder.setType(
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		ActionResponse actionResponse = putMappingRequestBuilder.get();

		LogUtil.logActionResponse(_log, actionResponse);
	}

	public void createLiferayDocumentTypeMappings(
		CreateIndexRequestBuilder createIndexRequestBuilder, String mappings) {

		createIndexRequestBuilder.addMapping(
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
		CreateIndexRequestBuilder createIndexRequestBuilder) {

		String requiredDefaultMappings = ResourceUtil.getResourceAsString(
			getClass(),
			LiferayTypeMappingsConstants.
				LIFERAY_DOCUMENT_TYPE_MAPPING_FILE_NAME);

		createLiferayDocumentTypeMappings(
			createIndexRequestBuilder, requiredDefaultMappings);
	}

	protected JSONObject createJSONObject(String mappings) {
		try {
			return _jsonFactory.createJSONObject(mappings);
		}
		catch (JSONException jsone) {
			throw new RuntimeException(jsone);
		}
	}

	protected String getMappings(String indexName, String typeName) {
		GetMappingsRequestBuilder getMappingsRequestBuilder =
			_indicesAdminClient.prepareGetMappings(indexName);

		getMappingsRequestBuilder.setTypes(typeName);

		GetMappingsResponse getMappingsResponse =
			getMappingsRequestBuilder.get();

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>>
			map = getMappingsResponse.mappings();

		ImmutableOpenMap<String, MappingMetaData> mappings = map.get(indexName);

		MappingMetaData mappingMetaData = mappings.get(typeName);

		CompressedXContent compressedXContent = mappingMetaData.source();

		return compressedXContent.toString();
	}

	protected JSONArray merge(JSONArray jsonArray1, JSONArray jsonArray2) {
		LinkedHashMap<String, JSONObject> linkedHashMap = new LinkedHashMap<>();

		putAll(linkedHashMap, jsonArray1);

		putAll(linkedHashMap, jsonArray2);

		JSONArray jsonArray3 = _jsonFactory.createJSONArray();

		linkedHashMap.forEach((key, value) -> jsonArray3.put(value));

		return jsonArray3;
	}

	protected String mergeDynamicTemplates(
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

		String mappings = getMappings(indexName, typeName);

		JSONObject mappingsJSONObject = createJSONObject(mappings);

		JSONObject typeJSONObject = mappingsJSONObject.getJSONObject(typeName);

		JSONArray typeTemplatesJSONArray = typeJSONObject.getJSONArray(
			"dynamic_templates");

		sourceTypeJSONObject.put(
			"dynamic_templates",
			merge(typeTemplatesJSONArray, sourceTypeTemplatesJSONArray));

		return sourceJSONObject.toString();
	}

	protected void putAll(Map<String, JSONObject> map, JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray namesJSONArray = jsonObject.names();

			String name = (String)namesJSONArray.get(0);

			map.put(name, jsonObject);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayDocumentTypeFactory.class);

	private final IndicesAdminClient _indicesAdminClient;
	private final JSONFactory _jsonFactory;

}