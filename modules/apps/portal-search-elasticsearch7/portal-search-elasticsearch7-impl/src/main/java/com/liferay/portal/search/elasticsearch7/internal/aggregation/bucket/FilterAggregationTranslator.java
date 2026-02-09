/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.BaseAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryVisitor;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class FilterAggregationTranslator {

	public FilterAggregationBuilder translate(
		FilterAggregation filterAggregation) {

		QueryBuilder filterQueryBuilder =
			ElasticsearchQueryVisitor.INSTANCE.translate(
				filterAggregation.getFilterQuery());

		FilterAggregationBuilder filterAggregationBuilder =
			AggregationBuilders.filter(
				filterAggregation.getName(), filterQueryBuilder);

		_baseAggregationTranslator.translate(
			filterAggregationBuilder, filterAggregation);

		return filterAggregationBuilder;
	}

	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();

}