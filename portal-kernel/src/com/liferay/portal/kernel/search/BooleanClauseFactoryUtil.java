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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanClauseFactoryImpl;

/**
 * @author Bruno Farache
 */
public class BooleanClauseFactoryUtil {

	public static BooleanClause<Query> create(Query query, String occur) {
		return getBooleanClauseFactory().create(query, occur);
	}

	public static BooleanClause<Query> create(
		String field, String value, String occur) {

		return getBooleanClauseFactory().create(field, value, occur);
	}

	public static BooleanClause<Filter> createFilter(
		Filter filter, BooleanClauseOccur booleanClauseOccur) {

		return getBooleanClauseFactory().createFilter(
			filter, booleanClauseOccur);
	}

	public static BooleanClause<Filter> createFilter(
		SearchContext searchContext, Filter filter,
		BooleanClauseOccur booleanClauseOccur) {

		return getBooleanClauseFactory().createFilter(
			filter, booleanClauseOccur);
	}

	public static BooleanClause<Filter> createFilter(
		SearchContext searchContext, String field, String value,
		BooleanClauseOccur booleanClauseOccur) {

		return getBooleanClauseFactory().createFilter(
			field, value, booleanClauseOccur);
	}

	public static BooleanClause<Filter> createFilter(
		String field, String value, BooleanClauseOccur booleanClauseOccur) {

		return getBooleanClauseFactory().createFilter(
			field, value, booleanClauseOccur);
	}

	public static BooleanClauseFactory getBooleanClauseFactory() {
		return _booleanClauseFactory;
	}

	private static final BooleanClauseFactory _booleanClauseFactory =
		new BooleanClauseFactoryImpl();

}