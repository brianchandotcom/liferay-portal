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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.impl.SocialActivityCounterImpl;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityCounterFinderImpl
	extends BasePersistenceImpl<SocialActivityCounter>
	implements SocialActivityCounterFinder {

	public static String COUNT_U_BY_G_C_N_S_E =
		SocialActivityCounterFinder.class.getName() + ".countU_ByG_C_N_S_E";

	public static String FIND_AC_BY_G_N_S_E_1 =
		SocialActivityCounterFinder.class.getName() + ".findAC_ByG_N_S_E_1";

	public static String FIND_AC_BY_G_N_S_E_2 =
		SocialActivityCounterFinder.class.getName() + ".findAC_ByG_N_S_E_2";

	public static String FIND_AC_BY_G_C_C_N_S_E =
		SocialActivityCounterFinder.class.getName() + ".findAC_By_G_C_C_N_S_E";

	public static String FIND_U_BY_G_C_N_S_E =
		SocialActivityCounterFinder.class.getName() + ".findU_ByG_C_N_S_E";

	public int countU_ByG_N(long groupId, String[] names)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_U_BY_G_C_N_S_E);

			sql = StringUtil.replace(sql, "[$NAME$]", getNames(names));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(PortalUtil.getClassNameId(User.class.getName()));
			qPos.add(SocialCounterPeriodUtil.getPeriodLength());
			qPos.add(SocialCounterPeriodUtil.getActivityDay());

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

	public List<SocialActivityCounter> findAC_ByG_N_S_E_1(
			long groupId, String name, int startPeriod, int endPeriod)
		throws SystemException {

		StringBundler sb = new StringBundler(8);

		sb.append(groupId);
		sb.append(StringPool.POUND);
		sb.append(name);
		sb.append(StringPool.POUND);
		sb.append(startPeriod);
		sb.append(StringPool.POUND);
		sb.append(endPeriod);
		sb.append(StringPool.POUND);

		String key = sb.toString();

		List<SocialActivityCounter> activityCounters = null;

		if (startPeriod != SocialCounterPeriodUtil.getStartPeriod()) {
			activityCounters =
				(List<SocialActivityCounter>)_activityCounters.get(key);
		}

		if (activityCounters != null) {
			return activityCounters;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_AC_BY_G_N_S_E_1);

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(startPeriod);
			qPos.add(endPeriod);

			activityCounters = new ArrayList<SocialActivityCounter>();

			Iterator<Object[]> itr = q.iterate();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				SocialActivityCounter activityCounter =
					new SocialActivityCounterImpl();

				activityCounter.setName(
					GetterUtil.getString((Serializable)array[0]));
				activityCounter.setCurrentValue(
					GetterUtil.getInteger((Serializable)array[1]));
				activityCounter.setStartPeriod(
					GetterUtil.getInteger((Serializable)array[2]));
				activityCounter.setEndPeriod(
					GetterUtil.getInteger((Serializable)array[3]));

				activityCounters.add(activityCounter);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			if (activityCounters == null) {
				_activityCounters.remove(key);
			}
			else {
				if (startPeriod != SocialCounterPeriodUtil.getStartPeriod()) {
					_activityCounters.put(key, activityCounters);
				}
			}

			closeSession(session);
		}

		return activityCounters;
	}

	public List<SocialActivityCounter> findAC_ByG_N_S_E_2(
			long groupId, String counterName, int startPeriod, int endPeriod)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_AC_BY_G_N_S_E_2);

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(counterName);
			qPos.add(startPeriod);
			qPos.add(endPeriod);

			List<SocialActivityCounter> activityCounters =
				new ArrayList<SocialActivityCounter>();

			Iterator<Object[]> itr = q.iterate();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				SocialActivityCounter activityCounter =
					new SocialActivityCounterImpl();

				activityCounter.setClassNameId(
					GetterUtil.getLong((Serializable)array[0]));
				activityCounter.setName(
					GetterUtil.getString((Serializable)array[1]));
				activityCounter.setCurrentValue(
					GetterUtil.getInteger((Serializable)array[2]));

				activityCounters.add(activityCounter);
			}

			return activityCounters;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivityCounter> findAC_By_G_C_C_N_S_E(
			long groupId, List<Long> userIds, String[] names, int start,
			int end)
		throws SystemException {

		if (names.length == 0) {
			return null;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_AC_BY_G_C_C_N_S_E);

			sql = StringUtil.replace(
				sql, new String[] {"[$CLASS_PK$]", "[$NAME$]"},
				new String[] {StringUtil.merge(userIds), getNames(names)});

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(PortalUtil.getClassNameId(User.class.getName()));
			qPos.add(SocialCounterPeriodUtil.getPeriodLength());
			qPos.add(SocialCounterPeriodUtil.getActivityDay());

			q.addEntity(
				"SocialActivityCounter", SocialActivityCounterImpl.class);

			return (List<SocialActivityCounter>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Long> findU_ByG_N(
			long groupId, String[] names, int start, int end)
		throws SystemException {

		if (names.length == 0) {
			return null;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_U_BY_G_C_N_S_E);

			sql = StringUtil.replace(sql, "[$NAME$]", getNames(names));

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(PortalUtil.getClassNameId(User.class.getName()));
			qPos.add(SocialCounterPeriodUtil.getPeriodLength());
			qPos.add(SocialCounterPeriodUtil.getActivityDay());

			return (List<Long>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getNames(String[] names) {
		if (names.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(names.length * 2 - 1);

		for (int i = 0; i < names.length; i++) {
			sb.append(StringUtil.quote(names[i], StringPool.APOSTROPHE));

			if ((i + 1) < names.length) {
				sb.append(StringPool.COMMA);
			}
		}

		return sb.toString();
	}

	private static PortalCache _activityCounters = MultiVMPoolUtil.getCache(
		SocialActivityCounterFinder.class.getName());

}