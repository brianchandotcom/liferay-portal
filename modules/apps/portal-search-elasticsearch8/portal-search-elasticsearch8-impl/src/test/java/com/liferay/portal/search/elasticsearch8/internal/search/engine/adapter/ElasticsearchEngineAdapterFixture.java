/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index.IndexRequestExecutorTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot.SnapshotRequestExecutorTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;

/**
 * @author Michael C. Han
 */
public class ElasticsearchEngineAdapterFixture {

	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			_elasticsearchClientResolver);
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		_searchRequestExecutorFixture = new SearchRequestExecutorFixture() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};

		documentRequestExecutorFixture.setUp();
		_searchRequestExecutorFixture.setUp();

		ElasticsearchSearchEngineAdapterImpl
			elasticsearchSearchEngineAdapterImpl =
				new ElasticsearchSearchEngineAdapterImpl() {
					{
						setThrowOriginalExceptions(true);
					}
				};

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngineAdapterImpl,
			"_elasticsearchClientResolver", elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngineAdapterImpl, "_documentRequestExecutor",
			documentRequestExecutorFixture.getDocumentRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngineAdapterImpl, "_indexRequestExecutor",
			IndexRequestExecutorTestUtil.createIndexRequestExecutor(
				elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngineAdapterImpl, "_searchRequestExecutor",
			_searchRequestExecutorFixture.getSearchRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchEngineAdapterImpl, "_snapshotRequestExecutor",
			SnapshotRequestExecutorTestUtil.createSnapshotRequestExecutor(
				elasticsearchClientResolver));

		elasticsearchSearchEngineAdapterImpl.activate();

		return elasticsearchSearchEngineAdapterImpl;
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private static SearchRequestExecutorFixture _searchRequestExecutorFixture;

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SearchEngineAdapter _searchEngineAdapter;

}