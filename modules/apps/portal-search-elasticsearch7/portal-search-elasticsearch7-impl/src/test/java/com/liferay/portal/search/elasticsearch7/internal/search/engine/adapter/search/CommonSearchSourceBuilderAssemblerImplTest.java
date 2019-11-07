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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.facet.DefaultFacetTranslator;
import com.liferay.portal.search.elasticsearch7.internal.filter.ElasticsearchFilterTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.index.LiferayIndexFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.SearchAssert;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.filter.ComplexQueryBuilderFactory;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.internal.filter.ComplexQueryBuilderImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;

import java.util.Arrays;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Wade Cao
 */
public class CommonSearchSourceBuilderAssemblerImplTest {

	@Before
	public void setUp() throws Exception {
		_indexName = new IndexName(testName.getMethodName());

		Class<?> clazz = getClass();

		_liferayIndexFixture = new LiferayIndexFixture(
			clazz.getSimpleName(), _indexName);

		_liferayIndexFixture.setUp();

		Queries queries = new QueriesImpl();

		_commonSearchSourceBuilderAssembler =
			createCommonSearchSourceBuilderAssembler(queries);
		_queries = queries;
	}

	@After
	public void tearDown() throws Exception {
		_liferayIndexFixture.tearDown();
	}

	@Test
	public void testPartsWhenAdditiveWillAppendToWhatMainQueryFinds()
		throws Exception {

		index("alpha 1", "JournalArticle");
		index("alpha 2", "DLFileEntry");
		index("bravo 1", "DLFileEntry");

		SearchSearchRequest searchSearchRequest = createSearchSearchRequest();

		searchSearchRequest.setQuery(
			new MatchQuery("entryClassName", "DLFileEntry"));

		addPart("filter", _queries.term("title", "bravo"), searchSearchRequest);

		assertSearch(searchSearchRequest, "bravo 1");

		addPartAdditive(
			"should", _queries.term("entryClassName", "JournalArticle"),
			searchSearchRequest);

		assertSearch(searchSearchRequest, "alpha 1", "bravo 1");
	}

	@Test
	public void testPartsWillNarrowDownWhatMainQueryFinds() throws Exception {
		index("alpha 1", "JournalArticle");
		index("alpha 2", "DLFileEntry");
		index("bravo 1", "DLFileEntry");

		SearchSearchRequest searchSearchRequest = createSearchSearchRequest();

		searchSearchRequest.setQuery(new MatchQuery("title", "alpha"));

		assertSearch(searchSearchRequest, "alpha 1", "alpha 2");

		addPart(
			"filter", _queries.term("entryClassName", "DLFileEntry"),
			searchSearchRequest);

		assertSearch(searchSearchRequest, "alpha 2");
	}

	@Rule
	public TestName testName = new TestName();

	protected static CommonSearchSourceBuilderAssembler
		createCommonSearchSourceBuilderAssembler(Queries queries) {

		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture =
				new ElasticsearchQueryTranslatorFixture();

		ElasticsearchFilterTranslatorFixture
			elasticsearchFilterTranslatorFixture =
				new ElasticsearchFilterTranslatorFixture();

		ElasticsearchQueryTranslator elasticsearchQueryTranslator =
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator();

		com.liferay.portal.search.elasticsearch7.internal.legacy.query.
			ElasticsearchQueryTranslatorFixture
				legacyElasticsearchQueryTranslatorFixture =
					new com.liferay.portal.search.elasticsearch7.internal.
						legacy.query.ElasticsearchQueryTranslatorFixture();

		com.liferay.portal.search.elasticsearch7.internal.legacy.query.
			ElasticsearchQueryTranslator legacyElasticsearchQueryTranslator =
				legacyElasticsearchQueryTranslatorFixture.
					getElasticsearchQueryTranslator();

		return new CommonSearchSourceBuilderAssemblerImpl() {
			{
				setComplexQueryBuilderFactory(
					createComplexQueryBuilderFactory(queries));
				setFacetTranslator(new DefaultFacetTranslator());
				setFilterToQueryBuilderTranslator(
					elasticsearchFilterTranslatorFixture.
						getElasticsearchFilterTranslator());
				setLegacyQueryToQueryBuilderTranslator(
					legacyElasticsearchQueryTranslator);
				setQueryToQueryBuilderTranslator(elasticsearchQueryTranslator);
			}
		};
	}

	protected static ComplexQueryBuilderFactory
		createComplexQueryBuilderFactory(Queries queries) {

		return () -> new ComplexQueryBuilderImpl(queries, null);
	}

	protected void addPart(
		String occur, Query query, SearchSearchRequest searchSearchRequest) {

		searchSearchRequest.addComplexQueryParts(
			Arrays.asList(
				_complexQueryPartBuilderFactory.builder(
				).occur(
					occur
				).query(
					query
				).build()));
	}

	protected void addPartAdditive(
		String occur, Query query, SearchSearchRequest searchSearchRequest) {

		searchSearchRequest.addComplexQueryParts(
			Arrays.asList(
				_complexQueryPartBuilderFactory.builder(
				).additive(
					true
				).occur(
					occur
				).query(
					query
				).build()));
	}

	protected void assertSearch(
			SearchSearchRequest searchSearchRequest, String... expected)
		throws Exception {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		SearchRequest searchRequest = new SearchRequest();

		_commonSearchSourceBuilderAssembler.assemble(
			searchSourceBuilder, searchSearchRequest, searchRequest);

		SearchAssert.assertSearch(
			_liferayIndexFixture.getRestHighLevelClient(), searchSourceBuilder,
			searchRequest, "title", expected);
	}

	protected SearchSearchRequest createSearchSearchRequest() {
		return new SearchSearchRequest() {
			{
				setIndexNames(_indexName.getName());
			}
		};
	}

	protected void index(String title, String entryClassName) {
		_liferayIndexFixture.index(
			HashMapBuilder.<String, Object>put(
				"entryClassName", entryClassName
			).put(
				"title", title
			).build());
	}

	private CommonSearchSourceBuilderAssembler
		_commonSearchSourceBuilderAssembler;
	private final ComplexQueryPartBuilderFactory
		_complexQueryPartBuilderFactory =
			new ComplexQueryPartBuilderFactoryImpl();
	private IndexName _indexName;
	private LiferayIndexFixture _liferayIndexFixture;
	private Queries _queries;

}