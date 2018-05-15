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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchCPDefinitionInventoryException;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.impl.CPDefinitionInventoryImpl;
import com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl;
import com.liferay.commerce.service.persistence.CPDefinitionInventoryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the cp definition inventory service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryPersistence
 * @see com.liferay.commerce.service.persistence.CPDefinitionInventoryUtil
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryPersistenceImpl extends BasePersistenceImpl<CPDefinitionInventory>
	implements CPDefinitionInventoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionInventoryUtil} to access the cp definition inventory persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionInventoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPDefinitionInventoryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp definition inventories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition inventories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @return the range of matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition inventories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition inventories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CPDefinitionInventory> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionInventory>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionInventory cpDefinitionInventory : list) {
					if (!Objects.equals(uuid, cpDefinitionInventory.getUuid())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONINVENTORY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CPDefinitionInventoryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionInventory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionInventory>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first cp definition inventory in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpDefinitionInventory != null) {
			return cpDefinitionInventory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionInventoryException(msg.toString());
	}

	/**
	 * Returns the first cp definition inventory in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		List<CPDefinitionInventory> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition inventory in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpDefinitionInventory != null) {
			return cpDefinitionInventory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionInventoryException(msg.toString());
	}

	/**
	 * Returns the last cp definition inventory in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionInventory> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition inventories before and after the current cp definition inventory in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionInventoryId the primary key of the current cp definition inventory
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory[] findByUuid_PrevAndNext(
		long CPDefinitionInventoryId, String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = findByPrimaryKey(CPDefinitionInventoryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionInventory[] array = new CPDefinitionInventoryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpDefinitionInventory,
					uuid, orderByComparator, true);

			array[1] = cpDefinitionInventory;

			array[2] = getByUuid_PrevAndNext(session, cpDefinitionInventory,
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

	protected CPDefinitionInventory getByUuid_PrevAndNext(Session session,
		CPDefinitionInventory cpDefinitionInventory, String uuid,
		OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPDEFINITIONINVENTORY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CPDefinitionInventoryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionInventory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionInventory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition inventories where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionInventory cpDefinitionInventory : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionInventory);
		}
	}

	/**
	 * Returns the number of cp definition inventories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition inventories
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONINVENTORY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpDefinitionInventory.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpDefinitionInventory.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpDefinitionInventory.uuid IS NULL OR cpDefinitionInventory.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionInventoryModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionInventoryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = fetchByUUID_G(uuid,
				groupId);

		if (cpDefinitionInventory == null) {
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

			throw new NoSuchCPDefinitionInventoryException(msg.toString());
		}

		return cpDefinitionInventory;
	}

	/**
	 * Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition inventory where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionInventory) {
			CPDefinitionInventory cpDefinitionInventory = (CPDefinitionInventory)result;

			if (!Objects.equals(uuid, cpDefinitionInventory.getUuid()) ||
					(groupId != cpDefinitionInventory.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONINVENTORY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

				List<CPDefinitionInventory> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPDefinitionInventory cpDefinitionInventory = list.get(0);

					result = cpDefinitionInventory;

					cacheResult(cpDefinitionInventory);

					if ((cpDefinitionInventory.getUuid() == null) ||
							!cpDefinitionInventory.getUuid().equals(uuid) ||
							(cpDefinitionInventory.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpDefinitionInventory);
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
			return (CPDefinitionInventory)result;
		}
	}

	/**
	 * Removes the cp definition inventory where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition inventory that was removed
	 */
	@Override
	public CPDefinitionInventory removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = findByUUID_G(uuid, groupId);

		return remove(cpDefinitionInventory);
	}

	/**
	 * Returns the number of cp definition inventories where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition inventories
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONINVENTORY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpDefinitionInventory.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpDefinitionInventory.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpDefinitionInventory.uuid IS NULL OR cpDefinitionInventory.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpDefinitionInventory.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionInventoryModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionInventoryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @return the range of matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition inventories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator,
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

		List<CPDefinitionInventory> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionInventory>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionInventory cpDefinitionInventory : list) {
					if (!Objects.equals(uuid, cpDefinitionInventory.getUuid()) ||
							(companyId != cpDefinitionInventory.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONINVENTORY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CPDefinitionInventoryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionInventory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionInventory>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpDefinitionInventory != null) {
			return cpDefinitionInventory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionInventoryException(msg.toString());
	}

	/**
	 * Returns the first cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		List<CPDefinitionInventory> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpDefinitionInventory != null) {
			return cpDefinitionInventory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionInventoryException(msg.toString());
	}

	/**
	 * Returns the last cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionInventory> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition inventories before and after the current cp definition inventory in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionInventoryId the primary key of the current cp definition inventory
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory[] findByUuid_C_PrevAndNext(
		long CPDefinitionInventoryId, String uuid, long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = findByPrimaryKey(CPDefinitionInventoryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionInventory[] array = new CPDefinitionInventoryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpDefinitionInventory,
					uuid, companyId, orderByComparator, true);

			array[1] = cpDefinitionInventory;

			array[2] = getByUuid_C_PrevAndNext(session, cpDefinitionInventory,
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

	protected CPDefinitionInventory getByUuid_C_PrevAndNext(Session session,
		CPDefinitionInventory cpDefinitionInventory, String uuid,
		long companyId,
		OrderByComparator<CPDefinitionInventory> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONINVENTORY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CPDefinitionInventoryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionInventory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionInventory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition inventories where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionInventory cpDefinitionInventory : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionInventory);
		}
	}

	/**
	 * Returns the number of cp definition inventories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition inventories
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONINVENTORY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpDefinitionInventory.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpDefinitionInventory.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpDefinitionInventory.uuid IS NULL OR cpDefinitionInventory.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpDefinitionInventory.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CPDEFINITIONID = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCPDefinitionId", new String[] { Long.class.getName() },
			CPDefinitionInventoryModelImpl.CPDEFINITIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID = new FinderPath(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the cp definition inventory where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = fetchByCPDefinitionId(CPDefinitionId);

		if (cpDefinitionInventory == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("CPDefinitionId=");
			msg.append(CPDefinitionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionInventoryException(msg.toString());
		}

		return cpDefinitionInventory;
	}

	/**
	 * Returns the cp definition inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByCPDefinitionId(long CPDefinitionId) {
		return fetchByCPDefinitionId(CPDefinitionId, true);
	}

	/**
	 * Returns the cp definition inventory where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByCPDefinitionId(long CPDefinitionId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { CPDefinitionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionInventory) {
			CPDefinitionInventory cpDefinitionInventory = (CPDefinitionInventory)result;

			if ((CPDefinitionId != cpDefinitionInventory.getCPDefinitionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CPDEFINITIONINVENTORY_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				List<CPDefinitionInventory> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
						finderArgs, list);
				}
				else {
					CPDefinitionInventory cpDefinitionInventory = list.get(0);

					result = cpDefinitionInventory;

					cacheResult(cpDefinitionInventory);

					if ((cpDefinitionInventory.getCPDefinitionId() != CPDefinitionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
							finderArgs, cpDefinitionInventory);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
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
			return (CPDefinitionInventory)result;
		}
	}

	/**
	 * Removes the cp definition inventory where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition inventory that was removed
	 */
	@Override
	public CPDefinitionInventory removeByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = findByCPDefinitionId(CPDefinitionId);

		return remove(cpDefinitionInventory);
	}

	/**
	 * Returns the number of cp definition inventories where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition inventories
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID;

		Object[] finderArgs = new Object[] { CPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONINVENTORY_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 = "cpDefinitionInventory.CPDefinitionId = ?";

	public CPDefinitionInventoryPersistenceImpl() {
		setModelClass(CPDefinitionInventory.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp definition inventory in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionInventory the cp definition inventory
	 */
	@Override
	public void cacheResult(CPDefinitionInventory cpDefinitionInventory) {
		entityCache.putResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			cpDefinitionInventory.getPrimaryKey(), cpDefinitionInventory);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpDefinitionInventory.getUuid(),
				cpDefinitionInventory.getGroupId()
			}, cpDefinitionInventory);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
			new Object[] { cpDefinitionInventory.getCPDefinitionId() },
			cpDefinitionInventory);

		cpDefinitionInventory.resetOriginalValues();
	}

	/**
	 * Caches the cp definition inventories in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionInventories the cp definition inventories
	 */
	@Override
	public void cacheResult(List<CPDefinitionInventory> cpDefinitionInventories) {
		for (CPDefinitionInventory cpDefinitionInventory : cpDefinitionInventories) {
			if (entityCache.getResult(
						CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionInventoryImpl.class,
						cpDefinitionInventory.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionInventory);
			}
			else {
				cpDefinitionInventory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition inventories.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionInventoryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition inventory.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionInventory cpDefinitionInventory) {
		entityCache.removeResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			cpDefinitionInventory.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionInventoryModelImpl)cpDefinitionInventory,
			true);
	}

	@Override
	public void clearCache(List<CPDefinitionInventory> cpDefinitionInventories) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionInventory cpDefinitionInventory : cpDefinitionInventories) {
			entityCache.removeResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionInventoryImpl.class,
				cpDefinitionInventory.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionInventoryModelImpl)cpDefinitionInventory,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionInventoryModelImpl cpDefinitionInventoryModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionInventoryModelImpl.getUuid(),
				cpDefinitionInventoryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpDefinitionInventoryModelImpl, false);

		args = new Object[] { cpDefinitionInventoryModelImpl.getCPDefinitionId() };

		finderCache.putResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID, args,
			cpDefinitionInventoryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionInventoryModelImpl cpDefinitionInventoryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionInventoryModelImpl.getUuid(),
					cpDefinitionInventoryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpDefinitionInventoryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionInventoryModelImpl.getOriginalUuid(),
					cpDefinitionInventoryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionInventoryModelImpl.getCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID, args);
		}

		if ((cpDefinitionInventoryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CPDEFINITIONID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionInventoryModelImpl.getOriginalCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID, args);
		}
	}

	/**
	 * Creates a new cp definition inventory with the primary key. Does not add the cp definition inventory to the database.
	 *
	 * @param CPDefinitionInventoryId the primary key for the new cp definition inventory
	 * @return the new cp definition inventory
	 */
	@Override
	public CPDefinitionInventory create(long CPDefinitionInventoryId) {
		CPDefinitionInventory cpDefinitionInventory = new CPDefinitionInventoryImpl();

		cpDefinitionInventory.setNew(true);
		cpDefinitionInventory.setPrimaryKey(CPDefinitionInventoryId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionInventory.setUuid(uuid);

		cpDefinitionInventory.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionInventory;
	}

	/**
	 * Removes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionInventoryId the primary key of the cp definition inventory
	 * @return the cp definition inventory that was removed
	 * @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory remove(long CPDefinitionInventoryId)
		throws NoSuchCPDefinitionInventoryException {
		return remove((Serializable)CPDefinitionInventoryId);
	}

	/**
	 * Removes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition inventory
	 * @return the cp definition inventory that was removed
	 * @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory remove(Serializable primaryKey)
		throws NoSuchCPDefinitionInventoryException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionInventory cpDefinitionInventory = (CPDefinitionInventory)session.get(CPDefinitionInventoryImpl.class,
					primaryKey);

			if (cpDefinitionInventory == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionInventoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionInventory);
		}
		catch (NoSuchCPDefinitionInventoryException nsee) {
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
	protected CPDefinitionInventory removeImpl(
		CPDefinitionInventory cpDefinitionInventory) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionInventory)) {
				cpDefinitionInventory = (CPDefinitionInventory)session.get(CPDefinitionInventoryImpl.class,
						cpDefinitionInventory.getPrimaryKeyObj());
			}

			if (cpDefinitionInventory != null) {
				session.delete(cpDefinitionInventory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionInventory != null) {
			clearCache(cpDefinitionInventory);
		}

		return cpDefinitionInventory;
	}

	@Override
	public CPDefinitionInventory updateImpl(
		CPDefinitionInventory cpDefinitionInventory) {
		boolean isNew = cpDefinitionInventory.isNew();

		if (!(cpDefinitionInventory instanceof CPDefinitionInventoryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionInventory.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpDefinitionInventory);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionInventory proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionInventory implementation " +
				cpDefinitionInventory.getClass());
		}

		CPDefinitionInventoryModelImpl cpDefinitionInventoryModelImpl = (CPDefinitionInventoryModelImpl)cpDefinitionInventory;

		if (Validator.isNull(cpDefinitionInventory.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionInventory.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinitionInventory.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionInventory.setCreateDate(now);
			}
			else {
				cpDefinitionInventory.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpDefinitionInventoryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionInventory.setModifiedDate(now);
			}
			else {
				cpDefinitionInventory.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionInventory.isNew()) {
				session.save(cpDefinitionInventory);

				cpDefinitionInventory.setNew(false);
			}
			else {
				cpDefinitionInventory = (CPDefinitionInventory)session.merge(cpDefinitionInventory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionInventoryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpDefinitionInventoryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpDefinitionInventoryModelImpl.getUuid(),
					cpDefinitionInventoryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionInventoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionInventoryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpDefinitionInventoryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpDefinitionInventoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionInventoryModelImpl.getOriginalUuid(),
						cpDefinitionInventoryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpDefinitionInventoryModelImpl.getUuid(),
						cpDefinitionInventoryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		entityCache.putResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionInventoryImpl.class,
			cpDefinitionInventory.getPrimaryKey(), cpDefinitionInventory, false);

		clearUniqueFindersCache(cpDefinitionInventoryModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionInventoryModelImpl);

		cpDefinitionInventory.resetOriginalValues();

		return cpDefinitionInventory;
	}

	/**
	 * Returns the cp definition inventory with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition inventory
	 * @return the cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionInventoryException {
		CPDefinitionInventory cpDefinitionInventory = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionInventory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionInventoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionInventory;
	}

	/**
	 * Returns the cp definition inventory with the primary key or throws a {@link NoSuchCPDefinitionInventoryException} if it could not be found.
	 *
	 * @param CPDefinitionInventoryId the primary key of the cp definition inventory
	 * @return the cp definition inventory
	 * @throws NoSuchCPDefinitionInventoryException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory findByPrimaryKey(long CPDefinitionInventoryId)
		throws NoSuchCPDefinitionInventoryException {
		return findByPrimaryKey((Serializable)CPDefinitionInventoryId);
	}

	/**
	 * Returns the cp definition inventory with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition inventory
	 * @return the cp definition inventory, or <code>null</code> if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionInventoryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionInventory cpDefinitionInventory = (CPDefinitionInventory)serializable;

		if (cpDefinitionInventory == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionInventory = (CPDefinitionInventory)session.get(CPDefinitionInventoryImpl.class,
						primaryKey);

				if (cpDefinitionInventory != null) {
					cacheResult(cpDefinitionInventory);
				}
				else {
					entityCache.putResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionInventoryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionInventoryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionInventory;
	}

	/**
	 * Returns the cp definition inventory with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionInventoryId the primary key of the cp definition inventory
	 * @return the cp definition inventory, or <code>null</code> if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory fetchByPrimaryKey(long CPDefinitionInventoryId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionInventoryId);
	}

	@Override
	public Map<Serializable, CPDefinitionInventory> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionInventory> map = new HashMap<Serializable, CPDefinitionInventory>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionInventory cpDefinitionInventory = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionInventory != null) {
				map.put(primaryKey, cpDefinitionInventory);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionInventoryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionInventory)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONINVENTORY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CPDefinitionInventory cpDefinitionInventory : (List<CPDefinitionInventory>)q.list()) {
				map.put(cpDefinitionInventory.getPrimaryKeyObj(),
					cpDefinitionInventory);

				cacheResult(cpDefinitionInventory);

				uncachedPrimaryKeys.remove(cpDefinitionInventory.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionInventoryModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionInventoryImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition inventories.
	 *
	 * @return the cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition inventories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @return the range of cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition inventories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findAll(int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition inventories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition inventories
	 */
	@Override
	public List<CPDefinitionInventory> findAll(int start, int end,
		OrderByComparator<CPDefinitionInventory> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CPDefinitionInventory> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionInventory>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONINVENTORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONINVENTORY;

				if (pagination) {
					sql = sql.concat(CPDefinitionInventoryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionInventory>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionInventory>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the cp definition inventories from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionInventory cpDefinitionInventory : findAll()) {
			remove(cpDefinitionInventory);
		}
	}

	/**
	 * Returns the number of cp definition inventories.
	 *
	 * @return the number of cp definition inventories
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONINVENTORY);

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
		return CPDefinitionInventoryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition inventory persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionInventoryImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONINVENTORY = "SELECT cpDefinitionInventory FROM CPDefinitionInventory cpDefinitionInventory";
	private static final String _SQL_SELECT_CPDEFINITIONINVENTORY_WHERE_PKS_IN = "SELECT cpDefinitionInventory FROM CPDefinitionInventory cpDefinitionInventory WHERE CPDefinitionInventoryId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONINVENTORY_WHERE = "SELECT cpDefinitionInventory FROM CPDefinitionInventory cpDefinitionInventory WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONINVENTORY = "SELECT COUNT(cpDefinitionInventory) FROM CPDefinitionInventory cpDefinitionInventory";
	private static final String _SQL_COUNT_CPDEFINITIONINVENTORY_WHERE = "SELECT COUNT(cpDefinitionInventory) FROM CPDefinitionInventory cpDefinitionInventory WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionInventory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionInventory exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionInventory exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionInventoryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}