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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchUserNotificationEventException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.UserNotificationEventPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.model.impl.UserNotificationEventModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the user notification event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserNotificationEventPersistenceImpl
	extends BasePersistenceImpl<UserNotificationEvent>
	implements UserNotificationEventPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserNotificationEventUtil</code> to access the user notification event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserNotificationEventImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the user notification events where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if (!uuid.equals(userNotificationEvent.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
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

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_First(
			String uuid,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByUuid_First(
			uuid, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByUuid_First(
		String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_Last(
			String uuid,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByUuid_Last(
			uuid, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByUuid_Last(
		String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByUuid_PrevAndNext(
			long userNotificationEventId, String uuid,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		uuid = Objects.toString(uuid, "");

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, userNotificationEvent, uuid, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUuid_PrevAndNext(
				session, userNotificationEvent, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByUuid_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		String uuid, OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (UserNotificationEvent userNotificationEvent :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"userNotificationEvent.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(userNotificationEvent.uuid IS NULL OR userNotificationEvent.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if (!uuid.equals(userNotificationEvent.getUuid()) ||
						(companyId != userNotificationEvent.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
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

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByUuid_C_PrevAndNext(
			long userNotificationEventId, String uuid, long companyId,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		uuid = Objects.toString(uuid, "");

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, userNotificationEvent, uuid, companyId,
				orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUuid_C_PrevAndNext(
				session, userNotificationEvent, uuid, companyId,
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

	protected UserNotificationEvent getByUuid_C_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		String uuid, long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (UserNotificationEvent userNotificationEvent :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"userNotificationEvent.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(userNotificationEvent.uuid IS NULL OR userNotificationEvent.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"userNotificationEvent.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the user notification events where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUserId(
		long userId, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if (userId != userNotificationEvent.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUserId_First(
			long userId,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByUserId_First(
			userId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByUserId_First(
		long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUserId_Last(
			long userId,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByUserId_Last(
			userId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByUserId_Last(
		long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByUserId_PrevAndNext(
			long userNotificationEventId, long userId,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, userNotificationEvent, userId, orderByComparator,
				true);

			array[1] = userNotificationEvent;

			array[2] = getByUserId_PrevAndNext(
				session, userNotificationEvent, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByUserId_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (UserNotificationEvent userNotificationEvent :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"userNotificationEvent.userId = ?";

	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;

	/**
	 * Returns all the user notification events where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByType(String type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByType(
		String type, int start, int end) {

		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByType(
		String type, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByType(
		String type, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByType;
				finderArgs = new Object[] {type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByType;
			finderArgs = new Object[] {type, start, end, orderByComparator};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if (!type.equals(userNotificationEvent.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindType) {
					qPos.add(type);
				}

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByType_First(
			String type,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByType_First(
			type, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByType_First(
		String type,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByType(
			type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByType_Last(
			String type,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByType_Last(
			type, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByType_Last(
		String type,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByType(
			type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where type = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByType_PrevAndNext(
			long userNotificationEventId, String type,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		type = Objects.toString(type, "");

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByType_PrevAndNext(
				session, userNotificationEvent, type, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByType_PrevAndNext(
				session, userNotificationEvent, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByType_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		String type, OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_TYPE_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(String type) {
		for (UserNotificationEvent userNotificationEvent :
				findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByType(String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByType;

		Object[] finderArgs = new Object[] {type};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_TYPE_TYPE_2 =
		"userNotificationEvent.type = ?";

	private static final String _FINDER_COLUMN_TYPE_TYPE_3 =
		"(userNotificationEvent.type IS NULL OR userNotificationEvent.type = '')";

	private FinderPath _finderPathWithPaginationFindByU_DT;
	private FinderPath _finderPathWithoutPaginationFindByU_DT;
	private FinderPath _finderPathCountByU_DT;

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT(
		long userId, int deliveryType) {

		return findByU_DT(
			userId, deliveryType, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT(
		long userId, int deliveryType, int start, int end) {

		return findByU_DT(userId, deliveryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT(
		long userId, int deliveryType, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_DT(
			userId, deliveryType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT(
		long userId, int deliveryType, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_DT;
				finderArgs = new Object[] {userId, deliveryType};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_DT;
			finderArgs = new Object[] {
				userId, deliveryType, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_DELIVERYTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_First(
			long userId, int deliveryType,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_First(
			userId, deliveryType, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_First(
		long userId, int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_DT(
			userId, deliveryType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_Last(
			long userId, int deliveryType,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_Last(
			userId, deliveryType, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_Last(
		long userId, int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_DT(userId, deliveryType);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT(
			userId, deliveryType, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_PrevAndNext(
			long userNotificationEventId, long userId, int deliveryType,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType,
				orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType,
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

	protected UserNotificationEvent getByU_DT_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_DELIVERYTYPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(deliveryType);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and deliveryType = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 */
	@Override
	public void removeByU_DT(long userId, int deliveryType) {
		for (UserNotificationEvent userNotificationEvent :
				findByU_DT(
					userId, deliveryType, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_DT(long userId, int deliveryType) {
		FinderPath finderPath = _finderPathCountByU_DT;

		Object[] finderArgs = new Object[] {userId, deliveryType};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_DELIVERYTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

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

	private static final String _FINDER_COLUMN_U_DT_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ?";

	private FinderPath _finderPathWithPaginationFindByU_D;
	private FinderPath _finderPathWithoutPaginationFindByU_D;
	private FinderPath _finderPathCountByU_D;

	/**
	 * Returns all the user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D(
		long userId, boolean delivered) {

		return findByU_D(
			userId, delivered, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D(
		long userId, boolean delivered, int start, int end) {

		return findByU_D(userId, delivered, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D(
		long userId, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_D(
			userId, delivered, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D(
		long userId, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_D;
				finderArgs = new Object[] {userId, delivered};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_D;
			finderArgs = new Object[] {
				userId, delivered, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(delivered != userNotificationEvent.isDelivered())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_USERID_2);

			query.append(_FINDER_COLUMN_U_D_DELIVERED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_First(
			long userId, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_First(
			userId, delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_First(
		long userId, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_D(
			userId, delivered, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_Last(
			long userId, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_Last(
			userId, delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_Last(
		long userId, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_D(userId, delivered);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_D(
			userId, delivered, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_D_PrevAndNext(
			long userNotificationEventId, long userId, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_D_PrevAndNext(
				session, userNotificationEvent, userId, delivered,
				orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_D_PrevAndNext(
				session, userNotificationEvent, userId, delivered,
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

	protected UserNotificationEvent getByU_D_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_D_USERID_2);

		query.append(_FINDER_COLUMN_U_D_DELIVERED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(delivered);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and delivered = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 */
	@Override
	public void removeByU_D(long userId, boolean delivered) {
		for (UserNotificationEvent userNotificationEvent :
				findByU_D(
					userId, delivered, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_D(long userId, boolean delivered) {
		FinderPath finderPath = _finderPathCountByU_D;

		Object[] finderArgs = new Object[] {userId, delivered};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_USERID_2);

			query.append(_FINDER_COLUMN_U_D_DELIVERED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

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

	private static final String _FINDER_COLUMN_U_D_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_DELIVERED_2 =
		"userNotificationEvent.delivered = ?";

	private FinderPath _finderPathWithPaginationFindByU_A;
	private FinderPath _finderPathWithoutPaginationFindByU_A;
	private FinderPath _finderPathCountByU_A;

	/**
	 * Returns all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_A(
		long userId, boolean archived) {

		return findByU_A(
			userId, archived, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_A(
		long userId, boolean archived, int start, int end) {

		return findByU_A(userId, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_A(
		long userId, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_A(userId, archived, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_A(
		long userId, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_A;
				finderArgs = new Object[] {userId, archived};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_A;
			finderArgs = new Object[] {
				userId, archived, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_A_USERID_2);

			query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_A_First(
			long userId, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_A_First(
			userId, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_A_First(
		long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_A(
			userId, archived, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_A_Last(
			long userId, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_A_Last(
			userId, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_A_Last(
		long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_A(userId, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_A(
			userId, archived, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_A_PrevAndNext(
			long userNotificationEventId, long userId, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_A_PrevAndNext(
				session, userNotificationEvent, userId, archived,
				orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_A_PrevAndNext(
				session, userNotificationEvent, userId, archived,
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

	protected UserNotificationEvent getByU_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_A_USERID_2);

		query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 */
	@Override
	public void removeByU_A(long userId, boolean archived) {
		for (UserNotificationEvent userNotificationEvent :
				findByU_A(
					userId, archived, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_A(long userId, boolean archived) {
		FinderPath finderPath = _finderPathCountByU_A;

		Object[] finderArgs = new Object[] {userId, archived};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_A_USERID_2);

			query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_DT_D;
	private FinderPath _finderPathWithoutPaginationFindByU_DT_D;
	private FinderPath _finderPathCountByU_DT_D;

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D(
		long userId, int deliveryType, boolean delivered) {

		return findByU_DT_D(
			userId, deliveryType, delivered, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D(
		long userId, int deliveryType, boolean delivered, int start, int end) {

		return findByU_DT_D(userId, deliveryType, delivered, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D(
		long userId, int deliveryType, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_DT_D(
			userId, deliveryType, delivered, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D(
		long userId, int deliveryType, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_DT_D;
				finderArgs = new Object[] {userId, deliveryType, delivered};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_DT_D;
			finderArgs = new Object[] {
				userId, deliveryType, delivered, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.isDelivered())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_DELIVERED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_First(
			long userId, int deliveryType, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_First(
			userId, deliveryType, delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_First(
		long userId, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_DT_D(
			userId, deliveryType, delivered, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_Last(
			long userId, int deliveryType, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_Last(
			userId, deliveryType, delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_Last(
		long userId, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_DT_D(userId, deliveryType, delivered);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_D(
			userId, deliveryType, delivered, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_D_PrevAndNext(
			long userNotificationEventId, long userId, int deliveryType,
			boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_D_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
				orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_D_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
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

	protected UserNotificationEvent getByU_DT_D_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_D_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_D_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_D_DELIVERED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(deliveryType);

		qPos.add(delivered);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 */
	@Override
	public void removeByU_DT_D(
		long userId, int deliveryType, boolean delivered) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_DT_D(
					userId, deliveryType, delivered, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_DT_D(long userId, int deliveryType, boolean delivered) {
		FinderPath finderPath = _finderPathCountByU_DT_D;

		Object[] finderArgs = new Object[] {userId, deliveryType, delivered};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_DELIVERED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

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

	private static final String _FINDER_COLUMN_U_DT_D_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_DELIVERED_2 =
		"userNotificationEvent.delivered = ?";

	private FinderPath _finderPathWithPaginationFindByU_DT_A;
	private FinderPath _finderPathWithoutPaginationFindByU_DT_A;
	private FinderPath _finderPathCountByU_DT_A;

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_A(
		long userId, int deliveryType, boolean archived) {

		return findByU_DT_A(
			userId, deliveryType, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_A(
		long userId, int deliveryType, boolean archived, int start, int end) {

		return findByU_DT_A(userId, deliveryType, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_A(
		long userId, int deliveryType, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_DT_A(
			userId, deliveryType, archived, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_A(
		long userId, int deliveryType, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_DT_A;
				finderArgs = new Object[] {userId, deliveryType, archived};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_DT_A;
			finderArgs = new Object[] {
				userId, deliveryType, archived, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_A_First(
			long userId, int deliveryType, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_A_First(
			userId, deliveryType, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_A_First(
		long userId, int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_DT_A(
			userId, deliveryType, archived, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_A_Last(
			long userId, int deliveryType, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_A_Last(
			userId, deliveryType, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_A_Last(
		long userId, int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_DT_A(userId, deliveryType, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_A(
			userId, deliveryType, archived, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_A_PrevAndNext(
			long userNotificationEventId, long userId, int deliveryType,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, archived,
				orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, archived,
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

	protected UserNotificationEvent getByU_DT_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_A_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(deliveryType);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 */
	@Override
	public void removeByU_DT_A(
		long userId, int deliveryType, boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_DT_A(
					userId, deliveryType, archived, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_DT_A(long userId, int deliveryType, boolean archived) {
		FinderPath finderPath = _finderPathCountByU_DT_A;

		Object[] finderArgs = new Object[] {userId, deliveryType, archived};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_DT_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_A_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_D_AR;
	private FinderPath _finderPathWithoutPaginationFindByU_D_AR;
	private FinderPath _finderPathCountByU_D_AR;

	/**
	 * Returns all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR(
		long userId, boolean delivered, boolean actionRequired) {

		return findByU_D_AR(
			userId, delivered, actionRequired, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR(
		long userId, boolean delivered, boolean actionRequired, int start,
		int end) {

		return findByU_D_AR(
			userId, delivered, actionRequired, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR(
		long userId, boolean delivered, boolean actionRequired, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_D_AR(
			userId, delivered, actionRequired, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR(
		long userId, boolean delivered, boolean actionRequired, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_D_AR;
				finderArgs = new Object[] {userId, delivered, actionRequired};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_D_AR;
			finderArgs = new Object[] {
				userId, delivered, actionRequired, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(delivered != userNotificationEvent.isDelivered()) ||
						(actionRequired !=
							userNotificationEvent.isActionRequired())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_AR_USERID_2);

			query.append(_FINDER_COLUMN_U_D_AR_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_AR_ACTIONREQUIRED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

				qPos.add(actionRequired);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_AR_First(
			long userId, boolean delivered, boolean actionRequired,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_AR_First(
			userId, delivered, actionRequired, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_AR_First(
		long userId, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_D_AR(
			userId, delivered, actionRequired, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_AR_Last(
			long userId, boolean delivered, boolean actionRequired,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_AR_Last(
			userId, delivered, actionRequired, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_AR_Last(
		long userId, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_D_AR(userId, delivered, actionRequired);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_D_AR(
			userId, delivered, actionRequired, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_D_AR_PrevAndNext(
			long userNotificationEventId, long userId, boolean delivered,
			boolean actionRequired,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_D_AR_PrevAndNext(
				session, userNotificationEvent, userId, delivered,
				actionRequired, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_D_AR_PrevAndNext(
				session, userNotificationEvent, userId, delivered,
				actionRequired, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_D_AR_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_D_AR_USERID_2);

		query.append(_FINDER_COLUMN_U_D_AR_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_D_AR_ACTIONREQUIRED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(delivered);

		qPos.add(actionRequired);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 */
	@Override
	public void removeByU_D_AR(
		long userId, boolean delivered, boolean actionRequired) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_D_AR(
					userId, delivered, actionRequired, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_D_AR(
		long userId, boolean delivered, boolean actionRequired) {

		FinderPath finderPath = _finderPathCountByU_D_AR;

		Object[] finderArgs = new Object[] {userId, delivered, actionRequired};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_AR_USERID_2);

			query.append(_FINDER_COLUMN_U_D_AR_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_AR_ACTIONREQUIRED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

				qPos.add(actionRequired);

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

	private static final String _FINDER_COLUMN_U_D_AR_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_AR_DELIVERED_2 =
		"userNotificationEvent.delivered = ? AND ";

	private static final String _FINDER_COLUMN_U_D_AR_ACTIONREQUIRED_2 =
		"userNotificationEvent.actionRequired = ?";

	private FinderPath _finderPathWithPaginationFindByU_D_A;
	private FinderPath _finderPathWithoutPaginationFindByU_D_A;
	private FinderPath _finderPathCountByU_D_A;

	/**
	 * Returns all the user notification events where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_A(
		long userId, boolean delivered, boolean archived) {

		return findByU_D_A(
			userId, delivered, archived, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_A(
		long userId, boolean delivered, boolean archived, int start, int end) {

		return findByU_D_A(userId, delivered, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_A(
		long userId, boolean delivered, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_D_A(
			userId, delivered, archived, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_A(
		long userId, boolean delivered, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_D_A;
				finderArgs = new Object[] {userId, delivered, archived};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_D_A;
			finderArgs = new Object[] {
				userId, delivered, archived, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(delivered != userNotificationEvent.isDelivered()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_A_First(
			long userId, boolean delivered, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_A_First(
			userId, delivered, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_A_First(
		long userId, boolean delivered, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_D_A(
			userId, delivered, archived, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_A_Last(
			long userId, boolean delivered, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_A_Last(
			userId, delivered, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_A_Last(
		long userId, boolean delivered, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_D_A(userId, delivered, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_D_A(
			userId, delivered, archived, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_D_A_PrevAndNext(
			long userNotificationEventId, long userId, boolean delivered,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_D_A_PrevAndNext(
				session, userNotificationEvent, userId, delivered, archived,
				orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_D_A_PrevAndNext(
				session, userNotificationEvent, userId, delivered, archived,
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

	protected UserNotificationEvent getByU_D_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, boolean delivered, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_D_A_USERID_2);

		query.append(_FINDER_COLUMN_U_D_A_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_D_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(delivered);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and delivered = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 */
	@Override
	public void removeByU_D_A(
		long userId, boolean delivered, boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_D_A(
					userId, delivered, archived, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_D_A(long userId, boolean delivered, boolean archived) {
		FinderPath finderPath = _finderPathCountByU_D_A;

		Object[] finderArgs = new Object[] {userId, delivered, archived};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_D_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_A_DELIVERED_2 =
		"userNotificationEvent.delivered = ? AND ";

	private static final String _FINDER_COLUMN_U_D_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_AR_A;
	private FinderPath _finderPathWithoutPaginationFindByU_AR_A;
	private FinderPath _finderPathCountByU_AR_A;

	/**
	 * Returns all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_AR_A(
		long userId, boolean actionRequired, boolean archived) {

		return findByU_AR_A(
			userId, actionRequired, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_AR_A(
		long userId, boolean actionRequired, boolean archived, int start,
		int end) {

		return findByU_AR_A(userId, actionRequired, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_AR_A(
		long userId, boolean actionRequired, boolean archived, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_AR_A(
			userId, actionRequired, archived, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_AR_A(
		long userId, boolean actionRequired, boolean archived, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_AR_A;
				finderArgs = new Object[] {userId, actionRequired, archived};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_AR_A;
			finderArgs = new Object[] {
				userId, actionRequired, archived, start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(actionRequired !=
							userNotificationEvent.isActionRequired()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_AR_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(actionRequired);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_AR_A_First(
			long userId, boolean actionRequired, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_AR_A_First(
			userId, actionRequired, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_AR_A_First(
		long userId, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_AR_A(
			userId, actionRequired, archived, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_AR_A_Last(
			long userId, boolean actionRequired, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_AR_A_Last(
			userId, actionRequired, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_AR_A_Last(
		long userId, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_AR_A(userId, actionRequired, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_AR_A(
			userId, actionRequired, archived, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_AR_A_PrevAndNext(
			long userNotificationEventId, long userId, boolean actionRequired,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, actionRequired,
				archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, actionRequired,
				archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_AR_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_AR_A_USERID_2);

		query.append(_FINDER_COLUMN_U_AR_A_ACTIONREQUIRED_2);

		query.append(_FINDER_COLUMN_U_AR_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(actionRequired);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 */
	@Override
	public void removeByU_AR_A(
		long userId, boolean actionRequired, boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_AR_A(
					userId, actionRequired, archived, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_AR_A(
		long userId, boolean actionRequired, boolean archived) {

		FinderPath finderPath = _finderPathCountByU_AR_A;

		Object[] finderArgs = new Object[] {userId, actionRequired, archived};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_AR_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(actionRequired);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_AR_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_AR_A_ACTIONREQUIRED_2 =
		"userNotificationEvent.actionRequired = ? AND ";

	private static final String _FINDER_COLUMN_U_AR_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_T_DT_D;
	private FinderPath _finderPathWithoutPaginationFindByU_T_DT_D;
	private FinderPath _finderPathCountByU_T_DT_D;

	/**
	 * Returns all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D(
		long userId, String type, int deliveryType, boolean delivered) {

		return findByU_T_DT_D(
			userId, type, deliveryType, delivered, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D(
		long userId, String type, int deliveryType, boolean delivered,
		int start, int end) {

		return findByU_T_DT_D(
			userId, type, deliveryType, delivered, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D(
		long userId, String type, int deliveryType, boolean delivered,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_T_DT_D(
			userId, type, deliveryType, delivered, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D(
		long userId, String type, int deliveryType, boolean delivered,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_T_DT_D;
				finderArgs = new Object[] {
					userId, type, deliveryType, delivered
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_T_DT_D;
			finderArgs = new Object[] {
				userId, type, deliveryType, delivered, start, end,
				orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						!type.equals(userNotificationEvent.getType()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.isDelivered())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_T_DT_D_USERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_U_T_DT_D_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_DT_D_TYPE_2);
			}

			query.append(_FINDER_COLUMN_U_T_DT_D_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_T_DT_D_DELIVERED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(deliveryType);

				qPos.add(delivered);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_T_DT_D_First(
			long userId, String type, int deliveryType, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_T_DT_D_First(
			userId, type, deliveryType, delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_T_DT_D_First(
		long userId, String type, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_T_DT_D(
			userId, type, deliveryType, delivered, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_T_DT_D_Last(
			long userId, String type, int deliveryType, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_T_DT_D_Last(
			userId, type, deliveryType, delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_T_DT_D_Last(
		long userId, String type, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_T_DT_D(userId, type, deliveryType, delivered);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_T_DT_D(
			userId, type, deliveryType, delivered, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_T_DT_D_PrevAndNext(
			long userNotificationEventId, long userId, String type,
			int deliveryType, boolean delivered,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		type = Objects.toString(type, "");

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_T_DT_D_PrevAndNext(
				session, userNotificationEvent, userId, type, deliveryType,
				delivered, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_T_DT_D_PrevAndNext(
				session, userNotificationEvent, userId, type, deliveryType,
				delivered, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_T_DT_D_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, String type, int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_T_DT_D_USERID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_U_T_DT_D_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_U_T_DT_D_TYPE_2);
		}

		query.append(_FINDER_COLUMN_U_T_DT_D_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_T_DT_D_DELIVERED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (bindType) {
			qPos.add(type);
		}

		qPos.add(deliveryType);

		qPos.add(delivered);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 */
	@Override
	public void removeByU_T_DT_D(
		long userId, String type, int deliveryType, boolean delivered) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_T_DT_D(
					userId, type, deliveryType, delivered, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_T_DT_D(
		long userId, String type, int deliveryType, boolean delivered) {

		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByU_T_DT_D;

		Object[] finderArgs = new Object[] {
			userId, type, deliveryType, delivered
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_T_DT_D_USERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_U_T_DT_D_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_DT_D_TYPE_2);
			}

			query.append(_FINDER_COLUMN_U_T_DT_D_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_T_DT_D_DELIVERED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(deliveryType);

				qPos.add(delivered);

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

	private static final String _FINDER_COLUMN_U_T_DT_D_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_TYPE_2 =
		"userNotificationEvent.type = ? AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_TYPE_3 =
		"(userNotificationEvent.type IS NULL OR userNotificationEvent.type = '') AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_DELIVERED_2 =
		"userNotificationEvent.delivered = ?";

	private FinderPath _finderPathWithPaginationFindByU_DT_D_AR;
	private FinderPath _finderPathWithoutPaginationFindByU_DT_D_AR;
	private FinderPath _finderPathCountByU_DT_D_AR;

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired) {

		return findByU_DT_D_AR(
			userId, deliveryType, delivered, actionRequired, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, int start, int end) {

		return findByU_DT_D_AR(
			userId, deliveryType, delivered, actionRequired, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_DT_D_AR(
			userId, deliveryType, delivered, actionRequired, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_DT_D_AR;
				finderArgs = new Object[] {
					userId, deliveryType, delivered, actionRequired
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_DT_D_AR;
			finderArgs = new Object[] {
				userId, deliveryType, delivered, actionRequired, start, end,
				orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.isDelivered()) ||
						(actionRequired !=
							userNotificationEvent.isActionRequired())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_AR_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_ACTIONREQUIRED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(actionRequired);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_AR_First(
			long userId, int deliveryType, boolean delivered,
			boolean actionRequired,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_AR_First(
			userId, deliveryType, delivered, actionRequired, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_AR_First(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_DT_D_AR(
			userId, deliveryType, delivered, actionRequired, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_AR_Last(
			long userId, int deliveryType, boolean delivered,
			boolean actionRequired,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_AR_Last(
			userId, deliveryType, delivered, actionRequired, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_AR_Last(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_DT_D_AR(
			userId, deliveryType, delivered, actionRequired);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_D_AR(
			userId, deliveryType, delivered, actionRequired, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_D_AR_PrevAndNext(
			long userNotificationEventId, long userId, int deliveryType,
			boolean delivered, boolean actionRequired,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_D_AR_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
				actionRequired, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_D_AR_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
				actionRequired, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_DT_D_AR_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_D_AR_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_D_AR_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_D_AR_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_DT_D_AR_ACTIONREQUIRED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(deliveryType);

		qPos.add(delivered);

		qPos.add(actionRequired);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 */
	@Override
	public void removeByU_DT_D_AR(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_DT_D_AR(
					userId, deliveryType, delivered, actionRequired,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_DT_D_AR(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired) {

		FinderPath finderPath = _finderPathCountByU_DT_D_AR;

		Object[] finderArgs = new Object[] {
			userId, deliveryType, delivered, actionRequired
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_AR_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_ACTIONREQUIRED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(actionRequired);

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

	private static final String _FINDER_COLUMN_U_DT_D_AR_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_AR_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_AR_DELIVERED_2 =
		"userNotificationEvent.delivered = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_AR_ACTIONREQUIRED_2 =
		"userNotificationEvent.actionRequired = ?";

	private FinderPath _finderPathWithPaginationFindByU_DT_D_A;
	private FinderPath _finderPathWithoutPaginationFindByU_DT_D_A;
	private FinderPath _finderPathCountByU_DT_D_A;

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_A(
		long userId, int deliveryType, boolean delivered, boolean archived) {

		return findByU_DT_D_A(
			userId, deliveryType, delivered, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_A(
		long userId, int deliveryType, boolean delivered, boolean archived,
		int start, int end) {

		return findByU_DT_D_A(
			userId, deliveryType, delivered, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_A(
		long userId, int deliveryType, boolean delivered, boolean archived,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_DT_D_A(
			userId, deliveryType, delivered, archived, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_A(
		long userId, int deliveryType, boolean delivered, boolean archived,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_DT_D_A;
				finderArgs = new Object[] {
					userId, deliveryType, delivered, archived
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_DT_D_A;
			finderArgs = new Object[] {
				userId, deliveryType, delivered, archived, start, end,
				orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.isDelivered()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_A_First(
			long userId, int deliveryType, boolean delivered, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_A_First(
			userId, deliveryType, delivered, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_A_First(
		long userId, int deliveryType, boolean delivered, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_DT_D_A(
			userId, deliveryType, delivered, archived, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_A_Last(
			long userId, int deliveryType, boolean delivered, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_A_Last(
			userId, deliveryType, delivered, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_A_Last(
		long userId, int deliveryType, boolean delivered, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_DT_D_A(userId, deliveryType, delivered, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_D_A(
			userId, deliveryType, delivered, archived, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_D_A_PrevAndNext(
			long userNotificationEventId, long userId, int deliveryType,
			boolean delivered, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_D_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
				archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_D_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
				archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_DT_D_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, int deliveryType, boolean delivered, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_D_A_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_DT_D_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(deliveryType);

		qPos.add(delivered);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 */
	@Override
	public void removeByU_DT_D_A(
		long userId, int deliveryType, boolean delivered, boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_DT_D_A(
					userId, deliveryType, delivered, archived,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_DT_D_A(
		long userId, int deliveryType, boolean delivered, boolean archived) {

		FinderPath finderPath = _finderPathCountByU_DT_D_A;

		Object[] finderArgs = new Object[] {
			userId, deliveryType, delivered, archived
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_DT_D_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_A_DELIVERED_2 =
		"userNotificationEvent.delivered = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_DT_AR_A;
	private FinderPath _finderPathWithoutPaginationFindByU_DT_AR_A;
	private FinderPath _finderPathCountByU_DT_AR_A;

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_AR_A(
		long userId, int deliveryType, boolean actionRequired,
		boolean archived) {

		return findByU_DT_AR_A(
			userId, deliveryType, actionRequired, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_AR_A(
		long userId, int deliveryType, boolean actionRequired, boolean archived,
		int start, int end) {

		return findByU_DT_AR_A(
			userId, deliveryType, actionRequired, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_AR_A(
		long userId, int deliveryType, boolean actionRequired, boolean archived,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_DT_AR_A(
			userId, deliveryType, actionRequired, archived, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_AR_A(
		long userId, int deliveryType, boolean actionRequired, boolean archived,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_DT_AR_A;
				finderArgs = new Object[] {
					userId, deliveryType, actionRequired, archived
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_DT_AR_A;
			finderArgs = new Object[] {
				userId, deliveryType, actionRequired, archived, start, end,
				orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(actionRequired !=
							userNotificationEvent.isActionRequired()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_AR_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_DT_AR_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(actionRequired);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_AR_A_First(
			long userId, int deliveryType, boolean actionRequired,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_AR_A_First(
			userId, deliveryType, actionRequired, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_AR_A_First(
		long userId, int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_DT_AR_A(
			userId, deliveryType, actionRequired, archived, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_AR_A_Last(
			long userId, int deliveryType, boolean actionRequired,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_AR_A_Last(
			userId, deliveryType, actionRequired, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_AR_A_Last(
		long userId, int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_DT_AR_A(
			userId, deliveryType, actionRequired, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_AR_A(
			userId, deliveryType, actionRequired, archived, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_AR_A_PrevAndNext(
			long userNotificationEventId, long userId, int deliveryType,
			boolean actionRequired, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType,
				actionRequired, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType,
				actionRequired, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_DT_AR_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_AR_A_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_AR_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_AR_A_ACTIONREQUIRED_2);

		query.append(_FINDER_COLUMN_U_DT_AR_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(deliveryType);

		qPos.add(actionRequired);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 */
	@Override
	public void removeByU_DT_AR_A(
		long userId, int deliveryType, boolean actionRequired,
		boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_DT_AR_A(
					userId, deliveryType, actionRequired, archived,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_DT_AR_A(
		long userId, int deliveryType, boolean actionRequired,
		boolean archived) {

		FinderPath finderPath = _finderPathCountByU_DT_AR_A;

		Object[] finderArgs = new Object[] {
			userId, deliveryType, actionRequired, archived
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_AR_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_DT_AR_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(actionRequired);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_DT_AR_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_AR_A_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_AR_A_ACTIONREQUIRED_2 =
		"userNotificationEvent.actionRequired = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_AR_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_D_AR_A;
	private FinderPath _finderPathWithoutPaginationFindByU_D_AR_A;
	private FinderPath _finderPathCountByU_D_AR_A;

	/**
	 * Returns all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR_A(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived) {

		return findByU_D_AR_A(
			userId, delivered, actionRequired, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR_A(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived, int start, int end) {

		return findByU_D_AR_A(
			userId, delivered, actionRequired, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR_A(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_D_AR_A(
			userId, delivered, actionRequired, archived, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_AR_A(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_D_AR_A;
				finderArgs = new Object[] {
					userId, delivered, actionRequired, archived
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_D_AR_A;
			finderArgs = new Object[] {
				userId, delivered, actionRequired, archived, start, end,
				orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(delivered != userNotificationEvent.isDelivered()) ||
						(actionRequired !=
							userNotificationEvent.isActionRequired()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_D_AR_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_D_AR_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

				qPos.add(actionRequired);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_AR_A_First(
			long userId, boolean delivered, boolean actionRequired,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_AR_A_First(
			userId, delivered, actionRequired, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_AR_A_First(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_D_AR_A(
			userId, delivered, actionRequired, archived, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_AR_A_Last(
			long userId, boolean delivered, boolean actionRequired,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_D_AR_A_Last(
			userId, delivered, actionRequired, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_D_AR_A_Last(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_D_AR_A(
			userId, delivered, actionRequired, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_D_AR_A(
			userId, delivered, actionRequired, archived, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_D_AR_A_PrevAndNext(
			long userNotificationEventId, long userId, boolean delivered,
			boolean actionRequired, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_D_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, delivered,
				actionRequired, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_D_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, delivered,
				actionRequired, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_D_AR_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, boolean delivered, boolean actionRequired,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_D_AR_A_USERID_2);

		query.append(_FINDER_COLUMN_U_D_AR_A_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_D_AR_A_ACTIONREQUIRED_2);

		query.append(_FINDER_COLUMN_U_D_AR_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(delivered);

		qPos.add(actionRequired);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 */
	@Override
	public void removeByU_D_AR_A(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_D_AR_A(
					userId, delivered, actionRequired, archived,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_D_AR_A(
		long userId, boolean delivered, boolean actionRequired,
		boolean archived) {

		FinderPath finderPath = _finderPathCountByU_D_AR_A;

		Object[] finderArgs = new Object[] {
			userId, delivered, actionRequired, archived
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_D_AR_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_D_AR_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(delivered);

				qPos.add(actionRequired);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_D_AR_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_AR_A_DELIVERED_2 =
		"userNotificationEvent.delivered = ? AND ";

	private static final String _FINDER_COLUMN_U_D_AR_A_ACTIONREQUIRED_2 =
		"userNotificationEvent.actionRequired = ? AND ";

	private static final String _FINDER_COLUMN_U_D_AR_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_T_DT_D_A;
	private FinderPath _finderPathWithoutPaginationFindByU_T_DT_D_A;
	private FinderPath _finderPathCountByU_T_DT_D_A;

	/**
	 * Returns all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D_A(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived) {

		return findByU_T_DT_D_A(
			userId, type, deliveryType, delivered, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D_A(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived, int start, int end) {

		return findByU_T_DT_D_A(
			userId, type, deliveryType, delivered, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D_A(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_T_DT_D_A(
			userId, type, deliveryType, delivered, archived, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_T_DT_D_A(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_T_DT_D_A;
				finderArgs = new Object[] {
					userId, type, deliveryType, delivered, archived
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_T_DT_D_A;
			finderArgs = new Object[] {
				userId, type, deliveryType, delivered, archived, start, end,
				orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						!type.equals(userNotificationEvent.getType()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.isDelivered()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_T_DT_D_A_USERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_U_T_DT_D_A_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_DT_D_A_TYPE_2);
			}

			query.append(_FINDER_COLUMN_U_T_DT_D_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_T_DT_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_T_DT_D_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_T_DT_D_A_First(
			long userId, String type, int deliveryType, boolean delivered,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_T_DT_D_A_First(
			userId, type, deliveryType, delivered, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_T_DT_D_A_First(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_T_DT_D_A(
			userId, type, deliveryType, delivered, archived, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_T_DT_D_A_Last(
			long userId, String type, int deliveryType, boolean delivered,
			boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_T_DT_D_A_Last(
			userId, type, deliveryType, delivered, archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_T_DT_D_A_Last(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_T_DT_D_A(
			userId, type, deliveryType, delivered, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_T_DT_D_A(
			userId, type, deliveryType, delivered, archived, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_T_DT_D_A_PrevAndNext(
			long userNotificationEventId, long userId, String type,
			int deliveryType, boolean delivered, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		type = Objects.toString(type, "");

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_T_DT_D_A_PrevAndNext(
				session, userNotificationEvent, userId, type, deliveryType,
				delivered, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_T_DT_D_A_PrevAndNext(
				session, userNotificationEvent, userId, type, deliveryType,
				delivered, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_T_DT_D_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_T_DT_D_A_USERID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_U_T_DT_D_A_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_U_T_DT_D_A_TYPE_2);
		}

		query.append(_FINDER_COLUMN_U_T_DT_D_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_T_DT_D_A_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_T_DT_D_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (bindType) {
			qPos.add(type);
		}

		qPos.add(deliveryType);

		qPos.add(delivered);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 */
	@Override
	public void removeByU_T_DT_D_A(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_T_DT_D_A(
					userId, type, deliveryType, delivered, archived,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and type = &#63; and deliveryType = &#63; and delivered = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_T_DT_D_A(
		long userId, String type, int deliveryType, boolean delivered,
		boolean archived) {

		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByU_T_DT_D_A;

		Object[] finderArgs = new Object[] {
			userId, type, deliveryType, delivered, archived
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_T_DT_D_A_USERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_U_T_DT_D_A_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_U_T_DT_D_A_TYPE_2);
			}

			query.append(_FINDER_COLUMN_U_T_DT_D_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_T_DT_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_T_DT_D_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_T_DT_D_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_A_TYPE_2 =
		"userNotificationEvent.type = ? AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_A_TYPE_3 =
		"(userNotificationEvent.type IS NULL OR userNotificationEvent.type = '') AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_A_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_A_DELIVERED_2 =
		"userNotificationEvent.delivered = ? AND ";

	private static final String _FINDER_COLUMN_U_T_DT_D_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	private FinderPath _finderPathWithPaginationFindByU_DT_D_AR_A;
	private FinderPath _finderPathWithoutPaginationFindByU_DT_D_AR_A;
	private FinderPath _finderPathCountByU_DT_D_AR_A;

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR_A(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived) {

		return findByU_DT_D_AR_A(
			userId, deliveryType, delivered, actionRequired, archived,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR_A(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived, int start, int end) {

		return findByU_DT_D_AR_A(
			userId, deliveryType, delivered, actionRequired, archived, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR_A(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findByU_DT_D_AR_A(
			userId, deliveryType, delivered, actionRequired, archived, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D_AR_A(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_DT_D_AR_A;
				finderArgs = new Object[] {
					userId, deliveryType, delivered, actionRequired, archived
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_DT_D_AR_A;
			finderArgs = new Object[] {
				userId, deliveryType, delivered, actionRequired, archived,
				start, end, orderByComparator
			};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UserNotificationEvent userNotificationEvent : list) {
					if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType !=
							userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.isDelivered()) ||
						(actionRequired !=
							userNotificationEvent.isActionRequired()) ||
						(archived != userNotificationEvent.isArchived())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(actionRequired);

				qPos.add(archived);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_AR_A_First(
			long userId, int deliveryType, boolean delivered,
			boolean actionRequired, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_AR_A_First(
			userId, deliveryType, delivered, actionRequired, archived,
			orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_AR_A_First(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		List<UserNotificationEvent> list = findByU_DT_D_AR_A(
			userId, deliveryType, delivered, actionRequired, archived, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event
	 * @throws NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_AR_A_Last(
			long userId, int deliveryType, boolean delivered,
			boolean actionRequired, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_AR_A_Last(
			userId, deliveryType, delivered, actionRequired, archived,
			orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(", actionRequired=");
		msg.append(actionRequired);

		msg.append(", archived=");
		msg.append(archived);

		msg.append("}");

		throw new NoSuchUserNotificationEventException(msg.toString());
	}

	/**
	 * Returns the last user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user notification event, or <code>null</code> if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent fetchByU_DT_D_AR_A_Last(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		int count = countByU_DT_D_AR_A(
			userId, deliveryType, delivered, actionRequired, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_D_AR_A(
			userId, deliveryType, delivered, actionRequired, archived,
			count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the user notification events before and after the current user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userNotificationEventId the primary key of the current user notification event
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_D_AR_A_PrevAndNext(
			long userNotificationEventId, long userId, int deliveryType,
			boolean delivered, boolean actionRequired, boolean archived,
			OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = findByPrimaryKey(
			userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_D_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
				actionRequired, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_D_AR_A_PrevAndNext(
				session, userNotificationEvent, userId, deliveryType, delivered,
				actionRequired, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_DT_D_AR_A_PrevAndNext(
		Session session, UserNotificationEvent userNotificationEvent,
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_D_AR_A_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_D_AR_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_D_AR_A_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_DT_D_AR_A_ACTIONREQUIRED_2);

		query.append(_FINDER_COLUMN_U_DT_D_AR_A_ARCHIVED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(deliveryType);

		qPos.add(delivered);

		qPos.add(actionRequired);

		qPos.add(archived);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						userNotificationEvent)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<UserNotificationEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 */
	@Override
	public void removeByU_DT_D_AR_A(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived) {

		for (UserNotificationEvent userNotificationEvent :
				findByU_DT_D_AR_A(
					userId, deliveryType, delivered, actionRequired, archived,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the number of matching user notification events
	 */
	@Override
	public int countByU_DT_D_AR_A(
		long userId, int deliveryType, boolean delivered,
		boolean actionRequired, boolean archived) {

		FinderPath finderPath = _finderPathCountByU_DT_D_AR_A;

		Object[] finderArgs = new Object[] {
			userId, deliveryType, delivered, actionRequired, archived
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_DT_D_AR_A_ARCHIVED_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(deliveryType);

				qPos.add(delivered);

				qPos.add(actionRequired);

				qPos.add(archived);

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

	private static final String _FINDER_COLUMN_U_DT_D_AR_A_USERID_2 =
		"userNotificationEvent.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_AR_A_DELIVERYTYPE_2 =
		"userNotificationEvent.deliveryType = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_AR_A_DELIVERED_2 =
		"userNotificationEvent.delivered = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_AR_A_ACTIONREQUIRED_2 =
		"userNotificationEvent.actionRequired = ? AND ";

	private static final String _FINDER_COLUMN_U_DT_D_AR_A_ARCHIVED_2 =
		"userNotificationEvent.archived = ?";

	public UserNotificationEventPersistenceImpl() {
		setModelClass(UserNotificationEvent.class);

		setModelImplClass(UserNotificationEventImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the user notification event in the entity cache if it is enabled.
	 *
	 * @param userNotificationEvent the user notification event
	 */
	@Override
	public void cacheResult(UserNotificationEvent userNotificationEvent) {
		EntityCacheUtil.putResult(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey(), userNotificationEvent);

		userNotificationEvent.resetOriginalValues();
	}

	/**
	 * Caches the user notification events in the entity cache if it is enabled.
	 *
	 * @param userNotificationEvents the user notification events
	 */
	@Override
	public void cacheResult(
		List<UserNotificationEvent> userNotificationEvents) {

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			if (EntityCacheUtil.getResult(
					UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
					UserNotificationEventImpl.class,
					userNotificationEvent.getPrimaryKey()) == null) {

				cacheResult(userNotificationEvent);
			}
			else {
				userNotificationEvent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all user notification events.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserNotificationEventImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user notification event.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserNotificationEvent userNotificationEvent) {
		EntityCacheUtil.removeResult(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<UserNotificationEvent> userNotificationEvents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			EntityCacheUtil.removeResult(
				UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
				UserNotificationEventImpl.class,
				userNotificationEvent.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
				UserNotificationEventImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new user notification event with the primary key. Does not add the user notification event to the database.
	 *
	 * @param userNotificationEventId the primary key for the new user notification event
	 * @return the new user notification event
	 */
	@Override
	public UserNotificationEvent create(long userNotificationEventId) {
		UserNotificationEvent userNotificationEvent =
			new UserNotificationEventImpl();

		userNotificationEvent.setNew(true);
		userNotificationEvent.setPrimaryKey(userNotificationEventId);

		String uuid = PortalUUIDUtil.generate();

		userNotificationEvent.setUuid(uuid);

		userNotificationEvent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return userNotificationEvent;
	}

	/**
	 * Removes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event that was removed
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent remove(long userNotificationEventId)
		throws NoSuchUserNotificationEventException {

		return remove((Serializable)userNotificationEventId);
	}

	/**
	 * Removes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user notification event
	 * @return the user notification event that was removed
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent remove(Serializable primaryKey)
		throws NoSuchUserNotificationEventException {

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent userNotificationEvent =
				(UserNotificationEvent)session.get(
					UserNotificationEventImpl.class, primaryKey);

			if (userNotificationEvent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserNotificationEventException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(userNotificationEvent);
		}
		catch (NoSuchUserNotificationEventException nsee) {
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
	protected UserNotificationEvent removeImpl(
		UserNotificationEvent userNotificationEvent) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userNotificationEvent)) {
				userNotificationEvent = (UserNotificationEvent)session.get(
					UserNotificationEventImpl.class,
					userNotificationEvent.getPrimaryKeyObj());
			}

			if (userNotificationEvent != null) {
				session.delete(userNotificationEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (userNotificationEvent != null) {
			clearCache(userNotificationEvent);
		}

		return userNotificationEvent;
	}

	@Override
	public UserNotificationEvent updateImpl(
		UserNotificationEvent userNotificationEvent) {

		boolean isNew = userNotificationEvent.isNew();

		if (!(userNotificationEvent instanceof
				UserNotificationEventModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(userNotificationEvent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					userNotificationEvent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in userNotificationEvent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UserNotificationEvent implementation " +
					userNotificationEvent.getClass());
		}

		UserNotificationEventModelImpl userNotificationEventModelImpl =
			(UserNotificationEventModelImpl)userNotificationEvent;

		if (Validator.isNull(userNotificationEvent.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			userNotificationEvent.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (userNotificationEvent.isNew()) {
				session.save(userNotificationEvent);

				userNotificationEvent.setNew(false);
			}
			else {
				userNotificationEvent = (UserNotificationEvent)session.merge(
					userNotificationEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!UserNotificationEventModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				userNotificationEventModelImpl.getUuid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUuid(),
				userNotificationEventModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {userNotificationEventModelImpl.getUserId()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {userNotificationEventModelImpl.getType()};

			FinderCacheUtil.removeResult(_finderPathCountByType, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByType, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getDeliveryType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_DT, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_DT, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.isDelivered()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_D, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_D, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isDelivered()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_DT_D, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_DT_D, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_DT_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_DT_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.isDelivered(),
				userNotificationEventModelImpl.isActionRequired()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_D_AR, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_D_AR, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.isDelivered(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_D_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_D_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.isActionRequired(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_AR_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_AR_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getType(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isDelivered()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_T_DT_D, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_T_DT_D, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isDelivered(),
				userNotificationEventModelImpl.isActionRequired()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_DT_D_AR, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_DT_D_AR, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isDelivered(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_DT_D_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_DT_D_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isActionRequired(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_DT_AR_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_DT_AR_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.isDelivered(),
				userNotificationEventModelImpl.isActionRequired(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_D_AR_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_D_AR_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getType(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isDelivered(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_T_DT_D_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_T_DT_D_A, args);

			args = new Object[] {
				userNotificationEventModelImpl.getUserId(),
				userNotificationEventModelImpl.getDeliveryType(),
				userNotificationEventModelImpl.isDelivered(),
				userNotificationEventModelImpl.isActionRequired(),
				userNotificationEventModelImpl.isArchived()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_DT_D_AR_A, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_DT_D_AR_A, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {userNotificationEventModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUuid(),
					userNotificationEventModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUuid(),
					userNotificationEventModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByType.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByType, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByType, args);

				args = new Object[] {userNotificationEventModelImpl.getType()};

				FinderCacheUtil.removeResult(_finderPathCountByType, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByType, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_DT.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDeliveryType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getDeliveryType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDelivered()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.isDelivered()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_DT_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalDelivered()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isDelivered()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_DT_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_D_AR.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDelivered(),
					userNotificationEventModelImpl.getOriginalActionRequired()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D_AR, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D_AR, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.isDelivered(),
					userNotificationEventModelImpl.isActionRequired()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D_AR, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D_AR, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_D_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDelivered(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.isDelivered(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_AR_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalActionRequired(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_AR_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.isActionRequired(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_AR_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_T_DT_D.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalType(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalDelivered()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_T_DT_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_T_DT_D, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getType(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isDelivered()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_T_DT_D, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_T_DT_D, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_DT_D_AR.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalDelivered(),
					userNotificationEventModelImpl.getOriginalActionRequired()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_D_AR, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D_AR, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isDelivered(),
					userNotificationEventModelImpl.isActionRequired()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_D_AR, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D_AR, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_DT_D_A.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalDelivered(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_D_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isDelivered(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_D_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_DT_AR_A.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalActionRequired(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_AR_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isActionRequired(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_DT_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_AR_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_D_AR_A.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDelivered(),
					userNotificationEventModelImpl.getOriginalActionRequired(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D_AR_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.isDelivered(),
					userNotificationEventModelImpl.isActionRequired(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_D_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_D_AR_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_T_DT_D_A.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalType(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalDelivered(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByU_T_DT_D_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_T_DT_D_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getType(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isDelivered(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByU_T_DT_D_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_T_DT_D_A, args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_DT_D_AR_A.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					userNotificationEventModelImpl.getOriginalUserId(),
					userNotificationEventModelImpl.getOriginalDeliveryType(),
					userNotificationEventModelImpl.getOriginalDelivered(),
					userNotificationEventModelImpl.getOriginalActionRequired(),
					userNotificationEventModelImpl.getOriginalArchived()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByU_DT_D_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D_AR_A, args);

				args = new Object[] {
					userNotificationEventModelImpl.getUserId(),
					userNotificationEventModelImpl.getDeliveryType(),
					userNotificationEventModelImpl.isDelivered(),
					userNotificationEventModelImpl.isActionRequired(),
					userNotificationEventModelImpl.isArchived()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByU_DT_D_AR_A, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_DT_D_AR_A, args);
			}
		}

		EntityCacheUtil.putResult(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey(), userNotificationEvent,
			false);

		userNotificationEvent.resetOriginalValues();

		return userNotificationEvent;
	}

	/**
	 * Returns the user notification event with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user notification event
	 * @return the user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserNotificationEventException {

		UserNotificationEvent userNotificationEvent = fetchByPrimaryKey(
			primaryKey);

		if (userNotificationEvent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserNotificationEventException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return userNotificationEvent;
	}

	/**
	 * Returns the user notification event with the primary key or throws a <code>NoSuchUserNotificationEventException</code> if it could not be found.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event
	 * @throws NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent findByPrimaryKey(long userNotificationEventId)
		throws NoSuchUserNotificationEventException {

		return findByPrimaryKey((Serializable)userNotificationEventId);
	}

	/**
	 * Returns the user notification event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event, or <code>null</code> if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent fetchByPrimaryKey(
		long userNotificationEventId) {

		return fetchByPrimaryKey((Serializable)userNotificationEventId);
	}

	/**
	 * Returns all the user notification events.
	 *
	 * @return the user notification events
	 */
	@Override
	public List<UserNotificationEvent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of user notification events
	 */
	@Override
	public List<UserNotificationEvent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user notification events
	 */
	@Override
	public List<UserNotificationEvent> findAll(
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the user notification events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserNotificationEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of user notification events
	 */
	@Override
	public List<UserNotificationEvent> findAll(
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<UserNotificationEvent> list = null;

		if (useFinderCache) {
			list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_USERNOTIFICATIONEVENT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERNOTIFICATIONEVENT;

				sql = sql.concat(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<UserNotificationEvent>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the user notification events from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UserNotificationEvent userNotificationEvent : findAll()) {
			remove(userNotificationEvent);
		}
	}

	/**
	 * Returns the number of user notification events.
	 *
	 * @return the number of user notification events
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERNOTIFICATIONEVENT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "userNotificationEventId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USERNOTIFICATIONEVENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserNotificationEventModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user notification event persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			UserNotificationEventModelImpl.UUID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			UserNotificationEventModelImpl.UUID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.COMPANYID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByType = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByType = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] {String.class.getName()},
			UserNotificationEventModelImpl.TYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByType = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByU_DT = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_DT = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT",
			new String[] {Long.class.getName(), Integer.class.getName()},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_DT = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByU_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByU_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_A",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByU_DT_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_D",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_DT_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT_D",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_DT_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_D",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_DT_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_DT_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_DT_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_D_AR = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D_AR",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_D_AR = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D_AR",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_D_AR = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D_AR",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_AR_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_AR_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_AR_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_T_DT_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_T_DT_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_T_DT_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_T_DT_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_T_DT_D = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T_DT_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_DT_D_AR = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_D_AR",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_DT_D_AR = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT_D_AR",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_DT_D_AR = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_D_AR",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_DT_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_D_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_DT_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT_D_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_DT_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_D_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_DT_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_AR_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_DT_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT_AR_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_DT_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_AR_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_D_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D_AR_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_D_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D_AR_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_D_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D_AR_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_T_DT_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_T_DT_D_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_T_DT_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_T_DT_D_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_T_DT_D_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T_DT_D_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByU_DT_D_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_D_AR_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_DT_D_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT_D_AR_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);

		_finderPathCountByU_DT_D_AR_A = new FinderPath(
			UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_D_AR_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserNotificationEventImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_USERNOTIFICATIONEVENT =
		"SELECT userNotificationEvent FROM UserNotificationEvent userNotificationEvent";

	private static final String _SQL_SELECT_USERNOTIFICATIONEVENT_WHERE =
		"SELECT userNotificationEvent FROM UserNotificationEvent userNotificationEvent WHERE ";

	private static final String _SQL_COUNT_USERNOTIFICATIONEVENT =
		"SELECT COUNT(userNotificationEvent) FROM UserNotificationEvent userNotificationEvent";

	private static final String _SQL_COUNT_USERNOTIFICATIONEVENT_WHERE =
		"SELECT COUNT(userNotificationEvent) FROM UserNotificationEvent userNotificationEvent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"userNotificationEvent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UserNotificationEvent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UserNotificationEvent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserNotificationEventPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

}