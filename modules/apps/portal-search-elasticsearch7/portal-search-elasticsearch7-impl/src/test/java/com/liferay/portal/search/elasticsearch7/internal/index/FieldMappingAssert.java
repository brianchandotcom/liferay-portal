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

import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse.FieldMappingMetaData;
import org.elasticsearch.client.IndicesAdminClient;

import org.junit.Assert;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 */
public class FieldMappingAssert {

	public static void assertAnalyzer(
			String expectedValue, String field, String type, String index,
			IndicesAdminClient indicesAdminClient)
		throws Exception {

		assertFieldMappingMetaData(
			expectedValue, "analyzer", field, type, index, indicesAdminClient);
	}

	public static void assertFieldMappingMetaData(
			final String expectedValue, final String key, final String field,
			final String type, final String index,
			final IndicesAdminClient indicesAdminClient)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					doAssertFieldMappingMetaData(
						expectedValue, key, field, type, index,
						indicesAdminClient);

					return null;
				}

			});
	}

	public static void assertType(
			String expectedValue, String field, String type, String index,
			IndicesAdminClient indicesAdminClient)
		throws Exception {

		assertFieldMappingMetaData(
			expectedValue, "type", field, type, index, indicesAdminClient);
	}

	protected static void doAssertFieldMappingMetaData(
		String expectedValue, String key, String field, String type,
		String index, IndicesAdminClient indicesAdminClient) {

		FieldMappingMetaData fieldMappingMetaData = getFieldMapping(
			field, type, index, indicesAdminClient);

		String value = getFieldMappingMetaDataValue(
			fieldMappingMetaData, field, key);

		Assert.assertEquals(expectedValue, value);
	}

	protected static FieldMappingMetaData getFieldMapping(
		String field, String type, String index,
		IndicesAdminClient indicesAdminClient) {

		GetFieldMappingsRequestBuilder getFieldMappingsRequestBuilder =
			indicesAdminClient.prepareGetFieldMappings(index);

		getFieldMappingsRequestBuilder.setFields(field);
		getFieldMappingsRequestBuilder.setTypes(type);

		GetFieldMappingsResponse getFieldMappingsResponse =
			getFieldMappingsRequestBuilder.get();

		return getFieldMappingsResponse.fieldMappings(index, type, field);
	}

	@SuppressWarnings("unchecked")
	protected static String getFieldMappingMetaDataValue(
		FieldMappingMetaData fieldMappingMetaData, String field, String key) {

		Map<String, Object> mappings = fieldMappingMetaData.sourceAsMap();

		Map<String, Object> mapping = (Map<String, Object>)mappings.get(field);

		return (String)mapping.get(key);
	}

}