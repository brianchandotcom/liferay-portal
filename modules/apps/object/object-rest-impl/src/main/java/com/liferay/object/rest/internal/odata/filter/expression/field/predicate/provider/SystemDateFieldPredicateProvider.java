/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.odata.filter.expression.field.predicate.provider;

import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.odata.filter.expression.field.predicate.provider.FieldPredicateProvider;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
			BinaryExpression.Operation operation, Object right)
		throws ExpressionVisitException {

		Expression<Object> expression =
			(Expression<Object>)objectDefinitionColumnSupplier.apply(
				String.valueOf(left));

		right = _formatDateForSpecificDBType(right);

		if (right == null) {
			if (operation == BinaryExpression.Operation.EQ) {
				return expression.isNull();
			}
			else if (operation == BinaryExpression.Operation.NE) {
				return expression.isNotNull();
			}
		}

		if (right instanceof Date) {
			return _getDateTimePredicate(
				(Date)right, expression, Function.identity(), operation);
		}

		String valueString = (String)right;

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			ObjectFieldUtil.getDateTimePattern(valueString));

		try {
			return _getDateTimePredicate(
				dateFormat.parse(valueString), expression, dateFormat::format,
				operation);
		}
		catch (ParseException parseException) {
			throw new ExpressionVisitException(
				"Unable to parse date " + valueString, parseException);
		}
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
			Object left, List<Object> rights)
		throws ExpressionVisitException {

		Predicate predicate = null;

		for (Object right : rights) {
			Predicate binaryExpressionPredicate = getBinaryExpressionPredicate(
				objectDefinitionColumnSupplier, left, 0L,
				BinaryExpression.Operation.EQ, right);

			if (predicate == null) {
				predicate = binaryExpressionPredicate;

				continue;
			}

			predicate = predicate.or(binaryExpressionPredicate);
		}

		return Predicate.withParentheses(predicate);
	}

	@Override
	public Predicate getIsNotEmptyPredicate(
		String fieldName,
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier) {

		throw new UnsupportedOperationException(
			"Unsupported method getIsNotEmptyPredicate for " +
				"dateCreated/dateModified fields");
	}

	@Override
	public Predicate getStartsWithPredicate(
		Function<String, Column<?, ?>> objectDefinitionColumnSupplier,
		String fieldName, Object fieldValue) {

		throw new UnsupportedOperationException(
			"Unsupported method getStartsWithPredicate for " +
				"dateCreated/dateModified fields");
	}

	private Object _formatDateForSpecificDBType(Object date) {
		if (Objects.equals(DBManagerUtil.getDBType(), DBType.DB2) ||
			Objects.equals(DBManagerUtil.getDBType(), DBType.HYPERSONIC) ||
			Objects.equals(DBManagerUtil.getDBType(), DBType.ORACLE)) {

			String pattern = "yyyy-MM-dd HH:mm:ss.SSS";

			if (Objects.equals(DBManagerUtil.getDBType(), DBType.ORACLE)) {
				pattern = "dd-MMM-yyyy hh:mm:ss.SSS a";
			}

			Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
				pattern);

			return format.format(date);
		}

		return date;
	}

	private Predicate _getDateTimePredicate(
		Date date, Expression<Object> expression,
		Function<Object, Object> function,
		BinaryExpression.Operation operation) {

		if (operation == BinaryExpression.Operation.EQ) {
			Date truncatedDate = _truncateMilliseconds(date);

			return expression.gte(
				function.apply(truncatedDate)
			).and(
				expression.lt(function.apply(_incrementSecond(truncatedDate)))
			).withParentheses();
		}
		else if (operation == BinaryExpression.Operation.GE) {
			return expression.gte(function.apply(_truncateMilliseconds(date)));
		}
		else if (operation == BinaryExpression.Operation.GT) {
			return expression.gte(
				function.apply(_incrementSecond(_truncateMilliseconds(date))));
		}
		else if (operation == BinaryExpression.Operation.LE) {
			return expression.lt(
				function.apply(_incrementSecond(_truncateMilliseconds(date))));
		}
		else if (operation == BinaryExpression.Operation.LT) {
			return expression.lt(function.apply(_truncateMilliseconds(date)));
		}
		else if (operation == BinaryExpression.Operation.NE) {
			Date truncatedDate = _truncateMilliseconds(date);

			return expression.lt(
				function.apply(truncatedDate)
			).or(
				expression.gte(function.apply(_incrementSecond(truncatedDate)))
			).withParentheses();
		}

		return null;
	}

	private Date _incrementSecond(Date date) {
		Calendar calendar = CalendarFactoryUtil.getCalendar(date.getTime());

		calendar.add(Calendar.SECOND, 1);

		return calendar.getTime();
	}

	private Date _truncateMilliseconds(Date date) {
		Calendar calendar = CalendarFactoryUtil.getCalendar(date.getTime());

		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

}