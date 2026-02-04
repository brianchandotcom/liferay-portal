/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;

/**
 * @author Michael C. Han
 */
public class OpenSearchAggregationTranslatorFixture {

	public OpenSearchAggregationTranslatorFixture() {
		OpenSearchAggregationTranslator openSearchAggregationTranslator =
			new OpenSearchAggregationTranslator();

		ReflectionTestUtil.setFieldValue(
			openSearchAggregationTranslator, "_pipelineAggregationTranslator",
			new OpenSearchPipelineAggregationTranslator());

		_openSearchAggregationTranslator = openSearchAggregationTranslator;
	}

	public OpenSearchAggregationTranslator
		getOpenSearchAggregationTranslator() {

		return _openSearchAggregationTranslator;
	}

	private final OpenSearchAggregationTranslator
		_openSearchAggregationTranslator;

}