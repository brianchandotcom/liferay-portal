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

package com.liferay.sharepoint.rest.repository.internal.search.kql;

import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.generic.DisMaxQuery;
import com.liferay.portal.kernel.search.generic.FuzzyQuery;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.MoreLikeThisQuery;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.search.query.QueryVisitor;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.text.DateFormat;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class KQLQueryVisitor implements QueryVisitor<KQLQuery> {

	public KQLQueryVisitor(
		ExtRepositoryQueryMapper extRepositoryQueryMapper,
		String siteAbsoluteURL) {

		_extRepositoryQueryMapper = extRepositoryQueryMapper;
		_siteAbsoluteURL = siteAbsoluteURL;
	}

	@Override
	public KQLQuery visitQuery(BooleanQuery booleanQuery) {
		if (!booleanQuery.hasClauses()) {
			return NullKQLQuery.INSTANCE;
		}

		KQLQuery mustKQLQuery = NullKQLQuery.INSTANCE;
		KQLQuery mustNotKQLQuery = NullKQLQuery.INSTANCE;
		KQLQuery shouldKQLQuery = NullKQLQuery.INSTANCE;

		for (BooleanClause<Query> booleanClause : booleanQuery.clauses()) {
			BooleanClauseOccur booleanClauseOccur =
				booleanClause.getBooleanClauseOccur();

			Query query = booleanClause.getClause();

			KQLQuery kqlQuery = query.accept(this);

			if (booleanClauseOccur.equals(BooleanClauseOccur.MUST)) {
				mustKQLQuery = mustKQLQuery.and(kqlQuery);
			}
			else if (booleanClauseOccur.equals(BooleanClauseOccur.MUST_NOT)) {
				mustNotKQLQuery = mustNotKQLQuery.and(kqlQuery);
			}
			else if (booleanClauseOccur.equals(BooleanClauseOccur.SHOULD)) {
				shouldKQLQuery = shouldKQLQuery.or(kqlQuery);
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported boolean clause occur: " +
						booleanClauseOccur.getName());
			}
		}

		return KQLQuery.and(
			mustKQLQuery, mustNotKQLQuery.not(), shouldKQLQuery);
	}

	@Override
	public KQLQuery visitQuery(DisMaxQuery disMaxQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(FuzzyQuery fuzzyQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(MatchAllQuery matchAllQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(MatchQuery matchQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(MoreLikeThisQuery moreLikeThisQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(MultiMatchQuery multiMatchQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(NestedQuery nestedQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(StringQuery stringQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public KQLQuery visitQuery(TermQuery termQuery) {
		return _visitQueryTerm(termQuery.getQueryTerm());
	}

	@Override
	public KQLQuery visitQuery(TermRangeQuery termRangeQuery) {
		String field = termRangeQuery.getField();

		String translatedField = _translateField(field);

		if (translatedField == null) {
			return NullKQLQuery.INSTANCE;
		}

		return KQLQuery.range(
			_translateValue(field, termRangeQuery.getLowerTerm()),
			_translateValue(field, termRangeQuery.getUpperTerm()));
	}

	@Override
	public KQLQuery visitQuery(WildcardQuery wildcardQuery) {
		return _visitQueryTerm(wildcardQuery.getQueryTerm());
	}

	private String _translateField(String field) {
		if (field.equals(Field.CREATE_DATE) ||
			field.equals(Field.MODIFIED_DATE)) {

			return "LastModifiedDate";
		}

		if (field.equals(Field.FOLDER_ID)) {
			return "Path";
		}

		if (field.equals(Field.NAME) || field.equals(Field.TITLE)) {
			return "Title";
		}

		if (field.equals(Field.USER_ID) || field.equals(Field.USER_NAME)) {
			return "Author";
		}

		return null;
	}

	private String _translateValue(String field, String value) {
		try {
			if (field.equals(Field.CREATE_DATE) ||
				field.equals(Field.MODIFIED_DATE)) {

				Date date = _extRepositoryQueryMapper.formatDateParameterValue(
					field, value);

				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy-MM-ddTHH:mm:ss");

				return dateFormat.format(date);
			}

			String formattedValue =
				_extRepositoryQueryMapper.formatParameterValue(field, value);

			if (field.equals(Field.FOLDER_ID)) {
				formattedValue =
					_siteAbsoluteURL + StringPool.SLASH + formattedValue;
			}

			return String.format("\"%s\"", formattedValue);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	private KQLQuery _visitQueryTerm(QueryTerm queryTerm) {
		String field = queryTerm.getField();

		String translatedField = _translateField(field);

		if (translatedField == null) {
			return NullKQLQuery.INSTANCE;
		}

		return KQLQuery.eq(
			translatedField, _translateValue(field, queryTerm.getValue()));
	}

	private final ExtRepositoryQueryMapper _extRepositoryQueryMapper;
	private final String _siteAbsoluteURL;

}