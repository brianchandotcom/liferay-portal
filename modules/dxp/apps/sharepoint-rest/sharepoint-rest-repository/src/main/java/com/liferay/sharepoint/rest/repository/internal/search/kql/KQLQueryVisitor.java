/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
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

	private String _removeExtension(String s) {
		int i = s.lastIndexOf(CharPool.PERIOD);

		if (i == -1) {
			return s;
		}

		return s.substring(0, i);
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

			if (field.equals(Field.NAME) || field.equals(Field.TITLE)) {
				value = _removeExtension(value);
			}

			String formattedValue =
				_extRepositoryQueryMapper.formatParameterValue(field, value);

			if (field.equals(Field.FOLDER_ID)) {
				formattedValue =
					_siteAbsoluteURL + StringPool.SLASH + formattedValue;
			}

			return String.format("\"%s\"", formattedValue);
		}
		catch (SearchException searchException) {
			throw new SystemException(searchException);
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