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

package com.liferay.portal.odata.internal.filter;

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Julio Camarero
 */
public class ExpressionVisitorImpl implements ExpressionVisitor<Object> {

	public ExpressionVisitorImpl(
		Format format, Locale locale, EntityModel entityModel) {

		_format = format;
		_locale = locale;
		_entityModel = entityModel;
	}

	@Override
	public Filter visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		Optional<Filter> filterOptional = _getFilterOptional(
			operation, left, right, _locale);

		return filterOptional.orElseThrow(
			() -> new UnsupportedOperationException(
				"Unsupported method visitBinaryExpressionOperation with " +
					"operation " + operation));
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression) {
		if (Objects.equals(
				LiteralExpression.Type.DATE, literalExpression.getType()) ||
			Objects.equals(
				LiteralExpression.Type.DATE_TIME,
				literalExpression.getType())) {

			return _normalizeDateLiteral(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.STRING,
					literalExpression.getType())) {

			return _normalizeStringLiteral(literalExpression.getText());
		}

		return literalExpression.getText();
	}

	@Override
	public Object visitMemberExpression(MemberExpression memberExpression) {
		List<String> resourcePath = memberExpression.getResourcePath();

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		EntityField entityField = entityFieldsMap.get(resourcePath.get(0));

		if ((resourcePath.size() == 2) &&
			Objects.equals(entityField.getType(), EntityField.Type.COMPLEX)) {

			ComplexEntityField complexEntityField =
				(ComplexEntityField)entityField;

			Map<String, EntityField> entityFieldMap =
				complexEntityField.getEntityFieldsMap();

			return entityFieldMap.get(resourcePath.get(1));
		}

		return entityField;
	}

	@Override
	public Object visitMethodExpression(
		List<Object> expressions, MethodExpression.Type type) {

		if (type == MethodExpression.Type.CONTAINS) {
			if (expressions.size() != 2) {
				throw new UnsupportedOperationException(
					StringBundler.concat(
						"Unsupported method visitMethodExpression with method",
						"type ", type, " and ", expressions.size(), "params"));
			}

			return _contains(
				(EntityField)expressions.get(0), expressions.get(1), _locale);
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitMethodExpression with method type " +
				type);
	}

	@Override
	public Filter visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		if (Objects.equals(UnaryExpression.Operation.NOT, operation)) {
			return _getNotFilter((Filter)operand);
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitUnaryExpressionOperation with operation " +
				operation);
	}

	private Filter _contains(
		EntityField entityField, Object fieldValue, Locale locale) {

		return new QueryFilter(
			new WildcardQueryImpl(
				entityField.getFilterableName(locale),
				"*" + entityField.getFilterableValue(fieldValue) + "*"));
	}

	private Filter _getANDFilter(Filter leftFilter, Filter rightFilter) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(leftFilter, BooleanClauseOccur.MUST);
		booleanFilter.add(rightFilter, BooleanClauseOccur.MUST);

		return booleanFilter;
	}

	private Filter _getEQFilter(
		EntityField entityField, Object fieldValue, Locale locale) {

		return new TermFilter(
			entityField.getFilterableName(locale),
			entityField.getFilterableValue(fieldValue));
	}

	private Optional<Filter> _getFilterOptional(
		BinaryExpression.Operation operation, Object left, Object right,
		Locale locale) {

		Filter filter = null;

		if (Objects.equals(BinaryExpression.Operation.AND, operation)) {
			filter = _getANDFilter((Filter)left, (Filter)right);
		}
		else if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			filter = _getEQFilter((EntityField)left, right, locale);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			filter = _getGEFilter((EntityField)left, right, locale);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			filter = _getGTFilter((EntityField)left, right, locale);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			filter = _getLEFilter((EntityField)left, right, locale);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			filter = _getLTFilter((EntityField)left, right, locale);
		}
		else if (Objects.equals(BinaryExpression.Operation.OR, operation)) {
			filter = _getORFilter((Filter)left, (Filter)right);
		}
		else {
			return Optional.empty();
		}

		return Optional.of(filter);
	}

	private Filter _getGEFilter(
		EntityField entityField, Object fieldValue, Locale locale) {

		if (Objects.equals(entityField.getType(), EntityField.Type.DATE) ||
			Objects.equals(entityField.getType(), EntityField.Type.DATE_TIME) ||
			Objects.equals(entityField.getType(), EntityField.Type.DOUBLE) ||
			Objects.equals(entityField.getType(), EntityField.Type.INTEGER) ||
			Objects.equals(entityField.getType(), EntityField.Type.STRING)) {

			return new RangeTermFilter(
				entityField.getFilterableName(locale), true, true,
				entityField.getFilterableValue(fieldValue), null);
		}

		throw new UnsupportedOperationException(
			"Unsupported method _getGEFilter with entity field type " +
				entityField.getType());
	}

	private Filter _getGTFilter(
		EntityField entityField, Object fieldValue, Locale locale) {

		if (Objects.equals(entityField.getType(), EntityField.Type.DATE) ||
			Objects.equals(entityField.getType(), EntityField.Type.DATE_TIME) ||
			Objects.equals(entityField.getType(), EntityField.Type.DOUBLE) ||
			Objects.equals(entityField.getType(), EntityField.Type.INTEGER) ||
			Objects.equals(entityField.getType(), EntityField.Type.STRING)) {

			return new RangeTermFilter(
				entityField.getFilterableName(locale), false, true,
				entityField.getFilterableValue(fieldValue), null);
		}

		throw new UnsupportedOperationException(
			"Unsupported method _getGTFilter with entity field type " +
				entityField.getType());
	}

	private Filter _getLEFilter(
		EntityField entityField, Object fieldValue, Locale locale) {

		if (Objects.equals(entityField.getType(), EntityField.Type.DATE) ||
			Objects.equals(entityField.getType(), EntityField.Type.DATE_TIME) ||
			Objects.equals(entityField.getType(), EntityField.Type.DOUBLE) ||
			Objects.equals(entityField.getType(), EntityField.Type.INTEGER) ||
			Objects.equals(entityField.getType(), EntityField.Type.STRING)) {

			return new RangeTermFilter(
				entityField.getFilterableName(locale), false, true, null,
				entityField.getFilterableValue(fieldValue));
		}

		throw new UnsupportedOperationException(
			"Unsupported method _getLEFilter with entity field type " +
				entityField.getType());
	}

	private Filter _getLTFilter(
		EntityField entityField, Object fieldValue, Locale locale) {

		if (Objects.equals(entityField.getType(), EntityField.Type.DATE) ||
			Objects.equals(entityField.getType(), EntityField.Type.DATE_TIME) ||
			Objects.equals(entityField.getType(), EntityField.Type.DOUBLE) ||
			Objects.equals(entityField.getType(), EntityField.Type.INTEGER) ||
			Objects.equals(entityField.getType(), EntityField.Type.STRING)) {

			return new RangeTermFilter(
				entityField.getFilterableName(locale), false, false, null,
				entityField.getFilterableValue(fieldValue));
		}

		throw new UnsupportedOperationException(
			"Unsupported method _getLTFilter with entity field type " +
				entityField.getType());
	}

	private Filter _getNotFilter(Filter filter) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(filter, BooleanClauseOccur.MUST_NOT);

		return booleanFilter;
	}

	private Filter _getORFilter(Filter leftFilter, Filter rightFilter) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(leftFilter, BooleanClauseOccur.SHOULD);
		booleanFilter.add(rightFilter, BooleanClauseOccur.SHOULD);

		return booleanFilter;
	}

	private Object _normalizeDateLiteral(String literal) {
		try {
			Date date = ISO8601Utils.parse(literal, new ParsePosition(0));

			return _format.format(date);
		}
		catch (ParseException pe) {
			throw new InvalidFilterException(
				"Invalid date format, use ISO 8601: " + pe.getMessage());
		}
	}

	private Object _normalizeStringLiteral(String literal) {
		literal = StringUtil.toLowerCase(literal);

		literal = StringUtil.unquote(literal);

		return StringUtil.replace(
			literal, StringPool.DOUBLE_APOSTROPHE, StringPool.APOSTROPHE);
	}

	private final EntityModel _entityModel;
	private final Format _format;
	private final Locale _locale;

}