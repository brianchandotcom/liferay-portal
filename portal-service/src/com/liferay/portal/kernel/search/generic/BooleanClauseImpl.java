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
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;

/**
 * @author Michael C. Han
 */
public class BooleanClauseImpl implements BooleanClause {

	public BooleanClauseImpl(
		Query query, BooleanClauseOccur booleanClauseOccur) {

		_query = query;
		_booleanClauseOccur = booleanClauseOccur;
	}

	@Override
	public BooleanClauseOccur getBooleanClauseOccur() {
		return _booleanClauseOccur;
	}

	@Override
	public Query getQuery() {
		return _query;
	}

	private final BooleanClauseOccur _booleanClauseOccur;
	private final Query _query;

}