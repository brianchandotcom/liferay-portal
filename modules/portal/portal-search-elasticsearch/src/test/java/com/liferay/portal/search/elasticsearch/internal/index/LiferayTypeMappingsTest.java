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

import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture.Index;
import com.liferay.portal.search.elasticsearch.internal.connection.LiferayIndexCreationHelper;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse.FieldMappingMetaData;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class LiferayTypeMappingsTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			LiferayTypeMappingsTest.class.getSimpleName());

		_elasticsearchFixture.setUp();

		_index = createIndex();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testPortugueseDynamicTemplatesMatchAnalyzers()
		throws Exception {

		Client client = _elasticsearchFixture.getClient();

		IndexRequestBuilder indexRequestBuilder = client.prepareIndex(
			_index.getName(), LiferayTypeMappingsConstants.TYPE);

		String field_pt = RandomTestUtil.randomString() + "_pt";
		String field_pt_BR = RandomTestUtil.randomString() + "_pt_BR";
		String field_pt_PT = RandomTestUtil.randomString() + "_pt_PT";

		indexRequestBuilder.setSource(
			field_pt, RandomTestUtil.randomString(), field_pt_BR,
			RandomTestUtil.randomString(), field_pt_PT,
			RandomTestUtil.randomString());

		indexRequestBuilder.get();

		assertAnalyzer(field_pt, "portuguese");
		assertAnalyzer(field_pt_BR, "brazilian");
		assertAnalyzer(field_pt_PT, "portuguese");
	}

	@Rule
	public TestName testName = new TestName();

	protected static String getAnalyzer(
		FieldMappingMetaData fieldMappingMetaData, final String field) {

		Map<String, Object> mappings = fieldMappingMetaData.sourceAsMap();

		Map<String, Object> mapping = (Map<String, Object>)mappings.get(field);

		return (String)mapping.get("analyzer");
	}

	protected static FieldMappingMetaData getFieldMapping(
		AdminClient adminClient, String index, String type, String field) {

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		GetFieldMappingsRequestBuilder getFieldMappingsRequestBuilder =
			indicesAdminClient.prepareGetFieldMappings(index);

		getFieldMappingsRequestBuilder.setFields(field);
		getFieldMappingsRequestBuilder.setTypes(type);

		GetFieldMappingsResponse getFieldMappingsResponse =
			getFieldMappingsRequestBuilder.get();

		return getFieldMappingsResponse.fieldMappings(index, type, field);
	}

	protected void assertAnalyzer(final String field, final String analyzer)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Assert.assertEquals(
						analyzer, getAnalyzer(getFieldMapping(field), field));

					return null;
				}

			});
	}

	protected Index createIndex() {
		return _elasticsearchFixture.createIndex(
			testName.getMethodName(), new LiferayIndexCreationHelper());
	}

	protected FieldMappingMetaData getFieldMapping(final String field) {
		return getFieldMapping(
			_elasticsearchFixture.getAdminClient(), _index.getName(),
			LiferayTypeMappingsConstants.TYPE, field);
	}

	private ElasticsearchFixture _elasticsearchFixture;
	private Index _index;

}