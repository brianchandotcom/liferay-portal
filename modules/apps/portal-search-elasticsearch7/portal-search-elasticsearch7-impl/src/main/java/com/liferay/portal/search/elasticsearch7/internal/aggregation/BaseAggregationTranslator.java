/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline.ElasticsearchPipelineAggregationVisitor;

import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * @author Michael C. Han
 */
public class BaseAggregationTranslator {

	public AggregationBuilder translate(
		AggregationBuilder aggregationBuilder, Aggregation aggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator) {

		for (Aggregation childAggregation :
				aggregation.getChildrenAggregations()) {

			aggregationBuilder.subAggregation(
				aggregationTranslator.translate(childAggregation));
		}

		for (PipelineAggregation pipelineAggregation :
				aggregation.getPipelineAggregations()) {

			aggregationBuilder.subAggregation(
				pipelineAggregation.accept(
					ElasticsearchPipelineAggregationVisitor.INSTANCE));
		}

		return aggregationBuilder;
	}

}