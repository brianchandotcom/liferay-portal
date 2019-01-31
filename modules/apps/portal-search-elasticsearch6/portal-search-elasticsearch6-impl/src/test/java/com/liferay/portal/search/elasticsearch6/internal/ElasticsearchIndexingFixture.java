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
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexCreationHelper;
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
	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	@Override
	public boolean isSearchEngineAvailable() {
		return true;
	}

	@Override
	public void setUp() throws Exception {
		_elasticsearchFixture.setUp();

		ElasticsearchEngineAdapterFixture elasticsearchEngineAdapterFixture =
			createElasticsearchEngineAdapterFixture(
				_elasticsearchFixture, getFacetProcessor());

		elasticsearchEngineAdapterFixture.setUp();

		SearchEngineAdapter searchEngineAdapter =
			elasticsearchEngineAdapterFixture.getSearchEngineAdapter();

		IndexNameBuilder indexNameBuilder = String::valueOf;

		Localization localization = new LocalizationImpl();

		ElasticsearchIndexSearcher elasticsearchIndexSearcher =
			createIndexSearcher(
				_elasticsearchFixture, searchEngineAdapter, indexNameBuilder,
				localization);

		IndexWriter indexWriter = createIndexWriter(
			searchEngineAdapter, indexNameBuilder, localization);

		_indexSearcher = elasticsearchIndexSearcher;
		_indexWriter = indexWriter;
		_searchEngineAdapter = searchEngineAdapter;

		elasticsearchIndexSearcher.activate(
			_elasticsearchFixture.getElasticsearchConfigurationProperties());

		createIndex(indexNameBuilder);
	}

	@Override
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	protected static ElasticsearchEngineAdapterFixture
		createElasticsearchEngineAdapterFixture(
			ElasticsearchClientResolver elasticsearchClientResolver,
			FacetProcessor<SearchRequestBuilder> facetProcessor) {

		return new ElasticsearchEngineAdapterFixture() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setFacetProcessor(facetProcessor);
			}
		};
	}

	protected static QuerySuggester createElasticsearchQuerySuggester(
		ElasticsearchClientResolver elasticsearchClientResolver,
		IndexNameBuilder indexNameBuilder, Localization localization) {

		return new ElasticsearchQuerySuggester() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndexNameBuilder(indexNameBuilder);
				setLocalization(localization);
				setSuggesterTranslator(
					createElasticsearchSuggesterTranslator());
			}
		};
	}

	protected static ElasticsearchSpellCheckIndexWriter
		createElasticsearchSpellCheckIndexWriter(
			SearchEngineAdapter searchEngineAdapter,
			IndexNameBuilder indexNameBuilder, Localization localization) {

		return new ElasticsearchSpellCheckIndexWriter() {
			{
				digester = new DigesterImpl();

				setIndexNameBuilder(indexNameBuilder);
				setLocalization(localization);
				setSearchEngineAdapter(searchEngineAdapter);
			}
		};
	}

	protected static ElasticsearchSuggesterTranslator
		createElasticsearchSuggesterTranslator() {

		return new ElasticsearchSuggesterTranslator() {
			{
				setPhraseSuggesterTranslator(
					new PhraseSuggesterTranslatorImpl());
				setTermSuggesterTranslator(new TermSuggesterTranslatorImpl());
			}
		};
	}

	protected static ElasticsearchIndexSearcher createIndexSearcher(
		ElasticsearchFixture elasticsearchFixture,
		SearchEngineAdapter searchEngineAdapter,
		IndexNameBuilder indexNameBuilder, Localization localization) {

		return new ElasticsearchIndexSearcher() {
			{
				setIndexNameBuilder(indexNameBuilder);
				setProps(createProps());
				setQuerySuggester(
					createElasticsearchQuerySuggester(
						elasticsearchFixture, indexNameBuilder, localization));
				setSearchEngineAdapter(searchEngineAdapter);
				setSearchRequestBuilderFactory(
					new SearchRequestBuilderFactoryImpl());
				setSearchResponseBuilderFactory(
					new SearchResponseBuilderFactoryImpl());

				activate(
					elasticsearchFixture.
						getElasticsearchConfigurationProperties());
			}
		};
	}

	protected static IndexWriter createIndexWriter(
		final SearchEngineAdapter searchEngineAdapter,
		final IndexNameBuilder indexNameBuilder, Localization localization) {

		return new ElasticsearchIndexWriter() {
			{
				setSearchEngineAdapter(searchEngineAdapter);
				setIndexNameBuilder(indexNameBuilder);

				setSpellCheckIndexWriter(
					createElasticsearchSpellCheckIndexWriter(
						searchEngineAdapter, indexNameBuilder, localization));
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
		IndexCreator indexCreator = new IndexCreator() {
			{
				setElasticsearchClientResolver(_elasticsearchFixture);
				setIndexCreationHelper(_indexCreationHelper);
				setLiferayMappingsAddedToIndex(_liferayMappingsAddedToIndex);
			}
		};

		indexCreator.createIndex(
			new IndexName(indexNameBuilder.getIndexName(_companyId)));
	}

	protected FacetProcessor<SearchRequestBuilder> getFacetProcessor() {
		if (_facetProcessor != null) {
			return _facetProcessor;
		}

		return new DefaultFacetProcessor();
	}

	protected void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	protected void setElasticsearchFixture(
		ElasticsearchFixture elasticsearchFixture) {

		_elasticsearchFixture = elasticsearchFixture;
	}

	protected void setFacetProcessor(
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_facetProcessor = facetProcessor;
	}

	protected void setIndexCreationHelper(
		IndexCreationHelper indexCreationHelper) {

		_indexCreationHelper = indexCreationHelper;
	}

	protected void setLiferayMappingsAddedToIndex(
		boolean liferayMappingsAddedToIndex) {

		_liferayMappingsAddedToIndex = liferayMappingsAddedToIndex;
	}

	private long _companyId;
	private ElasticsearchFixture _elasticsearchFixture;
	private FacetProcessor<SearchRequestBuilder> _facetProcessor;
	private IndexCreationHelper _indexCreationHelper;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private boolean _liferayMappingsAddedToIndex;
	private SearchEngineAdapter _searchEngineAdapter;

}