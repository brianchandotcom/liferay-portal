/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.range.RangeTermQueryValue;
import com.liferay.portal.search.filter.range.RangeTermQueryValueParser;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.NestedQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.SimpleStringQuery;
import com.liferay.portal.search.query.StringQuery;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.util.SearchStringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class ComplexQueryBuilder {

	public ComplexQueryBuilder addParts(
		Collection<ComplexQueryPart> complexQueryParts) {

		_complexQueryParts.addAll(complexQueryParts);

		return this;
	}

	public Query build() {
		Map<String, ComplexQueryPart> complexQueryPartsMap = new HashMap<>();

		for (ComplexQueryPart complexQueryPart : _complexQueryParts) {
			if (Validator.isBlank(complexQueryPart.getName())) {
				continue;
			}

			complexQueryPartsMap.put(
				complexQueryPart.getName(), complexQueryPart);
		}

		Build build = new Build(complexQueryPartsMap, _getRootBooleanQuery());

		return build.build();
	}

	public Query buildPart(ComplexQueryPart complexQueryPart) {
		Build build = new Build(null, null);

		return build.getQuery(complexQueryPart);
	}

	public ComplexQueryBuilder root(BooleanQuery booleanQuery) {
		_booleanQuery = booleanQuery;

		return this;
	}

	private BooleanQuery _getRootBooleanQuery() {
		if (_booleanQuery != null) {
			return _booleanQuery;
		}

		return QueriesUtil.booleanQuery();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ComplexQueryBuilder.class);

	private BooleanQuery _booleanQuery;
	private final List<ComplexQueryPart> _complexQueryParts = new ArrayList<>();

	private class Build {

		public Build(
			Map<String, ComplexQueryPart> complexQueryPartsMap,
			BooleanQuery rootBooleanQuery) {

			_complexQueryPartsMap = complexQueryPartsMap;
			_rootBooleanQuery = rootBooleanQuery;
		}

		public Query build() {
			_complexQueryParts.forEach(this::hydrate);

			return getRootBooleanQuery();
		}

		protected Query addQuery(ComplexQueryPart complexQueryPart) {
			Query query = getQuery(complexQueryPart);

			if (query == null) {
				return null;
			}

			String occur = complexQueryPart.getOccur();

			if (complexQueryPart.isAdditive() && !occur.equals("should")) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Additive complex query part with " +
							complexQueryPart.getOccur() +
								" occur may not behave as expected");
				}
			}

			addQueryClause(
				getParentBooleanQuery(complexQueryPart.getParent()), occur,
				query);

			return query;
		}

		protected void addQueryClause(
			BooleanQuery booleanQuery, String occur, Query query) {

			if (Validator.isBlank(occur) || occur.equals("filter")) {
				booleanQuery.addFilterQueryClauses(query);
			}
			else if (Objects.equals(occur, "must")) {
				booleanQuery.addMustQueryClauses(query);
			}
			else if (Objects.equals(occur, "must_not")) {
				booleanQuery.addMustNotQueryClauses(query);
			}
			else if (Objects.equals(occur, "should")) {
				booleanQuery.addShouldQueryClauses(query);
			}
		}

		protected Query buildQuery(String type, String field, String value) {
			if (Objects.equals(type, "bool")) {
				return QueriesUtil.booleanQuery();
			}

			if (Objects.equals(type, "date_range")) {
				RangeTermQueryValue rangeTermQueryValue =
					_rangeTermQueryParser.parse(value);

				if (rangeTermQueryValue == null) {
					return null;
				}

				return QueriesUtil.dateRangeTerm(
					field, rangeTermQueryValue.isIncludesLower(),
					rangeTermQueryValue.isIncludesUpper(),
					rangeTermQueryValue.getLowerBound(),
					rangeTermQueryValue.getUpperBound());
			}

			if (Objects.equals(type, "exists")) {
				return QueriesUtil.exists(field);
			}

			if (Objects.equals(type, "fuzzy")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.fuzzy(field, value);
			}

			if (Objects.equals(type, "match")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.match(field, value);
			}

			if (Objects.equals(type, "match_phrase")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.matchPhrase(field, value);
			}

			if (Objects.equals(type, "match_phrase_prefix")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.matchPhrasePrefix(field, value);
			}

			if (Objects.equals(type, "multi_match")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.multiMatch(
					value, SearchStringUtil.splitAndUnquote(field));
			}

			if (Objects.equals(type, "nested")) {
				return QueriesUtil.nested(field, QueriesUtil.booleanQuery());
			}

			if (Objects.equals(type, "prefix")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.prefix(field, value);
			}

			if (Objects.equals(type, "query_string")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				StringQuery stringQuery = QueriesUtil.string(value);

				if (!Validator.isBlank(field)) {
					stringQuery.setDefaultField(field);
				}

				return stringQuery;
			}

			if (Objects.equals(type, "range")) {
				RangeTermQueryValue rangeTermQueryValue =
					_rangeTermQueryParser.parse(value);

				if (rangeTermQueryValue == null) {
					return null;
				}

				return QueriesUtil.rangeTerm(
					field, rangeTermQueryValue.isIncludesLower(),
					rangeTermQueryValue.isIncludesUpper(),
					rangeTermQueryValue.getLowerBound(),
					rangeTermQueryValue.getUpperBound());
			}

			if (Objects.equals(type, "regexp")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.regex(field, value);
			}

			if (Objects.equals(type, "script")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.script(Scripts.INSTANCE.script(value));
			}

			if (Objects.equals(type, "simple_query_string")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				SimpleStringQuery simpleStringQuery = QueriesUtil.simpleString(
					value);

				if (!Validator.isBlank(field)) {
					simpleStringQuery.addFields(field);
				}

				return simpleStringQuery;
			}

			if (Objects.equals(type, "term")) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return QueriesUtil.term(field, value);
			}

			if (!Objects.equals(type, "wildcard") || Validator.isBlank(value)) {
				return null;
			}

			return QueriesUtil.wildcard(field, value);
		}

		protected Query getNamedQuery(String name) {
			return _queriesMap.get(name);
		}

		protected BooleanQuery getParentBooleanQuery(String parent) {
			if (!Validator.isBlank(parent)) {
				ComplexQueryPart complexQueryPart = _complexQueryPartsMap.get(
					parent);

				if (complexQueryPart != null) {
					Query query = hydrate(complexQueryPart);

					if (query instanceof NestedQuery) {
						NestedQuery nestedQuery = (NestedQuery)query;

						query = nestedQuery.getQuery();
					}

					if (query instanceof BooleanQuery) {
						return (BooleanQuery)query;
					}
				}
			}

			return getRootBooleanQuery();
		}

		protected Query getQuery(ComplexQueryPart complexQueryPart) {
			if (complexQueryPart.isDisabled()) {
				return null;
			}

			if (complexQueryPart.getQuery() != null) {
				return complexQueryPart.getQuery();
			}

			String type = GetterUtil.getString(complexQueryPart.getType());

			String field = GetterUtil.getString(complexQueryPart.getField());

			String value = GetterUtil.getString(complexQueryPart.getValue());

			Query query = buildQuery(type, field, value);

			if (query == null) {
				return null;
			}

			query.setBoost(complexQueryPart.getBoost());
			query.setQueryName(complexQueryPart.getName());

			return query;
		}

		protected BooleanQuery getRootBooleanQuery() {
			return _rootBooleanQuery;
		}

		protected Query hydrate(ComplexQueryPart complexQueryPart) {
			Query query = getNamedQuery(complexQueryPart.getName());

			if (query != null) {
				return query;
			}

			return putNamedQuery(
				complexQueryPart.getName(), addQuery(complexQueryPart));
		}

		protected Query putNamedQuery(String name, Query query) {
			if (Validator.isBlank(name)) {
				return query;
			}

			_queriesMap.put(name, query);

			return query;
		}

		private final Map<String, ComplexQueryPart> _complexQueryPartsMap;
		private final Map<String, Query> _queriesMap = new HashMap<>();
		private final RangeTermQueryValueParser _rangeTermQueryParser =
			new RangeTermQueryValueParser();
		private final BooleanQuery _rootBooleanQuery;

	}

}