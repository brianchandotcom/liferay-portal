/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateHistogramAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateRangeAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FilterAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FiltersAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.GeoDistanceAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.HistogramAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.RangeAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTextAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.TermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.ScriptedMetricAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.TopHitsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.WeightedAvgAggregationTranslator;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationTranslatorFixture {

	public ElasticsearchAggregationTranslatorFixture() {
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator =
			new ElasticsearchAggregationTranslator();

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_dateHistogramAggregationTranslator",
			new DateHistogramAggregationTranslator());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_dateRangeAggregationTranslator",
			new DateRangeAggregationTranslator());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_histogramAggregationTranslator",
			new HistogramAggregationTranslator());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_rangeAggregationTranslator",
			new RangeAggregationTranslator());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_termsAggregationTranslator",
			new TermsAggregationTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_geoDistanceAggregationTranslator",
			new GeoDistanceAggregationTranslator());

		_injectQueryAggregationTranslators(elasticsearchAggregationTranslator);
		_injectScriptAggregationTranslators(elasticsearchAggregationTranslator);

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_topHitsAggregationTranslator",
			new TopHitsAggregationTranslator());

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
			new FilterAggregationTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator, "_filtersAggregationTranslator",
			new FiltersAggregationTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_significantTermsAggregationTranslator",
			new SignificantTermsAggregationTranslator());

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_significantTextAggregationTranslator",
			new SignificantTextAggregationTranslator());
	}

	private void _injectScriptAggregationTranslators(
		ElasticsearchAggregationTranslator elasticsearchAggregationTranslator) {

		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_scriptedMetricAggregationTranslator",
			new ScriptedMetricAggregationTranslator());
		ReflectionTestUtil.setFieldValue(
			elasticsearchAggregationTranslator,
			"_weightedAvgAggregationTranslator",
			new WeightedAvgAggregationTranslator());
	}

	private final ElasticsearchAggregationTranslator
		_elasticsearchAggregationTranslator;

}