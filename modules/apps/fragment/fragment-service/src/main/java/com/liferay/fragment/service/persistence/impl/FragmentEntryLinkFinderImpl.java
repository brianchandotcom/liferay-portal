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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.model.impl.FragmentEntryLinkImpl;
import com.liferay.fragment.service.persistence.FragmentEntryLinkFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Iterator;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryLinkFinderImpl
	extends FragmentEntryLinkFinderBaseImpl implements FragmentEntryLinkFinder {

	public static final String COUNT_BY_G_F =
		FragmentEntryLinkFinder.class.getName() + ".countByG_F";

	public static final String COUNT_BY_G_F_C =
		FragmentEntryLinkFinder.class.getName() + ".countByG_F_C";

	public static final String COUNT_BY_G_F_C_L =
		FragmentEntryLinkFinder.class.getName() + ".countByG_F_C_L";

	public static final String FIND_BY_G_F =
		FragmentEntryLinkFinder.class.getName() + ".findByG_F";

	public static final String FIND_BY_G_F_C =
		FragmentEntryLinkFinder.class.getName() + ".findByG_F_C";

	public static final String FIND_BY_G_F_C_L =
		FragmentEntryLinkFinder.class.getName() + ".findByG_F_C_L";

	@Override
	public int countByG_F(long groupId, long fragmentEntryId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_F);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(fragmentEntryId);

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

	@Override
	public int countByG_F_C(
		long groupId, long fragmentEntryId, long classNameId) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_F_C);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(fragmentEntryId);
			qPos.add(classNameId);

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

	@Override
	public int countByG_F_C_L(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateEntryType) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_G_F_C_L);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(fragmentEntryId);
			qPos.add(classNameId);
			qPos.add(layoutPageTemplateEntryType);

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

	@Override
	public List<FragmentEntryLink> findByG_F(
		long groupId, long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_F);

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("FragmentEntryLink", FragmentEntryLinkImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(fragmentEntryId);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<FragmentEntryLink> findByG_F_C(
		long groupId, long fragmentEntryId, long classNameId, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_F_C);

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("FragmentEntryLink", FragmentEntryLinkImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(fragmentEntryId);
			qPos.add(classNameId);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<FragmentEntryLink> findByG_F_C_L(
		long groupId, long fragmentEntryId, long classNameId,
		int layoutPageTemplateEntryType, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_F_C_L);

			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("FragmentEntryLink", FragmentEntryLinkImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(fragmentEntryId);
			qPos.add(classNameId);
			qPos.add(layoutPageTemplateEntryType);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}