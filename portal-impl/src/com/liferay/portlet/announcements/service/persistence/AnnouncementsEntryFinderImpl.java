/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.model.AnnouncementsFlagConstants;
import com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thiago Moreira
 * @author Raymond Augé
 */
public class AnnouncementsEntryFinderImpl
	extends BasePersistenceImpl<AnnouncementsEntry>
	implements AnnouncementsEntryFinder {

	public static final String COUNT_BY_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".countByHidden";

	public static final String COUNT_BY_NOT_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".countByNotHidden";

	public static final String FIND_BY_DISPLAY_DATE =
		AnnouncementsEntryFinder.class.getName() + ".findByDisplayDate";

	public static final String FIND_BY_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".findByHidden";

	public static final String FIND_BY_NOT_HIDDEN =
		AnnouncementsEntryFinder.class.getName() + ".findByNotHidden";

	@Override
	public int countByScope(
			long userId, long classNameId, long[] classPKs,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagConstants.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(COUNT_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(classNameId, classPKs));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, classNameId, classPKs);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagConstants.HIDDEN);

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
	public int countByScopes(
			long userId, LinkedHashMap<Long, long[]> scopes,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagConstants.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(COUNT_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(sql, "[$CLASS_PKS$]", getClassPKs(scopes));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, scopes);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagConstants.HIDDEN);

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
	public List<AnnouncementsEntry> findByDisplayDate(
			Date displayDateLT, Date displayDateGT)
		throws SystemException {

		Timestamp displayDateLT_TS = CalendarUtil.getTimestamp(displayDateLT);
		Timestamp displayDateGT_TS = CalendarUtil.getTimestamp(displayDateGT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_DISPLAY_DATE);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("AnnouncementsEntry", AnnouncementsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(displayDateGT_TS);
			qPos.add(displayDateLT_TS);

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
	public List<AnnouncementsEntry> findByScope(
			long userId, long classNameId, long[] classPKs,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagConstants.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(FIND_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(
				sql, "[$CLASS_PKS$]", getClassPKs(classNameId, classPKs));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("AnnouncementsEntry", AnnouncementsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, classNameId, classPKs);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagConstants.HIDDEN);

			return (List<AnnouncementsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<AnnouncementsEntry> findByScopes(
			long userId, LinkedHashMap<Long, long[]> scopes,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, boolean alert,
			int flagValue, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_HIDDEN);

			if (flagValue == AnnouncementsFlagConstants.NOT_HIDDEN) {
				sql = CustomSQLUtil.get(FIND_BY_NOT_HIDDEN);
			}

			sql = StringUtil.replace(sql, "[$CLASS_PKS$]", getClassPKs(scopes));
			sql = CustomSQLUtil.replaceAndOperator(sql, true);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("AnnouncementsEntry", AnnouncementsEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setClassPKs(qPos, scopes);

			setDates(
				qPos, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute);

			qPos.add(alert);
			qPos.add(userId);
			qPos.add(AnnouncementsFlagConstants.HIDDEN);

			return (List<AnnouncementsEntry>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getClassPKs(LinkedHashMap<Long, long[]> scopes) {
		if ((scopes == null) || scopes.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(scopes.size() * 4);

		for (Map.Entry<Long, long[]> entry : scopes.entrySet()) {
			Long classNameId = entry.getKey();
			long[] classPKs = entry.getValue();

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(getClassPKs(classNameId.longValue(), classPKs));
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(" OR ");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	protected String getClassPKs(long classNameId, long[] classPKs) {
		if (classPKs.length == 0) {
			return "(AnnouncementsEntry.classNameId = ?) AND (";
		}

		StringBundler sb = new StringBundler(classPKs.length * 2 + 1);

		sb.append("(AnnouncementsEntry.classNameId = ?) AND (");

		for (int i = 0; i < classPKs.length; i++) {
			sb.append("(AnnouncementsEntry.classPK = ?)");

			if ((i + 1) < classPKs.length) {
				sb.append(" OR ");
			}
			else {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}

		return sb.toString();
	}

	protected void setClassPKs(
		QueryPos qPos, LinkedHashMap<Long, long[]> scopes) {

		if (scopes == null) {
			return;
		}

		for (Map.Entry<Long, long[]> entry : scopes.entrySet()) {
			Long classNameId = entry.getKey();
			long[] classPKs = entry.getValue();

			setClassPKs(qPos, classNameId.longValue(), classPKs);
		}
	}

	protected void setClassPKs(
		QueryPos qPos, long classNameId, long[] classPKs) {

		qPos.add(classNameId);

		for (int i = 0; i < classPKs.length; i++) {
			qPos.add(classPKs[i]);
		}
	}

	protected void setDates(
		QueryPos qPos, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute) {

		Date displayDate = null;

		try {
			displayDate = PortalUtil.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, null);
		}
		catch (PortalException pe) {
		}

		if (displayDate == null) {
			displayDate = new Date();
		}

		Timestamp displayDateTS = CalendarUtil.getTimestamp(displayDate);

		Date expirationDate = null;

		try {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, null);
		}
		catch (PortalException pe) {
		}

		if (expirationDate == null) {
			expirationDate = new Date();
		}

		Timestamp expirationDateTS = CalendarUtil.getTimestamp(expirationDate);

		qPos.add(displayDateTS);
		qPos.add(displayDateTS);
		qPos.add(expirationDateTS);
		qPos.add(expirationDateTS);
	}

}