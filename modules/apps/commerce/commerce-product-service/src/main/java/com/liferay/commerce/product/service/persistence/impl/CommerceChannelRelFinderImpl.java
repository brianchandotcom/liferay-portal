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

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.model.impl.CommerceChannelRelImpl;
import com.liferay.commerce.product.service.persistence.CommerceChannelRelFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelRelFinderImpl
	extends CommerceChannelRelFinderBaseImpl
	implements CommerceChannelRelFinder {

	public static final String COUNT_BY_C_C =
		CommerceChannelRelFinder.class.getName() + ".countByC_C";

	public static final String FIND_BY_C_C =
		CommerceChannelRelFinder.class.getName() + ".findByC_C";

	@Override
	public int countByC_C(String className, long classPK, String name) {
		return countByC_C(className, classPK, name, false);
	}

	@Override
	public int countByC_C(
		String className, long classPK, String name, boolean inlineSQLHelper) {

		if (!_isValidClassName(className)) {
			return 0;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_C_C);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, className, "CommerceChannel.commerceChannelId", null,
					null, new long[] {0}, null);
			}

			String[] keywords = _customSQL.keywords(name, true);

			if (Validator.isNotNull(name)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CommerceChannel.name)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CommerceChannel.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(PortalUtil.getClassNameId(className));
			queryPos.add(classPK);

			if (Validator.isNotNull(name)) {
				queryPos.add(keywords, 2);
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

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public int countByC_C(
		String className, long classPK, String classPKField, String name,
		boolean inlineSQLHelper) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<CommerceChannelRel> findByC_C(
		String className, long classPK, String name, int start, int end) {

		return findByC_C(className, classPK, name, start, end, false);
	}

	@Override
	public List<CommerceChannelRel> findByC_C(
		String className, long classPK, String name, int start, int end,
		boolean inlineSQLHelper) {

		if (!_isValidClassName(className)) {
			return Collections.emptyList();
		}

		Session session = null;

		try {
			session = openSession();

			String[] keywords = _customSQL.keywords(name, true);

			String sql = _customSQL.get(getClass(), FIND_BY_C_C);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, className, "CommerceChannel.commerceChannelId", null,
					null, new long[] {0}, null);
			}

			if (Validator.isNotNull(name)) {
				sql = _customSQL.replaceKeywords(
					sql, "LOWER(CommerceChannel.name)", StringPool.LIKE, true,
					keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CommerceChannel.name) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommerceChannelRelImpl.TABLE_NAME,
				CommerceChannelRelImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(PortalUtil.getClassNameId(className));
			queryPos.add(classPK);

			if (Validator.isNotNull(name)) {
				queryPos.add(keywords, 2);
			}

			return (List<CommerceChannelRel>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<CommerceChannelRel> findByC_C(
		String className, long classPK, String classPKField, String name,
		int start, int end, boolean inlineSQLHelper) {

		throw new UnsupportedOperationException();
	}

	private List<Long> _findClassNameIds() {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), _FIND_CLASS_NAME_IDS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("classNameId", Type.LONG);

			return (List<Long>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private boolean _isValidClassName(String className) {
		long classNameId = PortalUtil.getClassNameId(className);

		List<Long> validClassNameIds = _findClassNameIds();

		return validClassNameIds.contains(classNameId);
	}

	private static final String _FIND_CLASS_NAME_IDS =
		CommerceChannelRelFinder.class.getName() + ".findClassNameIds";

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}