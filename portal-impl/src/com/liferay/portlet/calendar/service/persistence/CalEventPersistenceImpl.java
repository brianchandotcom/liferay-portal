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

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.calendar.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.calendar.model.impl.CalEventModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the cal event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CalEventPersistence
 * @see CalEventUtil
 * @generated
 */
public class CalEventPersistenceImpl extends BasePersistenceImpl<CalEvent>
	implements CalEventPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CalEventUtil} to access the cal event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CalEventImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CalEventModelImpl.UUID_COLUMN_BITMASK |
			CalEventModelImpl.STARTDATE_COLUMN_BITMASK |
			CalEventModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cal events where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if (!Validator.equals(uuid, calEvent.getUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByUuid_First(uuid, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<CalEvent> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByUuid_Last(uuid, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where uuid = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByUuid_PrevAndNext(long eventId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByUuid_PrevAndNext(session, calEvent, uuid,
					orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByUuid_PrevAndNext(session, calEvent, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByUuid_PrevAndNext(Session session,
		CalEvent calEvent, String uuid, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cal events where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUuid(String uuid) throws SystemException {
		for (CalEvent calEvent : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "calEvent.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "calEvent.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(calEvent.uuid IS NULL OR calEvent.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CalEventModelImpl.UUID_COLUMN_BITMASK |
			CalEventModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cal event where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.calendar.NoSuchEventException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByUUID_G(String uuid, long groupId)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByUUID_G(uuid, groupId);

		if (calEvent == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEventException(msg.toString());
		}

		return calEvent;
	}

	/**
	 * Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CalEvent) {
			CalEvent calEvent = (CalEvent)result;

			if (!Validator.equals(uuid, calEvent.getUuid()) ||
					(groupId != calEvent.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<CalEvent> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CalEvent calEvent = list.get(0);

					result = calEvent;

					cacheResult(calEvent);

					if ((calEvent.getUuid() == null) ||
							!calEvent.getUuid().equals(uuid) ||
							(calEvent.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, calEvent);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CalEvent)result;
		}
	}

	/**
	 * Removes the cal event where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cal event that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent removeByUUID_G(String uuid, long groupId)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByUUID_G(uuid, groupId);

		return remove(calEvent);
	}

	/**
	 * Returns the number of cal events where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "calEvent.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "calEvent.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(calEvent.uuid IS NULL OR calEvent.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "calEvent.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CalEventModelImpl.UUID_COLUMN_BITMASK |
			CalEventModelImpl.COMPANYID_COLUMN_BITMASK |
			CalEventModelImpl.STARTDATE_COLUMN_BITMASK |
			CalEventModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cal events where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if (!Validator.equals(uuid, calEvent.getUuid()) ||
						(companyId != calEvent.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CalEvent> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByUuid_C_PrevAndNext(long eventId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, calEvent, uuid,
					companyId, orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByUuid_C_PrevAndNext(session, calEvent, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByUuid_C_PrevAndNext(Session session,
		CalEvent calEvent, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cal events where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (CalEvent calEvent : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "calEvent.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "calEvent.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(calEvent.uuid IS NULL OR calEvent.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "calEvent.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CalEventModelImpl.COMPANYID_COLUMN_BITMASK |
			CalEventModelImpl.STARTDATE_COLUMN_BITMASK |
			CalEventModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cal events where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cal events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((companyId != calEvent.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByCompanyId_First(companyId, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CalEvent> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByCompanyId_Last(companyId, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where companyId = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByCompanyId_PrevAndNext(long eventId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, calEvent, companyId,
					orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByCompanyId_PrevAndNext(session, calEvent, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByCompanyId_PrevAndNext(Session session,
		CalEvent calEvent, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cal events where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByCompanyId(long companyId) throws SystemException {
		for (CalEvent calEvent : findByCompanyId(companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByCompanyId(long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "calEvent.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CalEventModelImpl.GROUPID_COLUMN_BITMASK |
			CalEventModelImpl.STARTDATE_COLUMN_BITMASK |
			CalEventModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cal events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByGroupId(long groupId) throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((groupId != calEvent.getGroupId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByGroupId_First(groupId, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CalEvent> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByGroupId_Last(groupId, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where groupId = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByGroupId_PrevAndNext(long eventId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, calEvent, groupId,
					orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByGroupId_PrevAndNext(session, calEvent, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByGroupId_PrevAndNext(Session session,
		CalEvent calEvent, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cal events that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByGroupId(long groupId, int start, int end)
		throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<CalEvent>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] filterFindByGroupId_PrevAndNext(long eventId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(eventId, groupId, orderByComparator);
		}

		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, calEvent,
					groupId, orderByComparator, true);

			array[1] = calEvent;

			array[2] = filterGetByGroupId_PrevAndNext(session, calEvent,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent filterGetByGroupId_PrevAndNext(Session session,
		CalEvent calEvent, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cal events where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByGroupId(long groupId) throws SystemException {
		for (CalEvent calEvent : findByGroupId(groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByGroupId(long groupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of cal events that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "calEvent.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_NOTREMINDBY =
		new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByNotRemindBy",
			new String[] {
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_NOTREMINDBY =
		new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByNotRemindBy",
			new String[] { Integer.class.getName() });

	/**
	 * Returns all the cal events where remindBy &ne; &#63;.
	 *
	 * @param remindBy the remind by
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByNotRemindBy(int remindBy)
		throws SystemException {
		return findByNotRemindBy(remindBy, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where remindBy &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param remindBy the remind by
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByNotRemindBy(int remindBy, int start, int end)
		throws SystemException {
		return findByNotRemindBy(remindBy, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where remindBy &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param remindBy the remind by
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByNotRemindBy(int remindBy, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NOTREMINDBY;
		finderArgs = new Object[] { remindBy, start, end, orderByComparator };

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((remindBy == calEvent.getRemindBy())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_NOTREMINDBY_REMINDBY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(remindBy);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where remindBy &ne; &#63;.
	 *
	 * @param remindBy the remind by
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByNotRemindBy_First(int remindBy,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByNotRemindBy_First(remindBy, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("remindBy=");
		msg.append(remindBy);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where remindBy &ne; &#63;.
	 *
	 * @param remindBy the remind by
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByNotRemindBy_First(int remindBy,
		OrderByComparator orderByComparator) throws SystemException {
		List<CalEvent> list = findByNotRemindBy(remindBy, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where remindBy &ne; &#63;.
	 *
	 * @param remindBy the remind by
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByNotRemindBy_Last(int remindBy,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByNotRemindBy_Last(remindBy, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("remindBy=");
		msg.append(remindBy);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where remindBy &ne; &#63;.
	 *
	 * @param remindBy the remind by
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByNotRemindBy_Last(int remindBy,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByNotRemindBy(remindBy);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByNotRemindBy(remindBy, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where remindBy &ne; &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param remindBy the remind by
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByNotRemindBy_PrevAndNext(long eventId, int remindBy,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByNotRemindBy_PrevAndNext(session, calEvent,
					remindBy, orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByNotRemindBy_PrevAndNext(session, calEvent,
					remindBy, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByNotRemindBy_PrevAndNext(Session session,
		CalEvent calEvent, int remindBy, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_NOTREMINDBY_REMINDBY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(remindBy);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cal events where remindBy &ne; &#63; from the database.
	 *
	 * @param remindBy the remind by
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByNotRemindBy(int remindBy) throws SystemException {
		for (CalEvent calEvent : findByNotRemindBy(remindBy, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where remindBy &ne; &#63;.
	 *
	 * @param remindBy the remind by
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByNotRemindBy(int remindBy) throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_NOTREMINDBY;

		Object[] finderArgs = new Object[] { remindBy };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_NOTREMINDBY_REMINDBY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(remindBy);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_NOTREMINDBY_REMINDBY_2 = "calEvent.remindBy != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), String.class.getName() },
			CalEventModelImpl.GROUPID_COLUMN_BITMASK |
			CalEventModelImpl.TYPE_COLUMN_BITMASK |
			CalEventModelImpl.STARTDATE_COLUMN_BITMASK |
			CalEventModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the cal events where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String type)
		throws SystemException {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String type, int start,
		int end) throws SystemException {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((groupId != calEvent.getGroupId()) ||
						!Validator.equals(type, calEvent.getType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByG_T_First(long groupId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByG_T_First(groupId, type, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByG_T_First(long groupId, String type,
		OrderByComparator orderByComparator) throws SystemException {
		List<CalEvent> list = findByG_T(groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByG_T_Last(long groupId, String type,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByG_T_Last(groupId, type, orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByG_T_Last(long groupId, String type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByG_T(groupId, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByG_T_PrevAndNext(long eventId, long groupId,
		String type, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByG_T_PrevAndNext(session, calEvent, groupId, type,
					orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByG_T_PrevAndNext(session, calEvent, groupId, type,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByG_T_PrevAndNext(Session session, CalEvent calEvent,
		long groupId, String type, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_TYPE_1);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T(long groupId, String type)
		throws SystemException {
		return filterFindByG_T(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T(long groupId, String type, int start,
		int end) throws SystemException {
		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T(long groupId, String type, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindType) {
				qPos.add(type);
			}

			return (List<CalEvent>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] filterFindByG_T_PrevAndNext(long eventId, long groupId,
		String type, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(eventId, groupId, type,
				orderByComparator);
		}

		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(session, calEvent, groupId,
					type, orderByComparator, true);

			array[1] = calEvent;

			array[2] = filterGetByG_T_PrevAndNext(session, calEvent, groupId,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent filterGetByG_T_PrevAndNext(Session session,
		CalEvent calEvent, long groupId, String type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @return the matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T(long groupId, String[] types)
		throws SystemException {
		return filterFindByG_T(groupId, types, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T(long groupId, String[] types,
		int start, int end) throws SystemException {
		return filterFindByG_T(groupId, types, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T(long groupId, String[] types,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, types, start, end, orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_5);

		conjunctionable = true;

		if ((types == null) || (types.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < types.length; i++) {
				String type = types[i];

				if (type == null) {
					query.append(_FINDER_COLUMN_G_T_TYPE_4_SQL);
				}
				else if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TYPE_6_SQL);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TYPE_5_SQL);
				}

				if ((i + 1) < types.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (types != null) {
				qPos.add(types);
			}

			return (List<CalEvent>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the cal events where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String[] types)
		throws SystemException {
		return findByG_T(groupId, types, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String[] types, int start,
		int end) throws SystemException {
		return findByG_T(groupId, types, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String[] types, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if ((types != null) && (types.length == 1)) {
			return findByG_T(groupId, types[0], start, end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { groupId, StringUtil.merge(types) };
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(types),
					
					start, end, orderByComparator
				};
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((groupId != calEvent.getGroupId()) ||
						!ArrayUtil.contains(types, calEvent.getType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_GROUPID_5);

			conjunctionable = true;

			if ((types == null) || (types.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type == null) {
						query.append(_FINDER_COLUMN_G_T_TYPE_4);
					}
					else if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TYPE_6);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TYPE_5);
					}

					if ((i + 1) < types.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (types != null) {
					qPos.add(types);
				}

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cal events where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_T(long groupId, String type)
		throws SystemException {
		for (CalEvent calEvent : findByG_T(groupId, type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_T(long groupId, String type) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_T_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of cal events where groupId = &#63; and type = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_T(long groupId, String[] types)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, StringUtil.merge(types) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_GROUPID_5);

			conjunctionable = true;

			if ((types == null) || (types.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type == null) {
						query.append(_FINDER_COLUMN_G_T_TYPE_4);
					}
					else if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TYPE_6);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TYPE_5);
					}

					if ((i + 1) < types.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (types != null) {
					qPos.add(types);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of cal events that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_T(long groupId, String type)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindType) {
				qPos.add(type);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of cal events that the user has permission to view where groupId = &#63; and type = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @return the number of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_T(long groupId, String[] types)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, types);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_CALEVENT_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_5);

		conjunctionable = true;

		if ((types == null) || (types.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < types.length; i++) {
				String type = types[i];

				if (type == null) {
					query.append(_FINDER_COLUMN_G_T_TYPE_4_SQL);
				}
				else if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_TYPE_6_SQL);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_TYPE_5_SQL);
				}

				if ((i + 1) < types.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (types != null) {
				qPos.add(types);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "calEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_GROUPID_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_T_TYPE_1 = "calEvent.type IS NULL";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "calEvent.type = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_3 = "(calEvent.type IS NULL OR calEvent.type = '')";
	private static final String _FINDER_COLUMN_G_T_TYPE_4 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_TYPE_1) + ")";
	private static final String _FINDER_COLUMN_G_T_TYPE_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_TYPE_2) + ")";
	private static final String _FINDER_COLUMN_G_T_TYPE_6 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_TYPE_3) + ")";
	private static final String _FINDER_COLUMN_G_T_TYPE_1_SQL = "calEvent.type_ IS NULL";
	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL = "calEvent.type_ = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_3_SQL = "(calEvent.type_ IS NULL OR calEvent.type_ = '')";
	private static final String _FINDER_COLUMN_G_T_TYPE_4_SQL = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_TYPE_1) + ")";
	private static final String _FINDER_COLUMN_G_T_TYPE_5_SQL = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_TYPE_2) + ")";
	private static final String _FINDER_COLUMN_G_T_TYPE_6_SQL = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_TYPE_3) + ")";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_R = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_R",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_R",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CalEventModelImpl.GROUPID_COLUMN_BITMASK |
			CalEventModelImpl.REPEATING_COLUMN_BITMASK |
			CalEventModelImpl.STARTDATE_COLUMN_BITMASK |
			CalEventModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_R = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_R",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the cal events where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_R(long groupId, boolean repeating)
		throws SystemException {
		return findByG_R(groupId, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_R(long groupId, boolean repeating, int start,
		int end) throws SystemException {
		return findByG_R(groupId, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_R(long groupId, boolean repeating, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R;
			finderArgs = new Object[] { groupId, repeating };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_R;
			finderArgs = new Object[] {
					groupId, repeating,
					
					start, end, orderByComparator
				};
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((groupId != calEvent.getGroupId()) ||
						(repeating != calEvent.getRepeating())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_REPEATING_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(repeating);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByG_R_First(long groupId, boolean repeating,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByG_R_First(groupId, repeating,
				orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", repeating=");
		msg.append(repeating);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByG_R_First(long groupId, boolean repeating,
		OrderByComparator orderByComparator) throws SystemException {
		List<CalEvent> list = findByG_R(groupId, repeating, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByG_R_Last(long groupId, boolean repeating,
		OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByG_R_Last(groupId, repeating,
				orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", repeating=");
		msg.append(repeating);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByG_R_Last(long groupId, boolean repeating,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_R(groupId, repeating);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByG_R(groupId, repeating, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and repeating = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByG_R_PrevAndNext(long eventId, long groupId,
		boolean repeating, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByG_R_PrevAndNext(session, calEvent, groupId,
					repeating, orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByG_R_PrevAndNext(session, calEvent, groupId,
					repeating, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByG_R_PrevAndNext(Session session, CalEvent calEvent,
		long groupId, boolean repeating, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_REPEATING_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(repeating);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @return the matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_R(long groupId, boolean repeating)
		throws SystemException {
		return filterFindByG_R(groupId, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_R(long groupId, boolean repeating,
		int start, int end) throws SystemException {
		return filterFindByG_R(groupId, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_R(long groupId, boolean repeating,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_R(groupId, repeating, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_REPEATING_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(repeating);

			return (List<CalEvent>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] filterFindByG_R_PrevAndNext(long eventId, long groupId,
		boolean repeating, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_R_PrevAndNext(eventId, groupId, repeating,
				orderByComparator);
		}

		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = filterGetByG_R_PrevAndNext(session, calEvent, groupId,
					repeating, orderByComparator, true);

			array[1] = calEvent;

			array[2] = filterGetByG_R_PrevAndNext(session, calEvent, groupId,
					repeating, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent filterGetByG_R_PrevAndNext(Session session,
		CalEvent calEvent, long groupId, boolean repeating,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_REPEATING_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(repeating);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cal events where groupId = &#63; and repeating = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_R(long groupId, boolean repeating)
		throws SystemException {
		for (CalEvent calEvent : findByG_R(groupId, repeating,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_R(long groupId, boolean repeating)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_R;

		Object[] finderArgs = new Object[] { groupId, repeating };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_R_GROUPID_2);

			query.append(_FINDER_COLUMN_G_R_REPEATING_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(repeating);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of cal events that the user has permission to view where groupId = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @return the number of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_R(long groupId, boolean repeating)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_R(groupId, repeating);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_R_GROUPID_2);

		query.append(_FINDER_COLUMN_G_R_REPEATING_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(repeating);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_R_GROUPID_2 = "calEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_R_REPEATING_2 = "calEvent.repeating = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, CalEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			CalEventModelImpl.GROUPID_COLUMN_BITMASK |
			CalEventModelImpl.TYPE_COLUMN_BITMASK |
			CalEventModelImpl.REPEATING_COLUMN_BITMASK |
			CalEventModelImpl.STARTDATE_COLUMN_BITMASK |
			CalEventModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T_R = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_R = new FinderPath(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String type,
		boolean repeating) throws SystemException {
		return findByG_T_R(groupId, type, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String type,
		boolean repeating, int start, int end) throws SystemException {
		return findByG_T_R(groupId, type, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String type,
		boolean repeating, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R;
			finderArgs = new Object[] { groupId, type, repeating };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R;
			finderArgs = new Object[] {
					groupId, type, repeating,
					
					start, end, orderByComparator
				};
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((groupId != calEvent.getGroupId()) ||
						!Validator.equals(type, calEvent.getType()) ||
						(repeating != calEvent.getRepeating())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_T_R_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_R_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_R_TYPE_2);
			}

			query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(repeating);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByG_T_R_First(long groupId, String type,
		boolean repeating, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByG_T_R_First(groupId, type, repeating,
				orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", repeating=");
		msg.append(repeating);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the first cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByG_T_R_First(long groupId, String type,
		boolean repeating, OrderByComparator orderByComparator)
		throws SystemException {
		List<CalEvent> list = findByG_T_R(groupId, type, repeating, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByG_T_R_Last(long groupId, String type,
		boolean repeating, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByG_T_R_Last(groupId, type, repeating,
				orderByComparator);

		if (calEvent != null) {
			return calEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", repeating=");
		msg.append(repeating);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEventException(msg.toString());
	}

	/**
	 * Returns the last cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cal event, or <code>null</code> if a matching cal event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByG_T_R_Last(long groupId, String type,
		boolean repeating, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_T_R(groupId, type, repeating);

		if (count == 0) {
			return null;
		}

		List<CalEvent> list = findByG_T_R(groupId, type, repeating, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] findByG_T_R_PrevAndNext(long eventId, long groupId,
		String type, boolean repeating, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = getByG_T_R_PrevAndNext(session, calEvent, groupId, type,
					repeating, orderByComparator, true);

			array[1] = calEvent;

			array[2] = getByG_T_R_PrevAndNext(session, calEvent, groupId, type,
					repeating, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent getByG_T_R_PrevAndNext(Session session,
		CalEvent calEvent, long groupId, String type, boolean repeating,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_1);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_R_TYPE_2);
		}

		query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CalEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindType) {
			qPos.add(type);
		}

		qPos.add(repeating);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @return the matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T_R(long groupId, String type,
		boolean repeating) throws SystemException {
		return filterFindByG_T_R(groupId, type, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T_R(long groupId, String type,
		boolean repeating, int start, int end) throws SystemException {
		return filterFindByG_T_R(groupId, type, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events that the user has permissions to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T_R(long groupId, String type,
		boolean repeating, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_R(groupId, type, repeating, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_R_TYPE_2_SQL);
		}

		query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindType) {
				qPos.add(type);
			}

			qPos.add(repeating);

			return (List<CalEvent>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the cal events before and after the current cal event in the ordered set of cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param eventId the primary key of the current cal event
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent[] filterFindByG_T_R_PrevAndNext(long eventId, long groupId,
		String type, boolean repeating, OrderByComparator orderByComparator)
		throws NoSuchEventException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_R_PrevAndNext(eventId, groupId, type, repeating,
				orderByComparator);
		}

		CalEvent calEvent = findByPrimaryKey(eventId);

		Session session = null;

		try {
			session = openSession();

			CalEvent[] array = new CalEventImpl[3];

			array[0] = filterGetByG_T_R_PrevAndNext(session, calEvent, groupId,
					type, repeating, orderByComparator, true);

			array[1] = calEvent;

			array[2] = filterGetByG_T_R_PrevAndNext(session, calEvent, groupId,
					type, repeating, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CalEvent filterGetByG_T_R_PrevAndNext(Session session,
		CalEvent calEvent, long groupId, String type, boolean repeating,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_R_TYPE_2_SQL);
		}

		query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindType) {
			qPos.add(type);
		}

		qPos.add(repeating);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(calEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CalEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @return the matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T_R(long groupId, String[] types,
		boolean repeating) throws SystemException {
		return filterFindByG_T_R(groupId, types, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T_R(long groupId, String[] types,
		boolean repeating, int start, int end) throws SystemException {
		return filterFindByG_T_R(groupId, types, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> filterFindByG_T_R(long groupId, String[] types,
		boolean repeating, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_R(groupId, types, repeating, start, end,
				orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_T_R_GROUPID_5);

		conjunctionable = true;

		if ((types == null) || (types.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < types.length; i++) {
				String type = types[i];

				if (type == null) {
					query.append(_FINDER_COLUMN_G_T_R_TYPE_4_SQL);
				}
				else if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_R_TYPE_6_SQL);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_R_TYPE_5_SQL);
				}

				if ((i + 1) < types.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_T_R_REPEATING_5);

		conjunctionable = true;

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CalEventModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CalEventImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CalEventImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (types != null) {
				qPos.add(types);
			}

			qPos.add(repeating);

			return (List<CalEvent>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @return the matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String[] types,
		boolean repeating) throws SystemException {
		return findByG_T_R(groupId, types, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String[] types,
		boolean repeating, int start, int end) throws SystemException {
		return findByG_T_R(groupId, types, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String[] types,
		boolean repeating, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if ((types != null) && (types.length == 1)) {
			return findByG_T_R(groupId, types[0], repeating, start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, StringUtil.merge(types), repeating
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(types), repeating,
					
					start, end, orderByComparator
				};
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CalEvent calEvent : list) {
				if ((groupId != calEvent.getGroupId()) ||
						!ArrayUtil.contains(types, calEvent.getType()) ||
						(repeating != calEvent.getRepeating())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_R_GROUPID_5);

			conjunctionable = true;

			if ((types == null) || (types.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type == null) {
						query.append(_FINDER_COLUMN_G_T_R_TYPE_4);
					}
					else if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_R_TYPE_6);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_R_TYPE_5);
					}

					if ((i + 1) < types.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_R_REPEATING_5);

			conjunctionable = true;

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CalEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (types != null) {
					qPos.add(types);
				}

				qPos.add(repeating);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cal events where groupId = &#63; and type = &#63; and repeating = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_T_R(long groupId, String type, boolean repeating)
		throws SystemException {
		for (CalEvent calEvent : findByG_T_R(groupId, type, repeating,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_T_R(long groupId, String type, boolean repeating)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T_R;

		Object[] finderArgs = new Object[] { groupId, type, repeating };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

			boolean bindType = false;

			if (type == null) {
				query.append(_FINDER_COLUMN_G_T_R_TYPE_1);
			}
			else if (type.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_R_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_R_TYPE_2);
			}

			query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(repeating);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @return the number of matching cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_T_R(long groupId, String[] types, boolean repeating)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(types), repeating
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_R_GROUPID_5);

			conjunctionable = true;

			if ((types == null) || (types.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type == null) {
						query.append(_FINDER_COLUMN_G_T_R_TYPE_4);
					}
					else if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_R_TYPE_6);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_R_TYPE_5);
					}

					if ((i + 1) < types.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_R_REPEATING_5);

			conjunctionable = true;

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (types != null) {
					qPos.add(types);
				}

				qPos.add(repeating);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_R,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_R,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of cal events that the user has permission to view where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @return the number of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_T_R(long groupId, String type, boolean repeating)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T_R(groupId, type, repeating);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_CALEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

		boolean bindType = false;

		if (type == null) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_1_SQL);
		}
		else if (type.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_R_TYPE_3_SQL);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_R_TYPE_2_SQL);
		}

		query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindType) {
				qPos.add(type);
			}

			qPos.add(repeating);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of cal events that the user has permission to view where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @return the number of matching cal events that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_T_R(long groupId, String[] types,
		boolean repeating) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T_R(groupId, types, repeating);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_CALEVENT_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_T_R_GROUPID_5);

		conjunctionable = true;

		if ((types == null) || (types.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < types.length; i++) {
				String type = types[i];

				if (type == null) {
					query.append(_FINDER_COLUMN_G_T_R_TYPE_4_SQL);
				}
				else if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_T_R_TYPE_6_SQL);
				}
				else {
					query.append(_FINDER_COLUMN_G_T_R_TYPE_5_SQL);
				}

				if ((i + 1) < types.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_T_R_REPEATING_5);

		conjunctionable = true;

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CalEvent.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (types != null) {
				qPos.add(types);
			}

			qPos.add(repeating);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_T_R_GROUPID_2 = "calEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_R_GROUPID_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_1 = "calEvent.type IS NULL AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_2 = "calEvent.type = ? AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_3 = "(calEvent.type IS NULL OR calEvent.type = '') AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_4 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_1) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_2) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_6 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_3) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_1_SQL = "calEvent.type_ IS NULL AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_2_SQL = "calEvent.type_ = ? AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_3_SQL = "(calEvent.type_ IS NULL OR calEvent.type_ = '') AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_4_SQL = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_1) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_5_SQL = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_2) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_6_SQL = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_3) + ")";
	private static final String _FINDER_COLUMN_G_T_R_REPEATING_2 = "calEvent.repeating = ?";
	private static final String _FINDER_COLUMN_G_T_R_REPEATING_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_REPEATING_2) + ")";

	public CalEventPersistenceImpl() {
		setModelClass(CalEvent.class);
	}

	/**
	 * Caches the cal event in the entity cache if it is enabled.
	 *
	 * @param calEvent the cal event
	 */
	@Override
	public void cacheResult(CalEvent calEvent) {
		EntityCacheUtil.putResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventImpl.class, calEvent.getPrimaryKey(), calEvent);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { calEvent.getUuid(), calEvent.getGroupId() }, calEvent);

		calEvent.resetOriginalValues();
	}

	/**
	 * Caches the cal events in the entity cache if it is enabled.
	 *
	 * @param calEvents the cal events
	 */
	@Override
	public void cacheResult(List<CalEvent> calEvents) {
		for (CalEvent calEvent : calEvents) {
			if (EntityCacheUtil.getResult(
						CalEventModelImpl.ENTITY_CACHE_ENABLED,
						CalEventImpl.class, calEvent.getPrimaryKey()) == null) {
				cacheResult(calEvent);
			}
			else {
				calEvent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cal events.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CalEventImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CalEventImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cal event.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CalEvent calEvent) {
		EntityCacheUtil.removeResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventImpl.class, calEvent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(calEvent);
	}

	@Override
	public void clearCache(List<CalEvent> calEvents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CalEvent calEvent : calEvents) {
			EntityCacheUtil.removeResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
				CalEventImpl.class, calEvent.getPrimaryKey());

			clearUniqueFindersCache(calEvent);
		}
	}

	protected void cacheUniqueFindersCache(CalEvent calEvent) {
		if (calEvent.isNew()) {
			Object[] args = new Object[] {
					calEvent.getUuid(), calEvent.getGroupId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				calEvent);
		}
		else {
			CalEventModelImpl calEventModelImpl = (CalEventModelImpl)calEvent;

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEvent.getUuid(), calEvent.getGroupId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					calEvent);
			}
		}
	}

	protected void clearUniqueFindersCache(CalEvent calEvent) {
		CalEventModelImpl calEventModelImpl = (CalEventModelImpl)calEvent;

		Object[] args = new Object[] { calEvent.getUuid(), calEvent.getGroupId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((calEventModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					calEventModelImpl.getOriginalUuid(),
					calEventModelImpl.getOriginalGroupId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new cal event with the primary key. Does not add the cal event to the database.
	 *
	 * @param eventId the primary key for the new cal event
	 * @return the new cal event
	 */
	@Override
	public CalEvent create(long eventId) {
		CalEvent calEvent = new CalEventImpl();

		calEvent.setNew(true);
		calEvent.setPrimaryKey(eventId);

		String uuid = PortalUUIDUtil.generate();

		calEvent.setUuid(uuid);

		return calEvent;
	}

	/**
	 * Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eventId the primary key of the cal event
	 * @return the cal event that was removed
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent remove(long eventId)
		throws NoSuchEventException, SystemException {
		return remove((Serializable)eventId);
	}

	/**
	 * Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cal event
	 * @return the cal event that was removed
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent remove(Serializable primaryKey)
		throws NoSuchEventException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CalEvent calEvent = (CalEvent)session.get(CalEventImpl.class,
					primaryKey);

			if (calEvent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(calEvent);
		}
		catch (NoSuchEventException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CalEvent removeImpl(CalEvent calEvent) throws SystemException {
		calEvent = toUnwrappedModel(calEvent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(calEvent)) {
				calEvent = (CalEvent)session.get(CalEventImpl.class,
						calEvent.getPrimaryKeyObj());
			}

			if (calEvent != null) {
				session.delete(calEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (calEvent != null) {
			clearCache(calEvent);
		}

		return calEvent;
	}

	@Override
	public CalEvent updateImpl(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws SystemException {
		calEvent = toUnwrappedModel(calEvent);

		boolean isNew = calEvent.isNew();

		CalEventModelImpl calEventModelImpl = (CalEventModelImpl)calEvent;

		if (Validator.isNull(calEvent.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			calEvent.setUuid(uuid);
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = calEvent.getCompanyId();

			long groupId = calEvent.getGroupId();

			long eventId = 0;

			if (!isNew) {
				eventId = calEvent.getPrimaryKey();
			}

			try {
				calEvent.setTitle(SanitizerUtil.sanitize(companyId, groupId,
						userId,
						com.liferay.portlet.calendar.model.CalEvent.class.getName(),
						eventId, ContentTypes.TEXT_PLAIN, Sanitizer.MODE_ALL,
						calEvent.getTitle(), null));

				calEvent.setDescription(SanitizerUtil.sanitize(companyId,
						groupId, userId,
						com.liferay.portlet.calendar.model.CalEvent.class.getName(),
						eventId, ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
						calEvent.getDescription(), null));
			}
			catch (SanitizerException se) {
				throw new SystemException(se);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (calEvent.isNew()) {
				session.save(calEvent);

				calEvent.setNew(false);
			}
			else {
				session.merge(calEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CalEventModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { calEventModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { calEventModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalUuid(),
						calEventModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						calEventModelImpl.getUuid(),
						calEventModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { calEventModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { calEventModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId(),
						calEventModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						calEventModelImpl.getGroupId(),
						calEventModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId(),
						calEventModelImpl.getOriginalRepeating()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R,
					args);

				args = new Object[] {
						calEventModelImpl.getGroupId(),
						calEventModelImpl.getRepeating()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId(),
						calEventModelImpl.getOriginalType(),
						calEventModelImpl.getOriginalRepeating()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R,
					args);

				args = new Object[] {
						calEventModelImpl.getGroupId(),
						calEventModelImpl.getType(),
						calEventModelImpl.getRepeating()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R,
					args);
			}
		}

		EntityCacheUtil.putResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventImpl.class, calEvent.getPrimaryKey(), calEvent);

		clearUniqueFindersCache(calEvent);
		cacheUniqueFindersCache(calEvent);

		calEvent.resetOriginalValues();

		return calEvent;
	}

	protected CalEvent toUnwrappedModel(CalEvent calEvent) {
		if (calEvent instanceof CalEventImpl) {
			return calEvent;
		}

		CalEventImpl calEventImpl = new CalEventImpl();

		calEventImpl.setNew(calEvent.isNew());
		calEventImpl.setPrimaryKey(calEvent.getPrimaryKey());

		calEventImpl.setUuid(calEvent.getUuid());
		calEventImpl.setEventId(calEvent.getEventId());
		calEventImpl.setGroupId(calEvent.getGroupId());
		calEventImpl.setCompanyId(calEvent.getCompanyId());
		calEventImpl.setUserId(calEvent.getUserId());
		calEventImpl.setUserName(calEvent.getUserName());
		calEventImpl.setCreateDate(calEvent.getCreateDate());
		calEventImpl.setModifiedDate(calEvent.getModifiedDate());
		calEventImpl.setTitle(calEvent.getTitle());
		calEventImpl.setDescription(calEvent.getDescription());
		calEventImpl.setLocation(calEvent.getLocation());
		calEventImpl.setStartDate(calEvent.getStartDate());
		calEventImpl.setEndDate(calEvent.getEndDate());
		calEventImpl.setDurationHour(calEvent.getDurationHour());
		calEventImpl.setDurationMinute(calEvent.getDurationMinute());
		calEventImpl.setAllDay(calEvent.isAllDay());
		calEventImpl.setTimeZoneSensitive(calEvent.isTimeZoneSensitive());
		calEventImpl.setType(calEvent.getType());
		calEventImpl.setRepeating(calEvent.isRepeating());
		calEventImpl.setRecurrence(calEvent.getRecurrence());
		calEventImpl.setRemindBy(calEvent.getRemindBy());
		calEventImpl.setFirstReminder(calEvent.getFirstReminder());
		calEventImpl.setSecondReminder(calEvent.getSecondReminder());

		return calEventImpl;
	}

	/**
	 * Returns the cal event with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cal event
	 * @return the cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEventException, SystemException {
		CalEvent calEvent = fetchByPrimaryKey(primaryKey);

		if (calEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return calEvent;
	}

	/**
	 * Returns the cal event with the primary key or throws a {@link com.liferay.portlet.calendar.NoSuchEventException} if it could not be found.
	 *
	 * @param eventId the primary key of the cal event
	 * @return the cal event
	 * @throws com.liferay.portlet.calendar.NoSuchEventException if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent findByPrimaryKey(long eventId)
		throws NoSuchEventException, SystemException {
		return findByPrimaryKey((Serializable)eventId);
	}

	/**
	 * Returns the cal event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cal event
	 * @return the cal event, or <code>null</code> if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		CalEvent calEvent = (CalEvent)EntityCacheUtil.getResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
				CalEventImpl.class, primaryKey);

		if (calEvent == _nullCalEvent) {
			return null;
		}

		if (calEvent == null) {
			Session session = null;

			try {
				session = openSession();

				calEvent = (CalEvent)session.get(CalEventImpl.class, primaryKey);

				if (calEvent != null) {
					cacheResult(calEvent);
				}
				else {
					EntityCacheUtil.putResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
						CalEventImpl.class, primaryKey, _nullCalEvent);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
					CalEventImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return calEvent;
	}

	/**
	 * Returns the cal event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param eventId the primary key of the cal event
	 * @return the cal event, or <code>null</code> if a cal event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CalEvent fetchByPrimaryKey(long eventId) throws SystemException {
		return fetchByPrimaryKey((Serializable)eventId);
	}

	/**
	 * Returns all the cal events.
	 *
	 * @return the cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.calendar.model.impl.CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CalEvent> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<CalEvent> list = (List<CalEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CALEVENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CALEVENT;

				if (pagination) {
					sql = sql.concat(CalEventModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CalEvent>(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cal events from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (CalEvent calEvent : findAll()) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events.
	 *
	 * @return the number of cal events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CALEVENT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the cal event persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.calendar.model.CalEvent")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CalEvent>> listenersList = new ArrayList<ModelListener<CalEvent>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CalEvent>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(CalEventImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_CALEVENT = "SELECT calEvent FROM CalEvent calEvent";
	private static final String _SQL_SELECT_CALEVENT_WHERE = "SELECT calEvent FROM CalEvent calEvent WHERE ";
	private static final String _SQL_COUNT_CALEVENT = "SELECT COUNT(calEvent) FROM CalEvent calEvent";
	private static final String _SQL_COUNT_CALEVENT_WHERE = "SELECT COUNT(calEvent) FROM CalEvent calEvent WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "calEvent.eventId";
	private static final String _FILTER_SQL_SELECT_CALEVENT_WHERE = "SELECT DISTINCT {calEvent.*} FROM CalEvent calEvent WHERE ";
	private static final String _FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {CalEvent.*} FROM (SELECT DISTINCT calEvent.eventId FROM CalEvent calEvent WHERE ";
	private static final String _FILTER_SQL_SELECT_CALEVENT_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN CalEvent ON TEMP_TABLE.eventId = CalEvent.eventId";
	private static final String _FILTER_SQL_COUNT_CALEVENT_WHERE = "SELECT COUNT(DISTINCT calEvent.eventId) AS COUNT_VALUE FROM CalEvent calEvent WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "calEvent";
	private static final String _FILTER_ENTITY_TABLE = "CalEvent";
	private static final String _ORDER_BY_ENTITY_ALIAS = "calEvent.";
	private static final String _ORDER_BY_ENTITY_TABLE = "CalEvent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CalEvent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CalEvent exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(CalEventPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
	private static CalEvent _nullCalEvent = new CalEventImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CalEvent> toCacheModel() {
				return _nullCalEventCacheModel;
			}
		};

	private static CacheModel<CalEvent> _nullCalEventCacheModel = new CacheModel<CalEvent>() {
			@Override
			public CalEvent toEntityModel() {
				return _nullCalEvent;
			}
		};
}