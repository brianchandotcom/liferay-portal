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

package com.liferay.commerce.tax.service.persistence.impl;

import com.liferay.commerce.tax.exception.NoSuchTaxMethodException;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.model.CommerceTaxMethodTable;
import com.liferay.commerce.tax.model.impl.CommerceTaxMethodImpl;
import com.liferay.commerce.tax.model.impl.CommerceTaxMethodModelImpl;
import com.liferay.commerce.tax.service.persistence.CommerceTaxMethodPersistence;
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
 * The persistence implementation for the commerce tax method service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceTaxMethodPersistenceImpl
	extends BasePersistenceImpl<CommerceTaxMethod>
	implements CommerceTaxMethodPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTaxMethodUtil</code> to access the commerce tax method persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTaxMethodImpl.class.getName();

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
	 * Returns all the commerce tax methods where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @return the range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		List<CommerceTaxMethod> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxMethod>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxMethod commerceTaxMethod : list) {
					if (groupId != commerceTaxMethod.getGroupId()) {
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

			sb.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<CommerceTaxMethod>)QueryUtil.list(
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
	 * Returns the first commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax method
	 * @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod findByGroupId_First(
			long groupId,
			OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {

		CommerceTaxMethod commerceTaxMethod = fetchByGroupId_First(
			groupId, orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTaxMethodException(sb.toString());
	}

	/**
	 * Returns the first commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByGroupId_First(
		long groupId, OrderByComparator<CommerceTaxMethod> orderByComparator) {

		List<CommerceTaxMethod> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

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
	public CommerceTaxMethod findByGroupId_Last(
			long groupId,
			OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {

		CommerceTaxMethod commerceTaxMethod = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchTaxMethodException(sb.toString());
	}

	/**
	 * Returns the last commerce tax method in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByGroupId_Last(
		long groupId, OrderByComparator<CommerceTaxMethod> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxMethod> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

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

		CommerceTaxMethod commerceTaxMethod = findByPrimaryKey(
			commerceTaxMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxMethod[] array = new CommerceTaxMethodImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, commerceTaxMethod, groupId, orderByComparator, true);

			array[1] = commerceTaxMethod;

			array[2] = getByGroupId_PrevAndNext(
				session, commerceTaxMethod, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTaxMethod getByGroupId_PrevAndNext(
		Session session, CommerceTaxMethod commerceTaxMethod, long groupId,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

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
			sb.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
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
						commerceTaxMethod)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTaxMethod> list = query.list();

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
		for (CommerceTaxMethod commerceTaxMethod :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXMETHOD_WHERE);

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
		"commerceTaxMethod.groupId = ?";

	private FinderPath _finderPathFetchByG_E;
	private FinderPath _finderPathCountByG_E;

	/**
	 * Returns the commerce tax method where groupId = &#63; and engineKey = &#63; or throws a <code>NoSuchTaxMethodException</code> if it could not be found.
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

			throw new NoSuchTaxMethodException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax method, or <code>null</code> if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByG_E(
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

		if (result instanceof CommerceTaxMethod) {
			CommerceTaxMethod commerceTaxMethod = (CommerceTaxMethod)result;

			if ((groupId != commerceTaxMethod.getGroupId()) ||
				!Objects.equals(engineKey, commerceTaxMethod.getEngineKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

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

				List<CommerceTaxMethod> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_E, finderArgs, list);
					}
				}
				else {
					CommerceTaxMethod commerceTaxMethod = list.get(0);

					result = commerceTaxMethod;

					cacheResult(commerceTaxMethod);
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
		engineKey = Objects.toString(engineKey, "");

		FinderPath finderPath = _finderPathCountByG_E;

		Object[] finderArgs = new Object[] {groupId, engineKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETAXMETHOD_WHERE);

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
		"commerceTaxMethod.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_2 =
		"commerceTaxMethod.engineKey = ?";

	private static final String _FINDER_COLUMN_G_E_ENGINEKEY_3 =
		"(commerceTaxMethod.engineKey IS NULL OR commerceTaxMethod.engineKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;

	/**
	 * Returns all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByG_A(long groupId, boolean active) {
		return findByG_A(
			groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @return the range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByG_A(
		long groupId, boolean active, int start, int end) {

		return findByG_A(groupId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
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
	public List<CommerceTaxMethod> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {

		return findByG_A(groupId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		List<CommerceTaxMethod> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxMethod>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(active);

				list = (List<CommerceTaxMethod>)QueryUtil.list(
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
	 * Returns the first commerce tax method in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax method
	 * @throws NoSuchTaxMethodException if a matching commerce tax method could not be found
	 */
	@Override
	public CommerceTaxMethod findByG_A_First(
			long groupId, boolean active,
			OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {

		CommerceTaxMethod commerceTaxMethod = fetchByG_A_First(
			groupId, active, orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchTaxMethodException(sb.toString());
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
	public CommerceTaxMethod fetchByG_A_First(
		long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {

		List<CommerceTaxMethod> list = findByG_A(
			groupId, active, 0, 1, orderByComparator);

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
	public CommerceTaxMethod findByG_A_Last(
			long groupId, boolean active,
			OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {

		CommerceTaxMethod commerceTaxMethod = fetchByG_A_Last(
			groupId, active, orderByComparator);

		if (commerceTaxMethod != null) {
			return commerceTaxMethod;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchTaxMethodException(sb.toString());
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
	public CommerceTaxMethod fetchByG_A_Last(
		long groupId, boolean active,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {

		int count = countByG_A(groupId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxMethod> list = findByG_A(
			groupId, active, count - 1, count, orderByComparator);

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
	public CommerceTaxMethod[] findByG_A_PrevAndNext(
			long commerceTaxMethodId, long groupId, boolean active,
			OrderByComparator<CommerceTaxMethod> orderByComparator)
		throws NoSuchTaxMethodException {

		CommerceTaxMethod commerceTaxMethod = findByPrimaryKey(
			commerceTaxMethodId);

		Session session = null;

		try {
			session = openSession();

			CommerceTaxMethod[] array = new CommerceTaxMethodImpl[3];

			array[0] = getByG_A_PrevAndNext(
				session, commerceTaxMethod, groupId, active, orderByComparator,
				true);

			array[1] = commerceTaxMethod;

			array[2] = getByG_A_PrevAndNext(
				session, commerceTaxMethod, groupId, active, orderByComparator,
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

	protected CommerceTaxMethod getByG_A_PrevAndNext(
		Session session, CommerceTaxMethod commerceTaxMethod, long groupId,
		boolean active, OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETAXMETHOD_WHERE);

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
			sb.append(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
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
						commerceTaxMethod)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTaxMethod> list = query.list();

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
		for (CommerceTaxMethod commerceTaxMethod :
				findByG_A(
					groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByG_A;

		Object[] finderArgs = new Object[] {groupId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETAXMETHOD_WHERE);

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
		"commerceTaxMethod.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 =
		"commerceTaxMethod.active = ?";

	public CommerceTaxMethodPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceTaxMethod.class);

		setModelImplClass(CommerceTaxMethodImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTaxMethodTable.INSTANCE);
	}

	/**
	 * Caches the commerce tax method in the entity cache if it is enabled.
	 *
	 * @param commerceTaxMethod the commerce tax method
	 */
	@Override
	public void cacheResult(CommerceTaxMethod commerceTaxMethod) {
		entityCache.putResult(
			CommerceTaxMethodImpl.class, commerceTaxMethod.getPrimaryKey(),
			commerceTaxMethod);

		finderCache.putResult(
			_finderPathFetchByG_E,
			new Object[] {
				commerceTaxMethod.getGroupId(), commerceTaxMethod.getEngineKey()
			},
			commerceTaxMethod);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce tax methods in the entity cache if it is enabled.
	 *
	 * @param commerceTaxMethods the commerce tax methods
	 */
	@Override
	public void cacheResult(List<CommerceTaxMethod> commerceTaxMethods) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTaxMethods.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			if (entityCache.getResult(
					CommerceTaxMethodImpl.class,
					commerceTaxMethod.getPrimaryKey()) == null) {

				cacheResult(commerceTaxMethod);
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax methods.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxMethodImpl.class);

		finderCache.clearCache(CommerceTaxMethodImpl.class);
	}

	/**
	 * Clears the cache for the commerce tax method.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTaxMethod commerceTaxMethod) {
		entityCache.removeResult(
			CommerceTaxMethodImpl.class, commerceTaxMethod);
	}

	@Override
	public void clearCache(List<CommerceTaxMethod> commerceTaxMethods) {
		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			entityCache.removeResult(
				CommerceTaxMethodImpl.class, commerceTaxMethod);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTaxMethodImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceTaxMethodImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTaxMethodModelImpl commerceTaxMethodModelImpl) {

		Object[] args = new Object[] {
			commerceTaxMethodModelImpl.getGroupId(),
			commerceTaxMethodModelImpl.getEngineKey()
		};

		finderCache.putResult(_finderPathCountByG_E, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_E, args, commerceTaxMethodModelImpl);
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

		commerceTaxMethod.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			CommerceTaxMethod commerceTaxMethod =
				(CommerceTaxMethod)session.get(
					CommerceTaxMethodImpl.class, primaryKey);

			if (commerceTaxMethod == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxMethodException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTaxMethod);
		}
		catch (NoSuchTaxMethodException noSuchEntityException) {
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
	protected CommerceTaxMethod removeImpl(
		CommerceTaxMethod commerceTaxMethod) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxMethod)) {
				commerceTaxMethod = (CommerceTaxMethod)session.get(
					CommerceTaxMethodImpl.class,
					commerceTaxMethod.getPrimaryKeyObj());
			}

			if (commerceTaxMethod != null) {
				session.delete(commerceTaxMethod);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = commerceTaxMethod.isNew();

		if (!(commerceTaxMethod instanceof CommerceTaxMethodModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTaxMethod.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTaxMethod);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTaxMethod proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTaxMethod implementation " +
					commerceTaxMethod.getClass());
		}

		CommerceTaxMethodModelImpl commerceTaxMethodModelImpl =
			(CommerceTaxMethodModelImpl)commerceTaxMethod;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTaxMethod.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxMethod.setCreateDate(date);
			}
			else {
				commerceTaxMethod.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTaxMethodModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxMethod.setModifiedDate(date);
			}
			else {
				commerceTaxMethod.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTaxMethod);
			}
			else {
				commerceTaxMethod = (CommerceTaxMethod)session.merge(
					commerceTaxMethod);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTaxMethodImpl.class, commerceTaxMethodModelImpl, false,
			true);

		cacheUniqueFindersCache(commerceTaxMethodModelImpl);

		if (isNew) {
			commerceTaxMethod.setNew(false);
		}

		commerceTaxMethod.resetOriginalValues();

		return commerceTaxMethod;
	}

	/**
	 * Returns the commerce tax method with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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

			throw new NoSuchTaxMethodException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTaxMethod;
	}

	/**
	 * Returns the commerce tax method with the primary key or throws a <code>NoSuchTaxMethodException</code> if it could not be found.
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
	 * @param commerceTaxMethodId the primary key of the commerce tax method
	 * @return the commerce tax method, or <code>null</code> if a commerce tax method with the primary key could not be found
	 */
	@Override
	public CommerceTaxMethod fetchByPrimaryKey(long commerceTaxMethodId) {
		return fetchByPrimaryKey((Serializable)commerceTaxMethodId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax methods.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxMethodModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax methods
	 * @param end the upper bound of the range of commerce tax methods (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tax methods
	 */
	@Override
	public List<CommerceTaxMethod> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxMethod> orderByComparator,
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

		List<CommerceTaxMethod> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxMethod>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETAXMETHOD);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXMETHOD;

				sql = sql.concat(CommerceTaxMethodModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTaxMethod>)QueryUtil.list(
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCETAXMETHOD);

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
		return "commerceTaxMethodId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETAXMETHOD;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTaxMethodModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax method persistence.
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
		entityCache.removeCache(CommerceTaxMethodImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCETAXMETHOD =
		"SELECT commerceTaxMethod FROM CommerceTaxMethod commerceTaxMethod";

	private static final String _SQL_SELECT_COMMERCETAXMETHOD_WHERE =
		"SELECT commerceTaxMethod FROM CommerceTaxMethod commerceTaxMethod WHERE ";

	private static final String _SQL_COUNT_COMMERCETAXMETHOD =
		"SELECT COUNT(commerceTaxMethod) FROM CommerceTaxMethod commerceTaxMethod";

	private static final String _SQL_COUNT_COMMERCETAXMETHOD_WHERE =
		"SELECT COUNT(commerceTaxMethod) FROM CommerceTaxMethod commerceTaxMethod WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTaxMethod.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTaxMethod exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTaxMethod exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTaxMethodPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}