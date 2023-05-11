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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.BoostingQuery;
import com.liferay.portal.search.query.CommonTermsQuery;
import com.liferay.portal.search.query.ConstantScoreQuery;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.query.DisMaxQuery;
import com.liferay.portal.search.query.ExistsQuery;
import com.liferay.portal.search.query.FunctionScoreQuery;
import com.liferay.portal.search.query.FuzzyQuery;
import com.liferay.portal.search.query.GeoBoundingBoxQuery;
import com.liferay.portal.search.query.GeoDistanceQuery;
import com.liferay.portal.search.query.GeoDistanceRangeQuery;
import com.liferay.portal.search.query.GeoPolygonQuery;
import com.liferay.portal.search.query.GeoShapeQuery;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.MatchAllQuery;
import com.liferay.portal.search.query.MatchPhrasePrefixQuery;
import com.liferay.portal.search.query.MatchPhraseQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.query.MultiMatchQuery;
import com.liferay.portal.search.query.NestedQuery;
import com.liferay.portal.search.query.PercolateQuery;
import com.liferay.portal.search.query.PrefixQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryTranslator;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.RangeTermQuery;
import com.liferay.portal.search.query.RegexQuery;
import com.liferay.portal.search.query.ScriptQuery;
import com.liferay.portal.search.query.SimpleStringQuery;
import com.liferay.portal.search.query.StringQuery;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.query.TermsSetQuery;
import com.liferay.portal.search.query.WildcardQuery;
import com.liferay.portal.search.query.WrapperQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = QueryTranslator.class
)
public class ElasticsearchQueryTranslator
	implements QueryTranslator<QueryBuilder>, QueryVisitor<QueryBuilder> {

	@Override
	public QueryBuilder translate(Query query) {
		QueryBuilder queryBuilder = query.accept(this);

		if (queryBuilder == null) {
			queryBuilder = QueryBuilders.queryStringQuery(query.toString());
		}

		if (query.getBoost() != null) {
			queryBuilder.boost(query.getBoost());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visit(BooleanQuery booleanQuery) {
		return _booleanQueryTranslator.translate(booleanQuery, this);
	}

	@Override
	public QueryBuilder visit(BoostingQuery boostingQuery) {
		return _boostingQueryTranslator.translate(boostingQuery, this);
	}

	@Override
	public QueryBuilder visit(CommonTermsQuery commonTermsQuery) {
		return _commonTermsQueryTranslator.translate(commonTermsQuery);
	}

	@Override
	public QueryBuilder visit(ConstantScoreQuery constantScoreQuery) {
		return _constantScoreQueryTranslator.translate(
			constantScoreQuery, this);
	}

	@Override
	public QueryBuilder visit(DateRangeTermQuery dateRangeTermQuery) {
		return _dateRangeTermQueryTranslator.translate(dateRangeTermQuery);
	}

	@Override
	public QueryBuilder visit(DisMaxQuery disMaxQuery) {
		return _disMaxQueryTranslator.translate(disMaxQuery, this);
	}

	@Override
	public QueryBuilder visit(ExistsQuery existsQuery) {
		return _existsQueryTranslator.translate(existsQuery);
	}

	@Override
	public QueryBuilder visit(FunctionScoreQuery functionScoreQuery) {
		return _functionScoreQueryTranslator.translate(
			functionScoreQuery, this);
	}

	@Override
	public QueryBuilder visit(FuzzyQuery fuzzyQuery) {
		return _fuzzyQueryTranslator.translate(fuzzyQuery);
	}

	@Override
	public QueryBuilder visit(GeoBoundingBoxQuery geoBoundingBoxQuery) {
		return _geoBoundingBoxQueryTranslator.translate(geoBoundingBoxQuery);
	}

	@Override
	public QueryBuilder visit(GeoDistanceQuery geoDistanceQuery) {
		return _geoDistanceQueryTranslator.translate(geoDistanceQuery);
	}

	@Override
	public QueryBuilder visit(GeoDistanceRangeQuery geoDistanceRangeQuery) {
		return _geoDistanceRangeQueryTranslator.translate(
			geoDistanceRangeQuery);
	}

	@Override
	public QueryBuilder visit(GeoPolygonQuery geoPolygonQuery) {
		return _geoPolygonQueryTranslator.translate(geoPolygonQuery);
	}

	@Override
	public QueryBuilder visit(GeoShapeQuery geoShapeQuery) {
		return _geoShapeQueryTranslator.translate(geoShapeQuery);
	}

	@Override
	public QueryBuilder visit(IdsQuery idsQuery) {
		return _idsQueryTranslator.translate(idsQuery);
	}

	@Override
	public QueryBuilder visit(MatchAllQuery matchAllQuery) {
		return _matchAllQueryTranslator.translate(matchAllQuery);
	}

	@Override
	public QueryBuilder visit(MatchPhrasePrefixQuery matchPhrasePrefixQuery) {
		return _matchPhrasePrefixQueryTranslator.translate(
			matchPhrasePrefixQuery);
	}

	@Override
	public QueryBuilder visit(MatchPhraseQuery matchPhraseQuery) {
		return _matchPhraseQueryTranslator.translate(matchPhraseQuery);
	}

	@Override
	public QueryBuilder visit(MatchQuery matchQuery) {
		return _matchQueryTranslator.translate(matchQuery);
	}

	@Override
	public QueryBuilder visit(MoreLikeThisQuery moreLikeThisQuery) {
		return _moreLikeThisQueryTranslator.translate(moreLikeThisQuery);
	}

	@Override
	public QueryBuilder visit(MultiMatchQuery multiMatchQuery) {
		return _multiMatchQueryTranslator.translate(multiMatchQuery);
	}

	@Override
	public QueryBuilder visit(NestedQuery nestedQuery) {
		return _nestedQueryTranslator.translate(nestedQuery, this);
	}

	@Override
	public QueryBuilder visit(PercolateQuery percolateQuery) {
		return _percolateQueryTranslator.translate(percolateQuery);
	}

	@Override
	public QueryBuilder visit(PrefixQuery prefixQuery) {
		return _prefixQueryTranslator.translate(prefixQuery);
	}

	@Override
	public QueryBuilder visit(RangeTermQuery rangeTermQuery) {
		return _rangeTermQueryTranslator.translate(rangeTermQuery);
	}

	@Override
	public QueryBuilder visit(RegexQuery regexQuery) {
		return _regexQueryTranslator.translate(regexQuery);
	}

	@Override
	public QueryBuilder visit(ScriptQuery scriptQuery) {
		return _scriptQueryTranslator.translate(scriptQuery);
	}

	@Override
	public QueryBuilder visit(SimpleStringQuery simpleStringQuery) {
		return _simpleQueryStringQueryTranslator.translate(simpleStringQuery);
	}

	@Override
	public QueryBuilder visit(StringQuery stringQuery) {
		return _stringQueryTranslator.translate(stringQuery);
	}

	@Override
	public QueryBuilder visit(TermQuery termQuery) {
		return _termQueryTranslator.translate(termQuery);
	}

	@Override
	public QueryBuilder visit(TermsQuery termsQuery) {
		return _termsQueryTranslator.translate(termsQuery);
	}

	@Override
	public QueryBuilder visit(TermsSetQuery termsSetQuery) {
		return _termsSetQueryTranslator.translate(termsSetQuery);
	}

	@Override
	public QueryBuilder visit(WildcardQuery wildcardQuery) {
		return _wildcardQueryTranslator.translate(wildcardQuery);
	}

	@Override
	public QueryBuilder visit(WrapperQuery wrapperQuery) {
		return _wrapperQueryTranslator.translate(wrapperQuery);
	}

	@Reference
	private BooleanQueryTranslator _booleanQueryTranslator;

	@Reference
	private BoostingQueryTranslator _boostingQueryTranslator;

	@Reference
	private CommonTermsQueryTranslator _commonTermsQueryTranslator;

	@Reference
	private ConstantScoreQueryTranslator _constantScoreQueryTranslator;

	@Reference
	private DateRangeTermQueryTranslator _dateRangeTermQueryTranslator;

	@Reference
	private DisMaxQueryTranslator _disMaxQueryTranslator;

	@Reference
	private ExistsQueryTranslator _existsQueryTranslator;

	@Reference
	private FunctionScoreQueryTranslator _functionScoreQueryTranslator;

	@Reference
	private FuzzyQueryTranslator _fuzzyQueryTranslator;

	@Reference
	private GeoBoundingBoxQueryTranslator _geoBoundingBoxQueryTranslator;

	@Reference
	private GeoDistanceQueryTranslator _geoDistanceQueryTranslator;

	@Reference
	private GeoDistanceRangeQueryTranslator _geoDistanceRangeQueryTranslator;

	@Reference
	private GeoPolygonQueryTranslator _geoPolygonQueryTranslator;

	@Reference
	private GeoShapeQueryTranslator _geoShapeQueryTranslator;

	@Reference
	private IdsQueryTranslator _idsQueryTranslator;

	@Reference
	private MatchAllQueryTranslator _matchAllQueryTranslator;

	@Reference
	private MatchPhrasePrefixQueryTranslator _matchPhrasePrefixQueryTranslator;

	@Reference
	private MatchPhraseQueryTranslator _matchPhraseQueryTranslator;

	@Reference
	private MatchQueryTranslator _matchQueryTranslator;

	@Reference
	private MoreLikeThisQueryTranslator _moreLikeThisQueryTranslator;

	@Reference
	private MultiMatchQueryTranslator _multiMatchQueryTranslator;

	@Reference
	private NestedQueryTranslator _nestedQueryTranslator;

	@Reference
	private PercolateQueryTranslator _percolateQueryTranslator;

	@Reference
	private PrefixQueryTranslator _prefixQueryTranslator;

	@Reference
	private RangeTermQueryTranslator _rangeTermQueryTranslator;

	@Reference
	private RegexQueryTranslator _regexQueryTranslator;

	@Reference
	private ScriptQueryTranslator _scriptQueryTranslator;

	@Reference
	private SimpleStringQueryTranslator _simpleQueryStringQueryTranslator;

	@Reference
	private StringQueryTranslator _stringQueryTranslator;

	@Reference
	private TermQueryTranslator _termQueryTranslator;

	@Reference
	private TermsQueryTranslator _termsQueryTranslator;

	@Reference
	private TermsSetQueryTranslator _termsSetQueryTranslator;

	@Reference
	private WildcardQueryTranslator _wildcardQueryTranslator;

	@Reference
	private WrapperQueryTranslator _wrapperQueryTranslator;

}