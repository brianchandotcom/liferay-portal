/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.TermsQuery;

import java.util.ArrayList;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = TermsQueryTranslator.class)
public class TermsQueryTranslatorImpl implements TermsQueryTranslator {

	@Override
	public QueryBuilder translate(TermsQuery termsQuery) {
		String field = termsQuery.getField();
		int maxTermsCount = 65536;
		String[] terms = termsQuery.getValues();

		if (terms.length <= maxTermsCount) {
			return QueryBuilders.termsQuery(field, terms);
		}

		ArrayList<String> termsList = new ArrayList<>();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

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