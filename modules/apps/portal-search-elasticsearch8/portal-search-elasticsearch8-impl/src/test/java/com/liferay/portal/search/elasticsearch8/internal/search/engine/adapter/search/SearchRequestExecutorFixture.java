/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsResultsTranslatorImpl;

/**
 * @author Michael C. Han
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = _createSearchRequestExecutor(
			_elasticsearchClientResolver);
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private MultisearchSearchRequestExecutor
		_createMultisearchSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			SearchSearchResponseAssembler searchSearchResponseAssembler) {

		MultisearchSearchRequestExecutor multisearchSearchRequestExecutor =
			new MultisearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_searchSearchResponseAssembler",
			searchSearchResponseAssembler);

		return multisearchSearchRequestExecutor;
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ElasticsearchSearchRequestExecutor elasticsearchSearchRequestExecutor =
			new ElasticsearchSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			_createSearchSearchResponseAssembler();

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor,
			"_multisearchSearchRequestExecutor",
			_createMultisearchSearchRequestExecutor(
				elasticsearchClientResolver, searchSearchResponseAssembler));

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor, "_searchSearchRequestExecutor",
			_createSearchSearchRequestExecutor(
				elasticsearchClientResolver, searchSearchResponseAssembler));

		elasticsearchSearchRequestExecutor.activate();

		return elasticsearchSearchRequestExecutor;
	}

	private SearchSearchRequestExecutor _createSearchSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		SearchSearchResponseAssembler searchSearchResponseAssembler) {

		SearchSearchRequestExecutor searchSearchRequestExecutor =
			new SearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
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

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SearchRequestExecutor _searchRequestExecutor;

}