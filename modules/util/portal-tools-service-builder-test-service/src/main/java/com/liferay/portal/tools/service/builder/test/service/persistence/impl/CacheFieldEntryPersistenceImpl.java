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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCacheFieldEntryException;
import com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry;
import com.liferay.portal.tools.service.builder.test.model.CacheFieldEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheFieldEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the cache field entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CacheFieldEntryPersistenceImpl
	extends BasePersistenceImpl<CacheFieldEntry>
	implements CacheFieldEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CacheFieldEntryUtil</code> to access the cache field entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CacheFieldEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the cache field entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cache field entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @return the range of matching cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cache field entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cache field entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator,
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

		List<CacheFieldEntry> list = null;

		if (useFinderCache) {
			list = (List<CacheFieldEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CacheFieldEntry cacheFieldEntry : list) {
					if (groupId != cacheFieldEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_CACHEFIELDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CacheFieldEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<CacheFieldEntry>)QueryUtil.list(
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
	 * Returns the first cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache field entry
	 * @throws NoSuchCacheFieldEntryException if a matching cache field entry could not be found
	 */
	@Override
	public CacheFieldEntry findByGroupId_First(
			long groupId, OrderByComparator<CacheFieldEntry> orderByComparator)
		throws NoSuchCacheFieldEntryException {

		CacheFieldEntry cacheFieldEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (cacheFieldEntry != null) {
			return cacheFieldEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchCacheFieldEntryException(sb.toString());
	}

	/**
	 * Returns the first cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cache field entry, or <code>null</code> if a matching cache field entry could not be found
	 */
	@Override
	public CacheFieldEntry fetchByGroupId_First(
		long groupId, OrderByComparator<CacheFieldEntry> orderByComparator) {

		List<CacheFieldEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cache field entry
	 * @throws NoSuchCacheFieldEntryException if a matching cache field entry could not be found
	 */
	@Override
	public CacheFieldEntry findByGroupId_Last(
			long groupId, OrderByComparator<CacheFieldEntry> orderByComparator)
		throws NoSuchCacheFieldEntryException {

		CacheFieldEntry cacheFieldEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (cacheFieldEntry != null) {
			return cacheFieldEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchCacheFieldEntryException(sb.toString());
	}

	/**
	 * Returns the last cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cache field entry, or <code>null</code> if a matching cache field entry could not be found
	 */
	@Override
	public CacheFieldEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<CacheFieldEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CacheFieldEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cache field entries before and after the current cache field entry in the ordered set where groupId = &#63;.
	 *
	 * @param cacheFieldEntryId the primary key of the current cache field entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cache field entry
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	@Override
	public CacheFieldEntry[] findByGroupId_PrevAndNext(
			long cacheFieldEntryId, long groupId,
			OrderByComparator<CacheFieldEntry> orderByComparator)
		throws NoSuchCacheFieldEntryException {

		CacheFieldEntry cacheFieldEntry = findByPrimaryKey(cacheFieldEntryId);

		Session session = null;

		try {
			session = openSession();

			CacheFieldEntry[] array = new CacheFieldEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, cacheFieldEntry, groupId, orderByComparator, true);

			array[1] = cacheFieldEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, cacheFieldEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CacheFieldEntry getByGroupId_PrevAndNext(
		Session session, CacheFieldEntry cacheFieldEntry, long groupId,
		OrderByComparator<CacheFieldEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CACHEFIELDENTRY_WHERE);

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
			sb.append(CacheFieldEntryModelImpl.ORDER_BY_JPQL);
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
						cacheFieldEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CacheFieldEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cache field entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CacheFieldEntry cacheFieldEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cacheFieldEntry);
		}
	}

	/**
	 * Returns the number of cache field entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cache field entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CACHEFIELDENTRY_WHERE);

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
		"cacheFieldEntry.groupId = ?";

	public CacheFieldEntryPersistenceImpl() {
		setModelClass(CacheFieldEntry.class);

		setModelImplClass(CacheFieldEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CacheFieldEntryTable.INSTANCE);
	}

	/**
	 * Caches the cache field entry in the entity cache if it is enabled.
	 *
	 * @param cacheFieldEntry the cache field entry
	 */
	@Override
	public void cacheResult(CacheFieldEntry cacheFieldEntry) {
		entityCache.putResult(
			CacheFieldEntryImpl.class, cacheFieldEntry.getPrimaryKey(),
			cacheFieldEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cache field entries in the entity cache if it is enabled.
	 *
	 * @param cacheFieldEntries the cache field entries
	 */
	@Override
	public void cacheResult(List<CacheFieldEntry> cacheFieldEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cacheFieldEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CacheFieldEntry cacheFieldEntry : cacheFieldEntries) {
			CacheFieldEntry cachedCacheFieldEntry =
				(CacheFieldEntry)entityCache.getResult(
					CacheFieldEntryImpl.class, cacheFieldEntry.getPrimaryKey());

			if (cachedCacheFieldEntry == null) {
				cacheResult(cacheFieldEntry);
			}
			else {
				CacheFieldEntryModelImpl cacheFieldEntryModelImpl =
					(CacheFieldEntryModelImpl)cacheFieldEntry;
				CacheFieldEntryModelImpl cachedCacheFieldEntryModelImpl =
					(CacheFieldEntryModelImpl)cachedCacheFieldEntry;

				cacheFieldEntryModelImpl.setNickname(
					cachedCacheFieldEntryModelImpl.getNickname());
			}
		}
	}

	/**
	 * Clears the cache for all cache field entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CacheFieldEntryImpl.class);

		finderCache.clearCache(CacheFieldEntryImpl.class);
	}

	/**
	 * Clears the cache for the cache field entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CacheFieldEntry cacheFieldEntry) {
		entityCache.removeResult(CacheFieldEntryImpl.class, cacheFieldEntry);
	}

	@Override
	public void clearCache(List<CacheFieldEntry> cacheFieldEntries) {
		for (CacheFieldEntry cacheFieldEntry : cacheFieldEntries) {
			entityCache.removeResult(
				CacheFieldEntryImpl.class, cacheFieldEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CacheFieldEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CacheFieldEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new cache field entry with the primary key. Does not add the cache field entry to the database.
	 *
	 * @param cacheFieldEntryId the primary key for the new cache field entry
	 * @return the new cache field entry
	 */
	@Override
	public CacheFieldEntry create(long cacheFieldEntryId) {
		CacheFieldEntry cacheFieldEntry = new CacheFieldEntryImpl();

		cacheFieldEntry.setNew(true);
		cacheFieldEntry.setPrimaryKey(cacheFieldEntryId);

		return cacheFieldEntry;
	}

	/**
	 * Removes the cache field entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry that was removed
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	@Override
	public CacheFieldEntry remove(long cacheFieldEntryId)
		throws NoSuchCacheFieldEntryException {

		return remove((Serializable)cacheFieldEntryId);
	}

	/**
	 * Removes the cache field entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cache field entry
	 * @return the cache field entry that was removed
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	@Override
	public CacheFieldEntry remove(Serializable primaryKey)
		throws NoSuchCacheFieldEntryException {

		Session session = null;

		try {
			session = openSession();

			CacheFieldEntry cacheFieldEntry = (CacheFieldEntry)session.get(
				CacheFieldEntryImpl.class, primaryKey);

			if (cacheFieldEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCacheFieldEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cacheFieldEntry);
		}
		catch (NoSuchCacheFieldEntryException noSuchEntityException) {
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
	protected CacheFieldEntry removeImpl(CacheFieldEntry cacheFieldEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cacheFieldEntry)) {
				cacheFieldEntry = (CacheFieldEntry)session.get(
					CacheFieldEntryImpl.class,
					cacheFieldEntry.getPrimaryKeyObj());
			}

			if (cacheFieldEntry != null) {
				session.delete(cacheFieldEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cacheFieldEntry != null) {
			clearCache(cacheFieldEntry);
		}

		return cacheFieldEntry;
	}

	@Override
	public CacheFieldEntry updateImpl(CacheFieldEntry cacheFieldEntry) {
		boolean isNew = cacheFieldEntry.isNew();

		if (!(cacheFieldEntry instanceof CacheFieldEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cacheFieldEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cacheFieldEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cacheFieldEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CacheFieldEntry implementation " +
					cacheFieldEntry.getClass());
		}

		CacheFieldEntryModelImpl cacheFieldEntryModelImpl =
			(CacheFieldEntryModelImpl)cacheFieldEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cacheFieldEntry);
			}
			else {
				cacheFieldEntry = (CacheFieldEntry)session.merge(
					cacheFieldEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CacheFieldEntryImpl.class, cacheFieldEntryModelImpl, false, true);

		if (isNew) {
			cacheFieldEntry.setNew(false);
		}

		cacheFieldEntry.resetOriginalValues();

		return cacheFieldEntry;
	}

	/**
	 * Returns the cache field entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cache field entry
	 * @return the cache field entry
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	@Override
	public CacheFieldEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCacheFieldEntryException {

		CacheFieldEntry cacheFieldEntry = fetchByPrimaryKey(primaryKey);

		if (cacheFieldEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCacheFieldEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cacheFieldEntry;
	}

	/**
	 * Returns the cache field entry with the primary key or throws a <code>NoSuchCacheFieldEntryException</code> if it could not be found.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry
	 * @throws NoSuchCacheFieldEntryException if a cache field entry with the primary key could not be found
	 */
	@Override
	public CacheFieldEntry findByPrimaryKey(long cacheFieldEntryId)
		throws NoSuchCacheFieldEntryException {

		return findByPrimaryKey((Serializable)cacheFieldEntryId);
	}

	/**
	 * Returns the cache field entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry, or <code>null</code> if a cache field entry with the primary key could not be found
	 */
	@Override
	public CacheFieldEntry fetchByPrimaryKey(long cacheFieldEntryId) {
		return fetchByPrimaryKey((Serializable)cacheFieldEntryId);
	}

	/**
	 * Returns all the cache field entries.
	 *
	 * @return the cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cache field entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @return the range of cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cache field entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findAll(
		int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cache field entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cache field entries
	 */
	@Override
	public List<CacheFieldEntry> findAll(
		int start, int end,
		OrderByComparator<CacheFieldEntry> orderByComparator,
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

		List<CacheFieldEntry> list = null;

		if (useFinderCache) {
			list = (List<CacheFieldEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CACHEFIELDENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CACHEFIELDENTRY;

				sql = sql.concat(CacheFieldEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CacheFieldEntry>)QueryUtil.list(
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
	 * Removes all the cache field entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CacheFieldEntry cacheFieldEntry : findAll()) {
			remove(cacheFieldEntry);
		}
	}

	/**
	 * Returns the number of cache field entries.
	 *
	 * @return the number of cache field entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CACHEFIELDENTRY);

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
		return "cacheFieldEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CACHEFIELDENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CacheFieldEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cache field entry persistence.
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
	}

	public void destroy() {
		entityCache.removeCache(CacheFieldEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CACHEFIELDENTRY =
		"SELECT cacheFieldEntry FROM CacheFieldEntry cacheFieldEntry";

	private static final String _SQL_SELECT_CACHEFIELDENTRY_WHERE =
		"SELECT cacheFieldEntry FROM CacheFieldEntry cacheFieldEntry WHERE ";

	private static final String _SQL_COUNT_CACHEFIELDENTRY =
		"SELECT COUNT(cacheFieldEntry) FROM CacheFieldEntry cacheFieldEntry";

	private static final String _SQL_COUNT_CACHEFIELDENTRY_WHERE =
		"SELECT COUNT(cacheFieldEntry) FROM CacheFieldEntry cacheFieldEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cacheFieldEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CacheFieldEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CacheFieldEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CacheFieldEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}