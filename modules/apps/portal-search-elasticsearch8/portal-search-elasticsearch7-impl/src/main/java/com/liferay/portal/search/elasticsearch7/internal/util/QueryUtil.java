/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.util;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.AbstractQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Gustavo Lima
 */
public class QueryUtil {

	public static Integer maxTermsCount = 65536;

	public static AbstractQueryBuilder<? extends AbstractQueryBuilder<?>>
		translateTerms(String field, String[] terms) {

		if (terms.length <= maxTermsCount) {
			return QueryBuilders.termsQuery(field, terms);
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		List<String> termsList = new ArrayList<>();

		for (String term : terms) {
			termsList.add(term);

			if (termsList.size() == maxTermsCount) {
				boolQueryBuilder.should(
					QueryBuilders.termsQuery(
						field, termsList.toArray(new String[0])));

				termsList.clear();
			}
		}

		if (!termsList.isEmpty()) {
			boolQueryBuilder.should(
				QueryBuilders.termsQuery(
					field, termsList.toArray(new String[0])));
		}

		return boolQueryBuilder;
	}

}