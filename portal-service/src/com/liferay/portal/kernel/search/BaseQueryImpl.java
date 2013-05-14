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

package com.liferay.portal.kernel.search;

/**
 * @author Michael C. Han
 */
public abstract class BaseQueryImpl implements Query {

	@Override
	public QueryConfig getQueryConfig() {
		if (_queryConfig == null) {
			_queryConfig = new QueryConfig();
		}

		return _queryConfig;
	}

	@Override
	public abstract Object getWrappedQuery();

	@Override
	public void setQueryConfig(QueryConfig queryConfig) {
		_queryConfig = queryConfig;
	}

	private QueryConfig _queryConfig;

}