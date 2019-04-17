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

package com.liferay.portal.search.internal.filter;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.ComplexQueryBuilder;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.internal.util.SearchStringUtil;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.SimpleStringQuery;
import com.liferay.portal.search.query.StringQuery;
import com.liferay.portal.search.script.Scripts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author André de Oliveira
 */
public class ComplexQueryBuilderImpl implements ComplexQueryBuilder {

	public ComplexQueryBuilderImpl(Queries queries, Scripts scripts) {
		_queries = queries;
		_scripts = scripts;
	}

	@Override
	public ComplexQueryBuilder addParts(
		Collection<ComplexQueryPart> complexQueryParts) {

		_complexQueryParts.addAll(complexQueryParts);

		return this;
	}

	@Override
	public Query build() {
		Map<String, ComplexQueryPart> complexQueryPartsMap =
			_complexQueryParts.stream(
			).filter(
				filterQueryDefinition -> !Validator.isBlank(
					filterQueryDefinition.getName())
			).collect(
				Collectors.toMap(ComplexQueryPart::getName, Function.identity())
			);

		Build build = new Build(complexQueryPartsMap);

		return build.build();
	}

	@Override
	public ComplexQueryBuilder root(BooleanQuery booleanQuery) {
		_booleanQuery = booleanQuery;

		return this;
	}

	private BooleanQuery _booleanQuery;
	private final List<ComplexQueryPart> _complexQueryParts = new ArrayList<>();
	private final Queries _queries;
	private final Scripts _scripts;

	private class Build {

		public Build(Map<String, ComplexQueryPart> complexQueryPartsMap) {
			_complexQueryPartsMap = complexQueryPartsMap;
		}

		public Query build() {
			_complexQueryParts.forEach(this::hydrate);

			return getRootBooleanQuery();
		}

		protected Query addQuery(ComplexQueryPart complexQueryPart) {
			Query query = buildQuery(complexQueryPart);

			if (query == null) {
				return null;
			}

			addQueryClause(
				getParentBooleanQuery(complexQueryPart.getParent()),
				complexQueryPart.getOccur(), query);

			return query;
		}

		protected void addQueryClause(
			BooleanQuery booleanQuery, String occur, Query query) {

			if (Validator.isBlank(occur) || occur.equals("filter")) {
				booleanQuery.addFilterQueryClauses(query);
			}
			else if (Objects.equals("must", occur)) {
				booleanQuery.addMustQueryClauses(query);
			}
			else if (Objects.equals("must_not", occur)) {
				booleanQuery.addMustNotQueryClauses(query);
			}
			else if (Objects.equals("should", occur)) {
				booleanQuery.addShouldQueryClauses(query);
			}
		}

		protected Query buildQuery(ComplexQueryPart complexQueryPart) {
			if (complexQueryPart.isDisabled()) {
				return null;
			}

			String field = GetterUtil.getString(complexQueryPart.getField());
			String type = GetterUtil.getString(complexQueryPart.getType());
			String value = GetterUtil.getString(complexQueryPart.getValue());

			Query query = buildQuery(type, field, value);

			if (query == null) {
				return null;
			}

			query.setBoost(complexQueryPart.getBoost());
			query.setQueryName(complexQueryPart.getName());

			return query;
		}

		protected Query buildQuery(String type, String field, String value) {
			if ("bool".equals(type)) {
				return _queries.booleanQuery();
			}

			if ("exists".equals(type)) {
				return _queries.exists(field);
			}

			if ("fuzzy".equals(type)) {
				return _queries.fuzzy(field, value);
			}

			if ("match".equals(type)) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return _queries.match(field, value);
			}

			if ("match_phrase".equals(type)) {
				return _queries.matchPhrase(field, value);
			}

			if ("match_phrase_prefix".equals(type)) {
				return _queries.matchPhrasePrefix(field, value);
			}

			if ("multi_match".equals(type)) {
				if (Validator.isBlank(value)) {
					return null;
				}

				return _queries.multiMatch(
					value, SearchStringUtil.splitAndUnquote(field));
			}

			if ("prefix".equals(type)) {
				return _queries.prefix(field, value);
			}

			if ("query_string".equals(type)) {
				StringQuery stringQuery = _queries.string(value);

				if (!Validator.isBlank(field)) {
					stringQuery.setDefaultField(field);
				}

				return stringQuery;
			}

			if ("regexp".equals(type)) {
				return _queries.regex(field, value);
			}

			if ("script".equals(type)) {
				return _queries.script(_scripts.script(value));
			}

			if ("simple_query_string".equals(type)) {
				SimpleStringQuery simpleStringQuery = _queries.simpleString(
					value);

				if (!Validator.isBlank(field)) {
					simpleStringQuery.addFields(field);
				}

				return simpleStringQuery;
			}

			if ("term".equals(type)) {
				return _queries.term(field, value);
			}

			if ("wildcard".equals(type)) {
				return _queries.wildcard(field, value);
			}

			return null;
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

					if (query instanceof BooleanQuery) {
						return (BooleanQuery)query;
					}
				}
			}

			return getRootBooleanQuery();
		}

		protected BooleanQuery getRootBooleanQuery() {
			return _booleanQuery;
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

	}

}