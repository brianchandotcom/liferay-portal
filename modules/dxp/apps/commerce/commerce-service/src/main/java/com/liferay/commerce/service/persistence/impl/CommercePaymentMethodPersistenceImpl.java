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

import com.liferay.commerce.exception.NoSuchPaymentMethodException;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.impl.CommercePaymentMethodImpl;
import com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl;
import com.liferay.commerce.service.persistence.CommercePaymentMethodPersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
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
 * The persistence implementation for the commerce payment method service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodPersistence
 * @see com.liferay.commerce.service.persistence.CommercePaymentMethodUtil
 * @generated
 */
@ProviderType
public class CommercePaymentMethodPersistenceImpl extends BasePersistenceImpl<CommercePaymentMethod>
	implements CommercePaymentMethodPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommercePaymentMethodUtil} to access the commerce payment method persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommercePaymentMethodImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommercePaymentMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommercePaymentMethodModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce payment methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce payment methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @return the range of matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce payment methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce payment methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommercePaymentMethod> orderByComparator,
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

		List<CommercePaymentMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePaymentMethod>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePaymentMethod commercePaymentMethod : list) {
					if ((groupId != commercePaymentMethod.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommercePaymentMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommercePaymentMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePaymentMethod>)QueryUtil.list(q,
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
	 * Returns the first commerce payment method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method
	 * @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod findByGroupId_First(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commercePaymentMethod != null) {
			return commercePaymentMethod;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPaymentMethodException(msg.toString());
	}

	/**
	 * Returns the first commerce payment method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByGroupId_First(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		List<CommercePaymentMethod> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce payment method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method
	 * @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod findByGroupId_Last(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commercePaymentMethod != null) {
			return commercePaymentMethod;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPaymentMethodException(msg.toString());
	}

	/**
	 * Returns the last commerce payment method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByGroupId_Last(long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommercePaymentMethod> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce payment methods before and after the current commerce payment method in the ordered set where groupId = &#63;.
	 *
	 * @param commercePaymentMethodId the primary key of the current commerce payment method
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method
	 * @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod[] findByGroupId_PrevAndNext(
		long commercePaymentMethodId, long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = findByPrimaryKey(commercePaymentMethodId);

		Session session = null;

		try {
			session = openSession();

			CommercePaymentMethod[] array = new CommercePaymentMethodImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commercePaymentMethod,
					groupId, orderByComparator, true);

			array[1] = commercePaymentMethod;

			array[2] = getByGroupId_PrevAndNext(session, commercePaymentMethod,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePaymentMethod getByGroupId_PrevAndNext(Session session,
		CommercePaymentMethod commercePaymentMethod, long groupId,
		OrderByComparator<CommercePaymentMethod> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE);

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
			query.append(CommercePaymentMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commercePaymentMethod);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePaymentMethod> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce payment methods where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommercePaymentMethod commercePaymentMethod : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePaymentMethod);
		}
	}

	/**
	 * Returns the number of commerce payment methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce payment methods
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPAYMENTMETHOD_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commercePaymentMethod.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_E = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED,
			CommercePaymentMethodImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_E",
			new String[] { Long.class.getName(), String.class.getName() },
			CommercePaymentMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommercePaymentMethodModelImpl.ENGINEKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_E = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_E",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or throws a {@link NoSuchPaymentMethodException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the matching commerce payment method
	 * @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod findByG_E(long groupId, String engineKey)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = fetchByG_E(groupId,
				engineKey);

		if (commercePaymentMethod == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", engineKey=");
			msg.append(engineKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPaymentMethodException(msg.toString());
		}

		return commercePaymentMethod;
	}

	/**
	 * Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByG_E(long groupId, String engineKey) {
		return fetchByG_E(groupId, engineKey, true);
	}

	/**
	 * Returns the commerce payment method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByG_E(long groupId, String engineKey,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, engineKey };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_E,
					finderArgs, this);
		}

		if (result instanceof CommercePaymentMethod) {
			CommercePaymentMethod commercePaymentMethod = (CommercePaymentMethod)result;

			if ((groupId != commercePaymentMethod.getGroupId()) ||
					!Objects.equals(engineKey,
						commercePaymentMethod.getEngineKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE);

			query.append(_FINDER_COLUMN_G_E_GROUPID_2);

			boolean bindEngineKey = false;

			if (engineKey == null) {
				query.append(_FINDER_COLUMN_G_E_ENGINEKEY_1);
			}
			else if (engineKey.equals("")) {
				query.append(_FINDER_COLUMN_G_E_ENGINEKEY_3);
			}
			else {
				bindEngineKey = true;

				query.append(_FINDER_COLUMN_G_E_ENGINEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindEngineKey) {
					qPos.add(engineKey);
				}

				List<CommercePaymentMethod> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_E, finderArgs,
						list);
				}
				else {
					CommercePaymentMethod commercePaymentMethod = list.get(0);

					result = commercePaymentMethod;

					cacheResult(commercePaymentMethod);

					if ((commercePaymentMethod.getGroupId() != groupId) ||
							(commercePaymentMethod.getEngineKey() == null) ||
							!commercePaymentMethod.getEngineKey()
													  .equals(engineKey)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_E,
							finderArgs, commercePaymentMethod);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_E, finderArgs);

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
			return (CommercePaymentMethod)result;
		}
	}

	/**
	 * Removes the commerce payment method where groupId = &#63; and engineKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the commerce payment method that was removed
	 */
	@Override
	public CommercePaymentMethod removeByG_E(long groupId, String engineKey)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = findByG_E(groupId,
				engineKey);

		return remove(commercePaymentMethod);
	}

	/**
	 * Returns the number of commerce payment methods where groupId = &#63; and engineKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the number of matching commerce payment methods
	 */
	@Override
	public int countByG_E(long groupId, String engineKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_E;

		Object[] finderArgs = new Object[] { groupId, engineKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPAYMENTMETHOD_WHERE);

			query.append(_FINDER_COLUMN_G_E_GROUPID_2);

			boolean bindEngineKey = false;

			if (engineKey == null) {
				query.append(_FINDER_COLUMN_G_E_ENGINEKEY_1);
			}
			else if (engineKey.equals("")) {
				query.append(_FINDER_COLUMN_G_E_ENGINEKEY_3);
			}
			else {
				bindEngineKey = true;

				query.append(_FINDER_COLUMN_G_E_ENGINEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindEngineKey) {
					qPos.add(engineKey);
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

	private static final String _FINDER_COLUMN_G_E_GROUPID_2 = "commercePaymentMethod.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_1 = "commercePaymentMethod.engineKey IS NULL";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_2 = "commercePaymentMethod.engineKey = ?";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_3 = "(commercePaymentMethod.engineKey IS NULL OR commercePaymentMethod.engineKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CommercePaymentMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommercePaymentMethodModelImpl.ACTIVE_COLUMN_BITMASK |
			CommercePaymentMethodModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the commerce payment methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByG_A(long groupId, boolean active) {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @return the range of matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByG_A(long groupId, boolean active,
		int start, int end) {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce payment methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, active,
					
					start, end, orderByComparator
				};
		}

		List<CommercePaymentMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePaymentMethod>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePaymentMethod commercePaymentMethod : list) {
					if ((groupId != commercePaymentMethod.getGroupId()) ||
							(active != commercePaymentMethod.getActive())) {
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

			query.append(_SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommercePaymentMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				if (!pagination) {
					list = (List<CommercePaymentMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePaymentMethod>)QueryUtil.list(q,
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
	 * Returns the first commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method
	 * @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod findByG_A_First(long groupId, boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = fetchByG_A_First(groupId,
				active, orderByComparator);

		if (commercePaymentMethod != null) {
			return commercePaymentMethod;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchPaymentMethodException(msg.toString());
	}

	/**
	 * Returns the first commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByG_A_First(long groupId, boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		List<CommercePaymentMethod> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method
	 * @throws NoSuchPaymentMethodException if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod findByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = fetchByG_A_Last(groupId,
				active, orderByComparator);

		if (commercePaymentMethod != null) {
			return commercePaymentMethod;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchPaymentMethodException(msg.toString());
	}

	/**
	 * Returns the last commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method, or <code>null</code> if a matching commerce payment method could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<CommercePaymentMethod> list = findByG_A(groupId, active,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce payment methods before and after the current commerce payment method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param commercePaymentMethodId the primary key of the current commerce payment method
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method
	 * @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod[] findByG_A_PrevAndNext(
		long commercePaymentMethodId, long groupId, boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = findByPrimaryKey(commercePaymentMethodId);

		Session session = null;

		try {
			session = openSession();

			CommercePaymentMethod[] array = new CommercePaymentMethodImpl[3];

			array[0] = getByG_A_PrevAndNext(session, commercePaymentMethod,
					groupId, active, orderByComparator, true);

			array[1] = commercePaymentMethod;

			array[2] = getByG_A_PrevAndNext(session, commercePaymentMethod,
					groupId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePaymentMethod getByG_A_PrevAndNext(Session session,
		CommercePaymentMethod commercePaymentMethod, long groupId,
		boolean active,
		OrderByComparator<CommercePaymentMethod> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			query.append(CommercePaymentMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commercePaymentMethod);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePaymentMethod> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce payment methods where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (CommercePaymentMethod commercePaymentMethod : findByG_A(groupId,
				active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePaymentMethod);
		}
	}

	/**
	 * Returns the number of commerce payment methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching commerce payment methods
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPAYMENTMETHOD_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "commercePaymentMethod.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "commercePaymentMethod.active = ?";

	public CommercePaymentMethodPersistenceImpl() {
		setModelClass(CommercePaymentMethod.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("active", "active_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce payment method in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethod the commerce payment method
	 */
	@Override
	public void cacheResult(CommercePaymentMethod commercePaymentMethod) {
		entityCache.putResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			commercePaymentMethod.getPrimaryKey(), commercePaymentMethod);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_E,
			new Object[] {
				commercePaymentMethod.getGroupId(),
				commercePaymentMethod.getEngineKey()
			}, commercePaymentMethod);

		commercePaymentMethod.resetOriginalValues();
	}

	/**
	 * Caches the commerce payment methods in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethods the commerce payment methods
	 */
	@Override
	public void cacheResult(List<CommercePaymentMethod> commercePaymentMethods) {
		for (CommercePaymentMethod commercePaymentMethod : commercePaymentMethods) {
			if (entityCache.getResult(
						CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
						CommercePaymentMethodImpl.class,
						commercePaymentMethod.getPrimaryKey()) == null) {
				cacheResult(commercePaymentMethod);
			}
			else {
				commercePaymentMethod.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce payment methods.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePaymentMethodImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce payment method.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommercePaymentMethod commercePaymentMethod) {
		entityCache.removeResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			commercePaymentMethod.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommercePaymentMethodModelImpl)commercePaymentMethod,
			true);
	}

	@Override
	public void clearCache(List<CommercePaymentMethod> commercePaymentMethods) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommercePaymentMethod commercePaymentMethod : commercePaymentMethods) {
			entityCache.removeResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
				CommercePaymentMethodImpl.class,
				commercePaymentMethod.getPrimaryKey());

			clearUniqueFindersCache((CommercePaymentMethodModelImpl)commercePaymentMethod,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePaymentMethodModelImpl commercePaymentMethodModelImpl) {
		Object[] args = new Object[] {
				commercePaymentMethodModelImpl.getGroupId(),
				commercePaymentMethodModelImpl.getEngineKey()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_E, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_E, args,
			commercePaymentMethodModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommercePaymentMethodModelImpl commercePaymentMethodModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commercePaymentMethodModelImpl.getGroupId(),
					commercePaymentMethodModelImpl.getEngineKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_E, args);
		}

		if ((commercePaymentMethodModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_E.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commercePaymentMethodModelImpl.getOriginalGroupId(),
					commercePaymentMethodModelImpl.getOriginalEngineKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_E, args);
		}
	}

	/**
	 * Creates a new commerce payment method with the primary key. Does not add the commerce payment method to the database.
	 *
	 * @param commercePaymentMethodId the primary key for the new commerce payment method
	 * @return the new commerce payment method
	 */
	@Override
	public CommercePaymentMethod create(long commercePaymentMethodId) {
		CommercePaymentMethod commercePaymentMethod = new CommercePaymentMethodImpl();

		commercePaymentMethod.setNew(true);
		commercePaymentMethod.setPrimaryKey(commercePaymentMethodId);

		commercePaymentMethod.setCompanyId(companyProvider.getCompanyId());

		return commercePaymentMethod;
	}

	/**
	 * Removes the commerce payment method with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentMethodId the primary key of the commerce payment method
	 * @return the commerce payment method that was removed
	 * @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod remove(long commercePaymentMethodId)
		throws NoSuchPaymentMethodException {
		return remove((Serializable)commercePaymentMethodId);
	}

	/**
	 * Removes the commerce payment method with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce payment method
	 * @return the commerce payment method that was removed
	 * @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod remove(Serializable primaryKey)
		throws NoSuchPaymentMethodException {
		Session session = null;

		try {
			session = openSession();

			CommercePaymentMethod commercePaymentMethod = (CommercePaymentMethod)session.get(CommercePaymentMethodImpl.class,
					primaryKey);

			if (commercePaymentMethod == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPaymentMethodException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commercePaymentMethod);
		}
		catch (NoSuchPaymentMethodException nsee) {
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
	protected CommercePaymentMethod removeImpl(
		CommercePaymentMethod commercePaymentMethod) {
		commercePaymentMethod = toUnwrappedModel(commercePaymentMethod);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePaymentMethod)) {
				commercePaymentMethod = (CommercePaymentMethod)session.get(CommercePaymentMethodImpl.class,
						commercePaymentMethod.getPrimaryKeyObj());
			}

			if (commercePaymentMethod != null) {
				session.delete(commercePaymentMethod);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commercePaymentMethod != null) {
			clearCache(commercePaymentMethod);
		}

		return commercePaymentMethod;
	}

	@Override
	public CommercePaymentMethod updateImpl(
		CommercePaymentMethod commercePaymentMethod) {
		commercePaymentMethod = toUnwrappedModel(commercePaymentMethod);

		boolean isNew = commercePaymentMethod.isNew();

		CommercePaymentMethodModelImpl commercePaymentMethodModelImpl = (CommercePaymentMethodModelImpl)commercePaymentMethod;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commercePaymentMethod.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePaymentMethod.setCreateDate(now);
			}
			else {
				commercePaymentMethod.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commercePaymentMethodModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePaymentMethod.setModifiedDate(now);
			}
			else {
				commercePaymentMethod.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commercePaymentMethod.isNew()) {
				session.save(commercePaymentMethod);

				commercePaymentMethod.setNew(false);
			}
			else {
				commercePaymentMethod = (CommercePaymentMethod)session.merge(commercePaymentMethod);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommercePaymentMethodModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commercePaymentMethodModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commercePaymentMethodModelImpl.getGroupId(),
					commercePaymentMethodModelImpl.getActive()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commercePaymentMethodModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePaymentMethodModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commercePaymentMethodModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commercePaymentMethodModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePaymentMethodModelImpl.getOriginalGroupId(),
						commercePaymentMethodModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						commercePaymentMethodModelImpl.getGroupId(),
						commercePaymentMethodModelImpl.getActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		entityCache.putResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommercePaymentMethodImpl.class,
			commercePaymentMethod.getPrimaryKey(), commercePaymentMethod, false);

		clearUniqueFindersCache(commercePaymentMethodModelImpl, false);
		cacheUniqueFindersCache(commercePaymentMethodModelImpl);

		commercePaymentMethod.resetOriginalValues();

		return commercePaymentMethod;
	}

	protected CommercePaymentMethod toUnwrappedModel(
		CommercePaymentMethod commercePaymentMethod) {
		if (commercePaymentMethod instanceof CommercePaymentMethodImpl) {
			return commercePaymentMethod;
		}

		CommercePaymentMethodImpl commercePaymentMethodImpl = new CommercePaymentMethodImpl();

		commercePaymentMethodImpl.setNew(commercePaymentMethod.isNew());
		commercePaymentMethodImpl.setPrimaryKey(commercePaymentMethod.getPrimaryKey());

		commercePaymentMethodImpl.setCommercePaymentMethodId(commercePaymentMethod.getCommercePaymentMethodId());
		commercePaymentMethodImpl.setGroupId(commercePaymentMethod.getGroupId());
		commercePaymentMethodImpl.setCompanyId(commercePaymentMethod.getCompanyId());
		commercePaymentMethodImpl.setUserId(commercePaymentMethod.getUserId());
		commercePaymentMethodImpl.setUserName(commercePaymentMethod.getUserName());
		commercePaymentMethodImpl.setCreateDate(commercePaymentMethod.getCreateDate());
		commercePaymentMethodImpl.setModifiedDate(commercePaymentMethod.getModifiedDate());
		commercePaymentMethodImpl.setName(commercePaymentMethod.getName());
		commercePaymentMethodImpl.setDescription(commercePaymentMethod.getDescription());
		commercePaymentMethodImpl.setImageId(commercePaymentMethod.getImageId());
		commercePaymentMethodImpl.setEngineKey(commercePaymentMethod.getEngineKey());
		commercePaymentMethodImpl.setPriority(commercePaymentMethod.getPriority());
		commercePaymentMethodImpl.setActive(commercePaymentMethod.isActive());

		return commercePaymentMethodImpl;
	}

	/**
	 * Returns the commerce payment method with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce payment method
	 * @return the commerce payment method
	 * @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPaymentMethodException {
		CommercePaymentMethod commercePaymentMethod = fetchByPrimaryKey(primaryKey);

		if (commercePaymentMethod == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPaymentMethodException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commercePaymentMethod;
	}

	/**
	 * Returns the commerce payment method with the primary key or throws a {@link NoSuchPaymentMethodException} if it could not be found.
	 *
	 * @param commercePaymentMethodId the primary key of the commerce payment method
	 * @return the commerce payment method
	 * @throws NoSuchPaymentMethodException if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod findByPrimaryKey(long commercePaymentMethodId)
		throws NoSuchPaymentMethodException {
		return findByPrimaryKey((Serializable)commercePaymentMethodId);
	}

	/**
	 * Returns the commerce payment method with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce payment method
	 * @return the commerce payment method, or <code>null</code> if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
				CommercePaymentMethodImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommercePaymentMethod commercePaymentMethod = (CommercePaymentMethod)serializable;

		if (commercePaymentMethod == null) {
			Session session = null;

			try {
				session = openSession();

				commercePaymentMethod = (CommercePaymentMethod)session.get(CommercePaymentMethodImpl.class,
						primaryKey);

				if (commercePaymentMethod != null) {
					cacheResult(commercePaymentMethod);
				}
				else {
					entityCache.putResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
						CommercePaymentMethodImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommercePaymentMethodImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commercePaymentMethod;
	}

	/**
	 * Returns the commerce payment method with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentMethodId the primary key of the commerce payment method
	 * @return the commerce payment method, or <code>null</code> if a commerce payment method with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethod fetchByPrimaryKey(long commercePaymentMethodId) {
		return fetchByPrimaryKey((Serializable)commercePaymentMethodId);
	}

	@Override
	public Map<Serializable, CommercePaymentMethod> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePaymentMethod> map = new HashMap<Serializable, CommercePaymentMethod>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePaymentMethod commercePaymentMethod = fetchByPrimaryKey(primaryKey);

			if (commercePaymentMethod != null) {
				map.put(primaryKey, commercePaymentMethod);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommercePaymentMethodImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommercePaymentMethod)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE_PKS_IN);

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

			for (CommercePaymentMethod commercePaymentMethod : (List<CommercePaymentMethod>)q.list()) {
				map.put(commercePaymentMethod.getPrimaryKeyObj(),
					commercePaymentMethod);

				cacheResult(commercePaymentMethod);

				uncachedPrimaryKeys.remove(commercePaymentMethod.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommercePaymentMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommercePaymentMethodImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce payment methods.
	 *
	 * @return the commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce payment methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @return the range of commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce payment methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findAll(int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce payment methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment methods
	 * @param end the upper bound of the range of commerce payment methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce payment methods
	 */
	@Override
	public List<CommercePaymentMethod> findAll(int start, int end,
		OrderByComparator<CommercePaymentMethod> orderByComparator,
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

		List<CommercePaymentMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePaymentMethod>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPAYMENTMETHOD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPAYMENTMETHOD;

				if (pagination) {
					sql = sql.concat(CommercePaymentMethodModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommercePaymentMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePaymentMethod>)QueryUtil.list(q,
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
	 * Removes all the commerce payment methods from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePaymentMethod commercePaymentMethod : findAll()) {
			remove(commercePaymentMethod);
		}
	}

	/**
	 * Returns the number of commerce payment methods.
	 *
	 * @return the number of commerce payment methods
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPAYMENTMETHOD);

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
		return CommercePaymentMethodModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce payment method persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommercePaymentMethodImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPAYMENTMETHOD = "SELECT commercePaymentMethod FROM CommercePaymentMethod commercePaymentMethod";
	private static final String _SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE_PKS_IN = "SELECT commercePaymentMethod FROM CommercePaymentMethod commercePaymentMethod WHERE commercePaymentMethodId IN (";
	private static final String _SQL_SELECT_COMMERCEPAYMENTMETHOD_WHERE = "SELECT commercePaymentMethod FROM CommercePaymentMethod commercePaymentMethod WHERE ";
	private static final String _SQL_COUNT_COMMERCEPAYMENTMETHOD = "SELECT COUNT(commercePaymentMethod) FROM CommercePaymentMethod commercePaymentMethod";
	private static final String _SQL_COUNT_COMMERCEPAYMENTMETHOD_WHERE = "SELECT COUNT(commercePaymentMethod) FROM CommercePaymentMethod commercePaymentMethod WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commercePaymentMethod.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommercePaymentMethod exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommercePaymentMethod exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommercePaymentMethodPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
}