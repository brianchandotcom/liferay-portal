/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.opensearch2.internal.query.OpenSearchQueryTranslatorFixture;
import com.liferay.portal.search.opensearch2.internal.sort.OpenSearchSortFieldTranslatorFixture;

/**
 * @author Michael C. Han
 */
public class OpenSearchPipelineAggregationTranslatorFixture {

	public OpenSearchPipelineAggregationTranslatorFixture() {
		OpenSearchPipelineAggregationTranslator
			openSearchPipelineAggregationTranslator =
				new OpenSearchPipelineAggregationTranslator();

		_injectSortFieldTranslators(openSearchPipelineAggregationTranslator);

		_openSearchPipelineAggregationTranslator =
			openSearchPipelineAggregationTranslator;
	}

	public OpenSearchPipelineAggregationTranslator
		getOpenSearchPipelineAggregationTranslator() {

		return _openSearchPipelineAggregationTranslator;
	}

	private void _injectSortFieldTranslators(
		OpenSearchPipelineAggregationTranslator
			openSearchPipelineAggregationTranslator) {

		OpenSearchQueryTranslatorFixture openSearchQueryTranslatorFixture =
			new OpenSearchQueryTranslatorFixture();

		OpenSearchSortFieldTranslatorFixture
			openSearchSortFieldTranslatorFixture =
				new OpenSearchSortFieldTranslatorFixture(
					openSearchQueryTranslatorFixture.
						getOpenSearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			openSearchPipelineAggregationTranslator, "_sortFieldTranslator",
			openSearchSortFieldTranslatorFixture.
				getOpenSearchSortFieldTranslator());
	}

	private final OpenSearchPipelineAggregationTranslator
		_openSearchPipelineAggregationTranslator;

}