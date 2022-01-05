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

import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ElasticsearchSearchEngineBackupTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchSearchEngineBackupTest.class.getSimpleName()
			).build();

		ElasticsearchSearchEngineFixture elasticsearchSearchEngineFixture =
			new ElasticsearchSearchEngineFixture(
				elasticsearchConnectionFixture);

		elasticsearchSearchEngineFixture.setUp();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;

		_elasticsearchSearchEngineFixture = elasticsearchSearchEngineFixture;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchSearchEngineFixture.tearDown();
	}

	@Test
	public void testBackup() throws SearchException {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		elasticsearchSearchEngine.backup(companyId, "backup_test");

		GetSnapshotsResponse getSnapshotsResponse = _getGetSnapshotsResponse(
			"liferay_backup", new String[] {"backup_test"}, true);

		List<SnapshotInfo> snapshotInfos = getSnapshotsResponse.getSnapshots();

		Assert.assertTrue(snapshotInfos.size() == 1);

		_deleteSnapshot("liferay_backup", "backup_test");
	}

	@Test
	public void testRestore() throws SearchException {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		elasticsearchSearchEngine.createBackupRepository();

		_createSnapshot(
			"liferay_backup", "restore_test", true, String.valueOf(companyId));

		elasticsearchSearchEngine.restore(companyId, "restore_test");

		_deleteSnapshot("liferay_backup", "restore_test");
	}

	protected SnapshotClient getSnapshotClient() {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionFixture.getRestHighLevelClient();

		return restHighLevelClient.snapshot();
	}

	private void _createSnapshot(
		String repositoryName, String snapshotName, boolean waitForCompletion,
		String... indexNames) {

		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest(
			repositoryName, snapshotName);

		createSnapshotRequest.indices(indexNames);
		createSnapshotRequest.waitForCompletion(waitForCompletion);

		SnapshotClient snapshotClient = getSnapshotClient();

		try {
			snapshotClient.create(
				createSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteSnapshot(String repository, String snapshot) {
		DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest(
			repository, snapshot);

		SnapshotClient snapshotClient = getSnapshotClient();

		try {
			snapshotClient.delete(
				deleteSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private GetSnapshotsResponse _getGetSnapshotsResponse(
		String repository, String[] snapshots, boolean ignoreUnavailable) {

		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest();

		getSnapshotsRequest.ignoreUnavailable(ignoreUnavailable);
		getSnapshotsRequest.repository(repository);
		getSnapshotsRequest.snapshots(snapshots);

		SnapshotClient snapshotClient = getSnapshotClient();

		try {
			return snapshotClient.get(
				getSnapshotsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;
	private static ElasticsearchSearchEngineFixture
		_elasticsearchSearchEngineFixture;

}