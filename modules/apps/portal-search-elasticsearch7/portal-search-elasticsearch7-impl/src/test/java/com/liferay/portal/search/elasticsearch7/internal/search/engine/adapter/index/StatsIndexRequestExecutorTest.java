/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.index.StatsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.StatsIndexResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Felipe Lorenz
 */
public class StatsIndexRequestExecutorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			StatsIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testIndexRequestExecution() {
		StatsIndexRequest statsIndexRequest = new StatsIndexRequest(
			_INDEX_NAME);

		StatsIndexRequestExecutorImpl statsIndexRequestExecutorImpl =
			new StatsIndexRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			statsIndexRequestExecutorImpl, "_elasticsearchClientResolver",
			_elasticsearchFixture);
		ReflectionTestUtil.setFieldValue(
			statsIndexRequestExecutorImpl, "_jsonFactory",
			new JSONFactoryImpl());

		StatsIndexResponse statsIndexResponse =
			statsIndexRequestExecutorImpl.execute(statsIndexRequest);

		Assert.assertNotNull(statsIndexResponse);

		Assert.assertNotNull(statsIndexResponse.getTotalSpace());
	}

	private static final String _INDEX_NAME = "liferay";

	private ElasticsearchFixture _elasticsearchFixture;

}