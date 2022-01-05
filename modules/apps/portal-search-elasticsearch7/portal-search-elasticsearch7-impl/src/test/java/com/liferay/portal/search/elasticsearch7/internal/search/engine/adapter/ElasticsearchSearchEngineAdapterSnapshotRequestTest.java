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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot.SnapshotRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryResponse;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotResponse;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotResponse;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesResponse;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRepositoryDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotState;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;
import org.elasticsearch.cluster.metadata.RepositoryMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.repositories.fs.FsRepository;
import org.elasticsearch.snapshots.SnapshotInfo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSearchEngineAdapterSnapshotRequestTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterSnapshotRequestTest.class);

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		_searchEngineAdapter = createSearchEngineAdapter(_elasticsearchFixture);

		RestHighLevelClient restHighLevelClient =
			_elasticsearchFixture.getRestHighLevelClient();

		_indicesClient = restHighLevelClient.indices();

		_snapshotClient = restHighLevelClient.snapshot();

		_createIndex();
		_createRepository(_TEST_REPOSITORY_NAME, _TEST_REPOSITORY_NAME);
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();
		_deleteRepository(_TEST_REPOSITORY_NAME);
	}

	@Test
	public void testCreateSnapshot() {
		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest(
			_TEST_REPOSITORY_NAME, "test_create_snapshot");

		createSnapshotRequest.setIndexNames(_INDEX_NAME);

		CreateSnapshotResponse createSnapshotResponse =
			_searchEngineAdapter.execute(createSnapshotRequest);

		SnapshotDetails snapshotDetails =
			createSnapshotResponse.getSnapshotDetails();

		Assert.assertArrayEquals(
			createSnapshotRequest.getIndexNames(),
			snapshotDetails.getIndexNames());

		Assert.assertEquals(
			SnapshotState.SUCCESS, snapshotDetails.getSnapshotState());

		Assert.assertTrue(snapshotDetails.getSuccessfulShards() > 0);

		List<SnapshotInfo> snapshotInfos = _getSnapshotInfo(
			"test_create_snapshot");

		Assert.assertEquals("Expected 1 SnapshotInfo", 1, snapshotInfos.size());

		SnapshotInfo snapshotInfo = snapshotInfos.get(0);

		List<String> indices = snapshotInfo.indices();

		Assert.assertArrayEquals(
			createSnapshotRequest.getIndexNames(), indices.toArray());

		Assert.assertEquals(
			"test_create_snapshot", createSnapshotRequest.getSnapshotName());
		Assert.assertEquals(
			createSnapshotRequest.getRepositoryName(), _TEST_REPOSITORY_NAME);

		_deleteSnapshot(_TEST_REPOSITORY_NAME, "test_create_snapshot");
	}

	@Test
	public void testCreateSnapshotRepository() {
		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest =
			new CreateSnapshotRepositoryRequest(
				"testCreateSnapshotRepository", "testCreateSnapshotRepository");

		CreateSnapshotRepositoryResponse createSnapshotRepositoryResponse =
			_searchEngineAdapter.execute(createSnapshotRepositoryRequest);

		Assert.assertTrue(createSnapshotRepositoryResponse.isAcknowledged());

		GetRepositoriesResponse getRepositoriesResponse =
			_getGetRepositoriesResponse(
				new String[] {"testCreateSnapshotRepository"});

		List<RepositoryMetadata> repositoryMetadatas =
			getRepositoriesResponse.repositories();

		Assert.assertEquals(
			"Expected 1 RepositoryMetadata", 1, repositoryMetadatas.size());

		RepositoryMetadata repositoryMetadata = repositoryMetadatas.get(0);

		Assert.assertEquals(
			"testCreateSnapshotRepository", repositoryMetadata.name());
		Assert.assertEquals(
			SnapshotRepositoryDetails.FS_REPOSITORY_TYPE,
			repositoryMetadata.type());

		_deleteRepository("testCreateSnapshotRepository");
	}

	@Test
	public void testDeleteSnapshot() throws Exception {
		_createSnapshot(
			_TEST_REPOSITORY_NAME, "test_delete_snapshot", true, _INDEX_NAME);

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				List<SnapshotInfo> snapshotInfos = _getSnapshotInfo(
					"test_delete_snapshot");

				Assert.assertEquals(
					"Expected 1 SnapshotInfo", 1, snapshotInfos.size());

				DeleteSnapshotRequest deleteSnapshotRequest =
					new DeleteSnapshotRequest(
						_TEST_REPOSITORY_NAME, "test_delete_snapshot");

				DeleteSnapshotResponse deleteSnapshotResponse =
					_searchEngineAdapter.execute(deleteSnapshotRequest);

				Assert.assertTrue(deleteSnapshotResponse.isAcknowledged());

				snapshotInfos = _getSnapshotInfo("test_delete_snapshot");

				Assert.assertTrue(snapshotInfos.isEmpty());

				return null;
			});
	}

	@Test
	public void testGetSnapshotRepositories() {
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest =
			new GetSnapshotRepositoriesRequest(_TEST_REPOSITORY_NAME);

		GetSnapshotRepositoriesResponse getSnapshotRepositoriesResponse =
			_searchEngineAdapter.execute(getSnapshotRepositoriesRequest);

		List<SnapshotRepositoryDetails> snapshotRepositoryDetailsList =
			getSnapshotRepositoriesResponse.getSnapshotRepositoryDetails();

		Assert.assertEquals(
			"Expected 1 SnapshotRepositoryDetails", 1,
			snapshotRepositoryDetailsList.size());

		SnapshotRepositoryDetails snapshotRepositoryDetails =
			snapshotRepositoryDetailsList.get(0);

		Assert.assertEquals(
			_TEST_REPOSITORY_NAME, snapshotRepositoryDetails.getName());
		Assert.assertEquals(
			SnapshotRepositoryDetails.FS_REPOSITORY_TYPE,
			snapshotRepositoryDetails.getType());
	}

	@Test
	public void testGetSnapshots() {
		_createSnapshot(
			_TEST_REPOSITORY_NAME, "test_get_snapshots", true, _INDEX_NAME);

		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest(
			_TEST_REPOSITORY_NAME);

		getSnapshotsRequest.setSnapshotNames("test_get_snapshots");

		com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsResponse
			getSnapshotsResponse = _searchEngineAdapter.execute(
				getSnapshotsRequest);

		List<SnapshotDetails> snapshotDetailsList =
			getSnapshotsResponse.getSnapshotDetails();

		Assert.assertEquals(
			"Expected 1 SnapshotDetails", 1, snapshotDetailsList.size());

		SnapshotDetails snapshotDetails = snapshotDetailsList.get(0);

		Assert.assertArrayEquals(
			new String[] {_INDEX_NAME}, snapshotDetails.getIndexNames());
		Assert.assertEquals(
			SnapshotState.SUCCESS, snapshotDetails.getSnapshotState());

		_deleteSnapshot(_TEST_REPOSITORY_NAME, "test_get_snapshots");
	}

	@Test
	public void testRestoreSnapshot() {
		_createSnapshot(
			_TEST_REPOSITORY_NAME, "test_restore_snapshot", true, _INDEX_NAME);

		_deleteIndex();

		RestoreSnapshotRequest restoreSnapshotRequest =
			new RestoreSnapshotRequest(
				_TEST_REPOSITORY_NAME, "test_restore_snapshot");

		restoreSnapshotRequest.setIndexNames(_INDEX_NAME);

		_searchEngineAdapter.execute(restoreSnapshotRequest);

		Assert.assertTrue("Indices not restored", _indicesExists(_INDEX_NAME));

		_deleteSnapshot(_TEST_REPOSITORY_NAME, "test_restore_snapshot");
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setSnapshotRequestExecutor(
					_createSnapshotRequestExecutor(
						elasticsearchClientResolver));
			}
		};
	}

	private static SnapshotRequestExecutor _createSnapshotRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		SnapshotRequestExecutorFixture snapshotRequestExecutorFixture =
			new SnapshotRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		snapshotRequestExecutorFixture.setUp();

		return snapshotRequestExecutorFixture.getSnapshotRequestExecutor();
	}

	private void _createIndex() {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _createRepository(String name, String location) {
		PutRepositoryRequest putRepositoryRequest = new PutRepositoryRequest();

		putRepositoryRequest.name(name);

		Settings.Builder builder = Settings.builder();

		builder.put(FsRepository.LOCATION_SETTING.getKey(), location);

		putRepositoryRequest.settings(builder);

		putRepositoryRequest.type(SnapshotRepositoryDetails.FS_REPOSITORY_TYPE);

		try {
			_snapshotClient.createRepository(
				putRepositoryRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _createSnapshot(
		String repositoryName, String snapshotName, boolean waitForCompletion,
		String... indexNames) {

		org.elasticsearch.action.admin.cluster.snapshots.create.
			CreateSnapshotRequest createSnapshotRequest =
				new org.elasticsearch.action.admin.cluster.snapshots.create.
					CreateSnapshotRequest(repositoryName, snapshotName);

		createSnapshotRequest.indices(indexNames);
		createSnapshotRequest.waitForCompletion(waitForCompletion);

		try {
			_snapshotClient.create(
				createSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteIndex() {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteRepository(String name) {
		DeleteRepositoryRequest deleteRepositoryRequest =
			new DeleteRepositoryRequest(name);

		try {
			_snapshotClient.deleteRepository(
				deleteRepositoryRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteSnapshot(String repository, String snapshot) {
		org.elasticsearch.action.admin.cluster.snapshots.delete.
			DeleteSnapshotRequest deleteSnapshotRequest =
				new org.elasticsearch.action.admin.cluster.snapshots.delete.
					DeleteSnapshotRequest(repository, snapshot);

		try {
			_snapshotClient.delete(
				deleteSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GetRepositoriesResponse _getGetRepositoriesResponse(
		String[] repositories) {

		GetRepositoriesRequest getRepositoriesRequest =
			new GetRepositoriesRequest(repositories);

		try {
			return _snapshotClient.getRepository(
				getRepositoriesRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private List<SnapshotInfo> _getSnapshotInfo(String snapshotName) {
		org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest
			getSnapshotsRequest =
				new org.elasticsearch.action.admin.cluster.snapshots.get.
					GetSnapshotsRequest(_TEST_REPOSITORY_NAME);

		getSnapshotsRequest.ignoreUnavailable(true);
		getSnapshotsRequest.snapshots(new String[] {snapshotName});

		GetSnapshotsResponse getSnapshotsResponse;

		try {
			getSnapshotsResponse = _snapshotClient.get(
				getSnapshotsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return getSnapshotsResponse.getSnapshots();
	}

	private boolean _indicesExists(String indexName) {
		GetIndexRequest getIndexRequest = new GetIndexRequest();

		getIndexRequest.indices(indexName);

		try {
			return _indicesClient.exists(
				getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _TEST_REPOSITORY_NAME =
		"testRepositoryOperations";

	private static ElasticsearchFixture _elasticsearchFixture;

	private IndicesClient _indicesClient;
	private SearchEngineAdapter _searchEngineAdapter;
	private SnapshotClient _snapshotClient;

}