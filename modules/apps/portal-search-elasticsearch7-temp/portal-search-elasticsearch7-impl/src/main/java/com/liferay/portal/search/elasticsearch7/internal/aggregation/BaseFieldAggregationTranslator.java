/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.FieldAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.script.ScriptTranslator;

import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class BaseFieldAggregationTranslator {

	public <T extends ValuesSourceAggregationBuilder> T translate(
		ValuesSourceAggregationBuilderFactory<T>
			valuesSourceAggregationBuilderFactory,
		FieldAggregation baseFieldAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		T valuesSourceAggregationBuilder =
			valuesSourceAggregationBuilderFactory.create(baseFieldAggregation);

		_setField(valuesSourceAggregationBuilder, baseFieldAggregation);
		_setMissing(valuesSourceAggregationBuilder, baseFieldAggregation);
		_setScript(valuesSourceAggregationBuilder, baseFieldAggregation);

		_baseAggregationTranslator.translate(
			valuesSourceAggregationBuilder, baseFieldAggregation,
			aggregationTranslator, pipelineAggregationTranslator);

		return valuesSourceAggregationBuilder;
	}

	public interface ValuesSourceAggregationBuilderFactory
		<T extends ValuesSourceAggregationBuilder> {

		public T create(FieldAggregation baseFieldAggregation);

	}

	private <T extends ValuesSourceAggregationBuilder> void _setField(
		T valuesSourceAggregationBuilder,
		FieldAggregation baseFieldAggregation) {

		if (baseFieldAggregation.getField() != null) {
			valuesSourceAggregationBuilder.field(
				baseFieldAggregation.getField());
		}
	}

	private <T extends ValuesSourceAggregationBuilder> void _setMissing(
		T valuesSourceAggregationBuilder,
		FieldAggregation baseFieldAggregation) {

		if (baseFieldAggregation.getMissing() != null) {
			valuesSourceAggregationBuilder.missing(
				baseFieldAggregation.getMissing());
		}
	}

	private <T extends ValuesSourceAggregationBuilder> void _setScript(
		T valuesSourceAggregationBuilder,
		FieldAggregation baseFieldAggregation) {

		if (baseFieldAggregation.getScript() != null) {
			Script elasticsearchScript = _scriptTranslator.translate(
				baseFieldAggregation.getScript());

			valuesSourceAggregationBuilder.script(elasticsearchScript);
		}
	}

	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();
	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}