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

package com.liferay.portal.kernel.dao.model.dsl.query;

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.clause.WhereClause;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Where
	extends BaseASTNode implements GroupByStep, LimitStep, OrderByStep, Query {

	public Where(WhereStep whereStep, WhereClause whereClause) {
		super(whereStep);

		_whereClause = Objects.requireNonNull(whereClause);
	}

	public WhereClause getWhereClause() {
		return _whereClause;
	}

	@Override
	protected void doAccept(ASTNodeVisitor astNodeVisitor) {
		astNodeVisitor.visit(this);
	}

	private final WhereClause _whereClause;

}