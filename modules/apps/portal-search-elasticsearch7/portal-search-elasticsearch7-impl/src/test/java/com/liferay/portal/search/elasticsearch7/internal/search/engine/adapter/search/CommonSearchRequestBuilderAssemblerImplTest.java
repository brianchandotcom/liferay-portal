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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.facet.DefaultFacetTranslator;
import com.liferay.portal.search.elasticsearch7.internal.filter.ElasticsearchFilterTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.index.LiferayIndexFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.query.SearchAssert;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.filter.ComplexQueryBuilder;
import com.liferay.portal.search.filter.ComplexQueryBuilderFactory;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.internal.filter.ComplexQueryBuilderImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Wade Cao
 */
public class CommonSearchRequestBuilderAssemblerImplTest {

	@Before
	public void setUp() throws Exception {
		_indexName = new IndexName(testName.getMethodName());

		Class<?> clazz = getClass();

		_liferayIndexFixture = new LiferayIndexFixture(
			clazz.getSimpleName(), _indexName);

		_liferayIndexFixture.setUp();

		_commonSearchRequestBuilderAssembler =
			createCommonSearchRequestBuilderAssembler();
	}

	@After
	public void tearDown() throws Exception {
		_liferayIndexFixture.tearDown();
	}

	@Test
	public void testCustomQueryWithComplexQueryPart() throws Exception {
		addDocuments();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest() {
			{
				setIndexNames(_indexName.getName());
				setQuery(new MatchQuery("title", "alpha"));
			}
		};

		assertSearch(searchSearchRequest, "title", "alpha 1", "alpha 2");

		ComplexQueryPartBuilderFactory complexQueryPartBuilderFactory =
			new ComplexQueryPartBuilderFactoryImpl();

		searchSearchRequest.addComplexQueryParts(
			ListUtil.toList(
				complexQueryPartBuilderFactory.builder(
				).boost(
					null
				).disabled(
					false
				).field(
					"entryClassName"
				).name(
					StringPool.BLANK
				).occur(
					"must"
				).parent(
					StringPool.BLANK
				).type(
					"match"
				).value(
					"DLFileEntry"
				).build()));

		searchSearchRequest.setIndexNames(_indexName.getName());

		assertSearch(searchSearchRequest, "title", "alpha 2");
	}

	@Rule
	public TestName testName = new TestName();

	protected void addDocuments() {
		index(
			new HashMap<String, Object>() {
				{
					put("title", "alpha 1");
					put("entryClassName", "JournalArticle");
				}
			});

		index(
			new HashMap<String, Object>() {
				{
					put("title", "alpha 2");
					put("entryClassName", "DLFileEntry");
				}
			});

		index(
			new HashMap<String, Object>() {
				{
					put("title", "bravo 1");
					put("entryClassName", "DLFileEntry");
				}
			});
	}

	protected void assertSearch(
			SearchSearchRequest searchSearchRequest, String field,
			String... expected)
		throws Exception {

		SearchRequestBuilder searchRequestBuilder = new SearchRequestBuilder(
			_liferayIndexFixture.getClient(), SearchAction.INSTANCE);

		_commonSearchRequestBuilderAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		SearchAssert.assertSearch(searchRequestBuilder, field, expected);
	}

	protected CommonSearchRequestBuilderAssembler
		createCommonSearchRequestBuilderAssembler() {

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

		return new CommonSearchRequestBuilderAssemblerImpl() {
			{
				setComplexQueryBuilderFactory(
					new TestComplexQueryBuilderFactory());
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

	protected void index(Map<String, Object> map) {
		_liferayIndexFixture.index(map);
	}

	protected class TestComplexQueryBuilderFactory
		implements ComplexQueryBuilderFactory {

		@Override
		public ComplexQueryBuilder builder() {
			return new ComplexQueryBuilderImpl(new QueriesImpl(), null);
		}

	}

	private CommonSearchRequestBuilderAssembler
		_commonSearchRequestBuilderAssembler;
	private IndexName _indexName;
	private LiferayIndexFixture _liferayIndexFixture;

}