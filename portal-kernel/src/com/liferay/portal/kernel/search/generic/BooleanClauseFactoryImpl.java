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

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactory;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanClauseOccurImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;

/**
 * @author Bruno Farache
 */
public class BooleanClauseFactoryImpl implements BooleanClauseFactory {

	@Override
	public BooleanClause<Query> create(Query query, String occur) {
		BooleanClauseOccur booleanClauseOccur = new BooleanClauseOccurImpl(
			occur);

		return new BooleanClauseImpl<>(query, booleanClauseOccur);
	}

	@Override
	public BooleanClause<Query> create(
		String field, String value, String occur) {

		Query query = new TermQueryImpl(field, value);

		BooleanClauseOccur booleanClauseOccur = new BooleanClauseOccurImpl(
			occur);

		return new BooleanClauseImpl<>(query, booleanClauseOccur);
	}

	@Override
	public BooleanClause<Filter> createFilter(
		Filter filter, BooleanClauseOccur booleanClauseOccur) {

		return new BooleanClauseImpl<>(filter, booleanClauseOccur);
	}

	@Override
	public BooleanClause<Filter> createFilter(
		String field, String value, BooleanClauseOccur booleanClauseOccur) {

		TermFilter termFilter = new TermFilter(field, value);

		return new BooleanClauseImpl<Filter>(termFilter, booleanClauseOccur);
	}

}