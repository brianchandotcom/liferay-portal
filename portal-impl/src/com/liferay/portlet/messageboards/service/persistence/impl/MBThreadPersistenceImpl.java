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

package com.liferay.portlet.messageboards.service.persistence.impl;

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
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl;
import com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence;

import java.io.Serializable;

import java.sql.Timestamp;

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
 * The persistence implementation for the message boards thread service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadPersistence
 * @see com.liferay.portlet.messageboards.service.persistence.MBThreadUtil
 * @generated
 */
@ProviderType
public class MBThreadPersistenceImpl extends BasePersistenceImpl<MBThread>
	implements MBThreadPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MBThreadUtil} to access the message boards thread persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MBThreadImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MBThreadModelImpl.UUID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the message boards threads where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(String uuid, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid(String uuid, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
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

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if (!Validator.equals(uuid, mbThread.getUuid())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
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
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_First(String uuid,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByUuid_First(uuid, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_First(String uuid,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_Last(String uuid,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByUuid_Last(uuid, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_Last(String uuid,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where uuid = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByUuid_PrevAndNext(long threadId, String uuid,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mbThread, uuid,
					orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByUuid_PrevAndNext(session, mbThread, uuid,
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

	protected MBThread getByUuid_PrevAndNext(Session session,
		MBThread mbThread, String uuid,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MBThread mbThread : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mbThread.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mbThread.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mbThread.uuid IS NULL OR mbThread.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MBThreadModelImpl.UUID_COLUMN_BITMASK |
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the message boards thread where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchThreadException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUUID_G(String uuid, long groupId)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByUUID_G(uuid, groupId);

		if (mbThread == null) {
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

			throw new NoSuchThreadException(msg.toString());
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the message boards thread where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MBThread) {
			MBThread mbThread = (MBThread)result;

			if (!Validator.equals(uuid, mbThread.getUuid()) ||
					(groupId != mbThread.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

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

				List<MBThread> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					MBThread mbThread = list.get(0);

					result = mbThread;

					cacheResult(mbThread);

					if ((mbThread.getUuid() == null) ||
							!mbThread.getUuid().equals(uuid) ||
							(mbThread.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mbThread);
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
			return (MBThread)result;
		}
	}

	/**
	 * Removes the message boards thread where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the message boards thread that was removed
	 */
	@Override
	public MBThread removeByUUID_G(String uuid, long groupId)
		throws NoSuchThreadException {
		MBThread mbThread = findByUUID_G(uuid, groupId);

		return remove(mbThread);
	}

	/**
	 * Returns the number of message boards threads where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mbThread.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mbThread.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mbThread.uuid IS NULL OR mbThread.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mbThread.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MBThreadModelImpl.UUID_COLUMN_BITMASK |
			MBThreadModelImpl.COMPANYID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<MBThread> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<MBThread> orderByComparator,
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

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if (!Validator.equals(uuid, mbThread.getUuid()) ||
							(companyId != mbThread.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
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
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByUuid_C_PrevAndNext(long threadId, String uuid,
		long companyId, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mbThread, uuid,
					companyId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByUuid_C_PrevAndNext(session, mbThread, uuid,
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

	protected MBThread getByUuid_C_PrevAndNext(Session session,
		MBThread mbThread, String uuid, long companyId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MBThread mbThread : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mbThread.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mbThread.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mbThread.uuid IS NULL OR mbThread.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mbThread.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the message boards threads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(long groupId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByGroupId(long groupId, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
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

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByGroupId_First(long groupId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByGroupId_First(groupId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByGroupId_First(long groupId,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByGroupId_Last(long groupId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByGroupId_Last(groupId, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByGroupId_Last(long groupId,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByGroupId_PrevAndNext(long threadId, long groupId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, mbThread, groupId,
					orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByGroupId_PrevAndNext(session, mbThread, groupId,
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

	protected MBThread getByGroupId_PrevAndNext(Session session,
		MBThread mbThread, long groupId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByGroupId(long groupId, int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByGroupId(long groupId, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByGroupId_PrevAndNext(long threadId,
		long groupId, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(threadId, groupId,
				orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, mbThread,
					groupId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByGroupId_PrevAndNext(session, mbThread,
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

	protected MBThread filterGetByGroupId_PrevAndNext(Session session,
		MBThread mbThread, long groupId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (MBThread mbThread : findByGroupId(groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

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

	/**
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "mbThread.groupId = ? AND mbThread.categoryId != -1";
	public static final FinderPath FINDER_PATH_FETCH_BY_ROOTMESSAGEID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByRootMessageId",
			new String[] { Long.class.getName() },
			MBThreadModelImpl.ROOTMESSAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ROOTMESSAGEID = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRootMessageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the message boards thread where rootMessageId = &#63; or throws a {@link NoSuchThreadException} if it could not be found.
	 *
	 * @param rootMessageId the root message ID
	 * @return the matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByRootMessageId(long rootMessageId)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByRootMessageId(rootMessageId);

		if (mbThread == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("rootMessageId=");
			msg.append(rootMessageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchThreadException(msg.toString());
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread where rootMessageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param rootMessageId the root message ID
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByRootMessageId(long rootMessageId) {
		return fetchByRootMessageId(rootMessageId, true);
	}

	/**
	 * Returns the message boards thread where rootMessageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param rootMessageId the root message ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByRootMessageId(long rootMessageId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { rootMessageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID,
					finderArgs, this);
		}

		if (result instanceof MBThread) {
			MBThread mbThread = (MBThread)result;

			if ((rootMessageId != mbThread.getRootMessageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_ROOTMESSAGEID_ROOTMESSAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rootMessageId);

				List<MBThread> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"MBThreadPersistenceImpl.fetchByRootMessageId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					MBThread mbThread = list.get(0);

					result = mbThread;

					cacheResult(mbThread);

					if ((mbThread.getRootMessageId() != rootMessageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID,
							finderArgs, mbThread);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID,
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
			return (MBThread)result;
		}
	}

	/**
	 * Removes the message boards thread where rootMessageId = &#63; from the database.
	 *
	 * @param rootMessageId the root message ID
	 * @return the message boards thread that was removed
	 */
	@Override
	public MBThread removeByRootMessageId(long rootMessageId)
		throws NoSuchThreadException {
		MBThread mbThread = findByRootMessageId(rootMessageId);

		return remove(mbThread);
	}

	/**
	 * Returns the number of message boards threads where rootMessageId = &#63;.
	 *
	 * @param rootMessageId the root message ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByRootMessageId(long rootMessageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ROOTMESSAGEID;

		Object[] finderArgs = new Object[] { rootMessageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_ROOTMESSAGEID_ROOTMESSAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rootMessageId);

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

	private static final String _FINDER_COLUMN_ROOTMESSAGEID_ROOTMESSAGEID_2 = "mbThread.rootMessageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long categoryId) {
		return findByG_C(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long categoryId, int start,
		int end) {
		return findByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long categoryId, int start,
		int end, OrderByComparator<MBThread> orderByComparator) {
		return findByG_C(groupId, categoryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long categoryId, int start,
		int end, OrderByComparator<MBThread> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, categoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, categoryId,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(categoryId != mbThread.getCategoryId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_First(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_First(groupId, categoryId,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_First(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_C(groupId, categoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_Last(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_Last(groupId, categoryId,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_Last(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_C(groupId, categoryId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C(groupId, categoryId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_PrevAndNext(long threadId, long groupId,
		long categoryId, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_PrevAndNext(session, mbThread, groupId,
					categoryId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_C_PrevAndNext(session, mbThread, groupId,
					categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_C_PrevAndNext(Session session, MBThread mbThread,
		long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long categoryId) {
		return filterFindByG_C(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long categoryId,
		int start, int end) {
		return filterFindByG_C(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long categoryId,
		int start, int end, OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, categoryId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_PrevAndNext(long threadId, long groupId,
		long categoryId, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(threadId, groupId, categoryId,
				orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(session, mbThread, groupId,
					categoryId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_PrevAndNext(session, mbThread, groupId,
					categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_C_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long[] categoryIds) {
		return filterFindByG_C(groupId, categoryIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long[] categoryIds,
		int start, int end) {
		return filterFindByG_C(groupId, categoryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C(long groupId, long[] categoryIds,
		int start, int end, OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, categoryIds, start, end, orderByComparator);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		if (categoryIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

			query.append(StringUtil.merge(categoryIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long[] categoryIds) {
		return findByG_C(groupId, categoryIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long[] categoryIds,
		int start, int end) {
		return findByG_C(groupId, categoryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long[] categoryIds,
		int start, int end, OrderByComparator<MBThread> orderByComparator) {
		return findByG_C(groupId, categoryIds, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C(long groupId, long[] categoryIds,
		int start, int end, OrderByComparator<MBThread> orderByComparator,
		boolean retrieveFromCache) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		if (categoryIds.length == 1) {
			return findByG_C(groupId, categoryIds[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { groupId, StringUtil.merge(categoryIds) };
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds),
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							!ArrayUtil.contains(categoryIds,
								mbThread.getCategoryId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			if (categoryIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

				query.append(StringUtil.merge(categoryIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C,
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
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 */
	@Override
	public void removeByG_C(long groupId, long categoryId) {
		for (MBThread mbThread : findByG_C(groupId, categoryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C(long groupId, long categoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, categoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

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
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C(long groupId, long[] categoryIds) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds)
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			if (categoryIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

				query.append(StringUtil.merge(categoryIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

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

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C,
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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long categoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, categoryId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long[] categoryIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, categoryIds);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		if (categoryIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_CATEGORYID_7);

			query.append(StringUtil.merge(categoryIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CATEGORYID_2 = "mbThread.categoryId = ?";
	private static final String _FINDER_COLUMN_G_C_CATEGORYID_7 = "mbThread.categoryId IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTC = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_NotC",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTC = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotC",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(long groupId, long categoryId) {
		return findByG_NotC(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(long groupId, long categoryId,
		int start, int end) {
		return findByG_NotC(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(long groupId, long categoryId,
		int start, int end, OrderByComparator<MBThread> orderByComparator) {
		return findByG_NotC(groupId, categoryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC(long groupId, long categoryId,
		int start, int end, OrderByComparator<MBThread> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTC;
		finderArgs = new Object[] {
				groupId, categoryId,
				
				start, end, orderByComparator
			};

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(categoryId == mbThread.getCategoryId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_First(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_NotC_First(groupId, categoryId,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_First(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_NotC(groupId, categoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_Last(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_NotC_Last(groupId, categoryId,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_Last(long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_NotC(groupId, categoryId);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_NotC(groupId, categoryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_NotC_PrevAndNext(long threadId, long groupId,
		long categoryId, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_NotC_PrevAndNext(session, mbThread, groupId,
					categoryId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_NotC_PrevAndNext(session, mbThread, groupId,
					categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_NotC_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC(long groupId, long categoryId) {
		return filterFindByG_NotC(groupId, categoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC(long groupId, long categoryId,
		int start, int end) {
		return filterFindByG_NotC(groupId, categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC(long groupId, long categoryId,
		int start, int end, OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC(groupId, categoryId, start, end,
				orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_NotC_PrevAndNext(long threadId,
		long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_PrevAndNext(threadId, groupId, categoryId,
				orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_NotC_PrevAndNext(session, mbThread,
					groupId, categoryId, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_NotC_PrevAndNext(session, mbThread,
					groupId, categoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_NotC_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 */
	@Override
	public void removeByG_NotC(long groupId, long categoryId) {
		for (MBThread mbThread : findByG_NotC(groupId, categoryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_NotC(long groupId, long categoryId) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTC;

		Object[] finderArgs = new Object[] { groupId, categoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotC(long groupId, long categoryId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotC(groupId, categoryId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_NOTC_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_CATEGORYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

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

	private static final String _FINDER_COLUMN_G_NOTC_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NOTC_CATEGORYID_2 = "mbThread.categoryId != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.STATUS_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(long groupId, int status) {
		return findByG_S(groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(long groupId, int status, int start, int end) {
		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator<MBThread> orderByComparator) {
		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator<MBThread> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(status != mbThread.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_S_First(long groupId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_S_First(groupId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_S_First(long groupId, int status,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_S(groupId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_S_Last(long groupId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_S_Last(groupId, status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_S_Last(long groupId, int status,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_S(groupId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_S_PrevAndNext(long threadId, long groupId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_S_PrevAndNext(session, mbThread, groupId, status,
					orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_S_PrevAndNext(session, mbThread, groupId, status,
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

	protected MBThread getByG_S_PrevAndNext(Session session, MBThread mbThread,
		long groupId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_S(long groupId, int status) {
		return filterFindByG_S(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_S(long groupId, int status, int start,
		int end) {
		return filterFindByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_S(long groupId, int status, int start,
		int end, OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, status, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_S_PrevAndNext(long threadId, long groupId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_PrevAndNext(threadId, groupId, status,
				orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_S_PrevAndNext(session, mbThread, groupId,
					status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_S_PrevAndNext(session, mbThread, groupId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_S_PrevAndNext(Session session,
		MBThread mbThread, long groupId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (MBThread mbThread : findByG_S(groupId, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_S(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "mbThread.status = ? AND mbThread.categoryId != -1";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Double.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] { Long.class.getName(), Double.class.getName() },
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] { Long.class.getName(), Double.class.getName() });

	/**
	 * Returns all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(long categoryId, double priority) {
		return findByC_P(categoryId, priority, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(long categoryId, double priority,
		int start, int end) {
		return findByC_P(categoryId, priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(long categoryId, double priority,
		int start, int end, OrderByComparator<MBThread> orderByComparator) {
		return findByC_P(categoryId, priority, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByC_P(long categoryId, double priority,
		int start, int end, OrderByComparator<MBThread> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] { categoryId, priority };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] {
					categoryId, priority,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((categoryId != mbThread.getCategoryId()) ||
							(priority != mbThread.getPriority())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_C_P_CATEGORYID_2);

			query.append(_FINDER_COLUMN_C_P_PRIORITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				qPos.add(priority);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByC_P_First(long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByC_P_First(categoryId, priority,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("categoryId=");
		msg.append(categoryId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByC_P_First(long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByC_P(categoryId, priority, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByC_P_Last(long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByC_P_Last(categoryId, priority,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("categoryId=");
		msg.append(categoryId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByC_P_Last(long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByC_P(categoryId, priority);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByC_P(categoryId, priority, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where categoryId = &#63; and priority = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByC_P_PrevAndNext(long threadId, long categoryId,
		double priority, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByC_P_PrevAndNext(session, mbThread, categoryId,
					priority, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByC_P_PrevAndNext(session, mbThread, categoryId,
					priority, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByC_P_PrevAndNext(Session session, MBThread mbThread,
		long categoryId, double priority,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_C_P_CATEGORYID_2);

		query.append(_FINDER_COLUMN_C_P_PRIORITY_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(categoryId);

		qPos.add(priority);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where categoryId = &#63; and priority = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 */
	@Override
	public void removeByC_P(long categoryId, double priority) {
		for (MBThread mbThread : findByC_P(categoryId, priority,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where categoryId = &#63; and priority = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param priority the priority
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByC_P(long categoryId, double priority) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_P;

		Object[] finderArgs = new Object[] { categoryId, priority };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_C_P_CATEGORYID_2);

			query.append(_FINDER_COLUMN_C_P_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(categoryId);

				qPos.add(priority);

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

	private static final String _FINDER_COLUMN_C_P_CATEGORYID_2 = "mbThread.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PRIORITY_2 = "mbThread.priority = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByL_P",
			new String[] {
				Date.class.getName(), Double.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByL_P",
			new String[] { Date.class.getName(), Double.class.getName() },
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_L_P = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P",
			new String[] { Date.class.getName(), Double.class.getName() });

	/**
	 * Returns all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(Date lastPostDate, double priority) {
		return findByL_P(lastPostDate, priority, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(Date lastPostDate, double priority,
		int start, int end) {
		return findByL_P(lastPostDate, priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(Date lastPostDate, double priority,
		int start, int end, OrderByComparator<MBThread> orderByComparator) {
		return findByL_P(lastPostDate, priority, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByL_P(Date lastPostDate, double priority,
		int start, int end, OrderByComparator<MBThread> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P;
			finderArgs = new Object[] { lastPostDate, priority };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_L_P;
			finderArgs = new Object[] {
					lastPostDate, priority,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if (!Validator.equals(lastPostDate,
								mbThread.getLastPostDate()) ||
							(priority != mbThread.getPriority())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				query.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_2);
			}

			query.append(_FINDER_COLUMN_L_P_PRIORITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLastPostDate) {
					qPos.add(new Timestamp(lastPostDate.getTime()));
				}

				qPos.add(priority);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByL_P_First(Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByL_P_First(lastPostDate, priority,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lastPostDate=");
		msg.append(lastPostDate);

		msg.append(", priority=");
		msg.append(priority);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByL_P_First(Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByL_P(lastPostDate, priority, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByL_P_Last(Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByL_P_Last(lastPostDate, priority,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lastPostDate=");
		msg.append(lastPostDate);

		msg.append(", priority=");
		msg.append(priority);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByL_P_Last(Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator) {
		int count = countByL_P(lastPostDate, priority);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByL_P(lastPostDate, priority, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByL_P_PrevAndNext(long threadId, Date lastPostDate,
		double priority, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByL_P_PrevAndNext(session, mbThread, lastPostDate,
					priority, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByL_P_PrevAndNext(session, mbThread, lastPostDate,
					priority, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByL_P_PrevAndNext(Session session, MBThread mbThread,
		Date lastPostDate, double priority,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			query.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			query.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_2);
		}

		query.append(_FINDER_COLUMN_L_P_PRIORITY_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLastPostDate) {
			qPos.add(new Timestamp(lastPostDate.getTime()));
		}

		qPos.add(priority);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where lastPostDate = &#63; and priority = &#63; from the database.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 */
	@Override
	public void removeByL_P(Date lastPostDate, double priority) {
		for (MBThread mbThread : findByL_P(lastPostDate, priority,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where lastPostDate = &#63; and priority = &#63;.
	 *
	 * @param lastPostDate the last post date
	 * @param priority the priority
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByL_P(Date lastPostDate, double priority) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_L_P;

		Object[] finderArgs = new Object[] { lastPostDate, priority };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				query.append(_FINDER_COLUMN_L_P_LASTPOSTDATE_2);
			}

			query.append(_FINDER_COLUMN_L_P_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLastPostDate) {
					qPos.add(new Timestamp(lastPostDate.getTime()));
				}

				qPos.add(priority);

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

	private static final String _FINDER_COLUMN_L_P_LASTPOSTDATE_1 = "mbThread.lastPostDate IS NULL AND ";
	private static final String _FINDER_COLUMN_L_P_LASTPOSTDATE_2 = "mbThread.lastPostDate = ? AND ";
	private static final String _FINDER_COLUMN_L_P_PRIORITY_2 = "mbThread.priority = ? AND mbThread.categoryId != -1";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_L = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_L = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_L",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(long groupId, long categoryId,
		Date lastPostDate) {
		return findByG_C_L(groupId, categoryId, lastPostDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(long groupId, long categoryId,
		Date lastPostDate, int start, int end) {
		return findByG_C_L(groupId, categoryId, lastPostDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(long groupId, long categoryId,
		Date lastPostDate, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByG_C_L(groupId, categoryId, lastPostDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_L(long groupId, long categoryId,
		Date lastPostDate, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L;
			finderArgs = new Object[] { groupId, categoryId, lastPostDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_L;
			finderArgs = new Object[] {
					groupId, categoryId, lastPostDate,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(categoryId != mbThread.getCategoryId()) ||
							!Validator.equals(lastPostDate,
								mbThread.getLastPostDate())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (bindLastPostDate) {
					qPos.add(new Timestamp(lastPostDate.getTime()));
				}

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_L_First(long groupId, long categoryId,
		Date lastPostDate, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_L_First(groupId, categoryId,
				lastPostDate, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", lastPostDate=");
		msg.append(lastPostDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_L_First(long groupId, long categoryId,
		Date lastPostDate, OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_C_L(groupId, categoryId, lastPostDate, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_L_Last(long groupId, long categoryId,
		Date lastPostDate, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_L_Last(groupId, categoryId,
				lastPostDate, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", lastPostDate=");
		msg.append(lastPostDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_L_Last(long groupId, long categoryId,
		Date lastPostDate, OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_C_L(groupId, categoryId, lastPostDate);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C_L(groupId, categoryId, lastPostDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_L_PrevAndNext(long threadId, long groupId,
		long categoryId, Date lastPostDate,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_L_PrevAndNext(session, mbThread, groupId,
					categoryId, lastPostDate, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_C_L_PrevAndNext(session, mbThread, groupId,
					categoryId, lastPostDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_C_L_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, Date lastPostDate,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (bindLastPostDate) {
			qPos.add(new Timestamp(lastPostDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_L(long groupId, long categoryId,
		Date lastPostDate) {
		return filterFindByG_C_L(groupId, categoryId, lastPostDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_L(long groupId, long categoryId,
		Date lastPostDate, int start, int end) {
		return filterFindByG_C_L(groupId, categoryId, lastPostDate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_L(long groupId, long categoryId,
		Date lastPostDate, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_L(groupId, categoryId, lastPostDate, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			if (bindLastPostDate) {
				qPos.add(new Timestamp(lastPostDate.getTime()));
			}

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_L_PrevAndNext(long threadId,
		long groupId, long categoryId, Date lastPostDate,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_L_PrevAndNext(threadId, groupId, categoryId,
				lastPostDate, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_L_PrevAndNext(session, mbThread, groupId,
					categoryId, lastPostDate, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_L_PrevAndNext(session, mbThread, groupId,
					categoryId, lastPostDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_C_L_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, Date lastPostDate,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		if (bindLastPostDate) {
			qPos.add(new Timestamp(lastPostDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 */
	@Override
	public void removeByG_C_L(long groupId, long categoryId, Date lastPostDate) {
		for (MBThread mbThread : findByG_C_L(groupId, categoryId, lastPostDate,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_L(long groupId, long categoryId, Date lastPostDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_L;

		Object[] finderArgs = new Object[] { groupId, categoryId, lastPostDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				if (bindLastPostDate) {
					qPos.add(new Timestamp(lastPostDate.getTime()));
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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and lastPostDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param lastPostDate the last post date
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_L(long groupId, long categoryId,
		Date lastPostDate) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_L(groupId, categoryId, lastPostDate);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_L_CATEGORYID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			query.append(_FINDER_COLUMN_G_C_L_LASTPOSTDATE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			if (bindLastPostDate) {
				qPos.add(new Timestamp(lastPostDate.getTime()));
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

	private static final String _FINDER_COLUMN_G_C_L_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_L_CATEGORYID_2 = "mbThread.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_L_LASTPOSTDATE_1 = "mbThread.lastPostDate IS NULL";
	private static final String _FINDER_COLUMN_G_C_L_LASTPOSTDATE_2 = "mbThread.lastPostDate = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MBThreadModelImpl.GROUPID_COLUMN_BITMASK |
			MBThreadModelImpl.CATEGORYID_COLUMN_BITMASK |
			MBThreadModelImpl.STATUS_COLUMN_BITMASK |
			MBThreadModelImpl.PRIORITY_COLUMN_BITMASK |
			MBThreadModelImpl.LASTPOSTDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long categoryId, int status) {
		return findByG_C_S(groupId, categoryId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end) {
		return findByG_C_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByG_C_S(groupId, categoryId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S;
			finderArgs = new Object[] { groupId, categoryId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_S;
			finderArgs = new Object[] {
					groupId, categoryId, status,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(categoryId != mbThread.getCategoryId()) ||
							(status != mbThread.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_S_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_S_First(groupId, categoryId, status,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_S_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_C_S(groupId, categoryId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_S_Last(long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_S_Last(groupId, categoryId, status,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_S_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_C_S(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C_S(groupId, categoryId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_S_PrevAndNext(long threadId, long groupId,
		long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_S_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_C_S_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_C_S_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(long groupId, long categoryId,
		int status) {
		return filterFindByG_C_S(groupId, categoryId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(long groupId, long categoryId,
		int status, int start, int end) {
		return filterFindByG_C_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S(groupId, categoryId, status, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_S_PrevAndNext(long threadId,
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S_PrevAndNext(threadId, groupId, categoryId,
				status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_S_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_S_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_C_S_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(long groupId, long[] categoryIds,
		int status) {
		return filterFindByG_C_S(groupId, categoryIds, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(long groupId, long[] categoryIds,
		int status, int start, int end) {
		return filterFindByG_C_S(groupId, categoryIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_S(long groupId, long[] categoryIds,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_S(groupId, categoryIds, status, start, end,
				orderByComparator);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		if (categoryIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

			query.append(StringUtil.merge(categoryIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long[] categoryIds,
		int status) {
		return findByG_C_S(groupId, categoryIds, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long[] categoryIds,
		int status, int start, int end) {
		return findByG_C_S(groupId, categoryIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long[] categoryIds,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByG_C_S(groupId, categoryIds, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_S(long groupId, long[] categoryIds,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		if (categoryIds.length == 1) {
			return findByG_C_S(groupId, categoryIds[0], status, start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds), status
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds), status,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_S,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							!ArrayUtil.contains(categoryIds,
								mbThread.getCategoryId()) ||
							(status != mbThread.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			if (categoryIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

				query.append(StringUtil.merge(categoryIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_S,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_S,
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
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_S(long groupId, long categoryId, int status) {
		for (MBThread mbThread : findByG_C_S(groupId, categoryId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_S(long groupId, long categoryId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_S;

		Object[] finderArgs = new Object[] { groupId, categoryId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

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
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_S(long groupId, long[] categoryIds, int status) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds), status
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			if (categoryIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

				query.append(StringUtil.merge(categoryIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_S,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_S,
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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_S(long groupId, long categoryId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_S(groupId, categoryId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_S(long groupId, long[] categoryIds, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_S(groupId, categoryIds, status);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

		if (categoryIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_S_CATEGORYID_7);

			query.append(StringUtil.merge(categoryIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_S_STATUS_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_S_CATEGORYID_2 = "mbThread.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_S_CATEGORYID_7 = "mbThread.categoryId IN (";
	private static final String _FINDER_COLUMN_G_C_S_STATUS_2 = "mbThread.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_NOTS = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_NOTS =
		new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long categoryId,
		int status) {
		return findByG_C_NotS(groupId, categoryId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long categoryId,
		int status, int start, int end) {
		return findByG_C_NotS(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByG_C_NotS(groupId, categoryId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_NOTS;
		finderArgs = new Object[] {
				groupId, categoryId, status,
				
				start, end, orderByComparator
			};

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(categoryId != mbThread.getCategoryId()) ||
							(status == mbThread.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_NotS_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_NotS_First(groupId, categoryId, status,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_NotS_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_C_NotS(groupId, categoryId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_C_NotS_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_C_NotS_Last(groupId, categoryId, status,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_C_NotS_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_C_NotS(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_C_NotS(groupId, categoryId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_C_NotS_PrevAndNext(long threadId, long groupId,
		long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_C_NotS_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_C_NotS_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_C_NotS_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(long groupId, long categoryId,
		int status) {
		return filterFindByG_C_NotS(groupId, categoryId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(long groupId, long categoryId,
		int status, int start, int end) {
		return filterFindByG_C_NotS(groupId, categoryId, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotS(groupId, categoryId, status, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_C_NotS_PrevAndNext(long threadId,
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotS_PrevAndNext(threadId, groupId, categoryId,
				status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_C_NotS_PrevAndNext(session, mbThread,
					groupId, categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_C_NotS_PrevAndNext(session, mbThread,
					groupId, categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_C_NotS_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(long groupId,
		long[] categoryIds, int status) {
		return filterFindByG_C_NotS(groupId, categoryIds, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(long groupId,
		long[] categoryIds, int status, int start, int end) {
		return filterFindByG_C_NotS(groupId, categoryIds, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_C_NotS(long groupId,
		long[] categoryIds, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_NotS(groupId, categoryIds, status, start, end,
				orderByComparator);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		if (categoryIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

			query.append(StringUtil.merge(categoryIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long[] categoryIds,
		int status) {
		return findByG_C_NotS(groupId, categoryIds, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long[] categoryIds,
		int status, int start, int end) {
		return findByG_C_NotS(groupId, categoryIds, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long[] categoryIds,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByG_C_NotS(groupId, categoryIds, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_C_NotS(long groupId, long[] categoryIds,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		if (categoryIds.length == 1) {
			return findByG_C_NotS(groupId, categoryIds[0], status, start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds), status
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(categoryIds), status,
					
					start, end, orderByComparator
				};
		}

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_NOTS,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							!ArrayUtil.contains(categoryIds,
								mbThread.getCategoryId()) ||
							(status == mbThread.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			if (categoryIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

				query.append(StringUtil.merge(categoryIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_NOTS,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_NOTS,
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
	 * Removes all the message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_C_NotS(long groupId, long categoryId, int status) {
		for (MBThread mbThread : findByG_C_NotS(groupId, categoryId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_NotS(long groupId, long categoryId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_NOTS;

		Object[] finderArgs = new Object[] { groupId, categoryId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

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
	 * Returns the number of message boards threads where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_C_NotS(long groupId, long[] categoryIds, int status) {
		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(categoryIds), status
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_NOTS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

			if (categoryIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

				query.append(StringUtil.merge(categoryIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_NOTS,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_NOTS,
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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_NotS(long groupId, long categoryId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_NotS(groupId, categoryId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId = any &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryIds the category IDs
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_NotS(long groupId, long[] categoryIds,
		int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_NotS(groupId, categoryIds, status);
		}

		if (categoryIds == null) {
			categoryIds = new long[0];
		}
		else if (categoryIds.length > 1) {
			categoryIds = ArrayUtil.unique(categoryIds);

			Arrays.sort(categoryIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_C_NOTS_GROUPID_2);

		if (categoryIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_NOTS_CATEGORYID_7);

			query.append(StringUtil.merge(categoryIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_NOTS_STATUS_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_C_NOTS_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_NOTS_CATEGORYID_2 = "mbThread.categoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_NOTS_CATEGORYID_7 = "mbThread.categoryId IN (";
	private static final String _FINDER_COLUMN_G_C_NOTS_STATUS_2 = "mbThread.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTC_S = new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_NotC_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTC_S =
		new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotC_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(long groupId, long categoryId,
		int status) {
		return findByG_NotC_S(groupId, categoryId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(long groupId, long categoryId,
		int status, int start, int end) {
		return findByG_NotC_S(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByG_NotC_S(groupId, categoryId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTC_S;
		finderArgs = new Object[] {
				groupId, categoryId, status,
				
				start, end, orderByComparator
			};

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(categoryId == mbThread.getCategoryId()) ||
							(status != mbThread.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_S_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_NotC_S_First(groupId, categoryId, status,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_S_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_NotC_S(groupId, categoryId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_S_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_NotC_S_Last(groupId, categoryId, status,
				orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_S_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_NotC_S(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_NotC_S(groupId, categoryId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_NotC_S_PrevAndNext(long threadId, long groupId,
		long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_NotC_S_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_NotC_S_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_NotC_S_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_S(long groupId, long categoryId,
		int status) {
		return filterFindByG_NotC_S(groupId, categoryId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_S(long groupId, long categoryId,
		int status, int start, int end) {
		return filterFindByG_NotC_S(groupId, categoryId, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_S(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_S(groupId, categoryId, status, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_NotC_S_PrevAndNext(long threadId,
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_S_PrevAndNext(threadId, groupId, categoryId,
				status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_NotC_S_PrevAndNext(session, mbThread,
					groupId, categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_NotC_S_PrevAndNext(session, mbThread,
					groupId, categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_NotC_S_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_NotC_S(long groupId, long categoryId, int status) {
		for (MBThread mbThread : findByG_NotC_S(groupId, categoryId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_NotC_S(long groupId, long categoryId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTC_S;

		Object[] finderArgs = new Object[] { groupId, categoryId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotC_S(long groupId, long categoryId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotC_S(groupId, categoryId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_NOTC_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_NOTC_S_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NOTC_S_CATEGORYID_2 = "mbThread.categoryId != ? AND ";
	private static final String _FINDER_COLUMN_G_NOTC_S_STATUS_2 = "mbThread.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTC_NOTS =
		new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, MBThreadImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_NotC_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTC_NOTS =
		new FinderPath(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotC_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(long groupId, long categoryId,
		int status) {
		return findByG_NotC_NotS(groupId, categoryId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(long groupId, long categoryId,
		int status, int start, int end) {
		return findByG_NotC_NotS(groupId, categoryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findByG_NotC_NotS(groupId, categoryId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching message boards threads
	 */
	@Override
	public List<MBThread> findByG_NotC_NotS(long groupId, long categoryId,
		int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTC_NOTS;
		finderArgs = new Object[] {
				groupId, categoryId, status,
				
				start, end, orderByComparator
			};

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBThread mbThread : list) {
					if ((groupId != mbThread.getGroupId()) ||
							(categoryId == mbThread.getCategoryId()) ||
							(status == mbThread.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_NotS_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_NotC_NotS_First(groupId, categoryId,
				status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the first message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_NotS_First(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		List<MBThread> list = findByG_NotC_NotS(groupId, categoryId, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread
	 * @throws NoSuchThreadException if a matching message boards thread could not be found
	 */
	@Override
	public MBThread findByG_NotC_NotS_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByG_NotC_NotS_Last(groupId, categoryId,
				status, orderByComparator);

		if (mbThread != null) {
			return mbThread;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", categoryId=");
		msg.append(categoryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchThreadException(msg.toString());
	}

	/**
	 * Returns the last message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards thread, or <code>null</code> if a matching message boards thread could not be found
	 */
	@Override
	public MBThread fetchByG_NotC_NotS_Last(long groupId, long categoryId,
		int status, OrderByComparator<MBThread> orderByComparator) {
		int count = countByG_NotC_NotS(groupId, categoryId, status);

		if (count == 0) {
			return null;
		}

		List<MBThread> list = findByG_NotC_NotS(groupId, categoryId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] findByG_NotC_NotS_PrevAndNext(long threadId,
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = getByG_NotC_NotS_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = getByG_NotC_NotS_PrevAndNext(session, mbThread, groupId,
					categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread getByG_NotC_NotS_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

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
			query.append(MBThreadModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_NotS(long groupId,
		long categoryId, int status) {
		return filterFindByG_NotC_NotS(groupId, categoryId, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_NotS(long groupId,
		long categoryId, int status, int start, int end) {
		return filterFindByG_NotC_NotS(groupId, categoryId, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the message boards threads that the user has permissions to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards threads that the user has permission to view
	 */
	@Override
	public List<MBThread> filterFindByG_NotC_NotS(long groupId,
		long categoryId, int status, int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_NotS(groupId, categoryId, status, start, end,
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
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

			return (List<MBThread>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the message boards threads before and after the current message boards thread in the ordered set of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param threadId the primary key of the current message boards thread
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread[] filterFindByG_NotC_NotS_PrevAndNext(long threadId,
		long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator)
		throws NoSuchThreadException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotC_NotS_PrevAndNext(threadId, groupId, categoryId,
				status, orderByComparator);
		}

		MBThread mbThread = findByPrimaryKey(threadId);

		Session session = null;

		try {
			session = openSession();

			MBThread[] array = new MBThreadImpl[3];

			array[0] = filterGetByG_NotC_NotS_PrevAndNext(session, mbThread,
					groupId, categoryId, status, orderByComparator, true);

			array[1] = mbThread;

			array[2] = filterGetByG_NotC_NotS_PrevAndNext(session, mbThread,
					groupId, categoryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBThread filterGetByG_NotC_NotS_PrevAndNext(Session session,
		MBThread mbThread, long groupId, long categoryId, int status,
		OrderByComparator<MBThread> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(MBThreadModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(MBThreadModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, MBThreadImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, MBThreadImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(categoryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mbThread);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MBThread> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 */
	@Override
	public void removeByG_NotC_NotS(long groupId, long categoryId, int status) {
		for (MBThread mbThread : findByG_NotC_NotS(groupId, categoryId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads
	 */
	@Override
	public int countByG_NotC_NotS(long groupId, long categoryId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTC_NOTS;

		Object[] finderArgs = new Object[] { groupId, categoryId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MBTHREAD_WHERE);

			query.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

			query.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(categoryId);

				qPos.add(status);

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
	 * Returns the number of message boards threads that the user has permission to view where groupId = &#63; and categoryId &ne; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param categoryId the category ID
	 * @param status the status
	 * @return the number of matching message boards threads that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotC_NotS(long groupId, long categoryId,
		int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotC_NotS(groupId, categoryId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_MBTHREAD_WHERE);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2);

		query.append(_FINDER_COLUMN_G_NOTC_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				MBThread.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(categoryId);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_NOTC_NOTS_GROUPID_2 = "mbThread.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NOTC_NOTS_CATEGORYID_2 = "mbThread.categoryId != ? AND ";
	private static final String _FINDER_COLUMN_G_NOTC_NOTS_STATUS_2 = "mbThread.status != ?";

	public MBThreadPersistenceImpl() {
		setModelClass(MBThread.class);
	}

	/**
	 * Caches the message boards thread in the entity cache if it is enabled.
	 *
	 * @param mbThread the message boards thread
	 */
	@Override
	public void cacheResult(MBThread mbThread) {
		entityCache.putResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadImpl.class, mbThread.getPrimaryKey(), mbThread);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mbThread.getUuid(), mbThread.getGroupId() }, mbThread);

		finderCache.putResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID,
			new Object[] { mbThread.getRootMessageId() }, mbThread);

		mbThread.resetOriginalValues();
	}

	/**
	 * Caches the message boards threads in the entity cache if it is enabled.
	 *
	 * @param mbThreads the message boards threads
	 */
	@Override
	public void cacheResult(List<MBThread> mbThreads) {
		for (MBThread mbThread : mbThreads) {
			if (entityCache.getResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
						MBThreadImpl.class, mbThread.getPrimaryKey()) == null) {
				cacheResult(mbThread);
			}
			else {
				mbThread.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards threads.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MBThreadImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards thread.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBThread mbThread) {
		entityCache.removeResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadImpl.class, mbThread.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MBThreadModelImpl)mbThread);
	}

	@Override
	public void clearCache(List<MBThread> mbThreads) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBThread mbThread : mbThreads) {
			entityCache.removeResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
				MBThreadImpl.class, mbThread.getPrimaryKey());

			clearUniqueFindersCache((MBThreadModelImpl)mbThread);
		}
	}

	protected void cacheUniqueFindersCache(
		MBThreadModelImpl mbThreadModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					mbThreadModelImpl.getUuid(), mbThreadModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				mbThreadModelImpl);

			args = new Object[] { mbThreadModelImpl.getRootMessageId() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_ROOTMESSAGEID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID, args,
				mbThreadModelImpl);
		}
		else {
			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getUuid(),
						mbThreadModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					mbThreadModelImpl);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_ROOTMESSAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getRootMessageId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_ROOTMESSAGEID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID, args,
					mbThreadModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(MBThreadModelImpl mbThreadModelImpl) {
		Object[] args = new Object[] {
				mbThreadModelImpl.getUuid(), mbThreadModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((mbThreadModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					mbThreadModelImpl.getOriginalUuid(),
					mbThreadModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] { mbThreadModelImpl.getRootMessageId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_ROOTMESSAGEID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID, args);

		if ((mbThreadModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_ROOTMESSAGEID.getColumnBitmask()) != 0) {
			args = new Object[] { mbThreadModelImpl.getOriginalRootMessageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ROOTMESSAGEID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ROOTMESSAGEID, args);
		}
	}

	/**
	 * Creates a new message boards thread with the primary key. Does not add the message boards thread to the database.
	 *
	 * @param threadId the primary key for the new message boards thread
	 * @return the new message boards thread
	 */
	@Override
	public MBThread create(long threadId) {
		MBThread mbThread = new MBThreadImpl();

		mbThread.setNew(true);
		mbThread.setPrimaryKey(threadId);

		String uuid = PortalUUIDUtil.generate();

		mbThread.setUuid(uuid);

		mbThread.setCompanyId(companyProvider.getCompanyId());

		return mbThread;
	}

	/**
	 * Removes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread that was removed
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread remove(long threadId) throws NoSuchThreadException {
		return remove((Serializable)threadId);
	}

	/**
	 * Removes the message boards thread with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards thread
	 * @return the message boards thread that was removed
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread remove(Serializable primaryKey)
		throws NoSuchThreadException {
		Session session = null;

		try {
			session = openSession();

			MBThread mbThread = (MBThread)session.get(MBThreadImpl.class,
					primaryKey);

			if (mbThread == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchThreadException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mbThread);
		}
		catch (NoSuchThreadException nsee) {
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
	protected MBThread removeImpl(MBThread mbThread) {
		mbThread = toUnwrappedModel(mbThread);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbThread)) {
				mbThread = (MBThread)session.get(MBThreadImpl.class,
						mbThread.getPrimaryKeyObj());
			}

			if (mbThread != null) {
				session.delete(mbThread);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mbThread != null) {
			clearCache(mbThread);
		}

		return mbThread;
	}

	@Override
	public MBThread updateImpl(MBThread mbThread) {
		mbThread = toUnwrappedModel(mbThread);

		boolean isNew = mbThread.isNew();

		MBThreadModelImpl mbThreadModelImpl = (MBThreadModelImpl)mbThread;

		if (Validator.isNull(mbThread.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mbThread.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mbThread.getCreateDate() == null)) {
			if (serviceContext == null) {
				mbThread.setCreateDate(now);
			}
			else {
				mbThread.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!mbThreadModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mbThread.setModifiedDate(now);
			}
			else {
				mbThread.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (mbThread.isNew()) {
				session.save(mbThread);

				mbThread.setNew(false);
			}
			else {
				mbThread = (MBThread)session.merge(mbThread);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MBThreadModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { mbThreadModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mbThreadModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalUuid(),
						mbThreadModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mbThreadModelImpl.getUuid(),
						mbThreadModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { mbThreadModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalGroupId(),
						mbThreadModelImpl.getOriginalCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						mbThreadModelImpl.getGroupId(),
						mbThreadModelImpl.getCategoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalGroupId(),
						mbThreadModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						mbThreadModelImpl.getGroupId(),
						mbThreadModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalCategoryId(),
						mbThreadModelImpl.getOriginalPriority()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);

				args = new Object[] {
						mbThreadModelImpl.getCategoryId(),
						mbThreadModelImpl.getPriority()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalLastPostDate(),
						mbThreadModelImpl.getOriginalPriority()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P,
					args);

				args = new Object[] {
						mbThreadModelImpl.getLastPostDate(),
						mbThreadModelImpl.getPriority()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_L_P, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_L_P,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalGroupId(),
						mbThreadModelImpl.getOriginalCategoryId(),
						mbThreadModelImpl.getOriginalLastPostDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L,
					args);

				args = new Object[] {
						mbThreadModelImpl.getGroupId(),
						mbThreadModelImpl.getCategoryId(),
						mbThreadModelImpl.getLastPostDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_L,
					args);
			}

			if ((mbThreadModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mbThreadModelImpl.getOriginalGroupId(),
						mbThreadModelImpl.getOriginalCategoryId(),
						mbThreadModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S,
					args);

				args = new Object[] {
						mbThreadModelImpl.getGroupId(),
						mbThreadModelImpl.getCategoryId(),
						mbThreadModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_S,
					args);
			}
		}

		entityCache.putResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
			MBThreadImpl.class, mbThread.getPrimaryKey(), mbThread, false);

		clearUniqueFindersCache(mbThreadModelImpl);
		cacheUniqueFindersCache(mbThreadModelImpl, isNew);

		mbThread.resetOriginalValues();

		return mbThread;
	}

	protected MBThread toUnwrappedModel(MBThread mbThread) {
		if (mbThread instanceof MBThreadImpl) {
			return mbThread;
		}

		MBThreadImpl mbThreadImpl = new MBThreadImpl();

		mbThreadImpl.setNew(mbThread.isNew());
		mbThreadImpl.setPrimaryKey(mbThread.getPrimaryKey());

		mbThreadImpl.setUuid(mbThread.getUuid());
		mbThreadImpl.setThreadId(mbThread.getThreadId());
		mbThreadImpl.setGroupId(mbThread.getGroupId());
		mbThreadImpl.setCompanyId(mbThread.getCompanyId());
		mbThreadImpl.setUserId(mbThread.getUserId());
		mbThreadImpl.setUserName(mbThread.getUserName());
		mbThreadImpl.setCreateDate(mbThread.getCreateDate());
		mbThreadImpl.setModifiedDate(mbThread.getModifiedDate());
		mbThreadImpl.setCategoryId(mbThread.getCategoryId());
		mbThreadImpl.setRootMessageId(mbThread.getRootMessageId());
		mbThreadImpl.setRootMessageUserId(mbThread.getRootMessageUserId());
		mbThreadImpl.setMessageCount(mbThread.getMessageCount());
		mbThreadImpl.setViewCount(mbThread.getViewCount());
		mbThreadImpl.setLastPostByUserId(mbThread.getLastPostByUserId());
		mbThreadImpl.setLastPostDate(mbThread.getLastPostDate());
		mbThreadImpl.setPriority(mbThread.getPriority());
		mbThreadImpl.setQuestion(mbThread.isQuestion());
		mbThreadImpl.setLastPublishDate(mbThread.getLastPublishDate());
		mbThreadImpl.setStatus(mbThread.getStatus());
		mbThreadImpl.setStatusByUserId(mbThread.getStatusByUserId());
		mbThreadImpl.setStatusByUserName(mbThread.getStatusByUserName());
		mbThreadImpl.setStatusDate(mbThread.getStatusDate());

		return mbThreadImpl;
	}

	/**
	 * Returns the message boards thread with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread
	 * @return the message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread findByPrimaryKey(Serializable primaryKey)
		throws NoSuchThreadException {
		MBThread mbThread = fetchByPrimaryKey(primaryKey);

		if (mbThread == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchThreadException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread with the primary key or throws a {@link NoSuchThreadException} if it could not be found.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread
	 * @throws NoSuchThreadException if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread findByPrimaryKey(long threadId)
		throws NoSuchThreadException {
		return findByPrimaryKey((Serializable)threadId);
	}

	/**
	 * Returns the message boards thread with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards thread
	 * @return the message boards thread, or <code>null</code> if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread fetchByPrimaryKey(Serializable primaryKey) {
		MBThread mbThread = (MBThread)entityCache.getResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
				MBThreadImpl.class, primaryKey);

		if (mbThread == _nullMBThread) {
			return null;
		}

		if (mbThread == null) {
			Session session = null;

			try {
				session = openSession();

				mbThread = (MBThread)session.get(MBThreadImpl.class, primaryKey);

				if (mbThread != null) {
					cacheResult(mbThread);
				}
				else {
					entityCache.putResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
						MBThreadImpl.class, primaryKey, _nullMBThread);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
					MBThreadImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return mbThread;
	}

	/**
	 * Returns the message boards thread with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param threadId the primary key of the message boards thread
	 * @return the message boards thread, or <code>null</code> if a message boards thread with the primary key could not be found
	 */
	@Override
	public MBThread fetchByPrimaryKey(long threadId) {
		return fetchByPrimaryKey((Serializable)threadId);
	}

	@Override
	public Map<Serializable, MBThread> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBThread> map = new HashMap<Serializable, MBThread>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBThread mbThread = fetchByPrimaryKey(primaryKey);

			if (mbThread != null) {
				map.put(primaryKey, mbThread);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			MBThread mbThread = (MBThread)entityCache.getResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
					MBThreadImpl.class, primaryKey);

			if (mbThread == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, mbThread);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MBTHREAD_WHERE_PKS_IN);

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

			for (MBThread mbThread : (List<MBThread>)q.list()) {
				map.put(mbThread.getPrimaryKeyObj(), mbThread);

				cacheResult(mbThread);

				uncachedPrimaryKeys.remove(mbThread.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MBThreadModelImpl.ENTITY_CACHE_ENABLED,
					MBThreadImpl.class, primaryKey, _nullMBThread);
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
	 * Returns all the message boards threads.
	 *
	 * @return the message boards threads
	 */
	@Override
	public List<MBThread> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @return the range of message boards threads
	 */
	@Override
	public List<MBThread> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards threads
	 */
	@Override
	public List<MBThread> findAll(int start, int end,
		OrderByComparator<MBThread> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards threads.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards threads
	 * @param end the upper bound of the range of message boards threads (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of message boards threads
	 */
	@Override
	public List<MBThread> findAll(int start, int end,
		OrderByComparator<MBThread> orderByComparator, boolean retrieveFromCache) {
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

		List<MBThread> list = null;

		if (retrieveFromCache) {
			list = (List<MBThread>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MBTHREAD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MBTHREAD;

				if (pagination) {
					sql = sql.concat(MBThreadModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MBThread>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the message boards threads from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MBThread mbThread : findAll()) {
			remove(mbThread);
		}
	}

	/**
	 * Returns the number of message boards threads.
	 *
	 * @return the number of message boards threads
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MBTHREAD);

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
		return MBThreadModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the message boards thread persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MBThreadImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_MBTHREAD = "SELECT mbThread FROM MBThread mbThread";
	private static final String _SQL_SELECT_MBTHREAD_WHERE_PKS_IN = "SELECT mbThread FROM MBThread mbThread WHERE threadId IN (";
	private static final String _SQL_SELECT_MBTHREAD_WHERE = "SELECT mbThread FROM MBThread mbThread WHERE ";
	private static final String _SQL_COUNT_MBTHREAD = "SELECT COUNT(mbThread) FROM MBThread mbThread";
	private static final String _SQL_COUNT_MBTHREAD_WHERE = "SELECT COUNT(mbThread) FROM MBThread mbThread WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "mbThread.threadId";
	private static final String _FILTER_SQL_SELECT_MBTHREAD_WHERE = "SELECT DISTINCT {mbThread.*} FROM MBThread mbThread WHERE ";
	private static final String _FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {MBThread.*} FROM (SELECT DISTINCT mbThread.threadId FROM MBThread mbThread WHERE ";
	private static final String _FILTER_SQL_SELECT_MBTHREAD_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN MBThread ON TEMP_TABLE.threadId = MBThread.threadId";
	private static final String _FILTER_SQL_COUNT_MBTHREAD_WHERE = "SELECT COUNT(DISTINCT mbThread.threadId) AS COUNT_VALUE FROM MBThread mbThread WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "mbThread";
	private static final String _FILTER_ENTITY_TABLE = "MBThread";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mbThread.";
	private static final String _ORDER_BY_ENTITY_TABLE = "MBThread.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MBThread exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MBThread exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MBThreadPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final MBThread _nullMBThread = new MBThreadImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MBThread> toCacheModel() {
				return _nullMBThreadCacheModel;
			}
		};

	private static final CacheModel<MBThread> _nullMBThreadCacheModel = new CacheModel<MBThread>() {
			@Override
			public MBThread toEntityModel() {
				return _nullMBThread;
			}
		};
}