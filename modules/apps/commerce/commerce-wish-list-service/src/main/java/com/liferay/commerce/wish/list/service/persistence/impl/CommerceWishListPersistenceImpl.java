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

import com.liferay.commerce.wish.list.exception.NoSuchWishListException;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListTable;
import com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl;
import com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl;
import com.liferay.commerce.wish.list.service.persistence.CommerceWishListPersistence;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce wish list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @generated
 */
public class CommerceWishListPersistenceImpl
	extends BasePersistenceImpl<CommerceWishList>
	implements CommerceWishListPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceWishListUtil</code> to access the commerce wish list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceWishListImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the commerce wish lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishList commerceWishList : list) {
					if (!uuid.equals(commerceWishList.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Returns the first commerce wish list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByUuid_First(
			String uuid, OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUuid_First(
		String uuid, OrderByComparator<CommerceWishList> orderByComparator) {

		List<CommerceWishList> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByUuid_Last(
			String uuid, OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByUuid_Last(
			uuid, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUuid_Last(
		String uuid, OrderByComparator<CommerceWishList> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceWishList> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish lists before and after the current commerce wish list in the ordered set where uuid = &#63;.
	 *
	 * @param commerceWishListId the primary key of the current commerce wish list
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList[] findByUuid_PrevAndNext(
			long commerceWishListId, String uuid,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		uuid = Objects.toString(uuid, "");

		CommerceWishList commerceWishList = findByPrimaryKey(
			commerceWishListId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishList[] array = new CommerceWishListImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceWishList, uuid, orderByComparator, true);

			array[1] = commerceWishList;

			array[2] = getByUuid_PrevAndNext(
				session, commerceWishList, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceWishList getByUuid_PrevAndNext(
		Session session, CommerceWishList commerceWishList, String uuid,
		OrderByComparator<CommerceWishList> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish lists where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceWishList commerceWishList :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"commerceWishList.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceWishList.uuid IS NULL OR commerceWishList.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the commerce wish list where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchWishListException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByUUID_G(String uuid, long groupId)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByUUID_G(uuid, groupId);

		if (commerceWishList == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchWishListException(sb.toString());
		}

		return commerceWishList;
	}

	/**
	 * Returns the commerce wish list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce wish list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof CommerceWishList) {
			CommerceWishList commerceWishList = (CommerceWishList)result;

			if (!Objects.equals(uuid, commerceWishList.getUuid()) ||
				(groupId != commerceWishList.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<CommerceWishList> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CommerceWishList commerceWishList = list.get(0);

					result = commerceWishList;

					cacheResult(commerceWishList);
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
			return (CommerceWishList)result;
		}
	}

	/**
	 * Removes the commerce wish list where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce wish list that was removed
	 */
	@Override
	public CommerceWishList removeByUUID_G(String uuid, long groupId)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = findByUUID_G(uuid, groupId);

		return remove(commerceWishList);
	}

	/**
	 * Returns the number of commerce wish lists where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"commerceWishList.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(commerceWishList.uuid IS NULL OR commerceWishList.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"commerceWishList.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishList commerceWishList : list) {
					if (!uuid.equals(commerceWishList.getUuid()) ||
						(companyId != commerceWishList.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Returns the first commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceWishList> orderByComparator) {

		List<CommerceWishList> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceWishList> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishList> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish lists before and after the current commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceWishListId the primary key of the current commerce wish list
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList[] findByUuid_C_PrevAndNext(
			long commerceWishListId, String uuid, long companyId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		uuid = Objects.toString(uuid, "");

		CommerceWishList commerceWishList = findByPrimaryKey(
			commerceWishListId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishList[] array = new CommerceWishListImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceWishList, uuid, companyId, orderByComparator,
				true);

			array[1] = commerceWishList;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceWishList, uuid, companyId, orderByComparator,
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

	protected CommerceWishList getByUuid_C_PrevAndNext(
		Session session, CommerceWishList commerceWishList, String uuid,
		long companyId, OrderByComparator<CommerceWishList> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish lists where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceWishList commerceWishList :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"commerceWishList.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceWishList.uuid IS NULL OR commerceWishList.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceWishList.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the commerce wish lists where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
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

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishList commerceWishList : list) {
					if (groupId != commerceWishList.getGroupId()) {
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

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Returns the first commerce wish list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByGroupId_First(
			long groupId, OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByGroupId_First(
			groupId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByGroupId_First(
		long groupId, OrderByComparator<CommerceWishList> orderByComparator) {

		List<CommerceWishList> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByGroupId_Last(
			long groupId, OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByGroupId_Last(
		long groupId, OrderByComparator<CommerceWishList> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishList> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish lists before and after the current commerce wish list in the ordered set where groupId = &#63;.
	 *
	 * @param commerceWishListId the primary key of the current commerce wish list
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList[] findByGroupId_PrevAndNext(
			long commerceWishListId, long groupId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = findByPrimaryKey(
			commerceWishListId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishList[] array = new CommerceWishListImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, commerceWishList, groupId, orderByComparator, true);

			array[1] = commerceWishList;

			array[2] = getByGroupId_PrevAndNext(
				session, commerceWishList, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceWishList getByGroupId_PrevAndNext(
		Session session, CommerceWishList commerceWishList, long groupId,
		OrderByComparator<CommerceWishList> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

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
			sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
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
						commerceWishList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish lists where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceWishList commerceWishList :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

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
		"commerceWishList.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the commerce wish lists where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUserId(
		long userId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByUserId(
		long userId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishList commerceWishList : list) {
					if (userId != commerceWishList.getUserId()) {
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

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Returns the first commerce wish list in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByUserId_First(
			long userId, OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByUserId_First(
			userId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUserId_First(
		long userId, OrderByComparator<CommerceWishList> orderByComparator) {

		List<CommerceWishList> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByUserId_Last(
			long userId, OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByUserId_Last(
			userId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByUserId_Last(
		long userId, OrderByComparator<CommerceWishList> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishList> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish lists before and after the current commerce wish list in the ordered set where userId = &#63;.
	 *
	 * @param commerceWishListId the primary key of the current commerce wish list
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList[] findByUserId_PrevAndNext(
			long commerceWishListId, long userId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = findByPrimaryKey(
			commerceWishListId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishList[] array = new CommerceWishListImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, commerceWishList, userId, orderByComparator, true);

			array[1] = commerceWishList;

			array[2] = getByUserId_PrevAndNext(
				session, commerceWishList, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceWishList getByUserId_PrevAndNext(
		Session session, CommerceWishList commerceWishList, long userId,
		OrderByComparator<CommerceWishList> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

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
			sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish lists where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (CommerceWishList commerceWishList :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"commerceWishList.userId = ?";

	private FinderPath _finderPathWithPaginationFindByG_U;
	private FinderPath _finderPathWithoutPaginationFindByG_U;
	private FinderPath _finderPathCountByG_U;

	/**
	 * Returns all the commerce wish lists where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U(long groupId, long userId) {
		return findByG_U(
			groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U(
		long groupId, long userId, int start, int end) {

		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_U;
				finderArgs = new Object[] {groupId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_U;
			finderArgs = new Object[] {
				groupId, userId, start, end, orderByComparator
			};
		}

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishList commerceWishList : list) {
					if ((groupId != commerceWishList.getGroupId()) ||
						(userId != commerceWishList.getUserId())) {

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

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByG_U_First(
			long groupId, long userId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByG_U_First(
			groupId, userId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<CommerceWishList> orderByComparator) {

		List<CommerceWishList> list = findByG_U(
			groupId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByG_U_Last(
			long groupId, long userId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByG_U_Last(
			groupId, userId, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByG_U_Last(
		long groupId, long userId,
		OrderByComparator<CommerceWishList> orderByComparator) {

		int count = countByG_U(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<CommerceWishList> list = findByG_U(
			groupId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish lists before and after the current commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param commerceWishListId the primary key of the current commerce wish list
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList[] findByG_U_PrevAndNext(
			long commerceWishListId, long groupId, long userId,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = findByPrimaryKey(
			commerceWishListId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishList[] array = new CommerceWishListImpl[3];

			array[0] = getByG_U_PrevAndNext(
				session, commerceWishList, groupId, userId, orderByComparator,
				true);

			array[1] = commerceWishList;

			array[2] = getByG_U_PrevAndNext(
				session, commerceWishList, groupId, userId, orderByComparator,
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

	protected CommerceWishList getByG_U_PrevAndNext(
		Session session, CommerceWishList commerceWishList, long groupId,
		long userId, OrderByComparator<CommerceWishList> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

		sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_USERID_2);

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
			sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish lists where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		for (CommerceWishList commerceWishList :
				findByG_U(
					groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = _finderPathCountByG_U;

		Object[] finderArgs = new Object[] {groupId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 =
		"commerceWishList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_USERID_2 =
		"commerceWishList.userId = ?";

	private FinderPath _finderPathWithPaginationFindByU_LtC;
	private FinderPath _finderPathWithPaginationCountByU_LtC;

	/**
	 * Returns all the commerce wish lists where userId = &#63; and createDate &lt; &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @return the matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByU_LtC(long userId, Date createDate) {
		return findByU_LtC(
			userId, createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists where userId = &#63; and createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByU_LtC(
		long userId, Date createDate, int start, int end) {

		return findByU_LtC(userId, createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where userId = &#63; and createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByU_LtC(
		long userId, Date createDate, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findByU_LtC(
			userId, createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where userId = &#63; and createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByU_LtC(
		long userId, Date createDate, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByU_LtC;
		finderArgs = new Object[] {
			userId, _getTime(createDate), start, end, orderByComparator
		};

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishList commerceWishList : list) {
					if ((userId != commerceWishList.getUserId()) ||
						(createDate.getTime() <= commerceWishList.getCreateDate(
						).getTime())) {

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

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_U_LTC_USERID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_U_LTC_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_U_LTC_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Returns the first commerce wish list in the ordered set where userId = &#63; and createDate &lt; &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByU_LtC_First(
			long userId, Date createDate,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByU_LtC_First(
			userId, createDate, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list in the ordered set where userId = &#63; and createDate &lt; &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByU_LtC_First(
		long userId, Date createDate,
		OrderByComparator<CommerceWishList> orderByComparator) {

		List<CommerceWishList> list = findByU_LtC(
			userId, createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list in the ordered set where userId = &#63; and createDate &lt; &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByU_LtC_Last(
			long userId, Date createDate,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByU_LtC_Last(
			userId, createDate, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list in the ordered set where userId = &#63; and createDate &lt; &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByU_LtC_Last(
		long userId, Date createDate,
		OrderByComparator<CommerceWishList> orderByComparator) {

		int count = countByU_LtC(userId, createDate);

		if (count == 0) {
			return null;
		}

		List<CommerceWishList> list = findByU_LtC(
			userId, createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish lists before and after the current commerce wish list in the ordered set where userId = &#63; and createDate &lt; &#63;.
	 *
	 * @param commerceWishListId the primary key of the current commerce wish list
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList[] findByU_LtC_PrevAndNext(
			long commerceWishListId, long userId, Date createDate,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = findByPrimaryKey(
			commerceWishListId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishList[] array = new CommerceWishListImpl[3];

			array[0] = getByU_LtC_PrevAndNext(
				session, commerceWishList, userId, createDate,
				orderByComparator, true);

			array[1] = commerceWishList;

			array[2] = getByU_LtC_PrevAndNext(
				session, commerceWishList, userId, createDate,
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

	protected CommerceWishList getByU_LtC_PrevAndNext(
		Session session, CommerceWishList commerceWishList, long userId,
		Date createDate, OrderByComparator<CommerceWishList> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

		sb.append(_FINDER_COLUMN_U_LTC_USERID_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			sb.append(_FINDER_COLUMN_U_LTC_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			sb.append(_FINDER_COLUMN_U_LTC_CREATEDATE_2);
		}

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
			sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (bindCreateDate) {
			queryPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish lists where userId = &#63; and createDate &lt; &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 */
	@Override
	public void removeByU_LtC(long userId, Date createDate) {
		for (CommerceWishList commerceWishList :
				findByU_LtC(
					userId, createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists where userId = &#63; and createDate &lt; &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByU_LtC(long userId, Date createDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByU_LtC;

		Object[] finderArgs = new Object[] {userId, _getTime(createDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_U_LTC_USERID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_U_LTC_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_U_LTC_CREATEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
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

	private static final String _FINDER_COLUMN_U_LTC_USERID_2 =
		"commerceWishList.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_LTC_CREATEDATE_1 =
		"commerceWishList.createDate IS NULL";

	private static final String _FINDER_COLUMN_U_LTC_CREATEDATE_2 =
		"commerceWishList.createDate < ?";

	private FinderPath _finderPathWithPaginationFindByG_U_D;
	private FinderPath _finderPathWithoutPaginationFindByG_U_D;
	private FinderPath _finderPathCountByG_U_D;

	/**
	 * Returns all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @return the matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U_D(
		long groupId, long userId, boolean defaultWishList) {

		return findByG_U_D(
			groupId, userId, defaultWishList, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U_D(
		long groupId, long userId, boolean defaultWishList, int start,
		int end) {

		return findByG_U_D(groupId, userId, defaultWishList, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U_D(
		long groupId, long userId, boolean defaultWishList, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findByG_U_D(
			groupId, userId, defaultWishList, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findByG_U_D(
		long groupId, long userId, boolean defaultWishList, int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_U_D;
				finderArgs = new Object[] {groupId, userId, defaultWishList};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_U_D;
			finderArgs = new Object[] {
				groupId, userId, defaultWishList, start, end, orderByComparator
			};
		}

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWishList commerceWishList : list) {
					if ((groupId != commerceWishList.getGroupId()) ||
						(userId != commerceWishList.getUserId()) ||
						(defaultWishList !=
							commerceWishList.isDefaultWishList())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_G_U_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_D_USERID_2);

			sb.append(_FINDER_COLUMN_G_U_D_DEFAULTWISHLIST_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(defaultWishList);

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByG_U_D_First(
			long groupId, long userId, boolean defaultWishList,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByG_U_D_First(
			groupId, userId, defaultWishList, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", defaultWishList=");
		sb.append(defaultWishList);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByG_U_D_First(
		long groupId, long userId, boolean defaultWishList,
		OrderByComparator<CommerceWishList> orderByComparator) {

		List<CommerceWishList> list = findByG_U_D(
			groupId, userId, defaultWishList, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list
	 * @throws NoSuchWishListException if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList findByG_U_D_Last(
			long groupId, long userId, boolean defaultWishList,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByG_U_D_Last(
			groupId, userId, defaultWishList, orderByComparator);

		if (commerceWishList != null) {
			return commerceWishList;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", defaultWishList=");
		sb.append(defaultWishList);

		sb.append("}");

		throw new NoSuchWishListException(sb.toString());
	}

	/**
	 * Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	 */
	@Override
	public CommerceWishList fetchByG_U_D_Last(
		long groupId, long userId, boolean defaultWishList,
		OrderByComparator<CommerceWishList> orderByComparator) {

		int count = countByG_U_D(groupId, userId, defaultWishList);

		if (count == 0) {
			return null;
		}

		List<CommerceWishList> list = findByG_U_D(
			groupId, userId, defaultWishList, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce wish lists before and after the current commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * @param commerceWishListId the primary key of the current commerce wish list
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList[] findByG_U_D_PrevAndNext(
			long commerceWishListId, long groupId, long userId,
			boolean defaultWishList,
			OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = findByPrimaryKey(
			commerceWishListId);

		Session session = null;

		try {
			session = openSession();

			CommerceWishList[] array = new CommerceWishListImpl[3];

			array[0] = getByG_U_D_PrevAndNext(
				session, commerceWishList, groupId, userId, defaultWishList,
				orderByComparator, true);

			array[1] = commerceWishList;

			array[2] = getByG_U_D_PrevAndNext(
				session, commerceWishList, groupId, userId, defaultWishList,
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

	protected CommerceWishList getByG_U_D_PrevAndNext(
		Session session, CommerceWishList commerceWishList, long groupId,
		long userId, boolean defaultWishList,
		OrderByComparator<CommerceWishList> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_COMMERCEWISHLIST_WHERE);

		sb.append(_FINDER_COLUMN_G_U_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_U_D_USERID_2);

		sb.append(_FINDER_COLUMN_G_U_D_DEFAULTWISHLIST_2);

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
			sb.append(CommerceWishListModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		queryPos.add(defaultWishList);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceWishList)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceWishList> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 */
	@Override
	public void removeByG_U_D(
		long groupId, long userId, boolean defaultWishList) {

		for (CommerceWishList commerceWishList :
				findByG_U_D(
					groupId, userId, defaultWishList, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param defaultWishList the default wish list
	 * @return the number of matching commerce wish lists
	 */
	@Override
	public int countByG_U_D(
		long groupId, long userId, boolean defaultWishList) {

		FinderPath finderPath = _finderPathCountByG_U_D;

		Object[] finderArgs = new Object[] {groupId, userId, defaultWishList};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEWISHLIST_WHERE);

			sb.append(_FINDER_COLUMN_G_U_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_D_USERID_2);

			sb.append(_FINDER_COLUMN_G_U_D_DEFAULTWISHLIST_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(defaultWishList);

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

	private static final String _FINDER_COLUMN_G_U_D_GROUPID_2 =
		"commerceWishList.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_D_USERID_2 =
		"commerceWishList.userId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_D_DEFAULTWISHLIST_2 =
		"commerceWishList.defaultWishList = ?";

	public CommerceWishListPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceWishList.class);

		setModelImplClass(CommerceWishListImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceWishListTable.INSTANCE);
	}

	/**
	 * Caches the commerce wish list in the entity cache if it is enabled.
	 *
	 * @param commerceWishList the commerce wish list
	 */
	@Override
	public void cacheResult(CommerceWishList commerceWishList) {
		entityCache.putResult(
			CommerceWishListImpl.class, commerceWishList.getPrimaryKey(),
			commerceWishList);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				commerceWishList.getUuid(), commerceWishList.getGroupId()
			},
			commerceWishList);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce wish lists in the entity cache if it is enabled.
	 *
	 * @param commerceWishLists the commerce wish lists
	 */
	@Override
	public void cacheResult(List<CommerceWishList> commerceWishLists) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceWishLists.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceWishList commerceWishList : commerceWishLists) {
			if (entityCache.getResult(
					CommerceWishListImpl.class,
					commerceWishList.getPrimaryKey()) == null) {

				cacheResult(commerceWishList);
			}
		}
	}

	/**
	 * Clears the cache for all commerce wish lists.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceWishListImpl.class);

		finderCache.clearCache(CommerceWishListImpl.class);
	}

	/**
	 * Clears the cache for the commerce wish list.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceWishList commerceWishList) {
		entityCache.removeResult(CommerceWishListImpl.class, commerceWishList);
	}

	@Override
	public void clearCache(List<CommerceWishList> commerceWishLists) {
		for (CommerceWishList commerceWishList : commerceWishLists) {
			entityCache.removeResult(
				CommerceWishListImpl.class, commerceWishList);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceWishListImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceWishListImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceWishListModelImpl commerceWishListModelImpl) {

		Object[] args = new Object[] {
			commerceWishListModelImpl.getUuid(),
			commerceWishListModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, commerceWishListModelImpl);
	}

	/**
	 * Creates a new commerce wish list with the primary key. Does not add the commerce wish list to the database.
	 *
	 * @param commerceWishListId the primary key for the new commerce wish list
	 * @return the new commerce wish list
	 */
	@Override
	public CommerceWishList create(long commerceWishListId) {
		CommerceWishList commerceWishList = new CommerceWishListImpl();

		commerceWishList.setNew(true);
		commerceWishList.setPrimaryKey(commerceWishListId);

		String uuid = PortalUUIDUtil.generate();

		commerceWishList.setUuid(uuid);

		commerceWishList.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceWishList;
	}

	/**
	 * Removes the commerce wish list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceWishListId the primary key of the commerce wish list
	 * @return the commerce wish list that was removed
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList remove(long commerceWishListId)
		throws NoSuchWishListException {

		return remove((Serializable)commerceWishListId);
	}

	/**
	 * Removes the commerce wish list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce wish list
	 * @return the commerce wish list that was removed
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList remove(Serializable primaryKey)
		throws NoSuchWishListException {

		Session session = null;

		try {
			session = openSession();

			CommerceWishList commerceWishList = (CommerceWishList)session.get(
				CommerceWishListImpl.class, primaryKey);

			if (commerceWishList == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWishListException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceWishList);
		}
		catch (NoSuchWishListException noSuchEntityException) {
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
	protected CommerceWishList removeImpl(CommerceWishList commerceWishList) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceWishList)) {
				commerceWishList = (CommerceWishList)session.get(
					CommerceWishListImpl.class,
					commerceWishList.getPrimaryKeyObj());
			}

			if (commerceWishList != null) {
				session.delete(commerceWishList);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceWishList != null) {
			clearCache(commerceWishList);
		}

		return commerceWishList;
	}

	@Override
	public CommerceWishList updateImpl(CommerceWishList commerceWishList) {
		boolean isNew = commerceWishList.isNew();

		if (!(commerceWishList instanceof CommerceWishListModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceWishList.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceWishList);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceWishList proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceWishList implementation " +
					commerceWishList.getClass());
		}

		CommerceWishListModelImpl commerceWishListModelImpl =
			(CommerceWishListModelImpl)commerceWishList;

		if (Validator.isNull(commerceWishList.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceWishList.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceWishList.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceWishList.setCreateDate(date);
			}
			else {
				commerceWishList.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceWishListModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceWishList.setModifiedDate(date);
			}
			else {
				commerceWishList.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceWishList);
			}
			else {
				commerceWishList = (CommerceWishList)session.merge(
					commerceWishList);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceWishListImpl.class, commerceWishListModelImpl, false, true);

		cacheUniqueFindersCache(commerceWishListModelImpl);

		if (isNew) {
			commerceWishList.setNew(false);
		}

		commerceWishList.resetOriginalValues();

		return commerceWishList;
	}

	/**
	 * Returns the commerce wish list with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce wish list
	 * @return the commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWishListException {

		CommerceWishList commerceWishList = fetchByPrimaryKey(primaryKey);

		if (commerceWishList == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWishListException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceWishList;
	}

	/**
	 * Returns the commerce wish list with the primary key or throws a <code>NoSuchWishListException</code> if it could not be found.
	 *
	 * @param commerceWishListId the primary key of the commerce wish list
	 * @return the commerce wish list
	 * @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList findByPrimaryKey(long commerceWishListId)
		throws NoSuchWishListException {

		return findByPrimaryKey((Serializable)commerceWishListId);
	}

	/**
	 * Returns the commerce wish list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceWishListId the primary key of the commerce wish list
	 * @return the commerce wish list, or <code>null</code> if a commerce wish list with the primary key could not be found
	 */
	@Override
	public CommerceWishList fetchByPrimaryKey(long commerceWishListId) {
		return fetchByPrimaryKey((Serializable)commerceWishListId);
	}

	/**
	 * Returns all the commerce wish lists.
	 *
	 * @return the commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce wish lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @return the range of commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findAll(
		int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce wish lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceWishListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce wish lists
	 * @param end the upper bound of the range of commerce wish lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce wish lists
	 */
	@Override
	public List<CommerceWishList> findAll(
		int start, int end,
		OrderByComparator<CommerceWishList> orderByComparator,
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

		List<CommerceWishList> list = null;

		if (useFinderCache) {
			list = (List<CommerceWishList>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEWISHLIST);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEWISHLIST;

				sql = sql.concat(CommerceWishListModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceWishList>)QueryUtil.list(
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
	 * Removes all the commerce wish lists from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceWishList commerceWishList : findAll()) {
			remove(commerceWishList);
		}
	}

	/**
	 * Returns the number of commerce wish lists.
	 *
	 * @return the number of commerce wish lists
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEWISHLIST);

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
		return "commerceWishListId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEWISHLIST;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceWishListModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce wish list persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

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

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathWithPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId"}, true);

		_finderPathWithoutPaginationFindByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, true);

		_finderPathCountByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, false);

		_finderPathWithPaginationFindByU_LtC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_LtC",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "createDate"}, true);

		_finderPathWithPaginationCountByU_LtC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByU_LtC",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"userId", "createDate"}, false);

		_finderPathWithPaginationFindByG_U_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "userId", "defaultWishList"}, true);

		_finderPathWithoutPaginationFindByG_U_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "userId", "defaultWishList"}, true);

		_finderPathCountByG_U_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "userId", "defaultWishList"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceWishListImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCEWISHLIST =
		"SELECT commerceWishList FROM CommerceWishList commerceWishList";

	private static final String _SQL_SELECT_COMMERCEWISHLIST_WHERE =
		"SELECT commerceWishList FROM CommerceWishList commerceWishList WHERE ";

	private static final String _SQL_COUNT_COMMERCEWISHLIST =
		"SELECT COUNT(commerceWishList) FROM CommerceWishList commerceWishList";

	private static final String _SQL_COUNT_COMMERCEWISHLIST_WHERE =
		"SELECT COUNT(commerceWishList) FROM CommerceWishList commerceWishList WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceWishList.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceWishList exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceWishList exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceWishListPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}