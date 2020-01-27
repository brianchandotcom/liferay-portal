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

package com.liferay.portal.search.elasticsearch6.internal.facet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Optional;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "class.name=com.liferay.portal.kernel.search.facet.DateRangeFacet",
	service = FacetProcessor.class
)
public class DateRangeFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public Optional<AggregationBuilder> processFacet(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject jsonObject = facetConfiguration.getData();

		JSONArray jsonArray = jsonObject.getJSONArray("ranges");

		if (jsonArray == null) {
			return Optional.empty();
		}

		DateRangeAggregationBuilder dateRangeAggregationBuilder =
			AggregationBuilders.dateRange(FacetUtil.getAggregationName(facet));

		dateRangeAggregationBuilder.field(facetConfiguration.getFieldName());

		String format = jsonObject.getString("format");

		dateRangeAggregationBuilder.format(format);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rangeJSONObject = jsonArray.getJSONObject(i);

			String range = rangeJSONObject.getString("range");

			range = StringUtil.removeSubstring(range, CharPool.OPEN_BRACKET);
			range = StringUtil.removeSubstring(range, CharPool.CLOSE_BRACKET);

			String[] rangeParts = range.split(StringPool.SPACE);

			dateRangeAggregationBuilder.addRange(rangeParts[0], rangeParts[2]);
		}

		return Optional.of(dateRangeAggregationBuilder);
	}

}