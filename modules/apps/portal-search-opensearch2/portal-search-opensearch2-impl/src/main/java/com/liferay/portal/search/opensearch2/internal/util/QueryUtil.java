/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.util;

import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch._types.query_dsl.RangeQuery;

/**
 * @author Petteri Karttunen
 */
public class QueryUtil {

	public static List<String> fieldsBoostsToFieldsWithBoosts(
		Map<String, Float> fieldsBoosts) {

		if (MapUtil.isEmpty(fieldsBoosts)) {
			return Collections.emptyList();
		}

		List<String> fieldsWithBoosts = new ArrayList<>();

		MapUtil.isNotEmptyForEach(
			fieldsBoosts,
			(key, value) -> {
				if (value == null) {
					value = 1.0F;
				}

				fieldsWithBoosts.add(key + "^" + value);
			});

		return fieldsWithBoosts;
	}

	public static void setRanges(
		RangeQuery.Builder builder, boolean includesLower,
		boolean includesUpper, Object lowerTerm, Object upperTerm) {

		if (lowerTerm != null) {
			if (includesLower) {
				builder.gte(JsonData.of(lowerTerm));
			}
			else {
				builder.gt(JsonData.of(lowerTerm));
			}
		}

		if (upperTerm != null) {
			if (includesUpper) {
				builder.lte(JsonData.of(upperTerm));
			}
			else {
				builder.lt(JsonData.of(upperTerm));
			}
		}
	}

}