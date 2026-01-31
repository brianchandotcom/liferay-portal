/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.opensearch2.internal.geolocation.GeoTranslator;
import com.liferay.portal.search.opensearch2.internal.highlight.HighlightTranslator;
import com.liferay.portal.search.opensearch2.internal.query.OpenSearchQueryTranslatorFixture;
import com.liferay.portal.search.opensearch2.internal.script.ScriptTranslator;

import org.opensearch.client.opensearch._types.aggregations.Aggregation;

/**
 * @author Michael C. Han
 */
public class OpenSearchAggregationTranslatorFixture {

	public OpenSearchAggregationTranslatorFixture() {
		OpenSearchQueryTranslatorFixture openSearchQueryTranslatorFixture =
			new OpenSearchQueryTranslatorFixture();

		PipelineAggregationTranslator<Aggregation>
			pipelineAggregationTranslator =
				new OpenSearchPipelineAggregationTranslator();

		OpenSearchAggregationTranslator openSearchAggregationTranslator =
			new OpenSearchAggregationTranslator();

		ReflectionTestUtil.setFieldValue(
			openSearchAggregationTranslator, "_geoTranslator",
			new GeoTranslator());
		ReflectionTestUtil.setFieldValue(
			openSearchAggregationTranslator, "_highlightTranslator",
			new HighlightTranslator());
		ReflectionTestUtil.setFieldValue(
			openSearchAggregationTranslator, "_pipelineAggregationTranslator",
			pipelineAggregationTranslator);
		ReflectionTestUtil.setFieldValue(
			openSearchAggregationTranslator, "_queryTranslator",
			openSearchQueryTranslatorFixture.getOpenSearchQueryTranslator());
		ReflectionTestUtil.setFieldValue(
			openSearchAggregationTranslator, "scriptTranslator",
			new ScriptTranslator());

		_openSearchAggregationTranslator = openSearchAggregationTranslator;
	}

	public OpenSearchAggregationTranslator
		getOpenSearchAggregationTranslator() {

		return _openSearchAggregationTranslator;
	}

	private final OpenSearchAggregationTranslator
		_openSearchAggregationTranslator;

}