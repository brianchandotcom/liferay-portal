/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author Hugo Huijser
 */
public class BooleanQueryImpl extends BaseQueryImpl implements BooleanQuery {

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public BooleanQuery addFilterQueryClauses(Query... clauses) {
		if (ArrayUtil.isEmpty(clauses)) {
			return this;
		}

		Collections.addAll(_filterQueryClauses, clauses);

		return this;
	}

	@Override
	public BooleanQuery addMustNotQueryClauses(Query... clauses) {
		if (ArrayUtil.isEmpty(clauses)) {
			return this;
		}

		Collections.addAll(_mustNotQueryClauses, clauses);

		return this;
	}

	@Override
	public BooleanQuery addMustQueryClauses(Query... clauses) {
		if (ArrayUtil.isEmpty(clauses)) {
			return this;
		}

		Collections.addAll(_mustQueryClauses, clauses);

		return this;
	}

	@Override
	public BooleanQuery addShouldQueryClauses(Query... clauses) {
		if (ArrayUtil.isEmpty(clauses)) {
			return this;
		}

		Collections.addAll(_shouldQueryClauses, clauses);

		return this;
	}

	@Override
	public Boolean getAdjustPureNegative() {
		return _adjustPureNegative;
	}

	@Override
	public List<Query> getFilterQueryClauses() {
		return Collections.unmodifiableList(_filterQueryClauses);
	}

	@Override
	public Integer getMinimumShouldMatch() {
		return _minimumShouldMatch;
	}

	@Override
	public List<Query> getMustNotQueryClauses() {
		return Collections.unmodifiableList(_mustNotQueryClauses);
	}

	@Override
	public List<Query> getMustQueryClauses() {
		return Collections.unmodifiableList(_mustQueryClauses);
	}

	@Override
	public List<Query> getShouldQueryClauses() {
		return Collections.unmodifiableList(_shouldQueryClauses);
	}

	@Override
	public boolean hasClauses() {
		if (!_filterQueryClauses.isEmpty() || !_mustQueryClauses.isEmpty() ||
			!_mustNotQueryClauses.isEmpty() || !_shouldQueryClauses.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public void setAdjustPureNegative(Boolean adjustPureNegative) {
		_adjustPureNegative = adjustPureNegative;
	}

	@Override
	public void setMinimumShouldMatch(Integer minimumShouldMatch) {
		_minimumShouldMatch = minimumShouldMatch;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", filterQueryClauses=");
		sb.append(_filterQueryClauses);
		sb.append(", mustQueryClauses=");
		sb.append(_mustQueryClauses);
		sb.append(", mustNotQueryClauses=");
		sb.append(_mustNotQueryClauses);
		sb.append(", shouldQueryClauses=");
		sb.append(_shouldQueryClauses);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private Boolean _adjustPureNegative;
	private final List<Query> _filterQueryClauses = new ArrayList<>();
	private Integer _minimumShouldMatch;
	private final List<Query> _mustNotQueryClauses = new ArrayList<>();
	private final List<Query> _mustQueryClauses = new ArrayList<>();
	private final List<Query> _shouldQueryClauses = new ArrayList<>();

}