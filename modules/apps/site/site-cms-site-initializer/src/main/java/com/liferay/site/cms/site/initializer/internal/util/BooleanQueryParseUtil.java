/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
public class BooleanQueryParseUtil {

	public static BooleanQuery parse(Queries queries, String filterString) {
		if (Validator.isNull(filterString)) {
			return null;
		}

		Map<String, Map<?, ?>> parsedResult = _parseFilterString(filterString);

		if (MapUtil.isEmpty(parsedResult)) {
			return null;
		}

		BooleanQuery booleanQuery = queries.booleanQuery();

		Map<String, SimpleClauseData> stringSimpleClauseDataMap =
			(Map)parsedResult.get("simple");

		for (Map.Entry<String, SimpleClauseData> entry :
				stringSimpleClauseDataMap.entrySet()) {

			SimpleClauseData simpleClauseData = entry.getValue();

			String operator = simpleClauseData.getOperator();

			if (operator.contains("eq")) {
				booleanQuery.addFilterQueryClauses(
					queries.term(
						_fieldMapping.get(simpleClauseData.getField()),
						simpleClauseData.getValue()));
			}
			else if (operator.contains("in")) {
				TermsQuery termsQuery = queries.terms(
					_fieldMapping.get(simpleClauseData.getField()));

				ListUtil.isNotEmptyForEach(
					(List)simpleClauseData.getValue(), termsQuery::addValues);

				booleanQuery.addShouldQueryClauses(termsQuery);
			}
			else if (operator.contains("ne")) {
				booleanQuery.addMustNotQueryClauses(
					queries.term(
						_fieldMapping.get(simpleClauseData.getField()),
						simpleClauseData.getValue()));
			}
			else if (operator.contains("not_in")) {
				TermsQuery mustNotTermsQuery = queries.terms(
					_fieldMapping.get(simpleClauseData.getField()));

				mustNotTermsQuery.addValues(
					(Object[])ArrayUtil.toStringArray(
						(List)simpleClauseData.getValue()));

				booleanQuery.addMustNotQueryClauses(mustNotTermsQuery);
			}
			else if (operator.contains("or")) {
				BooleanQuery orBooleanQuery = queries.booleanQuery();

				List<Query> values = new ArrayList<>();

				ListUtil.isNotEmptyForEach(
					(List)simpleClauseData.getValue(),
					orClause -> values.add(
						queries.term(
							_fieldMapping.get(simpleClauseData.getField()),
							orClause)));

				orBooleanQuery.addShouldQueryClauses(
					values.toArray(new Query[0]));

				booleanQuery.addMustQueryClauses(orBooleanQuery);
			}
		}

		Map<String, DateRangeClauseData> dateRangeClauseDataMap =
			(Map)parsedResult.get("dateRange");

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"yyyyMMddHHmmss"
		).withZone(
			ZoneId.of("UTC")
		);

		for (Map.Entry<String, DateRangeClauseData> entry :
				dateRangeClauseDataMap.entrySet()) {

			DateRangeClauseData dateRangeClauseData = entry.getValue();

			String start = dateRangeClauseData.getStart();
			String end = dateRangeClauseData.getEnd();

			booleanQuery.addMustQueryClauses(
				queries.dateRangeTerm(
					_fieldMapping.get(dateRangeClauseData.getField()),
					Validator.isNotNull(start), Validator.isNotNull(end),
					Validator.isNotNull(start) ?
						dateTimeFormatter.format(Instant.parse(start)) : null,
					Validator.isNotNull(end) ?
						dateTimeFormatter.format(Instant.parse(end)) : null));
		}

