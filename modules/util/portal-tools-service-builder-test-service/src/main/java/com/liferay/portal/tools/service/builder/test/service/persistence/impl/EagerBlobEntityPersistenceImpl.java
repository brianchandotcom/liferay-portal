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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchEagerBlobEntityException;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity;
import com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.EagerBlobEntityModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.EagerBlobEntityPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the eager blob entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EagerBlobEntityPersistenceImpl
	extends BasePersistenceImpl<EagerBlobEntity>
	implements EagerBlobEntityPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>EagerBlobEntityUtil</code> to access the eager blob entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		EagerBlobEntityImpl.class.getName();

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
	 * Returns all the eager blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of matching eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator,
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

		List<EagerBlobEntity> list = null;

		if (useFinderCache) {
			list = (List<EagerBlobEntity>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (EagerBlobEntity eagerBlobEntity : list) {
					if (!uuid.equals(eagerBlobEntity.getUuid())) {
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

			query.append(_SQL_SELECT_EAGERBLOBENTITY_WHERE);

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
				query.append(EagerBlobEntityModelImpl.ORDER_BY_JPQL);
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

				list = (List<EagerBlobEntity>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity findByUuid_First(
			String uuid, OrderByComparator<EagerBlobEntity> orderByComparator)
		throws NoSuchEagerBlobEntityException {

		EagerBlobEntity eagerBlobEntity = fetchByUuid_First(
			uuid, orderByComparator);

		if (eagerBlobEntity != null) {
			return eagerBlobEntity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEagerBlobEntityException(msg.toString());
	}

	/**
	 * Returns the first eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity fetchByUuid_First(
		String uuid, OrderByComparator<EagerBlobEntity> orderByComparator) {

		List<EagerBlobEntity> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity findByUuid_Last(
			String uuid, OrderByComparator<EagerBlobEntity> orderByComparator)
		throws NoSuchEagerBlobEntityException {

		EagerBlobEntity eagerBlobEntity = fetchByUuid_Last(
			uuid, orderByComparator);

		if (eagerBlobEntity != null) {
			return eagerBlobEntity;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEagerBlobEntityException(msg.toString());
	}

	/**
	 * Returns the last eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity fetchByUuid_Last(
		String uuid, OrderByComparator<EagerBlobEntity> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<EagerBlobEntity> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the eager blob entities before and after the current eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param eagerBlobEntityId the primary key of the current eager blob entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	@Override
	public EagerBlobEntity[] findByUuid_PrevAndNext(
			long eagerBlobEntityId, String uuid,
			OrderByComparator<EagerBlobEntity> orderByComparator)
		throws NoSuchEagerBlobEntityException {

		uuid = Objects.toString(uuid, "");

		EagerBlobEntity eagerBlobEntity = findByPrimaryKey(eagerBlobEntityId);

		Session session = null;

		try {
			session = openSession();

			EagerBlobEntity[] array = new EagerBlobEntityImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, eagerBlobEntity, uuid, orderByComparator, true);

			array[1] = eagerBlobEntity;

			array[2] = getByUuid_PrevAndNext(
				session, eagerBlobEntity, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected EagerBlobEntity getByUuid_PrevAndNext(
		Session session, EagerBlobEntity eagerBlobEntity, String uuid,
		OrderByComparator<EagerBlobEntity> orderByComparator,
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

		query.append(_SQL_SELECT_EAGERBLOBENTITY_WHERE);

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
			query.append(EagerBlobEntityModelImpl.ORDER_BY_JPQL);
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
						eagerBlobEntity)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<EagerBlobEntity> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the eager blob entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (EagerBlobEntity eagerBlobEntity :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(eagerBlobEntity);
		}
	}

	/**
	 * Returns the number of eager blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching eager blob entities
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EAGERBLOBENTITY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"eagerBlobEntity.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(eagerBlobEntity.uuid IS NULL OR eagerBlobEntity.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEagerBlobEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity findByUUID_G(String uuid, long groupId)
		throws NoSuchEagerBlobEntityException {

		EagerBlobEntity eagerBlobEntity = fetchByUUID_G(uuid, groupId);

		if (eagerBlobEntity == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEagerBlobEntityException(msg.toString());
		}

		return eagerBlobEntity;
	}

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	@Override
	public EagerBlobEntity fetchByUUID_G(
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

		if (result instanceof EagerBlobEntity) {
			EagerBlobEntity eagerBlobEntity = (EagerBlobEntity)result;

			if (!Objects.equals(uuid, eagerBlobEntity.getUuid()) ||
				(groupId != eagerBlobEntity.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_EAGERBLOBENTITY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

				List<EagerBlobEntity> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					EagerBlobEntity eagerBlobEntity = list.get(0);

					result = eagerBlobEntity;

					cacheResult(eagerBlobEntity);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

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
			return (EagerBlobEntity)result;
		}
	}

	/**
	 * Removes the eager blob entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the eager blob entity that was removed
	 */
	@Override
	public EagerBlobEntity removeByUUID_G(String uuid, long groupId)
		throws NoSuchEagerBlobEntityException {

		EagerBlobEntity eagerBlobEntity = findByUUID_G(uuid, groupId);

		return remove(eagerBlobEntity);
	}

	/**
	 * Returns the number of eager blob entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching eager blob entities
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EAGERBLOBENTITY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"eagerBlobEntity.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(eagerBlobEntity.uuid IS NULL OR eagerBlobEntity.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"eagerBlobEntity.groupId = ?";

	public EagerBlobEntityPersistenceImpl() {
		setModelClass(EagerBlobEntity.class);

		setModelImplClass(EagerBlobEntityImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("blob", "blob_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the eager blob entity in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 */
	@Override
	public void cacheResult(EagerBlobEntity eagerBlobEntity) {
		entityCache.putResult(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityImpl.class, eagerBlobEntity.getPrimaryKey(),
			eagerBlobEntity);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				eagerBlobEntity.getUuid(), eagerBlobEntity.getGroupId()
			},
			eagerBlobEntity);

		eagerBlobEntity.resetOriginalValues();
	}

	/**
	 * Caches the eager blob entities in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntities the eager blob entities
	 */
	@Override
	public void cacheResult(List<EagerBlobEntity> eagerBlobEntities) {
		for (EagerBlobEntity eagerBlobEntity : eagerBlobEntities) {
			if (entityCache.getResult(
					EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
					EagerBlobEntityImpl.class,
					eagerBlobEntity.getPrimaryKey()) == null) {

				cacheResult(eagerBlobEntity);
			}
			else {
				eagerBlobEntity.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all eager blob entities.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(EagerBlobEntityImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the eager blob entity.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(EagerBlobEntity eagerBlobEntity) {
		entityCache.removeResult(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityImpl.class, eagerBlobEntity.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(EagerBlobEntityModelImpl)eagerBlobEntity, true);
	}

	@Override
	public void clearCache(List<EagerBlobEntity> eagerBlobEntities) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (EagerBlobEntity eagerBlobEntity : eagerBlobEntities) {
			entityCache.removeResult(
				EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
				EagerBlobEntityImpl.class, eagerBlobEntity.getPrimaryKey());

			clearUniqueFindersCache(
				(EagerBlobEntityModelImpl)eagerBlobEntity, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
				EagerBlobEntityImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		EagerBlobEntityModelImpl eagerBlobEntityModelImpl) {

		Object[] args = new Object[] {
			eagerBlobEntityModelImpl.getUuid(),
			eagerBlobEntityModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, eagerBlobEntityModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		EagerBlobEntityModelImpl eagerBlobEntityModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				eagerBlobEntityModelImpl.getUuid(),
				eagerBlobEntityModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (!Objects.equals(
				eagerBlobEntityModelImpl.getUuid(),
				eagerBlobEntityModelImpl.getOriginalUuid()) ||
			(eagerBlobEntityModelImpl.getGroupId() !=
				eagerBlobEntityModelImpl.getOriginalGroupId())) {

			Object[] args = new Object[] {
				eagerBlobEntityModelImpl.getOriginalUuid(),
				eagerBlobEntityModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new eager blob entity with the primary key. Does not add the eager blob entity to the database.
	 *
	 * @param eagerBlobEntityId the primary key for the new eager blob entity
	 * @return the new eager blob entity
	 */
	@Override
	public EagerBlobEntity create(long eagerBlobEntityId) {
		EagerBlobEntity eagerBlobEntity = new EagerBlobEntityImpl();

		eagerBlobEntity.setNew(true);
		eagerBlobEntity.setPrimaryKey(eagerBlobEntityId);

		String uuid = PortalUUIDUtil.generate();

		eagerBlobEntity.setUuid(uuid);

		return eagerBlobEntity;
	}

	/**
	 * Removes the eager blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity that was removed
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	@Override
	public EagerBlobEntity remove(long eagerBlobEntityId)
		throws NoSuchEagerBlobEntityException {

		return remove((Serializable)eagerBlobEntityId);
	}

	/**
	 * Removes the eager blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the eager blob entity
	 * @return the eager blob entity that was removed
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	@Override
	public EagerBlobEntity remove(Serializable primaryKey)
		throws NoSuchEagerBlobEntityException {

		Session session = null;

		try {
			session = openSession();

			EagerBlobEntity eagerBlobEntity = (EagerBlobEntity)session.get(
				EagerBlobEntityImpl.class, primaryKey);

			if (eagerBlobEntity == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEagerBlobEntityException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(eagerBlobEntity);
		}
		catch (NoSuchEagerBlobEntityException nsee) {
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
	protected EagerBlobEntity removeImpl(EagerBlobEntity eagerBlobEntity) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(eagerBlobEntity)) {
				eagerBlobEntity = (EagerBlobEntity)session.get(
					EagerBlobEntityImpl.class,
					eagerBlobEntity.getPrimaryKeyObj());
			}

			if (eagerBlobEntity != null) {
				session.delete(eagerBlobEntity);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (eagerBlobEntity != null) {
			clearCache(eagerBlobEntity);
		}

		return eagerBlobEntity;
	}

	@Override
	public EagerBlobEntity updateImpl(EagerBlobEntity eagerBlobEntity) {
		boolean isNew = eagerBlobEntity.isNew();

		if (!(eagerBlobEntity instanceof EagerBlobEntityModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(eagerBlobEntity.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					eagerBlobEntity);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in eagerBlobEntity proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom EagerBlobEntity implementation " +
					eagerBlobEntity.getClass());
		}

		EagerBlobEntityModelImpl eagerBlobEntityModelImpl =
			(EagerBlobEntityModelImpl)eagerBlobEntity;

		if (Validator.isNull(eagerBlobEntity.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			eagerBlobEntity.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (eagerBlobEntity.isNew()) {
				session.save(eagerBlobEntity);

				eagerBlobEntity.setNew(false);
			}
			else {
				eagerBlobEntity = (EagerBlobEntity)session.merge(
					eagerBlobEntity);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if (!Objects.equals(
					eagerBlobEntity.getUuid(),
					eagerBlobEntityModelImpl.getOriginalUuid())) {

				Object[] args = new Object[] {
					eagerBlobEntityModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {eagerBlobEntityModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}
		}

		entityCache.putResult(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityImpl.class, eagerBlobEntity.getPrimaryKey(),
			eagerBlobEntity, false);

		clearUniqueFindersCache(eagerBlobEntityModelImpl, false);
		cacheUniqueFindersCache(eagerBlobEntityModelImpl);

		eagerBlobEntity.resetOriginalValues();

		return eagerBlobEntity;
	}

	/**
	 * Returns the eager blob entity with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the eager blob entity
	 * @return the eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	@Override
	public EagerBlobEntity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEagerBlobEntityException {

		EagerBlobEntity eagerBlobEntity = fetchByPrimaryKey(primaryKey);

		if (eagerBlobEntity == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEagerBlobEntityException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return eagerBlobEntity;
	}

	/**
	 * Returns the eager blob entity with the primary key or throws a <code>NoSuchEagerBlobEntityException</code> if it could not be found.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	@Override
	public EagerBlobEntity findByPrimaryKey(long eagerBlobEntityId)
		throws NoSuchEagerBlobEntityException {

		return findByPrimaryKey((Serializable)eagerBlobEntityId);
	}

	/**
	 * Returns the eager blob entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity, or <code>null</code> if a eager blob entity with the primary key could not be found
	 */
	@Override
	public EagerBlobEntity fetchByPrimaryKey(long eagerBlobEntityId) {
		return fetchByPrimaryKey((Serializable)eagerBlobEntityId);
	}

	/**
	 * Returns all the eager blob entities.
	 *
	 * @return the eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findAll(
		int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of eager blob entities
	 */
	@Override
	public List<EagerBlobEntity> findAll(
		int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator,
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

		List<EagerBlobEntity> list = null;

		if (useFinderCache) {
			list = (List<EagerBlobEntity>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_EAGERBLOBENTITY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_EAGERBLOBENTITY;

				sql = sql.concat(EagerBlobEntityModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<EagerBlobEntity>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Removes all the eager blob entities from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (EagerBlobEntity eagerBlobEntity : findAll()) {
			remove(eagerBlobEntity);
		}
	}

	/**
	 * Returns the number of eager blob entities.
	 *
	 * @return the number of eager blob entities
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_EAGERBLOBENTITY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "eagerBlobEntityId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EAGERBLOBENTITY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return EagerBlobEntityModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the eager blob entity persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED,
			EagerBlobEntityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED,
			EagerBlobEntityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED,
			EagerBlobEntityImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED,
			EagerBlobEntityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()});

		_finderPathCountByUuid = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED,
			EagerBlobEntityImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathCountByUUID_G = new FinderPath(
			EagerBlobEntityModelImpl.ENTITY_CACHE_ENABLED,
			EagerBlobEntityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(EagerBlobEntityImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_EAGERBLOBENTITY =
		"SELECT eagerBlobEntity FROM EagerBlobEntity eagerBlobEntity";

	private static final String _SQL_SELECT_EAGERBLOBENTITY_WHERE =
		"SELECT eagerBlobEntity FROM EagerBlobEntity eagerBlobEntity WHERE ";

	private static final String _SQL_COUNT_EAGERBLOBENTITY =
		"SELECT COUNT(eagerBlobEntity) FROM EagerBlobEntity eagerBlobEntity";

	private static final String _SQL_COUNT_EAGERBLOBENTITY_WHERE =
		"SELECT COUNT(eagerBlobEntity) FROM EagerBlobEntity eagerBlobEntity WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "eagerBlobEntity.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No EagerBlobEntity exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No EagerBlobEntity exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		EagerBlobEntityPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "blob"});

}