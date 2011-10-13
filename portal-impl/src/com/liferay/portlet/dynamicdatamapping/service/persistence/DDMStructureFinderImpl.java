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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Eduardo Lundgren
 * @author Connor McKay
 */
public class DDMStructureFinderImpl
	extends BasePersistenceImpl<DDMStructure> implements DDMStructureFinder {

	public static String COUNT_BY_C_G_C_N_D_S =
		DDMStructureFinder.class.getName() + ".countByC_G_C_N_D_S";

	public static String FIND_BY_C_G_C_N_D_S =
		DDMStructureFinder.class.getName() + ".findByC_G_C_N_D_S";

	public int countByKeywords(
			long companyId, long groupId, long[] classNameIds, String keywords)
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

		return countByC_G_C_N_D_S(
			companyId, groupId, classNameIds, names, descriptions, null,
			andOperator);
	}

	public int countByC_G_C_N_D_S(
			long companyId, long groupId, long[] classNameIds, String name,
			String description, String storageType, boolean andOperator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] storageTypes = CustomSQLUtil.keywords(storageType, false);

		return countByC_G_C_N_D_S(
			companyId, groupId, classNameIds, names, descriptions,
			storageTypes, andOperator);
	}

	public int countByC_G_C_N_D_S(
			long companyId, long groupId, long[] classNameIds, String[] names,
			String[] descriptions, String[] storageTypes, boolean andOperator)
		throws SystemException {

		String[] classNameIdsString = null;

		if (classNameIds == null) {
			classNameIdsString = new String[] {null};
		}
		else {
			classNameIdsString = ArrayUtil.toStringArray(classNameIds);
		}

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		storageTypes = CustomSQLUtil.keywords(storageTypes, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_C_N_D_S);

			if (groupId <= 0) {
				sql = StringUtil.replace(sql, "(groupId = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "classNameId", StringPool.EQUAL, false,
				classNameIdsString);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false,
				descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "storageType", StringPool.LIKE, true, storageTypes);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(classNameIds, 2);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(storageTypes, 2);

			Iterator<Long> itr = q.list().iterator();

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

	public List<DDMStructure> findByKeywords(
			long companyId, long groupId, long[] classNameIds, String keywords,
			int start, int end, OrderByComparator orderByComparator)
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

		return findByC_G_C_N_D_S(
			companyId, groupId, classNameIds, names, descriptions, null,
			andOperator, start, end, orderByComparator);
	}

	public List<DDMStructure> findByC_G_C_N_D_S(
			long companyId, long groupId, long[] classNameIds, String name,
			String description, String storageType, boolean andOperator,
			int start, int end, OrderByComparator orderByComparator)
		throws SystemException {

		String[] names = CustomSQLUtil.keywords(name);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] storageTypes = CustomSQLUtil.keywords(storageType, false);

		return findByC_G_C_N_D_S(
			companyId, groupId, classNameIds, names, descriptions,
			storageTypes, andOperator, start, end, orderByComparator);
	}

	public List<DDMStructure> findByC_G_C_N_D_S(
			long companyId, long groupId, long[] classNameIds, String[] names,
			String[] descriptions, String[] storageTypes, boolean andOperator,
			int start, int end, OrderByComparator orderByComparator)
		throws SystemException {

		String[] classNameIdsString = null;

		if (classNameIds == null) {
			classNameIdsString = new String[] {null};
		}
		else {
			classNameIdsString = ArrayUtil.toStringArray(classNameIds);
		}

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		storageTypes = CustomSQLUtil.keywords(storageTypes, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_C_N_D_S);

			if (groupId <= 0) {
				sql = StringUtil.replace(sql, "(groupId = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "classNameId", StringPool.EQUAL, false,
				classNameIdsString);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(name)", StringPool.LIKE, false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false,
				descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "storageType", StringPool.LIKE, true, storageTypes);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				String orderByFields = StringUtil.merge(
					orderByComparator.getOrderByFields(), StringPool.COMMA);

				sql = StringUtil.replace(
					sql, "structureId DESC", orderByFields.concat(" DESC"));
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("DDMStructure", DDMStructureImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(classNameIds, 2);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);
			qPos.add(storageTypes, 2);

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

}