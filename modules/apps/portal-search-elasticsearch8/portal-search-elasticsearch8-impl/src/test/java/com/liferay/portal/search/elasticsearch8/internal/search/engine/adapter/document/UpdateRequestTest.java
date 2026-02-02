/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.List;
import java.util.Map;

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

		_elasticsearchClient = _elasticsearchFixture.getElasticsearchClient();

		_elasticsearchIndicesClient = _elasticsearchClient.indices();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws IOException {
		_elasticsearchIndicesClient.create(
			CreateIndexRequest.of(
				createIndexRequest -> createIndexRequest.index(_INDEX_NAME)));
	}

	@After
	public void tearDown() throws IOException {
		_elasticsearchIndicesClient.delete(
			DeleteIndexRequest.of(
				deleteIndexRequest -> deleteIndexRequest.index(_INDEX_NAME)));
	}

	@Test
	public void testUnsetValueWithArrayWithNull() throws Exception {
		String id = _indexAndGetId();

		_updateField(id, "field2", new Object[] {null});

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));

		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)fields.get("field2");

		Assert.assertEquals(list.toString(), 1, list.size());
		Assert.assertNull(list.get(0));
	}

	@Test
	public void testUnsetValueWithEmptyArray() throws Exception {
		String id = _indexAndGetId();

		_updateField(id, "field2", new Object[0]);

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));

		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)fields.get("field2");

		Assert.assertTrue(list.toString(), list.isEmpty());
	}

	@Test
	public void testUnsetValueWithNull() throws Exception {
		String id = _indexAndGetId();

		_updateField(id, "field2", null);

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));
		Assert.assertNull(fields.get("field2"));
	}

	@Test
	public void testUpdateRequestWithMap() throws Exception {
		String id = _indexAndGetId();

		_updateField(id, "field2", "UPDATED FIELD");

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", fields.get("field1"));
		Assert.assertEquals("UPDATED FIELD", fields.get("field2"));
	}

	private Map<String, JsonData> _getFields(String id) throws Exception {
		GetResponse<JsonData> getResponse = _elasticsearchClient.get(
			GetRequest.of(
				getRequest -> getRequest.id(
					id
				).index(
					_INDEX_NAME
				)),
			JsonData.class);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(getResponse.source()));

		return ConversionUtil.toJsonDataMap(jsonObject.toMap());
	}

	private String _indexAndGetId() throws Exception {
		IndexRequest.Builder<JsonData> indexRequestBuilder =
			new IndexRequest.Builder<>();

		indexRequestBuilder.document(
			JsonData.of(
				HashMapBuilder.put(
					"field1", "an example"
				).put(
					"field2", "some test"
				).build()));
		indexRequestBuilder.index(_INDEX_NAME);

		IndexResponse indexResponse = _elasticsearchClient.index(
			indexRequestBuilder.build());

		return indexResponse.id();
	}

	private void _updateField(String id, String fieldName, Object fieldValue)
		throws Exception {

		UpdateRequest.Builder<JsonData, JsonData> updateRequestBuilder =
			new UpdateRequest.Builder<>();

		updateRequestBuilder.doc(
			JsonData.of(
				HashMapBuilder.put(
					fieldName, fieldValue
				).build()));
		updateRequestBuilder.id(id);
		updateRequestBuilder.index(_INDEX_NAME);

		_elasticsearchClient.update(
			updateRequestBuilder.build(), JsonData.class);
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static ElasticsearchClient _elasticsearchClient;
	private static ElasticsearchFixture _elasticsearchFixture;
	private static ElasticsearchIndicesClient _elasticsearchIndicesClient;

}