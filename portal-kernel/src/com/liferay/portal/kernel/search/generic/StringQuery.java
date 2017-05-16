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

package com.liferay.portal.kernel.search.generic;

import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * Provides support for parsing raw, human readable query syntax. No
 * transformation is made on user input.
 *
 * <p>
 * The actual query syntax and any further processing are dependent on your
 * search engine's implementation details. Consult your search provider's
 * documentation for more information.
 * </p>
 *
 * @author Bruno Farache
 */
public class StringQuery extends BaseQueryImpl implements Query {

	public StringQuery(String query) {
		_query = query;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public String getQuery() {
		return _query;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", query=");
		sb.append(_query);
		sb.append("}");

		return sb.toString();
	}

	private final String _query;

}