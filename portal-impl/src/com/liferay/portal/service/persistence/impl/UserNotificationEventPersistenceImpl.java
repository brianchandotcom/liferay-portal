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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchUserNotificationEventException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.model.impl.UserNotificationEventModelImpl;
import com.liferay.portal.service.persistence.UserNotificationEventPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the user notification event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEventPersistence
 * @see UserNotificationEventUtil
 * @generated
 */
@ProviderType
public class UserNotificationEventPersistenceImpl extends BasePersistenceImpl<UserNotificationEvent>
	implements UserNotificationEventPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserNotificationEventUtil} to access the user notification event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserNotificationEventImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			UserNotificationEventModelImpl.UUID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid(String uuid, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {
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

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if (!Validator.equals(uuid, userNotificationEvent.getUuid())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_First(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_First(uuid,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByUuid_First(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByUuid(uuid, 0, 1,
				orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_Last(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_Last(uuid,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByUuid_Last(String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByUuid_PrevAndNext(
		long userNotificationEventId, String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUuid_PrevAndNext(session, userNotificationEvent,
					uuid, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUuid_PrevAndNext(session, userNotificationEvent,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByUuid_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, String uuid,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
		for (UserNotificationEvent userNotificationEvent : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "userNotificationEvent.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "userNotificationEvent.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(userNotificationEvent.uuid IS NULL OR userNotificationEvent.uuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			UserNotificationEventModelImpl.UUID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.COMPANYID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
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

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if (!Validator.equals(uuid, userNotificationEvent.getUuid()) ||
						(companyId != userNotificationEvent.getCompanyId())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByUuid_C_PrevAndNext(
		long userNotificationEventId, String uuid, long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, userNotificationEvent,
					uuid, companyId, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUuid_C_PrevAndNext(session, userNotificationEvent,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByUuid_C_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, String uuid,
		long companyId,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
		for (UserNotificationEvent userNotificationEvent : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "userNotificationEvent.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "userNotificationEvent.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(userNotificationEvent.uuid IS NULL OR userNotificationEvent.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "userNotificationEvent.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUserId(long userId, int start,
		int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByUserId(long userId, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUserId_First(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByUserId_First(userId,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByUserId_First(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByUserId(userId, 0, 1,
				orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByUserId_Last(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByUserId_Last(userId,
				orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByUserId_Last(long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByUserId(userId, count - 1,
				count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByUserId_PrevAndNext(
		long userNotificationEventId, long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByUserId_PrevAndNext(session, userNotificationEvent,
					userId, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByUserId_PrevAndNext(session, userNotificationEvent,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByUserId_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(UserNotificationEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
		for (UserNotificationEvent userNotificationEvent : findByUserId(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "userNotificationEvent.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT",
			new String[] { Long.class.getName(), Integer.class.getName() },
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_DT = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT(long userId, int deliveryType) {
		return findByU_DT(userId, deliveryType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT(long userId,
		int deliveryType, int start, int end) {
		return findByU_DT(userId, deliveryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT(long userId,
		int deliveryType, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT;
			finderArgs = new Object[] { userId, deliveryType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT;
			finderArgs = new Object[] {
					userId, deliveryType,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType != userNotificationEvent.getDeliveryType())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_DELIVERYTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_First(long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_First(userId,
				deliveryType, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_First(long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_DT(userId, deliveryType, 0,
				1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_Last(long userId, int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_Last(userId,
				deliveryType, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", deliveryType=");
		msg.append(deliveryType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_Last(long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_DT(userId, deliveryType);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT(userId, deliveryType,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_PrevAndNext(session, userNotificationEvent,
					userId, deliveryType, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_PrevAndNext(session, userNotificationEvent,
					userId, deliveryType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_DT_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		int deliveryType,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_DELIVERYTYPE_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
		for (UserNotificationEvent userNotificationEvent : findByU_DT(userId,
				deliveryType, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_DT;

		Object[] finderArgs = new Object[] { userId, deliveryType };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_U_DT_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_DELIVERYTYPE_2 = "userNotificationEvent.deliveryType = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_D = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_D = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D(long userId, boolean delivered) {
		return findByU_D(userId, delivered, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D(long userId,
		boolean delivered, int start, int end) {
		return findByU_D(userId, delivered, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_D(long userId,
		boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D;
			finderArgs = new Object[] { userId, delivered };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_D;
			finderArgs = new Object[] {
					userId, delivered,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(delivered != userNotificationEvent.getDelivered())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_USERID_2);

			query.append(_FINDER_COLUMN_U_D_DELIVERED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_First(long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_D_First(userId,
				delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_D_First(long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_D(userId, delivered, 0, 1,
				orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_Last(long userId, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_D_Last(userId,
				delivered, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", delivered=");
		msg.append(delivered);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_D_Last(long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_D(userId, delivered);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_D(userId, delivered,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_D_PrevAndNext(
		long userNotificationEventId, long userId, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_D_PrevAndNext(session, userNotificationEvent,
					userId, delivered, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_D_PrevAndNext(session, userNotificationEvent,
					userId, delivered, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_D_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_D_USERID_2);

		query.append(_FINDER_COLUMN_U_D_DELIVERED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
		for (UserNotificationEvent userNotificationEvent : findByU_D(userId,
				delivered, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_D;

		Object[] finderArgs = new Object[] { userId, delivered };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_U_D_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_D_DELIVERED_2 = "userNotificationEvent.delivered = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_A(long userId, boolean archived) {
		return findByU_A(userId, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @return the range of matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_A(long userId, boolean archived,
		int start, int end) {
		return findByU_A(userId, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_A(long userId, boolean archived,
		int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A;
			finderArgs = new Object[] { userId, archived };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_A;
			finderArgs = new Object[] {
					userId, archived,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(archived != userNotificationEvent.getArchived())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_A_USERID_2);

			query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_A_First(long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_A_First(userId,
				archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", archived=");
		msg.append(archived);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_A_First(long userId,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_A(userId, archived, 0, 1,
				orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_A_Last(long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_A_Last(userId,
				archived, orderByComparator);

		if (userNotificationEvent != null) {
			return userNotificationEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", archived=");
		msg.append(archived);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_A_Last(long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_A(userId, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_A(userId, archived,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_A_PrevAndNext(
		long userNotificationEventId, long userId, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_A_PrevAndNext(session, userNotificationEvent,
					userId, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_A_PrevAndNext(session, userNotificationEvent,
					userId, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_A_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_A_USERID_2);

		query.append(_FINDER_COLUMN_U_A_ARCHIVED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
		for (UserNotificationEvent userNotificationEvent : findByU_A(userId,
				archived, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_A;

		Object[] finderArgs = new Object[] { userId, archived };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_U_A_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_A_ARCHIVED_2 = "userNotificationEvent.archived = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_D = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_D",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_COUNT_BY_U_DT_D = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_D",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_D(long userId,
		int deliveryType, boolean delivered) {
		return findByU_DT_D(userId, deliveryType, delivered, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_D(long userId,
		int deliveryType, boolean delivered, int start, int end) {
		return findByU_DT_D(userId, deliveryType, delivered, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_D(long userId,
		int deliveryType, boolean delivered, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D;
			finderArgs = new Object[] { userId, deliveryType, delivered };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_D;
			finderArgs = new Object[] {
					userId, deliveryType, delivered,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType != userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.getDelivered())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_DELIVERED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_First(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_First(userId,
				deliveryType, delivered, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_D_First(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_DT_D(userId, deliveryType,
				delivered, 0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_Last(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_Last(userId,
				deliveryType, delivered, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_D_Last(long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_DT_D(userId, deliveryType, delivered);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_D(userId, deliveryType,
				delivered, count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_D_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_D_PrevAndNext(session, userNotificationEvent,
					userId, deliveryType, delivered, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_D_PrevAndNext(session, userNotificationEvent,
					userId, deliveryType, delivered, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_DT_D_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		int deliveryType, boolean delivered,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_D_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_D_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_D_DELIVERED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
	public void removeByU_DT_D(long userId, int deliveryType, boolean delivered) {
		for (UserNotificationEvent userNotificationEvent : findByU_DT_D(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_DT_D;

		Object[] finderArgs = new Object[] { userId, deliveryType, delivered };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_U_DT_D_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_D_DELIVERYTYPE_2 = "userNotificationEvent.deliveryType = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_D_DELIVERED_2 = "userNotificationEvent.delivered = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_COUNT_BY_U_DT_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_DT_A(long userId,
		int deliveryType, boolean archived) {
		return findByU_DT_A(userId, deliveryType, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_A(long userId,
		int deliveryType, boolean archived, int start, int end) {
		return findByU_DT_A(userId, deliveryType, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_A(long userId,
		int deliveryType, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A;
			finderArgs = new Object[] { userId, deliveryType, archived };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_A;
			finderArgs = new Object[] {
					userId, deliveryType, archived,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType != userNotificationEvent.getDeliveryType()) ||
						(archived != userNotificationEvent.getArchived())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_A_First(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_A_First(userId,
				deliveryType, archived, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_A_First(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_DT_A(userId, deliveryType,
				archived, 0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_A_Last(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_A_Last(userId,
				deliveryType, archived, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_A_Last(long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_DT_A(userId, deliveryType, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_A(userId, deliveryType,
				archived, count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_A_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_A_PrevAndNext(session, userNotificationEvent,
					userId, deliveryType, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_A_PrevAndNext(session, userNotificationEvent,
					userId, deliveryType, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_DT_A_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		int deliveryType, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_A_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_A_ARCHIVED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
	public void removeByU_DT_A(long userId, int deliveryType, boolean archived) {
		for (UserNotificationEvent userNotificationEvent : findByU_DT_A(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_DT_A;

		Object[] finderArgs = new Object[] { userId, deliveryType, archived };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

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

	private static final String _FINDER_COLUMN_U_DT_A_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_A_DELIVERYTYPE_2 = "userNotificationEvent.deliveryType = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_A_ARCHIVED_2 = "userNotificationEvent.archived = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_D_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_D_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_D_A(long userId,
		boolean delivered, boolean actionRequired) {
		return findByU_D_A(userId, delivered, actionRequired,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_D_A(long userId,
		boolean delivered, boolean actionRequired, int start, int end) {
		return findByU_D_A(userId, delivered, actionRequired, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_D_A(long userId,
		boolean delivered, boolean actionRequired, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D_A;
			finderArgs = new Object[] { userId, delivered, actionRequired };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_D_A;
			finderArgs = new Object[] {
					userId, delivered, actionRequired,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(delivered != userNotificationEvent.getDelivered()) ||
						(actionRequired != userNotificationEvent.getActionRequired())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_A_ACTIONREQUIRED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_A_First(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_D_A_First(userId,
				delivered, actionRequired, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_D_A_First(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_D_A(userId, delivered,
				actionRequired, 0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_D_A_Last(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_D_A_Last(userId,
				delivered, actionRequired, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_D_A_Last(long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_D_A(userId, delivered, actionRequired);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_D_A(userId, delivered,
				actionRequired, count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_D_A_PrevAndNext(
		long userNotificationEventId, long userId, boolean delivered,
		boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_D_A_PrevAndNext(session, userNotificationEvent,
					userId, delivered, actionRequired, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_D_A_PrevAndNext(session, userNotificationEvent,
					userId, delivered, actionRequired, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_D_A_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_D_A_USERID_2);

		query.append(_FINDER_COLUMN_U_D_A_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_D_A_ACTIONREQUIRED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
	public void removeByU_D_A(long userId, boolean delivered,
		boolean actionRequired) {
		for (UserNotificationEvent userNotificationEvent : findByU_D_A(userId,
				delivered, actionRequired, QueryUtil.ALL_POS,
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
	public int countByU_D_A(long userId, boolean delivered,
		boolean actionRequired) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_D_A;

		Object[] finderArgs = new Object[] { userId, delivered, actionRequired };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_D_A_ACTIONREQUIRED_2);

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

	private static final String _FINDER_COLUMN_U_D_A_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_D_A_DELIVERED_2 = "userNotificationEvent.delivered = ? AND ";
	private static final String _FINDER_COLUMN_U_D_A_ACTIONREQUIRED_2 = "userNotificationEvent.actionRequired = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_A_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_A_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_A_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_A_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_A_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @return the matching user notification events
	 */
	@Override
	public List<UserNotificationEvent> findByU_A_A(long userId,
		boolean actionRequired, boolean archived) {
		return findByU_A_A(userId, actionRequired, archived, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_A_A(long userId,
		boolean actionRequired, boolean archived, int start, int end) {
		return findByU_A_A(userId, actionRequired, archived, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_A_A(long userId,
		boolean actionRequired, boolean archived, int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A_A;
			finderArgs = new Object[] { userId, actionRequired, archived };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_A_A;
			finderArgs = new Object[] {
					userId, actionRequired, archived,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(actionRequired != userNotificationEvent.getActionRequired()) ||
						(archived != userNotificationEvent.getArchived())) {
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

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_A_A_USERID_2);

			query.append(_FINDER_COLUMN_U_A_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_A_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_A_A_First(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_A_A_First(userId,
				actionRequired, archived, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_A_A_First(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_A_A(userId, actionRequired,
				archived, 0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_A_A_Last(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_A_A_Last(userId,
				actionRequired, archived, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_A_A_Last(long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_A_A(userId, actionRequired, archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_A_A(userId, actionRequired,
				archived, count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_A_A_PrevAndNext(
		long userNotificationEventId, long userId, boolean actionRequired,
		boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_A_A_PrevAndNext(session, userNotificationEvent,
					userId, actionRequired, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_A_A_PrevAndNext(session, userNotificationEvent,
					userId, actionRequired, archived, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserNotificationEvent getByU_A_A_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_A_A_USERID_2);

		query.append(_FINDER_COLUMN_U_A_A_ACTIONREQUIRED_2);

		query.append(_FINDER_COLUMN_U_A_A_ARCHIVED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
	public void removeByU_A_A(long userId, boolean actionRequired,
		boolean archived) {
		for (UserNotificationEvent userNotificationEvent : findByU_A_A(userId,
				actionRequired, archived, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
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
	public int countByU_A_A(long userId, boolean actionRequired,
		boolean archived) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_A_A;

		Object[] finderArgs = new Object[] { userId, actionRequired, archived };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_A_A_USERID_2);

			query.append(_FINDER_COLUMN_U_A_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_A_A_ARCHIVED_2);

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

	private static final String _FINDER_COLUMN_U_A_A_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_A_A_ACTIONREQUIRED_2 = "userNotificationEvent.actionRequired = ? AND ";
	private static final String _FINDER_COLUMN_U_A_A_ARCHIVED_2 = "userNotificationEvent.archived = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_D_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_D_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D_A =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
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
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_DT_D_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_D_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});

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
	public List<UserNotificationEvent> findByU_DT_D_A(long userId,
		int deliveryType, boolean delivered, boolean actionRequired) {
		return findByU_DT_D_A(userId, deliveryType, delivered, actionRequired,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_D_A(long userId,
		int deliveryType, boolean delivered, boolean actionRequired, int start,
		int end) {
		return findByU_DT_D_A(userId, deliveryType, delivered, actionRequired,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_D_A(long userId,
		int deliveryType, boolean delivered, boolean actionRequired, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D_A;
			finderArgs = new Object[] {
					userId, deliveryType, delivered, actionRequired
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_D_A;
			finderArgs = new Object[] {
					userId, deliveryType, delivered, actionRequired,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType != userNotificationEvent.getDeliveryType()) ||
						(delivered != userNotificationEvent.getDelivered()) ||
						(actionRequired != userNotificationEvent.getActionRequired())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_ACTIONREQUIRED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and delivered = &#63; and actionRequired = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param delivered the delivered
	 * @param actionRequired the action required
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_A_First(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_A_First(userId,
				deliveryType, delivered, actionRequired, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_D_A_First(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_DT_D_A(userId, deliveryType,
				delivered, actionRequired, 0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_D_A_Last(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_D_A_Last(userId,
				deliveryType, delivered, actionRequired, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_D_A_Last(long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_DT_D_A(userId, deliveryType, delivered,
				actionRequired);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_D_A(userId, deliveryType,
				delivered, actionRequired, count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_D_A_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_D_A_PrevAndNext(session,
					userNotificationEvent, userId, deliveryType, delivered,
					actionRequired, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_D_A_PrevAndNext(session,
					userNotificationEvent, userId, deliveryType, delivered,
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

	protected UserNotificationEvent getByU_DT_D_A_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		int deliveryType, boolean delivered, boolean actionRequired,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_D_A_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERED_2);

		query.append(_FINDER_COLUMN_U_DT_D_A_ACTIONREQUIRED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
	public void removeByU_DT_D_A(long userId, int deliveryType,
		boolean delivered, boolean actionRequired) {
		for (UserNotificationEvent userNotificationEvent : findByU_DT_D_A(
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
	public int countByU_DT_D_A(long userId, int deliveryType,
		boolean delivered, boolean actionRequired) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_DT_D_A;

		Object[] finderArgs = new Object[] {
				userId, deliveryType, delivered, actionRequired
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_D_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_DELIVERED_2);

			query.append(_FINDER_COLUMN_U_DT_D_A_ACTIONREQUIRED_2);

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

	private static final String _FINDER_COLUMN_U_DT_D_A_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_D_A_DELIVERYTYPE_2 = "userNotificationEvent.deliveryType = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_D_A_DELIVERED_2 = "userNotificationEvent.delivered = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_D_A_ACTIONREQUIRED_2 = "userNotificationEvent.actionRequired = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_A_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_DT_A_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A_A =
		new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_DT_A_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			},
			UserNotificationEventModelImpl.USERID_COLUMN_BITMASK |
			UserNotificationEventModelImpl.DELIVERYTYPE_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ACTIONREQUIRED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.ARCHIVED_COLUMN_BITMASK |
			UserNotificationEventModelImpl.TIMESTAMP_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_DT_A_A = new FinderPath(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_DT_A_A",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});

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
	public List<UserNotificationEvent> findByU_DT_A_A(long userId,
		int deliveryType, boolean actionRequired, boolean archived) {
		return findByU_DT_A_A(userId, deliveryType, actionRequired, archived,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_A_A(long userId,
		int deliveryType, boolean actionRequired, boolean archived, int start,
		int end) {
		return findByU_DT_A_A(userId, deliveryType, actionRequired, archived,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the user notification events where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<UserNotificationEvent> findByU_DT_A_A(long userId,
		int deliveryType, boolean actionRequired, boolean archived, int start,
		int end, OrderByComparator<UserNotificationEvent> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A_A;
			finderArgs = new Object[] {
					userId, deliveryType, actionRequired, archived
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_DT_A_A;
			finderArgs = new Object[] {
					userId, deliveryType, actionRequired, archived,
					
					start, end, orderByComparator
				};
		}

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (UserNotificationEvent userNotificationEvent : list) {
				if ((userId != userNotificationEvent.getUserId()) ||
						(deliveryType != userNotificationEvent.getDeliveryType()) ||
						(actionRequired != userNotificationEvent.getActionRequired()) ||
						(archived != userNotificationEvent.getArchived())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_A_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_A_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_A_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_DT_A_A_ARCHIVED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
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

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first user notification event in the ordered set where userId = &#63; and deliveryType = &#63; and actionRequired = &#63; and archived = &#63;.
	 *
	 * @param userId the user ID
	 * @param deliveryType the delivery type
	 * @param actionRequired the action required
	 * @param archived the archived
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_A_A_First(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_A_A_First(userId,
				deliveryType, actionRequired, archived, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_A_A_First(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		List<UserNotificationEvent> list = findByU_DT_A_A(userId, deliveryType,
				actionRequired, archived, 0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a matching user notification event could not be found
	 */
	@Override
	public UserNotificationEvent findByU_DT_A_A_Last(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByU_DT_A_A_Last(userId,
				deliveryType, actionRequired, archived, orderByComparator);

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

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public UserNotificationEvent fetchByU_DT_A_A_Last(long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
		int count = countByU_DT_A_A(userId, deliveryType, actionRequired,
				archived);

		if (count == 0) {
			return null;
		}

		List<UserNotificationEvent> list = findByU_DT_A_A(userId, deliveryType,
				actionRequired, archived, count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent[] findByU_DT_A_A_PrevAndNext(
		long userNotificationEventId, long userId, int deliveryType,
		boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = findByPrimaryKey(userNotificationEventId);

		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent[] array = new UserNotificationEventImpl[3];

			array[0] = getByU_DT_A_A_PrevAndNext(session,
					userNotificationEvent, userId, deliveryType,
					actionRequired, archived, orderByComparator, true);

			array[1] = userNotificationEvent;

			array[2] = getByU_DT_A_A_PrevAndNext(session,
					userNotificationEvent, userId, deliveryType,
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

	protected UserNotificationEvent getByU_DT_A_A_PrevAndNext(Session session,
		UserNotificationEvent userNotificationEvent, long userId,
		int deliveryType, boolean actionRequired, boolean archived,
		OrderByComparator<UserNotificationEvent> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE);

		query.append(_FINDER_COLUMN_U_DT_A_A_USERID_2);

		query.append(_FINDER_COLUMN_U_DT_A_A_DELIVERYTYPE_2);

		query.append(_FINDER_COLUMN_U_DT_A_A_ACTIONREQUIRED_2);

		query.append(_FINDER_COLUMN_U_DT_A_A_ARCHIVED_2);

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
			Object[] values = orderByComparator.getOrderByConditionValues(userNotificationEvent);

			for (Object value : values) {
				qPos.add(value);
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
	public void removeByU_DT_A_A(long userId, int deliveryType,
		boolean actionRequired, boolean archived) {
		for (UserNotificationEvent userNotificationEvent : findByU_DT_A_A(
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
	public int countByU_DT_A_A(long userId, int deliveryType,
		boolean actionRequired, boolean archived) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_DT_A_A;

		Object[] finderArgs = new Object[] {
				userId, deliveryType, actionRequired, archived
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_USERNOTIFICATIONEVENT_WHERE);

			query.append(_FINDER_COLUMN_U_DT_A_A_USERID_2);

			query.append(_FINDER_COLUMN_U_DT_A_A_DELIVERYTYPE_2);

			query.append(_FINDER_COLUMN_U_DT_A_A_ACTIONREQUIRED_2);

			query.append(_FINDER_COLUMN_U_DT_A_A_ARCHIVED_2);

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

	private static final String _FINDER_COLUMN_U_DT_A_A_USERID_2 = "userNotificationEvent.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_A_A_DELIVERYTYPE_2 = "userNotificationEvent.deliveryType = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_A_A_ACTIONREQUIRED_2 = "userNotificationEvent.actionRequired = ? AND ";
	private static final String _FINDER_COLUMN_U_DT_A_A_ARCHIVED_2 = "userNotificationEvent.archived = ?";

	public UserNotificationEventPersistenceImpl() {
		setModelClass(UserNotificationEvent.class);
	}

	/**
	 * Caches the user notification event in the entity cache if it is enabled.
	 *
	 * @param userNotificationEvent the user notification event
	 */
	@Override
	public void cacheResult(UserNotificationEvent userNotificationEvent) {
		EntityCacheUtil.putResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
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
	public void cacheResult(List<UserNotificationEvent> userNotificationEvents) {
		for (UserNotificationEvent userNotificationEvent : userNotificationEvents) {
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
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserNotificationEventImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserNotificationEventImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user notification event.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(UserNotificationEvent userNotificationEvent) {
		EntityCacheUtil.removeResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<UserNotificationEvent> userNotificationEvents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (UserNotificationEvent userNotificationEvent : userNotificationEvents) {
			EntityCacheUtil.removeResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
				UserNotificationEventImpl.class,
				userNotificationEvent.getPrimaryKey());
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
		UserNotificationEvent userNotificationEvent = new UserNotificationEventImpl();

		userNotificationEvent.setNew(true);
		userNotificationEvent.setPrimaryKey(userNotificationEventId);

		String uuid = PortalUUIDUtil.generate();

		userNotificationEvent.setUuid(uuid);

		return userNotificationEvent;
	}

	/**
	 * Removes the user notification event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event that was removed
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
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
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent remove(Serializable primaryKey)
		throws NoSuchUserNotificationEventException {
		Session session = null;

		try {
			session = openSession();

			UserNotificationEvent userNotificationEvent = (UserNotificationEvent)session.get(UserNotificationEventImpl.class,
					primaryKey);

			if (userNotificationEvent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserNotificationEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
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
		userNotificationEvent = toUnwrappedModel(userNotificationEvent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(userNotificationEvent)) {
				userNotificationEvent = (UserNotificationEvent)session.get(UserNotificationEventImpl.class,
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
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent) {
		userNotificationEvent = toUnwrappedModel(userNotificationEvent);

		boolean isNew = userNotificationEvent.isNew();

		UserNotificationEventModelImpl userNotificationEventModelImpl = (UserNotificationEventModelImpl)userNotificationEvent;

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
				session.merge(userNotificationEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserNotificationEventModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { userNotificationEventModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUuid(),
						userNotificationEventModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUuid(),
						userNotificationEventModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { userNotificationEventModelImpl.getUserId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalDeliveryType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getDeliveryType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalDelivered()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getDelivered()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalDeliveryType(),
						userNotificationEventModelImpl.getOriginalDelivered()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getDeliveryType(),
						userNotificationEventModelImpl.getDelivered()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalDeliveryType(),
						userNotificationEventModelImpl.getOriginalArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getDeliveryType(),
						userNotificationEventModelImpl.getArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalDelivered(),
						userNotificationEventModelImpl.getOriginalActionRequired()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_D_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D_A,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getDelivered(),
						userNotificationEventModelImpl.getActionRequired()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_D_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_D_A,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalActionRequired(),
						userNotificationEventModelImpl.getOriginalArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_A_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A_A,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getActionRequired(),
						userNotificationEventModelImpl.getArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_A_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_A_A,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalDeliveryType(),
						userNotificationEventModelImpl.getOriginalDelivered(),
						userNotificationEventModelImpl.getOriginalActionRequired()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_D_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D_A,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getDeliveryType(),
						userNotificationEventModelImpl.getDelivered(),
						userNotificationEventModelImpl.getActionRequired()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_D_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_D_A,
					args);
			}

			if ((userNotificationEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userNotificationEventModelImpl.getOriginalUserId(),
						userNotificationEventModelImpl.getOriginalDeliveryType(),
						userNotificationEventModelImpl.getOriginalActionRequired(),
						userNotificationEventModelImpl.getOriginalArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_A_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A_A,
					args);

				args = new Object[] {
						userNotificationEventModelImpl.getUserId(),
						userNotificationEventModelImpl.getDeliveryType(),
						userNotificationEventModelImpl.getActionRequired(),
						userNotificationEventModelImpl.getArchived()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_U_DT_A_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_DT_A_A,
					args);
			}
		}

		EntityCacheUtil.putResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
			UserNotificationEventImpl.class,
			userNotificationEvent.getPrimaryKey(), userNotificationEvent, false);

		userNotificationEvent.resetOriginalValues();

		return userNotificationEvent;
	}

	protected UserNotificationEvent toUnwrappedModel(
		UserNotificationEvent userNotificationEvent) {
		if (userNotificationEvent instanceof UserNotificationEventImpl) {
			return userNotificationEvent;
		}

		UserNotificationEventImpl userNotificationEventImpl = new UserNotificationEventImpl();

		userNotificationEventImpl.setNew(userNotificationEvent.isNew());
		userNotificationEventImpl.setPrimaryKey(userNotificationEvent.getPrimaryKey());

		userNotificationEventImpl.setMvccVersion(userNotificationEvent.getMvccVersion());
		userNotificationEventImpl.setUuid(userNotificationEvent.getUuid());
		userNotificationEventImpl.setUserNotificationEventId(userNotificationEvent.getUserNotificationEventId());
		userNotificationEventImpl.setCompanyId(userNotificationEvent.getCompanyId());
		userNotificationEventImpl.setUserId(userNotificationEvent.getUserId());
		userNotificationEventImpl.setType(userNotificationEvent.getType());
		userNotificationEventImpl.setTimestamp(userNotificationEvent.getTimestamp());
		userNotificationEventImpl.setDeliveryType(userNotificationEvent.getDeliveryType());
		userNotificationEventImpl.setDeliverBy(userNotificationEvent.getDeliverBy());
		userNotificationEventImpl.setDelivered(userNotificationEvent.isDelivered());
		userNotificationEventImpl.setPayload(userNotificationEvent.getPayload());
		userNotificationEventImpl.setActionRequired(userNotificationEvent.isActionRequired());
		userNotificationEventImpl.setArchived(userNotificationEvent.isArchived());

		return userNotificationEventImpl;
	}

	/**
	 * Returns the user notification event with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user notification event
	 * @return the user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserNotificationEventException {
		UserNotificationEvent userNotificationEvent = fetchByPrimaryKey(primaryKey);

		if (userNotificationEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserNotificationEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return userNotificationEvent;
	}

	/**
	 * Returns the user notification event with the primary key or throws a {@link com.liferay.portal.NoSuchUserNotificationEventException} if it could not be found.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event
	 * @throws com.liferay.portal.NoSuchUserNotificationEventException if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent findByPrimaryKey(long userNotificationEventId)
		throws NoSuchUserNotificationEventException {
		return findByPrimaryKey((Serializable)userNotificationEventId);
	}

	/**
	 * Returns the user notification event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user notification event
	 * @return the user notification event, or <code>null</code> if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent fetchByPrimaryKey(Serializable primaryKey) {
		UserNotificationEvent userNotificationEvent = (UserNotificationEvent)EntityCacheUtil.getResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
				UserNotificationEventImpl.class, primaryKey);

		if (userNotificationEvent == _nullUserNotificationEvent) {
			return null;
		}

		if (userNotificationEvent == null) {
			Session session = null;

			try {
				session = openSession();

				userNotificationEvent = (UserNotificationEvent)session.get(UserNotificationEventImpl.class,
						primaryKey);

				if (userNotificationEvent != null) {
					cacheResult(userNotificationEvent);
				}
				else {
					EntityCacheUtil.putResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
						UserNotificationEventImpl.class, primaryKey,
						_nullUserNotificationEvent);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
					UserNotificationEventImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return userNotificationEvent;
	}

	/**
	 * Returns the user notification event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userNotificationEventId the primary key of the user notification event
	 * @return the user notification event, or <code>null</code> if a user notification event with the primary key could not be found
	 */
	@Override
	public UserNotificationEvent fetchByPrimaryKey(long userNotificationEventId) {
		return fetchByPrimaryKey((Serializable)userNotificationEventId);
	}

	@Override
	public Map<Serializable, UserNotificationEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, UserNotificationEvent> map = new HashMap<Serializable, UserNotificationEvent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			UserNotificationEvent userNotificationEvent = fetchByPrimaryKey(primaryKey);

			if (userNotificationEvent != null) {
				map.put(primaryKey, userNotificationEvent);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			UserNotificationEvent userNotificationEvent = (UserNotificationEvent)EntityCacheUtil.getResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
					UserNotificationEventImpl.class, primaryKey);

			if (userNotificationEvent == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, userNotificationEvent);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_USERNOTIFICATIONEVENT_WHERE_PKS_IN);

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

			for (UserNotificationEvent userNotificationEvent : (List<UserNotificationEvent>)q.list()) {
				map.put(userNotificationEvent.getPrimaryKeyObj(),
					userNotificationEvent);

				cacheResult(userNotificationEvent);

				uncachedPrimaryKeys.remove(userNotificationEvent.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(UserNotificationEventModelImpl.ENTITY_CACHE_ENABLED,
					UserNotificationEventImpl.class, primaryKey,
					_nullUserNotificationEvent);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserNotificationEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of user notification events
	 * @param end the upper bound of the range of user notification events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user notification events
	 */
	@Override
	public List<UserNotificationEvent> findAll(int start, int end,
		OrderByComparator<UserNotificationEvent> orderByComparator) {
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

		List<UserNotificationEvent> list = (List<UserNotificationEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERNOTIFICATIONEVENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERNOTIFICATIONEVENT;

				if (pagination) {
					sql = sql.concat(UserNotificationEventModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<UserNotificationEvent>)QueryUtil.list(q,
							getDialect(), start, end);
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
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USERNOTIFICATIONEVENT);

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
	 * Initializes the user notification event persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserNotificationEventImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_USERNOTIFICATIONEVENT = "SELECT userNotificationEvent FROM UserNotificationEvent userNotificationEvent";
	private static final String _SQL_SELECT_USERNOTIFICATIONEVENT_WHERE_PKS_IN = "SELECT userNotificationEvent FROM UserNotificationEvent userNotificationEvent WHERE userNotificationEventId IN (";
	private static final String _SQL_SELECT_USERNOTIFICATIONEVENT_WHERE = "SELECT userNotificationEvent FROM UserNotificationEvent userNotificationEvent WHERE ";
	private static final String _SQL_COUNT_USERNOTIFICATIONEVENT = "SELECT COUNT(userNotificationEvent) FROM UserNotificationEvent userNotificationEvent";
	private static final String _SQL_COUNT_USERNOTIFICATIONEVENT_WHERE = "SELECT COUNT(userNotificationEvent) FROM UserNotificationEvent userNotificationEvent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userNotificationEvent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserNotificationEvent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserNotificationEvent exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(UserNotificationEventPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
	private static final UserNotificationEvent _nullUserNotificationEvent = new UserNotificationEventImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<UserNotificationEvent> toCacheModel() {
				return _nullUserNotificationEventCacheModel;
			}
		};

	private static final CacheModel<UserNotificationEvent> _nullUserNotificationEventCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<UserNotificationEvent>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public UserNotificationEvent toEntityModel() {
			return _nullUserNotificationEvent;
		}
	}
}