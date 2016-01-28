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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.calendar.exception.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.calendar.model.impl.CalEventModelImpl;
import com.liferay.portlet.calendar.service.persistence.CalEventPersistence;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 * @see com.liferay.portlet.calendar.service.persistence.CalEventUtil
 * @deprecated As of 7.0.0, with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
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
	 */
	@Override
	public List<CalEvent> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByUuid(String uuid, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByUuid(String uuid, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalEvent calEvent : list) {
					if (!Validator.equals(uuid, calEvent.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByUuid_First(String uuid,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByUuid_First(String uuid,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByUuid_Last(String uuid,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByUuid_Last(String uuid,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByUuid_PrevAndNext(long eventId, String uuid,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		CalEvent calEvent, String uuid,
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
	 */
	@Override
	public void removeByUuid(String uuid) {
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
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * Returns the cal event where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEventException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cal event
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByUUID_G(String uuid, long groupId)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cal event where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cal event, or <code>null</code> if a matching cal event could not be found
	 */
	@Override
	public CalEvent fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CalEvent calEvent = list.get(0);

					result = calEvent;

					cacheResult(calEvent);

					if ((calEvent.getUuid() == null) ||
							!calEvent.getUuid().equals(uuid) ||
							(calEvent.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, calEvent);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
	 */
	@Override
	public CalEvent removeByUUID_G(String uuid, long groupId)
		throws NoSuchEventException {
		CalEvent calEvent = findByUUID_G(uuid, groupId);

		return remove(calEvent);
	}

	/**
	 * Returns the number of cal events where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cal events
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 */
	@Override
	public List<CalEvent> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
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
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByUuid_C_PrevAndNext(long eventId, String uuid,
		long companyId, OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
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
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 */
	@Override
	public List<CalEvent> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalEvent calEvent : list) {
					if ((groupId != calEvent.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByGroupId_First(long groupId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByGroupId_First(long groupId,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByGroupId_Last(long groupId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByGroupId_Last(long groupId,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByGroupId_PrevAndNext(long eventId, long groupId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		CalEvent calEvent, long groupId,
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
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
	 * Removes all the cal events where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
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
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "calEvent.groupId = ?";
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
	 */
	@Override
	public List<CalEvent> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cal events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalEvent calEvent : list) {
					if ((companyId != calEvent.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByCompanyId_First(long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByCompanyId_First(long companyId,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByCompanyId_Last(long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByCompanyId_Last(long companyId,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByCompanyId_PrevAndNext(long eventId, long companyId,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		CalEvent calEvent, long companyId,
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
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
	 */
	@Override
	public void removeByCompanyId(long companyId) {
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
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "calEvent.companyId = ?";
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
	 */
	@Override
	public List<CalEvent> findByNotRemindBy(int remindBy) {
		return findByNotRemindBy(remindBy, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where remindBy &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param remindBy the remind by
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByNotRemindBy(int remindBy, int start, int end) {
		return findByNotRemindBy(remindBy, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where remindBy &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param remindBy the remind by
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByNotRemindBy(int remindBy, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return findByNotRemindBy(remindBy, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where remindBy &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param remindBy the remind by
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByNotRemindBy(int remindBy, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_NOTREMINDBY;
		finderArgs = new Object[] { remindBy, start, end, orderByComparator };

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalEvent calEvent : list) {
					if ((remindBy == calEvent.getRemindBy())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByNotRemindBy_First(int remindBy,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByNotRemindBy_First(int remindBy,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByNotRemindBy_Last(int remindBy,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByNotRemindBy_Last(int remindBy,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByNotRemindBy_PrevAndNext(long eventId, int remindBy,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		CalEvent calEvent, int remindBy,
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
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
	 */
	@Override
	public void removeByNotRemindBy(int remindBy) {
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
	 */
	@Override
	public int countByNotRemindBy(int remindBy) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_NOTREMINDBY;

		Object[] finderArgs = new Object[] { remindBy };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String type, int start,
		int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String type, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String type, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
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
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByG_T_First(long groupId, String type,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByG_T_First(long groupId, String type,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByG_T_Last(long groupId, String type,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByG_T_Last(long groupId, String type,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByG_T_PrevAndNext(long eventId, long groupId,
		String type, OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		long groupId, String type,
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
	 * Returns all the cal events where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @return the matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String[] types) {
		return findByG_T(groupId, types, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String[] types, int start,
		int end) {
		return findByG_T(groupId, types, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String[] types, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return findByG_T(groupId, types, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T(long groupId, String[] types, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
		if (types == null) {
			types = new String[0];
		}
		else if (types.length > 1) {
			types = ArrayUtil.distinct(types, NULL_SAFE_STRING_COMPARATOR);

			Arrays.sort(types, NULL_SAFE_STRING_COMPARATOR);
		}

		if (types.length == 1) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T,
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
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (types.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type == null) {
						query.append(_FINDER_COLUMN_G_T_TYPE_1);
					}
					else if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TYPE_2);
					}

					if ((i + 1) < types.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

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

				for (String type : types) {
					if ((type != null) && !type.isEmpty()) {
						qPos.add(type);
					}
				}

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T,
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
	 */
	@Override
	public void removeByG_T(long groupId, String type) {
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
	 */
	@Override
	public int countByG_T(long groupId, String type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 */
	@Override
	public int countByG_T(long groupId, String[] types) {
		if (types == null) {
			types = new String[0];
		}
		else if (types.length > 1) {
			types = ArrayUtil.distinct(types, NULL_SAFE_STRING_COMPARATOR);

			Arrays.sort(types, NULL_SAFE_STRING_COMPARATOR);
		}

		Object[] finderArgs = new Object[] { groupId, StringUtil.merge(types) };

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			if (types.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < types.length; i++) {
					String type = types[i];

					if (type == null) {
						query.append(_FINDER_COLUMN_G_T_TYPE_1);
					}
					else if (type.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_T_TYPE_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_T_TYPE_2);
					}

					if ((i + 1) < types.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				for (String type : types) {
					if ((type != null) && !type.isEmpty()) {
						qPos.add(type);
					}
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "calEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_1 = "calEvent.type IS NULL";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "calEvent.type = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_3 = "(calEvent.type IS NULL OR calEvent.type = '')";
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
	 */
	@Override
	public List<CalEvent> findByG_R(long groupId, boolean repeating) {
		return findByG_R(groupId, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_R(long groupId, boolean repeating, int start,
		int end) {
		return findByG_R(groupId, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_R(long groupId, boolean repeating, int start,
		int end, OrderByComparator<CalEvent> orderByComparator) {
		return findByG_R(groupId, repeating, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_R(long groupId, boolean repeating, int start,
		int end, OrderByComparator<CalEvent> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
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
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByG_R_First(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByG_R_First(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByG_R_Last(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByG_R_Last(long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByG_R_PrevAndNext(long eventId, long groupId,
		boolean repeating, OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		long groupId, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
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
	 * Removes all the cal events where groupId = &#63; and repeating = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param repeating the repeating
	 */
	@Override
	public void removeByG_R(long groupId, boolean repeating) {
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
	 */
	@Override
	public int countByG_R(long groupId, boolean repeating) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_R;

		Object[] finderArgs = new Object[] { groupId, repeating };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
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
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String type,
		boolean repeating) {
		return findByG_T_R(groupId, type, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String type,
		boolean repeating, int start, int end) {
		return findByG_T_R(groupId, type, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String type,
		boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return findByG_T_R(groupId, type, repeating, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String type,
		boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
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
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByG_T_R_First(long groupId, String type,
		boolean repeating, OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByG_T_R_First(long groupId, String type,
		boolean repeating, OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a matching cal event could not be found
	 */
	@Override
	public CalEvent findByG_T_R_Last(long groupId, String type,
		boolean repeating, OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
	 */
	@Override
	public CalEvent fetchByG_T_R_Last(long groupId, String type,
		boolean repeating, OrderByComparator<CalEvent> orderByComparator) {
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
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent[] findByG_T_R_PrevAndNext(long eventId, long groupId,
		String type, boolean repeating,
		OrderByComparator<CalEvent> orderByComparator)
		throws NoSuchEventException {
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
		OrderByComparator<CalEvent> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
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
	 * Returns all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @return the matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String[] types,
		boolean repeating) {
		return findByG_T_R(groupId, types, repeating, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String[] types,
		boolean repeating, int start, int end) {
		return findByG_T_R(groupId, types, repeating, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = any &#63; and repeating = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param types the types
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String[] types,
		boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return findByG_T_R(groupId, types, repeating, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events where groupId = &#63; and type = &#63; and repeating = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param repeating the repeating
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cal events
	 */
	@Override
	public List<CalEvent> findByG_T_R(long groupId, String[] types,
		boolean repeating, int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
		if (types == null) {
			types = new String[0];
		}
		else if (types.length > 1) {
			types = ArrayUtil.distinct(types, NULL_SAFE_STRING_COMPARATOR);

			Arrays.sort(types, NULL_SAFE_STRING_COMPARATOR);
		}

		if (types.length == 1) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R,
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
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

			if (types.length > 0) {
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

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

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

				for (String type : types) {
					if ((type != null) && !type.isEmpty()) {
						qPos.add(type);
					}
				}

				qPos.add(repeating);

				if (!pagination) {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_R,
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
	 */
	@Override
	public void removeByG_T_R(long groupId, String type, boolean repeating) {
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
	 */
	@Override
	public int countByG_T_R(long groupId, String type, boolean repeating) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T_R;

		Object[] finderArgs = new Object[] { groupId, type, repeating };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 */
	@Override
	public int countByG_T_R(long groupId, String[] types, boolean repeating) {
		if (types == null) {
			types = new String[0];
		}
		else if (types.length > 1) {
			types = ArrayUtil.distinct(types, NULL_SAFE_STRING_COMPARATOR);

			Arrays.sort(types, NULL_SAFE_STRING_COMPARATOR);
		}

		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(types), repeating
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_CALEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_T_R_GROUPID_2);

			if (types.length > 0) {
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

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_T_R_REPEATING_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				for (String type : types) {
					if ((type != null) && !type.isEmpty()) {
						qPos.add(type);
					}
				}

				qPos.add(repeating);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_R,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_R,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_T_R_GROUPID_2 = "calEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_1 = "calEvent.type IS NULL AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_2 = "calEvent.type = ? AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_3 = "(calEvent.type IS NULL OR calEvent.type = '') AND ";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_4 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_1) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_2) + ")";
	private static final String _FINDER_COLUMN_G_T_R_TYPE_6 = "(" +
		removeConjunction(_FINDER_COLUMN_G_T_R_TYPE_3) + ")";
	private static final String _FINDER_COLUMN_G_T_R_REPEATING_2 = "calEvent.repeating = ?";

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
		entityCache.putResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventImpl.class, calEvent.getPrimaryKey(), calEvent);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
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
			if (entityCache.getResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CalEventImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cal event.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CalEvent calEvent) {
		entityCache.removeResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventImpl.class, calEvent.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CalEventModelImpl)calEvent);
	}

	@Override
	public void clearCache(List<CalEvent> calEvents) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CalEvent calEvent : calEvents) {
			entityCache.removeResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
				CalEventImpl.class, calEvent.getPrimaryKey());

			clearUniqueFindersCache((CalEventModelImpl)calEvent);
		}
	}

	protected void cacheUniqueFindersCache(
		CalEventModelImpl calEventModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					calEventModelImpl.getUuid(), calEventModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				calEventModelImpl);
		}
		else {
			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getUuid(),
						calEventModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					calEventModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(CalEventModelImpl calEventModelImpl) {
		Object[] args = new Object[] {
				calEventModelImpl.getUuid(), calEventModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((calEventModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					calEventModelImpl.getOriginalUuid(),
					calEventModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
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

		calEvent.setCompanyId(companyProvider.getCompanyId());

		return calEvent;
	}

	/**
	 * Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eventId the primary key of the cal event
	 * @return the cal event that was removed
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent remove(long eventId) throws NoSuchEventException {
		return remove((Serializable)eventId);
	}

	/**
	 * Removes the cal event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cal event
	 * @return the cal event that was removed
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent remove(Serializable primaryKey) throws NoSuchEventException {
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
	protected CalEvent removeImpl(CalEvent calEvent) {
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
	public CalEvent updateImpl(CalEvent calEvent) {
		calEvent = toUnwrappedModel(calEvent);

		boolean isNew = calEvent.isNew();

		CalEventModelImpl calEventModelImpl = (CalEventModelImpl)calEvent;

		if (Validator.isNull(calEvent.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			calEvent.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (calEvent.getCreateDate() == null)) {
			if (serviceContext == null) {
				calEvent.setCreateDate(now);
			}
			else {
				calEvent.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!calEventModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				calEvent.setModifiedDate(now);
			}
			else {
				calEvent.setModifiedDate(serviceContext.getModifiedDate(now));
			}
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
				calEvent = (CalEvent)session.merge(calEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CalEventModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { calEventModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { calEventModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalUuid(),
						calEventModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						calEventModelImpl.getUuid(),
						calEventModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { calEventModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { calEventModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId(),
						calEventModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						calEventModelImpl.getGroupId(),
						calEventModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId(),
						calEventModelImpl.getOriginalRepeating()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R,
					args);

				args = new Object[] {
						calEventModelImpl.getGroupId(),
						calEventModelImpl.getRepeating()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_R,
					args);
			}

			if ((calEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						calEventModelImpl.getOriginalGroupId(),
						calEventModelImpl.getOriginalType(),
						calEventModelImpl.getOriginalRepeating()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R,
					args);

				args = new Object[] {
						calEventModelImpl.getGroupId(),
						calEventModelImpl.getType(),
						calEventModelImpl.getRepeating()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_R,
					args);
			}
		}

		entityCache.putResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
			CalEventImpl.class, calEvent.getPrimaryKey(), calEvent, false);

		clearUniqueFindersCache(calEventModelImpl);
		cacheUniqueFindersCache(calEventModelImpl, isNew);

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
	 * Returns the cal event with the primary key or throws a {@link com.liferay.portal.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cal event
	 * @return the cal event
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEventException {
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
	 * Returns the cal event with the primary key or throws a {@link NoSuchEventException} if it could not be found.
	 *
	 * @param eventId the primary key of the cal event
	 * @return the cal event
	 * @throws NoSuchEventException if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent findByPrimaryKey(long eventId) throws NoSuchEventException {
		return findByPrimaryKey((Serializable)eventId);
	}

	/**
	 * Returns the cal event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cal event
	 * @return the cal event, or <code>null</code> if a cal event with the primary key could not be found
	 */
	@Override
	public CalEvent fetchByPrimaryKey(Serializable primaryKey) {
		CalEvent calEvent = (CalEvent)entityCache.getResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
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
					entityCache.putResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
						CalEventImpl.class, primaryKey, _nullCalEvent);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
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
	 */
	@Override
	public CalEvent fetchByPrimaryKey(long eventId) {
		return fetchByPrimaryKey((Serializable)eventId);
	}

	@Override
	public Map<Serializable, CalEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CalEvent> map = new HashMap<Serializable, CalEvent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CalEvent calEvent = fetchByPrimaryKey(primaryKey);

			if (calEvent != null) {
				map.put(primaryKey, calEvent);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			CalEvent calEvent = (CalEvent)entityCache.getResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
					CalEventImpl.class, primaryKey);

			if (calEvent == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, calEvent);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CALEVENT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CalEvent calEvent : (List<CalEvent>)q.list()) {
				map.put(calEvent.getPrimaryKeyObj(), calEvent);

				cacheResult(calEvent);

				uncachedPrimaryKeys.remove(calEvent.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CalEventModelImpl.ENTITY_CACHE_ENABLED,
					CalEventImpl.class, primaryKey, _nullCalEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the cal events.
	 *
	 * @return the cal events
	 */
	@Override
	public List<CalEvent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cal events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @return the range of cal events
	 */
	@Override
	public List<CalEvent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cal events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cal events
	 */
	@Override
	public List<CalEvent> findAll(int start, int end,
		OrderByComparator<CalEvent> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cal events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CalEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cal events
	 * @param end the upper bound of the range of cal events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cal events
	 */
	@Override
	public List<CalEvent> findAll(int start, int end,
		OrderByComparator<CalEvent> orderByComparator, boolean retrieveFromCache) {
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

		List<CalEvent> list = null;

		if (retrieveFromCache) {
			list = (List<CalEvent>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CalEvent>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

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
	 */
	@Override
	public void removeAll() {
		for (CalEvent calEvent : findAll()) {
			remove(calEvent);
		}
	}

	/**
	 * Returns the number of cal events.
	 *
	 * @return the number of cal events
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CALEVENT);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CalEventModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cal event persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CalEventImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_CALEVENT = "SELECT calEvent FROM CalEvent calEvent";
	private static final String _SQL_SELECT_CALEVENT_WHERE_PKS_IN = "SELECT calEvent FROM CalEvent calEvent WHERE eventId IN (";
	private static final String _SQL_SELECT_CALEVENT_WHERE = "SELECT calEvent FROM CalEvent calEvent WHERE ";
	private static final String _SQL_COUNT_CALEVENT = "SELECT COUNT(calEvent) FROM CalEvent calEvent";
	private static final String _SQL_COUNT_CALEVENT_WHERE = "SELECT COUNT(calEvent) FROM CalEvent calEvent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "calEvent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CalEvent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CalEvent exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CalEventPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
	private static final CalEvent _nullCalEvent = new CalEventImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CalEvent> toCacheModel() {
				return _nullCalEventCacheModel;
			}
		};

	private static final CacheModel<CalEvent> _nullCalEventCacheModel = new CacheModel<CalEvent>() {
			@Override
			public CalEvent toEntityModel() {
				return _nullCalEvent;
			}
		};
}