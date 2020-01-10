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

package com.liferay.portal.kernel.dao.model.dsl.set;

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Union extends BaseASTNode implements Query {

	public Union(Query leftQuery, boolean all, Query rightQuery) {
		_leftQuery = Objects.requireNonNull(leftQuery);
		_all = all;
		_rightQuery = Objects.requireNonNull(rightQuery);
	}

	public Query getLeftQuery() {
		return _leftQuery;
	}

	public Query getRightQuery() {
		return _rightQuery;
	}

	public boolean isAll() {
		return _all;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		_leftQuery.toSQL(consumer, astNodeListener);

		if (_all) {
			consumer.accept(" union all ");
		}
		else {
			consumer.accept(" union ");
		}

		_rightQuery.toSQL(consumer, astNodeListener);
	}

	private final boolean _all;
	private final Query _leftQuery;
	private final Query _rightQuery;

}