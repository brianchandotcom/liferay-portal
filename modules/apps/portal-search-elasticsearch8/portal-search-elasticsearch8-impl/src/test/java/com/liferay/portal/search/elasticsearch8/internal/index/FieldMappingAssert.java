/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.index;

import co.elastic.clients.elasticsearch._types.mapping.FieldMapping;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.GetFieldMappingRequest;
import co.elastic.clients.elasticsearch.indices.GetFieldMappingResponse;
import co.elastic.clients.elasticsearch.indices.get_field_mapping.TypeFieldMappings;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 */
public class FieldMappingAssert {

	public static void assertAnalyzer(
			String expectedValue,
			ElasticsearchIndicesClient elasticsearchIndicesClient, String field,
			String index)
		throws Exception {

		assertFieldMappingMetadata(
			expectedValue, elasticsearchIndicesClient, field, index,
			"analyzer");
	}

	public static void assertFieldMappingMetadata(
			String expectedValue,
			ElasticsearchIndicesClient elasticsearchIndicesClient, String field,
			String index, String key)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				try {
					_assertFieldMappingMetadata(
						expectedValue, elasticsearchIndicesClient, field, index,
						key);
				}
				catch (JSONException jsonException) {
					throw new RuntimeException(jsonException);
				}
			});
	}

	public static void assertType(
			String expectedValue,
			ElasticsearchIndicesClient elasticsearchIndicesClient, String field,
			String index)
		throws Exception {

		assertFieldMappingMetadata(
			expectedValue, elasticsearchIndicesClient, field, index, "type");
	}

	private static void _assertFieldMappingMetadata(
			String expectedValue,
			ElasticsearchIndicesClient elasticsearchIndicesClient, String field,
			String index, String key)
		throws JSONException {

		Assert.assertEquals(
			expectedValue,
			_getFieldMappingPropertyValue(
				field, key,
				_getTypeFieldMappings(
					elasticsearchIndicesClient, field, index)));
	}

	private static String _getFieldMappingPropertyValue(
			String field, String key, TypeFieldMappings typeFieldMappings)
		throws JSONException {

		Map<String, FieldMapping> fieldMappings = typeFieldMappings.mappings();

		FieldMapping fieldMapping = fieldMappings.get(field);

		if (fieldMapping == null) {
			return null;
		}

		Map<String, Property> properties = fieldMapping.mapping();

		Property property = properties.get(field);

		if (key.equals("store") || key.equals("type")) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JsonpUtil.toString(property));

			return jsonObject.getString(key);
		}

		if (!property.isText() || !key.equals("analyzer")) {
			return null;
		}

		TextProperty textProperty = property.text();

		return textProperty.analyzer();
	}

	private static TypeFieldMappings _getTypeFieldMappings(
		ElasticsearchIndicesClient elasticsearchIndicesClient, String field,
		String index) {

		try {
			GetFieldMappingResponse getFieldMappingResponse =
				elasticsearchIndicesClient.getFieldMapping(
					GetFieldMappingRequest.of(
						getFieldMappingRequest -> getFieldMappingRequest.fields(
							field
						).index(
							index
						)));

			return getFieldMappingResponse.get(index);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}