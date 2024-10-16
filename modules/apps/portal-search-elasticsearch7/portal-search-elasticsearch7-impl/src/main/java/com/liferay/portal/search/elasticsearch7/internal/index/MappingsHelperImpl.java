/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.IndexMappingsConstants;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.spi.index.configuration.contributor.helper.MappingsHelper;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.xcontent.XContentType;

/**
 * @author André de Oliveira
 */
public class MappingsHelperImpl implements MappingsHelper {

	public MappingsHelperImpl(
		String indexName, IndicesClient indicesClient, JSONFactory jsonFactory,
		String overrideMappings,
		SearchEngineInformation searchEngineInformation) {

		_indexName = indexName;
		_indicesClient = indicesClient;
		_jsonFactory = jsonFactory;
		_overrideMappings = overrideMappings;
		_searchEngineInformation = searchEngineInformation;
	}

	public void putDefaultOrOverrideMappings() {
		_putMappings(_getDefaultOrOverrideMappingsJSONObject());
	}

	@Override
	public void putMappings(String source) {
		if (Validator.isNotNull(_overrideMappings)) {
			return;
		}

		_putMappings(
			_getMappingsJSONObjectWithMergedDynamicTemplates(
				_getCurrentMappings(_indexName), source));
	}

	public void setDefaultOrOverrideMappings(
		CreateIndexRequest createIndexRequest) {

		JSONObject mappingsJSONObject =
			_getDefaultOrOverrideMappingsJSONObject();

		createIndexRequest.mapping(
			mappingsJSONObject.toString(), XContentType.JSON);
	}

	private String _addTextEmbeddingDynamicTemplates(String mappings) {
		JSONObject jsonObject = _createJSONObject(mappings);

		JSONArray jsonArray = jsonObject.getJSONArray("dynamic_templates");

		for (int dimension :
				_searchEngineInformation.getEmbeddingVectorDimensions()) {

			jsonArray.put(
				JSONUtil.put(
					"template_text_embedding_" + dimension,
					JSONUtil.put(
						"mapping",
						JSONUtil.put(
							"dims", dimension
						).put(
							"type", "dense_vector"
						)
					).put(
						"path_match",
						StringBundler.concat(
							"text_embedding_", dimension, StringPool.STAR)
					)));
		}

		return jsonObject.toString();
	}

	private JSONObject _createJSONObject(String mappings) {
		try {
			return _jsonFactory.createJSONObject(mappings);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	private String _getCurrentMappings(String indexName) {
		if (Validator.isNotNull(_overrideMappings)) {
			return StringPool.BLANK;
		}

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

	private JSONObject _getDefaultOrOverrideMappingsJSONObject() {
		if (Validator.isNotNull(_overrideMappings)) {
			return _removeLegacyDocumentType(_overrideMappings);
		}

		String defaultMappings = _addTextEmbeddingDynamicTemplates(
			ResourceUtil.getResourceAsString(
				getClass(), IndexMappingsConstants.INDEX_MAPPINGS_FILE_NAME));

		return _getMappingsJSONObjectWithMergedDynamicTemplates(
			StringPool.BLANK, defaultMappings);
	}

	private JSONObject _getMappingsJSONObjectWithMergedDynamicTemplates(
		String currentMappings, String putMappings) {

		JSONObject currentMappingsJSONObject = _removeLegacyDocumentType(
			currentMappings);
		JSONObject putMappingsJSONObject = _removeLegacyDocumentType(
			putMappings);

		putMappingsJSONObject.put(
			"dynamic_templates",
			_mergeDynamicTemplates(
				currentMappingsJSONObject.getJSONArray("dynamic_templates"),
				putMappingsJSONObject.getJSONArray("dynamic_templates")));

		return putMappingsJSONObject;
	}

	private JSONArray _mergeDynamicTemplates(
		JSONArray currentDynamicTemplatesJSONArray,
		JSONArray putDynamicTemplatesJSONArray) {

		if (putDynamicTemplatesJSONArray == null) {
			return currentDynamicTemplatesJSONArray;
		}

		LinkedHashMap<String, JSONObject> linkedHashMap = new LinkedHashMap<>();

		_putAll(linkedHashMap, putDynamicTemplatesJSONArray);

		_putAll(linkedHashMap, currentDynamicTemplatesJSONArray);

		JSONArray mergedDynamicTemplatesJSONArray =
			_jsonFactory.createJSONArray();

		JSONObject defaultTemplateJSONObject = null;

		for (Map.Entry<String, JSONObject> entry : linkedHashMap.entrySet()) {
			String key = entry.getKey();

			if (key.equals("template_")) {
				defaultTemplateJSONObject = entry.getValue();
			}
			else {
				mergedDynamicTemplatesJSONArray.put(entry.getValue());
			}
		}

		if (defaultTemplateJSONObject != null) {
			mergedDynamicTemplatesJSONArray.put(defaultTemplateJSONObject);
		}

		return mergedDynamicTemplatesJSONArray;
	}

	private void _putAll(Map<String, JSONObject> map, JSONArray jsonArray) {
		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray namesJSONArray = jsonObject.names();

			String name = (String)namesJSONArray.get(0);

			map.put(name, jsonObject);
		}
	}

	private void _putMappings(JSONObject mappingsJSONObject) {
		PutMappingRequest putMappingRequest = new PutMappingRequest(_indexName);

		putMappingRequest.source(
			mappingsJSONObject.toString(), XContentType.JSON);

		try {
			ActionResponse actionResponse = _indicesClient.putMapping(
				putMappingRequest, RequestOptions.DEFAULT);

			SearchLogHelperUtil.logActionResponse(_log, actionResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"The attempted mappings update for index ", _indexName,
						" is not compatible with its current mappings. Please ",
						"recreate the index or modify the attempted updates."),
					exception);
			}
		}
	}

	private JSONObject _removeLegacyDocumentType(String json) {
		JSONObject jsonObject = _createJSONObject(json);

		if (jsonObject.has(
				IndexMappingsConstants.LEGACY_LIFERAY_DOCUMENT_TYPE)) {

			return jsonObject.getJSONObject(
				IndexMappingsConstants.LEGACY_LIFERAY_DOCUMENT_TYPE);
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MappingsHelperImpl.class);

	private final String _indexName;
	private final IndicesClient _indicesClient;
	private final JSONFactory _jsonFactory;
	private final String _overrideMappings;
	private final SearchEngineInformation _searchEngineInformation;

}