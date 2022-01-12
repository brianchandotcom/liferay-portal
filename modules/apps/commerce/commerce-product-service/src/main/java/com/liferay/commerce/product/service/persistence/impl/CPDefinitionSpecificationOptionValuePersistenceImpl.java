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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueTable;
import com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValuePersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValueUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the cp definition specification option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionSpecificationOptionValuePersistenceImpl
	extends BasePersistenceImpl<CPDefinitionSpecificationOptionValue>
	implements CPDefinitionSpecificationOptionValuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionSpecificationOptionValueUtil</code> to access the cp definition specification option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionSpecificationOptionValueImpl.class.getName();

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
	 * Returns all the cp definition specification option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if (!uuid.equals(
							cpDefinitionSpecificationOptionValue.getUuid())) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
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

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUuid_First(
			String uuid,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUuid_First(
				uuid, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUuid_Last(
			String uuid,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUuid_Last(
				uuid, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[] findByUuid_PrevAndNext(
			long CPDefinitionSpecificationOptionValueId, String uuid,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, uuid,
				orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByUuid_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, uuid,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue getByUuid_PrevAndNext(
		Session session,
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue,
		String uuid,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByUuid(
						uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"cpDefinitionSpecificationOptionValue.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpDefinitionSpecificationOptionValue.uuid IS NULL OR cpDefinitionSpecificationOptionValue.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUUID_G(
			String uuid, long groupId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUUID_G(uuid, groupId);

		if (cpDefinitionSpecificationOptionValue == null) {
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

			throw new NoSuchCPDefinitionSpecificationOptionValueException(
				sb.toString());
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition specification option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof CPDefinitionSpecificationOptionValue) {
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)result;

			if (!Objects.equals(
					uuid, cpDefinitionSpecificationOptionValue.getUuid()) ||
				(groupId !=
					cpDefinitionSpecificationOptionValue.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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

				List<CPDefinitionSpecificationOptionValue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue = list.get(0);

					result = cpDefinitionSpecificationOptionValue;

					cacheResult(cpDefinitionSpecificationOptionValue);
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
			return (CPDefinitionSpecificationOptionValue)result;
		}
	}

	/**
	 * Removes the cp definition specification option value where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition specification option value that was removed
	 */
	@Override
	public CPDefinitionSpecificationOptionValue removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByUUID_G(uuid, groupId);

		return remove(cpDefinitionSpecificationOptionValue);
	}

	/**
	 * Returns the number of cp definition specification option values where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"cpDefinitionSpecificationOptionValue.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpDefinitionSpecificationOptionValue.uuid IS NULL OR cpDefinitionSpecificationOptionValue.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpDefinitionSpecificationOptionValue.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if (!uuid.equals(
							cpDefinitionSpecificationOptionValue.getUuid()) ||
						(companyId !=
							cpDefinitionSpecificationOptionValue.
								getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
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

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUuid_C_First(
				uuid, companyId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByUuid_C_Last(
				uuid, companyId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[] findByUuid_C_PrevAndNext(
			long CPDefinitionSpecificationOptionValueId, String uuid,
			long companyId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, uuid, companyId,
				orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue getByUuid_C_PrevAndNext(
		Session session,
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue,
		String uuid, long companyId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByUuid_C(
						uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"cpDefinitionSpecificationOptionValue.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpDefinitionSpecificationOptionValue.uuid IS NULL OR cpDefinitionSpecificationOptionValue.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpDefinitionSpecificationOptionValue.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the cp definition specification option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId) {

		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if (groupId !=
							cpDefinitionSpecificationOptionValue.getGroupId()) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByGroupId_First(
			long groupId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByGroupId_First(
				groupId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByGroupId_First(
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByGroupId_Last(
			long groupId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByGroupId_Last(
				groupId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where groupId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[] findByGroupId_PrevAndNext(
			long CPDefinitionSpecificationOptionValueId, long groupId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, groupId,
				orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByGroupId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, groupId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue getByGroupId_PrevAndNext(
		Session session,
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue,
		long groupId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByGroupId(
						groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"cpDefinitionSpecificationOptionValue.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCPDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;

	/**
	 * Returns all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId) {

		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCPDefinitionId;
				finderArgs = new Object[] {CPDefinitionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPDefinitionId;
			finderArgs = new Object[] {
				CPDefinitionId, start, end, orderByComparator
			};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if (CPDefinitionId !=
							cpDefinitionSpecificationOptionValue.
								getCPDefinitionId()) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByCPDefinitionId_First(
				CPDefinitionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list = findByCPDefinitionId(
			CPDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByCPDefinitionId_Last(
				CPDefinitionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list = findByCPDefinitionId(
			CPDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[]
			findByCPDefinitionId_PrevAndNext(
				long CPDefinitionSpecificationOptionValueId,
				long CPDefinitionId,
				OrderByComparator<CPDefinitionSpecificationOptionValue>
					orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, CPDefinitionId,
				orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, CPDefinitionId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue
		getByCPDefinitionId_PrevAndNext(
			Session session,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue,
			long CPDefinitionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

		sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByCPDefinitionId(
						CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPDefinitionId;

			finderArgs = new Object[] {CPDefinitionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 =
		"cpDefinitionSpecificationOptionValue.CPDefinitionId = ?";

	private FinderPath _finderPathWithPaginationFindByCPSpecificationOptionId;
	private FinderPath
		_finderPathWithoutPaginationFindByCPSpecificationOptionId;
	private FinderPath _finderPathCountByCPSpecificationOptionId;

	/**
	 * Returns all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(long CPSpecificationOptionId) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		return findByCPSpecificationOptionId(
			CPSpecificationOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue>
		findByCPSpecificationOptionId(
			long CPSpecificationOptionId, int start, int end,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator,
			boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCPSpecificationOptionId;
				finderArgs = new Object[] {CPSpecificationOptionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPSpecificationOptionId;
			finderArgs = new Object[] {
				CPSpecificationOptionId, start, end, orderByComparator
			};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if (CPSpecificationOptionId !=
							cpDefinitionSpecificationOptionValue.
								getCPSpecificationOptionId()) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(
				_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPSpecificationOptionId);

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
			findByCPSpecificationOptionId_First(
				long CPSpecificationOptionId,
				OrderByComparator<CPDefinitionSpecificationOptionValue>
					orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				fetchByCPSpecificationOptionId_First(
					CPSpecificationOptionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
		fetchByCPSpecificationOptionId_First(
			long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list =
			findByCPSpecificationOptionId(
				CPSpecificationOptionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
			findByCPSpecificationOptionId_Last(
				long CPSpecificationOptionId,
				OrderByComparator<CPDefinitionSpecificationOptionValue>
					orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				fetchByCPSpecificationOptionId_Last(
					CPSpecificationOptionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue
		fetchByCPSpecificationOptionId_Last(
			long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		int count = countByCPSpecificationOptionId(CPSpecificationOptionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list =
			findByCPSpecificationOptionId(
				CPSpecificationOptionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[]
			findByCPSpecificationOptionId_PrevAndNext(
				long CPDefinitionSpecificationOptionValueId,
				long CPSpecificationOptionId,
				OrderByComparator<CPDefinitionSpecificationOptionValue>
					orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByCPSpecificationOptionId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue,
				CPSpecificationOptionId, orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByCPSpecificationOptionId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue,
				CPSpecificationOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue
		getByCPSpecificationOptionId_PrevAndNext(
			Session session,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue,
			long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

		sb.append(
			_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPSpecificationOptionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	@Override
	public void removeByCPSpecificationOptionId(long CPSpecificationOptionId) {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByCPSpecificationOptionId(
						CPSpecificationOptionId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where CPSpecificationOptionId = &#63;.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByCPSpecificationOptionId(long CPSpecificationOptionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPSpecificationOptionId;

			finderArgs = new Object[] {CPSpecificationOptionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(
				_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPSpecificationOptionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String
		_FINDER_COLUMN_CPSPECIFICATIONOPTIONID_CPSPECIFICATIONOPTIONID_2 =
			"cpDefinitionSpecificationOptionValue.CPSpecificationOptionId = ?";

	private FinderPath _finderPathWithPaginationFindByCPOptionCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCPOptionCategoryId;
	private FinderPath _finderPathCountByCPOptionCategoryId;

	/**
	 * Returns all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId) {

		return findByCPOptionCategoryId(
			CPOptionCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end) {

		return findByCPOptionCategoryId(CPOptionCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByCPOptionCategoryId(
			CPOptionCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByCPOptionCategoryId(
		long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCPOptionCategoryId;
				finderArgs = new Object[] {CPOptionCategoryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPOptionCategoryId;
			finderArgs = new Object[] {
				CPOptionCategoryId, start, end, orderByComparator
			};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if (CPOptionCategoryId !=
							cpDefinitionSpecificationOptionValue.
								getCPOptionCategoryId()) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_CPOPTIONCATEGORYID_CPOPTIONCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPOptionCategoryId);

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByCPOptionCategoryId_First(
			long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				fetchByCPOptionCategoryId_First(
					CPOptionCategoryId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPOptionCategoryId=");
		sb.append(CPOptionCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByCPOptionCategoryId_First(
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list =
			findByCPOptionCategoryId(
				CPOptionCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByCPOptionCategoryId_Last(
			long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				fetchByCPOptionCategoryId_Last(
					CPOptionCategoryId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPOptionCategoryId=");
		sb.append(CPOptionCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByCPOptionCategoryId_Last(
		long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		int count = countByCPOptionCategoryId(CPOptionCategoryId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list =
			findByCPOptionCategoryId(
				CPOptionCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[]
			findByCPOptionCategoryId_PrevAndNext(
				long CPDefinitionSpecificationOptionValueId,
				long CPOptionCategoryId,
				OrderByComparator<CPDefinitionSpecificationOptionValue>
					orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByCPOptionCategoryId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue,
				CPOptionCategoryId, orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByCPOptionCategoryId_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue,
				CPOptionCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue
		getByCPOptionCategoryId_PrevAndNext(
			Session session,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue,
			long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

		sb.append(_FINDER_COLUMN_CPOPTIONCATEGORYID_CPOPTIONCATEGORYID_2);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPOptionCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where CPOptionCategoryId = &#63; from the database.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 */
	@Override
	public void removeByCPOptionCategoryId(long CPOptionCategoryId) {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByCPOptionCategoryId(
						CPOptionCategoryId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where CPOptionCategoryId = &#63;.
	 *
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByCPOptionCategoryId(long CPOptionCategoryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPOptionCategoryId;

			finderArgs = new Object[] {CPOptionCategoryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_CPOPTIONCATEGORYID_CPOPTIONCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPOptionCategoryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String
		_FINDER_COLUMN_CPOPTIONCATEGORYID_CPOPTIONCATEGORYID_2 =
			"cpDefinitionSpecificationOptionValue.CPOptionCategoryId = ?";

	private FinderPath _finderPathFetchByC_CSOVI;
	private FinderPath _finderPathCountByC_CSOVI;

	/**
	 * Returns the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_CSOVI(
			long CPDefinitionSpecificationOptionValueId, long CPDefinitionId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_CSOVI(
				CPDefinitionSpecificationOptionValueId, CPDefinitionId);

		if (cpDefinitionSpecificationOptionValue == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionSpecificationOptionValueId=");
			sb.append(CPDefinitionSpecificationOptionValueId);

			sb.append(", CPDefinitionId=");
			sb.append(CPDefinitionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionSpecificationOptionValueException(
				sb.toString());
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_CSOVI(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId) {

		return fetchByC_CSOVI(
			CPDefinitionSpecificationOptionValueId, CPDefinitionId, true);
	}

	/**
	 * Returns the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_CSOVI(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				CPDefinitionSpecificationOptionValueId, CPDefinitionId
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByC_CSOVI, finderArgs);
		}

		if (result instanceof CPDefinitionSpecificationOptionValue) {
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)result;

			if ((CPDefinitionSpecificationOptionValueId !=
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId()) ||
				(CPDefinitionId !=
					cpDefinitionSpecificationOptionValue.getCPDefinitionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(
				_FINDER_COLUMN_C_CSOVI_CPDEFINITIONSPECIFICATIONOPTIONVALUEID_2);

			sb.append(_FINDER_COLUMN_C_CSOVI_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionSpecificationOptionValueId);

				queryPos.add(CPDefinitionId);

				List<CPDefinitionSpecificationOptionValue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_CSOVI, finderArgs, list);
					}
				}
				else {
					CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue = list.get(0);

					result = cpDefinitionSpecificationOptionValue;

					cacheResult(cpDefinitionSpecificationOptionValue);
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
			return (CPDefinitionSpecificationOptionValue)result;
		}
	}

	/**
	 * Removes the cp definition specification option value where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition specification option value that was removed
	 */
	@Override
	public CPDefinitionSpecificationOptionValue removeByC_CSOVI(
			long CPDefinitionSpecificationOptionValueId, long CPDefinitionId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByC_CSOVI(
				CPDefinitionSpecificationOptionValueId, CPDefinitionId);

		return remove(cpDefinitionSpecificationOptionValue);
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionSpecificationOptionValueId = &#63; and CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the cp definition specification option value ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByC_CSOVI(
		long CPDefinitionSpecificationOptionValueId, long CPDefinitionId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_CSOVI;

			finderArgs = new Object[] {
				CPDefinitionSpecificationOptionValueId, CPDefinitionId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(
				_FINDER_COLUMN_C_CSOVI_CPDEFINITIONSPECIFICATIONOPTIONVALUEID_2);

			sb.append(_FINDER_COLUMN_C_CSOVI_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionSpecificationOptionValueId);

				queryPos.add(CPDefinitionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String
		_FINDER_COLUMN_C_CSOVI_CPDEFINITIONSPECIFICATIONOPTIONVALUEID_2 =
			"cpDefinitionSpecificationOptionValue.CPDefinitionSpecificationOptionValueId = ? AND ";

	private static final String _FINDER_COLUMN_C_CSOVI_CPDEFINITIONID_2 =
		"cpDefinitionSpecificationOptionValue.CPDefinitionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_CSO;
	private FinderPath _finderPathWithoutPaginationFindByC_CSO;
	private FinderPath _finderPathCountByC_CSO;

	/**
	 * Returns all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId) {

		return findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId, int start, int end) {

		return findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_CSO;
				finderArgs = new Object[] {
					CPDefinitionId, CPSpecificationOptionId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_CSO;
			finderArgs = new Object[] {
				CPDefinitionId, CPSpecificationOptionId, start, end,
				orderByComparator
			};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if ((CPDefinitionId !=
							cpDefinitionSpecificationOptionValue.
								getCPDefinitionId()) ||
						(CPSpecificationOptionId !=
							cpDefinitionSpecificationOptionValue.
								getCPSpecificationOptionId())) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_CSO_CPDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_C_CSO_CPSPECIFICATIONOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				queryPos.add(CPSpecificationOptionId);

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_CSO_First(
			long CPDefinitionId, long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_CSO_First(
				CPDefinitionId, CPSpecificationOptionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append(", CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_CSO_First(
		long CPDefinitionId, long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list = findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_CSO_Last(
			long CPDefinitionId, long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_CSO_Last(
				CPDefinitionId, CPSpecificationOptionId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append(", CPSpecificationOptionId=");
		sb.append(CPSpecificationOptionId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_CSO_Last(
		long CPDefinitionId, long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		int count = countByC_CSO(CPDefinitionId, CPSpecificationOptionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list = findByC_CSO(
			CPDefinitionId, CPSpecificationOptionId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[] findByC_CSO_PrevAndNext(
			long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
			long CPSpecificationOptionId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByC_CSO_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, CPDefinitionId,
				CPSpecificationOptionId, orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByC_CSO_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, CPDefinitionId,
				CPSpecificationOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue getByC_CSO_PrevAndNext(
		Session session,
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue,
		long CPDefinitionId, long CPSpecificationOptionId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

		sb.append(_FINDER_COLUMN_C_CSO_CPDEFINITIONID_2);

		sb.append(_FINDER_COLUMN_C_CSO_CPSPECIFICATIONOPTIONID_2);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionId);

		queryPos.add(CPSpecificationOptionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 */
	@Override
	public void removeByC_CSO(
		long CPDefinitionId, long CPSpecificationOptionId) {

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByC_CSO(
						CPDefinitionId, CPSpecificationOptionId,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPSpecificationOptionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPSpecificationOptionId the cp specification option ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByC_CSO(long CPDefinitionId, long CPSpecificationOptionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_CSO;

			finderArgs = new Object[] {CPDefinitionId, CPSpecificationOptionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_CSO_CPDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_C_CSO_CPSPECIFICATIONOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				queryPos.add(CPSpecificationOptionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_CSO_CPDEFINITIONID_2 =
		"cpDefinitionSpecificationOptionValue.CPDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_C_CSO_CPSPECIFICATIONOPTIONID_2 =
		"cpDefinitionSpecificationOptionValue.CPSpecificationOptionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_COC;
	private FinderPath _finderPathWithoutPaginationFindByC_COC;
	private FinderPath _finderPathCountByC_COC;

	/**
	 * Returns all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId) {

		return findByC_COC(
			CPDefinitionId, CPOptionCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end) {

		return findByC_COC(
			CPDefinitionId, CPOptionCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findByC_COC(
			CPDefinitionId, CPOptionCategoryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findByC_COC(
		long CPDefinitionId, long CPOptionCategoryId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_COC;
				finderArgs = new Object[] {CPDefinitionId, CPOptionCategoryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_COC;
			finderArgs = new Object[] {
				CPDefinitionId, CPOptionCategoryId, start, end,
				orderByComparator
			};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue : list) {

					if ((CPDefinitionId !=
							cpDefinitionSpecificationOptionValue.
								getCPDefinitionId()) ||
						(CPOptionCategoryId !=
							cpDefinitionSpecificationOptionValue.
								getCPOptionCategoryId())) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_COC_CPDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_C_COC_CPOPTIONCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				queryPos.add(CPOptionCategoryId);

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_COC_First(
			long CPDefinitionId, long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_COC_First(
				CPDefinitionId, CPOptionCategoryId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append(", CPOptionCategoryId=");
		sb.append(CPOptionCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the first cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_COC_First(
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		List<CPDefinitionSpecificationOptionValue> list = findByC_COC(
			CPDefinitionId, CPOptionCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByC_COC_Last(
			long CPDefinitionId, long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByC_COC_Last(
				CPDefinitionId, CPOptionCategoryId, orderByComparator);

		if (cpDefinitionSpecificationOptionValue != null) {
			return cpDefinitionSpecificationOptionValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append(", CPOptionCategoryId=");
		sb.append(CPOptionCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionSpecificationOptionValueException(
			sb.toString());
	}

	/**
	 * Returns the last cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByC_COC_Last(
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		int count = countByC_COC(CPDefinitionId, CPOptionCategoryId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionSpecificationOptionValue> list = findByC_COC(
			CPDefinitionId, CPOptionCategoryId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition specification option values before and after the current cp definition specification option value in the ordered set where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the current cp definition specification option value
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue[] findByC_COC_PrevAndNext(
			long CPDefinitionSpecificationOptionValueId, long CPDefinitionId,
			long CPOptionCategoryId,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = findByPrimaryKey(
				CPDefinitionSpecificationOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue[] array =
				new CPDefinitionSpecificationOptionValueImpl[3];

			array[0] = getByC_COC_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, CPDefinitionId,
				CPOptionCategoryId, orderByComparator, true);

			array[1] = cpDefinitionSpecificationOptionValue;

			array[2] = getByC_COC_PrevAndNext(
				session, cpDefinitionSpecificationOptionValue, CPDefinitionId,
				CPOptionCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionSpecificationOptionValue getByC_COC_PrevAndNext(
		Session session,
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue,
		long CPDefinitionId, long CPOptionCategoryId,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

		sb.append(_FINDER_COLUMN_C_COC_CPDEFINITIONID_2);

		sb.append(_FINDER_COLUMN_C_COC_CPOPTIONCATEGORYID_2);

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
			sb.append(
				CPDefinitionSpecificationOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionId);

		queryPos.add(CPOptionCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionSpecificationOptionValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionSpecificationOptionValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 */
	@Override
	public void removeByC_COC(long CPDefinitionId, long CPOptionCategoryId) {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					findByC_COC(
						CPDefinitionId, CPOptionCategoryId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values where CPDefinitionId = &#63; and CPOptionCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param CPOptionCategoryId the cp option category ID
	 * @return the number of matching cp definition specification option values
	 */
	@Override
	public int countByC_COC(long CPDefinitionId, long CPOptionCategoryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_COC;

			finderArgs = new Object[] {CPDefinitionId, CPOptionCategoryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_COC_CPDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_C_COC_CPOPTIONCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				queryPos.add(CPOptionCategoryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_COC_CPDEFINITIONID_2 =
		"cpDefinitionSpecificationOptionValue.CPDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_C_COC_CPOPTIONCATEGORYID_2 =
		"cpDefinitionSpecificationOptionValue.CPOptionCategoryId = ?";

	public CPDefinitionSpecificationOptionValuePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"CPDefinitionSpecificationOptionValueId",
			"CPDSpecificationOptionValueId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinitionSpecificationOptionValue.class);

		setModelImplClass(CPDefinitionSpecificationOptionValueImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionSpecificationOptionValueTable.INSTANCE);
	}

	/**
	 * Caches the cp definition specification option value in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	 */
	@Override
	public void cacheResult(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		if (cpDefinitionSpecificationOptionValue.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CPDefinitionSpecificationOptionValueImpl.class,
			cpDefinitionSpecificationOptionValue.getPrimaryKey(),
			cpDefinitionSpecificationOptionValue);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				cpDefinitionSpecificationOptionValue.getUuid(),
				cpDefinitionSpecificationOptionValue.getGroupId()
			},
			cpDefinitionSpecificationOptionValue);

		finderCache.putResult(
			_finderPathFetchByC_CSOVI,
			new Object[] {
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId(),
				cpDefinitionSpecificationOptionValue.getCPDefinitionId()
			},
			cpDefinitionSpecificationOptionValue);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp definition specification option values in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionSpecificationOptionValues the cp definition specification option values
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpDefinitionSpecificationOptionValues.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			if (cpDefinitionSpecificationOptionValue.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CPDefinitionSpecificationOptionValueImpl.class,
					cpDefinitionSpecificationOptionValue.getPrimaryKey()) ==
						null) {

				cacheResult(cpDefinitionSpecificationOptionValue);
			}
		}
	}

	/**
	 * Clears the cache for all cp definition specification option values.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionSpecificationOptionValueImpl.class);

		finderCache.clearCache(CPDefinitionSpecificationOptionValueImpl.class);
	}

	/**
	 * Clears the cache for the cp definition specification option value.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		entityCache.removeResult(
			CPDefinitionSpecificationOptionValueImpl.class,
			cpDefinitionSpecificationOptionValue);
	}

	@Override
	public void clearCache(
		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues) {

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			entityCache.removeResult(
				CPDefinitionSpecificationOptionValueImpl.class,
				cpDefinitionSpecificationOptionValue);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionSpecificationOptionValueImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionSpecificationOptionValueImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionSpecificationOptionValueModelImpl
			cpDefinitionSpecificationOptionValueModelImpl) {

		Object[] args = new Object[] {
			cpDefinitionSpecificationOptionValueModelImpl.getUuid(),
			cpDefinitionSpecificationOptionValueModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			cpDefinitionSpecificationOptionValueModelImpl);

		args = new Object[] {
			cpDefinitionSpecificationOptionValueModelImpl.
				getCPDefinitionSpecificationOptionValueId(),
			cpDefinitionSpecificationOptionValueModelImpl.getCPDefinitionId()
		};

		finderCache.putResult(_finderPathCountByC_CSOVI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_CSOVI, args,
			cpDefinitionSpecificationOptionValueModelImpl);
	}

	/**
	 * Creates a new cp definition specification option value with the primary key. Does not add the cp definition specification option value to the database.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key for the new cp definition specification option value
	 * @return the new cp definition specification option value
	 */
	@Override
	public CPDefinitionSpecificationOptionValue create(
		long CPDefinitionSpecificationOptionValueId) {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				new CPDefinitionSpecificationOptionValueImpl();

		cpDefinitionSpecificationOptionValue.setNew(true);
		cpDefinitionSpecificationOptionValue.setPrimaryKey(
			CPDefinitionSpecificationOptionValueId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionSpecificationOptionValue.setUuid(uuid);

		cpDefinitionSpecificationOptionValue.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Removes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value that was removed
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue remove(
			long CPDefinitionSpecificationOptionValueId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		return remove((Serializable)CPDefinitionSpecificationOptionValueId);
	}

	/**
	 * Removes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition specification option value
	 * @return the cp definition specification option value that was removed
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue remove(Serializable primaryKey)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)session.get(
						CPDefinitionSpecificationOptionValueImpl.class,
						primaryKey);

			if (cpDefinitionSpecificationOptionValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionSpecificationOptionValueException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionSpecificationOptionValue);
		}
		catch (NoSuchCPDefinitionSpecificationOptionValueException
					noSuchEntityException) {

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
	protected CPDefinitionSpecificationOptionValue removeImpl(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionSpecificationOptionValue)) {
				cpDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)session.get(
						CPDefinitionSpecificationOptionValueImpl.class,
						cpDefinitionSpecificationOptionValue.
							getPrimaryKeyObj());
			}

			if ((cpDefinitionSpecificationOptionValue != null) &&
				ctPersistenceHelper.isRemove(
					cpDefinitionSpecificationOptionValue)) {

				session.delete(cpDefinitionSpecificationOptionValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionSpecificationOptionValue != null) {
			clearCache(cpDefinitionSpecificationOptionValue);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue updateImpl(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		boolean isNew = cpDefinitionSpecificationOptionValue.isNew();

		if (!(cpDefinitionSpecificationOptionValue instanceof
				CPDefinitionSpecificationOptionValueModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					cpDefinitionSpecificationOptionValue.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionSpecificationOptionValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionSpecificationOptionValue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionSpecificationOptionValue implementation " +
					cpDefinitionSpecificationOptionValue.getClass());
		}

		CPDefinitionSpecificationOptionValueModelImpl
			cpDefinitionSpecificationOptionValueModelImpl =
				(CPDefinitionSpecificationOptionValueModelImpl)
					cpDefinitionSpecificationOptionValue;

		if (Validator.isNull(cpDefinitionSpecificationOptionValue.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionSpecificationOptionValue.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(cpDefinitionSpecificationOptionValue.getCreateDate() == null)) {

			if (serviceContext == null) {
				cpDefinitionSpecificationOptionValue.setCreateDate(date);
			}
			else {
				cpDefinitionSpecificationOptionValue.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionSpecificationOptionValueModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				cpDefinitionSpecificationOptionValue.setModifiedDate(date);
			}
			else {
				cpDefinitionSpecificationOptionValue.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(
					cpDefinitionSpecificationOptionValue)) {

				if (!isNew) {
					session.evict(
						CPDefinitionSpecificationOptionValueImpl.class,
						cpDefinitionSpecificationOptionValue.
							getPrimaryKeyObj());
				}

				session.save(cpDefinitionSpecificationOptionValue);
			}
			else {
				cpDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)session.merge(
						cpDefinitionSpecificationOptionValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionSpecificationOptionValue.getCtCollectionId() != 0) {
			if (isNew) {
				cpDefinitionSpecificationOptionValue.setNew(false);
			}

			cpDefinitionSpecificationOptionValue.resetOriginalValues();

			return cpDefinitionSpecificationOptionValue;
		}

		entityCache.putResult(
			CPDefinitionSpecificationOptionValueImpl.class,
			cpDefinitionSpecificationOptionValueModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionSpecificationOptionValueModelImpl);

		if (isNew) {
			cpDefinitionSpecificationOptionValue.setNew(false);
		}

		cpDefinitionSpecificationOptionValue.resetOriginalValues();

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition specification option value
	 * @return the cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = fetchByPrimaryKey(
				primaryKey);

		if (cpDefinitionSpecificationOptionValue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionSpecificationOptionValueException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value with the primary key or throws a <code>NoSuchCPDefinitionSpecificationOptionValueException</code> if it could not be found.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value
	 * @throws NoSuchCPDefinitionSpecificationOptionValueException if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue findByPrimaryKey(
			long CPDefinitionSpecificationOptionValueId)
		throws NoSuchCPDefinitionSpecificationOptionValueException {

		return findByPrimaryKey(
			(Serializable)CPDefinitionSpecificationOptionValueId);
	}

	/**
	 * Returns the cp definition specification option value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition specification option value
	 * @return the cp definition specification option value, or <code>null</code> if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CPDefinitionSpecificationOptionValue.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = null;

		Session session = null;

		try {
			session = openSession();

			cpDefinitionSpecificationOptionValue =
				(CPDefinitionSpecificationOptionValue)session.get(
					CPDefinitionSpecificationOptionValueImpl.class, primaryKey);

			if (cpDefinitionSpecificationOptionValue != null) {
				cacheResult(cpDefinitionSpecificationOptionValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpDefinitionSpecificationOptionValue;
	}

	/**
	 * Returns the cp definition specification option value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	 * @return the cp definition specification option value, or <code>null</code> if a cp definition specification option value with the primary key could not be found
	 */
	@Override
	public CPDefinitionSpecificationOptionValue fetchByPrimaryKey(
		long CPDefinitionSpecificationOptionValueId) {

		return fetchByPrimaryKey(
			(Serializable)CPDefinitionSpecificationOptionValueId);
	}

	@Override
	public Map<Serializable, CPDefinitionSpecificationOptionValue>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CPDefinitionSpecificationOptionValue.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionSpecificationOptionValue> map =
			new HashMap<Serializable, CPDefinitionSpecificationOptionValue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue = fetchByPrimaryKey(
					primaryKey);

			if (cpDefinitionSpecificationOptionValue != null) {
				map.put(primaryKey, cpDefinitionSpecificationOptionValue);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
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

			for (CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue :
						(List<CPDefinitionSpecificationOptionValue>)
							query.list()) {

				map.put(
					cpDefinitionSpecificationOptionValue.getPrimaryKeyObj(),
					cpDefinitionSpecificationOptionValue);

				cacheResult(cpDefinitionSpecificationOptionValue);
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
	 * Returns all the cp definition specification option values.
	 *
	 * @return the cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition specification option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @return the range of cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition specification option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionSpecificationOptionValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition specification option values
	 * @param end the upper bound of the range of cp definition specification option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition specification option values
	 */
	@Override
	public List<CPDefinitionSpecificationOptionValue> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CPDefinitionSpecificationOptionValue> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CPDefinitionSpecificationOptionValue>)
					finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE;

				sql = sql.concat(
					CPDefinitionSpecificationOptionValueModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CPDefinitionSpecificationOptionValue>)QueryUtil.list(
						query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the cp definition specification option values from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue : findAll()) {

			remove(cpDefinitionSpecificationOptionValue);
		}
	}

	/**
	 * Returns the number of cp definition specification option values.
	 *
	 * @return the number of cp definition specification option values
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPDefinitionSpecificationOptionValue.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "CPDSpecificationOptionValueId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionSpecificationOptionValueModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPDSpecificationOptionValue";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("CPDefinitionId");
		ctStrictColumnNames.add("CPSpecificationOptionId");
		ctStrictColumnNames.add("CPOptionCategoryId");
		ctStrictColumnNames.add("value");
		ctStrictColumnNames.add("priority");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPDSpecificationOptionValueId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"CPDSpecificationOptionValueId", "CPDefinitionId"});
	}

	/**
	 * Initializes the cp definition specification option value persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId"}, true);

		_finderPathWithoutPaginationFindByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, true);

		_finderPathCountByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, false);

		_finderPathWithPaginationFindByCPSpecificationOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPSpecificationOptionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPSpecificationOptionId"}, true);

		_finderPathWithoutPaginationFindByCPSpecificationOptionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCPSpecificationOptionId",
				new String[] {Long.class.getName()},
				new String[] {"CPSpecificationOptionId"}, true);

		_finderPathCountByCPSpecificationOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPSpecificationOptionId",
			new String[] {Long.class.getName()},
			new String[] {"CPSpecificationOptionId"}, false);

		_finderPathWithPaginationFindByCPOptionCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPOptionCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPOptionCategoryId"}, true);

		_finderPathWithoutPaginationFindByCPOptionCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCPOptionCategoryId", new String[] {Long.class.getName()},
			new String[] {"CPOptionCategoryId"}, true);

		_finderPathCountByCPOptionCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPOptionCategoryId", new String[] {Long.class.getName()},
			new String[] {"CPOptionCategoryId"}, false);

		_finderPathFetchByC_CSOVI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_CSOVI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDSpecificationOptionValueId", "CPDefinitionId"},
			true);

		_finderPathCountByC_CSOVI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CSOVI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDSpecificationOptionValueId", "CPDefinitionId"},
			false);

		_finderPathWithPaginationFindByC_CSO = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CSO",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId", "CPSpecificationOptionId"}, true);

		_finderPathWithoutPaginationFindByC_CSO = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CSO",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPSpecificationOptionId"}, true);

		_finderPathCountByC_CSO = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CSO",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPSpecificationOptionId"}, false);

		_finderPathWithPaginationFindByC_COC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_COC",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionId", "CPOptionCategoryId"}, true);

		_finderPathWithoutPaginationFindByC_COC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_COC",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPOptionCategoryId"}, true);

		_finderPathCountByC_COC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_COC",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "CPOptionCategoryId"}, false);

		_setCPDefinitionSpecificationOptionValueUtilPersistence(this);
	}

	public void destroy() {
		_setCPDefinitionSpecificationOptionValueUtilPersistence(null);

		entityCache.removeCache(
			CPDefinitionSpecificationOptionValueImpl.class.getName());
	}

	private void _setCPDefinitionSpecificationOptionValueUtilPersistence(
		CPDefinitionSpecificationOptionValuePersistence
			cpDefinitionSpecificationOptionValuePersistence) {

		try {
			Field field =
				CPDefinitionSpecificationOptionValueUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, cpDefinitionSpecificationOptionValuePersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = CTPersistenceHelper.class)
	protected CTPersistenceHelper ctPersistenceHelper;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String
		_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE =
			"SELECT cpDefinitionSpecificationOptionValue FROM CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue";

	private static final String
		_SQL_SELECT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE =
			"SELECT cpDefinitionSpecificationOptionValue FROM CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue WHERE ";

	private static final String
		_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE =
			"SELECT COUNT(cpDefinitionSpecificationOptionValue) FROM CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue";

	private static final String
		_SQL_COUNT_CPDEFINITIONSPECIFICATIONOPTIONVALUE_WHERE =
			"SELECT COUNT(cpDefinitionSpecificationOptionValue) FROM CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionSpecificationOptionValue.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionSpecificationOptionValue exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionSpecificationOptionValue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionSpecificationOptionValuePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "CPDefinitionSpecificationOptionValueId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}