/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

import org.opensearch.client.opensearch._types.mapping.FieldMapping;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch._types.mapping.TextProperty;
import org.opensearch.client.opensearch.indices.GetFieldMappingRequest;
import org.opensearch.client.opensearch.indices.GetFieldMappingResponse;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.indices.get_field_mapping.TypeFieldMappings;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class FieldMappingAssert {

	public static void assertAnalyzer(
			String expectedValue, String field, String index,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws Exception {

		assertFieldMappingMetadata(
			expectedValue, "analyzer", field, index, openSearchIndicesClient);
	}

	public static void assertFieldMappingMetadata(
			String expectedValue, String key, String field, String index,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				try {
					_assertFieldMappingMetadata(
						expectedValue, field, index, key,
						openSearchIndicesClient);
				}
				catch (JSONException jsonException) {
					throw new RuntimeException(jsonException);
				}
			});
	}

	public static void assertType(
			String expectedValue, String field, String index,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws Exception {

		assertFieldMappingMetadata(
			expectedValue, "type", field, index, openSearchIndicesClient);
	}

	private static void _assertFieldMappingMetadata(
			String expectedValue, String field, String index, String key,
			OpenSearchIndicesClient openSearchIndicesClient)
		throws JSONException {

		Assert.assertEquals(
			expectedValue,
			_getFieldMappingPropertyValue(
				field, key,
				_getTypeFieldMappings(field, index, openSearchIndicesClient)));
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

		if (property.isText() && key.equals("analyzer")) {
			TextProperty textProperty = property.text();

			return textProperty.analyzer();
		}

		return null;
	}

	private static TypeFieldMappings _getTypeFieldMappings(
		String field, String index,
		OpenSearchIndicesClient openSearchIndicesClient) {

		try {
			GetFieldMappingResponse getFieldMappingResponse =
				openSearchIndicesClient.getFieldMapping(
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