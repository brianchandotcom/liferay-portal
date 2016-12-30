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
 * @author Michael C. Han
 */
public class NestedQuery extends BaseQueryImpl {

	public NestedQuery(String path, Query query) {
		_path = path;
		_query = query;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public String getPath() {
		return _path;
	}

	public Query getQuery() {
		return _query;
	}

	@Override
	public boolean hasChildren() {
		if (_query == null) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", path=");
		sb.append(_path);
		sb.append(", query=");
		sb.append(_query);
		sb.append("}");

		return sb.toString();
	}

	private final String _path;
	private final Query _query;

}