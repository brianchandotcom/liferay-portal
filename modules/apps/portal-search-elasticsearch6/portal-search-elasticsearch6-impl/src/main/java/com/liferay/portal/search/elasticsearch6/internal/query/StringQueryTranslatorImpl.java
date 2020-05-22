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

import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.StringQuery;

import java.util.Map.Entry;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(service = StringQueryTranslator.class)
public class StringQueryTranslatorImpl implements StringQueryTranslator {

	@Override
	public QueryBuilder translate(StringQuery stringQuery) {
		QueryStringQueryBuilder queryStringQueryBuilder =
			QueryBuilders.queryStringQuery(stringQuery.getQuery());

		if (stringQuery.getAllowLeadingWildcard() != null) {
			queryStringQueryBuilder.allowLeadingWildcard(
				stringQuery.getAllowLeadingWildcard());
		}

		if (stringQuery.getAnalyzer() != null) {
			queryStringQueryBuilder.analyzer(stringQuery.getAnalyzer());
		}

		if (stringQuery.getAnalyzeWildcard() != null) {
			queryStringQueryBuilder.analyzeWildcard(
				stringQuery.getAnalyzeWildcard());
		}

		if (stringQuery.getAutoGenerateSynonymsPhraseQuery() != null) {
			queryStringQueryBuilder.autoGenerateSynonymsPhraseQuery(
				stringQuery.getAutoGenerateSynonymsPhraseQuery());
		}

		if (stringQuery.getDefaultField() != null) {
			queryStringQueryBuilder.defaultField(stringQuery.getDefaultField());
		}

		if (stringQuery.getDefaultOperator() != null) {
			Operator operator = stringQuery.getDefaultOperator();

			if (operator == Operator.OR) {
				queryStringQueryBuilder.defaultOperator(
					org.elasticsearch.index.query.Operator.OR);
			}
			else if (operator == Operator.AND) {
				queryStringQueryBuilder.defaultOperator(
					org.elasticsearch.index.query.Operator.AND);
			}
			else {
				throw new IllegalArgumentException(
					"Invalid operator: " + operator);
			}
		}

		if (stringQuery.getEnablePositionIncrements() != null) {
			queryStringQueryBuilder.enablePositionIncrements(
				stringQuery.getEnablePositionIncrements());
		}

		if (stringQuery.getEscape() != null) {
			queryStringQueryBuilder.escape(stringQuery.getEscape());
		}
		
		if (!stringQuery.getFields().isEmpty()) {

			for (Entry<String, Float> entry : stringQuery.getFieldsBoosts().entrySet()) {
				if (entry.getValue() == null) {
					queryStringQueryBuilder.field(
							entry.getKey());
				} else {
					queryStringQueryBuilder.field(
							entry.getKey(), entry.getValue());
				}
			}
		}		

		if (stringQuery.getFuzziness() != null) {
			queryStringQueryBuilder.fuzziness(
				Fuzziness.build(stringQuery.getFuzziness()));
		}

		if (stringQuery.getFuzzyMaxExpansions() != null) {
			queryStringQueryBuilder.fuzzyMaxExpansions(
				stringQuery.getFuzzyMaxExpansions());
		}

		if (stringQuery.getFuzzyPrefixLength() != null) {
			queryStringQueryBuilder.fuzzyPrefixLength(
				stringQuery.getFuzzyPrefixLength());
		}

		if (stringQuery.getFuzzyRewrite() != null) {
			queryStringQueryBuilder.fuzzyRewrite(
					stringQuery.getFuzzyRewrite());
		}
		
		if (stringQuery.getFuzzyTranspositions() != null) {
			queryStringQueryBuilder.fuzzyTranspositions(
				stringQuery.getFuzzyTranspositions());
		}

		if (stringQuery.getLenient() != null) {
			queryStringQueryBuilder.lenient(stringQuery.getLenient());
		}

		if (stringQuery.getMaxDeterminedStates() != null) {
			queryStringQueryBuilder.maxDeterminizedStates(
				stringQuery.getMaxDeterminedStates());
		}

		if (stringQuery.getMinimumShouldMatch() != null) {
			queryStringQueryBuilder.minimumShouldMatch(
				stringQuery.getMinimumShouldMatch());
		}
		
		if (stringQuery.getPhraseSlop() != null) {
			queryStringQueryBuilder.phraseSlop(stringQuery.getPhraseSlop());
		}

		if (stringQuery.getQuoteAnalyzer() != null) {
			queryStringQueryBuilder.quoteAnalyzer(
				stringQuery.getQuoteAnalyzer());
		}

		if (stringQuery.getQuoteFieldSuffix() != null) {
			queryStringQueryBuilder.quoteFieldSuffix(
				stringQuery.getQuoteFieldSuffix());
		}

		if (stringQuery.getRewrite() != null) {
			queryStringQueryBuilder.rewrite(stringQuery.getRewrite());
		}

		if (stringQuery.getTieBreaker() != null) {
			queryStringQueryBuilder.tieBreaker(stringQuery.getTieBreaker());
		}
		
		if (stringQuery.getTimeZone() != null) {
			queryStringQueryBuilder.timeZone(stringQuery.getTimeZone());
		}

		return queryStringQueryBuilder;
	}

}