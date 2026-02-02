/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.WaitForActiveShards;
import co.elastic.clients.elasticsearch.indices.OpenRequest;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.search.engine.adapter.index.OpenIndexRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class OpenIndexRequestExecutorTest {

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
		OpenIndexRequest openIndexRequest = new OpenIndexRequest(_INDEX_NAME);

		IndicesOptions indicesOptions = new IndicesOptions();

		indicesOptions.setIgnoreUnavailable(true);

		openIndexRequest.setIndicesOptions(indicesOptions);

		openIndexRequest.setTimeout(100);
		openIndexRequest.setWaitForActiveShards(200);

		OpenIndexRequestExecutor openIndexRequestExecutor =
			new OpenIndexRequestExecutor(_elasticsearchFixture);

		OpenRequest openRequest = openIndexRequestExecutor.createOpenRequest(
			openIndexRequest);

		Assert.assertArrayEquals(
			openIndexRequest.getIndexNames(),
			ArrayUtil.toStringArray(openRequest.index()));

		Time masterTimeout = openRequest.masterTimeout();

		Assert.assertEquals(
			openIndexRequest.getTimeout() + "ms", masterTimeout.time());

		Time timeout = openRequest.timeout();

		Assert.assertEquals(
			openIndexRequest.getTimeout() + "ms", timeout.time());

		WaitForActiveShards waitForActiveShards =
			openRequest.waitForActiveShards();

		Integer count = waitForActiveShards.count();

		Assert.assertEquals(
			openIndexRequest.getWaitForActiveShards(), count.intValue());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}