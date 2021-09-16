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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchFinderWhereClauseEntryException;
import com.liferay.portal.tools.service.builder.test.model.FinderWhereClauseEntry;
import com.liferay.portal.tools.service.builder.test.model.FinderWhereClauseEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.FinderWhereClauseEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.FinderWhereClauseEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.FinderWhereClauseEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the finder where clause entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FinderWhereClauseEntryPersistenceImpl
	extends BasePersistenceImpl<FinderWhereClauseEntry>
	implements FinderWhereClauseEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FinderWhereClauseEntryUtil</code> to access the finder where clause entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FinderWhereClauseEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByName_Nickname;
	private FinderPath _finderPathWithoutPaginationFindByName_Nickname;
	private FinderPath _finderPathCountByName_Nickname;

	/**
	 * Returns all the finder where clause entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findByName_Nickname(String name) {
		return findByName_Nickname(
			name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the finder where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FinderWhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of finder where clause entries
	 * @param end the upper bound of the range of finder where clause entries (not inclusive)
	 * @return the range of matching finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findByName_Nickname(
		String name, int start, int end) {

		return findByName_Nickname(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the finder where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FinderWhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of finder where clause entries
	 * @param end the upper bound of the range of finder where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findByName_Nickname(
		String name, int start, int end,
		OrderByComparator<FinderWhereClauseEntry> orderByComparator) {

		return findByName_Nickname(name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the finder where clause entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FinderWhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of finder where clause entries
	 * @param end the upper bound of the range of finder where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findByName_Nickname(
		String name, int start, int end,
		OrderByComparator<FinderWhereClauseEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByName_Nickname;
				finderArgs = new Object[] {name};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByName_Nickname;
			finderArgs = new Object[] {name, start, end, orderByComparator};
		}

		List<FinderWhereClauseEntry> list = null;

		if (useFinderCache) {
			list = (List<FinderWhereClauseEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FinderWhereClauseEntry finderWhereClauseEntry : list) {
					if (!name.equals(finderWhereClauseEntry.getName())) {
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

			sb.append(_SQL_SELECT_FINDERWHERECLAUSEENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NICKNAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NICKNAME_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FinderWhereClauseEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<FinderWhereClauseEntry>)QueryUtil.list(
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
	 * Returns the first finder where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching finder where clause entry
	 * @throws NoSuchFinderWhereClauseEntryException if a matching finder where clause entry could not be found
	 */
	@Override
	public FinderWhereClauseEntry findByName_Nickname_First(
			String name,
			OrderByComparator<FinderWhereClauseEntry> orderByComparator)
		throws NoSuchFinderWhereClauseEntryException {

		FinderWhereClauseEntry finderWhereClauseEntry =
			fetchByName_Nickname_First(name, orderByComparator);

		if (finderWhereClauseEntry != null) {
			return finderWhereClauseEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchFinderWhereClauseEntryException(sb.toString());
	}

	/**
	 * Returns the first finder where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching finder where clause entry, or <code>null</code> if a matching finder where clause entry could not be found
	 */
	@Override
	public FinderWhereClauseEntry fetchByName_Nickname_First(
		String name,
		OrderByComparator<FinderWhereClauseEntry> orderByComparator) {

		List<FinderWhereClauseEntry> list = findByName_Nickname(
			name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last finder where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching finder where clause entry
	 * @throws NoSuchFinderWhereClauseEntryException if a matching finder where clause entry could not be found
	 */
	@Override
	public FinderWhereClauseEntry findByName_Nickname_Last(
			String name,
			OrderByComparator<FinderWhereClauseEntry> orderByComparator)
		throws NoSuchFinderWhereClauseEntryException {

		FinderWhereClauseEntry finderWhereClauseEntry =
			fetchByName_Nickname_Last(name, orderByComparator);

		if (finderWhereClauseEntry != null) {
			return finderWhereClauseEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchFinderWhereClauseEntryException(sb.toString());
	}

	/**
	 * Returns the last finder where clause entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching finder where clause entry, or <code>null</code> if a matching finder where clause entry could not be found
	 */
	@Override
	public FinderWhereClauseEntry fetchByName_Nickname_Last(
		String name,
		OrderByComparator<FinderWhereClauseEntry> orderByComparator) {

		int count = countByName_Nickname(name);

		if (count == 0) {
			return null;
		}

		List<FinderWhereClauseEntry> list = findByName_Nickname(
			name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the finder where clause entries before and after the current finder where clause entry in the ordered set where name = &#63;.
	 *
	 * @param finderWhereClauseEntryId the primary key of the current finder where clause entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next finder where clause entry
	 * @throws NoSuchFinderWhereClauseEntryException if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public FinderWhereClauseEntry[] findByName_Nickname_PrevAndNext(
			long finderWhereClauseEntryId, String name,
			OrderByComparator<FinderWhereClauseEntry> orderByComparator)
		throws NoSuchFinderWhereClauseEntryException {

		name = Objects.toString(name, "");

		FinderWhereClauseEntry finderWhereClauseEntry = findByPrimaryKey(
			finderWhereClauseEntryId);

		Session session = null;

		try {
			session = openSession();

			FinderWhereClauseEntry[] array = new FinderWhereClauseEntryImpl[3];

			array[0] = getByName_Nickname_PrevAndNext(
				session, finderWhereClauseEntry, name, orderByComparator, true);

			array[1] = finderWhereClauseEntry;

			array[2] = getByName_Nickname_PrevAndNext(
				session, finderWhereClauseEntry, name, orderByComparator,
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

	protected FinderWhereClauseEntry getByName_Nickname_PrevAndNext(
		Session session, FinderWhereClauseEntry finderWhereClauseEntry,
		String name,
		OrderByComparator<FinderWhereClauseEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FINDERWHERECLAUSEENTRY_WHERE);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_NAME_NICKNAME_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_NAME_NICKNAME_NAME_2);
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
			sb.append(FinderWhereClauseEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						finderWhereClauseEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FinderWhereClauseEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the finder where clause entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	@Override
	public void removeByName_Nickname(String name) {
		for (FinderWhereClauseEntry finderWhereClauseEntry :
				findByName_Nickname(
					name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(finderWhereClauseEntry);
		}
	}

	/**
	 * Returns the number of finder where clause entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching finder where clause entries
	 */
	@Override
	public int countByName_Nickname(String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName_Nickname;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FINDERWHERECLAUSEENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NICKNAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NICKNAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_NAME_NICKNAME_NAME_2 =
		"finderWhereClauseEntry.name = ? AND finderWhereClauseEntry.nickname is not NULL";

	private static final String _FINDER_COLUMN_NAME_NICKNAME_NAME_3 =
		"(finderWhereClauseEntry.name IS NULL OR finderWhereClauseEntry.name = '') AND finderWhereClauseEntry.nickname is not NULL";

	public FinderWhereClauseEntryPersistenceImpl() {
		setModelClass(FinderWhereClauseEntry.class);

		setModelImplClass(FinderWhereClauseEntryImpl.class);
		setModelPKClass(long.class);

		setTable(FinderWhereClauseEntryTable.INSTANCE);
	}

	/**
	 * Caches the finder where clause entry in the entity cache if it is enabled.
	 *
	 * @param finderWhereClauseEntry the finder where clause entry
	 */
	@Override
	public void cacheResult(FinderWhereClauseEntry finderWhereClauseEntry) {
		entityCache.putResult(
			FinderWhereClauseEntryImpl.class,
			finderWhereClauseEntry.getPrimaryKey(), finderWhereClauseEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the finder where clause entries in the entity cache if it is enabled.
	 *
	 * @param finderWhereClauseEntries the finder where clause entries
	 */
	@Override
	public void cacheResult(
		List<FinderWhereClauseEntry> finderWhereClauseEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (finderWhereClauseEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FinderWhereClauseEntry finderWhereClauseEntry :
				finderWhereClauseEntries) {

			if (entityCache.getResult(
					FinderWhereClauseEntryImpl.class,
					finderWhereClauseEntry.getPrimaryKey()) == null) {

				cacheResult(finderWhereClauseEntry);
			}
		}
	}

	/**
	 * Clears the cache for all finder where clause entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FinderWhereClauseEntryImpl.class);

		finderCache.clearCache(FinderWhereClauseEntryImpl.class);
	}

	/**
	 * Clears the cache for the finder where clause entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FinderWhereClauseEntry finderWhereClauseEntry) {
		entityCache.removeResult(
			FinderWhereClauseEntryImpl.class, finderWhereClauseEntry);
	}

	@Override
	public void clearCache(
		List<FinderWhereClauseEntry> finderWhereClauseEntries) {

		for (FinderWhereClauseEntry finderWhereClauseEntry :
				finderWhereClauseEntries) {

			entityCache.removeResult(
				FinderWhereClauseEntryImpl.class, finderWhereClauseEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FinderWhereClauseEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FinderWhereClauseEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new finder where clause entry with the primary key. Does not add the finder where clause entry to the database.
	 *
	 * @param finderWhereClauseEntryId the primary key for the new finder where clause entry
	 * @return the new finder where clause entry
	 */
	@Override
	public FinderWhereClauseEntry create(long finderWhereClauseEntryId) {
		FinderWhereClauseEntry finderWhereClauseEntry =
			new FinderWhereClauseEntryImpl();

		finderWhereClauseEntry.setNew(true);
		finderWhereClauseEntry.setPrimaryKey(finderWhereClauseEntryId);

		return finderWhereClauseEntry;
	}

	/**
	 * Removes the finder where clause entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param finderWhereClauseEntryId the primary key of the finder where clause entry
	 * @return the finder where clause entry that was removed
	 * @throws NoSuchFinderWhereClauseEntryException if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public FinderWhereClauseEntry remove(long finderWhereClauseEntryId)
		throws NoSuchFinderWhereClauseEntryException {

		return remove((Serializable)finderWhereClauseEntryId);
	}

	/**
	 * Removes the finder where clause entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the finder where clause entry
	 * @return the finder where clause entry that was removed
	 * @throws NoSuchFinderWhereClauseEntryException if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public FinderWhereClauseEntry remove(Serializable primaryKey)
		throws NoSuchFinderWhereClauseEntryException {

		Session session = null;

		try {
			session = openSession();

			FinderWhereClauseEntry finderWhereClauseEntry =
				(FinderWhereClauseEntry)session.get(
					FinderWhereClauseEntryImpl.class, primaryKey);

			if (finderWhereClauseEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFinderWhereClauseEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(finderWhereClauseEntry);
		}
		catch (NoSuchFinderWhereClauseEntryException noSuchEntityException) {
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
	protected FinderWhereClauseEntry removeImpl(
		FinderWhereClauseEntry finderWhereClauseEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(finderWhereClauseEntry)) {
				finderWhereClauseEntry = (FinderWhereClauseEntry)session.get(
					FinderWhereClauseEntryImpl.class,
					finderWhereClauseEntry.getPrimaryKeyObj());
			}

			if (finderWhereClauseEntry != null) {
				session.delete(finderWhereClauseEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (finderWhereClauseEntry != null) {
			clearCache(finderWhereClauseEntry);
		}

		return finderWhereClauseEntry;
	}

	@Override
	public FinderWhereClauseEntry updateImpl(
		FinderWhereClauseEntry finderWhereClauseEntry) {

		boolean isNew = finderWhereClauseEntry.isNew();

		if (!(finderWhereClauseEntry instanceof
				FinderWhereClauseEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(finderWhereClauseEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					finderWhereClauseEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in finderWhereClauseEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FinderWhereClauseEntry implementation " +
					finderWhereClauseEntry.getClass());
		}

		FinderWhereClauseEntryModelImpl finderWhereClauseEntryModelImpl =
			(FinderWhereClauseEntryModelImpl)finderWhereClauseEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(finderWhereClauseEntry);
			}
			else {
				finderWhereClauseEntry = (FinderWhereClauseEntry)session.merge(
					finderWhereClauseEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FinderWhereClauseEntryImpl.class, finderWhereClauseEntryModelImpl,
			false, true);

		if (isNew) {
			finderWhereClauseEntry.setNew(false);
		}

		finderWhereClauseEntry.resetOriginalValues();

		return finderWhereClauseEntry;
	}

	/**
	 * Returns the finder where clause entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the finder where clause entry
	 * @return the finder where clause entry
	 * @throws NoSuchFinderWhereClauseEntryException if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public FinderWhereClauseEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFinderWhereClauseEntryException {

		FinderWhereClauseEntry finderWhereClauseEntry = fetchByPrimaryKey(
			primaryKey);

		if (finderWhereClauseEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFinderWhereClauseEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return finderWhereClauseEntry;
	}

	/**
	 * Returns the finder where clause entry with the primary key or throws a <code>NoSuchFinderWhereClauseEntryException</code> if it could not be found.
	 *
	 * @param finderWhereClauseEntryId the primary key of the finder where clause entry
	 * @return the finder where clause entry
	 * @throws NoSuchFinderWhereClauseEntryException if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public FinderWhereClauseEntry findByPrimaryKey(
			long finderWhereClauseEntryId)
		throws NoSuchFinderWhereClauseEntryException {

		return findByPrimaryKey((Serializable)finderWhereClauseEntryId);
	}

	/**
	 * Returns the finder where clause entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param finderWhereClauseEntryId the primary key of the finder where clause entry
	 * @return the finder where clause entry, or <code>null</code> if a finder where clause entry with the primary key could not be found
	 */
	@Override
	public FinderWhereClauseEntry fetchByPrimaryKey(
		long finderWhereClauseEntryId) {

		return fetchByPrimaryKey((Serializable)finderWhereClauseEntryId);
	}

	/**
	 * Returns all the finder where clause entries.
	 *
	 * @return the finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the finder where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FinderWhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of finder where clause entries
	 * @param end the upper bound of the range of finder where clause entries (not inclusive)
	 * @return the range of finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the finder where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FinderWhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of finder where clause entries
	 * @param end the upper bound of the range of finder where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findAll(
		int start, int end,
		OrderByComparator<FinderWhereClauseEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the finder where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FinderWhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of finder where clause entries
	 * @param end the upper bound of the range of finder where clause entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of finder where clause entries
	 */
	@Override
	public List<FinderWhereClauseEntry> findAll(
		int start, int end,
		OrderByComparator<FinderWhereClauseEntry> orderByComparator,
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

		List<FinderWhereClauseEntry> list = null;

		if (useFinderCache) {
			list = (List<FinderWhereClauseEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FINDERWHERECLAUSEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FINDERWHERECLAUSEENTRY;

				sql = sql.concat(FinderWhereClauseEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FinderWhereClauseEntry>)QueryUtil.list(
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
	 * Removes all the finder where clause entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FinderWhereClauseEntry finderWhereClauseEntry : findAll()) {
			remove(finderWhereClauseEntry);
		}
	}

	/**
	 * Returns the number of finder where clause entries.
	 *
	 * @return the number of finder where clause entries
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
					_SQL_COUNT_FINDERWHERECLAUSEENTRY);

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
		return "finderWhereClauseEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FINDERWHERECLAUSEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FinderWhereClauseEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the finder where clause entry persistence.
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

		_finderPathWithPaginationFindByName_Nickname = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName_Nickname",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"name"}, true);

		_finderPathWithoutPaginationFindByName_Nickname = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName_Nickname",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_finderPathCountByName_Nickname = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName_Nickname",
			new String[] {String.class.getName()}, new String[] {"name"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(FinderWhereClauseEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FINDERWHERECLAUSEENTRY =
		"SELECT finderWhereClauseEntry FROM FinderWhereClauseEntry finderWhereClauseEntry";

	private static final String _SQL_SELECT_FINDERWHERECLAUSEENTRY_WHERE =
		"SELECT finderWhereClauseEntry FROM FinderWhereClauseEntry finderWhereClauseEntry WHERE ";

	private static final String _SQL_COUNT_FINDERWHERECLAUSEENTRY =
		"SELECT COUNT(finderWhereClauseEntry) FROM FinderWhereClauseEntry finderWhereClauseEntry";

	private static final String _SQL_COUNT_FINDERWHERECLAUSEENTRY_WHERE =
		"SELECT COUNT(finderWhereClauseEntry) FROM FinderWhereClauseEntry finderWhereClauseEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"finderWhereClauseEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FinderWhereClauseEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FinderWhereClauseEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FinderWhereClauseEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}