/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Crescenzo Rega
 */
public class BooleanQueryParserUtil {

	public static BooleanQuery parse(Queries queries, String filterString) {
		if (Validator.isNull(filterString)) {
			return null;
		}

		Map<String, Map<?, ?>> clauseMap = _getClauseMap(filterString);

		if (MapUtil.isEmpty(clauseMap)) {
			return null;
		}

		BooleanQuery booleanQuery = queries.booleanQuery();

		Map<String, SimpleClause> simpleClauses = (Map)clauseMap.get(
			"simple");

		for (Map.Entry<String, SimpleClause> entry :
				simpleClauses.entrySet()) {

			SimpleClause simpleClause = entry.getValue();

			String operator = simpleClause.getOperator();

			if (operator.contains("eq")) {
				booleanQuery.addFilterQueryClauses(
					queries.term(
						_fieldMapping.get(simpleClause.getField()),
						simpleClause.getValue()));
			}
			else if (operator.contains("in")) {
				TermsQuery termsQuery = queries.terms(
					_fieldMapping.get(simpleClause.getField()));

				ListUtil.isNotEmptyForEach(
					(List)simpleClause.getValue(), termsQuery::addValues);

				booleanQuery.addShouldQueryClauses(termsQuery);
			}
			else if (operator.contains("ne")) {
				booleanQuery.addMustNotQueryClauses(
					queries.term(
						_fieldMapping.get(simpleClause.getField()),
						simpleClause.getValue()));
			}
			else if (operator.contains("not_in")) {
				TermsQuery termsQuery = queries.terms(
					_fieldMapping.get(simpleClause.getField()));

				termsQuery.addValues(
					(Object[])ArrayUtil.toStringArray(
						(List)simpleClause.getValue()));

				booleanQuery.addMustNotQueryClauses(termsQuery);
			}
			else if (operator.contains("or")) {
				BooleanQuery orBooleanQuery = queries.booleanQuery();

				List<Query> values = new ArrayList<>();

				ListUtil.isNotEmptyForEach(
					(List)simpleClause.getValue(),
					object -> values.add(
						queries.term(
							_fieldMapping.get(simpleClause.getField()),
							object)));

				orBooleanQuery.addShouldQueryClauses(
					values.toArray(new Query[0]));

				booleanQuery.addMustQueryClauses(orBooleanQuery);
			}
		}

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"yyyyMMddHHmmss"
		).withZone(
			ZoneId.of("UTC")
		);

		Map<String, DateRangeClause> dateRangeClauseMap = (Map)clauseMap.get(
			"dateRange");

		for (Map.Entry<String, DateRangeClause> entry :
				dateRangeClauseMap.entrySet()) {

			DateRangeClause dateRangeClause = entry.getValue();

			String start = dateRangeClause.getStart();
			String end = dateRangeClause.getEnd();

			booleanQuery.addMustQueryClauses(
				queries.dateRangeTerm(
					_fieldMapping.get(dateRangeClause.getField()),
					Validator.isNotNull(start), Validator.isNotNull(end),
					Validator.isNotNull(start) ?
						dateTimeFormatter.format(Instant.parse(start)) : null,
					Validator.isNotNull(end) ?
						dateTimeFormatter.format(Instant.parse(end)) : null));
		}

		return booleanQuery;
	}

	private static Map<String, Map<?, ?>> _getClauseMap(String filterString) {
		Map<String, DateRangeClause> dateRangeClauses = new HashMap<>();
		Map<String, SimpleClause> simpleClauses = new HashMap<>();

		String[] clauses = StringUtil.split(
			StringUtil.trim(
				filterString.replaceAll(
					"\\s*AND\\s*", " AND "
				).replaceAll(
					"\\s*and\\s*", " AND "
				).replaceAll(
					"\\s*OR\\s*", " OR "
				).replaceAll(
					"\\s*or\\s*", " OR "
				)),
			" AND ");

		for (String clause : clauses) {
			if (Validator.isNull(clause)) {
				continue;
			}

			Matcher notMatcher = _clauseNotPattern.matcher(clause);

			if (notMatcher.matches()) {
				Matcher matcher = _clausesPattern.matcher(notMatcher.group(1));

				if (notMatcher.matches() && matcher.find()) {
					String field = matcher.group(1);
					String operator = matcher.group(2);

					if (operator.equals("in")) {
						String rawValue = matcher.group(3);

						String innerIn = rawValue.replaceAll(
							"^'|'$", ""
						).replaceAll(
							"^\\(|\\)$", ""
						);

						String[] parts = innerIn.split(",");

						List<String> valuesList = new ArrayList<>();

						for (String part : parts) {
							valuesList.add(StringUtil.trim(part));
						}

						simpleClauses.put(
							field,
							new SimpleClause(field, "not_in", valuesList));

						continue;
					}
				}
			}

			if (clause.startsWith("(") && clause.endsWith(")")) {
				clause = clause.substring(1, clause.length() - 1);

				if (clause.contains(" OR ")) {
					String[] orParts = clause.split(" OR ");
					List<String> values = new ArrayList<>();
					String field = null;

					for (String orPart : orParts) {
						Matcher matcher = _clausesPattern.matcher(orPart);

						if (matcher.find()) {
							String leftOperand = matcher.group(1);
							String operator = matcher.group(2);

							if (operator.equals("eq")) {
								if (field == null) {
									field = leftOperand;
								}
								else if (!field.equals(leftOperand)) {
									continue;
								}

								values.add(matcher.group(3));
							}
						}
					}

					if ((field != null) && !values.isEmpty()) {
						simpleClauses.put(
							field, SimpleClause.add(field, values));

						continue;
					}
				}
			}

			Matcher matcher = _clausesPattern.matcher(clause);

			if (matcher.find()) {
				String field = matcher.group(1);
				String operator = matcher.group(2);

				String rawValue = matcher.group(3);

				String value = rawValue.replaceAll("^'|'$", "");

				if (operator.equals("in")) {
					value = value.replaceAll(
						"^\\(|\\)$", ""
					).replaceAll(
						" ", ""
					);

					simpleClauses.put(
						field,
						new SimpleClause(
							field, operator,
							new ArrayList<>(Arrays.asList(value.split(",")))));
				}
				else if (operator.equals("ge") || operator.equals("le")) {
					dateRangeClauses.computeIfAbsent(
						field, DateRangeClause::new
					).addDateRangeClause(
						operator, value
					);
				}
				else {
					simpleClauses.put(
						field, new SimpleClause(field, operator, value));
				}
			}
		}

		return HashMapBuilder.<String, Map<?, ?>>put(
			"dateRange", dateRangeClauses
		).put(
			"simple", simpleClauses
		).build();
	}

	private static final Pattern _clauseNotPattern = Pattern.compile(
		"\\(\\s*not\\s+\\(([^)]+)\\)\\s*\\)", Pattern.CASE_INSENSITIVE);
	private static final Pattern _clausesPattern = Pattern.compile(
		"(\\w+)\\s+(eq|in|ge|le|ne)\\s+(.*)");
	private static final Map<String, String> _fieldMapping = HashMapBuilder.put(
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

		public static SimpleClause add(String field, List<String> values) {
			List<String> cleanedValues = TransformUtil.transform(
				values, value -> value.replaceAll("^'|'$", ""));

			return new SimpleClause(field, "or", cleanedValues);
		}

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