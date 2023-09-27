/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

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