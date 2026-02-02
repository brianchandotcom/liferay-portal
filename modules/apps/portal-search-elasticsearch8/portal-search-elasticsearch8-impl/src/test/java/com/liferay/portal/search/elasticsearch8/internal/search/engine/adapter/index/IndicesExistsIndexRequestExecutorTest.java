/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.indices.ExistsRequest;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class IndicesExistsIndexRequestExecutorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			IndicesExistsIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testIndexRequestTranslation() {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(_INDEX_NAME_1, _INDEX_NAME_2);

		IndicesExistsIndexRequestExecutor indicesExistsIndexRequestExecutor =
			new IndicesExistsIndexRequestExecutor(_elasticsearchFixture);

		ExistsRequest existsRequest =
			indicesExistsIndexRequestExecutor.createExistsRequest(
				indicesExistsIndexRequest);

		List<String> indices = existsRequest.index();

		Assert.assertEquals(String.join(", ", indices), 2, indices.size());
		Assert.assertEquals(_INDEX_NAME_1, indices.get(0));
		Assert.assertEquals(_INDEX_NAME_2, indices.get(1));
	}

	private static final String _INDEX_NAME_1 = "test_request_index1";

	private static final String _INDEX_NAME_2 = "test_request_index2";

	private ElasticsearchFixture _elasticsearchFixture;

}