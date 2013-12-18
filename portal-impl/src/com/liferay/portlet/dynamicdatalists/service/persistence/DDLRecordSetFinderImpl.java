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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Marcellus Tavares
 * @author Connor McKay
 */
public class DDLRecordSetFinderImpl extends BasePersistenceImpl<DDLRecordSet>
	implements DDLRecordSetFinder {

	public static final String COUNT_BY_C_G_N_D_S =
		DDLRecordSetFinder.class.getName() + ".countByC_G_N_D_S";

	public static final String FIND_BY_C_G_N_D_S =
		DDLRecordSetFinder.class.getName() + ".findByC_G_N_D_S";

	@Override
	public int countByKeywords(
			long companyId, long groupId, String keywords, int scope)
		throws SystemException {

		return doCountByKeywords(companyId, groupId, keywords, scope, false);
	}

	@Override
	public int countByC_G_N_D_S(
			long companyId, long groupId, String name, String description,
			int scope, boolean andOperator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return doCountByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, false);
	}

	@Override
	public int filterCountByKeywords(
			long companyId, long groupId, String keywords, int scope)
		throws SystemException {

		return doCountByKeywords(companyId, groupId, keywords, scope, true);
	}

	@Override
	public int filterCountByC_G_N_D_S(
			long companyId, long groupId, String name, String description,
			int scope, boolean andOperator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return doCountByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, true);
	}

	@Override
	public List<DDLRecordSet> filterFindByKeywords(
			long companyId, long groupId, String keywords, int scope, int start,
			int end, OrderByComparator orderByComparator)
		throws SystemException {

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

		return filterFindByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, start,
			end, orderByComparator);
	}

	@Override
	public List<DDLRecordSet> filterFindByC_G_N_D_S(
			long companyId, long groupId, String name, String description,
			int scope, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return filterFindByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, start,
			end, orderByComparator);
	}

	@Override
	public List<DDLRecordSet> filterFindByC_G_N_D_S(
			long companyId, long groupId, String[] names, String[] descriptions,
			int scope, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return doFindByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, start,
			end, orderByComparator, true);
	}

	@Override
	public List<DDLRecordSet> findByKeywords(
			long companyId, long groupId, String keywords, int scope, int start,
			int end, OrderByComparator orderByComparator)
		throws SystemException {

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

		return findByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, start,
			end, orderByComparator);
	}

	@Override
	public List<DDLRecordSet> findByC_G_N_D_S(
			long companyId, long groupId, String name, String description,
			int scope, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);

		return findByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, start,
			end, orderByComparator);
	}

	@Override
	public List<DDLRecordSet> findByC_G_N_D_S(
			long companyId, long groupId, String[] names, String[] descriptions,
			int scope, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return doFindByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator, start,
			end, orderByComparator, false);
	}

	protected int doCountByC_G_N_D_S(
			long companyId, long groupId, String[] names, String[] descriptions,
			int scope, boolean andOperator, boolean inlineSQLHelper)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_N_D_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DDLRecordSet.class.getName(),
					"DDLRecordSet.recordSetId", groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(DDLRecordSet.groupId = ?) AND", StringPool.BLANK);
			}

			if (scope == DDLRecordSetConstants.SCOPE_ANY) {
				sql = StringUtil.replace(
					sql, "(DDLRecordSet.scope = ?) AND", StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(DDLRecordSet.name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "DDLRecordSet.description", StringPool.LIKE, true,
				descriptions);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (scope != DDLRecordSetConstants.SCOPE_ANY) {
				qPos.add(scope);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByKeywords(
			long companyId, long groupId, String keywords, int scope,
			boolean inlineSQLHelper)
		throws SystemException {

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

		return doCountByC_G_N_D_S(
			companyId, groupId, names, descriptions, scope, andOperator,
			inlineSQLHelper);
	}

	protected List<DDLRecordSet> doFindByC_G_N_D_S(
			long companyId, long groupId, String[] names, String[] descriptions,
			int scope, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator, boolean inlineSQLHelper)
		throws SystemException {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_N_D_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, DDLRecordSet.class.getName(),
					"DDLRecordSet.recordSetId", groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(DDLRecordSet.groupId = ?) AND", StringPool.BLANK);
			}

			if (scope == DDLRecordSetConstants.SCOPE_ANY) {
				sql = StringUtil.replace(
					sql, "(DDLRecordSet.scope = ?) AND", StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(DDLRecordSet.name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "DDLRecordSet.description", StringPool.LIKE, true,
				descriptions);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("DDLRecordSet", DDLRecordSetImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (scope != DDLRecordSetConstants.SCOPE_ANY) {
				qPos.add(scope);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			return (List<DDLRecordSet>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}