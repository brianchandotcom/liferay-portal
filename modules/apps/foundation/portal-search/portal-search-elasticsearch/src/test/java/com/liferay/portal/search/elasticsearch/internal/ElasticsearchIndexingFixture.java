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
import com.liferay.portal.search.elasticsearch.index.IndexNameBuilder;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch.internal.facet.DefaultFacetProcessor;
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
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Collections;

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

	@Override
	public void setUp() throws Exception {
		_elasticsearchFixture.setUp();

		createIndex();

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new TestElasticsearchConnectionManager(_elasticsearchFixture);

		_indexSearcher = createIndexSearcher(
			elasticsearchConnectionManager, _indexNameBuilder);
		_indexWriter = createIndexWriter(
			elasticsearchConnectionManager, _indexNameBuilder);
	}

	@Override
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	protected static ElasticsearchFilterTranslator
		createElasticsearchFilterTranslator() {

		return new ElasticsearchFilterTranslator() {
			{
				booleanFilterTranslator = new BooleanFilterTranslatorImpl();
				dateRangeTermFilterTranslator =
					new DateRangeTermFilterTranslatorImpl();
				existsFilterTranslator = new ExistsFilterTranslatorImpl();
				geoBoundingBoxFilterTranslator =
					new GeoBoundingBoxFilterTranslatorImpl();
				geoDistanceFilterTranslator =
					new GeoDistanceFilterTranslatorImpl();
				geoDistanceRangeFilterTranslator =
					new GeoDistanceRangeFilterTranslatorImpl();
				geoPolygonFilterTranslator =
					new GeoPolygonFilterTranslatorImpl();
				missingFilterTranslator = new MissingFilterTranslatorImpl();
				prefixFilterTranslator = new PrefixFilterTranslatorImpl();
				queryFilterTranslator = new QueryFilterTranslatorImpl();
				rangeTermFilterTranslator = new RangeTermFilterTranslatorImpl();
				termFilterTranslator = new TermFilterTranslatorImpl();
				termsFilterTranslator = new TermsFilterTranslatorImpl();
			}
		};
	}

	protected static ElasticsearchQueryTranslator
		createElasticsearchQueryTranslator() {

		return new ElasticsearchQueryTranslator() {
			{
				booleanQueryTranslator = new BooleanQueryTranslatorImpl();
				disMaxQueryTranslator = new DisMaxQueryTranslatorImpl();
				fuzzyQueryTranslator = new FuzzyQueryTranslatorImpl();
				matchAllQueryTranslator = new MatchAllQueryTranslatorImpl();
				matchQueryTranslator = new MatchQueryTranslatorImpl();
				moreLikeThisQueryTranslator =
					new MoreLikeThisQueryTranslatorImpl();
				multiMatchQueryTranslator = new MultiMatchQueryTranslatorImpl();
				nestedQueryTranslator = new NestedQueryTranslatorImpl();
				stringQueryTranslator = new StringQueryTranslatorImpl();
				termQueryTranslator = new TermQueryTranslatorImpl();
				termRangeQueryTranslator = new TermRangeQueryTranslatorImpl();
				wildcardQueryTranslator = new WildcardQueryTranslatorImpl();
			}
		};
	}

	protected void createIndex() {
		_indexCreator.createIndex(
			new IndexName(_indexNameBuilder.getIndexName(_companyId)));
	}

	protected IndexSearcher createIndexSearcher(
		final ElasticsearchConnectionManager elasticsearchConnectionManager1,
		final IndexNameBuilder indexNameBuilder1) {

		return new ElasticsearchIndexSearcher() {
			{
				elasticsearchConnectionManager =
					elasticsearchConnectionManager1;
				facetProcessor = new DefaultFacetProcessor();
				filterTranslator = createElasticsearchFilterTranslator();
				groupByTranslator = new DefaultGroupByTranslator();
				indexNameBuilder = indexNameBuilder1;
				queryTranslator = createElasticsearchQueryTranslator();
				statsTranslator = new DefaultStatsTranslator();
				searchHitDocumentTranslator =
					new SearchHitDocumentTranslatorImpl();

				activate(Collections.<String, Object>emptyMap());
			}
		};
	}

	protected IndexWriter createIndexWriter(
		final ElasticsearchConnectionManager elasticsearchConnectionManager1,
		final IndexNameBuilder indexNameBuilder1) {

		final ElasticsearchUpdateDocumentCommand updateDocumentCommand =
			new ElasticsearchUpdateDocumentCommandImpl() {
				{
					elasticsearchConnectionManager =
						elasticsearchConnectionManager1;
					elasticsearchDocumentFactory =
						new DefaultElasticsearchDocumentFactory();
					indexNameBuilder = indexNameBuilder1;

					activate(Collections.<String, Object>emptyMap());
				}
			};

		return new ElasticsearchIndexWriter() {
			{
				elasticsearchConnectionManager =
					elasticsearchConnectionManager1;
				elasticsearchUpdateDocumentCommand = updateDocumentCommand;
				indexNameBuilder = indexNameBuilder1;
			}
		};
	}

	protected static class TestIndexNameBuilder implements IndexNameBuilder {

		@Override
		public String getIndexName(long companyId) {
			return String.valueOf(companyId);
		}

	}

	private final long _companyId;
	private final ElasticsearchFixture _elasticsearchFixture;
	private final IndexCreator _indexCreator;
	private final IndexNameBuilder _indexNameBuilder =
		new TestIndexNameBuilder();
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;

}