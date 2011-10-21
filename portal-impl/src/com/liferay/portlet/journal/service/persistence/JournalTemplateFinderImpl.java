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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Prakash Reddy
 * @author Connor McKay
 */
public class JournalTemplateFinderImpl
	extends BasePersistenceImpl<JournalTemplate>
	implements JournalTemplateFinder {

	public static String COUNT_BY_C_G_T_S_N_D =
		JournalTemplateFinder.class.getName() + ".countByC_G_T_S_N_D";

	public static String FIND_BY_C_G_T_S_N_D =
		JournalTemplateFinder.class.getName() + ".findByC_G_T_S_N_D";

	public int countByKeywords(
			long companyId, long[] groupIds, String keywords,
			String structureId, String structureIdComparator)
		throws SystemException {

		String[] templateIds = null;
		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			templateIds = CustomSQLUtil.keywords(keywords, false);
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return doCountByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, false);
	}

	public int countByC_G_T_S_N_D(
			long companyId, long[] groupIds, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator)
		throws SystemException {

		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return doCountByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, false);
	}

	public int filterCountByKeywords(
			long companyId, long[] groupIds, String keywords,
			String structureId, String structureIdComparator)
		throws SystemException {

		String[] templateIds = null;
		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			templateIds = CustomSQLUtil.keywords(keywords, false);
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return doCountByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, true);
	}

	public int filterCountByC_G_T_S_N_D(
			long companyId, long[] groupIds, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator)
		throws SystemException {

		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return doCountByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, true);
	}

	public List<JournalTemplate> filterFindByKeywords(
			long companyId, long[] groupIds, String keywords,
			String structureId, String structureIdComparator, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		String[] templateIds = null;
		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			templateIds = CustomSQLUtil.keywords(keywords, false);
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return doFindByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, start, end,
			obc, true);
	}

	public List<JournalTemplate> filterFindByC_G_T_S_N_D(
			long companyId, long[] groupIds, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return doFindByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, start, end,
			obc, true);
	}

	public List<JournalTemplate> findByKeywords(
			long companyId, long[] groupIds, String keywords,
			String structureId, String structureIdComparator, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		String[] templateIds = null;
		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			templateIds = CustomSQLUtil.keywords(keywords, false);
			names = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords);
		}
		else {
			andOperator = true;
		}

		return doFindByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, start, end,
			obc, false);
	}

	public List<JournalTemplate> findByC_G_T_S_N_D(
			long companyId, long[] groupIds, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description);

		return doFindByC_G_T_S_N_D(
			companyId, groupIds, templateIds, structureId,
			structureIdComparator, names, descriptions, andOperator, start, end,
			obc, false);
	}

	protected int doCountByC_G_T_S_N_D(
			long companyId, long[] groupIds, String[] templateIds,
			String structureId, String structureIdComparator, String[] names,
			String[] descriptions, boolean andOperator, boolean inlineSQLHelper)
		throws SystemException {

		templateIds = CustomSQLUtil.keywords(templateIds, false);
		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_T_S_N_D);

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = CustomSQLUtil.replaceKeywords(
				sql, "templateId", StringPool.LIKE, false, templateIds);

			if (structureIdComparator.equals(StringPool.NOT_LIKE)) {
				sql = replaceStructureIdComparator(sql);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(description)", StringPool.LIKE, true, descriptions);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalTemplate.class.getName(), "JournalTemplate.id_",
					groupIds);

				sql = StringUtil.replace(
					sql, "(companyId", "(JournalTemplate.companyId");

				sql = StringUtil.replace(
					sql, "(name", "(JournalTemplate.name");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupIds);
			qPos.add(templateIds, 2);

			if (structureIdComparator.equals(StringPool.LIKE)) {
				qPos.add(structureId);
				qPos.add(structureId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			if (structureIdComparator.equals(StringPool.NOT_LIKE)) {
				if (CustomSQLUtil.isVendorOracle()) {
				}
				else {
					qPos.add(structureId);
				}
			}

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

	protected List<JournalTemplate> doFindByC_G_T_S_N_D(
			long companyId, long[] groupIds, String[] templateIds,
			String structureId, String structureIdComparator, String[] names,
			String[] descriptions, boolean andOperator, int start, int end,
			OrderByComparator obc, boolean inlineSQLHelper)
		throws SystemException {

		templateIds = CustomSQLUtil.keywords(templateIds, false);
		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_T_S_N_D);

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = CustomSQLUtil.replaceKeywords(
				sql, "templateId", StringPool.LIKE, false, templateIds);

			if (structureIdComparator.equals(StringPool.NOT_LIKE)) {
				sql = replaceStructureIdComparator(sql);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(description)", StringPool.LIKE, true, descriptions);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
						sql, JournalTemplate.class.getName(),
						"JournalTemplate.id_", groupIds);

				sql = StringUtil.replace(
					sql, "(companyId", "(JournalTemplate.companyId");

				sql = StringUtil.replace(
					sql, "(name", "(JournalTemplate.name");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("JournalTemplate", JournalTemplateImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupIds);
			qPos.add(templateIds, 2);

			if (structureIdComparator.equals(StringPool.LIKE)) {
				qPos.add(structureId);
				qPos.add(structureId);
			}

			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			if (structureIdComparator.equals(StringPool.NOT_LIKE)) {
				if (CustomSQLUtil.isVendorOracle()) {
				}
				else {
					qPos.add(structureId);
				}
			}

			return (List<JournalTemplate>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getGroupIds(long[] groupIds) {
		if (groupIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(groupIds.length + 2);

		sb.append(" (groupId = ? ");

		for (int i = 1; i < groupIds.length; i++) {
			sb.append(" OR groupId = ? ");
		}

		sb.append(") AND ");

		return sb.toString();
	}

	protected String replaceStructureIdComparator(String sql) {
		String insertSQL = "structureId NOT LIKE ? AND structureId IS NOT NULL";

		if (CustomSQLUtil.isVendorOracle()) {
			insertSQL = "structureId IS NOT NULL";
		}

		insertSQL = " AND (" + insertSQL + ") ";

		String removeSQL =
			"(structureId LIKE ? [$AND_OR_NULL_CHECK$]) [$AND_OR_CONNECTOR$]";

		sql = StringUtil.replace(sql, removeSQL, StringPool.BLANK);

		int pos = sql.indexOf("ORDER BY");

		if (pos == -1) {
			sql = sql + insertSQL;
		}
		else {
			sql = StringUtil.insert(sql, insertSQL, pos);
		}

		return sql;
	}

}