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

package com.liferay.portal.search.elasticsearch.internal.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.facet.FacetProcessor;

import org.elasticsearch.action.search.SearchRequestBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 * @author Tibor Lipusz
 */
@Component(
	immediate = true,
	property = {"class.name=com.liferay.portal.kernel.search.facet.RangeFacet"}
)
public class RangeFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public void processFacet(
		SearchRequestBuilder searchRequestBuilder, Facet facet) {

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		DefaultRangeBuilder defaultRangeBuilder = new DefaultRangeBuilder(
			facetConfiguration.getFieldName());

		defaultRangeBuilder.field(facetConfiguration.getFieldName());

		addConfigurationRanges(facetConfiguration, defaultRangeBuilder);

		addCustomRange(facet, defaultRangeBuilder);

		if (defaultRangeBuilder.hasRanges()) {
			searchRequestBuilder.addAggregation(defaultRangeBuilder);
		}
	}

	protected void addConfigurationRanges(
		FacetConfiguration facetConfiguration,
		DefaultRangeBuilder defaultRangeBuilder) {

		JSONObject jsonObject = facetConfiguration.getData();

		JSONArray jsonArray = jsonObject.getJSONArray("ranges");

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rangeJSONObject = jsonArray.getJSONObject(i);

			String rangeString = rangeJSONObject.getString("range");

			String[] range = RangeParserUtil.parserRange(rangeString);

			defaultRangeBuilder.addRange(range[0], range[1]);
		}
	}

	protected void addCustomRange(
		Facet facet, DefaultRangeBuilder defaultRangeBuilder) {

		SearchContext searchContext = facet.getSearchContext();

		String rangeParam = GetterUtil.getString(
			searchContext.getAttribute(facet.getFieldId()));

		if (Validator.isNull(rangeParam)) {
			return;
		}

		String[] range = RangeParserUtil.parserRange(rangeParam);

		defaultRangeBuilder.addRange(range[0], range[1]);
	}

}