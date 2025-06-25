/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.GeoBoundingBoxFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceRangeFilter;
import com.liferay.portal.kernel.search.filter.GeoPolygonFilter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.PrefixFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.filter.DateRangeFilter;
import com.liferay.portal.search.filter.FilterVisitor;
import com.liferay.portal.search.filter.RangeFilter;
import com.liferay.portal.search.filter.TermsSetFilter;
import com.liferay.portal.search.solr8.internal.query.BaseQueryVisitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermInSetQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;
import org.apache.solr.client.solrj.util.ClientUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Solr", service = FilterTranslator.class
)
public class SolrFilterTranslator
	implements FilterTranslator<Query>, FilterVisitor<Query> {

	@Override
	public Query translate(Filter filter) {
		return filter.accept(this);
	}

	@Override
	public Query visit(BooleanFilter booleanFilter) {
		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		for (com.liferay.portal.kernel.search.BooleanClause<Filter>
				booleanClause : booleanFilter.getMustBooleanClauses()) {

			builder.add(translate(booleanClause), BooleanClause.Occur.MUST);
		}

		for (com.liferay.portal.kernel.search.BooleanClause<Filter>
				booleanClause : booleanFilter.getMustNotBooleanClauses()) {

			builder.add(translate(booleanClause), BooleanClause.Occur.MUST_NOT);
		}

		if (_isOnlyMustNotClauses(booleanFilter)) {
			builder.add(new MatchAllDocsQuery(), BooleanClause.Occur.SHOULD);
		}

		for (com.liferay.portal.kernel.search.BooleanClause<Filter>
				booleanClause : booleanFilter.getShouldBooleanClauses()) {

			builder.add(translate(booleanClause), BooleanClause.Occur.SHOULD);
		}

		return builder.build();
	}

	@Override
	public Query visit(DateRangeFilter dateRangeFilter) {
		return TermRangeQuery.newStringRange(
			dateRangeFilter.getFieldName(), dateRangeFilter.getFrom(),
			dateRangeFilter.getTo(), dateRangeFilter.isIncludeLower(),
			dateRangeFilter.isIncludeUpper());
	}

	@Override
	public Query visit(DateRangeTermFilter dateRangeTermFilter) {
		return _dateRangeTermFilterTranslator.translate(dateRangeTermFilter);
	}

	@Override
	public Query visit(ExistsFilter existsFilter) {
		return _existsFilterTranslator.translate(existsFilter);
	}

	@Override
	public Query visit(GeoBoundingBoxFilter geoBoundingBoxFilter) {
		return _geoBoundingBoxFilterTranslator.translate(geoBoundingBoxFilter);
	}

	@Override
	public Query visit(GeoDistanceFilter geoDistanceFilter) {
		return _geoDistanceFilterTranslator.translate(geoDistanceFilter);
	}

	@Override
	public Query visit(GeoDistanceRangeFilter geoDistanceRangeFilter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Query visit(GeoPolygonFilter geoPolygonFilter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Query visit(MissingFilter missingFilter) {
		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		builder.add(new MatchAllDocsQuery(), BooleanClause.Occur.SHOULD);

		builder.add(
			TermRangeQuery.newStringRange(
				missingFilter.getField(), "*", "*", true, true),
			BooleanClause.Occur.MUST_NOT);

		return builder.build();
	}

	@Override
	public Query visit(PrefixFilter prefixFilter) {
		return _prefixFilterTranslator.translate(prefixFilter);
	}

	@Override
	public Query visit(QueryFilter queryFilter) {
		com.liferay.portal.kernel.search.Query query = queryFilter.getQuery();

		return query.accept(_queryVisitor);
	}

	@Override
	public Query visit(RangeFilter rangeFilter) {
		return TermRangeQuery.newStringRange(
			rangeFilter.getFieldName(), rangeFilter.getFrom(),
			rangeFilter.getTo(), rangeFilter.isIncludeLower(),
			rangeFilter.isIncludeUpper());
	}

	@Override
	public Query visit(RangeTermFilter rangeTermFilter) {
		return _rangeTermFilterTranslator.translate(rangeTermFilter);
	}

	@Override
	public Query visit(TermFilter termFilter) {
		return _termFilterTranslator.translate(termFilter);
	}

	@Override
	public Query visit(TermsFilter termsFilter) {
		String field = _escape(termsFilter.getField());

		List<BytesRef> bytesRefs = new ArrayList<>();

		for (String value : termsFilter.getValues()) {
			if (value.isEmpty()) {
				value = StringPool.DOUBLE_APOSTROPHE;
			}

			Term term = new Term(field, ClientUtils.escapeQueryChars(value));

			bytesRefs.add(term.bytes());
		}

		Query query = new TermInSetQuery(field, bytesRefs);

		if (bytesRefs.size() == 1) {
			return query;
		}

		BooleanQuery.Builder builder = new BooleanQuery.Builder();

		builder.add(query, BooleanClause.Occur.SHOULD);

		return builder.build();
	}

	@Override
	public Query visit(TermsSetFilter termsSetFilter) {
		throw new UnsupportedOperationException();
	}

	protected Query translate(
		com.liferay.portal.kernel.search.BooleanClause<Filter> booleanClause) {

		Filter filter = booleanClause.getClause();

		return filter.accept(this);
	}

	private String _escape(String value) {
		return StringUtil.replace(
			value, CharPool.SPACE, StringPool.BACK_SLASH + StringPool.SPACE);
	}

	private boolean _isOnlyMustNotClauses(BooleanFilter booleanFilter) {
		List<com.liferay.portal.kernel.search.BooleanClause<Filter>>
			booleanClauses = booleanFilter.getMustBooleanClauses();

		if (!booleanClauses.isEmpty()) {
			return false;
		}

		booleanClauses = booleanFilter.getShouldBooleanClauses();

		if (!booleanClauses.isEmpty()) {
			return false;
		}

		booleanClauses = booleanFilter.getMustNotBooleanClauses();

		return !booleanClauses.isEmpty();
	}

	@Reference
	private DateRangeTermFilterTranslator _dateRangeTermFilterTranslator;

	@Reference
	private ExistsFilterTranslator _existsFilterTranslator;

	@Reference
	private GeoBoundingBoxFilterTranslator _geoBoundingBoxFilterTranslator;

	@Reference
	private GeoDistanceFilterTranslator _geoDistanceFilterTranslator;

	@Reference
	private PrefixFilterTranslator _prefixFilterTranslator;

	private final QueryVisitor<Query> _queryVisitor = new BaseQueryVisitor() {
	};

	@Reference
	private RangeTermFilterTranslator _rangeTermFilterTranslator;

	@Reference
	private TermFilterTranslator _termFilterTranslator;

}