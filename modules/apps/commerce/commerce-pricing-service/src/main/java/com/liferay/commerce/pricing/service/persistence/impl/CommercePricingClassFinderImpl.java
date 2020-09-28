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

package com.liferay.commerce.pricing.service.persistence.impl;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.impl.CommercePricingClassImpl;
import com.liferay.commerce.pricing.service.persistence.CommercePricingClassFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassFinderImpl
	extends CommercePricingClassFinderBaseImpl
	implements CommercePricingClassFinder {

	public static final String COUNT_BY_CPDEFINITION_ID =
		CommercePricingClassFinder.class.getName() + ".countByCPDefinitionId";

	public static final String FIND_BY_CPDEFINITION_ID =
		CommercePricingClassFinder.class.getName() + ".findByCPDefinitionId";

	@Override
	public int countByCPDefinitionId(long cpDefinitionId, String title) {
		return countByCPDefinitionId(cpDefinitionId, title, false);
	}

	@Override
	public int countByCPDefinitionId(
		long cpDefinitionId, String title, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_CPDEFINITION_ID);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommercePricingClass.class.getName(),
					"CommercePricingClass.commercePricingClassId", null, null,
					new long[] {0}, null);
			}

			String[] keywords = _customSQL.keywords(title, true);

			if (Validator.isNotNull(title)) {
				sql = _customSQL.replaceKeywords(
					sql, "(LOWER(CommercePricingClass.title)", StringPool.LIKE,
					true, keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CommercePricingClass.title) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(cpDefinitionId);

			if (Validator.isNotNull(title)) {
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

	@Override
	public List<CommercePricingClass> findByCPDefinitionId(
		long cpDefinitionId, String title, int start, int end) {

		return findByCPDefinitionId(cpDefinitionId, title, start, end, false);
	}

	@Override
	public List<CommercePricingClass> findByCPDefinitionId(
		long cpDefinitionId, String title, int start, int end,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String[] keywords = _customSQL.keywords(title, true);

			String sql = _customSQL.get(getClass(), FIND_BY_CPDEFINITION_ID);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CommercePricingClass.class.getName(),
					"CommercePricingClass.commercePricingClassId", null, null,
					new long[] {0}, null);
			}

			if (Validator.isNotNull(title)) {
				sql = _customSQL.replaceKeywords(
					sql, "(LOWER(CommercePricingClass.title)", StringPool.LIKE,
					true, keywords);
				sql = _customSQL.replaceAndOperator(sql, false);
			}
			else {
				sql = StringUtil.removeSubstring(
					sql,
					" AND (LOWER(CommercePricingClass.title) LIKE ? " +
						"[$AND_OR_NULL_CHECK$])");
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommercePricingClassImpl.TABLE_NAME,
				CommercePricingClassImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(cpDefinitionId);

			if (Validator.isNotNull(title)) {
				queryPos.add(keywords, 2);
			}

			return (List<CommercePricingClass>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}