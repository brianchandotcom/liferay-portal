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

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryTable;
import com.liferay.commerce.product.model.impl.CPAttachmentFileEntryImpl;
import com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryPersistence;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryUtil;
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

import java.sql.Timestamp;

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
 * The persistence implementation for the cp attachment file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CPAttachmentFileEntryPersistenceImpl
	extends BasePersistenceImpl<CPAttachmentFileEntry>
	implements CPAttachmentFileEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPAttachmentFileEntryUtil</code> to access the cp attachment file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPAttachmentFileEntryImpl.class.getName();

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
	 * Returns all the cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

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

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if (!uuid.equals(cpAttachmentFileEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_First(
			String uuid,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByUuid_PrevAndNext(
			long CPAttachmentFileEntryId, String uuid,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		uuid = Objects.toString(uuid, "");

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpAttachmentFileEntry, uuid, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByUuid_PrevAndNext(
				session, cpAttachmentFileEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByUuid_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		String uuid, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

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

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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
		"cpAttachmentFileEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpAttachmentFileEntry.uuid IS NULL OR cpAttachmentFileEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUUID_G(
			uuid, groupId);

		if (cpAttachmentFileEntry == null) {
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

			throw new NoSuchCPAttachmentFileEntryException(sb.toString());
		}

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof CPAttachmentFileEntry) {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				(CPAttachmentFileEntry)result;

			if (!Objects.equals(uuid, cpAttachmentFileEntry.getUuid()) ||
				(groupId != cpAttachmentFileEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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

				List<CPAttachmentFileEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPAttachmentFileEntry cpAttachmentFileEntry = list.get(0);

					result = cpAttachmentFileEntry;

					cacheResult(cpAttachmentFileEntry);
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
			return (CPAttachmentFileEntry)result;
		}
	}

	/**
	 * Removes the cp attachment file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp attachment file entry that was removed
	 */
	@Override
	public CPAttachmentFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByUUID_G(
			uuid, groupId);

		return remove(cpAttachmentFileEntry);
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

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

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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
		"cpAttachmentFileEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpAttachmentFileEntry.uuid IS NULL OR cpAttachmentFileEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpAttachmentFileEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

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

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if (!uuid.equals(cpAttachmentFileEntry.getUuid()) ||
						(companyId != cpAttachmentFileEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByUuid_C_PrevAndNext(
			long CPAttachmentFileEntryId, String uuid, long companyId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		uuid = Objects.toString(uuid, "");

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpAttachmentFileEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpAttachmentFileEntry, uuid, companyId,
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

	protected CPAttachmentFileEntry getByUuid_C_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		String uuid, long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

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

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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
		"cpAttachmentFileEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpAttachmentFileEntry.uuid IS NULL OR cpAttachmentFileEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpAttachmentFileEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId !=
							cpAttachmentFileEntry.getClassNameId()) ||
						(classPK != cpAttachmentFileEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK,
				orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK,
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

	protected CPAttachmentFileEntry getByC_C_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"cpAttachmentFileEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"cpAttachmentFileEntry.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;

	/**
	 * Returns all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status) {

		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtD_S;
		finderArgs = new Object[] {
			_getTime(displayDate), status, start, end, orderByComparator
		};

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((displayDate.getTime() <=
							cpAttachmentFileEntry.getDisplayDate(
							).getTime()) ||
						(status != cpAttachmentFileEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByLtD_S(
			displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByLtD_S_Last(
			displayDate, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByLtD_S(
			displayDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByLtD_S_PrevAndNext(
			long CPAttachmentFileEntryId, Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByLtD_S_PrevAndNext(
				session, cpAttachmentFileEntry, displayDate, status,
				orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByLtD_S_PrevAndNext(
				session, cpAttachmentFileEntry, displayDate, status,
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

	protected CPAttachmentFileEntry getByLtD_S_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindDisplayDate) {
			queryPos.add(new Timestamp(displayDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByLtD_S;

			finderArgs = new Object[] {_getTime(displayDate), status};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 =
		"cpAttachmentFileEntry.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"cpAttachmentFileEntry.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"cpAttachmentFileEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_F;
	private FinderPath _finderPathWithoutPaginationFindByC_C_F;
	private FinderPath _finderPathCountByC_C_F;

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId) {

		return findByC_C_F(
			classNameId, classPK, fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end) {

		return findByC_C_F(classNameId, classPK, fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByC_C_F(
			classNameId, classPK, fileEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_F(
		long classNameId, long classPK, long fileEntryId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_F;
				finderArgs = new Object[] {classNameId, classPK, fileEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_F;
			finderArgs = new Object[] {
				classNameId, classPK, fileEntryId, start, end, orderByComparator
			};
		}

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId !=
							cpAttachmentFileEntry.getClassNameId()) ||
						(classPK != cpAttachmentFileEntry.getClassPK()) ||
						(fileEntryId !=
							cpAttachmentFileEntry.getFileEntryId())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_F_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_F_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_F_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(fileEntryId);

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_F_First(
			long classNameId, long classPK, long fileEntryId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_F_First(
			classNameId, classPK, fileEntryId, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_F_First(
		long classNameId, long classPK, long fileEntryId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByC_C_F(
			classNameId, classPK, fileEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_F_Last(
			long classNameId, long classPK, long fileEntryId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_F_Last(
			classNameId, classPK, fileEntryId, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_F_Last(
		long classNameId, long classPK, long fileEntryId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByC_C_F(classNameId, classPK, fileEntryId);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_F(
			classNameId, classPK, fileEntryId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_F_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			long fileEntryId,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_F_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK,
				fileEntryId, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_F_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK,
				fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByC_C_F_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK, long fileEntryId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_F_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_F_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_F_FILEENTRYID_2);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(fileEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByC_C_F(
		long classNameId, long classPK, long fileEntryId) {

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByC_C_F(
					classNameId, classPK, fileEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and fileEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param fileEntryId the file entry ID
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_F(long classNameId, long classPK, long fileEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_F;

			finderArgs = new Object[] {classNameId, classPK, fileEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_F_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_F_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_F_FILEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(fileEntryId);

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

	private static final String _FINDER_COLUMN_C_C_F_CLASSNAMEID_2 =
		"cpAttachmentFileEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_F_CLASSPK_2 =
		"cpAttachmentFileEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_F_FILEENTRYID_2 =
		"cpAttachmentFileEntry.fileEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL) {

		return findByC_C_C(
			classNameId, classPK, cdnURL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end) {

		return findByC_C_C(classNameId, classPK, cdnURL, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByC_C_C(
			classNameId, classPK, cdnURL, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_C(
		long classNameId, long classPK, String cdnURL, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		cdnURL = Objects.toString(cdnURL, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_C;
				finderArgs = new Object[] {classNameId, classPK, cdnURL};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_C;
			finderArgs = new Object[] {
				classNameId, classPK, cdnURL, start, end, orderByComparator
			};
		}

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId !=
							cpAttachmentFileEntry.getClassNameId()) ||
						(classPK != cpAttachmentFileEntry.getClassPK()) ||
						!cdnURL.equals(cpAttachmentFileEntry.getCDNURL())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			boolean bindCDNURL = false;

			if (cdnURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_C_CDNURL_3);
			}
			else {
				bindCDNURL = true;

				sb.append(_FINDER_COLUMN_C_C_C_CDNURL_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindCDNURL) {
					queryPos.add(cdnURL);
				}

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_C_First(
			long classNameId, long classPK, String cdnURL,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_C_First(
			classNameId, classPK, cdnURL, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", cdnURL=");
		sb.append(cdnURL);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_C_First(
		long classNameId, long classPK, String cdnURL,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByC_C_C(
			classNameId, classPK, cdnURL, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_C_Last(
			long classNameId, long classPK, String cdnURL,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_C_Last(
			classNameId, classPK, cdnURL, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", cdnURL=");
		sb.append(cdnURL);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_C_Last(
		long classNameId, long classPK, String cdnURL,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByC_C_C(classNameId, classPK, cdnURL);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_C(
			classNameId, classPK, cdnURL, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_C_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			String cdnURL,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		cdnURL = Objects.toString(cdnURL, "");

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_C_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK, cdnURL,
				orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_C_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK, cdnURL,
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

	protected CPAttachmentFileEntry getByC_C_C_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK, String cdnURL,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

		boolean bindCDNURL = false;

		if (cdnURL.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_C_CDNURL_3);
		}
		else {
			bindCDNURL = true;

			sb.append(_FINDER_COLUMN_C_C_C_CDNURL_2);
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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (bindCDNURL) {
			queryPos.add(cdnURL);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 */
	@Override
	public void removeByC_C_C(long classNameId, long classPK, String cdnURL) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByC_C_C(
					classNameId, classPK, cdnURL, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and cdnURL = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param cdnURL the cdn url
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_C(long classNameId, long classPK, String cdnURL) {
		cdnURL = Objects.toString(cdnURL, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_C;

			finderArgs = new Object[] {classNameId, classPK, cdnURL};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			boolean bindCDNURL = false;

			if (cdnURL.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_C_CDNURL_3);
			}
			else {
				bindCDNURL = true;

				sb.append(_FINDER_COLUMN_C_C_C_CDNURL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindCDNURL) {
					queryPos.add(cdnURL);
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

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"cpAttachmentFileEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"cpAttachmentFileEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CDNURL_2 =
		"cpAttachmentFileEntry.cdnURL = ?";

	private static final String _FINDER_COLUMN_C_C_C_CDNURL_3 =
		"(cpAttachmentFileEntry.cdnURL IS NULL OR cpAttachmentFileEntry.cdnURL = '')";

	private FinderPath _finderPathWithPaginationFindByC_C_LtD_S;
	private FinderPath _finderPathWithPaginationCountByC_C_LtD_S;

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status) {

		return findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end) {

		return findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_C_LtD_S;
		finderArgs = new Object[] {
			classNameId, classPK, _getTime(displayDate), status, start, end,
			orderByComparator
		};

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId !=
							cpAttachmentFileEntry.getClassNameId()) ||
						(classPK != cpAttachmentFileEntry.getClassPK()) ||
						(displayDate.getTime() <=
							cpAttachmentFileEntry.getDisplayDate(
							).getTime()) ||
						(status != cpAttachmentFileEntry.getStatus())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_LTD_S_CLASSPK_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_C_C_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_LtD_S_First(
			long classNameId, long classPK, Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_LtD_S_First(
			classNameId, classPK, displayDate, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_LtD_S_First(
		long classNameId, long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_LtD_S_Last(
			long classNameId, long classPK, Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_LtD_S_Last(
			classNameId, classPK, displayDate, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_LtD_S_Last(
		long classNameId, long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByC_C_LtD_S(classNameId, classPK, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_LtD_S(
			classNameId, classPK, displayDate, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_LtD_S_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			Date displayDate, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_LtD_S_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK,
				displayDate, status, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_LtD_S_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK,
				displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByC_C_LtD_S_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_LTD_S_CLASSPK_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_C_C_LTD_S_STATUS_2);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (bindDisplayDate) {
			queryPos.add(new Timestamp(displayDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status) {

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByC_C_LtD_S(
					classNameId, classPK, displayDate, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_LtD_S(
		long classNameId, long classPK, Date displayDate, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_C_LtD_S;

			finderArgs = new Object[] {
				classNameId, classPK, _getTime(displayDate), status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_LTD_S_CLASSPK_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_C_C_LTD_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2 =
		"cpAttachmentFileEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTD_S_CLASSPK_2 =
		"cpAttachmentFileEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1 =
		"cpAttachmentFileEntry.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2 =
		"cpAttachmentFileEntry.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_C_C_LTD_S_STATUS_2 =
		"cpAttachmentFileEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_T_ST;
	private FinderPath _finderPathWithoutPaginationFindByC_C_T_ST;
	private FinderPath _finderPathCountByC_C_T_ST;

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status) {

		return findByC_C_T_ST(
			classNameId, classPK, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end) {

		return findByC_C_T_ST(
			classNameId, classPK, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByC_C_T_ST(
			classNameId, classPK, type, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_T_ST;
				finderArgs = new Object[] {classNameId, classPK, type, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_T_ST;
			finderArgs = new Object[] {
				classNameId, classPK, type, status, start, end,
				orderByComparator
			};
		}

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId !=
							cpAttachmentFileEntry.getClassNameId()) ||
						(classPK != cpAttachmentFileEntry.getClassPK()) ||
						(type != cpAttachmentFileEntry.getType()) ||
						(status != cpAttachmentFileEntry.getStatus())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_ST_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_ST_TYPE_2);

			sb.append(_FINDER_COLUMN_C_C_T_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				queryPos.add(status);

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_ST_First(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_ST_First(
			classNameId, classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_ST_First(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByC_C_T_ST(
			classNameId, classPK, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_ST_Last(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_ST_Last(
			classNameId, classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_ST_Last(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByC_C_T_ST(classNameId, classPK, type, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_T_ST(
			classNameId, classPK, type, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_T_ST_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_T_ST_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK, type,
				status, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_T_ST_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK, type,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByC_C_T_ST_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_T_ST_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_T_ST_TYPE_2);

		sb.append(_FINDER_COLUMN_C_C_T_ST_STATUS_2);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByC_C_T_ST(
		long classNameId, long classPK, int type, int status) {

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByC_C_T_ST(
					classNameId, classPK, type, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_T_ST(
		long classNameId, long classPK, int type, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_T_ST;

			finderArgs = new Object[] {classNameId, classPK, type, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_ST_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_ST_TYPE_2);

			sb.append(_FINDER_COLUMN_C_C_T_ST_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2 =
		"cpAttachmentFileEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_ST_CLASSPK_2 =
		"cpAttachmentFileEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_ST_TYPE_2 =
		"cpAttachmentFileEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_ST_STATUS_2 =
		"cpAttachmentFileEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_T_NotST;
	private FinderPath _finderPathWithPaginationCountByC_C_T_NotST;

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status) {

		return findByC_C_T_NotST(
			classNameId, classPK, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end) {

		return findByC_C_T_NotST(
			classNameId, classPK, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findByC_C_T_NotST(
			classNameId, classPK, type, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(
		long classNameId, long classPK, int type, int status, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_C_T_NotST;
		finderArgs = new Object[] {
			classNameId, classPK, type, status, start, end, orderByComparator
		};

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId !=
							cpAttachmentFileEntry.getClassNameId()) ||
						(classPK != cpAttachmentFileEntry.getClassPK()) ||
						(type != cpAttachmentFileEntry.getType()) ||
						(status == cpAttachmentFileEntry.getStatus())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_TYPE_2);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				queryPos.add(status);

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_NotST_First(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_NotST_First(
			classNameId, classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_NotST_First(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		List<CPAttachmentFileEntry> list = findByC_C_T_NotST(
			classNameId, classPK, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_NotST_Last(
			long classNameId, long classPK, int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_NotST_Last(
			classNameId, classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status!=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPAttachmentFileEntryException(sb.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_NotST_Last(
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		int count = countByC_C_T_NotST(classNameId, classPK, type, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_T_NotST(
			classNameId, classPK, type, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_T_NotST_PrevAndNext(
			long CPAttachmentFileEntryId, long classNameId, long classPK,
			int type, int status,
			OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(
			CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_T_NotST_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK, type,
				status, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_T_NotST_PrevAndNext(
				session, cpAttachmentFileEntry, classNameId, classPK, type,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByC_C_T_NotST_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_T_NOTST_TYPE_2);

		sb.append(_FINDER_COLUMN_C_C_T_NOTST_STATUS_2);

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
			sb.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpAttachmentFileEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPAttachmentFileEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByC_C_T_NotST(
		long classNameId, long classPK, int type, int status) {

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				findByC_C_T_NotST(
					classNameId, classPK, type, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_T_NotST(
		long classNameId, long classPK, int type, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_C_T_NotST;

			finderArgs = new Object[] {classNameId, classPK, type, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_TYPE_2);

			sb.append(_FINDER_COLUMN_C_C_T_NOTST_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2 =
		"cpAttachmentFileEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2 =
		"cpAttachmentFileEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_NOTST_TYPE_2 =
		"cpAttachmentFileEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_C_C_T_NOTST_STATUS_2 =
		"cpAttachmentFileEntry.status != ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (cpAttachmentFileEntry == null) {
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

			throw new NoSuchCPAttachmentFileEntryException(sb.toString());
		}

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof CPAttachmentFileEntry) {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				(CPAttachmentFileEntry)result;

			if ((companyId != cpAttachmentFileEntry.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					cpAttachmentFileEntry.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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

				List<CPAttachmentFileEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CPAttachmentFileEntryPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPAttachmentFileEntry cpAttachmentFileEntry = list.get(0);

					result = cpAttachmentFileEntry;

					cacheResult(cpAttachmentFileEntry);
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
			return (CPAttachmentFileEntry)result;
		}
	}

	/**
	 * Removes the cp attachment file entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the cp attachment file entry that was removed
	 */
	@Override
	public CPAttachmentFileEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(cpAttachmentFileEntry);
	}

	/**
	 * Returns the number of cp attachment file entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_ERC;

			finderArgs = new Object[] {companyId, externalReferenceCode};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"cpAttachmentFileEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"cpAttachmentFileEntry.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(cpAttachmentFileEntry.externalReferenceCode IS NULL OR cpAttachmentFileEntry.externalReferenceCode = '')";

	public CPAttachmentFileEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPAttachmentFileEntry.class);

		setModelImplClass(CPAttachmentFileEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CPAttachmentFileEntryTable.INSTANCE);
	}

	/**
	 * Caches the cp attachment file entry in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntry the cp attachment file entry
	 */
	@Override
	public void cacheResult(CPAttachmentFileEntry cpAttachmentFileEntry) {
		if (cpAttachmentFileEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CPAttachmentFileEntryImpl.class,
			cpAttachmentFileEntry.getPrimaryKey(), cpAttachmentFileEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				cpAttachmentFileEntry.getUuid(),
				cpAttachmentFileEntry.getGroupId()
			},
			cpAttachmentFileEntry);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				cpAttachmentFileEntry.getCompanyId(),
				cpAttachmentFileEntry.getExternalReferenceCode()
			},
			cpAttachmentFileEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cp attachment file entries in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntries the cp attachment file entries
	 */
	@Override
	public void cacheResult(
		List<CPAttachmentFileEntry> cpAttachmentFileEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpAttachmentFileEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			if (cpAttachmentFileEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CPAttachmentFileEntryImpl.class,
					cpAttachmentFileEntry.getPrimaryKey()) == null) {

				cacheResult(cpAttachmentFileEntry);
			}
		}
	}

	/**
	 * Clears the cache for all cp attachment file entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPAttachmentFileEntryImpl.class);

		finderCache.clearCache(CPAttachmentFileEntryImpl.class);
	}

	/**
	 * Clears the cache for the cp attachment file entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPAttachmentFileEntry cpAttachmentFileEntry) {
		entityCache.removeResult(
			CPAttachmentFileEntryImpl.class, cpAttachmentFileEntry);
	}

	@Override
	public void clearCache(
		List<CPAttachmentFileEntry> cpAttachmentFileEntries) {

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			entityCache.removeResult(
				CPAttachmentFileEntryImpl.class, cpAttachmentFileEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPAttachmentFileEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPAttachmentFileEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPAttachmentFileEntryModelImpl cpAttachmentFileEntryModelImpl) {

		Object[] args = new Object[] {
			cpAttachmentFileEntryModelImpl.getUuid(),
			cpAttachmentFileEntryModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, cpAttachmentFileEntryModelImpl);

		args = new Object[] {
			cpAttachmentFileEntryModelImpl.getCompanyId(),
			cpAttachmentFileEntryModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, cpAttachmentFileEntryModelImpl);
	}

	/**
	 * Creates a new cp attachment file entry with the primary key. Does not add the cp attachment file entry to the database.
	 *
	 * @param CPAttachmentFileEntryId the primary key for the new cp attachment file entry
	 * @return the new cp attachment file entry
	 */
	@Override
	public CPAttachmentFileEntry create(long CPAttachmentFileEntryId) {
		CPAttachmentFileEntry cpAttachmentFileEntry =
			new CPAttachmentFileEntryImpl();

		cpAttachmentFileEntry.setNew(true);
		cpAttachmentFileEntry.setPrimaryKey(CPAttachmentFileEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpAttachmentFileEntry.setUuid(uuid);

		cpAttachmentFileEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpAttachmentFileEntry;
	}

	/**
	 * Removes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry remove(long CPAttachmentFileEntryId)
		throws NoSuchCPAttachmentFileEntryException {

		return remove((Serializable)CPAttachmentFileEntryId);
	}

	/**
	 * Removes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry remove(Serializable primaryKey)
		throws NoSuchCPAttachmentFileEntryException {

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry cpAttachmentFileEntry =
				(CPAttachmentFileEntry)session.get(
					CPAttachmentFileEntryImpl.class, primaryKey);

			if (cpAttachmentFileEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPAttachmentFileEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpAttachmentFileEntry);
		}
		catch (NoSuchCPAttachmentFileEntryException noSuchEntityException) {
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
	protected CPAttachmentFileEntry removeImpl(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpAttachmentFileEntry)) {
				cpAttachmentFileEntry = (CPAttachmentFileEntry)session.get(
					CPAttachmentFileEntryImpl.class,
					cpAttachmentFileEntry.getPrimaryKeyObj());
			}

			if ((cpAttachmentFileEntry != null) &&
				ctPersistenceHelper.isRemove(cpAttachmentFileEntry)) {

				session.delete(cpAttachmentFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpAttachmentFileEntry != null) {
			clearCache(cpAttachmentFileEntry);
		}

		return cpAttachmentFileEntry;
	}

	@Override
	public CPAttachmentFileEntry updateImpl(
		CPAttachmentFileEntry cpAttachmentFileEntry) {

		boolean isNew = cpAttachmentFileEntry.isNew();

		if (!(cpAttachmentFileEntry instanceof
				CPAttachmentFileEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpAttachmentFileEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpAttachmentFileEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpAttachmentFileEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPAttachmentFileEntry implementation " +
					cpAttachmentFileEntry.getClass());
		}

		CPAttachmentFileEntryModelImpl cpAttachmentFileEntryModelImpl =
			(CPAttachmentFileEntryModelImpl)cpAttachmentFileEntry;

		if (Validator.isNull(cpAttachmentFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpAttachmentFileEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpAttachmentFileEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpAttachmentFileEntry.setCreateDate(date);
			}
			else {
				cpAttachmentFileEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpAttachmentFileEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpAttachmentFileEntry.setModifiedDate(date);
			}
			else {
				cpAttachmentFileEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(cpAttachmentFileEntry)) {
				if (!isNew) {
					session.evict(
						CPAttachmentFileEntryImpl.class,
						cpAttachmentFileEntry.getPrimaryKeyObj());
				}

				session.save(cpAttachmentFileEntry);
			}
			else {
				cpAttachmentFileEntry = (CPAttachmentFileEntry)session.merge(
					cpAttachmentFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpAttachmentFileEntry.getCtCollectionId() != 0) {
			if (isNew) {
				cpAttachmentFileEntry.setNew(false);
			}

			cpAttachmentFileEntry.resetOriginalValues();

			return cpAttachmentFileEntry;
		}

		entityCache.putResult(
			CPAttachmentFileEntryImpl.class, cpAttachmentFileEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(cpAttachmentFileEntryModelImpl);

		if (isNew) {
			cpAttachmentFileEntry.setNew(false);
		}

		cpAttachmentFileEntry.resetOriginalValues();

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp attachment file entry
	 * @return the cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPAttachmentFileEntryException {

		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByPrimaryKey(
			primaryKey);

		if (cpAttachmentFileEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPAttachmentFileEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry with the primary key or throws a <code>NoSuchCPAttachmentFileEntryException</code> if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByPrimaryKey(long CPAttachmentFileEntryId)
		throws NoSuchCPAttachmentFileEntryException {

		return findByPrimaryKey((Serializable)CPAttachmentFileEntryId);
	}

	/**
	 * Returns the cp attachment file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp attachment file entry
	 * @return the cp attachment file entry, or <code>null</code> if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(CPAttachmentFileEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry = null;

		Session session = null;

		try {
			session = openSession();

			cpAttachmentFileEntry = (CPAttachmentFileEntry)session.get(
				CPAttachmentFileEntryImpl.class, primaryKey);

			if (cpAttachmentFileEntry != null) {
				cacheResult(cpAttachmentFileEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry, or <code>null</code> if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByPrimaryKey(
		long CPAttachmentFileEntryId) {

		return fetchByPrimaryKey((Serializable)CPAttachmentFileEntryId);
	}

	@Override
	public Map<Serializable, CPAttachmentFileEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CPAttachmentFileEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPAttachmentFileEntry> map =
			new HashMap<Serializable, CPAttachmentFileEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPAttachmentFileEntry cpAttachmentFileEntry = fetchByPrimaryKey(
				primaryKey);

			if (cpAttachmentFileEntry != null) {
				map.put(primaryKey, cpAttachmentFileEntry);
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

			for (CPAttachmentFileEntry cpAttachmentFileEntry :
					(List<CPAttachmentFileEntry>)query.list()) {

				map.put(
					cpAttachmentFileEntry.getPrimaryKeyObj(),
					cpAttachmentFileEntry);

				cacheResult(cpAttachmentFileEntry);
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
	 * Returns all the cp attachment file entries.
	 *
	 * @return the cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll(
		int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPAttachmentFileEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll(
		int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

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

		List<CPAttachmentFileEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPATTACHMENTFILEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPATTACHMENTFILEENTRY;

				sql = sql.concat(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPAttachmentFileEntry>)QueryUtil.list(
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
	 * Removes all the cp attachment file entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findAll()) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries.
	 *
	 * @return the number of cp attachment file entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CPAttachmentFileEntry.class);

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
					_SQL_COUNT_CPATTACHMENTFILEENTRY);

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
		return "CPAttachmentFileEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPATTACHMENTFILEENTRY;
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
		return CPAttachmentFileEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPAttachmentFileEntry";
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
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("fileEntryId");
		ctStrictColumnNames.add("cdnEnabled");
		ctStrictColumnNames.add("cdnURL");
		ctStrictColumnNames.add("displayDate");
		ctStrictColumnNames.add("expirationDate");
		ctStrictColumnNames.add("title");
		ctStrictColumnNames.add("json");
		ctStrictColumnNames.add("priority");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("statusByUserId");
		ctStrictColumnNames.add("statusByUserName");
		ctStrictColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPAttachmentFileEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the cp attachment file entry persistence.
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

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_finderPathWithPaginationFindByC_C_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK", "fileEntryId"}, true);

		_finderPathWithoutPaginationFindByC_C_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "fileEntryId"}, true);

		_finderPathCountByC_C_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "fileEntryId"}, false);

		_finderPathWithPaginationFindByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK", "cdnURL"}, true);

		_finderPathWithoutPaginationFindByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"classNameId", "classPK", "cdnURL"}, true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"classNameId", "classPK", "cdnURL"}, false);

		_finderPathWithPaginationFindByC_C_LtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK", "displayDate", "status"},
			true);

		_finderPathWithPaginationCountByC_C_LtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Date.class.getName(), Integer.class.getName()
			},
			new String[] {"classNameId", "classPK", "displayDate", "status"},
			false);

		_finderPathWithPaginationFindByC_C_T_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_", "status"}, true);

		_finderPathWithoutPaginationFindByC_C_T_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_", "status"}, true);

		_finderPathCountByC_C_T_ST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_", "status"}, false);

		_finderPathWithPaginationFindByC_C_T_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T_NotST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_", "status"}, true);

		_finderPathWithPaginationCountByC_C_T_NotST = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_T_NotST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			new String[] {"classNameId", "classPK", "type_", "status"}, false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);

		_setCPAttachmentFileEntryUtilPersistence(this);
	}

	public void destroy() {
		_setCPAttachmentFileEntryUtilPersistence(null);

		entityCache.removeCache(CPAttachmentFileEntryImpl.class.getName());
	}

	private void _setCPAttachmentFileEntryUtilPersistence(
		CPAttachmentFileEntryPersistence cpAttachmentFileEntryPersistence) {

		try {
			Field field = CPAttachmentFileEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, cpAttachmentFileEntryPersistence);
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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_CPATTACHMENTFILEENTRY =
		"SELECT cpAttachmentFileEntry FROM CPAttachmentFileEntry cpAttachmentFileEntry";

	private static final String _SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE =
		"SELECT cpAttachmentFileEntry FROM CPAttachmentFileEntry cpAttachmentFileEntry WHERE ";

	private static final String _SQL_COUNT_CPATTACHMENTFILEENTRY =
		"SELECT COUNT(cpAttachmentFileEntry) FROM CPAttachmentFileEntry cpAttachmentFileEntry";

	private static final String _SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE =
		"SELECT COUNT(cpAttachmentFileEntry) FROM CPAttachmentFileEntry cpAttachmentFileEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpAttachmentFileEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPAttachmentFileEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPAttachmentFileEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPAttachmentFileEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}