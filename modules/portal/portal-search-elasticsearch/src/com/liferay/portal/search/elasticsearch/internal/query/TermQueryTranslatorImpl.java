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

import com.liferay.portal.kernel.search.QueryPreProcessConfiguration;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.query.TermQueryTranslator;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(immediate = true, service = TermQueryTranslator.class)
public class TermQueryTranslatorImpl implements TermQueryTranslator {

	@Override
	public QueryBuilder translate(TermQuery termQuery) {
		QueryTerm queryTerm = termQuery.getQueryTerm();

		String field = queryTerm.getField();
		String value = queryTerm.getValue();

		if ((_queryPreProcessConfiguration != null) &&
			_queryPreProcessConfiguration.isSubstringSearchAlways(field)) {

			WildcardQueryBuilder wildcardQueryBuilder =
				toCaseInsensitiveSubstringQuery(field, value);

			if (!termQuery.isDefaultBoost()) {
				wildcardQueryBuilder.boost(termQuery.getBoost());
			}

			return wildcardQueryBuilder;
		}

		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(
			field, value);

		if (!termQuery.isDefaultBoost()) {
			matchQueryBuilder.boost(termQuery.getBoost());
		}

		if (Validator.isNotNull(termQuery.getAnalyzer())) {
			matchQueryBuilder.analyzer(termQuery.getAnalyzer());
		}

		return matchQueryBuilder;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = queryPreProcessConfiguration;
	}

	protected WildcardQueryBuilder toCaseInsensitiveSubstringQuery(
		String field, String value) {

		value = StringUtil.replace(value, StringPool.PERCENT, StringPool.BLANK);
		value = StringUtil.toLowerCase(value);
		value = StringPool.STAR + value + StringPool.STAR;

		return QueryBuilders.wildcardQuery(field, value);
	}

	protected void unsetQueryPreProcessConfiguration(
		QueryPreProcessConfiguration queryPreProcessConfiguration) {

		_queryPreProcessConfiguration = null;
	}

	private QueryPreProcessConfiguration _queryPreProcessConfiguration;

}