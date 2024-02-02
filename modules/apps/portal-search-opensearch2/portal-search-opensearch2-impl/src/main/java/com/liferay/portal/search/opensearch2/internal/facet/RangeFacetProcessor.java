/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ListUtil;

import org.opensearch.client.opensearch._types.aggregations.Aggregation;
import org.opensearch.client.opensearch._types.aggregations.AggregationBuilders;
import org.opensearch.client.opensearch._types.aggregations.AggregationRange;
import org.opensearch.client.opensearch._types.aggregations.RangeAggregation;
import org.opensearch.client.opensearch.core.SearchRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 * @author Tibor Lipusz
 * @author Petteri Karttunen
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
	implements FacetProcessor<SearchRequest.Builder> {

	@Override
	public Aggregation.Builder.ContainerBuilder processFacet(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		RangeAggregation.Builder rangeAggregationBuilder =
			AggregationBuilders.range();

		rangeAggregationBuilder.field(facetConfiguration.getFieldName());

		_addConfigurationRanges(rangeAggregationBuilder, facetConfiguration);

		RangeAggregation rangeAggregation = rangeAggregationBuilder.build();

		if (ListUtil.isEmpty(rangeAggregation.ranges())) {
			return null;
		}

		Aggregation.Builder aggregationBuilder = new Aggregation.Builder();

		return aggregationBuilder.range(rangeAggregation);
	}

	private void _addConfigurationRanges(
		RangeAggregation.Builder builder,
		FacetConfiguration facetConfiguration) {

		JSONObject jsonObject = facetConfiguration.getData();

		JSONArray jsonArray = jsonObject.getJSONArray("ranges");

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rangeJSONObject = jsonArray.getJSONObject(i);

			builder.ranges(
				_createAggregationRange(rangeJSONObject.getString("range")));
		}
	}

	private AggregationRange _createAggregationRange(String range) {
		String[] rangeParts = RangeParserUtil.parserRange(range);

		return AggregationRange.of(
			aggregationRange -> aggregationRange.key(
				range
			).from(
				rangeParts[0]
			).to(
				rangeParts[1]
			));
	}

}