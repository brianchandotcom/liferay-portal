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

package com.liferay.structured.content.apio.internal.architect.filter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.structured.content.apio.architect.entity.EntityField;
import com.liferay.structured.content.apio.architect.filter.expression.BinaryExpression;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitor;
import com.liferay.structured.content.apio.architect.filter.expression.LiteralExpression;
import com.liferay.structured.content.apio.architect.filter.expression.MemberExpression;

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
		Locale locale,
		StructuredContentSingleEntitySchemaBasedEdmProvider
			structuredContentSingleEntitySchemaBasedEdmProvider) {

		_locale = locale;
		_structuredContentSingleEntitySchemaBasedEdmProvider =
			structuredContentSingleEntitySchemaBasedEdmProvider;
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
				LiteralExpression.Type.STRING, literalExpression.getType())) {

			return _normalizeLiteral(literalExpression.getText());
		}

		return literalExpression.getText();
	}

	@Override
	public Object visitMemberExpression(MemberExpression memberExpression) {
		List<String> resourcePath = memberExpression.getResourcePath();

		Map<String, EntityField> entityFieldsMap =
			_structuredContentSingleEntitySchemaBasedEdmProvider.
				getEntityFieldsMap();

		return entityFieldsMap.get(resourcePath.get(0));
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
			entityField.getSortableName(locale), String.valueOf(fieldValue));
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
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			filter = _getLEFilter((EntityField)left, right, locale);
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

		return new RangeTermFilter(
			entityField.getSortableName(locale), true, true,
			String.valueOf(fieldValue), null);
	}

	private Filter _getLEFilter(
		EntityField entityField, Object fieldValue, Locale locale) {

		return new RangeTermFilter(
			entityField.getSortableName(locale), false, true, null,
			String.valueOf(fieldValue));
	}

	private Filter _getORFilter(Filter leftFilter, Filter rightFilter) {
		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(leftFilter, BooleanClauseOccur.SHOULD);
		booleanFilter.add(rightFilter, BooleanClauseOccur.SHOULD);

		return booleanFilter;
	}

	private Object _normalizeLiteral(String literal) {
		literal = StringUtil.toLowerCase(literal);

		literal = StringUtil.unquote(literal);

		return StringUtil.replace(
			literal, StringPool.DOUBLE_APOSTROPHE, StringPool.APOSTROPHE);
	}

	private final Locale _locale;
	private final StructuredContentSingleEntitySchemaBasedEdmProvider
		_structuredContentSingleEntitySchemaBasedEdmProvider;

}