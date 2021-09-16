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

package com.liferay.commerce.product.type.virtual.order.service.persistence.impl;

import com.liferay.commerce.product.type.virtual.order.exception.NoSuchVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemTable;
import com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemImpl;
import com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl;
import com.liferay.commerce.product.type.virtual.order.service.persistence.CommerceVirtualOrderItemPersistence;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce virtual order item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceVirtualOrderItemPersistenceImpl
	extends BasePersistenceImpl<CommerceVirtualOrderItem>
	implements CommerceVirtualOrderItemPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceVirtualOrderItemUtil</code> to access the commerce virtual order item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceVirtualOrderItemImpl.class.getName();

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
	 * Returns all the commerce virtual order items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce virtual order items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @return the range of matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
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

		List<CommerceVirtualOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceVirtualOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVirtualOrderItem commerceVirtualOrderItem : list) {
					if (!uuid.equals(commerceVirtualOrderItem.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEM_WHERE);

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
				sb.append(CommerceVirtualOrderItemModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceVirtualOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByUuid_First(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceVirtualOrderItem != null) {
			return commerceVirtualOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVirtualOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		List<CommerceVirtualOrderItem> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem = fetchByUuid_Last(
			uuid, orderByComparator);

		if (commerceVirtualOrderItem != null) {
			return commerceVirtualOrderItem;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVirtualOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceVirtualOrderItem> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce virtual order items before and after the current commerce virtual order item in the ordered set where uuid = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the current commerce virtual order item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItem[] findByUuid_PrevAndNext(
			long commerceVirtualOrderItemId, String uuid,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException {

		uuid = Objects.toString(uuid, "");

		CommerceVirtualOrderItem commerceVirtualOrderItem = findByPrimaryKey(
			commerceVirtualOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItem[] array =
				new CommerceVirtualOrderItemImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceVirtualOrderItem, uuid, orderByComparator,
				true);

			array[1] = commerceVirtualOrderItem;

			array[2] = getByUuid_PrevAndNext(
				session, commerceVirtualOrderItem, uuid, orderByComparator,
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

	protected CommerceVirtualOrderItem getByUuid_PrevAndNext(
		Session session, CommerceVirtualOrderItem commerceVirtualOrderItem,
		String uuid,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEM_WHERE);

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
			sb.append(CommerceVirtualOrderItemModelImpl.ORDER_BY_JPQL);
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
						commerceVirtualOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceVirtualOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce virtual order items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceVirtualOrderItem commerceVirtualOrderItem :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceVirtualOrderItem);
		}
	}

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce virtual order items
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEM_WHERE);

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
		"commerceVirtualOrderItem.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceVirtualOrderItem.uuid IS NULL OR commerceVirtualOrderItem.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByUUID_G(String uuid, long groupId)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem = fetchByUUID_G(
			uuid, groupId);

		if (commerceVirtualOrderItem == null) {
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

			throw new NoSuchVirtualOrderItemException(sb.toString());
		}

		return commerceVirtualOrderItem;
	}

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce virtual order item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByUUID_G(
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

		if (result instanceof CommerceVirtualOrderItem) {
			CommerceVirtualOrderItem commerceVirtualOrderItem =
				(CommerceVirtualOrderItem)result;

			if (!Objects.equals(uuid, commerceVirtualOrderItem.getUuid()) ||
				(groupId != commerceVirtualOrderItem.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEM_WHERE);

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

				List<CommerceVirtualOrderItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CommerceVirtualOrderItem commerceVirtualOrderItem =
						list.get(0);

					result = commerceVirtualOrderItem;

					cacheResult(commerceVirtualOrderItem);
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
			return (CommerceVirtualOrderItem)result;
		}
	}

	/**
	 * Removes the commerce virtual order item where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce virtual order item that was removed
	 */
	@Override
	public CommerceVirtualOrderItem removeByUUID_G(String uuid, long groupId)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem = findByUUID_G(
			uuid, groupId);

		return remove(commerceVirtualOrderItem);
	}

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce virtual order items
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEM_WHERE);

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
		"commerceVirtualOrderItem.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(commerceVirtualOrderItem.uuid IS NULL OR commerceVirtualOrderItem.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"commerceVirtualOrderItem.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @return the range of matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
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

		List<CommerceVirtualOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceVirtualOrderItem>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVirtualOrderItem commerceVirtualOrderItem : list) {
					if (!uuid.equals(commerceVirtualOrderItem.getUuid()) ||
						(companyId !=
							commerceVirtualOrderItem.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEM_WHERE);

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
				sb.append(CommerceVirtualOrderItemModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceVirtualOrderItem>)QueryUtil.list(
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
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commerceVirtualOrderItem != null) {
			return commerceVirtualOrderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemException(sb.toString());
	}

	/**
	 * Returns the first commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		List<CommerceVirtualOrderItem> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (commerceVirtualOrderItem != null) {
			return commerceVirtualOrderItem;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVirtualOrderItemException(sb.toString());
	}

	/**
	 * Returns the last commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceVirtualOrderItem> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce virtual order items before and after the current commerce virtual order item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the current commerce virtual order item
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItem[] findByUuid_C_PrevAndNext(
			long commerceVirtualOrderItemId, String uuid, long companyId,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator)
		throws NoSuchVirtualOrderItemException {

		uuid = Objects.toString(uuid, "");

		CommerceVirtualOrderItem commerceVirtualOrderItem = findByPrimaryKey(
			commerceVirtualOrderItemId);

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItem[] array =
				new CommerceVirtualOrderItemImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceVirtualOrderItem, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceVirtualOrderItem;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceVirtualOrderItem, uuid, companyId,
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

	protected CommerceVirtualOrderItem getByUuid_C_PrevAndNext(
		Session session, CommerceVirtualOrderItem commerceVirtualOrderItem,
		String uuid, long companyId,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEM_WHERE);

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
			sb.append(CommerceVirtualOrderItemModelImpl.ORDER_BY_JPQL);
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
						commerceVirtualOrderItem)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceVirtualOrderItem> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce virtual order items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceVirtualOrderItem commerceVirtualOrderItem :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceVirtualOrderItem);
		}
	}

	/**
	 * Returns the number of commerce virtual order items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce virtual order items
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEM_WHERE);

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
		"commerceVirtualOrderItem.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceVirtualOrderItem.uuid IS NULL OR commerceVirtualOrderItem.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceVirtualOrderItem.companyId = ?";

	private FinderPath _finderPathFetchByCommerceOrderItemId;
	private FinderPath _finderPathCountByCommerceOrderItemId;

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			fetchByCommerceOrderItemId(commerceOrderItemId);

		if (commerceVirtualOrderItem == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceOrderItemId=");
			sb.append(commerceOrderItemId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchVirtualOrderItemException(sb.toString());
		}

		return commerceVirtualOrderItem;
	}

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByCommerceOrderItemId(
		long commerceOrderItemId) {

		return fetchByCommerceOrderItemId(commerceOrderItemId, true);
	}

	/**
	 * Returns the commerce virtual order item where commerceOrderItemId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByCommerceOrderItemId(
		long commerceOrderItemId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceOrderItemId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCommerceOrderItemId, finderArgs);
		}

		if (result instanceof CommerceVirtualOrderItem) {
			CommerceVirtualOrderItem commerceVirtualOrderItem =
				(CommerceVirtualOrderItem)result;

			if (commerceOrderItemId !=
					commerceVirtualOrderItem.getCommerceOrderItemId()) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERITEMID_COMMERCEORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderItemId);

				List<CommerceVirtualOrderItem> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCommerceOrderItemId, finderArgs,
							list);
					}
				}
				else {
					CommerceVirtualOrderItem commerceVirtualOrderItem =
						list.get(0);

					result = commerceVirtualOrderItem;

					cacheResult(commerceVirtualOrderItem);
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
			return (CommerceVirtualOrderItem)result;
		}
	}

	/**
	 * Removes the commerce virtual order item where commerceOrderItemId = &#63; from the database.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the commerce virtual order item that was removed
	 */
	@Override
	public CommerceVirtualOrderItem removeByCommerceOrderItemId(
			long commerceOrderItemId)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			findByCommerceOrderItemId(commerceOrderItemId);

		return remove(commerceVirtualOrderItem);
	}

	/**
	 * Returns the number of commerce virtual order items where commerceOrderItemId = &#63;.
	 *
	 * @param commerceOrderItemId the commerce order item ID
	 * @return the number of matching commerce virtual order items
	 */
	@Override
	public int countByCommerceOrderItemId(long commerceOrderItemId) {
		FinderPath finderPath = _finderPathCountByCommerceOrderItemId;

		Object[] finderArgs = new Object[] {commerceOrderItemId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEVIRTUALORDERITEM_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERITEMID_COMMERCEORDERITEMID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderItemId);

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

	private static final String
		_FINDER_COLUMN_COMMERCEORDERITEMID_COMMERCEORDERITEMID_2 =
			"commerceVirtualOrderItem.commerceOrderItemId = ?";

	public CommerceVirtualOrderItemPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceVirtualOrderItem.class);

		setModelImplClass(CommerceVirtualOrderItemImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceVirtualOrderItemTable.INSTANCE);
	}

	/**
	 * Caches the commerce virtual order item in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItem the commerce virtual order item
	 */
	@Override
	public void cacheResult(CommerceVirtualOrderItem commerceVirtualOrderItem) {
		entityCache.putResult(
			CommerceVirtualOrderItemImpl.class,
			commerceVirtualOrderItem.getPrimaryKey(), commerceVirtualOrderItem);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				commerceVirtualOrderItem.getUuid(),
				commerceVirtualOrderItem.getGroupId()
			},
			commerceVirtualOrderItem);

		finderCache.putResult(
			_finderPathFetchByCommerceOrderItemId,
			new Object[] {commerceVirtualOrderItem.getCommerceOrderItemId()},
			commerceVirtualOrderItem);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce virtual order items in the entity cache if it is enabled.
	 *
	 * @param commerceVirtualOrderItems the commerce virtual order items
	 */
	@Override
	public void cacheResult(
		List<CommerceVirtualOrderItem> commerceVirtualOrderItems) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceVirtualOrderItems.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceVirtualOrderItem commerceVirtualOrderItem :
				commerceVirtualOrderItems) {

			if (entityCache.getResult(
					CommerceVirtualOrderItemImpl.class,
					commerceVirtualOrderItem.getPrimaryKey()) == null) {

				cacheResult(commerceVirtualOrderItem);
			}
		}
	}

	/**
	 * Clears the cache for all commerce virtual order items.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceVirtualOrderItemImpl.class);

		finderCache.clearCache(CommerceVirtualOrderItemImpl.class);
	}

	/**
	 * Clears the cache for the commerce virtual order item.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceVirtualOrderItem commerceVirtualOrderItem) {
		entityCache.removeResult(
			CommerceVirtualOrderItemImpl.class, commerceVirtualOrderItem);
	}

	@Override
	public void clearCache(
		List<CommerceVirtualOrderItem> commerceVirtualOrderItems) {

		for (CommerceVirtualOrderItem commerceVirtualOrderItem :
				commerceVirtualOrderItems) {

			entityCache.removeResult(
				CommerceVirtualOrderItemImpl.class, commerceVirtualOrderItem);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceVirtualOrderItemImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceVirtualOrderItemImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceVirtualOrderItemModelImpl commerceVirtualOrderItemModelImpl) {

		Object[] args = new Object[] {
			commerceVirtualOrderItemModelImpl.getUuid(),
			commerceVirtualOrderItemModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, commerceVirtualOrderItemModelImpl);

		args = new Object[] {
			commerceVirtualOrderItemModelImpl.getCommerceOrderItemId()
		};

		finderCache.putResult(
			_finderPathCountByCommerceOrderItemId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCommerceOrderItemId, args,
			commerceVirtualOrderItemModelImpl);
	}

	/**
	 * Creates a new commerce virtual order item with the primary key. Does not add the commerce virtual order item to the database.
	 *
	 * @param commerceVirtualOrderItemId the primary key for the new commerce virtual order item
	 * @return the new commerce virtual order item
	 */
	@Override
	public CommerceVirtualOrderItem create(long commerceVirtualOrderItemId) {
		CommerceVirtualOrderItem commerceVirtualOrderItem =
			new CommerceVirtualOrderItemImpl();

		commerceVirtualOrderItem.setNew(true);
		commerceVirtualOrderItem.setPrimaryKey(commerceVirtualOrderItemId);

		String uuid = PortalUUIDUtil.generate();

		commerceVirtualOrderItem.setUuid(uuid);

		commerceVirtualOrderItem.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceVirtualOrderItem;
	}

	/**
	 * Removes the commerce virtual order item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item that was removed
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItem remove(long commerceVirtualOrderItemId)
		throws NoSuchVirtualOrderItemException {

		return remove((Serializable)commerceVirtualOrderItemId);
	}

	/**
	 * Removes the commerce virtual order item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce virtual order item
	 * @return the commerce virtual order item that was removed
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItem remove(Serializable primaryKey)
		throws NoSuchVirtualOrderItemException {

		Session session = null;

		try {
			session = openSession();

			CommerceVirtualOrderItem commerceVirtualOrderItem =
				(CommerceVirtualOrderItem)session.get(
					CommerceVirtualOrderItemImpl.class, primaryKey);

			if (commerceVirtualOrderItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVirtualOrderItemException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceVirtualOrderItem);
		}
		catch (NoSuchVirtualOrderItemException noSuchEntityException) {
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
	protected CommerceVirtualOrderItem removeImpl(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceVirtualOrderItem)) {
				commerceVirtualOrderItem =
					(CommerceVirtualOrderItem)session.get(
						CommerceVirtualOrderItemImpl.class,
						commerceVirtualOrderItem.getPrimaryKeyObj());
			}

			if (commerceVirtualOrderItem != null) {
				session.delete(commerceVirtualOrderItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceVirtualOrderItem != null) {
			clearCache(commerceVirtualOrderItem);
		}

		return commerceVirtualOrderItem;
	}

	@Override
	public CommerceVirtualOrderItem updateImpl(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		boolean isNew = commerceVirtualOrderItem.isNew();

		if (!(commerceVirtualOrderItem instanceof
				CommerceVirtualOrderItemModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceVirtualOrderItem.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceVirtualOrderItem);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceVirtualOrderItem proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceVirtualOrderItem implementation " +
					commerceVirtualOrderItem.getClass());
		}

		CommerceVirtualOrderItemModelImpl commerceVirtualOrderItemModelImpl =
			(CommerceVirtualOrderItemModelImpl)commerceVirtualOrderItem;

		if (Validator.isNull(commerceVirtualOrderItem.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceVirtualOrderItem.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceVirtualOrderItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceVirtualOrderItem.setCreateDate(date);
			}
			else {
				commerceVirtualOrderItem.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceVirtualOrderItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceVirtualOrderItem.setModifiedDate(date);
			}
			else {
				commerceVirtualOrderItem.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceVirtualOrderItem);
			}
			else {
				commerceVirtualOrderItem =
					(CommerceVirtualOrderItem)session.merge(
						commerceVirtualOrderItem);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceVirtualOrderItemImpl.class,
			commerceVirtualOrderItemModelImpl, false, true);

		cacheUniqueFindersCache(commerceVirtualOrderItemModelImpl);

		if (isNew) {
			commerceVirtualOrderItem.setNew(false);
		}

		commerceVirtualOrderItem.resetOriginalValues();

		return commerceVirtualOrderItem;
	}

	/**
	 * Returns the commerce virtual order item with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce virtual order item
	 * @return the commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVirtualOrderItemException {

		CommerceVirtualOrderItem commerceVirtualOrderItem = fetchByPrimaryKey(
			primaryKey);

		if (commerceVirtualOrderItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVirtualOrderItemException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceVirtualOrderItem;
	}

	/**
	 * Returns the commerce virtual order item with the primary key or throws a <code>NoSuchVirtualOrderItemException</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item
	 * @throws NoSuchVirtualOrderItemException if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItem findByPrimaryKey(
			long commerceVirtualOrderItemId)
		throws NoSuchVirtualOrderItemException {

		return findByPrimaryKey((Serializable)commerceVirtualOrderItemId);
	}

	/**
	 * Returns the commerce virtual order item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item, or <code>null</code> if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public CommerceVirtualOrderItem fetchByPrimaryKey(
		long commerceVirtualOrderItemId) {

		return fetchByPrimaryKey((Serializable)commerceVirtualOrderItemId);
	}

	/**
	 * Returns all the commerce virtual order items.
	 *
	 * @return the commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce virtual order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @return the range of commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce virtual order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce virtual order items
	 */
	@Override
	public List<CommerceVirtualOrderItem> findAll(
		int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator,
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

		List<CommerceVirtualOrderItem> list = null;

		if (useFinderCache) {
			list = (List<CommerceVirtualOrderItem>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEVIRTUALORDERITEM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEVIRTUALORDERITEM;

				sql = sql.concat(
					CommerceVirtualOrderItemModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceVirtualOrderItem>)QueryUtil.list(
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
	 * Removes all the commerce virtual order items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceVirtualOrderItem commerceVirtualOrderItem : findAll()) {
			remove(commerceVirtualOrderItem);
		}
	}

	/**
	 * Returns the number of commerce virtual order items.
	 *
	 * @return the number of commerce virtual order items
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
					_SQL_COUNT_COMMERCEVIRTUALORDERITEM);

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
		return "commerceVirtualOrderItemId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEVIRTUALORDERITEM;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceVirtualOrderItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce virtual order item persistence.
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

		_finderPathFetchByCommerceOrderItemId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCommerceOrderItemId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderItemId"}, true);

		_finderPathCountByCommerceOrderItemId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderItemId", new String[] {Long.class.getName()},
			new String[] {"commerceOrderItemId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceVirtualOrderItemImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEVIRTUALORDERITEM =
		"SELECT commerceVirtualOrderItem FROM CommerceVirtualOrderItem commerceVirtualOrderItem";

	private static final String _SQL_SELECT_COMMERCEVIRTUALORDERITEM_WHERE =
		"SELECT commerceVirtualOrderItem FROM CommerceVirtualOrderItem commerceVirtualOrderItem WHERE ";

	private static final String _SQL_COUNT_COMMERCEVIRTUALORDERITEM =
		"SELECT COUNT(commerceVirtualOrderItem) FROM CommerceVirtualOrderItem commerceVirtualOrderItem";

	private static final String _SQL_COUNT_COMMERCEVIRTUALORDERITEM_WHERE =
		"SELECT COUNT(commerceVirtualOrderItem) FROM CommerceVirtualOrderItem commerceVirtualOrderItem WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceVirtualOrderItem.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceVirtualOrderItem exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceVirtualOrderItem exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceVirtualOrderItemPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}