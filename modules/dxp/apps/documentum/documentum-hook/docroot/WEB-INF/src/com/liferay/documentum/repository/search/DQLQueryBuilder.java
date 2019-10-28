/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.documentum.repository.search;

import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.documentum.repository.model.Constants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mika Koivisto
 * @author Iván Zaera Avellón
 */
public class DQLQueryBuilder {

	public DQLQueryBuilder(ExtRepositoryQueryMapper extRepositoryQueryMapper) {
		_extRepositoryQueryMapper = extRepositoryQueryMapper;
	}

	public String buildSearchCountQueryString(
			SearchContext searchContext, Query query)
		throws SearchException {

		StringBundler sb = new StringBundler(4);

		sb.append("SELECT COUNT(r_object_id) AS num_hits FROM ");
		sb.append(Constants.DM_DOCUMENT);

		DQLConjunction dqlConjunction = new DQLConjunction();

		_traverseQuery(dqlConjunction, query, searchContext.getQueryConfig());

		if (!dqlConjunction.isEmpty()) {
			sb.append(" WHERE ");
			sb.append(dqlConjunction.toQueryFragment());
		}

		return sb.toString();
	}

	public String buildSearchSelectQueryString(
			SearchContext searchContext, Query query)
		throws SearchException {

		StringBundler sb = new StringBundler();

		sb.append("SELECT ");
		sb.append(Constants.R_OBJECT_ID);
		sb.append(" FROM ");
		sb.append(Constants.DM_DOCUMENT);

		DQLConjunction dqlConjunction = new DQLConjunction();

		_traverseQuery(dqlConjunction, query, searchContext.getQueryConfig());

		if (!dqlConjunction.isEmpty()) {
			sb.append(" WHERE ");
			sb.append(dqlConjunction.toQueryFragment());
		}

		Sort[] sorts = searchContext.getSorts();

		if ((sorts != null) && (sorts.length > 0)) {
			sb.append(" ORDER BY ");

			for (int i = 0; i < sorts.length; i++) {
				Sort sort = sorts[i];

				if (i > 0) {
					sb.append(", ");
				}

				sb.append(_dqlFields.get(sort.getFieldName()));

				if (sort.isReverse()) {
					sb.append(" DESC");
				}
				else {
					sb.append(" ASC");
				}
			}
		}

		return sb.toString();
	}

	private DQLCriterion _buildFieldExpression(
			String field, String value,
			DQLSimpleExpressionOperator dqlSimpleExpressionOperator,
			QueryConfig queryConfig)
		throws SearchException {

		DQLCriterion dqlCriterion = null;

		if (DQLSimpleExpressionOperator.LIKE == dqlSimpleExpressionOperator) {
			value = value.replaceAll("\\*", StringPool.PERCENT);
		}

		if (field.equals(Field.CREATE_DATE) ||
			field.equals(Field.MODIFIED_DATE)) {

			Date date = _extRepositoryQueryMapper.formatDateParameterValue(
				field, value);

			dqlCriterion = new DQLDateExpression(
				_dqlFields.get(field), date, dqlSimpleExpressionOperator);
		}
		else if (field.equals(Field.FOLDER_ID)) {
			String extRepositoryFolderKey =
				_extRepositoryQueryMapper.formatParameterValue(field, value);

			boolean descend = false;

			if (queryConfig != null) {
				descend = queryConfig.isSearchSubfolders();
			}

			dqlCriterion = new DQLInFolderExpression(
				extRepositoryFolderKey, descend);
		}
		else if (field.equals(Field.USER_ID) || field.equals(Field.USER_NAME)) {
			String screenName = _extRepositoryQueryMapper.formatParameterValue(
				field, value);

			dqlCriterion = new DQLSimpleExpression(
				Constants.R_CREATOR_NAME, screenName,
				dqlSimpleExpressionOperator);
		}
		else {
			value = _extRepositoryQueryMapper.formatParameterValue(
				field, value);

			String dqlField = _dqlFields.get(field);

			if (Validator.isNull(dqlField)) {
				dqlField = field;
			}

			dqlCriterion = new DQLSimpleExpression(
				dqlField, value, dqlSimpleExpressionOperator);
		}

		return dqlCriterion;
	}

