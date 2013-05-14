/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;

/**
 * @author Brian Wing Shun Chan
 */
public class BooleanClauseImpl implements BooleanClause {

	public BooleanClauseImpl(
		org.apache.lucene.search.BooleanClause booleanClause) {

		_booleanClause = booleanClause;
	}

	public org.apache.lucene.search.BooleanClause getBooleanClause() {
		return _booleanClause;
	}

	@Override
	public BooleanClauseOccur getBooleanClauseOccur() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Query getQuery() {
		throw new UnsupportedOperationException();
	}

	private org.apache.lucene.search.BooleanClause _booleanClause;

}