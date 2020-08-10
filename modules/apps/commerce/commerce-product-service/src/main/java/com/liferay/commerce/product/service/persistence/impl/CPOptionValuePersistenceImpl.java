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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the cp option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CPOptionValuePersistenceImpl
	extends BasePersistenceImpl<CPOptionValue>
	implements CPOptionValuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPOptionValueUtil</code> to access the cp option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPOptionValueImpl.class.getName();

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
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

		List<CPOptionValue> list = null;

		if (useFinderCache) {
			list = (List<CPOptionValue>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if (!uuid.equals(cpOptionValue.getUuid())) {
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

			sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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
				sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPOptionValue>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
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
	public CPOptionValue findByUuid_First(
			String uuid, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUuid_First(
		String uuid, OrderByComparator<CPOptionValue> orderByComparator) {

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
	public CPOptionValue findByUuid_Last(
			String uuid, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByUuid_Last(uuid, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByUuid_Last(
		String uuid, OrderByComparator<CPOptionValue> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

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
	public CPOptionValue[] findByUuid_PrevAndNext(
			long CPOptionValueId, String uuid,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		uuid = Objects.toString(uuid, "");

		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpOptionValue, uuid, orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByUuid_PrevAndNext(
				session, cpOptionValue, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPOptionValue getByUuid_PrevAndNext(
		Session session, CPOptionValue cpOptionValue, String uuid,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

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
			sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
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
						cpOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPOptionValue> list = query.list();

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
		for (CPOptionValue cpOptionValue :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

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
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"cpOptionValue.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpOptionValue.uuid IS NULL OR cpOptionValue.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
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
	public List<CPOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CPOptionValue> list = null;

		if (useFinderCache) {
			list = (List<CPOptionValue>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if (!uuid.equals(cpOptionValue.getUuid()) ||
						(companyId != cpOptionValue.getCompanyId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(companyId);

				list = (List<CPOptionValue>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
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
	public CPOptionValue findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
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
	public CPOptionValue fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {

		List<CPOptionValue> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

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
	public CPOptionValue findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
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
	public CPOptionValue fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

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
	public CPOptionValue[] findByUuid_C_PrevAndNext(
			long CPOptionValueId, String uuid, long companyId,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		uuid = Objects.toString(uuid, "");

		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpOptionValue, uuid, companyId, orderByComparator,
				true);

			array[1] = cpOptionValue;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpOptionValue, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPOptionValue getByUuid_C_PrevAndNext(
		Session session, CPOptionValue cpOptionValue, String uuid,
		long companyId, OrderByComparator<CPOptionValue> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPOptionValue> list = query.list();

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
		for (CPOptionValue cpOptionValue :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"cpOptionValue.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpOptionValue.uuid IS NULL OR cpOptionValue.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpOptionValue.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the cp option values where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<CPOptionValue> list = null;

		if (useFinderCache) {
			list = (List<CPOptionValue>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if (companyId != cpOptionValue.getCompanyId()) {
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

			sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CPOptionValue>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
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
	public CPOptionValue findByCompanyId_First(
			long companyId, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCompanyId_First(
		long companyId, OrderByComparator<CPOptionValue> orderByComparator) {

		List<CPOptionValue> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

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
	public CPOptionValue findByCompanyId_Last(
			long companyId, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCompanyId_Last(
		long companyId, OrderByComparator<CPOptionValue> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

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
	public CPOptionValue[] findByCompanyId_PrevAndNext(
			long CPOptionValueId, long companyId,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, cpOptionValue, companyId, orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByCompanyId_PrevAndNext(
				session, cpOptionValue, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPOptionValue getByCompanyId_PrevAndNext(
		Session session, CPOptionValue cpOptionValue, long companyId,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPOptionValue> list = query.list();

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
		for (CPOptionValue cpOptionValue :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"cpOptionValue.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCPOptionId;
	private FinderPath _finderPathWithoutPaginationFindByCPOptionId;
	private FinderPath _finderPathCountByCPOptionId;

	/**
	 * Returns all the cp option values where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @return the matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(long CPOptionId) {
		return findByCPOptionId(
			CPOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp option values where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @return the range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(
		long CPOptionId, int start, int end) {

		return findByCPOptionId(CPOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(
		long CPOptionId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {

		return findByCPOptionId(
			CPOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values where CPOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionId the cp option ID
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp option values
	 */
	@Override
	public List<CPOptionValue> findByCPOptionId(
		long CPOptionId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPOptionId;
				finderArgs = new Object[] {CPOptionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPOptionId;
			finderArgs = new Object[] {
				CPOptionId, start, end, orderByComparator
			};
		}

		List<CPOptionValue> list = null;

		if (useFinderCache) {
			list = (List<CPOptionValue>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPOptionValue cpOptionValue : list) {
					if (CPOptionId != cpOptionValue.getCPOptionId()) {
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

			sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPOptionId);

				list = (List<CPOptionValue>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
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
	public CPOptionValue findByCPOptionId_First(
			long CPOptionId, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByCPOptionId_First(
			CPOptionId, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPOptionId=");
		sb.append(CPOptionId);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
	}

	/**
	 * Returns the first cp option value in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCPOptionId_First(
		long CPOptionId, OrderByComparator<CPOptionValue> orderByComparator) {

		List<CPOptionValue> list = findByCPOptionId(
			CPOptionId, 0, 1, orderByComparator);

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
	public CPOptionValue findByCPOptionId_Last(
			long CPOptionId, OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByCPOptionId_Last(
			CPOptionId, orderByComparator);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPOptionId=");
		sb.append(CPOptionId);

		sb.append("}");

		throw new NoSuchCPOptionValueException(sb.toString());
	}

	/**
	 * Returns the last cp option value in the ordered set where CPOptionId = &#63;.
	 *
	 * @param CPOptionId the cp option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByCPOptionId_Last(
		long CPOptionId, OrderByComparator<CPOptionValue> orderByComparator) {

		int count = countByCPOptionId(CPOptionId);

		if (count == 0) {
			return null;
		}

		List<CPOptionValue> list = findByCPOptionId(
			CPOptionId, count - 1, count, orderByComparator);

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
	public CPOptionValue[] findByCPOptionId_PrevAndNext(
			long CPOptionValueId, long CPOptionId,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = findByPrimaryKey(CPOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPOptionValue[] array = new CPOptionValueImpl[3];

			array[0] = getByCPOptionId_PrevAndNext(
				session, cpOptionValue, CPOptionId, orderByComparator, true);

			array[1] = cpOptionValue;

			array[2] = getByCPOptionId_PrevAndNext(
				session, cpOptionValue, CPOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPOptionValue getByCPOptionId_PrevAndNext(
		Session session, CPOptionValue cpOptionValue, long CPOptionId,
		OrderByComparator<CPOptionValue> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

		sb.append(_FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2);

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
			sb.append(CPOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPOptionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPOptionValue> list = query.list();

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
		for (CPOptionValue cpOptionValue :
				findByCPOptionId(
					CPOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		FinderPath finderPath = _finderPathCountByCPOptionId;

		Object[] finderArgs = new Object[] {CPOptionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPOptionId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CPOPTIONID_CPOPTIONID_2 =
		"cpOptionValue.CPOptionId = ?";

	private FinderPath _finderPathFetchByC_K;
	private FinderPath _finderPathCountByC_K;

	/**
	 * Returns the cp option value where CPOptionId = &#63; and key = &#63; or throws a <code>NoSuchCPOptionValueException</code> if it could not be found.
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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPOptionId=");
			sb.append(CPOptionId);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPOptionValueException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByC_K(
		long CPOptionId, String key, boolean useFinderCache) {

		key = Objects.toString(key, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CPOptionId, key};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_K, finderArgs, this);
		}

		if (result instanceof CPOptionValue) {
			CPOptionValue cpOptionValue = (CPOptionValue)result;

			if ((CPOptionId != cpOptionValue.getCPOptionId()) ||
				!Objects.equals(key, cpOptionValue.getKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_K_CPOPTIONID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPOptionId);

				if (bindKey) {
					queryPos.add(key);
				}

				List<CPOptionValue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_K, finderArgs, list);
					}
				}
				else {
					CPOptionValue cpOptionValue = list.get(0);

					result = cpOptionValue;

					cacheResult(cpOptionValue);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_K, finderArgs);
				}

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
		key = Objects.toString(key, "");

		FinderPath finderPath = _finderPathCountByC_K;

		Object[] finderArgs = new Object[] {CPOptionId, key};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_K_CPOPTIONID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_C_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPOptionId);

				if (bindKey) {
					queryPos.add(key);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_K_CPOPTIONID_2 =
		"cpOptionValue.CPOptionId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_KEY_2 =
		"cpOptionValue.key = ?";

	private static final String _FINDER_COLUMN_C_K_KEY_3 =
		"(cpOptionValue.key IS NULL OR cpOptionValue.key = '')";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the cp option value where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCPOptionValueException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp option value
	 * @throws NoSuchCPOptionValueException if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (cpOptionValue == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPOptionValueException(sb.toString());
		}

		return cpOptionValue;
	}

	/**
	 * Returns the cp option value where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the cp option value where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	 */
	@Override
	public CPOptionValue fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_ERC, finderArgs, this);
		}

		if (result instanceof CPOptionValue) {
			CPOptionValue cpOptionValue = (CPOptionValue)result;

			if ((companyId != cpOptionValue.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					cpOptionValue.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<CPOptionValue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CPOptionValuePersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPOptionValue cpOptionValue = list.get(0);

					result = cpOptionValue;

					cacheResult(cpOptionValue);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByC_ERC, finderArgs);
				}

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
			return (CPOptionValue)result;
		}
	}

	/**
	 * Removes the cp option value where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the cp option value that was removed
	 */
	@Override
	public CPOptionValue removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPOptionValueException {

		CPOptionValue cpOptionValue = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(cpOptionValue);
	}

	/**
	 * Returns the number of cp option values where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching cp option values
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"cpOptionValue.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"cpOptionValue.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(cpOptionValue.externalReferenceCode IS NULL OR cpOptionValue.externalReferenceCode = '')";

	public CPOptionValuePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("key", "key_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		setModelClass(CPOptionValue.class);
	}

	/**
	 * Caches the cp option value in the entity cache if it is enabled.
	 *
	 * @param cpOptionValue the cp option value
	 */
	@Override
	public void cacheResult(CPOptionValue cpOptionValue) {
		entityCache.putResult(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueImpl.class, cpOptionValue.getPrimaryKey(),
			cpOptionValue);

		finderCache.putResult(
			_finderPathFetchByC_K,
			new Object[] {
				cpOptionValue.getCPOptionId(), cpOptionValue.getKey()
			},
			cpOptionValue);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				cpOptionValue.getCompanyId(),
				cpOptionValue.getExternalReferenceCode()
			},
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
					CPOptionValueImpl.class, cpOptionValue.getPrimaryKey()) ==
						null) {

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
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
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
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPOptionValue cpOptionValue) {
		entityCache.removeResult(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
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
			entityCache.removeResult(
				CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
				CPOptionValueImpl.class, cpOptionValue.getPrimaryKey());

			clearUniqueFindersCache(
				(CPOptionValueModelImpl)cpOptionValue, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
				CPOptionValueImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPOptionValueModelImpl cpOptionValueModelImpl) {

		Object[] args = new Object[] {
			cpOptionValueModelImpl.getCPOptionId(),
			cpOptionValueModelImpl.getKey()
		};

		finderCache.putResult(
			_finderPathCountByC_K, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_K, args, cpOptionValueModelImpl, false);

		args = new Object[] {
			cpOptionValueModelImpl.getCompanyId(),
			cpOptionValueModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(
			_finderPathCountByC_ERC, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, cpOptionValueModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPOptionValueModelImpl cpOptionValueModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				cpOptionValueModelImpl.getCPOptionId(),
				cpOptionValueModelImpl.getKey()
			};

			finderCache.removeResult(_finderPathCountByC_K, args);
			finderCache.removeResult(_finderPathFetchByC_K, args);
		}

		if ((cpOptionValueModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_K.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				cpOptionValueModelImpl.getOriginalCPOptionId(),
				cpOptionValueModelImpl.getOriginalKey()
			};

			finderCache.removeResult(_finderPathCountByC_K, args);
			finderCache.removeResult(_finderPathFetchByC_K, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				cpOptionValueModelImpl.getCompanyId(),
				cpOptionValueModelImpl.getExternalReferenceCode()
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
		}

		if ((cpOptionValueModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_ERC.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				cpOptionValueModelImpl.getOriginalCompanyId(),
				cpOptionValueModelImpl.getOriginalExternalReferenceCode()
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
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

		cpOptionValue.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			CPOptionValue cpOptionValue = (CPOptionValue)session.get(
				CPOptionValueImpl.class, primaryKey);

			if (cpOptionValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPOptionValueException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpOptionValue);
		}
		catch (NoSuchCPOptionValueException noSuchEntityException) {
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
	protected CPOptionValue removeImpl(CPOptionValue cpOptionValue) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpOptionValue)) {
				cpOptionValue = (CPOptionValue)session.get(
					CPOptionValueImpl.class, cpOptionValue.getPrimaryKeyObj());
			}

			if (cpOptionValue != null) {
				session.delete(cpOptionValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = cpOptionValue.isNew();

		if (!(cpOptionValue instanceof CPOptionValueModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpOptionValue.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpOptionValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpOptionValue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPOptionValue implementation " +
					cpOptionValue.getClass());
		}

		CPOptionValueModelImpl cpOptionValueModelImpl =
			(CPOptionValueModelImpl)cpOptionValue;

		if (Validator.isNull(cpOptionValue.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpOptionValue.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

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
				cpOptionValue.setModifiedDate(
					serviceContext.getModifiedDate(now));
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
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPOptionValueModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {cpOptionValueModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				cpOptionValueModelImpl.getUuid(),
				cpOptionValueModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {cpOptionValueModelImpl.getCompanyId()};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {cpOptionValueModelImpl.getCPOptionId()};

			finderCache.removeResult(_finderPathCountByCPOptionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCPOptionId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((cpOptionValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					cpOptionValueModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {cpOptionValueModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((cpOptionValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					cpOptionValueModelImpl.getOriginalUuid(),
					cpOptionValueModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					cpOptionValueModelImpl.getUuid(),
					cpOptionValueModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((cpOptionValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					cpOptionValueModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {cpOptionValueModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((cpOptionValueModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCPOptionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					cpOptionValueModelImpl.getOriginalCPOptionId()
				};

				finderCache.removeResult(_finderPathCountByCPOptionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCPOptionId, args);

				args = new Object[] {cpOptionValueModelImpl.getCPOptionId()};

				finderCache.removeResult(_finderPathCountByCPOptionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCPOptionId, args);
			}
		}

		entityCache.putResult(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueImpl.class, cpOptionValue.getPrimaryKey(),
			cpOptionValue, false);

		clearUniqueFindersCache(cpOptionValueModelImpl, false);
		cacheUniqueFindersCache(cpOptionValueModelImpl);

		cpOptionValue.resetOriginalValues();

		return cpOptionValue;
	}

	/**
	 * Returns the cp option value with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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

			throw new NoSuchCPOptionValueException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpOptionValue;
	}

	/**
	 * Returns the cp option value with the primary key or throws a <code>NoSuchCPOptionValueException</code> if it could not be found.
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
		Serializable serializable = entityCache.getResult(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPOptionValue cpOptionValue = (CPOptionValue)serializable;

		if (cpOptionValue == null) {
			Session session = null;

			try {
				session = openSession();

				cpOptionValue = (CPOptionValue)session.get(
					CPOptionValueImpl.class, primaryKey);

				if (cpOptionValue != null) {
					cacheResult(cpOptionValue);
				}
				else {
					entityCache.putResult(
						CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
						CPOptionValueImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CPOptionValueImpl.class, primaryKey);

				throw processException(exception);
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

		Map<Serializable, CPOptionValue> map =
			new HashMap<Serializable, CPOptionValue>();

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
			Serializable serializable = entityCache.getResult(
				CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
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

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_CPOPTIONVALUE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CPOptionValue cpOptionValue :
					(List<CPOptionValue>)query.list()) {

				map.put(cpOptionValue.getPrimaryKeyObj(), cpOptionValue);

				cacheResult(cpOptionValue);

				uncachedPrimaryKeys.remove(cpOptionValue.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CPOptionValueImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp option values
	 */
	@Override
	public List<CPOptionValue> findAll(
		int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp option values
	 * @param end the upper bound of the range of cp option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp option values
	 */
	@Override
	public List<CPOptionValue> findAll(
		int start, int end, OrderByComparator<CPOptionValue> orderByComparator,
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

		List<CPOptionValue> list = null;

		if (useFinderCache) {
			list = (List<CPOptionValue>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPOPTIONVALUE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPOPTIONVALUE;

				sql = sql.concat(CPOptionValueModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPOptionValue>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CPOPTIONVALUE);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return CPOptionValueModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp option value persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			CPOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			CPOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CPOptionValueModelImpl.COMPANYID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			CPOptionValueModelImpl.COMPANYID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCPOptionId = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPOptionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCPOptionId = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCPOptionId", new String[] {Long.class.getName()},
			CPOptionValueModelImpl.CPOPTIONID_COLUMN_BITMASK |
			CPOptionValueModelImpl.PRIORITY_COLUMN_BITMASK |
			CPOptionValueModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByCPOptionId = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPOptionId",
			new String[] {Long.class.getName()});

		_finderPathFetchByC_K = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			CPOptionValueModelImpl.CPOPTIONID_COLUMN_BITMASK |
			CPOptionValueModelImpl.KEY_COLUMN_BITMASK);

		_finderPathCountByC_K = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByC_ERC = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CPOptionValueImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			CPOptionValueModelImpl.COMPANYID_COLUMN_BITMASK |
			CPOptionValueModelImpl.EXTERNALREFERENCECODE_COLUMN_BITMASK);

		_finderPathCountByC_ERC = new FinderPath(
			CPOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CPOptionValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(CPOptionValueImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPOPTIONVALUE =
		"SELECT cpOptionValue FROM CPOptionValue cpOptionValue";

	private static final String _SQL_SELECT_CPOPTIONVALUE_WHERE_PKS_IN =
		"SELECT cpOptionValue FROM CPOptionValue cpOptionValue WHERE CPOptionValueId IN (";

	private static final String _SQL_SELECT_CPOPTIONVALUE_WHERE =
		"SELECT cpOptionValue FROM CPOptionValue cpOptionValue WHERE ";

	private static final String _SQL_COUNT_CPOPTIONVALUE =
		"SELECT COUNT(cpOptionValue) FROM CPOptionValue cpOptionValue";

	private static final String _SQL_COUNT_CPOPTIONVALUE_WHERE =
		"SELECT COUNT(cpOptionValue) FROM CPOptionValue cpOptionValue WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cpOptionValue.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPOptionValue exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPOptionValue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPOptionValuePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "key"});

}