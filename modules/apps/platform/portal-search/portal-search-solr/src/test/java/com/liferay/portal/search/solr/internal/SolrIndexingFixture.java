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

package com.liferay.portal.search.solr.internal;

import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.search.solr.connection.SolrClientManager;
import com.liferay.portal.search.solr.connection.TestSolrClientManager;
import com.liferay.portal.search.solr.document.SolrUpdateDocumentCommand;
import com.liferay.portal.search.solr.internal.document.DefaultSolrDocumentFactory;
import com.liferay.portal.search.solr.internal.facet.DateRangeFacetProcessor;
import com.liferay.portal.search.solr.internal.filter.BooleanFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.DateRangeTermFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.ExistsFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.GeoBoundingBoxFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.GeoDistanceFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.GeoDistanceRangeFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.GeoPolygonFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.MissingFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.PrefixFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.QueryFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.RangeTermFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.SolrFilterTranslator;
import com.liferay.portal.search.solr.internal.filter.TermFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.filter.TermsFilterTranslatorImpl;
import com.liferay.portal.search.solr.internal.groupby.DefaultGroupByTranslator;
import com.liferay.portal.search.solr.internal.query.BooleanQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.DisMaxQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.FuzzyQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.MatchAllQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.MatchQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.MoreLikeThisQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.MultiMatchQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.NestedQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.SolrQueryTranslator;
import com.liferay.portal.search.solr.internal.query.StringQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.TermQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.TermRangeQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.query.WildcardQueryTranslatorImpl;
import com.liferay.portal.search.solr.internal.stats.DefaultStatsTranslator;
import com.liferay.portal.search.unit.test.IndexingFixture;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author André de Oliveira
 */
public class SolrIndexingFixture implements IndexingFixture {

	public SolrIndexingFixture() throws Exception {
		_properties = createSolrConfigurationProperties();
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
		return SolrUnitTestRequirements.isSolrExternallyStartedByDeveloper();
	}

	@Override
	public void setUp() throws Exception {
		SolrClientManager solrClientManager = new TestSolrClientManager(
			_properties);

		_indexSearcher = createIndexSearcher(solrClientManager);
		_indexWriter = createIndexWriter(solrClientManager);
	}

	@Override
	public void tearDown() throws Exception {
	}

	protected static SolrFilterTranslator createSolrFilterTranslator() {
		return new SolrFilterTranslator() {
			{
				setBooleanQueryTranslator(new BooleanFilterTranslatorImpl());
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

	protected IndexSearcher createIndexSearcher(
		final SolrClientManager solrClientManager) {

		return new SolrIndexSearcher() {
			{
				setFacetProcessor(new DateRangeFacetProcessor());
				setFilterTranslator(createSolrFilterTranslator());
				setGroupByTranslator(new DefaultGroupByTranslator());
				setQueryTranslator(createSolrQueryTranslator());
				setSolrClientManager(solrClientManager);
				setStatsTranslator(new DefaultStatsTranslator());

				activate(_properties);
			}
		};
	}

	protected IndexWriter createIndexWriter(
		final SolrClientManager solrClientManager) {

		final SolrUpdateDocumentCommand updateDocumentCommand =
			new SolrUpdateDocumentCommandImpl() {
				{
					setSolrClientManager(solrClientManager);
					setSolrDocumentFactory(new DefaultSolrDocumentFactory());
				}
			};

		return new SolrIndexWriter() {
			{
				setSolrClientManager(solrClientManager);
				setSolrUpdateDocumentCommand(updateDocumentCommand);
			}
		};
	}

	protected Map<String, Object> createSolrConfigurationProperties() {
		Map<String, Object> properties = new HashMap<>();

		properties.put("logExceptionsOnly", false);
		properties.put("readURL", "http://localhost:8983/solr/liferay");
		properties.put("writeURL", "http://localhost:8983/solr/liferay");

		return properties;
	}

	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private final Map<String, Object> _properties;

}