/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.generic;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactory;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;

/**
 * @author Bruno Farache
 */
public class BooleanClauseFactoryImpl implements BooleanClauseFactory {

	@Override
	public BooleanClause<Query> create(Query query, String occur) {
		return new BooleanClauseImpl<>(
			query, BooleanClauseOccur.valueOf(occur));
	}

	@Override
	public BooleanClause<Query> create(
		String field, String value, String occur) {

		return new BooleanClauseImpl<>(
			new TermQueryImpl(field, value), BooleanClauseOccur.valueOf(occur));
	}

	@Override
	public BooleanClause<Filter> createFilter(
		Filter filter, BooleanClauseOccur booleanClauseOccur) {

		return new BooleanClauseImpl<>(filter, booleanClauseOccur);
	}

	@Override
	public BooleanClause<Filter> createFilter(
		String field, String value, BooleanClauseOccur booleanClauseOccur) {

		return new BooleanClauseImpl<Filter>(
			new TermFilter(field, value), booleanClauseOccur);
	}

}