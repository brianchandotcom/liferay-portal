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

import com.liferay.commerce.exception.NoSuchCartItemException;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.model.impl.CommerceCartItemImpl;
import com.liferay.commerce.model.impl.CommerceCartItemModelImpl;
import com.liferay.commerce.service.persistence.CommerceCartItemPersistence;

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
 * The persistence implementation for the commerce cart item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartItemPersistence
 * @see com.liferay.commerce.service.persistence.CommerceCartItemUtil
 * @generated
 */
@ProviderType
public class CommerceCartItemPersistenceImpl extends BasePersistenceImpl<CommerceCartItem>
	implements CommerceCartItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceCartItemUtil} to access the commerce cart item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceCartItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECARTID =
		new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceCartId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECARTID =
		new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCommerceCartId",
			new String[] { Long.class.getName() },
			CommerceCartItemModelImpl.COMMERCECARTID_COLUMN_BITMASK |
			CommerceCartItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCECARTID = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCommerceCartId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce cart items where commerceCartId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @return the matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCommerceCartId(long commerceCartId) {
		return findByCommerceCartId(commerceCartId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce cart items where commerceCartId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @return the range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCommerceCartId(long commerceCartId,
		int start, int end) {
		return findByCommerceCartId(commerceCartId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where commerceCartId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCommerceCartId(long commerceCartId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return findByCommerceCartId(commerceCartId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where commerceCartId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCommerceCartId(long commerceCartId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECARTID;
			finderArgs = new Object[] { commerceCartId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECARTID;
			finderArgs = new Object[] {
					commerceCartId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCartItem commerceCartItem : list) {
					if ((commerceCartId != commerceCartItem.getCommerceCartId())) {
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

			query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECARTID_COMMERCECARTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCartId);

				if (!pagination) {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
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
	 * Returns the first commerce cart item in the ordered set where commerceCartId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByCommerceCartId_First(long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByCommerceCartId_First(commerceCartId,
				orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCartId=");
		msg.append(commerceCartId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the first commerce cart item in the ordered set where commerceCartId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByCommerceCartId_First(long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		List<CommerceCartItem> list = findByCommerceCartId(commerceCartId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart item in the ordered set where commerceCartId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByCommerceCartId_Last(long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByCommerceCartId_Last(commerceCartId,
				orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCartId=");
		msg.append(commerceCartId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the last commerce cart item in the ordered set where commerceCartId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByCommerceCartId_Last(long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		int count = countByCommerceCartId(commerceCartId);

		if (count == 0) {
			return null;
		}

		List<CommerceCartItem> list = findByCommerceCartId(commerceCartId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce cart items before and after the current commerce cart item in the ordered set where commerceCartId = &#63;.
	 *
	 * @param commerceCartItemId the primary key of the current commerce cart item
	 * @param commerceCartId the commerce cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart item
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem[] findByCommerceCartId_PrevAndNext(
		long commerceCartItemId, long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = findByPrimaryKey(commerceCartItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceCartItem[] array = new CommerceCartItemImpl[3];

			array[0] = getByCommerceCartId_PrevAndNext(session,
					commerceCartItem, commerceCartId, orderByComparator, true);

			array[1] = commerceCartItem;

			array[2] = getByCommerceCartId_PrevAndNext(session,
					commerceCartItem, commerceCartId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCartItem getByCommerceCartId_PrevAndNext(
		Session session, CommerceCartItem commerceCartItem,
		long commerceCartId,
		OrderByComparator<CommerceCartItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

		query.append(_FINDER_COLUMN_COMMERCECARTID_COMMERCECARTID_2);

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
			query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceCartId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCartItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCartItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce cart items where commerceCartId = &#63; from the database.
	 *
	 * @param commerceCartId the commerce cart ID
	 */
	@Override
	public void removeByCommerceCartId(long commerceCartId) {
		for (CommerceCartItem commerceCartItem : findByCommerceCartId(
				commerceCartId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCartItem);
		}
	}

	/**
	 * Returns the number of commerce cart items where commerceCartId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @return the number of matching commerce cart items
	 */
	@Override
	public int countByCommerceCartId(long commerceCartId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCECARTID;

		Object[] finderArgs = new Object[] { commerceCartId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECARTITEM_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECARTID_COMMERCECARTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCartId);

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

	private static final String _FINDER_COLUMN_COMMERCECARTID_COMMERCECARTID_2 = "commerceCartItem.commerceCartId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPDefinitionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] { Long.class.getName() },
			CommerceCartItemModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CommerceCartItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce cart items where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPDefinitionId(long CPDefinitionId) {
		return findByCPDefinitionId(CPDefinitionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce cart items where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @return the range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPDefinitionId(long CPDefinitionId,
		int start, int end) {
		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPDefinitionId(long CPDefinitionId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPDefinitionId(long CPDefinitionId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
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

		List<CommerceCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCartItem commerceCartItem : list) {
					if ((CPDefinitionId != commerceCartItem.getCPDefinitionId())) {
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

			query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				if (!pagination) {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
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
	 * Returns the first commerce cart item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByCPDefinitionId_First(long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByCPDefinitionId_First(CPDefinitionId,
				orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the first commerce cart item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByCPDefinitionId_First(long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		List<CommerceCartItem> list = findByCPDefinitionId(CPDefinitionId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByCPDefinitionId_Last(long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByCPDefinitionId_Last(CPDefinitionId,
				orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the last commerce cart item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByCPDefinitionId_Last(long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CommerceCartItem> list = findByCPDefinitionId(CPDefinitionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce cart items before and after the current commerce cart item in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param commerceCartItemId the primary key of the current commerce cart item
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart item
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem[] findByCPDefinitionId_PrevAndNext(
		long commerceCartItemId, long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = findByPrimaryKey(commerceCartItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceCartItem[] array = new CommerceCartItemImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(session,
					commerceCartItem, CPDefinitionId, orderByComparator, true);

			array[1] = commerceCartItem;

			array[2] = getByCPDefinitionId_PrevAndNext(session,
					commerceCartItem, CPDefinitionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCartItem getByCPDefinitionId_PrevAndNext(
		Session session, CommerceCartItem commerceCartItem,
		long CPDefinitionId,
		OrderByComparator<CommerceCartItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

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
			query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCartItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCartItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce cart items where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CommerceCartItem commerceCartItem : findByCPDefinitionId(
				CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCartItem);
		}
	}

	/**
	 * Returns the number of commerce cart items where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching commerce cart items
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID;

		Object[] finderArgs = new Object[] { CPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECARTITEM_WHERE);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 = "commerceCartItem.CPDefinitionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPINSTANCEID =
		new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCPInstanceId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID =
		new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPInstanceId",
			new String[] { Long.class.getName() },
			CommerceCartItemModelImpl.CPINSTANCEID_COLUMN_BITMASK |
			CommerceCartItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPINSTANCEID = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPInstanceId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce cart items where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPInstanceId(long CPInstanceId) {
		return findByCPInstanceId(CPInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce cart items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @return the range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPInstanceId(long CPInstanceId,
		int start, int end) {
		return findByCPInstanceId(CPInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPInstanceId(long CPInstanceId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return findByCPInstanceId(CPInstanceId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByCPInstanceId(long CPInstanceId,
		int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
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

		List<CommerceCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCartItem commerceCartItem : list) {
					if ((CPInstanceId != commerceCartItem.getCPInstanceId())) {
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

			query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

			query.append(_FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPInstanceId);

				if (!pagination) {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
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
	 * Returns the first commerce cart item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByCPInstanceId_First(long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByCPInstanceId_First(CPInstanceId,
				orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPInstanceId=");
		msg.append(CPInstanceId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the first commerce cart item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByCPInstanceId_First(long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		List<CommerceCartItem> list = findByCPInstanceId(CPInstanceId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByCPInstanceId_Last(long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByCPInstanceId_Last(CPInstanceId,
				orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPInstanceId=");
		msg.append(CPInstanceId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the last commerce cart item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByCPInstanceId_Last(long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		int count = countByCPInstanceId(CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CommerceCartItem> list = findByCPInstanceId(CPInstanceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce cart items before and after the current commerce cart item in the ordered set where CPInstanceId = &#63;.
	 *
	 * @param commerceCartItemId the primary key of the current commerce cart item
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart item
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem[] findByCPInstanceId_PrevAndNext(
		long commerceCartItemId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = findByPrimaryKey(commerceCartItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceCartItem[] array = new CommerceCartItemImpl[3];

			array[0] = getByCPInstanceId_PrevAndNext(session, commerceCartItem,
					CPInstanceId, orderByComparator, true);

			array[1] = commerceCartItem;

			array[2] = getByCPInstanceId_PrevAndNext(session, commerceCartItem,
					CPInstanceId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCartItem getByCPInstanceId_PrevAndNext(Session session,
		CommerceCartItem commerceCartItem, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

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
			query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCartItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCartItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce cart items where CPInstanceId = &#63; from the database.
	 *
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByCPInstanceId(long CPInstanceId) {
		for (CommerceCartItem commerceCartItem : findByCPInstanceId(
				CPInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCartItem);
		}
	}

	/**
	 * Returns the number of commerce cart items where CPInstanceId = &#63;.
	 *
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching commerce cart items
	 */
	@Override
	public int countByCPInstanceId(long CPInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPINSTANCEID;

		Object[] finderArgs = new Object[] { CPInstanceId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECARTITEM_WHERE);

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

	private static final String _FINDER_COLUMN_CPINSTANCEID_CPINSTANCEID_2 = "commerceCartItem.CPInstanceId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_D_I = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_D_I",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D_I = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED,
			CommerceCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_D_I",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CommerceCartItemModelImpl.COMMERCECARTID_COLUMN_BITMASK |
			CommerceCartItemModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CommerceCartItemModelImpl.CPINSTANCEID_COLUMN_BITMASK |
			CommerceCartItemModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_D_I = new FinderPath(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_D_I",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the commerce cart items where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @return the matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByC_D_I(long commerceCartId,
		long CPDefinitionId, long CPInstanceId) {
		return findByC_D_I(commerceCartId, CPDefinitionId, CPInstanceId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce cart items where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @return the range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByC_D_I(long commerceCartId,
		long CPDefinitionId, long CPInstanceId, int start, int end) {
		return findByC_D_I(commerceCartId, CPDefinitionId, CPInstanceId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByC_D_I(long commerceCartId,
		long CPDefinitionId, long CPInstanceId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return findByC_D_I(commerceCartId, CPDefinitionId, CPInstanceId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cart items where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findByC_D_I(long commerceCartId,
		long CPDefinitionId, long CPInstanceId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D_I;
			finderArgs = new Object[] {
					commerceCartId, CPDefinitionId, CPInstanceId
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_D_I;
			finderArgs = new Object[] {
					commerceCartId, CPDefinitionId, CPInstanceId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCartItem commerceCartItem : list) {
					if ((commerceCartId != commerceCartItem.getCommerceCartId()) ||
							(CPDefinitionId != commerceCartItem.getCPDefinitionId()) ||
							(CPInstanceId != commerceCartItem.getCPInstanceId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

			query.append(_FINDER_COLUMN_C_D_I_COMMERCECARTID_2);

			query.append(_FINDER_COLUMN_C_D_I_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_C_D_I_CPINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCartId);

				qPos.add(CPDefinitionId);

				qPos.add(CPInstanceId);

				if (!pagination) {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
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
	 * Returns the first commerce cart item in the ordered set where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByC_D_I_First(long commerceCartId,
		long CPDefinitionId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByC_D_I_First(commerceCartId,
				CPDefinitionId, CPInstanceId, orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCartId=");
		msg.append(commerceCartId);

		msg.append(", CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append(", CPInstanceId=");
		msg.append(CPInstanceId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the first commerce cart item in the ordered set where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByC_D_I_First(long commerceCartId,
		long CPDefinitionId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		List<CommerceCartItem> list = findByC_D_I(commerceCartId,
				CPDefinitionId, CPInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart item in the ordered set where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item
	 * @throws NoSuchCartItemException if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem findByC_D_I_Last(long commerceCartId,
		long CPDefinitionId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByC_D_I_Last(commerceCartId,
				CPDefinitionId, CPInstanceId, orderByComparator);

		if (commerceCartItem != null) {
			return commerceCartItem;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCartId=");
		msg.append(commerceCartId);

		msg.append(", CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append(", CPInstanceId=");
		msg.append(CPInstanceId);

		msg.append("}");

		throw new NoSuchCartItemException(msg.toString());
	}

	/**
	 * Returns the last commerce cart item in the ordered set where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart item, or <code>null</code> if a matching commerce cart item could not be found
	 */
	@Override
	public CommerceCartItem fetchByC_D_I_Last(long commerceCartId,
		long CPDefinitionId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		int count = countByC_D_I(commerceCartId, CPDefinitionId, CPInstanceId);

		if (count == 0) {
			return null;
		}

		List<CommerceCartItem> list = findByC_D_I(commerceCartId,
				CPDefinitionId, CPInstanceId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce cart items before and after the current commerce cart item in the ordered set where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceCartItemId the primary key of the current commerce cart item
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart item
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem[] findByC_D_I_PrevAndNext(long commerceCartItemId,
		long commerceCartId, long CPDefinitionId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = findByPrimaryKey(commerceCartItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceCartItem[] array = new CommerceCartItemImpl[3];

			array[0] = getByC_D_I_PrevAndNext(session, commerceCartItem,
					commerceCartId, CPDefinitionId, CPInstanceId,
					orderByComparator, true);

			array[1] = commerceCartItem;

			array[2] = getByC_D_I_PrevAndNext(session, commerceCartItem,
					commerceCartId, CPDefinitionId, CPInstanceId,
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

	protected CommerceCartItem getByC_D_I_PrevAndNext(Session session,
		CommerceCartItem commerceCartItem, long commerceCartId,
		long CPDefinitionId, long CPInstanceId,
		OrderByComparator<CommerceCartItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE);

		query.append(_FINDER_COLUMN_C_D_I_COMMERCECARTID_2);

		query.append(_FINDER_COLUMN_C_D_I_CPDEFINITIONID_2);

		query.append(_FINDER_COLUMN_C_D_I_CPINSTANCEID_2);

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
			query.append(CommerceCartItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceCartId);

		qPos.add(CPDefinitionId);

		qPos.add(CPInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCartItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCartItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce cart items where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63; from the database.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 */
	@Override
	public void removeByC_D_I(long commerceCartId, long CPDefinitionId,
		long CPInstanceId) {
		for (CommerceCartItem commerceCartItem : findByC_D_I(commerceCartId,
				CPDefinitionId, CPInstanceId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceCartItem);
		}
	}

	/**
	 * Returns the number of commerce cart items where commerceCartId = &#63; and CPDefinitionId = &#63; and CPInstanceId = &#63;.
	 *
	 * @param commerceCartId the commerce cart ID
	 * @param CPDefinitionId the cp definition ID
	 * @param CPInstanceId the cp instance ID
	 * @return the number of matching commerce cart items
	 */
	@Override
	public int countByC_D_I(long commerceCartId, long CPDefinitionId,
		long CPInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_D_I;

		Object[] finderArgs = new Object[] {
				commerceCartId, CPDefinitionId, CPInstanceId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCECARTITEM_WHERE);

			query.append(_FINDER_COLUMN_C_D_I_COMMERCECARTID_2);

			query.append(_FINDER_COLUMN_C_D_I_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_C_D_I_CPINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCartId);

				qPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_C_D_I_COMMERCECARTID_2 = "commerceCartItem.commerceCartId = ? AND ";
	private static final String _FINDER_COLUMN_C_D_I_CPDEFINITIONID_2 = "commerceCartItem.CPDefinitionId = ? AND ";
	private static final String _FINDER_COLUMN_C_D_I_CPINSTANCEID_2 = "commerceCartItem.CPInstanceId = ?";

	public CommerceCartItemPersistenceImpl() {
		setModelClass(CommerceCartItem.class);
	}

	/**
	 * Caches the commerce cart item in the entity cache if it is enabled.
	 *
	 * @param commerceCartItem the commerce cart item
	 */
	@Override
	public void cacheResult(CommerceCartItem commerceCartItem) {
		entityCache.putResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemImpl.class, commerceCartItem.getPrimaryKey(),
			commerceCartItem);

		commerceCartItem.resetOriginalValues();
	}

	/**
	 * Caches the commerce cart items in the entity cache if it is enabled.
	 *
	 * @param commerceCartItems the commerce cart items
	 */
	@Override
	public void cacheResult(List<CommerceCartItem> commerceCartItems) {
		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			if (entityCache.getResult(
						CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCartItemImpl.class,
						commerceCartItem.getPrimaryKey()) == null) {
				cacheResult(commerceCartItem);
			}
			else {
				commerceCartItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce cart items.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceCartItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce cart item.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceCartItem commerceCartItem) {
		entityCache.removeResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemImpl.class, commerceCartItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceCartItem> commerceCartItems) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			entityCache.removeResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCartItemImpl.class, commerceCartItem.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce cart item with the primary key. Does not add the commerce cart item to the database.
	 *
	 * @param commerceCartItemId the primary key for the new commerce cart item
	 * @return the new commerce cart item
	 */
	@Override
	public CommerceCartItem create(long commerceCartItemId) {
		CommerceCartItem commerceCartItem = new CommerceCartItemImpl();

		commerceCartItem.setNew(true);
		commerceCartItem.setPrimaryKey(commerceCartItemId);

		commerceCartItem.setCompanyId(companyProvider.getCompanyId());

		return commerceCartItem;
	}

	/**
	 * Removes the commerce cart item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCartItemId the primary key of the commerce cart item
	 * @return the commerce cart item that was removed
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem remove(long commerceCartItemId)
		throws NoSuchCartItemException {
		return remove((Serializable)commerceCartItemId);
	}

	/**
	 * Removes the commerce cart item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce cart item
	 * @return the commerce cart item that was removed
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem remove(Serializable primaryKey)
		throws NoSuchCartItemException {
		Session session = null;

		try {
			session = openSession();

			CommerceCartItem commerceCartItem = (CommerceCartItem)session.get(CommerceCartItemImpl.class,
					primaryKey);

			if (commerceCartItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCartItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceCartItem);
		}
		catch (NoSuchCartItemException nsee) {
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
	protected CommerceCartItem removeImpl(CommerceCartItem commerceCartItem) {
		commerceCartItem = toUnwrappedModel(commerceCartItem);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceCartItem)) {
				commerceCartItem = (CommerceCartItem)session.get(CommerceCartItemImpl.class,
						commerceCartItem.getPrimaryKeyObj());
			}

			if (commerceCartItem != null) {
				session.delete(commerceCartItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceCartItem != null) {
			clearCache(commerceCartItem);
		}

		return commerceCartItem;
	}

	@Override
	public CommerceCartItem updateImpl(CommerceCartItem commerceCartItem) {
		commerceCartItem = toUnwrappedModel(commerceCartItem);

		boolean isNew = commerceCartItem.isNew();

		CommerceCartItemModelImpl commerceCartItemModelImpl = (CommerceCartItemModelImpl)commerceCartItem;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceCartItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceCartItem.setCreateDate(now);
			}
			else {
				commerceCartItem.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceCartItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceCartItem.setModifiedDate(now);
			}
			else {
				commerceCartItem.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceCartItem.isNew()) {
				session.save(commerceCartItem);

				commerceCartItem.setNew(false);
			}
			else {
				commerceCartItem = (CommerceCartItem)session.merge(commerceCartItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceCartItemModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceCartItemModelImpl.getCommerceCartId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECARTID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECARTID,
				args);

			args = new Object[] { commerceCartItemModelImpl.getCPDefinitionId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
				args);

			args = new Object[] { commerceCartItemModelImpl.getCPInstanceId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPINSTANCEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID,
				args);

			args = new Object[] {
					commerceCartItemModelImpl.getCommerceCartId(),
					commerceCartItemModelImpl.getCPDefinitionId(),
					commerceCartItemModelImpl.getCPInstanceId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_D_I, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D_I,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceCartItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECARTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartItemModelImpl.getOriginalCommerceCartId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECARTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECARTID,
					args);

				args = new Object[] {
						commerceCartItemModelImpl.getCommerceCartId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECARTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECARTID,
					args);
			}

			if ((commerceCartItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartItemModelImpl.getOriginalCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);

				args = new Object[] {
						commerceCartItemModelImpl.getCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);
			}

			if ((commerceCartItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartItemModelImpl.getOriginalCPInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPINSTANCEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID,
					args);

				args = new Object[] { commerceCartItemModelImpl.getCPInstanceId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPINSTANCEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPINSTANCEID,
					args);
			}

			if ((commerceCartItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D_I.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartItemModelImpl.getOriginalCommerceCartId(),
						commerceCartItemModelImpl.getOriginalCPDefinitionId(),
						commerceCartItemModelImpl.getOriginalCPInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_D_I, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D_I,
					args);

				args = new Object[] {
						commerceCartItemModelImpl.getCommerceCartId(),
						commerceCartItemModelImpl.getCPDefinitionId(),
						commerceCartItemModelImpl.getCPInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_D_I, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D_I,
					args);
			}
		}

		entityCache.putResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartItemImpl.class, commerceCartItem.getPrimaryKey(),
			commerceCartItem, false);

		commerceCartItem.resetOriginalValues();

		return commerceCartItem;
	}

	protected CommerceCartItem toUnwrappedModel(
		CommerceCartItem commerceCartItem) {
		if (commerceCartItem instanceof CommerceCartItemImpl) {
			return commerceCartItem;
		}

		CommerceCartItemImpl commerceCartItemImpl = new CommerceCartItemImpl();

		commerceCartItemImpl.setNew(commerceCartItem.isNew());
		commerceCartItemImpl.setPrimaryKey(commerceCartItem.getPrimaryKey());

		commerceCartItemImpl.setCommerceCartItemId(commerceCartItem.getCommerceCartItemId());
		commerceCartItemImpl.setGroupId(commerceCartItem.getGroupId());
		commerceCartItemImpl.setCompanyId(commerceCartItem.getCompanyId());
		commerceCartItemImpl.setUserId(commerceCartItem.getUserId());
		commerceCartItemImpl.setUserName(commerceCartItem.getUserName());
		commerceCartItemImpl.setCreateDate(commerceCartItem.getCreateDate());
		commerceCartItemImpl.setModifiedDate(commerceCartItem.getModifiedDate());
		commerceCartItemImpl.setCommerceCartId(commerceCartItem.getCommerceCartId());
		commerceCartItemImpl.setCPDefinitionId(commerceCartItem.getCPDefinitionId());
		commerceCartItemImpl.setCPInstanceId(commerceCartItem.getCPInstanceId());
		commerceCartItemImpl.setQuantity(commerceCartItem.getQuantity());
		commerceCartItemImpl.setJson(commerceCartItem.getJson());

		return commerceCartItemImpl;
	}

	/**
	 * Returns the commerce cart item with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cart item
	 * @return the commerce cart item
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCartItemException {
		CommerceCartItem commerceCartItem = fetchByPrimaryKey(primaryKey);

		if (commerceCartItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCartItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceCartItem;
	}

	/**
	 * Returns the commerce cart item with the primary key or throws a {@link NoSuchCartItemException} if it could not be found.
	 *
	 * @param commerceCartItemId the primary key of the commerce cart item
	 * @return the commerce cart item
	 * @throws NoSuchCartItemException if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem findByPrimaryKey(long commerceCartItemId)
		throws NoSuchCartItemException {
		return findByPrimaryKey((Serializable)commerceCartItemId);
	}

	/**
	 * Returns the commerce cart item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cart item
	 * @return the commerce cart item, or <code>null</code> if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCartItemImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceCartItem commerceCartItem = (CommerceCartItem)serializable;

		if (commerceCartItem == null) {
			Session session = null;

			try {
				session = openSession();

				commerceCartItem = (CommerceCartItem)session.get(CommerceCartItemImpl.class,
						primaryKey);

				if (commerceCartItem != null) {
					cacheResult(commerceCartItem);
				}
				else {
					entityCache.putResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCartItemImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartItemImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceCartItem;
	}

	/**
	 * Returns the commerce cart item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCartItemId the primary key of the commerce cart item
	 * @return the commerce cart item, or <code>null</code> if a commerce cart item with the primary key could not be found
	 */
	@Override
	public CommerceCartItem fetchByPrimaryKey(long commerceCartItemId) {
		return fetchByPrimaryKey((Serializable)commerceCartItemId);
	}

	@Override
	public Map<Serializable, CommerceCartItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceCartItem> map = new HashMap<Serializable, CommerceCartItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceCartItem commerceCartItem = fetchByPrimaryKey(primaryKey);

			if (commerceCartItem != null) {
				map.put(primaryKey, commerceCartItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartItemImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceCartItem)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCECARTITEM_WHERE_PKS_IN);

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

			for (CommerceCartItem commerceCartItem : (List<CommerceCartItem>)q.list()) {
				map.put(commerceCartItem.getPrimaryKeyObj(), commerceCartItem);

				cacheResult(commerceCartItem);

				uncachedPrimaryKeys.remove(commerceCartItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceCartItemModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartItemImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce cart items.
	 *
	 * @return the commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce cart items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @return the range of commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce cart items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findAll(int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce cart items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce cart items
	 * @param end the upper bound of the range of commerce cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce cart items
	 */
	@Override
	public List<CommerceCartItem> findAll(int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator,
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

		List<CommerceCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCECARTITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECARTITEM;

				if (pagination) {
					sql = sql.concat(CommerceCartItemModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCartItem>)QueryUtil.list(q,
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
	 * Removes all the commerce cart items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceCartItem commerceCartItem : findAll()) {
			remove(commerceCartItem);
		}
	}

	/**
	 * Returns the number of commerce cart items.
	 *
	 * @return the number of commerce cart items
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCECARTITEM);

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
		return CommerceCartItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce cart item persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceCartItemImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCECARTITEM = "SELECT commerceCartItem FROM CommerceCartItem commerceCartItem";
	private static final String _SQL_SELECT_COMMERCECARTITEM_WHERE_PKS_IN = "SELECT commerceCartItem FROM CommerceCartItem commerceCartItem WHERE commerceCartItemId IN (";
	private static final String _SQL_SELECT_COMMERCECARTITEM_WHERE = "SELECT commerceCartItem FROM CommerceCartItem commerceCartItem WHERE ";
	private static final String _SQL_COUNT_COMMERCECARTITEM = "SELECT COUNT(commerceCartItem) FROM CommerceCartItem commerceCartItem";
	private static final String _SQL_COUNT_COMMERCECARTITEM_WHERE = "SELECT COUNT(commerceCartItem) FROM CommerceCartItem commerceCartItem WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceCartItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceCartItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceCartItem exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceCartItemPersistenceImpl.class);
}