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

package com.liferay.portal.kernel.repository.cmis.search;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermRangeQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mika Koivisto
 */
public class BaseCmisSearchQueryBuilder implements CMISSearchQueryBuilder {

	@Override
	public String buildQuery(SearchContext searchContext, Query query)
		throws SearchException {

		StringBundler sb = new StringBundler();

		sb.append("SELECT cmis:objectId");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (queryConfig.isScoreEnabled()) {
			sb.append(", SCORE() AS HITS");
		}

		sb.append(" FROM cmis:document");

		CMISConjunction cmisConjunction = new CMISConjunction();

		traverseQuery(cmisConjunction, query, queryConfig);

		if (!cmisConjunction.isEmpty()) {
			sb.append(" WHERE ");
			sb.append(cmisConjunction.toQueryFragment());
		}

		Sort[] sorts = searchContext.getSorts();

		if (queryConfig.isScoreEnabled() ||
			((sorts != null) && (sorts.length > 0))) {

			sb.append(" ORDER BY ");
		}

		if ((sorts != null) && (sorts.length > 0)) {
			int i = 0;

			for (Sort sort : sorts) {
				String fieldName = sort.getFieldName();

				if (!isSupportedField(fieldName)) {
					continue;
				}

				if (i > 0) {
					sb.append(", ");
				}

				sb.append(getCmisField(fieldName));

				if (sort.isReverse()) {
					sb.append(" DESC");
				}
				else {
					sb.append(" ASC");
				}

				i++;
			}
		}
		else if (queryConfig.isScoreEnabled()) {
			sb.append("HITS DESC");
		}

		return sb.toString();
	}

