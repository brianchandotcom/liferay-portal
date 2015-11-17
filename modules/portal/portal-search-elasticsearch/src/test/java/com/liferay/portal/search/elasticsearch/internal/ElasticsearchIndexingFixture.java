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

package com.liferay.portal.search.elasticsearch.internal;

import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.connection.TestElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.document.ElasticsearchUpdateDocumentCommand;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch.internal.facet.DateRangeFacetProcessor;
import com.liferay.portal.search.elasticsearch.internal.filter.BooleanFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.DateRangeTermFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.ElasticsearchFilterTranslator;
import com.liferay.portal.search.elasticsearch.internal.filter.ExistsFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.GeoBoundingBoxFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.GeoDistanceFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.GeoDistanceRangeFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.GeoPolygonFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.MissingFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.PrefixFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.QueryFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.RangeTermFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.TermFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.filter.TermsFilterTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.groupby.DefaultGroupByTranslator;
import com.liferay.portal.search.elasticsearch.internal.query.BooleanQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.DisMaxQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.elasticsearch.internal.query.FuzzyQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.MatchAllQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.MatchQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.MoreLikeThisQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.MultiMatchQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.NestedQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.StringQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.TermQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.TermRangeQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.query.WildcardQueryTranslatorImpl;
import com.liferay.portal.search.elasticsearch.internal.stats.DefaultStatsTranslator;
import com.liferay.portal.search.unit.test.IndexingFixture;

import java.util.HashMap;

/**
 * @author André de Oliveira
 */
public class ElasticsearchIndexingFixture implements IndexingFixture {

	public ElasticsearchIndexingFixture(String subdirName) throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			subdirName, _properties);
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
		_elasticsearchFixture.setUp();

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new TestElasticsearchConnectionManager(_elasticsearchFixture);

		_indexSearcher = createIndexSearcher(elasticsearchConnectionManager);
		_indexWriter = createIndexWriter(elasticsearchConnectionManager);
	}

	@Override
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	protected static ElasticsearchFilterTranslator
		createElasticsearchFilterTranslator() {

		return new ElasticsearchFilterTranslator() {
			{
				setBooleanFilterTranslator(new BooleanFilterTranslatorImpl());
				setDateRangeTermFilterTranslator(
					new DateRangeTermFilterTranslatorImpl());
				setExistsFilterTranslator(new ExistsFilterTranslatorImpl());
				setGeoBoundingBoxFilterTranslator(
					new GeoBoundingBoxFilterTranslatorImpl());
				setGeoDistanceFilterTranslator(
					new GeoDistanceFilterTranslatorImpl());
				setGeoDistanceRangeFilterTranslator(
					new GeoDistanceRangeFilterTranslatorImpl());
				setGeoPolygonFilterTranslator(
					new GeoPolygonFilterTranslatorImpl());
				setMissingFilterTranslator(new MissingFilterTranslatorImpl());
				setPrefixFilterTranslator(new PrefixFilterTranslatorImpl());
				setQueryFilterTranslator(new QueryFilterTranslatorImpl());
				setRangeTermFilterTranslator(
					new RangeTermFilterTranslatorImpl());
				setTermFilterTranslator(new TermFilterTranslatorImpl());
				setTermsFilterTranslator(new TermsFilterTranslatorImpl());
			}
		};
	}

	protected static ElasticsearchQueryTranslator
		createElasticsearchQueryTranslator() {

		return new ElasticsearchQueryTranslator() {
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

	protected IndexSearcher createIndexSearcher(
		final ElasticsearchConnectionManager elasticsearchConnectionManager) {

		return new ElasticsearchIndexSearcher() {
			{
				setElasticsearchConnectionManager(
					elasticsearchConnectionManager);
				setFacetProcessor(new DateRangeFacetProcessor());
				setFilterTranslator(createElasticsearchFilterTranslator());
				setGroupByTranslator(new DefaultGroupByTranslator());
				setQueryTranslator(createElasticsearchQueryTranslator());
				setStatsTranslator(new DefaultStatsTranslator());

				activate(_properties);
			}
		};
	}

	protected IndexWriter createIndexWriter(
		final ElasticsearchConnectionManager elasticsearchConnectionManager) {

		final ElasticsearchUpdateDocumentCommand updateDocumentCommand =
			new ElasticsearchUpdateDocumentCommandImpl() {
				{
					setElasticsearchConnectionManager(
						elasticsearchConnectionManager);
					setElasticsearchDocumentFactory(
						new DefaultElasticsearchDocumentFactory());

					activate(_properties);
				}
			};

		return new ElasticsearchIndexWriter() {
			{
				setElasticsearchConnectionManager(
					elasticsearchConnectionManager);
				setElasticsearchUpdateDocumentCommand(updateDocumentCommand);
			}
		};
	}

	private final ElasticsearchFixture _elasticsearchFixture;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private final HashMap<String, Object> _properties = new HashMap<>();

}