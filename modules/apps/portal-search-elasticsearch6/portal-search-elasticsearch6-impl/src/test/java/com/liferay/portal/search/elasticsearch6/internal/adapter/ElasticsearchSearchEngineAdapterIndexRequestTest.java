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

package com.liferay.portal.search.elasticsearch6.internal.adapter;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.search.elasticsearch6.internal.adapter.index.IndexRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.TestElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexResponse;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.xcontent.XContentType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchSearchEngineAdapterIndexRequestTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterIndexRequestTest.class.
				getSimpleName());

		_elasticsearchFixture.setUp();

		_elasticsearchConnectionManager =
			new TestElasticsearchConnectionManager(_elasticsearchFixture);

		AdminClient adminClient =
			_elasticsearchConnectionManager.getAdminClient();

		CreateIndexRequestBuilder createIndexRequestBuilder =
			adminClient.indices().prepareCreate(_INDEX_NAME);

		createIndexRequestBuilder.get();

		_indicesAdminClient =
			_elasticsearchConnectionManager.getAdminClient().indices();

		_configureEngine();
	}

	@After
	public void tearDown() throws Exception {
		AdminClient adminClient =
			_elasticsearchConnectionManager.getAdminClient();

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			adminClient.indices().prepareDelete(_INDEX_NAME);

		deleteIndexRequestBuilder.get();

		_elasticsearchFixture.tearDown();
	}

	@Ignore
	@Test
	public void testExecuteGetFieldMappingIndexRequest() {
		String mappingName = "testGetFieldMapping";
		String mappingSource =
			"{\"properties\":{\"testField\":{\"type\":\"keyword\"}, " +
				"\"otherTestField\":{\"type\":\"keyword\"}}}";

		String[] fields = {"otherTestField"};

		_putMapping(mappingName, mappingSource);

		GetFieldMappingIndexRequest getFieldMappingIndexRequest =
			new GetFieldMappingIndexRequest(_INDEX_NAME, mappingName, fields);

		GetFieldMappingIndexResponse getFieldMappingIndexResponse =
			_searchEngineAdapter.execute(getFieldMappingIndexRequest);

		String mappings = getFieldMappingIndexResponse.getMappings();

		Assert.assertTrue(mappings.contains(mappingSource));
	}

	@Test
	public void testExecuteGetMappingIndexRequest() throws JSONException {
		String mappingName = "testGetMapping";
		String mappingSource =
			"{\"properties\":{\"testField\":{\"type\":\"keyword\"}}}";

		_putMapping(mappingName, mappingSource);

		GetMappingIndexRequest getMappingIndexRequest =
			new GetMappingIndexRequest(_INDEX_NAME, mappingName);

		GetMappingIndexResponse getMappingIndexResponse =
			_searchEngineAdapter.execute(getMappingIndexRequest);

		String mappings = getMappingIndexResponse.getMappings();

		Assert.assertTrue(mappings.contains(mappingSource));
	}

	@Test
	public void testExecutePutMappingIndexRequest() {
		String mappingName = "testPutMapping";
		String mappingSource =
			"{\"properties\":{\"testField\":{\"type\":\"keyword\"}}}";

		PutMappingIndexRequest putMappingIndexRequest =
			new PutMappingIndexRequest(_INDEX_NAME, mappingName, mappingSource);

		PutMappingIndexResponse putMappingIndexResponse =
			_searchEngineAdapter.execute(putMappingIndexRequest);

		Assert.assertTrue(putMappingIndexResponse.getAcknowledged());

		GetMappingsRequestBuilder getMappingsRequestBuilder =
			_indicesAdminClient.prepareGetMappings(_INDEX_NAME);

		getMappingsRequestBuilder.setTypes(mappingName);

		GetMappingsResponse getMappingsResponse =
			getMappingsRequestBuilder.get();

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>>
			mappings = getMappingsResponse.getMappings();

		MappingMetaData mappingMetaData = mappings.get(
			_INDEX_NAME).get(mappingName);

		CompressedXContent mappingContent = mappingMetaData.source();

		Assert.assertTrue(mappingContent.toString().contains(mappingSource));
	}

	private void _configureEngine() {
		_searchEngineAdapter = new ElasticsearchSearchEngineAdapterImpl();

		IndexRequestExecutorFixture indexRequestExecutorFixture =
			new IndexRequestExecutorFixture(_elasticsearchConnectionManager);

		((ElasticsearchSearchEngineAdapterImpl)_searchEngineAdapter).
			indexRequestExecutor = indexRequestExecutorFixture.createExecutor();
	}

	private void _putMapping(String mappingName, String mappingSource) {
		PutMappingRequestBuilder putMappingRequestBuilder =
			_indicesAdminClient.preparePutMapping(_INDEX_NAME);

		putMappingRequestBuilder.setSource(mappingSource, XContentType.JSON);

		putMappingRequestBuilder.setType(mappingName);

		putMappingRequestBuilder.get();
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private ElasticsearchFixture _elasticsearchFixture;
	private IndicesAdminClient _indicesAdminClient;
	private SearchEngineAdapter _searchEngineAdapter;

}