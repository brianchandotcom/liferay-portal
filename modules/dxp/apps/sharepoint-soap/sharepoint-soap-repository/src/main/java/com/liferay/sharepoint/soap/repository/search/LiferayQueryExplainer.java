/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;

/**
 * @author Iván Zaera
 */
public class LiferayQueryExplainer {

	public String explain(Query query) {
		StringBundler sb = new StringBundler();

		_explain(sb, query);

		return sb.toString();
	}

	private void _explain(StringBundler sb, BooleanQuery booleanQuery) {
		for (BooleanClause<Query> booleanClause : booleanQuery.clauses()) {
			BooleanClauseOccur booleanClauseOccur =
				booleanClause.getBooleanClauseOccur();

			Query query = booleanClause.getClause();

			_print(sb, "<" + booleanClauseOccur.getName() + ">");

			_explain(sb, query);

			_print(sb, "</" + booleanClauseOccur.getName() + ">");
		}
	}

	private void _explain(StringBundler sb, Query query) {
		if (query instanceof BooleanQuery) {
			_explain(sb, (BooleanQuery)query);
		}
		else if (query instanceof TermQuery) {
			_explain(sb, (TermQuery)query);
		}
		else if (query instanceof TermRangeQuery) {
			_explain(sb, (TermRangeQuery)query);
		}
		else if (query instanceof WildcardQuery) {
			_explain(sb, (WildcardQuery)query);
		}
	}

	private void _explain(StringBundler sb, QueryTerm queryTerm) {
		_print(
			sb,
			StringBundler.concat(
				queryTerm.getField(), " == \"", queryTerm.getValue(), "\""));
	}

	private void _explain(StringBundler sb, TermQuery termQuery) {
		_explain(sb, termQuery.getQueryTerm());
	}

	private void _explain(StringBundler sb, TermRangeQuery termRangeQuery) {
		String lowerTerm = termRangeQuery.getLowerTerm();

		String upperTerm = termRangeQuery.getUpperTerm();

		String openInterval = termRangeQuery.includesLower() ? "[" : "(";

		String closeInterval = termRangeQuery.includesUpper() ? "]" : ")";

		_print(
			sb,
			StringBundler.concat(
				termRangeQuery.getField(), " ∈ ", openInterval, "\"", lowerTerm,
				"\", \"", upperTerm, "\"", closeInterval));
	}

	private void _explain(StringBundler sb, WildcardQuery wildcardQuery) {
		_explain(sb, wildcardQuery.getQueryTerm());
	}

	private void _print(StringBundler sb, String s) {
		sb.append(s);
	}

}