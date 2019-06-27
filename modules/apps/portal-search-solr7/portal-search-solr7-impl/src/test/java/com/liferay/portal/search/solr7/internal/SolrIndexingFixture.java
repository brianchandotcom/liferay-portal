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

package com.liferay.portal.search.solr7.internal;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr7.internal.connection.TestSolrClientManager;
import com.liferay.portal.search.solr7.internal.document.DefaultSolrDocumentFactory;
import com.liferay.portal.search.solr7.internal.facet.DefaultFacetProcessor;
import com.liferay.portal.search.solr7.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr7.internal.query.BooleanQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.DisMaxQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.FuzzyQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.MatchAllQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.MatchQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.MoreLikeThisQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.MultiMatchQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.NestedQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.SolrQueryTranslator;
import com.liferay.portal.search.solr7.internal.query.StringQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.TermQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.TermRangeQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.query.WildcardQueryTranslatorImpl;
import com.liferay.portal.search.solr7.internal.search.engine.adapter.SolrSearchEngineAdapterFixture;
import com.liferay.portal.search.solr7.internal.suggest.NGramHolderBuilderImpl;
import com.liferay.portal.search.solr7.internal.suggest.NGramQueryBuilderImpl;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.util.LocalizationImpl;

import java.nio.ByteBuffer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

import org.mockito.Mockito;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author André de Oliveira
 */
public class SolrIndexingFixture implements IndexingFixture {

	public SolrIndexingFixture() {
		this(Collections.<String, Object>emptyMap());
	}

	public SolrIndexingFixture(
		Map<String, Object> solrConfigurationProperties) {

		_properties = createSolrConfigurationProperties(
			solrConfigurationProperties);
	}

	@Override
	public long getCompanyId() {
		return _COMPANY_ID;
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
		return SolrUnitTestRequirements.isSolrExternallyStartedByDeveloper();
	}

