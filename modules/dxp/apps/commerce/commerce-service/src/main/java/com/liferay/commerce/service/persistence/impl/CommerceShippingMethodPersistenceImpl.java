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

import com.liferay.commerce.exception.NoSuchShippingMethodException;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.impl.CommerceShippingMethodImpl;
import com.liferay.commerce.model.impl.CommerceShippingMethodModelImpl;
import com.liferay.commerce.service.persistence.CommerceShippingMethodPersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the commerce shipping method service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodPersistence
 * @see com.liferay.commerce.service.persistence.CommerceShippingMethodUtil
 * @generated
 */
@ProviderType
public class CommerceShippingMethodPersistenceImpl extends BasePersistenceImpl<CommerceShippingMethod>
	implements CommerceShippingMethodPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceShippingMethodUtil} to access the commerce shipping method persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceShippingMethodImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceShippingMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceShippingMethodModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce shipping methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @return the range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceShippingMethod> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		List<CommerceShippingMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingMethod>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingMethod commerceShippingMethod : list) {
					if ((groupId != commerceShippingMethod.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceShippingMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingMethod>)QueryUtil.list(q,
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
	 * Returns the first commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByGroupId_First(long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchShippingMethodException(msg.toString());
	}

	/**
	 * Returns the first commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {
		List<CommerceShippingMethod> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByGroupId_Last(long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchShippingMethodException(msg.toString());
	}

	/**
	 * Returns the last commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingMethod> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping methods before and after the current commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param commerceShippingMethodId the primary key of the current commerce shipping method
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping method
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod[] findByGroupId_PrevAndNext(
		long commerceShippingMethodId, long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = findByPrimaryKey(commerceShippingMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingMethod[] array = new CommerceShippingMethodImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceShippingMethod, groupId, orderByComparator, true);

			array[1] = commerceShippingMethod;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceShippingMethod, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingMethod getByGroupId_PrevAndNext(Session session,
		CommerceShippingMethod commerceShippingMethod, long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

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
			query.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShippingMethod);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShippingMethod> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping methods where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceShippingMethod commerceShippingMethod : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShippingMethod);
		}
	}

	/**
	 * Returns the number of commerce shipping methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce shipping methods
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceShippingMethod.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_E = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingMethodImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_E",
			new String[] { Long.class.getName(), String.class.getName() },
			CommerceShippingMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceShippingMethodModelImpl.ENGINEKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_E = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_E",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the commerce shipping method where groupId = &#63; and engineKey = &#63; or throws a {@link NoSuchShippingMethodException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByG_E(long groupId, String engineKey)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = fetchByG_E(groupId,
				engineKey);

		if (commerceShippingMethod == null) {
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

			throw new NoSuchShippingMethodException(msg.toString());
		}

		return commerceShippingMethod;
	}

	/**
	 * Returns the commerce shipping method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByG_E(long groupId, String engineKey) {
		return fetchByG_E(groupId, engineKey, true);
	}

	/**
	 * Returns the commerce shipping method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByG_E(long groupId, String engineKey,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, engineKey };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_E,
					finderArgs, this);
		}

		if (result instanceof CommerceShippingMethod) {
			CommerceShippingMethod commerceShippingMethod = (CommerceShippingMethod)result;

			if ((groupId != commerceShippingMethod.getGroupId()) ||
					!Objects.equals(engineKey,
						commerceShippingMethod.getEngineKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

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

				List<CommerceShippingMethod> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_E, finderArgs,
						list);
				}
				else {
					CommerceShippingMethod commerceShippingMethod = list.get(0);

					result = commerceShippingMethod;

					cacheResult(commerceShippingMethod);

					if ((commerceShippingMethod.getGroupId() != groupId) ||
							(commerceShippingMethod.getEngineKey() == null) ||
							!commerceShippingMethod.getEngineKey()
													   .equals(engineKey)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_E,
							finderArgs, commerceShippingMethod);
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
			return (CommerceShippingMethod)result;
		}
	}

	/**
	 * Removes the commerce shipping method where groupId = &#63; and engineKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the commerce shipping method that was removed
	 */
	@Override
	public CommerceShippingMethod removeByG_E(long groupId, String engineKey)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = findByG_E(groupId,
				engineKey);

		return remove(commerceShippingMethod);
	}

	/**
	 * Returns the number of commerce shipping methods where groupId = &#63; and engineKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the number of matching commerce shipping methods
	 */
	@Override
	public int countByG_E(long groupId, String engineKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_E;

		Object[] finderArgs = new Object[] { groupId, engineKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE);

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

	private static final String _FINDER_COLUMN_G_E_GROUPID_2 = "commerceShippingMethod.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_1 = "commerceShippingMethod.engineKey IS NULL";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_2 = "commerceShippingMethod.engineKey = ?";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_3 = "(commerceShippingMethod.engineKey IS NULL OR commerceShippingMethod.engineKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CommerceShippingMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceShippingMethodModelImpl.ACTIVE_COLUMN_BITMASK |
			CommerceShippingMethodModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByG_A(long groupId, boolean active) {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @return the range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByG_A(long groupId, boolean active,
		int start, int end) {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {
		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		List<CommerceShippingMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingMethod>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingMethod commerceShippingMethod : list) {
					if ((groupId != commerceShippingMethod.getGroupId()) ||
							(active != commerceShippingMethod.isActive())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceShippingMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingMethod>)QueryUtil.list(q,
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
	 * Returns the first commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByG_A_First(long groupId, boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = fetchByG_A_First(groupId,
				active, orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchShippingMethodException(msg.toString());
	}

	/**
	 * Returns the first commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByG_A_First(long groupId,
		boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {
		List<CommerceShippingMethod> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = fetchByG_A_Last(groupId,
				active, orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchShippingMethodException(msg.toString());
	}

	/**
	 * Returns the last commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {
		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingMethod> list = findByG_A(groupId, active,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping methods before and after the current commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param commerceShippingMethodId the primary key of the current commerce shipping method
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping method
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod[] findByG_A_PrevAndNext(
		long commerceShippingMethodId, long groupId, boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = findByPrimaryKey(commerceShippingMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingMethod[] array = new CommerceShippingMethodImpl[3];

			array[0] = getByG_A_PrevAndNext(session, commerceShippingMethod,
					groupId, active, orderByComparator, true);

			array[1] = commerceShippingMethod;

			array[2] = getByG_A_PrevAndNext(session, commerceShippingMethod,
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

	protected CommerceShippingMethod getByG_A_PrevAndNext(Session session,
		CommerceShippingMethod commerceShippingMethod, long groupId,
		boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

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
			query.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShippingMethod);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShippingMethod> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping methods where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (CommerceShippingMethod commerceShippingMethod : findByG_A(
				groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShippingMethod);
		}
	}

	/**
	 * Returns the number of commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching commerce shipping methods
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "commerceShippingMethod.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "commerceShippingMethod.active = ?";

	public CommerceShippingMethodPersistenceImpl() {
		setModelClass(CommerceShippingMethod.class);

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
	 * Caches the commerce shipping method in the entity cache if it is enabled.
	 *
	 * @param commerceShippingMethod the commerce shipping method
	 */
	@Override
	public void cacheResult(CommerceShippingMethod commerceShippingMethod) {
		entityCache.putResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			commerceShippingMethod.getPrimaryKey(), commerceShippingMethod);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_E,
			new Object[] {
				commerceShippingMethod.getGroupId(),
				commerceShippingMethod.getEngineKey()
			}, commerceShippingMethod);

		commerceShippingMethod.resetOriginalValues();
	}

	/**
	 * Caches the commerce shipping methods in the entity cache if it is enabled.
	 *
	 * @param commerceShippingMethods the commerce shipping methods
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingMethod> commerceShippingMethods) {
		for (CommerceShippingMethod commerceShippingMethod : commerceShippingMethods) {
			if (entityCache.getResult(
						CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShippingMethodImpl.class,
						commerceShippingMethod.getPrimaryKey()) == null) {
				cacheResult(commerceShippingMethod);
			}
			else {
				commerceShippingMethod.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping methods.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingMethodImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce shipping method.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceShippingMethod commerceShippingMethod) {
		entityCache.removeResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			commerceShippingMethod.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceShippingMethodModelImpl)commerceShippingMethod,
			true);
	}

	@Override
	public void clearCache(List<CommerceShippingMethod> commerceShippingMethods) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceShippingMethod commerceShippingMethod : commerceShippingMethods) {
			entityCache.removeResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShippingMethodImpl.class,
				commerceShippingMethod.getPrimaryKey());

			clearUniqueFindersCache((CommerceShippingMethodModelImpl)commerceShippingMethod,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceShippingMethodModelImpl commerceShippingMethodModelImpl) {
		Object[] args = new Object[] {
				commerceShippingMethodModelImpl.getGroupId(),
				commerceShippingMethodModelImpl.getEngineKey()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_E, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_E, args,
			commerceShippingMethodModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceShippingMethodModelImpl commerceShippingMethodModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceShippingMethodModelImpl.getGroupId(),
					commerceShippingMethodModelImpl.getEngineKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_E, args);
		}

		if ((commerceShippingMethodModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_E.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceShippingMethodModelImpl.getOriginalGroupId(),
					commerceShippingMethodModelImpl.getOriginalEngineKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_E, args);
		}
	}

	/**
	 * Creates a new commerce shipping method with the primary key. Does not add the commerce shipping method to the database.
	 *
	 * @param commerceShippingMethodId the primary key for the new commerce shipping method
	 * @return the new commerce shipping method
	 */
	@Override
	public CommerceShippingMethod create(long commerceShippingMethodId) {
		CommerceShippingMethod commerceShippingMethod = new CommerceShippingMethodImpl();

		commerceShippingMethod.setNew(true);
		commerceShippingMethod.setPrimaryKey(commerceShippingMethodId);

		commerceShippingMethod.setCompanyId(companyProvider.getCompanyId());

		return commerceShippingMethod;
	}

	/**
	 * Removes the commerce shipping method with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingMethodId the primary key of the commerce shipping method
	 * @return the commerce shipping method that was removed
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod remove(long commerceShippingMethodId)
		throws NoSuchShippingMethodException {
		return remove((Serializable)commerceShippingMethodId);
	}

	/**
	 * Removes the commerce shipping method with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipping method
	 * @return the commerce shipping method that was removed
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod remove(Serializable primaryKey)
		throws NoSuchShippingMethodException {
		Session session = null;

		try {
			session = openSession();

			CommerceShippingMethod commerceShippingMethod = (CommerceShippingMethod)session.get(CommerceShippingMethodImpl.class,
					primaryKey);

			if (commerceShippingMethod == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingMethodException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceShippingMethod);
		}
		catch (NoSuchShippingMethodException nsee) {
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
	protected CommerceShippingMethod removeImpl(
		CommerceShippingMethod commerceShippingMethod) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingMethod)) {
				commerceShippingMethod = (CommerceShippingMethod)session.get(CommerceShippingMethodImpl.class,
						commerceShippingMethod.getPrimaryKeyObj());
			}

			if (commerceShippingMethod != null) {
				session.delete(commerceShippingMethod);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceShippingMethod != null) {
			clearCache(commerceShippingMethod);
		}

		return commerceShippingMethod;
	}

	@Override
	public CommerceShippingMethod updateImpl(
		CommerceShippingMethod commerceShippingMethod) {
		boolean isNew = commerceShippingMethod.isNew();

		if (!(commerceShippingMethod instanceof CommerceShippingMethodModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceShippingMethod.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceShippingMethod);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingMethod proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingMethod implementation " +
				commerceShippingMethod.getClass());
		}

		CommerceShippingMethodModelImpl commerceShippingMethodModelImpl = (CommerceShippingMethodModelImpl)commerceShippingMethod;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceShippingMethod.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShippingMethod.setCreateDate(now);
			}
			else {
				commerceShippingMethod.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceShippingMethodModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShippingMethod.setModifiedDate(now);
			}
			else {
				commerceShippingMethod.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceShippingMethod.isNew()) {
				session.save(commerceShippingMethod);

				commerceShippingMethod.setNew(false);
			}
			else {
				commerceShippingMethod = (CommerceShippingMethod)session.merge(commerceShippingMethod);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceShippingMethodModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceShippingMethodModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceShippingMethodModelImpl.getGroupId(),
					commerceShippingMethodModelImpl.isActive()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceShippingMethodModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShippingMethodModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceShippingMethodModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceShippingMethodModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShippingMethodModelImpl.getOriginalGroupId(),
						commerceShippingMethodModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						commerceShippingMethodModelImpl.getGroupId(),
						commerceShippingMethodModelImpl.isActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		entityCache.putResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShippingMethodImpl.class,
			commerceShippingMethod.getPrimaryKey(), commerceShippingMethod,
			false);

		clearUniqueFindersCache(commerceShippingMethodModelImpl, false);
		cacheUniqueFindersCache(commerceShippingMethodModelImpl);

		commerceShippingMethod.resetOriginalValues();

		return commerceShippingMethod;
	}

	/**
	 * Returns the commerce shipping method with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping method
	 * @return the commerce shipping method
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod findByPrimaryKey(Serializable primaryKey)
		throws NoSuchShippingMethodException {
		CommerceShippingMethod commerceShippingMethod = fetchByPrimaryKey(primaryKey);

		if (commerceShippingMethod == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingMethodException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceShippingMethod;
	}

	/**
	 * Returns the commerce shipping method with the primary key or throws a {@link NoSuchShippingMethodException} if it could not be found.
	 *
	 * @param commerceShippingMethodId the primary key of the commerce shipping method
	 * @return the commerce shipping method
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod findByPrimaryKey(
		long commerceShippingMethodId) throws NoSuchShippingMethodException {
		return findByPrimaryKey((Serializable)commerceShippingMethodId);
	}

	/**
	 * Returns the commerce shipping method with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping method
	 * @return the commerce shipping method, or <code>null</code> if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShippingMethodImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceShippingMethod commerceShippingMethod = (CommerceShippingMethod)serializable;

		if (commerceShippingMethod == null) {
			Session session = null;

			try {
				session = openSession();

				commerceShippingMethod = (CommerceShippingMethod)session.get(CommerceShippingMethodImpl.class,
						primaryKey);

				if (commerceShippingMethod != null) {
					cacheResult(commerceShippingMethod);
				}
				else {
					entityCache.putResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShippingMethodImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingMethodImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceShippingMethod;
	}

	/**
	 * Returns the commerce shipping method with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingMethodId the primary key of the commerce shipping method
	 * @return the commerce shipping method, or <code>null</code> if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByPrimaryKey(
		long commerceShippingMethodId) {
		return fetchByPrimaryKey((Serializable)commerceShippingMethodId);
	}

	@Override
	public Map<Serializable, CommerceShippingMethod> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceShippingMethod> map = new HashMap<Serializable, CommerceShippingMethod>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceShippingMethod commerceShippingMethod = fetchByPrimaryKey(primaryKey);

			if (commerceShippingMethod != null) {
				map.put(primaryKey, commerceShippingMethod);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingMethodImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceShippingMethod)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE_PKS_IN);

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

			for (CommerceShippingMethod commerceShippingMethod : (List<CommerceShippingMethod>)q.list()) {
				map.put(commerceShippingMethod.getPrimaryKeyObj(),
					commerceShippingMethod);

				cacheResult(commerceShippingMethod);

				uncachedPrimaryKeys.remove(commerceShippingMethod.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceShippingMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShippingMethodImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce shipping methods.
	 *
	 * @return the commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @return the range of commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findAll(int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShippingMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findAll(int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		List<CommerceShippingMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShippingMethod>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGMETHOD;

				if (pagination) {
					sql = sql.concat(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceShippingMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShippingMethod>)QueryUtil.list(q,
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
	 * Removes all the commerce shipping methods from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShippingMethod commerceShippingMethod : findAll()) {
			remove(commerceShippingMethod);
		}
	}

	/**
	 * Returns the number of commerce shipping methods.
	 *
	 * @return the number of commerce shipping methods
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCESHIPPINGMETHOD);

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
		return CommerceShippingMethodModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping method persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceShippingMethodImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCESHIPPINGMETHOD = "SELECT commerceShippingMethod FROM CommerceShippingMethod commerceShippingMethod";
	private static final String _SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE_PKS_IN = "SELECT commerceShippingMethod FROM CommerceShippingMethod commerceShippingMethod WHERE commerceShippingMethodId IN (";
	private static final String _SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE = "SELECT commerceShippingMethod FROM CommerceShippingMethod commerceShippingMethod WHERE ";
	private static final String _SQL_COUNT_COMMERCESHIPPINGMETHOD = "SELECT COUNT(commerceShippingMethod) FROM CommerceShippingMethod commerceShippingMethod";
	private static final String _SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE = "SELECT COUNT(commerceShippingMethod) FROM CommerceShippingMethod commerceShippingMethod WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceShippingMethod.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceShippingMethod exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceShippingMethod exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceShippingMethodPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
}