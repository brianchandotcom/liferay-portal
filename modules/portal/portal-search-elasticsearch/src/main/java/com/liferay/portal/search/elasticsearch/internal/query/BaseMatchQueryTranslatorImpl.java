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

import com.liferay.portal.kernel.search.generic.MatchQuery;

import org.elasticsearch.index.query.MatchQueryBuilder;

/**
 * @author Michael C. Han
 */
public abstract class BaseMatchQueryTranslatorImpl {

	protected MatchQueryBuilder.Operator translate(
		MatchQuery.Operator matchQueryOperator) {

		if (matchQueryOperator == MatchQuery.Operator.AND) {
			return MatchQueryBuilder.Operator.AND;
		}
		else if (matchQueryOperator == MatchQuery.Operator.OR) {
			return MatchQueryBuilder.Operator.AND;
		}

		throw new IllegalArgumentException(
			"Invalid operator: " + matchQueryOperator);
	}

	protected String translate(
		MatchQuery.RewriteMethod matchQueryRewriteMethod) {

		if (matchQueryRewriteMethod ==
				MatchQuery.RewriteMethod.CONSTANT_SCORE_AUTO) {

			return "constant_score_auto";
		}
		else if (matchQueryRewriteMethod ==
					MatchQuery.RewriteMethod.CONSTANT_SCORE_BOOLEAN) {

			return "constant_score_boolean";
		}
		else if (matchQueryRewriteMethod ==
					MatchQuery.RewriteMethod.CONSTANT_SCORE_FILTER) {

			return "constant_score_filter";
		}
		else if (matchQueryRewriteMethod ==
					MatchQuery.RewriteMethod.SCORING_BOOLEAN) {

			return "scoring_boolean";
		}
		else if (matchQueryRewriteMethod ==
					MatchQuery.RewriteMethod.TOP_TERMS_N) {

			return "top_terms_N";
		}
		else if (matchQueryRewriteMethod ==
					MatchQuery.RewriteMethod.TOP_TERMS_BOOST_N) {

			return "top_terms_boost_N";
		}

		throw new IllegalArgumentException(
			"Invalid rewrite method: " + matchQueryRewriteMethod);
	}

	protected MatchQueryBuilder.ZeroTermsQuery translate(
		MatchQuery.ZeroTermsQuery matchQueryZeroTermsQuery) {

		if (matchQueryZeroTermsQuery == MatchQuery.ZeroTermsQuery.ALL) {
			return MatchQueryBuilder.ZeroTermsQuery.ALL;
		}
		else if (matchQueryZeroTermsQuery == MatchQuery.ZeroTermsQuery.NONE) {
			return MatchQueryBuilder.ZeroTermsQuery.NONE;
		}

		throw new IllegalArgumentException(
			"Invalid zero terms query: " + matchQueryZeroTermsQuery);
	}

}