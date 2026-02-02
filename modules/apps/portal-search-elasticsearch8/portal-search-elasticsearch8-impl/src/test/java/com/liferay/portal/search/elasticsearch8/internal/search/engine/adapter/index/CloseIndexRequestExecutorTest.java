/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch._types.Time;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
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

	@Ignore
	@Test
	public void testIndexRequestTranslation() {
		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(
			_INDEX_NAME);

		IndicesOptions indicesOptions1 = new IndicesOptions();

		indicesOptions1.setIgnoreUnavailable(true);

		closeIndexRequest.setIndicesOptions(indicesOptions1);

		closeIndexRequest.setTimeout(100);

		CloseIndexRequestExecutor closeIndexRequestExecutor =
			new CloseIndexRequestExecutor(_elasticsearchFixture);

		co.elastic.clients.elasticsearch.indices.CloseIndexRequest
			elasticsearchCloseIndexRequest =
				closeIndexRequestExecutor.createCloseIndexRequest(
					closeIndexRequest);

		Assert.assertArrayEquals(
			closeIndexRequest.getIndexNames(),
			ArrayUtil.toStringArray(elasticsearchCloseIndexRequest.index()));

		IndicesOptions indicesOptions2 = closeIndexRequest.getIndicesOptions();

		Assert.assertEquals(
			indicesOptions2.isIgnoreUnavailable(),
			elasticsearchCloseIndexRequest.ignoreUnavailable());

		Assert.assertEquals(
			indicesOptions2.isAllowNoIndices(),
			elasticsearchCloseIndexRequest.allowNoIndices());

		Assert.assertEquals(
			indicesOptions2.isExpandToOpenIndices(),
			elasticsearchCloseIndexRequest.expandWildcards());

		Assert.assertEquals(
			indicesOptions2.isExpandToClosedIndices(),
			elasticsearchCloseIndexRequest.expandWildcards());

		Time masterTimeout = elasticsearchCloseIndexRequest.masterTimeout();

		Assert.assertEquals(
			closeIndexRequest.getTimeout() + "ms", masterTimeout.time());

		Time timeout = elasticsearchCloseIndexRequest.timeout();

		Assert.assertEquals(
			closeIndexRequest.getTimeout() + "ms", timeout.time());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}