		return booleanQuery;
	}

	private static Map<String, Map<?, ?>> _parseFilterString(
		String filterString) {

		Map<String, SimpleClauseData> simpleClauses = new HashMap<>();
		Map<String, DateRangeClauseData> dateRangeClauses = new HashMap<>();

		String cleanedString = filterString.trim(
		).replaceAll(
			"\\s*AND\\s*", " AND "
		).replaceAll(
			"\\s*and\\s*", " AND "
		).replaceAll(
			"\\s*OR\\s*", " OR "
		).replaceAll(
			"\\s*or\\s*", " OR "
		);

		String[] rawClauses = cleanedString.split(" AND ");

		for (String rawClause : rawClauses) {
			String clause = rawClause.trim();

			if (clause.isEmpty()) {
				continue;
			}

			Matcher notMatcher = _clauseNotPattern.matcher(clause);

			if (notMatcher.matches()) {
				String innerNotClause = notMatcher.group(
					1
				).trim();

				Matcher innerMatcher = _clausesPattern.matcher(innerNotClause);

				if (innerMatcher.find()) {
					String field = innerMatcher.group(
						1
					).trim();
					String operator = innerMatcher.group(
						2
					).trim();

					if (operator.equals("in")) {
						String rawValue = innerMatcher.group(
							3
						).trim();

						String valueString = rawValue.replaceAll("^'|'$", "");

						String innerIn = valueString.replaceAll(
							"^\\(|\\)$", "");

						String[] parts = innerIn.split(",");

						List<String> valuesList = new ArrayList<>();

						for (String part : parts) {
							valuesList.add(part.trim());
						}

						SimpleClauseData data = new SimpleClauseData(
							field, "not_in", valuesList);

						simpleClauses.put(field, data);

						continue;
					}
				}
			}

			if (clause.startsWith("(") && clause.endsWith(")")) {
				String innerClause = clause.substring(
					1, clause.length() - 1
				).trim();

				if (innerClause.contains(" OR ")) {
					String[] orParts = innerClause.split(" OR ");
					List<String> values = new ArrayList<>();
					String commonField = null;

					for (String orPart : orParts) {
						Matcher orMatcher = _clausesPattern.matcher(
							orPart.trim());

						if (orMatcher.find()) {
							String field = orMatcher.group(
								1
							).trim();
							String operator = orMatcher.group(
								2
							).trim();

							if (operator.equals("eq")) {
								if (commonField == null) {
									commonField = field;
								}
								else if (!commonField.equals(field)) {
									continue;
								}

								String rawValue = orMatcher.group(
									3
								).trim();

								values.add(rawValue);
							}
						}
					}

					if ((commonField != null) && !values.isEmpty()) {
						SimpleClauseData data = SimpleClauseData.createOrClause(
							commonField, values);

						simpleClauses.put(commonField, data);

						continue;
					}
				}
				else {
					clause = innerClause;
				}
			}

			Matcher matcher = _clausesPattern.matcher(clause);

			if (matcher.find()) {
				String field = matcher.group(
					1
				).trim();

				String operator = matcher.group(
					2
				).trim();

				String rawValue = matcher.group(
					3
				).trim();

				String valueString = rawValue.replaceAll("^'|'$", "");

				Object value;

				if (operator.equals("in")) {
					String innerIn = valueString.replaceAll("^\\(|\\)$", "");

					String[] parts = innerIn.split(",");

					List<String> valuesList = new ArrayList<>();

					for (String part : parts) {
						valuesList.add(part.trim());
					}

					value = valuesList;
				}
				else {
					value = valueString;
				}

				if (operator.equals("ge") || operator.equals("le")) {
					dateRangeClauses.computeIfAbsent(
						field, DateRangeClauseData::new
					).addClause(
						operator, (String)value
					);
				}
				else {
					SimpleClauseData data = new SimpleClauseData(
						field, operator, value);

					simpleClauses.put(field, data);
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

	private static class DateRangeClauseData {

		public DateRangeClauseData(String field) {
			_field = field;
		}

		public void addClause(String operator, String value) {
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

	private static class SimpleClauseData {

		public static SimpleClauseData createOrClause(
			String field, List<String> values) {

			List<String> cleanedValues = new ArrayList<>();

			for (String value : values) {
				cleanedValues.add(value.replaceAll("^'|'$", ""));
			}

			return new SimpleClauseData(field, "or", cleanedValues);
		}

		public SimpleClauseData(String field, String operator, Object value) {
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