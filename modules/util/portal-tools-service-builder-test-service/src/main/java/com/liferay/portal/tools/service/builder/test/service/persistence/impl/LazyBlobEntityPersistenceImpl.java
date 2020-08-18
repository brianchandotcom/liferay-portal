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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLazyBlobEntityException;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntityTable;
import com.liferay.portal.tools.service.builder.test.model.impl.LazyBlobEntityImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LazyBlobEntityModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LazyBlobEntityPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the lazy blob entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LazyBlobEntityPersistenceImpl
	extends BasePersistenceImpl<LazyBlobEntity>
	implements LazyBlobEntityPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LazyBlobEntityUtil</code> to access the lazy blob entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LazyBlobEntityImpl.class.getName();

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
	 * Returns all the lazy blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @return the range of matching lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LazyBlobEntity> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LazyBlobEntity> orderByComparator,
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

		List<LazyBlobEntity> list = null;

		if (useFinderCache) {
			list = (List<LazyBlobEntity>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LazyBlobEntity lazyBlobEntity : list) {
					if (!uuid.equals(lazyBlobEntity.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_LAZYBLOBENTITY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LazyBlobEntityModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<LazyBlobEntity>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	@Override
	public LazyBlobEntity findByUuid_First(
			String uuid, OrderByComparator<LazyBlobEntity> orderByComparator)
		throws NoSuchLazyBlobEntityException {

		LazyBlobEntity lazyBlobEntity = fetchByUuid_First(
			uuid, orderByComparator);

		if (lazyBlobEntity != null) {
			return lazyBlobEntity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLazyBlobEntityException(sb.toString());
	}

	/**
	 * Returns the first lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	@Override
	public LazyBlobEntity fetchByUuid_First(
		String uuid, OrderByComparator<LazyBlobEntity> orderByComparator) {

		List<LazyBlobEntity> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	@Override
	public LazyBlobEntity findByUuid_Last(
			String uuid, OrderByComparator<LazyBlobEntity> orderByComparator)
		throws NoSuchLazyBlobEntityException {

		LazyBlobEntity lazyBlobEntity = fetchByUuid_Last(
			uuid, orderByComparator);

		if (lazyBlobEntity != null) {
			return lazyBlobEntity;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLazyBlobEntityException(sb.toString());
	}

	/**
	 * Returns the last lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	@Override
	public LazyBlobEntity fetchByUuid_Last(
		String uuid, OrderByComparator<LazyBlobEntity> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LazyBlobEntity> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lazy blob entities before and after the current lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param lazyBlobEntityId the primary key of the current lazy blob entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public LazyBlobEntity[] findByUuid_PrevAndNext(
			long lazyBlobEntityId, String uuid,
			OrderByComparator<LazyBlobEntity> orderByComparator)
		throws NoSuchLazyBlobEntityException {

		uuid = Objects.toString(uuid, "");

		LazyBlobEntity lazyBlobEntity = findByPrimaryKey(lazyBlobEntityId);

		Session session = null;

		try {
			session = openSession();

			LazyBlobEntity[] array = new LazyBlobEntityImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, lazyBlobEntity, uuid, orderByComparator, true);

			array[1] = lazyBlobEntity;

			array[2] = getByUuid_PrevAndNext(
				session, lazyBlobEntity, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LazyBlobEntity getByUuid_PrevAndNext(
		Session session, LazyBlobEntity lazyBlobEntity, String uuid,
		OrderByComparator<LazyBlobEntity> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LAZYBLOBENTITY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(LazyBlobEntityModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lazyBlobEntity)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LazyBlobEntity> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lazy blob entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LazyBlobEntity lazyBlobEntity :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lazyBlobEntity);
		}
	}

	/**
	 * Returns the number of lazy blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lazy blob entities
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAZYBLOBENTITY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"lazyBlobEntity.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(lazyBlobEntity.uuid IS NULL OR lazyBlobEntity.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLazyBlobEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	@Override
	public LazyBlobEntity findByUUID_G(String uuid, long groupId)
		throws NoSuchLazyBlobEntityException {

		LazyBlobEntity lazyBlobEntity = fetchByUUID_G(uuid, groupId);

		if (lazyBlobEntity == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLazyBlobEntityException(sb.toString());
		}

		return lazyBlobEntity;
	}

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	@Override
	public LazyBlobEntity fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	@Override
	public LazyBlobEntity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof LazyBlobEntity) {
			LazyBlobEntity lazyBlobEntity = (LazyBlobEntity)result;

			if (!Objects.equals(uuid, lazyBlobEntity.getUuid()) ||
				(groupId != lazyBlobEntity.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAZYBLOBENTITY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<LazyBlobEntity> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					LazyBlobEntity lazyBlobEntity = list.get(0);

					result = lazyBlobEntity;

					cacheResult(lazyBlobEntity);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LazyBlobEntity)result;
		}
	}

	/**
	 * Removes the lazy blob entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the lazy blob entity that was removed
	 */
	@Override
	public LazyBlobEntity removeByUUID_G(String uuid, long groupId)
		throws NoSuchLazyBlobEntityException {

		LazyBlobEntity lazyBlobEntity = findByUUID_G(uuid, groupId);

		return remove(lazyBlobEntity);
	}

	/**
	 * Returns the number of lazy blob entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lazy blob entities
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAZYBLOBENTITY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"lazyBlobEntity.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(lazyBlobEntity.uuid IS NULL OR lazyBlobEntity.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"lazyBlobEntity.groupId = ?";

	public LazyBlobEntityPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LazyBlobEntity.class);

		setModelImplClass(LazyBlobEntityImpl.class);
		setModelPKClass(long.class);

		setTable(LazyBlobEntityTable.INSTANCE);
	}

	/**
	 * Caches the lazy blob entity in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 */
	@Override
	public void cacheResult(LazyBlobEntity lazyBlobEntity) {
		entityCache.putResult(
			LazyBlobEntityImpl.class, lazyBlobEntity.getPrimaryKey(),
			lazyBlobEntity);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				lazyBlobEntity.getUuid(), lazyBlobEntity.getGroupId()
			},
			lazyBlobEntity);

		lazyBlobEntity.resetOriginalValues();
	}

	/**
	 * Caches the lazy blob entities in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntities the lazy blob entities
	 */
	@Override
	public void cacheResult(List<LazyBlobEntity> lazyBlobEntities) {
		for (LazyBlobEntity lazyBlobEntity : lazyBlobEntities) {
			if (entityCache.getResult(
					LazyBlobEntityImpl.class, lazyBlobEntity.getPrimaryKey()) ==
						null) {

				cacheResult(lazyBlobEntity);
			}
			else {
				lazyBlobEntity.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lazy blob entities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LazyBlobEntityImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lazy blob entity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LazyBlobEntity lazyBlobEntity) {
		entityCache.removeResult(
			LazyBlobEntityImpl.class, lazyBlobEntity.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LazyBlobEntityModelImpl)lazyBlobEntity, true);
	}

	@Override
	public void clearCache(List<LazyBlobEntity> lazyBlobEntities) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LazyBlobEntity lazyBlobEntity : lazyBlobEntities) {
			entityCache.removeResult(
				LazyBlobEntityImpl.class, lazyBlobEntity.getPrimaryKey());

			clearUniqueFindersCache(
				(LazyBlobEntityModelImpl)lazyBlobEntity, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LazyBlobEntityImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LazyBlobEntityModelImpl lazyBlobEntityModelImpl) {

		Object[] args = new Object[] {
			lazyBlobEntityModelImpl.getUuid(),
			lazyBlobEntityModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, lazyBlobEntityModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LazyBlobEntityModelImpl lazyBlobEntityModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				lazyBlobEntityModelImpl.getUuid(),
				lazyBlobEntityModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((lazyBlobEntityModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				lazyBlobEntityModelImpl.getOriginalUuid(),
				lazyBlobEntityModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new lazy blob entity with the primary key. Does not add the lazy blob entity to the database.
	 *
	 * @param lazyBlobEntityId the primary key for the new lazy blob entity
	 * @return the new lazy blob entity
	 */
	@Override
	public LazyBlobEntity create(long lazyBlobEntityId) {
		LazyBlobEntity lazyBlobEntity = new LazyBlobEntityImpl();

		lazyBlobEntity.setNew(true);
		lazyBlobEntity.setPrimaryKey(lazyBlobEntityId);

		String uuid = PortalUUIDUtil.generate();

		lazyBlobEntity.setUuid(uuid);

		return lazyBlobEntity;
	}

	/**
	 * Removes the lazy blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity that was removed
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public LazyBlobEntity remove(long lazyBlobEntityId)
		throws NoSuchLazyBlobEntityException {

		return remove((Serializable)lazyBlobEntityId);
	}

	/**
	 * Removes the lazy blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lazy blob entity
	 * @return the lazy blob entity that was removed
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public LazyBlobEntity remove(Serializable primaryKey)
		throws NoSuchLazyBlobEntityException {

		Session session = null;

		try {
			session = openSession();

			LazyBlobEntity lazyBlobEntity = (LazyBlobEntity)session.get(
				LazyBlobEntityImpl.class, primaryKey);

			if (lazyBlobEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLazyBlobEntityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(lazyBlobEntity);
		}
		catch (NoSuchLazyBlobEntityException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LazyBlobEntity removeImpl(LazyBlobEntity lazyBlobEntity) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lazyBlobEntity)) {
				lazyBlobEntity = (LazyBlobEntity)session.get(
					LazyBlobEntityImpl.class,
					lazyBlobEntity.getPrimaryKeyObj());
			}

			if (lazyBlobEntity != null) {
				session.delete(lazyBlobEntity);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (lazyBlobEntity != null) {
			clearCache(lazyBlobEntity);
		}

		return lazyBlobEntity;
	}

	@Override
	public LazyBlobEntity updateImpl(LazyBlobEntity lazyBlobEntity) {
		boolean isNew = lazyBlobEntity.isNew();

		if (!(lazyBlobEntity instanceof LazyBlobEntityModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lazyBlobEntity.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					lazyBlobEntity);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lazyBlobEntity proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LazyBlobEntity implementation " +
					lazyBlobEntity.getClass());
		}

		LazyBlobEntityModelImpl lazyBlobEntityModelImpl =
			(LazyBlobEntityModelImpl)lazyBlobEntity;

		if (Validator.isNull(lazyBlobEntity.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			lazyBlobEntity.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (lazyBlobEntity.isNew()) {
				session.save(lazyBlobEntity);

				lazyBlobEntity.setNew(false);
			}
			else {
				session.evict(
					LazyBlobEntityImpl.class,
					lazyBlobEntity.getPrimaryKeyObj());

				session.saveOrUpdate(lazyBlobEntity);
			}

			session.flush();
			session.clear();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {lazyBlobEntityModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((lazyBlobEntityModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					lazyBlobEntityModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {lazyBlobEntityModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}
		}

		entityCache.putResult(
			LazyBlobEntityImpl.class, lazyBlobEntity.getPrimaryKey(),
			lazyBlobEntity, false);

		clearUniqueFindersCache(lazyBlobEntityModelImpl, false);
		cacheUniqueFindersCache(lazyBlobEntityModelImpl);

		lazyBlobEntity.resetOriginalValues();

		return lazyBlobEntity;
	}

	/**
	 * Returns the lazy blob entity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lazy blob entity
	 * @return the lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public LazyBlobEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLazyBlobEntityException {

		LazyBlobEntity lazyBlobEntity = fetchByPrimaryKey(primaryKey);

		if (lazyBlobEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLazyBlobEntityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return lazyBlobEntity;
	}

	/**
	 * Returns the lazy blob entity with the primary key or throws a <code>NoSuchLazyBlobEntityException</code> if it could not be found.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public LazyBlobEntity findByPrimaryKey(long lazyBlobEntityId)
		throws NoSuchLazyBlobEntityException {

		return findByPrimaryKey((Serializable)lazyBlobEntityId);
	}

	/**
	 * Returns the lazy blob entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity, or <code>null</code> if a lazy blob entity with the primary key could not be found
	 */
	@Override
	public LazyBlobEntity fetchByPrimaryKey(long lazyBlobEntityId) {
		return fetchByPrimaryKey((Serializable)lazyBlobEntityId);
	}

	/**
	 * Returns all the lazy blob entities.
	 *
	 * @return the lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @return the range of lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findAll(
		int start, int end,
		OrderByComparator<LazyBlobEntity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lazy blob entities
	 */
	@Override
	public List<LazyBlobEntity> findAll(
		int start, int end, OrderByComparator<LazyBlobEntity> orderByComparator,
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

		List<LazyBlobEntity> list = null;

		if (useFinderCache) {
			list = (List<LazyBlobEntity>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAZYBLOBENTITY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAZYBLOBENTITY;

				sql = sql.concat(LazyBlobEntityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LazyBlobEntity>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the lazy blob entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LazyBlobEntity lazyBlobEntity : findAll()) {
			remove(lazyBlobEntity);
		}
	}

	/**
	 * Returns the number of lazy blob entities.
	 *
	 * @return the number of lazy blob entities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LAZYBLOBENTITY);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
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
		return "lazyBlobEntityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAZYBLOBENTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LazyBlobEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lazy blob entity persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LazyBlobEntityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LazyBlobEntityImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			LazyBlobEntityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			LazyBlobEntityImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			LazyBlobEntityModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			LazyBlobEntityImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LazyBlobEntityModelImpl.UUID_COLUMN_BITMASK |
			LazyBlobEntityModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(LazyBlobEntityImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LAZYBLOBENTITY =
		"SELECT lazyBlobEntity FROM LazyBlobEntity lazyBlobEntity";

	private static final String _SQL_SELECT_LAZYBLOBENTITY_WHERE =
		"SELECT lazyBlobEntity FROM LazyBlobEntity lazyBlobEntity WHERE ";

	private static final String _SQL_COUNT_LAZYBLOBENTITY =
		"SELECT COUNT(lazyBlobEntity) FROM LazyBlobEntity lazyBlobEntity";

	private static final String _SQL_COUNT_LAZYBLOBENTITY_WHERE =
		"SELECT COUNT(lazyBlobEntity) FROM LazyBlobEntity lazyBlobEntity WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "lazyBlobEntity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LazyBlobEntity exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LazyBlobEntity exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LazyBlobEntityPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}