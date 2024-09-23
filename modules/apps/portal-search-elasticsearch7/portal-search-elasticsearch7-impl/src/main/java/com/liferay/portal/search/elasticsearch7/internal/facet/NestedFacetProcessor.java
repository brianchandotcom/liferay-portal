/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.facet.nested.NestedFacet;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Díaz
 * @author Petteri Karttunen
 */
@Component(
	property = "class.name=com.liferay.portal.search.internal.facet.NestedFacetImpl",
	service = FacetProcessor.class
)
public class NestedFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public AggregationBuilder processFacet(Facet facet) {
		if (!(facet instanceof NestedFacet)) {
			return null;
		}

		NestedFacet nestedFacet = (NestedFacet)facet;

		NestedAggregationBuilder nestedAggregationBuilder =
			AggregationBuilders.nested(
				FacetUtil.getAggregationName(facet), nestedFacet.getPath());

		TermsAggregationBuilder termsAggregationBuilder =
			_getTermsAggregationBuilder(nestedFacet);

		if ((nestedFacet.getChildAggregation() != null) ||
			Validator.isNotNull(nestedFacet.getFilterField())) {

			TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(
				nestedFacet.getFilterField(), nestedFacet.getFilterValue());

			FilterAggregationBuilder filterAggregationBuilder =
				AggregationBuilders.filter(
					FacetUtil.getAggregationName(facet), termQueryBuilder);

			if (nestedFacet.getChildAggregation() != null) {
				filterAggregationBuilder.subAggregation(
					_getChildAggregationBuilder(
						nestedFacet.getChildAggregation()));
			}
			else {
				filterAggregationBuilder.subAggregation(
					termsAggregationBuilder);
			}

			nestedAggregationBuilder.subAggregation(filterAggregationBuilder);
		}
		else {
			nestedAggregationBuilder.subAggregation(termsAggregationBuilder);
		}

		return nestedAggregationBuilder;
	}

	private AggregationBuilder _getChildAggregationBuilder(
		Aggregation aggregation) {

		if (aggregation instanceof DateRangeAggregation) {
			return _getDateRangeAggregationBuilder(
				(DateRangeAggregation)aggregation);
		}
		else if (aggregation instanceof RangeAggregation) {
			return _getRangeAggregationBuilder((RangeAggregation)aggregation);
		}

		Class<?> clazz = aggregation.getClass();

		throw new UnsupportedOperationException(
			"Nested facet does not support child aggregation " +
				clazz.getName());
	}

	private AggregationBuilder _getDateRangeAggregationBuilder(
		DateRangeAggregation dateRangeAggregation) {

		DateRangeAggregationBuilder dateRangeAggregationBuilder =
			AggregationBuilders.dateRange(dateRangeAggregation.getName());

		dateRangeAggregationBuilder.field(dateRangeAggregation.getField());
		dateRangeAggregationBuilder.format(dateRangeAggregation.getFormat());

		for (Range range : dateRangeAggregation.getRanges()) {
			dateRangeAggregationBuilder.addRange(
				range.getKey(), range.getFromAsString(), range.getToAsString());
		}

		return dateRangeAggregationBuilder;
	}

	private AggregationBuilder _getRangeAggregationBuilder(
		RangeAggregation rangeAggregation) {

		RangeAggregationBuilder rangeAggregationBuilder =
			AggregationBuilders.range(rangeAggregation.getName());

		rangeAggregationBuilder.field(rangeAggregation.getField());

		if (!Validator.isBlank(rangeAggregation.getFormat())) {
			rangeAggregationBuilder.format(rangeAggregation.getFormat());
		}

		for (Range range : rangeAggregation.getRanges()) {
			rangeAggregationBuilder.addRange(
				range.getKey(), GetterUtil.getDouble(range.getFromAsString()),
				GetterUtil.getDouble(range.getToAsString()));
		}

		return rangeAggregationBuilder;
	}

	private TermsAggregationBuilder _getTermsAggregationBuilder(
		NestedFacet nestedFacet) {

		TermsAggregationBuilder termsAggregationBuilder =
			AggregationBuilders.terms(
				FacetUtil.getAggregationName(nestedFacet));

		termsAggregationBuilder.field(nestedFacet.getFieldName());

		FacetConfiguration facetConfiguration =
			nestedFacet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		int minDocCount = dataJSONObject.getInt("frequencyThreshold", -1);

		if (minDocCount >= 0) {
			termsAggregationBuilder.minDocCount(minDocCount);
		}

		int size = dataJSONObject.getInt("maxTerms");

		if (size > 0) {
			termsAggregationBuilder.size(size);
		}

		return termsAggregationBuilder;
	}

}