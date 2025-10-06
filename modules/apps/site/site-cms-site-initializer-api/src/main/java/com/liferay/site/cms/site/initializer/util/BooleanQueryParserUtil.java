/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Crescenzo Rega
 */
public class BooleanQueryParserUtil {

	public static BooleanQuery parse(String filter, Queries queries) {
		if (Validator.isNull(filter)) {
			return null;
		}

		Map<String, Map<?, ?>> clausesMap = _getClausesMap(filter);

		if (MapUtil.isEmpty(clausesMap)) {
			return null;
		}

		BooleanQuery booleanQuery = queries.booleanQuery();

		Map<String, DateRangeClause> dateRangeClausesMap =
			(Map<String, DateRangeClause>)clausesMap.get("dateRangeClauses");

		for (Map.Entry<String, DateRangeClause> entry :
				dateRangeClausesMap.entrySet()) {

			DateRangeClause dateRangeClause = entry.getValue();

			String start = dateRangeClause.getStart();
			String end = dateRangeClause.getEnd();

			booleanQuery.addMustQueryClauses(
				queries.dateRangeTerm(
					_fieldsMap.get(dateRangeClause.getField()),
					Validator.isNotNull(start), Validator.isNotNull(end),
					Validator.isNotNull(start) ?
						_dateTimeFormatter.format(Instant.parse(start)) : null,
					Validator.isNotNull(end) ?
						_dateTimeFormatter.format(Instant.parse(end)) : null));
		}

		Map<String, SimpleClause> simpleClausesMap =
			(Map<String, SimpleClause>)clausesMap.get("simpleClauses");

		for (Map.Entry<String, SimpleClause> entry :
				simpleClausesMap.entrySet()) {

			SimpleClause simpleClause = entry.getValue();

			String field = _fieldsMap.get(simpleClause.getField());
			String operator = simpleClause.getOperator();
			Object value = simpleClause.getValue();

			if (StringUtil.equals(operator, "eq")) {
				booleanQuery.addFilterQueryClauses(queries.term(field, value));
			}
			else if (StringUtil.equals(operator, "in")) {
				TermsQuery termsQuery = queries.terms(field);

				termsQuery.addValues(
					(Object[])ArrayUtil.toStringArray((List)value));

				booleanQuery.addShouldQueryClauses(termsQuery);
			}
			else if (StringUtil.equals(operator, "ne")) {
				booleanQuery.addMustNotQueryClauses(queries.term(field, value));
			}
			else if (StringUtil.equals(operator, "not_in")) {
				TermsQuery termsQuery = queries.terms(field);

				termsQuery.addValues(
					(Object[])ArrayUtil.toStringArray((List)value));

				booleanQuery.addMustNotQueryClauses(termsQuery);
			}
			else if (StringUtil.equals(operator, "or")) {
				BooleanQuery shouldBooleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					shouldBooleanQuery.addShouldQueryClauses(
						TransformUtil.transformToArray(
							(List)value, object -> queries.term(field, object),
							Query.class)));
			}
		}

