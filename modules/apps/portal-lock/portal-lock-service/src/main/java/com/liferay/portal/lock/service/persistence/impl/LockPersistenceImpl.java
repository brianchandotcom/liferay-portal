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

package com.liferay.portal.lock.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.lock.exception.NoSuchLockException;
import com.liferay.portal.lock.model.Lock;
import com.liferay.portal.lock.model.impl.LockImpl;
import com.liferay.portal.lock.model.impl.LockModelImpl;
import com.liferay.portal.lock.service.persistence.LockPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the lock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LockPersistence
 * @see com.liferay.portal.lock.service.persistence.LockUtil
 * @generated
 */
@ProviderType
public class LockPersistenceImpl extends BasePersistenceImpl<Lock>
	implements LockPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LockUtil} to access the lock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LockImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the locks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching locks
	 */
	@Override
	public List<Lock> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of matching locks
	 */
	@Override
	public List<Lock> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching locks
	 */
	@Override
	public List<Lock> findByUuid(String uuid, int start, int end,
		OrderByComparator<Lock> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the locks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching locks
	 */
	@Override
	public List<Lock> findByUuid(String uuid, int start, int end,
		OrderByComparator<Lock> orderByComparator, boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<Lock> list = null;

		if (retrieveFromCache) {
			list = (List<Lock>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (Lock lock : list) {
					if (!uuid.equals(lock.getUuid())) {
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

			query.append(_SQL_SELECT_LOCK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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
				query.append(LockModelImpl.ORDER_BY_JPQL);
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
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock
	 * @throws NoSuchLockException if a matching lock could not be found
	 */
	@Override
	public Lock findByUuid_First(String uuid,
		OrderByComparator<Lock> orderByComparator) throws NoSuchLockException {
		Lock lock = fetchByUuid_First(uuid, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the first lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByUuid_First(String uuid,
		OrderByComparator<Lock> orderByComparator) {
		List<Lock> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock
	 * @throws NoSuchLockException if a matching lock could not be found
	 */
	@Override
	public Lock findByUuid_Last(String uuid,
		OrderByComparator<Lock> orderByComparator) throws NoSuchLockException {
		Lock lock = fetchByUuid_Last(uuid, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByUuid_Last(String uuid,
		OrderByComparator<Lock> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<Lock> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the locks before and after the current lock in the ordered set where uuid = &#63;.
	 *
	 * @param lockId the primary key of the current lock
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lock
	 * @throws NoSuchLockException if a lock with the primary key could not be found
	 */
	@Override
	public Lock[] findByUuid_PrevAndNext(long lockId, String uuid,
		OrderByComparator<Lock> orderByComparator) throws NoSuchLockException {
		uuid = Objects.toString(uuid, "");

		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByUuid_PrevAndNext(session, lock, uuid,
					orderByComparator, true);

			array[1] = lock;

			array[2] = getByUuid_PrevAndNext(session, lock, uuid,
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

	protected Lock getByUuid_PrevAndNext(Session session, Lock lock,
		String uuid, OrderByComparator<Lock> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
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
			query.append(LockModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					lock)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the locks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (Lock lock : findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(lock);
		}
	}

	/**
	 * Returns the number of locks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching locks
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LOCK_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "lock.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(lock.uuid IS NULL OR lock.uuid = '')";
	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the locks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching locks
	 */
	@Override
	public List<Lock> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of matching locks
	 */
	@Override
	public List<Lock> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching locks
	 */
	@Override
	public List<Lock> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<Lock> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the locks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching locks
	 */
	@Override
	public List<Lock> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<Lock> orderByComparator,
		boolean retrieveFromCache) {
		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<Lock> list = null;

		if (retrieveFromCache) {
			list = (List<Lock>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (Lock lock : list) {
					if (!uuid.equals(lock.getUuid()) ||
							(companyId != lock.getCompanyId())) {
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

			query.append(_SQL_SELECT_LOCK_WHERE);

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
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LockModelImpl.ORDER_BY_JPQL);
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
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock
	 * @throws NoSuchLockException if a matching lock could not be found
	 */
	@Override
	public Lock findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<Lock> orderByComparator) throws NoSuchLockException {
		Lock lock = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the first lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<Lock> orderByComparator) {
		List<Lock> list = findByUuid_C(uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock
	 * @throws NoSuchLockException if a matching lock could not be found
	 */
	@Override
	public Lock findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<Lock> orderByComparator) throws NoSuchLockException {
		Lock lock = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the last lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<Lock> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<Lock> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the locks before and after the current lock in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param lockId the primary key of the current lock
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lock
	 * @throws NoSuchLockException if a lock with the primary key could not be found
	 */
	@Override
	public Lock[] findByUuid_C_PrevAndNext(long lockId, String uuid,
		long companyId, OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException {
		uuid = Objects.toString(uuid, "");

		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, lock, uuid, companyId,
					orderByComparator, true);

			array[1] = lock;

			array[2] = getByUuid_C_PrevAndNext(session, lock, uuid, companyId,
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

	protected Lock getByUuid_C_PrevAndNext(Session session, Lock lock,
		String uuid, long companyId, OrderByComparator<Lock> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

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
			query.append(LockModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					lock)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the locks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (Lock lock : findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(lock);
		}
	}

	/**
	 * Returns the number of locks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching locks
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LOCK_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "lock.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(lock.uuid IS NULL OR lock.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "lock.companyId = ?";
	private FinderPath _finderPathWithPaginationFindByLtExpirationDate;
	private FinderPath _finderPathWithPaginationCountByLtExpirationDate;

	/**
	 * Returns all the locks where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching locks
	 */
	@Override
	public List<Lock> findByLtExpirationDate(Date expirationDate) {
		return findByLtExpirationDate(expirationDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of matching locks
	 */
	@Override
	public List<Lock> findByLtExpirationDate(Date expirationDate, int start,
		int end) {
		return findByLtExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching locks
	 */
	@Override
	public List<Lock> findByLtExpirationDate(Date expirationDate, int start,
		int end, OrderByComparator<Lock> orderByComparator) {
		return findByLtExpirationDate(expirationDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the locks where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching locks
	 */
	@Override
	public List<Lock> findByLtExpirationDate(Date expirationDate, int start,
		int end, OrderByComparator<Lock> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtExpirationDate;
		finderArgs = new Object[] {
				_getTime(expirationDate),
				
				start, end, orderByComparator
			};

		List<Lock> list = null;

		if (retrieveFromCache) {
			list = (List<Lock>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (Lock lock : list) {
					if ((expirationDate.getTime() <= lock.getExpirationDate()
															 .getTime())) {
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

			query.append(_SQL_SELECT_LOCK_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LockModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExpirationDate) {
					qPos.add(new Timestamp(expirationDate.getTime()));
				}

				if (!pagination) {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock
	 * @throws NoSuchLockException if a matching lock could not be found
	 */
	@Override
	public Lock findByLtExpirationDate_First(Date expirationDate,
		OrderByComparator<Lock> orderByComparator) throws NoSuchLockException {
		Lock lock = fetchByLtExpirationDate_First(expirationDate,
				orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append("}");

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the first lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByLtExpirationDate_First(Date expirationDate,
		OrderByComparator<Lock> orderByComparator) {
		List<Lock> list = findByLtExpirationDate(expirationDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock
	 * @throws NoSuchLockException if a matching lock could not be found
	 */
	@Override
	public Lock findByLtExpirationDate_Last(Date expirationDate,
		OrderByComparator<Lock> orderByComparator) throws NoSuchLockException {
		Lock lock = fetchByLtExpirationDate_Last(expirationDate,
				orderByComparator);

		if (lock != null) {
			return lock;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append("}");

		throw new NoSuchLockException(msg.toString());
	}

	/**
	 * Returns the last lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByLtExpirationDate_Last(Date expirationDate,
		OrderByComparator<Lock> orderByComparator) {
		int count = countByLtExpirationDate(expirationDate);

		if (count == 0) {
			return null;
		}

		List<Lock> list = findByLtExpirationDate(expirationDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the locks before and after the current lock in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param lockId the primary key of the current lock
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lock
	 * @throws NoSuchLockException if a lock with the primary key could not be found
	 */
	@Override
	public Lock[] findByLtExpirationDate_PrevAndNext(long lockId,
		Date expirationDate, OrderByComparator<Lock> orderByComparator)
		throws NoSuchLockException {
		Lock lock = findByPrimaryKey(lockId);

		Session session = null;

		try {
			session = openSession();

			Lock[] array = new LockImpl[3];

			array[0] = getByLtExpirationDate_PrevAndNext(session, lock,
					expirationDate, orderByComparator, true);

			array[1] = lock;

			array[2] = getByLtExpirationDate_PrevAndNext(session, lock,
					expirationDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Lock getByLtExpirationDate_PrevAndNext(Session session,
		Lock lock, Date expirationDate,
		OrderByComparator<Lock> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LOCK_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
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
			query.append(LockModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindExpirationDate) {
			qPos.add(new Timestamp(expirationDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					lock)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<Lock> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the locks where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByLtExpirationDate(Date expirationDate) {
		for (Lock lock : findByLtExpirationDate(expirationDate,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lock);
		}
	}

	/**
	 * Returns the number of locks where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching locks
	 */
	@Override
	public int countByLtExpirationDate(Date expirationDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtExpirationDate;

		Object[] finderArgs = new Object[] { _getTime(expirationDate) };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LOCK_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				query.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExpirationDate) {
					qPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1 =
		"lock.expirationDate IS NULL";
	private static final String _FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2 =
		"lock.expirationDate < ?";
	private FinderPath _finderPathFetchByC_K;
	private FinderPath _finderPathCountByC_K;

	/**
	 * Returns the lock where className = &#63; and key = &#63; or throws a {@link NoSuchLockException} if it could not be found.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the matching lock
	 * @throws NoSuchLockException if a matching lock could not be found
	 */
	@Override
	public Lock findByC_K(String className, String key)
		throws NoSuchLockException {
		Lock lock = fetchByC_K(className, key);

		if (lock == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("className=");
			msg.append(className);

			msg.append(", key=");
			msg.append(key);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLockException(msg.toString());
		}

		return lock;
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByC_K(String className, String key) {
		return fetchByC_K(className, key, true);
	}

	/**
	 * Returns the lock where className = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param className the class name
	 * @param key the key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching lock, or <code>null</code> if a matching lock could not be found
	 */
	@Override
	public Lock fetchByC_K(String className, String key,
		boolean retrieveFromCache) {
		className = Objects.toString(className, "");
		key = Objects.toString(key, "");

		Object[] finderArgs = new Object[] { className, key };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(_finderPathFetchByC_K, finderArgs,
					this);
		}

		if (result instanceof Lock) {
			Lock lock = (Lock)result;

			if (!Objects.equals(className, lock.getClassName()) ||
					!Objects.equals(key, lock.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LOCK_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				query.append(_FINDER_COLUMN_C_K_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				query.append(_FINDER_COLUMN_C_K_CLASSNAME_2);
			}

			boolean bindKey = false;

			if (key.isEmpty()) {
				query.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindClassName) {
					qPos.add(className);
				}

				if (bindKey) {
					qPos.add(key);
				}

				List<Lock> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(_finderPathFetchByC_K, finderArgs,
						list);
				}
				else {
					Lock lock = list.get(0);

					result = lock;

					cacheResult(lock);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByC_K, finderArgs);

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
			return (Lock)result;
		}
	}

	/**
	 * Removes the lock where className = &#63; and key = &#63; from the database.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the lock that was removed
	 */
	@Override
	public Lock removeByC_K(String className, String key)
		throws NoSuchLockException {
		Lock lock = findByC_K(className, key);

		return remove(lock);
	}

	/**
	 * Returns the number of locks where className = &#63; and key = &#63;.
	 *
	 * @param className the class name
	 * @param key the key
	 * @return the number of matching locks
	 */
	@Override
	public int countByC_K(String className, String key) {
		className = Objects.toString(className, "");
		key = Objects.toString(key, "");

		FinderPath finderPath = _finderPathCountByC_K;

		Object[] finderArgs = new Object[] { className, key };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LOCK_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				query.append(_FINDER_COLUMN_C_K_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				query.append(_FINDER_COLUMN_C_K_CLASSNAME_2);
			}

			boolean bindKey = false;

			if (key.isEmpty()) {
				query.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindClassName) {
					qPos.add(className);
				}

				if (bindKey) {
					qPos.add(key);
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

	private static final String _FINDER_COLUMN_C_K_CLASSNAME_2 = "lock.className = ? AND ";
	private static final String _FINDER_COLUMN_C_K_CLASSNAME_3 = "(lock.className IS NULL OR lock.className = '') AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "lock.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(lock.key IS NULL OR lock.key = '')";

	public LockPersistenceImpl() {
		setModelClass(Lock.class);

		setModelImplClass(LockImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LockModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the lock in the entity cache if it is enabled.
	 *
	 * @param lock the lock
	 */
	@Override
	public void cacheResult(Lock lock) {
		entityCache.putResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey(), lock);

		finderCache.putResult(_finderPathFetchByC_K,
			new Object[] { lock.getClassName(), lock.getKey() }, lock);

		lock.resetOriginalValues();
	}

	/**
	 * Caches the locks in the entity cache if it is enabled.
	 *
	 * @param locks the locks
	 */
	@Override
	public void cacheResult(List<Lock> locks) {
		for (Lock lock : locks) {
			if (entityCache.getResult(LockModelImpl.ENTITY_CACHE_ENABLED,
						LockImpl.class, lock.getPrimaryKey()) == null) {
				cacheResult(lock);
			}
			else {
				lock.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all locks.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LockImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lock.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Lock lock) {
		entityCache.removeResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LockModelImpl)lock, true);
	}

	@Override
	public void clearCache(List<Lock> locks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Lock lock : locks) {
			entityCache.removeResult(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockImpl.class, lock.getPrimaryKey());

			clearUniqueFindersCache((LockModelImpl)lock, true);
		}
	}

	protected void cacheUniqueFindersCache(LockModelImpl lockModelImpl) {
		Object[] args = new Object[] {
				lockModelImpl.getClassName(), lockModelImpl.getKey()
			};

		finderCache.putResult(_finderPathCountByC_K, args, Long.valueOf(1),
			false);
		finderCache.putResult(_finderPathFetchByC_K, args, lockModelImpl, false);
	}

	protected void clearUniqueFindersCache(LockModelImpl lockModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					lockModelImpl.getClassName(), lockModelImpl.getKey()
				};

			finderCache.removeResult(_finderPathCountByC_K, args);
			finderCache.removeResult(_finderPathFetchByC_K, args);
		}

		if ((lockModelImpl.getColumnBitmask() &
				_finderPathFetchByC_K.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					lockModelImpl.getOriginalClassName(),
					lockModelImpl.getOriginalKey()
				};

			finderCache.removeResult(_finderPathCountByC_K, args);
			finderCache.removeResult(_finderPathFetchByC_K, args);
		}
	}

	/**
	 * Creates a new lock with the primary key. Does not add the lock to the database.
	 *
	 * @param lockId the primary key for the new lock
	 * @return the new lock
	 */
	@Override
	public Lock create(long lockId) {
		Lock lock = new LockImpl();

		lock.setNew(true);
		lock.setPrimaryKey(lockId);

		String uuid = PortalUUIDUtil.generate();

		lock.setUuid(uuid);

		lock.setCompanyId(companyProvider.getCompanyId());

		return lock;
	}

	/**
	 * Removes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock that was removed
	 * @throws NoSuchLockException if a lock with the primary key could not be found
	 */
	@Override
	public Lock remove(long lockId) throws NoSuchLockException {
		return remove((Serializable)lockId);
	}

	/**
	 * Removes the lock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lock
	 * @return the lock that was removed
	 * @throws NoSuchLockException if a lock with the primary key could not be found
	 */
	@Override
	public Lock remove(Serializable primaryKey) throws NoSuchLockException {
		Session session = null;

		try {
			session = openSession();

			Lock lock = (Lock)session.get(LockImpl.class, primaryKey);

			if (lock == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(lock);
		}
		catch (NoSuchLockException nsee) {
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
	protected Lock removeImpl(Lock lock) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lock)) {
				lock = (Lock)session.get(LockImpl.class, lock.getPrimaryKeyObj());
			}

			if (lock != null) {
				session.delete(lock);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (lock != null) {
			clearCache(lock);
		}

		return lock;
	}

	@Override
	public Lock updateImpl(Lock lock) {
		boolean isNew = lock.isNew();

		if (!(lock instanceof LockModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lock.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(lock);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lock proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Lock implementation " +
				lock.getClass());
		}

		LockModelImpl lockModelImpl = (LockModelImpl)lock;

		if (Validator.isNull(lock.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			lock.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (lock.isNew()) {
				session.save(lock);

				lock.setNew(false);
			}
			else {
				lock = (Lock)session.merge(lock);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LockModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { lockModelImpl.getUuid() };

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByUuid,
				args);

			args = new Object[] {
					lockModelImpl.getUuid(), lockModelImpl.getCompanyId()
				};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByUuid_C,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((lockModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByUuid.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { lockModelImpl.getOriginalUuid() };

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid,
					args);

				args = new Object[] { lockModelImpl.getUuid() };

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid,
					args);
			}

			if ((lockModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lockModelImpl.getOriginalUuid(),
						lockModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid_C,
					args);

				args = new Object[] {
						lockModelImpl.getUuid(), lockModelImpl.getCompanyId()
					};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByUuid_C,
					args);
			}
		}

		entityCache.putResult(LockModelImpl.ENTITY_CACHE_ENABLED,
			LockImpl.class, lock.getPrimaryKey(), lock, false);

		clearUniqueFindersCache(lockModelImpl, false);
		cacheUniqueFindersCache(lockModelImpl);

		lock.resetOriginalValues();

		return lock;
	}

	/**
	 * Returns the lock with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the lock
	 * @return the lock
	 * @throws NoSuchLockException if a lock with the primary key could not be found
	 */
	@Override
	public Lock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLockException {
		Lock lock = fetchByPrimaryKey(primaryKey);

		if (lock == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLockException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return lock;
	}

	/**
	 * Returns the lock with the primary key or throws a {@link NoSuchLockException} if it could not be found.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock
	 * @throws NoSuchLockException if a lock with the primary key could not be found
	 */
	@Override
	public Lock findByPrimaryKey(long lockId) throws NoSuchLockException {
		return findByPrimaryKey((Serializable)lockId);
	}

	/**
	 * Returns the lock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lockId the primary key of the lock
	 * @return the lock, or <code>null</code> if a lock with the primary key could not be found
	 */
	@Override
	public Lock fetchByPrimaryKey(long lockId) {
		return fetchByPrimaryKey((Serializable)lockId);
	}

	/**
	 * Returns all the locks.
	 *
	 * @return the locks
	 */
	@Override
	public List<Lock> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @return the range of locks
	 */
	@Override
	public List<Lock> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of locks
	 */
	@Override
	public List<Lock> findAll(int start, int end,
		OrderByComparator<Lock> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the locks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LockModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of locks
	 * @param end the upper bound of the range of locks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of locks
	 */
	@Override
	public List<Lock> findAll(int start, int end,
		OrderByComparator<Lock> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Lock> list = null;

		if (retrieveFromCache) {
			list = (List<Lock>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LOCK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LOCK;

				if (pagination) {
					sql = sql.concat(LockModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Lock>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the locks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Lock lock : findAll()) {
			remove(lock);
		}
	}

	/**
	 * Returns the number of locks.
	 *
	 * @return the number of locks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LOCK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);

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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "lockId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LOCK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LockModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lock persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
				new String[] {
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
				new String[] { String.class.getName() },
				LockModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
				new String[] { String.class.getName() });

		_finderPathWithPaginationFindByUuid_C = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
				new String[] {
					String.class.getName(), Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
				new String[] { String.class.getName(), Long.class.getName() },
				LockModelImpl.UUID_COLUMN_BITMASK |
				LockModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
				new String[] { String.class.getName(), Long.class.getName() });

		_finderPathWithPaginationFindByLtExpirationDate = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByLtExpirationDate",
				new String[] {
					Date.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithPaginationCountByLtExpirationDate = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"countByLtExpirationDate", new String[] { Date.class.getName() });

		_finderPathFetchByC_K = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, LockImpl.class,
				FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
				new String[] { String.class.getName(), String.class.getName() },
				LockModelImpl.CLASSNAME_COLUMN_BITMASK |
				LockModelImpl.KEY_COLUMN_BITMASK);

		_finderPathCountByC_K = new FinderPath(LockModelImpl.ENTITY_CACHE_ENABLED,
				LockModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
				new String[] { String.class.getName(), String.class.getName() });
	}

	public void destroy() {
		entityCache.removeCache(LockImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_LOCK = "SELECT lock FROM Lock lock";
	private static final String _SQL_SELECT_LOCK_WHERE = "SELECT lock FROM Lock lock WHERE ";
	private static final String _SQL_COUNT_LOCK = "SELECT COUNT(lock) FROM Lock lock";
	private static final String _SQL_COUNT_LOCK_WHERE = "SELECT COUNT(lock) FROM Lock lock WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lock.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Lock exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Lock exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LockPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "key"
			});
}