/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.index.constants.MappingsConstants;
import com.liferay.portal.search.opensearch2.internal.util.IndexUtil;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.opensearch2.internal.util.ResourceUtil;
import com.liferay.portal.search.spi.settings.TypeMappingsHelper;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.opensearch.client.opensearch._types.mapping.DynamicTemplate;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch.indices.GetMappingRequest;
import org.opensearch.client.opensearch.indices.GetMappingResponse;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.indices.PutMappingRequest;
import org.opensearch.client.opensearch.indices.PutMappingResponse;
import org.opensearch.client.opensearch.indices.get_mapping.IndexMappingRecord;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class MappingsFactory implements TypeMappingsHelper {

	public MappingsFactory(
		JSONFactory jsonFactory,
		OpenSearchIndicesClient openSearchIndicesClient,
		OpenSearchConfigurationWrapper openSearchConfigurationWrapper) {

		_jsonFactory = jsonFactory;
		_openSearchIndicesClient = openSearchIndicesClient;
		_openSearchConfigurationWrapper = openSearchConfigurationWrapper;
	}

	public void addOptionalDefaultMappings(String indexName) {
		String name = StringUtil.replace(
			MappingsConstants.LIFERAY_MAPPING_FILE_NAME, ".json",
			"-optional-defaults.json");

		addTypeMappings(
			indexName, ResourceUtil.getResourceAsString(getClass(), name));
	}

	@Override
	public void addTypeMappings(String indexName, String source) {
		PutMappingRequest.Builder builder = new PutMappingRequest.Builder();

		builder.index(indexName);

		JSONObject mappingsJSONObject = _removeLegacyDocumentType(
			_createJSONObject(source));

		_mergeExistingDynamicTemplates(indexName, mappingsJSONObject);

		List<Map<String, DynamicTemplate>> dynamicTemplates =
			IndexUtil.getDynamicTemplatesMap(mappingsJSONObject);

		if (dynamicTemplates != null) {
			builder.dynamicTemplates(dynamicTemplates);
		}

		Map<String, Property> properties = IndexUtil.getPropertiesMap(
			mappingsJSONObject);

		if (properties != null) {
			builder.properties(properties);
		}

		try {
			PutMappingResponse putMappingResponse =
				_openSearchIndicesClient.putMapping(builder.build());

			JsonpUtil.logInfoResponse(putMappingResponse, _log);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public String getMappings(String indexName) {
		try {
			GetMappingResponse getMappingResponse =
				_openSearchIndicesClient.getMapping(
					GetMappingRequest.of(
						getMappingRequest -> getMappingRequest.index(
							indexName)));

			Map<String, IndexMappingRecord> indexMappingRecords =
				getMappingResponse.result();

			IndexMappingRecord indexMappingRecord = indexMappingRecords.get(
				indexName);

			return JsonpUtil.toString(indexMappingRecord.mappings());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public JSONObject getMappingsJSONObject() {
		JSONObject mappingsJSONObject = _jsonFactory.createJSONObject();

		if (Validator.isNotNull(
				_openSearchConfigurationWrapper.overrideTypeMappings())) {

			_mergeMappings(
				_openSearchConfigurationWrapper.overrideTypeMappings(),
				mappingsJSONObject);
		}
		else {
			_mergeMappings(
				ResourceUtil.getResourceAsString(
					getClass(), MappingsConstants.LIFERAY_MAPPING_FILE_NAME),
				mappingsJSONObject);

			_mergeAdditionalTypeMappings(mappingsJSONObject);
		}

		return mappingsJSONObject;
	}

	private JSONObject _createJSONObject(String jsonString) {
		try {
			return _jsonFactory.createJSONObject(jsonString);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	private void _mergeAdditionalTypeMappings(JSONObject mappingsJSONObject) {
		if (Validator.isBlank(
				_openSearchConfigurationWrapper.additionalTypeMappings())) {

			return;
		}

		_mergeMappings(
			_openSearchConfigurationWrapper.additionalTypeMappings(),
			mappingsJSONObject);
	}

	private void _mergeExistingDynamicTemplates(
		String indexName, JSONObject mappingsJSONObject) {

		JSONArray dynamicTemplatesJSONArray = mappingsJSONObject.getJSONArray(
			"dynamic_templates");

		if (dynamicTemplatesJSONArray == null) {
			return;
		}

		JSONObject existingMappingsJSONObject = _createJSONObject(
			getMappings(indexName));

		JSONArray existingDynamicTemplatesJSONArray =
			existingMappingsJSONObject.getJSONArray("dynamic_templates");

		mappingsJSONObject.put(
			"dynamic_templates",
			IndexUtil.mergeDynamicTemplates(
				existingDynamicTemplatesJSONArray, dynamicTemplatesJSONArray));
	}

	private void _mergeMappings(
		String mappings, JSONObject mappingsJSONObject) {

		IndexUtil.mergeToJsonObject(
			mappingsJSONObject,
			_removeLegacyDocumentType(_createJSONObject(mappings)));
	}

	private JSONObject _removeLegacyDocumentType(JSONObject sourceJSONObject) {
		if (sourceJSONObject.has(
				MappingsConstants.LIFERAY_LEGACY_DOCUMENT_TYPE)) {

			return sourceJSONObject.getJSONObject(
				MappingsConstants.LIFERAY_LEGACY_DOCUMENT_TYPE);
		}

		return sourceJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MappingsFactory.class);

	private final JSONFactory _jsonFactory;
	private final OpenSearchConfigurationWrapper
		_openSearchConfigurationWrapper;
	private final OpenSearchIndicesClient _openSearchIndicesClient;

}