		return booleanQuery;
	}

	private static Map<String, Map<?, ?>> _getClausesMap(String filter) {
		Map<String, DateRangeClause> dateRangeClauses = new HashMap<>();
		Map<String, SimpleClause> simpleClauses = new HashMap<>();

		String[] clauses = StringUtil.split(
			StringUtil.trim(
				filter.replaceAll(
					"\\s+AND\\s+", " AND "
				).replaceAll(
					"\\s+OR\\s+", " OR "
				).replaceAll(
					"\\s+and\\s+", " AND "
				).replaceAll(
					"\\s+or\\s+", " OR "
				)),
			" AND ");

		for (String clause : clauses) {
			if (Validator.isNull(clause)) {
				continue;
			}

			Matcher matcher = _clauseNotPattern.matcher(clause);

			if (matcher.matches()) {
				matcher = _clauseSimplePattern.matcher(matcher.group(1));

				if (matcher.find() &&
					StringUtil.equals(matcher.group(2), "in")) {

					String field = matcher.group(1);

					simpleClauses.put(
						field,
						new SimpleClause(
							field, "not_in",
							TransformUtil.transformToList(
								_removeParenthesesAndSplit(matcher.group(3)),
								BooleanQueryParserUtil::_removeQuotesAndTrim)));
				}
			}
			else if (clause.startsWith("(") && clause.endsWith(")")) {
				clause = clause.substring(1, clause.length() - 1);

				if (clause.contains(" OR ")) {
					String field = null;
					List<String> values = new ArrayList<>();

					for (String part : clause.split(" OR ")) {
						matcher = _clauseSimplePattern.matcher(part);

						if (matcher.find() &&
							StringUtil.equals(matcher.group(2), "eq")) {

							if (Validator.isNull(field)) {
								field = matcher.group(1);
							}
							else if (!field.equals(matcher.group(1))) {
								continue;
							}

							values.add(_removeQuotesAndTrim(matcher.group(3)));
						}
					}

					if (Validator.isNotNull(field) && !values.isEmpty()) {
						simpleClauses.put(
							field, new SimpleClause(field, "or", values));
					}
				}
				else if (clause.contains(" ge ") || clause.contains(" le ")) {
					matcher = _clauseDateRangePattern.matcher(clause);

					if (matcher.find()) {
						dateRangeClauses.computeIfAbsent(
							matcher.group(1), DateRangeClause::new
						).addDateRangeClause(
							matcher.group(2),
							_removeQuotesAndTrim(matcher.group(3))
						);
					}
				}
			}
			else {
				matcher = _clauseSimplePattern.matcher(clause);

				if (matcher.find()) {
					String field = matcher.group(1);
					String operator = matcher.group(2);

					String value = _removeQuotesAndTrim(matcher.group(3));

					if (StringUtil.equals(operator, "in")) {
						simpleClauses.put(
							field,
							new SimpleClause(
								field, operator,
								TransformUtil.transformToList(
									_removeParenthesesAndSplit(value),
									BooleanQueryParserUtil::
										_removeQuotesAndTrim)));
					}
					else {
						simpleClauses.put(
							field, new SimpleClause(field, operator, value));
					}
				}
			}
		}

		return HashMapBuilder.<String, Map<?, ?>>put(
			"dateRangeClauses", dateRangeClauses
		).put(
			"simpleClauses", simpleClauses
		).build();
	}

	private static String[] _removeParenthesesAndSplit(String string) {
		return StringUtil.split(string.replaceAll("^\\(|\\)$", ""));
	}

	private static String _removeQuotesAndTrim(String string) {
		return StringUtil.trim(string.replaceAll("^'|'$", ""));
	}

	private static final Pattern _clauseDateRangePattern = Pattern.compile(
		"(\\w+)\\s+(ge|le)\\s+(.*)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _clauseNotPattern = Pattern.compile(
		"\\(\\s*not\\s+\\(([^)]+)\\)\\s*\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _clauseSimplePattern = Pattern.compile(
		"(\\w+)\\s+(eq|in|ne)\\s+(.*)", Pattern.CASE_INSENSITIVE);
	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern(
			"yyyyMMddHHmmss"
		).withZone(
			ZoneId.of("UTC")
		);
	private static final Map<String, String> _fieldsMap = HashMapBuilder.put(
		"cmsKind", "cms_kind"
	).put(
		"cmsRoot", "cms_root"
	).put(
		"cmsSection", "cms_section"
	).put(
		"dateCreated", Field.CREATE_DATE
	).put(
		"dateDisplay", Field.DISPLAY_DATE
	).put(
		"dateExpiration", Field.EXPIRATION_DATE
	).put(
		"dateModified", Field.MODIFIED_DATE
	).put(
		"datePublish", Field.PUBLISH_DATE
	).put(
		"dateReview", "reviewDate"
	).put(
		"status", "status"
	).build();

	private static class DateRangeClause {

		public DateRangeClause(String field) {
			_field = field;
		}

		public void addDateRangeClause(String operator, String value) {
			if (operator.equals("ge")) {
				_start = value;
			}
			else if (operator.equals("le")) {
				_end = value;
			}
		}

		public String getEnd() {
			return _end;
		}

		public String getField() {
			return _field;
		}

		public String getStart() {
			return _start;
		}

		private String _end;
		private final String _field;
		private String _start;

	}

	private static class SimpleClause {

		public SimpleClause(String field, String operator, Object value) {
			_field = field;
			_operator = operator;
			_value = value;
		}

		public String getField() {
			return _field;
		}

		public String getOperator() {
			return _operator;
		}

		public Object getValue() {
			return _value;
		}

		private final String _field;
		private final String _operator;
		private final Object _value;

	}

}