/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.AbstractRangeBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(
	property = {
		"class.name=com.liferay.portal.kernel.search.facet.DateRangeFacet",
		"class.name=com.liferay.portal.search.internal.facet.DateRangeFacetImpl"
	},
	service = FacetProcessor.class
)
public class DateRangeFacetProcessor
	extends RangeFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	protected AbstractRangeBuilder getRangeBuilder(String name) {
		return AggregationBuilders.dateRange(name);
	}

}