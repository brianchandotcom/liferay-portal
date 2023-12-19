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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.opensearch2.internal.util.SetterUtil;

import org.opensearch.client.opensearch._types.aggregations.Aggregation;
import org.opensearch.client.opensearch._types.aggregations.AggregationBuilders;
import org.opensearch.client.opensearch._types.aggregations.DateRangeAggregation;
import org.opensearch.client.opensearch._types.aggregations.DateRangeExpression;
import org.opensearch.client.opensearch._types.aggregations.FieldDateMath;
import org.opensearch.client.opensearch.core.SearchRequest;

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
	implements FacetProcessor<SearchRequest.Builder> {

	@Override
	public Aggregation.Builder.ContainerBuilder processFacet(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject jsonObject = facetConfiguration.getData();

		JSONArray jsonArray = jsonObject.getJSONArray("ranges");

		if (jsonArray == null) {
			return null;
		}

		DateRangeAggregation.Builder dateRangeAggregationBuilder =
			AggregationBuilders.dateRange();

		dateRangeAggregationBuilder.field(facetConfiguration.getFieldName());

		SetterUtil.setNotBlankString(
			dateRangeAggregationBuilder::format,
			jsonObject.getString("format"));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject rangeJSONObject = jsonArray.getJSONObject(i);

			String label = rangeJSONObject.getString("label");

			if (Validator.isBlank(label)) {
				label = rangeJSONObject.getString("range");
			}

			dateRangeAggregationBuilder.ranges(
				_createAggregationRange(
					label,
					RangeParserUtil.parserRange(
						rangeJSONObject.getString("range"))));
		}

		Aggregation.Builder aggregationBuilder = new Aggregation.Builder();

		return aggregationBuilder.dateRange(
			dateRangeAggregationBuilder.build());
	}

	private DateRangeExpression _createAggregationRange(
		String key, String[] rangeParts) {

		return DateRangeExpression.of(
			dateRangeExpression -> dateRangeExpression.key(
				key
			).from(
				FieldDateMath.of(
					fieldDateMath -> fieldDateMath.expr(rangeParts[0]))
			).to(
				FieldDateMath.of(
					fieldDateMath -> fieldDateMath.expr(rangeParts[1]))
			));
	}

}