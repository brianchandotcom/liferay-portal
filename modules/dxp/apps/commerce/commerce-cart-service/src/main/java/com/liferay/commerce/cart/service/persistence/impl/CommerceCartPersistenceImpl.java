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

package com.liferay.commerce.cart.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.exception.NoSuchCartException;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.model.impl.CommerceCartImpl;
import com.liferay.commerce.cart.model.impl.CommerceCartModelImpl;
import com.liferay.commerce.cart.service.persistence.CommerceCartPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

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
 * The persistence implementation for the commerce cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceCartPersistence
 * @see com.liferay.commerce.cart.service.persistence.CommerceCartUtil
 * @generated
 */
@ProviderType
public class CommerceCartPersistenceImpl extends BasePersistenceImpl<CommerceCart>
	implements CommerceCartPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceCartUtil} to access the commerce cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceCartImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			CommerceCartModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceCartModelImpl.TYPE_COLUMN_BITMASK |
			CommerceCartModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the commerce carts where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_T(long groupId, int type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce carts where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_T(long groupId, int type, int start,
		int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_T(long groupId, int type, int start,
		int end, OrderByComparator<CommerceCart> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_T(long groupId, int type, int start,
		int end, OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCart commerceCart : list) {
					if ((groupId != commerceCart.getGroupId()) ||
							(type != commerceCart.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				if (!pagination) {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByG_T_First(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByG_T_First(groupId, type,
				orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_T_First(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		List<CommerceCart> list = findByG_T(groupId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByG_T_Last(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByG_T_Last(groupId, type,
				orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_T_Last(long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceCart> list = findByG_T(groupId, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] findByG_T_PrevAndNext(long CommerceCartId,
		long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = getByG_T_PrevAndNext(session, commerceCart, groupId,
					type, orderByComparator, true);

			array[1] = commerceCart;

			array[2] = getByG_T_PrevAndNext(session, commerceCart, groupId,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCart getByG_T_PrevAndNext(Session session,
		CommerceCart commerceCart, long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCECART_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce carts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_T(long groupId, int type) {
		return filterFindByG_T(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_T(long groupId, int type,
		int start, int end) {
		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_T(long groupId, int type,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceCartModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CommerceCartImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CommerceCartImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			return (List<CommerceCart>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set of commerce carts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] filterFindByG_T_PrevAndNext(long CommerceCartId,
		long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(CommerceCartId, groupId, type,
				orderByComparator);
		}

		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(session, commerceCart,
					groupId, type, orderByComparator, true);

			array[1] = commerceCart;

			array[2] = filterGetByG_T_PrevAndNext(session, commerceCart,
					groupId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCart filterGetByG_T_PrevAndNext(Session session,
		CommerceCart commerceCart, long groupId, int type,
		OrderByComparator<CommerceCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceCartModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CommerceCartImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CommerceCartImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce carts where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (CommerceCart commerceCart : findByG_T(groupId, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

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

	/**
	 * Returns the number of commerce carts that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching commerce carts that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_COMMERCECART_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "commerceCart.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "commerceCart.type = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL = "commerceCart.type_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			CommerceCartModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceCartModelImpl.USERID_COLUMN_BITMASK |
			CommerceCartModelImpl.TYPE_COLUMN_BITMASK |
			CommerceCartModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_T(long groupId, long userId, int type) {
		return findByG_U_T(groupId, userId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_T(long groupId, long userId, int type,
		int start, int end) {
		return findByG_U_T(groupId, userId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_T(long groupId, long userId, int type,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator) {
		return findByG_U_T(groupId, userId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_T(long groupId, long userId, int type,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T;
			finderArgs = new Object[] { groupId, userId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_T;
			finderArgs = new Object[] {
					groupId, userId, type,
					
					start, end, orderByComparator
				};
		}

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCart commerceCart : list) {
					if ((groupId != commerceCart.getGroupId()) ||
							(userId != commerceCart.getUserId()) ||
							(type != commerceCart.getType())) {
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

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(type);

				if (!pagination) {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByG_U_T_First(long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByG_U_T_First(groupId, userId, type,
				orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_U_T_First(long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		List<CommerceCart> list = findByG_U_T(groupId, userId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByG_U_T_Last(long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByG_U_T_Last(groupId, userId, type,
				orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_U_T_Last(long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		int count = countByG_U_T(groupId, userId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceCart> list = findByG_U_T(groupId, userId, type, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] findByG_U_T_PrevAndNext(long CommerceCartId,
		long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = getByG_U_T_PrevAndNext(session, commerceCart, groupId,
					userId, type, orderByComparator, true);

			array[1] = commerceCart;

			array[2] = getByG_U_T_PrevAndNext(session, commerceCart, groupId,
					userId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCart getByG_U_T_PrevAndNext(Session session,
		CommerceCart commerceCart, long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_COMMERCECART_WHERE);

		query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

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
			query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_U_T(long groupId, long userId,
		int type) {
		return filterFindByG_U_T(groupId, userId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_U_T(long groupId, long userId,
		int type, int start, int end) {
		return filterFindByG_U_T(groupId, userId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts that the user has permissions to view where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_U_T(long groupId, long userId,
		int type, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_T(groupId, userId, type, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceCartModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CommerceCartImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CommerceCartImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(type);

			return (List<CommerceCart>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set of commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] filterFindByG_U_T_PrevAndNext(long CommerceCartId,
		long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_T_PrevAndNext(CommerceCartId, groupId, userId,
				type, orderByComparator);
		}

		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = filterGetByG_U_T_PrevAndNext(session, commerceCart,
					groupId, userId, type, orderByComparator, true);

			array[1] = commerceCart;

			array[2] = filterGetByG_U_T_PrevAndNext(session, commerceCart,
					groupId, userId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCart filterGetByG_U_T_PrevAndNext(Session session,
		CommerceCart commerceCart, long groupId, long userId, int type,
		OrderByComparator<CommerceCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceCartModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CommerceCartImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CommerceCartImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce carts where groupId = &#63; and userId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 */
	@Override
	public void removeByG_U_T(long groupId, long userId, int type) {
		for (CommerceCart commerceCart : findByG_U_T(groupId, userId, type,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByG_U_T(long groupId, long userId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_T;

		Object[] finderArgs = new Object[] { groupId, userId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(type);

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

	/**
	 * Returns the number of commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce carts that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_T(long groupId, long userId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_T(groupId, userId, type);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_COMMERCECART_WHERE);

		query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_T_USERID_2);

		query.append(_FINDER_COLUMN_G_U_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(type);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_T_GROUPID_2 = "commerceCart.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_T_USERID_2 = "commerceCart.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_T_TYPE_2 = "commerceCart.type = ?";
	private static final String _FINDER_COLUMN_G_U_T_TYPE_2_SQL = "commerceCart.type_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_N_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_N_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_T =
		new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_N_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			CommerceCartModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceCartModelImpl.USERID_COLUMN_BITMASK |
			CommerceCartModelImpl.NAME_COLUMN_BITMASK |
			CommerceCartModelImpl.TYPE_COLUMN_BITMASK |
			CommerceCartModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_N_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_N_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_N_T(long groupId, long userId,
		String name, int type) {
		return findByG_U_N_T(groupId, userId, name, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_N_T(long groupId, long userId,
		String name, int type, int start, int end) {
		return findByG_U_N_T(groupId, userId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_N_T(long groupId, long userId,
		String name, int type, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return findByG_U_N_T(groupId, userId, name, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByG_U_N_T(long groupId, long userId,
		String name, int type, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_T;
			finderArgs = new Object[] { groupId, userId, name, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_N_T;
			finderArgs = new Object[] {
					groupId, userId, name, type,
					
					start, end, orderByComparator
				};
		}

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCart commerceCart : list) {
					if ((groupId != commerceCart.getGroupId()) ||
							(userId != commerceCart.getUserId()) ||
							!Objects.equals(name, commerceCart.getName()) ||
							(type != commerceCart.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_U_N_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_N_T_USERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_U_N_T_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_N_T_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_U_N_T_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_U_N_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(type);

				if (!pagination) {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByG_U_N_T_First(long groupId, long userId,
		String name, int type, OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByG_U_N_T_First(groupId, userId, name,
				type, orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_U_N_T_First(long groupId, long userId,
		String name, int type, OrderByComparator<CommerceCart> orderByComparator) {
		List<CommerceCart> list = findByG_U_N_T(groupId, userId, name, type, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByG_U_N_T_Last(long groupId, long userId,
		String name, int type, OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByG_U_N_T_Last(groupId, userId, name,
				type, orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_U_N_T_Last(long groupId, long userId,
		String name, int type, OrderByComparator<CommerceCart> orderByComparator) {
		int count = countByG_U_N_T(groupId, userId, name, type);

		if (count == 0) {
			return null;
		}

		List<CommerceCart> list = findByG_U_N_T(groupId, userId, name, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] findByG_U_N_T_PrevAndNext(long CommerceCartId,
		long groupId, long userId, String name, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = getByG_U_N_T_PrevAndNext(session, commerceCart, groupId,
					userId, name, type, orderByComparator, true);

			array[1] = commerceCart;

			array[2] = getByG_U_N_T_PrevAndNext(session, commerceCart, groupId,
					userId, name, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCart getByG_U_N_T_PrevAndNext(Session session,
		CommerceCart commerceCart, long groupId, long userId, String name,
		int type, OrderByComparator<CommerceCart> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_COMMERCECART_WHERE);

		query.append(_FINDER_COLUMN_G_U_N_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_T_USERID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_U_N_T_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_U_N_T_TYPE_2);

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
			query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_U_N_T(long groupId, long userId,
		String name, int type) {
		return filterFindByG_U_N_T(groupId, userId, name, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_U_N_T(long groupId, long userId,
		String name, int type, int start, int end) {
		return filterFindByG_U_N_T(groupId, userId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts that the user has permissions to view where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts that the user has permission to view
	 */
	@Override
	public List<CommerceCart> filterFindByG_U_N_T(long groupId, long userId,
		String name, int type, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_N_T(groupId, userId, name, type, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_N_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_T_USERID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_U_N_T_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_U_N_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceCartModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, CommerceCartImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, CommerceCartImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(type);

			return (List<CommerceCart>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set of commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] filterFindByG_U_N_T_PrevAndNext(long CommerceCartId,
		long groupId, long userId, String name, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_N_T_PrevAndNext(CommerceCartId, groupId, userId,
				name, type, orderByComparator);
		}

		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = filterGetByG_U_N_T_PrevAndNext(session, commerceCart,
					groupId, userId, name, type, orderByComparator, true);

			array[1] = commerceCart;

			array[2] = filterGetByG_U_N_T_PrevAndNext(session, commerceCart,
					groupId, userId, name, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCart filterGetByG_U_N_T_PrevAndNext(Session session,
		CommerceCart commerceCart, long groupId, long userId, String name,
		int type, OrderByComparator<CommerceCart> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(8 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_N_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_T_USERID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_U_N_T_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_U_N_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(CommerceCartModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, CommerceCartImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, CommerceCartImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 */
	@Override
	public void removeByG_U_N_T(long groupId, long userId, String name, int type) {
		for (CommerceCart commerceCart : findByG_U_N_T(groupId, userId, name,
				type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByG_U_N_T(long groupId, long userId, String name, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_N_T;

		Object[] finderArgs = new Object[] { groupId, userId, name, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_U_N_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_N_T_USERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_U_N_T_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_N_T_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_U_N_T_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_U_N_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(type);

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

	/**
	 * Returns the number of commerce carts that the user has permission to view where groupId = &#63; and userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching commerce carts that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_N_T(long groupId, long userId, String name,
		int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_N_T(groupId, userId, name, type);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_COMMERCECART_WHERE);

		query.append(_FINDER_COLUMN_G_U_N_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_N_T_USERID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_U_N_T_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_U_N_T_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_U_N_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				CommerceCart.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(type);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_N_T_GROUPID_2 = "commerceCart.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_N_T_USERID_2 = "commerceCart.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_N_T_NAME_1 = "commerceCart.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_U_N_T_NAME_2 = "commerceCart.name = ? AND ";
	private static final String _FINDER_COLUMN_G_U_N_T_NAME_3 = "(commerceCart.name IS NULL OR commerceCart.name = '') AND ";
	private static final String _FINDER_COLUMN_G_U_N_T_TYPE_2 = "commerceCart.type = ?";
	private static final String _FINDER_COLUMN_G_U_N_T_TYPE_2_SQL = "commerceCart.type_ = ?";

	public CommerceCartPersistenceImpl() {
		setModelClass(CommerceCart.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce cart in the entity cache if it is enabled.
	 *
	 * @param commerceCart the commerce cart
	 */
	@Override
	public void cacheResult(CommerceCart commerceCart) {
		entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartImpl.class, commerceCart.getPrimaryKey(), commerceCart);

		commerceCart.resetOriginalValues();
	}

	/**
	 * Caches the commerce carts in the entity cache if it is enabled.
	 *
	 * @param commerceCarts the commerce carts
	 */
	@Override
	public void cacheResult(List<CommerceCart> commerceCarts) {
		for (CommerceCart commerceCart : commerceCarts) {
			if (entityCache.getResult(
						CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCartImpl.class, commerceCart.getPrimaryKey()) == null) {
				cacheResult(commerceCart);
			}
			else {
				commerceCart.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce carts.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceCartImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce cart.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceCart commerceCart) {
		entityCache.removeResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartImpl.class, commerceCart.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceCart> commerceCarts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceCart commerceCart : commerceCarts) {
			entityCache.removeResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCartImpl.class, commerceCart.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	 *
	 * @param CommerceCartId the primary key for the new commerce cart
	 * @return the new commerce cart
	 */
	@Override
	public CommerceCart create(long CommerceCartId) {
		CommerceCart commerceCart = new CommerceCartImpl();

		commerceCart.setNew(true);
		commerceCart.setPrimaryKey(CommerceCartId);

		commerceCart.setCompanyId(companyProvider.getCompanyId());

		return commerceCart;
	}

	/**
	 * Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommerceCartId the primary key of the commerce cart
	 * @return the commerce cart that was removed
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart remove(long CommerceCartId) throws NoSuchCartException {
		return remove((Serializable)CommerceCartId);
	}

	/**
	 * Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce cart
	 * @return the commerce cart that was removed
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart remove(Serializable primaryKey)
		throws NoSuchCartException {
		Session session = null;

		try {
			session = openSession();

			CommerceCart commerceCart = (CommerceCart)session.get(CommerceCartImpl.class,
					primaryKey);

			if (commerceCart == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceCart);
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
	protected CommerceCart removeImpl(CommerceCart commerceCart) {
		commerceCart = toUnwrappedModel(commerceCart);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceCart)) {
				commerceCart = (CommerceCart)session.get(CommerceCartImpl.class,
						commerceCart.getPrimaryKeyObj());
			}

			if (commerceCart != null) {
				session.delete(commerceCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceCart != null) {
			clearCache(commerceCart);
		}

		return commerceCart;
	}

	@Override
	public CommerceCart updateImpl(CommerceCart commerceCart) {
		commerceCart = toUnwrappedModel(commerceCart);

		boolean isNew = commerceCart.isNew();

		CommerceCartModelImpl commerceCartModelImpl = (CommerceCartModelImpl)commerceCart;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceCart.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceCart.setCreateDate(now);
			}
			else {
				commerceCart.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceCartModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceCart.setModifiedDate(now);
			}
			else {
				commerceCart.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceCart.isNew()) {
				session.save(commerceCart);

				commerceCart.setNew(false);
			}
			else {
				commerceCart = (CommerceCart)session.merge(commerceCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceCartModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceCartModelImpl.getGroupId(),
					commerceCartModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
				args);

			args = new Object[] {
					commerceCartModelImpl.getGroupId(),
					commerceCartModelImpl.getUserId(),
					commerceCartModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T,
				args);

			args = new Object[] {
					commerceCartModelImpl.getGroupId(),
					commerceCartModelImpl.getUserId(),
					commerceCartModelImpl.getName(),
					commerceCartModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_N_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_T,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartModelImpl.getOriginalGroupId(),
						commerceCartModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						commerceCartModelImpl.getGroupId(),
						commerceCartModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((commerceCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartModelImpl.getOriginalGroupId(),
						commerceCartModelImpl.getOriginalUserId(),
						commerceCartModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T,
					args);

				args = new Object[] {
						commerceCartModelImpl.getGroupId(),
						commerceCartModelImpl.getUserId(),
						commerceCartModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_T,
					args);
			}

			if ((commerceCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartModelImpl.getOriginalGroupId(),
						commerceCartModelImpl.getOriginalUserId(),
						commerceCartModelImpl.getOriginalName(),
						commerceCartModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_N_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_T,
					args);

				args = new Object[] {
						commerceCartModelImpl.getGroupId(),
						commerceCartModelImpl.getUserId(),
						commerceCartModelImpl.getName(),
						commerceCartModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_N_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_N_T,
					args);
			}
		}

		entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartImpl.class, commerceCart.getPrimaryKey(), commerceCart,
			false);

		commerceCart.resetOriginalValues();

		return commerceCart;
	}

	protected CommerceCart toUnwrappedModel(CommerceCart commerceCart) {
		if (commerceCart instanceof CommerceCartImpl) {
			return commerceCart;
		}

		CommerceCartImpl commerceCartImpl = new CommerceCartImpl();

		commerceCartImpl.setNew(commerceCart.isNew());
		commerceCartImpl.setPrimaryKey(commerceCart.getPrimaryKey());

		commerceCartImpl.setCommerceCartId(commerceCart.getCommerceCartId());
		commerceCartImpl.setGroupId(commerceCart.getGroupId());
		commerceCartImpl.setCompanyId(commerceCart.getCompanyId());
		commerceCartImpl.setUserId(commerceCart.getUserId());
		commerceCartImpl.setUserName(commerceCart.getUserName());
		commerceCartImpl.setCreateDate(commerceCart.getCreateDate());
		commerceCartImpl.setModifiedDate(commerceCart.getModifiedDate());
		commerceCartImpl.setName(commerceCart.getName());
		commerceCartImpl.setType(commerceCart.getType());

		return commerceCartImpl;
	}

	/**
	 * Returns the commerce cart with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cart
	 * @return the commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByPrimaryKey(primaryKey);

		if (commerceCart == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceCart;
	}

	/**
	 * Returns the commerce cart with the primary key or throws a {@link NoSuchCartException} if it could not be found.
	 *
	 * @param CommerceCartId the primary key of the commerce cart
	 * @return the commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart findByPrimaryKey(long CommerceCartId)
		throws NoSuchCartException {
		return findByPrimaryKey((Serializable)CommerceCartId);
	}

	/**
	 * Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cart
	 * @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCartImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceCart commerceCart = (CommerceCart)serializable;

		if (commerceCart == null) {
			Session session = null;

			try {
				session = openSession();

				commerceCart = (CommerceCart)session.get(CommerceCartImpl.class,
						primaryKey);

				if (commerceCart != null) {
					cacheResult(commerceCart);
				}
				else {
					entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCartImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceCart;
	}

	/**
	 * Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommerceCartId the primary key of the commerce cart
	 * @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart fetchByPrimaryKey(long CommerceCartId) {
		return fetchByPrimaryKey((Serializable)CommerceCartId);
	}

	@Override
	public Map<Serializable, CommerceCart> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceCart> map = new HashMap<Serializable, CommerceCart>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceCart commerceCart = fetchByPrimaryKey(primaryKey);

			if (commerceCart != null) {
				map.put(primaryKey, commerceCart);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceCart)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCECART_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceCart commerceCart : (List<CommerceCart>)q.list()) {
				map.put(commerceCart.getPrimaryKeyObj(), commerceCart);

				cacheResult(commerceCart);

				uncachedPrimaryKeys.remove(commerceCart.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce carts.
	 *
	 * @return the commerce carts
	 */
	@Override
	public List<CommerceCart> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of commerce carts
	 */
	@Override
	public List<CommerceCart> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce carts
	 */
	@Override
	public List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce carts
	 */
	@Override
	public List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
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

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCECART);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECART;

				if (pagination) {
					sql = sql.concat(CommerceCartModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the commerce carts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceCart commerceCart : findAll()) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts.
	 *
	 * @return the number of commerce carts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCECART);

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
		return CommerceCartModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce cart persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceCartImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCECART = "SELECT commerceCart FROM CommerceCart commerceCart";
	private static final String _SQL_SELECT_COMMERCECART_WHERE_PKS_IN = "SELECT commerceCart FROM CommerceCart commerceCart WHERE CommerceCartId IN (";
	private static final String _SQL_SELECT_COMMERCECART_WHERE = "SELECT commerceCart FROM CommerceCart commerceCart WHERE ";
	private static final String _SQL_COUNT_COMMERCECART = "SELECT COUNT(commerceCart) FROM CommerceCart commerceCart";
	private static final String _SQL_COUNT_COMMERCECART_WHERE = "SELECT COUNT(commerceCart) FROM CommerceCart commerceCart WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "commerceCart.CommerceCartId";
	private static final String _FILTER_SQL_SELECT_COMMERCECART_WHERE = "SELECT DISTINCT {commerceCart.*} FROM CommerceCart commerceCart WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {CommerceCart.*} FROM (SELECT DISTINCT commerceCart.CommerceCartId FROM CommerceCart commerceCart WHERE ";
	private static final String _FILTER_SQL_SELECT_COMMERCECART_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN CommerceCart ON TEMP_TABLE.CommerceCartId = CommerceCart.CommerceCartId";
	private static final String _FILTER_SQL_COUNT_COMMERCECART_WHERE = "SELECT COUNT(DISTINCT commerceCart.CommerceCartId) AS COUNT_VALUE FROM CommerceCart commerceCart WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "commerceCart";
	private static final String _FILTER_ENTITY_TABLE = "CommerceCart";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceCart.";
	private static final String _ORDER_BY_ENTITY_TABLE = "CommerceCart.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceCart exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceCart exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceCartPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}