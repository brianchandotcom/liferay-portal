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

import com.liferay.commerce.exception.NoSuchTaxMethodException;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.model.impl.CommerceTaxMethodImpl;
import com.liferay.commerce.model.impl.CommerceTaxMethodModelImpl;
import com.liferay.commerce.service.persistence.CommerceTaxMethodPersistence;

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
 * The persistence implementation for the commerce tax method service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethodPersistence
 * @see com.liferay.commerce.service.persistence.CommerceTaxMethodUtil
 * @generated
 */
@ProviderType
public class CommerceTaxMethodPersistenceImpl extends BasePersistenceImpl<CommerceTaxMethod>
	implements CommerceTaxMethodPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceTaxMethodUtil} to access the commerce tax method persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceTaxMethodImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceTaxMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceTaxMethodModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tax methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @return the range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		List<CommerceTaxMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxMethod>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxMethod commerceTaxMethod : list) {
					if ((groupId != commerceTaxMethod.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceTaxMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxMethod>)QueryUtil.list(q,
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
	 * Returns the first commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax method
	 * @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod findByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchTaxMethodException(msg.toString());
	}

	/**
	 * Returns the first commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		List<CommerceTaxMethod> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax method
	 * @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchTaxMethodException(msg.toString());
	}

	/**
	 * Returns the last commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxMethod> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax methods before and after the current commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param commerceTaxMethodId the primary key of the current commerce tax method
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax method
	 * @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod[] findByGroupId_PrevAndNext(
		long commerceTaxMethodId, long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = findByPrimaryKey(commerceTaxMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxMethod[] array = new CommerceTaxMethodImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commerceTaxMethod,
					groupId, orderByComparator, true);

			array[1] = commerceTaxMethod;

			array[2] = getByGroupId_PrevAndNext(session, commerceTaxMethod,
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

	protected CommerceTaxMethod getByGroupId_PrevAndNext(Session session,
		CommerceTaxMethod commerceTaxMethod, long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

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
			query.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTaxMethod);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTaxMethod> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax methods where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceTaxMethod commerceTaxMethod : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTaxMethod);
		}
	}

	/**
	 * Returns the number of commerce tax methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce tax methods
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETAXMETHOD_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceTaxMethod.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_E = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxMethodImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_E",
			new String[] { Long.class.getName(), String.class.getName() },
			CommerceTaxMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceTaxMethodModelImpl.ENGINEKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_E = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_E",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the commerce tax method where groupId = &#63; and engineKey = &#63; or throws a {@link NoSuchTaxMethodException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the matching commerce tax method
	 * @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod findByG_E(long groupId, String engineKey)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = fetchByG_E(groupId, engineKey);

		if (commerceTaxMethod == null) {
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

			throw new NoSuchTaxMethodException(msg.toString());
		}

		return commerceTaxMethod;
	}

	/**
	 * Returns the commerce tax method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByG_E(long groupId, String engineKey) {
		return fetchByG_E(groupId, engineKey, true);
	}

	/**
	 * Returns the commerce tax method where groupId = &#63; and engineKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByG_E(long groupId, String engineKey,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, engineKey };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_E,
					finderArgs, this);
		}

		if (result instanceof CommerceTaxMethod) {
			CommerceTaxMethod commerceTaxMethod = (CommerceTaxMethod)result;

			if ((groupId != commerceTaxMethod.getGroupId()) ||
					!Objects.equals(engineKey, commerceTaxMethod.getEngineKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

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

				List<CommerceTaxMethod> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_E, finderArgs,
						list);
				}
				else {
					CommerceTaxMethod commerceTaxMethod = list.get(0);

					result = commerceTaxMethod;

					cacheResult(commerceTaxMethod);

					if ((commerceTaxMethod.getGroupId() != groupId) ||
							(commerceTaxMethod.getEngineKey() == null) ||
							!commerceTaxMethod.getEngineKey().equals(engineKey)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_E,
							finderArgs, commerceTaxMethod);
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
			return (CommerceTaxMethod)result;
		}
	}

	/**
	 * Removes the commerce tax method where groupId = &#63; and engineKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the commerce tax method that was removed
	 */
	@Override
	public CommerceTaxMethod removeByG_E(long groupId, String engineKey)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = findByG_E(groupId, engineKey);

		return remove(commerceTaxMethod);
	}

	/**
	 * Returns the number of commerce tax methods where groupId = &#63; and engineKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the number of matching commerce tax methods
	 */
	@Override
	public int countByG_E(long groupId, String engineKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_E;

		Object[] finderArgs = new Object[] { groupId, engineKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETAXMETHOD_WHERE);

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

	private static final String _FINDER_COLUMN_G_E_GROUPID_2 = "commerceTaxMethod.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_1 = "commerceTaxMethod.engineKey IS NULL";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_2 = "commerceTaxMethod.engineKey = ?";
	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_3 = "(commerceTaxMethod.engineKey IS NULL OR commerceTaxMethod.engineKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED,
			CommerceTaxMethodImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			CommerceTaxMethodModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceTaxMethodModelImpl.ACTIVE_COLUMN_BITMASK |
			CommerceTaxMethodModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByG_A(long groupId, boolean active) {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @return the range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByG_A(long groupId, boolean active,
		int start, int end) {
		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByG_A(long groupId, boolean active,
		int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		List<CommerceTaxMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxMethod>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxMethod commerceTaxMethod : list) {
					if ((groupId != commerceTaxMethod.getGroupId()) ||
							(active != commerceTaxMethod.isActive())) {
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

			query.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceTaxMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxMethod>)QueryUtil.list(q,
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
	 * Returns the first commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax method
	 * @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod findByG_A_First(long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = fetchByG_A_First(groupId, active,
				orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchTaxMethodException(msg.toString());
	}

	/**
	 * Returns the first commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByG_A_First(long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		List<CommerceTaxMethod> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax method
	 * @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod findByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = fetchByG_A_Last(groupId, active,
				orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", active=");
		msg.append(active);

		msg.append("}");

		throw new NoSuchTaxMethodException(msg.toString());
	}

	/**
	 * Returns the last commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByG_A_Last(long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxMethod> list = findByG_A(groupId, active, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tax methods before and after the current commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param commerceTaxMethodId the primary key of the current commerce tax method
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tax method
	 * @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod[] findByG_A_PrevAndNext(long commerceTaxMethodId,
		long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = findByPrimaryKey(commerceTaxMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxMethod[] array = new CommerceTaxMethodImpl[3];

			array[0] = getByG_A_PrevAndNext(session, commerceTaxMethod,
					groupId, active, orderByComparator, true);

			array[1] = commerceTaxMethod;

			array[2] = getByG_A_PrevAndNext(session, commerceTaxMethod,
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

	protected CommerceTaxMethod getByG_A_PrevAndNext(Session session,
		CommerceTaxMethod commerceTaxMethod, long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

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
			query.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTaxMethod);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTaxMethod> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tax methods where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	@Override
	public void removeByG_A(long groupId, boolean active) {
		for (CommerceTaxMethod commerceTaxMethod : findByG_A(groupId, active,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTaxMethod);
		}
	}

	/**
	 * Returns the number of commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching commerce tax methods
	 */
	@Override
	public int countByG_A(long groupId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETAXMETHOD_WHERE);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "commerceTaxMethod.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "commerceTaxMethod.active = ?";

	public CommerceTaxMethodPersistenceImpl() {
		setModelClass(CommerceTaxMethod.class);

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
	 * Caches the commerce tax method in the entity cache if it is enabled.
	 *
	 * @param commerceTaxMethod the commerce tax method
	 */
	@Override
	public void cacheResult(CommerceTaxMethod commerceTaxMethod) {
		entityCache.putResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodImpl.class, commerceTaxMethod.getPrimaryKey(),
			commerceTaxMethod);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_E,
			new Object[] {
				commerceTaxMethod.getGroupId(), commerceTaxMethod.getEngineKey()
			}, commerceTaxMethod);

		commerceTaxMethod.resetOriginalValues();
	}

	/**
	 * Caches the commerce tax methods in the entity cache if it is enabled.
	 *
	 * @param commerceTaxMethods the commerce tax methods
	 */
	@Override
	public void cacheResult(List<CommerceTaxMethod> commerceTaxMethods) {
		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			if (entityCache.getResult(
						CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxMethodImpl.class,
						commerceTaxMethod.getPrimaryKey()) == null) {
				cacheResult(commerceTaxMethod);
			}
			else {
				commerceTaxMethod.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax methods.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxMethodImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce tax method.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTaxMethod commerceTaxMethod) {
		entityCache.removeResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodImpl.class, commerceTaxMethod.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceTaxMethodModelImpl)commerceTaxMethod,
			true);
	}

	@Override
	public void clearCache(List<CommerceTaxMethod> commerceTaxMethods) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			entityCache.removeResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxMethodImpl.class, commerceTaxMethod.getPrimaryKey());

			clearUniqueFindersCache((CommerceTaxMethodModelImpl)commerceTaxMethod,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTaxMethodModelImpl commerceTaxMethodModelImpl) {
		Object[] args = new Object[] {
				commerceTaxMethodModelImpl.getGroupId(),
				commerceTaxMethodModelImpl.getEngineKey()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_E, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_E, args,
			commerceTaxMethodModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceTaxMethodModelImpl commerceTaxMethodModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceTaxMethodModelImpl.getGroupId(),
					commerceTaxMethodModelImpl.getEngineKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_E, args);
		}

		if ((commerceTaxMethodModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_E.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceTaxMethodModelImpl.getOriginalGroupId(),
					commerceTaxMethodModelImpl.getOriginalEngineKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_E, args);
		}
	}

	/**
	 * Creates a new commerce tax method with the primary key. Does not add the commerce tax method to the database.
	 *
	 * @param commerceTaxMethodId the primary key for the new commerce tax method
	 * @return the new commerce tax method
	 */
	@Override
	public CommerceTaxMethod create(long commerceTaxMethodId) {
		CommerceTaxMethod commerceTaxMethod = new CommerceTaxMethodImpl();

		commerceTaxMethod.setNew(true);
		commerceTaxMethod.setPrimaryKey(commerceTaxMethodId);

		commerceTaxMethod.setCompanyId(companyProvider.getCompanyId());

		return commerceTaxMethod;
	}

	/**
	 * Removes the commerce tax method with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxMethodId the primary key of the commerce tax method
	 * @return the commerce tax method that was removed
	 * @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod remove(long commerceTaxMethodId)
		throws NoSuchTaxMethodException {
		return remove((Serializable)commerceTaxMethodId);
	}

	/**
	 * Removes the commerce tax method with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tax method
	 * @return the commerce tax method that was removed
	 * @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod remove(Serializable primaryKey)
		throws NoSuchTaxMethodException {
		Session session = null;

		try {
			session = openSession();

			CommerceTaxMethod commerceTaxMethod = (CommerceTaxMethod)session.get(CommerceTaxMethodImpl.class,
					primaryKey);

			if (commerceTaxMethod == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxMethodException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceTaxMethod);
		}
		catch (NoSuchTaxMethodException nsee) {
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
	protected CommerceTaxMethod removeImpl(CommerceTaxMethod commerceTaxMethod) {
		commerceTaxMethod = toUnwrappedModel(commerceTaxMethod);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxMethod)) {
				commerceTaxMethod = (CommerceTaxMethod)session.get(CommerceTaxMethodImpl.class,
						commerceTaxMethod.getPrimaryKeyObj());
			}

			if (commerceTaxMethod != null) {
				session.delete(commerceTaxMethod);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceTaxMethod != null) {
			clearCache(commerceTaxMethod);
		}

		return commerceTaxMethod;
	}

	@Override
	public CommerceTaxMethod updateImpl(CommerceTaxMethod commerceTaxMethod) {
		commerceTaxMethod = toUnwrappedModel(commerceTaxMethod);

		boolean isNew = commerceTaxMethod.isNew();

		CommerceTaxMethodModelImpl commerceTaxMethodModelImpl = (CommerceTaxMethodModelImpl)commerceTaxMethod;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceTaxMethod.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxMethod.setCreateDate(now);
			}
			else {
				commerceTaxMethod.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceTaxMethodModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxMethod.setModifiedDate(now);
			}
			else {
				commerceTaxMethod.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceTaxMethod.isNew()) {
				session.save(commerceTaxMethod);

				commerceTaxMethod.setNew(false);
			}
			else {
				commerceTaxMethod = (CommerceTaxMethod)session.merge(commerceTaxMethod);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceTaxMethodModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { commerceTaxMethodModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceTaxMethodModelImpl.getGroupId(),
					commerceTaxMethodModelImpl.isActive()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceTaxMethodModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTaxMethodModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceTaxMethodModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceTaxMethodModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTaxMethodModelImpl.getOriginalGroupId(),
						commerceTaxMethodModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						commerceTaxMethodModelImpl.getGroupId(),
						commerceTaxMethodModelImpl.isActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}
		}

		entityCache.putResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTaxMethodImpl.class, commerceTaxMethod.getPrimaryKey(),
			commerceTaxMethod, false);

		clearUniqueFindersCache(commerceTaxMethodModelImpl, false);
		cacheUniqueFindersCache(commerceTaxMethodModelImpl);

		commerceTaxMethod.resetOriginalValues();

		return commerceTaxMethod;
	}

	protected CommerceTaxMethod toUnwrappedModel(
		CommerceTaxMethod commerceTaxMethod) {
		if (commerceTaxMethod instanceof CommerceTaxMethodImpl) {
			return commerceTaxMethod;
		}

		CommerceTaxMethodImpl commerceTaxMethodImpl = new CommerceTaxMethodImpl();

		commerceTaxMethodImpl.setNew(commerceTaxMethod.isNew());
		commerceTaxMethodImpl.setPrimaryKey(commerceTaxMethod.getPrimaryKey());

		commerceTaxMethodImpl.setCommerceTaxMethodId(commerceTaxMethod.getCommerceTaxMethodId());
		commerceTaxMethodImpl.setGroupId(commerceTaxMethod.getGroupId());
		commerceTaxMethodImpl.setCompanyId(commerceTaxMethod.getCompanyId());
		commerceTaxMethodImpl.setUserId(commerceTaxMethod.getUserId());
		commerceTaxMethodImpl.setUserName(commerceTaxMethod.getUserName());
		commerceTaxMethodImpl.setCreateDate(commerceTaxMethod.getCreateDate());
		commerceTaxMethodImpl.setModifiedDate(commerceTaxMethod.getModifiedDate());
		commerceTaxMethodImpl.setName(commerceTaxMethod.getName());
		commerceTaxMethodImpl.setDescription(commerceTaxMethod.getDescription());
		commerceTaxMethodImpl.setEngineKey(commerceTaxMethod.getEngineKey());
		commerceTaxMethodImpl.setPercentage(commerceTaxMethod.isPercentage());
		commerceTaxMethodImpl.setActive(commerceTaxMethod.isActive());

		return commerceTaxMethodImpl;
	}

	/**
	 * Returns the commerce tax method with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax method
	 * @return the commerce tax method
	 * @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaxMethodException {
		CommerceTaxMethod commerceTaxMethod = fetchByPrimaryKey(primaryKey);

		if (commerceTaxMethod == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxMethodException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceTaxMethod;
	}

	/**
	 * Returns the commerce tax method with the primary key or throws a {@link NoSuchTaxMethodException} if it could not be found.
	 *
	 * @param commerceTaxMethodId the primary key of the commerce tax method
	 * @return the commerce tax method
	 * @throws NoSuchTaxMethodException if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod findByPrimaryKey(long commerceTaxMethodId)
		throws NoSuchTaxMethodException {
		return findByPrimaryKey((Serializable)commerceTaxMethodId);
	}

	/**
	 * Returns the commerce tax method with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax method
	 * @return the commerce tax method, or <code>null</code> if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTaxMethodImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceTaxMethod commerceTaxMethod = (CommerceTaxMethod)serializable;

		if (commerceTaxMethod == null) {
			Session session = null;

			try {
				session = openSession();

				commerceTaxMethod = (CommerceTaxMethod)session.get(CommerceTaxMethodImpl.class,
						primaryKey);

				if (commerceTaxMethod != null) {
					cacheResult(commerceTaxMethod);
				}
				else {
					entityCache.putResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTaxMethodImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxMethodImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceTaxMethod;
	}

	/**
	 * Returns the commerce tax method with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxMethodId the primary key of the commerce tax method
	 * @return the commerce tax method, or <code>null</code> if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByPrimaryKey(long commerceTaxMethodId) {
		return fetchByPrimaryKey((Serializable)commerceTaxMethodId);
	}

	@Override
	public Map<Serializable, CommerceTaxMethod> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTaxMethod> map = new HashMap<Serializable, CommerceTaxMethod>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTaxMethod commerceTaxMethod = fetchByPrimaryKey(primaryKey);

			if (commerceTaxMethod != null) {
				map.put(primaryKey, commerceTaxMethod);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxMethodImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceTaxMethod)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE_PKS_IN);

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

			for (CommerceTaxMethod commerceTaxMethod : (List<CommerceTaxMethod>)q.list()) {
				map.put(commerceTaxMethod.getPrimaryKeyObj(), commerceTaxMethod);

				cacheResult(commerceTaxMethod);

				uncachedPrimaryKeys.remove(commerceTaxMethod.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceTaxMethodModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTaxMethodImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce tax methods.
	 *
	 * @return the commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @return the range of commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findAll(int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findAll(int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		List<CommerceTaxMethod> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTaxMethod>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCETAXMETHOD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXMETHOD;

				if (pagination) {
					sql = sql.concat(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceTaxMethod>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTaxMethod>)QueryUtil.list(q,
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
	 * Removes all the commerce tax methods from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxMethod commerceTaxMethod : findAll()) {
			remove(commerceTaxMethod);
		}
	}

	/**
	 * Returns the number of commerce tax methods.
	 *
	 * @return the number of commerce tax methods
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCETAXMETHOD);

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
		return CommerceTaxMethodModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax method persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceTaxMethodImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCETAXMETHOD = "SELECT commerceTaxMethod FROM CommerceTaxMethod commerceTaxMethod";
	private static final String _SQL_SELECT_COMMERCETAXMETHOD_WHERE_PKS_IN = "SELECT commerceTaxMethod FROM CommerceTaxMethod commerceTaxMethod WHERE commerceTaxMethodId IN (";
	private static final String _SQL_SELECT_COMMERCETAXMETHOD_WHERE = "SELECT commerceTaxMethod FROM CommerceTaxMethod commerceTaxMethod WHERE ";
	private static final String _SQL_COUNT_COMMERCETAXMETHOD = "SELECT COUNT(commerceTaxMethod) FROM CommerceTaxMethod commerceTaxMethod";
	private static final String _SQL_COUNT_COMMERCETAXMETHOD_WHERE = "SELECT COUNT(commerceTaxMethod) FROM CommerceTaxMethod commerceTaxMethod WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTaxMethod.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceTaxMethod exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceTaxMethod exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceTaxMethodPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"active"
			});
}