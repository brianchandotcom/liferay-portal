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

package com.liferay.portal.kernel.dao.model.dsl.ast;

import com.liferay.portal.kernel.dao.model.Column;
import com.liferay.portal.kernel.dao.model.Table;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Alias;
import com.liferay.portal.kernel.dao.model.dsl.expressions.CaseWhenThen;
import com.liferay.portal.kernel.dao.model.dsl.expressions.ElseEnd;
import com.liferay.portal.kernel.dao.model.dsl.expressions.NullExpression;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Predicate;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Scalar;
import com.liferay.portal.kernel.dao.model.dsl.expressions.ScalarList;
import com.liferay.portal.kernel.dao.model.dsl.expressions.WhenThen;
import com.liferay.portal.kernel.dao.model.dsl.functions.Function;
import com.liferay.portal.kernel.dao.model.dsl.joins.Join;
import com.liferay.portal.kernel.dao.model.dsl.query.AggregateExpression;
import com.liferay.portal.kernel.dao.model.dsl.query.From;
import com.liferay.portal.kernel.dao.model.dsl.query.GroupBy;
import com.liferay.portal.kernel.dao.model.dsl.query.Limit;
import com.liferay.portal.kernel.dao.model.dsl.query.NamedQuery;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderBy;
import com.liferay.portal.kernel.dao.model.dsl.query.Select;
import com.liferay.portal.kernel.dao.model.dsl.query.Where;
import com.liferay.portal.kernel.dao.model.dsl.set.Union;

/**
 * @author Preston Crary
 */
public interface ASTNodeVisitor {

	public void visit(AggregateExpression<?> aggregateExpression);

	public void visit(Alias<?> alias);

	public void visit(CaseWhenThen<?> caseWhenThen);

	public void visit(Column<?, ?> column);

	public void visit(ElseEnd<?> elseEnd);

	public void visit(From from);

	public void visit(Function<?> function);

	public void visit(GroupBy groupBy);

	public void visit(Join join);

	public void visit(Limit limit);

	public void visit(NamedQuery namedQuery);

	public void visit(NullExpression nullExpression);

	public void visit(OrderBy orderBy);

	public void visit(Predicate predicate);

	public void visit(Scalar<?> scalar);

	public void visit(ScalarList<?> scalarList);

	public void visit(Select select);

	public void visit(Table<?> table);

	public void visit(Union union);

	public void visit(WhenThen<?> whenThen);

	public void visit(Where where);

	public void visited(ASTNode astNode);

}