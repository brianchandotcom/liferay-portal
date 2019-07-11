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
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.search.engine.adapter.index.OpenIndexRequest;

import org.elasticsearch.action.admin.indices.open.OpenIndexRequestBuilder;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.common.unit.TimeValue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class OpenIndexRequestExecutorTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			CreateIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testIndexRequestTranslation() {
		OpenIndexRequest openIndexRequest = new OpenIndexRequest(_INDEX_NAME);

		IndicesOptions indicesOptions = new IndicesOptions();

		indicesOptions.setIgnoreUnavailable(true);

		openIndexRequest.setIndicesOptions(indicesOptions);

		openIndexRequest.setTimeout(100);
		openIndexRequest.setWaitForActiveShards(200);

		OpenIndexRequestExecutorImpl openIndexRequestExecutorImpl =
			new OpenIndexRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
					setIndicesOptionsTranslator(
						new IndicesOptionsTranslatorImpl());
				}
			};

		OpenIndexRequestBuilder openIndexRequestBuilder =
			openIndexRequestExecutorImpl.createOpenIndexRequestBuilder(
				openIndexRequest);

		org.elasticsearch.action.admin.indices.open.OpenIndexRequest
			elastichsearchOpenIndexRequest = openIndexRequestBuilder.request();

		Assert.assertArrayEquals(
			openIndexRequest.getIndexNames(),
			elastichsearchOpenIndexRequest.indices());

		IndicesOptionsTranslator indicesOptionsTranslator =
			new IndicesOptionsTranslatorImpl();

		Assert.assertEquals(
			indicesOptionsTranslator.translate(
				openIndexRequest.getIndicesOptions()),
			elastichsearchOpenIndexRequest.indicesOptions());

		Assert.assertEquals(
			TimeValue.timeValueMillis(openIndexRequest.getTimeout()),
			elastichsearchOpenIndexRequest.masterNodeTimeout());

		Assert.assertEquals(
			TimeValue.timeValueMillis(openIndexRequest.getTimeout()),
			elastichsearchOpenIndexRequest.timeout());

		Assert.assertEquals(
			ActiveShardCount.from(openIndexRequest.getWaitForActiveShards()),
			elastichsearchOpenIndexRequest.waitForActiveShards());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}