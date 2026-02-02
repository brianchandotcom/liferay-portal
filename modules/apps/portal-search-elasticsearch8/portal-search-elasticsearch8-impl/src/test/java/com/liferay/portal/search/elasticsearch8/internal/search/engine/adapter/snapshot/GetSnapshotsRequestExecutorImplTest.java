/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.snapshot.GetSnapshotRequest;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index.AnalyzeIndexRequestExecutorTest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class GetSnapshotsRequestExecutorImplTest {

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
	public void testCreateGetSnapshotsRequest() {
		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest(
			"repository1");

		getSnapshotsRequest.setIgnoreUnavailable(true);
		getSnapshotsRequest.setSnapshotNames("snapshot1", "snapshot2");
		getSnapshotsRequest.setVerbose(true);

		GetSnapshotsRequestExecutor getSnapshotsRequestExecutor =
			new GetSnapshotsRequestExecutor(_elasticsearchFixture);

		GetSnapshotRequest elasticsearchGetSnapshotRequest =
			getSnapshotsRequestExecutor.createGetSnapshotRequest(
				getSnapshotsRequest);

		Assert.assertEquals(
			getSnapshotsRequest.getRepositoryName(),
			elasticsearchGetSnapshotRequest.repository());
		Assert.assertArrayEquals(
			getSnapshotsRequest.getSnapshotNames(),
			ArrayUtil.toStringArray(
				elasticsearchGetSnapshotRequest.snapshot()));
		Assert.assertEquals(
			getSnapshotsRequest.isIgnoreUnavailable(),
			elasticsearchGetSnapshotRequest.ignoreUnavailable());
		Assert.assertEquals(
			getSnapshotsRequest.isVerbose(),
			elasticsearchGetSnapshotRequest.verbose());
	}

	private ElasticsearchFixture _elasticsearchFixture;

}