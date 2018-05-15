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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.impl.CPDisplayLayoutImpl;
import com.liferay.commerce.product.model.impl.CPDisplayLayoutModelImpl;
import com.liferay.commerce.product.service.persistence.CPDisplayLayoutPersistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
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
 * The persistence implementation for the cp display layout service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDisplayLayoutPersistence
 * @see com.liferay.commerce.product.service.persistence.CPDisplayLayoutUtil
 * @generated
 */
@ProviderType
public class CPDisplayLayoutPersistenceImpl extends BasePersistenceImpl<CPDisplayLayout>
	implements CPDisplayLayoutPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDisplayLayoutUtil} to access the cp display layout persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDisplayLayoutImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPDisplayLayoutModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp display layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp display layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
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

		List<CPDisplayLayout> list = null;

		if (retrieveFromCache) {
			list = (List<CPDisplayLayout>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDisplayLayout cpDisplayLayout : list) {
					if (!Objects.equals(uuid, cpDisplayLayout.getUuid())) {
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

			query.append(_SQL_SELECT_CPDISPLAYLAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CPDisplayLayoutModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDisplayLayout>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDisplayLayout>)QueryUtil.list(q,
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
	 * Returns the first cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout findByUuid_First(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpDisplayLayout != null) {
			return cpDisplayLayout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDisplayLayoutException(msg.toString());
	}

	/**
	 * Returns the first cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByUuid_First(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		List<CPDisplayLayout> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout findByUuid_Last(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpDisplayLayout != null) {
			return cpDisplayLayout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDisplayLayoutException(msg.toString());
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByUuid_Last(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDisplayLayout> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where uuid = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout[] findByUuid_PrevAndNext(long CPDisplayLayoutId,
		String uuid, OrderByComparator<CPDisplayLayout> orderByComparator)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = findByPrimaryKey(CPDisplayLayoutId);

		Session session = null;

		try {
			session = openSession();

			CPDisplayLayout[] array = new CPDisplayLayoutImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpDisplayLayout, uuid,
					orderByComparator, true);

			array[1] = cpDisplayLayout;

			array[2] = getByUuid_PrevAndNext(session, cpDisplayLayout, uuid,
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

	protected CPDisplayLayout getByUuid_PrevAndNext(Session session,
		CPDisplayLayout cpDisplayLayout, String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPDISPLAYLAYOUT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CPDisplayLayoutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDisplayLayout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDisplayLayout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp display layouts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDisplayLayout cpDisplayLayout : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDisplayLayout);
		}
	}

	/**
	 * Returns the number of cp display layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp display layouts
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDISPLAYLAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpDisplayLayout.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpDisplayLayout.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpDisplayLayout.uuid IS NULL OR cpDisplayLayout.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDisplayLayoutModelImpl.UUID_COLUMN_BITMASK |
			CPDisplayLayoutModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp display layout where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDisplayLayoutException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = fetchByUUID_G(uuid, groupId);

		if (cpDisplayLayout == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDisplayLayoutException(msg.toString());
		}

		return cpDisplayLayout;
	}

	/**
	 * Returns the cp display layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp display layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPDisplayLayout) {
			CPDisplayLayout cpDisplayLayout = (CPDisplayLayout)result;

			if (!Objects.equals(uuid, cpDisplayLayout.getUuid()) ||
					(groupId != cpDisplayLayout.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDISPLAYLAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

				List<CPDisplayLayout> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPDisplayLayout cpDisplayLayout = list.get(0);

					result = cpDisplayLayout;

					cacheResult(cpDisplayLayout);

					if ((cpDisplayLayout.getUuid() == null) ||
							!cpDisplayLayout.getUuid().equals(uuid) ||
							(cpDisplayLayout.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpDisplayLayout);
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
			return (CPDisplayLayout)result;
		}
	}

	/**
	 * Removes the cp display layout where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp display layout that was removed
	 */
	@Override
	public CPDisplayLayout removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = findByUUID_G(uuid, groupId);

		return remove(cpDisplayLayout);
	}

	/**
	 * Returns the number of cp display layouts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp display layouts
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDISPLAYLAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpDisplayLayout.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpDisplayLayout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpDisplayLayout.uuid IS NULL OR cpDisplayLayout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpDisplayLayout.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDisplayLayoutModelImpl.UUID_COLUMN_BITMASK |
			CPDisplayLayoutModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CPDisplayLayout> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
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

		List<CPDisplayLayout> list = null;

		if (retrieveFromCache) {
			list = (List<CPDisplayLayout>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDisplayLayout cpDisplayLayout : list) {
					if (!Objects.equals(uuid, cpDisplayLayout.getUuid()) ||
							(companyId != cpDisplayLayout.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDISPLAYLAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CPDisplayLayoutModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDisplayLayout>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDisplayLayout>)QueryUtil.list(q,
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
	 * Returns the first cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (cpDisplayLayout != null) {
			return cpDisplayLayout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDisplayLayoutException(msg.toString());
	}

	/**
	 * Returns the first cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		List<CPDisplayLayout> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (cpDisplayLayout != null) {
			return cpDisplayLayout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDisplayLayoutException(msg.toString());
	}

	/**
	 * Returns the last cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDisplayLayout> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp display layouts before and after the current cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDisplayLayoutId the primary key of the current cp display layout
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout[] findByUuid_C_PrevAndNext(long CPDisplayLayoutId,
		String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = findByPrimaryKey(CPDisplayLayoutId);

		Session session = null;

		try {
			session = openSession();

			CPDisplayLayout[] array = new CPDisplayLayoutImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpDisplayLayout, uuid,
					companyId, orderByComparator, true);

			array[1] = cpDisplayLayout;

			array[2] = getByUuid_C_PrevAndNext(session, cpDisplayLayout, uuid,
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

	protected CPDisplayLayout getByUuid_C_PrevAndNext(Session session,
		CPDisplayLayout cpDisplayLayout, String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CPDISPLAYLAYOUT_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CPDisplayLayoutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDisplayLayout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDisplayLayout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp display layouts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDisplayLayout cpDisplayLayout : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDisplayLayout);
		}
	}

	/**
	 * Returns the number of cp display layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp display layouts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDISPLAYLAYOUT_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpDisplayLayout.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpDisplayLayout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpDisplayLayout.uuid IS NULL OR cpDisplayLayout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpDisplayLayout.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CPDisplayLayoutModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPDisplayLayoutModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp display layout where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchCPDisplayLayoutException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout findByC_C(long classNameId, long classPK)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = fetchByC_C(classNameId, classPK);

		if (cpDisplayLayout == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDisplayLayoutException(msg.toString());
		}

		return cpDisplayLayout;
	}

	/**
	 * Returns the cp display layout where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the cp display layout where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	 */
	@Override
	public CPDisplayLayout fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof CPDisplayLayout) {
			CPDisplayLayout cpDisplayLayout = (CPDisplayLayout)result;

			if ((classNameId != cpDisplayLayout.getClassNameId()) ||
					(classPK != cpDisplayLayout.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDISPLAYLAYOUT_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<CPDisplayLayout> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					CPDisplayLayout cpDisplayLayout = list.get(0);

					result = cpDisplayLayout;

					cacheResult(cpDisplayLayout);

					if ((cpDisplayLayout.getClassNameId() != classNameId) ||
							(cpDisplayLayout.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, cpDisplayLayout);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, finderArgs);

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
			return (CPDisplayLayout)result;
		}
	}

	/**
	 * Removes the cp display layout where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the cp display layout that was removed
	 */
	@Override
	public CPDisplayLayout removeByC_C(long classNameId, long classPK)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = findByC_C(classNameId, classPK);

		return remove(cpDisplayLayout);
	}

	/**
	 * Returns the number of cp display layouts where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp display layouts
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDISPLAYLAYOUT_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "cpDisplayLayout.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "cpDisplayLayout.classPK = ?";

	public CPDisplayLayoutPersistenceImpl() {
		setModelClass(CPDisplayLayout.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

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
	 * Caches the cp display layout in the entity cache if it is enabled.
	 *
	 * @param cpDisplayLayout the cp display layout
	 */
	@Override
	public void cacheResult(CPDisplayLayout cpDisplayLayout) {
		entityCache.putResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, cpDisplayLayout.getPrimaryKey(),
			cpDisplayLayout);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { cpDisplayLayout.getUuid(), cpDisplayLayout.getGroupId() },
			cpDisplayLayout);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				cpDisplayLayout.getClassNameId(), cpDisplayLayout.getClassPK()
			}, cpDisplayLayout);

		cpDisplayLayout.resetOriginalValues();
	}

	/**
	 * Caches the cp display layouts in the entity cache if it is enabled.
	 *
	 * @param cpDisplayLayouts the cp display layouts
	 */
	@Override
	public void cacheResult(List<CPDisplayLayout> cpDisplayLayouts) {
		for (CPDisplayLayout cpDisplayLayout : cpDisplayLayouts) {
			if (entityCache.getResult(
						CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
						CPDisplayLayoutImpl.class,
						cpDisplayLayout.getPrimaryKey()) == null) {
				cacheResult(cpDisplayLayout);
			}
			else {
				cpDisplayLayout.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp display layouts.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDisplayLayoutImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp display layout.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDisplayLayout cpDisplayLayout) {
		entityCache.removeResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, cpDisplayLayout.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDisplayLayoutModelImpl)cpDisplayLayout, true);
	}

	@Override
	public void clearCache(List<CPDisplayLayout> cpDisplayLayouts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDisplayLayout cpDisplayLayout : cpDisplayLayouts) {
			entityCache.removeResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
				CPDisplayLayoutImpl.class, cpDisplayLayout.getPrimaryKey());

			clearUniqueFindersCache((CPDisplayLayoutModelImpl)cpDisplayLayout,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDisplayLayoutModelImpl cpDisplayLayoutModelImpl) {
		Object[] args = new Object[] {
				cpDisplayLayoutModelImpl.getUuid(),
				cpDisplayLayoutModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpDisplayLayoutModelImpl, false);

		args = new Object[] {
				cpDisplayLayoutModelImpl.getClassNameId(),
				cpDisplayLayoutModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
			cpDisplayLayoutModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDisplayLayoutModelImpl cpDisplayLayoutModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDisplayLayoutModelImpl.getUuid(),
					cpDisplayLayoutModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpDisplayLayoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDisplayLayoutModelImpl.getOriginalUuid(),
					cpDisplayLayoutModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDisplayLayoutModelImpl.getClassNameId(),
					cpDisplayLayoutModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}

		if ((cpDisplayLayoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDisplayLayoutModelImpl.getOriginalClassNameId(),
					cpDisplayLayoutModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new cp display layout with the primary key. Does not add the cp display layout to the database.
	 *
	 * @param CPDisplayLayoutId the primary key for the new cp display layout
	 * @return the new cp display layout
	 */
	@Override
	public CPDisplayLayout create(long CPDisplayLayoutId) {
		CPDisplayLayout cpDisplayLayout = new CPDisplayLayoutImpl();

		cpDisplayLayout.setNew(true);
		cpDisplayLayout.setPrimaryKey(CPDisplayLayoutId);

		String uuid = PortalUUIDUtil.generate();

		cpDisplayLayout.setUuid(uuid);

		cpDisplayLayout.setCompanyId(companyProvider.getCompanyId());

		return cpDisplayLayout;
	}

	/**
	 * Removes the cp display layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout that was removed
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout remove(long CPDisplayLayoutId)
		throws NoSuchCPDisplayLayoutException {
		return remove((Serializable)CPDisplayLayoutId);
	}

	/**
	 * Removes the cp display layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp display layout
	 * @return the cp display layout that was removed
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout remove(Serializable primaryKey)
		throws NoSuchCPDisplayLayoutException {
		Session session = null;

		try {
			session = openSession();

			CPDisplayLayout cpDisplayLayout = (CPDisplayLayout)session.get(CPDisplayLayoutImpl.class,
					primaryKey);

			if (cpDisplayLayout == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDisplayLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDisplayLayout);
		}
		catch (NoSuchCPDisplayLayoutException nsee) {
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
	protected CPDisplayLayout removeImpl(CPDisplayLayout cpDisplayLayout) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDisplayLayout)) {
				cpDisplayLayout = (CPDisplayLayout)session.get(CPDisplayLayoutImpl.class,
						cpDisplayLayout.getPrimaryKeyObj());
			}

			if (cpDisplayLayout != null) {
				session.delete(cpDisplayLayout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDisplayLayout != null) {
			clearCache(cpDisplayLayout);
		}

		return cpDisplayLayout;
	}

	@Override
	public CPDisplayLayout updateImpl(CPDisplayLayout cpDisplayLayout) {
		boolean isNew = cpDisplayLayout.isNew();

		if (!(cpDisplayLayout instanceof CPDisplayLayoutModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDisplayLayout.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpDisplayLayout);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDisplayLayout proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDisplayLayout implementation " +
				cpDisplayLayout.getClass());
		}

		CPDisplayLayoutModelImpl cpDisplayLayoutModelImpl = (CPDisplayLayoutModelImpl)cpDisplayLayout;

		if (Validator.isNull(cpDisplayLayout.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDisplayLayout.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDisplayLayout.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDisplayLayout.setCreateDate(now);
			}
			else {
				cpDisplayLayout.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cpDisplayLayoutModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDisplayLayout.setModifiedDate(now);
			}
			else {
				cpDisplayLayout.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpDisplayLayout.isNew()) {
				session.save(cpDisplayLayout);

				cpDisplayLayout.setNew(false);
			}
			else {
				cpDisplayLayout = (CPDisplayLayout)session.merge(cpDisplayLayout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDisplayLayoutModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cpDisplayLayoutModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpDisplayLayoutModelImpl.getUuid(),
					cpDisplayLayoutModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDisplayLayoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDisplayLayoutModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpDisplayLayoutModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpDisplayLayoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDisplayLayoutModelImpl.getOriginalUuid(),
						cpDisplayLayoutModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpDisplayLayoutModelImpl.getUuid(),
						cpDisplayLayoutModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		entityCache.putResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
			CPDisplayLayoutImpl.class, cpDisplayLayout.getPrimaryKey(),
			cpDisplayLayout, false);

		clearUniqueFindersCache(cpDisplayLayoutModelImpl, false);
		cacheUniqueFindersCache(cpDisplayLayoutModelImpl);

		cpDisplayLayout.resetOriginalValues();

		return cpDisplayLayout;
	}

	/**
	 * Returns the cp display layout with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp display layout
	 * @return the cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDisplayLayoutException {
		CPDisplayLayout cpDisplayLayout = fetchByPrimaryKey(primaryKey);

		if (cpDisplayLayout == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDisplayLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDisplayLayout;
	}

	/**
	 * Returns the cp display layout with the primary key or throws a {@link NoSuchCPDisplayLayoutException} if it could not be found.
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout
	 * @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout findByPrimaryKey(long CPDisplayLayoutId)
		throws NoSuchCPDisplayLayoutException {
		return findByPrimaryKey((Serializable)CPDisplayLayoutId);
	}

	/**
	 * Returns the cp display layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp display layout
	 * @return the cp display layout, or <code>null</code> if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
				CPDisplayLayoutImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDisplayLayout cpDisplayLayout = (CPDisplayLayout)serializable;

		if (cpDisplayLayout == null) {
			Session session = null;

			try {
				session = openSession();

				cpDisplayLayout = (CPDisplayLayout)session.get(CPDisplayLayoutImpl.class,
						primaryKey);

				if (cpDisplayLayout != null) {
					cacheResult(cpDisplayLayout);
				}
				else {
					entityCache.putResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
						CPDisplayLayoutImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
					CPDisplayLayoutImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDisplayLayout;
	}

	/**
	 * Returns the cp display layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDisplayLayoutId the primary key of the cp display layout
	 * @return the cp display layout, or <code>null</code> if a cp display layout with the primary key could not be found
	 */
	@Override
	public CPDisplayLayout fetchByPrimaryKey(long CPDisplayLayoutId) {
		return fetchByPrimaryKey((Serializable)CPDisplayLayoutId);
	}

	@Override
	public Map<Serializable, CPDisplayLayout> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDisplayLayout> map = new HashMap<Serializable, CPDisplayLayout>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDisplayLayout cpDisplayLayout = fetchByPrimaryKey(primaryKey);

			if (cpDisplayLayout != null) {
				map.put(primaryKey, cpDisplayLayout);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
					CPDisplayLayoutImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDisplayLayout)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDISPLAYLAYOUT_WHERE_PKS_IN);

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

			for (CPDisplayLayout cpDisplayLayout : (List<CPDisplayLayout>)q.list()) {
				map.put(cpDisplayLayout.getPrimaryKeyObj(), cpDisplayLayout);

				cacheResult(cpDisplayLayout);

				uncachedPrimaryKeys.remove(cpDisplayLayout.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDisplayLayoutModelImpl.ENTITY_CACHE_ENABLED,
					CPDisplayLayoutImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp display layouts.
	 *
	 * @return the cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp display layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @return the range of cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp display layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findAll(int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp display layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp display layouts
	 * @param end the upper bound of the range of cp display layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp display layouts
	 */
	@Override
	public List<CPDisplayLayout> findAll(int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
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

		List<CPDisplayLayout> list = null;

		if (retrieveFromCache) {
			list = (List<CPDisplayLayout>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDISPLAYLAYOUT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDISPLAYLAYOUT;

				if (pagination) {
					sql = sql.concat(CPDisplayLayoutModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDisplayLayout>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDisplayLayout>)QueryUtil.list(q,
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
	 * Removes all the cp display layouts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDisplayLayout cpDisplayLayout : findAll()) {
			remove(cpDisplayLayout);
		}
	}

	/**
	 * Returns the number of cp display layouts.
	 *
	 * @return the number of cp display layouts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDISPLAYLAYOUT);

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
		return CPDisplayLayoutModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp display layout persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDisplayLayoutImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDISPLAYLAYOUT = "SELECT cpDisplayLayout FROM CPDisplayLayout cpDisplayLayout";
	private static final String _SQL_SELECT_CPDISPLAYLAYOUT_WHERE_PKS_IN = "SELECT cpDisplayLayout FROM CPDisplayLayout cpDisplayLayout WHERE CPDisplayLayoutId IN (";
	private static final String _SQL_SELECT_CPDISPLAYLAYOUT_WHERE = "SELECT cpDisplayLayout FROM CPDisplayLayout cpDisplayLayout WHERE ";
	private static final String _SQL_COUNT_CPDISPLAYLAYOUT = "SELECT COUNT(cpDisplayLayout) FROM CPDisplayLayout cpDisplayLayout";
	private static final String _SQL_COUNT_CPDISPLAYLAYOUT_WHERE = "SELECT COUNT(cpDisplayLayout) FROM CPDisplayLayout cpDisplayLayout WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDisplayLayout.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDisplayLayout exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDisplayLayout exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDisplayLayoutPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}