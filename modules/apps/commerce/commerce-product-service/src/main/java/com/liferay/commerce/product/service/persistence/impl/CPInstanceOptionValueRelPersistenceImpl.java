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

import com.liferay.commerce.product.exception.NoSuchCPInstanceOptionValueRelException;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.CPInstanceOptionValueRelTable;
import com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelImpl;
import com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl;
import com.liferay.commerce.product.service.persistence.CPInstanceOptionValueRelPersistence;
import com.liferay.commerce.product.service.persistence.CPInstanceOptionValueRelUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the cp instance option value rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CPInstanceOptionValueRelPersistenceImpl
	extends BasePersistenceImpl<CPInstanceOptionValueRel>
	implements CPInstanceOptionValueRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPInstanceOptionValueRelUtil</code> to access the cp instance option value rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPInstanceOptionValueRelImpl.class.getName();

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
	 * Returns all the cp instance option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

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

		List<CPInstanceOptionValueRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceOptionValueRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceOptionValueRel cpInstanceOptionValueRel : list) {
					if (!uuid.equals(cpInstanceOptionValueRel.getUuid())) {
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

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

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
				sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPInstanceOptionValueRel>)QueryUtil.list(
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
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByUuid_First(
			String uuid,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		List<CPInstanceOptionValueRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByUuid_Last(
			String uuid,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = fetchByUuid_Last(
			uuid, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPInstanceOptionValueRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where uuid = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel[] findByUuid_PrevAndNext(
			long CPInstanceOptionValueRelId, String uuid,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		uuid = Objects.toString(uuid, "");

		CPInstanceOptionValueRel cpInstanceOptionValueRel = findByPrimaryKey(
			CPInstanceOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceOptionValueRel[] array =
				new CPInstanceOptionValueRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpInstanceOptionValueRel, uuid, orderByComparator,
				true);

			array[1] = cpInstanceOptionValueRel;

			array[2] = getByUuid_PrevAndNext(
				session, cpInstanceOptionValueRel, uuid, orderByComparator,
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

	protected CPInstanceOptionValueRel getByUuid_PrevAndNext(
		Session session, CPInstanceOptionValueRel cpInstanceOptionValueRel,
		String uuid,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

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
			sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
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
						cpInstanceOptionValueRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceOptionValueRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance option value rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpInstanceOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

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

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

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
		"cpInstanceOptionValueRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpInstanceOptionValueRel.uuid IS NULL OR cpInstanceOptionValueRel.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByUUID_G(String uuid, long groupId)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = fetchByUUID_G(
			uuid, groupId);

		if (cpInstanceOptionValueRel == null) {
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

			throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
		}

		return cpInstanceOptionValueRel;
	}

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp instance option value rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof CPInstanceOptionValueRel) {
			CPInstanceOptionValueRel cpInstanceOptionValueRel =
				(CPInstanceOptionValueRel)result;

			if (!Objects.equals(uuid, cpInstanceOptionValueRel.getUuid()) ||
				(groupId != cpInstanceOptionValueRel.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

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

				List<CPInstanceOptionValueRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPInstanceOptionValueRel cpInstanceOptionValueRel =
						list.get(0);

					result = cpInstanceOptionValueRel;

					cacheResult(cpInstanceOptionValueRel);
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
			return (CPInstanceOptionValueRel)result;
		}
	}

	/**
	 * Removes the cp instance option value rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp instance option value rel that was removed
	 */
	@Override
	public CPInstanceOptionValueRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = findByUUID_G(
			uuid, groupId);

		return remove(cpInstanceOptionValueRel);
	}

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

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

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

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
		"cpInstanceOptionValueRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpInstanceOptionValueRel.uuid IS NULL OR cpInstanceOptionValueRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpInstanceOptionValueRel.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

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

		List<CPInstanceOptionValueRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceOptionValueRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceOptionValueRel cpInstanceOptionValueRel : list) {
					if (!uuid.equals(cpInstanceOptionValueRel.getUuid()) ||
						(companyId !=
							cpInstanceOptionValueRel.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

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
				sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPInstanceOptionValueRel>)QueryUtil.list(
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
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		List<CPInstanceOptionValueRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPInstanceOptionValueRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel[] findByUuid_C_PrevAndNext(
			long CPInstanceOptionValueRelId, String uuid, long companyId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		uuid = Objects.toString(uuid, "");

		CPInstanceOptionValueRel cpInstanceOptionValueRel = findByPrimaryKey(
			CPInstanceOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceOptionValueRel[] array =
				new CPInstanceOptionValueRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpInstanceOptionValueRel, uuid, companyId,
				orderByComparator, true);

			array[1] = cpInstanceOptionValueRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpInstanceOptionValueRel, uuid, companyId,
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

	protected CPInstanceOptionValueRel getByUuid_C_PrevAndNext(
		Session session, CPInstanceOptionValueRel cpInstanceOptionValueRel,
		String uuid, long companyId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

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
			sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
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
						cpInstanceOptionValueRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceOptionValueRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance option value rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpInstanceOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp instance option value rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

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

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

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
		"cpInstanceOptionValueRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpInstanceOptionValueRel.uuid IS NULL OR cpInstanceOptionValueRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpInstanceOptionValueRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCPDefinitionOptionRelId;
	private FinderPath
		_finderPathWithoutPaginationFindByCPDefinitionOptionRelId;
	private FinderPath _finderPathCountByCPDefinitionOptionRelId;

	/**
	 * Returns all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId) {

		return findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end) {

		return findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPDefinitionOptionRelId(
		long CPDefinitionOptionRelId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCPDefinitionOptionRelId;
				finderArgs = new Object[] {CPDefinitionOptionRelId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPDefinitionOptionRelId;
			finderArgs = new Object[] {
				CPDefinitionOptionRelId, start, end, orderByComparator
			};
		}

		List<CPInstanceOptionValueRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceOptionValueRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceOptionValueRel cpInstanceOptionValueRel : list) {
					if (CPDefinitionOptionRelId !=
							cpInstanceOptionValueRel.
								getCPDefinitionOptionRelId()) {

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

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(
				_FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionRelId);

				list = (List<CPInstanceOptionValueRel>)QueryUtil.list(
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
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCPDefinitionOptionRelId_First(
			long CPDefinitionOptionRelId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			fetchByCPDefinitionOptionRelId_First(
				CPDefinitionOptionRelId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCPDefinitionOptionRelId_First(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		List<CPInstanceOptionValueRel> list = findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCPDefinitionOptionRelId_Last(
			long CPDefinitionOptionRelId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			fetchByCPDefinitionOptionRelId_Last(
				CPDefinitionOptionRelId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCPDefinitionOptionRelId_Last(
		long CPDefinitionOptionRelId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		int count = countByCPDefinitionOptionRelId(CPDefinitionOptionRelId);

		if (count == 0) {
			return null;
		}

		List<CPInstanceOptionValueRel> list = findByCPDefinitionOptionRelId(
			CPDefinitionOptionRelId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel[] findByCPDefinitionOptionRelId_PrevAndNext(
			long CPInstanceOptionValueRelId, long CPDefinitionOptionRelId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = findByPrimaryKey(
			CPInstanceOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceOptionValueRel[] array =
				new CPInstanceOptionValueRelImpl[3];

			array[0] = getByCPDefinitionOptionRelId_PrevAndNext(
				session, cpInstanceOptionValueRel, CPDefinitionOptionRelId,
				orderByComparator, true);

			array[1] = cpInstanceOptionValueRel;

			array[2] = getByCPDefinitionOptionRelId_PrevAndNext(
				session, cpInstanceOptionValueRel, CPDefinitionOptionRelId,
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

	protected CPInstanceOptionValueRel getByCPDefinitionOptionRelId_PrevAndNext(
		Session session, CPInstanceOptionValueRel cpInstanceOptionValueRel,
		long CPDefinitionOptionRelId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

		sb.append(
			_FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2);

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
			sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionOptionRelId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceOptionValueRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceOptionValueRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance option value rels where CPDefinitionOptionRelId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 */
	@Override
	public void removeByCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				findByCPDefinitionOptionRelId(
					CPDefinitionOptionRelId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpInstanceOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByCPDefinitionOptionRelId(long CPDefinitionOptionRelId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPDefinitionOptionRelId;

			finderArgs = new Object[] {CPDefinitionOptionRelId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(
				_FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionRelId);

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
		_FINDER_COLUMN_CPDEFINITIONOPTIONRELID_CPDEFINITIONOPTIONRELID_2 =
			"cpInstanceOptionValueRel.CPDefinitionOptionRelId = ?";

	private FinderPath _finderPathWithPaginationFindByCPInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByCPInstanceId;
	private FinderPath _finderPathCountByCPInstanceId;

	/**
	 * Returns all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId) {

		return findByCPInstanceId(
			CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end) {

		return findByCPInstanceId(CPInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCPInstanceId;
				finderArgs = new Object[] {CPInstanceId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPInstanceId;
			finderArgs = new Object[] {
				CPInstanceId, start, end, orderByComparator
			};
		}

		List<CPInstanceOptionValueRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceOptionValueRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceOptionValueRel cpInstanceOptionValueRel : list) {
					if (CPInstanceId !=
							cpInstanceOptionValueRel.getCPInstanceId()) {

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

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				list = (List<CPInstanceOptionValueRel>)QueryUtil.list(
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
	 * Returns the first cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCPInstanceId_First(
			long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			fetchByCPInstanceId_First(CPInstanceId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		List<CPInstanceOptionValueRel> list = findByCPInstanceId(
			CPInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCPInstanceId_Last(
			long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			fetchByCPInstanceId_Last(CPInstanceId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		int count = countByCPInstanceId(CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CPInstanceOptionValueRel> list = findByCPInstanceId(
			CPInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel[] findByCPInstanceId_PrevAndNext(
			long CPInstanceOptionValueRelId, long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = findByPrimaryKey(
			CPInstanceOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceOptionValueRel[] array =
				new CPInstanceOptionValueRelImpl[3];

			array[0] = getByCPInstanceId_PrevAndNext(
				session, cpInstanceOptionValueRel, CPInstanceId,
				orderByComparator, true);

			array[1] = cpInstanceOptionValueRel;

			array[2] = getByCPInstanceId_PrevAndNext(
				session, cpInstanceOptionValueRel, CPInstanceId,
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

	protected CPInstanceOptionValueRel getByCPInstanceId_PrevAndNext(
		Session session, CPInstanceOptionValueRel cpInstanceOptionValueRel,
		long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

		sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

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
			sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceOptionValueRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceOptionValueRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance option value rels where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByCPInstanceId(long CPInstanceId) {
		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				findByCPInstanceId(
					CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpInstanceOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp instance option value rels where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByCPInstanceId(long CPInstanceId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPInstanceId;

			finderArgs = new Object[] {CPInstanceId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

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

	private static final String _FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2 =
		"cpInstanceOptionValueRel.CPInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByCDORI_CII;
	private FinderPath _finderPathWithoutPaginationFindByCDORI_CII;
	private FinderPath _finderPathCountByCDORI_CII;

	/**
	 * Returns all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId) {

		return findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end) {

		return findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId, int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCDORI_CII;
				finderArgs = new Object[] {
					CPDefinitionOptionRelId, CPInstanceId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCDORI_CII;
			finderArgs = new Object[] {
				CPDefinitionOptionRelId, CPInstanceId, start, end,
				orderByComparator
			};
		}

		List<CPInstanceOptionValueRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceOptionValueRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPInstanceOptionValueRel cpInstanceOptionValueRel : list) {
					if ((CPDefinitionOptionRelId !=
							cpInstanceOptionValueRel.
								getCPDefinitionOptionRelId()) ||
						(CPInstanceId !=
							cpInstanceOptionValueRel.getCPInstanceId())) {

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

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(_FINDER_COLUMN_CDORI_CII_CPDEFINITIONOPTIONRELID_2);

			sb.append(_FINDER_COLUMN_CDORI_CII_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionRelId);

				queryPos.add(CPInstanceId);

				list = (List<CPInstanceOptionValueRel>)QueryUtil.list(
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
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCDORI_CII_First(
			long CPDefinitionOptionRelId, long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			fetchByCDORI_CII_First(
				CPDefinitionOptionRelId, CPInstanceId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);

		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the first cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCDORI_CII_First(
		long CPDefinitionOptionRelId, long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		List<CPInstanceOptionValueRel> list = findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCDORI_CII_Last(
			long CPDefinitionOptionRelId, long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			fetchByCDORI_CII_Last(
				CPDefinitionOptionRelId, CPInstanceId, orderByComparator);

		if (cpInstanceOptionValueRel != null) {
			return cpInstanceOptionValueRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);

		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
	}

	/**
	 * Returns the last cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCDORI_CII_Last(
		long CPDefinitionOptionRelId, long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		int count = countByCDORI_CII(CPDefinitionOptionRelId, CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CPInstanceOptionValueRel> list = findByCDORI_CII(
			CPDefinitionOptionRelId, CPInstanceId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp instance option value rels before and after the current cp instance option value rel in the ordered set where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the current cp instance option value rel
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel[] findByCDORI_CII_PrevAndNext(
			long CPInstanceOptionValueRelId, long CPDefinitionOptionRelId,
			long CPInstanceId,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = findByPrimaryKey(
			CPInstanceOptionValueRelId);

		Session session = null;

		try {
			session = openSession();

			CPInstanceOptionValueRel[] array =
				new CPInstanceOptionValueRelImpl[3];

			array[0] = getByCDORI_CII_PrevAndNext(
				session, cpInstanceOptionValueRel, CPDefinitionOptionRelId,
				CPInstanceId, orderByComparator, true);

			array[1] = cpInstanceOptionValueRel;

			array[2] = getByCDORI_CII_PrevAndNext(
				session, cpInstanceOptionValueRel, CPDefinitionOptionRelId,
				CPInstanceId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPInstanceOptionValueRel getByCDORI_CII_PrevAndNext(
		Session session, CPInstanceOptionValueRel cpInstanceOptionValueRel,
		long CPDefinitionOptionRelId, long CPInstanceId,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

		sb.append(_FINDER_COLUMN_CDORI_CII_CPDEFINITIONOPTIONRELID_2);

		sb.append(_FINDER_COLUMN_CDORI_CII_CPINSTANCEID_2);

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
			sb.append(CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPDefinitionOptionRelId);

		queryPos.add(CPInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpInstanceOptionValueRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPInstanceOptionValueRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId) {

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				findByCDORI_CII(
					CPDefinitionOptionRelId, CPInstanceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpInstanceOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByCDORI_CII(
		long CPDefinitionOptionRelId, long CPInstanceId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCDORI_CII;

			finderArgs = new Object[] {CPDefinitionOptionRelId, CPInstanceId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(_FINDER_COLUMN_CDORI_CII_CPDEFINITIONOPTIONRELID_2);

			sb.append(_FINDER_COLUMN_CDORI_CII_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionRelId);

				queryPos.add(CPInstanceId);

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
		_FINDER_COLUMN_CDORI_CII_CPDEFINITIONOPTIONRELID_2 =
			"cpInstanceOptionValueRel.CPDefinitionOptionRelId = ? AND ";

	private static final String _FINDER_COLUMN_CDORI_CII_CPINSTANCEID_2 =
		"cpInstanceOptionValueRel.CPInstanceId = ?";

	private FinderPath _finderPathFetchByCDOVRI_CII;
	private FinderPath _finderPathCountByCDOVRI_CII;

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCDOVRI_CII(
			long CPDefinitionOptionValueRelId, long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = fetchByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId);

		if (cpInstanceOptionValueRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionOptionValueRelId=");
			sb.append(CPDefinitionOptionValueRelId);

			sb.append(", CPInstanceId=");
			sb.append(CPInstanceId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
		}

		return cpInstanceOptionValueRel;
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId) {

		return fetchByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId, true);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				CPDefinitionOptionValueRelId, CPInstanceId
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCDOVRI_CII, finderArgs);
		}

		if (result instanceof CPInstanceOptionValueRel) {
			CPInstanceOptionValueRel cpInstanceOptionValueRel =
				(CPInstanceOptionValueRel)result;

			if ((CPDefinitionOptionValueRelId !=
					cpInstanceOptionValueRel.
						getCPDefinitionOptionValueRelId()) ||
				(CPInstanceId != cpInstanceOptionValueRel.getCPInstanceId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(_FINDER_COLUMN_CDOVRI_CII_CPDEFINITIONOPTIONVALUERELID_2);

			sb.append(_FINDER_COLUMN_CDOVRI_CII_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionValueRelId);

				queryPos.add(CPInstanceId);

				List<CPInstanceOptionValueRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCDOVRI_CII, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {
									CPDefinitionOptionValueRelId, CPInstanceId
								};
							}

							_log.warn(
								"CPInstanceOptionValueRelPersistenceImpl.fetchByCDOVRI_CII(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPInstanceOptionValueRel cpInstanceOptionValueRel =
						list.get(0);

					result = cpInstanceOptionValueRel;

					cacheResult(cpInstanceOptionValueRel);
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
			return (CPInstanceOptionValueRel)result;
		}
	}

	/**
	 * Removes the cp instance option value rel where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the cp instance option value rel that was removed
	 */
	@Override
	public CPInstanceOptionValueRel removeByCDOVRI_CII(
			long CPDefinitionOptionValueRelId, long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = findByCDOVRI_CII(
			CPDefinitionOptionValueRelId, CPInstanceId);

		return remove(cpInstanceOptionValueRel);
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByCDOVRI_CII(
		long CPDefinitionOptionValueRelId, long CPInstanceId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCDOVRI_CII;

			finderArgs = new Object[] {
				CPDefinitionOptionValueRelId, CPInstanceId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(_FINDER_COLUMN_CDOVRI_CII_CPDEFINITIONOPTIONVALUERELID_2);

			sb.append(_FINDER_COLUMN_CDOVRI_CII_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionValueRelId);

				queryPos.add(CPInstanceId);

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
		_FINDER_COLUMN_CDOVRI_CII_CPDEFINITIONOPTIONVALUERELID_2 =
			"cpInstanceOptionValueRel.CPDefinitionOptionValueRelId = ? AND ";

	private static final String _FINDER_COLUMN_CDOVRI_CII_CPINSTANCEID_2 =
		"cpInstanceOptionValueRel.CPInstanceId = ?";

	private FinderPath _finderPathFetchByCDORI_CDOVRI_CII;
	private FinderPath _finderPathCountByCDORI_CDOVRI_CII;

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByCDORI_CDOVRI_CII(
			long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
			long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			fetchByCDORI_CDOVRI_CII(
				CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
				CPInstanceId);

		if (cpInstanceOptionValueRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionOptionRelId=");
			sb.append(CPDefinitionOptionRelId);

			sb.append(", CPDefinitionOptionValueRelId=");
			sb.append(CPDefinitionOptionValueRelId);

			sb.append(", CPInstanceId=");
			sb.append(CPInstanceId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPInstanceOptionValueRelException(sb.toString());
		}

		return cpInstanceOptionValueRel;
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId) {

		return fetchByCDORI_CDOVRI_CII(
			CPDefinitionOptionRelId, CPDefinitionOptionValueRelId, CPInstanceId,
			true);
	}

	/**
	 * Returns the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
				CPInstanceId
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCDORI_CDOVRI_CII, finderArgs);
		}

		if (result instanceof CPInstanceOptionValueRel) {
			CPInstanceOptionValueRel cpInstanceOptionValueRel =
				(CPInstanceOptionValueRel)result;

			if ((CPDefinitionOptionRelId !=
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId()) ||
				(CPDefinitionOptionValueRelId !=
					cpInstanceOptionValueRel.
						getCPDefinitionOptionValueRelId()) ||
				(CPInstanceId != cpInstanceOptionValueRel.getCPInstanceId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(
				_FINDER_COLUMN_CDORI_CDOVRI_CII_CPDEFINITIONOPTIONRELID_2);

			sb.append(
				_FINDER_COLUMN_CDORI_CDOVRI_CII_CPDEFINITIONOPTIONVALUERELID_2);

			sb.append(_FINDER_COLUMN_CDORI_CDOVRI_CII_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionRelId);

				queryPos.add(CPDefinitionOptionValueRelId);

				queryPos.add(CPInstanceId);

				List<CPInstanceOptionValueRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCDORI_CDOVRI_CII, finderArgs,
							list);
					}
				}
				else {
					CPInstanceOptionValueRel cpInstanceOptionValueRel =
						list.get(0);

					result = cpInstanceOptionValueRel;

					cacheResult(cpInstanceOptionValueRel);
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
			return (CPInstanceOptionValueRel)result;
		}
	}

	/**
	 * Removes the cp instance option value rel where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the cp instance option value rel that was removed
	 */
	@Override
	public CPInstanceOptionValueRel removeByCDORI_CDOVRI_CII(
			long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
			long CPInstanceId)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			findByCDORI_CDOVRI_CII(
				CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
				CPInstanceId);

		return remove(cpInstanceOptionValueRel);
	}

	/**
	 * Returns the number of cp instance option value rels where CPDefinitionOptionRelId = &#63; and CPDefinitionOptionValueRelId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param CPDefinitionOptionRelId the cp definition option rel ID
	 * @param CPDefinitionOptionValueRelId the cp definition option value rel ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching cp instance option value rels
	 */
	@Override
	public int countByCDORI_CDOVRI_CII(
		long CPDefinitionOptionRelId, long CPDefinitionOptionValueRelId,
		long CPInstanceId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCDORI_CDOVRI_CII;

			finderArgs = new Object[] {
				CPDefinitionOptionRelId, CPDefinitionOptionValueRelId,
				CPInstanceId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE);

			sb.append(
				_FINDER_COLUMN_CDORI_CDOVRI_CII_CPDEFINITIONOPTIONRELID_2);

			sb.append(
				_FINDER_COLUMN_CDORI_CDOVRI_CII_CPDEFINITIONOPTIONVALUERELID_2);

			sb.append(_FINDER_COLUMN_CDORI_CDOVRI_CII_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionOptionRelId);

				queryPos.add(CPDefinitionOptionValueRelId);

				queryPos.add(CPInstanceId);

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
		_FINDER_COLUMN_CDORI_CDOVRI_CII_CPDEFINITIONOPTIONRELID_2 =
			"cpInstanceOptionValueRel.CPDefinitionOptionRelId = ? AND ";

	private static final String
		_FINDER_COLUMN_CDORI_CDOVRI_CII_CPDEFINITIONOPTIONVALUERELID_2 =
			"cpInstanceOptionValueRel.CPDefinitionOptionValueRelId = ? AND ";

	private static final String _FINDER_COLUMN_CDORI_CDOVRI_CII_CPINSTANCEID_2 =
		"cpInstanceOptionValueRel.CPInstanceId = ?";

	public CPInstanceOptionValueRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPInstanceOptionValueRel.class);

		setModelImplClass(CPInstanceOptionValueRelImpl.class);
		setModelPKClass(long.class);

		setTable(CPInstanceOptionValueRelTable.INSTANCE);
	}

	/**
	 * Caches the cp instance option value rel in the entity cache if it is enabled.
	 *
	 * @param cpInstanceOptionValueRel the cp instance option value rel
	 */
	@Override
	public void cacheResult(CPInstanceOptionValueRel cpInstanceOptionValueRel) {
		if (cpInstanceOptionValueRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CPInstanceOptionValueRelImpl.class,
			cpInstanceOptionValueRel.getPrimaryKey(), cpInstanceOptionValueRel);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				cpInstanceOptionValueRel.getUuid(),
				cpInstanceOptionValueRel.getGroupId()
			},
			cpInstanceOptionValueRel);

		finderCache.putResult(
			_finderPathFetchByCDOVRI_CII,
			new Object[] {
				cpInstanceOptionValueRel.getCPDefinitionOptionValueRelId(),
				cpInstanceOptionValueRel.getCPInstanceId()
			},
			cpInstanceOptionValueRel);

		finderCache.putResult(
			_finderPathFetchByCDORI_CDOVRI_CII,
			new Object[] {
				cpInstanceOptionValueRel.getCPDefinitionOptionRelId(),
				cpInstanceOptionValueRel.getCPDefinitionOptionValueRelId(),
				cpInstanceOptionValueRel.getCPInstanceId()
			},
			cpInstanceOptionValueRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp instance option value rels in the entity cache if it is enabled.
	 *
	 * @param cpInstanceOptionValueRels the cp instance option value rels
	 */
	@Override
	public void cacheResult(
		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpInstanceOptionValueRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpInstanceOptionValueRels) {

			if (cpInstanceOptionValueRel.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CPInstanceOptionValueRelImpl.class,
					cpInstanceOptionValueRel.getPrimaryKey()) == null) {

				cacheResult(cpInstanceOptionValueRel);
			}
		}
	}

	/**
	 * Clears the cache for all cp instance option value rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPInstanceOptionValueRelImpl.class);

		finderCache.clearCache(CPInstanceOptionValueRelImpl.class);
	}

	/**
	 * Clears the cache for the cp instance option value rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPInstanceOptionValueRel cpInstanceOptionValueRel) {
		entityCache.removeResult(
			CPInstanceOptionValueRelImpl.class, cpInstanceOptionValueRel);
	}

	@Override
	public void clearCache(
		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels) {

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpInstanceOptionValueRels) {

			entityCache.removeResult(
				CPInstanceOptionValueRelImpl.class, cpInstanceOptionValueRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPInstanceOptionValueRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPInstanceOptionValueRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPInstanceOptionValueRelModelImpl cpInstanceOptionValueRelModelImpl) {

		Object[] args = new Object[] {
			cpInstanceOptionValueRelModelImpl.getUuid(),
			cpInstanceOptionValueRelModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, cpInstanceOptionValueRelModelImpl);

		args = new Object[] {
			cpInstanceOptionValueRelModelImpl.getCPDefinitionOptionValueRelId(),
			cpInstanceOptionValueRelModelImpl.getCPInstanceId()
		};

		finderCache.putResult(
			_finderPathCountByCDOVRI_CII, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCDOVRI_CII, args,
			cpInstanceOptionValueRelModelImpl);

		args = new Object[] {
			cpInstanceOptionValueRelModelImpl.getCPDefinitionOptionRelId(),
			cpInstanceOptionValueRelModelImpl.getCPDefinitionOptionValueRelId(),
			cpInstanceOptionValueRelModelImpl.getCPInstanceId()
		};

		finderCache.putResult(
			_finderPathCountByCDORI_CDOVRI_CII, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCDORI_CDOVRI_CII, args,
			cpInstanceOptionValueRelModelImpl);
	}

	/**
	 * Creates a new cp instance option value rel with the primary key. Does not add the cp instance option value rel to the database.
	 *
	 * @param CPInstanceOptionValueRelId the primary key for the new cp instance option value rel
	 * @return the new cp instance option value rel
	 */
	@Override
	public CPInstanceOptionValueRel create(long CPInstanceOptionValueRelId) {
		CPInstanceOptionValueRel cpInstanceOptionValueRel =
			new CPInstanceOptionValueRelImpl();

		cpInstanceOptionValueRel.setNew(true);
		cpInstanceOptionValueRel.setPrimaryKey(CPInstanceOptionValueRelId);

		String uuid = PortalUUIDUtil.generate();

		cpInstanceOptionValueRel.setUuid(uuid);

		cpInstanceOptionValueRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpInstanceOptionValueRel;
	}

	/**
	 * Removes the cp instance option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel that was removed
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel remove(long CPInstanceOptionValueRelId)
		throws NoSuchCPInstanceOptionValueRelException {

		return remove((Serializable)CPInstanceOptionValueRelId);
	}

	/**
	 * Removes the cp instance option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp instance option value rel
	 * @return the cp instance option value rel that was removed
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel remove(Serializable primaryKey)
		throws NoSuchCPInstanceOptionValueRelException {

		Session session = null;

		try {
			session = openSession();

			CPInstanceOptionValueRel cpInstanceOptionValueRel =
				(CPInstanceOptionValueRel)session.get(
					CPInstanceOptionValueRelImpl.class, primaryKey);

			if (cpInstanceOptionValueRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPInstanceOptionValueRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpInstanceOptionValueRel);
		}
		catch (NoSuchCPInstanceOptionValueRelException noSuchEntityException) {
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
	protected CPInstanceOptionValueRel removeImpl(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpInstanceOptionValueRel)) {
				cpInstanceOptionValueRel =
					(CPInstanceOptionValueRel)session.get(
						CPInstanceOptionValueRelImpl.class,
						cpInstanceOptionValueRel.getPrimaryKeyObj());
			}

			if ((cpInstanceOptionValueRel != null) &&
				ctPersistenceHelper.isRemove(cpInstanceOptionValueRel)) {

				session.delete(cpInstanceOptionValueRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpInstanceOptionValueRel != null) {
			clearCache(cpInstanceOptionValueRel);
		}

		return cpInstanceOptionValueRel;
	}

	@Override
	public CPInstanceOptionValueRel updateImpl(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		boolean isNew = cpInstanceOptionValueRel.isNew();

		if (!(cpInstanceOptionValueRel instanceof
				CPInstanceOptionValueRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpInstanceOptionValueRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpInstanceOptionValueRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpInstanceOptionValueRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPInstanceOptionValueRel implementation " +
					cpInstanceOptionValueRel.getClass());
		}

		CPInstanceOptionValueRelModelImpl cpInstanceOptionValueRelModelImpl =
			(CPInstanceOptionValueRelModelImpl)cpInstanceOptionValueRel;

		if (Validator.isNull(cpInstanceOptionValueRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpInstanceOptionValueRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpInstanceOptionValueRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpInstanceOptionValueRel.setCreateDate(date);
			}
			else {
				cpInstanceOptionValueRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpInstanceOptionValueRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpInstanceOptionValueRel.setModifiedDate(date);
			}
			else {
				cpInstanceOptionValueRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpInstanceOptionValueRel)) {
				if (!isNew) {
					session.evict(
						CPInstanceOptionValueRelImpl.class,
						cpInstanceOptionValueRel.getPrimaryKeyObj());
				}

				session.save(cpInstanceOptionValueRel);
			}
			else {
				cpInstanceOptionValueRel =
					(CPInstanceOptionValueRel)session.merge(
						cpInstanceOptionValueRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpInstanceOptionValueRel.getCtCollectionId() != 0) {
			if (isNew) {
				cpInstanceOptionValueRel.setNew(false);
			}

			cpInstanceOptionValueRel.resetOriginalValues();

			return cpInstanceOptionValueRel;
		}

		entityCache.putResult(
			CPInstanceOptionValueRelImpl.class,
			cpInstanceOptionValueRelModelImpl, false, true);

		cacheUniqueFindersCache(cpInstanceOptionValueRelModelImpl);

		if (isNew) {
			cpInstanceOptionValueRel.setNew(false);
		}

		cpInstanceOptionValueRel.resetOriginalValues();

		return cpInstanceOptionValueRel;
	}

	/**
	 * Returns the cp instance option value rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp instance option value rel
	 * @return the cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPInstanceOptionValueRelException {

		CPInstanceOptionValueRel cpInstanceOptionValueRel = fetchByPrimaryKey(
			primaryKey);

		if (cpInstanceOptionValueRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPInstanceOptionValueRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpInstanceOptionValueRel;
	}

	/**
	 * Returns the cp instance option value rel with the primary key or throws a <code>NoSuchCPInstanceOptionValueRelException</code> if it could not be found.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel
	 * @throws NoSuchCPInstanceOptionValueRelException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel findByPrimaryKey(
			long CPInstanceOptionValueRelId)
		throws NoSuchCPInstanceOptionValueRelException {

		return findByPrimaryKey((Serializable)CPInstanceOptionValueRelId);
	}

	/**
	 * Returns the cp instance option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp instance option value rel
	 * @return the cp instance option value rel, or <code>null</code> if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				CPInstanceOptionValueRel.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CPInstanceOptionValueRel cpInstanceOptionValueRel = null;

		Session session = null;

		try {
			session = openSession();

			cpInstanceOptionValueRel = (CPInstanceOptionValueRel)session.get(
				CPInstanceOptionValueRelImpl.class, primaryKey);

			if (cpInstanceOptionValueRel != null) {
				cacheResult(cpInstanceOptionValueRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpInstanceOptionValueRel;
	}

	/**
	 * Returns the cp instance option value rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel, or <code>null</code> if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel fetchByPrimaryKey(
		long CPInstanceOptionValueRelId) {

		return fetchByPrimaryKey((Serializable)CPInstanceOptionValueRelId);
	}

	@Override
	public Map<Serializable, CPInstanceOptionValueRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CPInstanceOptionValueRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPInstanceOptionValueRel> map =
			new HashMap<Serializable, CPInstanceOptionValueRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPInstanceOptionValueRel cpInstanceOptionValueRel =
				fetchByPrimaryKey(primaryKey);

			if (cpInstanceOptionValueRel != null) {
				map.put(primaryKey, cpInstanceOptionValueRel);
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

			for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
					(List<CPInstanceOptionValueRel>)query.list()) {

				map.put(
					cpInstanceOptionValueRel.getPrimaryKeyObj(),
					cpInstanceOptionValueRel);

				cacheResult(cpInstanceOptionValueRel);
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
	 * Returns all the cp instance option value rels.
	 *
	 * @return the cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp instance option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp instance option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> findAll(
		int start, int end,
		OrderByComparator<CPInstanceOptionValueRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

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

		List<CPInstanceOptionValueRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPInstanceOptionValueRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPINSTANCEOPTIONVALUEREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPINSTANCEOPTIONVALUEREL;

				sql = sql.concat(
					CPInstanceOptionValueRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPInstanceOptionValueRel>)QueryUtil.list(
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
	 * Removes all the cp instance option value rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPInstanceOptionValueRel cpInstanceOptionValueRel : findAll()) {
			remove(cpInstanceOptionValueRel);
		}
	}

	/**
	 * Returns the number of cp instance option value rels.
	 *
	 * @return the number of cp instance option value rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPInstanceOptionValueRel.class);

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
					_SQL_COUNT_CPINSTANCEOPTIONVALUEREL);

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
		return "CPInstanceOptionValueRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPINSTANCEOPTIONVALUEREL;
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
		return CPInstanceOptionValueRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPInstanceOptionValueRel";
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
		ctStrictColumnNames.add("CPDefinitionOptionRelId");
		ctStrictColumnNames.add("CPDefinitionOptionValueRelId");
		ctStrictColumnNames.add("CPInstanceId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPInstanceOptionValueRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {
				"CPDefinitionOptionRelId", "CPDefinitionOptionValueRelId",
				"CPInstanceId"
			});
	}

	/**
	 * Initializes the cp instance option value rel persistence.
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

		_finderPathWithPaginationFindByCPDefinitionOptionRelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPDefinitionOptionRelId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionOptionRelId"}, true);

		_finderPathWithoutPaginationFindByCPDefinitionOptionRelId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCPDefinitionOptionRelId",
				new String[] {Long.class.getName()},
				new String[] {"CPDefinitionOptionRelId"}, true);

		_finderPathCountByCPDefinitionOptionRelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPDefinitionOptionRelId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionOptionRelId"}, false);

		_finderPathWithPaginationFindByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPInstanceId"}, true);

		_finderPathWithoutPaginationFindByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPInstanceId",
			new String[] {Long.class.getName()}, new String[] {"CPInstanceId"},
			true);

		_finderPathCountByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPInstanceId",
			new String[] {Long.class.getName()}, new String[] {"CPInstanceId"},
			false);

		_finderPathWithPaginationFindByCDORI_CII = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCDORI_CII",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CPDefinitionOptionRelId", "CPInstanceId"}, true);

		_finderPathWithoutPaginationFindByCDORI_CII = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCDORI_CII",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionOptionRelId", "CPInstanceId"}, true);

		_finderPathCountByCDORI_CII = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCDORI_CII",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionOptionRelId", "CPInstanceId"}, false);

		_finderPathFetchByCDOVRI_CII = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCDOVRI_CII",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionOptionValueRelId", "CPInstanceId"},
			true);

		_finderPathCountByCDOVRI_CII = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCDOVRI_CII",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionOptionValueRelId", "CPInstanceId"},
			false);

		_finderPathFetchByCDORI_CDOVRI_CII = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCDORI_CDOVRI_CII",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"CPDefinitionOptionRelId", "CPDefinitionOptionValueRelId",
				"CPInstanceId"
			},
			true);

		_finderPathCountByCDORI_CDOVRI_CII = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCDORI_CDOVRI_CII",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"CPDefinitionOptionRelId", "CPDefinitionOptionValueRelId",
				"CPInstanceId"
			},
			false);

		_setCPInstanceOptionValueRelUtilPersistence(this);
	}

	public void destroy() {
		_setCPInstanceOptionValueRelUtilPersistence(null);

		entityCache.removeCache(CPInstanceOptionValueRelImpl.class.getName());
	}

	private void _setCPInstanceOptionValueRelUtilPersistence(
		CPInstanceOptionValueRelPersistence
			cpInstanceOptionValueRelPersistence) {

		try {
			Field field = CPInstanceOptionValueRelUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, cpInstanceOptionValueRelPersistence);
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

	private static final String _SQL_SELECT_CPINSTANCEOPTIONVALUEREL =
		"SELECT cpInstanceOptionValueRel FROM CPInstanceOptionValueRel cpInstanceOptionValueRel";

	private static final String _SQL_SELECT_CPINSTANCEOPTIONVALUEREL_WHERE =
		"SELECT cpInstanceOptionValueRel FROM CPInstanceOptionValueRel cpInstanceOptionValueRel WHERE ";

	private static final String _SQL_COUNT_CPINSTANCEOPTIONVALUEREL =
		"SELECT COUNT(cpInstanceOptionValueRel) FROM CPInstanceOptionValueRel cpInstanceOptionValueRel";

	private static final String _SQL_COUNT_CPINSTANCEOPTIONVALUEREL_WHERE =
		"SELECT COUNT(cpInstanceOptionValueRel) FROM CPInstanceOptionValueRel cpInstanceOptionValueRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpInstanceOptionValueRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPInstanceOptionValueRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPInstanceOptionValueRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceOptionValueRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}