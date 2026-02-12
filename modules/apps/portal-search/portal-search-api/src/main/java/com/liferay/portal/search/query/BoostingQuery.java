/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

/**
 * @author Michael C. Han
 */
public class BoostingQuery extends Query {

	public BoostingQuery(Query positiveQuery, Query negativeQuery) {
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