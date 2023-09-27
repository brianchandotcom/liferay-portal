/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.generic;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class DisMaxQuery extends BaseQueryImpl {

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public void addQuery(Query query) {
		_queries.add(query);
	}

	public Set<Query> getQueries() {
		return Collections.unmodifiableSet(_queries);
	}

	public Float getTieBreaker() {
		return _tieBreaker;
	}

	@Override
	public boolean hasChildren() {
		return !isEmpty();
	}

	public boolean isEmpty() {
		return _queries.isEmpty();
	}

	public void setTieBreaker(Float tieBreaker) {
		_tieBreaker = tieBreaker;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", queries=");
		sb.append(_queries);
		sb.append(", tieBreaker=");
		sb.append(_tieBreaker);
		sb.append("}");

		return sb.toString();
	}

	private final Set<Query> _queries = new HashSet<>();
	private Float _tieBreaker;

}