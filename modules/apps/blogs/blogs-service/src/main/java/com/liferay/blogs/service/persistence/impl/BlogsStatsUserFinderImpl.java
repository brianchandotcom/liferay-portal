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

package com.liferay.blogs.service.persistence.impl;

import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.blogs.service.persistence.BlogsStatsUserFinder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = BlogsStatsUserFinder.class)
public class BlogsStatsUserFinderImpl
	extends BlogsStatsUserFinderBaseImpl implements BlogsStatsUserFinder {

	public static final String COUNT_BY_ORGANIZATION_IDS =
		BlogsStatsUserFinder.class.getName() + ".countByOrganizationIds";

	public static final String FIND_BY_GROUP_IDS =
		BlogsStatsUserFinder.class.getName() + ".findByGroupIds";

	public static final String FIND_BY_ORGANIZATION_IDS =
		BlogsStatsUserFinder.class.getName() + ".findByOrganizationIds";

	@Override
	public int countByOrganizationId(long organizationId) {
		return countByOrganizationIds(ListUtil.fromArray(organizationId));
	}

	@Override
	public int countByOrganizationIds(List<Long> organizationIds) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_ORGANIZATION_IDS);

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			for (Long organizationId : organizationIds) {
				queryPos.add(organizationId);
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
	public List<BlogsStatsUser> findByGroupIds(
		long companyId, long groupId, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_GROUP_IDS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar("userId", Type.LONG);
			sqlQuery.addScalar("lastPostDate", Type.TIMESTAMP);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);
			queryPos.add(groupId);
			queryPos.add(groupId);
			queryPos.add(groupId);

			List<BlogsStatsUser> statsUsers = new ArrayList<>();

			Iterator<Object[]> iterator = (Iterator<Object[]>)QueryUtil.iterate(
				sqlQuery, getDialect(), start, end);

			while (iterator.hasNext()) {
				Object[] array = iterator.next();

				long userId = (Long)array[0];
				Date lastPostDate = (Date)array[1];

				List<BlogsStatsUser> curStatsUsers =
					BlogsStatsUserUtil.findByU_L(userId, lastPostDate);

				if (!curStatsUsers.isEmpty()) {
					BlogsStatsUser statsUser = curStatsUsers.get(0);

					statsUsers.add(statsUser);
				}
			}

			return statsUsers;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<BlogsStatsUser> findByOrganizationId(
		long organizationId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return findByOrganizationIds(
			ListUtil.fromArray(organizationId), start, end, orderByComparator);
	}

	@Override
	public List<BlogsStatsUser> findByOrganizationIds(
		List<Long> organizationIds, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_ORGANIZATION_IDS);

			sql = StringUtil.replace(
				sql, "[$ORGANIZATION_ID$]",
				getOrganizationIds(organizationIds));
			sql = _customSQL.replaceOrderBy(sql, orderByComparator);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("BlogsStatsUser", BlogsStatsUserImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			for (Long organizationId : organizationIds) {
				queryPos.add(organizationId);
			}

			return (List<BlogsStatsUser>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getOrganizationIds(List<Long> organizationIds) {
		if (organizationIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((organizationIds.size() * 2) - 1);

		for (int i = 0; i < organizationIds.size(); i++) {
			sb.append("Users_Orgs.organizationId = ? ");

			if ((i + 1) != organizationIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

	@Reference
	private CustomSQL _customSQL;

}