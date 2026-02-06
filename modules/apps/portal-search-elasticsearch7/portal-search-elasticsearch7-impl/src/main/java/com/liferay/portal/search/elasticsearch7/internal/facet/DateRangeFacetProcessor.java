/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.AbstractRangeBuilder;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class DateRangeFacetProcessor
	extends RangeFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	public static final DateRangeFacetProcessor INSTANCE =
		new DateRangeFacetProcessor();

	@Override
	protected AbstractRangeBuilder getRangeBuilder(String name) {
		return AggregationBuilders.dateRange(name);
	}

}