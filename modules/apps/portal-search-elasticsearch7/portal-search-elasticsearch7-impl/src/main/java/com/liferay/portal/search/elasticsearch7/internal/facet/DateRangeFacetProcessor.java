/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.StringUtil;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;

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
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public AggregationBuilder processFacet(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject jsonObject = facetConfiguration.getData();

		JSONArray jsonArray = jsonObject.getJSONArray("ranges");

		if (jsonArray == null) {
			return null;
		}

		DateRangeAggregationBuilder dateRangeAggregationBuilder =
			AggregationBuilders.dateRange(FacetUtil.getAggregationName(facet));

		dateRangeAggregationBuilder.field(facetConfiguration.getFieldName());

		String format = jsonObject.getString("format");

		dateRangeAggregationBuilder.format(format);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rangeJSONObject = jsonArray.getJSONObject(i);

			String range = rangeJSONObject.getString("range");

			String formattedRange = StringUtil.replace(
				range, CharPool.OPEN_BRACKET, StringPool.BLANK);

			formattedRange = StringUtil.replace(
				formattedRange, CharPool.CLOSE_BRACKET, StringPool.BLANK);

			String[] formattedRangeParts = formattedRange.split(
				StringPool.SPACE);

			dateRangeAggregationBuilder.addRange(
				range, formattedRangeParts[0], formattedRangeParts[2]);
		}

		return dateRangeAggregationBuilder;
	}

}