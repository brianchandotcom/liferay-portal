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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.EmbeddedElasticsearchConnection;
import com.liferay.portal.search.elasticsearch7.internal.index.CompanyIdIndexNameBuilder;
import com.liferay.portal.search.elasticsearch7.internal.index.CompanyIndexFactory;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.ElasticsearchEngineAdapterFixture;
import com.liferay.portal.search.elasticsearch7.internal.settings.IndexSettingsContributorHelper;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.io.IOException;

import java.util.List;

import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;
import org.elasticsearch.snapshots.SnapshotInfo;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
@Ignore
public class ElasticsearchSearchEngineTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		_elasticsearchConnectionManager = createElasticsearchConnectionManager(
			_elasticsearchFixture.getEmbeddedElasticsearchConnection());

		ElasticsearchEngineAdapterFixture elasticsearchEngineAdapterFixture =
			new ElasticsearchEngineAdapterFixture() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
				}
			};

		elasticsearchEngineAdapterFixture.setUp();

		_searchEngineAdapter =
			elasticsearchEngineAdapterFixture.getSearchEngineAdapter();
	}

	@Test
	public void testBackup() throws SearchException {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			createElasticsearchSearchEngine(
				_elasticsearchConnectionManager, _searchEngineAdapter);

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		elasticsearchSearchEngine.backup(companyId, "backup_test");

		GetSnapshotsResponse getSnapshotsResponse = getGetSnapshotsResponse(
			"liferay_backup", new String[] {"backup_test"}, true);

		List<SnapshotInfo> snapshotInfos = getSnapshotsResponse.getSnapshots();

		Assert.assertTrue(snapshotInfos.size() == 1);

		deleteSnapshot("liferay_backup", "backup_test");
	}

	@Test
	public void testInitializeAfterReconnect() {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			createElasticsearchSearchEngine(
				_elasticsearchConnectionManager, _searchEngineAdapter);

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		reconnect(_elasticsearchConnectionManager);

		elasticsearchSearchEngine.initialize(companyId);
	}

	@Test
	public void testRestore() throws SearchException {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			createElasticsearchSearchEngine(
				_elasticsearchConnectionManager, _searchEngineAdapter);

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		elasticsearchSearchEngine.createBackupRepository();

		createSnapshot(
			"liferay_backup", "restore_test", true, String.valueOf(companyId));

		elasticsearchSearchEngine.restore(companyId, "restore_test");

		deleteSnapshot("liferay_backup", "restore_test");
	}

	protected static CompanyIndexFactory createCompanyIndexFactory() {
		CompanyIndexFactory companyIndexFactory = new CompanyIndexFactory() {
			{
				indexNameBuilder = createIndexNameBuilder();
				jsonFactory = new JSONFactoryImpl();
			}
		};

		ReflectionTestUtil.setFieldValue(
			companyIndexFactory, "_indexSettingsContributorHelper",
			new IndexSettingsContributorHelper());

		return companyIndexFactory;
	}

	protected static IndexNameBuilder createIndexNameBuilder() {
		return new CompanyIdIndexNameBuilder() {
			{
				setIndexNamePrefix(null);
			}
		};
	}

	protected ElasticsearchConnectionManager
		createElasticsearchConnectionManager(
			EmbeddedElasticsearchConnection embeddedElasticsearchConnection) {

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new ElasticsearchConnectionManager();

		elasticsearchConnectionManager.setEmbeddedElasticsearchConnection(
			embeddedElasticsearchConnection);

		return elasticsearchConnectionManager;
	}

	protected ElasticsearchSearchEngine createElasticsearchSearchEngine(
		final ElasticsearchConnectionManager elasticsearchConnectionManager,
		final SearchEngineAdapter searchEngineAdapter) {

		return new ElasticsearchSearchEngine() {
			{
				setIndexFactory(createCompanyIndexFactory());
				setIndexNameBuilder(String::valueOf);
				setElasticsearchConnectionManager(
					elasticsearchConnectionManager);
				setSearchEngineAdapter(searchEngineAdapter);
			}
		};
	}

	protected void createSnapshot(
		String repositoryName, String snapshotName, boolean waitForCompletion,
		String... indexNames) {

		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest(
			repositoryName, snapshotName);

		createSnapshotRequest.indices(indexNames);
		createSnapshotRequest.waitForCompletion(waitForCompletion);

		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionManager.getRestHighLevelClient();

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			snapshotClient.create(
				createSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected void deleteSnapshot(String repository, String snapshot) {
		DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest(
			repository, snapshot);

		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionManager.getRestHighLevelClient();

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			snapshotClient.delete(
				deleteSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected GetSnapshotsResponse getGetSnapshotsResponse(
		String repository, String[] snapshots, boolean ignoreUnavailable) {

		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest();

		getSnapshotsRequest.ignoreUnavailable(ignoreUnavailable);
		getSnapshotsRequest.repository(repository);
		getSnapshotsRequest.snapshots(snapshots);

		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionManager.getRestHighLevelClient();

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.get(
				getSnapshotsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected void reconnect(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		ElasticsearchConnection elasticsearchConnection =
			elasticsearchConnectionManager.getElasticsearchConnection();

		elasticsearchConnection.close();
	}

	private static ElasticsearchFixture _elasticsearchFixture;

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private SearchEngineAdapter _searchEngineAdapter;

}