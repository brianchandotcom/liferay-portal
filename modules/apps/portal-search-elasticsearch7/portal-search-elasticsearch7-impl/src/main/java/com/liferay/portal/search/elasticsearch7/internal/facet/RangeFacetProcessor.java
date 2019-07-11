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

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 * @author Tibor Lipusz
 */
@Component(
	immediate = true,
	property = "class.name=com.liferay.portal.kernel.search.facet.RangeFacet",
	service = FacetProcessor.class
)
public class RangeFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public Optional<AggregationBuilder> processFacet(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		RangeAggregationBuilder rangeAggregationBuilder =
			AggregationBuilders.range(FacetUtil.getAggregationName(facet));

		rangeAggregationBuilder.field(facetConfiguration.getFieldName());

		addConfigurationRanges(facetConfiguration, rangeAggregationBuilder);

		addCustomRange(facet, rangeAggregationBuilder);

		if (ListUtil.isEmpty(rangeAggregationBuilder.ranges())) {
			return Optional.empty();
		}

		return Optional.of(rangeAggregationBuilder);
	}

	protected void addConfigurationRanges(
		FacetConfiguration facetConfiguration,
		RangeAggregationBuilder rangeAggregationBuilder) {

		JSONObject jsonObject = facetConfiguration.getData();

		JSONArray jsonArray = jsonObject.getJSONArray("ranges");

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rangeJSONObject = jsonArray.getJSONObject(i);

			String rangeString = rangeJSONObject.getString("range");

			String[] range = RangeParserUtil.parserRange(rangeString);

			rangeAggregationBuilder.addRange(createRange(rangeString, range));
		}
	}

	protected void addCustomRange(
		Facet facet, RangeAggregationBuilder rangeAggregationBuilder) {

		SearchContext searchContext = facet.getSearchContext();

		String rangeString = GetterUtil.getString(
			searchContext.getAttribute(facet.getFieldId()));

		if (Validator.isNull(rangeString)) {
			return;
		}

		String[] range = RangeParserUtil.parserRange(rangeString);

		rangeAggregationBuilder.addRange(createRange(rangeString, range));
	}

	protected RangeAggregator.Range createRange(String key, String[] range) {
		return new RangeAggregator.Range(key, range[0], range[1]);
	}

}