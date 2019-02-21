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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.DisMaxQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class DisMaxQueryImpl extends BaseQueryImpl implements DisMaxQuery {

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
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

	private static final long serialVersionUID = 1L;

	private final Set<Query> _queries = new HashSet<>();
	private Float _tieBreaker;

}