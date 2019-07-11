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

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = {QueryToQueryBuilderTranslator.class, QueryTranslator.class}
)
public class ElasticsearchQueryTranslator
	implements QueryToQueryBuilderTranslator, QueryTranslator<QueryBuilder>,
			   QueryVisitor<QueryBuilder> {

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

	@Reference(unbind = "-")
	protected void setBooleanQueryTranslator(
		BooleanQueryTranslator booleanQueryTranslator) {

		_booleanQueryTranslator = booleanQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setBoostingQueryTranslator(
		BoostingQueryTranslator boostingQueryTranslator) {

		_boostingQueryTranslator = boostingQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setCommonTermsQueryTranslator(
		CommonTermsQueryTranslator commonTermsQueryTranslator) {

		_commonTermsQueryTranslator = commonTermsQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setConstantScoreQueryTranslator(
		ConstantScoreQueryTranslator constantScoreQueryTranslator) {

		_constantScoreQueryTranslator = constantScoreQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setDateRangeTermQueryTranslator(
		DateRangeTermQueryTranslator dateRangeTermQueryTranslator) {

		_dateRangeTermQueryTranslator = dateRangeTermQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setDisMaxQueryTranslator(
		DisMaxQueryTranslator disMaxQueryTranslator) {

		_disMaxQueryTranslator = disMaxQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setExistsQueryTranslator(
		ExistsQueryTranslator existsQueryTranslator) {

		_existsQueryTranslator = existsQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setFunctionScoreQueryTranslator(
		FunctionScoreQueryTranslator functionScoreQueryTranslator) {

		_functionScoreQueryTranslator = functionScoreQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setFuzzyQueryTranslator(
		FuzzyQueryTranslator fuzzyQueryTranslator) {

		_fuzzyQueryTranslator = fuzzyQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoBoundingBoxQueryTranslator(
		GeoBoundingBoxQueryTranslator geoBoundingBoxQueryTranslator) {

		_geoBoundingBoxQueryTranslator = geoBoundingBoxQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoDistanceQueryTranslator(
		GeoDistanceQueryTranslator geoDistanceQueryTranslator) {

		_geoDistanceQueryTranslator = geoDistanceQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoDistanceRangeQueryTranslator(
		GeoDistanceRangeQueryTranslator geoDistanceRangeQueryTranslator) {

		_geoDistanceRangeQueryTranslator = geoDistanceRangeQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoPolygonQueryTranslator(
		GeoPolygonQueryTranslator geoPolygonQueryTranslator) {

		_geoPolygonQueryTranslator = geoPolygonQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoShapeQueryTranslator(
		GeoShapeQueryTranslator geoShapeQueryTranslator) {

		_geoShapeQueryTranslator = geoShapeQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setIdsQueryTranslator(
		IdsQueryTranslator idsQueryTranslator) {

		_idsQueryTranslator = idsQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setMatchAllQueryTranslator(
		MatchAllQueryTranslator matchAllQueryTranslator) {

		_matchAllQueryTranslator = matchAllQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setMatchPhrasePrefixQueryTranslator(
		MatchPhrasePrefixQueryTranslator matchPhrasePrefixQueryTranslator) {

		_matchPhrasePrefixQueryTranslator = matchPhrasePrefixQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setMatchPhraseQueryTranslator(
		MatchPhraseQueryTranslator matchPhraseQueryTranslator) {

		_matchPhraseQueryTranslator = matchPhraseQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setMatchQueryTranslator(
		MatchQueryTranslator matchQueryTranslator) {

		_matchQueryTranslator = matchQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setMoreLikeThisQueryTranslator(
		MoreLikeThisQueryTranslator moreLikeThisQueryTranslator) {

		_moreLikeThisQueryTranslator = moreLikeThisQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setMultiMatchQueryTranslator(
		MultiMatchQueryTranslator multiMatchQueryTranslator) {

		_multiMatchQueryTranslator = multiMatchQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setNestedQueryTranslator(
		NestedQueryTranslator nestedQueryTranslator) {

		_nestedQueryTranslator = nestedQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setPercolateQueryTranslator(
		PercolateQueryTranslator percolateQueryTranslator) {

		_percolateQueryTranslator = percolateQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setPrefixQueryTranslator(
		PrefixQueryTranslator prefixQueryTranslator) {

		_prefixQueryTranslator = prefixQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setRangeTermQueryTranslator(
		RangeTermQueryTranslator rangeTermQueryTranslator) {

		_rangeTermQueryTranslator = rangeTermQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setRegexQueryTranslator(
		RegexQueryTranslator regexQueryTranslator) {

		_regexQueryTranslator = regexQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setScriptQueryTranslator(
		ScriptQueryTranslator scriptQueryTranslator) {

		_scriptQueryTranslator = scriptQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setSimpleQueryStringQueryTranslator(
		SimpleStringQueryTranslator simpleQueryStringQueryTranslator) {

		_simpleQueryStringQueryTranslator = simpleQueryStringQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setStringQueryTranslator(
		StringQueryTranslator stringQueryTranslator) {

		_stringQueryTranslator = stringQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setTermQueryTranslator(
		TermQueryTranslator termQueryTranslator) {

		_termQueryTranslator = termQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setTermsQueryTranslator(
		TermsQueryTranslator termsQueryTranslator) {

		_termsQueryTranslator = termsQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setTermsSetQueryTranslator(
		TermsSetQueryTranslator termsSetQueryTranslator) {

		_termsSetQueryTranslator = termsSetQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setWildcardQueryTranslator(
		WildcardQueryTranslator wildcardQueryTranslator) {

		_wildcardQueryTranslator = wildcardQueryTranslator;
	}

	private BooleanQueryTranslator _booleanQueryTranslator;
	private BoostingQueryTranslator _boostingQueryTranslator;
	private CommonTermsQueryTranslator _commonTermsQueryTranslator;
	private ConstantScoreQueryTranslator _constantScoreQueryTranslator;
	private DateRangeTermQueryTranslator _dateRangeTermQueryTranslator;
	private DisMaxQueryTranslator _disMaxQueryTranslator;
	private ExistsQueryTranslator _existsQueryTranslator;
	private FunctionScoreQueryTranslator _functionScoreQueryTranslator;
	private FuzzyQueryTranslator _fuzzyQueryTranslator;
	private GeoBoundingBoxQueryTranslator _geoBoundingBoxQueryTranslator;
	private GeoDistanceQueryTranslator _geoDistanceQueryTranslator;
	private GeoDistanceRangeQueryTranslator _geoDistanceRangeQueryTranslator;
	private GeoPolygonQueryTranslator _geoPolygonQueryTranslator;
	private GeoShapeQueryTranslator _geoShapeQueryTranslator;
	private IdsQueryTranslator _idsQueryTranslator;
	private MatchAllQueryTranslator _matchAllQueryTranslator;
	private MatchPhrasePrefixQueryTranslator _matchPhrasePrefixQueryTranslator;
	private MatchPhraseQueryTranslator _matchPhraseQueryTranslator;
	private MatchQueryTranslator _matchQueryTranslator;
	private MoreLikeThisQueryTranslator _moreLikeThisQueryTranslator;
	private MultiMatchQueryTranslator _multiMatchQueryTranslator;
	private NestedQueryTranslator _nestedQueryTranslator;
	private PercolateQueryTranslator _percolateQueryTranslator;
	private PrefixQueryTranslator _prefixQueryTranslator;
	private RangeTermQueryTranslator _rangeTermQueryTranslator;
	private RegexQueryTranslator _regexQueryTranslator;
	private ScriptQueryTranslator _scriptQueryTranslator;
	private SimpleStringQueryTranslator _simpleQueryStringQueryTranslator;
	private StringQueryTranslator _stringQueryTranslator;
	private TermQueryTranslator _termQueryTranslator;
	private TermsQueryTranslator _termsQueryTranslator;
	private TermsSetQueryTranslator _termsSetQueryTranslator;
	private WildcardQueryTranslator _wildcardQueryTranslator;

}