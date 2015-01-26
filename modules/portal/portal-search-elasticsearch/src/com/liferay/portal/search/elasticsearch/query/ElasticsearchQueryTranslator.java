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

package com.liferay.portal.search.elasticsearch.query;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTranslator;
import com.liferay.portal.kernel.search.QueryVisitor;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(immediate = true, service = QueryTranslator.class)
public class ElasticsearchQueryTranslator
	implements QueryTranslator<QueryBuilder>, QueryVisitor<QueryBuilder> {

	@Reference
	public void setBooleanQueryTranslator(
		BooleanQueryTranslator booleanQueryTranslator) {

		this._booleanQueryTranslator = booleanQueryTranslator;
	}

	@Reference
	public void setTermQueryTranslator(
		TermQueryTranslator termQueryTranslator) {

		this._termQueryTranslator = termQueryTranslator;
	}

	@Reference
	public void setTermRangeQueryTranslator(
		TermRangeQueryTranslator termRangeQueryTranslator) {

		this._termRangeQueryTranslator = termRangeQueryTranslator;
	}

	@Reference
	public void setWildcardQueryTranslator(
		WildcardQueryTranslator wildcardQueryTranslator) {

		this._wildcardQueryTranslator = wildcardQueryTranslator;
	}

	@Override
	public QueryBuilder translate(Query query) throws ParseException {
		QueryBuilder queryBuilder = query.accept(this);

		if (queryBuilder == null) {
			queryBuilder = QueryBuilders.queryString(query.toString());
		}

		return queryBuilder;
	}

	@Override
	public Object translateForSolr(Query query) throws ParseException {
		throw new UnsupportedOperationException();
	}

	@Override
	public QueryBuilder visitQuery(BooleanQuery booleanQuery) {
		return _booleanQueryTranslator.translate(booleanQuery, this);
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

	private BooleanQueryTranslator _booleanQueryTranslator;
	private TermQueryTranslator _termQueryTranslator;
	private TermRangeQueryTranslator _termRangeQueryTranslator;
	private WildcardQueryTranslator _wildcardQueryTranslator;

}