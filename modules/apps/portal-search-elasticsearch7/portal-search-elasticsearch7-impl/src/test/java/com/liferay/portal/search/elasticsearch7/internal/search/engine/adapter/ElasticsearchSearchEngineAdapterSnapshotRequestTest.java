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

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryAction;
import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequestBuilder;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesAction;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequestBuilder;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryAction;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotAction;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotAction;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsAction;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsAction;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.repositories.fs.FsRepository;
import org.elasticsearch.snapshots.SnapshotInfo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSearchEngineAdapterSnapshotRequestTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterClusterRequestTest.class.
				getSimpleName());

		_elasticsearchFixture.setUp();

		_searchEngineAdapter = createSearchEngineAdapter(_elasticsearchFixture);

		_indicesAdminClient = _elasticsearchFixture.getIndicesAdminClient();

		createIndex();
		createRepository(_TEST_REPOSITORY_NAME, _TEST_REPOSITORY_NAME);
	}

	@After
	public void tearDown() throws Exception {
		deleteIndex();
		deleteRepository(_TEST_REPOSITORY_NAME);

		_elasticsearchFixture.tearDown();
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

		List<SnapshotInfo> snapshotInfos = getSnapshotInfo(
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

		DeleteSnapshotRequestBuilder deleteSnapshotRequestBuilder =
			new DeleteSnapshotRequestBuilder(
				_elasticsearchFixture.getClient(),
				DeleteSnapshotAction.INSTANCE);

		deleteSnapshotRequestBuilder.setRepository(_TEST_REPOSITORY_NAME);
		deleteSnapshotRequestBuilder.setSnapshot("test_create_snapshot");

		deleteSnapshotRequestBuilder.get();
	}

	@Test
	public void testCreateSnapshotRepository() {
		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest =
			new CreateSnapshotRepositoryRequest(
				"testCreateSnapshotRepository", "testCreateSnapshotRepository");

		CreateSnapshotRepositoryResponse createSnapshotRepositoryResponse =
			_searchEngineAdapter.execute(createSnapshotRepositoryRequest);

		Assert.assertTrue(createSnapshotRepositoryResponse.isAcknowledged());

		GetRepositoriesRequestBuilder getRepositoriesRequestBuilder =
			new GetRepositoriesRequestBuilder(
				_elasticsearchFixture.getClient(),
				GetRepositoriesAction.INSTANCE);

		getRepositoriesRequestBuilder.addRepositories(
			"testCreateSnapshotRepository");

		GetRepositoriesResponse getRepositoriesResponse =
			getRepositoriesRequestBuilder.get();

		List<RepositoryMetaData> repositoryMetaDatas =
			getRepositoriesResponse.repositories();

		Assert.assertEquals(
			"Expected 1 RepositoryMetaData", 1, repositoryMetaDatas.size());

		RepositoryMetaData repositoryMetaData = repositoryMetaDatas.get(0);

		Assert.assertEquals(
			"testCreateSnapshotRepository", repositoryMetaData.name());
		Assert.assertEquals(
			SnapshotRepositoryDetails.FS_REPOSITORY_TYPE,
			repositoryMetaData.type());

		deleteRepository("testCreateSnapshotRepository");
	}

	@Test
	public void testDeleteSnapshot() throws Exception {
		CreateSnapshotRequestBuilder createSnapshotRequestBuilder =
			new CreateSnapshotRequestBuilder(
				_elasticsearchFixture.getClient(),
				CreateSnapshotAction.INSTANCE);

		createSnapshotRequestBuilder.setIndices(_INDEX_NAME);
		createSnapshotRequestBuilder.setRepository(_TEST_REPOSITORY_NAME);
		createSnapshotRequestBuilder.setSnapshot("test_delete_snapshot");
		createSnapshotRequestBuilder.setWaitForCompletion(true);

		createSnapshotRequestBuilder.get();

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				List<SnapshotInfo> snapshotInfos = getSnapshotInfo(
					"test_delete_snapshot");

				Assert.assertEquals(
					"Expected 1 SnapshotInfo", 1, snapshotInfos.size());

				DeleteSnapshotRequest deleteSnapshotRequest =
					new DeleteSnapshotRequest(
						_TEST_REPOSITORY_NAME, "test_delete_snapshot");

				DeleteSnapshotResponse deleteSnapshotResponse =
					_searchEngineAdapter.execute(deleteSnapshotRequest);

				Assert.assertTrue(deleteSnapshotResponse.isAcknowledged());

				snapshotInfos = getSnapshotInfo("test_delete_snapshot");

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
		CreateSnapshotRequestBuilder createSnapshotRequestBuilder =
			new CreateSnapshotRequestBuilder(
				_elasticsearchFixture.getClient(),
				CreateSnapshotAction.INSTANCE);

		createSnapshotRequestBuilder.setIndices(_INDEX_NAME);
		createSnapshotRequestBuilder.setRepository(_TEST_REPOSITORY_NAME);
		createSnapshotRequestBuilder.setSnapshot("test_get_snapshots");
		createSnapshotRequestBuilder.setWaitForCompletion(true);

		createSnapshotRequestBuilder.get();

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

		DeleteSnapshotRequestBuilder deleteSnapshotRequestBuilder =
			new DeleteSnapshotRequestBuilder(
				_elasticsearchFixture.getClient(),
				DeleteSnapshotAction.INSTANCE);

		deleteSnapshotRequestBuilder.setRepository(_TEST_REPOSITORY_NAME);
		deleteSnapshotRequestBuilder.setSnapshot("test_get_snapshots");

		deleteSnapshotRequestBuilder.get();
	}

	@Test
	public void testRestoreSnapshot() {
		CreateSnapshotRequestBuilder createSnapshotRequestBuilder =
			new CreateSnapshotRequestBuilder(
				_elasticsearchFixture.getClient(),
				CreateSnapshotAction.INSTANCE);

		createSnapshotRequestBuilder.setIndices(_INDEX_NAME);
		createSnapshotRequestBuilder.setRepository(_TEST_REPOSITORY_NAME);
		createSnapshotRequestBuilder.setSnapshot("test_restore_snapshot");
		createSnapshotRequestBuilder.setWaitForCompletion(true);

		createSnapshotRequestBuilder.get();

		deleteIndex();

		RestoreSnapshotRequest restoreSnapshotRequest =
			new RestoreSnapshotRequest(
				_TEST_REPOSITORY_NAME, "test_restore_snapshot");

		restoreSnapshotRequest.setIndexNames(_INDEX_NAME);

		_searchEngineAdapter.execute(restoreSnapshotRequest);

		IndicesExistsRequestBuilder indicesExistsRequestBuilder =
			new IndicesExistsRequestBuilder(
				_elasticsearchFixture.getClient(),
				IndicesExistsAction.INSTANCE);

		indicesExistsRequestBuilder.setIndices(_INDEX_NAME);

		IndicesExistsResponse indicesExistsResponse =
			indicesExistsRequestBuilder.get();

		Assert.assertTrue(
			"Indices not restored", indicesExistsResponse.isExists());

		DeleteSnapshotRequestBuilder deleteSnapshotRequestBuilder =
			new DeleteSnapshotRequestBuilder(
				_elasticsearchFixture.getClient(),
				DeleteSnapshotAction.INSTANCE);

		deleteSnapshotRequestBuilder.setRepository(_TEST_REPOSITORY_NAME);
		deleteSnapshotRequestBuilder.setSnapshot("test_restore_snapshot");

		deleteSnapshotRequestBuilder.get();
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setSnapshotRequestExecutor(
					createSnapshotRequestExecutor(elasticsearchClientResolver));
			}
		};
	}

	protected static SnapshotRequestExecutor createSnapshotRequestExecutor(
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

	protected void createIndex() {
		CreateIndexRequestBuilder createIndexRequestBuilder =
			_indicesAdminClient.prepareCreate(_INDEX_NAME);

		createIndexRequestBuilder.get();
	}

	protected void createRepository(String name, String location) {
		PutRepositoryRequestBuilder putRepositoryRequestBuilder =
			new PutRepositoryRequestBuilder(
				_elasticsearchFixture.getClient(),
				PutRepositoryAction.INSTANCE);

		putRepositoryRequestBuilder.setName(name);

		Settings.Builder builder = Settings.builder();

		builder.put(FsRepository.LOCATION_SETTING.getKey(), location);

		putRepositoryRequestBuilder.setSettings(builder);

		putRepositoryRequestBuilder.setType(
			SnapshotRepositoryDetails.FS_REPOSITORY_TYPE);

		putRepositoryRequestBuilder.get();
	}

	protected void deleteIndex() {
		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			_indicesAdminClient.prepareDelete(_INDEX_NAME);

		deleteIndexRequestBuilder.get();
	}

	protected void deleteRepository(String name) {
		DeleteRepositoryRequestBuilder deleteRepositoryRequestBuilder =
			new DeleteRepositoryRequestBuilder(
				_elasticsearchFixture.getClient(),
				DeleteRepositoryAction.INSTANCE);

		deleteRepositoryRequestBuilder.setName(name);

		deleteRepositoryRequestBuilder.get();
	}

	protected List<SnapshotInfo> getSnapshotInfo(String snapshotName) {
		GetSnapshotsRequestBuilder getSnapshotsRequestBuilder =
			new GetSnapshotsRequestBuilder(
				_elasticsearchFixture.getClient(), GetSnapshotsAction.INSTANCE);

		getSnapshotsRequestBuilder.setIgnoreUnavailable(true);
		getSnapshotsRequestBuilder.setRepository(_TEST_REPOSITORY_NAME);
		getSnapshotsRequestBuilder.setSnapshots(snapshotName);

		GetSnapshotsResponse getSnapshotsResponse =
			getSnapshotsRequestBuilder.get();

		return getSnapshotsResponse.getSnapshots();
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _TEST_REPOSITORY_NAME =
		"testRepositoryOperations";

	private ElasticsearchFixture _elasticsearchFixture;
	private IndicesAdminClient _indicesAdminClient;
	private SearchEngineAdapter _searchEngineAdapter;

}