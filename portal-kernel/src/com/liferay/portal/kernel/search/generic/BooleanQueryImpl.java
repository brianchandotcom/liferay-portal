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

package com.liferay.portal.kernel.search.generic;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.BaseBooleanQueryImpl;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanClauseOccurImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.query.FieldQueryFactoryUtil;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author Hugo Huijser
 */
public class BooleanQueryImpl extends BaseBooleanQueryImpl {

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	@Override
	public Query add(Query query, BooleanClauseOccur booleanClauseOccur) {
		if (query == null) {
			throw new IllegalArgumentException("Query is null");
		}

		_booleanClauses.add(new BooleanClauseImpl<>(query, booleanClauseOccur));

		return query;
	}

	@Override
	public Query add(Query query, String occur) {
		BooleanClauseOccur booleanClauseOccur = new BooleanClauseOccurImpl(
			occur);

		add(query, booleanClauseOccur);

		return query;
	}

	@Override
	public Query addExactTerm(String field, boolean value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, Boolean value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, double value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, Double value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, int value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, Integer value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, long value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, Long value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, short value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, Short value) {
		return addExactTerm(field, String.valueOf(value));
	}

	@Override
	public Query addExactTerm(String field, String value) {
		TermQueryImpl termQueryImpl = new TermQueryImpl(
			new QueryTermImpl(field, String.valueOf(value)));

		return add(termQueryImpl, BooleanClauseOccur.SHOULD);
	}

	@Override
	public Collection<Query> addNumericRangeTerm(
		String field, int startValue, int endValue) {

		List<Query> queries = new ArrayList<>();

		for (int i = startValue; i <= endValue; i++) {
			Query query = addExactTerm(field, i);

			queries.add(query);
		}

		return queries;
	}

	@Override
	public Collection<Query> addNumericRangeTerm(
		String field, Integer startValue, Integer endValue) {

		return addNumericRangeTerm(
			field, startValue.intValue(), endValue.intValue());
	}

	@Override
	public Collection<Query> addNumericRangeTerm(
		String field, long startValue, long endValue) {

		List<Query> queries = new ArrayList<>();

		for (long i = startValue; i <= endValue; i++) {
			Query query = addExactTerm(field, i);

			queries.add(query);
		}

		return queries;
	}

	@Override
	public Collection<Query> addNumericRangeTerm(
		String field, Long startValue, Long endValue) {

		return addNumericRangeTerm(
			field, startValue.longValue(), endValue.longValue());
	}

	@Override
	public Collection<Query> addNumericRangeTerm(
		String field, short startValue, short endValue) {

		List<Query> queries = new ArrayList<>();

		for (short i = startValue; i <= endValue; i++) {
			Query query = addExactTerm(field, i);

			queries.add(query);
		}

		return queries;
	}

	@Override
	public Collection<Query> addNumericRangeTerm(
		String field, Short startValue, Short endValue) {

		return addNumericRangeTerm(
			field, startValue.shortValue(), endValue.shortValue());
	}

	@Override
	public Query addRangeTerm(String field, int startValue, int endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	@Override
	public Query addRangeTerm(
		String field, Integer startValue, Integer endValue) {

		return addRangeTerm(field, startValue.intValue(), endValue.intValue());
	}

	@Override
	public Query addRangeTerm(String field, long startValue, long endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	@Override
	public Query addRangeTerm(String field, Long startValue, Long endValue) {
		return addRangeTerm(
			field, startValue.longValue(), endValue.longValue());
	}

	@Override
	public Query addRangeTerm(String field, short startValue, short endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	@Override
	public Query addRangeTerm(String field, Short startValue, Short endValue) {
		return addRangeTerm(
			field, startValue.shortValue(), endValue.shortValue());
	}

	@Override
	public Query addRangeTerm(
		String field, String startValue, String endValue) {

		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, startValue, endValue, true, true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	@Override
	public Query addRequiredTerm(String field, boolean value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, Boolean value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, double value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, Double value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, int value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, Integer value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, long value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, Long value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, short value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, Short value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addRequiredTerm(String field, String value) {
		return addRequiredTerm(field, value, false);
	}

	@Override
	public Query addRequiredTerm(String field, String value, boolean like) {
		return addRequiredTerm(field, value, like, false);
	}

	public Query addRequiredTerm(
		String field, String value, boolean like, boolean parseKeywords) {

		Query query = FieldQueryFactoryUtil.createQuery(
			field, value, like, parseKeywords);

		return add(query, BooleanClauseOccur.MUST);
	}

	@Override
	public Query addTerm(String field, long value) {
		return addTerm(field, String.valueOf(value), false);
	}

	@Override
	public Query addTerm(String field, String value) {
		return addTerm(field, value, false);
	}

	@Override
	public Query addTerm(String field, String value, boolean like) {
		return addTerm(field, value, like, BooleanClauseOccur.SHOULD);
	}

	public Query addTerm(
		String field, String value, boolean like, boolean parseKeywords) {

		Query query = FieldQueryFactoryUtil.createQuery(
			field, value, like, parseKeywords);

		return add(query, BooleanClauseOccur.SHOULD);
	}

	@Override
	public Query addTerm(
		String field, String value, boolean like,
		BooleanClauseOccur booleanClauseOccur) {

		Query query = FieldQueryFactoryUtil.createQuery(
			field, value, like, false);

		return add(query, booleanClauseOccur);
	}

	@Override
	public List<BooleanClause<Query>> clauses() {
		return Collections.unmodifiableList(_booleanClauses);
	}

	@Override
	public boolean hasChildren() {
		return hasClauses();
	}

	@Override
	public boolean hasClauses() {
		return !_booleanClauses.isEmpty();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{booleanClauses=");
		sb.append(_booleanClauses);
		sb.append(", className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append("}");

		return sb.toString();
	}

	private final List<BooleanClause<Query>> _booleanClauses =
		new ArrayList<>();

}