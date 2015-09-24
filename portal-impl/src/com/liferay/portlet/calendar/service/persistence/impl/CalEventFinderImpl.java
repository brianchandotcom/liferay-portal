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

package com.liferay.portlet.calendar.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventConstants;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.calendar.service.persistence.CalEventFinder;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author     Brian Wing Shun Chan
 * @author     Zsolt Balogh
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class CalEventFinderImpl
	extends CalEventFinderBaseImpl implements CalEventFinder {

	public static final String COUNT_BY_G_SD_T =
		CalEventFinder.class.getName() + ".countByG_SD_T";

	public static final String FIND_BY_FUTURE_REMINDERS =
		CalEventFinder.class.getName() + ".findByFutureReminders";

	public static final String FIND_BY_NO_ASSETS =
		CalEventFinder.class.getName() + ".findByNoAssets";

	public static final String FIND_BY_G_SD_T =
		CalEventFinder.class.getName() + ".findByG_SD_T";

	@Override
	public int countByG_SD_T(
		long groupId, Date startDateGT, Date startDateLT,
		boolean timeZoneSensitive, String[] types) {

		Timestamp startDateGT_TS = CalendarUtil.getTimestamp(startDateGT);
		Timestamp startDateLT_TS = CalendarUtil.getTimestamp(startDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_SD_T);

			sql = StringUtil.replace(sql, "[$TYPE$]", getTypes(types));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CalEvent", CalEventImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(startDateGT_TS);
			qPos.add(startDateLT_TS);
			qPos.add(timeZoneSensitive);
			qPos.add(false);

			if ((types != null) && (types.length > 0) &&
				((types.length > 1) || Validator.isNotNull(types[0]))) {

				for (String type : types) {
					qPos.add(type);
				}
			}

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
	public List<CalEvent> findByFutureReminders() {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR, -24);

		Timestamp calendar_TS = CalendarUtil.getTimestamp(calendar.getTime());

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_FUTURE_REMINDERS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CalEvent", CalEventImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(CalEventConstants.REMIND_BY_NONE);
			qPos.add(calendar_TS);
			qPos.add(calendar_TS);

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
	public List<CalEvent> findByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CalEvent", CalEventImpl.class);

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
	public List<CalEvent> findByG_SD_T(
		long groupId, Date startDateGT, Date startDateLT,
		boolean timeZoneSensitive, String[] types) {

		return findByG_SD_T(
			groupId, startDateGT, startDateLT, timeZoneSensitive, types,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CalEvent> findByG_SD_T(
		long groupId, Date startDateGT, Date startDateLT,
		boolean timeZoneSensitive, String[] types, int start, int end) {

		Timestamp startDateGT_TS = CalendarUtil.getTimestamp(startDateGT);
		Timestamp startDateLT_TS = CalendarUtil.getTimestamp(startDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_SD_T);

			sql = StringUtil.replace(sql, "[$TYPE$]", getTypes(types));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("CalEvent", CalEventImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(startDateGT_TS);
			qPos.add(startDateLT_TS);
			qPos.add(timeZoneSensitive);
			qPos.add(false);

			if ((types != null) && (types.length > 0) &&
				((types.length > 1) || Validator.isNotNull(types[0]))) {

				for (String type : types) {
					qPos.add(type);
				}
			}

			return (List<CalEvent>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getTypes(String[] types) {
		if ((types != null) && (types.length > 0) &&
			((types.length > 1) || Validator.isNotNull(types[0]))) {

			StringBundler sb = new StringBundler(types.length * 2 + 1);

			sb.append(" AND (");

			for (int i = 0; i < types.length; i++) {
				sb.append("type_ = ? ");

				if ((i + 1) != types.length) {
					sb.append("OR ");
				}
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		return StringPool.BLANK;
	}

}