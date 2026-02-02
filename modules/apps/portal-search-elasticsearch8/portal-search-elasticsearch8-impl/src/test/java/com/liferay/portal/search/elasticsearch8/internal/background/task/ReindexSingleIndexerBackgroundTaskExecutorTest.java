/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.background.task;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch8.internal.index.FieldMappingAssert;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.ElasticsearchSearchEngineFixture;
import com.liferay.portal.search.test.util.background.task.BaseReindexSingleIndexerBackgroundTaskExecutorTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class ReindexSingleIndexerBackgroundTaskExecutorTest
	extends BaseReindexSingleIndexerBackgroundTaskExecutorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	public ReindexSingleIndexerBackgroundTaskExecutorTest() {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ReindexSingleIndexerBackgroundTaskExecutorTest.class.
					getSimpleName()
			).build();

		ElasticsearchSearchEngineFixture elasticsearchSearchEngineFixture =
			new ElasticsearchSearchEngineFixture(
				elasticsearchConnectionFixture);

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;

		_elasticsearchSearchEngineFixture = elasticsearchSearchEngineFixture;
	}

	@Ignore
	@Override
	@Test
	public void testFieldMappings() throws Exception {
	}

	@Override
	protected void assertFieldType(String fieldName, String fieldType)
		throws Exception {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchConnectionFixture.getElasticsearchClient();

		FieldMappingAssert.assertType(
			fieldType, elasticsearchClient.indices(), fieldName,
			getIndexName());
	}

	@Override
	protected ElasticsearchSearchEngineFixture getSearchEngineFixture() {
		return _elasticsearchSearchEngineFixture;
	}

	private final ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;
	private final ElasticsearchSearchEngineFixture
		_elasticsearchSearchEngineFixture;

}