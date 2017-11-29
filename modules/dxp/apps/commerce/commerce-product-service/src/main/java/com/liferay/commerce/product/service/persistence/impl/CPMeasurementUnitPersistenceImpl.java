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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.impl.CPMeasurementUnitImpl;
import com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl;
import com.liferay.commerce.product.service.persistence.CPMeasurementUnitPersistence;

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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Arrays;
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
 * The persistence implementation for the cp measurement unit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPMeasurementUnitPersistence
 * @see com.liferay.commerce.product.service.persistence.CPMeasurementUnitUtil
 * @generated
 */
@ProviderType
public class CPMeasurementUnitPersistenceImpl extends BasePersistenceImpl<CPMeasurementUnit>
	implements CPMeasurementUnitPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPMeasurementUnitUtil} to access the cp measurement unit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPMeasurementUnitImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPMeasurementUnitModelImpl.UUID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp measurement units where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
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

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPMeasurementUnit cpMeasurementUnit : list) {
					if (!Objects.equals(uuid, cpMeasurementUnit.getUuid())) {
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

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

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
				query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
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
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUuid_First(String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUuid_First(String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		List<CPMeasurementUnit> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUuid_Last(String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUuid_Last(String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPMeasurementUnit> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp measurement units before and after the current cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param CPMeasurementUnitId the primary key of the current cp measurement unit
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit[] findByUuid_PrevAndNext(
		long CPMeasurementUnitId, String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByPrimaryKey(CPMeasurementUnitId);

		Session session = null;

		try {
			session = openSession();

			CPMeasurementUnit[] array = new CPMeasurementUnitImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpMeasurementUnit, uuid,
					orderByComparator, true);

			array[1] = cpMeasurementUnit;

			array[2] = getByUuid_PrevAndNext(session, cpMeasurementUnit, uuid,
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

	protected CPMeasurementUnit getByUuid_PrevAndNext(Session session,
		CPMeasurementUnit cpMeasurementUnit, String uuid,
		OrderByComparator<CPMeasurementUnit> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

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
			query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpMeasurementUnit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPMeasurementUnit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp measurement units where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPMeasurementUnit cpMeasurementUnit : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpMeasurementUnit.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpMeasurementUnit.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpMeasurementUnit.uuid IS NULL OR cpMeasurementUnit.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPMeasurementUnitModelImpl.UUID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUUID_G(String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByUUID_G(uuid, groupId);

		if (cpMeasurementUnit == null) {
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

			throw new NoSuchCPMeasurementUnitException(msg.toString());
		}

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPMeasurementUnit) {
			CPMeasurementUnit cpMeasurementUnit = (CPMeasurementUnit)result;

			if (!Objects.equals(uuid, cpMeasurementUnit.getUuid()) ||
					(groupId != cpMeasurementUnit.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

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

				List<CPMeasurementUnit> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPMeasurementUnit cpMeasurementUnit = list.get(0);

					result = cpMeasurementUnit;

					cacheResult(cpMeasurementUnit);

					if ((cpMeasurementUnit.getUuid() == null) ||
							!cpMeasurementUnit.getUuid().equals(uuid) ||
							(cpMeasurementUnit.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpMeasurementUnit);
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
			return (CPMeasurementUnit)result;
		}
	}

	/**
	 * Removes the cp measurement unit where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp measurement unit that was removed
	 */
	@Override
	public CPMeasurementUnit removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByUUID_G(uuid, groupId);

		return remove(cpMeasurementUnit);
	}

	/**
	 * Returns the number of cp measurement units where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpMeasurementUnit.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpMeasurementUnit.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpMeasurementUnit.uuid IS NULL OR cpMeasurementUnit.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpMeasurementUnit.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPMeasurementUnitModelImpl.UUID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.COMPANYID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
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

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPMeasurementUnit cpMeasurementUnit : list) {
					if (!Objects.equals(uuid, cpMeasurementUnit.getUuid()) ||
							(companyId != cpMeasurementUnit.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

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
				query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
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
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		List<CPMeasurementUnit> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPMeasurementUnit> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp measurement units before and after the current cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPMeasurementUnitId the primary key of the current cp measurement unit
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit[] findByUuid_C_PrevAndNext(
		long CPMeasurementUnitId, String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByPrimaryKey(CPMeasurementUnitId);

		Session session = null;

		try {
			session = openSession();

			CPMeasurementUnit[] array = new CPMeasurementUnitImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpMeasurementUnit,
					uuid, companyId, orderByComparator, true);

			array[1] = cpMeasurementUnit;

			array[2] = getByUuid_C_PrevAndNext(session, cpMeasurementUnit,
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

	protected CPMeasurementUnit getByUuid_C_PrevAndNext(Session session,
		CPMeasurementUnit cpMeasurementUnit, String uuid, long companyId,
		OrderByComparator<CPMeasurementUnit> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

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
			query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpMeasurementUnit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPMeasurementUnit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp measurement units where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPMeasurementUnit cpMeasurementUnit : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpMeasurementUnit.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpMeasurementUnit.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpMeasurementUnit.uuid IS NULL OR cpMeasurementUnit.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpMeasurementUnit.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CPMeasurementUnitModelImpl.GROUPID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp measurement units where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPMeasurementUnit cpMeasurementUnit : list) {
					if ((groupId != cpMeasurementUnit.getGroupId())) {
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

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
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
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByGroupId_First(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByGroupId_First(groupId,
				orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByGroupId_First(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		List<CPMeasurementUnit> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByGroupId_Last(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByGroupId_Last(long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPMeasurementUnit> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63;.
	 *
	 * @param CPMeasurementUnitId the primary key of the current cp measurement unit
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit[] findByGroupId_PrevAndNext(
		long CPMeasurementUnitId, long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByPrimaryKey(CPMeasurementUnitId);

		Session session = null;

		try {
			session = openSession();

			CPMeasurementUnit[] array = new CPMeasurementUnitImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, cpMeasurementUnit,
					groupId, orderByComparator, true);

			array[1] = cpMeasurementUnit;

			array[2] = getByGroupId_PrevAndNext(session, cpMeasurementUnit,
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

	protected CPMeasurementUnit getByGroupId_PrevAndNext(Session session,
		CPMeasurementUnit cpMeasurementUnit, long groupId,
		OrderByComparator<CPMeasurementUnit> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

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
			query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpMeasurementUnit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPMeasurementUnit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp measurement units where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPMeasurementUnit cpMeasurementUnit : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cpMeasurementUnit.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			CPMeasurementUnitModelImpl.GROUPID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.TYPE_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the cp measurement units where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_T(long groupId, int type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp measurement units where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_T(long groupId, int type, int start,
		int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_T(long groupId, int type, int start,
		int end, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_T(long groupId, int type, int start,
		int end, OrderByComparator<CPMeasurementUnit> orderByComparator,
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

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPMeasurementUnit cpMeasurementUnit : list) {
					if ((groupId != cpMeasurementUnit.getGroupId()) ||
							(type != cpMeasurementUnit.getType())) {
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

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				if (!pagination) {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
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
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByG_T_First(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByG_T_First(groupId, type,
				orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_T_First(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		List<CPMeasurementUnit> list = findByG_T(groupId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByG_T_Last(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByG_T_Last(groupId, type,
				orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_T_Last(long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<CPMeasurementUnit> list = findByG_T(groupId, type, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param CPMeasurementUnitId the primary key of the current cp measurement unit
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit[] findByG_T_PrevAndNext(long CPMeasurementUnitId,
		long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByPrimaryKey(CPMeasurementUnitId);

		Session session = null;

		try {
			session = openSession();

			CPMeasurementUnit[] array = new CPMeasurementUnitImpl[3];

			array[0] = getByG_T_PrevAndNext(session, cpMeasurementUnit,
					groupId, type, orderByComparator, true);

			array[1] = cpMeasurementUnit;

			array[2] = getByG_T_PrevAndNext(session, cpMeasurementUnit,
					groupId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPMeasurementUnit getByG_T_PrevAndNext(Session session,
		CPMeasurementUnit cpMeasurementUnit, long groupId, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpMeasurementUnit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPMeasurementUnit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp measurement units where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (CPMeasurementUnit cpMeasurementUnit : findByG_T(groupId, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "cpMeasurementUnit.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "cpMeasurementUnit.type = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_K_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_K_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			CPMeasurementUnitModelImpl.GROUPID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.KEY_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_K_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_K_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByG_K_T(long groupId, String key, int type)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByG_K_T(groupId, key, type);

		if (cpMeasurementUnit == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", key=");
			msg.append(key);

			msg.append(", type=");
			msg.append(type);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPMeasurementUnitException(msg.toString());
		}

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_K_T(long groupId, String key, int type) {
		return fetchByG_K_T(groupId, key, type, true);
	}

	/**
	 * Returns the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_K_T(long groupId, String key, int type,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, key, type };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_K_T,
					finderArgs, this);
		}

		if (result instanceof CPMeasurementUnit) {
			CPMeasurementUnit cpMeasurementUnit = (CPMeasurementUnit)result;

			if ((groupId != cpMeasurementUnit.getGroupId()) ||
					!Objects.equals(key, cpMeasurementUnit.getKey()) ||
					(type != cpMeasurementUnit.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_K_T_GROUPID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_G_K_T_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_G_K_T_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_K_T_KEY_2);
			}

			query.append(_FINDER_COLUMN_G_K_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(key);
				}

				qPos.add(type);

				List<CPMeasurementUnit> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_K_T,
						finderArgs, list);
				}
				else {
					CPMeasurementUnit cpMeasurementUnit = list.get(0);

					result = cpMeasurementUnit;

					cacheResult(cpMeasurementUnit);

					if ((cpMeasurementUnit.getGroupId() != groupId) ||
							(cpMeasurementUnit.getKey() == null) ||
							!cpMeasurementUnit.getKey().equals(key) ||
							(cpMeasurementUnit.getType() != type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_K_T,
							finderArgs, cpMeasurementUnit);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_K_T, finderArgs);

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
			return (CPMeasurementUnit)result;
		}
	}

	/**
	 * Removes the cp measurement unit where groupId = &#63; and key = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @return the cp measurement unit that was removed
	 */
	@Override
	public CPMeasurementUnit removeByG_K_T(long groupId, String key, int type)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByG_K_T(groupId, key, type);

		return remove(cpMeasurementUnit);
	}

	/**
	 * Returns the number of cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByG_K_T(long groupId, String key, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_K_T;

		Object[] finderArgs = new Object[] { groupId, key, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_K_T_GROUPID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_G_K_T_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_G_K_T_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_K_T_KEY_2);
			}

			query.append(_FINDER_COLUMN_G_K_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(key);
				}

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_K_T_GROUPID_2 = "cpMeasurementUnit.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_K_T_KEY_1 = "cpMeasurementUnit.key IS NULL AND ";
	private static final String _FINDER_COLUMN_G_K_T_KEY_2 = "cpMeasurementUnit.key = ? AND ";
	private static final String _FINDER_COLUMN_G_K_T_KEY_3 = "(cpMeasurementUnit.key IS NULL OR cpMeasurementUnit.key = '') AND ";
	private static final String _FINDER_COLUMN_G_K_T_TYPE_2 = "cpMeasurementUnit.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_ORK_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_OrK_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ORK_T =
		new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_OrK_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			CPMeasurementUnitModelImpl.GROUPID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.KEY_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.TYPE_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_ORK_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_OrK_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_ORK_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_OrK_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String key,
		int type) {
		return findByG_OrK_T(groupId, key, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String key,
		int type, int start, int end) {
		return findByG_OrK_T(groupId, key, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String key,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findByG_OrK_T(groupId, key, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String key,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ORK_T;
			finderArgs = new Object[] { groupId, key, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_ORK_T;
			finderArgs = new Object[] {
					groupId, key, type,
					
					start, end, orderByComparator
				};
		}

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPMeasurementUnit cpMeasurementUnit : list) {
					if ((groupId != cpMeasurementUnit.getGroupId()) ||
							!Objects.equals(key, cpMeasurementUnit.getKey()) ||
							(type != cpMeasurementUnit.getType())) {
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

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_ORK_T_GROUPID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_G_ORK_T_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_G_ORK_T_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_ORK_T_KEY_2);
			}

			query.append(_FINDER_COLUMN_G_ORK_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(StringUtil.toLowerCase(key));
				}

				qPos.add(type);

				if (!pagination) {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
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
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByG_OrK_T_First(long groupId, String key,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByG_OrK_T_First(groupId,
				key, type, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", key=");
		msg.append(key);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_OrK_T_First(long groupId, String key,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		List<CPMeasurementUnit> list = findByG_OrK_T(groupId, key, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByG_OrK_T_Last(long groupId, String key,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByG_OrK_T_Last(groupId, key,
				type, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", key=");
		msg.append(key);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_OrK_T_Last(long groupId, String key,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		int count = countByG_OrK_T(groupId, key, type);

		if (count == 0) {
			return null;
		}

		List<CPMeasurementUnit> list = findByG_OrK_T(groupId, key, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param CPMeasurementUnitId the primary key of the current cp measurement unit
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit[] findByG_OrK_T_PrevAndNext(
		long CPMeasurementUnitId, long groupId, String key, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByPrimaryKey(CPMeasurementUnitId);

		Session session = null;

		try {
			session = openSession();

			CPMeasurementUnit[] array = new CPMeasurementUnitImpl[3];

			array[0] = getByG_OrK_T_PrevAndNext(session, cpMeasurementUnit,
					groupId, key, type, orderByComparator, true);

			array[1] = cpMeasurementUnit;

			array[2] = getByG_OrK_T_PrevAndNext(session, cpMeasurementUnit,
					groupId, key, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPMeasurementUnit getByG_OrK_T_PrevAndNext(Session session,
		CPMeasurementUnit cpMeasurementUnit, long groupId, String key,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

		query.append(_FINDER_COLUMN_G_ORK_T_GROUPID_2);

		boolean bindKey = false;

		if (key == null) {
			query.append(_FINDER_COLUMN_G_ORK_T_KEY_1);
		}
		else if (key.equals("")) {
			query.append(_FINDER_COLUMN_G_ORK_T_KEY_3);
		}
		else {
			bindKey = true;

			query.append(_FINDER_COLUMN_G_ORK_T_KEY_2);
		}

		query.append(_FINDER_COLUMN_G_ORK_T_TYPE_2);

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
			query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindKey) {
			qPos.add(StringUtil.toLowerCase(key));
		}

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpMeasurementUnit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPMeasurementUnit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the cp measurement units where groupId = &#63; and key = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param keies the keies
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String[] keies,
		int type) {
		return findByG_OrK_T(groupId, keies, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where groupId = &#63; and key = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param keies the keies
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String[] keies,
		int type, int start, int end) {
		return findByG_OrK_T(groupId, keies, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and key = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param keies the keies
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String[] keies,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findByG_OrK_T(groupId, keies, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and key = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_OrK_T(long groupId, String[] keies,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		if (keies == null) {
			keies = new String[0];
		}
		else if (keies.length > 1) {
			keies = ArrayUtil.distinct(keies, NULL_SAFE_STRING_COMPARATOR);

			Arrays.sort(keies, NULL_SAFE_STRING_COMPARATOR);
		}

		if (keies.length == 1) {
			return findByG_OrK_T(groupId, keies[0], type, start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { groupId, StringUtil.merge(keies), type };
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(keies), type,
					
					start, end, orderByComparator
				};
		}

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_ORK_T,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPMeasurementUnit cpMeasurementUnit : list) {
					if ((groupId != cpMeasurementUnit.getGroupId()) ||
							!ArrayUtil.contains(keies,
								cpMeasurementUnit.getKey()) ||
							(type != cpMeasurementUnit.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_ORK_T_GROUPID_2);

			if (keies.length > 0) {
				query.append("(");

				for (int i = 0; i < keies.length; i++) {
					String key = keies[i];

					if (key == null) {
						query.append(_FINDER_COLUMN_G_ORK_T_KEY_4);
					}
					else if (key.equals("")) {
						query.append(_FINDER_COLUMN_G_ORK_T_KEY_6);
					}
					else {
						query.append(_FINDER_COLUMN_G_ORK_T_KEY_5);
					}

					if ((i + 1) < keies.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_ORK_T_TYPE_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				for (String key : keies) {
					if ((key != null) && !key.isEmpty()) {
						qPos.add(key);
					}
				}

				qPos.add(type);

				if (!pagination) {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_ORK_T,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_ORK_T,
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
	 * Removes all the cp measurement units where groupId = &#63; and key = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 */
	@Override
	public void removeByG_OrK_T(long groupId, String key, int type) {
		for (CPMeasurementUnit cpMeasurementUnit : findByG_OrK_T(groupId, key,
				type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units where groupId = &#63; and key = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param key the key
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByG_OrK_T(long groupId, String key, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_ORK_T;

		Object[] finderArgs = new Object[] { groupId, key, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_ORK_T_GROUPID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_G_ORK_T_KEY_1);
			}
			else if (key.equals("")) {
				query.append(_FINDER_COLUMN_G_ORK_T_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_G_ORK_T_KEY_2);
			}

			query.append(_FINDER_COLUMN_G_ORK_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindKey) {
					qPos.add(StringUtil.toLowerCase(key));
				}

				qPos.add(type);

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
	 * Returns the number of cp measurement units where groupId = &#63; and key = any &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param keies the keies
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByG_OrK_T(long groupId, String[] keies, int type) {
		if (keies == null) {
			keies = new String[0];
		}
		else if (keies.length > 1) {
			keies = ArrayUtil.distinct(keies, NULL_SAFE_STRING_COMPARATOR);

			Arrays.sort(keies, NULL_SAFE_STRING_COMPARATOR);
		}

		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(keies), type
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_ORK_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_ORK_T_GROUPID_2);

			if (keies.length > 0) {
				query.append("(");

				for (int i = 0; i < keies.length; i++) {
					String key = keies[i];

					if (key == null) {
						query.append(_FINDER_COLUMN_G_ORK_T_KEY_4);
					}
					else if (key.equals("")) {
						query.append(_FINDER_COLUMN_G_ORK_T_KEY_6);
					}
					else {
						query.append(_FINDER_COLUMN_G_ORK_T_KEY_5);
					}

					if ((i + 1) < keies.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_ORK_T_TYPE_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				for (String key : keies) {
					if ((key != null) && !key.isEmpty()) {
						qPos.add(key);
					}
				}

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_ORK_T,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_ORK_T,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_ORK_T_GROUPID_2 = "cpMeasurementUnit.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_ORK_T_KEY_1 = "cpMeasurementUnit.key IS NULL AND ";
	private static final String _FINDER_COLUMN_G_ORK_T_KEY_2 = "lower(cpMeasurementUnit.key) = ? AND ";
	private static final String _FINDER_COLUMN_G_ORK_T_KEY_3 = "(cpMeasurementUnit.key IS NULL OR cpMeasurementUnit.key = '') AND ";
	private static final String _FINDER_COLUMN_G_ORK_T_KEY_4 = "(" +
		removeConjunction(_FINDER_COLUMN_G_ORK_T_KEY_1) + ")";
	private static final String _FINDER_COLUMN_G_ORK_T_KEY_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_ORK_T_KEY_2) + ")";
	private static final String _FINDER_COLUMN_G_ORK_T_KEY_6 = "(" +
		removeConjunction(_FINDER_COLUMN_G_ORK_T_KEY_3) + ")";
	private static final String _FINDER_COLUMN_G_ORK_T_TYPE_2 = "cpMeasurementUnit.type = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED,
			CPMeasurementUnitImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			CPMeasurementUnitModelImpl.GROUPID_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.PRIMARY_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.TYPE_COLUMN_BITMASK |
			CPMeasurementUnitModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_T = new FinderPath(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_P_T(long groupId, boolean primary,
		int type) {
		return findByG_P_T(groupId, primary, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_P_T(long groupId, boolean primary,
		int type, int start, int end) {
		return findByG_P_T(groupId, primary, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_P_T(long groupId, boolean primary,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findByG_P_T(groupId, primary, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findByG_P_T(long groupId, boolean primary,
		int type, int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T;
			finderArgs = new Object[] { groupId, primary, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_T;
			finderArgs = new Object[] {
					groupId, primary, type,
					
					start, end, orderByComparator
				};
		}

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPMeasurementUnit cpMeasurementUnit : list) {
					if ((groupId != cpMeasurementUnit.getGroupId()) ||
							(primary != cpMeasurementUnit.getPrimary()) ||
							(type != cpMeasurementUnit.getType())) {
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

			query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIMARY_2);

			query.append(_FINDER_COLUMN_G_P_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(primary);

				qPos.add(type);

				if (!pagination) {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
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
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByG_P_T_First(long groupId, boolean primary,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByG_P_T_First(groupId,
				primary, type, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", primary=");
		msg.append(primary);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the first cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_P_T_First(long groupId, boolean primary,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		List<CPMeasurementUnit> list = findByG_P_T(groupId, primary, type, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit findByG_P_T_Last(long groupId, boolean primary,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByG_P_T_Last(groupId,
				primary, type, orderByComparator);

		if (cpMeasurementUnit != null) {
			return cpMeasurementUnit;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", primary=");
		msg.append(primary);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchCPMeasurementUnitException(msg.toString());
	}

	/**
	 * Returns the last cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByG_P_T_Last(long groupId, boolean primary,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator) {
		int count = countByG_P_T(groupId, primary, type);

		if (count == 0) {
			return null;
		}

		List<CPMeasurementUnit> list = findByG_P_T(groupId, primary, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp measurement units before and after the current cp measurement unit in the ordered set where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param CPMeasurementUnitId the primary key of the current cp measurement unit
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit[] findByG_P_T_PrevAndNext(
		long CPMeasurementUnitId, long groupId, boolean primary, int type,
		OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = findByPrimaryKey(CPMeasurementUnitId);

		Session session = null;

		try {
			session = openSession();

			CPMeasurementUnit[] array = new CPMeasurementUnitImpl[3];

			array[0] = getByG_P_T_PrevAndNext(session, cpMeasurementUnit,
					groupId, primary, type, orderByComparator, true);

			array[1] = cpMeasurementUnit;

			array[2] = getByG_P_T_PrevAndNext(session, cpMeasurementUnit,
					groupId, primary, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPMeasurementUnit getByG_P_T_PrevAndNext(Session session,
		CPMeasurementUnit cpMeasurementUnit, long groupId, boolean primary,
		int type, OrderByComparator<CPMeasurementUnit> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE);

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIMARY_2);

		query.append(_FINDER_COLUMN_G_P_T_TYPE_2);

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
			query.append(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(primary);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpMeasurementUnit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPMeasurementUnit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp measurement units where groupId = &#63; and primary = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 */
	@Override
	public void removeByG_P_T(long groupId, boolean primary, int type) {
		for (CPMeasurementUnit cpMeasurementUnit : findByG_P_T(groupId,
				primary, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units where groupId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param primary the primary
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	@Override
	public int countByG_P_T(long groupId, boolean primary, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_T;

		Object[] finderArgs = new Object[] { groupId, primary, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPMEASUREMENTUNIT_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIMARY_2);

			query.append(_FINDER_COLUMN_G_P_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(primary);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_P_T_GROUPID_2 = "cpMeasurementUnit.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_T_PRIMARY_2 = "cpMeasurementUnit.primary = ? AND ";
	private static final String _FINDER_COLUMN_G_P_T_TYPE_2 = "cpMeasurementUnit.type = ?";

	public CPMeasurementUnitPersistenceImpl() {
		setModelClass(CPMeasurementUnit.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("key", "key_");
			dbColumnNames.put("primary", "primary_");
			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp measurement unit in the entity cache if it is enabled.
	 *
	 * @param cpMeasurementUnit the cp measurement unit
	 */
	@Override
	public void cacheResult(CPMeasurementUnit cpMeasurementUnit) {
		entityCache.putResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitImpl.class, cpMeasurementUnit.getPrimaryKey(),
			cpMeasurementUnit);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpMeasurementUnit.getUuid(), cpMeasurementUnit.getGroupId()
			}, cpMeasurementUnit);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_K_T,
			new Object[] {
				cpMeasurementUnit.getGroupId(), cpMeasurementUnit.getKey(),
				cpMeasurementUnit.getType()
			}, cpMeasurementUnit);

		cpMeasurementUnit.resetOriginalValues();
	}

	/**
	 * Caches the cp measurement units in the entity cache if it is enabled.
	 *
	 * @param cpMeasurementUnits the cp measurement units
	 */
	@Override
	public void cacheResult(List<CPMeasurementUnit> cpMeasurementUnits) {
		for (CPMeasurementUnit cpMeasurementUnit : cpMeasurementUnits) {
			if (entityCache.getResult(
						CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
						CPMeasurementUnitImpl.class,
						cpMeasurementUnit.getPrimaryKey()) == null) {
				cacheResult(cpMeasurementUnit);
			}
			else {
				cpMeasurementUnit.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp measurement units.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPMeasurementUnitImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp measurement unit.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPMeasurementUnit cpMeasurementUnit) {
		entityCache.removeResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitImpl.class, cpMeasurementUnit.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPMeasurementUnitModelImpl)cpMeasurementUnit,
			true);
	}

	@Override
	public void clearCache(List<CPMeasurementUnit> cpMeasurementUnits) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPMeasurementUnit cpMeasurementUnit : cpMeasurementUnits) {
			entityCache.removeResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
				CPMeasurementUnitImpl.class, cpMeasurementUnit.getPrimaryKey());

			clearUniqueFindersCache((CPMeasurementUnitModelImpl)cpMeasurementUnit,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPMeasurementUnitModelImpl cpMeasurementUnitModelImpl) {
		Object[] args = new Object[] {
				cpMeasurementUnitModelImpl.getUuid(),
				cpMeasurementUnitModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpMeasurementUnitModelImpl, false);

		args = new Object[] {
				cpMeasurementUnitModelImpl.getGroupId(),
				cpMeasurementUnitModelImpl.getKey(),
				cpMeasurementUnitModelImpl.getType()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_K_T, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_K_T, args,
			cpMeasurementUnitModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPMeasurementUnitModelImpl cpMeasurementUnitModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpMeasurementUnitModelImpl.getUuid(),
					cpMeasurementUnitModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpMeasurementUnitModelImpl.getOriginalUuid(),
					cpMeasurementUnitModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpMeasurementUnitModelImpl.getGroupId(),
					cpMeasurementUnitModelImpl.getKey(),
					cpMeasurementUnitModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_K_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_K_T, args);
		}

		if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_K_T.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpMeasurementUnitModelImpl.getOriginalGroupId(),
					cpMeasurementUnitModelImpl.getOriginalKey(),
					cpMeasurementUnitModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_K_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_K_T, args);
		}
	}

	/**
	 * Creates a new cp measurement unit with the primary key. Does not add the cp measurement unit to the database.
	 *
	 * @param CPMeasurementUnitId the primary key for the new cp measurement unit
	 * @return the new cp measurement unit
	 */
	@Override
	public CPMeasurementUnit create(long CPMeasurementUnitId) {
		CPMeasurementUnit cpMeasurementUnit = new CPMeasurementUnitImpl();

		cpMeasurementUnit.setNew(true);
		cpMeasurementUnit.setPrimaryKey(CPMeasurementUnitId);

		String uuid = PortalUUIDUtil.generate();

		cpMeasurementUnit.setUuid(uuid);

		cpMeasurementUnit.setCompanyId(companyProvider.getCompanyId());

		return cpMeasurementUnit;
	}

	/**
	 * Removes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit that was removed
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit remove(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException {
		return remove((Serializable)CPMeasurementUnitId);
	}

	/**
	 * Removes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp measurement unit
	 * @return the cp measurement unit that was removed
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit remove(Serializable primaryKey)
		throws NoSuchCPMeasurementUnitException {
		Session session = null;

		try {
			session = openSession();

			CPMeasurementUnit cpMeasurementUnit = (CPMeasurementUnit)session.get(CPMeasurementUnitImpl.class,
					primaryKey);

			if (cpMeasurementUnit == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPMeasurementUnitException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpMeasurementUnit);
		}
		catch (NoSuchCPMeasurementUnitException nsee) {
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
	protected CPMeasurementUnit removeImpl(CPMeasurementUnit cpMeasurementUnit) {
		cpMeasurementUnit = toUnwrappedModel(cpMeasurementUnit);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpMeasurementUnit)) {
				cpMeasurementUnit = (CPMeasurementUnit)session.get(CPMeasurementUnitImpl.class,
						cpMeasurementUnit.getPrimaryKeyObj());
			}

			if (cpMeasurementUnit != null) {
				session.delete(cpMeasurementUnit);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpMeasurementUnit != null) {
			clearCache(cpMeasurementUnit);
		}

		return cpMeasurementUnit;
	}

	@Override
	public CPMeasurementUnit updateImpl(CPMeasurementUnit cpMeasurementUnit) {
		cpMeasurementUnit = toUnwrappedModel(cpMeasurementUnit);

		boolean isNew = cpMeasurementUnit.isNew();

		CPMeasurementUnitModelImpl cpMeasurementUnitModelImpl = (CPMeasurementUnitModelImpl)cpMeasurementUnit;

		if (Validator.isNull(cpMeasurementUnit.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpMeasurementUnit.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpMeasurementUnit.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpMeasurementUnit.setCreateDate(now);
			}
			else {
				cpMeasurementUnit.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpMeasurementUnitModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpMeasurementUnit.setModifiedDate(now);
			}
			else {
				cpMeasurementUnit.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpMeasurementUnit.isNew()) {
				session.save(cpMeasurementUnit);

				cpMeasurementUnit.setNew(false);
			}
			else {
				cpMeasurementUnit = (CPMeasurementUnit)session.merge(cpMeasurementUnit);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPMeasurementUnitModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cpMeasurementUnitModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpMeasurementUnitModelImpl.getUuid(),
					cpMeasurementUnitModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { cpMeasurementUnitModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					cpMeasurementUnitModelImpl.getGroupId(),
					cpMeasurementUnitModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
				args);

			args = new Object[] {
					cpMeasurementUnitModelImpl.getGroupId(),
					cpMeasurementUnitModelImpl.getKey(),
					cpMeasurementUnitModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_ORK_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ORK_T,
				args);

			args = new Object[] {
					cpMeasurementUnitModelImpl.getGroupId(),
					cpMeasurementUnitModelImpl.getPrimary(),
					cpMeasurementUnitModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_P_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpMeasurementUnitModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpMeasurementUnitModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpMeasurementUnitModelImpl.getOriginalUuid(),
						cpMeasurementUnitModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpMeasurementUnitModelImpl.getUuid(),
						cpMeasurementUnitModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpMeasurementUnitModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { cpMeasurementUnitModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpMeasurementUnitModelImpl.getOriginalGroupId(),
						cpMeasurementUnitModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						cpMeasurementUnitModelImpl.getGroupId(),
						cpMeasurementUnitModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ORK_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpMeasurementUnitModelImpl.getOriginalGroupId(),
						cpMeasurementUnitModelImpl.getOriginalKey(),
						cpMeasurementUnitModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_ORK_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ORK_T,
					args);

				args = new Object[] {
						cpMeasurementUnitModelImpl.getGroupId(),
						cpMeasurementUnitModelImpl.getKey(),
						cpMeasurementUnitModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_ORK_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_ORK_T,
					args);
			}

			if ((cpMeasurementUnitModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpMeasurementUnitModelImpl.getOriginalGroupId(),
						cpMeasurementUnitModelImpl.getOriginalPrimary(),
						cpMeasurementUnitModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_P_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T,
					args);

				args = new Object[] {
						cpMeasurementUnitModelImpl.getGroupId(),
						cpMeasurementUnitModelImpl.getPrimary(),
						cpMeasurementUnitModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_P_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_T,
					args);
			}
		}

		entityCache.putResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
			CPMeasurementUnitImpl.class, cpMeasurementUnit.getPrimaryKey(),
			cpMeasurementUnit, false);

		clearUniqueFindersCache(cpMeasurementUnitModelImpl, false);
		cacheUniqueFindersCache(cpMeasurementUnitModelImpl);

		cpMeasurementUnit.resetOriginalValues();

		return cpMeasurementUnit;
	}

	protected CPMeasurementUnit toUnwrappedModel(
		CPMeasurementUnit cpMeasurementUnit) {
		if (cpMeasurementUnit instanceof CPMeasurementUnitImpl) {
			return cpMeasurementUnit;
		}

		CPMeasurementUnitImpl cpMeasurementUnitImpl = new CPMeasurementUnitImpl();

		cpMeasurementUnitImpl.setNew(cpMeasurementUnit.isNew());
		cpMeasurementUnitImpl.setPrimaryKey(cpMeasurementUnit.getPrimaryKey());

		cpMeasurementUnitImpl.setUuid(cpMeasurementUnit.getUuid());
		cpMeasurementUnitImpl.setCPMeasurementUnitId(cpMeasurementUnit.getCPMeasurementUnitId());
		cpMeasurementUnitImpl.setGroupId(cpMeasurementUnit.getGroupId());
		cpMeasurementUnitImpl.setCompanyId(cpMeasurementUnit.getCompanyId());
		cpMeasurementUnitImpl.setUserId(cpMeasurementUnit.getUserId());
		cpMeasurementUnitImpl.setUserName(cpMeasurementUnit.getUserName());
		cpMeasurementUnitImpl.setCreateDate(cpMeasurementUnit.getCreateDate());
		cpMeasurementUnitImpl.setModifiedDate(cpMeasurementUnit.getModifiedDate());
		cpMeasurementUnitImpl.setName(cpMeasurementUnit.getName());
		cpMeasurementUnitImpl.setKey(cpMeasurementUnit.getKey());
		cpMeasurementUnitImpl.setRate(cpMeasurementUnit.getRate());
		cpMeasurementUnitImpl.setPrimary(cpMeasurementUnit.isPrimary());
		cpMeasurementUnitImpl.setPriority(cpMeasurementUnit.getPriority());
		cpMeasurementUnitImpl.setType(cpMeasurementUnit.getType());
		cpMeasurementUnitImpl.setLastPublishDate(cpMeasurementUnit.getLastPublishDate());

		return cpMeasurementUnitImpl;
	}

	/**
	 * Returns the cp measurement unit with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp measurement unit
	 * @return the cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPMeasurementUnitException {
		CPMeasurementUnit cpMeasurementUnit = fetchByPrimaryKey(primaryKey);

		if (cpMeasurementUnit == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPMeasurementUnitException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit with the primary key or throws a {@link NoSuchCPMeasurementUnitException} if it could not be found.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit findByPrimaryKey(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException {
		return findByPrimaryKey((Serializable)CPMeasurementUnitId);
	}

	/**
	 * Returns the cp measurement unit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp measurement unit
	 * @return the cp measurement unit, or <code>null</code> if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
				CPMeasurementUnitImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPMeasurementUnit cpMeasurementUnit = (CPMeasurementUnit)serializable;

		if (cpMeasurementUnit == null) {
			Session session = null;

			try {
				session = openSession();

				cpMeasurementUnit = (CPMeasurementUnit)session.get(CPMeasurementUnitImpl.class,
						primaryKey);

				if (cpMeasurementUnit != null) {
					cacheResult(cpMeasurementUnit);
				}
				else {
					entityCache.putResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
						CPMeasurementUnitImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
					CPMeasurementUnitImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpMeasurementUnit;
	}

	/**
	 * Returns the cp measurement unit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit, or <code>null</code> if a cp measurement unit with the primary key could not be found
	 */
	@Override
	public CPMeasurementUnit fetchByPrimaryKey(long CPMeasurementUnitId) {
		return fetchByPrimaryKey((Serializable)CPMeasurementUnitId);
	}

	@Override
	public Map<Serializable, CPMeasurementUnit> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPMeasurementUnit> map = new HashMap<Serializable, CPMeasurementUnit>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPMeasurementUnit cpMeasurementUnit = fetchByPrimaryKey(primaryKey);

			if (cpMeasurementUnit != null) {
				map.put(primaryKey, cpMeasurementUnit);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
					CPMeasurementUnitImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPMeasurementUnit)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPMEASUREMENTUNIT_WHERE_PKS_IN);

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

			for (CPMeasurementUnit cpMeasurementUnit : (List<CPMeasurementUnit>)q.list()) {
				map.put(cpMeasurementUnit.getPrimaryKeyObj(), cpMeasurementUnit);

				cacheResult(cpMeasurementUnit);

				uncachedPrimaryKeys.remove(cpMeasurementUnit.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPMeasurementUnitModelImpl.ENTITY_CACHE_ENABLED,
					CPMeasurementUnitImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp measurement units.
	 *
	 * @return the cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp measurement units.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp measurement units.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll(int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp measurement units
	 */
	@Override
	public List<CPMeasurementUnit> findAll(int start, int end,
		OrderByComparator<CPMeasurementUnit> orderByComparator,
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

		List<CPMeasurementUnit> list = null;

		if (retrieveFromCache) {
			list = (List<CPMeasurementUnit>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPMEASUREMENTUNIT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPMEASUREMENTUNIT;

				if (pagination) {
					sql = sql.concat(CPMeasurementUnitModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPMeasurementUnit>)QueryUtil.list(q,
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
	 * Removes all the cp measurement units from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPMeasurementUnit cpMeasurementUnit : findAll()) {
			remove(cpMeasurementUnit);
		}
	}

	/**
	 * Returns the number of cp measurement units.
	 *
	 * @return the number of cp measurement units
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPMEASUREMENTUNIT);

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
		return CPMeasurementUnitModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp measurement unit persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPMeasurementUnitImpl.class.getName());
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
	private static final String _SQL_SELECT_CPMEASUREMENTUNIT = "SELECT cpMeasurementUnit FROM CPMeasurementUnit cpMeasurementUnit";
	private static final String _SQL_SELECT_CPMEASUREMENTUNIT_WHERE_PKS_IN = "SELECT cpMeasurementUnit FROM CPMeasurementUnit cpMeasurementUnit WHERE CPMeasurementUnitId IN (";
	private static final String _SQL_SELECT_CPMEASUREMENTUNIT_WHERE = "SELECT cpMeasurementUnit FROM CPMeasurementUnit cpMeasurementUnit WHERE ";
	private static final String _SQL_COUNT_CPMEASUREMENTUNIT = "SELECT COUNT(cpMeasurementUnit) FROM CPMeasurementUnit cpMeasurementUnit";
	private static final String _SQL_COUNT_CPMEASUREMENTUNIT_WHERE = "SELECT COUNT(cpMeasurementUnit) FROM CPMeasurementUnit cpMeasurementUnit WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpMeasurementUnit.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPMeasurementUnit exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPMeasurementUnit exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPMeasurementUnitPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "key", "primary", "type"
			});
}