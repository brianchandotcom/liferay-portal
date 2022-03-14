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

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.BaseFieldAggregationTranslator;

import java.util.List;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.AbstractRangeBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = RangeAggregationTranslator.class)
public class RangeAggregationTranslatorImpl
	implements RangeAggregationTranslator {

	@Override
	public RangeAggregationBuilder translate(
		RangeAggregation rangeAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		RangeAggregationBuilder rangeAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.range(
					baseMetricsAggregation.getName()),
				rangeAggregation, aggregationTranslator,
				pipelineAggregationTranslator);

		populateRangeAggregationBuilder(
			rangeAggregation, rangeAggregationBuilder);

		return rangeAggregationBuilder;
	}

	protected void populateRangeAggregationBuilder(
		RangeAggregation rangeAggregation,
		AbstractRangeBuilder abstractRangeBuilder) {

		if (rangeAggregation.getFormat() != null) {
			abstractRangeBuilder.format(rangeAggregation.getFormat());
		}

		if (rangeAggregation.getKeyed() != null) {
			abstractRangeBuilder.keyed(rangeAggregation.getKeyed());
		}

		List<Range> rangeAggregationRanges = rangeAggregation.getRanges();

		rangeAggregationRanges.forEach(
			rangeAggregationRange -> {
				RangeAggregator.Range range = new RangeAggregator.Range(
					rangeAggregationRange.getKey(),
					rangeAggregationRange.getFrom(),
					rangeAggregationRange.getFromAsString(),
					rangeAggregationRange.getTo(),
					rangeAggregationRange.getToAsString());

				abstractRangeBuilder.addRange(range);
			});
	}

	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();

}