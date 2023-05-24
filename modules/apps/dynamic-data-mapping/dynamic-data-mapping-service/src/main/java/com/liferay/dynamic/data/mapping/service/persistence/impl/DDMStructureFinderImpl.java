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
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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

	public static final String COUNT_BY_C_G_C_N_D =
		DDMStructureFinder.class.getName() + ".countByC_G_C_N_D";

	public static final String FIND_BY_C_G_C_N_D =
		DDMStructureFinder.class.getName() + ".findByC_G_C_N_D";

	@Override
	public int countByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords) {

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

		return doCountByC_G_C_N_D(
			companyId, groupIds, classNameId, names, descriptions, andOperator,
			false);
	}

	@Override
	public int filterCountByC_G_C(
		long companyId, long[] groupIds, long classNameId) {

		String[] names = _customSQL.keywords(StringPool.BLANK);
		String[] descriptions = _customSQL.keywords(StringPool.BLANK, false);

		return doCountByC_G_C_N_D(
			companyId, groupIds, classNameId, names, descriptions, true, true);
	}

	@Override
	public List<DDMStructure> filterFindByC_G_C(
		long companyId, long[] groupIds, long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = _customSQL.keywords(StringPool.BLANK);
		String[] descriptions = _customSQL.keywords(StringPool.BLANK, false);

		return doFindByC_G_C_N_D(
			companyId, groupIds, classNameId, names, descriptions, true, start,
			end, orderByComparator, true);
	}

	@Override
	public List<DDMStructure> findByKeywords(
		long companyId, long[] groupIds, long classNameId, String keywords,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {

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

		return doFindByC_G_C_N_D(
			companyId, groupIds, classNameId, names, descriptions, andOperator,
			start, end, orderByComparator, false);
	}

	@Override
	public List<DDMStructure> findByC_G_C(
		long companyId, long[] groupIds, long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		String[] names = _customSQL.keywords(StringPool.BLANK);
		String[] descriptions = _customSQL.keywords(StringPool.BLANK, false);

		return doFindByC_G_C_N_D(
			companyId, groupIds, classNameId, names, descriptions, true, start,
			end, orderByComparator, false);
	}

	protected int doCountByC_G_C_N_D(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, boolean andOperator, boolean inlineSQLHelper) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_G_C_N_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					_ddmPermissionSupport.getStructureModelResourceName(
						classNameId),
					"DDMStructure.structureId", groupIds);
			}

			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = _customSQL.replaceKeywords(
				sql, "LOWER(CAST_TEXT(DDMStructure.name))", StringPool.LIKE,
				false, names);

			sql = _customSQL.replaceKeywords(
				sql, "DDMStructure.description", StringPool.LIKE, true,
				descriptions);

			sql = _customSQL.replaceAndOperator(sql, andOperator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (groupIds != null) {
				queryPos.add(groupIds);
			}

			queryPos.add(classNameId);
			queryPos.add(names, 2);
			queryPos.add(descriptions, 2);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		finally {
			closeSession(session);
		}
	}

	protected List<DDMStructure> doFindByC_G_C_N_D(
		long companyId, long[] groupIds, long classNameId, String[] names,
		String[] descriptions, boolean andOperator, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator,
		boolean inlineSQLHelper) {

		names = _customSQL.keywords(names);
		descriptions = _customSQL.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_G_C_N_D);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql,
					_ddmPermissionSupport.getStructureModelResourceName(
						classNameId),
					"DDMStructure.structureId", groupIds);
			}

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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("DDMStructure", DDMStructureImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (groupIds != null) {
				queryPos.add(groupIds);
			}

			queryPos.add(classNameId);
			queryPos.add(names, 2);
			queryPos.add(descriptions, 2);

			return (List<DDMStructure>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
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

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

}