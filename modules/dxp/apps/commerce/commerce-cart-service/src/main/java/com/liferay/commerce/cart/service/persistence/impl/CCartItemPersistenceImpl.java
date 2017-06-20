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

import com.liferay.commerce.cart.exception.NoSuchCCartItemException;
import com.liferay.commerce.cart.model.CCartItem;
import com.liferay.commerce.cart.model.impl.CCartItemImpl;
import com.liferay.commerce.cart.model.impl.CCartItemModelImpl;
import com.liferay.commerce.cart.service.persistence.CCartItemPersistence;

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
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
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
 * The persistence implementation for the c cart item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CCartItemPersistence
 * @see com.liferay.commerce.cart.service.persistence.CCartItemUtil
 * @generated
 */
@ProviderType
public class CCartItemPersistenceImpl extends BasePersistenceImpl<CCartItem>
	implements CCartItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CCartItemUtil} to access the c cart item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CCartItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CCartItemModelImpl.UUID_COLUMN_BITMASK |
			CCartItemModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the c cart items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c cart items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @return the range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the c cart items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid(String uuid, int start, int end,
		OrderByComparator<CCartItem> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c cart items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid(String uuid, int start, int end,
		OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<CCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CCartItem cCartItem : list) {
					if (!Objects.equals(uuid, cCartItem.getUuid())) {
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

			query.append(_SQL_SELECT_CCARTITEM_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CCartItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first c cart item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c cart item
	 * @throws NoSuchCCartItemException if a matching c cart item could not be found
	 */
	@Override
	public CCartItem findByUuid_First(String uuid,
		OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByUuid_First(uuid, orderByComparator);

		if (cCartItem != null) {
			return cCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCCartItemException(msg.toString());
	}

	/**
	 * Returns the first c cart item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByUuid_First(String uuid,
		OrderByComparator<CCartItem> orderByComparator) {
		List<CCartItem> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c cart item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c cart item
	 * @throws NoSuchCCartItemException if a matching c cart item could not be found
	 */
	@Override
	public CCartItem findByUuid_Last(String uuid,
		OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByUuid_Last(uuid, orderByComparator);

		if (cCartItem != null) {
			return cCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCCartItemException(msg.toString());
	}

	/**
	 * Returns the last c cart item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByUuid_Last(String uuid,
		OrderByComparator<CCartItem> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CCartItem> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c cart items before and after the current c cart item in the ordered set where uuid = &#63;.
	 *
	 * @param CCartItemId the primary key of the current c cart item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c cart item
	 * @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem[] findByUuid_PrevAndNext(long CCartItemId, String uuid,
		OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = findByPrimaryKey(CCartItemId);

		Session session = null;

		try {
			session = openSession();

			CCartItem[] array = new CCartItemImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cCartItem, uuid,
					orderByComparator, true);

			array[1] = cCartItem;

			array[2] = getByUuid_PrevAndNext(session, cCartItem, uuid,
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

	protected CCartItem getByUuid_PrevAndNext(Session session,
		CCartItem cCartItem, String uuid,
		OrderByComparator<CCartItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CCARTITEM_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			query.append(CCartItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cCartItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CCartItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c cart items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CCartItem cCartItem : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cCartItem);
		}
	}

	/**
	 * Returns the number of c cart items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching c cart items
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CCARTITEM_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cCartItem.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cCartItem.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cCartItem.uuid IS NULL OR cCartItem.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CCartItemModelImpl.UUID_COLUMN_BITMASK |
			CCartItemModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the c cart item where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCCartItemException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching c cart item
	 * @throws NoSuchCCartItemException if a matching c cart item could not be found
	 */
	@Override
	public CCartItem findByUUID_G(String uuid, long groupId)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByUUID_G(uuid, groupId);

		if (cCartItem == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCCartItemException(msg.toString());
		}

		return cCartItem;
	}

	/**
	 * Returns the c cart item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the c cart item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CCartItem) {
			CCartItem cCartItem = (CCartItem)result;

			if (!Objects.equals(uuid, cCartItem.getUuid()) ||
					(groupId != cCartItem.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CCARTITEM_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<CCartItem> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CCartItem cCartItem = list.get(0);

					result = cCartItem;

					cacheResult(cCartItem);

					if ((cCartItem.getUuid() == null) ||
							!cCartItem.getUuid().equals(uuid) ||
							(cCartItem.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cCartItem);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
			return (CCartItem)result;
		}
	}

	/**
	 * Removes the c cart item where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the c cart item that was removed
	 */
	@Override
	public CCartItem removeByUUID_G(String uuid, long groupId)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = findByUUID_G(uuid, groupId);

		return remove(cCartItem);
	}

	/**
	 * Returns the number of c cart items where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching c cart items
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CCARTITEM_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cCartItem.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cCartItem.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cCartItem.uuid IS NULL OR cCartItem.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cCartItem.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CCartItemModelImpl.UUID_COLUMN_BITMASK |
			CCartItemModelImpl.COMPANYID_COLUMN_BITMASK |
			CCartItemModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the c cart items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c cart items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @return the range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the c cart items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<CCartItem> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c cart items where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<CCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CCartItem cCartItem : list) {
					if (!Objects.equals(uuid, cCartItem.getUuid()) ||
							(companyId != cCartItem.getCompanyId())) {
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

			query.append(_SQL_SELECT_CCARTITEM_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CCartItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c cart item
	 * @throws NoSuchCCartItemException if a matching c cart item could not be found
	 */
	@Override
	public CCartItem findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (cCartItem != null) {
			return cCartItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCCartItemException(msg.toString());
	}

	/**
	 * Returns the first c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CCartItem> orderByComparator) {
		List<CCartItem> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c cart item
	 * @throws NoSuchCCartItemException if a matching c cart item could not be found
	 */
	@Override
	public CCartItem findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (cCartItem != null) {
			return cCartItem;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCCartItemException(msg.toString());
	}

	/**
	 * Returns the last c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CCartItem> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CCartItem> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c cart items before and after the current c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CCartItemId the primary key of the current c cart item
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c cart item
	 * @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem[] findByUuid_C_PrevAndNext(long CCartItemId, String uuid,
		long companyId, OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = findByPrimaryKey(CCartItemId);

		Session session = null;

		try {
			session = openSession();

			CCartItem[] array = new CCartItemImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cCartItem, uuid,
					companyId, orderByComparator, true);

			array[1] = cCartItem;

			array[2] = getByUuid_C_PrevAndNext(session, cCartItem, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CCartItem getByUuid_C_PrevAndNext(Session session,
		CCartItem cCartItem, String uuid, long companyId,
		OrderByComparator<CCartItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CCARTITEM_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(CCartItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cCartItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CCartItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c cart items where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CCartItem cCartItem : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cCartItem);
		}
	}

	/**
	 * Returns the number of c cart items where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching c cart items
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CCARTITEM_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cCartItem.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cCartItem.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cCartItem.uuid IS NULL OR cCartItem.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cCartItem.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CCARTID = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCCartId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CCARTID =
		new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, CCartItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCCartId",
			new String[] { Long.class.getName() },
			CCartItemModelImpl.CCARTID_COLUMN_BITMASK |
			CCartItemModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CCARTID = new FinderPath(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCCartId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the c cart items where CCartId = &#63;.
	 *
	 * @param CCartId the c cart ID
	 * @return the matching c cart items
	 */
	@Override
	public List<CCartItem> findByCCartId(long CCartId) {
		return findByCCartId(CCartId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c cart items where CCartId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CCartId the c cart ID
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @return the range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByCCartId(long CCartId, int start, int end) {
		return findByCCartId(CCartId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the c cart items where CCartId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CCartId the c cart ID
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByCCartId(long CCartId, int start, int end,
		OrderByComparator<CCartItem> orderByComparator) {
		return findByCCartId(CCartId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c cart items where CCartId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CCartId the c cart ID
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c cart items
	 */
	@Override
	public List<CCartItem> findByCCartId(long CCartId, int start, int end,
		OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CCARTID;
			finderArgs = new Object[] { CCartId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CCARTID;
			finderArgs = new Object[] { CCartId, start, end, orderByComparator };
		}

		List<CCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CCartItem cCartItem : list) {
					if ((CCartId != cCartItem.getCCartId())) {
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

			query.append(_SQL_SELECT_CCARTITEM_WHERE);

			query.append(_FINDER_COLUMN_CCARTID_CCARTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CCartItemModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CCartId);

				if (!pagination) {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first c cart item in the ordered set where CCartId = &#63;.
	 *
	 * @param CCartId the c cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c cart item
	 * @throws NoSuchCCartItemException if a matching c cart item could not be found
	 */
	@Override
	public CCartItem findByCCartId_First(long CCartId,
		OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByCCartId_First(CCartId, orderByComparator);

		if (cCartItem != null) {
			return cCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CCartId=");
		msg.append(CCartId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCCartItemException(msg.toString());
	}

	/**
	 * Returns the first c cart item in the ordered set where CCartId = &#63;.
	 *
	 * @param CCartId the c cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByCCartId_First(long CCartId,
		OrderByComparator<CCartItem> orderByComparator) {
		List<CCartItem> list = findByCCartId(CCartId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c cart item in the ordered set where CCartId = &#63;.
	 *
	 * @param CCartId the c cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c cart item
	 * @throws NoSuchCCartItemException if a matching c cart item could not be found
	 */
	@Override
	public CCartItem findByCCartId_Last(long CCartId,
		OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByCCartId_Last(CCartId, orderByComparator);

		if (cCartItem != null) {
			return cCartItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CCartId=");
		msg.append(CCartId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCCartItemException(msg.toString());
	}

	/**
	 * Returns the last c cart item in the ordered set where CCartId = &#63;.
	 *
	 * @param CCartId the c cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	 */
	@Override
	public CCartItem fetchByCCartId_Last(long CCartId,
		OrderByComparator<CCartItem> orderByComparator) {
		int count = countByCCartId(CCartId);

		if (count == 0) {
			return null;
		}

		List<CCartItem> list = findByCCartId(CCartId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c cart items before and after the current c cart item in the ordered set where CCartId = &#63;.
	 *
	 * @param CCartItemId the primary key of the current c cart item
	 * @param CCartId the c cart ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c cart item
	 * @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem[] findByCCartId_PrevAndNext(long CCartItemId,
		long CCartId, OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = findByPrimaryKey(CCartItemId);

		Session session = null;

		try {
			session = openSession();

			CCartItem[] array = new CCartItemImpl[3];

			array[0] = getByCCartId_PrevAndNext(session, cCartItem, CCartId,
					orderByComparator, true);

			array[1] = cCartItem;

			array[2] = getByCCartId_PrevAndNext(session, cCartItem, CCartId,
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

	protected CCartItem getByCCartId_PrevAndNext(Session session,
		CCartItem cCartItem, long CCartId,
		OrderByComparator<CCartItem> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CCARTITEM_WHERE);

		query.append(_FINDER_COLUMN_CCARTID_CCARTID_2);

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
			query.append(CCartItemModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CCartId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cCartItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CCartItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c cart items where CCartId = &#63; from the database.
	 *
	 * @param CCartId the c cart ID
	 */
	@Override
	public void removeByCCartId(long CCartId) {
		for (CCartItem cCartItem : findByCCartId(CCartId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cCartItem);
		}
	}

	/**
	 * Returns the number of c cart items where CCartId = &#63;.
	 *
	 * @param CCartId the c cart ID
	 * @return the number of matching c cart items
	 */
	@Override
	public int countByCCartId(long CCartId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CCARTID;

		Object[] finderArgs = new Object[] { CCartId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CCARTITEM_WHERE);

			query.append(_FINDER_COLUMN_CCARTID_CCARTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CCartId);

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

	private static final String _FINDER_COLUMN_CCARTID_CCARTID_2 = "cCartItem.CCartId = ?";

	public CCartItemPersistenceImpl() {
		setModelClass(CCartItem.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the c cart item in the entity cache if it is enabled.
	 *
	 * @param cCartItem the c cart item
	 */
	@Override
	public void cacheResult(CCartItem cCartItem) {
		entityCache.putResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemImpl.class, cCartItem.getPrimaryKey(), cCartItem);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { cCartItem.getUuid(), cCartItem.getGroupId() },
			cCartItem);

		cCartItem.resetOriginalValues();
	}

	/**
	 * Caches the c cart items in the entity cache if it is enabled.
	 *
	 * @param cCartItems the c cart items
	 */
	@Override
	public void cacheResult(List<CCartItem> cCartItems) {
		for (CCartItem cCartItem : cCartItems) {
			if (entityCache.getResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
						CCartItemImpl.class, cCartItem.getPrimaryKey()) == null) {
				cacheResult(cCartItem);
			}
			else {
				cCartItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all c cart items.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CCartItemImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the c cart item.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CCartItem cCartItem) {
		entityCache.removeResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemImpl.class, cCartItem.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CCartItemModelImpl)cCartItem, true);
	}

	@Override
	public void clearCache(List<CCartItem> cCartItems) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CCartItem cCartItem : cCartItems) {
			entityCache.removeResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
				CCartItemImpl.class, cCartItem.getPrimaryKey());

			clearUniqueFindersCache((CCartItemModelImpl)cCartItem, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CCartItemModelImpl cCartItemModelImpl) {
		Object[] args = new Object[] {
				cCartItemModelImpl.getUuid(), cCartItemModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cCartItemModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CCartItemModelImpl cCartItemModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cCartItemModelImpl.getUuid(),
					cCartItemModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cCartItemModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cCartItemModelImpl.getOriginalUuid(),
					cCartItemModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new c cart item with the primary key. Does not add the c cart item to the database.
	 *
	 * @param CCartItemId the primary key for the new c cart item
	 * @return the new c cart item
	 */
	@Override
	public CCartItem create(long CCartItemId) {
		CCartItem cCartItem = new CCartItemImpl();

		cCartItem.setNew(true);
		cCartItem.setPrimaryKey(CCartItemId);

		String uuid = PortalUUIDUtil.generate();

		cCartItem.setUuid(uuid);

		cCartItem.setCompanyId(companyProvider.getCompanyId());

		return cCartItem;
	}

	/**
	 * Removes the c cart item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CCartItemId the primary key of the c cart item
	 * @return the c cart item that was removed
	 * @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem remove(long CCartItemId) throws NoSuchCCartItemException {
		return remove((Serializable)CCartItemId);
	}

	/**
	 * Removes the c cart item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the c cart item
	 * @return the c cart item that was removed
	 * @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem remove(Serializable primaryKey)
		throws NoSuchCCartItemException {
		Session session = null;

		try {
			session = openSession();

			CCartItem cCartItem = (CCartItem)session.get(CCartItemImpl.class,
					primaryKey);

			if (cCartItem == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCCartItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cCartItem);
		}
		catch (NoSuchCCartItemException nsee) {
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
	protected CCartItem removeImpl(CCartItem cCartItem) {
		cCartItem = toUnwrappedModel(cCartItem);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cCartItem)) {
				cCartItem = (CCartItem)session.get(CCartItemImpl.class,
						cCartItem.getPrimaryKeyObj());
			}

			if (cCartItem != null) {
				session.delete(cCartItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cCartItem != null) {
			clearCache(cCartItem);
		}

		return cCartItem;
	}

	@Override
	public CCartItem updateImpl(CCartItem cCartItem) {
		cCartItem = toUnwrappedModel(cCartItem);

		boolean isNew = cCartItem.isNew();

		CCartItemModelImpl cCartItemModelImpl = (CCartItemModelImpl)cCartItem;

		if (Validator.isNull(cCartItem.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cCartItem.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cCartItem.getCreateDate() == null)) {
			if (serviceContext == null) {
				cCartItem.setCreateDate(now);
			}
			else {
				cCartItem.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cCartItemModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cCartItem.setModifiedDate(now);
			}
			else {
				cCartItem.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cCartItem.isNew()) {
				session.save(cCartItem);

				cCartItem.setNew(false);
			}
			else {
				cCartItem = (CCartItem)session.merge(cCartItem);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CCartItemModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cCartItemModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cCartItemModelImpl.getUuid(),
					cCartItemModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { cCartItemModelImpl.getCCartId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CCARTID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CCARTID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cCartItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cCartItemModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cCartItemModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cCartItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cCartItemModelImpl.getOriginalUuid(),
						cCartItemModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cCartItemModelImpl.getUuid(),
						cCartItemModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cCartItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CCARTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cCartItemModelImpl.getOriginalCCartId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CCARTID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CCARTID,
					args);

				args = new Object[] { cCartItemModelImpl.getCCartId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CCARTID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CCARTID,
					args);
			}
		}

		entityCache.putResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
			CCartItemImpl.class, cCartItem.getPrimaryKey(), cCartItem, false);

		clearUniqueFindersCache(cCartItemModelImpl, false);
		cacheUniqueFindersCache(cCartItemModelImpl);

		cCartItem.resetOriginalValues();

		return cCartItem;
	}

	protected CCartItem toUnwrappedModel(CCartItem cCartItem) {
		if (cCartItem instanceof CCartItemImpl) {
			return cCartItem;
		}

		CCartItemImpl cCartItemImpl = new CCartItemImpl();

		cCartItemImpl.setNew(cCartItem.isNew());
		cCartItemImpl.setPrimaryKey(cCartItem.getPrimaryKey());

		cCartItemImpl.setUuid(cCartItem.getUuid());
		cCartItemImpl.setCCartItemId(cCartItem.getCCartItemId());
		cCartItemImpl.setGroupId(cCartItem.getGroupId());
		cCartItemImpl.setCompanyId(cCartItem.getCompanyId());
		cCartItemImpl.setUserId(cCartItem.getUserId());
		cCartItemImpl.setUserName(cCartItem.getUserName());
		cCartItemImpl.setCreateDate(cCartItem.getCreateDate());
		cCartItemImpl.setModifiedDate(cCartItem.getModifiedDate());
		cCartItemImpl.setCCartId(cCartItem.getCCartId());
		cCartItemImpl.setCPDefinitionId(cCartItem.getCPDefinitionId());
		cCartItemImpl.setCPInstanceId(cCartItem.getCPInstanceId());
		cCartItemImpl.setQuantity(cCartItem.getQuantity());
		cCartItemImpl.setJson(cCartItem.getJson());

		return cCartItemImpl;
	}

	/**
	 * Returns the c cart item with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the c cart item
	 * @return the c cart item
	 * @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCCartItemException {
		CCartItem cCartItem = fetchByPrimaryKey(primaryKey);

		if (cCartItem == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCCartItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cCartItem;
	}

	/**
	 * Returns the c cart item with the primary key or throws a {@link NoSuchCCartItemException} if it could not be found.
	 *
	 * @param CCartItemId the primary key of the c cart item
	 * @return the c cart item
	 * @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem findByPrimaryKey(long CCartItemId)
		throws NoSuchCCartItemException {
		return findByPrimaryKey((Serializable)CCartItemId);
	}

	/**
	 * Returns the c cart item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the c cart item
	 * @return the c cart item, or <code>null</code> if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
				CCartItemImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CCartItem cCartItem = (CCartItem)serializable;

		if (cCartItem == null) {
			Session session = null;

			try {
				session = openSession();

				cCartItem = (CCartItem)session.get(CCartItemImpl.class,
						primaryKey);

				if (cCartItem != null) {
					cacheResult(cCartItem);
				}
				else {
					entityCache.putResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
						CCartItemImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
					CCartItemImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cCartItem;
	}

	/**
	 * Returns the c cart item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CCartItemId the primary key of the c cart item
	 * @return the c cart item, or <code>null</code> if a c cart item with the primary key could not be found
	 */
	@Override
	public CCartItem fetchByPrimaryKey(long CCartItemId) {
		return fetchByPrimaryKey((Serializable)CCartItemId);
	}

	@Override
	public Map<Serializable, CCartItem> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CCartItem> map = new HashMap<Serializable, CCartItem>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CCartItem cCartItem = fetchByPrimaryKey(primaryKey);

			if (cCartItem != null) {
				map.put(primaryKey, cCartItem);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
					CCartItemImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CCartItem)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CCARTITEM_WHERE_PKS_IN);

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

			for (CCartItem cCartItem : (List<CCartItem>)q.list()) {
				map.put(cCartItem.getPrimaryKeyObj(), cCartItem);

				cacheResult(cCartItem);

				uncachedPrimaryKeys.remove(cCartItem.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CCartItemModelImpl.ENTITY_CACHE_ENABLED,
					CCartItemImpl.class, primaryKey, nullModel);
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
	 * Returns all the c cart items.
	 *
	 * @return the c cart items
	 */
	@Override
	public List<CCartItem> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c cart items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @return the range of c cart items
	 */
	@Override
	public List<CCartItem> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the c cart items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of c cart items
	 */
	@Override
	public List<CCartItem> findAll(int start, int end,
		OrderByComparator<CCartItem> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c cart items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c cart items
	 * @param end the upper bound of the range of c cart items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of c cart items
	 */
	@Override
	public List<CCartItem> findAll(int start, int end,
		OrderByComparator<CCartItem> orderByComparator,
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

		List<CCartItem> list = null;

		if (retrieveFromCache) {
			list = (List<CCartItem>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CCARTITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CCARTITEM;

				if (pagination) {
					sql = sql.concat(CCartItemModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CCartItem>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the c cart items from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CCartItem cCartItem : findAll()) {
			remove(cCartItem);
		}
	}

	/**
	 * Returns the number of c cart items.
	 *
	 * @return the number of c cart items
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CCARTITEM);

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
		return CCartItemModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the c cart item persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CCartItemImpl.class.getName());
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
	private static final String _SQL_SELECT_CCARTITEM = "SELECT cCartItem FROM CCartItem cCartItem";
	private static final String _SQL_SELECT_CCARTITEM_WHERE_PKS_IN = "SELECT cCartItem FROM CCartItem cCartItem WHERE CCartItemId IN (";
	private static final String _SQL_SELECT_CCARTITEM_WHERE = "SELECT cCartItem FROM CCartItem cCartItem WHERE ";
	private static final String _SQL_COUNT_CCARTITEM = "SELECT COUNT(cCartItem) FROM CCartItem cCartItem";
	private static final String _SQL_COUNT_CCARTITEM_WHERE = "SELECT COUNT(cCartItem) FROM CCartItem cCartItem WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cCartItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CCartItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CCartItem exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CCartItemPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}