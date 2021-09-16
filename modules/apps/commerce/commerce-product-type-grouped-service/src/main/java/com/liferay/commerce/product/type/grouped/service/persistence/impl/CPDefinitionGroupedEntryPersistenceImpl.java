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

package com.liferay.commerce.product.type.grouped.service.persistence.impl;

import com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntryTable;
import com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryImpl;
import com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl;
import com.liferay.commerce.product.type.grouped.service.persistence.CPDefinitionGroupedEntryPersistence;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the cp definition grouped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @generated
 */
public class CPDefinitionGroupedEntryPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionGroupedEntry>
	implements CPDefinitionGroupedEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionGroupedEntryUtil</code> to access the cp definition grouped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionGroupedEntryImpl.class.getName();

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
	 * Returns all the cp definition grouped entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		List<CPDefinitionGroupedEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : list) {
					if (!uuid.equals(cpDefinitionGroupedEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
				sb.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(
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
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_First(
			String uuid,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
	}

	/**
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		List<CPDefinitionGroupedEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionGroupedEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry[] findByUuid_PrevAndNext(
			long CPDefinitionGroupedEntryId, String uuid,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByPrimaryKey(
			CPDefinitionGroupedEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry[] array =
				new CPDefinitionGroupedEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpDefinitionGroupedEntry, uuid, orderByComparator,
				true);

			array[1] = cpDefinitionGroupedEntry;

			array[2] = getByUuid_PrevAndNext(
				session, cpDefinitionGroupedEntry, uuid, orderByComparator,
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

	protected CPDefinitionGroupedEntry getByUuid_PrevAndNext(
		Session session, CPDefinitionGroupedEntry cpDefinitionGroupedEntry,
		String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
			sb.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionGroupedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionGroupedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition grouped entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
		"cpDefinitionGroupedEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpDefinitionGroupedEntry.uuid IS NULL OR cpDefinitionGroupedEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionGroupedEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUUID_G(
			uuid, groupId);

		if (cpDefinitionGroupedEntry == null) {
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

			throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
		}

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof CPDefinitionGroupedEntry) {
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
				(CPDefinitionGroupedEntry)result;

			if (!Objects.equals(uuid, cpDefinitionGroupedEntry.getUuid()) ||
				(groupId != cpDefinitionGroupedEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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

				List<CPDefinitionGroupedEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
						list.get(0);

					result = cpDefinitionGroupedEntry;

					cacheResult(cpDefinitionGroupedEntry);
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
			return (CPDefinitionGroupedEntry)result;
		}
	}

	/**
	 * Removes the cp definition grouped entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition grouped entry that was removed
	 */
	@Override
	public CPDefinitionGroupedEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByUUID_G(
			uuid, groupId);

		return remove(cpDefinitionGroupedEntry);
	}

	/**
	 * Returns the number of cp definition grouped entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
		"cpDefinitionGroupedEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpDefinitionGroupedEntry.uuid IS NULL OR cpDefinitionGroupedEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpDefinitionGroupedEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		List<CPDefinitionGroupedEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : list) {
					if (!uuid.equals(cpDefinitionGroupedEntry.getUuid()) ||
						(companyId !=
							cpDefinitionGroupedEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
				sb.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(
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
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
	}

	/**
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		List<CPDefinitionGroupedEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionGroupedEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry[] findByUuid_C_PrevAndNext(
			long CPDefinitionGroupedEntryId, String uuid, long companyId,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		uuid = Objects.toString(uuid, "");

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByPrimaryKey(
			CPDefinitionGroupedEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry[] array =
				new CPDefinitionGroupedEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpDefinitionGroupedEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = cpDefinitionGroupedEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpDefinitionGroupedEntry, uuid, companyId,
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

	protected CPDefinitionGroupedEntry getByUuid_C_PrevAndNext(
		Session session, CPDefinitionGroupedEntry cpDefinitionGroupedEntry,
		String uuid, long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
			sb.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionGroupedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionGroupedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition grouped entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"cpDefinitionGroupedEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpDefinitionGroupedEntry.uuid IS NULL OR cpDefinitionGroupedEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpDefinitionGroupedEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCPDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;

	/**
	 * Returns all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId) {

		return findByCPDefinitionId(
			CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {

		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		return findByCPDefinitionId(
			CPDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPDefinitionId;
				finderArgs = new Object[] {CPDefinitionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPDefinitionId;
			finderArgs = new Object[] {
				CPDefinitionId, start, end, orderByComparator
			};
		}

		List<CPDefinitionGroupedEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : list) {
					if (CPDefinitionId !=
							cpDefinitionGroupedEntry.getCPDefinitionId()) {

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

			sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(
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
	 * Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByCPDefinitionId_First(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			fetchByCPDefinitionId_First(CPDefinitionId, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
	}

	/**
	 * Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		List<CPDefinitionGroupedEntry> list = findByCPDefinitionId(
			CPDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByCPDefinitionId_Last(
			long CPDefinitionId,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPDefinitionId=");
		sb.append(CPDefinitionId);

		sb.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionGroupedEntry> list = findByCPDefinitionId(
			CPDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry[] findByCPDefinitionId_PrevAndNext(
			long CPDefinitionGroupedEntryId, long CPDefinitionId,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByPrimaryKey(
			CPDefinitionGroupedEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry[] array =
				new CPDefinitionGroupedEntryImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionGroupedEntry, CPDefinitionId,
				orderByComparator, true);

			array[1] = cpDefinitionGroupedEntry;

			array[2] = getByCPDefinitionId_PrevAndNext(
				session, cpDefinitionGroupedEntry, CPDefinitionId,
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

	protected CPDefinitionGroupedEntry getByCPDefinitionId_PrevAndNext(
		Session session, CPDefinitionGroupedEntry cpDefinitionGroupedEntry,
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
			sb.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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
						cpDefinitionGroupedEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionGroupedEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition grouped entries where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				findByCPDefinitionId(
					CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = _finderPathCountByCPDefinitionId;

		Object[] finderArgs = new Object[] {CPDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 =
		"cpDefinitionGroupedEntry.CPDefinitionId = ?";

	private FinderPath _finderPathFetchByC_E;
	private FinderPath _finderPathCountByC_E;

	/**
	 * Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCProductId = &#63; or throws a <code>NoSuchCPDefinitionGroupedEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCProductId the entry c product ID
	 * @return the matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByC_E(
			long CPDefinitionId, long entryCProductId)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByC_E(
			CPDefinitionId, entryCProductId);

		if (cpDefinitionGroupedEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionId=");
			sb.append(CPDefinitionId);

			sb.append(", entryCProductId=");
			sb.append(entryCProductId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionGroupedEntryException(sb.toString());
		}

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCProductId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCProductId the entry c product ID
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByC_E(
		long CPDefinitionId, long entryCProductId) {

		return fetchByC_E(CPDefinitionId, entryCProductId, true);
	}

	/**
	 * Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCProductId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCProductId the entry c product ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByC_E(
		long CPDefinitionId, long entryCProductId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CPDefinitionId, entryCProductId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_E, finderArgs);
		}

		if (result instanceof CPDefinitionGroupedEntry) {
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
				(CPDefinitionGroupedEntry)result;

			if ((CPDefinitionId !=
					cpDefinitionGroupedEntry.getCPDefinitionId()) ||
				(entryCProductId !=
					cpDefinitionGroupedEntry.getEntryCProductId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_E_CPDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_C_E_ENTRYCPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				queryPos.add(entryCProductId);

				List<CPDefinitionGroupedEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_E, finderArgs, list);
					}
				}
				else {
					CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
						list.get(0);

					result = cpDefinitionGroupedEntry;

					cacheResult(cpDefinitionGroupedEntry);
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
			return (CPDefinitionGroupedEntry)result;
		}
	}

	/**
	 * Removes the cp definition grouped entry where CPDefinitionId = &#63; and entryCProductId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCProductId the entry c product ID
	 * @return the cp definition grouped entry that was removed
	 */
	@Override
	public CPDefinitionGroupedEntry removeByC_E(
			long CPDefinitionId, long entryCProductId)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByC_E(
			CPDefinitionId, entryCProductId);

		return remove(cpDefinitionGroupedEntry);
	}

	/**
	 * Returns the number of cp definition grouped entries where CPDefinitionId = &#63; and entryCProductId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCProductId the entry c product ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByC_E(long CPDefinitionId, long entryCProductId) {
		FinderPath finderPath = _finderPathCountByC_E;

		Object[] finderArgs = new Object[] {CPDefinitionId, entryCProductId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_E_CPDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_C_E_ENTRYCPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				queryPos.add(entryCProductId);

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

	private static final String _FINDER_COLUMN_C_E_CPDEFINITIONID_2 =
		"cpDefinitionGroupedEntry.CPDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_C_E_ENTRYCPRODUCTID_2 =
		"cpDefinitionGroupedEntry.entryCProductId = ?";

	public CPDefinitionGroupedEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinitionGroupedEntry.class);

		setModelImplClass(CPDefinitionGroupedEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionGroupedEntryTable.INSTANCE);
	}

	/**
	 * Caches the cp definition grouped entry in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionGroupedEntry the cp definition grouped entry
	 */
	@Override
	public void cacheResult(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		entityCache.putResult(
			CPDefinitionGroupedEntryImpl.class,
			cpDefinitionGroupedEntry.getPrimaryKey(), cpDefinitionGroupedEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				cpDefinitionGroupedEntry.getUuid(),
				cpDefinitionGroupedEntry.getGroupId()
			},
			cpDefinitionGroupedEntry);

		finderCache.putResult(
			_finderPathFetchByC_E,
			new Object[] {
				cpDefinitionGroupedEntry.getCPDefinitionId(),
				cpDefinitionGroupedEntry.getEntryCProductId()
			},
			cpDefinitionGroupedEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp definition grouped entries in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionGroupedEntries the cp definition grouped entries
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpDefinitionGroupedEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				cpDefinitionGroupedEntries) {

			if (entityCache.getResult(
					CPDefinitionGroupedEntryImpl.class,
					cpDefinitionGroupedEntry.getPrimaryKey()) == null) {

				cacheResult(cpDefinitionGroupedEntry);
			}
		}
	}

	/**
	 * Clears the cache for all cp definition grouped entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionGroupedEntryImpl.class);

		finderCache.clearCache(CPDefinitionGroupedEntryImpl.class);
	}

	/**
	 * Clears the cache for the cp definition grouped entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		entityCache.removeResult(
			CPDefinitionGroupedEntryImpl.class, cpDefinitionGroupedEntry);
	}

	@Override
	public void clearCache(
		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries) {

		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				cpDefinitionGroupedEntries) {

			entityCache.removeResult(
				CPDefinitionGroupedEntryImpl.class, cpDefinitionGroupedEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionGroupedEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionGroupedEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionGroupedEntryModelImpl cpDefinitionGroupedEntryModelImpl) {

		Object[] args = new Object[] {
			cpDefinitionGroupedEntryModelImpl.getUuid(),
			cpDefinitionGroupedEntryModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, cpDefinitionGroupedEntryModelImpl);

		args = new Object[] {
			cpDefinitionGroupedEntryModelImpl.getCPDefinitionId(),
			cpDefinitionGroupedEntryModelImpl.getEntryCProductId()
		};

		finderCache.putResult(_finderPathCountByC_E, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_E, args, cpDefinitionGroupedEntryModelImpl);
	}

	/**
	 * Creates a new cp definition grouped entry with the primary key. Does not add the cp definition grouped entry to the database.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key for the new cp definition grouped entry
	 * @return the new cp definition grouped entry
	 */
	@Override
	public CPDefinitionGroupedEntry create(long CPDefinitionGroupedEntryId) {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			new CPDefinitionGroupedEntryImpl();

		cpDefinitionGroupedEntry.setNew(true);
		cpDefinitionGroupedEntry.setPrimaryKey(CPDefinitionGroupedEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionGroupedEntry.setUuid(uuid);

		cpDefinitionGroupedEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Removes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry that was removed
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry remove(long CPDefinitionGroupedEntryId)
		throws NoSuchCPDefinitionGroupedEntryException {

		return remove((Serializable)CPDefinitionGroupedEntryId);
	}

	/**
	 * Removes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry that was removed
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry remove(Serializable primaryKey)
		throws NoSuchCPDefinitionGroupedEntryException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
				(CPDefinitionGroupedEntry)session.get(
					CPDefinitionGroupedEntryImpl.class, primaryKey);

			if (cpDefinitionGroupedEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionGroupedEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionGroupedEntry);
		}
		catch (NoSuchCPDefinitionGroupedEntryException noSuchEntityException) {
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
	protected CPDefinitionGroupedEntry removeImpl(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionGroupedEntry)) {
				cpDefinitionGroupedEntry =
					(CPDefinitionGroupedEntry)session.get(
						CPDefinitionGroupedEntryImpl.class,
						cpDefinitionGroupedEntry.getPrimaryKeyObj());
			}

			if (cpDefinitionGroupedEntry != null) {
				session.delete(cpDefinitionGroupedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionGroupedEntry != null) {
			clearCache(cpDefinitionGroupedEntry);
		}

		return cpDefinitionGroupedEntry;
	}

	@Override
	public CPDefinitionGroupedEntry updateImpl(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		boolean isNew = cpDefinitionGroupedEntry.isNew();

		if (!(cpDefinitionGroupedEntry instanceof
				CPDefinitionGroupedEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionGroupedEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionGroupedEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionGroupedEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionGroupedEntry implementation " +
					cpDefinitionGroupedEntry.getClass());
		}

		CPDefinitionGroupedEntryModelImpl cpDefinitionGroupedEntryModelImpl =
			(CPDefinitionGroupedEntryModelImpl)cpDefinitionGroupedEntry;

		if (Validator.isNull(cpDefinitionGroupedEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionGroupedEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpDefinitionGroupedEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionGroupedEntry.setCreateDate(date);
			}
			else {
				cpDefinitionGroupedEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpDefinitionGroupedEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionGroupedEntry.setModifiedDate(date);
			}
			else {
				cpDefinitionGroupedEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpDefinitionGroupedEntry);
			}
			else {
				cpDefinitionGroupedEntry =
					(CPDefinitionGroupedEntry)session.merge(
						cpDefinitionGroupedEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionGroupedEntryImpl.class,
			cpDefinitionGroupedEntryModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionGroupedEntryModelImpl);

		if (isNew) {
			cpDefinitionGroupedEntry.setNew(false);
		}

		cpDefinitionGroupedEntry.resetOriginalValues();

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionGroupedEntryException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByPrimaryKey(
			primaryKey);

		if (cpDefinitionGroupedEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionGroupedEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry with the primary key or throws a <code>NoSuchCPDefinitionGroupedEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByPrimaryKey(
			long CPDefinitionGroupedEntryId)
		throws NoSuchCPDefinitionGroupedEntryException {

		return findByPrimaryKey((Serializable)CPDefinitionGroupedEntryId);
	}

	/**
	 * Returns the cp definition grouped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry, or <code>null</code> if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByPrimaryKey(
		long CPDefinitionGroupedEntryId) {

		return fetchByPrimaryKey((Serializable)CPDefinitionGroupedEntryId);
	}

	/**
	 * Returns all the cp definition grouped entries.
	 *
	 * @return the cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionGroupedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		List<CPDefinitionGroupedEntry> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONGROUPEDENTRY;

				sql = sql.concat(
					CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(
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
	 * Removes all the cp definition grouped entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : findAll()) {
			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries.
	 *
	 * @return the number of cp definition grouped entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_CPDEFINITIONGROUPEDENTRY);

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
		return "CPDefinitionGroupedEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONGROUPEDENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionGroupedEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition grouped entry persistence.
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

		_finderPathFetchByC_E = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_E",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "entryCProductId"}, true);

		_finderPathCountByC_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_E",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"CPDefinitionId", "entryCProductId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionGroupedEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPDEFINITIONGROUPEDENTRY =
		"SELECT cpDefinitionGroupedEntry FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry";

	private static final String _SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE =
		"SELECT cpDefinitionGroupedEntry FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry WHERE ";

	private static final String _SQL_COUNT_CPDEFINITIONGROUPEDENTRY =
		"SELECT COUNT(cpDefinitionGroupedEntry) FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry";

	private static final String _SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE =
		"SELECT COUNT(cpDefinitionGroupedEntry) FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionGroupedEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionGroupedEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionGroupedEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionGroupedEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}