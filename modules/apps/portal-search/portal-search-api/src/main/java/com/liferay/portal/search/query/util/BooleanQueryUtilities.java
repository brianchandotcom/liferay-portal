/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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