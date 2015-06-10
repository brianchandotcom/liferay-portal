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

import com.liferay.portal.kernel.search.generic.FuzzyLikeThisQuery;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.query.FuzzyLikeThisQueryTranslator;

import java.util.Set;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.FuzzyLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = FuzzyLikeThisQueryTranslator.class)
public class FuzzyLikeThisQueryTranslatorImpl
	implements FuzzyLikeThisQueryTranslator {

	@Override
	public QueryBuilder translate(FuzzyLikeThisQuery fuzzyLikeThisQuery) {
		Set<String> fields = fuzzyLikeThisQuery.getFields();

		FuzzyLikeThisQueryBuilder fuzzyLikeThisQueryBuilder =
			QueryBuilders.fuzzyLikeThisQuery(
				fields.toArray(new String[fields.size()]));

		if (Validator.isNotNull(fuzzyLikeThisQuery.getAnalyzer())) {
			fuzzyLikeThisQueryBuilder.analyzer(
				fuzzyLikeThisQuery.getAnalyzer());
		}

		if (fuzzyLikeThisQuery.getFuzziness() != null) {
			fuzzyLikeThisQueryBuilder.fuzziness(
				Fuzziness.fromSimilarity(fuzzyLikeThisQuery.getFuzziness()));
		}

		if (fuzzyLikeThisQuery.getMaxQueryTerms() != null) {
			fuzzyLikeThisQueryBuilder.maxQueryTerms(
				fuzzyLikeThisQuery.getMaxQueryTerms());
		}

		if (fuzzyLikeThisQuery.getPrefixLength() != null) {
			fuzzyLikeThisQueryBuilder.prefixLength(
				fuzzyLikeThisQuery.getPrefixLength());
		}

		if (!fuzzyLikeThisQuery.isDefaultBoost()) {
			fuzzyLikeThisQueryBuilder.boost(fuzzyLikeThisQuery.getBoost());
		}

		if (fuzzyLikeThisQuery.isIgnoreTermFrequency() != null) {
			fuzzyLikeThisQueryBuilder.ignoreTF(
				fuzzyLikeThisQuery.isIgnoreTermFrequency());
		}

		fuzzyLikeThisQueryBuilder.likeText(fuzzyLikeThisQuery.getLikeText());

		return fuzzyLikeThisQueryBuilder;
	}

}