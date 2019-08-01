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

package com.liferay.portal.search.elasticsearch6.internal.aggregation;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.FieldAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;

/**
 * @author André de Oliveira
 */
public class AggregationBuilderAssemblerImpl {

	public AggregationBuilderAssemblerImpl(
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		_aggregationTranslator = aggregationTranslator;
		_pipelineAggregationTranslator = pipelineAggregationTranslator;
	}

	public <AB extends AggregationBuilder> AB assembleAggregation(
		AB aggregationBuilder, Aggregation aggregation) {

		_baseAggregationTranslator.translate(
			aggregationBuilder, aggregation, _aggregationTranslator,
			_pipelineAggregationTranslator);

		return aggregationBuilder;
	}

	public <VSAB extends ValuesSourceAggregationBuilder> VSAB
		assembleFieldAggregation(
			VSAB valuesSourceAggregationBuilder,
			FieldAggregation fieldAggregation) {

		_baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> valuesSourceAggregationBuilder,
			fieldAggregation, _aggregationTranslator,
			_pipelineAggregationTranslator);

		return valuesSourceAggregationBuilder;
	}

	private final AggregationTranslator<AggregationBuilder>
		_aggregationTranslator;
	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();
	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();
	private final PipelineAggregationTranslator<PipelineAggregationBuilder>
		_pipelineAggregationTranslator;

}