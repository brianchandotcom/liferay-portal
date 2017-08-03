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

package com.liferay.sharepoint.repository.search;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.util.StringBundler;

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
		String field = queryTerm.getField();

		String value = queryTerm.getValue();

		_print(sb, field + " == \"" + value + "\"");
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
			termRangeQuery.getField() + " ∈ " + openInterval + "\"" +
				lowerTerm + "\", \"" + upperTerm + "\"" + closeInterval);
	}

	private void _explain(StringBundler sb, WildcardQuery wildcardQuery) {
		_explain(sb, wildcardQuery.getQueryTerm());
	}

	private void _print(StringBundler sb, String s) {
		sb.append(s);
	}

}