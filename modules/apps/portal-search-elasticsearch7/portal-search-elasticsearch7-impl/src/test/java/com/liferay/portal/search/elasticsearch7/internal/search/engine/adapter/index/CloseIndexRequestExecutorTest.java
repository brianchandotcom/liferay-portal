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
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.elasticsearch.core.TimeValue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class CloseIndexRequestExecutorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(
			_INDEX_NAME);

		IndicesOptions indicesOptions = new IndicesOptions();

		indicesOptions.setIgnoreUnavailable(true);

		closeIndexRequest.setIndicesOptions(indicesOptions);

		closeIndexRequest.setTimeout(100);

		CloseIndexRequestExecutorImpl closeIndexRequestExecutorImpl =
			new CloseIndexRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(_elasticsearchFixture);
					setIndicesOptionsTranslator(
						new IndicesOptionsTranslatorImpl());
				}
			};

		org.elasticsearch.client.indices.CloseIndexRequest
			elasticsearchCloseIndexRequest =
				closeIndexRequestExecutorImpl.createCloseIndexRequest(
					closeIndexRequest);

		Assert.assertArrayEquals(
			closeIndexRequest.getIndexNames(),
			elasticsearchCloseIndexRequest.indices());

		IndicesOptionsTranslator indicesOptionsTranslator =
			new IndicesOptionsTranslatorImpl();

		Assert.assertEquals(
			indicesOptionsTranslator.translate(
				closeIndexRequest.getIndicesOptions()),
			elasticsearchCloseIndexRequest.indicesOptions());

		Assert.assertEquals(
			TimeValue.timeValueMillis(closeIndexRequest.getTimeout()),
			elasticsearchCloseIndexRequest.masterNodeTimeout());

		Assert.assertEquals(
			TimeValue.timeValueMillis(closeIndexRequest.getTimeout()),
			elasticsearchCloseIndexRequest.timeout());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}