	public void setFacetProcessor(FacetProcessor<SolrQuery> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	@Override
	public void setUp() throws Exception {
		if (_facetProcessor == null) {
			_facetProcessor = createFacetProcessor();
		}

		SolrClientManager solrClientManager = new TestSolrClientManager(
			_properties);

		SolrSearchEngineAdapterFixture solrSearchEngineAdapterFixture =
			createSolrSearchEngineAdapterFixture(
				solrClientManager, _facetProcessor, _properties);

		solrSearchEngineAdapterFixture.setUp();

		SearchEngineAdapter searchEngineAdapter =
			solrSearchEngineAdapterFixture.getSearchEngineAdapter();

		_indexSearcher = createIndexSearcher(
			searchEngineAdapter, solrClientManager);
		_indexWriter = createIndexWriter(searchEngineAdapter);
		_searchEngineAdapter = searchEngineAdapter;
	}

	@Override
	public void tearDown() throws Exception {
	}

	protected static SolrQueryTranslator createSolrQueryTranslator() {
		return new SolrQueryTranslator() {
			{
				setBooleanQueryTranslator(new BooleanQueryTranslatorImpl());
				setDisMaxQueryTranslator(new DisMaxQueryTranslatorImpl());
				setFuzzyQueryTranslator(new FuzzyQueryTranslatorImpl());
				setMatchAllQueryTranslator(new MatchAllQueryTranslatorImpl());
				setMatchQueryTranslator(new MatchQueryTranslatorImpl());
				setMoreLikeThisQueryTranslator(
					new MoreLikeThisQueryTranslatorImpl());
				setMultiMatchQueryTranslator(
					new MultiMatchQueryTranslatorImpl());
				setNestedQueryTranslator(new NestedQueryTranslatorImpl());
				setStringQueryTranslator(new StringQueryTranslatorImpl());
				setTermQueryTranslator(new TermQueryTranslatorImpl());
				setTermRangeQueryTranslator(new TermRangeQueryTranslatorImpl());
				setWildcardQueryTranslator(new WildcardQueryTranslatorImpl());
			}
		};
	}

	protected static SolrSearchEngineAdapterFixture
		createSolrSearchEngineAdapterFixture(
			SolrClientManager solrClientManager,
			FacetProcessor<SolrQuery> facetProcessor,
			Map<String, Object> properties) {

		return new SolrSearchEngineAdapterFixture() {
			{
				setFacetProcessor(facetProcessor);
				setQueryTranslator(createSolrQueryTranslator());
				setSolrClientManager(solrClientManager);
				setSolrDocumentFactory(new DefaultSolrDocumentFactory());
				setProperties(properties);
			}
		};
	}

	protected Digester createDigester() {
		Digester digester = Mockito.mock(Digester.class);

		Mockito.doAnswer(
			invocation -> {
				Object[] args = invocation.getArguments();

				ByteBuffer byteBuffer = (ByteBuffer)args[1];

				return byteBuffer.array();
			}
		).when(
			digester
		).digestRaw(
			Mockito.anyString(), (ByteBuffer)Mockito.any()
		);

		return digester;
	}

	protected FacetProcessor createFacetProcessor() {
		return new DefaultFacetProcessor() {
			{
				setJSONFactory(_jsonFactory);
			}
		};
	}

	protected IndexSearcher createIndexSearcher(
		final SearchEngineAdapter searchEngineAdapter,
		final SolrClientManager solrClientManager) {

		return new SolrIndexSearcher() {
			{
				setFacetProcessor(_facetProcessor);
				setProps(createProps());
				setQuerySuggester(createSolrQuerySuggester(solrClientManager));
				setSearchRequestBuilderFactory(
					new SearchRequestBuilderFactoryImpl());
				setSearchResponseBuilderFactory(
					new SearchResponseBuilderFactoryImpl());
				setSearchEngineAdapter(searchEngineAdapter);

				activate(_properties);
			}
		};
	}

	protected IndexWriter createIndexWriter(
		final SearchEngineAdapter searchEngineAdapter) {

		return new SolrIndexWriter() {
			{
				setSearchEngineAdapter(searchEngineAdapter);
				setSpellCheckIndexWriter(
					createSolrSpellCheckIndexWriter(searchEngineAdapter));

				activate(_properties);
			}
		};
	}

	protected NGramQueryBuilderImpl createNGramQueryBuilder() {
		NGramQueryBuilderImpl nGramQueryBuilderImpl =
			new NGramQueryBuilderImpl();

		ReflectionTestUtil.setFieldValue(
			nGramQueryBuilderImpl, "_nGramHolderBuilder",
			new NGramHolderBuilderImpl());

		return nGramQueryBuilderImpl;
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

		Mockito.doReturn(
			"yyyyMMddHHmmss"
		).when(
			props
		).get(
			PropsKeys.INDEX_DATE_FORMAT_PATTERN
		);

		return props;
	}

	protected Map<String, Object> createSolrConfigurationProperties(
		Map<String, Object> solrConfigurationProperties) {

		Map<String, Object> properties = new HashMap<>();

		properties.put("defaultCollection", "liferay");
		properties.put("logExceptionsOnly", false);
		properties.put("readURL", "http://localhost:8983/solr/liferay");
		properties.put("writeURL", "http://localhost:8983/solr/liferay");

		properties.putAll(solrConfigurationProperties);

		return properties;
	}

	protected SolrQuerySuggester createSolrQuerySuggester(
		SolrClientManager solrClientManager) {

		return new SolrQuerySuggester() {
			{
				setLocalization(_localization);

				setNGramQueryBuilder(createNGramQueryBuilder());
				setSolrClientManager(solrClientManager);

				activate(_properties);
			}
		};
	}

	protected SolrSpellCheckIndexWriter createSolrSpellCheckIndexWriter(
		final SearchEngineAdapter searchEngineAdapter) {

		return new SolrSpellCheckIndexWriter() {
			{
				digester = createDigester();
				nGramHolderBuilder = new NGramHolderBuilderImpl();

				setSearchEngineAdapter(searchEngineAdapter);

				activate(_properties);
			}
		};
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private FacetProcessor<SolrQuery> _facetProcessor;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final Localization _localization = new LocalizationImpl();
	private final Map<String, Object> _properties;
	private SearchEngineAdapter _searchEngineAdapter;

}