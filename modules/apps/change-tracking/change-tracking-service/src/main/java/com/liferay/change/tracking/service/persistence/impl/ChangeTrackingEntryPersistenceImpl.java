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

package com.liferay.change.tracking.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.ChangeTrackingEntry;
import com.liferay.change.tracking.model.impl.ChangeTrackingEntryImpl;
import com.liferay.change.tracking.model.impl.ChangeTrackingEntryModelImpl;
import com.liferay.change.tracking.service.persistence.ChangeTrackingCollectionPersistence;
import com.liferay.change.tracking.service.persistence.ChangeTrackingEntryPersistence;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the change tracking entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeTrackingEntryPersistence
 * @see com.liferay.change.tracking.service.persistence.ChangeTrackingEntryUtil
 * @generated
 */
@ProviderType
public class ChangeTrackingEntryPersistenceImpl extends BasePersistenceImpl<ChangeTrackingEntry>
	implements ChangeTrackingEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ChangeTrackingEntryUtil} to access the change tracking entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ChangeTrackingEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangeTrackingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangeTrackingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangeTrackingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourcePrimKey",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangeTrackingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePrimKey",
			new String[] { Long.class.getName() },
			ChangeTrackingEntryModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY = new FinderPath(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByResourcePrimKey", new String[] { Long.class.getName() });

	/**
	 * Returns all the change tracking entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findByResourcePrimKey(long resourcePrimKey) {
		return findByResourcePrimKey(resourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the change tracking entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @return the range of matching change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end) {
		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the change tracking entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		return findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the change tracking entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] { resourcePrimKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] {
					resourcePrimKey,
					
					start, end, orderByComparator
				};
		}

		List<ChangeTrackingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangeTrackingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangeTrackingEntry changeTrackingEntry : list) {
					if ((resourcePrimKey != changeTrackingEntry.getResourcePrimKey())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CHANGETRACKINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangeTrackingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				if (!pagination) {
					list = (List<ChangeTrackingEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangeTrackingEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first change tracking entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching change tracking entry
	 * @throws NoSuchEntryException if a matching change tracking entry could not be found
	 */
	@Override
	public ChangeTrackingEntry findByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws NoSuchEntryException {
		ChangeTrackingEntry changeTrackingEntry = fetchByResourcePrimKey_First(resourcePrimKey,
				orderByComparator);

		if (changeTrackingEntry != null) {
			return changeTrackingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first change tracking entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching change tracking entry, or <code>null</code> if a matching change tracking entry could not be found
	 */
	@Override
	public ChangeTrackingEntry fetchByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		List<ChangeTrackingEntry> list = findByResourcePrimKey(resourcePrimKey,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last change tracking entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching change tracking entry
	 * @throws NoSuchEntryException if a matching change tracking entry could not be found
	 */
	@Override
	public ChangeTrackingEntry findByResourcePrimKey_Last(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws NoSuchEntryException {
		ChangeTrackingEntry changeTrackingEntry = fetchByResourcePrimKey_Last(resourcePrimKey,
				orderByComparator);

		if (changeTrackingEntry != null) {
			return changeTrackingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last change tracking entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching change tracking entry, or <code>null</code> if a matching change tracking entry could not be found
	 */
	@Override
	public ChangeTrackingEntry fetchByResourcePrimKey_Last(
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		int count = countByResourcePrimKey(resourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<ChangeTrackingEntry> list = findByResourcePrimKey(resourcePrimKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the change tracking entries before and after the current change tracking entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param changeTrackingEntryId the primary key of the current change tracking entry
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next change tracking entry
	 * @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	 */
	@Override
	public ChangeTrackingEntry[] findByResourcePrimKey_PrevAndNext(
		long changeTrackingEntryId, long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator)
		throws NoSuchEntryException {
		ChangeTrackingEntry changeTrackingEntry = findByPrimaryKey(changeTrackingEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangeTrackingEntry[] array = new ChangeTrackingEntryImpl[3];

			array[0] = getByResourcePrimKey_PrevAndNext(session,
					changeTrackingEntry, resourcePrimKey, orderByComparator,
					true);

			array[1] = changeTrackingEntry;

			array[2] = getByResourcePrimKey_PrevAndNext(session,
					changeTrackingEntry, resourcePrimKey, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangeTrackingEntry getByResourcePrimKey_PrevAndNext(
		Session session, ChangeTrackingEntry changeTrackingEntry,
		long resourcePrimKey,
		OrderByComparator<ChangeTrackingEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CHANGETRACKINGENTRY_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ChangeTrackingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changeTrackingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangeTrackingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the change tracking entries where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByResourcePrimKey(long resourcePrimKey) {
		for (ChangeTrackingEntry changeTrackingEntry : findByResourcePrimKey(
				resourcePrimKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changeTrackingEntry);
		}
	}

	/**
	 * Returns the number of change tracking entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching change tracking entries
	 */
	@Override
	public int countByResourcePrimKey(long resourcePrimKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY;

		Object[] finderArgs = new Object[] { resourcePrimKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHANGETRACKINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2 =
		"changeTrackingEntry.resourcePrimKey = ?";

	public ChangeTrackingEntryPersistenceImpl() {
		setModelClass(ChangeTrackingEntry.class);

		setModelImplClass(ChangeTrackingEntryImpl.class);
		setEntityCacheEnabled(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the change tracking entry in the entity cache if it is enabled.
	 *
	 * @param changeTrackingEntry the change tracking entry
	 */
	@Override
	public void cacheResult(ChangeTrackingEntry changeTrackingEntry) {
		entityCache.putResult(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryImpl.class, changeTrackingEntry.getPrimaryKey(),
			changeTrackingEntry);

		changeTrackingEntry.resetOriginalValues();
	}

	/**
	 * Caches the change tracking entries in the entity cache if it is enabled.
	 *
	 * @param changeTrackingEntries the change tracking entries
	 */
	@Override
	public void cacheResult(List<ChangeTrackingEntry> changeTrackingEntries) {
		for (ChangeTrackingEntry changeTrackingEntry : changeTrackingEntries) {
			if (entityCache.getResult(
						ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
						ChangeTrackingEntryImpl.class,
						changeTrackingEntry.getPrimaryKey()) == null) {
				cacheResult(changeTrackingEntry);
			}
			else {
				changeTrackingEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all change tracking entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangeTrackingEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the change tracking entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangeTrackingEntry changeTrackingEntry) {
		entityCache.removeResult(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryImpl.class, changeTrackingEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ChangeTrackingEntry> changeTrackingEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ChangeTrackingEntry changeTrackingEntry : changeTrackingEntries) {
			entityCache.removeResult(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
				ChangeTrackingEntryImpl.class,
				changeTrackingEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new change tracking entry with the primary key. Does not add the change tracking entry to the database.
	 *
	 * @param changeTrackingEntryId the primary key for the new change tracking entry
	 * @return the new change tracking entry
	 */
	@Override
	public ChangeTrackingEntry create(long changeTrackingEntryId) {
		ChangeTrackingEntry changeTrackingEntry = new ChangeTrackingEntryImpl();

		changeTrackingEntry.setNew(true);
		changeTrackingEntry.setPrimaryKey(changeTrackingEntryId);

		changeTrackingEntry.setCompanyId(companyProvider.getCompanyId());

		return changeTrackingEntry;
	}

	/**
	 * Removes the change tracking entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changeTrackingEntryId the primary key of the change tracking entry
	 * @return the change tracking entry that was removed
	 * @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	 */
	@Override
	public ChangeTrackingEntry remove(long changeTrackingEntryId)
		throws NoSuchEntryException {
		return remove((Serializable)changeTrackingEntryId);
	}

	/**
	 * Removes the change tracking entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the change tracking entry
	 * @return the change tracking entry that was removed
	 * @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	 */
	@Override
	public ChangeTrackingEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			ChangeTrackingEntry changeTrackingEntry = (ChangeTrackingEntry)session.get(ChangeTrackingEntryImpl.class,
					primaryKey);

			if (changeTrackingEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(changeTrackingEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ChangeTrackingEntry removeImpl(
		ChangeTrackingEntry changeTrackingEntry) {
		changeTrackingEntryToChangeTrackingCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(changeTrackingEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changeTrackingEntry)) {
				changeTrackingEntry = (ChangeTrackingEntry)session.get(ChangeTrackingEntryImpl.class,
						changeTrackingEntry.getPrimaryKeyObj());
			}

			if (changeTrackingEntry != null) {
				session.delete(changeTrackingEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (changeTrackingEntry != null) {
			clearCache(changeTrackingEntry);
		}

		return changeTrackingEntry;
	}

	@Override
	public ChangeTrackingEntry updateImpl(
		ChangeTrackingEntry changeTrackingEntry) {
		boolean isNew = changeTrackingEntry.isNew();

		if (!(changeTrackingEntry instanceof ChangeTrackingEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changeTrackingEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(changeTrackingEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changeTrackingEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangeTrackingEntry implementation " +
				changeTrackingEntry.getClass());
		}

		ChangeTrackingEntryModelImpl changeTrackingEntryModelImpl = (ChangeTrackingEntryModelImpl)changeTrackingEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (changeTrackingEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				changeTrackingEntry.setCreateDate(now);
			}
			else {
				changeTrackingEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!changeTrackingEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changeTrackingEntry.setModifiedDate(now);
			}
			else {
				changeTrackingEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (changeTrackingEntry.isNew()) {
				session.save(changeTrackingEntry);

				changeTrackingEntry.setNew(false);
			}
			else {
				changeTrackingEntry = (ChangeTrackingEntry)session.merge(changeTrackingEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ChangeTrackingEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					changeTrackingEntryModelImpl.getResourcePrimKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((changeTrackingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changeTrackingEntryModelImpl.getOriginalResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);

				args = new Object[] {
						changeTrackingEntryModelImpl.getResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);
			}
		}

		entityCache.putResult(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeTrackingEntryImpl.class, changeTrackingEntry.getPrimaryKey(),
			changeTrackingEntry, false);

		changeTrackingEntry.resetOriginalValues();

		return changeTrackingEntry;
	}

	/**
	 * Returns the change tracking entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the change tracking entry
	 * @return the change tracking entry
	 * @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	 */
	@Override
	public ChangeTrackingEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {
		ChangeTrackingEntry changeTrackingEntry = fetchByPrimaryKey(primaryKey);

		if (changeTrackingEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return changeTrackingEntry;
	}

	/**
	 * Returns the change tracking entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param changeTrackingEntryId the primary key of the change tracking entry
	 * @return the change tracking entry
	 * @throws NoSuchEntryException if a change tracking entry with the primary key could not be found
	 */
	@Override
	public ChangeTrackingEntry findByPrimaryKey(long changeTrackingEntryId)
		throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)changeTrackingEntryId);
	}

	/**
	 * Returns the change tracking entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changeTrackingEntryId the primary key of the change tracking entry
	 * @return the change tracking entry, or <code>null</code> if a change tracking entry with the primary key could not be found
	 */
	@Override
	public ChangeTrackingEntry fetchByPrimaryKey(long changeTrackingEntryId) {
		return fetchByPrimaryKey((Serializable)changeTrackingEntryId);
	}

	@Override
	public Map<Serializable, ChangeTrackingEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ChangeTrackingEntry> map = new HashMap<Serializable, ChangeTrackingEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ChangeTrackingEntry changeTrackingEntry = fetchByPrimaryKey(primaryKey);

			if (changeTrackingEntry != null) {
				map.put(primaryKey, changeTrackingEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangeTrackingEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ChangeTrackingEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CHANGETRACKINGENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ChangeTrackingEntry changeTrackingEntry : (List<ChangeTrackingEntry>)q.list()) {
				map.put(changeTrackingEntry.getPrimaryKeyObj(),
					changeTrackingEntry);

				cacheResult(changeTrackingEntry);

				uncachedPrimaryKeys.remove(changeTrackingEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ChangeTrackingEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangeTrackingEntryImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the change tracking entries.
	 *
	 * @return the change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the change tracking entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @return the range of change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the change tracking entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findAll(int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the change tracking entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of change tracking entries
	 */
	@Override
	public List<ChangeTrackingEntry> findAll(int start, int end,
		OrderByComparator<ChangeTrackingEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ChangeTrackingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangeTrackingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CHANGETRACKINGENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGETRACKINGENTRY;

				if (pagination) {
					sql = sql.concat(ChangeTrackingEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ChangeTrackingEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangeTrackingEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the change tracking entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangeTrackingEntry changeTrackingEntry : findAll()) {
			remove(changeTrackingEntry);
		}
	}

	/**
	 * Returns the number of change tracking entries.
	 *
	 * @return the number of change tracking entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHANGETRACKINGENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of change tracking collections associated with the change tracking entry.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @return long[] of the primaryKeys of change tracking collections associated with the change tracking entry
	 */
	@Override
	public long[] getChangeTrackingCollectionPrimaryKeys(long pk) {
		long[] pks = changeTrackingEntryToChangeTrackingCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the change tracking collections associated with the change tracking entry.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @return the change tracking collections associated with the change tracking entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk) {
		return getChangeTrackingCollections(pk, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the change tracking collections associated with the change tracking entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @return the range of change tracking collections associated with the change tracking entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk, int start, int end) {
		return getChangeTrackingCollections(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the change tracking collections associated with the change tracking entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeTrackingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param start the lower bound of the range of change tracking entries
	 * @param end the upper bound of the range of change tracking entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change tracking collections associated with the change tracking entry
	 */
	@Override
	public List<com.liferay.change.tracking.model.ChangeTrackingCollection> getChangeTrackingCollections(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.model.ChangeTrackingCollection> orderByComparator) {
		return changeTrackingEntryToChangeTrackingCollectionTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of change tracking collections associated with the change tracking entry.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @return the number of change tracking collections associated with the change tracking entry
	 */
	@Override
	public int getChangeTrackingCollectionsSize(long pk) {
		long[] pks = changeTrackingEntryToChangeTrackingCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the change tracking collection is associated with the change tracking entry.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollectionPK the primary key of the change tracking collection
	 * @return <code>true</code> if the change tracking collection is associated with the change tracking entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK) {
		return changeTrackingEntryToChangeTrackingCollectionTableMapper.containsTableMapping(pk,
			changeTrackingCollectionPK);
	}

	/**
	 * Returns <code>true</code> if the change tracking entry has any change tracking collections associated with it.
	 *
	 * @param pk the primary key of the change tracking entry to check for associations with change tracking collections
	 * @return <code>true</code> if the change tracking entry has any change tracking collections associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeTrackingCollections(long pk) {
		if (getChangeTrackingCollectionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollectionPK the primary key of the change tracking collection
	 */
	@Override
	public void addChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK) {
		ChangeTrackingEntry changeTrackingEntry = fetchByPrimaryKey(pk);

		if (changeTrackingEntry == null) {
			changeTrackingEntryToChangeTrackingCollectionTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeTrackingCollectionPK);
		}
		else {
			changeTrackingEntryToChangeTrackingCollectionTableMapper.addTableMapping(changeTrackingEntry.getCompanyId(),
				pk, changeTrackingCollectionPK);
		}
	}

	/**
	 * Adds an association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollection the change tracking collection
	 */
	@Override
	public void addChangeTrackingCollection(long pk,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		ChangeTrackingEntry changeTrackingEntry = fetchByPrimaryKey(pk);

		if (changeTrackingEntry == null) {
			changeTrackingEntryToChangeTrackingCollectionTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeTrackingCollection.getPrimaryKey());
		}
		else {
			changeTrackingEntryToChangeTrackingCollectionTableMapper.addTableMapping(changeTrackingEntry.getCompanyId(),
				pk, changeTrackingCollection.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollectionPKs the primary keys of the change tracking collections
	 */
	@Override
	public void addChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs) {
		long companyId = 0;

		ChangeTrackingEntry changeTrackingEntry = fetchByPrimaryKey(pk);

		if (changeTrackingEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeTrackingEntry.getCompanyId();
		}

		changeTrackingEntryToChangeTrackingCollectionTableMapper.addTableMappings(companyId,
			pk, changeTrackingCollectionPKs);
	}

	/**
	 * Adds an association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollections the change tracking collections
	 */
	@Override
	public void addChangeTrackingCollections(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		addChangeTrackingCollections(pk,
			ListUtil.toLongArray(changeTrackingCollections,
				com.liferay.change.tracking.model.ChangeTrackingCollection.CHANGE_TRACKING_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the change tracking entry and its change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry to clear the associated change tracking collections from
	 */
	@Override
	public void clearChangeTrackingCollections(long pk) {
		changeTrackingEntryToChangeTrackingCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollectionPK the primary key of the change tracking collection
	 */
	@Override
	public void removeChangeTrackingCollection(long pk,
		long changeTrackingCollectionPK) {
		changeTrackingEntryToChangeTrackingCollectionTableMapper.deleteTableMapping(pk,
			changeTrackingCollectionPK);
	}

	/**
	 * Removes the association between the change tracking entry and the change tracking collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollection the change tracking collection
	 */
	@Override
	public void removeChangeTrackingCollection(long pk,
		com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection) {
		changeTrackingEntryToChangeTrackingCollectionTableMapper.deleteTableMapping(pk,
			changeTrackingCollection.getPrimaryKey());
	}

	/**
	 * Removes the association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollectionPKs the primary keys of the change tracking collections
	 */
	@Override
	public void removeChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs) {
		changeTrackingEntryToChangeTrackingCollectionTableMapper.deleteTableMappings(pk,
			changeTrackingCollectionPKs);
	}

	/**
	 * Removes the association between the change tracking entry and the change tracking collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollections the change tracking collections
	 */
	@Override
	public void removeChangeTrackingCollections(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		removeChangeTrackingCollections(pk,
			ListUtil.toLongArray(changeTrackingCollections,
				com.liferay.change.tracking.model.ChangeTrackingCollection.CHANGE_TRACKING_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Sets the change tracking collections associated with the change tracking entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollectionPKs the primary keys of the change tracking collections to be associated with the change tracking entry
	 */
	@Override
	public void setChangeTrackingCollections(long pk,
		long[] changeTrackingCollectionPKs) {
		Set<Long> newChangeTrackingCollectionPKsSet = SetUtil.fromArray(changeTrackingCollectionPKs);
		Set<Long> oldChangeTrackingCollectionPKsSet = SetUtil.fromArray(changeTrackingEntryToChangeTrackingCollectionTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeChangeTrackingCollectionPKsSet = new HashSet<Long>(oldChangeTrackingCollectionPKsSet);

		removeChangeTrackingCollectionPKsSet.removeAll(newChangeTrackingCollectionPKsSet);

		changeTrackingEntryToChangeTrackingCollectionTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeChangeTrackingCollectionPKsSet));

		newChangeTrackingCollectionPKsSet.removeAll(oldChangeTrackingCollectionPKsSet);

		long companyId = 0;

		ChangeTrackingEntry changeTrackingEntry = fetchByPrimaryKey(pk);

		if (changeTrackingEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeTrackingEntry.getCompanyId();
		}

		changeTrackingEntryToChangeTrackingCollectionTableMapper.addTableMappings(companyId,
			pk, ArrayUtil.toLongArray(newChangeTrackingCollectionPKsSet));
	}

	/**
	 * Sets the change tracking collections associated with the change tracking entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change tracking entry
	 * @param changeTrackingCollections the change tracking collections to be associated with the change tracking entry
	 */
	@Override
	public void setChangeTrackingCollections(long pk,
		List<com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingCollections) {
		try {
			long[] changeTrackingCollectionPKs = new long[changeTrackingCollections.size()];

			for (int i = 0; i < changeTrackingCollections.size(); i++) {
				com.liferay.change.tracking.model.ChangeTrackingCollection changeTrackingCollection =
					changeTrackingCollections.get(i);

				changeTrackingCollectionPKs[i] = changeTrackingCollection.getPrimaryKey();
			}

			setChangeTrackingCollections(pk, changeTrackingCollectionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ChangeTrackingEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the change tracking entry persistence.
	 */
	public void afterPropertiesSet() {
		changeTrackingEntryToChangeTrackingCollectionTableMapper = TableMapperFactory.getTableMapper("Collections_Entries",
				"companyId", "changeTrackingEntryId",
				"changeTrackingCollectionId", this,
				changeTrackingCollectionPersistence);
	}

	public void destroy() {
		entityCache.removeCache(ChangeTrackingEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("Collections_Entries");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	@BeanReference(type = ChangeTrackingCollectionPersistence.class)
	protected ChangeTrackingCollectionPersistence changeTrackingCollectionPersistence;
	protected TableMapper<ChangeTrackingEntry, com.liferay.change.tracking.model.ChangeTrackingCollection> changeTrackingEntryToChangeTrackingCollectionTableMapper;
	private static final String _SQL_SELECT_CHANGETRACKINGENTRY = "SELECT changeTrackingEntry FROM ChangeTrackingEntry changeTrackingEntry";
	private static final String _SQL_SELECT_CHANGETRACKINGENTRY_WHERE_PKS_IN = "SELECT changeTrackingEntry FROM ChangeTrackingEntry changeTrackingEntry WHERE changeTrackingEntryId IN (";
	private static final String _SQL_SELECT_CHANGETRACKINGENTRY_WHERE = "SELECT changeTrackingEntry FROM ChangeTrackingEntry changeTrackingEntry WHERE ";
	private static final String _SQL_COUNT_CHANGETRACKINGENTRY = "SELECT COUNT(changeTrackingEntry) FROM ChangeTrackingEntry changeTrackingEntry";
	private static final String _SQL_COUNT_CHANGETRACKINGENTRY_WHERE = "SELECT COUNT(changeTrackingEntry) FROM ChangeTrackingEntry changeTrackingEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changeTrackingEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangeTrackingEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ChangeTrackingEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ChangeTrackingEntryPersistenceImpl.class);
}