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

package com.liferay.portlet.calendar.service.persistence;

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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventConstants;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Balogh
 */
public class CalEventFinderImpl
	extends BasePersistenceImpl<CalEvent> implements CalEventFinder {

	public static String COUNT_BY_G_SD_T =
		CalEventFinder.class.getName() + ".countByG_SD_T";

	public static String FIND_BY_FUTURE_REMINDERS =
		CalEventFinder.class.getName() + ".findByFutureReminders";

	public static String FIND_BY_NO_ASSETS =
		CalEventFinder.class.getName() + ".findByNoAssets";

	public static String FIND_BY_G_SD_T =
		CalEventFinder.class.getName() + ".findByG_SD_T";

	public int countByG_SD_T(
			long groupId, Date startDateGT, Date startDateLT,
			boolean timeZoneSensitive, String[] types)
		throws SystemException {

		Timestamp startDateGT_TS = CalendarUtil.getTimestamp(startDateGT);
		Timestamp startDateLT_TS = CalendarUtil.getTimestamp(startDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_SD_T);

			sql = StringUtil.replace(sql, "[$TYPE$]", getTypes(types));

			SQLQuery q = session.createSQLQuery(sql);

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

	public List<CalEvent> findByFutureReminders() throws SystemException {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR, -24);

		Timestamp calendar_TS = CalendarUtil.getTimestamp(calendar.getTime());

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_FUTURE_REMINDERS);

			SQLQuery q = session.createSQLQuery(sql);

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

	public List<CalEvent> findByNoAssets() throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSQLQuery(sql);

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

	public List<CalEvent> findByG_SD_T(
			long groupId, Date startDateGT, Date startDateLT,
			boolean timeZoneSensitive, String[] types)
		throws SystemException {

		return findByG_SD_T(
			groupId, startDateGT, startDateLT, timeZoneSensitive, types,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<CalEvent> findByG_SD_T(
			long groupId, Date startDateGT, Date startDateLT,
			boolean timeZoneSensitive, String[] types, int start, int end)
		throws SystemException {

		Timestamp startDateGT_TS = CalendarUtil.getTimestamp(startDateGT);
		Timestamp startDateLT_TS = CalendarUtil.getTimestamp(startDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_SD_T);

			sql = StringUtil.replace(sql, "[$TYPE$]", getTypes(types));

			SQLQuery q = session.createSQLQuery(sql);

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

			sb.append(")");

			return sb.toString();
		}

		return StringPool.BLANK;
	}

}