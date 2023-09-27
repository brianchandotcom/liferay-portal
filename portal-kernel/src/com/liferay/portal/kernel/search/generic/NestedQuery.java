/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.generic;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.QueryVisitor;

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