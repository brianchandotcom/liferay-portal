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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchOrderPaymentException;
import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.commerce.model.impl.CommerceOrderPaymentImpl;
import com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl;
import com.liferay.commerce.service.persistence.CommerceOrderPaymentPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce order payment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPaymentPersistence
 * @see com.liferay.commerce.service.persistence.CommerceOrderPaymentUtil
 * @generated
 */
@ProviderType
public class CommerceOrderPaymentPersistenceImpl extends BasePersistenceImpl<CommerceOrderPayment>
	implements CommerceOrderPaymentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceOrderPaymentUtil} to access the commerce order payment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceOrderPaymentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderPaymentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderPaymentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEORDERID =
		new FinderPath(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderPaymentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceOrderId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID =
		new FinderPath(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentModelImpl.FINDER_CACHE_ENABLED,
			CommerceOrderPaymentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCommerceOrderId",
			new String[] { Long.class.getName() },
			CommerceOrderPaymentModelImpl.COMMERCEORDERID_COLUMN_BITMASK |
			CommerceOrderPaymentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEORDERID = new FinderPath(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId) {
		return findByCommerceOrderId(commerceOrderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @return the range of matching commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {
		return findByCommerceOrderId(commerceOrderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		return findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID;
			finderArgs = new Object[] { commerceOrderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEORDERID;
			finderArgs = new Object[] {
					commerceOrderId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceOrderPayment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceOrderPayment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderPayment commerceOrderPayment : list) {
					if ((commerceOrderId != commerceOrderPayment.getCommerceOrderId())) {
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

			query.append(_SQL_SELECT_COMMERCEORDERPAYMENT_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceOrderPaymentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceOrderId);

				if (!pagination) {
					list = (List<CommerceOrderPayment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceOrderPayment>)QueryUtil.list(q,
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
	 * Returns the first commerce order payment in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order payment
	 * @throws NoSuchOrderPaymentException if a matching commerce order payment could not be found
	 */
	@Override
	public CommerceOrderPayment findByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws NoSuchOrderPaymentException {
		CommerceOrderPayment commerceOrderPayment = fetchByCommerceOrderId_First(commerceOrderId,
				orderByComparator);

		if (commerceOrderPayment != null) {
			return commerceOrderPayment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceOrderId=");
		msg.append(commerceOrderId);

		msg.append("}");

		throw new NoSuchOrderPaymentException(msg.toString());
	}

	/**
	 * Returns the first commerce order payment in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order payment, or <code>null</code> if a matching commerce order payment could not be found
	 */
	@Override
	public CommerceOrderPayment fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		List<CommerceOrderPayment> list = findByCommerceOrderId(commerceOrderId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order payment in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order payment
	 * @throws NoSuchOrderPaymentException if a matching commerce order payment could not be found
	 */
	@Override
	public CommerceOrderPayment findByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws NoSuchOrderPaymentException {
		CommerceOrderPayment commerceOrderPayment = fetchByCommerceOrderId_Last(commerceOrderId,
				orderByComparator);

		if (commerceOrderPayment != null) {
			return commerceOrderPayment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceOrderId=");
		msg.append(commerceOrderId);

		msg.append("}");

		throw new NoSuchOrderPaymentException(msg.toString());
	}

	/**
	 * Returns the last commerce order payment in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order payment, or <code>null</code> if a matching commerce order payment could not be found
	 */
	@Override
	public CommerceOrderPayment fetchByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		int count = countByCommerceOrderId(commerceOrderId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderPayment> list = findByCommerceOrderId(commerceOrderId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order payments before and after the current commerce order payment in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderPaymentId the primary key of the current commerce order payment
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order payment
	 * @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderPaymentId, long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws NoSuchOrderPaymentException {
		CommerceOrderPayment commerceOrderPayment = findByPrimaryKey(commerceOrderPaymentId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderPayment[] array = new CommerceOrderPaymentImpl[3];

			array[0] = getByCommerceOrderId_PrevAndNext(session,
					commerceOrderPayment, commerceOrderId, orderByComparator,
					true);

			array[1] = commerceOrderPayment;

			array[2] = getByCommerceOrderId_PrevAndNext(session,
					commerceOrderPayment, commerceOrderId, orderByComparator,
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

	protected CommerceOrderPayment getByCommerceOrderId_PrevAndNext(
		Session session, CommerceOrderPayment commerceOrderPayment,
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEORDERPAYMENT_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

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
			query.append(CommerceOrderPaymentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceOrderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceOrderPayment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceOrderPayment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order payments where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 */
	@Override
	public void removeByCommerceOrderId(long commerceOrderId) {
		for (CommerceOrderPayment commerceOrderPayment : findByCommerceOrderId(
				commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceOrderPayment);
		}
	}

	/**
	 * Returns the number of commerce order payments where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce order payments
	 */
	@Override
	public int countByCommerceOrderId(long commerceOrderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEORDERID;

		Object[] finderArgs = new Object[] { commerceOrderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEORDERPAYMENT_WHERE);

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
		"commerceOrderPayment.commerceOrderId = ?";

	public CommerceOrderPaymentPersistenceImpl() {
		setModelClass(CommerceOrderPayment.class);
	}

	/**
	 * Caches the commerce order payment in the entity cache if it is enabled.
	 *
	 * @param commerceOrderPayment the commerce order payment
	 */
	@Override
	public void cacheResult(CommerceOrderPayment commerceOrderPayment) {
		entityCache.putResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentImpl.class,
			commerceOrderPayment.getPrimaryKey(), commerceOrderPayment);

		commerceOrderPayment.resetOriginalValues();
	}

	/**
	 * Caches the commerce order payments in the entity cache if it is enabled.
	 *
	 * @param commerceOrderPayments the commerce order payments
	 */
	@Override
	public void cacheResult(List<CommerceOrderPayment> commerceOrderPayments) {
		for (CommerceOrderPayment commerceOrderPayment : commerceOrderPayments) {
			if (entityCache.getResult(
						CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
						CommerceOrderPaymentImpl.class,
						commerceOrderPayment.getPrimaryKey()) == null) {
				cacheResult(commerceOrderPayment);
			}
			else {
				commerceOrderPayment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce order payments.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderPaymentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce order payment.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceOrderPayment commerceOrderPayment) {
		entityCache.removeResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentImpl.class, commerceOrderPayment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceOrderPayment> commerceOrderPayments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceOrderPayment commerceOrderPayment : commerceOrderPayments) {
			entityCache.removeResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
				CommerceOrderPaymentImpl.class,
				commerceOrderPayment.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce order payment with the primary key. Does not add the commerce order payment to the database.
	 *
	 * @param commerceOrderPaymentId the primary key for the new commerce order payment
	 * @return the new commerce order payment
	 */
	@Override
	public CommerceOrderPayment create(long commerceOrderPaymentId) {
		CommerceOrderPayment commerceOrderPayment = new CommerceOrderPaymentImpl();

		commerceOrderPayment.setNew(true);
		commerceOrderPayment.setPrimaryKey(commerceOrderPaymentId);

		commerceOrderPayment.setCompanyId(companyProvider.getCompanyId());

		return commerceOrderPayment;
	}

	/**
	 * Removes the commerce order payment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderPaymentId the primary key of the commerce order payment
	 * @return the commerce order payment that was removed
	 * @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment remove(long commerceOrderPaymentId)
		throws NoSuchOrderPaymentException {
		return remove((Serializable)commerceOrderPaymentId);
	}

	/**
	 * Removes the commerce order payment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce order payment
	 * @return the commerce order payment that was removed
	 * @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment remove(Serializable primaryKey)
		throws NoSuchOrderPaymentException {
		Session session = null;

		try {
			session = openSession();

			CommerceOrderPayment commerceOrderPayment = (CommerceOrderPayment)session.get(CommerceOrderPaymentImpl.class,
					primaryKey);

			if (commerceOrderPayment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderPaymentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceOrderPayment);
		}
		catch (NoSuchOrderPaymentException nsee) {
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
	protected CommerceOrderPayment removeImpl(
		CommerceOrderPayment commerceOrderPayment) {
		commerceOrderPayment = toUnwrappedModel(commerceOrderPayment);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrderPayment)) {
				commerceOrderPayment = (CommerceOrderPayment)session.get(CommerceOrderPaymentImpl.class,
						commerceOrderPayment.getPrimaryKeyObj());
			}

			if (commerceOrderPayment != null) {
				session.delete(commerceOrderPayment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceOrderPayment != null) {
			clearCache(commerceOrderPayment);
		}

		return commerceOrderPayment;
	}

	@Override
	public CommerceOrderPayment updateImpl(
		CommerceOrderPayment commerceOrderPayment) {
		commerceOrderPayment = toUnwrappedModel(commerceOrderPayment);

		boolean isNew = commerceOrderPayment.isNew();

		CommerceOrderPaymentModelImpl commerceOrderPaymentModelImpl = (CommerceOrderPaymentModelImpl)commerceOrderPayment;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceOrderPayment.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrderPayment.setCreateDate(now);
			}
			else {
				commerceOrderPayment.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceOrderPaymentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrderPayment.setModifiedDate(now);
			}
			else {
				commerceOrderPayment.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceOrderPayment.isNew()) {
				session.save(commerceOrderPayment);

				commerceOrderPayment.setNew(false);
			}
			else {
				commerceOrderPayment = (CommerceOrderPayment)session.merge(commerceOrderPayment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceOrderPaymentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceOrderPaymentModelImpl.getCommerceOrderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceOrderPaymentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceOrderPaymentModelImpl.getOriginalCommerceOrderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID,
					args);

				args = new Object[] {
						commerceOrderPaymentModelImpl.getCommerceOrderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEORDERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEORDERID,
					args);
			}
		}

		entityCache.putResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceOrderPaymentImpl.class,
			commerceOrderPayment.getPrimaryKey(), commerceOrderPayment, false);

		commerceOrderPayment.resetOriginalValues();

		return commerceOrderPayment;
	}

	protected CommerceOrderPayment toUnwrappedModel(
		CommerceOrderPayment commerceOrderPayment) {
		if (commerceOrderPayment instanceof CommerceOrderPaymentImpl) {
			return commerceOrderPayment;
		}

		CommerceOrderPaymentImpl commerceOrderPaymentImpl = new CommerceOrderPaymentImpl();

		commerceOrderPaymentImpl.setNew(commerceOrderPayment.isNew());
		commerceOrderPaymentImpl.setPrimaryKey(commerceOrderPayment.getPrimaryKey());

		commerceOrderPaymentImpl.setCommerceOrderPaymentId(commerceOrderPayment.getCommerceOrderPaymentId());
		commerceOrderPaymentImpl.setGroupId(commerceOrderPayment.getGroupId());
		commerceOrderPaymentImpl.setCompanyId(commerceOrderPayment.getCompanyId());
		commerceOrderPaymentImpl.setUserId(commerceOrderPayment.getUserId());
		commerceOrderPaymentImpl.setUserName(commerceOrderPayment.getUserName());
		commerceOrderPaymentImpl.setCreateDate(commerceOrderPayment.getCreateDate());
		commerceOrderPaymentImpl.setModifiedDate(commerceOrderPayment.getModifiedDate());
		commerceOrderPaymentImpl.setCommerceOrderId(commerceOrderPayment.getCommerceOrderId());
		commerceOrderPaymentImpl.setCommercePaymentMethodId(commerceOrderPayment.getCommercePaymentMethodId());
		commerceOrderPaymentImpl.setStatus(commerceOrderPayment.getStatus());
		commerceOrderPaymentImpl.setContent(commerceOrderPayment.getContent());

		return commerceOrderPaymentImpl;
	}

	/**
	 * Returns the commerce order payment with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order payment
	 * @return the commerce order payment
	 * @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderPaymentException {
		CommerceOrderPayment commerceOrderPayment = fetchByPrimaryKey(primaryKey);

		if (commerceOrderPayment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderPaymentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceOrderPayment;
	}

	/**
	 * Returns the commerce order payment with the primary key or throws a {@link NoSuchOrderPaymentException} if it could not be found.
	 *
	 * @param commerceOrderPaymentId the primary key of the commerce order payment
	 * @return the commerce order payment
	 * @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment findByPrimaryKey(long commerceOrderPaymentId)
		throws NoSuchOrderPaymentException {
		return findByPrimaryKey((Serializable)commerceOrderPaymentId);
	}

	/**
	 * Returns the commerce order payment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order payment
	 * @return the commerce order payment, or <code>null</code> if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
				CommerceOrderPaymentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceOrderPayment commerceOrderPayment = (CommerceOrderPayment)serializable;

		if (commerceOrderPayment == null) {
			Session session = null;

			try {
				session = openSession();

				commerceOrderPayment = (CommerceOrderPayment)session.get(CommerceOrderPaymentImpl.class,
						primaryKey);

				if (commerceOrderPayment != null) {
					cacheResult(commerceOrderPayment);
				}
				else {
					entityCache.putResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
						CommerceOrderPaymentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderPaymentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceOrderPayment;
	}

	/**
	 * Returns the commerce order payment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderPaymentId the primary key of the commerce order payment
	 * @return the commerce order payment, or <code>null</code> if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment fetchByPrimaryKey(long commerceOrderPaymentId) {
		return fetchByPrimaryKey((Serializable)commerceOrderPaymentId);
	}

	@Override
	public Map<Serializable, CommerceOrderPayment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceOrderPayment> map = new HashMap<Serializable, CommerceOrderPayment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceOrderPayment commerceOrderPayment = fetchByPrimaryKey(primaryKey);

			if (commerceOrderPayment != null) {
				map.put(primaryKey, commerceOrderPayment);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderPaymentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceOrderPayment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEORDERPAYMENT_WHERE_PKS_IN);

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

			for (CommerceOrderPayment commerceOrderPayment : (List<CommerceOrderPayment>)q.list()) {
				map.put(commerceOrderPayment.getPrimaryKeyObj(),
					commerceOrderPayment);

				cacheResult(commerceOrderPayment);

				uncachedPrimaryKeys.remove(commerceOrderPayment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceOrderPaymentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceOrderPaymentImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce order payments.
	 *
	 * @return the commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order payments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @return the range of commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order payments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findAll(int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order payments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findAll(int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
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

		List<CommerceOrderPayment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceOrderPayment>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEORDERPAYMENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDERPAYMENT;

				if (pagination) {
					sql = sql.concat(CommerceOrderPaymentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceOrderPayment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceOrderPayment>)QueryUtil.list(q,
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
	 * Removes all the commerce order payments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceOrderPayment commerceOrderPayment : findAll()) {
			remove(commerceOrderPayment);
		}
	}

	/**
	 * Returns the number of commerce order payments.
	 *
	 * @return the number of commerce order payments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEORDERPAYMENT);

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
		return CommerceOrderPaymentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order payment persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceOrderPaymentImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEORDERPAYMENT = "SELECT commerceOrderPayment FROM CommerceOrderPayment commerceOrderPayment";
	private static final String _SQL_SELECT_COMMERCEORDERPAYMENT_WHERE_PKS_IN = "SELECT commerceOrderPayment FROM CommerceOrderPayment commerceOrderPayment WHERE commerceOrderPaymentId IN (";
	private static final String _SQL_SELECT_COMMERCEORDERPAYMENT_WHERE = "SELECT commerceOrderPayment FROM CommerceOrderPayment commerceOrderPayment WHERE ";
	private static final String _SQL_COUNT_COMMERCEORDERPAYMENT = "SELECT COUNT(commerceOrderPayment) FROM CommerceOrderPayment commerceOrderPayment";
	private static final String _SQL_COUNT_COMMERCEORDERPAYMENT_WHERE = "SELECT COUNT(commerceOrderPayment) FROM CommerceOrderPayment commerceOrderPayment WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceOrderPayment.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceOrderPayment exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceOrderPayment exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceOrderPaymentPersistenceImpl.class);
}