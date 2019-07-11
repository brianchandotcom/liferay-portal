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
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest;

import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequestBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class CreateSnapshotRequestExecutorImplTest {

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
	public void testCreatePutRepositoryRequestBuilder() {
		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest(
			"name", "location");

		createSnapshotRequest.setIndexNames("index1", "index2");
		createSnapshotRequest.setWaitForCompletion(true);

		CreateSnapshotRequestExecutorImpl createSnapshotRequestExecutorImpl =
			new CreateSnapshotRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
				}
			};

		CreateSnapshotRequestBuilder createSnapshotRequestBuilder =
			createSnapshotRequestExecutorImpl.
				createCreateSnapshotRequestBuilder(createSnapshotRequest);

		org.elasticsearch.action.admin.cluster.snapshots.create.
			CreateSnapshotRequest elasticsearchCreateSnapshotRequest =
				createSnapshotRequestBuilder.request();

		Assert.assertArrayEquals(
			createSnapshotRequest.getIndexNames(),
			elasticsearchCreateSnapshotRequest.indices());
		Assert.assertEquals(
			createSnapshotRequest.getRepositoryName(),
			elasticsearchCreateSnapshotRequest.repository());
		Assert.assertEquals(
			createSnapshotRequest.getSnapshotName(),
			elasticsearchCreateSnapshotRequest.snapshot());
		Assert.assertEquals(
			createSnapshotRequest.isWaitForCompletion(),
			elasticsearchCreateSnapshotRequest.waitForCompletion());
	}

	private ElasticsearchFixture _elasticsearchFixture;

}