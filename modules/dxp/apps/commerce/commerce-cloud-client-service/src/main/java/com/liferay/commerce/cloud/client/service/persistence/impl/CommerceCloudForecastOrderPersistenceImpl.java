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

import com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException;
import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;
import com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderImpl;
import com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl;
import com.liferay.commerce.cloud.client.service.persistence.CommerceCloudForecastOrderPersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the commerce cloud forecast order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudForecastOrderPersistence
 * @see com.liferay.commerce.cloud.client.service.persistence.CommerceCloudForecastOrderUtil
 * @generated
 */
@ProviderType
public class CommerceCloudForecastOrderPersistenceImpl
	extends BasePersistenceImpl<CommerceCloudForecastOrder>
	implements CommerceCloudForecastOrderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceCloudForecastOrderUtil} to access the commerce cloud forecast order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceCloudForecastOrderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_COMMERCEORDERID = new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCommerceOrderId", new String[] { Long.class.getName() },
			CommerceCloudForecastOrderModelImpl.COMMERCEORDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEORDERID = new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderId", new String[] { Long.class.getName() });

	/**
	 * Returns the commerce cloud forecast order where commerceOrderId = &#63; or throws a {@link NoSuchCloudForecastOrderException} if it could not be found.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce cloud forecast order
	 * @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	 */
	@Override
	public CommerceCloudForecastOrder findByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudForecastOrderException {
		CommerceCloudForecastOrder commerceCloudForecastOrder = fetchByCommerceOrderId(commerceOrderId);

		if (commerceCloudForecastOrder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commerceOrderId=");
			msg.append(commerceOrderId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCloudForecastOrderException(msg.toString());
		}

		return commerceCloudForecastOrder;
	}

	/**
	 * Returns the commerce cloud forecast order where commerceOrderId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	 */
	@Override
	public CommerceCloudForecastOrder fetchByCommerceOrderId(
		long commerceOrderId) {
		return fetchByCommerceOrderId(commerceOrderId, true);
	}

	/**
	 * Returns the commerce cloud forecast order where commerceOrderId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	 */
	@Override
	public CommerceCloudForecastOrder fetchByCommerceOrderId(
		long commerceOrderId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { commerceOrderId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
					finderArgs, this);
		}

		if (result instanceof CommerceCloudForecastOrder) {
			CommerceCloudForecastOrder commerceCloudForecastOrder = (CommerceCloudForecastOrder)result;

			if ((commerceOrderId != commerceCloudForecastOrder.getCommerceOrderId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_COMMERCECLOUDFORECASTORDER_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceOrderId);

				List<CommerceCloudForecastOrder> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
						finderArgs, list);
				}
				else {
					CommerceCloudForecastOrder commerceCloudForecastOrder = list.get(0);

					result = commerceCloudForecastOrder;

					cacheResult(commerceCloudForecastOrder);

					if ((commerceCloudForecastOrder.getCommerceOrderId() != commerceOrderId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
							finderArgs, commerceCloudForecastOrder);
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
			return (CommerceCloudForecastOrder)result;
		}
	}

	/**
	 * Removes the commerce cloud forecast order where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the commerce cloud forecast order that was removed
	 */
	@Override
	public CommerceCloudForecastOrder removeByCommerceOrderId(
		long commerceOrderId) throws NoSuchCloudForecastOrderException {
		CommerceCloudForecastOrder commerceCloudForecastOrder = findByCommerceOrderId(commerceOrderId);

		return remove(commerceCloudForecastOrder);
	}

	/**
	 * Returns the number of commerce cloud forecast orders where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce cloud forecast orders
	 */
	@Override
	public int countByCommerceOrderId(long commerceOrderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEORDERID;

		Object[] finderArgs = new Object[] { commerceOrderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECLOUDFORECASTORDER_WHERE);

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
		"commerceCloudForecastOrder.commerceOrderId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SYNCDATE = new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySyncDate",
			new String[] {
				Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE =
		new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySyncDate",
			new String[] { Date.class.getName() },
			CommerceCloudForecastOrderModelImpl.SYNCDATE_COLUMN_BITMASK |
			CommerceCloudForecastOrderModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SYNCDATE = new FinderPath(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySyncDate", new String[] { Date.class.getName() });

	/**
	 * Returns all the commerce cloud forecast orders where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @return the matching commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findBySyncDate(Date syncDate) {
		return findBySyncDate(syncDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce cloud forecast orders where syncDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param syncDate the sync date
	 * @param start the lower bound of the range of commerce cloud forecast orders
	 * @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	 * @return the range of matching commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findBySyncDate(Date syncDate,
		int start, int end) {
		return findBySyncDate(syncDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cloud forecast orders where syncDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param syncDate the sync date
	 * @param start the lower bound of the range of commerce cloud forecast orders
	 * @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findBySyncDate(Date syncDate,
		int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		return findBySyncDate(syncDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cloud forecast orders where syncDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param syncDate the sync date
	 * @param start the lower bound of the range of commerce cloud forecast orders
	 * @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findBySyncDate(Date syncDate,
		int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE;
			finderArgs = new Object[] { _getTime(syncDate) };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SYNCDATE;
			finderArgs = new Object[] {
					_getTime(syncDate),
					
					start, end, orderByComparator
				};
		}

		List<CommerceCloudForecastOrder> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCloudForecastOrder>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCloudForecastOrder commerceCloudForecastOrder : list) {
					if (!Objects.equals(syncDate,
								commerceCloudForecastOrder.getSyncDate())) {
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

			query.append(_SQL_SELECT_COMMERCECLOUDFORECASTORDER_WHERE);

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
				query.append(CommerceCloudForecastOrderModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceCloudForecastOrder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCloudForecastOrder>)QueryUtil.list(q,
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
	 * Returns the first commerce cloud forecast order in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cloud forecast order
	 * @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	 */
	@Override
	public CommerceCloudForecastOrder findBySyncDate_First(Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws NoSuchCloudForecastOrderException {
		CommerceCloudForecastOrder commerceCloudForecastOrder = fetchBySyncDate_First(syncDate,
				orderByComparator);

		if (commerceCloudForecastOrder != null) {
			return commerceCloudForecastOrder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("syncDate=");
		msg.append(syncDate);

		msg.append("}");

		throw new NoSuchCloudForecastOrderException(msg.toString());
	}

	/**
	 * Returns the first commerce cloud forecast order in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	 */
	@Override
	public CommerceCloudForecastOrder fetchBySyncDate_First(Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		List<CommerceCloudForecastOrder> list = findBySyncDate(syncDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cloud forecast order in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cloud forecast order
	 * @throws NoSuchCloudForecastOrderException if a matching commerce cloud forecast order could not be found
	 */
	@Override
	public CommerceCloudForecastOrder findBySyncDate_Last(Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws NoSuchCloudForecastOrderException {
		CommerceCloudForecastOrder commerceCloudForecastOrder = fetchBySyncDate_Last(syncDate,
				orderByComparator);

		if (commerceCloudForecastOrder != null) {
			return commerceCloudForecastOrder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("syncDate=");
		msg.append(syncDate);

		msg.append("}");

		throw new NoSuchCloudForecastOrderException(msg.toString());
	}

	/**
	 * Returns the last commerce cloud forecast order in the ordered set where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cloud forecast order, or <code>null</code> if a matching commerce cloud forecast order could not be found
	 */
	@Override
	public CommerceCloudForecastOrder fetchBySyncDate_Last(Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		int count = countBySyncDate(syncDate);

		if (count == 0) {
			return null;
		}

		List<CommerceCloudForecastOrder> list = findBySyncDate(syncDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce cloud forecast orders before and after the current commerce cloud forecast order in the ordered set where syncDate = &#63;.
	 *
	 * @param commerceCloudForecastOrderId the primary key of the current commerce cloud forecast order
	 * @param syncDate the sync date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cloud forecast order
	 * @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	 */
	@Override
	public CommerceCloudForecastOrder[] findBySyncDate_PrevAndNext(
		long commerceCloudForecastOrderId, Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator)
		throws NoSuchCloudForecastOrderException {
		CommerceCloudForecastOrder commerceCloudForecastOrder = findByPrimaryKey(commerceCloudForecastOrderId);

		Session session = null;

		try {
			session = openSession();

			CommerceCloudForecastOrder[] array = new CommerceCloudForecastOrderImpl[3];

			array[0] = getBySyncDate_PrevAndNext(session,
					commerceCloudForecastOrder, syncDate, orderByComparator,
					true);

			array[1] = commerceCloudForecastOrder;

			array[2] = getBySyncDate_PrevAndNext(session,
					commerceCloudForecastOrder, syncDate, orderByComparator,
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

	protected CommerceCloudForecastOrder getBySyncDate_PrevAndNext(
		Session session, CommerceCloudForecastOrder commerceCloudForecastOrder,
		Date syncDate,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCECLOUDFORECASTORDER_WHERE);

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
			query.append(CommerceCloudForecastOrderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCloudForecastOrder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCloudForecastOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce cloud forecast orders where syncDate = &#63; from the database.
	 *
	 * @param syncDate the sync date
	 */
	@Override
	public void removeBySyncDate(Date syncDate) {
		for (CommerceCloudForecastOrder commerceCloudForecastOrder : findBySyncDate(
				syncDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCloudForecastOrder);
		}
	}

	/**
	 * Returns the number of commerce cloud forecast orders where syncDate = &#63;.
	 *
	 * @param syncDate the sync date
	 * @return the number of matching commerce cloud forecast orders
	 */
	@Override
	public int countBySyncDate(Date syncDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SYNCDATE;

		Object[] finderArgs = new Object[] { _getTime(syncDate) };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECLOUDFORECASTORDER_WHERE);

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

	private static final String _FINDER_COLUMN_SYNCDATE_SYNCDATE_1 = "commerceCloudForecastOrder.syncDate IS NULL";
	private static final String _FINDER_COLUMN_SYNCDATE_SYNCDATE_2 = "commerceCloudForecastOrder.syncDate = ?";

	public CommerceCloudForecastOrderPersistenceImpl() {
		setModelClass(CommerceCloudForecastOrder.class);
	}

	/**
	 * Caches the commerce cloud forecast order in the entity cache if it is enabled.
	 *
	 * @param commerceCloudForecastOrder the commerce cloud forecast order
	 */
	@Override
	public void cacheResult(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		entityCache.putResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class,
			commerceCloudForecastOrder.getPrimaryKey(),
			commerceCloudForecastOrder);

		finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID,
			new Object[] { commerceCloudForecastOrder.getCommerceOrderId() },
			commerceCloudForecastOrder);

		commerceCloudForecastOrder.resetOriginalValues();
	}

	/**
	 * Caches the commerce cloud forecast orders in the entity cache if it is enabled.
	 *
	 * @param commerceCloudForecastOrders the commerce cloud forecast orders
	 */
	@Override
	public void cacheResult(
		List<CommerceCloudForecastOrder> commerceCloudForecastOrders) {
		for (CommerceCloudForecastOrder commerceCloudForecastOrder : commerceCloudForecastOrders) {
			if (entityCache.getResult(
						CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCloudForecastOrderImpl.class,
						commerceCloudForecastOrder.getPrimaryKey()) == null) {
				cacheResult(commerceCloudForecastOrder);
			}
			else {
				commerceCloudForecastOrder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce cloud forecast orders.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceCloudForecastOrderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce cloud forecast order.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		entityCache.removeResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class,
			commerceCloudForecastOrder.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceCloudForecastOrderModelImpl)commerceCloudForecastOrder,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceCloudForecastOrder> commerceCloudForecastOrders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceCloudForecastOrder commerceCloudForecastOrder : commerceCloudForecastOrders) {
			entityCache.removeResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCloudForecastOrderImpl.class,
				commerceCloudForecastOrder.getPrimaryKey());

			clearUniqueFindersCache((CommerceCloudForecastOrderModelImpl)commerceCloudForecastOrder,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceCloudForecastOrderModelImpl commerceCloudForecastOrderModelImpl) {
		Object[] args = new Object[] {
				commerceCloudForecastOrderModelImpl.getCommerceOrderId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID, args,
			commerceCloudForecastOrderModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceCloudForecastOrderModelImpl commerceCloudForecastOrderModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceCloudForecastOrderModelImpl.getCommerceOrderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID, args);
		}

		if ((commerceCloudForecastOrderModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_COMMERCEORDERID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceCloudForecastOrderModelImpl.getOriginalCommerceOrderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_COMMERCEORDERID, args);
		}
	}

	/**
	 * Creates a new commerce cloud forecast order with the primary key. Does not add the commerce cloud forecast order to the database.
	 *
	 * @param commerceCloudForecastOrderId the primary key for the new commerce cloud forecast order
	 * @return the new commerce cloud forecast order
	 */
	@Override
	public CommerceCloudForecastOrder create(long commerceCloudForecastOrderId) {
		CommerceCloudForecastOrder commerceCloudForecastOrder = new CommerceCloudForecastOrderImpl();

		commerceCloudForecastOrder.setNew(true);
		commerceCloudForecastOrder.setPrimaryKey(commerceCloudForecastOrderId);

		commerceCloudForecastOrder.setCompanyId(companyProvider.getCompanyId());

		return commerceCloudForecastOrder;
	}

	/**
	 * Removes the commerce cloud forecast order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	 * @return the commerce cloud forecast order that was removed
	 * @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	 */
	@Override
	public CommerceCloudForecastOrder remove(long commerceCloudForecastOrderId)
		throws NoSuchCloudForecastOrderException {
		return remove((Serializable)commerceCloudForecastOrderId);
	}

	/**
	 * Removes the commerce cloud forecast order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce cloud forecast order
	 * @return the commerce cloud forecast order that was removed
	 * @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	 */
	@Override
	public CommerceCloudForecastOrder remove(Serializable primaryKey)
		throws NoSuchCloudForecastOrderException {
		Session session = null;

		try {
			session = openSession();

			CommerceCloudForecastOrder commerceCloudForecastOrder = (CommerceCloudForecastOrder)session.get(CommerceCloudForecastOrderImpl.class,
					primaryKey);

			if (commerceCloudForecastOrder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCloudForecastOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceCloudForecastOrder);
		}
		catch (NoSuchCloudForecastOrderException nsee) {
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
	protected CommerceCloudForecastOrder removeImpl(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceCloudForecastOrder)) {
				commerceCloudForecastOrder = (CommerceCloudForecastOrder)session.get(CommerceCloudForecastOrderImpl.class,
						commerceCloudForecastOrder.getPrimaryKeyObj());
			}

			if (commerceCloudForecastOrder != null) {
				session.delete(commerceCloudForecastOrder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceCloudForecastOrder != null) {
			clearCache(commerceCloudForecastOrder);
		}

		return commerceCloudForecastOrder;
	}

	@Override
	public CommerceCloudForecastOrder updateImpl(
		CommerceCloudForecastOrder commerceCloudForecastOrder) {
		boolean isNew = commerceCloudForecastOrder.isNew();

		if (!(commerceCloudForecastOrder instanceof CommerceCloudForecastOrderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceCloudForecastOrder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceCloudForecastOrder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceCloudForecastOrder proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceCloudForecastOrder implementation " +
				commerceCloudForecastOrder.getClass());
		}

		CommerceCloudForecastOrderModelImpl commerceCloudForecastOrderModelImpl = (CommerceCloudForecastOrderModelImpl)commerceCloudForecastOrder;

		Session session = null;

		try {
			session = openSession();

			if (commerceCloudForecastOrder.isNew()) {
				session.save(commerceCloudForecastOrder);

				commerceCloudForecastOrder.setNew(false);
			}
			else {
				commerceCloudForecastOrder = (CommerceCloudForecastOrder)session.merge(commerceCloudForecastOrder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceCloudForecastOrderModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceCloudForecastOrderModelImpl.getSyncDate()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SYNCDATE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceCloudForecastOrderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCloudForecastOrderModelImpl.getOriginalSyncDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SYNCDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE,
					args);

				args = new Object[] {
						commerceCloudForecastOrderModelImpl.getSyncDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SYNCDATE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SYNCDATE,
					args);
			}
		}

		entityCache.putResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCloudForecastOrderImpl.class,
			commerceCloudForecastOrder.getPrimaryKey(),
			commerceCloudForecastOrder, false);

		clearUniqueFindersCache(commerceCloudForecastOrderModelImpl, false);
		cacheUniqueFindersCache(commerceCloudForecastOrderModelImpl);

		commerceCloudForecastOrder.resetOriginalValues();

		return commerceCloudForecastOrder;
	}

	/**
	 * Returns the commerce cloud forecast order with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cloud forecast order
	 * @return the commerce cloud forecast order
	 * @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	 */
	@Override
	public CommerceCloudForecastOrder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCloudForecastOrderException {
		CommerceCloudForecastOrder commerceCloudForecastOrder = fetchByPrimaryKey(primaryKey);

		if (commerceCloudForecastOrder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCloudForecastOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceCloudForecastOrder;
	}

	/**
	 * Returns the commerce cloud forecast order with the primary key or throws a {@link NoSuchCloudForecastOrderException} if it could not be found.
	 *
	 * @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	 * @return the commerce cloud forecast order
	 * @throws NoSuchCloudForecastOrderException if a commerce cloud forecast order with the primary key could not be found
	 */
	@Override
	public CommerceCloudForecastOrder findByPrimaryKey(
		long commerceCloudForecastOrderId)
		throws NoSuchCloudForecastOrderException {
		return findByPrimaryKey((Serializable)commerceCloudForecastOrderId);
	}

	/**
	 * Returns the commerce cloud forecast order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cloud forecast order
	 * @return the commerce cloud forecast order, or <code>null</code> if a commerce cloud forecast order with the primary key could not be found
	 */
	@Override
	public CommerceCloudForecastOrder fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCloudForecastOrderImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceCloudForecastOrder commerceCloudForecastOrder = (CommerceCloudForecastOrder)serializable;

		if (commerceCloudForecastOrder == null) {
			Session session = null;

			try {
				session = openSession();

				commerceCloudForecastOrder = (CommerceCloudForecastOrder)session.get(CommerceCloudForecastOrderImpl.class,
						primaryKey);

				if (commerceCloudForecastOrder != null) {
					cacheResult(commerceCloudForecastOrder);
				}
				else {
					entityCache.putResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCloudForecastOrderImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCloudForecastOrderImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceCloudForecastOrder;
	}

	/**
	 * Returns the commerce cloud forecast order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	 * @return the commerce cloud forecast order, or <code>null</code> if a commerce cloud forecast order with the primary key could not be found
	 */
	@Override
	public CommerceCloudForecastOrder fetchByPrimaryKey(
		long commerceCloudForecastOrderId) {
		return fetchByPrimaryKey((Serializable)commerceCloudForecastOrderId);
	}

	@Override
	public Map<Serializable, CommerceCloudForecastOrder> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceCloudForecastOrder> map = new HashMap<Serializable, CommerceCloudForecastOrder>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceCloudForecastOrder commerceCloudForecastOrder = fetchByPrimaryKey(primaryKey);

			if (commerceCloudForecastOrder != null) {
				map.put(primaryKey, commerceCloudForecastOrder);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCloudForecastOrderImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceCloudForecastOrder)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCECLOUDFORECASTORDER_WHERE_PKS_IN);

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

			for (CommerceCloudForecastOrder commerceCloudForecastOrder : (List<CommerceCloudForecastOrder>)q.list()) {
				map.put(commerceCloudForecastOrder.getPrimaryKeyObj(),
					commerceCloudForecastOrder);

				cacheResult(commerceCloudForecastOrder);

				uncachedPrimaryKeys.remove(commerceCloudForecastOrder.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceCloudForecastOrderModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCloudForecastOrderImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce cloud forecast orders.
	 *
	 * @return the commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce cloud forecast orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cloud forecast orders
	 * @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	 * @return the range of commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cloud forecast orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cloud forecast orders
	 * @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findAll(int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cloud forecast orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cloud forecast orders
	 * @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce cloud forecast orders
	 */
	@Override
	public List<CommerceCloudForecastOrder> findAll(int start, int end,
		OrderByComparator<CommerceCloudForecastOrder> orderByComparator,
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

		List<CommerceCloudForecastOrder> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCloudForecastOrder>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCECLOUDFORECASTORDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECLOUDFORECASTORDER;

				if (pagination) {
					sql = sql.concat(CommerceCloudForecastOrderModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceCloudForecastOrder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCloudForecastOrder>)QueryUtil.list(q,
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
	 * Removes all the commerce cloud forecast orders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceCloudForecastOrder commerceCloudForecastOrder : findAll()) {
			remove(commerceCloudForecastOrder);
		}
	}

	/**
	 * Returns the number of commerce cloud forecast orders.
	 *
	 * @return the number of commerce cloud forecast orders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCECLOUDFORECASTORDER);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceCloudForecastOrderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce cloud forecast order persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceCloudForecastOrderImpl.class.getName());
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

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCECLOUDFORECASTORDER = "SELECT commerceCloudForecastOrder FROM CommerceCloudForecastOrder commerceCloudForecastOrder";
	private static final String _SQL_SELECT_COMMERCECLOUDFORECASTORDER_WHERE_PKS_IN =
		"SELECT commerceCloudForecastOrder FROM CommerceCloudForecastOrder commerceCloudForecastOrder WHERE commerceCloudForecastOrderId IN (";
	private static final String _SQL_SELECT_COMMERCECLOUDFORECASTORDER_WHERE = "SELECT commerceCloudForecastOrder FROM CommerceCloudForecastOrder commerceCloudForecastOrder WHERE ";
	private static final String _SQL_COUNT_COMMERCECLOUDFORECASTORDER = "SELECT COUNT(commerceCloudForecastOrder) FROM CommerceCloudForecastOrder commerceCloudForecastOrder";
	private static final String _SQL_COUNT_COMMERCECLOUDFORECASTORDER_WHERE = "SELECT COUNT(commerceCloudForecastOrder) FROM CommerceCloudForecastOrder commerceCloudForecastOrder WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceCloudForecastOrder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceCloudForecastOrder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceCloudForecastOrder exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceCloudForecastOrderPersistenceImpl.class);
}