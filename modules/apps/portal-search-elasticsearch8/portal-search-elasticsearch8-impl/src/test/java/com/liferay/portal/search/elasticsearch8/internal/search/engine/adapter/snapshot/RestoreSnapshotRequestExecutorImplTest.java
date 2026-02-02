/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.snapshot.RestoreRequest;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index.AnalyzeIndexRequestExecutorTest;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class RestoreSnapshotRequestExecutorImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			AnalyzeIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testCreateRestoreSnapshotRequest() {
		RestoreSnapshotRequest restoreSnapshotRequest =
			new RestoreSnapshotRequest("repositoryName", "snapshotName");

		restoreSnapshotRequest.setIncludeAliases(true);
		restoreSnapshotRequest.setIndexNames("index1", "index2");
		restoreSnapshotRequest.setPartialRestore(true);
		restoreSnapshotRequest.setRestoreGlobalState(true);
		restoreSnapshotRequest.setWaitForCompletion(true);

		RestoreSnapshotRequestExecutor restoreSnapshotRequestExecutor =
			new RestoreSnapshotRequestExecutor(_elasticsearchFixture);

		RestoreRequest restoreRequest =
			restoreSnapshotRequestExecutor.createRestoreRequest(
				restoreSnapshotRequest);

		Assert.assertArrayEquals(
			restoreSnapshotRequest.getIndexNames(),
			ArrayUtil.toStringArray(restoreRequest.indices()));
		Assert.assertEquals(
			restoreSnapshotRequest.getRepositoryName(),
			restoreRequest.repository());
		Assert.assertEquals(
			restoreSnapshotRequest.getSnapshotName(),
			restoreRequest.snapshot());
		Assert.assertEquals(
			restoreSnapshotRequest.isIncludeAliases(),
			restoreRequest.includeAliases());
		Assert.assertEquals(
			restoreSnapshotRequest.isPartialRestore(),
			restoreRequest.partial());
		Assert.assertEquals(
			restoreSnapshotRequest.isRestoreGlobalState(),
			restoreRequest.includeGlobalState());
		Assert.assertEquals(
			restoreSnapshotRequest.isWaitForCompletion(),
			restoreRequest.waitForCompletion());
	}

	private ElasticsearchFixture _elasticsearchFixture;

}