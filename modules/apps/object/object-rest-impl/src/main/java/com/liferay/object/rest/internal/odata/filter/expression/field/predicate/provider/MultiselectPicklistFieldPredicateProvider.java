/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.odata.filter.expression.field.predicate.provider.FieldPredicateProvider;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(
	property = "field.predicate.provider.key=" + ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
	service = FieldPredicateProvider.class
)
public class MultiselectPicklistFieldPredicateProvider
	implements FieldPredicateProvider {

	@Override
	public Predicate getBinaryExpressionPredicate(
		Expression<?> objectDefinitionColumnSupplierExpression,
		BinaryExpression.Operation operation, Object fieldValue) {

		if (!Objects.equals(operation, BinaryExpression.Operation.EQ)) {
			throw new UnsupportedOperationException(
				operation +
					" is not supported in MultiselectPicklist Object Fields");
		}

		Expression<String> columnFieldExpression = _getFormatedColumnExpression(
			(Expression<String>)objectDefinitionColumnSupplierExpression);

		return columnFieldExpression.like(
			_getFieldValueExpression(null, fieldValue));
	}

	@Override
	public Predicate getContainsPredicate(
		Expression<?> objectDefinitionColumnSupplierExpression,
		Object fieldValue) {

		return objectDefinitionColumnSupplierExpression.like(
			_getFieldValueExpression(
				MethodExpression.Type.CONTAINS, fieldValue));
	}

	@Override
	public Predicate getInPredicate(
		Expression<?> objectDefinitionColumnSupplierExpression,
		List<Object> rights) {

		if (ListUtil.isEmpty(rights)) {
			return null;
		}

		Predicate predicate = null;

		for (Object right : rights) {
			if (predicate == null) {
				predicate = getBinaryExpressionPredicate(
					objectDefinitionColumnSupplierExpression,
					BinaryExpression.Operation.EQ, right);
			}
			else {
				predicate = predicate.or(
					getBinaryExpressionPredicate(
						objectDefinitionColumnSupplierExpression,
						BinaryExpression.Operation.EQ, right));
			}
		}

		return predicate;
	}

	@Override
	public Predicate getStartsWithPredicate(
		Expression<?> objectDefinitionColumnSupplierExpression,
		Object fieldValue) {

		Expression<String> columnFieldExpression = _getFormatedColumnExpression(
			(Expression<String>)objectDefinitionColumnSupplierExpression);

		return columnFieldExpression.like(
			_getFieldValueExpression(
				MethodExpression.Type.STARTS_WITH, fieldValue));
	}

	private Expression<String> _getFieldValueExpression(
		MethodExpression.Type methodExpressionType, Object fieldValue) {

		if (Objects.equals(
				methodExpressionType, MethodExpression.Type.CONTAINS)) {

			return DSLFunctionFactoryUtil.concat(
				new Scalar<>(StringPool.PERCENT),
				new Scalar<>(fieldValue.toString()),
				new Scalar<>(StringPool.PERCENT));
		}
		else if (Objects.equals(
					methodExpressionType, MethodExpression.Type.STARTS_WITH)) {

			return DSLFunctionFactoryUtil.concat(
				new Scalar<>("%, " + fieldValue + "%, %"));
		}

		return DSLFunctionFactoryUtil.concat(
			new Scalar<>("%, "), new Scalar<>(fieldValue.toString()),
			new Scalar<>(", %"));
	}

	private Expression<String> _getFormatedColumnExpression(
		Expression<String> expression) {

		StringBundler sb = new StringBundler(2);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);

		return DSLFunctionFactoryUtil.concat(
			new Scalar<>(sb.toString()), expression,
			new Scalar<>(sb.toString()));
	}

}