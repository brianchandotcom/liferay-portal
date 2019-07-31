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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.MultiMatchQuery;

import java.util.Map;
import java.util.Set;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = MultiMatchQueryTranslator.class)
public class MultiMatchQueryTranslatorImpl
	extends BaseMatchQueryTranslatorImpl implements MultiMatchQueryTranslator {

	@Override
	public QueryBuilder translate(MultiMatchQuery multiMatchQuery) {
		Set<String> fields = multiMatchQuery.getFields();

		MultiMatchQueryBuilder multiMatchQueryBuilder =
			QueryBuilders.multiMatchQuery(
				multiMatchQuery.getValue(), fields.toArray(new String[0]));

		if (Validator.isNotNull(multiMatchQuery.getAnalyzer())) {
			multiMatchQueryBuilder.analyzer(multiMatchQuery.getAnalyzer());
		}

		if (multiMatchQuery.getCutOffFrequency() != null) {
			multiMatchQueryBuilder.cutoffFrequency(
				multiMatchQuery.getCutOffFrequency());
		}

		Map<String, Float> fieldBoosts = multiMatchQuery.getFieldsBoosts();

		fieldBoosts.forEach(multiMatchQueryBuilder::field);

		if (multiMatchQuery.getFuzziness() != null) {
			multiMatchQueryBuilder.fuzziness(
				Fuzziness.build(multiMatchQuery.getFuzziness()));
		}

		if (multiMatchQuery.getFuzzyRewriteMethod() != null) {
			String multiMatchQueryFuzzyRewriteMethod = translate(
				multiMatchQuery.getFuzzyRewriteMethod());

			multiMatchQueryBuilder.fuzzyRewrite(
				multiMatchQueryFuzzyRewriteMethod);
		}

		if (multiMatchQuery.getMaxExpansions() != null) {
			multiMatchQueryBuilder.maxExpansions(
				multiMatchQuery.getMaxExpansions());
		}

		if (Validator.isNotNull(multiMatchQuery.getMinShouldMatch())) {
			multiMatchQueryBuilder.minimumShouldMatch(
				multiMatchQuery.getMinShouldMatch());
		}

		if (multiMatchQuery.getOperator() != null) {
			Operator matchQueryBuilderOperator = translate(
				multiMatchQuery.getOperator());

			multiMatchQueryBuilder.operator(matchQueryBuilderOperator);
		}

		if (multiMatchQuery.getPrefixLength() != null) {
			multiMatchQueryBuilder.prefixLength(
				multiMatchQuery.getPrefixLength());
		}

		if (multiMatchQuery.getSlop() != null) {
			multiMatchQueryBuilder.slop(multiMatchQuery.getSlop());
		}

		if (multiMatchQuery.getType() != null) {
			MultiMatchQueryBuilder.Type multiMatchQueryBuilderType = translate(
				multiMatchQuery.getType());

			multiMatchQueryBuilder.type(multiMatchQueryBuilderType);
		}

		if (multiMatchQuery.getZeroTermsQuery() != null) {
			MatchQuery.ZeroTermsQuery multiMatchQueryBuilderZeroTermsQuery =
				translate(multiMatchQuery.getZeroTermsQuery());

			multiMatchQueryBuilder.zeroTermsQuery(
				multiMatchQueryBuilderZeroTermsQuery);
		}

		if (multiMatchQuery.isLenient() != null) {
			multiMatchQueryBuilder.lenient(multiMatchQuery.isLenient());
		}

		return multiMatchQueryBuilder;
	}

	protected MultiMatchQueryBuilder.Type translate(
		MultiMatchQuery.Type multiMatchQueryType) {

		if (multiMatchQueryType == MultiMatchQuery.Type.BEST_FIELDS) {
			return MultiMatchQueryBuilder.Type.BEST_FIELDS;
		}
		else if (multiMatchQueryType == MultiMatchQuery.Type.CROSS_FIELDS) {
			return MultiMatchQueryBuilder.Type.CROSS_FIELDS;
		}
		else if (multiMatchQueryType == MultiMatchQuery.Type.MOST_FIELDS) {
			return MultiMatchQueryBuilder.Type.MOST_FIELDS;
		}
		else if (multiMatchQueryType == MultiMatchQuery.Type.PHRASE) {
			return MultiMatchQueryBuilder.Type.PHRASE;
		}
		else if (multiMatchQueryType == MultiMatchQuery.Type.PHRASE_PREFIX) {
			return MultiMatchQueryBuilder.Type.PHRASE_PREFIX;
		}

		throw new IllegalArgumentException(
			"Invalid multi match query type: " + multiMatchQueryType);
	}

}