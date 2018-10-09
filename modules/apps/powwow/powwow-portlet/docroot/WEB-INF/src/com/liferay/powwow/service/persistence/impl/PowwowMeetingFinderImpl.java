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

package com.liferay.powwow.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowMeetingConstants;
import com.liferay.powwow.model.impl.PowwowMeetingImpl;
import com.liferay.powwow.service.persistence.PowwowMeetingFinder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Matthew Kong
 */
public class PowwowMeetingFinderImpl
	extends PowwowMeetingFinderBaseImpl implements PowwowMeetingFinder {

	public static final String COUNT_BY_U_S =
		PowwowMeetingFinder.class.getName() + ".countByU_S";

	public static final String FIND_BY_U_S =
		PowwowMeetingFinder.class.getName() + ".findByU_S";

	@Override
	public int countByU_S(long userId, int[] statuses) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), COUNT_BY_U_S);

			sql = StringUtil.replace(
				sql, "[$STATUSES$]", getStatusesSQL(statuses));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			User user = UserLocalServiceUtil.fetchUser(userId);

			if (user == null) {
				return 0;
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(user.getUserId());
			qPos.add(user.getUserId());
			qPos.add(user.getEmailAddress());

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
	public List<PowwowMeeting> findByU_S(
		long userId, int[] statuses, int start, int end,
		OrderByComparator orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_U_S);

			sql = StringUtil.replace(
				sql, "[$STATUSES$]", getStatusesSQL(statuses));

			StringBundler sb = new StringBundler();

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, "PowwowMeeting.", orderByComparator);
			}

			sql = StringUtil.replace(sql, "[$ORDER_BY$]", sb.toString());

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("PowwowMeeting", PowwowMeetingImpl.class);

			User user = UserLocalServiceUtil.fetchUser(userId);

			if (user == null) {
				return Collections.emptyList();
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(user.getUserId());
			qPos.add(user.getUserId());
			qPos.add(user.getEmailAddress());

			return (List<PowwowMeeting>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getStatusesSQL(int[] statuses) {
		if (ArrayUtil.contains(statuses, PowwowMeetingConstants.STATUS_ANY)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("AND PowwowMeeting.status IN ");
		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int status : statuses) {
			sb.append(status);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}