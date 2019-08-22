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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureFinder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Lundgren
 * @author Connor McKay
 * @author Marcellus Tavares
 */
@Component(service = DDMStructureFinder.class)
public class DDMStructureFinderImpl
	extends DDMStructureFinderBaseImpl implements DDMStructureFinder {

	public static final String COUNT_BY_C_G_C_C_N_D =
		DDMStructureFinder.class.getName() + ".countByC_G_C_C_N_D";

	public static final String COUNT_BY_C_G_C_N_D_S_T_S =
		DDMStructureFinder.class.getName() + ".countByC_G_C_N_D_S_T_S";

	public static final String FIND_BY_C_G_C_C_N_D =
		DDMStructureFinder.class.getName() + ".findByC_G_C_C_N_D";

	public static final String FIND_BY_C_G_C_N_D_S_T_R =
		DDMStructureFinder.class.getName() + ".findByC_G_C_N_D_S_T_S";

	@Override
	public int countByKeywords(
		long companyId, long[] groupIds, long classNameId, long classPK,
		String keywords) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return doCountByC_G_C_C_N_D(
			companyId, groupIds, classNameId, classPK, names, descriptions,
			andOperator);
	}

	@Override
	public int countByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return countByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null,
			DDMStructureConstants.TYPE_DEFAULT, status, andOperator);
	}

	@Override
	public int countByC_G_C_S(
		long companyId, long[] groupIds, long classNameId, int status) {

		String[] names = _customSQL.keywords(StringPool.BLANK);
		String[] descriptions = _customSQL.keywords(StringPool.BLANK, false);

		return countByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null,
			DDMStructureConstants.TYPE_DEFAULT, status, true);
	}

	@Override
	public int countByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return countByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator);
	}

	@Override
	public int countByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator) {

		return doCountByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator, false);
	}

	@Override
	public int filterCountByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status) {

		return filterCountByKeywords(
			companyId, groupIds, classNameId, keywords,
			DDMStructureConstants.TYPE_DEFAULT, status);
	}

	@Override
	public int filterCountByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int type, int status) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterCountByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null, type,
			status, andOperator);
	}

	@Override
	public int filterCountByC_G_C_S(
		long companyId, long[] groupIds, long classNameId, int status) {

		String[] names = _customSQL.keywords(StringPool.BLANK);
		String[] descriptions = _customSQL.keywords(StringPool.BLANK, false);

		return filterCountByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null,
			DDMStructureConstants.TYPE_DEFAULT, status, true);
	}

	@Override
	public int filterCountByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return filterCountByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator);
	}

	@Override
	public int filterCountByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator) {

		return doCountByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator, true);
	}

	@Override
	public List<DDMStructure> filterFindByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int type, int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return filterFindByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null, type,
			status, andOperator, start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> filterFindByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return filterFindByKeywords(
			companyId, groupIds, classNameId, keywords,
			DDMStructureConstants.TYPE_DEFAULT, status, start, end,
			orderByComparator);
	}

	@Override
	public List<DDMStructure> filterFindByC_G_C_S(
		long companyId, long[] groupIds, long classNameId, int status,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = _customSQL.keywords(StringPool.BLANK);
		String[] descriptions = _customSQL.keywords(StringPool.BLANK, false);

		return filterFindByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null,
			DDMStructureConstants.TYPE_DEFAULT, status, true, start, end,
			orderByComparator);
	}

	@Override
	public List<DDMStructure> filterFindByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return filterFindByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator, start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> filterFindByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return doFindByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator, start, end, orderByComparator, true);
	}

	@Override
	public List<DDMStructure> findByKeywords(
		long companyId, long[] groupIds, long classNameId, long classPK,
		String keywords, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return doFindByC_G_C_C_N_D(
			companyId, groupIds, classNameId, classPK, names, descriptions,
			andOperator, start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> findByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int status, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = null;
		String[] descriptions = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			names = _customSQL.keywords(keywords);
			descriptions = _customSQL.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return findByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null,
			DDMStructureConstants.TYPE_DEFAULT, status, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public List<DDMStructure> findByC_G_C_S(
		long companyId, long[] groupIds, long classNameId, int status,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = _customSQL.keywords(StringPool.BLANK);
		String[] descriptions = _customSQL.keywords(StringPool.BLANK, false);

		return findByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, null,
			DDMStructureConstants.TYPE_DEFAULT, status, true, start, end,
			orderByComparator);
	}

	@Override
	public List<DDMStructure> findByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String name,
		String description, String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = _customSQL.keywords(name);
		String[] descriptions = _customSQL.keywords(description, false);

		return findByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator, start, end, orderByComparator);
	}

	@Override
	public List<DDMStructure> findByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		return doFindByC_G_C_N_D_S_T_S(
			companyId, groupIds, classNameId, names, descriptions, storageType,
			type, status, andOperator, start, end, orderByComparator, false);
	}

	protected int doCountByC_G_C_C_N_D(
		long companyId, long[] groupIds, long classNameId, long classPK,
		String[] names, String[] descriptions, boolean andOperator) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_G_C_C_N_D);

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(DDMStructure.name))", StringPool.LIKE,
				false, names);

			sql = _customSQL.replaceKeywords(
				sql, "DDMStructure.description", StringPool.LIKE, true,
				descriptions);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(classNameId);
			qPos.add(classPK);
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

	protected int doCountByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator, boolean inlineSQLHelper) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_G_C_N_D_S_T_S);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					_ddmPermissionSupport.getStructureModelResourceName(
						classNameId),
					"DDMStructure.structureId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = StringUtil.replace(sql, "[$STATUS$]", getStatus(status));
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(DDMStructure.name))", StringPool.LIKE,
				false, names);

			sql = _customSQL.replaceKeywords(
				sql, "DDMStructure.description", StringPool.LIKE, true,
				descriptions);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(classNameId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(storageType);
			qPos.add(storageType);
			qPos.add(type);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
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

	protected List<DDMStructure> doFindByC_G_C_C_N_D(
		long companyId, long[] groupIds, long classNameId, long classPK,
		String[] names, String[] descriptions, boolean andOperator, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_G_C_C_N_D);

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(DDMStructure.name))", StringPool.LIKE,
				false, names);
			sql = _customSQL.replaceKeywords(
				sql, "DDMStructure.description", StringPool.LIKE, true,
				descriptions);
			sql = _customSQL.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = _customSQL.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("DDMStructure", DDMStructureImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(classNameId);
			qPos.add(classPK);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			return (List<DDMStructure>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<DDMStructure> doFindByC_G_C_N_D_S_T_S(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, String storageType, int type, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator,
		boolean inlineSQLHelper) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_G_C_N_D_S_T_R);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					_ddmPermissionSupport.getStructureModelResourceName(
						classNameId),
					"DDMStructure.structureId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = StringUtil.replace(sql, "[$STATUS$]", getStatus(status));
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(DDMStructure.name))", StringPool.LIKE,
				false, names);
			sql = _customSQL.replaceKeywords(
				sql, "DDMStructure.description", StringPool.LIKE, true,
				descriptions);
			sql = _customSQL.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = _customSQL.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("DDMStructure", DDMStructureImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			qPos.add(classNameId);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(storageType);
			qPos.add(storageType);
			qPos.add(type);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			return (List<DDMStructure>)QueryUtil.list(
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
		if (ArrayUtil.isEmpty(groupIds)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(groupIds.length + 1);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < (groupIds.length - 1); i++) {
			sb.append("DDMStructure.groupId = ? OR ");
		}

		sb.append("DDMStructure.groupId = ?) AND");

		return sb.toString();
	}

	protected String getStatus(int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("AND EXISTS (SELECT 1 FROM DDMStructureVersion WHERE ");
		sb.append("(DDMStructureVersion.structureId = ");
		sb.append("DDMStructure.structureId) AND ");
		sb.append("(DDMStructureVersion.version = DDMStructure.version) AND ");
		sb.append("(DDMStructureVersion.status = ?))");

		return sb.toString();
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

}