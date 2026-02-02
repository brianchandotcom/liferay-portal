/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.DeleteIndexRequest;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.snapshot.CreateRepositoryRequest;
import co.elastic.clients.elasticsearch.snapshot.DeleteRepositoryRequest;
import co.elastic.clients.elasticsearch.snapshot.ElasticsearchSnapshotClient;
import co.elastic.clients.elasticsearch.snapshot.GetRepositoryRequest;
import co.elastic.clients.elasticsearch.snapshot.GetRepositoryResponse;
import co.elastic.clients.elasticsearch.snapshot.GetSnapshotRequest;
import co.elastic.clients.elasticsearch.snapshot.GetSnapshotResponse;
import co.elastic.clients.elasticsearch.snapshot.Repository;
import co.elastic.clients.elasticsearch.snapshot.SnapshotInfo;
import co.elastic.clients.transport.endpoints.BooleanResponse;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot.SnapshotRequestExecutorFixture;
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
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

		ElasticsearchClient elasticsearchClient =
			_elasticsearchFixture.getElasticsearchClient();

		_indicesClient = elasticsearchClient.indices();

		_snapshotClient = elasticsearchClient.snapshot();

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
		String repositoryName = "testCreateSnapshotRepository";
		String snapshotName = "test_create_snapshot_repository";

		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest =
			new CreateSnapshotRepositoryRequest(repositoryName, snapshotName);

		CreateSnapshotRepositoryResponse createSnapshotRepositoryResponse =
			_searchEngineAdapter.execute(createSnapshotRepositoryRequest);

		Assert.assertTrue(createSnapshotRepositoryResponse.isAcknowledged());

		GetRepositoryResponse getRepositoryResponse = _getGetRepositoryResponse(
			new String[] {repositoryName});

		Map<String, Repository> repositories = getRepositoryResponse.result();

		Assert.assertEquals(
			"Expected 1 RepositoryMetadata", 1, repositories.size());

		Set<String> repositoryKeys = repositories.keySet();

		Iterator<String> iterator = repositoryKeys.iterator();

		Assert.assertEquals(repositoryName, iterator.next());

		Repository repository = repositories.get(repositoryName);

		Repository.Kind kind = repository._kind();

		Assert.assertEquals(
			SnapshotRepositoryDetails.FS_REPOSITORY_TYPE, kind.jsonValue());

		_deleteRepository(repositoryName);
	}

	@Ignore
	@Test
	public void testDeleteSnapshot() throws Exception {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			"Missing required property 'GetSnapshotResponse.total'");

		String snapshotName = "test_delete_snapshot";

		_createSnapshot(_TEST_REPOSITORY_NAME, snapshotName, true, _INDEX_NAME);

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				List<SnapshotInfo> snapshotInfos = _getSnapshotInfo(
					snapshotName);

				Assert.assertEquals(
					"Expected 1 SnapshotInfo", 1, snapshotInfos.size());

				DeleteSnapshotRequest deleteSnapshotRequest =
					new DeleteSnapshotRequest(
						_TEST_REPOSITORY_NAME, snapshotName);

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

	@Ignore
	@Test
	public void testGetSnapshots() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			"Missing required property 'GetSnapshotResponse.total'");

		String snapshotName = "test_get_snapshots";

		_createSnapshot(_TEST_REPOSITORY_NAME, snapshotName, true, _INDEX_NAME);

		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest(
			_TEST_REPOSITORY_NAME);

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
			new String[] {_INDEX_NAME}, snapshotDetails.getIndexNames());
		Assert.assertEquals(
			SnapshotState.SUCCESS, snapshotDetails.getSnapshotState());

		_deleteSnapshot(_TEST_REPOSITORY_NAME, snapshotName);
	}

	@Test
	public void testRestoreSnapshot() {
		String snapshotName = "test_restore_snapshot";

		_createSnapshot(_TEST_REPOSITORY_NAME, snapshotName, true, _INDEX_NAME);

		_deleteIndex();

		RestoreSnapshotRequest restoreSnapshotRequest =
			new RestoreSnapshotRequest(_TEST_REPOSITORY_NAME, snapshotName);

		restoreSnapshotRequest.setIndexNames(_INDEX_NAME);

		_searchEngineAdapter.execute(restoreSnapshotRequest);

		Assert.assertTrue("Indices not restored", _indicesExists(_INDEX_NAME));

		_deleteSnapshot(_TEST_REPOSITORY_NAME, snapshotName);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		SearchEngineAdapter searchEngineAdapter =
			new ElasticsearchSearchEngineAdapterImpl();

		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_snapshotRequestExecutor",
			_createSnapshotRequestExecutor(elasticsearchClientResolver));

		return searchEngineAdapter;
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
		try {
			_indicesClient.create(
				CreateIndexRequest.of(
					createIndexRequest -> createIndexRequest.index(
						_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _createRepository(String repositoryName, String snapshotName) {
		CreateRepositoryRequest.Builder builder =
			new CreateRepositoryRequest.Builder();

		builder.name(repositoryName);
		builder.repository(
			Repository.of(
				repository -> repository.fs(
					fs -> fs.settings(
						settings -> settings.location(snapshotName)))));

		try {
			_snapshotClient.createRepository(builder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _createSnapshot(
		String repositoryName, String snapshotName, boolean waitForCompletion,
		String... indexNames) {

		co.elastic.clients.elasticsearch.snapshot.CreateSnapshotRequest.Builder
			builder =
				new co.elastic.clients.elasticsearch.snapshot.
					CreateSnapshotRequest.Builder();

		builder.indices(ListUtil.fromArray(indexNames));
		builder.repository(repositoryName);
		builder.snapshot(snapshotName);
		builder.waitForCompletion(waitForCompletion);

		try {
			_snapshotClient.create(builder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteIndex() {
		try {
			_indicesClient.delete(
				DeleteIndexRequest.of(
					deleteIndexRequest -> deleteIndexRequest.index(
						_INDEX_NAME)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteRepository(String name) {
		try {
			_snapshotClient.deleteRepository(
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
			_snapshotClient.delete(
				co.elastic.clients.elasticsearch.snapshot.DeleteSnapshotRequest.
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

	private GetRepositoryResponse _getGetRepositoryResponse(
		String[] repositories) {

		try {
			return _snapshotClient.getRepository(
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
		builder.repository(_TEST_REPOSITORY_NAME);
		builder.snapshot(snapshotName);

		try {
			GetSnapshotResponse getSnapshotResponse = _snapshotClient.get(
				builder.build());

			return getSnapshotResponse.snapshots();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private boolean _indicesExists(String indexName) {
		try {
			BooleanResponse booleanResponse = _indicesClient.exists(
				ExistsRequest.of(
					existRequest -> existRequest.index(indexName)));

			return booleanResponse.value();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _TEST_REPOSITORY_NAME =
		"testRepositoryOperations";

	private static ElasticsearchFixture _elasticsearchFixture;

	private ElasticsearchIndicesClient _indicesClient;
	private SearchEngineAdapter _searchEngineAdapter;
	private ElasticsearchSnapshotClient _snapshotClient;

}