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

package com.liferay.portal.search.lucene.internal.query;

import com.liferay.portal.kernel.search.BooleanClauseOccur;

import org.apache.lucene.search.BooleanQuery;

/**
 * @author Michael C. Han
 */
public interface LuceneQueryHelper {

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, boolean value);

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, double value);

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, int value);

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, long value);

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, short value);

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value);

	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Integer startValue,
		Integer endValue);

	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue);

	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, short startValue,
		short endValue);

	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Short startValue,
		Short endValue);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #addNumericRangeTerm(BooleanQuery, String, Long, Long)}
	 */
	@Deprecated
	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue);

	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, int startValue, int endValue);

	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, long startValue,
		long endValue);

	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue);

	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, boolean value);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, double value);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, int value);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, long value);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, short value);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String[] values, boolean like);

	public void addTerm(BooleanQuery booleanQuery, String field, long value);

	public void addTerm(BooleanQuery booleanQuery, String field, String value);

	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like);

	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		BooleanClauseOccur booleanClauseOccur);

	public void addTerm(
		BooleanQuery booleanQuery, String field, String[] values, boolean like);

}