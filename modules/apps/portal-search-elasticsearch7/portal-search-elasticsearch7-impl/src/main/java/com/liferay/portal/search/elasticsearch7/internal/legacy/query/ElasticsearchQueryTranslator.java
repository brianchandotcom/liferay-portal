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

package com.liferay.portal.search.elasticsearch7.internal.legacy.query;

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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.search.elasticsearch7.internal.filter.QueryFilterTranslator;
import com.liferay.portal.search.elasticsearch7.internal.filter.QueryFilterTranslatorImpl;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = {QueryToQueryBuilderTranslator.class, QueryTranslator.class}
)
public class ElasticsearchQueryTranslator
	implements QueryToQueryBuilderTranslator, QueryTranslator<QueryBuilder>,
			   QueryVisitor<QueryBuilder> {

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
		return booleanQueryTranslator.translate(booleanQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(DisMaxQuery disMaxQuery) {
		return disMaxQueryTranslator.translate(disMaxQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(FuzzyQuery fuzzyQuery) {
		return fuzzyQueryTranslator.translate(fuzzyQuery);
	}

	@Override
	public QueryBuilder visitQuery(MatchAllQuery matchAllQuery) {
		return matchAllQueryTranslator.translate(matchAllQuery);
	}

	@Override
	public QueryBuilder visitQuery(MatchQuery matchQuery) {
		return matchQueryTranslator.translate(matchQuery);
	}

	@Override
	public QueryBuilder visitQuery(MoreLikeThisQuery moreLikeThisQuery) {
		return moreLikeThisQueryTranslator.translate(moreLikeThisQuery);
	}

	@Override
	public QueryBuilder visitQuery(MultiMatchQuery multiMatchQuery) {
		return multiMatchQueryTranslator.translate(multiMatchQuery);
	}

	@Override
	public QueryBuilder visitQuery(NestedQuery nestedQuery) {
		return nestedQueryTranslator.translate(nestedQuery, this);
	}

	@Override
	public QueryBuilder visitQuery(StringQuery stringQuery) {
		return stringQueryTranslator.translate(stringQuery);
	}

	@Override
	public QueryBuilder visitQuery(TermQuery termQuery) {
		return termQueryTranslator.translate(termQuery);
	}

	@Override
	public QueryBuilder visitQuery(TermRangeQuery termRangeQuery) {
		return termRangeQueryTranslator.translate(termRangeQuery);
	}

	@Override
	public QueryBuilder visitQuery(WildcardQuery wildcardQuery) {
		return wildcardQueryTranslator.translate(wildcardQuery);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		QueryFilterTranslator queryFilterTranslator =
			new QueryFilterTranslatorImpl(this);

		_serviceRegistration = bundleContext.registerService(
			QueryFilterTranslator.class, queryFilterTranslator,
			new HashMapDictionary<>());
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Reference
	protected BooleanQueryTranslator booleanQueryTranslator;

	@Reference
	protected DisMaxQueryTranslator disMaxQueryTranslator;

	@Reference
	protected FuzzyQueryTranslator fuzzyQueryTranslator;

	@Reference
	protected MatchAllQueryTranslator matchAllQueryTranslator;

	@Reference
	protected MatchQueryTranslator matchQueryTranslator;

	@Reference
	protected MoreLikeThisQueryTranslator moreLikeThisQueryTranslator;

	@Reference
	protected MultiMatchQueryTranslator multiMatchQueryTranslator;

	@Reference
	protected NestedQueryTranslator nestedQueryTranslator;

	@Reference
	protected StringQueryTranslator stringQueryTranslator;

	@Reference
	protected TermQueryTranslator termQueryTranslator;

	@Reference
	protected TermRangeQueryTranslator termRangeQueryTranslator;

	@Reference
	protected WildcardQueryTranslator wildcardQueryTranslator;

	private ServiceRegistration<QueryFilterTranslator> _serviceRegistration;

}