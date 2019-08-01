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

import com.liferay.portal.search.query.CommonTermsQuery;
import com.liferay.portal.search.query.Operator;

import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = CommonTermsQueryTranslator.class)
public class CommonTermsQueryTranslatorImpl
	implements CommonTermsQueryTranslator {

	@Override
	public QueryBuilder translate(CommonTermsQuery commonTermsQuery) {
		CommonTermsQueryBuilder commonTermsQueryBuilder =
			QueryBuilders.commonTermsQuery(
				commonTermsQuery.getField(), commonTermsQuery.getText());

		if (commonTermsQuery.getAnalyzer() != null) {
			commonTermsQueryBuilder.analyzer(commonTermsQuery.getAnalyzer());
		}

		if (commonTermsQuery.getCutoffFrequency() != null) {
			commonTermsQueryBuilder.cutoffFrequency(
				commonTermsQuery.getCutoffFrequency());
		}

		if (commonTermsQuery.getHighFreqMinimumShouldMatch() != null) {
			commonTermsQueryBuilder.highFreqMinimumShouldMatch(
				commonTermsQuery.getHighFreqMinimumShouldMatch());
		}

		if (commonTermsQuery.getHighFreqOperator() != null) {
			commonTermsQueryBuilder.highFreqOperator(
				translate(commonTermsQuery.getHighFreqOperator()));
		}

		if (commonTermsQuery.getLowFreqMinimumShouldMatch() != null) {
			commonTermsQueryBuilder.highFreqMinimumShouldMatch(
				commonTermsQuery.getLowFreqMinimumShouldMatch());
		}

		if (commonTermsQuery.getLowFreqOperator() != null) {
			commonTermsQueryBuilder.lowFreqOperator(
				translate(commonTermsQuery.getLowFreqOperator()));
		}

		return commonTermsQueryBuilder;
	}

	protected org.elasticsearch.index.query.Operator translate(
		Operator matchQueryOperator) {

		if (matchQueryOperator == Operator.AND) {
			return org.elasticsearch.index.query.Operator.AND;
		}
		else if (matchQueryOperator == Operator.OR) {
			return org.elasticsearch.index.query.Operator.AND;
		}

		throw new IllegalArgumentException(
			"Invalid operator: " + matchQueryOperator);
	}

}