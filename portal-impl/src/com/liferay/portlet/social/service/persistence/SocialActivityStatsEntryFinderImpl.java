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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivityStatsEntry;
import com.liferay.portlet.social.model.impl.SocialActivityStatsEntryImpl;
import com.liferay.portlet.social.util.SocialStatsUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityStatsEntryFinderImpl
	extends BasePersistenceImpl<SocialActivityStatsEntry>
	implements SocialActivityStatsEntryFinder {

	public static String COUNT_TOP_USERS_BY_COUNTERS =
		SocialActivityStatsEntryFinder.class.getName() +
			".countTopUsersByCounters";

	public static String FIND_STATS_BY_NAME_AND_PERIOD =
		SocialActivityStatsEntryFinder.class.getName() +
			".findStatsByNameAndPeriod";

	public static String FIND_STATS_DISTRIBUTION_BY_NAME_AND_PERIOD =
		SocialActivityStatsEntryFinder.class.getName() +
			".findStatsDistributionByNameAndPeriod";

	public static String FIND_TAGS_BY_COUNTER =
		SocialActivityStatsEntryFinder.class.getName() +
			".findTagsByCounter";

	public static String FIND_TOP_USERS_BY_COUNTERS =
		SocialActivityStatsEntryFinder.class.getName() +
			".findTopUsersByCounters";

	public static String FIND_USER_STATS_BY_COUNTERS =
		SocialActivityStatsEntryFinder.class.getName() +
			".findUserStatsByCounters";

	public int countTopUsersByCounters(
			long groupId, String[] rankingCounters)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_TOP_USERS_BY_COUNTERS);

			StringBuilder rankingCounterList =
				new StringBuilder(StringUtil.quote(rankingCounters[0], "'"));

			for (int i = 1; i < rankingCounters.length; i++) {
				rankingCounterList.append(StringPool.COMMA);

				rankingCounterList.append(
					StringUtil.quote(rankingCounters[i], "'"));
			}

			sql = StringUtil.replace(
				sql,
				new String[] {
					"[$RANKING_COUNTER_NAMES$]",
					"[$CURRENT_STAT_PERIOD_LENGTH$]",
					"[$ACTIVITY_DATE$]",
					"[$CLASSNAME_ID$]",
					"[$GROUP_ID$]"
				},
				new String[] {
					rankingCounterList.toString(),
					String.valueOf(SocialStatsUtil.getStatPeriodLength()),
					String.valueOf(SocialStatsUtil.getActivityDay()),
					String.valueOf(PortalUtil.getClassNameId(
						User.class.getName())),
					String.valueOf(groupId)
				});

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public List<SocialActivityStatsEntry> findStatsByNameAndPeriod(
			long groupId, String statName, int statPeriodStart,
			int statPeriodEnd)
		throws SystemException {

		Serializable cacheKey = encodeCacheKey(
			"findStatsByNameAndPeriod",
			new Object[] {
				groupId, statName, statPeriodStart, statPeriodEnd
			});

		List<SocialActivityStatsEntry> list = null;

		if (statPeriodStart != SocialStatsUtil.getCurrentPeriodStart()) {
			list = (List<SocialActivityStatsEntry>)_portalCache.get(cacheKey);
		}

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = CustomSQLUtil.get(FIND_STATS_BY_NAME_AND_PERIOD);
				SQLQuery q = session.createSQLQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);
				qPos.add(statName);
				qPos.add(statPeriodStart);
				qPos.add(statPeriodEnd);

				List<Object[]> results = (List<Object[]>)QueryUtil.list(
					q, getDialect(), -1, -1);

				list = new ArrayList<SocialActivityStatsEntry>();

				for (Object[] result : results) {
					SocialActivityStatsEntry entry =
						new SocialActivityStatsEntryImpl();

					entry.setStatName((String)result[0]);
					entry.setCurrentValue(((Number)result[1]).intValue());
					entry.setStatPeriodStart(((Number)result[2]).intValue());
					entry.setStatPeriodEnd(((Number)result[3]).intValue());

					list.add(entry);
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
					if (statPeriodStart !=
							SocialStatsUtil.getCurrentPeriodStart()) {

						_portalCache.put(cacheKey, list);
					}
				}

				closeSession(session);
			}
		}

		return list;
	}

	public List<SocialActivityStatsEntry> findStatsDistributionByNameAndPeriod(
			long groupId, String statName, int statPeriodStart,
			int statPeriodEnd)
		throws SystemException {

		List<SocialActivityStatsEntry> list = null;

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				FIND_STATS_DISTRIBUTION_BY_NAME_AND_PERIOD);
			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(statName);
			qPos.add(statPeriodStart);
			qPos.add(statPeriodEnd);

			List<Object[]> results = (List<Object[]>)QueryUtil.list(
				q, getDialect(), -1, -1);

			list = new ArrayList<SocialActivityStatsEntry>();

			for (Object[] result : results) {
				SocialActivityStatsEntry entry =
					new SocialActivityStatsEntryImpl();

				entry.setClassNameId(((Number)result[0]).longValue());
				entry.setStatName((String)result[1]);
				entry.setCurrentValue(((Number)result[2]).intValue());

				list.add(entry);
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

	public List<SocialActivityStatsEntry> findTagsByCounter(
			long groupId, String statName, int statPeriodStart,
			int statPeriodEnd)
		throws SystemException {

		List<SocialActivityStatsEntry> list = null;

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_TAGS_BY_COUNTER);
			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(statName);
			qPos.add(statPeriodStart);
			qPos.add(statPeriodEnd);

			List<Object[]> results = (List<Object[]>)QueryUtil.list(
				q, getDialect(), -1, -1);

			list = new ArrayList<SocialActivityStatsEntry>();

			for (Object[] result : results) {
				SocialActivityStatsEntry entry =
					new SocialActivityStatsEntryImpl();

				entry.setClassPK(((Number)result[0]).longValue());
				entry.setStatName((String)result[1]);
				entry.setCurrentValue(((Number)result[3]).intValue());

				list.add(entry);
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

	public List<Long> findTopUsersByCounters(
			long groupId, String[] rankingCounters, int start, int end)
		throws SystemException {

		if (rankingCounters.length == 0) {
			return null;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_TOP_USERS_BY_COUNTERS);

			StringBuilder rankingCounterList =
				new StringBuilder(StringUtil.quote(rankingCounters[0], "'"));

			for (int i = 1; i < rankingCounters.length; i++) {
				rankingCounterList.append(StringPool.COMMA);

				rankingCounterList.append(
					StringUtil.quote(rankingCounters[i], "'"));
			}

			sql = StringUtil.replace(
				sql,
				new String[] {
					"[$RANKING_COUNTER_NAMES$]",
					"[$CURRENT_STAT_PERIOD_LENGTH$]",
					"[$ACTIVITY_DATE$]",
					"[$CLASSNAME_ID$]",
					"[$GROUP_ID$]"
				},
				new String[] {
					rankingCounterList.toString(),
					String.valueOf(SocialStatsUtil.getStatPeriodLength()),
					String.valueOf(SocialStatsUtil.getActivityDay()),
					String.valueOf(PortalUtil.getClassNameId(
						User.class.getName())),
					String.valueOf(groupId)
				});

			SQLQuery q = session.createSQLQuery(sql);

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

	public List<SocialActivityStatsEntry> findUserStatsByCounters(
			long groupId, List<Long> userIds, String[] selectedCounters,
			int start, int end)
		throws SystemException {

		if (selectedCounters.length == 0) {
			return null;
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_USER_STATS_BY_COUNTERS);

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
					"[$CURRENT_STAT_PERIOD_LENGTH$]",
					"[$ACTIVITY_DATE$]",
					"[$CLASSNAME_ID$]",
					"[$GROUP_ID$]",
					"[$USER_IDS$]"
				},
				new String[] {
					selectedCounterList.toString(),
					String.valueOf(SocialStatsUtil.getStatPeriodLength()),
					String.valueOf(SocialStatsUtil.getActivityDay()),
					String.valueOf(PortalUtil.getClassNameId(
						User.class.getName())),
					String.valueOf(groupId),
					StringUtil.merge(userIds)
				});

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("SocialActivityStats",
				SocialActivityStatsEntryImpl.class);

			return (List<SocialActivityStatsEntry>)QueryUtil.list(
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
		StringBundler sb = new StringBundler(args.length * 2 + 3);

		sb.append(methodName);

		for (Object arg : args) {
			sb.append(StringPool.PERIOD);
			sb.append(StringUtil.toHexString(arg));
		}

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				SocialActivityStatsEntryFinder.class.getName());

		return cacheKeyGenerator.getCacheKey(sb);
	}

	private static final String _CACHE_NAME =
		SocialActivityStatsEntryFinder.class.getName();

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		_CACHE_NAME);

}