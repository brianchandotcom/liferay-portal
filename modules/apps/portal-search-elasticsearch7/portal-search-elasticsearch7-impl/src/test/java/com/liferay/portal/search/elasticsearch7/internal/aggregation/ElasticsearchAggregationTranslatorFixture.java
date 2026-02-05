/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateHistogramAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateRangeAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FilterAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FiltersAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.GeoDistanceAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.HistogramAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.RangeAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTermsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTextAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.TermsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.ScriptedMetricAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.TopHitsAggregationTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.WeightedAvgAggregationTranslatorImpl;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationTranslatorFixture {

	public ElasticsearchAggregationTranslatorFixture() {
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator =
			new ElasticsearchAggregationTranslator();

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_aggregationBuilderAssemblerFactory",
			new AggregationBuilderAssemblerFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_dateHistogramAggregationTranslator",
			new DateHistogramAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_dateRangeAggregationTranslator",
			new DateRangeAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_histogramAggregationTranslator",
			new HistogramAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_rangeAggregationTranslator",
			new RangeAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_termsAggregationTranslator",
			new TermsAggregationTranslatorImpl());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_geoDistanceAggregationTranslator",
			new GeoDistanceAggregationTranslatorImpl());

		_injectQueryAggregationTranslators(elasticsearchAggregationTranslator);
		_injectScriptAggregationTranslators(elasticsearchAggregationTranslator);

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_topHitsAggregationTranslator",
			new TopHitsAggregationTranslatorImpl());

		_elasticsearchAggregationTranslator =
			elasticsearchAggregationTranslator;
	}

	public ElasticsearchAggregationTranslator
		getElasticsearchAggregationTranslator() {

		return _elasticsearchAggregationTranslator;
	}

	private void _injectQueryAggregationTranslators(
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator) {

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_filterAggregationTranslator",
			new FilterAggregationTranslatorImpl());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_filtersAggregationTranslator",
			new FiltersAggregationTranslatorImpl());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_significantTermsAggregationTranslator",
			new SignificantTermsAggregationTranslatorImpl());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_significantTextAggregationTranslator",
			new SignificantTextAggregationTranslatorImpl());
	}

	private void _injectScriptAggregationTranslators(
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator) {

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_scriptedMetricAggregationTranslator",
			new ScriptedMetricAggregationTranslatorImpl());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_weightedAvgAggregationTranslator",
			new WeightedAvgAggregationTranslatorImpl());
	}

	private final ElasticsearchAggregationTranslator
		_elasticsearchAggregationTranslator;

}