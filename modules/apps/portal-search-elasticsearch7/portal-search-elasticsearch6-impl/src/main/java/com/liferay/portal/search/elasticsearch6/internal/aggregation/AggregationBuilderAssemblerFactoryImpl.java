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

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = AggregationBuilderAssemblerFactory.class)
public class AggregationBuilderAssemblerFactoryImpl
	implements AggregationBuilderAssemblerFactory {

	@Override
	public AggregationBuilderAssemblerImpl getAggregationBuilderAssembler(
		AggregationTranslator<AggregationBuilder> aggregationTranslator) {

		return new AggregationBuilderAssemblerImpl(
			aggregationTranslator, _pipelineAggregationTranslator);
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setPipelineAggregationTranslator(
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		_pipelineAggregationTranslator = pipelineAggregationTranslator;
	}

	private PipelineAggregationTranslator<PipelineAggregationBuilder>
		_pipelineAggregationTranslator;

}