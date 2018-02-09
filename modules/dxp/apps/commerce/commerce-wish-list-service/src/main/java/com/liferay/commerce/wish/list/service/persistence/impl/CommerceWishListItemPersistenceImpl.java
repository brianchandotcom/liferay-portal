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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.exception.NoSuchWishListItemException;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.model.impl.CommerceWishListItemImpl;
import com.liferay.commerce.wish.list.model.impl.CommerceWishListItemModelImpl;
import com.liferay.commerce.wish.list.service.persistence.CommerceWishListItemPersistence;

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
 * The persistence implementation for the commerce wish list item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemPersistence
 * @see com.liferay.commerce.wish.list.service.persistence.CommerceWishListItemUtil
 * @generated
 */
@ProviderType
public class CommerceWishListItemPersistenceImpl extends BasePersistenceImpl<CommerceWishListItem>
	implements CommerceWishListItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceWishListItemUtil} to access the commerce wish list item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceWishListItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEWISHLISTID =
		new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceWishListId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEWISHLISTID =
		new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceWishListId", new String[] { Long.class.getName() },
			CommerceWishListItemModelImpl.COMMERCEWISHLISTID_COLUMN_BITMASK |
			CommerceWishListItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEWISHLISTID = new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceWishListId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId) {
		return findByCommerceWishListId(commerceWishListId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByCommerceWishListId(commerceWishListId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where commerceWishListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceWishListId the commerce wish list ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCommerceWishListId(
		long commerceWishListId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEWISHLISTID;
			finderArgs = new Object[] { commerceWishListId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEWISHLISTID;
			finderArgs = new Object[] {
					commerceWishListId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceWishListItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if ((commerceWishListId != commerceWishListItem.getCommerceWishListId())) {
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

			query.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceWishListId);

				if (!pagination) {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
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
		CommerceWishListItem commerceWishListItem = fetchByCommerceWishListId_First(commerceWishListId,
				orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceWishListId=");
		msg.append(commerceWishListId);

		msg.append("}");

		throw new NoSuchWishListItemException(msg.toString());
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
		List<CommerceWishListItem> list = findByCommerceWishListId(commerceWishListId,
				0, 1, orderByComparator);

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
		CommerceWishListItem commerceWishListItem = fetchByCommerceWishListId_Last(commerceWishListId,
				orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceWishListId=");
		msg.append(commerceWishListId);

		msg.append("}");

		throw new NoSuchWishListItemException(msg.toString());
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

		List<CommerceWishListItem> list = findByCommerceWishListId(commerceWishListId,
				count - 1, count, orderByComparator);

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
		CommerceWishListItem commerceWishListItem = findByPrimaryKey(commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCommerceWishListId_PrevAndNext(session,
					commerceWishListItem, commerceWishListId,
					orderByComparator, true);

			array[1] = commerceWishListItem;

			array[2] = getByCommerceWishListId_PrevAndNext(session,
					commerceWishListItem, commerceWishListId,
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

	protected CommerceWishListItem getByCommerceWishListId_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		long commerceWishListId,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2);

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
			query.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceWishListId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceWishListItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceWishListItem> list = q.list();

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
		for (CommerceWishListItem commerceWishListItem : findByCommerceWishListId(
				commerceWishListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEWISHLISTID;

		Object[] finderArgs = new Object[] { commerceWishListId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceWishListId);

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

	private static final String _FINDER_COLUMN_COMMERCEWISHLISTID_COMMERCEWISHLISTID_2 =
		"commerceWishListItem.commerceWishListId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] { Long.class.getName() },
			CommerceWishListItemModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CommerceWishListItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID = new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce wish list items where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPDefinitionId(long CPDefinitionId) {
		return findByCPDefinitionId(CPDefinitionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID;
			finderArgs = new Object[] { CPDefinitionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID;
			finderArgs = new Object[] {
					CPDefinitionId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceWishListItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if ((CPDefinitionId != commerceWishListItem.getCPDefinitionId())) {
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

			query.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				if (!pagination) {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
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
	 * Returns the first commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {
		CommerceWishListItem commerceWishListItem = fetchByCPDefinitionId_First(CPDefinitionId,
				orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchWishListItemException(msg.toString());
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		List<CommerceWishListItem> list = findByCPDefinitionId(CPDefinitionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCPDefinitionId_Last(long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {
		CommerceWishListItem commerceWishListItem = fetchByCPDefinitionId_Last(CPDefinitionId,
				orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchWishListItemException(msg.toString());
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishListItem> list = findByCPDefinitionId(CPDefinitionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem[] findByCPDefinitionId_PrevAndNext(
		long commerceWishListItemId, long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {
		CommerceWishListItem commerceWishListItem = findByPrimaryKey(commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(session,
					commerceWishListItem, CPDefinitionId, orderByComparator,
					true);

			array[1] = commerceWishListItem;

			array[2] = getByCPDefinitionId_PrevAndNext(session,
					commerceWishListItem, CPDefinitionId, orderByComparator,
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

	protected CommerceWishListItem getByCPDefinitionId_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		long CPDefinitionId,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

		query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

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
			query.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceWishListItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceWishListItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish list items where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CommerceWishListItem commerceWishListItem : findByCPDefinitionId(
				CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID;

		Object[] finderArgs = new Object[] { CPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 = "commerceWishListItem.CPDefinitionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPINSTANCEID =
		new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPInstanceId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID =
		new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPInstanceId",
			new String[] { Long.class.getName() },
			CommerceWishListItemModelImpl.CPINSTANCEID_COLUMN_BITMASK |
			CommerceWishListItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPINSTANCEID = new FinderPath(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPInstanceId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce wish list items where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceId(long CPInstanceId) {
		return findByCPInstanceId(CPInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish list items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @return the range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceId(long CPInstanceId,
		int start, int end) {
		return findByCPInstanceId(CPInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceId(long CPInstanceId,
		int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return findByCPInstanceId(CPInstanceId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findByCPInstanceId(long CPInstanceId,
		int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID;
			finderArgs = new Object[] { CPInstanceId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPINSTANCEID;
			finderArgs = new Object[] {
					CPInstanceId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceWishListItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishListItem commerceWishListItem : list) {
					if ((CPInstanceId != commerceWishListItem.getCPInstanceId())) {
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

			query.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

			query.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPInstanceId);

				if (!pagination) {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
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
	 * Returns the first commerce wish list item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCPInstanceId_First(long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {
		CommerceWishListItem commerceWishListItem = fetchByCPInstanceId_First(CPInstanceId,
				orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPInstanceId=");
		msg.append(CPInstanceId);

		msg.append("}");

		throw new NoSuchWishListItemException(msg.toString());
	}

	/**
	 * Returns the first commerce wish list item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCPInstanceId_First(long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		List<CommerceWishListItem> list = findByCPInstanceId(CPInstanceId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item
	 * @throws NoSuchWishListItemException if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem findByCPInstanceId_Last(long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {
		CommerceWishListItem commerceWishListItem = fetchByCPInstanceId_Last(CPInstanceId,
				orderByComparator);

		if (commerceWishListItem != null) {
			return commerceWishListItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPInstanceId=");
		msg.append(CPInstanceId);

		msg.append("}");

		throw new NoSuchWishListItemException(msg.toString());
	}

	/**
	 * Returns the last commerce wish list item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list item, or <code>null</code> if a matching commerce wish list item could not be found
	 */
	@Override
	public CommerceWishListItem fetchByCPInstanceId_Last(long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		int count = countByCPInstanceId(CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishListItem> list = findByCPInstanceId(CPInstanceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish list items before and after the current commerce wish list item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param commerceWishListItemId the primary key of the current commerce wish list item
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem[] findByCPInstanceId_PrevAndNext(
		long commerceWishListItemId, long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator)
		throws NoSuchWishListItemException {
		CommerceWishListItem commerceWishListItem = findByPrimaryKey(commerceWishListItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishListItem[] array = new CommerceWishListItemImpl[3];

			array[0] = getByCPInstanceId_PrevAndNext(session,
					commerceWishListItem, CPInstanceId, orderByComparator, true);

			array[1] = commerceWishListItem;

			array[2] = getByCPInstanceId_PrevAndNext(session,
					commerceWishListItem, CPInstanceId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceWishListItem getByCPInstanceId_PrevAndNext(
		Session session, CommerceWishListItem commerceWishListItem,
		long CPInstanceId,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE);

		query.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

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
			query.append(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceWishListItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceWishListItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish list items where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByCPInstanceId(long CPInstanceId) {
		for (CommerceWishListItem commerceWishListItem : findByCPInstanceId(
				CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceWishListItem);
		}
	}

	/**
	 * Returns the number of commerce wish list items where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching commerce wish list items
	 */
	@Override
	public int countByCPInstanceId(long CPInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPINSTANCEID;

		Object[] finderArgs = new Object[] { CPInstanceId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEWISHLISTITEM_WHERE);

			query.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPInstanceId);

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

	private static final String _FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2 = "commerceWishListItem.CPInstanceId = ?";

	public CommerceWishListItemPersistenceImpl() {
		setModelClass(CommerceWishListItem.class);
	}

	/**
	 * Caches the commerce wish list item in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItem the commerce wish list item
	 */
	@Override
	public void cacheResult(CommerceWishListItem commerceWishListItem) {
		entityCache.putResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			commerceWishListItem.getPrimaryKey(), commerceWishListItem);

		commerceWishListItem.resetOriginalValues();
	}

	/**
	 * Caches the commerce wish list items in the entity cache if it is enabled.
	 *
	 * @param commerceWishListItems the commerce wish list items
	 */
	@Override
	public void cacheResult(List<CommerceWishListItem> commerceWishListItems) {
		for (CommerceWishListItem commerceWishListItem : commerceWishListItems) {
			if (entityCache.getResult(
						CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
						CommerceWishListItemImpl.class,
						commerceWishListItem.getPrimaryKey()) == null) {
				cacheResult(commerceWishListItem);
			}
			else {
				commerceWishListItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce wish list items.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceWishListItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce wish list item.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceWishListItem commerceWishListItem) {
		entityCache.removeResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemImpl.class, commerceWishListItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceWishListItem> commerceWishListItems) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceWishListItem commerceWishListItem : commerceWishListItems) {
			entityCache.removeResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceWishListItemImpl.class,
				commerceWishListItem.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce wish list item with the primary key. Does not add the commerce wish list item to the database.
	 *
	 * @param commerceWishListItemId the primary key for the new commerce wish list item
	 * @return the new commerce wish list item
	 */
	@Override
	public CommerceWishListItem create(long commerceWishListItemId) {
		CommerceWishListItem commerceWishListItem = new CommerceWishListItemImpl();

		commerceWishListItem.setNew(true);
		commerceWishListItem.setPrimaryKey(commerceWishListItemId);

		commerceWishListItem.setCompanyId(companyProvider.getCompanyId());

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

			CommerceWishListItem commerceWishListItem = (CommerceWishListItem)session.get(CommerceWishListItemImpl.class,
					primaryKey);

			if (commerceWishListItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWishListItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceWishListItem);
		}
		catch (NoSuchWishListItemException nsee) {
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
	protected CommerceWishListItem removeImpl(
		CommerceWishListItem commerceWishListItem) {
		commerceWishListItem = toUnwrappedModel(commerceWishListItem);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceWishListItem)) {
				commerceWishListItem = (CommerceWishListItem)session.get(CommerceWishListItemImpl.class,
						commerceWishListItem.getPrimaryKeyObj());
			}

			if (commerceWishListItem != null) {
				session.delete(commerceWishListItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
		commerceWishListItem = toUnwrappedModel(commerceWishListItem);

		boolean isNew = commerceWishListItem.isNew();

		CommerceWishListItemModelImpl commerceWishListItemModelImpl = (CommerceWishListItemModelImpl)commerceWishListItem;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceWishListItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceWishListItem.setCreateDate(now);
			}
			else {
				commerceWishListItem.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceWishListItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceWishListItem.setModifiedDate(now);
			}
			else {
				commerceWishListItem.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceWishListItem.isNew()) {
				session.save(commerceWishListItem);

				commerceWishListItem.setNew(false);
			}
			else {
				commerceWishListItem = (CommerceWishListItem)session.merge(commerceWishListItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceWishListItemModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceWishListItemModelImpl.getCommerceWishListId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEWISHLISTID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEWISHLISTID,
				args);

			args = new Object[] {
					commerceWishListItemModelImpl.getCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
				args);

			args = new Object[] { commerceWishListItemModelImpl.getCPInstanceId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPINSTANCEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceWishListItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEWISHLISTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceWishListItemModelImpl.getOriginalCommerceWishListId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEWISHLISTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEWISHLISTID,
					args);

				args = new Object[] {
						commerceWishListItemModelImpl.getCommerceWishListId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEWISHLISTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEWISHLISTID,
					args);
			}

			if ((commerceWishListItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceWishListItemModelImpl.getOriginalCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);

				args = new Object[] {
						commerceWishListItemModelImpl.getCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);
			}

			if ((commerceWishListItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceWishListItemModelImpl.getOriginalCPInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPINSTANCEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID,
					args);

				args = new Object[] {
						commerceWishListItemModelImpl.getCPInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPINSTANCEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID,
					args);
			}
		}

		entityCache.putResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWishListItemImpl.class,
			commerceWishListItem.getPrimaryKey(), commerceWishListItem, false);

		commerceWishListItem.resetOriginalValues();

		return commerceWishListItem;
	}

	protected CommerceWishListItem toUnwrappedModel(
		CommerceWishListItem commerceWishListItem) {
		if (commerceWishListItem instanceof CommerceWishListItemImpl) {
			return commerceWishListItem;
		}

		CommerceWishListItemImpl commerceWishListItemImpl = new CommerceWishListItemImpl();

		commerceWishListItemImpl.setNew(commerceWishListItem.isNew());
		commerceWishListItemImpl.setPrimaryKey(commerceWishListItem.getPrimaryKey());

		commerceWishListItemImpl.setCommerceWishListItemId(commerceWishListItem.getCommerceWishListItemId());
		commerceWishListItemImpl.setGroupId(commerceWishListItem.getGroupId());
		commerceWishListItemImpl.setCompanyId(commerceWishListItem.getCompanyId());
		commerceWishListItemImpl.setUserId(commerceWishListItem.getUserId());
		commerceWishListItemImpl.setUserName(commerceWishListItem.getUserName());
		commerceWishListItemImpl.setCreateDate(commerceWishListItem.getCreateDate());
		commerceWishListItemImpl.setModifiedDate(commerceWishListItem.getModifiedDate());
		commerceWishListItemImpl.setCommerceWishListId(commerceWishListItem.getCommerceWishListId());
		commerceWishListItemImpl.setCPDefinitionId(commerceWishListItem.getCPDefinitionId());
		commerceWishListItemImpl.setCPInstanceId(commerceWishListItem.getCPInstanceId());
		commerceWishListItemImpl.setJson(commerceWishListItem.getJson());

		return commerceWishListItemImpl;
	}

	/**
	 * Returns the commerce wish list item with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce wish list item
	 * @return the commerce wish list item
	 * @throws NoSuchWishListItemException if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWishListItemException {
		CommerceWishListItem commerceWishListItem = fetchByPrimaryKey(primaryKey);

		if (commerceWishListItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWishListItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceWishListItem;
	}

	/**
	 * Returns the commerce wish list item with the primary key or throws a {@link NoSuchWishListItemException} if it could not be found.
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
	 * @param primaryKey the primary key of the commerce wish list item
	 * @return the commerce wish list item, or <code>null</code> if a commerce wish list item with the primary key could not be found
	 */
	@Override
	public CommerceWishListItem fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceWishListItemImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceWishListItem commerceWishListItem = (CommerceWishListItem)serializable;

		if (commerceWishListItem == null) {
			Session session = null;

			try {
				session = openSession();

				commerceWishListItem = (CommerceWishListItem)session.get(CommerceWishListItemImpl.class,
						primaryKey);

				if (commerceWishListItem != null) {
					cacheResult(commerceWishListItem);
				}
				else {
					entityCache.putResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
						CommerceWishListItemImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceWishListItemImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceWishListItem;
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

	@Override
	public Map<Serializable, CommerceWishListItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceWishListItem> map = new HashMap<Serializable, CommerceWishListItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceWishListItem commerceWishListItem = fetchByPrimaryKey(primaryKey);

			if (commerceWishListItem != null) {
				map.put(primaryKey, commerceWishListItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceWishListItemImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceWishListItem)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEWISHLISTITEM_WHERE_PKS_IN);

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

			for (CommerceWishListItem commerceWishListItem : (List<CommerceWishListItem>)q.list()) {
				map.put(commerceWishListItem.getPrimaryKeyObj(),
					commerceWishListItem);

				cacheResult(commerceWishListItem);

				uncachedPrimaryKeys.remove(commerceWishListItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceWishListItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceWishListItemImpl.class, primaryKey, nullModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findAll(int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish list items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish list items
	 * @param end the upper bound of the range of commerce wish list items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce wish list items
	 */
	@Override
	public List<CommerceWishListItem> findAll(int start, int end,
		OrderByComparator<CommerceWishListItem> orderByComparator,
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

		List<CommerceWishListItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceWishListItem>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEWISHLISTITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEWISHLISTITEM;

				if (pagination) {
					sql = sql.concat(CommerceWishListItemModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceWishListItem>)QueryUtil.list(q,
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEWISHLISTITEM);

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
		return CommerceWishListItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce wish list item persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceWishListItemImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEWISHLISTITEM = "SELECT commerceWishListItem FROM CommerceWishListItem commerceWishListItem";
	private static final String _SQL_SELECT_COMMERCEWISHLISTITEM_WHERE_PKS_IN = "SELECT commerceWishListItem FROM CommerceWishListItem commerceWishListItem WHERE commerceWishListItemId IN (";
	private static final String _SQL_SELECT_COMMERCEWISHLISTITEM_WHERE = "SELECT commerceWishListItem FROM CommerceWishListItem commerceWishListItem WHERE ";
	private static final String _SQL_COUNT_COMMERCEWISHLISTITEM = "SELECT COUNT(commerceWishListItem) FROM CommerceWishListItem commerceWishListItem";
	private static final String _SQL_COUNT_COMMERCEWISHLISTITEM_WHERE = "SELECT COUNT(commerceWishListItem) FROM CommerceWishListItem commerceWishListItem WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceWishListItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceWishListItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceWishListItem exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceWishListItemPersistenceImpl.class);
}