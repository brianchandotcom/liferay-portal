/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.query.FieldQueryFactoryUtil;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Michael C. Han
 */
public class BooleanQuery extends BaseQueryImpl {

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public Query add(Query query, BooleanClauseOccur booleanClauseOccur) {
		if (query == null) {
			throw new IllegalArgumentException("Query is null");
		}

		_booleanClauses.add(new BooleanClause<>(query, booleanClauseOccur));

		return query;
	}

	public Query addExactTerm(String field, boolean value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, Boolean value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, double value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, Double value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, int value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, Integer value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, long value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, Long value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, short value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, Short value) {
		return addExactTerm(field, String.valueOf(value));
	}

	public Query addExactTerm(String field, String value) {
		TermQuery termQuery = new TermQuery(
			new QueryTerm(field, String.valueOf(value)));

		return add(termQuery, BooleanClauseOccur.SHOULD);
	}

	public Collection<Query> addNumericRangeTerm(
		String field, int startValue, int endValue) {

		List<Query> queries = new ArrayList<>();

		for (int i = startValue; i <= endValue; i++) {
			Query query = addExactTerm(field, i);

			queries.add(query);
		}

		return queries;
	}

	public Collection<Query> addNumericRangeTerm(
		String field, Integer startValue, Integer endValue) {

		return addNumericRangeTerm(
			field, startValue.intValue(), endValue.intValue());
	}

	public Collection<Query> addNumericRangeTerm(
		String field, long startValue, long endValue) {

		List<Query> queries = new ArrayList<>();

		for (long i = startValue; i <= endValue; i++) {
			Query query = addExactTerm(field, i);

			queries.add(query);
		}

		return queries;
	}

	public Collection<Query> addNumericRangeTerm(
		String field, Long startValue, Long endValue) {

		return addNumericRangeTerm(
			field, startValue.longValue(), endValue.longValue());
	}

	public Collection<Query> addNumericRangeTerm(
		String field, short startValue, short endValue) {

		List<Query> queries = new ArrayList<>();

		for (short i = startValue; i <= endValue; i++) {
			Query query = addExactTerm(field, i);

			queries.add(query);
		}

		return queries;
	}

	public Collection<Query> addNumericRangeTerm(
		String field, Short startValue, Short endValue) {

		return addNumericRangeTerm(
			field, startValue.shortValue(), endValue.shortValue());
	}

	public Query addRangeTerm(String field, int startValue, int endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQuery(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public Query addRangeTerm(
		String field, Integer startValue, Integer endValue) {

		return addRangeTerm(field, startValue.intValue(), endValue.intValue());
	}

	public Query addRangeTerm(String field, long startValue, long endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQuery(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public Query addRangeTerm(String field, Long startValue, Long endValue) {
		return addRangeTerm(
			field, startValue.longValue(), endValue.longValue());
	}

	public Query addRangeTerm(String field, short startValue, short endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQuery(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public Query addRangeTerm(String field, Short startValue, Short endValue) {
		return addRangeTerm(
			field, startValue.shortValue(), endValue.shortValue());
	}

	public Query addRangeTerm(
		String field, String startValue, String endValue) {

		TermRangeQuery termRangeQuery = new TermRangeQuery(
			field, startValue, endValue, true, true);

		return add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public Query addRequiredTerm(String field, boolean value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, Boolean value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, double value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, Double value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, int value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, Integer value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, long value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, Long value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, short value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, Short value) {
		return addRequiredTerm(field, String.valueOf(value), false);
	}

	public Query addRequiredTerm(String field, String value) {
		return addRequiredTerm(field, value, false);
	}

	public Query addRequiredTerm(String field, String value, boolean like) {
		return addRequiredTerm(field, value, like, false);
	}

	public Query addRequiredTerm(
		String field, String value, boolean like, boolean parseKeywords) {

		Query query = FieldQueryFactoryUtil.createQuery(
			field, value, like, parseKeywords);

		return add(query, BooleanClauseOccur.MUST);
	}

	public Query addTerm(String field, long value) throws ParseException {
		return addTerm(field, String.valueOf(value), false);
	}

	public Query addTerm(String field, String value) throws ParseException {
		return addTerm(field, value, false);
	}

	public Query addTerm(String field, String value, boolean like)
		throws ParseException {

		return addTerm(field, value, like, BooleanClauseOccur.SHOULD);
	}

	public Query addTerm(
		String field, String value, boolean like, boolean parseKeywords) {

		Query query = FieldQueryFactoryUtil.createQuery(
			field, value, like, parseKeywords);

		return add(query, BooleanClauseOccur.SHOULD);
	}

	public Query addTerm(
			String field, String value, boolean like,
			BooleanClauseOccur booleanClauseOccur)
		throws ParseException {

		Query query = FieldQueryFactoryUtil.createQuery(
			field, value, like, false);

		return add(query, booleanClauseOccur);
	}

	public Map<String, Query> addTerms(String[] fields, String values)
		throws ParseException {

		if (Validator.isNull(values)) {
			return Collections.emptyMap();
		}

		if (fields == null) {
			fields = new String[0];
		}

		Map<String, Query> queries = new HashMap<>((int)(fields.length / .75));

		for (String field : fields) {
			Query query = addTerm(field, values);

			queries.put(field, query);
		}

		return queries;
	}

	public Map<String, Query> addTerms(
			String[] fields, String value, boolean like)
		throws ParseException {

		if (Validator.isNull(value)) {
			return Collections.emptyMap();
		}

		Map<String, Query> queries = new HashMap<>((int)(fields.length / .75));

		for (String field : fields) {
			Query query = addTerm(field, value, like);

			queries.put(field, query);
		}

		return queries;
	}

	public List<BooleanClause<Query>> clauses() {
		return Collections.unmodifiableList(_booleanClauses);
	}

	@Override
	public boolean hasChildren() {
		return hasClauses();
	}

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

	protected Map<String, List<Query>> addTerms(
			String[] fields, Map<String, List<String>> termFieldsValuesMap)
		throws ParseException {

		Map<String, List<Query>> queries = new HashMap<>(
			(int)(fields.length / .75));

		for (String field : fields) {
			List<String> valuesList = termFieldsValuesMap.get(field);

			List<Query> queriesList = new ArrayList<>(valuesList.size());

			queries.put(field, queriesList);

			for (String value : valuesList) {
				Query query = addTerm(field, value);

				queriesList.add(query);
			}
		}

		return queries;
	}

	protected String[] parseKeywords(String values) {
		if (!values.contains(StringPool.QUOTE)) {
			return StringUtil.split(values, CharPool.SPACE);
		}

		List<String> keywords = new ArrayList<>();

		while (values.length() > 0) {
			if (values.startsWith(StringPool.QUOTE)) {
				values = values.substring(1);

				if (values.contains(StringPool.QUOTE)) {
					int pos = values.indexOf(StringPool.QUOTE);

					keywords.add(values.substring(0, pos));

					values = values.substring(pos + 1);

					values = values.trim();
				}
			}
			else {
				if (values.contains(StringPool.SPACE)) {
					int pos = values.indexOf(StringPool.SPACE);

					keywords.add(values.substring(0, pos));

					values = values.substring(pos + 1);

					values = values.trim();
				}
				else {
					keywords.add(values);

					break;
				}
			}
		}

		return keywords.toArray(new String[0]);
	}

	private final List<BooleanClause<Query>> _booleanClauses =
		new ArrayList<>();

}