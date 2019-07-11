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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.FlushIndexRequest;

import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequestBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class FlushIndexRequestExecutorTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			FlushIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testIndexRequestTranslation() {
		FlushIndexRequest flushIndexRequest = new FlushIndexRequest(
			_INDEX_NAME);

		flushIndexRequest.setForce(true);
		flushIndexRequest.setWaitIfOngoing(true);

		FlushIndexRequestExecutorImpl flushIndexRequestExecutorImpl =
			new FlushIndexRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);

					setIndexRequestShardFailureTranslator(
						new IndexRequestShardFailureTranslatorImpl());
				}
			};

		FlushRequestBuilder flushRequestBuilder =
			flushIndexRequestExecutorImpl.createFlushRequestBuilder(
				flushIndexRequest);

		FlushRequest flushRequest = flushRequestBuilder.request();

		Assert.assertArrayEquals(
			new String[] {_INDEX_NAME}, flushRequest.indices());
		Assert.assertTrue(flushRequest.force());
		Assert.assertTrue(flushRequest.waitIfOngoing());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}