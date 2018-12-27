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

	public ElasticsearchIndexingFixture(
		ElasticsearchFixture elasticsearchFixture, long companyId) {

		this(
			elasticsearchFixture, companyId,
			new IndexCreator(elasticsearchFixture));
	}

	public ElasticsearchIndexingFixture(
		ElasticsearchFixture elasticsearchFixture, long companyId,
		IndexCreator indexCreator) {

		_elasticsearchFixture = elasticsearchFixture;
		_companyId = companyId;
		_indexCreator = indexCreator;
	}

	public ElasticsearchFixture getElasticsearchFixture() {
		return _elasticsearchFixture;
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

	public void setFacetProcessor(
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_facetProcessor = facetProcessor;
	}

	@Override
	public void setUp() throws Exception {
		_elasticsearchFixture.setUp();

		createIndex();

		ElasticsearchClientResolver elasticsearchClientResolver =
			_elasticsearchFixture;

		ElasticsearchEngineAdapterFixture elasticsearchEngineAdapterFixture =
			new ElasticsearchEngineAdapterFixture(
				_elasticsearchFixture, _facetProcessor);

		SearchEngineAdapter searchEngineAdapter =
			elasticsearchEngineAdapterFixture.getSearchEngineAdapter();

		_indexSearcher = createIndexSearcher(
			elasticsearchClientResolver, searchEngineAdapter,
			_indexNameBuilder);

		_indexWriter = createIndexWriter(
			searchEngineAdapter, _indexNameBuilder);
	}

	@Override
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	protected QuerySuggester createElasticsearchQuerySuggester(
		ElasticsearchClientResolver elasticsearchClientResolver1,
		IndexNameBuilder indexNameBuilder1) {

		return new ElasticsearchQuerySuggester() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				indexNameBuilder = indexNameBuilder1;
				localization = _localization;
				suggesterTranslator = createElasticsearchSuggesterTranslator();
			}
		};
	}

	protected ElasticsearchSpellCheckIndexWriter
		createElasticsearchSpellCheckIndexWriter(
			final SearchEngineAdapter searchEngineAdapter1,
			final IndexNameBuilder indexNameBuilder1) {

		return new ElasticsearchSpellCheckIndexWriter() {
			{
				digester = new DigesterImpl();
				indexNameBuilder = indexNameBuilder1;
				localization = _localization;
				searchEngineAdapter = searchEngineAdapter1;
			}
		};
	}

	protected ElasticsearchSuggesterTranslator
		createElasticsearchSuggesterTranslator() {

		return new ElasticsearchSuggesterTranslator() {
			{
				phraseSuggesterTranslator = new PhraseSuggesterTranslatorImpl();
				termSuggesterTranslator = new TermSuggesterTranslatorImpl();
			}
		};
	}

	protected void createIndex() {
		_indexCreator.createIndex(
			new IndexName(_indexNameBuilder.getIndexName(_companyId)));
	}

	protected IndexSearcher createIndexSearcher(
		ElasticsearchClientResolver elasticsearchClientResolver,
		SearchEngineAdapter searchEngineAdapter1,
		IndexNameBuilder indexNameBuilder1) {

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
						elasticsearchClientResolver, indexNameBuilder));

				activate(
					_elasticsearchFixture.
						getElasticsearchConfigurationProperties());
			}
		};
	}

	protected IndexWriter createIndexWriter(
		final SearchEngineAdapter searchEngineAdapter1,
		final IndexNameBuilder indexNameBuilder1) {

		return new ElasticsearchIndexWriter() {
			{
				searchEngineAdapter = searchEngineAdapter1;
				indexNameBuilder = indexNameBuilder1;

				setSpellCheckIndexWriter(
					createElasticsearchSpellCheckIndexWriter(
						searchEngineAdapter1, indexNameBuilder1));
			}
		};
	}

	protected Props createProps() {
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

	protected static class TestIndexNameBuilder implements IndexNameBuilder {

		@Override
		public String getIndexName(long companyId) {
			return String.valueOf(companyId);
		}

	}

	private final long _companyId;
	private final ElasticsearchFixture _elasticsearchFixture;
	private FacetProcessor<SearchRequestBuilder> _facetProcessor =
		new DefaultFacetProcessor();
	private final IndexCreator _indexCreator;
	private final IndexNameBuilder _indexNameBuilder =
		new TestIndexNameBuilder();
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private final Localization _localization = new LocalizationImpl();

}