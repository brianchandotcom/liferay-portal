/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class WildcardQuery extends BaseQueryImpl {

	public WildcardQuery(QueryTerm queryTerm) {
		_queryTerm = queryTerm;
	}

	public WildcardQuery(String field, String value) {
		this(new QueryTerm(field, value));
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public QueryTerm getQueryTerm() {
		return _queryTerm;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", queryTerm=");
		sb.append(_queryTerm);
		sb.append("}");

		return sb.toString();
	}

	private final QueryTerm _queryTerm;

}