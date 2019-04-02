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

package com.liferay.portal.search.elasticsearch6.internal.query;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.SimpleStringQuery;

import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = SimpleStringQueryTranslator.class)
public class SimpleStringQueryTranslatorImpl
	implements SimpleStringQueryTranslator {

	@Override
	public QueryBuilder translate(SimpleStringQuery simpleStringQuery) {
		SimpleQueryStringBuilder simpleQueryStringBuilder =
			QueryBuilders.simpleQueryStringQuery(simpleStringQuery.getQuery());

		if (simpleStringQuery.getAnalyzer() != null) {
			simpleQueryStringBuilder.analyzer(simpleStringQuery.getAnalyzer());
		}

		if (simpleStringQuery.getAnalyzeWildcard() != null) {
			simpleQueryStringBuilder.analyzeWildcard(
				simpleStringQuery.getAnalyzeWildcard());
		}

		if (simpleStringQuery.getAutoGenerateSynonymsPhraseQuery() != null) {
			simpleQueryStringBuilder.autoGenerateSynonymsPhraseQuery(
				simpleStringQuery.getAutoGenerateSynonymsPhraseQuery());
		}

		Map<String, Float> fieldBoostMap = simpleStringQuery.getFieldBoostMap();

		if (MapUtil.isNotEmpty(fieldBoostMap)) {
			simpleQueryStringBuilder.fields(
				fieldBoostMap.entrySet(
				).stream(
				).collect(
					Collectors.toMap(
						Map.Entry::getKey,
						entry -> GetterUtil.getFloat(
							entry.getValue(),
							AbstractQueryBuilder.DEFAULT_BOOST))
				));
		}

		if (simpleStringQuery.getDefaultOperator() != null) {
			Operator operator = simpleStringQuery.getDefaultOperator();

			if (operator == Operator.OR) {
				simpleQueryStringBuilder.defaultOperator(
					org.elasticsearch.index.query.Operator.OR);
			}
			else if (operator == Operator.AND) {
				simpleQueryStringBuilder.defaultOperator(
					org.elasticsearch.index.query.Operator.AND);
			}
			else {
				throw new IllegalArgumentException(
					"Invalid operator: " + operator);
			}
		}

		if (simpleStringQuery.getFuzzyMaxExpansions() != null) {
			simpleQueryStringBuilder.fuzzyMaxExpansions(
				simpleStringQuery.getFuzzyMaxExpansions());
		}

		if (simpleStringQuery.getFuzzyPrefixLength() != null) {
			simpleQueryStringBuilder.fuzzyPrefixLength(
				simpleStringQuery.getFuzzyPrefixLength());
		}

		if (simpleStringQuery.getFuzzyTranspositions() != null) {
			simpleQueryStringBuilder.fuzzyTranspositions(
				simpleStringQuery.getFuzzyTranspositions());
		}

		if (simpleStringQuery.getLenient() != null) {
			simpleQueryStringBuilder.lenient(simpleStringQuery.getLenient());
		}

		if (simpleStringQuery.getQuoteFieldSuffix() != null) {
			simpleQueryStringBuilder.quoteFieldSuffix(
				simpleStringQuery.getQuoteFieldSuffix());
		}

		return simpleQueryStringBuilder;
	}

}