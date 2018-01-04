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

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLinkImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLinkFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class DDMStructureLinkFinderImpl
	extends DDMStructureLinkFinderBaseImpl implements DDMStructureLinkFinder {

	public static final String COUNT_BY_C_C_N_D =
		DDMStructureLinkFinder.class.getName() + ".countByC_C_N_D";

	public static final String FIND_BY_C_C_N_D =
		DDMStructureLinkFinder.class.getName() + ".findByC_C_N_D";

	@Override
	public int countByKeywords(
		long classNameId, long classPK, String keywords) {

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

		return doCountByC_C_N_D(
			classNameId, classPK, names, descriptions, andOperator);
	}

	@Override
	public List<DDMStructureLink> findByKeywords(
		long classNameId, long classPK, String keywords, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

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

		return doFindByC_C_N_D(
			classNameId, classPK, names, descriptions, andOperator, start, end,
			orderByComparator);
	}

	protected int doCountByC_C_N_D(
		long classNameId, long classPK, String[] names, String[] descriptions,
		boolean andOperator) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), COUNT_BY_C_C_N_D);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CAST_TEXT(DDMStructure.name))", StringPool.LIKE,
				false, names);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "DDMStructure.description", StringPool.LIKE, true,
				descriptions);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

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

	protected List<DDMStructureLink> doFindByC_C_N_D(
		long classNameId, long classPK, String[] names, String[] descriptions,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {

		names = CustomSQLUtil.keywords(names);
		descriptions = CustomSQLUtil.keywords(descriptions, false);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), FIND_BY_C_C_N_D);

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CAST_TEXT(DDMStructure.name))", StringPool.LIKE,
				false, names);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "DDMStructure.description", StringPool.LIKE, true,
				descriptions);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("DDMStructureLink", DDMStructureLinkImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(classPK);
			qPos.add(names, 2);
			qPos.add(descriptions, 2);

			return (List<DDMStructureLink>)QueryUtil.list(
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