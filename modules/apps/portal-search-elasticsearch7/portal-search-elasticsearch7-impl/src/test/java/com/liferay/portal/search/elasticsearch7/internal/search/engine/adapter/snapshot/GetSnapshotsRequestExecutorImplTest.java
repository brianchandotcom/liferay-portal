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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.AnalyzeIndexRequestExecutorTest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest;

import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequestBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class GetSnapshotsRequestExecutorImplTest {

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

		GetSnapshotsRequestExecutorImpl getSnapshotsRequestExecutorImpl =
			new GetSnapshotsRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
				}
			};

		GetSnapshotsRequestBuilder getSnapshotsRequestBuilder =
			getSnapshotsRequestExecutorImpl.createGetSnapshotsRequest(
				getSnapshotsRequest);

		org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest
			elasticsearchGetSnapshotsRequest =
				getSnapshotsRequestBuilder.request();

		Assert.assertEquals(
			getSnapshotsRequest.isIgnoreUnavailable(),
			elasticsearchGetSnapshotsRequest.ignoreUnavailable());
		Assert.assertEquals(
			getSnapshotsRequest.getRepositoryName(),
			elasticsearchGetSnapshotsRequest.repository());
		Assert.assertArrayEquals(
			getSnapshotsRequest.getSnapshotNames(),
			elasticsearchGetSnapshotsRequest.snapshots());
		Assert.assertEquals(
			getSnapshotsRequest.isVerbose(),
			elasticsearchGetSnapshotsRequest.verbose());
	}

	private ElasticsearchFixture _elasticsearchFixture;

}