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

package com.liferay.shopping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.shopping.exception.NoSuchItemPriceException;
import com.liferay.shopping.model.ShoppingItemPrice;
import com.liferay.shopping.model.impl.ShoppingItemPriceImpl;
import com.liferay.shopping.model.impl.ShoppingItemPriceModelImpl;
import com.liferay.shopping.service.persistence.ShoppingItemPricePersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the shopping item price service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemPricePersistence
 * @see com.liferay.shopping.service.persistence.ShoppingItemPriceUtil
 * @generated
 */
@ProviderType
public class ShoppingItemPricePersistenceImpl extends BasePersistenceImpl<ShoppingItemPrice>
	implements ShoppingItemPricePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShoppingItemPriceUtil} to access the shopping item price persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingItemPriceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemPriceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemPriceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemPriceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByItemId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID =
		new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemPriceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByItemId",
			new String[] { Long.class.getName() },
			ShoppingItemPriceModelImpl.ITEMID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ITEMID = new FinderPath(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByItemId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the shopping item prices where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the matching shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findByItemId(long itemId) {
		return findByItemId(itemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping item prices where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item prices
	 * @param end the upper bound of the range of shopping item prices (not inclusive)
	 * @return the range of matching shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findByItemId(long itemId, int start, int end) {
		return findByItemId(itemId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping item prices where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item prices
	 * @param end the upper bound of the range of shopping item prices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findByItemId(long itemId, int start,
		int end, OrderByComparator<ShoppingItemPrice> orderByComparator) {
		return findByItemId(itemId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping item prices where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item prices
	 * @param end the upper bound of the range of shopping item prices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findByItemId(long itemId, int start,
		int end, OrderByComparator<ShoppingItemPrice> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId, start, end, orderByComparator };
		}

		List<ShoppingItemPrice> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingItemPrice>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingItemPrice shoppingItemPrice : list) {
					if ((itemId != shoppingItemPrice.getItemId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SHOPPINGITEMPRICE_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ShoppingItemPriceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				if (!pagination) {
					list = (List<ShoppingItemPrice>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingItemPrice>)QueryUtil.list(q,
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
	 * Returns the first shopping item price in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping item price
	 * @throws com.liferay.shopping.NoSuchItemPriceException if a matching shopping item price could not be found
	 */
	@Override
	public ShoppingItemPrice findByItemId_First(long itemId,
		OrderByComparator<ShoppingItemPrice> orderByComparator)
		throws NoSuchItemPriceException {
		ShoppingItemPrice shoppingItemPrice = fetchByItemId_First(itemId,
				orderByComparator);

		if (shoppingItemPrice != null) {
			return shoppingItemPrice;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("itemId=");
		msg.append(itemId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchItemPriceException(msg.toString());
	}

	/**
	 * Returns the first shopping item price in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping item price, or <code>null</code> if a matching shopping item price could not be found
	 */
	@Override
	public ShoppingItemPrice fetchByItemId_First(long itemId,
		OrderByComparator<ShoppingItemPrice> orderByComparator) {
		List<ShoppingItemPrice> list = findByItemId(itemId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping item price in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping item price
	 * @throws com.liferay.shopping.NoSuchItemPriceException if a matching shopping item price could not be found
	 */
	@Override
	public ShoppingItemPrice findByItemId_Last(long itemId,
		OrderByComparator<ShoppingItemPrice> orderByComparator)
		throws NoSuchItemPriceException {
		ShoppingItemPrice shoppingItemPrice = fetchByItemId_Last(itemId,
				orderByComparator);

		if (shoppingItemPrice != null) {
			return shoppingItemPrice;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("itemId=");
		msg.append(itemId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchItemPriceException(msg.toString());
	}

	/**
	 * Returns the last shopping item price in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping item price, or <code>null</code> if a matching shopping item price could not be found
	 */
	@Override
	public ShoppingItemPrice fetchByItemId_Last(long itemId,
		OrderByComparator<ShoppingItemPrice> orderByComparator) {
		int count = countByItemId(itemId);

		if (count == 0) {
			return null;
		}

		List<ShoppingItemPrice> list = findByItemId(itemId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping item prices before and after the current shopping item price in the ordered set where itemId = &#63;.
	 *
	 * @param itemPriceId the primary key of the current shopping item price
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping item price
	 * @throws com.liferay.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice[] findByItemId_PrevAndNext(long itemPriceId,
		long itemId, OrderByComparator<ShoppingItemPrice> orderByComparator)
		throws NoSuchItemPriceException {
		ShoppingItemPrice shoppingItemPrice = findByPrimaryKey(itemPriceId);

		Session session = null;

		try {
			session = openSession();

			ShoppingItemPrice[] array = new ShoppingItemPriceImpl[3];

			array[0] = getByItemId_PrevAndNext(session, shoppingItemPrice,
					itemId, orderByComparator, true);

			array[1] = shoppingItemPrice;

			array[2] = getByItemId_PrevAndNext(session, shoppingItemPrice,
					itemId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingItemPrice getByItemId_PrevAndNext(Session session,
		ShoppingItemPrice shoppingItemPrice, long itemId,
		OrderByComparator<ShoppingItemPrice> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGITEMPRICE_WHERE);

		query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

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
			query.append(ShoppingItemPriceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(itemId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingItemPrice);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingItemPrice> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping item prices where itemId = &#63; from the database.
	 *
	 * @param itemId the item ID
	 */
	@Override
	public void removeByItemId(long itemId) {
		for (ShoppingItemPrice shoppingItemPrice : findByItemId(itemId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(shoppingItemPrice);
		}
	}

	/**
	 * Returns the number of shopping item prices where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the number of matching shopping item prices
	 */
	@Override
	public int countByItemId(long itemId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ITEMID;

		Object[] finderArgs = new Object[] { itemId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGITEMPRICE_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

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

	private static final String _FINDER_COLUMN_ITEMID_ITEMID_2 = "shoppingItemPrice.itemId = ?";

	public ShoppingItemPricePersistenceImpl() {
		setModelClass(ShoppingItemPrice.class);
	}

	/**
	 * Caches the shopping item price in the entity cache if it is enabled.
	 *
	 * @param shoppingItemPrice the shopping item price
	 */
	@Override
	public void cacheResult(ShoppingItemPrice shoppingItemPrice) {
		entityCache.putResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceImpl.class, shoppingItemPrice.getPrimaryKey(),
			shoppingItemPrice);

		shoppingItemPrice.resetOriginalValues();
	}

	/**
	 * Caches the shopping item prices in the entity cache if it is enabled.
	 *
	 * @param shoppingItemPrices the shopping item prices
	 */
	@Override
	public void cacheResult(List<ShoppingItemPrice> shoppingItemPrices) {
		for (ShoppingItemPrice shoppingItemPrice : shoppingItemPrices) {
			if (entityCache.getResult(
						ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemPriceImpl.class,
						shoppingItemPrice.getPrimaryKey()) == null) {
				cacheResult(shoppingItemPrice);
			}
			else {
				shoppingItemPrice.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping item prices.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ShoppingItemPriceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping item price.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingItemPrice shoppingItemPrice) {
		entityCache.removeResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceImpl.class, shoppingItemPrice.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ShoppingItemPrice> shoppingItemPrices) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingItemPrice shoppingItemPrice : shoppingItemPrices) {
			entityCache.removeResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemPriceImpl.class, shoppingItemPrice.getPrimaryKey());
		}
	}

	/**
	 * Creates a new shopping item price with the primary key. Does not add the shopping item price to the database.
	 *
	 * @param itemPriceId the primary key for the new shopping item price
	 * @return the new shopping item price
	 */
	@Override
	public ShoppingItemPrice create(long itemPriceId) {
		ShoppingItemPrice shoppingItemPrice = new ShoppingItemPriceImpl();

		shoppingItemPrice.setNew(true);
		shoppingItemPrice.setPrimaryKey(itemPriceId);

		return shoppingItemPrice;
	}

	/**
	 * Removes the shopping item price with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemPriceId the primary key of the shopping item price
	 * @return the shopping item price that was removed
	 * @throws com.liferay.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice remove(long itemPriceId)
		throws NoSuchItemPriceException {
		return remove((Serializable)itemPriceId);
	}

	/**
	 * Removes the shopping item price with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping item price
	 * @return the shopping item price that was removed
	 * @throws com.liferay.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice remove(Serializable primaryKey)
		throws NoSuchItemPriceException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
					primaryKey);

			if (shoppingItemPrice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchItemPriceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shoppingItemPrice);
		}
		catch (NoSuchItemPriceException nsee) {
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
	protected ShoppingItemPrice removeImpl(ShoppingItemPrice shoppingItemPrice) {
		shoppingItemPrice = toUnwrappedModel(shoppingItemPrice);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(shoppingItemPrice)) {
				shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
						shoppingItemPrice.getPrimaryKeyObj());
			}

			if (shoppingItemPrice != null) {
				session.delete(shoppingItemPrice);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingItemPrice != null) {
			clearCache(shoppingItemPrice);
		}

		return shoppingItemPrice;
	}

	@Override
	public ShoppingItemPrice updateImpl(ShoppingItemPrice shoppingItemPrice) {
		shoppingItemPrice = toUnwrappedModel(shoppingItemPrice);

		boolean isNew = shoppingItemPrice.isNew();

		ShoppingItemPriceModelImpl shoppingItemPriceModelImpl = (ShoppingItemPriceModelImpl)shoppingItemPrice;

		Session session = null;

		try {
			session = openSession();

			if (shoppingItemPrice.isNew()) {
				session.save(shoppingItemPrice);

				shoppingItemPrice.setNew(false);
			}
			else {
				shoppingItemPrice = (ShoppingItemPrice)session.merge(shoppingItemPrice);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingItemPriceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingItemPriceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingItemPriceModelImpl.getOriginalItemId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);

				args = new Object[] { shoppingItemPriceModelImpl.getItemId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);
			}
		}

		entityCache.putResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemPriceImpl.class, shoppingItemPrice.getPrimaryKey(),
			shoppingItemPrice, false);

		shoppingItemPrice.resetOriginalValues();

		return shoppingItemPrice;
	}

	protected ShoppingItemPrice toUnwrappedModel(
		ShoppingItemPrice shoppingItemPrice) {
		if (shoppingItemPrice instanceof ShoppingItemPriceImpl) {
			return shoppingItemPrice;
		}

		ShoppingItemPriceImpl shoppingItemPriceImpl = new ShoppingItemPriceImpl();

		shoppingItemPriceImpl.setNew(shoppingItemPrice.isNew());
		shoppingItemPriceImpl.setPrimaryKey(shoppingItemPrice.getPrimaryKey());

		shoppingItemPriceImpl.setItemPriceId(shoppingItemPrice.getItemPriceId());
		shoppingItemPriceImpl.setCompanyId(shoppingItemPrice.getCompanyId());
		shoppingItemPriceImpl.setItemId(shoppingItemPrice.getItemId());
		shoppingItemPriceImpl.setMinQuantity(shoppingItemPrice.getMinQuantity());
		shoppingItemPriceImpl.setMaxQuantity(shoppingItemPrice.getMaxQuantity());
		shoppingItemPriceImpl.setPrice(shoppingItemPrice.getPrice());
		shoppingItemPriceImpl.setDiscount(shoppingItemPrice.getDiscount());
		shoppingItemPriceImpl.setTaxable(shoppingItemPrice.isTaxable());
		shoppingItemPriceImpl.setShipping(shoppingItemPrice.getShipping());
		shoppingItemPriceImpl.setUseShippingFormula(shoppingItemPrice.isUseShippingFormula());
		shoppingItemPriceImpl.setStatus(shoppingItemPrice.getStatus());

		return shoppingItemPriceImpl;
	}

	/**
	 * Returns the shopping item price with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item price
	 * @return the shopping item price
	 * @throws com.liferay.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice findByPrimaryKey(Serializable primaryKey)
		throws NoSuchItemPriceException {
		ShoppingItemPrice shoppingItemPrice = fetchByPrimaryKey(primaryKey);

		if (shoppingItemPrice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchItemPriceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return shoppingItemPrice;
	}

	/**
	 * Returns the shopping item price with the primary key or throws a {@link com.liferay.shopping.NoSuchItemPriceException} if it could not be found.
	 *
	 * @param itemPriceId the primary key of the shopping item price
	 * @return the shopping item price
	 * @throws com.liferay.shopping.NoSuchItemPriceException if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice findByPrimaryKey(long itemPriceId)
		throws NoSuchItemPriceException {
		return findByPrimaryKey((Serializable)itemPriceId);
	}

	/**
	 * Returns the shopping item price with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item price
	 * @return the shopping item price, or <code>null</code> if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice fetchByPrimaryKey(Serializable primaryKey) {
		ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)entityCache.getResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemPriceImpl.class, primaryKey);

		if (shoppingItemPrice == _nullShoppingItemPrice) {
			return null;
		}

		if (shoppingItemPrice == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingItemPrice = (ShoppingItemPrice)session.get(ShoppingItemPriceImpl.class,
						primaryKey);

				if (shoppingItemPrice != null) {
					cacheResult(shoppingItemPrice);
				}
				else {
					entityCache.putResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemPriceImpl.class, primaryKey,
						_nullShoppingItemPrice);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemPriceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return shoppingItemPrice;
	}

	/**
	 * Returns the shopping item price with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param itemPriceId the primary key of the shopping item price
	 * @return the shopping item price, or <code>null</code> if a shopping item price with the primary key could not be found
	 */
	@Override
	public ShoppingItemPrice fetchByPrimaryKey(long itemPriceId) {
		return fetchByPrimaryKey((Serializable)itemPriceId);
	}

	@Override
	public Map<Serializable, ShoppingItemPrice> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ShoppingItemPrice> map = new HashMap<Serializable, ShoppingItemPrice>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ShoppingItemPrice shoppingItemPrice = fetchByPrimaryKey(primaryKey);

			if (shoppingItemPrice != null) {
				map.put(primaryKey, shoppingItemPrice);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ShoppingItemPrice shoppingItemPrice = (ShoppingItemPrice)entityCache.getResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemPriceImpl.class, primaryKey);

			if (shoppingItemPrice == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, shoppingItemPrice);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SHOPPINGITEMPRICE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ShoppingItemPrice shoppingItemPrice : (List<ShoppingItemPrice>)q.list()) {
				map.put(shoppingItemPrice.getPrimaryKeyObj(), shoppingItemPrice);

				cacheResult(shoppingItemPrice);

				uncachedPrimaryKeys.remove(shoppingItemPrice.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ShoppingItemPriceModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemPriceImpl.class, primaryKey,
					_nullShoppingItemPrice);
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
	 * Returns all the shopping item prices.
	 *
	 * @return the shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping item prices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item prices
	 * @param end the upper bound of the range of shopping item prices (not inclusive)
	 * @return the range of shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping item prices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item prices
	 * @param end the upper bound of the range of shopping item prices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findAll(int start, int end,
		OrderByComparator<ShoppingItemPrice> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping item prices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item prices
	 * @param end the upper bound of the range of shopping item prices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of shopping item prices
	 */
	@Override
	public List<ShoppingItemPrice> findAll(int start, int end,
		OrderByComparator<ShoppingItemPrice> orderByComparator,
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

		List<ShoppingItemPrice> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingItemPrice>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHOPPINGITEMPRICE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGITEMPRICE;

				if (pagination) {
					sql = sql.concat(ShoppingItemPriceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ShoppingItemPrice>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingItemPrice>)QueryUtil.list(q,
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
	 * Removes all the shopping item prices from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ShoppingItemPrice shoppingItemPrice : findAll()) {
			remove(shoppingItemPrice);
		}
	}

	/**
	 * Returns the number of shopping item prices.
	 *
	 * @return the number of shopping item prices
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGITEMPRICE);

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
		return ShoppingItemPriceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the shopping item price persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ShoppingItemPriceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SHOPPINGITEMPRICE = "SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice";
	private static final String _SQL_SELECT_SHOPPINGITEMPRICE_WHERE_PKS_IN = "SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice WHERE itemPriceId IN (";
	private static final String _SQL_SELECT_SHOPPINGITEMPRICE_WHERE = "SELECT shoppingItemPrice FROM ShoppingItemPrice shoppingItemPrice WHERE ";
	private static final String _SQL_COUNT_SHOPPINGITEMPRICE = "SELECT COUNT(shoppingItemPrice) FROM ShoppingItemPrice shoppingItemPrice";
	private static final String _SQL_COUNT_SHOPPINGITEMPRICE_WHERE = "SELECT COUNT(shoppingItemPrice) FROM ShoppingItemPrice shoppingItemPrice WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingItemPrice.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingItemPrice exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingItemPrice exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ShoppingItemPricePersistenceImpl.class);
	private static final ShoppingItemPrice _nullShoppingItemPrice = new ShoppingItemPriceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingItemPrice> toCacheModel() {
				return _nullShoppingItemPriceCacheModel;
			}
		};

	private static final CacheModel<ShoppingItemPrice> _nullShoppingItemPriceCacheModel =
		new CacheModel<ShoppingItemPrice>() {
			@Override
			public ShoppingItemPrice toEntityModel() {
				return _nullShoppingItemPrice;
			}
		};
}