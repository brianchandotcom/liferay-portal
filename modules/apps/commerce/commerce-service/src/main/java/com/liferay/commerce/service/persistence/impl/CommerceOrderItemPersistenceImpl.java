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

import com.liferay.commerce.exception.NoSuchOrderItemException;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderItemTable;
import com.liferay.commerce.model.impl.CommerceOrderItemImpl;
import com.liferay.commerce.model.impl.CommerceOrderItemModelImpl;
import com.liferay.commerce.service.persistence.CommerceOrderItemPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce order item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderItemPersistenceImpl
	extends BasePersistenceImpl<CommerceOrderItem>
	implements CommerceOrderItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceOrderItemUtil</code> to access the commerce order item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceOrderItemImpl.class.getName();

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
	 * Returns all the commerce order items where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCommerceOrderId(long commerceOrderId) {
		return findByCommerceOrderId(
			commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order items where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @return the range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {

		return findByCommerceOrderId(commerceOrderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order items where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order items where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		List<CommerceOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderItem commerceOrderItem : list) {
					if (commerceOrderId !=
							commerceOrderItem.getCommerceOrderId()) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERID_COMMERCEORDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				list = (List<CommerceOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce order item in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByCommerceOrderId_First(
			long commerceOrderId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByCommerceOrderId_First(
			commerceOrderId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce order item in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		List<CommerceOrderItem> list = findByCommerceOrderId(
			commerceOrderId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order item in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByCommerceOrderId_Last(
			long commerceOrderId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByCommerceOrderId_Last(
			commerceOrderId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce order item in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		int count = countByCommerceOrderId(commerceOrderId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderItem> list = findByCommerceOrderId(
			commerceOrderId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order items before and after the current commerce order item in the ordered set where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderItemId the primary key of the current commerce order item
	 * @param commerceOrderId the commerce order ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem[] findByCommerceOrderId_PrevAndNext(
			long commerceOrderItemId, long commerceOrderId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByPrimaryKey(
			commerceOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderItem[] array = new CommerceOrderItemImpl[3];

			array[0] = getByCommerceOrderId_PrevAndNext(
				session, commerceOrderItem, commerceOrderId, orderByComparator,
				true);

			array[1] = commerceOrderItem;

			array[2] = getByCommerceOrderId_PrevAndNext(
				session, commerceOrderItem, commerceOrderId, orderByComparator,
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

	protected CommerceOrderItem getByCommerceOrderId_PrevAndNext(
		Session session, CommerceOrderItem commerceOrderItem,
		long commerceOrderId,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

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
			sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
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
						commerceOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order items where commerceOrderId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 */
	@Override
	public void removeByCommerceOrderId(long commerceOrderId) {
		for (CommerceOrderItem commerceOrderItem :
				findByCommerceOrderId(
					commerceOrderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceOrderItem);
		}
	}

	/**
	 * Returns the number of commerce order items where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByCommerceOrderId(long commerceOrderId) {
		FinderPath finderPath = _finderPathCountByCommerceOrderId;

		Object[] finderArgs = new Object[] {commerceOrderId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

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
			"commerceOrderItem.commerceOrderId = ?";

	private FinderPath _finderPathWithPaginationFindByCProductId;
	private FinderPath _finderPathWithoutPaginationFindByCProductId;
	private FinderPath _finderPathCountByCProductId;

	/**
	 * Returns all the commerce order items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCProductId(long CProductId) {
		return findByCProductId(
			CProductId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @return the range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCProductId(
		long CProductId, int start, int end) {

		return findByCProductId(CProductId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return findByCProductId(
			CProductId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCProductId;
				finderArgs = new Object[] {CProductId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCProductId;
			finderArgs = new Object[] {
				CProductId, start, end, orderByComparator
			};
		}

		List<CommerceOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderItem commerceOrderItem : list) {
					if (CProductId != commerceOrderItem.getCProductId()) {
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

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				list = (List<CommerceOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce order item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByCProductId_First(
			long CProductId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByCProductId_First(
			CProductId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce order item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByCProductId_First(
		long CProductId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		List<CommerceOrderItem> list = findByCProductId(
			CProductId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByCProductId_Last(
			long CProductId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByCProductId_Last(
			CProductId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce order item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByCProductId_Last(
		long CProductId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		int count = countByCProductId(CProductId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderItem> list = findByCProductId(
			CProductId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order items before and after the current commerce order item in the ordered set where CProductId = &#63;.
	 *
	 * @param commerceOrderItemId the primary key of the current commerce order item
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem[] findByCProductId_PrevAndNext(
			long commerceOrderItemId, long CProductId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByPrimaryKey(
			commerceOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderItem[] array = new CommerceOrderItemImpl[3];

			array[0] = getByCProductId_PrevAndNext(
				session, commerceOrderItem, CProductId, orderByComparator,
				true);

			array[1] = commerceOrderItem;

			array[2] = getByCProductId_PrevAndNext(
				session, commerceOrderItem, CProductId, orderByComparator,
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

	protected CommerceOrderItem getByCProductId_PrevAndNext(
		Session session, CommerceOrderItem commerceOrderItem, long CProductId,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

		sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

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
			sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CProductId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order items where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	@Override
	public void removeByCProductId(long CProductId) {
		for (CommerceOrderItem commerceOrderItem :
				findByCProductId(
					CProductId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceOrderItem);
		}
	}

	/**
	 * Returns the number of commerce order items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByCProductId(long CProductId) {
		FinderPath finderPath = _finderPathCountByCProductId;

		Object[] finderArgs = new Object[] {CProductId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

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

	private static final String _FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2 =
		"commerceOrderItem.CProductId = ?";

	private FinderPath _finderPathWithPaginationFindByCPInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByCPInstanceId;
	private FinderPath _finderPathCountByCPInstanceId;

	/**
	 * Returns all the commerce order items where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCPInstanceId(long CPInstanceId) {
		return findByCPInstanceId(
			CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @return the range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end) {

		return findByCPInstanceId(CPInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return findByCPInstanceId(
			CPInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPInstanceId;
				finderArgs = new Object[] {CPInstanceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPInstanceId;
			finderArgs = new Object[] {
				CPInstanceId, start, end, orderByComparator
			};
		}

		List<CommerceOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderItem commerceOrderItem : list) {
					if (CPInstanceId != commerceOrderItem.getCPInstanceId()) {
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

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

				list = (List<CommerceOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce order item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByCPInstanceId_First(
			long CPInstanceId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByCPInstanceId_First(
			CPInstanceId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce order item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByCPInstanceId_First(
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		List<CommerceOrderItem> list = findByCPInstanceId(
			CPInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByCPInstanceId_Last(
			long CPInstanceId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByCPInstanceId_Last(
			CPInstanceId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce order item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByCPInstanceId_Last(
		long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		int count = countByCPInstanceId(CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderItem> list = findByCPInstanceId(
			CPInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order items before and after the current commerce order item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param commerceOrderItemId the primary key of the current commerce order item
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem[] findByCPInstanceId_PrevAndNext(
			long commerceOrderItemId, long CPInstanceId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByPrimaryKey(
			commerceOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderItem[] array = new CommerceOrderItemImpl[3];

			array[0] = getByCPInstanceId_PrevAndNext(
				session, commerceOrderItem, CPInstanceId, orderByComparator,
				true);

			array[1] = commerceOrderItem;

			array[2] = getByCPInstanceId_PrevAndNext(
				session, commerceOrderItem, CPInstanceId, orderByComparator,
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

	protected CommerceOrderItem getByCPInstanceId_PrevAndNext(
		Session session, CommerceOrderItem commerceOrderItem, long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

		sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

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
			sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order items where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByCPInstanceId(long CPInstanceId) {
		for (CommerceOrderItem commerceOrderItem :
				findByCPInstanceId(
					CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceOrderItem);
		}
	}

	/**
	 * Returns the number of commerce order items where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByCPInstanceId(long CPInstanceId) {
		FinderPath finderPath = _finderPathCountByCPInstanceId;

		Object[] finderArgs = new Object[] {CPInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPInstanceId);

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

	private static final String _FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2 =
		"commerceOrderItem.CPInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByParentCommerceOrderItemId;
	private FinderPath
		_finderPathWithoutPaginationFindByParentCommerceOrderItemId;
	private FinderPath _finderPathCountByParentCommerceOrderItemId;

	/**
	 * Returns all the commerce order items where parentCommerceOrderItemId = &#63;.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @return the matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByParentCommerceOrderItemId(
		long parentCommerceOrderItemId) {

		return findByParentCommerceOrderItemId(
			parentCommerceOrderItemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce order items where parentCommerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @return the range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByParentCommerceOrderItemId(
		long parentCommerceOrderItemId, int start, int end) {

		return findByParentCommerceOrderItemId(
			parentCommerceOrderItemId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order items where parentCommerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByParentCommerceOrderItemId(
		long parentCommerceOrderItemId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return findByParentCommerceOrderItemId(
			parentCommerceOrderItemId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order items where parentCommerceOrderItemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByParentCommerceOrderItemId(
		long parentCommerceOrderItemId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByParentCommerceOrderItemId;
				finderArgs = new Object[] {parentCommerceOrderItemId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByParentCommerceOrderItemId;
			finderArgs = new Object[] {
				parentCommerceOrderItemId, start, end, orderByComparator
			};
		}

		List<CommerceOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderItem commerceOrderItem : list) {
					if (parentCommerceOrderItemId !=
							commerceOrderItem.getParentCommerceOrderItemId()) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_PARENTCOMMERCEORDERITEMID_PARENTCOMMERCEORDERITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentCommerceOrderItemId);

				list = (List<CommerceOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce order item in the ordered set where parentCommerceOrderItemId = &#63;.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByParentCommerceOrderItemId_First(
			long parentCommerceOrderItemId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem =
			fetchByParentCommerceOrderItemId_First(
				parentCommerceOrderItemId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentCommerceOrderItemId=");
		sb.append(parentCommerceOrderItemId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce order item in the ordered set where parentCommerceOrderItemId = &#63;.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByParentCommerceOrderItemId_First(
		long parentCommerceOrderItemId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		List<CommerceOrderItem> list = findByParentCommerceOrderItemId(
			parentCommerceOrderItemId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order item in the ordered set where parentCommerceOrderItemId = &#63;.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByParentCommerceOrderItemId_Last(
			long parentCommerceOrderItemId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem =
			fetchByParentCommerceOrderItemId_Last(
				parentCommerceOrderItemId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("parentCommerceOrderItemId=");
		sb.append(parentCommerceOrderItemId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce order item in the ordered set where parentCommerceOrderItemId = &#63;.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByParentCommerceOrderItemId_Last(
		long parentCommerceOrderItemId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		int count = countByParentCommerceOrderItemId(parentCommerceOrderItemId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderItem> list = findByParentCommerceOrderItemId(
			parentCommerceOrderItemId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order items before and after the current commerce order item in the ordered set where parentCommerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the primary key of the current commerce order item
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem[] findByParentCommerceOrderItemId_PrevAndNext(
			long commerceOrderItemId, long parentCommerceOrderItemId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByPrimaryKey(
			commerceOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderItem[] array = new CommerceOrderItemImpl[3];

			array[0] = getByParentCommerceOrderItemId_PrevAndNext(
				session, commerceOrderItem, parentCommerceOrderItemId,
				orderByComparator, true);

			array[1] = commerceOrderItem;

			array[2] = getByParentCommerceOrderItemId_PrevAndNext(
				session, commerceOrderItem, parentCommerceOrderItemId,
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

	protected CommerceOrderItem getByParentCommerceOrderItemId_PrevAndNext(
		Session session, CommerceOrderItem commerceOrderItem,
		long parentCommerceOrderItemId,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

		sb.append(
			_FINDER_COLUMN_PARENTCOMMERCEORDERITEMID_PARENTCOMMERCEORDERITEMID_2);

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
			sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(parentCommerceOrderItemId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order items where parentCommerceOrderItemId = &#63; from the database.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 */
	@Override
	public void removeByParentCommerceOrderItemId(
		long parentCommerceOrderItemId) {

		for (CommerceOrderItem commerceOrderItem :
				findByParentCommerceOrderItemId(
					parentCommerceOrderItemId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceOrderItem);
		}
	}

	/**
	 * Returns the number of commerce order items where parentCommerceOrderItemId = &#63;.
	 *
	 * @param parentCommerceOrderItemId the parent commerce order item ID
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByParentCommerceOrderItemId(
		long parentCommerceOrderItemId) {

		FinderPath finderPath = _finderPathCountByParentCommerceOrderItemId;

		Object[] finderArgs = new Object[] {parentCommerceOrderItemId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

			sb.append(
				_FINDER_COLUMN_PARENTCOMMERCEORDERITEMID_PARENTCOMMERCEORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(parentCommerceOrderItemId);

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
		_FINDER_COLUMN_PARENTCOMMERCEORDERITEMID_PARENTCOMMERCEORDERITEMID_2 =
			"commerceOrderItem.parentCommerceOrderItemId = ?";

	private FinderPath _finderPathFetchByBookedQuantityId;
	private FinderPath _finderPathCountByBookedQuantityId;

	/**
	 * Returns the commerce order item where bookedQuantityId = &#63; or throws a <code>NoSuchOrderItemException</code> if it could not be found.
	 *
	 * @param bookedQuantityId the booked quantity ID
	 * @return the matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByBookedQuantityId(long bookedQuantityId)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByBookedQuantityId(
			bookedQuantityId);

		if (commerceOrderItem == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("bookedQuantityId=");
			sb.append(bookedQuantityId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOrderItemException(sb.toString());
		}

		return commerceOrderItem;
	}

	/**
	 * Returns the commerce order item where bookedQuantityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param bookedQuantityId the booked quantity ID
	 * @return the matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByBookedQuantityId(long bookedQuantityId) {
		return fetchByBookedQuantityId(bookedQuantityId, true);
	}

	/**
	 * Returns the commerce order item where bookedQuantityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param bookedQuantityId the booked quantity ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByBookedQuantityId(
		long bookedQuantityId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {bookedQuantityId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBookedQuantityId, finderArgs);
		}

		if (result instanceof CommerceOrderItem) {
			CommerceOrderItem commerceOrderItem = (CommerceOrderItem)result;

			if (bookedQuantityId != commerceOrderItem.getBookedQuantityId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_BOOKEDQUANTITYID_BOOKEDQUANTITYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(bookedQuantityId);

				List<CommerceOrderItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBookedQuantityId, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {bookedQuantityId};
							}

							_log.warn(
								"CommerceOrderItemPersistenceImpl.fetchByBookedQuantityId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceOrderItem commerceOrderItem = list.get(0);

					result = commerceOrderItem;

					cacheResult(commerceOrderItem);
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
			return (CommerceOrderItem)result;
		}
	}

	/**
	 * Removes the commerce order item where bookedQuantityId = &#63; from the database.
	 *
	 * @param bookedQuantityId the booked quantity ID
	 * @return the commerce order item that was removed
	 */
	@Override
	public CommerceOrderItem removeByBookedQuantityId(long bookedQuantityId)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByBookedQuantityId(
			bookedQuantityId);

		return remove(commerceOrderItem);
	}

	/**
	 * Returns the number of commerce order items where bookedQuantityId = &#63;.
	 *
	 * @param bookedQuantityId the booked quantity ID
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByBookedQuantityId(long bookedQuantityId) {
		FinderPath finderPath = _finderPathCountByBookedQuantityId;

		Object[] finderArgs = new Object[] {bookedQuantityId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_BOOKEDQUANTITYID_BOOKEDQUANTITYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(bookedQuantityId);

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
		_FINDER_COLUMN_BOOKEDQUANTITYID_BOOKEDQUANTITYID_2 =
			"commerceOrderItem.bookedQuantityId = ?";

	private FinderPath _finderPathWithPaginationFindByC_I;
	private FinderPath _finderPathWithoutPaginationFindByC_I;
	private FinderPath _finderPathCountByC_I;

	/**
	 * Returns all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_I(
		long commerceOrderId, long CPInstanceId) {

		return findByC_I(
			commerceOrderId, CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @return the range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_I(
		long commerceOrderId, long CPInstanceId, int start, int end) {

		return findByC_I(commerceOrderId, CPInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_I(
		long commerceOrderId, long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return findByC_I(
			commerceOrderId, CPInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_I(
		long commerceOrderId, long CPInstanceId, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_I;
				finderArgs = new Object[] {commerceOrderId, CPInstanceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_I;
			finderArgs = new Object[] {
				commerceOrderId, CPInstanceId, start, end, orderByComparator
			};
		}

		List<CommerceOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderItem commerceOrderItem : list) {
					if ((commerceOrderId !=
							commerceOrderItem.getCommerceOrderId()) ||
						(CPInstanceId != commerceOrderItem.getCPInstanceId())) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_I_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_C_I_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(CPInstanceId);

				list = (List<CommerceOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByC_I_First(
			long commerceOrderId, long CPInstanceId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByC_I_First(
			commerceOrderId, CPInstanceId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByC_I_First(
		long commerceOrderId, long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		List<CommerceOrderItem> list = findByC_I(
			commerceOrderId, CPInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByC_I_Last(
			long commerceOrderId, long CPInstanceId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByC_I_Last(
			commerceOrderId, CPInstanceId, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByC_I_Last(
		long commerceOrderId, long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		int count = countByC_I(commerceOrderId, CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderItem> list = findByC_I(
			commerceOrderId, CPInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order items before and after the current commerce order item in the ordered set where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceOrderItemId the primary key of the current commerce order item
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem[] findByC_I_PrevAndNext(
			long commerceOrderItemId, long commerceOrderId, long CPInstanceId,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByPrimaryKey(
			commerceOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderItem[] array = new CommerceOrderItemImpl[3];

			array[0] = getByC_I_PrevAndNext(
				session, commerceOrderItem, commerceOrderId, CPInstanceId,
				orderByComparator, true);

			array[1] = commerceOrderItem;

			array[2] = getByC_I_PrevAndNext(
				session, commerceOrderItem, commerceOrderId, CPInstanceId,
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

	protected CommerceOrderItem getByC_I_PrevAndNext(
		Session session, CommerceOrderItem commerceOrderItem,
		long commerceOrderId, long CPInstanceId,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

		sb.append(_FINDER_COLUMN_C_I_COMMERCEORDERID_2);

		sb.append(_FINDER_COLUMN_C_I_CPINSTANCEID_2);

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
			sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderId);

		queryPos.add(CPInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByC_I(long commerceOrderId, long CPInstanceId) {
		for (CommerceOrderItem commerceOrderItem :
				findByC_I(
					commerceOrderId, CPInstanceId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceOrderItem);
		}
	}

	/**
	 * Returns the number of commerce order items where commerceOrderId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByC_I(long commerceOrderId, long CPInstanceId) {
		FinderPath finderPath = _finderPathCountByC_I;

		Object[] finderArgs = new Object[] {commerceOrderId, CPInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_I_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_C_I_CPINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(CPInstanceId);

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

	private static final String _FINDER_COLUMN_C_I_COMMERCEORDERID_2 =
		"commerceOrderItem.commerceOrderId = ? AND ";

	private static final String _FINDER_COLUMN_C_I_CPINSTANCEID_2 =
		"commerceOrderItem.CPInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the commerce order items where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @return the matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_S(
		long commerceOrderId, boolean subscription) {

		return findByC_S(
			commerceOrderId, subscription, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce order items where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @return the range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_S(
		long commerceOrderId, boolean subscription, int start, int end) {

		return findByC_S(commerceOrderId, subscription, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_S(
		long commerceOrderId, boolean subscription, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return findByC_S(
			commerceOrderId, subscription, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order items where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findByC_S(
		long commerceOrderId, boolean subscription, int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {commerceOrderId, subscription};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				commerceOrderId, subscription, start, end, orderByComparator
			};
		}

		List<CommerceOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderItem commerceOrderItem : list) {
					if ((commerceOrderId !=
							commerceOrderItem.getCommerceOrderId()) ||
						(subscription != commerceOrderItem.isSubscription())) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_C_S_SUBSCRIPTION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(subscription);

				list = (List<CommerceOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByC_S_First(
			long commerceOrderId, boolean subscription,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByC_S_First(
			commerceOrderId, subscription, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", subscription=");
		sb.append(subscription);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce order item in the ordered set where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByC_S_First(
		long commerceOrderId, boolean subscription,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		List<CommerceOrderItem> list = findByC_S(
			commerceOrderId, subscription, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByC_S_Last(
			long commerceOrderId, boolean subscription,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByC_S_Last(
			commerceOrderId, subscription, orderByComparator);

		if (commerceOrderItem != null) {
			return commerceOrderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderId=");
		sb.append(commerceOrderId);

		sb.append(", subscription=");
		sb.append(subscription);

		sb.append("}");

		throw new NoSuchOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce order item in the ordered set where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByC_S_Last(
		long commerceOrderId, boolean subscription,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		int count = countByC_S(commerceOrderId, subscription);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderItem> list = findByC_S(
			commerceOrderId, subscription, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order items before and after the current commerce order item in the ordered set where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * @param commerceOrderItemId the primary key of the current commerce order item
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem[] findByC_S_PrevAndNext(
			long commerceOrderItemId, long commerceOrderId,
			boolean subscription,
			OrderByComparator<CommerceOrderItem> orderByComparator)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByPrimaryKey(
			commerceOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderItem[] array = new CommerceOrderItemImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, commerceOrderItem, commerceOrderId, subscription,
				orderByComparator, true);

			array[1] = commerceOrderItem;

			array[2] = getByC_S_PrevAndNext(
				session, commerceOrderItem, commerceOrderId, subscription,
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

	protected CommerceOrderItem getByC_S_PrevAndNext(
		Session session, CommerceOrderItem commerceOrderItem,
		long commerceOrderId, boolean subscription,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMMERCEORDERID_2);

		sb.append(_FINDER_COLUMN_C_S_SUBSCRIPTION_2);

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
			sb.append(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderId);

		queryPos.add(subscription);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order items where commerceOrderId = &#63; and subscription = &#63; from the database.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 */
	@Override
	public void removeByC_S(long commerceOrderId, boolean subscription) {
		for (CommerceOrderItem commerceOrderItem :
				findByC_S(
					commerceOrderId, subscription, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceOrderItem);
		}
	}

	/**
	 * Returns the number of commerce order items where commerceOrderId = &#63; and subscription = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param subscription the subscription
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByC_S(long commerceOrderId, boolean subscription) {
		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {commerceOrderId, subscription};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMMERCEORDERID_2);

			sb.append(_FINDER_COLUMN_C_S_SUBSCRIPTION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderId);

				queryPos.add(subscription);

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

	private static final String _FINDER_COLUMN_C_S_COMMERCEORDERID_2 =
		"commerceOrderItem.commerceOrderId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SUBSCRIPTION_2 =
		"commerceOrderItem.subscription = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce order item where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderItemException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order item
	 * @throws NoSuchOrderItemException if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceOrderItem == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOrderItemException(sb.toString());
		}

		return commerceOrderItem;
	}

	/**
	 * Returns the commerce order item where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce order item where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order item, or <code>null</code> if a matching commerce order item could not be found
	 */
	@Override
	public CommerceOrderItem fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof CommerceOrderItem) {
			CommerceOrderItem commerceOrderItem = (CommerceOrderItem)result;

			if ((companyId != commerceOrderItem.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceOrderItem.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<CommerceOrderItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CommerceOrderItemPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceOrderItem commerceOrderItem = list.get(0);

					result = commerceOrderItem;

					cacheResult(commerceOrderItem);
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
			return (CommerceOrderItem)result;
		}
	}

	/**
	 * Removes the commerce order item where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order item that was removed
	 */
	@Override
	public CommerceOrderItem removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceOrderItem);
	}

	/**
	 * Returns the number of commerce order items where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order items
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"commerceOrderItem.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceOrderItem.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceOrderItem.externalReferenceCode IS NULL OR commerceOrderItem.externalReferenceCode = '')";

	public CommerceOrderItemPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"deliverySubscriptionTypeSettings", "deliverySubTypeSettings");
		dbColumnNames.put(
			"discountPercentageLevel1WithTaxAmount",
			"discountPctLevel1WithTaxAmount");
		dbColumnNames.put(
			"discountPercentageLevel2WithTaxAmount",
			"discountPctLevel2WithTaxAmount");
		dbColumnNames.put(
			"discountPercentageLevel3WithTaxAmount",
			"discountPctLevel3WithTaxAmount");
		dbColumnNames.put(
			"discountPercentageLevel4WithTaxAmount",
			"discountPctLevel4WithTaxAmount");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceOrderItem.class);

		setModelImplClass(CommerceOrderItemImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceOrderItemTable.INSTANCE);
	}

	/**
	 * Caches the commerce order item in the entity cache if it is enabled.
	 *
	 * @param commerceOrderItem the commerce order item
	 */
	@Override
	public void cacheResult(CommerceOrderItem commerceOrderItem) {
		entityCache.putResult(
			CommerceOrderItemImpl.class, commerceOrderItem.getPrimaryKey(),
			commerceOrderItem);

		finderCache.putResult(
			_finderPathFetchByBookedQuantityId,
			new Object[] {commerceOrderItem.getBookedQuantityId()},
			commerceOrderItem);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceOrderItem.getCompanyId(),
				commerceOrderItem.getExternalReferenceCode()
			},
			commerceOrderItem);
	}

	/**
	 * Caches the commerce order items in the entity cache if it is enabled.
	 *
	 * @param commerceOrderItems the commerce order items
	 */
	@Override
	public void cacheResult(List<CommerceOrderItem> commerceOrderItems) {
		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (entityCache.getResult(
					CommerceOrderItemImpl.class,
					commerceOrderItem.getPrimaryKey()) == null) {

				cacheResult(commerceOrderItem);
			}
		}
	}

	/**
	 * Clears the cache for all commerce order items.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderItemImpl.class);

		finderCache.clearCache(CommerceOrderItemImpl.class);
	}

	/**
	 * Clears the cache for the commerce order item.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceOrderItem commerceOrderItem) {
		entityCache.removeResult(
			CommerceOrderItemImpl.class, commerceOrderItem);
	}

	@Override
	public void clearCache(List<CommerceOrderItem> commerceOrderItems) {
		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			entityCache.removeResult(
				CommerceOrderItemImpl.class, commerceOrderItem);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceOrderItemImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceOrderItemImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceOrderItemModelImpl commerceOrderItemModelImpl) {

		Object[] args = new Object[] {
			commerceOrderItemModelImpl.getBookedQuantityId()
		};

		finderCache.putResult(
			_finderPathCountByBookedQuantityId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBookedQuantityId, args,
			commerceOrderItemModelImpl);

		args = new Object[] {
			commerceOrderItemModelImpl.getCompanyId(),
			commerceOrderItemModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceOrderItemModelImpl);
	}

	/**
	 * Creates a new commerce order item with the primary key. Does not add the commerce order item to the database.
	 *
	 * @param commerceOrderItemId the primary key for the new commerce order item
	 * @return the new commerce order item
	 */
	@Override
	public CommerceOrderItem create(long commerceOrderItemId) {
		CommerceOrderItem commerceOrderItem = new CommerceOrderItemImpl();

		commerceOrderItem.setNew(true);
		commerceOrderItem.setPrimaryKey(commerceOrderItemId);

		commerceOrderItem.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceOrderItem;
	}

	/**
	 * Removes the commerce order item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderItemId the primary key of the commerce order item
	 * @return the commerce order item that was removed
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem remove(long commerceOrderItemId)
		throws NoSuchOrderItemException {

		return remove((Serializable)commerceOrderItemId);
	}

	/**
	 * Removes the commerce order item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce order item
	 * @return the commerce order item that was removed
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem remove(Serializable primaryKey)
		throws NoSuchOrderItemException {

		Session session = null;

		try {
			session = openSession();

			CommerceOrderItem commerceOrderItem =
				(CommerceOrderItem)session.get(
					CommerceOrderItemImpl.class, primaryKey);

			if (commerceOrderItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderItemException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceOrderItem);
		}
		catch (NoSuchOrderItemException noSuchEntityException) {
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
	protected CommerceOrderItem removeImpl(
		CommerceOrderItem commerceOrderItem) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrderItem)) {
				commerceOrderItem = (CommerceOrderItem)session.get(
					CommerceOrderItemImpl.class,
					commerceOrderItem.getPrimaryKeyObj());
			}

			if (commerceOrderItem != null) {
				session.delete(commerceOrderItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceOrderItem != null) {
			clearCache(commerceOrderItem);
		}

		return commerceOrderItem;
	}

	@Override
	public CommerceOrderItem updateImpl(CommerceOrderItem commerceOrderItem) {
		boolean isNew = commerceOrderItem.isNew();

		if (!(commerceOrderItem instanceof CommerceOrderItemModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceOrderItem.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceOrderItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceOrderItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceOrderItem implementation " +
					commerceOrderItem.getClass());
		}

		CommerceOrderItemModelImpl commerceOrderItemModelImpl =
			(CommerceOrderItemModelImpl)commerceOrderItem;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceOrderItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrderItem.setCreateDate(now);
			}
			else {
				commerceOrderItem.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceOrderItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrderItem.setModifiedDate(now);
			}
			else {
				commerceOrderItem.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceOrderItem);
			}
			else {
				commerceOrderItem = (CommerceOrderItem)session.merge(
					commerceOrderItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceOrderItemImpl.class, commerceOrderItemModelImpl, false,
			true);

		cacheUniqueFindersCache(commerceOrderItemModelImpl);

		if (isNew) {
			commerceOrderItem.setNew(false);
		}

		commerceOrderItem.resetOriginalValues();

		return commerceOrderItem;
	}

	/**
	 * Returns the commerce order item with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order item
	 * @return the commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderItemException {

		CommerceOrderItem commerceOrderItem = fetchByPrimaryKey(primaryKey);

		if (commerceOrderItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderItemException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceOrderItem;
	}

	/**
	 * Returns the commerce order item with the primary key or throws a <code>NoSuchOrderItemException</code> if it could not be found.
	 *
	 * @param commerceOrderItemId the primary key of the commerce order item
	 * @return the commerce order item
	 * @throws NoSuchOrderItemException if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem findByPrimaryKey(long commerceOrderItemId)
		throws NoSuchOrderItemException {

		return findByPrimaryKey((Serializable)commerceOrderItemId);
	}

	/**
	 * Returns the commerce order item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderItemId the primary key of the commerce order item
	 * @return the commerce order item, or <code>null</code> if a commerce order item with the primary key could not be found
	 */
	@Override
	public CommerceOrderItem fetchByPrimaryKey(long commerceOrderItemId) {
		return fetchByPrimaryKey((Serializable)commerceOrderItemId);
	}

	/**
	 * Returns all the commerce order items.
	 *
	 * @return the commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @return the range of commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order items
	 * @param end the upper bound of the range of commerce order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order items
	 */
	@Override
	public List<CommerceOrderItem> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderItem> orderByComparator,
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

		List<CommerceOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderItem>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEORDERITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDERITEM;

				sql = sql.concat(CommerceOrderItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceOrderItem>)QueryUtil.list(
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
	 * Removes all the commerce order items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceOrderItem commerceOrderItem : findAll()) {
			remove(commerceOrderItem);
		}
	}

	/**
	 * Returns the number of commerce order items.
	 *
	 * @return the number of commerce order items
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEORDERITEM);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commerceOrderItemId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEORDERITEM;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceOrderItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order item persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceOrderItemPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceOrderItemModelArgumentsResolver(),
			new HashMapDictionary<>());

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

		_finderPathWithPaginationFindByCProductId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCProductId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CProductId"}, true);

		_finderPathWithoutPaginationFindByCProductId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			true);

		_finderPathCountByCProductId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			false);

		_finderPathWithPaginationFindByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPInstanceId"}, true);

		_finderPathWithoutPaginationFindByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPInstanceId",
			new String[] {Long.class.getName()}, new String[] {"CPInstanceId"},
			true);

		_finderPathCountByCPInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPInstanceId",
			new String[] {Long.class.getName()}, new String[] {"CPInstanceId"},
			false);

		_finderPathWithPaginationFindByParentCommerceOrderItemId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByParentCommerceOrderItemId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"parentCommerceOrderItemId"}, true);

		_finderPathWithoutPaginationFindByParentCommerceOrderItemId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByParentCommerceOrderItemId",
				new String[] {Long.class.getName()},
				new String[] {"parentCommerceOrderItemId"}, true);

		_finderPathCountByParentCommerceOrderItemId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentCommerceOrderItemId",
			new String[] {Long.class.getName()},
			new String[] {"parentCommerceOrderItemId"}, false);

		_finderPathFetchByBookedQuantityId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBookedQuantityId",
			new String[] {Long.class.getName()},
			new String[] {"bookedQuantityId"}, true);

		_finderPathCountByBookedQuantityId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBookedQuantityId", new String[] {Long.class.getName()},
			new String[] {"bookedQuantityId"}, false);

		_finderPathWithPaginationFindByC_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_I",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderId", "CPInstanceId"}, true);

		_finderPathWithoutPaginationFindByC_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_I",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceOrderId", "CPInstanceId"}, true);

		_finderPathCountByC_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_I",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceOrderId", "CPInstanceId"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderId", "subscription"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"commerceOrderId", "subscription"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"commerceOrderId", "subscription"}, false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceOrderItemImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEORDERITEM =
		"SELECT commerceOrderItem FROM CommerceOrderItem commerceOrderItem";

	private static final String _SQL_SELECT_COMMERCEORDERITEM_WHERE =
		"SELECT commerceOrderItem FROM CommerceOrderItem commerceOrderItem WHERE ";

	private static final String _SQL_COUNT_COMMERCEORDERITEM =
		"SELECT COUNT(commerceOrderItem) FROM CommerceOrderItem commerceOrderItem";

	private static final String _SQL_COUNT_COMMERCEORDERITEM_WHERE =
		"SELECT COUNT(commerceOrderItem) FROM CommerceOrderItem commerceOrderItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceOrderItem.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceOrderItem exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceOrderItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"deliverySubscriptionTypeSettings",
			"discountPercentageLevel1WithTaxAmount",
			"discountPercentageLevel2WithTaxAmount",
			"discountPercentageLevel3WithTaxAmount",
			"discountPercentageLevel4WithTaxAmount"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceOrderItemModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommerceOrderItemModelImpl commerceOrderItemModelImpl =
				(CommerceOrderItemModelImpl)baseModel;

			long columnBitmask = commerceOrderItemModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceOrderItemModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceOrderItemModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceOrderItemModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceOrderItemImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceOrderItemTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceOrderItemModelImpl commerceOrderItemModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceOrderItemModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceOrderItemModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}