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

package com.liferay.portal.kernel.dao.model.dsl.ast.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.model.Column;
import com.liferay.portal.kernel.dao.model.Table;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNode;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.clause.PredicateClause;
import com.liferay.portal.kernel.dao.model.dsl.clause.TableClause;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Alias;
import com.liferay.portal.kernel.dao.model.dsl.expressions.CaseWhenThen;
import com.liferay.portal.kernel.dao.model.dsl.expressions.ElseEnd;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;
import com.liferay.portal.kernel.dao.model.dsl.expressions.NullExpression;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Predicate;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Scalar;
import com.liferay.portal.kernel.dao.model.dsl.expressions.ScalarList;
import com.liferay.portal.kernel.dao.model.dsl.expressions.WhenThen;
import com.liferay.portal.kernel.dao.model.dsl.functions.Function;
import com.liferay.portal.kernel.dao.model.dsl.functions.FunctionType;
import com.liferay.portal.kernel.dao.model.dsl.joins.Join;
import com.liferay.portal.kernel.dao.model.dsl.query.AggregateExpression;
import com.liferay.portal.kernel.dao.model.dsl.query.From;
import com.liferay.portal.kernel.dao.model.dsl.query.GroupBy;
import com.liferay.portal.kernel.dao.model.dsl.query.Limit;
import com.liferay.portal.kernel.dao.model.dsl.query.NamedQuery;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderBy;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderByExpression;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;
import com.liferay.portal.kernel.dao.model.dsl.query.Select;
import com.liferay.portal.kernel.dao.model.dsl.query.Where;
import com.liferay.portal.kernel.dao.model.dsl.set.Union;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class DefaultASTNodeVisitor implements ASTNodeVisitor {

	public DefaultASTNodeVisitor() {
		_sb = new StringBundler();
	}

	public DefaultASTNodeVisitor(StringBundler sb) {
		_sb = sb;
	}

	public int getEnd() {
		return _end;
	}

	public List<Object> getScalarValues() {
		return _scalarValues;
	}

	public int getStart() {
		return _start;
	}

	public Set<Table<?>> getTables() {
		return _tables;
	}

	@Override
	public String toString() {
		return _sb.toString();
	}

	@Override
	public void visit(AggregateExpression<?> aggregateExpression) {
		_sb.append(aggregateExpression.getName());

		_sb.append("(");

		if (aggregateExpression.isDistinct()) {
			_sb.append("distinct ");
		}

		Expression<?> expression = aggregateExpression.getExpression();

		if (expression == null) {
			_sb.append("*");
		}
		else {
			expression.accept(this);
		}

		_sb.append(")");
	}

	@Override
	public void visit(Alias<?> alias) {
		_sb.append(alias.getAlias());
	}

	@Override
	public void visit(CaseWhenThen<?> caseWhenThen) {
		_sb.append("case when ");

		visit(caseWhenThen.getPredicate());

		_sb.append(" then ");

		Expression<?> thenExpression = caseWhenThen.getThenExpression();

		thenExpression.accept(this);
	}

	@Override
	public void visit(Column<?, ?> column) {
		Table<?> table = column.getTable();

		_sb.append(table.getName());

		_sb.append(".");
		_sb.append(column.getColumnName());
	}

	@Override
	public void visit(ElseEnd<?> elseEnd) {
		_sb.append("else ");

		Expression<?> elseExpression = elseEnd.getElseExpression();

		elseExpression.accept(this);

		_sb.append(" end");
	}

	@Override
	public void visit(From from) {
		_sb.append("from ");

		TableClause tableClause = from.getTableClause();

		tableClause.accept(this);
	}

	@Override
	public void visit(Function<?> function) {
		FunctionType functionType = function.getFunctionType();

		_sb.append(functionType.getPrefix());

		for (Expression<?> expression : function.getExpressions()) {
			expression.accept(this);

			_sb.append(functionType.getDelimiter());
		}

		_sb.setStringAt(functionType.getPostfix(), _sb.index() - 1);
	}

	@Override
	public void visit(GroupBy groupBy) {
		_sb.append("group by ");

		for (Expression expression : groupBy.getExpressions()) {
			expression.accept(this);

			_sb.append(", ");
		}

		_sb.setIndex(_sb.index() - 1);
	}

	@Override
	public void visit(Join join) {
		_sb.append(join.getJoinType());

		_sb.append(" join ");

		TableClause tableClause = join.getTableClause();

		tableClause.accept(this);

		_sb.append(" on ");

		visit(join.getOnPredicate());
	}

	@Override
	public void visit(Limit limit) {
		_start = limit.getStart();
		_end = limit.getEnd();
	}

	@Override
	public void visit(NamedQuery namedQuery) {
		_sb.append("(");

		Query query = namedQuery.getQuery();

		query.accept(this);

		_sb.append(") ");

		_sb.append(namedQuery.getName());
	}

	@Override
	public void visit(NullExpression nullExpression) {
		_sb.append("NULL");
	}

	@Override
	public void visit(OrderBy orderBy) {
		_sb.append("order by ");

		for (OrderByExpression orderByExpression :
				orderBy.getOrderByExpressions()) {

			Expression<?> expression = orderByExpression.getExpression();

			expression.accept(this);

			if (orderByExpression.isAscending()) {
				_sb.append(" asc");
			}
			else {
				_sb.append(" desc");
			}

			_sb.append(", ");
		}

		_sb.setIndex(_sb.index() - 1);
	}

	@Override
	public void visit(Predicate predicate) {
		Expression<?> leftExpression = predicate.getLeftExpression();

		if (predicate.isWrapParentheses()) {
			_sb.append("(");
		}

		leftExpression.accept(this);

		_sb.append(" ");
		_sb.append(predicate.getOperand());
		_sb.append(" ");

		PredicateClause predicateClause = predicate.getPredicateClause();

		if (predicateClause instanceof Query) {
			_sb.append("(");

			predicateClause.accept(this);

			_sb.append(")");
		}
		else {
			predicateClause.accept(this);
		}

		if (predicate.isWrapParentheses()) {
			_sb.append(")");
		}
	}

	@Override
	public void visit(Scalar<?> scalar) {
		Object value = scalar.getValue();

		if (value instanceof Long) {
			_sb.append((long)value);
		}
		else if (value instanceof Integer) {
			_sb.append((int)value);
		}
		else if (value == null) {
			_sb.append(StringPool.NULL);
		}
		else {
			_scalarValues.add(value);

			_sb.append(StringPool.QUESTION);
		}
	}

	@Override
	public void visit(ScalarList<?> scalarList) {
		_sb.append("(");

		for (Object value : scalarList.getValues()) {
			if (value instanceof Long) {
				_sb.append((long)value);
			}
			else if (value instanceof Integer) {
				_sb.append((int)value);
			}
			else if (value == null) {
				_sb.append(StringPool.NULL);
			}
			else {
				_scalarValues.add(value);

				_sb.append(StringPool.QUESTION);
			}

			_sb.append(", ");
		}

		_sb.setStringAt(")", _sb.index() - 1);
	}

	@Override
	public void visit(Select select) {
		_sb.append("select ");

		if (select.isDistinct()) {
			_sb.append("distinct ");
		}

		Expression<?>[] expressions = select.getExpressions();

		if (expressions.length > 0) {
			for (Expression<?> expression : expressions) {
				Expression<?> unwrappedExpression = expression.unwrapAlias();

				unwrappedExpression.accept(this);

				String alias = expression.getAlias();

				if (alias != null) {
					_sb.append(" ");
					_sb.append(alias);
				}

				_sb.append(", ");
			}

			_sb.setIndex(_sb.index() - 1);
		}
		else {
			_sb.append("*");
		}
	}

	@Override
	public void visit(Table<?> table) {
		_tables.add(table);

		_sb.append(table.getTableName());

		String alias = table.getAlias();

		if (alias != null) {
			_sb.append(" ");
			_sb.append(alias);
		}
	}

	@Override
	public void visit(Union union) {
		Query leftQuery = union.getLeftQuery();

		leftQuery.accept(this);

		if (union.isAll()) {
			_sb.append(" union all ");
		}
		else {
			_sb.append(" union ");
		}

		Query rightQuery = union.getRightQuery();

		rightQuery.accept(this);
	}

	@Override
	public void visit(WhenThen<?> whenThen) {
		_sb.append("when ");

		visit(whenThen.getPredicate());

		_sb.append(" then ");

		Expression<?> thenExpression = whenThen.getThenExpression();

		thenExpression.accept(this);
	}

	@Override
	public void visit(Where where) {
		_sb.append("where ");

		Predicate predicate = where.getPredicate();

		predicate.accept(this);
	}

	@Override
	public void visited(ASTNode astNode) {
		_sb.append(" ");
	}

	private int _end = QueryUtil.ALL_POS;
	private final StringBundler _sb;
	private final List<Object> _scalarValues = new ArrayList<>();
	private int _start = QueryUtil.ALL_POS;
	private final Set<Table<?>> _tables = new LinkedHashSet<>();

}