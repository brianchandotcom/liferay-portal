/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.filter.Filter;

/**
 * @author Bruno Farache
 */
public interface BooleanClauseFactory {

	public BooleanClause<Query> create(Query query, String occur);

	public BooleanClause<Query> create(
		String field, String value, String occur);

	public BooleanClause<Filter> createFilter(
		Filter filter, BooleanClauseOccur booleanClauseOccur);

	public BooleanClause<Filter> createFilter(
		String field, String value, BooleanClauseOccur booleanClauseOccur);

}