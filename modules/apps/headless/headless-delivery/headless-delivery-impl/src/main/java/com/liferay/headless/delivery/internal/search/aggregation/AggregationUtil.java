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

package com.liferay.headless.delivery.internal.search.aggregation;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.delivery.internal.dynamic.data.mapping.DDMStructureField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
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
				_getTermQueryField(ddmStructureField));

			FilterAggregation filterAggregation = aggregations.filter(
				"filterAggregation",
				queries.term(
					DDMIndexer.DDM_FIELD_ARRAY + "." +
						DDMIndexer.DDM_FIELD_NAME,
					_getDDMStructureNestedFieldName(ddmStructureField)));

			filterAggregation.addChildAggregation(termsAggregation);

			NestedAggregation nestedAggregation = aggregations.nested(
				"nestedAggregation", DDMIndexer.DDM_FIELD_ARRAY);

			nestedAggregation.addChildAggregation(filterAggregation);

			searchRequestBuilder.addAggregation(nestedAggregation);
		}
	}

	private static String _getDDMStructureNestedFieldName(
		DDMStructureField ddmStructureField) {

		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_PREFIX, ddmStructureField.getIndexType(),
			DDMIndexer.DDM_FIELD_SEPARATOR,
			ddmStructureField.getDDMStructureId(),
			DDMIndexer.DDM_FIELD_SEPARATOR, ddmStructureField.getName(),
			StringPool.UNDERLINE, ddmStructureField.getLocale());
	}

	private static String _getTermQueryField(
		DDMStructureField ddmStructureField) {

		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
			StringUtil.upperCaseFirstLetter(ddmStructureField.getIndexType()),
			StringPool.UNDERLINE, ddmStructureField.getLocale());
	}

}