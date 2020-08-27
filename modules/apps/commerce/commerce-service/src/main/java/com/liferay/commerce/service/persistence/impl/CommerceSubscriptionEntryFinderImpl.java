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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.model.impl.CommerceSubscriptionEntryImpl;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;
import java.util.List;

/**
 * @author Luca Pellizzon
 */
public class CommerceSubscriptionEntryFinderImpl
	extends CommerceSubscriptionEntryFinderBaseImpl
	implements CommerceSubscriptionEntryFinder {

	public static final String FIND_BY_DELIVERY_NEXT_ITERATION_DATE =
		CommerceSubscriptionEntryFinder.class.getName() +
			".findByDeliveryNextIterationDate";

	public static final String FIND_BY_NEXT_ITERATION_DATE =
		CommerceSubscriptionEntryFinder.class.getName() +
			".findByNextIterationDate";

	public static final String FIND_BY_A_S =
		CommerceSubscriptionEntryFinder.class.getName() + ".findByA_S";

	@Override
	public List<CommerceSubscriptionEntry> findByDeliveryNextIterationDate(
		Date nextIterationDate) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_DELIVERY_NEXT_ITERATION_DATE);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommerceSubscriptionEntryImpl.TABLE_NAME,
				CommerceSubscriptionEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (nextIterationDate != null) {
				queryPos.add(nextIterationDate);
			}

			return (List<CommerceSubscriptionEntry>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceSubscriptionEntry> findByNextIterationDate(
		Date nextIterationDate) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_NEXT_ITERATION_DATE);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommerceSubscriptionEntryImpl.TABLE_NAME,
				CommerceSubscriptionEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (nextIterationDate != null) {
				queryPos.add(nextIterationDate);
			}

			return (List<CommerceSubscriptionEntry>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceSubscriptionEntry> findByA_S(
		long commerceAccountId, long subscriptionStatus) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_A_S);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				CommerceSubscriptionEntryImpl.TABLE_NAME,
				CommerceSubscriptionEntryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(commerceAccountId);
			queryPos.add(subscriptionStatus);

			return (List<CommerceSubscriptionEntry>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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