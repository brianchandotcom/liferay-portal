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

import com.liferay.commerce.exception.NoSuchSubscriptionEntryException;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.model.CommerceSubscriptionEntryTable;
import com.liferay.commerce.model.impl.CommerceSubscriptionEntryImpl;
import com.liferay.commerce.model.impl.CommerceSubscriptionEntryModelImpl;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryPersistence;
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
 * The persistence implementation for the commerce subscription entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceSubscriptionEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceSubscriptionEntry>
	implements CommerceSubscriptionEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceSubscriptionEntryUtil</code> to access the commerce subscription entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceSubscriptionEntryImpl.class.getName();

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
	 * Returns all the commerce subscription entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceSubscriptionEntry commerceSubscriptionEntry :
						list) {

					if (!uuid.equals(commerceSubscriptionEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
				sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceSubscriptionEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce subscription entries before and after the current commerce subscription entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the current commerce subscription entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry[] findByUuid_PrevAndNext(
			long commerceSubscriptionEntryId, String uuid,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByPrimaryKey(
			commerceSubscriptionEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry[] array =
				new CommerceSubscriptionEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceSubscriptionEntry, uuid, orderByComparator,
				true);

			array[1] = commerceSubscriptionEntry;

			array[2] = getByUuid_PrevAndNext(
				session, commerceSubscriptionEntry, uuid, orderByComparator,
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

	protected CommerceSubscriptionEntry getByUuid_PrevAndNext(
		Session session, CommerceSubscriptionEntry commerceSubscriptionEntry,
		String uuid,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
			sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
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
						commerceSubscriptionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceSubscriptionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce subscription entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
		"commerceSubscriptionEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceSubscriptionEntry.uuid IS NULL OR commerceSubscriptionEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the commerce subscription entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByUUID_G(
			uuid, groupId);

		if (commerceSubscriptionEntry == null) {
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

			throw new NoSuchSubscriptionEntryException(sb.toString());
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce subscription entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUUID_G(
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

		if (result instanceof CommerceSubscriptionEntry) {
			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)result;

			if (!Objects.equals(uuid, commerceSubscriptionEntry.getUuid()) ||
				(groupId != commerceSubscriptionEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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

				List<CommerceSubscriptionEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CommerceSubscriptionEntry commerceSubscriptionEntry =
						list.get(0);

					result = commerceSubscriptionEntry;

					cacheResult(commerceSubscriptionEntry);
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
			return (CommerceSubscriptionEntry)result;
		}
	}

	/**
	 * Removes the commerce subscription entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce subscription entry that was removed
	 */
	@Override
	public CommerceSubscriptionEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByUUID_G(
			uuid, groupId);

		return remove(commerceSubscriptionEntry);
	}

	/**
	 * Returns the number of commerce subscription entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
		"commerceSubscriptionEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(commerceSubscriptionEntry.uuid IS NULL OR commerceSubscriptionEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"commerceSubscriptionEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceSubscriptionEntry commerceSubscriptionEntry :
						list) {

					if (!uuid.equals(commerceSubscriptionEntry.getUuid()) ||
						(companyId !=
							commerceSubscriptionEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
				sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceSubscriptionEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce subscription entries before and after the current commerce subscription entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the current commerce subscription entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry[] findByUuid_C_PrevAndNext(
			long commerceSubscriptionEntryId, String uuid, long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByPrimaryKey(
			commerceSubscriptionEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry[] array =
				new CommerceSubscriptionEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceSubscriptionEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceSubscriptionEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceSubscriptionEntry, uuid, companyId,
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

	protected CommerceSubscriptionEntry getByUuid_C_PrevAndNext(
		Session session, CommerceSubscriptionEntry commerceSubscriptionEntry,
		String uuid, long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
			sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
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
						commerceSubscriptionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceSubscriptionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce subscription entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
		"commerceSubscriptionEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceSubscriptionEntry.uuid IS NULL OR commerceSubscriptionEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceSubscriptionEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the commerce subscription entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceSubscriptionEntry commerceSubscriptionEntry :
						list) {

					if (groupId != commerceSubscriptionEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByGroupId_First(groupId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByGroupId_Last(groupId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceSubscriptionEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce subscription entries before and after the current commerce subscription entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the current commerce subscription entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry[] findByGroupId_PrevAndNext(
			long commerceSubscriptionEntryId, long groupId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByPrimaryKey(
			commerceSubscriptionEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry[] array =
				new CommerceSubscriptionEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, commerceSubscriptionEntry, groupId, orderByComparator,
				true);

			array[1] = commerceSubscriptionEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, commerceSubscriptionEntry, groupId, orderByComparator,
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

	protected CommerceSubscriptionEntry getByGroupId_PrevAndNext(
		Session session, CommerceSubscriptionEntry commerceSubscriptionEntry,
		long groupId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
			sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
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
						commerceSubscriptionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceSubscriptionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce subscription entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"commerceSubscriptionEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the commerce subscription entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceSubscriptionEntry commerceSubscriptionEntry :
						list) {

					if (companyId != commerceSubscriptionEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceSubscriptionEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce subscription entries before and after the current commerce subscription entry in the ordered set where companyId = &#63;.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the current commerce subscription entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry[] findByCompanyId_PrevAndNext(
			long commerceSubscriptionEntryId, long companyId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByPrimaryKey(
			commerceSubscriptionEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry[] array =
				new CommerceSubscriptionEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceSubscriptionEntry, companyId,
				orderByComparator, true);

			array[1] = commerceSubscriptionEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceSubscriptionEntry, companyId,
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

	protected CommerceSubscriptionEntry getByCompanyId_PrevAndNext(
		Session session, CommerceSubscriptionEntry commerceSubscriptionEntry,
		long companyId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
			sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
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
						commerceSubscriptionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceSubscriptionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce subscription entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

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
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"commerceSubscriptionEntry.companyId = ?";

	private FinderPath _finderPathFetchByCommerceOrderItemId;
	private FinderPath _finderPathCountByCommerceOrderItemId;

	/**
	 * Returns the commerce subscription entry where commerceOrderItemId = &#63; or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByCommerceOrderItemId(commerceOrderItemId);

		if (commerceSubscriptionEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceOrderItemId=");
			sb.append(commerceOrderItemId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSubscriptionEntryException(sb.toString());
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByCommerceOrderItemId(
		long commerceOrderItemId) {

		return fetchByCommerceOrderItemId(commerceOrderItemId, true);
	}

	/**
	 * Returns the commerce subscription entry where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByCommerceOrderItemId(
		long commerceOrderItemId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceOrderItemId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCommerceOrderItemId, finderArgs);
		}

		if (result instanceof CommerceSubscriptionEntry) {
			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)result;

			if (commerceOrderItemId !=
					commerceSubscriptionEntry.getCommerceOrderItemId()) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERITEMID_COMMERCEORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderItemId);

				List<CommerceSubscriptionEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCommerceOrderItemId, finderArgs,
							list);
					}
				}
				else {
					CommerceSubscriptionEntry commerceSubscriptionEntry =
						list.get(0);

					result = commerceSubscriptionEntry;

					cacheResult(commerceSubscriptionEntry);
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
			return (CommerceSubscriptionEntry)result;
		}
	}

	/**
	 * Removes the commerce subscription entry where commerceOrderItemId = &#63; from the database.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the commerce subscription entry that was removed
	 */
	@Override
	public CommerceSubscriptionEntry removeByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			findByCommerceOrderItemId(commerceOrderItemId);

		return remove(commerceSubscriptionEntry);
	}

	/**
	 * Returns the number of commerce subscription entries where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByCommerceOrderItemId(long commerceOrderItemId) {
		FinderPath finderPath = _finderPathCountByCommerceOrderItemId;

		Object[] finderArgs = new Object[] {commerceOrderItemId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERITEMID_COMMERCEORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderItemId);

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

	private static final String
		_FINDER_COLUMN_COMMERCEORDERITEMID_COMMERCEORDERITEMID_2 =
			"commerceSubscriptionEntry.commerceOrderItemId = ?";

	private FinderPath _finderPathWithPaginationFindBySubscriptionStatus;
	private FinderPath _finderPathWithoutPaginationFindBySubscriptionStatus;
	private FinderPath _finderPathCountBySubscriptionStatus;

	/**
	 * Returns all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus) {

		return findBySubscriptionStatus(
			subscriptionStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param subscriptionStatus the subscription status
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus, int start, int end) {

		return findBySubscriptionStatus(subscriptionStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param subscriptionStatus the subscription status
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findBySubscriptionStatus(
			subscriptionStatus, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param subscriptionStatus the subscription status
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findBySubscriptionStatus(
		int subscriptionStatus, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindBySubscriptionStatus;
				finderArgs = new Object[] {subscriptionStatus};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySubscriptionStatus;
			finderArgs = new Object[] {
				subscriptionStatus, start, end, orderByComparator
			};
		}

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceSubscriptionEntry commerceSubscriptionEntry :
						list) {

					if (subscriptionStatus !=
							commerceSubscriptionEntry.getSubscriptionStatus()) {

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

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_SUBSCRIPTIONSTATUS_SUBSCRIPTIONSTATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(subscriptionStatus);

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Returns the first commerce subscription entry in the ordered set where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findBySubscriptionStatus_First(
			int subscriptionStatus,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchBySubscriptionStatus_First(
				subscriptionStatus, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("subscriptionStatus=");
		sb.append(subscriptionStatus);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchBySubscriptionStatus_First(
		int subscriptionStatus,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findBySubscriptionStatus(
			subscriptionStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findBySubscriptionStatus_Last(
			int subscriptionStatus,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchBySubscriptionStatus_Last(
				subscriptionStatus, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("subscriptionStatus=");
		sb.append(subscriptionStatus);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchBySubscriptionStatus_Last(
		int subscriptionStatus,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		int count = countBySubscriptionStatus(subscriptionStatus);

		if (count == 0) {
			return null;
		}

		List<CommerceSubscriptionEntry> list = findBySubscriptionStatus(
			subscriptionStatus, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce subscription entries before and after the current commerce subscription entry in the ordered set where subscriptionStatus = &#63;.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the current commerce subscription entry
	 * @param subscriptionStatus the subscription status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry[] findBySubscriptionStatus_PrevAndNext(
			long commerceSubscriptionEntryId, int subscriptionStatus,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByPrimaryKey(
			commerceSubscriptionEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry[] array =
				new CommerceSubscriptionEntryImpl[3];

			array[0] = getBySubscriptionStatus_PrevAndNext(
				session, commerceSubscriptionEntry, subscriptionStatus,
				orderByComparator, true);

			array[1] = commerceSubscriptionEntry;

			array[2] = getBySubscriptionStatus_PrevAndNext(
				session, commerceSubscriptionEntry, subscriptionStatus,
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

	protected CommerceSubscriptionEntry getBySubscriptionStatus_PrevAndNext(
		Session session, CommerceSubscriptionEntry commerceSubscriptionEntry,
		int subscriptionStatus,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

		sb.append(_FINDER_COLUMN_SUBSCRIPTIONSTATUS_SUBSCRIPTIONSTATUS_2);

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
			sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(subscriptionStatus);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceSubscriptionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceSubscriptionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce subscription entries where subscriptionStatus = &#63; from the database.
	 *
	 * @param subscriptionStatus the subscription status
	 */
	@Override
	public void removeBySubscriptionStatus(int subscriptionStatus) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findBySubscriptionStatus(
					subscriptionStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where subscriptionStatus = &#63;.
	 *
	 * @param subscriptionStatus the subscription status
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countBySubscriptionStatus(int subscriptionStatus) {
		FinderPath finderPath = _finderPathCountBySubscriptionStatus;

		Object[] finderArgs = new Object[] {subscriptionStatus};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_SUBSCRIPTIONSTATUS_SUBSCRIPTIONSTATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(subscriptionStatus);

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

	private static final String
		_FINDER_COLUMN_SUBSCRIPTIONSTATUS_SUBSCRIPTIONSTATUS_2 =
			"commerceSubscriptionEntry.subscriptionStatus = ?";

	private FinderPath _finderPathWithPaginationFindByC_U;
	private FinderPath _finderPathWithoutPaginationFindByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId) {

		return findByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_U;
				finderArgs = new Object[] {companyId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_U;
			finderArgs = new Object[] {
				companyId, userId, start, end, orderByComparator
			};
		}

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceSubscriptionEntry commerceSubscriptionEntry :
						list) {

					if ((companyId !=
							commerceSubscriptionEntry.getCompanyId()) ||
						(userId != commerceSubscriptionEntry.getUserId())) {

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

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByC_U_First(
			long companyId, long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByC_U_First(
			companyId, userId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByC_U(
			companyId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByC_U_Last(
			long companyId, long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByC_U_Last(
			companyId, userId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByC_U_Last(
		long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		int count = countByC_U(companyId, userId);

		if (count == 0) {
			return null;
		}

		List<CommerceSubscriptionEntry> list = findByC_U(
			companyId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce subscription entries before and after the current commerce subscription entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the current commerce subscription entry
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry[] findByC_U_PrevAndNext(
			long commerceSubscriptionEntryId, long companyId, long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByPrimaryKey(
			commerceSubscriptionEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry[] array =
				new CommerceSubscriptionEntryImpl[3];

			array[0] = getByC_U_PrevAndNext(
				session, commerceSubscriptionEntry, companyId, userId,
				orderByComparator, true);

			array[1] = commerceSubscriptionEntry;

			array[2] = getByC_U_PrevAndNext(
				session, commerceSubscriptionEntry, companyId, userId,
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

	protected CommerceSubscriptionEntry getByC_U_PrevAndNext(
		Session session, CommerceSubscriptionEntry commerceSubscriptionEntry,
		long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

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
			sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceSubscriptionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceSubscriptionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce subscription entries where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByC_U(
					companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"commerceSubscriptionEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 =
		"commerceSubscriptionEntry.userId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_U;
	private FinderPath _finderPathWithoutPaginationFindByG_C_U;
	private FinderPath _finderPathCountByG_C_U;

	/**
	 * Returns all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId) {

		return findByG_C_U(
			groupId, companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId, int start, int end) {

		return findByG_C_U(groupId, companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findByG_C_U(
			groupId, companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findByG_C_U(
		long groupId, long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_U;
				finderArgs = new Object[] {groupId, companyId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_U;
			finderArgs = new Object[] {
				groupId, companyId, userId, start, end, orderByComparator
			};
		}

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceSubscriptionEntry commerceSubscriptionEntry :
						list) {

					if ((groupId != commerceSubscriptionEntry.getGroupId()) ||
						(companyId !=
							commerceSubscriptionEntry.getCompanyId()) ||
						(userId != commerceSubscriptionEntry.getUserId())) {

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

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(userId);

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByG_C_U_First(
			long groupId, long companyId, long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			fetchByG_C_U_First(groupId, companyId, userId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce subscription entry in the ordered set where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByG_C_U_First(
		long groupId, long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		List<CommerceSubscriptionEntry> list = findByG_C_U(
			groupId, companyId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByG_C_U_Last(
			long groupId, long companyId, long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByG_C_U_Last(
			groupId, companyId, userId, orderByComparator);

		if (commerceSubscriptionEntry != null) {
			return commerceSubscriptionEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchSubscriptionEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce subscription entry in the ordered set where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByG_C_U_Last(
		long groupId, long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		int count = countByG_C_U(groupId, companyId, userId);

		if (count == 0) {
			return null;
		}

		List<CommerceSubscriptionEntry> list = findByG_C_U(
			groupId, companyId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce subscription entries before and after the current commerce subscription entry in the ordered set where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the current commerce subscription entry
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry[] findByG_C_U_PrevAndNext(
			long commerceSubscriptionEntryId, long groupId, long companyId,
			long userId,
			OrderByComparator<CommerceSubscriptionEntry> orderByComparator)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByPrimaryKey(
			commerceSubscriptionEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry[] array =
				new CommerceSubscriptionEntryImpl[3];

			array[0] = getByG_C_U_PrevAndNext(
				session, commerceSubscriptionEntry, groupId, companyId, userId,
				orderByComparator, true);

			array[1] = commerceSubscriptionEntry;

			array[2] = getByG_C_U_PrevAndNext(
				session, commerceSubscriptionEntry, groupId, companyId, userId,
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

	protected CommerceSubscriptionEntry getByG_C_U_PrevAndNext(
		Session session, CommerceSubscriptionEntry commerceSubscriptionEntry,
		long groupId, long companyId, long userId,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_U_USERID_2);

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
			sb.append(CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(companyId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceSubscriptionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceSubscriptionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_C_U(long groupId, long companyId, long userId) {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				findByG_C_U(
					groupId, companyId, userId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries where groupId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByG_C_U(long groupId, long companyId, long userId) {
		FinderPath finderPath = _finderPathCountByG_C_U;

		Object[] finderArgs = new Object[] {groupId, companyId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(userId);

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
		"commerceSubscriptionEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_U_COMPANYID_2 =
		"commerceSubscriptionEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_U_USERID_2 =
		"commerceSubscriptionEntry.userId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByC_C_C(
			String CPInstanceUuid, long CProductId, long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByC_C_C(
			CPInstanceUuid, CProductId, commerceOrderItemId);

		if (commerceSubscriptionEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPInstanceUuid=");
			sb.append(CPInstanceUuid);

			sb.append(", CProductId=");
			sb.append(CProductId);

			sb.append(", commerceOrderItemId=");
			sb.append(commerceOrderItemId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchSubscriptionEntryException(sb.toString());
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByC_C_C(
		String CPInstanceUuid, long CProductId, long commerceOrderItemId) {

		return fetchByC_C_C(
			CPInstanceUuid, CProductId, commerceOrderItemId, true);
	}

	/**
	 * Returns the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce subscription entry, or <code>null</code> if a matching commerce subscription entry could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByC_C_C(
		String CPInstanceUuid, long CProductId, long commerceOrderItemId,
		boolean useFinderCache) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				CPInstanceUuid, CProductId, commerceOrderItemId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C_C, finderArgs);
		}

		if (result instanceof CommerceSubscriptionEntry) {
			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)result;

			if (!Objects.equals(
					CPInstanceUuid,
					commerceSubscriptionEntry.getCPInstanceUuid()) ||
				(CProductId != commerceSubscriptionEntry.getCProductId()) ||
				(commerceOrderItemId !=
					commerceSubscriptionEntry.getCommerceOrderItemId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_C_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_C_C_C_CPINSTANCEUUID_2);
			}

			sb.append(_FINDER_COLUMN_C_C_C_CPRODUCTID_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				queryPos.add(CProductId);

				queryPos.add(commerceOrderItemId);

				List<CommerceSubscriptionEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					CommerceSubscriptionEntry commerceSubscriptionEntry =
						list.get(0);

					result = commerceSubscriptionEntry;

					cacheResult(commerceSubscriptionEntry);
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
			return (CommerceSubscriptionEntry)result;
		}
	}

	/**
	 * Removes the commerce subscription entry where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the commerce subscription entry that was removed
	 */
	@Override
	public CommerceSubscriptionEntry removeByC_C_C(
			String CPInstanceUuid, long CProductId, long commerceOrderItemId)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = findByC_C_C(
			CPInstanceUuid, CProductId, commerceOrderItemId);

		return remove(commerceSubscriptionEntry);
	}

	/**
	 * Returns the number of commerce subscription entries where CPInstanceUuid = &#63; and CProductId = &#63; and commerceOrderItemId = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce subscription entries
	 */
	@Override
	public int countByC_C_C(
		String CPInstanceUuid, long CProductId, long commerceOrderItemId) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			CPInstanceUuid, CProductId, commerceOrderItemId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_C_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_C_C_C_CPINSTANCEUUID_2);
			}

			sb.append(_FINDER_COLUMN_C_C_C_CPRODUCTID_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				queryPos.add(CProductId);

				queryPos.add(commerceOrderItemId);

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

	private static final String _FINDER_COLUMN_C_C_C_CPINSTANCEUUID_2 =
		"commerceSubscriptionEntry.CPInstanceUuid = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CPINSTANCEUUID_3 =
		"(commerceSubscriptionEntry.CPInstanceUuid IS NULL OR commerceSubscriptionEntry.CPInstanceUuid = '') AND ";

	private static final String _FINDER_COLUMN_C_C_C_CPRODUCTID_2 =
		"commerceSubscriptionEntry.CProductId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_COMMERCEORDERITEMID_2 =
		"commerceSubscriptionEntry.commerceOrderItemId = ?";

	public CommerceSubscriptionEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"deliverySubscriptionTypeSettings", "deliverySubTypeSettings");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceSubscriptionEntry.class);

		setModelImplClass(CommerceSubscriptionEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceSubscriptionEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce subscription entry in the entity cache if it is enabled.
	 *
	 * @param commerceSubscriptionEntry the commerce subscription entry
	 */
	@Override
	public void cacheResult(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		entityCache.putResult(
			CommerceSubscriptionEntryImpl.class,
			commerceSubscriptionEntry.getPrimaryKey(),
			commerceSubscriptionEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				commerceSubscriptionEntry.getUuid(),
				commerceSubscriptionEntry.getGroupId()
			},
			commerceSubscriptionEntry);

		finderCache.putResult(
			_finderPathFetchByCommerceOrderItemId,
			new Object[] {commerceSubscriptionEntry.getCommerceOrderItemId()},
			commerceSubscriptionEntry);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commerceSubscriptionEntry.getCPInstanceUuid(),
				commerceSubscriptionEntry.getCProductId(),
				commerceSubscriptionEntry.getCommerceOrderItemId()
			},
			commerceSubscriptionEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce subscription entries in the entity cache if it is enabled.
	 *
	 * @param commerceSubscriptionEntries the commerce subscription entries
	 */
	@Override
	public void cacheResult(
		List<CommerceSubscriptionEntry> commerceSubscriptionEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceSubscriptionEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				commerceSubscriptionEntries) {

			if (entityCache.getResult(
					CommerceSubscriptionEntryImpl.class,
					commerceSubscriptionEntry.getPrimaryKey()) == null) {

				cacheResult(commerceSubscriptionEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce subscription entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceSubscriptionEntryImpl.class);

		finderCache.clearCache(CommerceSubscriptionEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce subscription entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		entityCache.removeResult(
			CommerceSubscriptionEntryImpl.class, commerceSubscriptionEntry);
	}

	@Override
	public void clearCache(
		List<CommerceSubscriptionEntry> commerceSubscriptionEntries) {

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				commerceSubscriptionEntries) {

			entityCache.removeResult(
				CommerceSubscriptionEntryImpl.class, commerceSubscriptionEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceSubscriptionEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceSubscriptionEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceSubscriptionEntryModelImpl commerceSubscriptionEntryModelImpl) {

		Object[] args = new Object[] {
			commerceSubscriptionEntryModelImpl.getUuid(),
			commerceSubscriptionEntryModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, commerceSubscriptionEntryModelImpl);

		args = new Object[] {
			commerceSubscriptionEntryModelImpl.getCommerceOrderItemId()
		};

		finderCache.putResult(
			_finderPathCountByCommerceOrderItemId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCommerceOrderItemId, args,
			commerceSubscriptionEntryModelImpl);

		args = new Object[] {
			commerceSubscriptionEntryModelImpl.getCPInstanceUuid(),
			commerceSubscriptionEntryModelImpl.getCProductId(),
			commerceSubscriptionEntryModelImpl.getCommerceOrderItemId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args, commerceSubscriptionEntryModelImpl);
	}

	/**
	 * Creates a new commerce subscription entry with the primary key. Does not add the commerce subscription entry to the database.
	 *
	 * @param commerceSubscriptionEntryId the primary key for the new commerce subscription entry
	 * @return the new commerce subscription entry
	 */
	@Override
	public CommerceSubscriptionEntry create(long commerceSubscriptionEntryId) {
		CommerceSubscriptionEntry commerceSubscriptionEntry =
			new CommerceSubscriptionEntryImpl();

		commerceSubscriptionEntry.setNew(true);
		commerceSubscriptionEntry.setPrimaryKey(commerceSubscriptionEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceSubscriptionEntry.setUuid(uuid);

		commerceSubscriptionEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceSubscriptionEntry;
	}

	/**
	 * Removes the commerce subscription entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry that was removed
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry remove(long commerceSubscriptionEntryId)
		throws NoSuchSubscriptionEntryException {

		return remove((Serializable)commerceSubscriptionEntryId);
	}

	/**
	 * Removes the commerce subscription entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce subscription entry
	 * @return the commerce subscription entry that was removed
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry remove(Serializable primaryKey)
		throws NoSuchSubscriptionEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceSubscriptionEntry commerceSubscriptionEntry =
				(CommerceSubscriptionEntry)session.get(
					CommerceSubscriptionEntryImpl.class, primaryKey);

			if (commerceSubscriptionEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSubscriptionEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceSubscriptionEntry);
		}
		catch (NoSuchSubscriptionEntryException noSuchEntityException) {
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
	protected CommerceSubscriptionEntry removeImpl(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceSubscriptionEntry)) {
				commerceSubscriptionEntry =
					(CommerceSubscriptionEntry)session.get(
						CommerceSubscriptionEntryImpl.class,
						commerceSubscriptionEntry.getPrimaryKeyObj());
			}

			if (commerceSubscriptionEntry != null) {
				session.delete(commerceSubscriptionEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceSubscriptionEntry != null) {
			clearCache(commerceSubscriptionEntry);
		}

		return commerceSubscriptionEntry;
	}

	@Override
	public CommerceSubscriptionEntry updateImpl(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		boolean isNew = commerceSubscriptionEntry.isNew();

		if (!(commerceSubscriptionEntry instanceof
				CommerceSubscriptionEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceSubscriptionEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceSubscriptionEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceSubscriptionEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceSubscriptionEntry implementation " +
					commerceSubscriptionEntry.getClass());
		}

		CommerceSubscriptionEntryModelImpl commerceSubscriptionEntryModelImpl =
			(CommerceSubscriptionEntryModelImpl)commerceSubscriptionEntry;

		if (Validator.isNull(commerceSubscriptionEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceSubscriptionEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceSubscriptionEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceSubscriptionEntry.setCreateDate(date);
			}
			else {
				commerceSubscriptionEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceSubscriptionEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceSubscriptionEntry.setModifiedDate(date);
			}
			else {
				commerceSubscriptionEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceSubscriptionEntry);
			}
			else {
				commerceSubscriptionEntry =
					(CommerceSubscriptionEntry)session.merge(
						commerceSubscriptionEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceSubscriptionEntryImpl.class,
			commerceSubscriptionEntryModelImpl, false, true);

		cacheUniqueFindersCache(commerceSubscriptionEntryModelImpl);

		if (isNew) {
			commerceSubscriptionEntry.setNew(false);
		}

		commerceSubscriptionEntry.resetOriginalValues();

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce subscription entry
	 * @return the commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSubscriptionEntryException {

		CommerceSubscriptionEntry commerceSubscriptionEntry = fetchByPrimaryKey(
			primaryKey);

		if (commerceSubscriptionEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSubscriptionEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceSubscriptionEntry;
	}

	/**
	 * Returns the commerce subscription entry with the primary key or throws a <code>NoSuchSubscriptionEntryException</code> if it could not be found.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry
	 * @throws NoSuchSubscriptionEntryException if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry findByPrimaryKey(
			long commerceSubscriptionEntryId)
		throws NoSuchSubscriptionEntryException {

		return findByPrimaryKey((Serializable)commerceSubscriptionEntryId);
	}

	/**
	 * Returns the commerce subscription entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceSubscriptionEntryId the primary key of the commerce subscription entry
	 * @return the commerce subscription entry, or <code>null</code> if a commerce subscription entry with the primary key could not be found
	 */
	@Override
	public CommerceSubscriptionEntry fetchByPrimaryKey(
		long commerceSubscriptionEntryId) {

		return fetchByPrimaryKey((Serializable)commerceSubscriptionEntryId);
	}

	/**
	 * Returns all the commerce subscription entries.
	 *
	 * @return the commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce subscription entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @return the range of commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce subscription entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceSubscriptionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce subscription entries
	 * @param end the upper bound of the range of commerce subscription entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce subscription entries
	 */
	@Override
	public List<CommerceSubscriptionEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator,
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

		List<CommerceSubscriptionEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceSubscriptionEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCESUBSCRIPTIONENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY;

				sql = sql.concat(
					CommerceSubscriptionEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceSubscriptionEntry>)QueryUtil.list(
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
	 * Removes all the commerce subscription entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceSubscriptionEntry commerceSubscriptionEntry : findAll()) {
			remove(commerceSubscriptionEntry);
		}
	}

	/**
	 * Returns the number of commerce subscription entries.
	 *
	 * @return the number of commerce subscription entries
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
					_SQL_COUNT_COMMERCESUBSCRIPTIONENTRY);

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
		return "commerceSubscriptionEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceSubscriptionEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce subscription entry persistence.
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathFetchByCommerceOrderItemId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCommerceOrderItemId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderItemId"}, true);

		_finderPathCountByCommerceOrderItemId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderItemId", new String[] {Long.class.getName()},
			new String[] {"commerceOrderItemId"}, false);

		_finderPathWithPaginationFindBySubscriptionStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySubscriptionStatus",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"subscriptionStatus"}, true);

		_finderPathWithoutPaginationFindBySubscriptionStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySubscriptionStatus", new String[] {Integer.class.getName()},
			new String[] {"subscriptionStatus"}, true);

		_finderPathCountBySubscriptionStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySubscriptionStatus", new String[] {Integer.class.getName()},
			new String[] {"subscriptionStatus"}, false);

		_finderPathWithPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);

		_finderPathWithPaginationFindByG_C_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByG_C_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "userId"}, true);

		_finderPathCountByG_C_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "userId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"CPInstanceUuid", "CProductId", "commerceOrderItemId"
			},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			new String[] {
				"CPInstanceUuid", "CProductId", "commerceOrderItemId"
			},
			false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceSubscriptionEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY =
		"SELECT commerceSubscriptionEntry FROM CommerceSubscriptionEntry commerceSubscriptionEntry";

	private static final String _SQL_SELECT_COMMERCESUBSCRIPTIONENTRY_WHERE =
		"SELECT commerceSubscriptionEntry FROM CommerceSubscriptionEntry commerceSubscriptionEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCESUBSCRIPTIONENTRY =
		"SELECT COUNT(commerceSubscriptionEntry) FROM CommerceSubscriptionEntry commerceSubscriptionEntry";

	private static final String _SQL_COUNT_COMMERCESUBSCRIPTIONENTRY_WHERE =
		"SELECT COUNT(commerceSubscriptionEntry) FROM CommerceSubscriptionEntry commerceSubscriptionEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceSubscriptionEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceSubscriptionEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceSubscriptionEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSubscriptionEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "deliverySubscriptionTypeSettings"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}