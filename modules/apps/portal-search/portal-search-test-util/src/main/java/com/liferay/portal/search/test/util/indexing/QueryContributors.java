/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;

/**
 * @author André de Oliveira
 */
public class QueryContributors {

	public static QueryContributor dummy() {
		return query -> {
		};
	}

	public static QueryContributor must(Query query1) {
		return query2 -> _add(
			(BooleanQuery)query2, query1, BooleanClauseOccur.MUST);
	}

	public static QueryContributor mustMatch(String field, String value) {
		return must(new MatchQuery(field, value));
	}

	public static QueryContributor mustNot(Query query1) {
		return query2 -> _add(
			(BooleanQuery)query2, query1, BooleanClauseOccur.MUST_NOT);
	}

	public static QueryContributor mustNotMatch(String field, String value) {
		return mustNot(new MatchQuery(field, value));
	}

	public static QueryContributor mustNotTerm(String field, String value) {
		return mustNot(new TermQueryImpl(field, value));
	}

	public static QueryContributor mustTerm(String field, String value) {
		return must(new TermQueryImpl(field, value));
	}

	private static void _add(
		BooleanQuery booleanQuery, Query query,
		BooleanClauseOccur booleanClauseOccur) {

		try {
			booleanQuery.add(query, booleanClauseOccur);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}
	}

}