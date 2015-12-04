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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.shopping.exception.NoSuchCartException;
import com.liferay.shopping.model.ShoppingCart;
import com.liferay.shopping.model.impl.ShoppingCartImpl;
import com.liferay.shopping.model.impl.ShoppingCartModelImpl;
import com.liferay.shopping.service.persistence.ShoppingCartPersistence;

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
 * The persistence implementation for the shopping cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartPersistence
 * @see com.liferay.shopping.service.persistence.ShoppingCartUtil
 * @generated
 */
@ProviderType
public class ShoppingCartPersistenceImpl extends BasePersistenceImpl<ShoppingCart>
	implements ShoppingCartPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShoppingCartUtil} to access the shopping cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingCartImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			ShoppingCartModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the shopping carts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping carts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @return the range of matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping carts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByGroupId(long groupId, int start, int end,
		OrderByComparator<ShoppingCart> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping carts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByGroupId(long groupId, int start, int end,
		OrderByComparator<ShoppingCart> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<ShoppingCart> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingCart shoppingCart : list) {
					if ((groupId != shoppingCart.getGroupId())) {
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

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ShoppingCartModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart findByGroupId_First(long groupId,
		OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = fetchByGroupId_First(groupId,
				orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart fetchByGroupId_First(long groupId,
		OrderByComparator<ShoppingCart> orderByComparator) {
		List<ShoppingCart> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart findByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart fetchByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCart> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ShoppingCart> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping carts before and after the current shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param cartId the primary key of the current shopping cart
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart[] findByGroupId_PrevAndNext(long cartId, long groupId,
		OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, shoppingCart, groupId,
					orderByComparator, true);

			array[1] = shoppingCart;

			array[2] = getByGroupId_PrevAndNext(session, shoppingCart, groupId,
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

	protected ShoppingCart getByGroupId_PrevAndNext(Session session,
		ShoppingCart shoppingCart, long groupId,
		OrderByComparator<ShoppingCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(ShoppingCartModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping carts where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ShoppingCart shoppingCart : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(shoppingCart);
		}
	}

	/**
	 * Returns the number of shopping carts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping carts
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "shoppingCart.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			ShoppingCartModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the shopping carts where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping carts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @return the range of matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping carts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByUserId(long userId, int start, int end,
		OrderByComparator<ShoppingCart> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping carts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping carts
	 */
	@Override
	public List<ShoppingCart> findByUserId(long userId, int start, int end,
		OrderByComparator<ShoppingCart> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<ShoppingCart> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingCart shoppingCart : list) {
					if ((userId != shoppingCart.getUserId())) {
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

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ShoppingCartModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart findByUserId_First(long userId,
		OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = fetchByUserId_First(userId,
				orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart fetchByUserId_First(long userId,
		OrderByComparator<ShoppingCart> orderByComparator) {
		List<ShoppingCart> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart findByUserId_Last(long userId,
		OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = fetchByUserId_Last(userId, orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart fetchByUserId_Last(long userId,
		OrderByComparator<ShoppingCart> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<ShoppingCart> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping carts before and after the current shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param cartId the primary key of the current shopping cart
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart[] findByUserId_PrevAndNext(long cartId, long userId,
		OrderByComparator<ShoppingCart> orderByComparator)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = getByUserId_PrevAndNext(session, shoppingCart, userId,
					orderByComparator, true);

			array[1] = shoppingCart;

			array[2] = getByUserId_PrevAndNext(session, shoppingCart, userId,
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

	protected ShoppingCart getByUserId_PrevAndNext(Session session,
		ShoppingCart shoppingCart, long userId,
		OrderByComparator<ShoppingCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(ShoppingCartModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping carts where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (ShoppingCart shoppingCart : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(shoppingCart);
		}
	}

	/**
	 * Returns the number of shopping carts where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching shopping carts
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "shoppingCart.userId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			ShoppingCartModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingCartModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the shopping cart where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.shopping.NoSuchCartException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart findByG_U(long groupId, long userId)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = fetchByG_U(groupId, userId);

		if (shoppingCart == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCartException(msg.toString());
		}

		return shoppingCart;
	}

	/**
	 * Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart fetchByG_U(long groupId, long userId) {
		return fetchByG_U(groupId, userId, true);
	}

	/**
	 * Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 */
	@Override
	public ShoppingCart fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result instanceof ShoppingCart) {
			ShoppingCart shoppingCart = (ShoppingCart)result;

			if ((groupId != shoppingCart.getGroupId()) ||
					(userId != shoppingCart.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<ShoppingCart> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_U, finderArgs,
						list);
				}
				else {
					ShoppingCart shoppingCart = list.get(0);

					result = shoppingCart;

					cacheResult(shoppingCart);

					if ((shoppingCart.getGroupId() != groupId) ||
							(shoppingCart.getUserId() != userId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, shoppingCart);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U, finderArgs);

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
			return (ShoppingCart)result;
		}
	}

	/**
	 * Removes the shopping cart where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the shopping cart that was removed
	 */
	@Override
	public ShoppingCart removeByG_U(long groupId, long userId)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = findByG_U(groupId, userId);

		return remove(shoppingCart);
	}

	/**
	 * Returns the number of shopping carts where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching shopping carts
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U;

		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "shoppingCart.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "shoppingCart.userId = ?";

	public ShoppingCartPersistenceImpl() {
		setModelClass(ShoppingCart.class);
	}

	/**
	 * Caches the shopping cart in the entity cache if it is enabled.
	 *
	 * @param shoppingCart the shopping cart
	 */
	@Override
	public void cacheResult(ShoppingCart shoppingCart) {
		entityCache.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey(), shoppingCart);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] { shoppingCart.getGroupId(), shoppingCart.getUserId() },
			shoppingCart);

		shoppingCart.resetOriginalValues();
	}

	/**
	 * Caches the shopping carts in the entity cache if it is enabled.
	 *
	 * @param shoppingCarts the shopping carts
	 */
	@Override
	public void cacheResult(List<ShoppingCart> shoppingCarts) {
		for (ShoppingCart shoppingCart : shoppingCarts) {
			if (entityCache.getResult(
						ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCartImpl.class, shoppingCart.getPrimaryKey()) == null) {
				cacheResult(shoppingCart);
			}
			else {
				shoppingCart.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping carts.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ShoppingCartImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping cart.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingCart shoppingCart) {
		entityCache.removeResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ShoppingCartModelImpl)shoppingCart);
	}

	@Override
	public void clearCache(List<ShoppingCart> shoppingCarts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingCart shoppingCart : shoppingCarts) {
			entityCache.removeResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCartImpl.class, shoppingCart.getPrimaryKey());

			clearUniqueFindersCache((ShoppingCartModelImpl)shoppingCart);
		}
	}

	protected void cacheUniqueFindersCache(
		ShoppingCartModelImpl shoppingCartModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					shoppingCartModelImpl.getGroupId(),
					shoppingCartModelImpl.getUserId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_G_U, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_G_U, args,
				shoppingCartModelImpl);
		}
		else {
			if ((shoppingCartModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingCartModelImpl.getGroupId(),
						shoppingCartModelImpl.getUserId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_G_U, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_G_U, args,
					shoppingCartModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		ShoppingCartModelImpl shoppingCartModelImpl) {
		Object[] args = new Object[] {
				shoppingCartModelImpl.getGroupId(),
				shoppingCartModelImpl.getUserId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U, args);

		if ((shoppingCartModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_U.getColumnBitmask()) != 0) {
			args = new Object[] {
					shoppingCartModelImpl.getOriginalGroupId(),
					shoppingCartModelImpl.getOriginalUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U, args);
		}
	}

	/**
	 * Creates a new shopping cart with the primary key. Does not add the shopping cart to the database.
	 *
	 * @param cartId the primary key for the new shopping cart
	 * @return the new shopping cart
	 */
	@Override
	public ShoppingCart create(long cartId) {
		ShoppingCart shoppingCart = new ShoppingCartImpl();

		shoppingCart.setNew(true);
		shoppingCart.setPrimaryKey(cartId);

		return shoppingCart;
	}

	/**
	 * Removes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cartId the primary key of the shopping cart
	 * @return the shopping cart that was removed
	 * @throws com.liferay.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart remove(long cartId) throws NoSuchCartException {
		return remove((Serializable)cartId);
	}

	/**
	 * Removes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping cart
	 * @return the shopping cart that was removed
	 * @throws com.liferay.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart remove(Serializable primaryKey)
		throws NoSuchCartException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCart shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
					primaryKey);

			if (shoppingCart == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shoppingCart);
		}
		catch (NoSuchCartException nsee) {
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
	protected ShoppingCart removeImpl(ShoppingCart shoppingCart) {
		shoppingCart = toUnwrappedModel(shoppingCart);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(shoppingCart)) {
				shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
						shoppingCart.getPrimaryKeyObj());
			}

			if (shoppingCart != null) {
				session.delete(shoppingCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingCart != null) {
			clearCache(shoppingCart);
		}

		return shoppingCart;
	}

	@Override
	public ShoppingCart updateImpl(ShoppingCart shoppingCart) {
		shoppingCart = toUnwrappedModel(shoppingCart);

		boolean isNew = shoppingCart.isNew();

		ShoppingCartModelImpl shoppingCartModelImpl = (ShoppingCartModelImpl)shoppingCart;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (shoppingCart.getCreateDate() == null)) {
			if (serviceContext == null) {
				shoppingCart.setCreateDate(now);
			}
			else {
				shoppingCart.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!shoppingCartModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				shoppingCart.setModifiedDate(now);
			}
			else {
				shoppingCart.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (shoppingCart.isNew()) {
				session.save(shoppingCart);

				shoppingCart.setNew(false);
			}
			else {
				shoppingCart = (ShoppingCart)session.merge(shoppingCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingCartModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingCartModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { shoppingCartModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((shoppingCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingCartModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { shoppingCartModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		entityCache.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey(), shoppingCart,
			false);

		clearUniqueFindersCache(shoppingCartModelImpl);
		cacheUniqueFindersCache(shoppingCartModelImpl, isNew);

		shoppingCart.resetOriginalValues();

		return shoppingCart;
	}

	protected ShoppingCart toUnwrappedModel(ShoppingCart shoppingCart) {
		if (shoppingCart instanceof ShoppingCartImpl) {
			return shoppingCart;
		}

		ShoppingCartImpl shoppingCartImpl = new ShoppingCartImpl();

		shoppingCartImpl.setNew(shoppingCart.isNew());
		shoppingCartImpl.setPrimaryKey(shoppingCart.getPrimaryKey());

		shoppingCartImpl.setCartId(shoppingCart.getCartId());
		shoppingCartImpl.setGroupId(shoppingCart.getGroupId());
		shoppingCartImpl.setCompanyId(shoppingCart.getCompanyId());
		shoppingCartImpl.setUserId(shoppingCart.getUserId());
		shoppingCartImpl.setUserName(shoppingCart.getUserName());
		shoppingCartImpl.setCreateDate(shoppingCart.getCreateDate());
		shoppingCartImpl.setModifiedDate(shoppingCart.getModifiedDate());
		shoppingCartImpl.setItemIds(shoppingCart.getItemIds());
		shoppingCartImpl.setCouponCodes(shoppingCart.getCouponCodes());
		shoppingCartImpl.setAltShipping(shoppingCart.getAltShipping());
		shoppingCartImpl.setInsure(shoppingCart.isInsure());

		return shoppingCartImpl;
	}

	/**
	 * Returns the shopping cart with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping cart
	 * @return the shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCartException {
		ShoppingCart shoppingCart = fetchByPrimaryKey(primaryKey);

		if (shoppingCart == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return shoppingCart;
	}

	/**
	 * Returns the shopping cart with the primary key or throws a {@link com.liferay.shopping.NoSuchCartException} if it could not be found.
	 *
	 * @param cartId the primary key of the shopping cart
	 * @return the shopping cart
	 * @throws com.liferay.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart findByPrimaryKey(long cartId)
		throws NoSuchCartException {
		return findByPrimaryKey((Serializable)cartId);
	}

	/**
	 * Returns the shopping cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping cart
	 * @return the shopping cart, or <code>null</code> if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart fetchByPrimaryKey(Serializable primaryKey) {
		ShoppingCart shoppingCart = (ShoppingCart)entityCache.getResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCartImpl.class, primaryKey);

		if (shoppingCart == _nullShoppingCart) {
			return null;
		}

		if (shoppingCart == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
						primaryKey);

				if (shoppingCart != null) {
					cacheResult(shoppingCart);
				}
				else {
					entityCache.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCartImpl.class, primaryKey, _nullShoppingCart);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingCartImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return shoppingCart;
	}

	/**
	 * Returns the shopping cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cartId the primary key of the shopping cart
	 * @return the shopping cart, or <code>null</code> if a shopping cart with the primary key could not be found
	 */
	@Override
	public ShoppingCart fetchByPrimaryKey(long cartId) {
		return fetchByPrimaryKey((Serializable)cartId);
	}

	@Override
	public Map<Serializable, ShoppingCart> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ShoppingCart> map = new HashMap<Serializable, ShoppingCart>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ShoppingCart shoppingCart = fetchByPrimaryKey(primaryKey);

			if (shoppingCart != null) {
				map.put(primaryKey, shoppingCart);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ShoppingCart shoppingCart = (ShoppingCart)entityCache.getResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingCartImpl.class, primaryKey);

			if (shoppingCart == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, shoppingCart);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SHOPPINGCART_WHERE_PKS_IN);

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

			for (ShoppingCart shoppingCart : (List<ShoppingCart>)q.list()) {
				map.put(shoppingCart.getPrimaryKeyObj(), shoppingCart);

				cacheResult(shoppingCart);

				uncachedPrimaryKeys.remove(shoppingCart.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingCartImpl.class, primaryKey, _nullShoppingCart);
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
	 * Returns all the shopping carts.
	 *
	 * @return the shopping carts
	 */
	@Override
	public List<ShoppingCart> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @return the range of shopping carts
	 */
	@Override
	public List<ShoppingCart> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping carts
	 */
	@Override
	public List<ShoppingCart> findAll(int start, int end,
		OrderByComparator<ShoppingCart> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of shopping carts
	 */
	@Override
	public List<ShoppingCart> findAll(int start, int end,
		OrderByComparator<ShoppingCart> orderByComparator,
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

		List<ShoppingCart> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingCart>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHOPPINGCART);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGCART;

				if (pagination) {
					sql = sql.concat(ShoppingCartModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the shopping carts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ShoppingCart shoppingCart : findAll()) {
			remove(shoppingCart);
		}
	}

	/**
	 * Returns the number of shopping carts.
	 *
	 * @return the number of shopping carts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGCART);

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
		return ShoppingCartModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the shopping cart persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ShoppingCartImpl.class.getName());
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
	private static final String _SQL_SELECT_SHOPPINGCART = "SELECT shoppingCart FROM ShoppingCart shoppingCart";
	private static final String _SQL_SELECT_SHOPPINGCART_WHERE_PKS_IN = "SELECT shoppingCart FROM ShoppingCart shoppingCart WHERE cartId IN (";
	private static final String _SQL_SELECT_SHOPPINGCART_WHERE = "SELECT shoppingCart FROM ShoppingCart shoppingCart WHERE ";
	private static final String _SQL_COUNT_SHOPPINGCART = "SELECT COUNT(shoppingCart) FROM ShoppingCart shoppingCart";
	private static final String _SQL_COUNT_SHOPPINGCART_WHERE = "SELECT COUNT(shoppingCart) FROM ShoppingCart shoppingCart WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingCart.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingCart exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingCart exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ShoppingCartPersistenceImpl.class);
	private static final ShoppingCart _nullShoppingCart = new ShoppingCartImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingCart> toCacheModel() {
				return _nullShoppingCartCacheModel;
			}
		};

	private static final CacheModel<ShoppingCart> _nullShoppingCartCacheModel = new CacheModel<ShoppingCart>() {
			@Override
			public ShoppingCart toEntityModel() {
				return _nullShoppingCart;
			}
		};
}