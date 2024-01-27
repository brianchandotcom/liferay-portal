/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactory;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactoryImpl;
import com.liferay.portal.search.opensearch2.internal.facet.FacetProcessor;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.cluster.ClusterRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index.IndexRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.snapshot.SnapshotRequestExecutorFixture;

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
			_openSearchConnectionManager, _getOpenSearchDocumentFactory(),
			_facetProcessor);
	}

	public void tearDown() {
		_searchRequestExecutorFixture.tearDown();
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		OpenSearchConnectionManager openSearchConnectionManager,
		OpenSearchDocumentFactory openSearchDocumentFactory,
		FacetProcessor<?> facetProcessor) {

		ClusterRequestExecutorFixture clusterRequestExecutorFixture =
			new ClusterRequestExecutorFixture() {
				{
					setOpenSearchConnectionManager(openSearchConnectionManager);
				}
			};

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setOpenSearchConnectionManager(openSearchConnectionManager);
					setOpenSearchDocumentFactory(openSearchDocumentFactory);
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

		SnapshotRequestExecutorFixture snapshotRequestExecutorFixture =
			new SnapshotRequestExecutorFixture() {
				{
					setOpenSearchConnectionManager(openSearchConnectionManager);
				}
			};

		clusterRequestExecutorFixture.setUp();
		documentRequestExecutorFixture.setUp();
		indexRequestExecutorFixture.setUp();
		_searchRequestExecutorFixture.setUp();
		snapshotRequestExecutorFixture.setUp();

		SearchEngineAdapter searchEngineAdapter =
			new OpenSearchSearchEngineAdapterImpl() {
				{
					setThrowOriginalExceptions(true);
				}
			};

		ReflectionTestUtil.setFieldValue(
			searchEngineAdapter, "_clusterRequestExecutor",
			clusterRequestExecutorFixture.getClusterRequestExecutor());
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
			snapshotRequestExecutorFixture.getSnapshotRequestExecutor());

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

	protected void setOpenSearchDocumentFactory(
		OpenSearchDocumentFactory openSearchDocumentFactory) {

		_openSearchDocumentFactory = openSearchDocumentFactory;
	}

	private OpenSearchDocumentFactory _getOpenSearchDocumentFactory() {
		if (_openSearchDocumentFactory != null) {
			return _openSearchDocumentFactory;
		}

		return new OpenSearchDocumentFactoryImpl();
	}

	private static SearchRequestExecutorFixture _searchRequestExecutorFixture;

	private FacetProcessor<SearchRequest.Builder> _facetProcessor;
	private OpenSearchConnectionManager _openSearchConnectionManager;
	private OpenSearchDocumentFactory _openSearchDocumentFactory;
	private SearchEngineAdapter _searchEngineAdapter;

}