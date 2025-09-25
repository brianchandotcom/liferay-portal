/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.AbstractRangeBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 * @author Tibor Lipusz
 */
@Component(
	property = {
		"class.name=com.liferay.portal.kernel.search.facet.RangeFacet",
		"class.name=com.liferay.portal.search.internal.facet.ModifiedFacetImpl",
		"class.name=com.liferay.portal.search.internal.facet.RangeFacetImpl"
	},
	service = FacetProcessor.class
)
public class RangeFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public AggregationBuilder processFacet(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		AbstractRangeBuilder abstractRangeBuilder = getRangeBuilder(
			FacetUtil.getAggregationName(facet));

		abstractRangeBuilder.field(facetConfiguration.getFieldName());

		JSONObject jsonObject = facetConfiguration.getData();

		String format = jsonObject.getString("format");

		if (Validator.isNotNull(format)) {
			abstractRangeBuilder.format(format);
		}

		_addRanges(facetConfiguration, abstractRangeBuilder);

		if (ListUtil.isEmpty(abstractRangeBuilder.ranges())) {
			return null;
		}

		return abstractRangeBuilder;
	}

	protected AbstractRangeBuilder getRangeBuilder(String name) {
		return AggregationBuilders.range(name);
	}

	private void _addRange(
		AbstractRangeBuilder abstractRangeBuilder, String range) {

		String[] rangeParts = RangeParserUtil.parserRange(range);

		abstractRangeBuilder.addRange(
			new RangeAggregator.Range(range, rangeParts[0], rangeParts[1]));
	}

	private void _addRanges(
		FacetConfiguration facetConfiguration,
		AbstractRangeBuilder abstractRangeBuilder) {

		JSONObject jsonObject = facetConfiguration.getData();

		JSONArray jsonArray = jsonObject.getJSONArray("ranges");

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rangeJSONObject = jsonArray.getJSONObject(i);

			_addRange(abstractRangeBuilder, rangeJSONObject.getString("range"));
		}
	}

}