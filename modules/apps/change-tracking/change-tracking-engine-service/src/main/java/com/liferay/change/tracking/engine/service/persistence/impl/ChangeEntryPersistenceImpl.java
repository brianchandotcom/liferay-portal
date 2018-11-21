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

package com.liferay.change.tracking.engine.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.exception.NoSuchChangeEntryException;
import com.liferay.change.tracking.engine.model.ChangeEntry;
import com.liferay.change.tracking.engine.model.impl.ChangeEntryImpl;
import com.liferay.change.tracking.engine.model.impl.ChangeEntryModelImpl;
import com.liferay.change.tracking.engine.service.persistence.ChangeCollectionPersistence;
import com.liferay.change.tracking.engine.service.persistence.ChangeEntryPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
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
 * The persistence implementation for the change entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangeEntryPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.ChangeEntryUtil
 * @generated
 */
@ProviderType
public class ChangeEntryPersistenceImpl extends BasePersistenceImpl<ChangeEntry>
	implements ChangeEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ChangeEntryUtil} to access the change entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ChangeEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryModelImpl.FINDER_CACHE_ENABLED, ChangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryModelImpl.FINDER_CACHE_ENABLED, ChangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryModelImpl.FINDER_CACHE_ENABLED, ChangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourcePrimKey",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryModelImpl.FINDER_CACHE_ENABLED, ChangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePrimKey",
			new String[] { Long.class.getName() },
			ChangeEntryModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY = new FinderPath(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByResourcePrimKey", new String[] { Long.class.getName() });

	/**
	 * Returns all the change entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching change entries
	 */
	@Override
	public List<ChangeEntry> findByResourcePrimKey(long resourcePrimKey) {
		return findByResourcePrimKey(resourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the change entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @return the range of matching change entries
	 */
	@Override
	public List<ChangeEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end) {
		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the change entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching change entries
	 */
	@Override
	public List<ChangeEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<ChangeEntry> orderByComparator) {
		return findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the change entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching change entries
	 */
	@Override
	public List<ChangeEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<ChangeEntry> orderByComparator,
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

		List<ChangeEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangeEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangeEntry changeEntry : list) {
					if ((resourcePrimKey != changeEntry.getResourcePrimKey())) {
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

			query.append(_SQL_SELECT_CHANGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangeEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				if (!pagination) {
					list = (List<ChangeEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangeEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first change entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching change entry
	 * @throws NoSuchChangeEntryException if a matching change entry could not be found
	 */
	@Override
	public ChangeEntry findByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<ChangeEntry> orderByComparator)
		throws NoSuchChangeEntryException {
		ChangeEntry changeEntry = fetchByResourcePrimKey_First(resourcePrimKey,
				orderByComparator);

		if (changeEntry != null) {
			return changeEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchChangeEntryException(msg.toString());
	}

	/**
	 * Returns the first change entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching change entry, or <code>null</code> if a matching change entry could not be found
	 */
	@Override
	public ChangeEntry fetchByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<ChangeEntry> orderByComparator) {
		List<ChangeEntry> list = findByResourcePrimKey(resourcePrimKey, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last change entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching change entry
	 * @throws NoSuchChangeEntryException if a matching change entry could not be found
	 */
	@Override
	public ChangeEntry findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<ChangeEntry> orderByComparator)
		throws NoSuchChangeEntryException {
		ChangeEntry changeEntry = fetchByResourcePrimKey_Last(resourcePrimKey,
				orderByComparator);

		if (changeEntry != null) {
			return changeEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchChangeEntryException(msg.toString());
	}

	/**
	 * Returns the last change entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching change entry, or <code>null</code> if a matching change entry could not be found
	 */
	@Override
	public ChangeEntry fetchByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<ChangeEntry> orderByComparator) {
		int count = countByResourcePrimKey(resourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<ChangeEntry> list = findByResourcePrimKey(resourcePrimKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the change entries before and after the current change entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param changeEntryId the primary key of the current change entry
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next change entry
	 * @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	 */
	@Override
	public ChangeEntry[] findByResourcePrimKey_PrevAndNext(long changeEntryId,
		long resourcePrimKey, OrderByComparator<ChangeEntry> orderByComparator)
		throws NoSuchChangeEntryException {
		ChangeEntry changeEntry = findByPrimaryKey(changeEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangeEntry[] array = new ChangeEntryImpl[3];

			array[0] = getByResourcePrimKey_PrevAndNext(session, changeEntry,
					resourcePrimKey, orderByComparator, true);

			array[1] = changeEntry;

			array[2] = getByResourcePrimKey_PrevAndNext(session, changeEntry,
					resourcePrimKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangeEntry getByResourcePrimKey_PrevAndNext(Session session,
		ChangeEntry changeEntry, long resourcePrimKey,
		OrderByComparator<ChangeEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CHANGEENTRY_WHERE);

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
			query.append(ChangeEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changeEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangeEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the change entries where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByResourcePrimKey(long resourcePrimKey) {
		for (ChangeEntry changeEntry : findByResourcePrimKey(resourcePrimKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changeEntry);
		}
	}

	/**
	 * Returns the number of change entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching change entries
	 */
	@Override
	public int countByResourcePrimKey(long resourcePrimKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY;

		Object[] finderArgs = new Object[] { resourcePrimKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHANGEENTRY_WHERE);

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
		"changeEntry.resourcePrimKey = ?";

	public ChangeEntryPersistenceImpl() {
		setModelClass(ChangeEntry.class);
	}

	/**
	 * Caches the change entry in the entity cache if it is enabled.
	 *
	 * @param changeEntry the change entry
	 */
	@Override
	public void cacheResult(ChangeEntry changeEntry) {
		entityCache.putResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryImpl.class, changeEntry.getPrimaryKey(), changeEntry);

		changeEntry.resetOriginalValues();
	}

	/**
	 * Caches the change entries in the entity cache if it is enabled.
	 *
	 * @param changeEntries the change entries
	 */
	@Override
	public void cacheResult(List<ChangeEntry> changeEntries) {
		for (ChangeEntry changeEntry : changeEntries) {
			if (entityCache.getResult(
						ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
						ChangeEntryImpl.class, changeEntry.getPrimaryKey()) == null) {
				cacheResult(changeEntry);
			}
			else {
				changeEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all change entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangeEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the change entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangeEntry changeEntry) {
		entityCache.removeResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryImpl.class, changeEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ChangeEntry> changeEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ChangeEntry changeEntry : changeEntries) {
			entityCache.removeResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
				ChangeEntryImpl.class, changeEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new change entry with the primary key. Does not add the change entry to the database.
	 *
	 * @param changeEntryId the primary key for the new change entry
	 * @return the new change entry
	 */
	@Override
	public ChangeEntry create(long changeEntryId) {
		ChangeEntry changeEntry = new ChangeEntryImpl();

		changeEntry.setNew(true);
		changeEntry.setPrimaryKey(changeEntryId);

		changeEntry.setCompanyId(companyProvider.getCompanyId());

		return changeEntry;
	}

	/**
	 * Removes the change entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changeEntryId the primary key of the change entry
	 * @return the change entry that was removed
	 * @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	 */
	@Override
	public ChangeEntry remove(long changeEntryId)
		throws NoSuchChangeEntryException {
		return remove((Serializable)changeEntryId);
	}

	/**
	 * Removes the change entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the change entry
	 * @return the change entry that was removed
	 * @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	 */
	@Override
	public ChangeEntry remove(Serializable primaryKey)
		throws NoSuchChangeEntryException {
		Session session = null;

		try {
			session = openSession();

			ChangeEntry changeEntry = (ChangeEntry)session.get(ChangeEntryImpl.class,
					primaryKey);

			if (changeEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchChangeEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(changeEntry);
		}
		catch (NoSuchChangeEntryException nsee) {
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
	protected ChangeEntry removeImpl(ChangeEntry changeEntry) {
		changeEntryToChangeCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(changeEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changeEntry)) {
				changeEntry = (ChangeEntry)session.get(ChangeEntryImpl.class,
						changeEntry.getPrimaryKeyObj());
			}

			if (changeEntry != null) {
				session.delete(changeEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (changeEntry != null) {
			clearCache(changeEntry);
		}

		return changeEntry;
	}

	@Override
	public ChangeEntry updateImpl(ChangeEntry changeEntry) {
		boolean isNew = changeEntry.isNew();

		if (!(changeEntry instanceof ChangeEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changeEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(changeEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changeEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangeEntry implementation " +
				changeEntry.getClass());
		}

		ChangeEntryModelImpl changeEntryModelImpl = (ChangeEntryModelImpl)changeEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (changeEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				changeEntry.setCreateDate(now);
			}
			else {
				changeEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!changeEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changeEntry.setModifiedDate(now);
			}
			else {
				changeEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (changeEntry.isNew()) {
				session.save(changeEntry);

				changeEntry.setNew(false);
			}
			else {
				changeEntry = (ChangeEntry)session.merge(changeEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ChangeEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					changeEntryModelImpl.getResourcePrimKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((changeEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changeEntryModelImpl.getOriginalResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);

				args = new Object[] { changeEntryModelImpl.getResourcePrimKey() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);
			}
		}

		entityCache.putResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangeEntryImpl.class, changeEntry.getPrimaryKey(), changeEntry,
			false);

		changeEntry.resetOriginalValues();

		return changeEntry;
	}

	/**
	 * Returns the change entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the change entry
	 * @return the change entry
	 * @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	 */
	@Override
	public ChangeEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchChangeEntryException {
		ChangeEntry changeEntry = fetchByPrimaryKey(primaryKey);

		if (changeEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchChangeEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return changeEntry;
	}

	/**
	 * Returns the change entry with the primary key or throws a {@link NoSuchChangeEntryException} if it could not be found.
	 *
	 * @param changeEntryId the primary key of the change entry
	 * @return the change entry
	 * @throws NoSuchChangeEntryException if a change entry with the primary key could not be found
	 */
	@Override
	public ChangeEntry findByPrimaryKey(long changeEntryId)
		throws NoSuchChangeEntryException {
		return findByPrimaryKey((Serializable)changeEntryId);
	}

	/**
	 * Returns the change entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the change entry
	 * @return the change entry, or <code>null</code> if a change entry with the primary key could not be found
	 */
	@Override
	public ChangeEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
				ChangeEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ChangeEntry changeEntry = (ChangeEntry)serializable;

		if (changeEntry == null) {
			Session session = null;

			try {
				session = openSession();

				changeEntry = (ChangeEntry)session.get(ChangeEntryImpl.class,
						primaryKey);

				if (changeEntry != null) {
					cacheResult(changeEntry);
				}
				else {
					entityCache.putResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
						ChangeEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangeEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return changeEntry;
	}

	/**
	 * Returns the change entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changeEntryId the primary key of the change entry
	 * @return the change entry, or <code>null</code> if a change entry with the primary key could not be found
	 */
	@Override
	public ChangeEntry fetchByPrimaryKey(long changeEntryId) {
		return fetchByPrimaryKey((Serializable)changeEntryId);
	}

	@Override
	public Map<Serializable, ChangeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ChangeEntry> map = new HashMap<Serializable, ChangeEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ChangeEntry changeEntry = fetchByPrimaryKey(primaryKey);

			if (changeEntry != null) {
				map.put(primaryKey, changeEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangeEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ChangeEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CHANGEENTRY_WHERE_PKS_IN);

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

			for (ChangeEntry changeEntry : (List<ChangeEntry>)q.list()) {
				map.put(changeEntry.getPrimaryKeyObj(), changeEntry);

				cacheResult(changeEntry);

				uncachedPrimaryKeys.remove(changeEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ChangeEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangeEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the change entries.
	 *
	 * @return the change entries
	 */
	@Override
	public List<ChangeEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the change entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @return the range of change entries
	 */
	@Override
	public List<ChangeEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the change entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change entries
	 */
	@Override
	public List<ChangeEntry> findAll(int start, int end,
		OrderByComparator<ChangeEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the change entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of change entries
	 */
	@Override
	public List<ChangeEntry> findAll(int start, int end,
		OrderByComparator<ChangeEntry> orderByComparator,
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

		List<ChangeEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangeEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CHANGEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGEENTRY;

				if (pagination) {
					sql = sql.concat(ChangeEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ChangeEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangeEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the change entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangeEntry changeEntry : findAll()) {
			remove(changeEntry);
		}
	}

	/**
	 * Returns the number of change entries.
	 *
	 * @return the number of change entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHANGEENTRY);

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
	 * Returns the primaryKeys of change collections associated with the change entry.
	 *
	 * @param pk the primary key of the change entry
	 * @return long[] of the primaryKeys of change collections associated with the change entry
	 */
	@Override
	public long[] getChangeCollectionPrimaryKeys(long pk) {
		long[] pks = changeEntryToChangeCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the change collections associated with the change entry.
	 *
	 * @param pk the primary key of the change entry
	 * @return the change collections associated with the change entry
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk) {
		return getChangeCollections(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the change collections associated with the change entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change entry
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @return the range of change collections associated with the change entry
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk, int start, int end) {
		return getChangeCollections(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the change collections associated with the change entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the change entry
	 * @param start the lower bound of the range of change entries
	 * @param end the upper bound of the range of change entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of change collections associated with the change entry
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.ChangeCollection> getChangeCollections(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.ChangeCollection> orderByComparator) {
		return changeEntryToChangeCollectionTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of change collections associated with the change entry.
	 *
	 * @param pk the primary key of the change entry
	 * @return the number of change collections associated with the change entry
	 */
	@Override
	public int getChangeCollectionsSize(long pk) {
		long[] pks = changeEntryToChangeCollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the change collection is associated with the change entry.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollectionPK the primary key of the change collection
	 * @return <code>true</code> if the change collection is associated with the change entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeCollection(long pk, long changeCollectionPK) {
		return changeEntryToChangeCollectionTableMapper.containsTableMapping(pk,
			changeCollectionPK);
	}

	/**
	 * Returns <code>true</code> if the change entry has any change collections associated with it.
	 *
	 * @param pk the primary key of the change entry to check for associations with change collections
	 * @return <code>true</code> if the change entry has any change collections associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsChangeCollections(long pk) {
		if (getChangeCollectionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollectionPK the primary key of the change collection
	 */
	@Override
	public void addChangeCollection(long pk, long changeCollectionPK) {
		ChangeEntry changeEntry = fetchByPrimaryKey(pk);

		if (changeEntry == null) {
			changeEntryToChangeCollectionTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeCollectionPK);
		}
		else {
			changeEntryToChangeCollectionTableMapper.addTableMapping(changeEntry.getCompanyId(),
				pk, changeCollectionPK);
		}
	}

	/**
	 * Adds an association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollection the change collection
	 */
	@Override
	public void addChangeCollection(long pk,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		ChangeEntry changeEntry = fetchByPrimaryKey(pk);

		if (changeEntry == null) {
			changeEntryToChangeCollectionTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, changeCollection.getPrimaryKey());
		}
		else {
			changeEntryToChangeCollectionTableMapper.addTableMapping(changeEntry.getCompanyId(),
				pk, changeCollection.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollectionPKs the primary keys of the change collections
	 */
	@Override
	public void addChangeCollections(long pk, long[] changeCollectionPKs) {
		long companyId = 0;

		ChangeEntry changeEntry = fetchByPrimaryKey(pk);

		if (changeEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeEntry.getCompanyId();
		}

		changeEntryToChangeCollectionTableMapper.addTableMappings(companyId,
			pk, changeCollectionPKs);
	}

	/**
	 * Adds an association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollections the change collections
	 */
	@Override
	public void addChangeCollections(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		addChangeCollections(pk,
			ListUtil.toLongArray(changeCollections,
				com.liferay.change.tracking.engine.model.ChangeCollection.CHANGE_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the change entry and its change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry to clear the associated change collections from
	 */
	@Override
	public void clearChangeCollections(long pk) {
		changeEntryToChangeCollectionTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollectionPK the primary key of the change collection
	 */
	@Override
	public void removeChangeCollection(long pk, long changeCollectionPK) {
		changeEntryToChangeCollectionTableMapper.deleteTableMapping(pk,
			changeCollectionPK);
	}

	/**
	 * Removes the association between the change entry and the change collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollection the change collection
	 */
	@Override
	public void removeChangeCollection(long pk,
		com.liferay.change.tracking.engine.model.ChangeCollection changeCollection) {
		changeEntryToChangeCollectionTableMapper.deleteTableMapping(pk,
			changeCollection.getPrimaryKey());
	}

	/**
	 * Removes the association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollectionPKs the primary keys of the change collections
	 */
	@Override
	public void removeChangeCollections(long pk, long[] changeCollectionPKs) {
		changeEntryToChangeCollectionTableMapper.deleteTableMappings(pk,
			changeCollectionPKs);
	}

	/**
	 * Removes the association between the change entry and the change collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollections the change collections
	 */
	@Override
	public void removeChangeCollections(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		removeChangeCollections(pk,
			ListUtil.toLongArray(changeCollections,
				com.liferay.change.tracking.engine.model.ChangeCollection.CHANGE_COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Sets the change collections associated with the change entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollectionPKs the primary keys of the change collections to be associated with the change entry
	 */
	@Override
	public void setChangeCollections(long pk, long[] changeCollectionPKs) {
		Set<Long> newChangeCollectionPKsSet = SetUtil.fromArray(changeCollectionPKs);
		Set<Long> oldChangeCollectionPKsSet = SetUtil.fromArray(changeEntryToChangeCollectionTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeChangeCollectionPKsSet = new HashSet<Long>(oldChangeCollectionPKsSet);

		removeChangeCollectionPKsSet.removeAll(newChangeCollectionPKsSet);

		changeEntryToChangeCollectionTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeChangeCollectionPKsSet));

		newChangeCollectionPKsSet.removeAll(oldChangeCollectionPKsSet);

		long companyId = 0;

		ChangeEntry changeEntry = fetchByPrimaryKey(pk);

		if (changeEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = changeEntry.getCompanyId();
		}

		changeEntryToChangeCollectionTableMapper.addTableMappings(companyId,
			pk, ArrayUtil.toLongArray(newChangeCollectionPKsSet));
	}

	/**
	 * Sets the change collections associated with the change entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the change entry
	 * @param changeCollections the change collections to be associated with the change entry
	 */
	@Override
	public void setChangeCollections(long pk,
		List<com.liferay.change.tracking.engine.model.ChangeCollection> changeCollections) {
		try {
			long[] changeCollectionPKs = new long[changeCollections.size()];

			for (int i = 0; i < changeCollections.size(); i++) {
				com.liferay.change.tracking.engine.model.ChangeCollection changeCollection =
					changeCollections.get(i);

				changeCollectionPKs[i] = changeCollection.getPrimaryKey();
			}

			setChangeCollections(pk, changeCollectionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ChangeEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the change entry persistence.
	 */
	public void afterPropertiesSet() {
		changeEntryToChangeCollectionTableMapper = TableMapperFactory.getTableMapper("Collections_Entries",
				"companyId", "changeEntryId", "changeCollectionId", this,
				changeCollectionPersistence);
	}

	public void destroy() {
		entityCache.removeCache(ChangeEntryImpl.class.getName());
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
	@BeanReference(type = ChangeCollectionPersistence.class)
	protected ChangeCollectionPersistence changeCollectionPersistence;
	protected TableMapper<ChangeEntry, com.liferay.change.tracking.engine.model.ChangeCollection> changeEntryToChangeCollectionTableMapper;
	private static final String _SQL_SELECT_CHANGEENTRY = "SELECT changeEntry FROM ChangeEntry changeEntry";
	private static final String _SQL_SELECT_CHANGEENTRY_WHERE_PKS_IN = "SELECT changeEntry FROM ChangeEntry changeEntry WHERE changeEntryId IN (";
	private static final String _SQL_SELECT_CHANGEENTRY_WHERE = "SELECT changeEntry FROM ChangeEntry changeEntry WHERE ";
	private static final String _SQL_COUNT_CHANGEENTRY = "SELECT COUNT(changeEntry) FROM ChangeEntry changeEntry";
	private static final String _SQL_COUNT_CHANGEENTRY_WHERE = "SELECT COUNT(changeEntry) FROM ChangeEntry changeEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changeEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangeEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ChangeEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ChangeEntryPersistenceImpl.class);
}