/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public Float getNegativeBoost() {
		return _negativeBoost;
	}

	@Override
	public Query getNegativeQuery() {
		return _negativeQuery;
	}

	@Override
	public Query getPositiveQuery() {
		return _positiveQuery;
	}

	@Override
	public void setNegativeBoost(Float negativeBoost) {
		_negativeBoost = negativeBoost;
	}

	private static final long serialVersionUID = 1L;

	private Float _negativeBoost;
	private final Query _negativeQuery;
	private final Query _positiveQuery;

}