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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class UpdateRequestTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture();

		_elasticsearchFixture.setUp();

		_restHighLevelClient = _elasticsearchFixture.getRestHighLevelClient();

		_indicesClient = _restHighLevelClient.indices();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws IOException {
		_indicesClient.create(
			new CreateIndexRequest(_INDEX_NAME), RequestOptions.DEFAULT);
	}

	@After
	public void tearDown() throws IOException {
		_indicesClient.delete(
			new DeleteIndexRequest(_INDEX_NAME), RequestOptions.DEFAULT);
	}

	@Test
	public void testUnsetValueWithArrayWithNull() throws IOException {
		String id = _indexAndGetId();

		_updateField(id, "field2", new Object[] {null});

		Map<String, Object> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));

		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)fields.get("field2");

		Assert.assertEquals(list.toString(), 1, list.size());
		Assert.assertNull(list.get(0));
	}

	@Test
	public void testUnsetValueWithEmptyArray() throws IOException {
		String id = _indexAndGetId();

		_updateField(id, "field2", new Object[0]);

		Map<String, Object> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));

		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)fields.get("field2");

		Assert.assertTrue(list.toString(), list.isEmpty());
	}

	@Test
	public void testUnsetValueWithNull() throws IOException {
		String id = _indexAndGetId();

		_updateField(id, "field2", null);

		Map<String, Object> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));
		Assert.assertNull(fields.get("field2"));
	}

	@Test
	public void testUpdateRequestWithMap() throws IOException {
		String id = _indexAndGetId();

		_updateField(id, "field2", "UPDATED FIELD");

		Map<String, Object> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));
		Assert.assertEquals("UPDATED FIELD", fields.get("field2"));
	}

	private Map<String, Object> _getFields(String id) throws IOException {
		GetRequest getRequest = new GetRequest(_INDEX_NAME, id);

		GetResponse getResponse = _restHighLevelClient.get(
			getRequest, RequestOptions.DEFAULT);

		return getResponse.getSource();
	}

	private String _indexAndGetId() throws IOException {
		IndexRequest indexRequest = new IndexRequest(_INDEX_NAME);

		indexRequest.source(
			HashMapBuilder.put(
				"field1", "an example"
			).put(
				"field2", "some test"
			).build());

		IndexResponse indexResponse = _restHighLevelClient.index(
			indexRequest, RequestOptions.DEFAULT);

		return indexResponse.getId();
	}

	private void _updateField(String id, String fieldName, Object fieldValue)
		throws IOException {

		UpdateRequest updateRequest = new UpdateRequest(_INDEX_NAME, id);

		updateRequest.doc(
			HashMapBuilder.put(
				fieldName, fieldValue
			).build());

		_restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static ElasticsearchFixture _elasticsearchFixture;
	private static IndicesClient _indicesClient;
	private static RestHighLevelClient _restHighLevelClient;

}