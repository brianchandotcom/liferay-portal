/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.FieldAggregation;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;

/**
 * @author André de Oliveira
 */
public class AggregationBuilderAssemblerImpl {

	public AggregationBuilderAssemblerImpl(
		AggregationTranslator<AggregationBuilder> aggregationTranslator) {

		_aggregationTranslator = aggregationTranslator;
	}

	public <AB extends AggregationBuilder> AB assembleAggregation(
		AB aggregationBuilder, Aggregation aggregation) {

		_baseAggregationTranslator.translate(
			aggregationBuilder, aggregation, _aggregationTranslator);

		return aggregationBuilder;
	}

	public <VSAB extends ValuesSourceAggregationBuilder> VSAB
		assembleFieldAggregation(
			VSAB valuesSourceAggregationBuilder,
			FieldAggregation fieldAggregation) {

		_baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> valuesSourceAggregationBuilder,
			fieldAggregation, _aggregationTranslator);

		return valuesSourceAggregationBuilder;
	}

	private final AggregationTranslator<AggregationBuilder>
		_aggregationTranslator;
	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();
	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();

}