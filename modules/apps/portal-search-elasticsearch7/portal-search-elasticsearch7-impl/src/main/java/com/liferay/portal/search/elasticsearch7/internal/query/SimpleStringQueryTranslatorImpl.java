/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.SimpleStringQuery;

import java.util.Map;

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
			for (Map.Entry<String, Float> entry : fieldBoostMap.entrySet()) {
				Float value = entry.getValue();

				if (value != null) {
					simpleQueryStringBuilder.field(entry.getKey(), value);
				}
				else {
					simpleQueryStringBuilder.field(entry.getKey());
				}
			}
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