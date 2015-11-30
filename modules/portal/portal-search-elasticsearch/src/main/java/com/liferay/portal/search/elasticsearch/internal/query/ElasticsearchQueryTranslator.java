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

package com.liferay.portal.search.elasticsearch.internal.query;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.DisMaxQuery;
import com.liferay.portal.kernel.search.generic.FuzzyQuery;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.MoreLikeThisQuery;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.search.elasticsearch.query.BooleanQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.DisMaxQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.FuzzyQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.MatchAllQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.MatchQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.MoreLikeThisQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.MultiMatchQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.NestedQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.StringQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.TermQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.TermRangeQueryTranslator;
import com.liferay.portal.search.elasticsearch.query.WildcardQueryTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(
	immediate = true, property = {"search.engine.impl=Elasticsearch"},
	service = QueryTranslator.class
)
public class ElasticsearchQueryTranslator
	implements QueryTranslator<QueryBuilder>, QueryVisitor<QueryBuilder> {

	@Override
	public QueryBuilder translate(Query query, SearchContext searchContext) {
		QueryBuilder queryBuilder = query.accept(this);

		if (queryBuilder == null) {
			queryBuilder = QueryBuilders.queryStringQuery(query.toString());
		}

		return queryBuilder;
	}

	@Override
	public QueryBuilder visitQuery(BooleanQuery booleanQuery) {
		return _booleanQueryTranslator.translate(booleanQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(DisMaxQuery disMaxQuery) {
		return _disMaxQueryTranslator.translate(disMaxQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(FuzzyQuery fuzzyQuery) {
		return _fuzzyQueryTranslator.translate(fuzzyQuery);
	}

	@Override
	public QueryBuilder visitQuery(MatchAllQuery matchAllQuery) {
		return _matchAllQueryTranslator.translate(matchAllQuery);
	}

	@Override
	public QueryBuilder visitQuery(MatchQuery matchQuery) {
		return _matchQueryTranslator.translate(matchQuery);
	}

	@Override
	public QueryBuilder visitQuery(MoreLikeThisQuery moreLikeThisQuery) {
		return _moreLikeThisQueryTranslator.translate(moreLikeThisQuery);
	}

	@Override
	public QueryBuilder visitQuery(MultiMatchQuery multiMatchQuery) {
		return _multiMatchQueryTranslator.translate(multiMatchQuery);
	}

	@Override
	public QueryBuilder visitQuery(NestedQuery nestedQuery) {
		return _nestedQueryTranslator.translate(nestedQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(StringQuery stringQuery) {
		return _stringQueryTranslator.translate(stringQuery);
	}

	@Override
	public QueryBuilder visitQuery(TermQuery termQuery) {
		return _termQueryTranslator.translate(termQuery);
	}

	@Override
	public QueryBuilder visitQuery(TermRangeQuery termRangeQuery) {
		return _termRangeQueryTranslator.translate(termRangeQuery);
	}

	@Override
	public QueryBuilder visitQuery(WildcardQuery wildcardQuery) {
		return _wildcardQueryTranslator.translate(wildcardQuery);
	}

	@Reference(unbind = "-")
	protected void setBooleanQueryTranslator(
		BooleanQueryTranslator booleanQueryTranslator) {

		_booleanQueryTranslator = booleanQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setDisMaxQueryTranslator(
		DisMaxQueryTranslator disMaxQueryTranslator) {

		_disMaxQueryTranslator = disMaxQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setFuzzyQueryTranslator(
		FuzzyQueryTranslator fuzzyQueryTranslator) {

		_fuzzyQueryTranslator = fuzzyQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setMatchAllQueryTranslator(
		MatchAllQueryTranslator matchAllQueryTranslator) {

		_matchAllQueryTranslator = matchAllQueryTranslator;
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
	protected void setTermRangeQueryTranslator(
		TermRangeQueryTranslator termRangeQueryTranslator) {

		_termRangeQueryTranslator = termRangeQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setWildcardQueryTranslator(
		WildcardQueryTranslator wildcardQueryTranslator) {

		_wildcardQueryTranslator = wildcardQueryTranslator;
	}

	private volatile BooleanQueryTranslator _booleanQueryTranslator;
	private volatile DisMaxQueryTranslator _disMaxQueryTranslator;
	private volatile FuzzyQueryTranslator _fuzzyQueryTranslator;
	private volatile MatchAllQueryTranslator _matchAllQueryTranslator;
	private volatile MatchQueryTranslator _matchQueryTranslator;
	private volatile MoreLikeThisQueryTranslator _moreLikeThisQueryTranslator;
	private volatile MultiMatchQueryTranslator _multiMatchQueryTranslator;
	private volatile NestedQueryTranslator _nestedQueryTranslator;
	private volatile StringQueryTranslator _stringQueryTranslator;
	private volatile TermQueryTranslator _termQueryTranslator;
	private volatile TermRangeQueryTranslator _termRangeQueryTranslator;
	private volatile WildcardQueryTranslator _wildcardQueryTranslator;

}