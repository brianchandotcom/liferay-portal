/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactory;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactoryImpl;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;
import com.liferay.portal.search.opensearch2.internal.util.IndexUtil;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Refresh;
import org.opensearch.client.opensearch._types.Result;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.GetResponse;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;

/**
 * @author Dylan Rebelak
 */
public class OpenSearchSearchEngineAdapterDocumentRequestTest
	extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_searchEngineAdapter = createSearchEngineAdapter(
			openSearchConnectionManager);

		_openSearchClient = openSearchConnectionManager.getOpenSearchClient();

		_openSearchIndicesClient = _openSearchClient.indices();

		_documentFixture.setUp();

		_createIndex();
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();

		_documentFixture.tearDown();
	}

	@Test
	public void testExecuteBulkDocumentRequest() throws JSONException {
		Document document1 = new DocumentImpl();

		document1.addKeyword(Field.UID, "1");
		document1.addKeyword(_FIELD_NAME, Boolean.TRUE.toString());

		BulkDocumentRequest bulkDocumentRequest1 = new BulkDocumentRequest();

		bulkDocumentRequest1.addBulkableDocumentRequest(
			new IndexDocumentRequest(TEST_INDEX_NAME, document1));

		Document document2 = new DocumentImpl();

		document2.addKeyword(Field.UID, "2");
		document2.addKeyword(_FIELD_NAME, Boolean.FALSE.toString());

		bulkDocumentRequest1.addBulkableDocumentRequest(
			new IndexDocumentRequest(TEST_INDEX_NAME, document2));

		BulkDocumentResponse bulkDocumentResponse1 =
			_searchEngineAdapter.execute(bulkDocumentRequest1);

		Assert.assertFalse(bulkDocumentResponse1.hasErrors());

		List<BulkDocumentItemResponse> bulkDocumentItemResponses1 =
			bulkDocumentResponse1.getBulkDocumentItemResponses();

		Assert.assertEquals(
			bulkDocumentItemResponses1.toString(), 2,
			bulkDocumentItemResponses1.size());

		BulkDocumentItemResponse bulkDocumentItemResponse1 =
			bulkDocumentItemResponses1.get(0);

		Assert.assertEquals("1", bulkDocumentItemResponse1.getId());

		BulkDocumentItemResponse bulkDocumentItemResponse2 =
			bulkDocumentItemResponses1.get(1);

		Assert.assertEquals("2", bulkDocumentItemResponse2.getId());

		BulkDocumentRequest bulkDocumentRequest2 = new BulkDocumentRequest();

		bulkDocumentRequest2.addBulkableDocumentRequest(
			new DeleteDocumentRequest(TEST_INDEX_NAME, "1"));

		Document document2Update = new DocumentImpl();

		document2Update.addKeyword(Field.UID, "2");
		document2Update.addKeyword(_FIELD_NAME, Boolean.TRUE.toString());

		bulkDocumentRequest2.addBulkableDocumentRequest(
			new UpdateDocumentRequest(TEST_INDEX_NAME, "2", document2Update));

		BulkDocumentResponse bulkDocumentResponse2 =
			_searchEngineAdapter.execute(bulkDocumentRequest2);

		Assert.assertFalse(bulkDocumentResponse2.hasErrors());

		List<BulkDocumentItemResponse> bulkDocumentItemResponses2 =
			bulkDocumentResponse2.getBulkDocumentItemResponses();

		Assert.assertEquals(
			bulkDocumentItemResponses2.toString(), 2,
			bulkDocumentItemResponses2.size());

		BulkDocumentItemResponse bulkDocumentItemResponse3 =
			bulkDocumentItemResponses2.get(0);

		Assert.assertEquals("1", bulkDocumentItemResponse3.getId());

		BulkDocumentItemResponse bulkDocumentItemResponse4 =
			bulkDocumentItemResponses2.get(1);

		Assert.assertEquals("2", bulkDocumentItemResponse4.getId());

		GetResponse<JsonData> getResponse1 = _getDocument("1");

		Assert.assertFalse(getResponse1.found());

		GetResponse<JsonData> getResponse2 = _getDocument("2");

		Assert.assertTrue(getResponse2.found());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(getResponse2.source()));

		Map<String, JsonData> map2 = ConversionUtil.toJsonDataMap(
			jsonObject.toMap());

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(map2.get(_FIELD_NAME)));
	}

	@Test
	public void testExecuteBulkDocumentRequestNoUid() throws Exception {
		Document document1 = new DocumentImpl();

		document1.addKeyword(_FIELD_NAME, Boolean.TRUE.toString());

		BulkDocumentRequest bulkDocumentRequest1 = new BulkDocumentRequest();

		bulkDocumentRequest1.addBulkableDocumentRequest(
			new IndexDocumentRequest(TEST_INDEX_NAME, document1));

		Document document2 = new DocumentImpl();

		document2.addKeyword(_FIELD_NAME, Boolean.FALSE.toString());

		bulkDocumentRequest1.addBulkableDocumentRequest(
			new IndexDocumentRequest(TEST_INDEX_NAME, document2));

		BulkDocumentResponse bulkDocumentResponse1 =
			_searchEngineAdapter.execute(bulkDocumentRequest1);

		Assert.assertFalse(bulkDocumentResponse1.hasErrors());

		List<BulkDocumentItemResponse> bulkDocumentItemResponses1 =
			bulkDocumentResponse1.getBulkDocumentItemResponses();

		Assert.assertEquals(
			bulkDocumentItemResponses1.toString(), 2,
			bulkDocumentItemResponses1.size());

		BulkDocumentItemResponse bulkDocumentItemResponse1 =
			bulkDocumentItemResponses1.get(0);

		Assert.assertFalse(
			Validator.isBlank(bulkDocumentItemResponse1.getId()));

		BulkDocumentItemResponse bulkDocumentItemResponse2 =
			bulkDocumentItemResponses1.get(1);

		Assert.assertFalse(
			Validator.isBlank(bulkDocumentItemResponse2.getId()));

		BulkDocumentRequest bulkDocumentRequest2 = new BulkDocumentRequest();

		bulkDocumentRequest2.addBulkableDocumentRequest(
			new DeleteDocumentRequest(
				TEST_INDEX_NAME, bulkDocumentItemResponse1.getId()));

		Document document2Update = new DocumentImpl();

		document2Update.addKeyword(
			Field.UID, bulkDocumentItemResponse2.getId());
		document2Update.addKeyword(_FIELD_NAME, Boolean.TRUE.toString());

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			TEST_INDEX_NAME, bulkDocumentItemResponse2.getId(),
			document2Update);

		bulkDocumentRequest2.addBulkableDocumentRequest(updateDocumentRequest);

		BulkDocumentResponse bulkDocumentResponse2 =
			_searchEngineAdapter.execute(bulkDocumentRequest2);

		Assert.assertFalse(bulkDocumentResponse2.hasErrors());

		List<BulkDocumentItemResponse> bulkDocumentItemResponses2 =
			bulkDocumentResponse2.getBulkDocumentItemResponses();

		Assert.assertEquals(
			bulkDocumentItemResponses2.toString(), 2,
			bulkDocumentItemResponses2.size());

		BulkDocumentItemResponse bulkDocumentItemResponse3 =
			bulkDocumentItemResponses2.get(0);

		Assert.assertEquals(
			bulkDocumentItemResponse1.getId(),
			bulkDocumentItemResponse3.getId());

		BulkDocumentItemResponse bulkDocumentItemResponse4 =
			bulkDocumentItemResponses2.get(1);

		Assert.assertEquals(
			bulkDocumentItemResponse2.getId(),
			bulkDocumentItemResponse4.getId());

		GetResponse<JsonData> getResponse1 = _getDocument(
			bulkDocumentItemResponse1.getId());

		Assert.assertFalse(getResponse1.found());

		GetResponse<JsonData> getResponse2 = _getDocument(
			bulkDocumentItemResponse2.getId());

		Assert.assertTrue(getResponse2.found());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(getResponse2.source()));

		Map<String, JsonData> map2 = ConversionUtil.toJsonDataMap(
			jsonObject.toMap());

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(map2.get(_FIELD_NAME)));
	}

	@Test
	public void testExecuteDeleteByQueryDocumentRequest() {
		_indexDocument(
			"1",
			JsonData.of(
				HashMapBuilder.<String, Object>put(
					_FIELD_NAME, Boolean.TRUE
				).build()));
		_indexDocument(
			"2",
			JsonData.of(
				HashMapBuilder.<String, Object>put(
					_FIELD_NAME, Boolean.FALSE
				).build()));

		BooleanQuery query = new BooleanQueryImpl();

		query.addExactTerm(_FIELD_NAME, true);

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(
				query, new String[] {TEST_INDEX_NAME});

		DeleteByQueryDocumentResponse deleteByQueryDocumentResponse =
			_searchEngineAdapter.execute(deleteByQueryDocumentRequest);

		Assert.assertEquals(1, deleteByQueryDocumentResponse.getDeleted());
	}

	@Test
	public void testExecuteDeleteDocumentRequest() {
		String id = "1";

		_indexDocument(
			"1",
			JsonData.of(
				HashMapBuilder.<String, Object>put(
					_FIELD_NAME, Boolean.TRUE
				).build()));

		GetResponse<JsonData> getResponse1 = _getDocument(id);

		Assert.assertTrue(getResponse1.found());

		DeleteDocumentResponse deleteDocumentResponse =
			_searchEngineAdapter.execute(
				new DeleteDocumentRequest(TEST_INDEX_NAME, id));

		Assert.assertEquals(
			Result.Deleted.jsonValue(),
			deleteDocumentResponse.getStatusString());

		GetResponse<JsonData> getResponse2 = _getDocument(id);

		Assert.assertFalse(getResponse2.found());
	}

	@Test
	public void testExecuteIndexDocumentRequestNoUid() {
		IndexDocumentResponse indexDocumentResponse = _indexDocumentWithAdapter(
			new DocumentImpl(), null);

		Assert.assertEquals(
			Result.Created.jsonValue(),
			indexDocumentResponse.getStatusString());

		Assert.assertNotNull(indexDocumentResponse.getUid());
	}

	@Test
	public void testExecuteIndexDocumentRequestNoUidWithUpdate()
		throws Exception {

		Document document = new DocumentImpl();

		IndexDocumentResponse indexDocumentResponse = _indexDocumentWithAdapter(
			document, null);

		document.addKeyword(_FIELD_NAME, true);

		_updateDocumentWithAdapter(document, indexDocumentResponse.getUid());

		Map<String, JsonData> fields = _getFields(
			indexDocumentResponse.getUid());

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(fields.get(_FIELD_NAME)));
	}

	@Test
	public void testExecuteIndexDocumentRequestUidInDocument() {
		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, "1");

		IndexDocumentResponse indexDocumentResponse = _indexDocumentWithAdapter(
			document, null);

		Assert.assertEquals(
			Result.Created.jsonValue(),
			indexDocumentResponse.getStatusString());

		Assert.assertEquals("1", indexDocumentResponse.getUid());
	}

	@Test
	public void testExecuteIndexDocumentRequestUidInRequest() {
		IndexDocumentResponse indexDocumentResponse = _indexDocumentWithAdapter(
			new DocumentImpl(), "1");

		Assert.assertEquals(
			Result.Created.jsonValue(),
			indexDocumentResponse.getStatusString());

		Assert.assertEquals("1", indexDocumentResponse.getUid());
	}

	@Test
	public void testExecuteUpdateByQueryDocumentRequest() {
		_indexDocument(
			"1",
			JsonData.of(
				HashMapBuilder.<String, Object>put(
					_FIELD_NAME, Boolean.TRUE
				).build()));

		BooleanQuery query = new BooleanQueryImpl();

		query.addExactTerm(_FIELD_NAME, true);

		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(
				query, null, new String[] {TEST_INDEX_NAME});

		UpdateByQueryDocumentResponse updateByQueryDocumentResponse =
			_searchEngineAdapter.execute(updateByQueryDocumentRequest);

		Assert.assertEquals(1, updateByQueryDocumentResponse.getUpdated());
	}

	@Test
	public void testExecuteUpdateDocumentRequest() throws Exception {
		Map<String, Object> documentFields = HashMapBuilder.<String, Object>put(
			_FIELD_NAME, Boolean.TRUE
		).build();

		String id = "1";

		_indexDocument(id, JsonData.of(documentFields));

		Map<String, JsonData> fields1 = _getFields(id);

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(fields1.get(_FIELD_NAME)));

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, id);
		document.addKeyword(_FIELD_NAME, false);

		UpdateDocumentResponse updateDocumentResponse =
			_updateDocumentWithAdapter(document, id);

		Assert.assertEquals(
			Result.Updated.jsonValue(),
			updateDocumentResponse.getStatusString());

		Map<String, JsonData> fields2 = _getFields(id);

		Assert.assertEquals(
			Boolean.FALSE.toString(), String.valueOf(fields2.get(_FIELD_NAME)));
	}

	@Test
	public void testExecuteUpdateDocumentRequestNoDocumentUid()
		throws Exception {

		String id = "1";

		_indexDocument(
			id,
			JsonData.of(
				HashMapBuilder.<String, Object>put(
					_FIELD_NAME, Boolean.TRUE
				).build()));

		Map<String, JsonData> map1 = _getFields(id);

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(map1.get(_FIELD_NAME)));

		Document document = new DocumentImpl();

		document.addKeyword(_FIELD_NAME, false);

		UpdateDocumentResponse updateDocumentResponse =
			_updateDocumentWithAdapter(document, id);

		Assert.assertEquals(
			Result.Updated.jsonValue(),
			updateDocumentResponse.getStatusString());

		Map<String, JsonData> fields2 = _getFields(id);

		Assert.assertEquals(
			Boolean.FALSE.toString(), String.valueOf(fields2.get(_FIELD_NAME)));
	}

	@Test
	public void testExecuteUpdateDocumentRequestNoRequestId() throws Exception {
		Map<String, Object> documentFields = HashMapBuilder.<String, Object>put(
			_FIELD_NAME, Boolean.TRUE
		).build();

		String id = "1";

		_indexDocument(id, JsonData.of(documentFields));

		Map<String, JsonData> fields1 = _getFields(id);

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(fields1.get(_FIELD_NAME)));

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, id);
		document.addKeyword(_FIELD_NAME, false);

		UpdateDocumentResponse updateDocumentResponse =
			_updateDocumentWithAdapter(document, null);

		Assert.assertEquals(
			Result.Updated.jsonValue(),
			updateDocumentResponse.getStatusString());

		Map<String, JsonData> fields2 = _getFields(id);

		Assert.assertEquals(
			Boolean.FALSE.toString(), String.valueOf(fields2.get(_FIELD_NAME)));
	}

	@Test
	public void testExecuteUpdateDocumentRequestScript() throws Exception {
		Map<String, Object> documentFields = HashMapBuilder.<String, Object>put(
			_FIELD_NAME, Boolean.TRUE
		).build();

		String id = "1";

		_indexDocument(id, JsonData.of(documentFields));

		Map<String, JsonData> fields1 = _getFields(id);

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(fields1.get(_FIELD_NAME)));

		UpdateDocumentResponse updateDocumentResponse =
			_updateDocumentWithAdapter(
				_scripts.script(
					StringBundler.concat(
						"ctx._source.", _FIELD_NAME, "=\"false\" ")),
				false, id);

		Assert.assertEquals(
			Result.Updated.jsonValue(),
			updateDocumentResponse.getStatusString());

		Map<String, JsonData> fields2 = _getFields(id);

		Assert.assertEquals(
			Boolean.FALSE.toString(), String.valueOf(fields2.get(_FIELD_NAME)));
	}

	@Test
	public void testExecuteUpdateDocumentRequestScriptedUpsert()
		throws Exception {

		String id = "1";

		_updateDocumentWithAdapter(
			_scripts.script(
				StringBundler.concat(
					"ctx._source.", _FIELD_NAME, "=\"true\" ")),
			true, id);

		Map<String, JsonData> fields = _getFields(id);

		Assert.assertEquals(
			Boolean.TRUE.toString(), String.valueOf(fields.get(_FIELD_NAME)));
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		OpenSearchConnectionManager openSearchConnectionManager) {

		SearchEngineAdapter searchEngineAdapter =
			new OpenSearchSearchEngineAdapterImpl();

		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_documentRequestExecutor",
			_createDocumentRequestExecutor(
				openSearchConnectionManager,
				new OpenSearchDocumentFactoryImpl()));

		return searchEngineAdapter;
	}

	private static DocumentRequestExecutor _createDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		OpenSearchDocumentFactory openSearchDocumentFactory) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setOpenSearchConnectionManager(openSearchConnectionManager);
					setOpenSearchDocumentFactory(openSearchDocumentFactory);
				}
			};

		documentRequestExecutorFixture.setUp();

		return documentRequestExecutorFixture.getDocumentRequestExecutor();
	}

	private void _createIndex() throws Exception {
		CreateIndexRequest.Builder builder = new CreateIndexRequest.Builder();

		builder.index(TEST_INDEX_NAME);

		builder.mappings(
			TypeMapping.of(
				typeMapping -> typeMapping.properties(
					IndexUtil.getPropertiesMap(
						JSONUtil.put(
							"properties",
							JSONUtil.put(
								"matchDocument",
								JSONUtil.put("type", "boolean")))))));

		try {
			_openSearchIndicesClient.create(builder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteIndex() {
		try {
			_openSearchIndicesClient.delete(
				DeleteIndexRequest.of(
					deleteIndexRequest -> deleteIndexRequest.index(
						TEST_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GetResponse<JsonData> _getDocument(String id) {
		try {
			return _openSearchClient.get(
				GetRequest.of(
					getRequest -> getRequest.id(
						id
					).index(
						TEST_INDEX_NAME
					)),
				JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
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

	private void _indexDocument(String id, JsonData jsonData) {
		IndexRequest.Builder<JsonData> indexRequestBuilder =
			new IndexRequest.Builder<>();

		indexRequestBuilder.document(jsonData);
		indexRequestBuilder.id(id);
		indexRequestBuilder.index(TEST_INDEX_NAME);
		indexRequestBuilder.refresh(Refresh.True);

		try {
			_openSearchClient.index(indexRequestBuilder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private IndexDocumentResponse _indexDocumentWithAdapter(
		Document document, String uid) {

		return _searchEngineAdapter.execute(
			new IndexDocumentRequest(TEST_INDEX_NAME, uid, document));
	}

	private UpdateDocumentResponse _updateDocumentWithAdapter(
		Document document, String uid) {

		return _searchEngineAdapter.execute(
			new UpdateDocumentRequest(TEST_INDEX_NAME, uid, document));
	}

	private UpdateDocumentResponse _updateDocumentWithAdapter(
		Script script, boolean scriptedUpsert, String uid) {

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			TEST_INDEX_NAME, uid, script);

		updateDocumentRequest.setScriptedUpsert(scriptedUpsert);

		return _searchEngineAdapter.execute(updateDocumentRequest);
	}

	private static final String _FIELD_NAME = "matchDocument";

	private static final Scripts _scripts = new ScriptsImpl();

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private OpenSearchClient _openSearchClient;
	private OpenSearchIndicesClient _openSearchIndicesClient;
	private SearchEngineAdapter _searchEngineAdapter;

}