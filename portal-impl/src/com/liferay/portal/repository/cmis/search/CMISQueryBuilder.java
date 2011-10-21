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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;

/**
 * @author Mika Koivisto
 */
public class CMISQueryBuilder {

	public static String buildQuery(SearchContext searchContext, Query query) {
		StringBundler sb = new StringBundler();

		sb.append("SELECT ");
		sb.append(PropertyIds.OBJECT_ID);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (queryConfig.isScoreEnabled()) {
			sb.append(", SCORE() AS HITS");
		}

		sb.append(" FROM ");
		sb.append(BaseTypeId.CMIS_DOCUMENT.value());

		CMISConjunction cmisConjunction = new CMISConjunction();

		_traverseQuery(cmisConjunction, query);

		if (!cmisConjunction.isEmpty()) {
			sb.append(" WHERE ");
			sb.append(cmisConjunction.toQueryFragment());
		}

		Sort[] sorts = searchContext.getSorts();

		if (queryConfig.isScoreEnabled() ||
			((sorts != null) && sorts.length > 0)) {

			sb.append(" ORDER BY ");
		}

		if ((sorts != null) && (sorts.length > 0)) {
			for (int i = 0; i < sorts.length; i++) {
				Sort sort = sorts[i];

				if (i > 0) {
					sb.append(", ");
				}

				String fieldName = sort.getFieldName();

				if (fieldName.equals(Field.CREATE_DATE)) {
					sb.append(PropertyIds.CREATION_DATE);
				}
				else if (fieldName.equals(Field.MODIFIED_DATE)) {
					sb.append(PropertyIds.LAST_MODIFICATION_DATE);
				}
				else if (fieldName.equals(Field.TITLE)) {
					sb.append(PropertyIds.NAME);
				}

				if (sort.isReverse()) {
					sb.append(" DESC");
				}
				else {
					sb.append(" ASC");
				}
			}
		}
		else if (queryConfig.isScoreEnabled()) {
			sb.append("HITS DESC");
		}

		return sb.toString();
	}

	private static CMISCriterion _buildFieldExpression(
		String field, String value,
		CMISSimpleExpressionOperator cmisSimpleExpressionOperator) {

		CMISCriterion cmisCriterion = null;

		boolean wildcard =
			CMISSimpleExpressionOperator.LIKE == cmisSimpleExpressionOperator;

		if (field.equals(Field.CONTENT)) {
			value = CMISParameterValueUtil.formatParameterValue(field, value);

			cmisCriterion = new CMISContainsExpression(value);
		}
		else if (field.equals(Field.FOLDER_ID)) {
			long folderId = GetterUtil.getLong(value);

			try {
				RepositoryEntry repositoryEntry =
					RepositoryEntryLocalServiceUtil.fetchRepositoryEntry(
						folderId);

				if (repositoryEntry != null) {
					String objectId = repositoryEntry.getMappedId();

					objectId = CMISParameterValueUtil.formatParameterValue(
						field, objectId, wildcard);

					cmisCriterion = new CMISInFolderExpression(objectId);
				}
			}
			catch (SystemException se) {
				_log.error(se, se);
			}
		}
		else if (field.equals(Field.TITLE)) {
			value = CMISParameterValueUtil.formatParameterValue(
				field, value, wildcard);

			cmisCriterion = new CMISSimpleExpression(
				PropertyIds.NAME, value, cmisSimpleExpressionOperator);
		}
		else if (field.equals(Field.USER_ID)) {
			try {
				long userId = GetterUtil.getLong(value);

				User user = UserLocalServiceUtil.getUserById(userId);

				String screenName = CMISParameterValueUtil.formatParameterValue(
					field, user.getScreenName(), wildcard);

				cmisCriterion = new CMISSimpleExpression(
					PropertyIds.CREATED_BY, screenName,
					cmisSimpleExpressionOperator);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}
			}
			catch (SystemException se) {
				_log.error(se, se);
			}
		}
		else if (field.equals(Field.USER_NAME)) {
			value = CMISParameterValueUtil.formatParameterValue(
				field, value, wildcard);

			cmisCriterion = new CMISSimpleExpression(
				PropertyIds.CREATED_BY, value, cmisSimpleExpressionOperator);
		}

		return cmisCriterion;
	}

	private static void _traverseQuery(CMISJunction criterion, Query query) {
		if (query instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query;

			List<BooleanClause> booleanClauses = booleanQuery.clauses();

			CMISConjunction anyCMISConjunction = new CMISConjunction();
			CMISConjunction notCMISConjunction = new CMISConjunction();
			CMISDisjunction cmisDisjunction = new CMISDisjunction();

			for (BooleanClause booleanClause : booleanClauses) {
				CMISJunction cmisJunction = cmisDisjunction;

				BooleanClauseOccur booleanClauseOccur =
					booleanClause.getBooleanClauseOccur();

				if (booleanClauseOccur.equals(BooleanClauseOccur.MUST)) {
					cmisJunction = anyCMISConjunction;
				}
				else if (booleanClauseOccur.equals(
							BooleanClauseOccur.MUST_NOT)) {

					cmisJunction = notCMISConjunction;
				}

				Query booleanClauseQuery = booleanClause.getQuery();

				_traverseQuery(cmisJunction, booleanClauseQuery);
			}

			if (!anyCMISConjunction.isEmpty()) {
				criterion.add(anyCMISConjunction);
			}

			if (!cmisDisjunction.isEmpty()) {
				criterion.add(cmisDisjunction);
			}

			if (!notCMISConjunction.isEmpty()) {
				criterion.add(new CMISNotExpression(notCMISConjunction));
			}
		}
		else if (query instanceof TermQuery) {
			TermQuery termQuery = (TermQuery)query;

			QueryTerm queryTerm = termQuery.getQueryTerm();

			if (!_supportedFields.contains(queryTerm.getField())) {
				return;
			}

			CMISCriterion cmisExpression = _buildFieldExpression(
				queryTerm.getField(), queryTerm.getValue(),
				CMISSimpleExpressionOperator.EQ);

			if (cmisExpression != null) {
				criterion.add(cmisExpression);
			}
		}
		else if (query instanceof TermRangeQuery) {
			TermRangeQuery termRangeQuery = (TermRangeQuery)query;

			if (!_supportedFields.contains(termRangeQuery.getField())) {
				return;
			}

			CMISCriterion cmisCriterion = new CMISBetweenExpression(
				termRangeQuery.getField(), termRangeQuery.getLowerTerm(),
				termRangeQuery.getUpperTerm(), termRangeQuery.includesLower(),
				termRangeQuery.includesUpper());

			criterion.add(cmisCriterion);
		}
		else if (query instanceof WildcardQuery) {
			WildcardQuery wildcardQuery = (WildcardQuery)query;

			QueryTerm queryTerm = wildcardQuery.getQueryTerm();

			if (!_supportedFields.contains(queryTerm.getField())) {
				return;
			}

			CMISCriterion cmisCriterion = _buildFieldExpression(
				queryTerm.getField(), queryTerm.getValue(),
				CMISSimpleExpressionOperator.LIKE);

			if (cmisCriterion != null) {
				criterion.add(cmisCriterion);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CMISQueryBuilder.class);

	private static Set<String> _supportedFields;

	static {
		_supportedFields = new HashSet<String>();

		_supportedFields.add(Field.CREATE_DATE);
		_supportedFields.add(Field.FOLDER_ID);
		_supportedFields.add(Field.NAME);
		_supportedFields.add(Field.TITLE);
		_supportedFields.add(Field.USER_ID);
		_supportedFields.add(Field.USER_NAME);
	}

}