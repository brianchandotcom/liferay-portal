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
import java.util.function.Consumer;
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

		if (right == null) {
			if (operation == BinaryExpression.Operation.EQ) {
				return expression.isNull();
			}
			else if (operation == BinaryExpression.Operation.NE) {
				return expression.isNotNull();
			}
		}

		right = _toDBSpecificFormat(right);

		try {
			return _getDateTimePredicate(right, expression, operation);
		}
		catch (ParseException parseException) {
			throw new ExpressionVisitException(
				"Unable to parse date " + right, parseException);
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

	private Predicate _getDateTimePredicate(
			Object date, Expression<Object> expression,
			BinaryExpression.Operation operation)
		throws ParseException {

		if (operation == BinaryExpression.Operation.EQ) {
			Object truncatedDate = _truncateMilliseconds(date);

			return expression.gte(
				truncatedDate
			).and(
				expression.lt(_incrementSecond(truncatedDate))
			).withParentheses();
		}
		else if (operation == BinaryExpression.Operation.GE) {
			return expression.gte(_truncateMilliseconds(date));
		}
		else if (operation == BinaryExpression.Operation.GT) {
			return expression.gte(
				_incrementSecond(_truncateMilliseconds(date)));
		}
		else if (operation == BinaryExpression.Operation.LE) {
			return expression.lt(_incrementSecond(_truncateMilliseconds(date)));
		}
		else if (operation == BinaryExpression.Operation.LT) {
			return expression.lt(_truncateMilliseconds(date));
		}
		else if (operation == BinaryExpression.Operation.NE) {
			Object truncatedDate = _truncateMilliseconds(date);

			return expression.lt(
				truncatedDate
			).or(
				expression.gte(_incrementSecond(truncatedDate))
			).withParentheses();
		}

		return null;
	}

	private Object _incrementSecond(Object date) throws ParseException {
		return _processDate(date, calendar -> calendar.add(Calendar.SECOND, 1));
	}

	private Object _processDate(
			Object date, Consumer<Calendar> calendarOperation)
		throws ParseException {

		Date dateObj;
		Function<Object, Object> resultTransformer;

		if (date instanceof String) {
			String dateString = (String)date;

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				ObjectFieldUtil.getDateTimePattern(dateString));

			dateObj = dateFormat.parse(dateString);

			resultTransformer = dateFormat::format;
		}
		else {
			dateObj = (Date)date;
			resultTransformer = Function.identity();
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(dateObj.getTime());

		calendarOperation.accept(calendar);

		return resultTransformer.apply(calendar.getTime());
	}

	private Object _toDBSpecificFormat(Object date) {
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

	private Object _truncateMilliseconds(Object date) throws ParseException {
		return _processDate(
			date, calendar -> calendar.set(Calendar.MILLISECOND, 0));
	}

}