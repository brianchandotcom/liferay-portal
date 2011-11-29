/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.generic;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseBooleanQueryImpl;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanClauseOccurImpl;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author Hugo Huijser
 */
public class BooleanQueryImpl extends BaseBooleanQueryImpl {

	public void add(Query query, BooleanClauseOccur booleanClauseOccur) {
		_clauses.add(new BooleanClauseImpl(query, booleanClauseOccur));
	}

	public void add(Query query, String occur) {
		BooleanClauseOccur booleanClauseOccur = new BooleanClauseOccurImpl(
			occur);

		add(query, booleanClauseOccur);
	}

	public void addExactTerm(String field, boolean value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Boolean value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, double value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Double value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, int value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Integer value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, long value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Long value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, short value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, Short value) {
		addExactTerm(field, String.valueOf(value));
	}

	public void addExactTerm(String field, String value) {
		TermQueryImpl termQuery = new TermQueryImpl(
			new QueryTermImpl(field, String.valueOf(value)));

		add(termQuery, BooleanClauseOccur.SHOULD);
	}

	public void addNumericRangeTerm(
		String field, int startValue, int endValue) {

		for (int i = startValue; i <= endValue; i++) {
			addExactTerm(field, i);
		}
	}

	public void addNumericRangeTerm(
		String field, Integer startValue, Integer endValue) {

		addNumericRangeTerm(field, startValue.intValue(), endValue.intValue());
	}

	public void addNumericRangeTerm(
		String field, long startValue, long endValue) {

		for (long i = startValue; i <= endValue; i++) {
			addExactTerm(field, i);
		}
	}

	public void addNumericRangeTerm(
		String field, Long startValue, Long endValue) {

		addNumericRangeTerm(
			field, startValue.longValue(), endValue.longValue());
	}

	public void addNumericRangeTerm(
		String field, short startValue, short endValue) {

		for (short i = startValue; i <= endValue; i++) {
			addExactTerm(field, i);
		}
	}

	public void addNumericRangeTerm(
		String field, Short startValue, Short endValue) {

		addNumericRangeTerm(
			field, startValue.shortValue(), endValue.shortValue());
	}

	public void addRangeTerm(String field, int startValue, int endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public void addRangeTerm(
		String field, Integer startValue, Integer endValue) {

		addRangeTerm(field, startValue.intValue(), endValue.intValue());
	}

	public void addRangeTerm(String field, long startValue, long endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public void addRangeTerm(String field, Long startValue, Long endValue) {
		addRangeTerm(field, startValue.longValue(), endValue.longValue());
	}

	public void addRangeTerm(String field, short startValue, short endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, String.valueOf(startValue), String.valueOf(endValue), true,
			true);

		add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public void addRangeTerm(String field, Short startValue, Short endValue) {
		addRangeTerm(field, startValue.shortValue(), endValue.shortValue());
	}

	public void addRangeTerm(String field, String startValue, String endValue) {
		TermRangeQuery termRangeQuery = new TermRangeQueryImpl(
			field, startValue, endValue, true, true);

		add(termRangeQuery, BooleanClauseOccur.SHOULD);
	}

	public void addRequiredTerm(String field, boolean value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Boolean value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, double value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Double value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, int value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Integer value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, long value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Long value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, short value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, Short value) {
		addRequiredTerm(field, String.valueOf(value), false);
	}

	public void addRequiredTerm(String field, String value) {
		addRequiredTerm(field, value, false);
	}

	public void addRequiredTerm(String field, String value, boolean like) {
		addRequiredTerm(field, value, like, false);
	}

	public void addRequiredTerm(
		String field, String value, boolean like, boolean parseKeywords) {

		if (like) {
			value = StringUtil.replace(
				value, StringPool.PERCENT, StringPool.BLANK);
		}

		String[] values = null;

		if (parseKeywords) {
			values = parseKeywords(value);
		}
		else {
			values = new String[] {value};
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		for (String curValue : values) {
			QueryTerm queryTerm = new QueryTermImpl(
				field, String.valueOf(curValue));

			Query query = null;

			if (like) {
				query = new WildcardQueryImpl(queryTerm);
			}
			else {
				query = new TermQueryImpl(queryTerm);
			}

			try {
				booleanQuery.add(query, BooleanClauseOccur.SHOULD);
			}
			catch (ParseException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug("ParseException thrown, skipping query", pe);
				}
			}
		}

		add(booleanQuery, BooleanClauseOccur.MUST);
	}

	public void addTerm(String field, long value) {
		addTerm(field, String.valueOf(value), false);
	}

	public void addTerm(String field, String value) {
		addTerm(field, value, false);
	}

	public void addTerm(String field, String value, boolean like) {
		Query query = null;

		if (like) {
			query = new WildcardQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}
		else {
			query = new TermQueryImpl(
				new QueryTermImpl(field, String.valueOf(value)));
		}

		add(query, BooleanClauseOccur.SHOULD);
	}

	public void addTerm(
		String field, String value, boolean like, boolean parseKeywords) {

		if (like) {
			value = StringUtil.replace(
				value, StringPool.PERCENT, StringPool.BLANK);
		}

		if (parseKeywords) {
			String[] keywords = parseKeywords(value);

			for (String keyword : keywords) {
				addTerm(field, keyword, like);
			}
		}
		else {
			addTerm(field, value, like);
		}
	}

	public List<BooleanClause> clauses() {
		return Collections.unmodifiableList(_clauses);
	}

	@Override
	public Object getWrappedQuery() {
		return this;
	}

	private static Log _log = LogFactoryUtil.getLog(BooleanQueryImpl.class);

	private List<BooleanClause> _clauses = new ArrayList<BooleanClause>();

}