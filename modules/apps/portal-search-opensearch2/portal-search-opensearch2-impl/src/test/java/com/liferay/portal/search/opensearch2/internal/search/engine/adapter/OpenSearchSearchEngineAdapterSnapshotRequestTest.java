/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsResponse;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRepositoryDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotState;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.snapshot.SnapshotRequestExecutorFixture;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;
import org.opensearch.client.opensearch.snapshot.CreateRepositoryRequest;
import org.opensearch.client.opensearch.snapshot.DeleteRepositoryRequest;
import org.opensearch.client.opensearch.snapshot.GetRepositoryRequest;
import org.opensearch.client.opensearch.snapshot.GetRepositoryResponse;
import org.opensearch.client.opensearch.snapshot.GetSnapshotRequest;
import org.opensearch.client.opensearch.snapshot.GetSnapshotResponse;
import org.opensearch.client.opensearch.snapshot.OpenSearchSnapshotClient;
import org.opensearch.client.opensearch.snapshot.Repository;
import org.opensearch.client.opensearch.snapshot.RepositorySettings;
import org.opensearch.client.opensearch.snapshot.SnapshotInfo;
import org.opensearch.client.transport.endpoints.BooleanResponse;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class OpenSearchSearchEngineAdapterSnapshotRequestTest
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

		OpenSearchClient openSearchClient =
			openSearchConnectionManager.getOpenSearchClient();

		_openSearchIndicesClient = openSearchClient.indices();

		_openSearchSnapshotClient = openSearchClient.snapshot();

		_createIndex();
		_createRepository(_REPOSITORY_NAME, _REPOSITORY_NAME);
	}

	@After
	public void tearDown() throws Exception {
		_deleteIndex();
		_deleteRepository(_REPOSITORY_NAME);
	}

	@Test
	public void testCreateSnapshot() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			"Missing required property 'GetSnapshotResponse.total'");

		String snapshotName = "test_create_snapshot";

		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest(
			_REPOSITORY_NAME, snapshotName);

		createSnapshotRequest.setIndexNames(TEST_INDEX_NAME);

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

		List<SnapshotInfo> snapshotInfos = _getSnapshotInfo(snapshotName);

		Assert.assertEquals("Expected 1 SnapshotInfo", 1, snapshotInfos.size());

		SnapshotInfo snapshotInfo = snapshotInfos.get(0);

		List<String> indices = snapshotInfo.indices();

		Assert.assertEquals(
			snapshotName, createSnapshotRequest.getSnapshotName());
		Assert.assertArrayEquals(
			createSnapshotRequest.getIndexNames(), indices.toArray());

		_deleteSnapshot(_REPOSITORY_NAME, snapshotName);
	}

	@Test
	public void testCreateSnapshotRepository() {
		String repositoryName = "testCreateSnapshotRepository";
		String snapshotName = "test_create_snapshot_repository";

		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest =
			new CreateSnapshotRepositoryRequest(repositoryName, snapshotName);

		CreateSnapshotRepositoryResponse createSnapshotRepositoryResponse =
			_searchEngineAdapter.execute(createSnapshotRepositoryRequest);

		Assert.assertTrue(createSnapshotRepositoryResponse.isAcknowledged());

		GetRepositoryResponse getRepositoryResponse =
			_getGetRepositoriesResponse(new String[] {repositoryName});

		Map<String, Repository> repositories = getRepositoryResponse.result();

		Assert.assertEquals(
			"Expected 1 RepositoryMetadata", 1, repositories.size());

		Set<String> repositoryKeys = repositories.keySet();

		Iterator<String> iterator = repositoryKeys.iterator();

		Assert.assertEquals(repositoryName, iterator.next());

		Repository repository = repositories.get(repositoryName);

		Assert.assertEquals(
			SnapshotRepositoryDetails.FS_REPOSITORY_TYPE, repository.type());

		_deleteRepository(repositoryName);
	}

	@Test
	public void testDeleteSnapshot() throws Exception {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			"Missing required property 'GetSnapshotResponse.total'");

		String snapshotName = "test_delete_snapshot";

		_createSnapshot(_REPOSITORY_NAME, snapshotName, true, TEST_INDEX_NAME);

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				List<SnapshotInfo> snapshotInfos = _getSnapshotInfo(
					snapshotName);

				Assert.assertEquals(
					"Expected 1 SnapshotInfo", 1, snapshotInfos.size());

				DeleteSnapshotRequest deleteSnapshotRequest =
					new DeleteSnapshotRequest(_REPOSITORY_NAME, snapshotName);

				DeleteSnapshotResponse deleteSnapshotResponse =
					_searchEngineAdapter.execute(deleteSnapshotRequest);

				Assert.assertTrue(deleteSnapshotResponse.isAcknowledged());

				snapshotInfos = _getSnapshotInfo(snapshotName);

				Assert.assertTrue(snapshotInfos.isEmpty());

				return null;
			});
	}

	@Test
	public void testGetSnapshotRepositories() {
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest =
			new GetSnapshotRepositoriesRequest(_REPOSITORY_NAME);

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
			_REPOSITORY_NAME, snapshotRepositoryDetails.getName());
		Assert.assertEquals(
			SnapshotRepositoryDetails.FS_REPOSITORY_TYPE,
			snapshotRepositoryDetails.getType());
	}

	@Test
	public void testGetSnapshots() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			"Missing required property 'GetSnapshotResponse.total'");

		String snapshotName = "test_get_snapshots";

		_createSnapshot(_REPOSITORY_NAME, snapshotName, true, TEST_INDEX_NAME);

		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest(
			_REPOSITORY_NAME);

		getSnapshotsRequest.setSnapshotNames(snapshotName);
		getSnapshotsRequest.setVerbose(true);

		GetSnapshotsResponse getSnapshotsResponse =
			_searchEngineAdapter.execute(getSnapshotsRequest);

		List<SnapshotDetails> snapshotDetailsList =
			getSnapshotsResponse.getSnapshotDetails();

		Assert.assertEquals(
			"Expected 1 SnapshotDetails", 1, snapshotDetailsList.size());

		SnapshotDetails snapshotDetails = snapshotDetailsList.get(0);

		Assert.assertArrayEquals(
			new String[] {TEST_INDEX_NAME}, snapshotDetails.getIndexNames());
		Assert.assertEquals(
			SnapshotState.SUCCESS, snapshotDetails.getSnapshotState());

		_deleteSnapshot(_REPOSITORY_NAME, snapshotName);
	}

	@Test
	public void testRestoreSnapshot() {
		String snapshotName = "test_restore_snapshot";

		_createSnapshot(_REPOSITORY_NAME, snapshotName, true, TEST_INDEX_NAME);

		_deleteIndex();

		RestoreSnapshotRequest restoreSnapshotRequest =
			new RestoreSnapshotRequest(_REPOSITORY_NAME, snapshotName);

		restoreSnapshotRequest.setIndexNames(TEST_INDEX_NAME);

		_searchEngineAdapter.execute(restoreSnapshotRequest);

		Assert.assertTrue(
			"Indices not restored", _indicesExists(TEST_INDEX_NAME));

		_deleteSnapshot(_REPOSITORY_NAME, snapshotName);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected static SearchEngineAdapter createSearchEngineAdapter(
		OpenSearchConnectionManager openSearchConnectionManager) {

		SearchEngineAdapter searchEngineAdapter =
			new OpenSearchSearchEngineAdapterImpl();

		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_snapshotRequestExecutor",
			_createSnapshotRequestExecutor(openSearchConnectionManager));

		return searchEngineAdapter;
	}

	private static SnapshotRequestExecutor _createSnapshotRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		SnapshotRequestExecutorFixture snapshotRequestExecutorFixture =
			new SnapshotRequestExecutorFixture() {
				{
					setOpenSearchConnectionManager(openSearchConnectionManager);
				}
			};

		snapshotRequestExecutorFixture.setUp();

		return snapshotRequestExecutorFixture.getSnapshotRequestExecutor();
	}

	private void _createIndex() {
		try {
			_openSearchIndicesClient.create(
				CreateIndexRequest.of(
					createIndexRequest -> createIndexRequest.index(
						TEST_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _createRepository(String repositoryName, String snapshotName) {
		CreateRepositoryRequest.Builder builder =
			new CreateRepositoryRequest.Builder();

		builder.name(repositoryName);
		builder.settings(
			RepositorySettings.of(
				repositorySettings -> repositorySettings.location(
					snapshotName)));
		builder.type(SnapshotRepositoryDetails.FS_REPOSITORY_TYPE);

		try {
			_openSearchSnapshotClient.createRepository(builder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _createSnapshot(
		String repositoryName, String snapshotName, boolean waitForCompletion,
		String... indexNames) {

		org.opensearch.client.opensearch.snapshot.CreateSnapshotRequest.Builder
			builder =
				new org.opensearch.client.opensearch.snapshot.
					CreateSnapshotRequest.Builder();

		builder.indices(ListUtil.fromArray(indexNames));
		builder.repository(repositoryName);
		builder.snapshot(snapshotName);
		builder.waitForCompletion(waitForCompletion);

		try {
			_openSearchSnapshotClient.create(builder.build());
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

	private void _deleteRepository(String name) {
		try {
			_openSearchSnapshotClient.deleteRepository(
				DeleteRepositoryRequest.of(
					deleteRepositoryRequest -> deleteRepositoryRequest.name(
						name)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteSnapshot(String repositoryName, String snapshotName) {
		try {
			_openSearchSnapshotClient.delete(
				org.opensearch.client.opensearch.snapshot.DeleteSnapshotRequest.
					of(
						deleteSnapshotRequest ->
							deleteSnapshotRequest.repository(
								repositoryName
							).snapshot(
								snapshotName
							)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GetRepositoryResponse _getGetRepositoriesResponse(
		String[] repositories) {

		try {
			return _openSearchSnapshotClient.getRepository(
				GetRepositoryRequest.of(
					getRepositoryRequest -> getRepositoryRequest.name(
						ListUtil.fromArray(repositories))));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private List<SnapshotInfo> _getSnapshotInfo(String snapshotName) {
		GetSnapshotRequest.Builder builder = new GetSnapshotRequest.Builder();

		builder.ignoreUnavailable(true);
		builder.repository(_REPOSITORY_NAME);
		builder.snapshot(snapshotName);

		try {
			GetSnapshotResponse getSnapshotResponse =
				_openSearchSnapshotClient.get(builder.build());

			return getSnapshotResponse.snapshots();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private boolean _indicesExists(String indexName) {
		try {
			BooleanResponse booleanResponse = _openSearchIndicesClient.exists(
				ExistsRequest.of(
					existRequest -> existRequest.index(indexName)));

			return booleanResponse.value();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final String _REPOSITORY_NAME = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private OpenSearchIndicesClient _openSearchIndicesClient;
	private OpenSearchSnapshotClient _openSearchSnapshotClient;
	private SearchEngineAdapter _searchEngineAdapter;

}