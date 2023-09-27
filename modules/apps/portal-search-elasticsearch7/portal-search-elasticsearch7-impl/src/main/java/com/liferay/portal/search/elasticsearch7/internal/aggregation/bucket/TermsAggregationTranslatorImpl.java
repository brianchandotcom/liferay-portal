/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.aggregation.bucket.CollectionMode;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;

import java.util.List;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregator;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = TermsAggregationTranslator.class)
public class TermsAggregationTranslatorImpl
	implements TermsAggregationTranslator {

	@Override
	public TermsAggregationBuilder translate(
		TermsAggregation termsAggregation) {

		TermsAggregationBuilder termsAggregationBuilder =
			AggregationBuilders.terms(termsAggregation.getName());

		if (termsAggregation.getCollectionMode() != null) {
			CollectionMode collectionMode =
				termsAggregation.getCollectionMode();

			if (collectionMode == CollectionMode.BREADTH_FIRST) {
				termsAggregationBuilder.collectMode(
					Aggregator.SubAggCollectionMode.BREADTH_FIRST);
			}
			else if (collectionMode == CollectionMode.DEPTH_FIRST) {
				termsAggregationBuilder.collectMode(
					Aggregator.SubAggCollectionMode.DEPTH_FIRST);
			}
		}

		if (termsAggregation.getExecutionHint() != null) {
			termsAggregationBuilder.executionHint(
				termsAggregation.getExecutionHint());
		}

		if (termsAggregation.getIncludeExcludeClause() != null) {
			termsAggregationBuilder.includeExclude(
				_includeExcludeTranslator.translate(
					termsAggregation.getIncludeExcludeClause()));
		}

		if (termsAggregation.getMinDocCount() != null) {
			termsAggregationBuilder.minDocCount(
				termsAggregation.getMinDocCount());
		}

		if (ListUtil.isNotEmpty(termsAggregation.getOrders())) {
			List<BucketOrder> bucketOrders = _orderTranslator.translate(
				termsAggregation.getOrders());

			termsAggregationBuilder.order(bucketOrders);
		}

		if (termsAggregation.getShardMinDocCount() != null) {
			termsAggregationBuilder.shardMinDocCount(
				termsAggregation.getShardMinDocCount());
		}

		if (termsAggregation.getShardSize() != null) {
			termsAggregationBuilder.shardSize(termsAggregation.getShardSize());
		}

		if (termsAggregation.getShowTermDocCountError() != null) {
			termsAggregationBuilder.showTermDocCountError(
				termsAggregation.getShowTermDocCountError());
		}

		if (termsAggregation.getSize() != null) {
			termsAggregationBuilder.size(termsAggregation.getSize());
		}

		return termsAggregationBuilder;
	}

	private final IncludeExcludeTranslator _includeExcludeTranslator =
		new IncludeExcludeTranslator();
	private final OrderTranslator _orderTranslator = new OrderTranslator();

}