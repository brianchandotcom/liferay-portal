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

package com.liferay.portal.kernel.search.filter;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Comparator;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class BooleanFilter extends BaseFilter {

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public Filter add(Filter filter) {
		return add(filter, BooleanClauseOccur.SHOULD);
	}

	public Filter add(Filter filter, BooleanClauseOccur booleanClauseOccur) {
		BooleanClause<Filter> booleanClause = new BooleanClauseImpl<>(
			filter, booleanClauseOccur);

		if (booleanClauseOccur.equals(BooleanClauseOccur.MUST)) {
			_mustBooleanClauses.add(booleanClause);
		}
		else if (booleanClauseOccur.equals(BooleanClauseOccur.MUST_NOT)) {
			_mustNotBooleanClauses.add(booleanClause);
		}
		else {
			_shouldBooleanClauses.add(booleanClause);
		}

		return filter;
	}

	public Filter addRangeTerm(String field, int startValue, int endValue) {
		RangeTermFilter rangeTermFilter = new RangeTermFilter(
			field, true, true, String.valueOf(startValue),
			String.valueOf(endValue));

		return add(rangeTermFilter, BooleanClauseOccur.SHOULD);
	}

	public Filter addRangeTerm(
		String field, Integer startValue, Integer endValue) {

		return addRangeTerm(field, startValue.intValue(), endValue.intValue());
	}

	public Filter addRangeTerm(String field, long startValue, long endValue) {
		RangeTermFilter rangeTermFilter = new RangeTermFilter(
			field, true, true, String.valueOf(startValue),
			String.valueOf(endValue));

		return add(rangeTermFilter, BooleanClauseOccur.SHOULD);
	}

	public Filter addRangeTerm(String field, Long startValue, Long endValue) {
		return addRangeTerm(
			field, startValue.longValue(), endValue.longValue());
	}

	public Filter addRangeTerm(String field, short startValue, short endValue) {
		RangeTermFilter rangeTermFilter = new RangeTermFilter(
			field, true, true, String.valueOf(startValue),
			String.valueOf(endValue));

		return add(rangeTermFilter, BooleanClauseOccur.SHOULD);
	}

	public Filter addRangeTerm(String field, Short startValue, Short endValue) {
		return addRangeTerm(
			field, startValue.shortValue(), endValue.shortValue());
	}

	public Filter addRangeTerm(
		String field, String startValue, String endValue) {

		RangeTermFilter rangeTermFilter = new RangeTermFilter(
			field, true, true, startValue, endValue);

		return add(rangeTermFilter, BooleanClauseOccur.SHOULD);
	}

	public Filter addRequiredTerm(String field, boolean value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, Boolean value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, double value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, Double value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, int value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, Integer value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, long value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, Long value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, short value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, Short value) {
		return addRequiredTerm(field, String.valueOf(value));
	}

	public Filter addRequiredTerm(String field, String value) {
		TermFilter termFilter = new TermFilter(field, value);

		return add(termFilter, BooleanClauseOccur.MUST);
	}

	public Filter addTerm(String field, boolean value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, Boolean value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, double value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, Double value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, int value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, Integer value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, long value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, Long value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, short value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, Short value) {
		return addTerm(field, String.valueOf(value));
	}

	public Filter addTerm(String field, String value) {
		return addTerm(field, value, BooleanClauseOccur.SHOULD);
	}

	public Filter addTerm(
		String field, String value, BooleanClauseOccur booleanClauseOccur) {

		TermFilter termFilter = new TermFilter(field, value);

		return add(termFilter, booleanClauseOccur);
	}

	public List<BooleanClause<Filter>> getMustBooleanClauses() {
		return _mustBooleanClauses;
	}

	public List<BooleanClause<Filter>> getMustNotBooleanClauses() {
		return _mustNotBooleanClauses;
	}

	public List<BooleanClause<Filter>> getShouldBooleanClauses() {
		return _shouldBooleanClauses;
	}

	@Override
	public int getSortOrder() {
		return 10;
	}

	public boolean hasClauses() {
		if (!_mustBooleanClauses.isEmpty() ||
			!_mustNotBooleanClauses.isEmpty() ||
			!_shouldBooleanClauses.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{MUST(");
		sb.append(getBooleanClauseString(_mustBooleanClauses));
		sb.append("), MUST_NOT(");
		sb.append(getBooleanClauseString(_mustNotBooleanClauses));
		sb.append("), SHOULD(");
		sb.append(getBooleanClauseString(_shouldBooleanClauses));
		sb.append("), ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	protected String getBooleanClauseString(
		List<BooleanClause<Filter>> booleanClauses) {

		StringBundler sb = new StringBundler(2 * booleanClauses.size());

		for (BooleanClause<Filter> booleanClause : _mustBooleanClauses) {
			sb.append(booleanClause);

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		return sb.toString();
	}

	private final List<BooleanClause<Filter>> _mustBooleanClauses =
		new SortedArrayList<>(new BooleanClauseComparator());
	private final List<BooleanClause<Filter>> _mustNotBooleanClauses =
		new SortedArrayList<>(new BooleanClauseComparator());
	private final List<BooleanClause<Filter>> _shouldBooleanClauses =
		new SortedArrayList<>(new BooleanClauseComparator());

	private static class BooleanClauseComparator
		implements Comparator<BooleanClause<Filter>> {

		@Override
		public int compare(
			BooleanClause<Filter> booleanClause1,
			BooleanClause<Filter> booleanClause2) {

			Filter filter1 = booleanClause1.getClause();
			Filter filter2 = booleanClause2.getClause();

			if (filter1.getSortOrder() < filter2.getSortOrder()) {
				return -1;
			}

			if (filter1.getSortOrder() > filter2.getSortOrder()) {
				return 1;
			}

			return 0;
		}

	}

}