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

import com.liferay.commerce.exception.NoSuchShippingMethodException;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingMethodTable;
import com.liferay.commerce.model.impl.CommerceShippingMethodImpl;
import com.liferay.commerce.model.impl.CommerceShippingMethodModelImpl;
import com.liferay.commerce.service.persistence.CommerceShippingMethodPersistence;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
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
 * @generated
 */
public class CommerceShippingMethodPersistenceImpl
	extends BasePersistenceImpl<CommerceShippingMethod>
	implements CommerceShippingMethodPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceShippingMethodUtil</code> to access the commerce shipping method persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceShippingMethodImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the commerce shipping methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @return the range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<CommerceShippingMethod> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingMethod>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingMethod commerceShippingMethod : list) {
					if (groupId != commerceShippingMethod.getGroupId()) {
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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<CommerceShippingMethod>)QueryUtil.list(
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
	 * Returns the first commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {

		CommerceShippingMethod commerceShippingMethod = fetchByGroupId_First(
			groupId, orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchShippingMethodException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {

		List<CommerceShippingMethod> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

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
	public CommerceShippingMethod findByGroupId_Last(
			long groupId,
			OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {

		CommerceShippingMethod commerceShippingMethod = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchShippingMethodException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingMethod> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

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

		CommerceShippingMethod commerceShippingMethod = findByPrimaryKey(
			commerceShippingMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingMethod[] array = new CommerceShippingMethodImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, commerceShippingMethod, groupId, orderByComparator,
				true);

			array[1] = commerceShippingMethod;

			array[2] = getByGroupId_PrevAndNext(
				session, commerceShippingMethod, groupId, orderByComparator,
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

	protected CommerceShippingMethod getByGroupId_PrevAndNext(
		Session session, CommerceShippingMethod commerceShippingMethod,
		long groupId,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingMethod)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingMethod> list = query.list();

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
		for (CommerceShippingMethod commerceShippingMethod :
				findByGroupId(
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
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"commerceShippingMethod.groupId = ?";

	private FinderPath _finderPathFetchByG_E;
	private FinderPath _finderPathCountByG_E;

	/**
	 * Returns the commerce shipping method where groupId = &#63; and engineKey = &#63; or throws a <code>NoSuchShippingMethodException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param engineKey the engine key
	 * @return the matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByG_E(long groupId, String engineKey)
		throws NoSuchShippingMethodException {

		CommerceShippingMethod commerceShippingMethod = fetchByG_E(
			groupId, engineKey);

		if (commerceShippingMethod == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", engineKey=");
			sb.append(engineKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchShippingMethodException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping method, or <code>null</code> if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod fetchByG_E(
		long groupId, String engineKey, boolean useFinderCache) {

		engineKey = Objects.toString(engineKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, engineKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByG_E, finderArgs);
		}

		if (result instanceof CommerceShippingMethod) {
			CommerceShippingMethod commerceShippingMethod =
				(CommerceShippingMethod)result;

			if ((groupId != commerceShippingMethod.getGroupId()) ||
				!Objects.equals(
					engineKey, commerceShippingMethod.getEngineKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_G_E_GROUPID_2);

			boolean bindEngineKey = false;

			if (engineKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_E_ENGINEKEY_3);
			}
			else {
				bindEngineKey = true;

				sb.append(_FINDER_COLUMN_G_E_ENGINEKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindEngineKey) {
					queryPos.add(engineKey);
				}

				List<CommerceShippingMethod> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_E, finderArgs, list);
					}
				}
				else {
					CommerceShippingMethod commerceShippingMethod = list.get(0);

					result = commerceShippingMethod;

					cacheResult(commerceShippingMethod);
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

		CommerceShippingMethod commerceShippingMethod = findByG_E(
			groupId, engineKey);

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
		engineKey = Objects.toString(engineKey, "");

		FinderPath finderPath = _finderPathCountByG_E;

		Object[] finderArgs = new Object[] {groupId, engineKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_G_E_GROUPID_2);

			boolean bindEngineKey = false;

			if (engineKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_E_ENGINEKEY_3);
			}
			else {
				bindEngineKey = true;

				sb.append(_FINDER_COLUMN_G_E_ENGINEKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindEngineKey) {
					queryPos.add(engineKey);
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

	private static final String _FINDER_COLUMN_G_E_GROUPID_2 =
		"commerceShippingMethod.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_2 =
		"commerceShippingMethod.engineKey = ?";

	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_3 =
		"(commerceShippingMethod.engineKey IS NULL OR commerceShippingMethod.engineKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;

	/**
	 * Returns all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByG_A(
		long groupId, boolean active) {

		return findByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @return the range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByG_A(
		long groupId, boolean active, int start, int end) {

		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
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
	public List<CommerceShippingMethod> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {

		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_A;
				finderArgs = new Object[] {groupId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_A;
			finderArgs = new Object[] {
				groupId, active, start, end, orderByComparator
			};
		}

		List<CommerceShippingMethod> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingMethod>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

				list = (List<CommerceShippingMethod>)QueryUtil.list(
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
	 * Returns the first commerce shipping method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping method
	 * @throws NoSuchShippingMethodException if a matching commerce shipping method could not be found
	 */
	@Override
	public CommerceShippingMethod findByG_A_First(
			long groupId, boolean active,
			OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {

		CommerceShippingMethod commerceShippingMethod = fetchByG_A_First(
			groupId, active, orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchShippingMethodException(sb.toString());
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
	public CommerceShippingMethod fetchByG_A_First(
		long groupId, boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {

		List<CommerceShippingMethod> list = findByG_A(
			groupId, active, 0, 1, orderByComparator);

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
	public CommerceShippingMethod findByG_A_Last(
			long groupId, boolean active,
			OrderByComparator<CommerceShippingMethod> orderByComparator)
		throws NoSuchShippingMethodException {

		CommerceShippingMethod commerceShippingMethod = fetchByG_A_Last(
			groupId, active, orderByComparator);

		if (commerceShippingMethod != null) {
			return commerceShippingMethod;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchShippingMethodException(sb.toString());
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
	public CommerceShippingMethod fetchByG_A_Last(
		long groupId, boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {

		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingMethod> list = findByG_A(
			groupId, active, count - 1, count, orderByComparator);

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

		CommerceShippingMethod commerceShippingMethod = findByPrimaryKey(
			commerceShippingMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingMethod[] array = new CommerceShippingMethodImpl[3];

			array[0] = getByG_A_PrevAndNext(
				session, commerceShippingMethod, groupId, active,
				orderByComparator, true);

			array[1] = commerceShippingMethod;

			array[2] = getByG_A_PrevAndNext(
				session, commerceShippingMethod, groupId, active,
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

	protected CommerceShippingMethod getByG_A_PrevAndNext(
		Session session, CommerceShippingMethod commerceShippingMethod,
		long groupId, boolean active,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE);

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			sb.append(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingMethod)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingMethod> list = query.list();

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
		for (CommerceShippingMethod commerceShippingMethod :
				findByG_A(
					groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByG_A;

		Object[] finderArgs = new Object[] {groupId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 =
		"commerceShippingMethod.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 =
		"commerceShippingMethod.active = ?";

	public CommerceShippingMethodPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceShippingMethod.class);

		setModelImplClass(CommerceShippingMethodImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceShippingMethodTable.INSTANCE);
	}

	/**
	 * Caches the commerce shipping method in the entity cache if it is enabled.
	 *
	 * @param commerceShippingMethod the commerce shipping method
	 */
	@Override
	public void cacheResult(CommerceShippingMethod commerceShippingMethod) {
		entityCache.putResult(
			CommerceShippingMethodImpl.class,
			commerceShippingMethod.getPrimaryKey(), commerceShippingMethod);

		finderCache.putResult(
			_finderPathFetchByG_E,
			new Object[] {
				commerceShippingMethod.getGroupId(),
				commerceShippingMethod.getEngineKey()
			},
			commerceShippingMethod);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce shipping methods in the entity cache if it is enabled.
	 *
	 * @param commerceShippingMethods the commerce shipping methods
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingMethod> commerceShippingMethods) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceShippingMethods.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			if (entityCache.getResult(
					CommerceShippingMethodImpl.class,
					commerceShippingMethod.getPrimaryKey()) == null) {

				cacheResult(commerceShippingMethod);
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping methods.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingMethodImpl.class);

		finderCache.clearCache(CommerceShippingMethodImpl.class);
	}

	/**
	 * Clears the cache for the commerce shipping method.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceShippingMethod commerceShippingMethod) {
		entityCache.removeResult(
			CommerceShippingMethodImpl.class, commerceShippingMethod);
	}

	@Override
	public void clearCache(
		List<CommerceShippingMethod> commerceShippingMethods) {

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			entityCache.removeResult(
				CommerceShippingMethodImpl.class, commerceShippingMethod);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceShippingMethodImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceShippingMethodImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceShippingMethodModelImpl commerceShippingMethodModelImpl) {

		Object[] args = new Object[] {
			commerceShippingMethodModelImpl.getGroupId(),
			commerceShippingMethodModelImpl.getEngineKey()
		};

		finderCache.putResult(_finderPathCountByG_E, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_E, args, commerceShippingMethodModelImpl);
	}

	/**
	 * Creates a new commerce shipping method with the primary key. Does not add the commerce shipping method to the database.
	 *
	 * @param commerceShippingMethodId the primary key for the new commerce shipping method
	 * @return the new commerce shipping method
	 */
	@Override
	public CommerceShippingMethod create(long commerceShippingMethodId) {
		CommerceShippingMethod commerceShippingMethod =
			new CommerceShippingMethodImpl();

		commerceShippingMethod.setNew(true);
		commerceShippingMethod.setPrimaryKey(commerceShippingMethodId);

		commerceShippingMethod.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			CommerceShippingMethod commerceShippingMethod =
				(CommerceShippingMethod)session.get(
					CommerceShippingMethodImpl.class, primaryKey);

			if (commerceShippingMethod == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingMethodException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceShippingMethod);
		}
		catch (NoSuchShippingMethodException noSuchEntityException) {
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
	protected CommerceShippingMethod removeImpl(
		CommerceShippingMethod commerceShippingMethod) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingMethod)) {
				commerceShippingMethod = (CommerceShippingMethod)session.get(
					CommerceShippingMethodImpl.class,
					commerceShippingMethod.getPrimaryKeyObj());
			}

			if (commerceShippingMethod != null) {
				session.delete(commerceShippingMethod);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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

		if (!(commerceShippingMethod instanceof
				CommerceShippingMethodModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceShippingMethod.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceShippingMethod);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingMethod proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingMethod implementation " +
					commerceShippingMethod.getClass());
		}

		CommerceShippingMethodModelImpl commerceShippingMethodModelImpl =
			(CommerceShippingMethodModelImpl)commerceShippingMethod;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceShippingMethod.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShippingMethod.setCreateDate(date);
			}
			else {
				commerceShippingMethod.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceShippingMethodModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShippingMethod.setModifiedDate(date);
			}
			else {
				commerceShippingMethod.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceShippingMethod);
			}
			else {
				commerceShippingMethod = (CommerceShippingMethod)session.merge(
					commerceShippingMethod);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceShippingMethodImpl.class, commerceShippingMethodModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceShippingMethodModelImpl);

		if (isNew) {
			commerceShippingMethod.setNew(false);
		}

		commerceShippingMethod.resetOriginalValues();

		return commerceShippingMethod;
	}

	/**
	 * Returns the commerce shipping method with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping method
	 * @return the commerce shipping method
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod findByPrimaryKey(Serializable primaryKey)
		throws NoSuchShippingMethodException {

		CommerceShippingMethod commerceShippingMethod = fetchByPrimaryKey(
			primaryKey);

		if (commerceShippingMethod == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingMethodException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceShippingMethod;
	}

	/**
	 * Returns the commerce shipping method with the primary key or throws a <code>NoSuchShippingMethodException</code> if it could not be found.
	 *
	 * @param commerceShippingMethodId the primary key of the commerce shipping method
	 * @return the commerce shipping method
	 * @throws NoSuchShippingMethodException if a commerce shipping method with the primary key could not be found
	 */
	@Override
	public CommerceShippingMethod findByPrimaryKey(
			long commerceShippingMethodId)
		throws NoSuchShippingMethodException {

		return findByPrimaryKey((Serializable)commerceShippingMethodId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingMethodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping methods
	 * @param end the upper bound of the range of commerce shipping methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping methods
	 */
	@Override
	public List<CommerceShippingMethod> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingMethod> orderByComparator,
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

		List<CommerceShippingMethod> list = null;

		if (useFinderCache) {
			list = (List<CommerceShippingMethod>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCESHIPPINGMETHOD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGMETHOD;

				sql = sql.concat(CommerceShippingMethodModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceShippingMethod>)QueryUtil.list(
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCESHIPPINGMETHOD);

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
		return "commerceShippingMethodId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCESHIPPINGMETHOD;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceShippingMethodModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping method persistence.
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

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathFetchByG_E = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_E",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "engineKey"}, true);

		_finderPathCountByG_E = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_E",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "engineKey"}, false);

		_finderPathWithPaginationFindByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "active_"}, true);

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "active_"}, true);

		_finderPathCountByG_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "active_"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceShippingMethodImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCESHIPPINGMETHOD =
		"SELECT commerceShippingMethod FROM CommerceShippingMethod commerceShippingMethod";

	private static final String _SQL_SELECT_COMMERCESHIPPINGMETHOD_WHERE =
		"SELECT commerceShippingMethod FROM CommerceShippingMethod commerceShippingMethod WHERE ";

	private static final String _SQL_COUNT_COMMERCESHIPPINGMETHOD =
		"SELECT COUNT(commerceShippingMethod) FROM CommerceShippingMethod commerceShippingMethod";

	private static final String _SQL_COUNT_COMMERCESHIPPINGMETHOD_WHERE =
		"SELECT COUNT(commerceShippingMethod) FROM CommerceShippingMethod commerceShippingMethod WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceShippingMethod.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceShippingMethod exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceShippingMethod exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingMethodPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}