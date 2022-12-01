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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLoadFinderCacheEntryException;
import com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry;
import com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.LoadFinderCacheEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LoadFinderCacheEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LoadFinderCacheEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LoadFinderCacheEntryUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the load finder cache entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LoadFinderCacheEntryPersistenceImpl
	extends BasePersistenceImpl<LoadFinderCacheEntry>
	implements LoadFinderCacheEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LoadFinderCacheEntryUtil</code> to access the load finder cache entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LoadFinderCacheEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	@Override
	public FinderPath getFinderPathWithoutPaginationFindAll() {
		return _finderPathWithoutPaginationFindAll;
	}

	@Override
	public FinderPath getFinderPathCountAll() {
		return _finderPathCountAll;
	}

	private FinderPath _finderPathFetchByUniqueName;

	@Override
	public FinderPath getFinderPathFetchByUniqueName() {
		return _finderPathFetchByUniqueName;
	}

	private FinderPath _finderPathCountByUniqueName;

	@Override
	public FinderPath getFinderPathCountByUniqueName() {
		return _finderPathCountByUniqueName;
	}

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or throws a <code>NoSuchLoadFinderCacheEntryException</code> if it could not be found.
	 *
	 * @param uniqueName the unique name
	 * @return the matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry findByUniqueName(String uniqueName)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = fetchByUniqueName(
			uniqueName);

		if (loadFinderCacheEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uniqueName=");
			sb.append(uniqueName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLoadFinderCacheEntryException(sb.toString());
		}

		return loadFinderCacheEntry;
	}

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uniqueName the unique name
	 * @return the matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry fetchByUniqueName(String uniqueName) {
		return fetchByUniqueName(uniqueName, true);
	}

	/**
	 * Returns the load finder cache entry where uniqueName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uniqueName the unique name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry fetchByUniqueName(
		String uniqueName, boolean useFinderCache) {

		uniqueName = Objects.toString(uniqueName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uniqueName};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUniqueName, finderArgs, this);
		}

		if (result instanceof LoadFinderCacheEntry) {
			LoadFinderCacheEntry loadFinderCacheEntry =
				(LoadFinderCacheEntry)result;

			if (!Objects.equals(
					uniqueName, loadFinderCacheEntry.getUniqueName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_LOADFINDERCACHEENTRY_WHERE);

			boolean bindUniqueName = false;

			if (uniqueName.isEmpty()) {
				sb.append(_FINDER_COLUMN_UNIQUENAME_UNIQUENAME_3);
			}
			else {
				bindUniqueName = true;

				sb.append(_FINDER_COLUMN_UNIQUENAME_UNIQUENAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUniqueName) {
					queryPos.add(uniqueName);
				}

				List<LoadFinderCacheEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUniqueName, finderArgs, list);
					}
				}
				else {
					LoadFinderCacheEntry loadFinderCacheEntry = list.get(0);

					result = loadFinderCacheEntry;

					cacheResult(loadFinderCacheEntry);
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
			return (LoadFinderCacheEntry)result;
		}
	}

	/**
	 * Removes the load finder cache entry where uniqueName = &#63; from the database.
	 *
	 * @param uniqueName the unique name
	 * @return the load finder cache entry that was removed
	 */
	@Override
	public LoadFinderCacheEntry removeByUniqueName(String uniqueName)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = findByUniqueName(
			uniqueName);

		return remove(loadFinderCacheEntry);
	}

	/**
	 * Returns the number of load finder cache entries where uniqueName = &#63;.
	 *
	 * @param uniqueName the unique name
	 * @return the number of matching load finder cache entries
	 */
	@Override
	public int countByUniqueName(String uniqueName) {
		uniqueName = Objects.toString(uniqueName, "");

		FinderPath finderPath = _finderPathCountByUniqueName;

		Object[] finderArgs = new Object[] {uniqueName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LOADFINDERCACHEENTRY_WHERE);

			boolean bindUniqueName = false;

			if (uniqueName.isEmpty()) {
				sb.append(_FINDER_COLUMN_UNIQUENAME_UNIQUENAME_3);
			}
			else {
				bindUniqueName = true;

				sb.append(_FINDER_COLUMN_UNIQUENAME_UNIQUENAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUniqueName) {
					queryPos.add(uniqueName);
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

	private static final String _FINDER_COLUMN_UNIQUENAME_UNIQUENAME_2 =
		"loadFinderCacheEntry.uniqueName = ?";

	private static final String _FINDER_COLUMN_UNIQUENAME_UNIQUENAME_3 =
		"(loadFinderCacheEntry.uniqueName IS NULL OR loadFinderCacheEntry.uniqueName = '')";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;

	@Override
	public FinderPath getFinderPathWithoutPaginationFindByGroupId() {
		return _finderPathWithoutPaginationFindByGroupId;
	}

	private FinderPath _finderPathCountByGroupId;

	@Override
	public FinderPath getFinderPathCountByGroupId() {
		return _finderPathCountByGroupId;
	}

	/**
	 * Returns all the load finder cache entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the load finder cache entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @return the range of matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator,
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

		List<LoadFinderCacheEntry> list = null;

		if (useFinderCache) {
			list = (List<LoadFinderCacheEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LoadFinderCacheEntry loadFinderCacheEntry : list) {
					if (groupId != loadFinderCacheEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_LOADFINDERCACHEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LoadFinderCacheEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<LoadFinderCacheEntry>)QueryUtil.list(
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
	 * Returns the first load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry findByGroupId_First(
			long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (loadFinderCacheEntry != null) {
			return loadFinderCacheEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLoadFinderCacheEntryException(sb.toString());
	}

	/**
	 * Returns the first load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		List<LoadFinderCacheEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (loadFinderCacheEntry != null) {
			return loadFinderCacheEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLoadFinderCacheEntryException(sb.toString());
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LoadFinderCacheEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the load finder cache entries before and after the current load finder cache entry in the ordered set where groupId = &#63;.
	 *
	 * @param loadFinderCacheEntryId the primary key of the current load finder cache entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public LoadFinderCacheEntry[] findByGroupId_PrevAndNext(
			long loadFinderCacheEntryId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = findByPrimaryKey(
			loadFinderCacheEntryId);

		Session session = null;

		try {
			session = openSession();

			LoadFinderCacheEntry[] array = new LoadFinderCacheEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, loadFinderCacheEntry, groupId, orderByComparator,
				true);

			array[1] = loadFinderCacheEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, loadFinderCacheEntry, groupId, orderByComparator,
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

	protected LoadFinderCacheEntry getByGroupId_PrevAndNext(
		Session session, LoadFinderCacheEntry loadFinderCacheEntry,
		long groupId, OrderByComparator<LoadFinderCacheEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LOADFINDERCACHEENTRY_WHERE);

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
			sb.append(LoadFinderCacheEntryModelImpl.ORDER_BY_JPQL);
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
						loadFinderCacheEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LoadFinderCacheEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the load finder cache entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LoadFinderCacheEntry loadFinderCacheEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(loadFinderCacheEntry);
		}
	}

	/**
	 * Returns the number of load finder cache entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching load finder cache entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LOADFINDERCACHEENTRY_WHERE);

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
		"loadFinderCacheEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByC_G;
	private FinderPath _finderPathWithoutPaginationFindByC_G;

	@Override
	public FinderPath getFinderPathWithoutPaginationFindByC_G() {
		return _finderPathWithoutPaginationFindByC_G;
	}

	private FinderPath _finderPathCountByC_G;

	@Override
	public FinderPath getFinderPathCountByC_G() {
		return _finderPathCountByC_G;
	}

	/**
	 * Returns all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByC_G(long companyId, long groupId) {
		return findByC_G(
			companyId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @return the range of matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end) {

		return findByC_G(companyId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return findByC_G(
			companyId, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findByC_G(
		long companyId, long groupId, int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_G;
				finderArgs = new Object[] {companyId, groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_G;
			finderArgs = new Object[] {
				companyId, groupId, start, end, orderByComparator
			};
		}

		List<LoadFinderCacheEntry> list = null;

		if (useFinderCache) {
			list = (List<LoadFinderCacheEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LoadFinderCacheEntry loadFinderCacheEntry : list) {
					if ((companyId != loadFinderCacheEntry.getCompanyId()) ||
						(groupId != loadFinderCacheEntry.getGroupId())) {

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

			sb.append(_SQL_SELECT_LOADFINDERCACHEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_G_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LoadFinderCacheEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(groupId);

				list = (List<LoadFinderCacheEntry>)QueryUtil.list(
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
	 * Returns the first load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry findByC_G_First(
			long companyId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = fetchByC_G_First(
			companyId, groupId, orderByComparator);

		if (loadFinderCacheEntry != null) {
			return loadFinderCacheEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLoadFinderCacheEntryException(sb.toString());
	}

	/**
	 * Returns the first load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry fetchByC_G_First(
		long companyId, long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		List<LoadFinderCacheEntry> list = findByC_G(
			companyId, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry findByC_G_Last(
			long companyId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = fetchByC_G_Last(
			companyId, groupId, orderByComparator);

		if (loadFinderCacheEntry != null) {
			return loadFinderCacheEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLoadFinderCacheEntryException(sb.toString());
	}

	/**
	 * Returns the last load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching load finder cache entry, or <code>null</code> if a matching load finder cache entry could not be found
	 */
	@Override
	public LoadFinderCacheEntry fetchByC_G_Last(
		long companyId, long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		int count = countByC_G(companyId, groupId);

		if (count == 0) {
			return null;
		}

		List<LoadFinderCacheEntry> list = findByC_G(
			companyId, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the load finder cache entries before and after the current load finder cache entry in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param loadFinderCacheEntryId the primary key of the current load finder cache entry
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public LoadFinderCacheEntry[] findByC_G_PrevAndNext(
			long loadFinderCacheEntryId, long companyId, long groupId,
			OrderByComparator<LoadFinderCacheEntry> orderByComparator)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = findByPrimaryKey(
			loadFinderCacheEntryId);

		Session session = null;

		try {
			session = openSession();

			LoadFinderCacheEntry[] array = new LoadFinderCacheEntryImpl[3];

			array[0] = getByC_G_PrevAndNext(
				session, loadFinderCacheEntry, companyId, groupId,
				orderByComparator, true);

			array[1] = loadFinderCacheEntry;

			array[2] = getByC_G_PrevAndNext(
				session, loadFinderCacheEntry, companyId, groupId,
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

	protected LoadFinderCacheEntry getByC_G_PrevAndNext(
		Session session, LoadFinderCacheEntry loadFinderCacheEntry,
		long companyId, long groupId,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LOADFINDERCACHEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_G_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_G_GROUPID_2);

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
			sb.append(LoadFinderCacheEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						loadFinderCacheEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LoadFinderCacheEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the load finder cache entries where companyId = &#63; and groupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByC_G(long companyId, long groupId) {
		for (LoadFinderCacheEntry loadFinderCacheEntry :
				findByC_G(
					companyId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(loadFinderCacheEntry);
		}
	}

	/**
	 * Returns the number of load finder cache entries where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the number of matching load finder cache entries
	 */
	@Override
	public int countByC_G(long companyId, long groupId) {
		FinderPath finderPath = _finderPathCountByC_G;

		Object[] finderArgs = new Object[] {companyId, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LOADFINDERCACHEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_G_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_G_COMPANYID_2 =
		"loadFinderCacheEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_G_GROUPID_2 =
		"loadFinderCacheEntry.groupId = ?";

	public LoadFinderCacheEntryPersistenceImpl() {
		setModelClass(LoadFinderCacheEntry.class);

		setModelImplClass(LoadFinderCacheEntryImpl.class);
		setModelPKClass(long.class);

		setTable(LoadFinderCacheEntryTable.INSTANCE);
	}

	/**
	 * Caches the load finder cache entry in the entity cache if it is enabled.
	 *
	 * @param loadFinderCacheEntry the load finder cache entry
	 */
	@Override
	public void cacheResult(LoadFinderCacheEntry loadFinderCacheEntry) {
		entityCache.putResult(
			LoadFinderCacheEntryImpl.class,
			loadFinderCacheEntry.getPrimaryKey(), loadFinderCacheEntry);

		finderCache.putResult(
			_finderPathFetchByUniqueName,
			new Object[] {loadFinderCacheEntry.getUniqueName()},
			loadFinderCacheEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the load finder cache entries in the entity cache if it is enabled.
	 *
	 * @param loadFinderCacheEntries the load finder cache entries
	 */
	@Override
	public void cacheResult(List<LoadFinderCacheEntry> loadFinderCacheEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (loadFinderCacheEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LoadFinderCacheEntry loadFinderCacheEntry :
				loadFinderCacheEntries) {

			if (entityCache.getResult(
					LoadFinderCacheEntryImpl.class,
					loadFinderCacheEntry.getPrimaryKey()) == null) {

				cacheResult(loadFinderCacheEntry);
			}
		}
	}

	/**
	 * Clears the cache for all load finder cache entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LoadFinderCacheEntryImpl.class);

		finderCache.clearCache(LoadFinderCacheEntryImpl.class);
	}

	/**
	 * Clears the cache for the load finder cache entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LoadFinderCacheEntry loadFinderCacheEntry) {
		entityCache.removeResult(
			LoadFinderCacheEntryImpl.class, loadFinderCacheEntry);
	}

	@Override
	public void clearCache(List<LoadFinderCacheEntry> loadFinderCacheEntries) {
		for (LoadFinderCacheEntry loadFinderCacheEntry :
				loadFinderCacheEntries) {

			entityCache.removeResult(
				LoadFinderCacheEntryImpl.class, loadFinderCacheEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LoadFinderCacheEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LoadFinderCacheEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LoadFinderCacheEntryModelImpl loadFinderCacheEntryModelImpl) {

		Object[] args = new Object[] {
			loadFinderCacheEntryModelImpl.getUniqueName()
		};

		finderCache.putResult(
			_finderPathCountByUniqueName, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUniqueName, args, loadFinderCacheEntryModelImpl);
	}

	/**
	 * Creates a new load finder cache entry with the primary key. Does not add the load finder cache entry to the database.
	 *
	 * @param loadFinderCacheEntryId the primary key for the new load finder cache entry
	 * @return the new load finder cache entry
	 */
	@Override
	public LoadFinderCacheEntry create(long loadFinderCacheEntryId) {
		LoadFinderCacheEntry loadFinderCacheEntry =
			new LoadFinderCacheEntryImpl();

		loadFinderCacheEntry.setNew(true);
		loadFinderCacheEntry.setPrimaryKey(loadFinderCacheEntryId);

		loadFinderCacheEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return loadFinderCacheEntry;
	}

	/**
	 * Removes the load finder cache entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry that was removed
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public LoadFinderCacheEntry remove(long loadFinderCacheEntryId)
		throws NoSuchLoadFinderCacheEntryException {

		return remove((Serializable)loadFinderCacheEntryId);
	}

	/**
	 * Removes the load finder cache entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the load finder cache entry
	 * @return the load finder cache entry that was removed
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public LoadFinderCacheEntry remove(Serializable primaryKey)
		throws NoSuchLoadFinderCacheEntryException {

		Session session = null;

		try {
			session = openSession();

			LoadFinderCacheEntry loadFinderCacheEntry =
				(LoadFinderCacheEntry)session.get(
					LoadFinderCacheEntryImpl.class, primaryKey);

			if (loadFinderCacheEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLoadFinderCacheEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(loadFinderCacheEntry);
		}
		catch (NoSuchLoadFinderCacheEntryException noSuchEntityException) {
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
	protected LoadFinderCacheEntry removeImpl(
		LoadFinderCacheEntry loadFinderCacheEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(loadFinderCacheEntry)) {
				loadFinderCacheEntry = (LoadFinderCacheEntry)session.get(
					LoadFinderCacheEntryImpl.class,
					loadFinderCacheEntry.getPrimaryKeyObj());
			}

			if (loadFinderCacheEntry != null) {
				session.delete(loadFinderCacheEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (loadFinderCacheEntry != null) {
			clearCache(loadFinderCacheEntry);
		}

		return loadFinderCacheEntry;
	}

	@Override
	public LoadFinderCacheEntry updateImpl(
		LoadFinderCacheEntry loadFinderCacheEntry) {

		boolean isNew = loadFinderCacheEntry.isNew();

		if (!(loadFinderCacheEntry instanceof LoadFinderCacheEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(loadFinderCacheEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					loadFinderCacheEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in loadFinderCacheEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LoadFinderCacheEntry implementation " +
					loadFinderCacheEntry.getClass());
		}

		LoadFinderCacheEntryModelImpl loadFinderCacheEntryModelImpl =
			(LoadFinderCacheEntryModelImpl)loadFinderCacheEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(loadFinderCacheEntry);
			}
			else {
				loadFinderCacheEntry = (LoadFinderCacheEntry)session.merge(
					loadFinderCacheEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			LoadFinderCacheEntryImpl.class, loadFinderCacheEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(loadFinderCacheEntryModelImpl);

		if (isNew) {
			loadFinderCacheEntry.setNew(false);
		}

		loadFinderCacheEntry.resetOriginalValues();

		return loadFinderCacheEntry;
	}

	/**
	 * Returns the load finder cache entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the load finder cache entry
	 * @return the load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public LoadFinderCacheEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLoadFinderCacheEntryException {

		LoadFinderCacheEntry loadFinderCacheEntry = fetchByPrimaryKey(
			primaryKey);

		if (loadFinderCacheEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLoadFinderCacheEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return loadFinderCacheEntry;
	}

	/**
	 * Returns the load finder cache entry with the primary key or throws a <code>NoSuchLoadFinderCacheEntryException</code> if it could not be found.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry
	 * @throws NoSuchLoadFinderCacheEntryException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public LoadFinderCacheEntry findByPrimaryKey(long loadFinderCacheEntryId)
		throws NoSuchLoadFinderCacheEntryException {

		return findByPrimaryKey((Serializable)loadFinderCacheEntryId);
	}

	/**
	 * Returns the load finder cache entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry, or <code>null</code> if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public LoadFinderCacheEntry fetchByPrimaryKey(long loadFinderCacheEntryId) {
		return fetchByPrimaryKey((Serializable)loadFinderCacheEntryId);
	}

	/**
	 * Returns all the load finder cache entries.
	 *
	 * @return the load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the load finder cache entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @return the range of load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findAll(
		int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the load finder cache entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of load finder cache entries
	 */
	@Override
	public List<LoadFinderCacheEntry> findAll(
		int start, int end,
		OrderByComparator<LoadFinderCacheEntry> orderByComparator,
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

		List<LoadFinderCacheEntry> list = null;

		if (useFinderCache) {
			list = (List<LoadFinderCacheEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LOADFINDERCACHEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LOADFINDERCACHEENTRY;

				sql = sql.concat(LoadFinderCacheEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LoadFinderCacheEntry>)QueryUtil.list(
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
	 * Removes all the load finder cache entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LoadFinderCacheEntry loadFinderCacheEntry : findAll()) {
			remove(loadFinderCacheEntry);
		}
	}

	/**
	 * Returns the number of load finder cache entries.
	 *
	 * @return the number of load finder cache entries
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
					_SQL_COUNT_LOADFINDERCACHEENTRY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "loadFinderCacheEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LOADFINDERCACHEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LoadFinderCacheEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the load finder cache entry persistence.
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

		_finderPathFetchByUniqueName = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUniqueName",
			new String[] {String.class.getName()}, new String[] {"uniqueName"},
			true);

		_finderPathCountByUniqueName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUniqueName",
			new String[] {String.class.getName()}, new String[] {"uniqueName"},
			false);

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

		_finderPathWithPaginationFindByC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "groupId"}, true);

		_finderPathWithoutPaginationFindByC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_G",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "groupId"}, true);

		_finderPathCountByC_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "groupId"}, false);

		_setLoadFinderCacheEntryUtilPersistence(this);
	}

	public void destroy() {
		_setLoadFinderCacheEntryUtilPersistence(null);

		entityCache.removeCache(LoadFinderCacheEntryImpl.class.getName());
	}

	private void _setLoadFinderCacheEntryUtilPersistence(
		LoadFinderCacheEntryPersistence loadFinderCacheEntryPersistence) {

		try {
			Field field = LoadFinderCacheEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, loadFinderCacheEntryPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LOADFINDERCACHEENTRY =
		"SELECT loadFinderCacheEntry FROM LoadFinderCacheEntry loadFinderCacheEntry";

	private static final String _SQL_SELECT_LOADFINDERCACHEENTRY_WHERE =
		"SELECT loadFinderCacheEntry FROM LoadFinderCacheEntry loadFinderCacheEntry WHERE ";

	private static final String _SQL_COUNT_LOADFINDERCACHEENTRY =
		"SELECT COUNT(loadFinderCacheEntry) FROM LoadFinderCacheEntry loadFinderCacheEntry";

	private static final String _SQL_COUNT_LOADFINDERCACHEENTRY_WHERE =
		"SELECT COUNT(loadFinderCacheEntry) FROM LoadFinderCacheEntry loadFinderCacheEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"loadFinderCacheEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LoadFinderCacheEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LoadFinderCacheEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LoadFinderCacheEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}