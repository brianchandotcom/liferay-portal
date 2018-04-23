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

package com.liferay.commerce.cloud.client.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cloud.client.exception.NoSuchCloudOrderForecastSyncException;
import com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync;
import com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncImpl;
import com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncModelImpl;
import com.liferay.commerce.cloud.client.service.persistence.CommerceCloudOrderForecastSyncPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce cloud order forecast sync service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudOrderForecastSyncPersistence
 * @see com.liferay.commerce.cloud.client.service.persistence.CommerceCloudOrderForecastSyncUtil
 * @generated
 */
@ProviderType
public class CommerceCloudOrderForecastSyncPersistenceImpl
	extends BasePersistenceImpl<CommerceCloudOrderForecastSync>
	implements CommerceCloudOrderForecastSyncPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceCloudOrderForecastSyncUtil} to access the commerce cloud order forecast sync persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceCloudOrderForecastSyncImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_COMMERCEORDERID = new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCommerceOrderId", new String[] { Long.class.getName() },
			CommerceCloudOrderForecastSyncModelImpl.COMMERCEORDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEORDERID = new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderId", new String[] { Long.class.getName() });

	/**
	 * Returns the commerce cloud order forecast sync where commerceOrderId = &#63; or throws a {@link NoSuchCloudOrderForecastSyncException} if it could not be found.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce cloud order forecast sync
	 * @throws NoSuchCloudOrderForecastSyncException if a matching commerce cloud order forecast sync could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync findByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudOrderForecastSyncException {
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = fetchByCommerceOrderId(commerceOrderId);

		if (commerceCloudOrderForecastSync == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commerceOrderId=");
			msg.append(commerceOrderId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCloudOrderForecastSyncException(msg.toString());
		}

		return commerceCloudOrderForecastSync;
	}

	/**
	 * Returns the commerce cloud order forecast sync where commerceOrderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync fetchByCommerceOrderId(
		long commerceOrderId) {
		return fetchByCommerceOrderId(commerceOrderId, true);
	}

	/**
	 * Returns the commerce cloud order forecast sync where commerceOrderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync fetchByCommerceOrderId(
		long commerceOrderId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { commerceOrderId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
					finderArgs, this);
		}

		if (result instanceof CommerceCloudOrderForecastSync) {
			CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = (CommerceCloudOrderForecastSync)result;

			if ((commerceOrderId != commerceCloudOrderForecastSync.getCommerceOrderId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceOrderId);

				List<CommerceCloudOrderForecastSync> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
						finderArgs, list);
				}
				else {
					CommerceCloudOrderForecastSync commerceCloudOrderForecastSync =
						list.get(0);

					result = commerceCloudOrderForecastSync;

					cacheResult(commerceCloudOrderForecastSync);

					if ((commerceCloudOrderForecastSync.getCommerceOrderId() != commerceOrderId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
							finderArgs, commerceCloudOrderForecastSync);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CommerceCloudOrderForecastSync)result;
		}
	}

	/**
	 * Removes the commerce cloud order forecast sync where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the commerce cloud order forecast sync that was removed
	 */
	@Override
	public CommerceCloudOrderForecastSync removeByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudOrderForecastSyncException {
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = findByCommerceOrderId(commerceOrderId);

		return remove(commerceCloudOrderForecastSync);
	}

	/**
	 * Returns the number of commerce cloud order forecast syncs where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce cloud order forecast syncs
	 */
	@Override
	public int countByCommerceOrderId(long commerceOrderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEORDERID;

		Object[] finderArgs = new Object[] { commerceOrderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECLOUDORDERFORECASTSYNC_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceOrderId);

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

	private static final String _FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2 =
		"commerceCloudOrderForecastSync.commerceOrderId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SYNCDATE = new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySyncDate",
			new String[] {
				Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE =
		new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySyncDate",
			new String[] { Date.class.getName() },
			CommerceCloudOrderForecastSyncModelImpl.SYNCDATE_COLUMN_BITMASK |
			CommerceCloudOrderForecastSyncModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SYNCDATE = new FinderPath(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySyncDate", new String[] { Date.class.getName() });

	/**
	 * Returns all the commerce cloud order forecast syncs where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @return the matching commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findBySyncDate(Date syncDate) {
		return findBySyncDate(syncDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce cloud order forecast syncs where syncDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param syncDate the sync date
	 * @param start the lower bound of the range of commerce cloud order forecast syncs
	 * @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	 * @return the range of matching commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findBySyncDate(Date syncDate,
		int start, int end) {
		return findBySyncDate(syncDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cloud order forecast syncs where syncDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param syncDate the sync date
	 * @param start the lower bound of the range of commerce cloud order forecast syncs
	 * @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findBySyncDate(Date syncDate,
		int start, int end,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator) {
		return findBySyncDate(syncDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cloud order forecast syncs where syncDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param syncDate the sync date
	 * @param start the lower bound of the range of commerce cloud order forecast syncs
	 * @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findBySyncDate(Date syncDate,
		int start, int end,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE;
			finderArgs = new Object[] { syncDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SYNCDATE;
			finderArgs = new Object[] { syncDate, start, end, orderByComparator };
		}

		List<CommerceCloudOrderForecastSync> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCloudOrderForecastSync>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync : list) {
					if (!Objects.equals(syncDate,
								commerceCloudOrderForecastSync.getSyncDate())) {
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

			query.append(_SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC_WHERE);

			boolean bindSyncDate = false;

			if (syncDate == null) {
				query.append(_FINDER_COLUMN_SYNCDATE_SYNCDATE_1);
			}
			else {
				bindSyncDate = true;

				query.append(_FINDER_COLUMN_SYNCDATE_SYNCDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCloudOrderForecastSyncModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSyncDate) {
					qPos.add(new Timestamp(syncDate.getTime()));
				}

				if (!pagination) {
					list = (List<CommerceCloudOrderForecastSync>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCloudOrderForecastSync>)QueryUtil.list(q,
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
	 * Returns the first commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cloud order forecast sync
	 * @throws NoSuchCloudOrderForecastSyncException if a matching commerce cloud order forecast sync could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync findBySyncDate_First(Date syncDate,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator)
		throws NoSuchCloudOrderForecastSyncException {
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = fetchBySyncDate_First(syncDate,
				orderByComparator);

		if (commerceCloudOrderForecastSync != null) {
			return commerceCloudOrderForecastSync;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("syncDate=");
		msg.append(syncDate);

		msg.append("}");

		throw new NoSuchCloudOrderForecastSyncException(msg.toString());
	}

	/**
	 * Returns the first commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync fetchBySyncDate_First(Date syncDate,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator) {
		List<CommerceCloudOrderForecastSync> list = findBySyncDate(syncDate, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cloud order forecast sync
	 * @throws NoSuchCloudOrderForecastSyncException if a matching commerce cloud order forecast sync could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync findBySyncDate_Last(Date syncDate,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator)
		throws NoSuchCloudOrderForecastSyncException {
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = fetchBySyncDate_Last(syncDate,
				orderByComparator);

		if (commerceCloudOrderForecastSync != null) {
			return commerceCloudOrderForecastSync;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("syncDate=");
		msg.append(syncDate);

		msg.append("}");

		throw new NoSuchCloudOrderForecastSyncException(msg.toString());
	}

	/**
	 * Returns the last commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cloud order forecast sync, or <code>null</code> if a matching commerce cloud order forecast sync could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync fetchBySyncDate_Last(Date syncDate,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator) {
		int count = countBySyncDate(syncDate);

		if (count == 0) {
			return null;
		}

		List<CommerceCloudOrderForecastSync> list = findBySyncDate(syncDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce cloud order forecast syncs before and after the current commerce cloud order forecast sync in the ordered set where syncDate = &#63;.
	 *
	 * @param commerceCloudOrderForecastSyncId the primary key of the current commerce cloud order forecast sync
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cloud order forecast sync
	 * @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync[] findBySyncDate_PrevAndNext(
		long commerceCloudOrderForecastSyncId, Date syncDate,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator)
		throws NoSuchCloudOrderForecastSyncException {
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = findByPrimaryKey(commerceCloudOrderForecastSyncId);

		Session session = null;

		try {
			session = openSession();

			CommerceCloudOrderForecastSync[] array = new CommerceCloudOrderForecastSyncImpl[3];

			array[0] = getBySyncDate_PrevAndNext(session,
					commerceCloudOrderForecastSync, syncDate,
					orderByComparator, true);

			array[1] = commerceCloudOrderForecastSync;

			array[2] = getBySyncDate_PrevAndNext(session,
					commerceCloudOrderForecastSync, syncDate,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCloudOrderForecastSync getBySyncDate_PrevAndNext(
		Session session,
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync,
		Date syncDate,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC_WHERE);

		boolean bindSyncDate = false;

		if (syncDate == null) {
			query.append(_FINDER_COLUMN_SYNCDATE_SYNCDATE_1);
		}
		else {
			bindSyncDate = true;

			query.append(_FINDER_COLUMN_SYNCDATE_SYNCDATE_2);
		}

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
			query.append(CommerceCloudOrderForecastSyncModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSyncDate) {
			qPos.add(new Timestamp(syncDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCloudOrderForecastSync);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCloudOrderForecastSync> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce cloud order forecast syncs where syncDate = &#63; from the database.
	 *
	 * @param syncDate the sync date
	 */
	@Override
	public void removeBySyncDate(Date syncDate) {
		for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync : findBySyncDate(
				syncDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCloudOrderForecastSync);
		}
	}

	/**
	 * Returns the number of commerce cloud order forecast syncs where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @return the number of matching commerce cloud order forecast syncs
	 */
	@Override
	public int countBySyncDate(Date syncDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SYNCDATE;

		Object[] finderArgs = new Object[] { syncDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECLOUDORDERFORECASTSYNC_WHERE);

			boolean bindSyncDate = false;

			if (syncDate == null) {
				query.append(_FINDER_COLUMN_SYNCDATE_SYNCDATE_1);
			}
			else {
				bindSyncDate = true;

				query.append(_FINDER_COLUMN_SYNCDATE_SYNCDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSyncDate) {
					qPos.add(new Timestamp(syncDate.getTime()));
				}

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

	private static final String _FINDER_COLUMN_SYNCDATE_SYNCDATE_1 = "commerceCloudOrderForecastSync.syncDate IS NULL";
	private static final String _FINDER_COLUMN_SYNCDATE_SYNCDATE_2 = "commerceCloudOrderForecastSync.syncDate = ?";

	public CommerceCloudOrderForecastSyncPersistenceImpl() {
		setModelClass(CommerceCloudOrderForecastSync.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceCloudOrderForecastSyncId",
				"CCOrderForecastSyncId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce cloud order forecast sync in the entity cache if it is enabled.
	 *
	 * @param commerceCloudOrderForecastSync the commerce cloud order forecast sync
	 */
	@Override
	public void cacheResult(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		entityCache.putResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class,
			commerceCloudOrderForecastSync.getPrimaryKey(),
			commerceCloudOrderForecastSync);

		finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
			new Object[] { commerceCloudOrderForecastSync.getCommerceOrderId() },
			commerceCloudOrderForecastSync);

		commerceCloudOrderForecastSync.resetOriginalValues();
	}

	/**
	 * Caches the commerce cloud order forecast syncs in the entity cache if it is enabled.
	 *
	 * @param commerceCloudOrderForecastSyncs the commerce cloud order forecast syncs
	 */
	@Override
	public void cacheResult(
		List<CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs) {
		for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync : commerceCloudOrderForecastSyncs) {
			if (entityCache.getResult(
						CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCloudOrderForecastSyncImpl.class,
						commerceCloudOrderForecastSync.getPrimaryKey()) == null) {
				cacheResult(commerceCloudOrderForecastSync);
			}
			else {
				commerceCloudOrderForecastSync.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce cloud order forecast syncs.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceCloudOrderForecastSyncImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce cloud order forecast sync.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		entityCache.removeResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class,
			commerceCloudOrderForecastSync.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceCloudOrderForecastSyncModelImpl)commerceCloudOrderForecastSync,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceCloudOrderForecastSync> commerceCloudOrderForecastSyncs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync : commerceCloudOrderForecastSyncs) {
			entityCache.removeResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCloudOrderForecastSyncImpl.class,
				commerceCloudOrderForecastSync.getPrimaryKey());

			clearUniqueFindersCache((CommerceCloudOrderForecastSyncModelImpl)commerceCloudOrderForecastSync,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceCloudOrderForecastSyncModelImpl commerceCloudOrderForecastSyncModelImpl) {
		Object[] args = new Object[] {
				commerceCloudOrderForecastSyncModelImpl.getCommerceOrderId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID, args,
			commerceCloudOrderForecastSyncModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceCloudOrderForecastSyncModelImpl commerceCloudOrderForecastSyncModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceCloudOrderForecastSyncModelImpl.getCommerceOrderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID, args);
		}

		if ((commerceCloudOrderForecastSyncModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_COMMERCEORDERID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceCloudOrderForecastSyncModelImpl.getOriginalCommerceOrderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID, args);
		}
	}

	/**
	 * Creates a new commerce cloud order forecast sync with the primary key. Does not add the commerce cloud order forecast sync to the database.
	 *
	 * @param commerceCloudOrderForecastSyncId the primary key for the new commerce cloud order forecast sync
	 * @return the new commerce cloud order forecast sync
	 */
	@Override
	public CommerceCloudOrderForecastSync create(
		long commerceCloudOrderForecastSyncId) {
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = new CommerceCloudOrderForecastSyncImpl();

		commerceCloudOrderForecastSync.setNew(true);
		commerceCloudOrderForecastSync.setPrimaryKey(commerceCloudOrderForecastSyncId);

		commerceCloudOrderForecastSync.setCompanyId(companyProvider.getCompanyId());

		return commerceCloudOrderForecastSync;
	}

	/**
	 * Removes the commerce cloud order forecast sync with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	 * @return the commerce cloud order forecast sync that was removed
	 * @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync remove(
		long commerceCloudOrderForecastSyncId)
		throws NoSuchCloudOrderForecastSyncException {
		return remove((Serializable)commerceCloudOrderForecastSyncId);
	}

	/**
	 * Removes the commerce cloud order forecast sync with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce cloud order forecast sync
	 * @return the commerce cloud order forecast sync that was removed
	 * @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync remove(Serializable primaryKey)
		throws NoSuchCloudOrderForecastSyncException {
		Session session = null;

		try {
			session = openSession();

			CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = (CommerceCloudOrderForecastSync)session.get(CommerceCloudOrderForecastSyncImpl.class,
					primaryKey);

			if (commerceCloudOrderForecastSync == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCloudOrderForecastSyncException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceCloudOrderForecastSync);
		}
		catch (NoSuchCloudOrderForecastSyncException nsee) {
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
	protected CommerceCloudOrderForecastSync removeImpl(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		commerceCloudOrderForecastSync = toUnwrappedModel(commerceCloudOrderForecastSync);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceCloudOrderForecastSync)) {
				commerceCloudOrderForecastSync = (CommerceCloudOrderForecastSync)session.get(CommerceCloudOrderForecastSyncImpl.class,
						commerceCloudOrderForecastSync.getPrimaryKeyObj());
			}

			if (commerceCloudOrderForecastSync != null) {
				session.delete(commerceCloudOrderForecastSync);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceCloudOrderForecastSync != null) {
			clearCache(commerceCloudOrderForecastSync);
		}

		return commerceCloudOrderForecastSync;
	}

	@Override
	public CommerceCloudOrderForecastSync updateImpl(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		commerceCloudOrderForecastSync = toUnwrappedModel(commerceCloudOrderForecastSync);

		boolean isNew = commerceCloudOrderForecastSync.isNew();

		CommerceCloudOrderForecastSyncModelImpl commerceCloudOrderForecastSyncModelImpl =
			(CommerceCloudOrderForecastSyncModelImpl)commerceCloudOrderForecastSync;

		Session session = null;

		try {
			session = openSession();

			if (commerceCloudOrderForecastSync.isNew()) {
				session.save(commerceCloudOrderForecastSync);

				commerceCloudOrderForecastSync.setNew(false);
			}
			else {
				commerceCloudOrderForecastSync = (CommerceCloudOrderForecastSync)session.merge(commerceCloudOrderForecastSync);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceCloudOrderForecastSyncModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceCloudOrderForecastSyncModelImpl.getSyncDate()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SYNCDATE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceCloudOrderForecastSyncModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCloudOrderForecastSyncModelImpl.getOriginalSyncDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SYNCDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE,
					args);

				args = new Object[] {
						commerceCloudOrderForecastSyncModelImpl.getSyncDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SYNCDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE,
					args);
			}
		}

		entityCache.putResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudOrderForecastSyncImpl.class,
			commerceCloudOrderForecastSync.getPrimaryKey(),
			commerceCloudOrderForecastSync, false);

		clearUniqueFindersCache(commerceCloudOrderForecastSyncModelImpl, false);
		cacheUniqueFindersCache(commerceCloudOrderForecastSyncModelImpl);

		commerceCloudOrderForecastSync.resetOriginalValues();

		return commerceCloudOrderForecastSync;
	}

	protected CommerceCloudOrderForecastSync toUnwrappedModel(
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		if (commerceCloudOrderForecastSync instanceof CommerceCloudOrderForecastSyncImpl) {
			return commerceCloudOrderForecastSync;
		}

		CommerceCloudOrderForecastSyncImpl commerceCloudOrderForecastSyncImpl = new CommerceCloudOrderForecastSyncImpl();

		commerceCloudOrderForecastSyncImpl.setNew(commerceCloudOrderForecastSync.isNew());
		commerceCloudOrderForecastSyncImpl.setPrimaryKey(commerceCloudOrderForecastSync.getPrimaryKey());

		commerceCloudOrderForecastSyncImpl.setCommerceCloudOrderForecastSyncId(commerceCloudOrderForecastSync.getCommerceCloudOrderForecastSyncId());
		commerceCloudOrderForecastSyncImpl.setGroupId(commerceCloudOrderForecastSync.getGroupId());
		commerceCloudOrderForecastSyncImpl.setCompanyId(commerceCloudOrderForecastSync.getCompanyId());
		commerceCloudOrderForecastSyncImpl.setCreateDate(commerceCloudOrderForecastSync.getCreateDate());
		commerceCloudOrderForecastSyncImpl.setCommerceOrderId(commerceCloudOrderForecastSync.getCommerceOrderId());
		commerceCloudOrderForecastSyncImpl.setSyncDate(commerceCloudOrderForecastSync.getSyncDate());

		return commerceCloudOrderForecastSyncImpl;
	}

	/**
	 * Returns the commerce cloud order forecast sync with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cloud order forecast sync
	 * @return the commerce cloud order forecast sync
	 * @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync findByPrimaryKey(
		Serializable primaryKey) throws NoSuchCloudOrderForecastSyncException {
		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = fetchByPrimaryKey(primaryKey);

		if (commerceCloudOrderForecastSync == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCloudOrderForecastSyncException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceCloudOrderForecastSync;
	}

	/**
	 * Returns the commerce cloud order forecast sync with the primary key or throws a {@link NoSuchCloudOrderForecastSyncException} if it could not be found.
	 *
	 * @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	 * @return the commerce cloud order forecast sync
	 * @throws NoSuchCloudOrderForecastSyncException if a commerce cloud order forecast sync with the primary key could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync findByPrimaryKey(
		long commerceCloudOrderForecastSyncId)
		throws NoSuchCloudOrderForecastSyncException {
		return findByPrimaryKey((Serializable)commerceCloudOrderForecastSyncId);
	}

	/**
	 * Returns the commerce cloud order forecast sync with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cloud order forecast sync
	 * @return the commerce cloud order forecast sync, or <code>null</code> if a commerce cloud order forecast sync with the primary key could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCloudOrderForecastSyncImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = (CommerceCloudOrderForecastSync)serializable;

		if (commerceCloudOrderForecastSync == null) {
			Session session = null;

			try {
				session = openSession();

				commerceCloudOrderForecastSync = (CommerceCloudOrderForecastSync)session.get(CommerceCloudOrderForecastSyncImpl.class,
						primaryKey);

				if (commerceCloudOrderForecastSync != null) {
					cacheResult(commerceCloudOrderForecastSync);
				}
				else {
					entityCache.putResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCloudOrderForecastSyncImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCloudOrderForecastSyncImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceCloudOrderForecastSync;
	}

	/**
	 * Returns the commerce cloud order forecast sync with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	 * @return the commerce cloud order forecast sync, or <code>null</code> if a commerce cloud order forecast sync with the primary key could not be found
	 */
	@Override
	public CommerceCloudOrderForecastSync fetchByPrimaryKey(
		long commerceCloudOrderForecastSyncId) {
		return fetchByPrimaryKey((Serializable)commerceCloudOrderForecastSyncId);
	}

	@Override
	public Map<Serializable, CommerceCloudOrderForecastSync> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceCloudOrderForecastSync> map = new HashMap<Serializable, CommerceCloudOrderForecastSync>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceCloudOrderForecastSync commerceCloudOrderForecastSync = fetchByPrimaryKey(primaryKey);

			if (commerceCloudOrderForecastSync != null) {
				map.put(primaryKey, commerceCloudOrderForecastSync);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCloudOrderForecastSyncImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceCloudOrderForecastSync)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC_WHERE_PKS_IN);

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

			for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync : (List<CommerceCloudOrderForecastSync>)q.list()) {
				map.put(commerceCloudOrderForecastSync.getPrimaryKeyObj(),
					commerceCloudOrderForecastSync);

				cacheResult(commerceCloudOrderForecastSync);

				uncachedPrimaryKeys.remove(commerceCloudOrderForecastSync.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceCloudOrderForecastSyncModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCloudOrderForecastSyncImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the commerce cloud order forecast syncs.
	 *
	 * @return the commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce cloud order forecast syncs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cloud order forecast syncs
	 * @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	 * @return the range of commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cloud order forecast syncs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cloud order forecast syncs
	 * @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findAll(int start, int end,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cloud order forecast syncs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cloud order forecast syncs
	 * @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce cloud order forecast syncs
	 */
	@Override
	public List<CommerceCloudOrderForecastSync> findAll(int start, int end,
		OrderByComparator<CommerceCloudOrderForecastSync> orderByComparator,
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

		List<CommerceCloudOrderForecastSync> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCloudOrderForecastSync>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC;

				if (pagination) {
					sql = sql.concat(CommerceCloudOrderForecastSyncModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceCloudOrderForecastSync>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCloudOrderForecastSync>)QueryUtil.list(q,
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
	 * Removes all the commerce cloud order forecast syncs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync : findAll()) {
			remove(commerceCloudOrderForecastSync);
		}
	}

	/**
	 * Returns the number of commerce cloud order forecast syncs.
	 *
	 * @return the number of commerce cloud order forecast syncs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCECLOUDORDERFORECASTSYNC);

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

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceCloudOrderForecastSyncModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce cloud order forecast sync persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceCloudOrderForecastSyncImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC = "SELECT commerceCloudOrderForecastSync FROM CommerceCloudOrderForecastSync commerceCloudOrderForecastSync";
	private static final String _SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC_WHERE_PKS_IN =
		"SELECT commerceCloudOrderForecastSync FROM CommerceCloudOrderForecastSync commerceCloudOrderForecastSync WHERE CCOrderForecastSyncId IN (";
	private static final String _SQL_SELECT_COMMERCECLOUDORDERFORECASTSYNC_WHERE =
		"SELECT commerceCloudOrderForecastSync FROM CommerceCloudOrderForecastSync commerceCloudOrderForecastSync WHERE ";
	private static final String _SQL_COUNT_COMMERCECLOUDORDERFORECASTSYNC = "SELECT COUNT(commerceCloudOrderForecastSync) FROM CommerceCloudOrderForecastSync commerceCloudOrderForecastSync";
	private static final String _SQL_COUNT_COMMERCECLOUDORDERFORECASTSYNC_WHERE = "SELECT COUNT(commerceCloudOrderForecastSync) FROM CommerceCloudOrderForecastSync commerceCloudOrderForecastSync WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceCloudOrderForecastSync.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceCloudOrderForecastSync exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceCloudOrderForecastSync exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceCloudOrderForecastSyncPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"commerceCloudOrderForecastSyncId"
			});
}