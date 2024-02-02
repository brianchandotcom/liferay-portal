/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.background.task;

import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.connection.TestOpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.index.FieldMappingAssert;
import com.liferay.portal.search.opensearch2.internal.search.engine.OpenSearchSearchEngineFixture;
import com.liferay.portal.search.test.util.background.task.BaseReindexSingleIndexerBackgroundTaskExecutorTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;

import org.opensearch.client.opensearch.OpenSearchClient;

/**
 * @author Adam Brandizzi
 */
public class ReindexSingleIndexerBackgroundTaskExecutorTest
	extends BaseReindexSingleIndexerBackgroundTaskExecutorTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	public ReindexSingleIndexerBackgroundTaskExecutorTest() {
		_openSearchSearchEngineFixture = new OpenSearchSearchEngineFixture(
			_openSearchConnectionManager);
	}

	@Override
	protected void assertFieldType(String fieldName, String fieldType)
		throws Exception {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient();

		FieldMappingAssert.assertType(
			fieldType, fieldName, getIndexName(), openSearchClient.indices());
	}

	@Override
	protected OpenSearchSearchEngineFixture getSearchEngineFixture() {
		return _openSearchSearchEngineFixture;
	}

	private final OpenSearchConnectionManager _openSearchConnectionManager =
		new TestOpenSearchConnectionManager();
	private final OpenSearchSearchEngineFixture _openSearchSearchEngineFixture;

}