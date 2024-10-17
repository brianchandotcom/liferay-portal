/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider;

import com.liferay.object.odata.filter.expression.field.predicate.provider.FieldPredicateProvider;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.filter.expression.BinaryExpression;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Raposo
 */
@Component(
	property = {
		"field.predicate.provider.key=dateCreated",
		"field.predicate.provider.key=dateModified"
	},
	service = FieldPredicateProvider.class
)
public class SystemDateFieldPredicateProvider
	implements FieldPredicateProvider {

	@Override
	public Predicate getBinaryExpressionPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object left, long objectDefinitionId,
		BinaryExpression.Operation operation, Object right) {

		Expression<String> expression =
			(Expression<String>)objectDefinitionColumnSupplier.apply(
				String.valueOf(left));

		if ((operation == BinaryExpression.Operation.NE) && (right == null)) {
			return expression.isNotNull();
		}
		else if ((operation == BinaryExpression.Operation.EQ) &&
				 (right == null)) {

			return expression.isNull();
		}

		return _getDateTimePredicate(expression, operation, (String)right);
	}

	@Override
	public Predicate getContainsPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		String fieldName, Object fieldValue) {

		throw new UnsupportedOperationException(
			"Unsupported method getContainsPredicate for " +
				"dateCreated/dateModified fields");
	}

	@Override
	public Predicate getInPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		Object left, List<Object> rights) {

		Expression<String> expression =
			(Expression<String>)objectDefinitionColumnSupplier.apply(
				String.valueOf(left));

		Predicate predicate = null;

		for (Object right : rights) {
			Predicate eqPredicate = _getDateTimePredicate(
				expression, BinaryExpression.Operation.EQ, (String)right);

			if (predicate == null) {
				predicate = eqPredicate;
			}
			else {
				predicate = predicate.or(eqPredicate);
			}
		}

		return predicate;
	}

	@Override
	public Predicate getIsNotEmptyPredicate(
		String fieldName,
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier) {

		return null;
	}

	@Override
	public Predicate getStartsWithPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		String fieldName, Object fieldValue) {

		throw new UnsupportedOperationException(
			"Unsupported method getStartsWithPredicate for " +
				"dateCreated/dateModified fields");
	}

	private <T> T _adjustToEndOfMillis(T value) {
		String dateString = (String)_adjustToStartOfMillis(value);

		if (value instanceof String) {
			try {
				String pattern = _getDateTimePattern(dateString);

				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(pattern);

				Date date = dateFormat.parse((String)dateString);

				date.setTime(date.getTime() + 1000);

				return (T)dateFormat.format(date);
			}
			catch (ParseException parseException) {
				throw new IllegalArgumentException(
					"Unable to parse date from " + value, parseException);
			}
		}
		else if (value instanceof Date) {
			Date date = (Date)value;

			date.setTime(date.getTime() + 1000);

			return (T)date;
		}

		return value;
	}

	private <T> T _adjustToStartOfMillis(T value) {
		if (value instanceof String) {
			String dateString = (String)value;

			try {
				String pattern = _getDateTimePattern(dateString);

				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(pattern);

				if (pattern.equals("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
					if (dateString.matches(
							"^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$")) {

						dateString = StringUtil.replace(
							dateString, 'Z', ".000Z");
					}
					else if (dateString.matches(
								"^\\d{4}-\\d{2}-\\d{2}T\\d{2}:" +
									"\\d{2}:\\d{2}\\.\\d{4,}Z$")) {

						String truncatedDateString = dateString.substring(
							0, dateString.indexOf('.') + 4);

						dateString = truncatedDateString + "Z";
					}
				}

				Date date = dateFormat.parse(dateString);

				date.setTime((date.getTime() / 1000) * 1000);

				if (dateString.length() == 20) {
					return (T)DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
					).format(
						date
					);
				}

				return (T)dateFormat.format(date);
			}
			catch (ParseException parseException) {
				throw new IllegalArgumentException(
					"Unable to parse date from " + dateString, parseException);
			}
		}
		else if (value instanceof Date) {
			Date date = (Date)value;

			date.setTime((date.getTime() / 1000) * 1000);

			return (T)date;
		}

		return value;
	}

	private String _getDateTimePattern(String value) {
		if (value.matches(
				"^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?Z$")) {

			return "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; // oData
		}

		if (value.length() == 23) {
			return "yyyy-MM-dd HH:mm:ss.SSS"; // Hypersonic, DB2
		}

		if (value.length() == 27) {
			return "dd-MMM-yyyy HH:mm:ss.SSS a"; // Oracle
		}

		throw new IllegalArgumentException(
			"Unrecognized date format: " + value);
	}

	private <T> Predicate _getDateTimePredicate(
		Expression<T> expression, BinaryExpression.Operation operation,
		T value) {

		if (operation == BinaryExpression.Operation.EQ) {
			T startOfSecond = _adjustToStartOfMillis(value);
			T endOfSecond = _adjustToEndOfMillis(value);

			return expression.gte(
				startOfSecond
			).and(
				expression.lt(endOfSecond)
			);
		}
		else if (operation == BinaryExpression.Operation.NE) {
			T startOfSecond = _adjustToStartOfMillis(value);
			T endOfSecond = _adjustToEndOfMillis(value);

			return expression.lt(
				startOfSecond
			).or(
				expression.gte(endOfSecond)
			);
		}
		else if (operation == BinaryExpression.Operation.GT) {
			return expression.gte(_adjustToEndOfMillis(value));
		}
		else if (operation == BinaryExpression.Operation.GE) {
			return expression.gte(_adjustToStartOfMillis(value));
		}
		else if (operation == BinaryExpression.Operation.LT) {
			return expression.lt(_adjustToStartOfMillis(value));
		}
		else if (operation == BinaryExpression.Operation.LE) {
			return expression.lt(_adjustToEndOfMillis(value));
		}

		return null;
	}

}