	protected CMISCriterion buildFieldExpression(
			String field, String value,
			CMISSimpleExpressionOperator cmisSimpleExpressionOperator)
		throws SearchException {

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
				throw new SearchException(
					"Unable to determine folder {folderId=" + folderId + "}",
					se);
			}
		}
		else if (field.equals(Field.USER_ID)) {
			try {
				long userId = GetterUtil.getLong(value);

				User user = UserLocalServiceUtil.getUserById(userId);

				String screenName = CMISParameterValueUtil.formatParameterValue(
					field, user.getScreenName(), wildcard);

				cmisCriterion = new CMISSimpleExpression(
					getCmisField(field), screenName,
					cmisSimpleExpressionOperator);
			}
			catch (Exception e) {
				if (e instanceof SearchException) {
					throw (SearchException)e;
				}

				throw new SearchException(
					"Unable to determine user {" + field + "=" + value + "}",
					e);
			}
		}
		else {
			value = CMISParameterValueUtil.formatParameterValue(
				field, value, wildcard);

			cmisCriterion = new CMISSimpleExpression(
				getCmisField(field), value, cmisSimpleExpressionOperator);
		}

		return cmisCriterion;
	}

	protected String getCmisField(String field) {
		return _cmisFields.get(field);
	}

	protected boolean isSupportedField(String field) {
		return _supportedFields.contains(field);
	}

	protected boolean isSupportsFullText(QueryConfig queryConfig) {
		String capabilityQuery = (String)queryConfig.getAttribute(
			"capabilityQuery");

		if (Validator.isNull(capabilityQuery)) {
			return false;
		}

		if (capabilityQuery.equals("bothcombined") ||
			capabilityQuery.equals("fulltextonly")) {

			return true;
		}

		return false;
	}

	protected boolean isSupportsOnlyFullText(QueryConfig queryConfig) {
		String capabilityQuery = (String)queryConfig.getAttribute(
			"capabilityQuery");

		if (Validator.isNull(capabilityQuery)) {
			return false;
		}

		if (capabilityQuery.equals("fulltextonly")) {
			return true;
		}

		return false;
	}

	protected void traverseQuery(
			CMISJunction criterion, Query query, QueryConfig queryConfig)
		throws SearchException {

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

				traverseQuery(cmisJunction, booleanClauseQuery, queryConfig);
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

			if (!isSupportedField(queryTerm.getField())) {
				return;
			}

			CMISCriterion cmisExpression = buildFieldExpression(
				queryTerm.getField(), queryTerm.getValue(),
				CMISSimpleExpressionOperator.EQ);

			if (cmisExpression != null) {
				boolean add = true;

				if ((cmisExpression instanceof CMISContainsExpression) &&
					!isSupportsFullText(queryConfig)) {

					add = false;
				}
				else if (!((cmisExpression instanceof CMISContainsExpression) ||
						   (cmisExpression instanceof CMISInFolderExpression) ||
						   (cmisExpression instanceof CMISInTreeExpression)) &&
						 isSupportsOnlyFullText(queryConfig)) {

					add = false;
				}

				if (add) {
					criterion.add(cmisExpression);
				}
			}
		}
		else if (query instanceof TermRangeQuery) {
			TermRangeQuery termRangeQuery = (TermRangeQuery)query;

			if (!isSupportedField(termRangeQuery.getField())) {
				return;
			}

			String fieldName = termRangeQuery.getField();

			String cmisField = getCmisField(fieldName);
			String cmisLowerTerm = CMISParameterValueUtil.formatParameterValue(
				fieldName, termRangeQuery.getLowerTerm());
			String cmisUpperTerm = CMISParameterValueUtil.formatParameterValue(
				fieldName, termRangeQuery.getUpperTerm());

			CMISCriterion cmisCriterion = new CMISBetweenExpression(
				cmisField, cmisLowerTerm, cmisUpperTerm,
				termRangeQuery.includesLower(), termRangeQuery.includesUpper());

			criterion.add(cmisCriterion);
		}
		else if (query instanceof WildcardQuery) {
			WildcardQuery wildcardQuery = (WildcardQuery)query;

			QueryTerm queryTerm = wildcardQuery.getQueryTerm();

			if (!isSupportedField(queryTerm.getField())) {
				return;
			}

			CMISCriterion cmisCriterion = buildFieldExpression(
				queryTerm.getField(), queryTerm.getValue(),
				CMISSimpleExpressionOperator.LIKE);

			if (cmisCriterion != null) {
				boolean add = true;

				if ((cmisCriterion instanceof CMISContainsExpression) &&
					!isSupportsFullText(queryConfig)) {

					add = false;
				}
				else if (!((cmisCriterion instanceof CMISContainsExpression) ||
						   (cmisCriterion instanceof CMISInFolderExpression) ||
						   (cmisCriterion instanceof CMISInTreeExpression)) &&
						 isSupportsOnlyFullText(queryConfig)) {

					add = false;
				}

				if (add) {
					criterion.add(cmisCriterion);
				}
			}
		}
	}

	private static Map<String, String> _cmisFields;
	private static Set<String> _supportedFields;

	static {
		_cmisFields = new HashMap<String, String>();

		_cmisFields.put(Field.CREATE_DATE, "cmis:creationDate");
		_cmisFields.put(Field.MODIFIED_DATE, "cmis:lastModificationDate");
		_cmisFields.put(Field.NAME, "cmis:name");
		_cmisFields.put(Field.TITLE, "cmis:name");
		_cmisFields.put(Field.USER_ID, "cmis:createdBy");
		_cmisFields.put(Field.USER_NAME, "cmis:createdBy");

		_supportedFields = new HashSet<String>();

		_supportedFields.add(Field.CONTENT);
		_supportedFields.add(Field.CREATE_DATE);
		_supportedFields.add(Field.FOLDER_ID);
		_supportedFields.add(Field.MODIFIED_DATE);
		_supportedFields.add(Field.NAME);
		_supportedFields.add(Field.TITLE);
		_supportedFields.add(Field.USER_ID);
		_supportedFields.add(Field.USER_NAME);
	}

}