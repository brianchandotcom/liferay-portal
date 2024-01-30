/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.indexing.OpenSearchIndexingFixture;
import com.liferay.portal.search.opensearch2.internal.indexing.OpenSearchIndexingFixtureFactory;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.indices.GetIndexRequest;
import org.opensearch.client.opensearch.indices.GetIndexResponse;
import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseOpenSearchTestCase {

	@BeforeClass
	public static void setUpBaseOpenSearchTestCaseClass() throws Exception {
		openSearchIndexingFixture =
			OpenSearchIndexingFixtureFactory.getInstance();

		openSearchIndexingFixture.setUp();

		openSearchConnectionManager =
			openSearchIndexingFixture.getOpenSearchConnectionManager();

		searchEngineAdapter =
			openSearchIndexingFixture.getSearchEngineAdapter();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		openSearchIndexingFixture.tearDown();
	}

	protected static GetIndexResponse getIndex(String... indices) {
		OpenSearchClient openSearchClient =
			openSearchConnectionManager.getOpenSearchClient();

		OpenSearchIndicesClient openSearchIndicesClient =
			openSearchClient.indices();

		try {
			return openSearchIndicesClient.get(
				GetIndexRequest.of(
					getIndexRequest -> getIndexRequest.index(
						ListUtil.fromArray(indices))));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected static final String TEST_INDEX_NAME = "test_index";

	protected static OpenSearchConnectionManager openSearchConnectionManager;
	protected static OpenSearchIndexingFixture openSearchIndexingFixture;
	protected static SearchEngineAdapter searchEngineAdapter;

}