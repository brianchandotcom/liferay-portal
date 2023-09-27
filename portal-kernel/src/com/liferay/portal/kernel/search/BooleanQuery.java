/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public interface BooleanQuery extends Query {

	public Query add(Query query, BooleanClauseOccur booleanClauseOccur)
		throws ParseException;

	public Query add(Query query, String occur) throws ParseException;

	public Query addExactTerm(String field, boolean value);

	public Query addExactTerm(String field, Boolean value);

	public Query addExactTerm(String field, double value);

	public Query addExactTerm(String field, Double value);

	public Query addExactTerm(String field, int value);

	public Query addExactTerm(String field, Integer value);

	public Query addExactTerm(String field, long value);

	public Query addExactTerm(String field, Long value);

	public Query addExactTerm(String field, short value);

	public Query addExactTerm(String field, Short value);

	public Query addExactTerm(String field, String value);

	public Collection<Query> addNumericRangeTerm(
		String field, int startValue, int endValue);

	public Collection<Query> addNumericRangeTerm(
		String field, Integer startValue, Integer endValue);

	public Collection<Query> addNumericRangeTerm(
		String field, long startValue, long endValue);

	public Collection<Query> addNumericRangeTerm(
		String field, Long startValue, Long endValue);

	public Collection<Query> addNumericRangeTerm(
		String field, short startValue, short endValue);

	public Collection<Query> addNumericRangeTerm(
		String field, Short startValue, Short endValue);

	public Query addRangeTerm(String field, int startValue, int endValue);

	public Query addRangeTerm(
		String field, Integer startValue, Integer endValue);

	public Query addRangeTerm(String field, long startValue, long endValue);

	public Query addRangeTerm(String field, Long startValue, Long endValue);

	public Query addRangeTerm(String field, short startValue, short endValue);

	public Query addRangeTerm(String field, Short startValue, Short endValue);

	public Query addRangeTerm(String field, String startValue, String endValue);

	public Query addRequiredTerm(String field, boolean value);

	public Query addRequiredTerm(String field, Boolean value);

	public Query addRequiredTerm(String field, double value);

	public Query addRequiredTerm(String field, Double value);

	public Query addRequiredTerm(String field, int value);

	public Query addRequiredTerm(String field, Integer value);

	public Query addRequiredTerm(String field, long value);

	public Query addRequiredTerm(String field, Long value);

	public Query addRequiredTerm(String field, short value);

	public Query addRequiredTerm(String field, Short value);

	public Query addRequiredTerm(String field, String value);

	public Query addRequiredTerm(String field, String value, boolean like);

	public Query addTerm(String field, long value) throws ParseException;

	public Query addTerm(String field, String value) throws ParseException;

	public Query addTerm(String field, String value, boolean like)
		throws ParseException;

	public Query addTerm(
			String field, String value, boolean like,
			BooleanClauseOccur booleanClauseOccur)
		throws ParseException;

	public Map<String, Query> addTerms(String[] fields, String values)
		throws ParseException;

	public Map<String, Query> addTerms(
			String[] fields, String value, boolean like)
		throws ParseException;

	public List<BooleanClause<Query>> clauses();

	public boolean hasClauses();

}