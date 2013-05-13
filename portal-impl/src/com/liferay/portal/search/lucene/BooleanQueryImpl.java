/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.BaseBooleanQueryImpl;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanClauseOccurImpl;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTranslatorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class BooleanQueryImpl extends BaseBooleanQueryImpl {

	public BooleanQueryImpl() {
		_booleanQuery = new org.apache.lucene.search.BooleanQuery();
	}

	@Override
	public void add(Query query, BooleanClauseOccur booleanClauseOccur)
		throws ParseException {

		_booleanQuery.add(
			(org.apache.lucene.search.Query)QueryTranslatorUtil.translate(
				query),
			BooleanClauseOccurTranslator.translate(booleanClauseOccur));
	}

	@Override
	public void add(Query query, String occur) throws ParseException {
		BooleanClauseOccur booleanClauseOccur = new BooleanClauseOccurImpl(
			occur);

		add(query, booleanClauseOccur);
	}

	@Override
	public void addExactTerm(String field, boolean value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, Boolean value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, double value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, Double value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, int value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, Integer value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, long value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, Long value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, short value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, Short value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addExactTerm(String field, String value) {
		LuceneHelperUtil.addExactTerm(_booleanQuery, field, value);
	}

	@Override
	public void addNumericRangeTerm(
		String field, int startValue, int endValue) {

		LuceneHelperUtil.addNumericRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addNumericRangeTerm(
		String field, Integer startValue, Integer endValue) {

		LuceneHelperUtil.addNumericRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addNumericRangeTerm(
		String field, long startValue, long endValue) {

		LuceneHelperUtil.addNumericRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addNumericRangeTerm(
		String field, Long startValue, Long endValue) {

		LuceneHelperUtil.addNumericRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addNumericRangeTerm(
		String field, short startValue, short endValue) {

		LuceneHelperUtil.addNumericRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addNumericRangeTerm(
		String field, Short startValue, Short endValue) {

		LuceneHelperUtil.addNumericRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRangeTerm(String field, int startValue, int endValue) {
		LuceneHelperUtil.addRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRangeTerm(
		String field, Integer startValue, Integer endValue) {

		LuceneHelperUtil.addRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRangeTerm(String field, long startValue, long endValue) {
		LuceneHelperUtil.addRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRangeTerm(String field, Long startValue, Long endValue) {
		LuceneHelperUtil.addRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRangeTerm(String field, short startValue, short endValue) {
		LuceneHelperUtil.addRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRangeTerm(String field, Short startValue, Short endValue) {
		LuceneHelperUtil.addRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRangeTerm(String field, String startValue, String endValue) {
		LuceneHelperUtil.addRangeTerm(
			_booleanQuery, field, startValue, endValue);
	}

	@Override
	public void addRequiredTerm(String field, boolean value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, Boolean value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, double value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, Double value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, int value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, Integer value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, long value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, Long value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, short value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, Short value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, String value) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value);
	}

	@Override
	public void addRequiredTerm(String field, String value, boolean like) {
		LuceneHelperUtil.addRequiredTerm(_booleanQuery, field, value, like);
	}

	@Override
	public void addTerm(String field, long value) {
		LuceneHelperUtil.addTerm(_booleanQuery, field, value);
	}

	@Override
	public void addTerm(String field, String value) {
		LuceneHelperUtil.addTerm(_booleanQuery, field, value);
	}

	@Override
	public void addTerm(String field, String value, boolean like) {
		LuceneHelperUtil.addTerm(_booleanQuery, field, value, like);
	}

	@Override
	public void addTerm(
		String field, String value, boolean like,
		BooleanClauseOccur booleanClauseOccur) {

		LuceneHelperUtil.addTerm(
			_booleanQuery, field, value, like, booleanClauseOccur);
	}

	@Override
	public List<BooleanClause> clauses() {
		List<org.apache.lucene.search.BooleanClause> luceneBooleanClauses =
			_booleanQuery.clauses();

		List<BooleanClause> booleanClauses = new ArrayList<BooleanClause>(
			luceneBooleanClauses.size());

		for (int i = 0; i < luceneBooleanClauses.size(); i++) {
			BooleanClause booleanClause = new BooleanClauseImpl(
				luceneBooleanClauses.get(i));

			booleanClauses.add(booleanClause);
		}

		return booleanClauses;
	}

	public org.apache.lucene.search.BooleanQuery getBooleanQuery() {
		return _booleanQuery;
	}

	@Override
	public Object getWrappedQuery() {
		return getBooleanQuery();
	}

	@Override
	public boolean hasClauses() {
		return !clauses().isEmpty();
	}

	@Override
	public String toString() {
		return _booleanQuery.toString();
	}

	private org.apache.lucene.search.BooleanQuery _booleanQuery;

}