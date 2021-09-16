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

import com.liferay.commerce.exception.NoSuchOrderPaymentException;
import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.commerce.model.CommerceOrderPaymentTable;
import com.liferay.commerce.model.impl.CommerceOrderPaymentImpl;
import com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl;
import com.liferay.commerce.service.persistence.CommerceOrderPaymentPersistence;
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
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
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
 * @generated
 */
public class CommerceOrderPaymentPersistenceImpl
	extends BasePersistenceImpl<CommerceOrderPayment>
	implements CommerceOrderPaymentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceOrderPaymentUtil</code> to access the commerce order payment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceOrderPaymentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceOrderId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceOrderId;
	private FinderPath _finderPathCountByCommerceOrderId;

	/**
	 * Returns all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId) {

		return findByCommerceOrderId(
			commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderPaymentModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderPaymentModelImpl</code>.
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

		return findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderPaymentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCommerceOrderId;
				finderArgs = new Object[] {commerceOrderId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceOrderId;
			finderArgs = new Object[] {
				commerceOrderId, start, end, orderByComparator
			};
		}

		List<CommerceOrderPayment> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderPayment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderPayment commerceOrderPayment : list) {
					if (commerceOrderId !=
							commerceOrderPayment.getCommerceOrderId()) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERPAYMENT_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderPaymentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				list = (List<CommerceOrderPayment>)QueryUtil.list(
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

		CommerceOrderPayment commerceOrderPayment =
			fetchByCommerceOrderId_First(commerceOrderId, orderByComparator);

		if (commerceOrderPayment != null) {
			return commerceOrderPayment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append("}");

		throw new NoSuchOrderPaymentException(sb.toString());
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

		List<CommerceOrderPayment> list = findByCommerceOrderId(
			commerceOrderId, 0, 1, orderByComparator);

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

		CommerceOrderPayment commerceOrderPayment = fetchByCommerceOrderId_Last(
			commerceOrderId, orderByComparator);

		if (commerceOrderPayment != null) {
			return commerceOrderPayment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append("}");

		throw new NoSuchOrderPaymentException(sb.toString());
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

		List<CommerceOrderPayment> list = findByCommerceOrderId(
			commerceOrderId, count - 1, count, orderByComparator);

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

		CommerceOrderPayment commerceOrderPayment = findByPrimaryKey(
			commerceOrderPaymentId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderPayment[] array = new CommerceOrderPaymentImpl[3];

			array[0] = getByCommerceOrderId_PrevAndNext(
				session, commerceOrderPayment, commerceOrderId,
				orderByComparator, true);

			array[1] = commerceOrderPayment;

			array[2] = getByCommerceOrderId_PrevAndNext(
				session, commerceOrderPayment, commerceOrderId,
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

	protected CommerceOrderPayment getByCommerceOrderId_PrevAndNext(
		Session session, CommerceOrderPayment commerceOrderPayment,
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERPAYMENT_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

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
			sb.append(CommerceOrderPaymentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderPayment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderPayment> list = query.list();

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
		for (CommerceOrderPayment commerceOrderPayment :
				findByCommerceOrderId(
					commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByCommerceOrderId;

		Object[] finderArgs = new Object[] {commerceOrderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERPAYMENT_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

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
		_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2 =
			"commerceOrderPayment.commerceOrderId = ?";

	public CommerceOrderPaymentPersistenceImpl() {
		setModelClass(CommerceOrderPayment.class);

		setModelImplClass(CommerceOrderPaymentImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceOrderPaymentTable.INSTANCE);
	}

	/**
	 * Caches the commerce order payment in the entity cache if it is enabled.
	 *
	 * @param commerceOrderPayment the commerce order payment
	 */
	@Override
	public void cacheResult(CommerceOrderPayment commerceOrderPayment) {
		entityCache.putResult(
			CommerceOrderPaymentImpl.class,
			commerceOrderPayment.getPrimaryKey(), commerceOrderPayment);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce order payments in the entity cache if it is enabled.
	 *
	 * @param commerceOrderPayments the commerce order payments
	 */
	@Override
	public void cacheResult(List<CommerceOrderPayment> commerceOrderPayments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceOrderPayments.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceOrderPayment commerceOrderPayment :
				commerceOrderPayments) {

			if (entityCache.getResult(
					CommerceOrderPaymentImpl.class,
					commerceOrderPayment.getPrimaryKey()) == null) {

				cacheResult(commerceOrderPayment);
			}
		}
	}

	/**
	 * Clears the cache for all commerce order payments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderPaymentImpl.class);

		finderCache.clearCache(CommerceOrderPaymentImpl.class);
	}

	/**
	 * Clears the cache for the commerce order payment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceOrderPayment commerceOrderPayment) {
		entityCache.removeResult(
			CommerceOrderPaymentImpl.class, commerceOrderPayment);
	}

	@Override
	public void clearCache(List<CommerceOrderPayment> commerceOrderPayments) {
		for (CommerceOrderPayment commerceOrderPayment :
				commerceOrderPayments) {

			entityCache.removeResult(
				CommerceOrderPaymentImpl.class, commerceOrderPayment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceOrderPaymentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceOrderPaymentImpl.class, primaryKey);
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
		CommerceOrderPayment commerceOrderPayment =
			new CommerceOrderPaymentImpl();

		commerceOrderPayment.setNew(true);
		commerceOrderPayment.setPrimaryKey(commerceOrderPaymentId);

		commerceOrderPayment.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			CommerceOrderPayment commerceOrderPayment =
				(CommerceOrderPayment)session.get(
					CommerceOrderPaymentImpl.class, primaryKey);

			if (commerceOrderPayment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderPaymentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceOrderPayment);
		}
		catch (NoSuchOrderPaymentException noSuchEntityException) {
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
	protected CommerceOrderPayment removeImpl(
		CommerceOrderPayment commerceOrderPayment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrderPayment)) {
				commerceOrderPayment = (CommerceOrderPayment)session.get(
					CommerceOrderPaymentImpl.class,
					commerceOrderPayment.getPrimaryKeyObj());
			}

			if (commerceOrderPayment != null) {
				session.delete(commerceOrderPayment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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

		boolean isNew = commerceOrderPayment.isNew();

		if (!(commerceOrderPayment instanceof CommerceOrderPaymentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceOrderPayment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceOrderPayment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceOrderPayment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceOrderPayment implementation " +
					commerceOrderPayment.getClass());
		}

		CommerceOrderPaymentModelImpl commerceOrderPaymentModelImpl =
			(CommerceOrderPaymentModelImpl)commerceOrderPayment;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceOrderPayment.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrderPayment.setCreateDate(date);
			}
			else {
				commerceOrderPayment.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceOrderPaymentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrderPayment.setModifiedDate(date);
			}
			else {
				commerceOrderPayment.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceOrderPayment);
			}
			else {
				commerceOrderPayment = (CommerceOrderPayment)session.merge(
					commerceOrderPayment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceOrderPaymentImpl.class, commerceOrderPaymentModelImpl,
			false, true);

		if (isNew) {
			commerceOrderPayment.setNew(false);
		}

		commerceOrderPayment.resetOriginalValues();

		return commerceOrderPayment;
	}

	/**
	 * Returns the commerce order payment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order payment
	 * @return the commerce order payment
	 * @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderPaymentException {

		CommerceOrderPayment commerceOrderPayment = fetchByPrimaryKey(
			primaryKey);

		if (commerceOrderPayment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderPaymentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceOrderPayment;
	}

	/**
	 * Returns the commerce order payment with the primary key or throws a <code>NoSuchOrderPaymentException</code> if it could not be found.
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
	 * @param commerceOrderPaymentId the primary key of the commerce order payment
	 * @return the commerce order payment, or <code>null</code> if a commerce order payment with the primary key could not be found
	 */
	@Override
	public CommerceOrderPayment fetchByPrimaryKey(long commerceOrderPaymentId) {
		return fetchByPrimaryKey((Serializable)commerceOrderPaymentId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderPaymentModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderPaymentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order payments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderPaymentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order payments
	 */
	@Override
	public List<CommerceOrderPayment> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
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

		List<CommerceOrderPayment> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderPayment>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEORDERPAYMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDERPAYMENT;

				sql = sql.concat(CommerceOrderPaymentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceOrderPayment>)QueryUtil.list(
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEORDERPAYMENT);

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
		return "commerceOrderPaymentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEORDERPAYMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceOrderPaymentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order payment persistence.
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

		_finderPathWithPaginationFindByCommerceOrderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceOrderId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderId"}, true);

		_finderPathWithoutPaginationFindByCommerceOrderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCommerceOrderId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderId"}, true);

		_finderPathCountByCommerceOrderId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCommerceOrderId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceOrderPaymentImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEORDERPAYMENT =
		"SELECT commerceOrderPayment FROM CommerceOrderPayment commerceOrderPayment";

	private static final String _SQL_SELECT_COMMERCEORDERPAYMENT_WHERE =
		"SELECT commerceOrderPayment FROM CommerceOrderPayment commerceOrderPayment WHERE ";

	private static final String _SQL_COUNT_COMMERCEORDERPAYMENT =
		"SELECT COUNT(commerceOrderPayment) FROM CommerceOrderPayment commerceOrderPayment";

	private static final String _SQL_COUNT_COMMERCEORDERPAYMENT_WHERE =
		"SELECT COUNT(commerceOrderPayment) FROM CommerceOrderPayment commerceOrderPayment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceOrderPayment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceOrderPayment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceOrderPayment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderPaymentPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}