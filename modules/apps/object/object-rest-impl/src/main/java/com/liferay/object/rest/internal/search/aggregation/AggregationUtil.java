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

package com.liferay.object.rest.internal.search.aggregation;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.NestedAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;

import java.util.Map;

/**
 * @author Javier de Arcos
 */
public class AggregationUtil {

	public static void processVulcanAggregation(
		Aggregations aggregations, Queries queries,
		SearchRequestBuilder searchRequestBuilder,
		Aggregation vulcanAggregation) {

		if (vulcanAggregation == null) {
			return;
		}

		Map<String, String> vulcanAggregationTerms =
			vulcanAggregation.getAggregationTerms();

		for (Map.Entry<String, String> vulcanAggregationEntry :
				vulcanAggregationTerms.entrySet()) {

			String aggregationEntryValue = vulcanAggregationEntry.getValue();

			if (!aggregationEntryValue.startsWith("nestedFieldArray")) {
				continue;
			}

			String[] aggregationEntryValueParts = aggregationEntryValue.split(
				StringPool.POUND);

			TermsAggregation termsAggregation = aggregations.terms(
				vulcanAggregationEntry.getKey(), aggregationEntryValueParts[0]);

			FilterAggregation filterAggregation = aggregations.filter(
				"filterAggregation",
				queries.term(
					"nestedFieldArray.fieldName",
					aggregationEntryValueParts[1]));

			filterAggregation.addChildAggregation(termsAggregation);

			NestedAggregation nestedAggregation = aggregations.nested(
				vulcanAggregationEntry.getKey(), "nestedFieldArray");

			nestedAggregation.addChildAggregation(filterAggregation);

			searchRequestBuilder.addAggregation(nestedAggregation);
		}
	}

}