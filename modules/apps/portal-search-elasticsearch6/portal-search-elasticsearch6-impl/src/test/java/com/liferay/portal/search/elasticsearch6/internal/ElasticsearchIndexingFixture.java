/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch6.internal.facet.DefaultFacetProcessor;
import com.liferay.portal.search.elasticsearch6.internal.facet.FacetProcessor;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexNameBuilder;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.ElasticsearchEngineAdapterFixture;
import com.liferay.portal.search.elasticsearch6.internal.suggest.ElasticsearchSuggesterTranslator;
import com.liferay.portal.search.elasticsearch6.internal.suggest.PhraseSuggesterTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.suggest.TermSuggesterTranslatorImpl;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.LocalizationImpl;

import org.elasticsearch.action.search.SearchRequestBuilder;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class ElasticsearchIndexingFixture implements IndexingFixture {

	public ElasticsearchFixture getElasticsearchFixture() {
		return elasticsearchFixture;
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	@Override
	public boolean isSearchEngineAvailable() {
		return true;
	}

	@Override
	public void setUp() throws Exception {
		elasticsearchFixture.setUp();

		ElasticsearchEngineAdapterFixture elasticsearchEngineAdapterFixture =
			createElasticsearchEngineAdapterFixture(
				elasticsearchFixture, getFacetProcessor());

		elasticsearchEngineAdapterFixture.setUp();

		SearchEngineAdapter searchEngineAdapter =
			elasticsearchEngineAdapterFixture.getSearchEngineAdapter();

		IndexNameBuilder indexNameBuilder = String::valueOf;

		Localization localization = new LocalizationImpl();

		ElasticsearchIndexSearcher elasticsearchIndexSearcher =
			createIndexSearcher(
				elasticsearchFixture, searchEngineAdapter, indexNameBuilder,
				localization);

		IndexWriter indexWriter = createIndexWriter(
			searchEngineAdapter, indexNameBuilder, localization);

		_indexSearcher = elasticsearchIndexSearcher;
		_indexWriter = indexWriter;

		elasticsearchIndexSearcher.activate(
			elasticsearchFixture.getElasticsearchConfigurationProperties());

		createIndex(indexNameBuilder);
	}

	@Override
	public void tearDown() throws Exception {
		elasticsearchFixture.tearDown();
	}

	protected static ElasticsearchEngineAdapterFixture
		createElasticsearchEngineAdapterFixture(
			ElasticsearchClientResolver elasticsearchClientResolver1,
			FacetProcessor facetProcessor1) {

		return new ElasticsearchEngineAdapterFixture() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				facetProcessor = facetProcessor1;
			}
		};
	}

	protected static QuerySuggester createElasticsearchQuerySuggester(
		ElasticsearchClientResolver elasticsearchClientResolver1,
		IndexNameBuilder indexNameBuilder1, Localization localization1) {

		return new ElasticsearchQuerySuggester() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indexNameBuilder = indexNameBuilder1;
				localization = localization1;
				suggesterTranslator = createElasticsearchSuggesterTranslator();
			}
		};
	}

	protected static ElasticsearchSpellCheckIndexWriter
		createElasticsearchSpellCheckIndexWriter(
			SearchEngineAdapter searchEngineAdapter1,
			IndexNameBuilder indexNameBuilder1, Localization localization1) {

		return new ElasticsearchSpellCheckIndexWriter() {
			{
				digester = new DigesterImpl();
				indexNameBuilder = indexNameBuilder1;
				localization = localization1;
				searchEngineAdapter = searchEngineAdapter1;
			}
		};
	}

	protected static ElasticsearchSuggesterTranslator
		createElasticsearchSuggesterTranslator() {

		return new ElasticsearchSuggesterTranslator() {
			{
				phraseSuggesterTranslator = new PhraseSuggesterTranslatorImpl();
				termSuggesterTranslator = new TermSuggesterTranslatorImpl();
			}
		};
	}

	protected static ElasticsearchIndexSearcher createIndexSearcher(
		ElasticsearchFixture elasticsearchFixture,
		SearchEngineAdapter searchEngineAdapter1,
		IndexNameBuilder indexNameBuilder1, Localization localization) {

		return new ElasticsearchIndexSearcher() {
			{
				indexNameBuilder = indexNameBuilder1;
				props = createProps();
				searchEngineAdapter = searchEngineAdapter1;
				searchRequestBuilderFactory =
					new SearchRequestBuilderFactoryImpl();
				searchResponseBuilderFactory =
					new SearchResponseBuilderFactoryImpl();

				setQuerySuggester(
					createElasticsearchQuerySuggester(
						elasticsearchFixture, indexNameBuilder1, localization));

				activate(
					elasticsearchFixture.
						getElasticsearchConfigurationProperties());
			}
		};
	}

	protected static IndexWriter createIndexWriter(
		final SearchEngineAdapter searchEngineAdapter1,
		final IndexNameBuilder indexNameBuilder1, Localization localization) {

		return new ElasticsearchIndexWriter() {
			{
				searchEngineAdapter = searchEngineAdapter1;
				indexNameBuilder = indexNameBuilder1;

				setSpellCheckIndexWriter(
					createElasticsearchSpellCheckIndexWriter(
						searchEngineAdapter1, indexNameBuilder1, localization));
			}
		};
	}

	protected static Props createProps() {
		Props props = Mockito.mock(Props.class);

		Mockito.doReturn(
			"20"
		).when(
			props
		).get(
			PropsKeys.INDEX_SEARCH_LIMIT
		);

		return props;
	}

	protected void createIndex(IndexNameBuilder indexNameBuilder) {
		IndexCreator indexCreator = getIndexCreator();

		indexCreator.createIndex(
			new IndexName(indexNameBuilder.getIndexName(companyId)));
	}

	protected FacetProcessor getFacetProcessor() {
		if (facetProcessor != null) {
			return facetProcessor;
		}

		return new DefaultFacetProcessor();
	}

	protected IndexCreator getIndexCreator() {
		if (indexCreator != null) {
			return indexCreator;
		}

		return new IndexCreator(elasticsearchFixture);
	}

	protected long companyId;
	protected ElasticsearchFixture elasticsearchFixture;
	protected FacetProcessor<SearchRequestBuilder> facetProcessor;
	protected IndexCreator indexCreator;

	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;

}