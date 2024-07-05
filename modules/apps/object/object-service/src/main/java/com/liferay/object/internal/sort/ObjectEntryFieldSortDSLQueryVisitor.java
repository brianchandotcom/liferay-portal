/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.sort;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.OrderByStep;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;
import com.liferay.petra.sql.dsl.spi.expression.AggregateExpression;
import com.liferay.petra.sql.dsl.spi.query.OrderBy;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Clob;
import java.sql.Types;

import java.util.Objects;
import java.util.Stack;

/**
 * @author Carlos Correa
 */
public class ObjectEntryFieldSortDSLQueryVisitor
	extends BaseSortDSLQueryVisitor {

	public ObjectEntryFieldSortDSLQueryVisitor(
		ObjectFieldLocalService objectFieldLocalService) {

		super(objectFieldLocalService, null);
	}

	@Override
	public DSLQuery visit(DSLQuery dslQuery, Sort sort) throws PortalException {
		ObjectDefinition objectDefinition = sort.getObjectDefinition();

		ObjectField objectField = objectFieldLocalService.fetchObjectField(
			objectDefinition.getObjectDefinitionId(), sort.getFieldName());

		Expression<?> columnExpression = null;
		Table fieldTable = null;
		String prefix = StringPool.BLANK;

		if (objectField == null) {
			Column<?, Object> column =
				(Column<?, Object>)objectFieldLocalService.getColumn(
					objectDefinition.getObjectDefinitionId(),
					sort.getFieldName());

			fieldTable = getAliasedTable(_getSuffix(sort), column.getTable());

			columnExpression = fieldTable.getColumn(sort.getFieldName());
		}
		else {
			fieldTable = getAliasedTable(
				_getSuffix(sort),
				objectFieldLocalService.getTable(
					objectDefinition.getObjectDefinitionId(),
					objectField.getName()));

			columnExpression = _getColumnExpression(objectField, fieldTable);

			if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_BOOLEAN)) {

				prefix = "AGGREGATION_BOOLEAN_";
			}
		}

		if (!contains(dslQuery, fieldTable)) {
			dslQuery = addLeftJoin(
				getPrimaryKeyColumn(fieldTable), null, dslQuery, fieldTable);
		}

		OrderByExpression orderByExpression = _getOrderByExpression(
			_isParentComplexField(sort), columnExpression, prefix,
			sort.isReverse());

		Stack<BaseASTNode> allBaseASTNodes = getAllBaseASTNodes(
			OrderByStep.class, dslQuery);

		OrderByStep orderByStep = (OrderByStep)allBaseASTNodes.pop();

		if (allBaseASTNodes.peek() instanceof OrderBy) {
			OrderBy orderBy = (OrderBy)allBaseASTNodes.pop();

			BaseASTNode baseASTNode = new OrderBy(
				(OrderByStep)orderBy.getChild(),
				ArrayUtil.append(
					orderBy.getOrderByExpressions(), orderByExpression));

			return updateParents(baseASTNode, allBaseASTNodes);
		}

		BaseASTNode baseASTNode = new OrderBy(
			orderByStep, new OrderByExpression[] {orderByExpression});

		return updateParents(baseASTNode, allBaseASTNodes);
	}

	private Expression<?> _getColumnExpression(
		ObjectField objectField, Table table) {

		if (objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_AUTO_INCREMENT)) {

			return table.getColumn(objectField.getSortableDBColumnName());
		}

		Column<?, ?> column = table.getColumn(objectField.getDBColumnName());

		if (column.getSQLType() == Types.CLOB) {
			return DSLFunctionFactoryUtil.castClobText(
				(Expression<Clob>)column);
		}

		return column;
	}

	private OrderByExpression _getOrderByExpression(
		boolean aggregate, Expression<?> expression, String prefix,
		boolean reverse) {

		if (reverse) {
			if (aggregate) {
				expression = new AggregateExpression<>(
					false, expression, prefix + "max");
			}

			return expression.descending();
		}

		if (aggregate) {
			expression = new AggregateExpression<>(
				false, expression, prefix + "min");
		}

		return expression.ascending();
	}

	private String _getSuffix(Sort sort) {
		if (_isParentComplexField(sort)) {
			return StringUtil.replace(
				StringUtil.removeLast(
					sort.getFieldPath(),
					CharPool.FORWARD_SLASH + sort.getFieldName()),
				CharPool.FORWARD_SLASH, CharPool.UNDERLINE);
		}

		return null;
	}

	private boolean _isParentComplexField(Sort sort) {
		if (StringUtil.equals(sort.getFieldName(), sort.getFieldPath())) {
			return false;
		}

		return true;
	}

}