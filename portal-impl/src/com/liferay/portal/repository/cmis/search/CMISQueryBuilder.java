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

package com.liferay.portal.repository.cmis.search;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;

/**
 * @author Mika Koivisto
 */
public class CMISQueryBuilder {

	public static String buildQuery(SearchContext searchContext, Query query) {
		StringBundler sb = new StringBundler();

		sb.append("SELECT " + PropertyIds.OBJECT_ID);

		sb.append(", SCORE() AS SCORE");

		sb.append(" FROM " + BaseTypeId.CMIS_DOCUMENT.value());

		CMISConjunction criterion = new CMISConjunction();

		traverseQuery(criterion, query);

		if (!criterion.isEmpty()) {
			sb.append(" WHERE ");
			sb.append(criterion.toQueryFragment());
		}

		sb.append(" ORDER BY ");

		Sort[] sorts = searchContext.getSorts();

		if (sorts != null && sorts.length > 0) {
			for (int i = 0; i < sorts.length; i++) {
				Sort sort = sorts[i];

				if (i > 0) {
					sb.append(", ");
				}

				String fieldName = sort.getFieldName();

				if (fieldName.equals(Field.TITLE)) {
					sb.append(PropertyIds.NAME);
				}
				else if (fieldName.equals(Field.CREATE_DATE)) {
					sb.append(PropertyIds.CREATION_DATE);
				}
				else if (fieldName.equals(Field.MODIFIED_DATE)) {
					sb.append(PropertyIds.LAST_MODIFICATION_DATE);
				}

				if (sort.isReverse()) {
					sb.append(" DESC");
				}
				else {
					sb.append(" ASC");
				}
			}
		}
		else {
			sb.append("SCORE DESC");
		}

		return sb.toString();
	}

	protected static void traverseQuery(CMISJunction criterion, Query query) {
		if (query instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query;

			List<BooleanClause> clauses = booleanQuery.clauses();

			CMISConjunction conjunction = new CMISConjunction();
			CMISDisjunction disjunction = new CMISDisjunction();
			CMISConjunction not = new CMISConjunction();

			for (BooleanClause clause : clauses) {
				CMISJunction junction = disjunction;
				BooleanClauseOccur occur = clause.getBooleanClauseOccur();

				if (occur.equals(BooleanClauseOccur.MUST)) {
					junction = conjunction;
				}
				else if (occur.equals(BooleanClauseOccur.MUST_NOT)) {
					junction = not;
				}

				Query clauseQuery = clause.getQuery();

				traverseQuery(junction, clauseQuery);
			}

			if (!conjunction.isEmpty()) {
				criterion.add(conjunction);
			}
			if (!disjunction.isEmpty()) {
				criterion.add(disjunction);
			}
		}
		else if (query instanceof TermQuery) {
			TermQuery termQuery = (TermQuery)query;

			QueryTerm queryTerm = termQuery.getQueryTerm();

			String field = queryTerm.getField();
			String value = queryTerm.getValue();

			if (_supportedFields.contains(field)) {
				CMISCriterion expression = createFieldExpression(
					field, value, CMISSimpleExpressionOperator.EQ);

				if (expression != null) {
					criterion.add(expression);
				}
			}
		}
		else if (query instanceof TermRangeQuery) {
			TermRangeQuery termRangeQuery = (TermRangeQuery)query;

			String field = termRangeQuery.getField();
			String lowerTerm = termRangeQuery.getLowerTerm();
			String upperTerm = termRangeQuery.getUpperTerm();
			boolean includesLower = termRangeQuery.includesLower();
			boolean includesUpper = termRangeQuery.includesUpper();

			if (_supportedFields.contains(field)) {
				CMISCriterion expression = new CMISBetweenExpression(
					field, lowerTerm, upperTerm, includesLower, includesUpper);

				if (expression != null) {
					criterion.add(expression);
				}
			}
		}
		else if (query instanceof WildcardQuery) {
			WildcardQuery wildcardQuery = (WildcardQuery)query;

			QueryTerm queryTerm = wildcardQuery.getQueryTerm();

			String field = queryTerm.getField();
			String value = queryTerm.getValue();

			if (_supportedFields.contains(field)) {
				CMISCriterion expression = createFieldExpression(
					field, value, CMISSimpleExpressionOperator.LIKE);

				if (expression != null) {
					criterion.add(expression);
				}
			}
		}
	}

	private static CMISCriterion createFieldExpression(
		String field, String value, CMISSimpleExpressionOperator op) {

		CMISCriterion criterion = null;

		boolean wildcard = CMISSimpleExpressionOperator.LIKE == op;

		if (Field.CONTENT.equals(field)) {
			value = CMISParameterValueUtil.formatParameterValue(field, value);

			criterion = new CMISContainsExpression(value);
		}
		else if (Field.FOLDER_ID.equals(field)) {
			long folderId = Long.valueOf(value);

			try {
				RepositoryEntry repositoryEntry =
					RepositoryEntryUtil.fetchByPrimaryKey(folderId);

				if (repositoryEntry != null) {
					String objectId = repositoryEntry.getMappedId();

					objectId = CMISParameterValueUtil.formatParameterValue(
						field, objectId, wildcard);

					criterion = new CMISInFolderExpression(objectId);
				}
			}
			catch (SystemException e) {
			}
		}
		else if (Field.TITLE.equals(field)) {
			value = CMISParameterValueUtil.formatParameterValue(
				field, value, wildcard);

			criterion = new CMISSimpleExpression(
				PropertyIds.NAME, value, op);
		}
		else if (Field.USER_ID.equals(field)) {
			try {
				User user = UserLocalServiceUtil.getUserById(Long.valueOf(
					value));

				String screenName = user.getScreenName();

				screenName = CMISParameterValueUtil.formatParameterValue(
					field, screenName, wildcard);

				criterion = new CMISSimpleExpression(
					PropertyIds.CREATED_BY, screenName, op);
			}
			catch (Exception e) {
			}
		}
		else if (Field.USER_NAME.equals(field)) {
			value = CMISParameterValueUtil.formatParameterValue(
				field, value, wildcard);

			criterion = new CMISSimpleExpression(
				PropertyIds.CREATED_BY, value, op);
		}

		return criterion;
	}

	private static List<String> _supportedFields;

	static {
		_supportedFields = new ArrayList<String>();

		_supportedFields.add(Field.CONTENT);
		_supportedFields.add(Field.CREATE_DATE);
		_supportedFields.add(Field.FOLDER_ID);
		_supportedFields.add(Field.NAME);
		_supportedFields.add(Field.TITLE);
		_supportedFields.add(Field.USER_ID);
		_supportedFields.add(Field.USER_NAME);
	}

}