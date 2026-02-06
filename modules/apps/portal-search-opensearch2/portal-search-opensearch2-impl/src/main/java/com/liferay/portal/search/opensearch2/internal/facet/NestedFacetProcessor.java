/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.facet.nested.NestedFacet;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;
import com.liferay.portal.search.opensearch2.internal.util.OpenSearchStringUtil;

import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.aggregations.Aggregation.Builder.ContainerBuilder;
import org.opensearch.client.opensearch._types.aggregations.AggregationBuilders;
import org.opensearch.client.opensearch._types.aggregations.AggregationRange;
import org.opensearch.client.opensearch._types.aggregations.DateRangeExpression;
import org.opensearch.client.opensearch._types.aggregations.TermsAggregation;
import org.opensearch.client.opensearch.core.SearchRequest;

/**
 * @author Jorge Díaz
 * @author Petteri Karttunen
 */
public class NestedFacetProcessor
	implements FacetProcessor<SearchRequest.Builder> {

	public static final NestedFacetProcessor INSTANCE =
		new NestedFacetProcessor();

	@Override
	public ContainerBuilder processFacet(Facet facet) {
		if (!(facet instanceof NestedFacet)) {
			return null;
		}

		NestedFacet nestedFacet = (NestedFacet)facet;

		org.opensearch.client.opensearch._types.aggregations.Aggregation.Builder
			nestedAggregationBuilder =
				new org.opensearch.client.opensearch._types.aggregations.
					Aggregation.Builder();

		ContainerBuilder nestedAggregationContainerBuilder =
			nestedAggregationBuilder.nested(
				nestedAggregation -> nestedAggregation.path(
					nestedFacet.getPath()));

		org.opensearch.client.opensearch._types.aggregations.Aggregation
			termsAggregation =
				new org.opensearch.client.opensearch._types.aggregations.
					Aggregation(_getTermsAggregation(nestedFacet));

		if ((nestedFacet.getChildAggregation() != null) ||
			Validator.isNotNull(nestedFacet.getFilterField())) {

			org.opensearch.client.opensearch._types.aggregations.Aggregation.
				Builder filterAggregationBuilder =
					new org.opensearch.client.opensearch._types.aggregations.
						Aggregation.Builder();

			ContainerBuilder filterAggregationContainerBuilder =
				filterAggregationBuilder.filter(
					query -> query.term(
						termQuery -> termQuery.field(
							nestedFacet.getFilterField()
						).value(
							FieldValue.of(nestedFacet.getFilterValue())
						)));

			if (nestedFacet.getChildAggregation() != null) {
				filterAggregationContainerBuilder.aggregations(
					FacetUtil.getAggregationName(facet),
					_getChildAggregation(nestedFacet.getChildAggregation()));
			}
			else {
				filterAggregationContainerBuilder.aggregations(
					FacetUtil.getAggregationName(facet), termsAggregation);
			}

			nestedAggregationContainerBuilder.aggregations(
				FacetUtil.getAggregationName(facet),
				filterAggregationContainerBuilder.build());
		}
		else {
			nestedAggregationContainerBuilder.aggregations(
				FacetUtil.getAggregationName(facet), termsAggregation);
		}

		return nestedAggregationContainerBuilder;
	}

	private AggregationRange _createAggregationRange(
		String from, String key, String to) {

		AggregationRange.Builder builder = new AggregationRange.Builder();

		if (!Validator.isBlank(from)) {
			builder.from(from);
		}

		if (!Validator.isBlank(key)) {
			builder.key(key);
		}

		if (!Validator.isBlank(to)) {
			builder.to(to);
		}

		return builder.build();
	}

	private org.opensearch.client.opensearch._types.aggregations.Aggregation
		_getChildAggregation(Aggregation aggregation) {

		if (aggregation instanceof DateRangeAggregation) {
			return _getDateRangeAggregation((DateRangeAggregation)aggregation);
		}
		else if (aggregation instanceof RangeAggregation) {
			return _getRangeAggregation((RangeAggregation)aggregation);
		}

		Class<?> clazz = aggregation.getClass();

		throw new UnsupportedOperationException(
			"Nested facet does not support child aggregation " +
				clazz.getName());
	}

	private org.opensearch.client.opensearch._types.aggregations.Aggregation
		_getDateRangeAggregation(DateRangeAggregation dateRangeAggregation) {

		org.opensearch.client.opensearch._types.aggregations.
			DateRangeAggregation.Builder builder =
				AggregationBuilders.dateRange();

		builder.field(dateRangeAggregation.getField());
		builder.format(dateRangeAggregation.getFormat());

		for (Range range : dateRangeAggregation.getRanges()) {
			builder.ranges(
				DateRangeExpression.of(
					dateRangeExpression -> dateRangeExpression.from(
						ConversionUtil.toFieldDateMath(
							range.getFromAsString(), range.getFrom())
					).key(
						range.getKey()
					).to(
						ConversionUtil.toFieldDateMath(
							range.getToAsString(), range.getTo())
					)));
		}

		return new org.opensearch.client.opensearch._types.aggregations.
			Aggregation(builder.build());
	}

	private org.opensearch.client.opensearch._types.aggregations.Aggregation
		_getRangeAggregation(RangeAggregation rangeAggregation) {

		org.opensearch.client.opensearch._types.aggregations.RangeAggregation.
			Builder builder = AggregationBuilders.range();

		builder.field(rangeAggregation.getField());

		ListUtil.isNotEmptyForEach(
			rangeAggregation.getRanges(),
			range -> builder.ranges(
				_createAggregationRange(
					OpenSearchStringUtil.getFirstStringValue(
						range::getFromAsString, range::getFrom),
					range.getKey(),
					OpenSearchStringUtil.getFirstStringValue(
						range::getToAsString, range::getTo))));

		return new org.opensearch.client.opensearch._types.aggregations.
			Aggregation(builder.build());
	}

	private TermsAggregation _getTermsAggregation(NestedFacet nestedFacet) {
		TermsAggregation.Builder builder = AggregationBuilders.terms();

		builder.field(nestedFacet.getFieldName());

		FacetConfiguration facetConfiguration =
			nestedFacet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		int minDocCount = dataJSONObject.getInt("frequencyThreshold", -1);

		if (minDocCount >= 0) {
			builder.minDocCount(minDocCount);
		}

		int size = dataJSONObject.getInt("maxTerms");

		if (size > 0) {
			builder.size(size);
		}

		return builder.build();
	}

}