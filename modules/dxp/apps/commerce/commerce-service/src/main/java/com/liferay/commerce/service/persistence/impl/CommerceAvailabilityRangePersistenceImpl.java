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

import com.liferay.commerce.exception.NoSuchAvailabilityRangeException;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.model.impl.CommerceAvailabilityRangeImpl;
import com.liferay.commerce.model.impl.CommerceAvailabilityRangeModelImpl;
import com.liferay.commerce.service.persistence.CommerceAvailabilityRangePersistence;

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
 * The persistence implementation for the commerce availability range service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRangePersistence
 * @see com.liferay.commerce.service.persistence.CommerceAvailabilityRangeUtil
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangePersistenceImpl
	extends BasePersistenceImpl<CommerceAvailabilityRange>
	implements CommerceAvailabilityRangePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceAvailabilityRangeUtil} to access the commerce availability range persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceAvailabilityRangeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceAvailabilityRangeModelImpl.UUID_COLUMN_BITMASK |
			CommerceAvailabilityRangeModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the commerce availability ranges where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability ranges where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @return the range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid(String uuid, int start,
		int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
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

		List<CommerceAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAvailabilityRange commerceAvailabilityRange : list) {
					if (!Objects.equals(uuid,
								commerceAvailabilityRange.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE);

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
				query.append(CommerceAvailabilityRangeModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
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
	 * Returns the first commerce availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceAvailabilityRange != null) {
			return commerceAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the first commerce availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		List<CommerceAvailabilityRange> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceAvailabilityRange != null) {
			return commerceAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the last commerce availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceAvailabilityRange> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where uuid = &#63;.
	 *
	 * @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange[] findByUuid_PrevAndNext(
		long commerceAvailabilityRangeId, String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = findByPrimaryKey(commerceAvailabilityRangeId);

		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityRange[] array = new CommerceAvailabilityRangeImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commerceAvailabilityRange, uuid, orderByComparator, true);

			array[1] = commerceAvailabilityRange;

			array[2] = getByUuid_PrevAndNext(session,
					commerceAvailabilityRange, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAvailabilityRange getByUuid_PrevAndNext(Session session,
		CommerceAvailabilityRange commerceAvailabilityRange, String uuid,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE);

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
			query.append(CommerceAvailabilityRangeModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAvailabilityRange);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAvailabilityRange> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce availability ranges where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceAvailabilityRange commerceAvailabilityRange : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAvailabilityRange);
		}
	}

	/**
	 * Returns the number of commerce availability ranges where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce availability ranges
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYRANGE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceAvailabilityRange.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceAvailabilityRange.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceAvailabilityRange.uuid IS NULL OR commerceAvailabilityRange.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceAvailabilityRangeModelImpl.UUID_COLUMN_BITMASK |
			CommerceAvailabilityRangeModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAvailabilityRangeException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByUUID_G(String uuid, long groupId)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByUUID_G(uuid,
				groupId);

		if (commerceAvailabilityRange == null) {
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

			throw new NoSuchAvailabilityRangeException(msg.toString());
		}

		return commerceAvailabilityRange;
	}

	/**
	 * Returns the commerce availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceAvailabilityRange) {
			CommerceAvailabilityRange commerceAvailabilityRange = (CommerceAvailabilityRange)result;

			if (!Objects.equals(uuid, commerceAvailabilityRange.getUuid()) ||
					(groupId != commerceAvailabilityRange.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE);

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

				List<CommerceAvailabilityRange> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceAvailabilityRange commerceAvailabilityRange = list.get(0);

					result = commerceAvailabilityRange;

					cacheResult(commerceAvailabilityRange);

					if ((commerceAvailabilityRange.getUuid() == null) ||
							!commerceAvailabilityRange.getUuid().equals(uuid) ||
							(commerceAvailabilityRange.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceAvailabilityRange);
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
			return (CommerceAvailabilityRange)result;
		}
	}

	/**
	 * Removes the commerce availability range where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce availability range that was removed
	 */
	@Override
	public CommerceAvailabilityRange removeByUUID_G(String uuid, long groupId)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = findByUUID_G(uuid,
				groupId);

		return remove(commerceAvailabilityRange);
	}

	/**
	 * Returns the number of commerce availability ranges where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce availability ranges
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYRANGE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceAvailabilityRange.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceAvailabilityRange.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceAvailabilityRange.uuid IS NULL OR commerceAvailabilityRange.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceAvailabilityRange.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceAvailabilityRangeModelImpl.UUID_COLUMN_BITMASK |
			CommerceAvailabilityRangeModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceAvailabilityRangeModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @return the range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
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

		List<CommerceAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAvailabilityRange commerceAvailabilityRange : list) {
					if (!Objects.equals(uuid,
								commerceAvailabilityRange.getUuid()) ||
							(companyId != commerceAvailabilityRange.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE);

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
				query.append(CommerceAvailabilityRangeModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
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
	 * Returns the first commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceAvailabilityRange != null) {
			return commerceAvailabilityRange;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the first commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		List<CommerceAvailabilityRange> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceAvailabilityRange != null) {
			return commerceAvailabilityRange;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the last commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceAvailabilityRange> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange[] findByUuid_C_PrevAndNext(
		long commerceAvailabilityRangeId, String uuid, long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = findByPrimaryKey(commerceAvailabilityRangeId);

		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityRange[] array = new CommerceAvailabilityRangeImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commerceAvailabilityRange, uuid, companyId,
					orderByComparator, true);

			array[1] = commerceAvailabilityRange;

			array[2] = getByUuid_C_PrevAndNext(session,
					commerceAvailabilityRange, uuid, companyId,
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

	protected CommerceAvailabilityRange getByUuid_C_PrevAndNext(
		Session session, CommerceAvailabilityRange commerceAvailabilityRange,
		String uuid, long companyId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE);

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
			query.append(CommerceAvailabilityRangeModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAvailabilityRange);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAvailabilityRange> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce availability ranges where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceAvailabilityRange commerceAvailabilityRange : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAvailabilityRange);
		}
	}

	/**
	 * Returns the number of commerce availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce availability ranges
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYRANGE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceAvailabilityRange.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceAvailabilityRange.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceAvailabilityRange.uuid IS NULL OR commerceAvailabilityRange.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceAvailabilityRange.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceAvailabilityRangeModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceAvailabilityRangeModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce availability ranges where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability ranges where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @return the range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
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

		List<CommerceAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAvailabilityRange commerceAvailabilityRange : list) {
					if ((groupId != commerceAvailabilityRange.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAvailabilityRangeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
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
	 * Returns the first commerce availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByGroupId_First(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceAvailabilityRange != null) {
			return commerceAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the first commerce availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		List<CommerceAvailabilityRange> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByGroupId_Last(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceAvailabilityRange != null) {
			return commerceAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the last commerce availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability range, or <code>null</code> if a matching commerce availability range could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceAvailabilityRange> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce availability ranges before and after the current commerce availability range in the ordered set where groupId = &#63;.
	 *
	 * @param commerceAvailabilityRangeId the primary key of the current commerce availability range
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange[] findByGroupId_PrevAndNext(
		long commerceAvailabilityRangeId, long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = findByPrimaryKey(commerceAvailabilityRangeId);

		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityRange[] array = new CommerceAvailabilityRangeImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceAvailabilityRange, groupId, orderByComparator, true);

			array[1] = commerceAvailabilityRange;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceAvailabilityRange, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAvailabilityRange getByGroupId_PrevAndNext(
		Session session, CommerceAvailabilityRange commerceAvailabilityRange,
		long groupId,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE);

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
			query.append(CommerceAvailabilityRangeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAvailabilityRange);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAvailabilityRange> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce availability ranges where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceAvailabilityRange commerceAvailabilityRange : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAvailabilityRange);
		}
	}

	/**
	 * Returns the number of commerce availability ranges where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce availability ranges
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYRANGE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceAvailabilityRange.groupId = ?";

	public CommerceAvailabilityRangePersistenceImpl() {
		setModelClass(CommerceAvailabilityRange.class);

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
	 * Caches the commerce availability range in the entity cache if it is enabled.
	 *
	 * @param commerceAvailabilityRange the commerce availability range
	 */
	@Override
	public void cacheResult(CommerceAvailabilityRange commerceAvailabilityRange) {
		entityCache.putResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			commerceAvailabilityRange.getPrimaryKey(), commerceAvailabilityRange);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceAvailabilityRange.getUuid(),
				commerceAvailabilityRange.getGroupId()
			}, commerceAvailabilityRange);

		commerceAvailabilityRange.resetOriginalValues();
	}

	/**
	 * Caches the commerce availability ranges in the entity cache if it is enabled.
	 *
	 * @param commerceAvailabilityRanges the commerce availability ranges
	 */
	@Override
	public void cacheResult(
		List<CommerceAvailabilityRange> commerceAvailabilityRanges) {
		for (CommerceAvailabilityRange commerceAvailabilityRange : commerceAvailabilityRanges) {
			if (entityCache.getResult(
						CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAvailabilityRangeImpl.class,
						commerceAvailabilityRange.getPrimaryKey()) == null) {
				cacheResult(commerceAvailabilityRange);
			}
			else {
				commerceAvailabilityRange.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce availability ranges.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAvailabilityRangeImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce availability range.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceAvailabilityRange commerceAvailabilityRange) {
		entityCache.removeResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			commerceAvailabilityRange.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceAvailabilityRangeModelImpl)commerceAvailabilityRange,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceAvailabilityRange> commerceAvailabilityRanges) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceAvailabilityRange commerceAvailabilityRange : commerceAvailabilityRanges) {
			entityCache.removeResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAvailabilityRangeImpl.class,
				commerceAvailabilityRange.getPrimaryKey());

			clearUniqueFindersCache((CommerceAvailabilityRangeModelImpl)commerceAvailabilityRange,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceAvailabilityRangeModelImpl commerceAvailabilityRangeModelImpl) {
		Object[] args = new Object[] {
				commerceAvailabilityRangeModelImpl.getUuid(),
				commerceAvailabilityRangeModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceAvailabilityRangeModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceAvailabilityRangeModelImpl commerceAvailabilityRangeModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceAvailabilityRangeModelImpl.getUuid(),
					commerceAvailabilityRangeModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceAvailabilityRangeModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceAvailabilityRangeModelImpl.getOriginalUuid(),
					commerceAvailabilityRangeModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new commerce availability range with the primary key. Does not add the commerce availability range to the database.
	 *
	 * @param commerceAvailabilityRangeId the primary key for the new commerce availability range
	 * @return the new commerce availability range
	 */
	@Override
	public CommerceAvailabilityRange create(long commerceAvailabilityRangeId) {
		CommerceAvailabilityRange commerceAvailabilityRange = new CommerceAvailabilityRangeImpl();

		commerceAvailabilityRange.setNew(true);
		commerceAvailabilityRange.setPrimaryKey(commerceAvailabilityRangeId);

		String uuid = PortalUUIDUtil.generate();

		commerceAvailabilityRange.setUuid(uuid);

		commerceAvailabilityRange.setCompanyId(companyProvider.getCompanyId());

		return commerceAvailabilityRange;
	}

	/**
	 * Removes the commerce availability range with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAvailabilityRangeId the primary key of the commerce availability range
	 * @return the commerce availability range that was removed
	 * @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange remove(long commerceAvailabilityRangeId)
		throws NoSuchAvailabilityRangeException {
		return remove((Serializable)commerceAvailabilityRangeId);
	}

	/**
	 * Removes the commerce availability range with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce availability range
	 * @return the commerce availability range that was removed
	 * @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange remove(Serializable primaryKey)
		throws NoSuchAvailabilityRangeException {
		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityRange commerceAvailabilityRange = (CommerceAvailabilityRange)session.get(CommerceAvailabilityRangeImpl.class,
					primaryKey);

			if (commerceAvailabilityRange == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAvailabilityRangeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceAvailabilityRange);
		}
		catch (NoSuchAvailabilityRangeException nsee) {
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
	protected CommerceAvailabilityRange removeImpl(
		CommerceAvailabilityRange commerceAvailabilityRange) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAvailabilityRange)) {
				commerceAvailabilityRange = (CommerceAvailabilityRange)session.get(CommerceAvailabilityRangeImpl.class,
						commerceAvailabilityRange.getPrimaryKeyObj());
			}

			if (commerceAvailabilityRange != null) {
				session.delete(commerceAvailabilityRange);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceAvailabilityRange != null) {
			clearCache(commerceAvailabilityRange);
		}

		return commerceAvailabilityRange;
	}

	@Override
	public CommerceAvailabilityRange updateImpl(
		CommerceAvailabilityRange commerceAvailabilityRange) {
		boolean isNew = commerceAvailabilityRange.isNew();

		if (!(commerceAvailabilityRange instanceof CommerceAvailabilityRangeModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceAvailabilityRange.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceAvailabilityRange);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceAvailabilityRange proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceAvailabilityRange implementation " +
				commerceAvailabilityRange.getClass());
		}

		CommerceAvailabilityRangeModelImpl commerceAvailabilityRangeModelImpl = (CommerceAvailabilityRangeModelImpl)commerceAvailabilityRange;

		if (Validator.isNull(commerceAvailabilityRange.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceAvailabilityRange.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAvailabilityRange.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAvailabilityRange.setCreateDate(now);
			}
			else {
				commerceAvailabilityRange.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceAvailabilityRangeModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAvailabilityRange.setModifiedDate(now);
			}
			else {
				commerceAvailabilityRange.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceAvailabilityRange.isNew()) {
				session.save(commerceAvailabilityRange);

				commerceAvailabilityRange.setNew(false);
			}
			else {
				commerceAvailabilityRange = (CommerceAvailabilityRange)session.merge(commerceAvailabilityRange);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceAvailabilityRangeModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceAvailabilityRangeModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceAvailabilityRangeModelImpl.getUuid(),
					commerceAvailabilityRangeModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { commerceAvailabilityRangeModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceAvailabilityRangeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAvailabilityRangeModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { commerceAvailabilityRangeModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceAvailabilityRangeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAvailabilityRangeModelImpl.getOriginalUuid(),
						commerceAvailabilityRangeModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceAvailabilityRangeModelImpl.getUuid(),
						commerceAvailabilityRangeModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceAvailabilityRangeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAvailabilityRangeModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceAvailabilityRangeModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityRangeImpl.class,
			commerceAvailabilityRange.getPrimaryKey(),
			commerceAvailabilityRange, false);

		clearUniqueFindersCache(commerceAvailabilityRangeModelImpl, false);
		cacheUniqueFindersCache(commerceAvailabilityRangeModelImpl);

		commerceAvailabilityRange.resetOriginalValues();

		return commerceAvailabilityRange;
	}

	/**
	 * Returns the commerce availability range with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce availability range
	 * @return the commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAvailabilityRangeException {
		CommerceAvailabilityRange commerceAvailabilityRange = fetchByPrimaryKey(primaryKey);

		if (commerceAvailabilityRange == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAvailabilityRangeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceAvailabilityRange;
	}

	/**
	 * Returns the commerce availability range with the primary key or throws a {@link NoSuchAvailabilityRangeException} if it could not be found.
	 *
	 * @param commerceAvailabilityRangeId the primary key of the commerce availability range
	 * @return the commerce availability range
	 * @throws NoSuchAvailabilityRangeException if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange findByPrimaryKey(
		long commerceAvailabilityRangeId)
		throws NoSuchAvailabilityRangeException {
		return findByPrimaryKey((Serializable)commerceAvailabilityRangeId);
	}

	/**
	 * Returns the commerce availability range with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce availability range
	 * @return the commerce availability range, or <code>null</code> if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAvailabilityRangeImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceAvailabilityRange commerceAvailabilityRange = (CommerceAvailabilityRange)serializable;

		if (commerceAvailabilityRange == null) {
			Session session = null;

			try {
				session = openSession();

				commerceAvailabilityRange = (CommerceAvailabilityRange)session.get(CommerceAvailabilityRangeImpl.class,
						primaryKey);

				if (commerceAvailabilityRange != null) {
					cacheResult(commerceAvailabilityRange);
				}
				else {
					entityCache.putResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAvailabilityRangeImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAvailabilityRangeImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceAvailabilityRange;
	}

	/**
	 * Returns the commerce availability range with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAvailabilityRangeId the primary key of the commerce availability range
	 * @return the commerce availability range, or <code>null</code> if a commerce availability range with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityRange fetchByPrimaryKey(
		long commerceAvailabilityRangeId) {
		return fetchByPrimaryKey((Serializable)commerceAvailabilityRangeId);
	}

	@Override
	public Map<Serializable, CommerceAvailabilityRange> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceAvailabilityRange> map = new HashMap<Serializable, CommerceAvailabilityRange>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceAvailabilityRange commerceAvailabilityRange = fetchByPrimaryKey(primaryKey);

			if (commerceAvailabilityRange != null) {
				map.put(primaryKey, commerceAvailabilityRange);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAvailabilityRangeImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceAvailabilityRange)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE_PKS_IN);

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

			for (CommerceAvailabilityRange commerceAvailabilityRange : (List<CommerceAvailabilityRange>)q.list()) {
				map.put(commerceAvailabilityRange.getPrimaryKeyObj(),
					commerceAvailabilityRange);

				cacheResult(commerceAvailabilityRange);

				uncachedPrimaryKeys.remove(commerceAvailabilityRange.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAvailabilityRangeImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce availability ranges.
	 *
	 * @return the commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability ranges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @return the range of commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability ranges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability ranges
	 * @param end the upper bound of the range of commerce availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce availability ranges
	 */
	@Override
	public List<CommerceAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator,
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

		List<CommerceAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEAVAILABILITYRANGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEAVAILABILITYRANGE;

				if (pagination) {
					sql = sql.concat(CommerceAvailabilityRangeModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityRange>)QueryUtil.list(q,
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
	 * Removes all the commerce availability ranges from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAvailabilityRange commerceAvailabilityRange : findAll()) {
			remove(commerceAvailabilityRange);
		}
	}

	/**
	 * Returns the number of commerce availability ranges.
	 *
	 * @return the number of commerce availability ranges
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEAVAILABILITYRANGE);

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
		return CommerceAvailabilityRangeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce availability range persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceAvailabilityRangeImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEAVAILABILITYRANGE = "SELECT commerceAvailabilityRange FROM CommerceAvailabilityRange commerceAvailabilityRange";
	private static final String _SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE_PKS_IN =
		"SELECT commerceAvailabilityRange FROM CommerceAvailabilityRange commerceAvailabilityRange WHERE commerceAvailabilityRangeId IN (";
	private static final String _SQL_SELECT_COMMERCEAVAILABILITYRANGE_WHERE = "SELECT commerceAvailabilityRange FROM CommerceAvailabilityRange commerceAvailabilityRange WHERE ";
	private static final String _SQL_COUNT_COMMERCEAVAILABILITYRANGE = "SELECT COUNT(commerceAvailabilityRange) FROM CommerceAvailabilityRange commerceAvailabilityRange";
	private static final String _SQL_COUNT_COMMERCEAVAILABILITYRANGE_WHERE = "SELECT COUNT(commerceAvailabilityRange) FROM CommerceAvailabilityRange commerceAvailabilityRange WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceAvailabilityRange.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceAvailabilityRange exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceAvailabilityRange exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceAvailabilityRangePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}