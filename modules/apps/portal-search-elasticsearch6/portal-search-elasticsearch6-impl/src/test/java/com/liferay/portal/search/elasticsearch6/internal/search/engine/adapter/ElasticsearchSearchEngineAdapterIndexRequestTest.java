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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.TestElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.index.IndexRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.FlushIndexRequest;
import com.liferay.portal.search.engine.adapter.index.FlushIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexResponse;

import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
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

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new TestElasticsearchConnectionManager(_elasticsearchFixture);

		AdminClient adminClient =
			elasticsearchConnectionManager.getAdminClient();

		_indicesAdminClient = adminClient.indices();

		_searchEngineAdapter = createSearchEngineAdapter(
			elasticsearchConnectionManager);

		createIndex();
	}

	@After
	public void tearDown() throws Exception {
		deleteIndex();

		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testExecuteFlushIndexRequest() {
		FlushIndexRequest flushIndexRequest = new FlushIndexRequest(
			_INDEX_NAME);

		FlushIndexResponse flushIndexResponse = _searchEngineAdapter.execute(
			flushIndexRequest);

		Assert.assertEquals(0, flushIndexResponse.getFailedShards());
	}

	@Ignore
	@Test
	public void testExecuteGetFieldMappingIndexRequest() {
		String mappingName = "testGetFieldMapping";
		String mappingSource =
			"{\"properties\":{\"testField\":{\"type\":\"keyword\"}, " +
				"\"otherTestField\":{\"type\":\"keyword\"}}}";

		_putMapping(mappingName, mappingSource);

		String[] fields = {"otherTestField"};

		GetFieldMappingIndexRequest getFieldMappingIndexRequest =
			new GetFieldMappingIndexRequest(
				new String[] {_INDEX_NAME}, mappingName, fields);

		GetFieldMappingIndexResponse getFieldMappingIndexResponse =
			_searchEngineAdapter.execute(getFieldMappingIndexRequest);

		String fieldMappings = String.valueOf(
			getFieldMappingIndexResponse.getFieldMappings());

		Assert.assertTrue(fieldMappings.contains(mappingSource));
	}

	@Test
	public void testExecuteGetMappingIndexRequest() throws JSONException {
		String mappingName = "testGetMapping";
		String mappingSource =
			"{\"properties\":{\"testField\":{\"type\":\"keyword\"}}}";

		_putMapping(mappingName, mappingSource);

		GetMappingIndexRequest getMappingIndexRequest =
			new GetMappingIndexRequest(new String[] {_INDEX_NAME}, mappingName);

		GetMappingIndexResponse getMappingIndexResponse =
			_searchEngineAdapter.execute(getMappingIndexRequest);

		Map<String, String> indexMappings =
			getMappingIndexResponse.getIndexMappings();

		String string = indexMappings.toString();

		Assert.assertTrue(string.contains(mappingSource));
	}

	@Test
	public void testExecutePutMappingIndexRequest() {
		String mappingName = "testPutMapping";
		String mappingSource =
			"{\"properties\":{\"testField\":{\"type\":\"keyword\"}}}";

		PutMappingIndexRequest putMappingIndexRequest =
			new PutMappingIndexRequest(
				new String[] {_INDEX_NAME}, mappingName, mappingSource);

		PutMappingIndexResponse putMappingIndexResponse =
			_searchEngineAdapter.execute(putMappingIndexRequest);

		Assert.assertTrue(putMappingIndexResponse.isAcknowledged());

		GetMappingsRequestBuilder getMappingsRequestBuilder =
			_indicesAdminClient.prepareGetMappings(_INDEX_NAME);

		getMappingsRequestBuilder.setTypes(mappingName);

		GetMappingsResponse getMappingsResponse =
			getMappingsRequestBuilder.get();

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>>
			immutableOpenMap1 = getMappingsResponse.getMappings();

		ImmutableOpenMap<String, MappingMetaData> immutableOpenMap2 =
			immutableOpenMap1.get(_INDEX_NAME);

		MappingMetaData mappingMetaData = immutableOpenMap2.get(mappingName);

		String mappingMetaDataSource = String.valueOf(mappingMetaData.source());

		Assert.assertTrue(mappingMetaDataSource.contains(mappingSource));
	}

	@Test
	public void testExecuteRefreshIndexRequest() {
		RefreshIndexRequest refreshIndexRequest = new RefreshIndexRequest(
			_INDEX_NAME);

		RefreshIndexResponse refreshIndexResponse =
			_searchEngineAdapter.execute(refreshIndexRequest);

		Assert.assertEquals(0, refreshIndexResponse.getFailedShards());
	}

	protected void createIndex() {
		CreateIndexRequestBuilder createIndexRequestBuilder =
			_indicesAdminClient.prepareCreate(_INDEX_NAME);

		createIndexRequestBuilder.get();
	}

	protected IndexRequestExecutor createIndexRequestExecutor(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		IndexRequestExecutorFixture indexRequestExecutorFixture =
			new IndexRequestExecutorFixture(elasticsearchConnectionManager);

		return indexRequestExecutorFixture.createExecutor();
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				indexRequestExecutor = createIndexRequestExecutor(
					elasticsearchConnectionManager);
			}
		};
	}

	protected void deleteIndex() {
		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			_indicesAdminClient.prepareDelete(_INDEX_NAME);

		deleteIndexRequestBuilder.get();
	}

	private void _putMapping(String mappingName, String mappingSource) {
		PutMappingRequestBuilder putMappingRequestBuilder =
			_indicesAdminClient.preparePutMapping(_INDEX_NAME);

		putMappingRequestBuilder.setSource(mappingSource, XContentType.JSON);
		putMappingRequestBuilder.setType(mappingName);

		putMappingRequestBuilder.get();
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;
	private IndicesAdminClient _indicesAdminClient;
	private SearchEngineAdapter _searchEngineAdapter;

}