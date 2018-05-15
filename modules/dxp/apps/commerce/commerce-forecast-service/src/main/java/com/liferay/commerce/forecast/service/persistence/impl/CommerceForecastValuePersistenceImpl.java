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

package com.liferay.commerce.forecast.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.forecast.exception.NoSuchForecastValueException;
import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.model.impl.CommerceForecastValueImpl;
import com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastValuePersistence;

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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the commerce forecast value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastValuePersistence
 * @see com.liferay.commerce.forecast.service.persistence.CommerceForecastValueUtil
 * @generated
 */
@ProviderType
public class CommerceForecastValuePersistenceImpl extends BasePersistenceImpl<CommerceForecastValue>
	implements CommerceForecastValuePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceForecastValueUtil} to access the commerce forecast value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceForecastValueImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID =
		new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceForecastEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID =
		new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceForecastEntryId",
			new String[] { Long.class.getName() },
			CommerceForecastValueModelImpl.COMMERCEFORECASTENTRYID_COLUMN_BITMASK |
			CommerceForecastValueModelImpl.DATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEFORECASTENTRYID = new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceForecastEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce forecast values where commerceForecastEntryId = &#63;.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @return the matching commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId) {
		return findByCommerceForecastEntryId(commerceForecastEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param start the lower bound of the range of commerce forecast values
	 * @param end the upper bound of the range of commerce forecast values (not inclusive)
	 * @return the range of matching commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end) {
		return findByCommerceForecastEntryId(commerceForecastEntryId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param start the lower bound of the range of commerce forecast values
	 * @param end the upper bound of the range of commerce forecast values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		return findByCommerceForecastEntryId(commerceForecastEntryId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce forecast values where commerceForecastEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param start the lower bound of the range of commerce forecast values
	 * @param end the upper bound of the range of commerce forecast values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findByCommerceForecastEntryId(
		long commerceForecastEntryId, int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID;
			finderArgs = new Object[] { commerceForecastEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID;
			finderArgs = new Object[] {
					commerceForecastEntryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceForecastValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceForecastValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceForecastValue commerceForecastValue : list) {
					if ((commerceForecastEntryId != commerceForecastValue.getCommerceForecastEntryId())) {
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

			query.append(_SQL_SELECT_COMMERCEFORECASTVALUE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEFORECASTENTRYID_COMMERCEFORECASTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceForecastValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceForecastEntryId);

				if (!pagination) {
					list = (List<CommerceForecastValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceForecastValue>)QueryUtil.list(q,
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
	 * Returns the first commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce forecast value
	 * @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	 */
	@Override
	public CommerceForecastValue findByCommerceForecastEntryId_First(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator)
		throws NoSuchForecastValueException {
		CommerceForecastValue commerceForecastValue = fetchByCommerceForecastEntryId_First(commerceForecastEntryId,
				orderByComparator);

		if (commerceForecastValue != null) {
			return commerceForecastValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceForecastEntryId=");
		msg.append(commerceForecastEntryId);

		msg.append("}");

		throw new NoSuchForecastValueException(msg.toString());
	}

	/**
	 * Returns the first commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	 */
	@Override
	public CommerceForecastValue fetchByCommerceForecastEntryId_First(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		List<CommerceForecastValue> list = findByCommerceForecastEntryId(commerceForecastEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce forecast value
	 * @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	 */
	@Override
	public CommerceForecastValue findByCommerceForecastEntryId_Last(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator)
		throws NoSuchForecastValueException {
		CommerceForecastValue commerceForecastValue = fetchByCommerceForecastEntryId_Last(commerceForecastEntryId,
				orderByComparator);

		if (commerceForecastValue != null) {
			return commerceForecastValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceForecastEntryId=");
		msg.append(commerceForecastEntryId);

		msg.append("}");

		throw new NoSuchForecastValueException(msg.toString());
	}

	/**
	 * Returns the last commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	 */
	@Override
	public CommerceForecastValue fetchByCommerceForecastEntryId_Last(
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		int count = countByCommerceForecastEntryId(commerceForecastEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceForecastValue> list = findByCommerceForecastEntryId(commerceForecastEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce forecast values before and after the current commerce forecast value in the ordered set where commerceForecastEntryId = &#63;.
	 *
	 * @param commerceForecastValueId the primary key of the current commerce forecast value
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce forecast value
	 * @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue[] findByCommerceForecastEntryId_PrevAndNext(
		long commerceForecastValueId, long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator)
		throws NoSuchForecastValueException {
		CommerceForecastValue commerceForecastValue = findByPrimaryKey(commerceForecastValueId);

		Session session = null;

		try {
			session = openSession();

			CommerceForecastValue[] array = new CommerceForecastValueImpl[3];

			array[0] = getByCommerceForecastEntryId_PrevAndNext(session,
					commerceForecastValue, commerceForecastEntryId,
					orderByComparator, true);

			array[1] = commerceForecastValue;

			array[2] = getByCommerceForecastEntryId_PrevAndNext(session,
					commerceForecastValue, commerceForecastEntryId,
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

	protected CommerceForecastValue getByCommerceForecastEntryId_PrevAndNext(
		Session session, CommerceForecastValue commerceForecastValue,
		long commerceForecastEntryId,
		OrderByComparator<CommerceForecastValue> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEFORECASTVALUE_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEFORECASTENTRYID_COMMERCEFORECASTENTRYID_2);

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
			query.append(CommerceForecastValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceForecastEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceForecastValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceForecastValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce forecast values where commerceForecastEntryId = &#63; from the database.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 */
	@Override
	public void removeByCommerceForecastEntryId(long commerceForecastEntryId) {
		for (CommerceForecastValue commerceForecastValue : findByCommerceForecastEntryId(
				commerceForecastEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(commerceForecastValue);
		}
	}

	/**
	 * Returns the number of commerce forecast values where commerceForecastEntryId = &#63;.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @return the number of matching commerce forecast values
	 */
	@Override
	public int countByCommerceForecastEntryId(long commerceForecastEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEFORECASTENTRYID;

		Object[] finderArgs = new Object[] { commerceForecastEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEFORECASTVALUE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEFORECASTENTRYID_COMMERCEFORECASTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceForecastEntryId);

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

	private static final String _FINDER_COLUMN_COMMERCEFORECASTENTRYID_COMMERCEFORECASTENTRYID_2 =
		"commerceForecastValue.commerceForecastEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_D = new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceForecastValueImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_D",
			new String[] { Long.class.getName(), Date.class.getName() },
			CommerceForecastValueModelImpl.COMMERCEFORECASTENTRYID_COLUMN_BITMASK |
			CommerceForecastValueModelImpl.DATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_D = new FinderPath(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_D",
			new String[] { Long.class.getName(), Date.class.getName() });

	/**
	 * Returns the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; or throws a {@link NoSuchForecastValueException} if it could not be found.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param date the date
	 * @return the matching commerce forecast value
	 * @throws NoSuchForecastValueException if a matching commerce forecast value could not be found
	 */
	@Override
	public CommerceForecastValue findByC_D(long commerceForecastEntryId,
		Date date) throws NoSuchForecastValueException {
		CommerceForecastValue commerceForecastValue = fetchByC_D(commerceForecastEntryId,
				date);

		if (commerceForecastValue == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commerceForecastEntryId=");
			msg.append(commerceForecastEntryId);

			msg.append(", date=");
			msg.append(date);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchForecastValueException(msg.toString());
		}

		return commerceForecastValue;
	}

	/**
	 * Returns the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param date the date
	 * @return the matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	 */
	@Override
	public CommerceForecastValue fetchByC_D(long commerceForecastEntryId,
		Date date) {
		return fetchByC_D(commerceForecastEntryId, date, true);
	}

	/**
	 * Returns the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param date the date
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce forecast value, or <code>null</code> if a matching commerce forecast value could not be found
	 */
	@Override
	public CommerceForecastValue fetchByC_D(long commerceForecastEntryId,
		Date date, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { commerceForecastEntryId, date };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_D,
					finderArgs, this);
		}

		if (result instanceof CommerceForecastValue) {
			CommerceForecastValue commerceForecastValue = (CommerceForecastValue)result;

			if ((commerceForecastEntryId != commerceForecastValue.getCommerceForecastEntryId()) ||
					!Objects.equals(date, commerceForecastValue.getDate())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEFORECASTVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_D_COMMERCEFORECASTENTRYID_2);

			boolean bindDate = false;

			if (date == null) {
				query.append(_FINDER_COLUMN_C_D_DATE_1);
			}
			else {
				bindDate = true;

				query.append(_FINDER_COLUMN_C_D_DATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceForecastEntryId);

				if (bindDate) {
					qPos.add(new Timestamp(date.getTime()));
				}

				List<CommerceForecastValue> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_D, finderArgs,
						list);
				}
				else {
					CommerceForecastValue commerceForecastValue = list.get(0);

					result = commerceForecastValue;

					cacheResult(commerceForecastValue);

					if ((commerceForecastValue.getCommerceForecastEntryId() != commerceForecastEntryId) ||
							(commerceForecastValue.getDate() == null) ||
							!commerceForecastValue.getDate().equals(date)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_D,
							finderArgs, commerceForecastValue);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_D, finderArgs);

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
			return (CommerceForecastValue)result;
		}
	}

	/**
	 * Removes the commerce forecast value where commerceForecastEntryId = &#63; and date = &#63; from the database.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param date the date
	 * @return the commerce forecast value that was removed
	 */
	@Override
	public CommerceForecastValue removeByC_D(long commerceForecastEntryId,
		Date date) throws NoSuchForecastValueException {
		CommerceForecastValue commerceForecastValue = findByC_D(commerceForecastEntryId,
				date);

		return remove(commerceForecastValue);
	}

	/**
	 * Returns the number of commerce forecast values where commerceForecastEntryId = &#63; and date = &#63;.
	 *
	 * @param commerceForecastEntryId the commerce forecast entry ID
	 * @param date the date
	 * @return the number of matching commerce forecast values
	 */
	@Override
	public int countByC_D(long commerceForecastEntryId, Date date) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_D;

		Object[] finderArgs = new Object[] { commerceForecastEntryId, date };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEFORECASTVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_D_COMMERCEFORECASTENTRYID_2);

			boolean bindDate = false;

			if (date == null) {
				query.append(_FINDER_COLUMN_C_D_DATE_1);
			}
			else {
				bindDate = true;

				query.append(_FINDER_COLUMN_C_D_DATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceForecastEntryId);

				if (bindDate) {
					qPos.add(new Timestamp(date.getTime()));
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

	private static final String _FINDER_COLUMN_C_D_COMMERCEFORECASTENTRYID_2 = "commerceForecastValue.commerceForecastEntryId = ? AND ";
	private static final String _FINDER_COLUMN_C_D_DATE_1 = "commerceForecastValue.date IS NULL";
	private static final String _FINDER_COLUMN_C_D_DATE_2 = "commerceForecastValue.date = ?";

	public CommerceForecastValuePersistenceImpl() {
		setModelClass(CommerceForecastValue.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("date", "date_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce forecast value in the entity cache if it is enabled.
	 *
	 * @param commerceForecastValue the commerce forecast value
	 */
	@Override
	public void cacheResult(CommerceForecastValue commerceForecastValue) {
		entityCache.putResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueImpl.class,
			commerceForecastValue.getPrimaryKey(), commerceForecastValue);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_D,
			new Object[] {
				commerceForecastValue.getCommerceForecastEntryId(),
				commerceForecastValue.getDate()
			}, commerceForecastValue);

		commerceForecastValue.resetOriginalValues();
	}

	/**
	 * Caches the commerce forecast values in the entity cache if it is enabled.
	 *
	 * @param commerceForecastValues the commerce forecast values
	 */
	@Override
	public void cacheResult(List<CommerceForecastValue> commerceForecastValues) {
		for (CommerceForecastValue commerceForecastValue : commerceForecastValues) {
			if (entityCache.getResult(
						CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
						CommerceForecastValueImpl.class,
						commerceForecastValue.getPrimaryKey()) == null) {
				cacheResult(commerceForecastValue);
			}
			else {
				commerceForecastValue.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce forecast values.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceForecastValueImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce forecast value.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceForecastValue commerceForecastValue) {
		entityCache.removeResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueImpl.class,
			commerceForecastValue.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceForecastValueModelImpl)commerceForecastValue,
			true);
	}

	@Override
	public void clearCache(List<CommerceForecastValue> commerceForecastValues) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceForecastValue commerceForecastValue : commerceForecastValues) {
			entityCache.removeResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
				CommerceForecastValueImpl.class,
				commerceForecastValue.getPrimaryKey());

			clearUniqueFindersCache((CommerceForecastValueModelImpl)commerceForecastValue,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceForecastValueModelImpl commerceForecastValueModelImpl) {
		Object[] args = new Object[] {
				commerceForecastValueModelImpl.getCommerceForecastEntryId(),
				commerceForecastValueModelImpl.getDate()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_D, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_D, args,
			commerceForecastValueModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceForecastValueModelImpl commerceForecastValueModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceForecastValueModelImpl.getCommerceForecastEntryId(),
					commerceForecastValueModelImpl.getDate()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_D, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_D, args);
		}

		if ((commerceForecastValueModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_D.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceForecastValueModelImpl.getOriginalCommerceForecastEntryId(),
					commerceForecastValueModelImpl.getOriginalDate()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_D, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_D, args);
		}
	}

	/**
	 * Creates a new commerce forecast value with the primary key. Does not add the commerce forecast value to the database.
	 *
	 * @param commerceForecastValueId the primary key for the new commerce forecast value
	 * @return the new commerce forecast value
	 */
	@Override
	public CommerceForecastValue create(long commerceForecastValueId) {
		CommerceForecastValue commerceForecastValue = new CommerceForecastValueImpl();

		commerceForecastValue.setNew(true);
		commerceForecastValue.setPrimaryKey(commerceForecastValueId);

		commerceForecastValue.setCompanyId(companyProvider.getCompanyId());

		return commerceForecastValue;
	}

	/**
	 * Removes the commerce forecast value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceForecastValueId the primary key of the commerce forecast value
	 * @return the commerce forecast value that was removed
	 * @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue remove(long commerceForecastValueId)
		throws NoSuchForecastValueException {
		return remove((Serializable)commerceForecastValueId);
	}

	/**
	 * Removes the commerce forecast value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce forecast value
	 * @return the commerce forecast value that was removed
	 * @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue remove(Serializable primaryKey)
		throws NoSuchForecastValueException {
		Session session = null;

		try {
			session = openSession();

			CommerceForecastValue commerceForecastValue = (CommerceForecastValue)session.get(CommerceForecastValueImpl.class,
					primaryKey);

			if (commerceForecastValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchForecastValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceForecastValue);
		}
		catch (NoSuchForecastValueException nsee) {
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
	protected CommerceForecastValue removeImpl(
		CommerceForecastValue commerceForecastValue) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceForecastValue)) {
				commerceForecastValue = (CommerceForecastValue)session.get(CommerceForecastValueImpl.class,
						commerceForecastValue.getPrimaryKeyObj());
			}

			if (commerceForecastValue != null) {
				session.delete(commerceForecastValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceForecastValue != null) {
			clearCache(commerceForecastValue);
		}

		return commerceForecastValue;
	}

	@Override
	public CommerceForecastValue updateImpl(
		CommerceForecastValue commerceForecastValue) {
		boolean isNew = commerceForecastValue.isNew();

		if (!(commerceForecastValue instanceof CommerceForecastValueModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceForecastValue.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceForecastValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceForecastValue proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceForecastValue implementation " +
				commerceForecastValue.getClass());
		}

		CommerceForecastValueModelImpl commerceForecastValueModelImpl = (CommerceForecastValueModelImpl)commerceForecastValue;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceForecastValue.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceForecastValue.setCreateDate(now);
			}
			else {
				commerceForecastValue.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceForecastValueModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceForecastValue.setModifiedDate(now);
			}
			else {
				commerceForecastValue.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceForecastValue.isNew()) {
				session.save(commerceForecastValue);

				commerceForecastValue.setNew(false);
			}
			else {
				commerceForecastValue = (CommerceForecastValue)session.merge(commerceForecastValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceForecastValueModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceForecastValueModelImpl.getCommerceForecastEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEFORECASTENTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceForecastValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceForecastValueModelImpl.getOriginalCommerceForecastEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEFORECASTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID,
					args);

				args = new Object[] {
						commerceForecastValueModelImpl.getCommerceForecastEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEFORECASTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEFORECASTENTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceForecastValueImpl.class,
			commerceForecastValue.getPrimaryKey(), commerceForecastValue, false);

		clearUniqueFindersCache(commerceForecastValueModelImpl, false);
		cacheUniqueFindersCache(commerceForecastValueModelImpl);

		commerceForecastValue.resetOriginalValues();

		return commerceForecastValue;
	}

	/**
	 * Returns the commerce forecast value with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce forecast value
	 * @return the commerce forecast value
	 * @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchForecastValueException {
		CommerceForecastValue commerceForecastValue = fetchByPrimaryKey(primaryKey);

		if (commerceForecastValue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchForecastValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceForecastValue;
	}

	/**
	 * Returns the commerce forecast value with the primary key or throws a {@link NoSuchForecastValueException} if it could not be found.
	 *
	 * @param commerceForecastValueId the primary key of the commerce forecast value
	 * @return the commerce forecast value
	 * @throws NoSuchForecastValueException if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue findByPrimaryKey(long commerceForecastValueId)
		throws NoSuchForecastValueException {
		return findByPrimaryKey((Serializable)commerceForecastValueId);
	}

	/**
	 * Returns the commerce forecast value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce forecast value
	 * @return the commerce forecast value, or <code>null</code> if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
				CommerceForecastValueImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceForecastValue commerceForecastValue = (CommerceForecastValue)serializable;

		if (commerceForecastValue == null) {
			Session session = null;

			try {
				session = openSession();

				commerceForecastValue = (CommerceForecastValue)session.get(CommerceForecastValueImpl.class,
						primaryKey);

				if (commerceForecastValue != null) {
					cacheResult(commerceForecastValue);
				}
				else {
					entityCache.putResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
						CommerceForecastValueImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
					CommerceForecastValueImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceForecastValue;
	}

	/**
	 * Returns the commerce forecast value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceForecastValueId the primary key of the commerce forecast value
	 * @return the commerce forecast value, or <code>null</code> if a commerce forecast value with the primary key could not be found
	 */
	@Override
	public CommerceForecastValue fetchByPrimaryKey(long commerceForecastValueId) {
		return fetchByPrimaryKey((Serializable)commerceForecastValueId);
	}

	@Override
	public Map<Serializable, CommerceForecastValue> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceForecastValue> map = new HashMap<Serializable, CommerceForecastValue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceForecastValue commerceForecastValue = fetchByPrimaryKey(primaryKey);

			if (commerceForecastValue != null) {
				map.put(primaryKey, commerceForecastValue);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
					CommerceForecastValueImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceForecastValue)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEFORECASTVALUE_WHERE_PKS_IN);

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

			for (CommerceForecastValue commerceForecastValue : (List<CommerceForecastValue>)q.list()) {
				map.put(commerceForecastValue.getPrimaryKeyObj(),
					commerceForecastValue);

				cacheResult(commerceForecastValue);

				uncachedPrimaryKeys.remove(commerceForecastValue.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceForecastValueModelImpl.ENTITY_CACHE_ENABLED,
					CommerceForecastValueImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce forecast values.
	 *
	 * @return the commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce forecast values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce forecast values
	 * @param end the upper bound of the range of commerce forecast values (not inclusive)
	 * @return the range of commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce forecast values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce forecast values
	 * @param end the upper bound of the range of commerce forecast values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findAll(int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce forecast values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce forecast values
	 * @param end the upper bound of the range of commerce forecast values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce forecast values
	 */
	@Override
	public List<CommerceForecastValue> findAll(int start, int end,
		OrderByComparator<CommerceForecastValue> orderByComparator,
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

		List<CommerceForecastValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceForecastValue>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEFORECASTVALUE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEFORECASTVALUE;

				if (pagination) {
					sql = sql.concat(CommerceForecastValueModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceForecastValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceForecastValue>)QueryUtil.list(q,
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
	 * Removes all the commerce forecast values from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceForecastValue commerceForecastValue : findAll()) {
			remove(commerceForecastValue);
		}
	}

	/**
	 * Returns the number of commerce forecast values.
	 *
	 * @return the number of commerce forecast values
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEFORECASTVALUE);

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
		return CommerceForecastValueModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce forecast value persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceForecastValueImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEFORECASTVALUE = "SELECT commerceForecastValue FROM CommerceForecastValue commerceForecastValue";
	private static final String _SQL_SELECT_COMMERCEFORECASTVALUE_WHERE_PKS_IN = "SELECT commerceForecastValue FROM CommerceForecastValue commerceForecastValue WHERE commerceForecastValueId IN (";
	private static final String _SQL_SELECT_COMMERCEFORECASTVALUE_WHERE = "SELECT commerceForecastValue FROM CommerceForecastValue commerceForecastValue WHERE ";
	private static final String _SQL_COUNT_COMMERCEFORECASTVALUE = "SELECT COUNT(commerceForecastValue) FROM CommerceForecastValue commerceForecastValue";
	private static final String _SQL_COUNT_COMMERCEFORECASTVALUE_WHERE = "SELECT COUNT(commerceForecastValue) FROM CommerceForecastValue commerceForecastValue WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceForecastValue.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceForecastValue exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceForecastValue exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceForecastValuePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"date"
			});
}