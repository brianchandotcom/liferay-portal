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

package com.liferay.portal.search.elasticsearch6.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.BaseAggregationTranslator;
import com.liferay.portal.search.elasticsearch6.internal.script.ScriptTranslator;

import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.scripted.ScriptedMetricAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = ScriptedMetricAggregationTranslator.class)
public class ScriptedMetricAggregationTranslatorImpl
	implements ScriptedMetricAggregationTranslator {

	@Override
	public ScriptedMetricAggregationBuilder translate(
		ScriptedMetricAggregation scriptedMetricAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		ScriptedMetricAggregationBuilder scriptedMetricAggregationBuilder =
			AggregationBuilders.scriptedMetric(
				scriptedMetricAggregation.getName());

		if (scriptedMetricAggregation.getCombineScript() != null) {
			Script elasticsearchCombineScript = _scriptTranslator.translate(
				scriptedMetricAggregation.getCombineScript());

			scriptedMetricAggregationBuilder.combineScript(
				elasticsearchCombineScript);
		}

		if (scriptedMetricAggregation.getInitScript() != null) {
			Script elasticsearchInitScript = _scriptTranslator.translate(
				scriptedMetricAggregation.getInitScript());

			scriptedMetricAggregationBuilder.initScript(
				elasticsearchInitScript);
		}

		if (scriptedMetricAggregation.getMapScript() != null) {
			Script elasticsearchMapScript = _scriptTranslator.translate(
				scriptedMetricAggregation.getMapScript());

			scriptedMetricAggregationBuilder.mapScript(elasticsearchMapScript);
		}

		if (scriptedMetricAggregation.getReduceScript() != null) {
			Script elasticsearchReduceScript = _scriptTranslator.translate(
				scriptedMetricAggregation.getReduceScript());

			scriptedMetricAggregationBuilder.reduceScript(
				elasticsearchReduceScript);
		}

		scriptedMetricAggregationBuilder.params(
			scriptedMetricAggregation.getParameters());

		_baseAggregationTranslator.translate(
			scriptedMetricAggregationBuilder, scriptedMetricAggregation,
			aggregationTranslator, pipelineAggregationTranslator);

		return scriptedMetricAggregationBuilder;
	}

	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();
	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}