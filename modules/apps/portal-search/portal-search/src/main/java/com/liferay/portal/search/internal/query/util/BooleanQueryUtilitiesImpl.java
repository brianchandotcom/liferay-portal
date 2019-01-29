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

package com.liferay.portal.search.internal.query.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.RangeTermQuery;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.field.FieldQueryFactory;
import com.liferay.portal.search.query.util.BooleanQueryUtilities;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = BooleanQueryUtilities.class)
public class BooleanQueryUtilitiesImpl implements BooleanQueryUtilities {

	@Override
	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Boolean value) {

		return addExactTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Double value) {

		return addExactTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Integer value) {

		return addExactTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Long value) {

		return addExactTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Short value) {

		return addExactTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		return addExactTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, Integer startValue,
		Integer endValue) {

		return addRangeTerm(booleanQuery, field, (Object)startValue, endValue);
	}

	@Override
	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue) {

		return addRangeTerm(booleanQuery, field, (Object)startValue, endValue);
	}

	@Override
	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, Short startValue,
		Short endValue) {

		return addRangeTerm(booleanQuery, field, (Object)startValue, endValue);
	}

	@Override
	public BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		return addRangeTerm(booleanQuery, field, (Object)startValue, endValue);
	}

	@Override
	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Boolean value) {

		return addRequiredTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Double value) {

		return addRequiredTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Integer value) {

		return addRequiredTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Long value) {

		return addRequiredTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Short value) {

		return addRequiredTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value) {

		return addRequiredTerm(booleanQuery, field, (Object)value);
	}

	@Override
	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		return addRequiredTerm(booleanQuery, field, value, like, false);
	}

	public BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		boolean parseKeywords) {

		Query query = _fieldQueryFactory.createQuery(
			field, value, like, parseKeywords);

		return booleanQuery.addMustQueryClauses(query);
	}

	@Override
	public BooleanQuery addTerm(
		BooleanQuery booleanQuery, String field, String value) {

		return addTerm(booleanQuery, field, value, false);
	}

	@Override
	public BooleanQuery addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		return addTerm(booleanQuery, field, value, like, false);
	}

	@Override
	public BooleanQuery addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		boolean parseKeywords) {

		Query query = _fieldQueryFactory.createQuery(field, value, like, false);

		return booleanQuery.addShouldQueryClauses(query);
	}

	@Override
	public BooleanQuery addTerms(
		BooleanQuery booleanQuery, String[] fields, String value) {

		if (Validator.isNull(value) || ArrayUtil.isEmpty(fields)) {
			return booleanQuery;
		}

		for (String field : fields) {
			Query query = _fieldQueryFactory.createQuery(
				field, value, false, false);

			booleanQuery.addShouldQueryClauses(query);
		}

		return booleanQuery;
	}

	@Override
	public BooleanQuery addTerms(
		BooleanQuery booleanQuery, String[] fields, String value,
		boolean like) {

		if (Validator.isNull(value) || ArrayUtil.isEmpty(fields)) {
			return booleanQuery;
		}

		for (String field : fields) {
			Query query = _fieldQueryFactory.createQuery(
				field, value, like, false);

			booleanQuery.addShouldQueryClauses(query);
		}

		return booleanQuery;
	}

	protected BooleanQuery addExactTerm(
		BooleanQuery booleanQuery, String field, Object value) {

		TermQuery termQuery = _queries.term(field, value);

		return booleanQuery.addShouldQueryClauses(termQuery);
	}

	protected BooleanQuery addRangeTerm(
		BooleanQuery booleanQuery, String field, Object startValue,
		Object endValue) {

		RangeTermQuery rangeTermQuery = _queries.rangeTerm(
			field, true, true, startValue, endValue);

		return booleanQuery.addShouldQueryClauses(rangeTermQuery);
	}

	protected BooleanQuery addRequiredTerm(
		BooleanQuery booleanQuery, String field, Object value) {

		TermQuery termQuery = _queries.term(field, value);

		return booleanQuery.addMustQueryClauses(termQuery);
	}

	@Reference
	private FieldQueryFactory _fieldQueryFactory;

	@Reference
	private Queries _queries;

}