	private void _traverseQuery(
			DQLJunction criterionDQLJunction, Query query,
			QueryConfig queryConfig)
		throws SearchException {

		if (query instanceof BooleanQuery) {
			BooleanQuery booleanQuery = (BooleanQuery)query;

			List<BooleanClause<Query>> booleanClauses = booleanQuery.clauses();

			DQLConjunction anyDQLConjunction = new DQLConjunction();
			DQLConjunction notDQLConjunction = new DQLConjunction();
			DQLDisjunction dqlDisjunction = new DQLDisjunction();

			for (BooleanClause<Query> booleanClause : booleanClauses) {
				DQLJunction dqlJunction = dqlDisjunction;

				BooleanClauseOccur booleanClauseOccur =
					booleanClause.getBooleanClauseOccur();

				if (booleanClauseOccur.equals(BooleanClauseOccur.MUST)) {
					dqlJunction = anyDQLConjunction;
				}
				else if (booleanClauseOccur.equals(
							BooleanClauseOccur.MUST_NOT)) {

					dqlJunction = notDQLConjunction;
				}

				Query booleanClauseQuery = booleanClause.getClause();

				_traverseQuery(dqlJunction, booleanClauseQuery, queryConfig);
			}

			if (!anyDQLConjunction.isEmpty()) {
				criterionDQLJunction.add(anyDQLConjunction);
			}

			if (!dqlDisjunction.isEmpty()) {
				criterionDQLJunction.add(dqlDisjunction);
			}

			if (!notDQLConjunction.isEmpty()) {
				criterionDQLJunction.add(
					new DQLNotExpression(notDQLConjunction));
			}
		}
		else if (query instanceof TermQuery) {
			TermQuery termQuery = (TermQuery)query;

			QueryTerm queryTerm = termQuery.getQueryTerm();

			if (!_supportedFields.contains(queryTerm.getField())) {
				return;
			}

			DQLCriterion dqlExpression = _buildFieldExpression(
				queryTerm.getField(), queryTerm.getValue(),
				DQLSimpleExpressionOperator.EQ, queryConfig);

			if (dqlExpression != null) {
				criterionDQLJunction.add(dqlExpression);
			}
		}
		else if (query instanceof TermRangeQuery) {
			TermRangeQuery termRangeQuery = (TermRangeQuery)query;

			if (!_supportedFields.contains(termRangeQuery.getField())) {
				return;
			}

			String fieldName = termRangeQuery.getField();

			String dqlField = _dqlFields.get(fieldName);
			String dqlLowerTerm = DQLParameterValueUtil.formatParameterValue(
				fieldName, termRangeQuery.getLowerTerm());
			String dqlUpperTerm = DQLParameterValueUtil.formatParameterValue(
				fieldName, termRangeQuery.getUpperTerm());

			DQLCriterion dqlCriterion = new DQLBetweenExpression(
				dqlField, dqlLowerTerm, dqlUpperTerm,
				termRangeQuery.includesLower(), termRangeQuery.includesUpper());

			criterionDQLJunction.add(dqlCriterion);
		}
		else if (query instanceof WildcardQuery) {
			WildcardQuery wildcardQuery = (WildcardQuery)query;

			QueryTerm queryTerm = wildcardQuery.getQueryTerm();

			if (!_supportedFields.contains(queryTerm.getField())) {
				return;
			}

			DQLCriterion dqlCriterion = _buildFieldExpression(
				queryTerm.getField(), queryTerm.getValue(),
				DQLSimpleExpressionOperator.LIKE, queryConfig);

			if (dqlCriterion != null) {
				criterionDQLJunction.add(dqlCriterion);
			}
		}
	}

	private static final Map<String, String> _dqlFields = HashMapBuilder.put(
		Field.CREATE_DATE, Constants.R_CREATION_DATE
	).put(
		Field.MODIFIED_DATE, Constants.R_MODIFY_DATE
	).put(
		Field.NAME, Constants.OBJECT_NAME
	).put(
		Field.TITLE, Constants.OBJECT_NAME
	).put(
		Field.USER_NAME, Constants.R_CREATOR_NAME
	).put(
		"modifiedDate", Constants.R_MODIFY_DATE
	).put(
		"size_", Constants.R_CONTENT_SIZE
	).build();
	private static final Set<String> _supportedFields = new HashSet<String>() {
		{
			add(Field.CREATE_DATE);
			add(Field.FOLDER_ID);
			add(Field.MODIFIED_DATE);
			add(Field.NAME);
			add(Field.TITLE);
			add(Field.USER_ID);
			add(Field.USER_NAME);
		}
	};

	private final ExtRepositoryQueryMapper _extRepositoryQueryMapper;

}