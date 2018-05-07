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

import com.liferay.commerce.product.exception.NoSuchCPOptionValueException;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.impl.CPOptionValueImpl;
import com.liferay.commerce.product.model.impl.CPOptionValueModelImpl;
import com.liferay.commerce.product.service.persistence.CPOptionValuePersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

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
 * The persistence implementation for the cp option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPOptionValuePersistence
 * @see com.liferay.commerce.product.service.persistence.CPOptionValueUtil
 * @generated
 */
@ProviderType
public class CPOptionValuePersistenceImpl extends BasePersistenceImpl<CPOptionValue>
	implements CPOptionValuePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPOptionValueUtil} to access the cp option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPOptionValueImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			CPOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
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

		List<CPOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CPOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if (!Objects.equals(uuid, cpOptionValue.getUuid())) {
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

			query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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
				query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first cp option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByUuid_First(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByUuid_First(uuid, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUuid_First(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator) {
		List<CPOptionValue> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByUuid_Last(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByUuid_Last(uuid, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUuid_Last(String uuid,
		OrderByComparator<CPOptionValue> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp option values before and after the current cp option value in the ordered set where uuid = &#63;.
	 *
	 * @param CPOptionValueId the primary key of the current cp option value
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option value
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue[] findByUuid_PrevAndNext(long CPOptionValueId,
		String uuid, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpOptionValue, uuid,
					orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByUuid_PrevAndNext(session, cpOptionValue, uuid,
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

	protected CPOptionValue getByUuid_PrevAndNext(Session session,
		CPOptionValue cpOptionValue, String uuid,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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
			query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp option values where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPOptionValue cpOptionValue : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cpOptionValue);
		}
	}

	/**
	 * Returns the number of cp option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpOptionValue.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpOptionValue.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpOptionValue.uuid IS NULL OR cpOptionValue.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CPOptionValueModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp option value where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByUUID_G(uuid, groupId);

		if (cpOptionValue == null) {
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

			throw new NoSuchCPOptionValueException(msg.toString());
		}

		return cpOptionValue;
	}

	/**
	 * Returns the cp option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPOptionValue) {
			CPOptionValue cpOptionValue = (CPOptionValue)result;

			if (!Objects.equals(uuid, cpOptionValue.getUuid()) ||
					(groupId != cpOptionValue.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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

				List<CPOptionValue> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPOptionValue cpOptionValue = list.get(0);

					result = cpOptionValue;

					cacheResult(cpOptionValue);

					if ((cpOptionValue.getUuid() == null) ||
							!cpOptionValue.getUuid().equals(uuid) ||
							(cpOptionValue.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpOptionValue);
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
			return (CPOptionValue)result;
		}
	}

	/**
	 * Removes the cp option value where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp option value that was removed
	 */
	@Override
	public CPOptionValue removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = findByUUID_G(uuid, groupId);

		return remove(cpOptionValue);
	}

	/**
	 * Returns the number of cp option values where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpOptionValue.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpOptionValue.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpOptionValue.uuid IS NULL OR cpOptionValue.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpOptionValue.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CPOptionValueModelImpl.COMPANYID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator,
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

		List<CPOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CPOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if (!Objects.equals(uuid, cpOptionValue.getUuid()) ||
							(companyId != cpOptionValue.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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
				query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		List<CPOptionValue> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp option values before and after the current cp option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPOptionValueId the primary key of the current cp option value
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option value
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue[] findByUuid_C_PrevAndNext(long CPOptionValueId,
		String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpOptionValue, uuid,
					companyId, orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByUuid_C_PrevAndNext(session, cpOptionValue, uuid,
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

	protected CPOptionValue getByUuid_C_PrevAndNext(Session session,
		CPOptionValue cpOptionValue, String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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
			query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp option values where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPOptionValue cpOptionValue : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpOptionValue);
		}
	}

	/**
	 * Returns the number of cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpOptionValue.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpOptionValue.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpOptionValue.uuid IS NULL OR cpOptionValue.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpOptionValue.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] { Long.class.getName() },
			CPOptionValueModelImpl.GROUPID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByGroupId(long groupId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
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

		List<CPOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CPOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if ((groupId != cpOptionValue.getGroupId())) {
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

			query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first cp option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByGroupId_First(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByGroupId_First(groupId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByGroupId_First(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		List<CPOptionValue> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByGroupId_Last(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByGroupId_Last(long groupId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp option values before and after the current cp option value in the ordered set where groupId = &#63;.
	 *
	 * @param CPOptionValueId the primary key of the current cp option value
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option value
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue[] findByGroupId_PrevAndNext(long CPOptionValueId,
		long groupId, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, cpOptionValue,
					groupId, orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByGroupId_PrevAndNext(session, cpOptionValue,
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

	protected CPOptionValue getByGroupId_PrevAndNext(Session session,
		CPOptionValue cpOptionValue, long groupId,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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
			query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp option values where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPOptionValue cpOptionValue : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpOptionValue);
		}
	}

	/**
	 * Returns the number of cp option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cpOptionValue.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] { Long.class.getName() },
			CPOptionValueModelImpl.COMPANYID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp option values where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<CPOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CPOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if ((companyId != cpOptionValue.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first cp option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByCompanyId_First(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCompanyId_First(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		List<CPOptionValue> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByCompanyId_Last(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCompanyId_Last(long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp option values before and after the current cp option value in the ordered set where companyId = &#63;.
	 *
	 * @param CPOptionValueId the primary key of the current cp option value
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option value
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue[] findByCompanyId_PrevAndNext(long CPOptionValueId,
		long companyId, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, cpOptionValue,
					companyId, orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByCompanyId_PrevAndNext(session, cpOptionValue,
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

	protected CPOptionValue getByCompanyId_PrevAndNext(Session session,
		CPOptionValue cpOptionValue, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp option values where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CPOptionValue cpOptionValue : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpOptionValue);
		}
	}

	/**
	 * Returns the number of cp option values where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "cpOptionValue.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPOPTIONID =
		new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPOptionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPOPTIONID =
		new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCPOptionId", new String[] { Long.class.getName() },
			CPOptionValueModelImpl.CPOPTIONID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPOPTIONID = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPOptionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp option values where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(long CPOptionId) {
		return findByCPOptionId(CPOptionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(long CPOptionId, int start,
		int end) {
		return findByCPOptionId(CPOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(long CPOptionId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator) {
		return findByCPOptionId(CPOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(long CPOptionId, int start,
		int end, OrderByComparator<CPOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPOPTIONID;
			finderArgs = new Object[] { CPOptionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPOPTIONID;
			finderArgs = new Object[] { CPOptionId, start, end, orderByComparator };
		}

		List<CPOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CPOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if ((CPOptionId != cpOptionValue.getCPOptionId())) {
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

			query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPOptionId);

				if (!pagination) {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first cp option value in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByCPOptionId_First(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByCPOptionId_First(CPOptionId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPOptionId=");
		msg.append(CPOptionId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCPOptionId_First(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		List<CPOptionValue> list = findByCPOptionId(CPOptionId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp option value in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByCPOptionId_Last(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByCPOptionId_Last(CPOptionId,
				orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPOptionId=");
		msg.append(CPOptionId);

		msg.append("}");

		throw new NoSuchCPOptionValueException(msg.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCPOptionId_Last(long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator) {
		int count = countByCPOptionId(CPOptionId);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByCPOptionId(CPOptionId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp option values before and after the current cp option value in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionValueId the primary key of the current cp option value
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp option value
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue[] findByCPOptionId_PrevAndNext(long CPOptionValueId,
		long CPOptionId, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByCPOptionId_PrevAndNext(session, cpOptionValue,
					CPOptionId, orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByCPOptionId_PrevAndNext(session, cpOptionValue,
					CPOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPOptionValue getByCPOptionId_PrevAndNext(Session session,
		CPOptionValue cpOptionValue, long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

		query.append(_FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2);

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
			query.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPOptionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp option values where CPOptionId = &#63; from the database.
	 *
	 * @param CPOptionId the cp option ID
	 */
	@Override
	public void removeByCPOptionId(long CPOptionId) {
		for (CPOptionValue cpOptionValue : findByCPOptionId(CPOptionId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpOptionValue);
		}
	}

	/**
	 * Returns the number of cp option values where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByCPOptionId(long CPOptionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPOPTIONID;

		Object[] finderArgs = new Object[] { CPOptionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPOptionId);

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

	private static final String _FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2 = "cpOptionValue.CPOptionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_K = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] { Long.class.getName(), String.class.getName() },
			CPOptionValueModelImpl.CPOPTIONID_COLUMN_BITMASK |
			CPOptionValueModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the cp option value where CPOptionId = &#63; and key = &#63; or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	 *
	 * @param CPOptionId the cp option ID
	 * @param key the key
	 * @return the matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByC_K(long CPOptionId, String key)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByC_K(CPOptionId, key);

		if (cpOptionValue == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("CPOptionId=");
			msg.append(CPOptionId);

			msg.append(", key=");
			msg.append(key);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPOptionValueException(msg.toString());
		}

		return cpOptionValue;
	}

	/**
	 * Returns the cp option value where CPOptionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPOptionId the cp option ID
	 * @param key the key
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByC_K(long CPOptionId, String key) {
		return fetchByC_K(CPOptionId, key, true);
	}

	/**
	 * Returns the cp option value where CPOptionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPOptionId the cp option ID
	 * @param key the key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByC_K(long CPOptionId, String key,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { CPOptionId, key };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_K,
					finderArgs, this);
		}

		if (result instanceof CPOptionValue) {
			CPOptionValue cpOptionValue = (CPOptionValue)result;

			if ((CPOptionId != cpOptionValue.getCPOptionId()) ||
					!Objects.equals(key, cpOptionValue.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_K_CPOPTIONID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else if (key.equals("")) {
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

				qPos.add(CPOptionId);

				if (bindKey) {
					qPos.add(key);
				}

				List<CPOptionValue> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_K, finderArgs,
						list);
				}
				else {
					CPOptionValue cpOptionValue = list.get(0);

					result = cpOptionValue;

					cacheResult(cpOptionValue);

					if ((cpOptionValue.getCPOptionId() != CPOptionId) ||
							(cpOptionValue.getKey() == null) ||
							!cpOptionValue.getKey().equals(key)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_K,
							finderArgs, cpOptionValue);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_K, finderArgs);

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
			return (CPOptionValue)result;
		}
	}

	/**
	 * Removes the cp option value where CPOptionId = &#63; and key = &#63; from the database.
	 *
	 * @param CPOptionId the cp option ID
	 * @param key the key
	 * @return the cp option value that was removed
	 */
	@Override
	public CPOptionValue removeByC_K(long CPOptionId, String key)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = findByC_K(CPOptionId, key);

		return remove(cpOptionValue);
	}

	/**
	 * Returns the number of cp option values where CPOptionId = &#63; and key = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param key the key
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByC_K(long CPOptionId, String key) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_K;

		Object[] finderArgs = new Object[] { CPOptionId, key };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_K_CPOPTIONID_2);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else if (key.equals("")) {
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

				qPos.add(CPOptionId);

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

	private static final String _FINDER_COLUMN_C_K_CPOPTIONID_2 = "cpOptionValue.CPOptionId = ? AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "cpOptionValue.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "cpOptionValue.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(cpOptionValue.key IS NULL OR cpOptionValue.key = '')";

	public CPOptionValuePersistenceImpl() {
		setModelClass(CPOptionValue.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("key", "key_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp option value in the entity cache if it is enabled.
	 *
	 * @param cpOptionValue the cp option value
	 */
	@Override
	public void cacheResult(CPOptionValue cpOptionValue) {
		entityCache.putResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueImpl.class, cpOptionValue.getPrimaryKey(),
			cpOptionValue);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { cpOptionValue.getUuid(), cpOptionValue.getGroupId() },
			cpOptionValue);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_K,
			new Object[] { cpOptionValue.getCPOptionId(), cpOptionValue.getKey() },
			cpOptionValue);

		cpOptionValue.resetOriginalValues();
	}

	/**
	 * Caches the cp option values in the entity cache if it is enabled.
	 *
	 * @param cpOptionValues the cp option values
	 */
	@Override
	public void cacheResult(List<CPOptionValue> cpOptionValues) {
		for (CPOptionValue cpOptionValue : cpOptionValues) {
			if (entityCache.getResult(
						CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
						CPOptionValueImpl.class, cpOptionValue.getPrimaryKey()) == null) {
				cacheResult(cpOptionValue);
			}
			else {
				cpOptionValue.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp option values.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPOptionValueImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp option value.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPOptionValue cpOptionValue) {
		entityCache.removeResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueImpl.class, cpOptionValue.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPOptionValueModelImpl)cpOptionValue, true);
	}

	@Override
	public void clearCache(List<CPOptionValue> cpOptionValues) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPOptionValue cpOptionValue : cpOptionValues) {
			entityCache.removeResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
				CPOptionValueImpl.class, cpOptionValue.getPrimaryKey());

			clearUniqueFindersCache((CPOptionValueModelImpl)cpOptionValue, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPOptionValueModelImpl cpOptionValueModelImpl) {
		Object[] args = new Object[] {
				cpOptionValueModelImpl.getUuid(),
				cpOptionValueModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpOptionValueModelImpl, false);

		args = new Object[] {
				cpOptionValueModelImpl.getCPOptionId(),
				cpOptionValueModelImpl.getKey()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_K, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_K, args,
			cpOptionValueModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPOptionValueModelImpl cpOptionValueModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpOptionValueModelImpl.getUuid(),
					cpOptionValueModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpOptionValueModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpOptionValueModelImpl.getOriginalUuid(),
					cpOptionValueModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpOptionValueModelImpl.getCPOptionId(),
					cpOptionValueModelImpl.getKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_K, args);
		}

		if ((cpOptionValueModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_K.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpOptionValueModelImpl.getOriginalCPOptionId(),
					cpOptionValueModelImpl.getOriginalKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_K, args);
		}
	}

	/**
	 * Creates a new cp option value with the primary key. Does not add the cp option value to the database.
	 *
	 * @param CPOptionValueId the primary key for the new cp option value
	 * @return the new cp option value
	 */
	@Override
	public CPOptionValue create(long CPOptionValueId) {
		CPOptionValue cpOptionValue = new CPOptionValueImpl();

		cpOptionValue.setNew(true);
		cpOptionValue.setPrimaryKey(CPOptionValueId);

		String uuid = PortalUUIDUtil.generate();

		cpOptionValue.setUuid(uuid);

		cpOptionValue.setCompanyId(companyProvider.getCompanyId());

		return cpOptionValue;
	}

	/**
	 * Removes the cp option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPOptionValueId the primary key of the cp option value
	 * @return the cp option value that was removed
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue remove(long CPOptionValueId)
		throws NoSuchCPOptionValueException {
		return remove((Serializable)CPOptionValueId);
	}

	/**
	 * Removes the cp option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp option value
	 * @return the cp option value that was removed
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue remove(Serializable primaryKey)
		throws NoSuchCPOptionValueException {
		Session session = null;

		try {
			session = openSession();

			CPOptionValue cpOptionValue = (CPOptionValue)session.get(CPOptionValueImpl.class,
					primaryKey);

			if (cpOptionValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPOptionValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpOptionValue);
		}
		catch (NoSuchCPOptionValueException nsee) {
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
	protected CPOptionValue removeImpl(CPOptionValue cpOptionValue) {
		cpOptionValue = toUnwrappedModel(cpOptionValue);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpOptionValue)) {
				cpOptionValue = (CPOptionValue)session.get(CPOptionValueImpl.class,
						cpOptionValue.getPrimaryKeyObj());
			}

			if (cpOptionValue != null) {
				session.delete(cpOptionValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpOptionValue != null) {
			clearCache(cpOptionValue);
		}

		return cpOptionValue;
	}

	@Override
	public CPOptionValue updateImpl(CPOptionValue cpOptionValue) {
		cpOptionValue = toUnwrappedModel(cpOptionValue);

		boolean isNew = cpOptionValue.isNew();

		CPOptionValueModelImpl cpOptionValueModelImpl = (CPOptionValueModelImpl)cpOptionValue;

		if (Validator.isNull(cpOptionValue.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpOptionValue.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpOptionValue.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpOptionValue.setCreateDate(now);
			}
			else {
				cpOptionValue.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cpOptionValueModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpOptionValue.setModifiedDate(now);
			}
			else {
				cpOptionValue.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpOptionValue.isNew()) {
				session.save(cpOptionValue);

				cpOptionValue.setNew(false);
			}
			else {
				cpOptionValue = (CPOptionValue)session.merge(cpOptionValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPOptionValueModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cpOptionValueModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpOptionValueModelImpl.getUuid(),
					cpOptionValueModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { cpOptionValueModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { cpOptionValueModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] { cpOptionValueModelImpl.getCPOptionId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPOPTIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPOPTIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpOptionValueModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpOptionValueModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpOptionValueModelImpl.getOriginalUuid(),
						cpOptionValueModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpOptionValueModelImpl.getUuid(),
						cpOptionValueModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpOptionValueModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { cpOptionValueModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((cpOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpOptionValueModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { cpOptionValueModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((cpOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPOPTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpOptionValueModelImpl.getOriginalCPOptionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPOPTIONID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPOPTIONID,
					args);

				args = new Object[] { cpOptionValueModelImpl.getCPOptionId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPOPTIONID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPOPTIONID,
					args);
			}
		}

		entityCache.putResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueImpl.class, cpOptionValue.getPrimaryKey(),
			cpOptionValue, false);

		clearUniqueFindersCache(cpOptionValueModelImpl, false);
		cacheUniqueFindersCache(cpOptionValueModelImpl);

		cpOptionValue.resetOriginalValues();

		return cpOptionValue;
	}

	protected CPOptionValue toUnwrappedModel(CPOptionValue cpOptionValue) {
		if (cpOptionValue instanceof CPOptionValueImpl) {
			return cpOptionValue;
		}

		CPOptionValueImpl cpOptionValueImpl = new CPOptionValueImpl();

		cpOptionValueImpl.setNew(cpOptionValue.isNew());
		cpOptionValueImpl.setPrimaryKey(cpOptionValue.getPrimaryKey());

		cpOptionValueImpl.setUuid(cpOptionValue.getUuid());
		cpOptionValueImpl.setCPOptionValueId(cpOptionValue.getCPOptionValueId());
		cpOptionValueImpl.setGroupId(cpOptionValue.getGroupId());
		cpOptionValueImpl.setCompanyId(cpOptionValue.getCompanyId());
		cpOptionValueImpl.setUserId(cpOptionValue.getUserId());
		cpOptionValueImpl.setUserName(cpOptionValue.getUserName());
		cpOptionValueImpl.setCreateDate(cpOptionValue.getCreateDate());
		cpOptionValueImpl.setModifiedDate(cpOptionValue.getModifiedDate());
		cpOptionValueImpl.setCPOptionId(cpOptionValue.getCPOptionId());
		cpOptionValueImpl.setName(cpOptionValue.getName());
		cpOptionValueImpl.setPriority(cpOptionValue.getPriority());
		cpOptionValueImpl.setKey(cpOptionValue.getKey());
		cpOptionValueImpl.setLastPublishDate(cpOptionValue.getLastPublishDate());

		return cpOptionValueImpl;
	}

	/**
	 * Returns the cp option value with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp option value
	 * @return the cp option value
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPOptionValueException {
		CPOptionValue cpOptionValue = fetchByPrimaryKey(primaryKey);

		if (cpOptionValue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPOptionValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpOptionValue;
	}

	/**
	 * Returns the cp option value with the primary key or throws a {@link NoSuchCPOptionValueException} if it could not be found.
	 *
	 * @param CPOptionValueId the primary key of the cp option value
	 * @return the cp option value
	 * @throws NoSuchCPOptionValueException if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue findByPrimaryKey(long CPOptionValueId)
		throws NoSuchCPOptionValueException {
		return findByPrimaryKey((Serializable)CPOptionValueId);
	}

	/**
	 * Returns the cp option value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp option value
	 * @return the cp option value, or <code>null</code> if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
				CPOptionValueImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPOptionValue cpOptionValue = (CPOptionValue)serializable;

		if (cpOptionValue == null) {
			Session session = null;

			try {
				session = openSession();

				cpOptionValue = (CPOptionValue)session.get(CPOptionValueImpl.class,
						primaryKey);

				if (cpOptionValue != null) {
					cacheResult(cpOptionValue);
				}
				else {
					entityCache.putResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
						CPOptionValueImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CPOptionValueImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpOptionValue;
	}

	/**
	 * Returns the cp option value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPOptionValueId the primary key of the cp option value
	 * @return the cp option value, or <code>null</code> if a cp option value with the primary key could not be found
	 */
	@Override
	public CPOptionValue fetchByPrimaryKey(long CPOptionValueId) {
		return fetchByPrimaryKey((Serializable)CPOptionValueId);
	}

	@Override
	public Map<Serializable, CPOptionValue> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPOptionValue> map = new HashMap<Serializable, CPOptionValue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPOptionValue cpOptionValue = fetchByPrimaryKey(primaryKey);

			if (cpOptionValue != null) {
				map.put(primaryKey, cpOptionValue);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CPOptionValueImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPOptionValue)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPOPTIONVALUE_WHERE_PKS_IN);

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

			for (CPOptionValue cpOptionValue : (List<CPOptionValue>)q.list()) {
				map.put(cpOptionValue.getPrimaryKeyObj(), cpOptionValue);

				cacheResult(cpOptionValue);

				uncachedPrimaryKeys.remove(cpOptionValue.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CPOptionValueImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp option values.
	 *
	 * @return the cp option values
	 */
	@Override
	public List<CPOptionValue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of cp option values
	 */
	@Override
	public List<CPOptionValue> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp option values
	 */
	@Override
	public List<CPOptionValue> findAll(int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp option values
	 */
	@Override
	public List<CPOptionValue> findAll(int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
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

		List<CPOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CPOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPOPTIONVALUE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPOPTIONVALUE;

				if (pagination) {
					sql = sql.concat(CPOptionValueModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPOptionValue>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the cp option values from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPOptionValue cpOptionValue : findAll()) {
			remove(cpOptionValue);
		}
	}

	/**
	 * Returns the number of cp option values.
	 *
	 * @return the number of cp option values
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPOPTIONVALUE);

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
		return CPOptionValueModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp option value persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPOptionValueImpl.class.getName());
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
	private static final String _SQL_SELECT_CPOPTIONVALUE = "SELECT cpOptionValue FROM CPOptionValue cpOptionValue";
	private static final String _SQL_SELECT_CPOPTIONVALUE_WHERE_PKS_IN = "SELECT cpOptionValue FROM CPOptionValue cpOptionValue WHERE CPOptionValueId IN (";
	private static final String _SQL_SELECT_CPOPTIONVALUE_WHERE = "SELECT cpOptionValue FROM CPOptionValue cpOptionValue WHERE ";
	private static final String _SQL_COUNT_CPOPTIONVALUE = "SELECT COUNT(cpOptionValue) FROM CPOptionValue cpOptionValue";
	private static final String _SQL_COUNT_CPOPTIONVALUE_WHERE = "SELECT COUNT(cpOptionValue) FROM CPOptionValue cpOptionValue WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpOptionValue.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPOptionValue exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPOptionValue exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPOptionValuePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "key"
			});
}