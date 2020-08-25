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

import com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.model.CPFriendlyURLEntryTable;
import com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl;
import com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl;
import com.liferay.commerce.product.service.persistence.CPFriendlyURLEntryPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the cp friendly url entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl}
 * @generated
 */
@Deprecated
public class CPFriendlyURLEntryPersistenceImpl
	extends BasePersistenceImpl<CPFriendlyURLEntry>
	implements CPFriendlyURLEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPFriendlyURLEntryUtil</code> to access the cp friendly url entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPFriendlyURLEntryImpl.class.getName();

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
	 * Returns all the cp friendly url entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		List<CPFriendlyURLEntry> list = null;

		if (useFinderCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if (!uuid.equals(cpFriendlyURLEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
				sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPFriendlyURLEntry>)QueryUtil.list(
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
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_First(
			String uuid,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_First(
		String uuid, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		List<CPFriendlyURLEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_Last(
		String uuid, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByUuid_PrevAndNext(
			long CPFriendlyURLEntryId, String uuid,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		uuid = Objects.toString(uuid, "");

		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(
			CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpFriendlyURLEntry, uuid, orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByUuid_PrevAndNext(
				session, cpFriendlyURLEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPFriendlyURLEntry getByUuid_PrevAndNext(
		Session session, CPFriendlyURLEntry cpFriendlyURLEntry, String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
			sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
						cpFriendlyURLEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPFriendlyURLEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

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
		"cpFriendlyURLEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpFriendlyURLEntry.uuid IS NULL OR cpFriendlyURLEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPFriendlyURLEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUUID_G(uuid, groupId);

		if (cpFriendlyURLEntry == null) {
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

			throw new NoSuchCPFriendlyURLEntryException(sb.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUUID_G(
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

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if (!Objects.equals(uuid, cpFriendlyURLEntry.getUuid()) ||
				(groupId != cpFriendlyURLEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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

				List<CPFriendlyURLEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = findByUUID_G(uuid, groupId);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

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
		"cpFriendlyURLEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpFriendlyURLEntry.uuid IS NULL OR cpFriendlyURLEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpFriendlyURLEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		List<CPFriendlyURLEntry> list = null;

		if (useFinderCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if (!uuid.equals(cpFriendlyURLEntry.getUuid()) ||
						(companyId != cpFriendlyURLEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
				sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CPFriendlyURLEntry>)QueryUtil.list(
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
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		List<CPFriendlyURLEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByUuid_C_PrevAndNext(
			long CPFriendlyURLEntryId, String uuid, long companyId,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		uuid = Objects.toString(uuid, "");

		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(
			CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpFriendlyURLEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpFriendlyURLEntry, uuid, companyId, orderByComparator,
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

	protected CPFriendlyURLEntry getByUuid_C_PrevAndNext(
		Session session, CPFriendlyURLEntry cpFriendlyURLEntry, String uuid,
		long companyId, OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
			sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
						cpFriendlyURLEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPFriendlyURLEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

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
		"cpFriendlyURLEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpFriendlyURLEntry.uuid IS NULL OR cpFriendlyURLEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpFriendlyURLEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the cp friendly url entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CPFriendlyURLEntry> list = null;

		if (useFinderCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if ((classNameId != cpFriendlyURLEntry.getClassNameId()) ||
						(classPK != cpFriendlyURLEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CPFriendlyURLEntry>)QueryUtil.list(
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
	 * Returns the first cp friendly url entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		List<CPFriendlyURLEntry> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByC_C_PrevAndNext(
			long CPFriendlyURLEntryId, long classNameId, long classPK,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(
			CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, cpFriendlyURLEntry, classNameId, classPK,
				orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByC_C_PrevAndNext(
				session, cpFriendlyURLEntry, classNameId, classPK,
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

	protected CPFriendlyURLEntry getByC_C_PrevAndNext(
		Session session, CPFriendlyURLEntry cpFriendlyURLEntry,
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
			sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
						cpFriendlyURLEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPFriendlyURLEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"cpFriendlyURLEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"cpFriendlyURLEntry.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C;
				finderArgs = new Object[] {groupId, classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CPFriendlyURLEntry> list = null;

		if (useFinderCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
						(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
						(classPK != cpFriendlyURLEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CPFriendlyURLEntry>)QueryUtil.list(
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
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		List<CPFriendlyURLEntry> list = findByG_C_C(
			groupId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByG_C_C(
			groupId, classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByG_C_C_PrevAndNext(
			long CPFriendlyURLEntryId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(
			CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByG_C_C_PrevAndNext(
				session, cpFriendlyURLEntry, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByG_C_C_PrevAndNext(
				session, cpFriendlyURLEntry, groupId, classNameId, classPK,
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

	protected CPFriendlyURLEntry getByG_C_C_PrevAndNext(
		Session session, CPFriendlyURLEntry cpFriendlyURLEntry, long groupId,
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpFriendlyURLEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPFriendlyURLEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry :
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByG_C_C;

		Object[] finderArgs = new Object[] {groupId, classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"cpFriendlyURLEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"cpFriendlyURLEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"cpFriendlyURLEntry.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_U;
	private FinderPath _finderPathWithoutPaginationFindByG_C_U;
	private FinderPath _finderPathCountByG_C_U;

	/**
	 * Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(
		long groupId, long classNameId, String urlTitle) {

		return findByG_C_U(
			groupId, classNameId, urlTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(
		long groupId, long classNameId, String urlTitle, int start, int end) {

		return findByG_C_U(groupId, classNameId, urlTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(
		long groupId, long classNameId, String urlTitle, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		return findByG_C_U(
			groupId, classNameId, urlTitle, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(
		long groupId, long classNameId, String urlTitle, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean useFinderCache) {

		urlTitle = Objects.toString(urlTitle, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_U;
				finderArgs = new Object[] {groupId, classNameId, urlTitle};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_U;
			finderArgs = new Object[] {
				groupId, classNameId, urlTitle, start, end, orderByComparator
			};
		}

		List<CPFriendlyURLEntry> list = null;

		if (useFinderCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
						(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
						!urlTitle.equals(cpFriendlyURLEntry.getUrlTitle())) {

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

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
				}

				list = (List<CPFriendlyURLEntry>)QueryUtil.list(
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
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_U_First(
			long groupId, long classNameId, String urlTitle,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_U_First(
			groupId, classNameId, urlTitle, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", urlTitle=");
		sb.append(urlTitle);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_U_First(
		long groupId, long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		List<CPFriendlyURLEntry> list = findByG_C_U(
			groupId, classNameId, urlTitle, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_U_Last(
			long groupId, long classNameId, String urlTitle,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_U_Last(
			groupId, classNameId, urlTitle, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", urlTitle=");
		sb.append(urlTitle);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_U_Last(
		long groupId, long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		int count = countByG_C_U(groupId, classNameId, urlTitle);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByG_C_U(
			groupId, classNameId, urlTitle, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByG_C_U_PrevAndNext(
			long CPFriendlyURLEntryId, long groupId, long classNameId,
			String urlTitle,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		urlTitle = Objects.toString(urlTitle, "");

		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(
			CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByG_C_U_PrevAndNext(
				session, cpFriendlyURLEntry, groupId, classNameId, urlTitle,
				orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByG_C_U_PrevAndNext(
				session, cpFriendlyURLEntry, groupId, classNameId, urlTitle,
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

	protected CPFriendlyURLEntry getByG_C_U_PrevAndNext(
		Session session, CPFriendlyURLEntry cpFriendlyURLEntry, long groupId,
		long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

		boolean bindUrlTitle = false;

		if (urlTitle.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
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
			sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		if (bindUrlTitle) {
			queryPos.add(urlTitle);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpFriendlyURLEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPFriendlyURLEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 */
	@Override
	public void removeByG_C_U(long groupId, long classNameId, String urlTitle) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry :
				findByG_C_U(
					groupId, classNameId, urlTitle, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_U(long groupId, long classNameId, String urlTitle) {
		urlTitle = Objects.toString(urlTitle, "");

		FinderPath finderPath = _finderPathCountByG_C_U;

		Object[] finderArgs = new Object[] {groupId, classNameId, urlTitle};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_C_U_GROUPID_2 =
		"cpFriendlyURLEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_U_CLASSNAMEID_2 =
		"cpFriendlyURLEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_2 =
		"cpFriendlyURLEntry.urlTitle = ?";

	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_3 =
		"(cpFriendlyURLEntry.urlTitle IS NULL OR cpFriendlyURLEntry.urlTitle = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_C_M;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_M;
	private FinderPath _finderPathCountByG_C_C_M;

	/**
	 * Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(
		long groupId, long classNameId, long classPK, boolean main) {

		return findByG_C_C_M(
			groupId, classNameId, classPK, main, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(
		long groupId, long classNameId, long classPK, boolean main, int start,
		int end) {

		return findByG_C_C_M(
			groupId, classNameId, classPK, main, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(
		long groupId, long classNameId, long classPK, boolean main, int start,
		int end, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		return findByG_C_C_M(
			groupId, classNameId, classPK, main, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(
		long groupId, long classNameId, long classPK, boolean main, int start,
		int end, OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_M;
				finderArgs = new Object[] {groupId, classNameId, classPK, main};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_C_M;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, main, start, end,
				orderByComparator
			};
		}

		List<CPFriendlyURLEntry> list = null;

		if (useFinderCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
						(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
						(classPK != cpFriendlyURLEntry.getClassPK()) ||
						(main != cpFriendlyURLEntry.isMain())) {

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

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_M_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_M_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_M_MAIN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(main);

				list = (List<CPFriendlyURLEntry>)QueryUtil.list(
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
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_M_First(
			long groupId, long classNameId, long classPK, boolean main,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_M_First(
			groupId, classNameId, classPK, main, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", main=");
		sb.append(main);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_M_First(
		long groupId, long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		List<CPFriendlyURLEntry> list = findByG_C_C_M(
			groupId, classNameId, classPK, main, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_M_Last(
			long groupId, long classNameId, long classPK, boolean main,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_M_Last(
			groupId, classNameId, classPK, main, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", main=");
		sb.append(main);

		sb.append("}");

		throw new NoSuchCPFriendlyURLEntryException(sb.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_M_Last(
		long groupId, long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		int count = countByG_C_C_M(groupId, classNameId, classPK, main);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByG_C_C_M(
			groupId, classNameId, classPK, main, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByG_C_C_M_PrevAndNext(
			long CPFriendlyURLEntryId, long groupId, long classNameId,
			long classPK, boolean main,
			OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(
			CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByG_C_C_M_PrevAndNext(
				session, cpFriendlyURLEntry, groupId, classNameId, classPK,
				main, orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByG_C_C_M_PrevAndNext(
				session, cpFriendlyURLEntry, groupId, classNameId, classPK,
				main, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPFriendlyURLEntry getByG_C_C_M_PrevAndNext(
		Session session, CPFriendlyURLEntry cpFriendlyURLEntry, long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_M_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_M_CLASSPK_2);

		sb.append(_FINDER_COLUMN_G_C_C_M_MAIN_2);

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
			sb.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(main);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpFriendlyURLEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPFriendlyURLEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 */
	@Override
	public void removeByG_C_C_M(
		long groupId, long classNameId, long classPK, boolean main) {

		for (CPFriendlyURLEntry cpFriendlyURLEntry :
				findByG_C_C_M(
					groupId, classNameId, classPK, main, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C_M(
		long groupId, long classNameId, long classPK, boolean main) {

		FinderPath finderPath = _finderPathCountByG_C_C_M;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, main
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_M_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_M_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_M_MAIN_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(main);

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

	private static final String _FINDER_COLUMN_G_C_C_M_GROUPID_2 =
		"cpFriendlyURLEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2 =
		"cpFriendlyURLEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_M_CLASSPK_2 =
		"cpFriendlyURLEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_M_MAIN_2 =
		"cpFriendlyURLEntry.main = ?";

	private FinderPath _finderPathFetchByG_C_L_U;
	private FinderPath _finderPathCountByG_C_L_U;

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or throws a <code>NoSuchCPFriendlyURLEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_L_U(
			long groupId, long classNameId, String languageId, String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_L_U(
			groupId, classNameId, languageId, urlTitle);

		if (cpFriendlyURLEntry == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append(", urlTitle=");
			sb.append(urlTitle);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPFriendlyURLEntryException(sb.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_L_U(
		long groupId, long classNameId, String languageId, String urlTitle) {

		return fetchByG_C_L_U(groupId, classNameId, languageId, urlTitle, true);
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_L_U(
		long groupId, long classNameId, String languageId, String urlTitle,
		boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");
		urlTitle = Objects.toString(urlTitle, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, classNameId, languageId, urlTitle
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_L_U, finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
				(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
				!Objects.equals(
					languageId, cpFriendlyURLEntry.getLanguageId()) ||
				!Objects.equals(urlTitle, cpFriendlyURLEntry.getUrlTitle())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_L_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_L_U_CLASSNAMEID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
				}

				List<CPFriendlyURLEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_C_L_U, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, classNameId, languageId, urlTitle
								};
							}

							_log.warn(
								"CPFriendlyURLEntryPersistenceImpl.fetchByG_C_L_U(long, long, String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByG_C_L_U(
			long groupId, long classNameId, String languageId, String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = findByG_C_L_U(
			groupId, classNameId, languageId, urlTitle);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_L_U(
		long groupId, long classNameId, String languageId, String urlTitle) {

		languageId = Objects.toString(languageId, "");
		urlTitle = Objects.toString(urlTitle, "");

		FinderPath finderPath = _finderPathCountByG_C_L_U;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, languageId, urlTitle
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_L_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_L_U_CLASSNAMEID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_C_L_U_GROUPID_2 =
		"cpFriendlyURLEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_U_CLASSNAMEID_2 =
		"cpFriendlyURLEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_U_LANGUAGEID_2 =
		"cpFriendlyURLEntry.languageId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_L_U_LANGUAGEID_3 =
		"(cpFriendlyURLEntry.languageId IS NULL OR cpFriendlyURLEntry.languageId = '') AND ";

	private static final String _FINDER_COLUMN_G_C_L_U_URLTITLE_2 =
		"cpFriendlyURLEntry.urlTitle = ?";

	private static final String _FINDER_COLUMN_G_C_L_U_URLTITLE_3 =
		"(cpFriendlyURLEntry.urlTitle IS NULL OR cpFriendlyURLEntry.urlTitle = '')";

	private FinderPath _finderPathFetchByG_C_C_L_U;
	private FinderPath _finderPathCountByG_C_C_L_U;

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or throws a <code>NoSuchCPFriendlyURLEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_L_U(
			long groupId, long classNameId, long classPK, String languageId,
			String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_L_U(
			groupId, classNameId, classPK, languageId, urlTitle);

		if (cpFriendlyURLEntry == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append(", urlTitle=");
			sb.append(urlTitle);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPFriendlyURLEntryException(sb.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_U(
		long groupId, long classNameId, long classPK, String languageId,
		String urlTitle) {

		return fetchByG_C_C_L_U(
			groupId, classNameId, classPK, languageId, urlTitle, true);
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_U(
		long groupId, long classNameId, long classPK, String languageId,
		String urlTitle, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");
		urlTitle = Objects.toString(urlTitle, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId, urlTitle
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_C_L_U, finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
				(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
				(classPK != cpFriendlyURLEntry.getClassPK()) ||
				!Objects.equals(
					languageId, cpFriendlyURLEntry.getLanguageId()) ||
				!Objects.equals(urlTitle, cpFriendlyURLEntry.getUrlTitle())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_L_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_U_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_U_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
				}

				List<CPFriendlyURLEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_C_C_L_U, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, classNameId, classPK, languageId,
									urlTitle
								};
							}

							_log.warn(
								"CPFriendlyURLEntryPersistenceImpl.fetchByG_C_C_L_U(long, long, long, String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByG_C_C_L_U(
			long groupId, long classNameId, long classPK, String languageId,
			String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = findByG_C_C_L_U(
			groupId, classNameId, classPK, languageId, urlTitle);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C_L_U(
		long groupId, long classNameId, long classPK, String languageId,
		String urlTitle) {

		languageId = Objects.toString(languageId, "");
		urlTitle = Objects.toString(urlTitle, "");

		FinderPath finderPath = _finderPathCountByG_C_C_L_U;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, languageId, urlTitle
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_L_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_U_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_U_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				if (bindUrlTitle) {
					queryPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_C_C_L_U_GROUPID_2 =
		"cpFriendlyURLEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_U_CLASSNAMEID_2 =
		"cpFriendlyURLEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_U_CLASSPK_2 =
		"cpFriendlyURLEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_2 =
		"cpFriendlyURLEntry.languageId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_3 =
		"(cpFriendlyURLEntry.languageId IS NULL OR cpFriendlyURLEntry.languageId = '') AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_U_URLTITLE_2 =
		"cpFriendlyURLEntry.urlTitle = ?";

	private static final String _FINDER_COLUMN_G_C_C_L_U_URLTITLE_3 =
		"(cpFriendlyURLEntry.urlTitle IS NULL OR cpFriendlyURLEntry.urlTitle = '')";

	private FinderPath _finderPathFetchByG_C_C_L_M;
	private FinderPath _finderPathCountByG_C_C_L_M;

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or throws a <code>NoSuchCPFriendlyURLEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_L_M(
			long groupId, long classNameId, long classPK, String languageId,
			boolean main)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_L_M(
			groupId, classNameId, classPK, languageId, main);

		if (cpFriendlyURLEntry == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append(", main=");
			sb.append(main);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPFriendlyURLEntryException(sb.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_M(
		long groupId, long classNameId, long classPK, String languageId,
		boolean main) {

		return fetchByG_C_C_L_M(
			groupId, classNameId, classPK, languageId, main, true);
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_M(
		long groupId, long classNameId, long classPK, String languageId,
		boolean main, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId, main
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_C_L_M, finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
				(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
				(classPK != cpFriendlyURLEntry.getClassPK()) ||
				!Objects.equals(
					languageId, cpFriendlyURLEntry.getLanguageId()) ||
				(main != cpFriendlyURLEntry.isMain())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_L_M_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_M_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_M_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_L_M_MAIN_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				queryPos.add(main);

				List<CPFriendlyURLEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_C_C_L_M, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									groupId, classNameId, classPK, languageId,
									main
								};
							}

							_log.warn(
								"CPFriendlyURLEntryPersistenceImpl.fetchByG_C_C_L_M(long, long, long, String, boolean, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByG_C_C_L_M(
			long groupId, long classNameId, long classPK, String languageId,
			boolean main)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = findByG_C_C_L_M(
			groupId, classNameId, classPK, languageId, main);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C_L_M(
		long groupId, long classNameId, long classPK, String languageId,
		boolean main) {

		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByG_C_C_L_M;

		Object[] finderArgs = new Object[] {
			groupId, classNameId, classPK, languageId, main
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_L_M_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_M_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_L_M_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_L_M_MAIN_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				queryPos.add(main);

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

	private static final String _FINDER_COLUMN_G_C_C_L_M_GROUPID_2 =
		"cpFriendlyURLEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_M_CLASSNAMEID_2 =
		"cpFriendlyURLEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_M_CLASSPK_2 =
		"cpFriendlyURLEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_2 =
		"cpFriendlyURLEntry.languageId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_3 =
		"(cpFriendlyURLEntry.languageId IS NULL OR cpFriendlyURLEntry.languageId = '') AND ";

	private static final String _FINDER_COLUMN_G_C_C_L_M_MAIN_2 =
		"cpFriendlyURLEntry.main = ?";

	public CPFriendlyURLEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPFriendlyURLEntry.class);

		setModelImplClass(CPFriendlyURLEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CPFriendlyURLEntryTable.INSTANCE);
	}

	/**
	 * Caches the cp friendly url entry in the entity cache if it is enabled.
	 *
	 * @param cpFriendlyURLEntry the cp friendly url entry
	 */
	@Override
	public void cacheResult(CPFriendlyURLEntry cpFriendlyURLEntry) {
		entityCache.putResult(
			CPFriendlyURLEntryImpl.class, cpFriendlyURLEntry.getPrimaryKey(),
			cpFriendlyURLEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				cpFriendlyURLEntry.getUuid(), cpFriendlyURLEntry.getGroupId()
			},
			cpFriendlyURLEntry);

		finderCache.putResult(
			_finderPathFetchByG_C_L_U,
			new Object[] {
				cpFriendlyURLEntry.getGroupId(),
				cpFriendlyURLEntry.getClassNameId(),
				cpFriendlyURLEntry.getLanguageId(),
				cpFriendlyURLEntry.getUrlTitle()
			},
			cpFriendlyURLEntry);

		finderCache.putResult(
			_finderPathFetchByG_C_C_L_U,
			new Object[] {
				cpFriendlyURLEntry.getGroupId(),
				cpFriendlyURLEntry.getClassNameId(),
				cpFriendlyURLEntry.getClassPK(),
				cpFriendlyURLEntry.getLanguageId(),
				cpFriendlyURLEntry.getUrlTitle()
			},
			cpFriendlyURLEntry);

		finderCache.putResult(
			_finderPathFetchByG_C_C_L_M,
			new Object[] {
				cpFriendlyURLEntry.getGroupId(),
				cpFriendlyURLEntry.getClassNameId(),
				cpFriendlyURLEntry.getClassPK(),
				cpFriendlyURLEntry.getLanguageId(), cpFriendlyURLEntry.isMain()
			},
			cpFriendlyURLEntry);
	}

	/**
	 * Caches the cp friendly url entries in the entity cache if it is enabled.
	 *
	 * @param cpFriendlyURLEntries the cp friendly url entries
	 */
	@Override
	public void cacheResult(List<CPFriendlyURLEntry> cpFriendlyURLEntries) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : cpFriendlyURLEntries) {
			if (entityCache.getResult(
					CPFriendlyURLEntryImpl.class,
					cpFriendlyURLEntry.getPrimaryKey()) == null) {

				cacheResult(cpFriendlyURLEntry);
			}
		}
	}

	/**
	 * Clears the cache for all cp friendly url entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPFriendlyURLEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp friendly url entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPFriendlyURLEntry cpFriendlyURLEntry) {
		entityCache.removeResult(
			CPFriendlyURLEntryImpl.class, cpFriendlyURLEntry);
	}

	@Override
	public void clearCache(List<CPFriendlyURLEntry> cpFriendlyURLEntries) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : cpFriendlyURLEntries) {
			entityCache.removeResult(
				CPFriendlyURLEntryImpl.class, cpFriendlyURLEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CPFriendlyURLEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPFriendlyURLEntryModelImpl cpFriendlyURLEntryModelImpl) {

		Object[] args = new Object[] {
			cpFriendlyURLEntryModelImpl.getUuid(),
			cpFriendlyURLEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, cpFriendlyURLEntryModelImpl, false);

		args = new Object[] {
			cpFriendlyURLEntryModelImpl.getGroupId(),
			cpFriendlyURLEntryModelImpl.getClassNameId(),
			cpFriendlyURLEntryModelImpl.getLanguageId(),
			cpFriendlyURLEntryModelImpl.getUrlTitle()
		};

		finderCache.putResult(
			_finderPathCountByG_C_L_U, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_C_L_U, args, cpFriendlyURLEntryModelImpl,
			false);

		args = new Object[] {
			cpFriendlyURLEntryModelImpl.getGroupId(),
			cpFriendlyURLEntryModelImpl.getClassNameId(),
			cpFriendlyURLEntryModelImpl.getClassPK(),
			cpFriendlyURLEntryModelImpl.getLanguageId(),
			cpFriendlyURLEntryModelImpl.getUrlTitle()
		};

		finderCache.putResult(
			_finderPathCountByG_C_C_L_U, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_C_C_L_U, args, cpFriendlyURLEntryModelImpl,
			false);

		args = new Object[] {
			cpFriendlyURLEntryModelImpl.getGroupId(),
			cpFriendlyURLEntryModelImpl.getClassNameId(),
			cpFriendlyURLEntryModelImpl.getClassPK(),
			cpFriendlyURLEntryModelImpl.getLanguageId(),
			cpFriendlyURLEntryModelImpl.isMain()
		};

		finderCache.putResult(
			_finderPathCountByG_C_C_L_M, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_C_C_L_M, args, cpFriendlyURLEntryModelImpl,
			false);
	}

	/**
	 * Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	 *
	 * @param CPFriendlyURLEntryId the primary key for the new cp friendly url entry
	 * @return the new cp friendly url entry
	 */
	@Override
	public CPFriendlyURLEntry create(long CPFriendlyURLEntryId) {
		CPFriendlyURLEntry cpFriendlyURLEntry = new CPFriendlyURLEntryImpl();

		cpFriendlyURLEntry.setNew(true);
		cpFriendlyURLEntry.setPrimaryKey(CPFriendlyURLEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpFriendlyURLEntry.setUuid(uuid);

		cpFriendlyURLEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpFriendlyURLEntry;
	}

	/**
	 * Removes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry that was removed
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry remove(long CPFriendlyURLEntryId)
		throws NoSuchCPFriendlyURLEntryException {

		return remove((Serializable)CPFriendlyURLEntryId);
	}

	/**
	 * Removes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp friendly url entry
	 * @return the cp friendly url entry that was removed
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry remove(Serializable primaryKey)
		throws NoSuchCPFriendlyURLEntryException {

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry cpFriendlyURLEntry =
				(CPFriendlyURLEntry)session.get(
					CPFriendlyURLEntryImpl.class, primaryKey);

			if (cpFriendlyURLEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPFriendlyURLEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpFriendlyURLEntry);
		}
		catch (NoSuchCPFriendlyURLEntryException noSuchEntityException) {
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
	protected CPFriendlyURLEntry removeImpl(
		CPFriendlyURLEntry cpFriendlyURLEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpFriendlyURLEntry)) {
				cpFriendlyURLEntry = (CPFriendlyURLEntry)session.get(
					CPFriendlyURLEntryImpl.class,
					cpFriendlyURLEntry.getPrimaryKeyObj());
			}

			if (cpFriendlyURLEntry != null) {
				session.delete(cpFriendlyURLEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpFriendlyURLEntry != null) {
			clearCache(cpFriendlyURLEntry);
		}

		return cpFriendlyURLEntry;
	}

	@Override
	public CPFriendlyURLEntry updateImpl(
		CPFriendlyURLEntry cpFriendlyURLEntry) {

		boolean isNew = cpFriendlyURLEntry.isNew();

		if (!(cpFriendlyURLEntry instanceof CPFriendlyURLEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpFriendlyURLEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpFriendlyURLEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpFriendlyURLEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPFriendlyURLEntry implementation " +
					cpFriendlyURLEntry.getClass());
		}

		CPFriendlyURLEntryModelImpl cpFriendlyURLEntryModelImpl =
			(CPFriendlyURLEntryModelImpl)cpFriendlyURLEntry;

		if (Validator.isNull(cpFriendlyURLEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpFriendlyURLEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpFriendlyURLEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpFriendlyURLEntry.setCreateDate(now);
			}
			else {
				cpFriendlyURLEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!cpFriendlyURLEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpFriendlyURLEntry.setModifiedDate(now);
			}
			else {
				cpFriendlyURLEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpFriendlyURLEntry);
			}
			else {
				cpFriendlyURLEntry = (CPFriendlyURLEntry)session.merge(
					cpFriendlyURLEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPFriendlyURLEntryImpl.class, cpFriendlyURLEntryModelImpl, false,
			true);

		cacheUniqueFindersCache(cpFriendlyURLEntryModelImpl);

		if (isNew) {
			cpFriendlyURLEntry.setNew(false);
		}

		cpFriendlyURLEntry.resetOriginalValues();

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp friendly url entry
	 * @return the cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPFriendlyURLEntryException {

		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByPrimaryKey(primaryKey);

		if (cpFriendlyURLEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPFriendlyURLEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry with the primary key or throws a <code>NoSuchCPFriendlyURLEntryException</code> if it could not be found.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByPrimaryKey(long CPFriendlyURLEntryId)
		throws NoSuchCPFriendlyURLEntryException {

		return findByPrimaryKey((Serializable)CPFriendlyURLEntryId);
	}

	/**
	 * Returns the cp friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry, or <code>null</code> if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByPrimaryKey(long CPFriendlyURLEntryId) {
		return fetchByPrimaryKey((Serializable)CPFriendlyURLEntryId);
	}

	/**
	 * Returns all the cp friendly url entries.
	 *
	 * @return the cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll(
		int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPFriendlyURLEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll(
		int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		List<CPFriendlyURLEntry> list = null;

		if (useFinderCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPFRIENDLYURLENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPFRIENDLYURLENTRY;

				sql = sql.concat(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPFriendlyURLEntry>)QueryUtil.list(
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
	 * Removes all the cp friendly url entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : findAll()) {
			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries.
	 *
	 * @return the number of cp friendly url entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_CPFRIENDLYURLENTRY);

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
		return "CPFriendlyURLEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPFRIENDLYURLENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPFriendlyURLEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp friendly url entry persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CPFriendlyURLEntryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CPFriendlyURLEntryModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", CPFriendlyURLEntry.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByC_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByG_C_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByG_C_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, true);

		_finderPathCountByG_C_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK"}, false);

		_finderPathWithPaginationFindByG_C_U = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "urlTitle"}, true);

		_finderPathWithoutPaginationFindByG_C_U = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "urlTitle"}, true);

		_finderPathCountByG_C_U = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "classNameId", "urlTitle"}, false);

		_finderPathWithPaginationFindByG_C_C_M = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "main"}, true);

		_finderPathWithoutPaginationFindByG_C_C_M = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "main"}, true);

		_finderPathCountByG_C_C_M = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			new String[] {"groupId", "classNameId", "classPK", "main"}, false);

		_finderPathFetchByG_C_L_U = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"groupId", "classNameId", "languageId", "urlTitle"},
			true);

		_finderPathCountByG_C_L_U = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"groupId", "classNameId", "languageId", "urlTitle"},
			false);

		_finderPathFetchByG_C_C_L_U = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "languageId", "urlTitle"
			},
			true);

		_finderPathCountByG_C_C_L_U = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "languageId", "urlTitle"
			},
			false);

		_finderPathFetchByG_C_C_L_M = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C_L_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "languageId", "main"
			},
			true);

		_finderPathCountByG_C_C_L_M = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_L_M",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {
				"groupId", "classNameId", "classPK", "languageId", "main"
			},
			false);
	}

	public void destroy() {
		entityCache.removeCache(CPFriendlyURLEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY =
		"SELECT cpFriendlyURLEntry FROM CPFriendlyURLEntry cpFriendlyURLEntry";

	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY_WHERE =
		"SELECT cpFriendlyURLEntry FROM CPFriendlyURLEntry cpFriendlyURLEntry WHERE ";

	private static final String _SQL_COUNT_CPFRIENDLYURLENTRY =
		"SELECT COUNT(cpFriendlyURLEntry) FROM CPFriendlyURLEntry cpFriendlyURLEntry";

	private static final String _SQL_COUNT_CPFRIENDLYURLENTRY_WHERE =
		"SELECT COUNT(cpFriendlyURLEntry) FROM CPFriendlyURLEntry cpFriendlyURLEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cpFriendlyURLEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPFriendlyURLEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPFriendlyURLEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPFriendlyURLEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class CPFriendlyURLEntryModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CPFriendlyURLEntryModelImpl cpFriendlyURLEntryModelImpl =
				(CPFriendlyURLEntryModelImpl)baseModel;

			long columnBitmask = cpFriendlyURLEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					cpFriendlyURLEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						cpFriendlyURLEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					cpFriendlyURLEntryModelImpl, columnNames, original);
			}

			return null;
		}

		private Object[] _getValue(
			CPFriendlyURLEntryModelImpl cpFriendlyURLEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						cpFriendlyURLEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = cpFriendlyURLEntryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}