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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchSystemEventException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.SystemEvent;
import com.liferay.portal.model.impl.SystemEventImpl;
import com.liferay.portal.model.impl.SystemEventModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the system event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SystemEventPersistence
 * @see SystemEventUtil
 * @generated
 */
public class SystemEventPersistenceImpl extends BasePersistenceImpl<SystemEvent>
	implements SystemEventPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SystemEventUtil} to access the system event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SystemEventImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SystemEventModelImpl.GROUPID_COLUMN_BITMASK |
			SystemEventModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the system events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByGroupId(long groupId, int start, int end,
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

		List<SystemEvent> list = (List<SystemEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SystemEvent systemEvent : list) {
				if ((groupId != systemEvent.getGroupId())) {
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

			query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SystemEvent>(list);
				}
				else {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByGroupId_First(groupId,
				orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<SystemEvent> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByGroupId_Last(groupId, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent[] findByGroupId_PrevAndNext(long systemEventId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, systemEvent, groupId,
					orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByGroupId_PrevAndNext(session, systemEvent, groupId,
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

	protected SystemEvent getByGroupId_PrevAndNext(Session session,
		SystemEvent systemEvent, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

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
			query.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(systemEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SystemEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByGroupId(long groupId) throws SystemException {
		for (SystemEvent systemEvent : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching system events
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

			query.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "systemEvent.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), Long.class.getName() },
			SystemEventModelImpl.GROUPID_COLUMN_BITMASK |
			SystemEventModelImpl.SYSTEMEVENTSETKEY_COLUMN_BITMASK |
			SystemEventModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @return the matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_S(long groupId, long systemEventSetKey)
		throws SystemException {
		return findByG_S(groupId, systemEventSetKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_S(long groupId, long systemEventSetKey,
		int start, int end) throws SystemException {
		return findByG_S(groupId, systemEventSetKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_S(long groupId, long systemEventSetKey,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, systemEventSetKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, systemEventSetKey,
					
					start, end, orderByComparator
				};
		}

		List<SystemEvent> list = (List<SystemEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SystemEvent systemEvent : list) {
				if ((groupId != systemEvent.getGroupId()) ||
						(systemEventSetKey != systemEvent.getSystemEventSetKey())) {
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

			query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(systemEventSetKey);

				if (!pagination) {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SystemEvent>(list);
				}
				else {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByG_S_First(long groupId, long systemEventSetKey,
		OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByG_S_First(groupId, systemEventSetKey,
				orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", systemEventSetKey=");
		msg.append(systemEventSetKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByG_S_First(long groupId, long systemEventSetKey,
		OrderByComparator orderByComparator) throws SystemException {
		List<SystemEvent> list = findByG_S(groupId, systemEventSetKey, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByG_S_Last(long groupId, long systemEventSetKey,
		OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByG_S_Last(groupId, systemEventSetKey,
				orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", systemEventSetKey=");
		msg.append(systemEventSetKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByG_S_Last(long groupId, long systemEventSetKey,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_S(groupId, systemEventSetKey);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByG_S(groupId, systemEventSetKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent[] findByG_S_PrevAndNext(long systemEventId,
		long groupId, long systemEventSetKey,
		OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByG_S_PrevAndNext(session, systemEvent, groupId,
					systemEventSetKey, orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByG_S_PrevAndNext(session, systemEvent, groupId,
					systemEventSetKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SystemEvent getByG_S_PrevAndNext(Session session,
		SystemEvent systemEvent, long groupId, long systemEventSetKey,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2);

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
			query.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(systemEventSetKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(systemEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SystemEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; and systemEventSetKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_S(long groupId, long systemEventSetKey)
		throws SystemException {
		for (SystemEvent systemEvent : findByG_S(groupId, systemEventSetKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @return the number of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_S(long groupId, long systemEventSetKey)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, systemEventSetKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(systemEventSetKey);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "systemEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2 = "systemEvent.systemEventSetKey = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SystemEventModelImpl.GROUPID_COLUMN_BITMASK |
			SystemEventModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SystemEventModelImpl.CLASSPK_COLUMN_BITMASK |
			SystemEventModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_C_C(long groupId, long classNameId,
		long classPK) throws SystemException {
		return findByG_C_C(groupId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) throws SystemException {
		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] { groupId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] {
					groupId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SystemEvent> list = (List<SystemEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SystemEvent systemEvent : list) {
				if ((groupId != systemEvent.getGroupId()) ||
						(classNameId != systemEvent.getClassNameId()) ||
						(classPK != systemEvent.getClassPK())) {
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

			query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SystemEvent>(list);
				}
				else {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByG_C_C_First(groupId, classNameId,
				classPK, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		List<SystemEvent> list = findByG_C_C(groupId, classNameId, classPK, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByG_C_C_Last(groupId, classNameId,
				classPK, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByG_C_C(groupId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent[] findByG_C_C_PrevAndNext(long systemEventId,
		long groupId, long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByG_C_C_PrevAndNext(session, systemEvent, groupId,
					classNameId, classPK, orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByG_C_C_PrevAndNext(session, systemEvent, groupId,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SystemEvent getByG_C_C_PrevAndNext(Session session,
		SystemEvent systemEvent, long groupId, long classNameId, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			query.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(systemEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SystemEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK)
		throws SystemException {
		for (SystemEvent systemEvent : findByG_C_C(groupId, classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "systemEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "systemEvent.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "systemEvent.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_T = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T =
		new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, SystemEventImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			SystemEventModelImpl.GROUPID_COLUMN_BITMASK |
			SystemEventModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SystemEventModelImpl.CLASSPK_COLUMN_BITMASK |
			SystemEventModelImpl.TYPE_COLUMN_BITMASK |
			SystemEventModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_T = new FinderPath(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(long groupId, long classNameId,
		long classPK, int type) throws SystemException {
		return findByG_C_C_T(groupId, classNameId, classPK, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(long groupId, long classNameId,
		long classPK, int type, int start, int end) throws SystemException {
		return findByG_C_C_T(groupId, classNameId, classPK, type, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(long groupId, long classNameId,
		long classPK, int type, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T;
			finderArgs = new Object[] { groupId, classNameId, classPK, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_T;
			finderArgs = new Object[] {
					groupId, classNameId, classPK, type,
					
					start, end, orderByComparator
				};
		}

		List<SystemEvent> list = (List<SystemEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (SystemEvent systemEvent : list) {
				if ((groupId != systemEvent.getGroupId()) ||
						(classNameId != systemEvent.getClassNameId()) ||
						(classPK != systemEvent.getClassPK()) ||
						(type != systemEvent.getType())) {
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

			query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				if (!pagination) {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SystemEvent>(list);
				}
				else {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByG_C_C_T_First(long groupId, long classNameId,
		long classPK, int type, OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByG_C_C_T_First(groupId, classNameId,
				classPK, type, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByG_C_C_T_First(long groupId, long classNameId,
		long classPK, int type, OrderByComparator orderByComparator)
		throws SystemException {
		List<SystemEvent> list = findByG_C_C_T(groupId, classNameId, classPK,
				type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, int type, OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByG_C_C_T_Last(groupId, classNameId,
				classPK, type, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchSystemEventException(msg.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, int type, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_C_T(groupId, classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByG_C_C_T(groupId, classNameId, classPK,
				type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent[] findByG_C_C_T_PrevAndNext(long systemEventId,
		long groupId, long classNameId, long classPK, int type,
		OrderByComparator orderByComparator)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByG_C_C_T_PrevAndNext(session, systemEvent, groupId,
					classNameId, classPK, type, orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByG_C_C_T_PrevAndNext(session, systemEvent, groupId,
					classNameId, classPK, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SystemEvent getByG_C_C_T_PrevAndNext(Session session,
		SystemEvent systemEvent, long groupId, long classNameId, long classPK,
		int type, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

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
			query.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(systemEvent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SystemEvent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_C_C_T(long groupId, long classNameId, long classPK,
		int type) throws SystemException {
		for (SystemEvent systemEvent : findByG_C_C_T(groupId, classNameId,
				classPK, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param type the type
	 * @return the number of matching system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_C_C_T(long groupId, long classNameId, long classPK,
		int type) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_T;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_C_C_T_GROUPID_2 = "systemEvent.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2 = "systemEvent.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_CLASSPK_2 = "systemEvent.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_2 = "systemEvent.type = ?";

	public SystemEventPersistenceImpl() {
		setModelClass(SystemEvent.class);
	}

	/**
	 * Caches the system event in the entity cache if it is enabled.
	 *
	 * @param systemEvent the system event
	 */
	@Override
	public void cacheResult(SystemEvent systemEvent) {
		EntityCacheUtil.putResult(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventImpl.class, systemEvent.getPrimaryKey(), systemEvent);

		systemEvent.resetOriginalValues();
	}

	/**
	 * Caches the system events in the entity cache if it is enabled.
	 *
	 * @param systemEvents the system events
	 */
	@Override
	public void cacheResult(List<SystemEvent> systemEvents) {
		for (SystemEvent systemEvent : systemEvents) {
			if (EntityCacheUtil.getResult(
						SystemEventModelImpl.ENTITY_CACHE_ENABLED,
						SystemEventImpl.class, systemEvent.getPrimaryKey()) == null) {
				cacheResult(systemEvent);
			}
			else {
				systemEvent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all system events.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SystemEventImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SystemEventImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the system event.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SystemEvent systemEvent) {
		EntityCacheUtil.removeResult(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventImpl.class, systemEvent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SystemEvent> systemEvents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SystemEvent systemEvent : systemEvents) {
			EntityCacheUtil.removeResult(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
				SystemEventImpl.class, systemEvent.getPrimaryKey());
		}
	}

	/**
	 * Creates a new system event with the primary key. Does not add the system event to the database.
	 *
	 * @param systemEventId the primary key for the new system event
	 * @return the new system event
	 */
	@Override
	public SystemEvent create(long systemEventId) {
		SystemEvent systemEvent = new SystemEventImpl();

		systemEvent.setNew(true);
		systemEvent.setPrimaryKey(systemEventId);

		return systemEvent;
	}

	/**
	 * Removes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event that was removed
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent remove(long systemEventId)
		throws NoSuchSystemEventException, SystemException {
		return remove((Serializable)systemEventId);
	}

	/**
	 * Removes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the system event
	 * @return the system event that was removed
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent remove(Serializable primaryKey)
		throws NoSuchSystemEventException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SystemEvent systemEvent = (SystemEvent)session.get(SystemEventImpl.class,
					primaryKey);

			if (systemEvent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSystemEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(systemEvent);
		}
		catch (NoSuchSystemEventException nsee) {
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
	protected SystemEvent removeImpl(SystemEvent systemEvent)
		throws SystemException {
		systemEvent = toUnwrappedModel(systemEvent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(systemEvent)) {
				systemEvent = (SystemEvent)session.get(SystemEventImpl.class,
						systemEvent.getPrimaryKeyObj());
			}

			if (systemEvent != null) {
				session.delete(systemEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (systemEvent != null) {
			clearCache(systemEvent);
		}

		return systemEvent;
	}

	@Override
	public SystemEvent updateImpl(
		com.liferay.portal.model.SystemEvent systemEvent)
		throws SystemException {
		systemEvent = toUnwrappedModel(systemEvent);

		boolean isNew = systemEvent.isNew();

		SystemEventModelImpl systemEventModelImpl = (SystemEventModelImpl)systemEvent;

		Session session = null;

		try {
			session = openSession();

			if (systemEvent.isNew()) {
				session.save(systemEvent);

				systemEvent.setNew(false);
			}
			else {
				session.merge(systemEvent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SystemEventModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((systemEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						systemEventModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { systemEventModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((systemEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						systemEventModelImpl.getOriginalGroupId(),
						systemEventModelImpl.getOriginalSystemEventSetKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						systemEventModelImpl.getGroupId(),
						systemEventModelImpl.getSystemEventSetKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}

			if ((systemEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						systemEventModelImpl.getOriginalGroupId(),
						systemEventModelImpl.getOriginalClassNameId(),
						systemEventModelImpl.getOriginalClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);

				args = new Object[] {
						systemEventModelImpl.getGroupId(),
						systemEventModelImpl.getClassNameId(),
						systemEventModelImpl.getClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);
			}

			if ((systemEventModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						systemEventModelImpl.getOriginalGroupId(),
						systemEventModelImpl.getOriginalClassNameId(),
						systemEventModelImpl.getOriginalClassPK(),
						systemEventModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T,
					args);

				args = new Object[] {
						systemEventModelImpl.getGroupId(),
						systemEventModelImpl.getClassNameId(),
						systemEventModelImpl.getClassPK(),
						systemEventModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_C_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_T,
					args);
			}
		}

		EntityCacheUtil.putResult(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
			SystemEventImpl.class, systemEvent.getPrimaryKey(), systemEvent);

		systemEvent.resetOriginalValues();

		return systemEvent;
	}

	protected SystemEvent toUnwrappedModel(SystemEvent systemEvent) {
		if (systemEvent instanceof SystemEventImpl) {
			return systemEvent;
		}

		SystemEventImpl systemEventImpl = new SystemEventImpl();

		systemEventImpl.setNew(systemEvent.isNew());
		systemEventImpl.setPrimaryKey(systemEvent.getPrimaryKey());

		systemEventImpl.setSystemEventId(systemEvent.getSystemEventId());
		systemEventImpl.setGroupId(systemEvent.getGroupId());
		systemEventImpl.setCompanyId(systemEvent.getCompanyId());
		systemEventImpl.setUserId(systemEvent.getUserId());
		systemEventImpl.setUserName(systemEvent.getUserName());
		systemEventImpl.setCreateDate(systemEvent.getCreateDate());
		systemEventImpl.setClassNameId(systemEvent.getClassNameId());
		systemEventImpl.setClassPK(systemEvent.getClassPK());
		systemEventImpl.setClassUuid(systemEvent.getClassUuid());
		systemEventImpl.setReferrerClassNameId(systemEvent.getReferrerClassNameId());
		systemEventImpl.setParentSystemEventId(systemEvent.getParentSystemEventId());
		systemEventImpl.setSystemEventSetKey(systemEvent.getSystemEventSetKey());
		systemEventImpl.setType(systemEvent.getType());
		systemEventImpl.setExtraData(systemEvent.getExtraData());
		systemEventImpl.setMvccVersion(systemEvent.getMvccVersion());

		return systemEventImpl;
	}

	/**
	 * Returns the system event with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the system event
	 * @return the system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSystemEventException, SystemException {
		SystemEvent systemEvent = fetchByPrimaryKey(primaryKey);

		if (systemEvent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSystemEventException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return systemEvent;
	}

	/**
	 * Returns the system event with the primary key or throws a {@link com.liferay.portal.NoSuchSystemEventException} if it could not be found.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event
	 * @throws com.liferay.portal.NoSuchSystemEventException if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent findByPrimaryKey(long systemEventId)
		throws NoSuchSystemEventException, SystemException {
		return findByPrimaryKey((Serializable)systemEventId);
	}

	/**
	 * Returns the system event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the system event
	 * @return the system event, or <code>null</code> if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		SystemEvent systemEvent = (SystemEvent)EntityCacheUtil.getResult(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
				SystemEventImpl.class, primaryKey);

		if (systemEvent == _nullSystemEvent) {
			return null;
		}

		if (systemEvent == null) {
			Session session = null;

			try {
				session = openSession();

				systemEvent = (SystemEvent)session.get(SystemEventImpl.class,
						primaryKey);

				if (systemEvent != null) {
					cacheResult(systemEvent);
				}
				else {
					EntityCacheUtil.putResult(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
						SystemEventImpl.class, primaryKey, _nullSystemEvent);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(SystemEventModelImpl.ENTITY_CACHE_ENABLED,
					SystemEventImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return systemEvent;
	}

	/**
	 * Returns the system event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event, or <code>null</code> if a system event with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SystemEvent fetchByPrimaryKey(long systemEventId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)systemEventId);
	}

	/**
	 * Returns all the system events.
	 *
	 * @return the system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.SystemEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of system events
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SystemEvent> findAll(int start, int end,
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

		List<SystemEvent> list = (List<SystemEvent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SYSTEMEVENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SYSTEMEVENT;

				if (pagination) {
					sql = sql.concat(SystemEventModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SystemEvent>(list);
				}
				else {
					list = (List<SystemEvent>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the system events from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (SystemEvent systemEvent : findAll()) {
			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events.
	 *
	 * @return the number of system events
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

				Query q = session.createQuery(_SQL_COUNT_SYSTEMEVENT);

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
	 * Initializes the system event persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.SystemEvent")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SystemEvent>> listenersList = new ArrayList<ModelListener<SystemEvent>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SystemEvent>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(SystemEventImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SYSTEMEVENT = "SELECT systemEvent FROM SystemEvent systemEvent";
	private static final String _SQL_SELECT_SYSTEMEVENT_WHERE = "SELECT systemEvent FROM SystemEvent systemEvent WHERE ";
	private static final String _SQL_COUNT_SYSTEMEVENT = "SELECT COUNT(systemEvent) FROM SystemEvent systemEvent";
	private static final String _SQL_COUNT_SYSTEMEVENT_WHERE = "SELECT COUNT(systemEvent) FROM SystemEvent systemEvent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "systemEvent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SystemEvent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SystemEvent exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SystemEventPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
	private static SystemEvent _nullSystemEvent = new SystemEventImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SystemEvent> toCacheModel() {
				return _nullSystemEventCacheModel;
			}
		};

	private static CacheModel<SystemEvent> _nullSystemEventCacheModel = new CacheModel<SystemEvent>() {
			@Override
			public SystemEvent toEntityModel() {
				return _nullSystemEvent;
			}
		};
}