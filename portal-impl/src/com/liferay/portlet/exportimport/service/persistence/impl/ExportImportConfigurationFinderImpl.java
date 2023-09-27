/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.exportimport.service.persistence.impl;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.persistence.ExportImportConfigurationFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationFinderImpl
	extends ExportImportConfigurationFinderBaseImpl
	implements ExportImportConfigurationFinder {

	public static final String COUNT_BY_C_G_N_D_T =
		ExportImportConfigurationFinder.class.getName() + ".countByC_G_N_D_T";

	public static final String FIND_BY_C_G_N_D_T =
		ExportImportConfigurationFinder.class.getName() + ".findByC_G_N_D_T";

	@Override
	public int countByKeywords(
		long companyId, long groupId, String keywords, int type, int status) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		QueryDefinition<ExportImportConfiguration> queryDefinition =
			new QueryDefinition<>(status);

		return doCountByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, andOperator,
			queryDefinition, false);
	}

	@Override
	public int countByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		QueryDefinition<ExportImportConfiguration> queryDefinition =
			new QueryDefinition<>(status);

		return doCountByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, andOperator,
			queryDefinition, false);
	}

	@Override
	public int filterCountByKeywords(
		long companyId, long groupId, String keywords, int type, int status) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterCountByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator);
	}

	@Override
	public int filterCountByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return filterCountByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator);
	}

	@Override
	public int filterCountByC_G_N_D_T(
		long companyId, long groupId, String[] names, String[] descriptions,
		int type, int status, boolean andOperator) {

		QueryDefinition<ExportImportConfiguration> queryDefinition =
			new QueryDefinition<>(status);

		return doCountByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, andOperator,
			queryDefinition, true);
	}

	@Override
	public List<ExportImportConfiguration> filterFindByKeywords(
		long companyId, long groupId, String keywords, int type, int status,
		int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterFindByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<ExportImportConfiguration> filterFindByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return filterFindByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<ExportImportConfiguration> filterFindByC_G_N_D_T(
		long companyId, long groupId, String[] names, String[] descriptions,
		int type, int status, boolean andOperator, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		QueryDefinition<ExportImportConfiguration> queryDefinition =
			new QueryDefinition<>(status, false, start, end, orderByComparator);

		return doFindByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, andOperator,
			queryDefinition, true);
	}

	@Override
	public List<ExportImportConfiguration> findByKeywords(
		long companyId, long groupId, String keywords, int type, int status,
		int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return findByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<ExportImportConfiguration> findByC_G_N_D_T(
		long companyId, long groupId, String name, String description, int type,
		int status, boolean andOperator, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return findByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, status, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public List<ExportImportConfiguration> findByC_G_N_D_T(
		long companyId, long groupId, String[] names, String[] descriptions,
		int type, int status, boolean andOperator, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		QueryDefinition<ExportImportConfiguration> queryDefinition =
			new QueryDefinition<>(status, false, start, end, orderByComparator);

		return doFindByC_G_N_D_T(
			companyId, groupId, names, descriptions, type, andOperator,
			queryDefinition, false);
	}

	protected int doCountByC_G_N_D_T(
		long companyId, long groupId, String[] names, String[] descriptions,
		int type, boolean andOperator,
		QueryDefinition<ExportImportConfiguration> queryDefinition,
		boolean inlineSQLHelper) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_N_D_T);

			sql = updateSQL(sql, queryDefinition);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, ExportImportConfiguration.class.getName(),
					"ExportImportConfiguration.exportImportConfigurationId",
					groupId);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, true, descriptions);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(groupId);
			queryPos.add(names, 2);
			queryPos.add(descriptions, 2);
			queryPos.add(type);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				queryPos.add(queryDefinition.getStatus());
			}

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<ExportImportConfiguration> doFindByC_G_N_D_T(
		long companyId, long groupId, String[] names, String[] descriptions,
		int type, boolean andOperator,
		QueryDefinition<ExportImportConfiguration> queryDefinition,
		boolean inlineSQLHelper) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_N_D_T);

			sql = updateSQL(sql, queryDefinition);

			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, ExportImportConfiguration.class.getName(),
					"ExportImportConfiguration.exportImportConfigurationId",
					groupId);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "LOWER(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, true, descriptions);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"ExportImportConfiguration",
				ExportImportConfigurationImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(groupId);
			queryPos.add(names, 2);
			queryPos.add(descriptions, 2);
			queryPos.add(type);

			if (queryDefinition.getStatus() != WorkflowConstants.STATUS_ANY) {
				queryPos.add(queryDefinition.getStatus());
			}

			return (List<ExportImportConfiguration>)QueryUtil.list(
				sqlQuery, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String updateSQL(
		String sql,
		QueryDefinition<ExportImportConfiguration> queryDefinition) {

		if (queryDefinition.getStatus() == WorkflowConstants.STATUS_ANY) {
			return sql;
		}

		if (queryDefinition.isExcludeStatus()) {
			return CustomSQLUtil.appendCriteria(sql, "AND (status != ?)");
		}

		return CustomSQLUtil.appendCriteria(sql, "AND (status = ?)");
	}

}