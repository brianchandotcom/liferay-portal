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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.clause.TableClause;
import com.liferay.portal.kernel.dao.model.dsl.joins.JoinStep;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class From
	extends BaseASTNode
	implements GroupByStep, JoinStep, LimitStep, OrderByStep, Query, WhereStep {

	public From(FromStep fromStep, TableClause tableClause) {
		super(fromStep);

		_tableClause = Objects.requireNonNull(tableClause);
	}

	@Override
	public void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append("from ");

		_tableClause.toSQL(sb, astNodeListener);
	}

	public TableClause getTableClause() {
		return _tableClause;
	}

	private final TableClause _tableClause;

}