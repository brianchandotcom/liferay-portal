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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.HierarchicalAggregationResult;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.internal.aggregation.AggregationsImpl;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.highlight.FieldConfigBuilderFactoryImpl;
import com.liferay.portal.search.internal.highlight.HighlightBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.internal.rescore.RescoreBuilderFactoryImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.internal.sort.SortsImpl;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.rescore.RescoreBuilderFactory;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.search.test.util.SearchMapUtil;
import com.liferay.portal.search.test.util.document.DocumentTranslator;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public abstract class BaseIndexingTestCase {

	@BeforeClass
	public static void setUpClassBaseIndexingTestCase() {
		_indexingFixture = null;

		_documentFixture.setUp();
	}

	@AfterClass
	public static void tearDownClassBaseIndexingTestCase() throws Exception {
		_documentFixture.tearDown();

		if (_indexingFixture == null) {
			return;
		}

		if (_indexingFixture.isSearchEngineAvailable()) {
			_indexingFixture.tearDown();
		}

		_indexingFixture = null;
	}

	@Before
	public void setUp() throws Exception {
		setUpIndexingFixture();

		Class<?> clazz = getClass();

		_entryClassName = StringUtil.toLowerCase(
			clazz.getSimpleName() + CharPool.PERIOD + testName.getMethodName());

		_indexSearcher = _indexingFixture.getIndexSearcher();
		_indexWriter = _indexingFixture.getIndexWriter();
	}

	@After
	public void tearDown() throws Exception {
		if ((_indexingFixture == null) ||
			!_indexingFixture.isSearchEngineAvailable()) {

			return;
		}

		_indexWriter.deleteEntityDocuments(
			createSearchContext(), _entryClassName);
	}

	@Rule
	public TestName testName = new TestName();

	protected static <K, V> Map<K, V> toMap(K key, V value) {
		return Collections.singletonMap(key, value);
	}

	protected void addDocument(Document document) {
		try {
			_indexWriter.addDocument(createSearchContext(), document);
		}
		catch (SearchException searchException) {
			_handle(searchException);

			throw new RuntimeException(searchException);
		}
	}

	protected void addDocument(DocumentBuilder documentBuilder) {
		DocumentTranslator documentTranslator = new DocumentTranslator();

		addDocument(
			documentTranslator.toLegacyDocument(documentBuilder.build()));
	}

	protected void addDocument(DocumentCreationHelper documentCreationHelper) {
		Document document = DocumentFixture.newDocument(
			getCompanyId(), GROUP_ID, _entryClassName);

		documentCreationHelper.populate(document);

		addDocument(document);
	}

	protected void addDocuments(
		Function<String, DocumentCreationHelper> function,
		Collection<String> values) {

		addDocuments(function, values.stream());
	}

	protected void addDocuments(
		Function<String, DocumentCreationHelper> function,
		Stream<String> stream) {

		stream.map(
			function
		).forEach(
			this::addDocument
		);
	}

	protected void assertSearch(
		Consumer<IndexingTestHelper> indexingTestHelperConsumer) {

		try {
			IdempotentRetryAssert.retryAssert(
				10, TimeUnit.SECONDS,
				() -> indexingTestHelperConsumer.accept(
					new IndexingTestHelper()));
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected abstract IndexingFixture createIndexingFixture() throws Exception;

	protected SearchContext createSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(getCompanyId());
		searchContext.setGroupIds(new long[] {GROUP_ID});

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setHitsProcessingEnabled(true);
		queryConfig.setScoreEnabled(false);

		searchContext.setStart(QueryUtil.ALL_POS);

		return searchContext;
	}

	protected long getCompanyId() {
		return _indexingFixture.getCompanyId();
	}

	protected Query getDefaultQuery() {
		Map<String, String> map = SearchMapUtil.join(
			toMap(Field.COMPANY_ID, String.valueOf(getCompanyId())),
			toMap(Field.ENTRY_CLASS_NAME, _entryClassName));

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		map.forEach(
			(key, value) -> booleanQueryImpl.add(
				new TermQueryImpl(key, value), BooleanClauseOccur.MUST));

		return booleanQueryImpl;
	}

	protected String getEntryClassName() {
		return _entryClassName;
	}

	protected long getGroupId() {
		return GROUP_ID;
	}

	protected IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	protected IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	protected SearchEngineAdapter getSearchEngineAdapter() {
		return _indexingFixture.getSearchEngineAdapter();
	}

	protected DocumentBuilder newDocumentBuilder() {
		return documentBuilderFactory.builder(
		).setLong(
			Field.COMPANY_ID, getCompanyId()
		).setString(
			Field.ENTRY_CLASS_NAME, getEntryClassName()
		).setLong(
			Field.GROUP_ID, getGroupId()
		);
	}

	protected Hits search(SearchContext searchContext) {
		return search(searchContext, getDefaultQuery());
	}

	protected Hits search(SearchContext searchContext, Query query) {
		try {
			return _indexSearcher.search(searchContext, query);
		}
		catch (SearchException searchException) {
			_handle(searchException);

			throw new RuntimeException(searchException);
		}
	}

	protected long searchCount(SearchContext searchContext, Query query) {
		try {
			return _indexSearcher.searchCount(searchContext, query);
		}
		catch (SearchException searchException) {
			_handle(searchException);

			throw new RuntimeException(searchException);
		}
	}

	protected void setPreBooleanFilter(Filter filter, Query query) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(filter, BooleanClauseOccur.MUST);

		query.setPreBooleanFilter(booleanFilter);
	}

	protected void setUpIndexingFixture() throws Exception {
		if (_indexingFixture != null) {
			Assume.assumeTrue(_indexingFixture.isSearchEngineAvailable());

			return;
		}

		_indexingFixture = createIndexingFixture();

		Assume.assumeTrue(_indexingFixture.isSearchEngineAvailable());

		_indexingFixture.setUp();
	}

	protected static final long GROUP_ID = RandomTestUtil.randomLong();

	protected final AggregationFixture aggregationFixture =
		new AggregationFixture();
	protected final Aggregations aggregations = new AggregationsImpl();
	protected final ComplexQueryPartBuilderFactory
		complexQueryPartBuilderFactory =
			new ComplexQueryPartBuilderFactoryImpl();
	protected DocumentBuilderFactory documentBuilderFactory =
		new DocumentBuilderFactoryImpl();
	protected final FieldConfigBuilderFactory fieldConfigBuilderFactory =
		new FieldConfigBuilderFactoryImpl();
	protected final GeoBuilders geoBuilders = new GeoBuildersImpl();
	protected final HighlightBuilderFactory highlightBuilderFactory =
		new HighlightBuilderFactoryImpl();
	protected final Queries queries = new QueriesImpl();
	protected final RescoreBuilderFactory rescoreBuilderFactory =
		new RescoreBuilderFactoryImpl();
	protected final Scripts scripts = new ScriptsImpl();
	protected final Sorts sorts = new SortsImpl();

	protected class IndexingTestHelper {

		public IndexingTestHelper() {
			_searchContext = createSearchContext();

			_searchRequestBuilder = new SearchRequestBuilderImpl(
				null, _searchContext);
		}

		public void assertResultCount(int expected) {
			Document[] documents = _hits.getDocs();

			Assert.assertEquals(
				(String)_searchContext.getAttribute("queryString") + "->" +
					Arrays.toString(documents),
				expected, documents.length);
		}

		public void assertValues(
			String fieldName, List<String> expectedValues) {

			DocumentsAssert.assertValues(
				(String)_searchContext.getAttribute("queryString"),
				_hits.getDocs(), fieldName, expectedValues);
		}

		public void define(Consumer<SearchContext> searchContextConsumer) {
			searchContextConsumer.accept(_searchContext);
		}

		public void defineRequest(
			Consumer<SearchRequestBuilder> searchRequestBuilderConsumer) {

			searchRequestBuilderConsumer.accept(_searchRequestBuilder);
		}

		public <AR extends AggregationResult> AR getAggregationResult(
			Aggregation aggregation) {

			return getAggregationResult(aggregation.getName());
		}

		public <AR extends AggregationResult> AR getAggregationResult(
			PipelineAggregation pipelineAggregation) {

			return getAggregationResult(pipelineAggregation.getName());
		}

		public <AR extends AggregationResult> AR getChildAggregationResult(
			Bucket bucket, Aggregation aggregation) {

			return (AR)bucket.getChildAggregationResult(aggregation.getName());
		}

		public <AR extends AggregationResult> AR getChildAggregationResult(
			HierarchicalAggregationResult aggregationResult,
			Aggregation aggregation) {

			return (AR)aggregationResult.getChildAggregationResult(
				aggregation.getName());
		}

		public String getRequestString() {
			return (String)_searchContext.getAttribute("queryString");
		}

		public SearchContext getSearchContext() {
			return _searchContext;
		}

		public void search() {
			_hits = BaseIndexingTestCase.this.search(
				_searchContext, getQuery());

			SearchResponseBuilder searchResponseBuilder =
				new SearchResponseBuilderImpl(_searchContext);

			_searchResponse = searchResponseBuilder.build();
		}

		public long searchCount() {
			long count = BaseIndexingTestCase.this.searchCount(
				_searchContext, getQuery());

			SearchResponseBuilder searchResponseBuilder =
				new SearchResponseBuilderImpl(_searchContext);

			_searchResponse = searchResponseBuilder.build();

			return count;
		}

		public void setFilter(Filter filter) {
			_filter = filter;
		}

		public void setPostFilter(Filter postFilter) {
			_postFilter = postFilter;
		}

		public void setQuery(Query query) {
			_query = query;
		}

		public void setQueryContributor(QueryContributor queryContributor) {
			_queryContributor = queryContributor;
		}

		public void setSearchContextAttribute(String name, Serializable value) {
			_searchContext.setAttribute(name, value);
		}

		public void verify(Consumer<Hits> hitsConsumer) {
			hitsConsumer.accept(_hits);
		}

		public void verifyContext(
			Consumer<SearchContext> searchContextConsumer) {

			searchContextConsumer.accept(_searchContext);
		}

		public void verifyResponse(
			Consumer<SearchResponse> searchResponseConsumer) {

			searchResponseConsumer.accept(_searchResponse);
		}

		protected <AR extends AggregationResult> AR getAggregationResult(
			String name) {

			AggregationResult aggregationResult =
				_searchResponse.getAggregationResult(name);

			Assert.assertNotNull(aggregationResult);

			return (AR)aggregationResult;
		}

		protected Query getQuery() {
			Query query = _query;

			if (query == null) {
				query = getDefaultQuery();
			}

			if (_queryContributor != null) {
				_queryContributor.contribute(query);
			}

			if (_filter != null) {
				setPreBooleanFilter(_filter, query);
			}

			if (_postFilter != null) {
				query.setPostFilter(_postFilter);
			}

			return query;
		}

		private Filter _filter;
		private Hits _hits;
		private Filter _postFilter;
		private Query _query;
		private QueryContributor _queryContributor;
		private final SearchContext _searchContext;
		private final SearchRequestBuilder _searchRequestBuilder;
		private SearchResponse _searchResponse;

	}

	private void _handle(SearchException searchException) {
		Throwable throwable = searchException.getCause();

		if (throwable instanceof RuntimeException) {
			throw (RuntimeException)throwable;
		}

		if (throwable != null) {
			throw new RuntimeException(throwable);
		}
	}

	private static final DocumentFixture _documentFixture =
		new DocumentFixture();
	private static IndexingFixture _indexingFixture;

	private String _entryClassName;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;

}