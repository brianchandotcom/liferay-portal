/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.facet.FacetProcessor;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.cluster.ClusterRequestExecutorTestUtil;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index.IndexRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.snapshot.SnapshotRequestExecutorTestUtil;

import org.opensearch.client.opensearch.core.SearchRequest;

/**
 * @author Michael C. Han
 */
public class OpenSearchEngineAdapterFixture {

	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			_openSearchConnectionManager, _facetProcessor);
	}

	public void tearDown() {
		_searchRequestExecutorFixture.tearDown();
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		OpenSearchConnectionManager openSearchConnectionManager,
		FacetProcessor<?> facetProcessor) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setOpenSearchConnectionManager(openSearchConnectionManager);
				}
			};

		IndexRequestExecutorFixture indexRequestExecutorFixture =
			new IndexRequestExecutorFixture() {
				{
					setOpenSearchConnectionManager(openSearchConnectionManager);
				}
			};

		_searchRequestExecutorFixture = new SearchRequestExecutorFixture() {
			{
				setFacetProcessor(facetProcessor);
				setOpenSearchConnectionManager(openSearchConnectionManager);
			}
		};

		documentRequestExecutorFixture.setUp();
		indexRequestExecutorFixture.setUp();
		_searchRequestExecutorFixture.setUp();

		SearchEngineAdapter searchEngineAdapter =
			new OpenSearchSearchEngineAdapterImpl() {
				{
					setThrowOriginalExceptions(true);
				}
			};

		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_clusterRequestExecutor",
			ClusterRequestExecutorTestUtil.createClusterRequestExecutor(
				openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_documentRequestExecutor",
			documentRequestExecutorFixture.getDocumentRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_indexRequestExecutor",
			indexRequestExecutorFixture.getIndexRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_searchRequestExecutor",
			_searchRequestExecutorFixture.getSearchRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_snapshotRequestExecutor",
			SnapshotRequestExecutorTestUtil.createSnapshotRequestExecutor(
				openSearchConnectionManager));

		return searchEngineAdapter;
	}

	protected void setFacetProcessor(
		FacetProcessor<SearchRequest.Builder> facetProcessor) {

		_facetProcessor = facetProcessor;
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private static SearchRequestExecutorFixture _searchRequestExecutorFixture;

	private FacetProcessor<SearchRequest.Builder> _facetProcessor;
	private OpenSearchConnectionManager _openSearchConnectionManager;
	private SearchEngineAdapter _searchEngineAdapter;

}