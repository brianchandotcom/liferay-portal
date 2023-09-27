/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.Operator;
import com.liferay.portal.search.query.StringQuery;

import java.util.Map;

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

		Map<String, Float> fieldsBoosts = stringQuery.getFieldsBoosts();

		for (Map.Entry<String, Float> entry : fieldsBoosts.entrySet()) {
			Float boost = entry.getValue();
			String field = entry.getKey();

			if (boost == null) {
				queryStringQueryBuilder.field(field);
			}
			else {
				queryStringQueryBuilder.field(field, boost);
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
			queryStringQueryBuilder.fuzzyRewrite(stringQuery.getFuzzyRewrite());
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