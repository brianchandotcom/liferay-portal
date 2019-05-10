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

package com.liferay.portal.search.query.util;

import com.liferay.portal.search.query.BooleanQuery;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface BooleanQueryUtilities {

	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Boolean value);

	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Double value);

	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Integer value);

	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Long value);

	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Short value);

	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, String value);

	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, Integer startValue,
		Integer endValue);

	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue);

	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, Short startValue,
		Short endValue);

	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue);

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Boolean value);

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Double value);

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Integer value);

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Long value);

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Short value);

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value);

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like);

	public BooleanQuery addTerm(
		BooleanQuery booleanQuery, String field, String value);

	public BooleanQuery addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like);

	public BooleanQuery addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		boolean parseKeywords);

	public BooleanQuery addTerms(
		BooleanQuery booleanQuery, String[] fields, String value);

	public BooleanQuery addTerms(
		BooleanQuery booleanQuery, String[] fields, String value, boolean like);

}