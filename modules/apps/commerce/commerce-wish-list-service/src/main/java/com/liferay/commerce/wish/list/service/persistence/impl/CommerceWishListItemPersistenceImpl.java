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

package com.liferay.commerce.wish.list.service.persistence.impl;

import com.liferay.commerce.wish.list.exception.NoSuchWishListItemException;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.model.CommerceWishListItemTable;
import com.liferay.commerce.wish.list.model.impl.CommerceWishListItemImpl;
import com.liferay.commerce.wish.list.model.impl.CommerceWishListItemModelImpl;
import com.liferay.commerce.wish.list.service.persistence.CommerceWishListItemPersistence;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce wish list item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @generated
 */
public class CommerceWishListItemPersistenceImpl
	extends BasePersistenceImpl<CommerceWishListItem>
	implements CommerceWishListItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceWishListItemUtil</code> to access the commerce wish list item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceWishListItemImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceWishListId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceWishListId;
	private FinderPath _finderPathCountByCommerceWishListId;

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId) {

		return findByCommerceWishListId(
			commerceWishListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end) {

		return findByCommerceWishListId(commerceWishListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return findByCommerceWishListId(
			commerceWishListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceWishListId;
				finderArgs = new Object[] {commerceWishListId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceWishListId;
			finderArgs = new Object[] {
				commerceWishListId, start, end, orderByComparator
			};
		}

		List<CommerceWishListItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if (commerceWishListId !=
							commerceWishListItem.getCommerceWishListId()) {

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

			sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

				list = (List<CommerceWishListItem>)QueryUtil.list(
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
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCommerceWishListId_First(
			long commerceWishListId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem =
			fetchByCommerceWishListId_First(
				commerceWishListId, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceWishListId=");
		sb.append(commerceWishListId);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCommerceWishListId_First(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		List<CommerceWishListItem> list = findByCommerceWishListId(
			commerceWishListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCommerceWishListId_Last(
			long commerceWishListId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem =
			fetchByCommerceWishListId_Last(
				commerceWishListId, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceWishListId=");
		sb.append(commerceWishListId);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCommerceWishListId_Last(
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		int count = countByCommerceWishListId(commerceWishListId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishListItem> list = findByCommerceWishListId(
			commerceWishListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param commerceWishListId the commerce wish list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem[] findByCommerceWishListId_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = findByPrimaryKey(
			commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCommerceWishListId_PrevAndNext(
				session, commerceWishListItem, commerceWishListId,
				orderByComparator, true);

			array[1] = commerceWishListItem;

			array[2] = getByCommerceWishListId_PrevAndNext(
				session, commerceWishListItem, commerceWishListId,
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

	protected CommerceWishListItem getByCommerceWishListId_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2);

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
			sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceWishListId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishListItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishListItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 */
	@Override
	public void removeByCommerceWishListId(long commerceWishListId) {
		for (CommerceWishListItem commerceWishListItem :
				findByCommerceWishListId(
					commerceWishListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCommerceWishListId(long commerceWishListId) {
		FinderPath finderPath = _finderPathCountByCommerceWishListId;

		Object[] finderArgs = new Object[] {commerceWishListId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

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
		_FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2 =
			"commerceWishListItem.commerceWishListId = ?";

	private FinderPath _finderPathWithPaginationFindByCPInstanceUuid;
	private FinderPath _finderPathWithoutPaginationFindByCPInstanceUuid;
	private FinderPath _finderPathCountByCPInstanceUuid;

	/**
	 * Returns all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid) {

		return findByCPInstanceUuid(
			CPInstanceUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end) {

		return findByCPInstanceUuid(CPInstanceUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return findByCPInstanceUuid(
			CPInstanceUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPInstanceUuid;
				finderArgs = new Object[] {CPInstanceUuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPInstanceUuid;
			finderArgs = new Object[] {
				CPInstanceUuid, start, end, orderByComparator
			};
		}

		List<CommerceWishListItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if (!CPInstanceUuid.equals(
							commerceWishListItem.getCPInstanceUuid())) {

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

			sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				list = (List<CommerceWishListItem>)QueryUtil.list(
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
	 * Returns the first commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCPInstanceUuid_First(
			String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCPInstanceUuid_First(
			CPInstanceUuid, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCPInstanceUuid_First(
		String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		List<CommerceWishListItem> list = findByCPInstanceUuid(
			CPInstanceUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCPInstanceUuid_Last(
			String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCPInstanceUuid_Last(
			CPInstanceUuid, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCPInstanceUuid_Last(
		String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		int count = countByCPInstanceUuid(CPInstanceUuid);

		if (count == 0) {
			return null;
		}

		List<CommerceWishListItem> list = findByCPInstanceUuid(
			CPInstanceUuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem[] findByCPInstanceUuid_PrevAndNext(
			long commerceWishListItemId, String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		CommerceWishListItem commerceWishListItem = findByPrimaryKey(
			commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCPInstanceUuid_PrevAndNext(
				session, commerceWishListItem, CPInstanceUuid,
				orderByComparator, true);

			array[1] = commerceWishListItem;

			array[2] = getByCPInstanceUuid_PrevAndNext(
				session, commerceWishListItem, CPInstanceUuid,
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

	protected CommerceWishListItem getByCPInstanceUuid_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

		boolean bindCPInstanceUuid = false;

		if (CPInstanceUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3);
		}
		else {
			bindCPInstanceUuid = true;

			sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2);
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
			sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCPInstanceUuid) {
			queryPos.add(CPInstanceUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishListItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishListItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish list items where CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 */
	@Override
	public void removeByCPInstanceUuid(String CPInstanceUuid) {
		for (CommerceWishListItem commerceWishListItem :
				findByCPInstanceUuid(
					CPInstanceUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCPInstanceUuid(String CPInstanceUuid) {
		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		FinderPath finderPath = _finderPathCountByCPInstanceUuid;

		Object[] finderArgs = new Object[] {CPInstanceUuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
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

	private static final String _FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2 =
		"commerceWishListItem.CPInstanceUuid = ?";

	private static final String _FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3 =
		"(commerceWishListItem.CPInstanceUuid IS NULL OR commerceWishListItem.CPInstanceUuid = '')";

	private FinderPath _finderPathWithPaginationFindByCProductId;
	private FinderPath _finderPathWithoutPaginationFindByCProductId;
	private FinderPath _finderPathCountByCProductId;

	/**
	 * Returns all the commerce wish list items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCProductId(long CProductId) {
		return findByCProductId(
			CProductId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end) {

		return findByCProductId(CProductId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return findByCProductId(
			CProductId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCProductId(
		long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		List<CommerceWishListItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if (CProductId != commerceWishListItem.getCProductId()) {
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

			sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				list = (List<CommerceWishListItem>)QueryUtil.list(
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
	 * Returns the first commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCProductId_First(
			long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCProductId_First(
			CProductId, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCProductId_First(
		long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		List<CommerceWishListItem> list = findByCProductId(
			CProductId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCProductId_Last(
			long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCProductId_Last(
			CProductId, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCProductId_Last(
		long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		int count = countByCProductId(CProductId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishListItem> list = findByCProductId(
			CProductId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CProductId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem[] findByCProductId_PrevAndNext(
			long commerceWishListItemId, long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = findByPrimaryKey(
			commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCProductId_PrevAndNext(
				session, commerceWishListItem, CProductId, orderByComparator,
				true);

			array[1] = commerceWishListItem;

			array[2] = getByCProductId_PrevAndNext(
				session, commerceWishListItem, CProductId, orderByComparator,
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

	protected CommerceWishListItem getByCProductId_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

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
			sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
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
						commerceWishListItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishListItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish list items where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 */
	@Override
	public void removeByCProductId(long CProductId) {
		for (CommerceWishListItem commerceWishListItem :
				findByCProductId(
					CProductId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCProductId(long CProductId) {
		FinderPath finderPath = _finderPathCountByCProductId;

		Object[] finderArgs = new Object[] {CProductId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

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
		"commerceWishListItem.CProductId = ?";

	private FinderPath _finderPathWithPaginationFindByCW_CPI;
	private FinderPath _finderPathWithoutPaginationFindByCW_CPI;
	private FinderPath _finderPathCountByCW_CPI;

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid) {

		return findByCW_CPI(
			commerceWishListId, CPInstanceUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end) {

		return findByCW_CPI(
			commerceWishListId, CPInstanceUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return findByCW_CPI(
			commerceWishListId, CPInstanceUuid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CPI(
		long commerceWishListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCW_CPI;
				finderArgs = new Object[] {commerceWishListId, CPInstanceUuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCW_CPI;
			finderArgs = new Object[] {
				commerceWishListId, CPInstanceUuid, start, end,
				orderByComparator
			};
		}

		List<CommerceWishListItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if ((commerceWishListId !=
							commerceWishListItem.getCommerceWishListId()) ||
						!CPInstanceUuid.equals(
							commerceWishListItem.getCPInstanceUuid())) {

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

			sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_CW_CPI_COMMERCEWISHLISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				list = (List<CommerceWishListItem>)QueryUtil.list(
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
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCW_CPI_First(
			long commerceWishListId, String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCW_CPI_First(
			commerceWishListId, CPInstanceUuid, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceWishListId=");
		sb.append(commerceWishListId);

		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCW_CPI_First(
		long commerceWishListId, String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		List<CommerceWishListItem> list = findByCW_CPI(
			commerceWishListId, CPInstanceUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCW_CPI_Last(
			long commerceWishListId, String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCW_CPI_Last(
			commerceWishListId, CPInstanceUuid, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceWishListId=");
		sb.append(commerceWishListId);

		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCW_CPI_Last(
		long commerceWishListId, String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		int count = countByCW_CPI(commerceWishListId, CPInstanceUuid);

		if (count == 0) {
			return null;
		}

		List<CommerceWishListItem> list = findByCW_CPI(
			commerceWishListId, CPInstanceUuid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem[] findByCW_CPI_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			String CPInstanceUuid,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		CommerceWishListItem commerceWishListItem = findByPrimaryKey(
			commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCW_CPI_PrevAndNext(
				session, commerceWishListItem, commerceWishListId,
				CPInstanceUuid, orderByComparator, true);

			array[1] = commerceWishListItem;

			array[2] = getByCW_CPI_PrevAndNext(
				session, commerceWishListItem, commerceWishListId,
				CPInstanceUuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceWishListItem getByCW_CPI_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		long commerceWishListId, String CPInstanceUuid,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

		sb.append(_FINDER_COLUMN_CW_CPI_COMMERCEWISHLISTID_2);

		boolean bindCPInstanceUuid = false;

		if (CPInstanceUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_3);
		}
		else {
			bindCPInstanceUuid = true;

			sb.append(_FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_2);
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
			sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceWishListId);

		if (bindCPInstanceUuid) {
			queryPos.add(CPInstanceUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishListItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishListItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 */
	@Override
	public void removeByCW_CPI(long commerceWishListId, String CPInstanceUuid) {
		for (CommerceWishListItem commerceWishListItem :
				findByCW_CPI(
					commerceWishListId, CPInstanceUuid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCW_CPI(long commerceWishListId, String CPInstanceUuid) {
		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		FinderPath finderPath = _finderPathCountByCW_CPI;

		Object[] finderArgs = new Object[] {commerceWishListId, CPInstanceUuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_CW_CPI_COMMERCEWISHLISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
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

	private static final String _FINDER_COLUMN_CW_CPI_COMMERCEWISHLISTID_2 =
		"commerceWishListItem.commerceWishListId = ? AND ";

	private static final String _FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_2 =
		"commerceWishListItem.CPInstanceUuid = ?";

	private static final String _FINDER_COLUMN_CW_CPI_CPINSTANCEUUID_3 =
		"(commerceWishListItem.CPInstanceUuid IS NULL OR commerceWishListItem.CPInstanceUuid = '')";

	private FinderPath _finderPathWithPaginationFindByCW_CP;
	private FinderPath _finderPathWithoutPaginationFindByCW_CP;
	private FinderPath _finderPathCountByCW_CP;

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId) {

		return findByCW_CP(
			commerceWishListId, CProductId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end) {

		return findByCW_CP(commerceWishListId, CProductId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return findByCW_CP(
			commerceWishListId, CProductId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCW_CP(
		long commerceWishListId, long CProductId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCW_CP;
				finderArgs = new Object[] {commerceWishListId, CProductId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCW_CP;
			finderArgs = new Object[] {
				commerceWishListId, CProductId, start, end, orderByComparator
			};
		}

		List<CommerceWishListItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if ((commerceWishListId !=
							commerceWishListItem.getCommerceWishListId()) ||
						(CProductId != commerceWishListItem.getCProductId())) {

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

			sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_CW_CP_COMMERCEWISHLISTID_2);

			sb.append(_FINDER_COLUMN_CW_CP_CPRODUCTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

				queryPos.add(CProductId);

				list = (List<CommerceWishListItem>)QueryUtil.list(
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
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCW_CP_First(
			long commerceWishListId, long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCW_CP_First(
			commerceWishListId, CProductId, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceWishListId=");
		sb.append(commerceWishListId);

		sb.append(", CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCW_CP_First(
		long commerceWishListId, long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		List<CommerceWishListItem> list = findByCW_CP(
			commerceWishListId, CProductId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCW_CP_Last(
			long commerceWishListId, long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCW_CP_Last(
			commerceWishListId, CProductId, orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceWishListId=");
		sb.append(commerceWishListId);

		sb.append(", CProductId=");
		sb.append(CProductId);

		sb.append("}");

		throw new NoSuchWishListItemException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCW_CP_Last(
		long commerceWishListId, long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		int count = countByCW_CP(commerceWishListId, CProductId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishListItem> list = findByCW_CP(
			commerceWishListId, CProductId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem[] findByCW_CP_PrevAndNext(
			long commerceWishListItemId, long commerceWishListId,
			long CProductId,
			OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = findByPrimaryKey(
			commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCW_CP_PrevAndNext(
				session, commerceWishListItem, commerceWishListId, CProductId,
				orderByComparator, true);

			array[1] = commerceWishListItem;

			array[2] = getByCW_CP_PrevAndNext(
				session, commerceWishListItem, commerceWishListId, CProductId,
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

	protected CommerceWishListItem getByCW_CP_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		long commerceWishListId, long CProductId,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

		sb.append(_FINDER_COLUMN_CW_CP_COMMERCEWISHLISTID_2);

		sb.append(_FINDER_COLUMN_CW_CP_CPRODUCTID_2);

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
			sb.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceWishListId);

		queryPos.add(CProductId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishListItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishListItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish list items where commerceWishListId = &#63; and CProductId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 */
	@Override
	public void removeByCW_CP(long commerceWishListId, long CProductId) {
		for (CommerceWishListItem commerceWishListItem :
				findByCW_CP(
					commerceWishListId, CProductId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCW_CP(long commerceWishListId, long CProductId) {
		FinderPath finderPath = _finderPathCountByCW_CP;

		Object[] finderArgs = new Object[] {commerceWishListId, CProductId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_CW_CP_COMMERCEWISHLISTID_2);

			sb.append(_FINDER_COLUMN_CW_CP_CPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

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

	private static final String _FINDER_COLUMN_CW_CP_COMMERCEWISHLISTID_2 =
		"commerceWishListItem.commerceWishListId = ? AND ";

	private static final String _FINDER_COLUMN_CW_CP_CPRODUCTID_2 =
		"commerceWishListItem.CProductId = ?";

	private FinderPath _finderPathFetchByCW_CPI_CP;
	private FinderPath _finderPathCountByCW_CPI_CP;

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or throws a <code>NoSuchWishListItemException</code> if it could not be found.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCW_CPI_CP(
			long commerceWishListId, String CPInstanceUuid, long CProductId)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId);

		if (commerceWishListItem == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceWishListId=");
			sb.append(commerceWishListId);

			sb.append(", CPInstanceUuid=");
			sb.append(CPInstanceUuid);

			sb.append(", CProductId=");
			sb.append(CProductId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchWishListItemException(sb.toString());
		}

		return commerceWishListItem;
	}

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId) {

		return fetchByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId, true);
	}

	/**
	 * Returns the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId,
		boolean useFinderCache) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				commerceWishListId, CPInstanceUuid, CProductId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCW_CPI_CP, finderArgs);
		}

		if (result instanceof CommerceWishListItem) {
			CommerceWishListItem commerceWishListItem =
				(CommerceWishListItem)result;

			if ((commerceWishListId !=
					commerceWishListItem.getCommerceWishListId()) ||
				!Objects.equals(
					CPInstanceUuid, commerceWishListItem.getCPInstanceUuid()) ||
				(CProductId != commerceWishListItem.getCProductId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_CW_CPI_CP_COMMERCEWISHLISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CW_CPI_CP_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CW_CPI_CP_CPINSTANCEUUID_2);
			}

			sb.append(_FINDER_COLUMN_CW_CPI_CP_CPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				queryPos.add(CProductId);

				List<CommerceWishListItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCW_CPI_CP, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									commerceWishListId, CPInstanceUuid,
									CProductId
								};
							}

							_log.warn(
								"CommerceWishListItemPersistenceImpl.fetchByCW_CPI_CP(long, String, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceWishListItem commerceWishListItem = list.get(0);

					result = commerceWishListItem;

					cacheResult(commerceWishListItem);
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
			return (CommerceWishListItem)result;
		}
	}

	/**
	 * Removes the commerce wish list item where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63; from the database.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the commerce wish list item that was removed
	 */
	@Override
	public CommerceWishListItem removeByCW_CPI_CP(
			long commerceWishListId, String CPInstanceUuid, long CProductId)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = findByCW_CPI_CP(
			commerceWishListId, CPInstanceUuid, CProductId);

		return remove(commerceWishListItem);
	}

	/**
	 * Returns the number of commerce wish list items where commerceWishListId = &#63; and CPInstanceUuid = &#63; and CProductId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param CProductId the c product ID
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCW_CPI_CP(
		long commerceWishListId, String CPInstanceUuid, long CProductId) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		FinderPath finderPath = _finderPathCountByCW_CPI_CP;

		Object[] finderArgs = new Object[] {
			commerceWishListId, CPInstanceUuid, CProductId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			sb.append(_FINDER_COLUMN_CW_CPI_CP_COMMERCEWISHLISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CW_CPI_CP_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CW_CPI_CP_CPINSTANCEUUID_2);
			}

			sb.append(_FINDER_COLUMN_CW_CPI_CP_CPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceWishListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

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

	private static final String _FINDER_COLUMN_CW_CPI_CP_COMMERCEWISHLISTID_2 =
		"commerceWishListItem.commerceWishListId = ? AND ";

	private static final String _FINDER_COLUMN_CW_CPI_CP_CPINSTANCEUUID_2 =
		"commerceWishListItem.CPInstanceUuid = ? AND ";

	private static final String _FINDER_COLUMN_CW_CPI_CP_CPINSTANCEUUID_3 =
		"(commerceWishListItem.CPInstanceUuid IS NULL OR commerceWishListItem.CPInstanceUuid = '') AND ";

	private static final String _FINDER_COLUMN_CW_CPI_CP_CPRODUCTID_2 =
		"commerceWishListItem.CProductId = ?";

	public CommerceWishListItemPersistenceImpl() {
		setModelClass(CommerceWishListItem.class);

		setModelImplClass(CommerceWishListItemImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceWishListItemTable.INSTANCE);
	}

	/**
	 * Caches the commerce wish list item in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItem the commerce wish list item
	 */
	@Override
	public void cacheResult(CommerceWishListItem commerceWishListItem) {
		entityCache.putResult(
			CommerceWishListItemImpl.class,
			commerceWishListItem.getPrimaryKey(), commerceWishListItem);

		finderCache.putResult(
			_finderPathFetchByCW_CPI_CP,
			new Object[] {
				commerceWishListItem.getCommerceWishListId(),
				commerceWishListItem.getCPInstanceUuid(),
				commerceWishListItem.getCProductId()
			},
			commerceWishListItem);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce wish list items in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItems the commerce wish list items
	 */
	@Override
	public void cacheResult(List<CommerceWishListItem> commerceWishListItems) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceWishListItems.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceWishListItem commerceWishListItem :
				commerceWishListItems) {

			if (entityCache.getResult(
					CommerceWishListItemImpl.class,
					commerceWishListItem.getPrimaryKey()) == null) {

				cacheResult(commerceWishListItem);
			}
		}
	}

	/**
	 * Clears the cache for all commerce wish list items.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceWishListItemImpl.class);

		finderCache.clearCache(CommerceWishListItemImpl.class);
	}

	/**
	 * Clears the cache for the commerce wish list item.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceWishListItem commerceWishListItem) {
		entityCache.removeResult(
			CommerceWishListItemImpl.class, commerceWishListItem);
	}

	@Override
	public void clearCache(List<CommerceWishListItem> commerceWishListItems) {
		for (CommerceWishListItem commerceWishListItem :
				commerceWishListItems) {

			entityCache.removeResult(
				CommerceWishListItemImpl.class, commerceWishListItem);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceWishListItemImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceWishListItemImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceWishListItemModelImpl commerceWishListItemModelImpl) {

		Object[] args = new Object[] {
			commerceWishListItemModelImpl.getCommerceWishListId(),
			commerceWishListItemModelImpl.getCPInstanceUuid(),
			commerceWishListItemModelImpl.getCProductId()
		};

		finderCache.putResult(
			_finderPathCountByCW_CPI_CP, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCW_CPI_CP, args, commerceWishListItemModelImpl);
	}

	/**
	 * Creates a new commerce wish list item with the primary key. Does not add the commerce wish list item to the database.
	 *
	 * @param commerceWishListItemId the primary key for the new commerce wish list item
	 * @return the new commerce wish list item
	 */
	@Override
	public CommerceWishListItem create(long commerceWishListItemId) {
		CommerceWishListItem commerceWishListItem =
			new CommerceWishListItemImpl();

		commerceWishListItem.setNew(true);
		commerceWishListItem.setPrimaryKey(commerceWishListItemId);

		commerceWishListItem.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceWishListItem;
	}

	/**
	 * Removes the commerce wish list item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item that was removed
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem remove(long commerceWishListItemId)
		throws NoSuchWishListItemException {

		return remove((Serializable)commerceWishListItemId);
	}

	/**
	 * Removes the commerce wish list item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce wish list item
	 * @return the commerce wish list item that was removed
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem remove(Serializable primaryKey)
		throws NoSuchWishListItemException {

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem commerceWishListItem =
				(CommerceWishListItem)session.get(
					CommerceWishListItemImpl.class, primaryKey);

			if (commerceWishListItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWishListItemException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceWishListItem);
		}
		catch (NoSuchWishListItemException noSuchEntityException) {
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
	protected CommerceWishListItem removeImpl(
		CommerceWishListItem commerceWishListItem) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceWishListItem)) {
				commerceWishListItem = (CommerceWishListItem)session.get(
					CommerceWishListItemImpl.class,
					commerceWishListItem.getPrimaryKeyObj());
			}

			if (commerceWishListItem != null) {
				session.delete(commerceWishListItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceWishListItem != null) {
			clearCache(commerceWishListItem);
		}

		return commerceWishListItem;
	}

	@Override
	public CommerceWishListItem updateImpl(
		CommerceWishListItem commerceWishListItem) {

		boolean isNew = commerceWishListItem.isNew();

		if (!(commerceWishListItem instanceof CommerceWishListItemModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceWishListItem.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceWishListItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceWishListItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceWishListItem implementation " +
					commerceWishListItem.getClass());
		}

		CommerceWishListItemModelImpl commerceWishListItemModelImpl =
			(CommerceWishListItemModelImpl)commerceWishListItem;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceWishListItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceWishListItem.setCreateDate(date);
			}
			else {
				commerceWishListItem.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceWishListItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceWishListItem.setModifiedDate(date);
			}
			else {
				commerceWishListItem.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceWishListItem);
			}
			else {
				commerceWishListItem = (CommerceWishListItem)session.merge(
					commerceWishListItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceWishListItemImpl.class, commerceWishListItemModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceWishListItemModelImpl);

		if (isNew) {
			commerceWishListItem.setNew(false);
		}

		commerceWishListItem.resetOriginalValues();

		return commerceWishListItem;
	}

	/**
	 * Returns the commerce wish list item with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce wish list item
	 * @return the commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWishListItemException {

		CommerceWishListItem commerceWishListItem = fetchByPrimaryKey(
			primaryKey);

		if (commerceWishListItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWishListItemException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceWishListItem;
	}

	/**
	 * Returns the commerce wish list item with the primary key or throws a <code>NoSuchWishListItemException</code> if it could not be found.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem findByPrimaryKey(long commerceWishListItemId)
		throws NoSuchWishListItemException {

		return findByPrimaryKey((Serializable)commerceWishListItemId);
	}

	/**
	 * Returns the commerce wish list item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceWishListItemId the primary key of the commerce wish list item
	 * @return the commerce wish list item, or <code>null</code> if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem fetchByPrimaryKey(long commerceWishListItemId) {
		return fetchByPrimaryKey((Serializable)commerceWishListItemId);
	}

	/**
	 * Returns all the commerce wish list items.
	 *
	 * @return the commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findAll(
		int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findAll(
		int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		List<CommerceWishListItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEWISHLISTITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEWISHLISTITEM;

				sql = sql.concat(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceWishListItem>)QueryUtil.list(
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
	 * Removes all the commerce wish list items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceWishListItem commerceWishListItem : findAll()) {
			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items.
	 *
	 * @return the number of commerce wish list items
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
					_SQL_COUNT_COMMERCEWISHLISTITEM);

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
		return "commerceWishListItemId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEWISHLISTITEM;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceWishListItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce wish list item persistence.
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

		_finderPathWithPaginationFindByCommerceWishListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceWishListId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceWishListId"}, true);

		_finderPathWithoutPaginationFindByCommerceWishListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceWishListId", new String[] {Long.class.getName()},
			new String[] {"commerceWishListId"}, true);

		_finderPathCountByCommerceWishListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceWishListId", new String[] {Long.class.getName()},
			new String[] {"commerceWishListId"}, false);

		_finderPathWithPaginationFindByCPInstanceUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPInstanceUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPInstanceUuid"}, true);

		_finderPathWithoutPaginationFindByCPInstanceUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPInstanceUuid",
			new String[] {String.class.getName()},
			new String[] {"CPInstanceUuid"}, true);

		_finderPathCountByCPInstanceUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPInstanceUuid",
			new String[] {String.class.getName()},
			new String[] {"CPInstanceUuid"}, false);

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

		_finderPathWithPaginationFindByCW_CPI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCW_CPI",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceWishListId", "CPInstanceUuid"}, true);

		_finderPathWithoutPaginationFindByCW_CPI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCW_CPI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commerceWishListId", "CPInstanceUuid"}, true);

		_finderPathCountByCW_CPI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCW_CPI",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commerceWishListId", "CPInstanceUuid"}, false);

		_finderPathWithPaginationFindByCW_CP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCW_CP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceWishListId", "CProductId"}, true);

		_finderPathWithoutPaginationFindByCW_CP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCW_CP",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceWishListId", "CProductId"}, true);

		_finderPathCountByCW_CP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCW_CP",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceWishListId", "CProductId"}, false);

		_finderPathFetchByCW_CPI_CP = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCW_CPI_CP",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {"commerceWishListId", "CPInstanceUuid", "CProductId"},
			true);

		_finderPathCountByCW_CPI_CP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCW_CPI_CP",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {"commerceWishListId", "CPInstanceUuid", "CProductId"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceWishListItemImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEWISHLISTITEM =
		"SELECT commerceWishListItem FROM CommerceWishListItem commerceWishListItem";

	private static final String _SQL_SELECT_COMMERCEWISHLISTITEM_WHERE =
		"SELECT commerceWishListItem FROM CommerceWishListItem commerceWishListItem WHERE ";

	private static final String _SQL_COUNT_COMMERCEWISHLISTITEM =
		"SELECT COUNT(commerceWishListItem) FROM CommerceWishListItem commerceWishListItem";

	private static final String _SQL_COUNT_COMMERCEWISHLISTITEM_WHERE =
		"SELECT COUNT(commerceWishListItem) FROM CommerceWishListItem commerceWishListItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceWishListItem.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceWishListItem exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceWishListItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceWishListItemPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}