/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.internal.highlight.HighlightFieldBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsResultsTranslatorImpl;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.search.response.SearchResponseTranslator;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = _createSearchRequestExecutor(
			_openSearchConnectionManager);
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private MultisearchSearchRequestExecutor
		_createMultisearchSearchRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager,
			SearchSearchResponseAssembler searchSearchResponseAssembler) {

		MultisearchSearchRequestExecutor multisearchSearchRequestExecutor =
			new MultisearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_searchSearchResponseAssembler",
			searchSearchResponseAssembler);

		return multisearchSearchRequestExecutor;
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		OpenSearchSearchRequestExecutor openSearchSearchRequestExecutor =
			new OpenSearchSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			openSearchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			_createSearchSearchResponseAssembler();

		ReflectionTestUtil.setFieldValue(
			openSearchSearchRequestExecutor,
			"_multisearchSearchRequestExecutor",
			_createMultisearchSearchRequestExecutor(
				openSearchConnectionManager, searchSearchResponseAssembler));

		ReflectionTestUtil.setFieldValue(
			openSearchSearchRequestExecutor, "_searchSearchRequestExecutor",
			_createSearchSearchRequestExecutor(
				openSearchConnectionManager, searchSearchResponseAssembler));

		openSearchSearchRequestExecutor.activate();

		return openSearchSearchRequestExecutor;
	}

	private SearchSearchRequestExecutor _createSearchSearchRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		SearchSearchResponseAssembler searchSearchResponseAssembler) {

		SearchSearchRequestExecutor searchSearchRequestExecutor =
			new SearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_searchSearchResponseAssembler",
			searchSearchResponseAssembler);

		return searchSearchRequestExecutor;
	}

	private SearchSearchResponseAssembler
		_createSearchSearchResponseAssembler() {

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			new SearchSearchResponseAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_highlightFieldBuilderFactory",
			new HighlightFieldBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_searchHitBuilderFactory",
			new SearchHitBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_searchHitsBuilderFactory",
			new SearchHitsBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_searchResponseTranslator",
			new SearchResponseTranslator(new StatsResultsTranslatorImpl()));

		return searchSearchResponseAssembler;
	}

	private OpenSearchConnectionManager _openSearchConnectionManager;
	private SearchRequestExecutor _searchRequestExecutor;

}