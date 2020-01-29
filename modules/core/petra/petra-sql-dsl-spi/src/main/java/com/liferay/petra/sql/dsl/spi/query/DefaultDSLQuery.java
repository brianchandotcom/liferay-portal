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

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.BaseTable;
import com.liferay.petra.sql.dsl.query.DSLQuery;

/**
 * @author Preston Crary
 */
public interface DefaultDSLQuery extends DSLQuery {

	@Override
	public default BaseTable<?> as(String name) {
		return new QueryTable(name, this);
	}

	@Override
	public default DSLQuery union(DSLQuery dslQuery) {
		return new SetOperation(this, SetOperationType.UNION, dslQuery);
	}

	@Override
	public default DSLQuery unionAll(DSLQuery dslQuery) {
		return new SetOperation(this, SetOperationType.UNION_ALL, dslQuery);
	}

}