/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.search.aggregation;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.delivery.dynamic.data.mapping.DDMStructureField;
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
		Aggregations aggregations, DDMIndexer ddmIndexer, Queries queries,
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

			if (ddmIndexer.isLegacyDDMIndexFieldsEnabled() ||
				!aggregationEntryValue.startsWith(
					DDMIndexer.DDM_FIELD_PREFIX)) {

				continue;
			}

			DDMStructureField ddmStructureField = DDMStructureField.from(
				aggregationEntryValue);

			TermsAggregation termsAggregation = aggregations.terms(
				vulcanAggregationEntry.getKey(),
				ddmStructureField.getDDMStructureNestedFieldName());

			FilterAggregation filterAggregation = aggregations.filter(
				"filterAggregation",
				queries.term(
					DDMIndexer.DDM_FIELD_ARRAY + "." +
						DDMIndexer.DDM_FIELD_NAME,
					ddmStructureField.getDDMStructureFieldName()));

			filterAggregation.addChildAggregation(termsAggregation);

			NestedAggregation nestedAggregation = aggregations.nested(
				ddmStructureField.getDDMStructureFieldName(),
				DDMIndexer.DDM_FIELD_ARRAY);

			nestedAggregation.addChildAggregation(filterAggregation);

			searchRequestBuilder.addAggregation(nestedAggregation);
		}
	}

}