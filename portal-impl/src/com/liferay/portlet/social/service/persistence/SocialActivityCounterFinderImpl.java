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
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
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

	public static String FIND_AC_BY_G_C_C_N_S_E =
	SocialActivityCounterFinder.class.getName() + ".findAC_By_G_C_C_N_S_E";

	public static String FIND_AC_BY_G_N_S_E_1 =
	SocialActivityCounterFinder.class.getName() + ".findAC_ByG_N_S_E_1";

	public static String FIND_AC_BY_G_N_S_E_2 =
	SocialActivityCounterFinder.class.getName() + ".findAC_ByG_N_S_E_2";

	public static String FIND_U_BY_G_C_N_S_E =
		SocialActivityCounterFinder.class.getName() + ".findU_ByG_C_N_S_E";

	public int countU_ByG_N(long groupId, String[] orderBy)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_U_BY_G_C_N_S_E);

			StringBuilder orderByList =
				new StringBuilder(StringUtil.quote(orderBy[0], "'"));

			for (int i = 1; i < orderBy.length; i++) {
				orderByList.append(StringPool.COMMA);

				orderByList.append(
					StringUtil.quote(orderBy[i], "'"));
			}

			sql = StringUtil.replace(
				sql, "[$RANKING_COUNTER_NAMES$]", orderByList.toString());

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(PortalUtil.getClassNameId(User.class.getName()));
			qPos.add(SocialCounterPeriodUtil.getPeriodLength());
			qPos.add(SocialCounterPeriodUtil.getActivityDay());

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

	public List<SocialActivityCounter> findAC_By_G_C_C_N_S_E(
			long groupId, List<Long> userIds, String[] selectedCounters,
			int start, int end)
		throws SystemException {

		if (selectedCounters.length == 0) {
			return null;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_AC_BY_G_C_C_N_S_E);

			StringBuilder selectedCounterList =
				new StringBuilder(StringUtil.quote(selectedCounters[0], "'"));

			for (int i = 1; i < selectedCounters.length; i++) {
				selectedCounterList.append(StringPool.COMMA);

				selectedCounterList.append(
					StringUtil.quote(selectedCounters[i], "'"));
			}

			sql = StringUtil.replace(
				sql,
				new String[] {
					"[$SELECTED_COUNTER_NAMES$]",
					"[$USER_IDS$]"
				},
				new String[] {
					selectedCounterList.toString(),
					StringUtil.merge(userIds)
				});

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(PortalUtil.getClassNameId(User.class.getName()));
			qPos.add(SocialCounterPeriodUtil.getPeriodLength());
			qPos.add(SocialCounterPeriodUtil.getActivityDay());

			q.addEntity("SocialActivityCounter",
				SocialActivityCounterImpl.class);

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

	public List<SocialActivityCounter> findAC_ByG_N_S_E_1(
			long groupId, String counterName, int startPeriod, int endPeriod)
		throws SystemException {

		Serializable cacheKey = encodeCacheKey(
			"findAC_ByG_N_S_E",
			new Object[] {
				groupId, counterName, startPeriod, endPeriod
			});

		List<SocialActivityCounter> list = null;

		if (startPeriod != SocialCounterPeriodUtil.getStartPeriod()) {
			list = (List<SocialActivityCounter>)_portalCache.get(cacheKey);
		}

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = CustomSQLUtil.get(FIND_AC_BY_G_N_S_E_1);
				SQLQuery q = session.createSQLQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);
				qPos.add(counterName);
				qPos.add(startPeriod);
				qPos.add(endPeriod);

				List<Object[]> results = (List<Object[]>)QueryUtil.list(
					q, getDialect(), -1, -1);

				list = new ArrayList<SocialActivityCounter>();

				for (Object[] result : results) {
					SocialActivityCounter activityCounter =
						new SocialActivityCounterImpl();

					activityCounter.setName(
						GetterUtil.getString((Serializable)result[0]));
					activityCounter.setCurrentValue(
						GetterUtil.getInteger((Serializable)result[1]));
					activityCounter.setStartPeriod(
						GetterUtil.getInteger((Serializable)result[2]));
					activityCounter.setEndPeriod(
						GetterUtil.getInteger((Serializable)result[3]));

					list.add(activityCounter);
				}
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
			finally {
				if (list == null) {
					_portalCache.remove(cacheKey);
				}
				else {
					if (startPeriod !=
							SocialCounterPeriodUtil.getStartPeriod()) {

						_portalCache.put(cacheKey, list);
					}
				}

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivityCounter> findAC_ByG_N_S_E_2(
			long groupId, String counterName, int startPeriod, int endPeriod)
		throws SystemException {

		List<SocialActivityCounter> list = null;

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

			List<Object[]> results = (List<Object[]>)QueryUtil.list(
				q, getDialect(), -1, -1);

			list = new ArrayList<SocialActivityCounter>();

			for (Object[] result : results) {
				SocialActivityCounter activityCounter =
					new SocialActivityCounterImpl();

				activityCounter.setClassNameId(
					GetterUtil.getLong((Serializable)result[0]));
				activityCounter.setName(
					GetterUtil.getString((Serializable)result[1]));
				activityCounter.setCurrentValue(
					GetterUtil.getInteger((Serializable)result[2]));

				list.add(activityCounter);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		return list;
	}

	public List<Long> findU_ByG_N(
			long groupId, String[] orderBy, int start, int end)
		throws SystemException {

		if (orderBy.length == 0) {
			return null;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_U_BY_G_C_N_S_E);

			StringBuilder orderByList =
				new StringBuilder(StringUtil.quote(orderBy[0], "'"));

			for (int i = 1; i < orderBy.length; i++) {
				orderByList.append(StringPool.COMMA);

				orderByList.append(
					StringUtil.quote(orderBy[i], "'"));
			}

			sql = StringUtil.replace(
				sql, "[$RANKING_COUNTER_NAMES$]", orderByList.toString());

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

	protected Serializable encodeCacheKey(String methodName, Object[] args) {
		StringBundler sb = new StringBundler(args.length * 2 + 1);

		sb.append(methodName);

		for (Object arg : args) {
			sb.append(StringPool.PERIOD);
			sb.append(StringUtil.toHexString(arg));
		}

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				SocialActivityCounterFinder.class.getName());

		return cacheKeyGenerator.getCacheKey(sb);
	}

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		SocialActivityCounterFinder.class.getName());

}