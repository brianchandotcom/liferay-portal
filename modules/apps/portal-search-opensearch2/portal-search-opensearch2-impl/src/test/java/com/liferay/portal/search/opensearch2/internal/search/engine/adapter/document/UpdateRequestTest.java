/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;

import java.io.IOException;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.GetResponse;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.IndexResponse;
import org.opensearch.client.opensearch.core.UpdateRequest;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;

/**
 * @author Adam Brandizzi
 * @author Petteri Karttunen
 */
public class UpdateRequestTest extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_openSearchClient = openSearchConnectionManager.getOpenSearchClient();

		_openSearchIndicesClient = _openSearchClient.indices();
	}

	@Before
	public void setUp() throws IOException {
		_openSearchIndicesClient.create(
			CreateIndexRequest.of(
				createIndexRequest -> createIndexRequest.index(
					TEST_INDEX_NAME)));
	}

	@After
	public void tearDown() throws IOException {
		_openSearchIndicesClient.delete(
			DeleteIndexRequest.of(
				deleteIndexRequest -> deleteIndexRequest.index(
					TEST_INDEX_NAME)));
	}

	@Test
	public void testUnsetValueWithArrayWithNull() throws Exception {
		String id = _indexAndGetId();

		_updateField("field2", new Object[] {null}, id);

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", String.valueOf(fields.get("field1")));

		JsonArray jsonArray = _getJsonArrayValue(fields.get("field2"));

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.size());

		JsonValue jsonValue = jsonArray.get(0);

		Assert.assertEquals(JsonValue.ValueType.NULL, jsonValue.getValueType());
	}

	@Test
	public void testUnsetValueWithBlankString() throws Exception {
		String id = _indexAndGetId();

		_updateField("field2", StringPool.BLANK, id);

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", String.valueOf(fields.get("field1")));
		Assert.assertEquals(
			StringPool.BLANK, String.valueOf(fields.get("field2")));
	}

	@Test
	public void testUnsetValueWithEmptyArray() throws Exception {
		String id = _indexAndGetId();

		_updateField("field2", new Object[0], id);

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", String.valueOf(fields.get("field1")));

		JsonArray jsonArray = _getJsonArrayValue(fields.get("field2"));

		Assert.assertTrue(jsonArray.toString(), jsonArray.isEmpty());
	}

	@Test
	public void testUpdateRequestWithMap() throws Exception {
		String id = _indexAndGetId();

		_updateField("field2", "UPDATED FIELD", id);

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals("an example", String.valueOf(fields.get("field1")));
		Assert.assertEquals(
			"UPDATED FIELD", String.valueOf(fields.get("field2")));
	}

	private Map<String, JsonData> _getFields(String id) throws Exception {
		GetResponse<JsonData> getResponse = _openSearchClient.get(
			GetRequest.of(
				getRequest -> getRequest.id(
					id
				).index(
					TEST_INDEX_NAME
				)),
			JsonData.class);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(getResponse.source()));

		return ConversionUtil.toJsonDataMap(jsonObject.toMap());
	}

	private JsonArray _getJsonArrayValue(JsonData jsonData) {
		JsonValue jsonValue = jsonData.toJson(new JacksonJsonpMapper());

		return jsonValue.asJsonArray();
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
		indexRequestBuilder.index(TEST_INDEX_NAME);

		IndexResponse indexResponse = _openSearchClient.index(
			indexRequestBuilder.build());

		return indexResponse.id();
	}

	private void _updateField(String fieldName, Object fieldValue, String id)
		throws Exception {

		UpdateRequest.Builder<JsonData, JsonData> updateRequestBuilder =
			new UpdateRequest.Builder<>();

		updateRequestBuilder.doc(
			JsonData.of(
				HashMapBuilder.put(
					fieldName, fieldValue
				).build()));
		updateRequestBuilder.id(id);
		updateRequestBuilder.index(TEST_INDEX_NAME);

		_openSearchClient.update(updateRequestBuilder.build(), JsonData.class);
	}

	private static OpenSearchClient _openSearchClient;
	private static OpenSearchIndicesClient _openSearchIndicesClient;

}