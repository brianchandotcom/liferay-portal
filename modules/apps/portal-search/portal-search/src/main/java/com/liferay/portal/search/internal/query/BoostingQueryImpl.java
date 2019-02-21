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

import com.liferay.portal.search.query.BoostingQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class BoostingQueryImpl extends BaseQueryImpl implements BoostingQuery {

	public BoostingQueryImpl(Query positiveQuery, Query negativeQuery) {
		_positiveQuery = positiveQuery;
		_negativeQuery = negativeQuery;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public Float getNegativeBoost() {
		return _negativeBoost;
	}

	public Query getNegativeQuery() {
		return _negativeQuery;
	}

	public Query getPositiveQuery() {
		return _positiveQuery;
	}

	public void setNegativeBoost(Float negativeBoost) {
		_negativeBoost = negativeBoost;
	}

	private static final long serialVersionUID = 1L;

	private Float _negativeBoost;
	private final Query _negativeQuery;
	private final Query _positiveQuery;

}