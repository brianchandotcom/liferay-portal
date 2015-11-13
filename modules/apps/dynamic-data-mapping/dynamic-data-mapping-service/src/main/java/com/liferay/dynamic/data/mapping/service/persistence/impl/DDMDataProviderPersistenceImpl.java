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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;
import com.liferay.dynamic.data.mapping.model.DDMDataProvider;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d d m data provider service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderUtil
 * @generated
 */
@ProviderType
public class DDMDataProviderPersistenceImpl extends BasePersistenceImpl<DDMDataProvider>
	implements DDMDataProviderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMDataProviderUtil} to access the d d m data provider persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMDataProviderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMDataProviderModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m data providers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data providers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @return the range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
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

		List<DDMDataProvider> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProvider>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProvider ddmDataProvider : list) {
					if (!Validator.equals(uuid, ddmDataProvider.getUuid())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

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
				query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMDataProvider>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByUuid_First(String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByUuid_First(uuid,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByUuid_First(String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		List<DDMDataProvider> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByUuid_Last(String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByUuid_Last(uuid,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByUuid_Last(String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMDataProvider> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data providers before and after the current d d m data provider in the ordered set where uuid = &#63;.
	 *
	 * @param dataProviderId the primary key of the current d d m data provider
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider[] findByUuid_PrevAndNext(long dataProviderId,
		String uuid, OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = findByPrimaryKey(dataProviderId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProvider[] array = new DDMDataProviderImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmDataProvider, uuid,
					orderByComparator, true);

			array[1] = ddmDataProvider;

			array[2] = getByUuid_PrevAndNext(session, ddmDataProvider, uuid,
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

	protected DDMDataProvider getByUuid_PrevAndNext(Session session,
		DDMDataProvider ddmDataProvider, String uuid,
		OrderByComparator<DDMDataProvider> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

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
			query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProvider);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProvider> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m data providers where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMDataProvider ddmDataProvider : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProvider);
		}
	}

	/**
	 * Returns the number of d d m data providers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m data providers
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMDATAPROVIDER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmDataProvider.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmDataProvider.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmDataProvider.uuid IS NULL OR ddmDataProvider.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMDataProviderModelImpl.UUID_COLUMN_BITMASK |
			DDMDataProviderModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the d d m data provider where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByUUID_G(String uuid, long groupId)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByUUID_G(uuid, groupId);

		if (ddmDataProvider == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDataProviderException(msg.toString());
		}

		return ddmDataProvider;
	}

	/**
	 * Returns the d d m data provider where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d m data provider where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDMDataProvider) {
			DDMDataProvider ddmDataProvider = (DDMDataProvider)result;

			if (!Validator.equals(uuid, ddmDataProvider.getUuid()) ||
					(groupId != ddmDataProvider.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

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

				List<DDMDataProvider> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DDMDataProvider ddmDataProvider = list.get(0);

					result = ddmDataProvider;

					cacheResult(ddmDataProvider);

					if ((ddmDataProvider.getUuid() == null) ||
							!ddmDataProvider.getUuid().equals(uuid) ||
							(ddmDataProvider.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmDataProvider);
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
			return (DDMDataProvider)result;
		}
	}

	/**
	 * Removes the d d m data provider where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d m data provider that was removed
	 */
	@Override
	public DDMDataProvider removeByUUID_G(String uuid, long groupId)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = findByUUID_G(uuid, groupId);

		return remove(ddmDataProvider);
	}

	/**
	 * Returns the number of d d m data providers where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d m data providers
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMDATAPROVIDER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmDataProvider.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmDataProvider.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmDataProvider.uuid IS NULL OR ddmDataProvider.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmDataProvider.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMDataProviderModelImpl.UUID_COLUMN_BITMASK |
			DDMDataProviderModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m data providers where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @return the range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
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

		List<DDMDataProvider> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProvider>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProvider ddmDataProvider : list) {
					if (!Validator.equals(uuid, ddmDataProvider.getUuid()) ||
							(companyId != ddmDataProvider.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

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
				query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMDataProvider>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		List<DDMDataProvider> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProvider> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data providers before and after the current d d m data provider in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param dataProviderId the primary key of the current d d m data provider
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider[] findByUuid_C_PrevAndNext(long dataProviderId,
		String uuid, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = findByPrimaryKey(dataProviderId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProvider[] array = new DDMDataProviderImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmDataProvider, uuid,
					companyId, orderByComparator, true);

			array[1] = ddmDataProvider;

			array[2] = getByUuid_C_PrevAndNext(session, ddmDataProvider, uuid,
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

	protected DDMDataProvider getByUuid_C_PrevAndNext(Session session,
		DDMDataProvider ddmDataProvider, String uuid, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

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
			query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProvider);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProvider> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m data providers where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMDataProvider ddmDataProvider : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProvider);
		}
	}

	/**
	 * Returns the number of d d m data providers where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m data providers
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMDATAPROVIDER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmDataProvider.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmDataProvider.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmDataProvider.uuid IS NULL OR ddmDataProvider.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmDataProvider.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DDMDataProviderModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m data providers where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data providers where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @return the range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator,
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

		List<DDMDataProvider> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProvider>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProvider ddmDataProvider : list) {
					if ((groupId != ddmDataProvider.getGroupId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByGroupId_First(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByGroupId_First(groupId,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByGroupId_First(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		List<DDMDataProvider> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByGroupId_Last(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByGroupId_Last(long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProvider> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data providers before and after the current d d m data provider in the ordered set where groupId = &#63;.
	 *
	 * @param dataProviderId the primary key of the current d d m data provider
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider[] findByGroupId_PrevAndNext(long dataProviderId,
		long groupId, OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = findByPrimaryKey(dataProviderId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProvider[] array = new DDMDataProviderImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, ddmDataProvider,
					groupId, orderByComparator, true);

			array[1] = ddmDataProvider;

			array[2] = getByGroupId_PrevAndNext(session, ddmDataProvider,
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

	protected DDMDataProvider getByGroupId_PrevAndNext(Session session,
		DDMDataProvider ddmDataProvider, long groupId,
		OrderByComparator<DDMDataProvider> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

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
			query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProvider);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProvider> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m data providers where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long[] groupIds) {
		return findByGroupId(groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m data providers where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @return the range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long[] groupIds, int start,
		int end) {
		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId(groupIds[0], start, end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { StringUtil.merge(groupIds) };
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					
					start, end, orderByComparator
				};
		}

		List<DDMDataProvider> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProvider>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProvider ddmDataProvider : list) {
					if (!ArrayUtil.contains(groupIds,
								ddmDataProvider.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the d d m data providers where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DDMDataProvider ddmDataProvider : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProvider);
		}
	}

	/**
	 * Returns the number of d d m data providers where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m data providers
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMDATAPROVIDER_WHERE);

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

	/**
	 * Returns the number of d d m data providers where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching d d m data providers
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		Object[] finderArgs = new Object[] { StringUtil.merge(groupIds) };

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DDMDATAPROVIDER_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmDataProvider.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 = "ddmDataProvider.groupId IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DDMDataProviderModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m data providers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m data providers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @return the range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data providers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DDMDataProvider> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<DDMDataProvider> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProvider>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProvider ddmDataProvider : list) {
					if ((companyId != ddmDataProvider.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByCompanyId_First(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByCompanyId_First(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		List<DDMDataProvider> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider findByCompanyId_Last(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (ddmDataProvider != null) {
			return ddmDataProvider;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	 */
	@Override
	public DDMDataProvider fetchByCompanyId_Last(long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProvider> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data providers before and after the current d d m data provider in the ordered set where companyId = &#63;.
	 *
	 * @param dataProviderId the primary key of the current d d m data provider
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider[] findByCompanyId_PrevAndNext(long dataProviderId,
		long companyId, OrderByComparator<DDMDataProvider> orderByComparator)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = findByPrimaryKey(dataProviderId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProvider[] array = new DDMDataProviderImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, ddmDataProvider,
					companyId, orderByComparator, true);

			array[1] = ddmDataProvider;

			array[2] = getByCompanyId_PrevAndNext(session, ddmDataProvider,
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

	protected DDMDataProvider getByCompanyId_PrevAndNext(Session session,
		DDMDataProvider ddmDataProvider, long companyId,
		OrderByComparator<DDMDataProvider> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(DDMDataProviderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProvider);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProvider> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m data providers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DDMDataProvider ddmDataProvider : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProvider);
		}
	}

	/**
	 * Returns the number of d d m data providers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching d d m data providers
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMDATAPROVIDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "ddmDataProvider.companyId = ?";

	public DDMDataProviderPersistenceImpl() {
		setModelClass(DDMDataProvider.class);
	}

	/**
	 * Caches the d d m data provider in the entity cache if it is enabled.
	 *
	 * @param ddmDataProvider the d d m data provider
	 */
	@Override
	public void cacheResult(DDMDataProvider ddmDataProvider) {
		entityCache.putResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderImpl.class, ddmDataProvider.getPrimaryKey(),
			ddmDataProvider);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { ddmDataProvider.getUuid(), ddmDataProvider.getGroupId() },
			ddmDataProvider);

		ddmDataProvider.resetOriginalValues();
	}

	/**
	 * Caches the d d m data providers in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviders the d d m data providers
	 */
	@Override
	public void cacheResult(List<DDMDataProvider> ddmDataProviders) {
		for (DDMDataProvider ddmDataProvider : ddmDataProviders) {
			if (entityCache.getResult(
						DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
						DDMDataProviderImpl.class,
						ddmDataProvider.getPrimaryKey()) == null) {
				cacheResult(ddmDataProvider);
			}
			else {
				ddmDataProvider.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m data providers.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMDataProviderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m data provider.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMDataProvider ddmDataProvider) {
		entityCache.removeResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderImpl.class, ddmDataProvider.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMDataProviderModelImpl)ddmDataProvider);
	}

	@Override
	public void clearCache(List<DDMDataProvider> ddmDataProviders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMDataProvider ddmDataProvider : ddmDataProviders) {
			entityCache.removeResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
				DDMDataProviderImpl.class, ddmDataProvider.getPrimaryKey());

			clearUniqueFindersCache((DDMDataProviderModelImpl)ddmDataProvider);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMDataProviderModelImpl ddmDataProviderModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ddmDataProviderModelImpl.getUuid(),
					ddmDataProviderModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				ddmDataProviderModelImpl);
		}
		else {
			if ((ddmDataProviderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderModelImpl.getUuid(),
						ddmDataProviderModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					ddmDataProviderModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMDataProviderModelImpl ddmDataProviderModelImpl) {
		Object[] args = new Object[] {
				ddmDataProviderModelImpl.getUuid(),
				ddmDataProviderModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((ddmDataProviderModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmDataProviderModelImpl.getOriginalUuid(),
					ddmDataProviderModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new d d m data provider with the primary key. Does not add the d d m data provider to the database.
	 *
	 * @param dataProviderId the primary key for the new d d m data provider
	 * @return the new d d m data provider
	 */
	@Override
	public DDMDataProvider create(long dataProviderId) {
		DDMDataProvider ddmDataProvider = new DDMDataProviderImpl();

		ddmDataProvider.setNew(true);
		ddmDataProvider.setPrimaryKey(dataProviderId);

		String uuid = PortalUUIDUtil.generate();

		ddmDataProvider.setUuid(uuid);

		return ddmDataProvider;
	}

	/**
	 * Removes the d d m data provider with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataProviderId the primary key of the d d m data provider
	 * @return the d d m data provider that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider remove(long dataProviderId)
		throws NoSuchDataProviderException {
		return remove((Serializable)dataProviderId);
	}

	/**
	 * Removes the d d m data provider with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m data provider
	 * @return the d d m data provider that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider remove(Serializable primaryKey)
		throws NoSuchDataProviderException {
		Session session = null;

		try {
			session = openSession();

			DDMDataProvider ddmDataProvider = (DDMDataProvider)session.get(DDMDataProviderImpl.class,
					primaryKey);

			if (ddmDataProvider == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDataProviderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmDataProvider);
		}
		catch (NoSuchDataProviderException nsee) {
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
	protected DDMDataProvider removeImpl(DDMDataProvider ddmDataProvider) {
		ddmDataProvider = toUnwrappedModel(ddmDataProvider);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmDataProvider)) {
				ddmDataProvider = (DDMDataProvider)session.get(DDMDataProviderImpl.class,
						ddmDataProvider.getPrimaryKeyObj());
			}

			if (ddmDataProvider != null) {
				session.delete(ddmDataProvider);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmDataProvider != null) {
			clearCache(ddmDataProvider);
		}

		return ddmDataProvider;
	}

	@Override
	public DDMDataProvider updateImpl(DDMDataProvider ddmDataProvider) {
		ddmDataProvider = toUnwrappedModel(ddmDataProvider);

		boolean isNew = ddmDataProvider.isNew();

		DDMDataProviderModelImpl ddmDataProviderModelImpl = (DDMDataProviderModelImpl)ddmDataProvider;

		if (Validator.isNull(ddmDataProvider.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmDataProvider.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddmDataProvider.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddmDataProvider.setCreateDate(now);
			}
			else {
				ddmDataProvider.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ddmDataProviderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddmDataProvider.setModifiedDate(now);
			}
			else {
				ddmDataProvider.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmDataProvider.isNew()) {
				session.save(ddmDataProvider);

				ddmDataProvider.setNew(false);
			}
			else {
				ddmDataProvider = (DDMDataProvider)session.merge(ddmDataProvider);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMDataProviderModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmDataProviderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmDataProviderModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmDataProviderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderModelImpl.getOriginalUuid(),
						ddmDataProviderModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmDataProviderModelImpl.getUuid(),
						ddmDataProviderModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddmDataProviderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { ddmDataProviderModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((ddmDataProviderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { ddmDataProviderModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderImpl.class, ddmDataProvider.getPrimaryKey(),
			ddmDataProvider, false);

		clearUniqueFindersCache(ddmDataProviderModelImpl);
		cacheUniqueFindersCache(ddmDataProviderModelImpl, isNew);

		ddmDataProvider.resetOriginalValues();

		return ddmDataProvider;
	}

	protected DDMDataProvider toUnwrappedModel(DDMDataProvider ddmDataProvider) {
		if (ddmDataProvider instanceof DDMDataProviderImpl) {
			return ddmDataProvider;
		}

		DDMDataProviderImpl ddmDataProviderImpl = new DDMDataProviderImpl();

		ddmDataProviderImpl.setNew(ddmDataProvider.isNew());
		ddmDataProviderImpl.setPrimaryKey(ddmDataProvider.getPrimaryKey());

		ddmDataProviderImpl.setUuid(ddmDataProvider.getUuid());
		ddmDataProviderImpl.setDataProviderId(ddmDataProvider.getDataProviderId());
		ddmDataProviderImpl.setGroupId(ddmDataProvider.getGroupId());
		ddmDataProviderImpl.setCompanyId(ddmDataProvider.getCompanyId());
		ddmDataProviderImpl.setUserId(ddmDataProvider.getUserId());
		ddmDataProviderImpl.setUserName(ddmDataProvider.getUserName());
		ddmDataProviderImpl.setCreateDate(ddmDataProvider.getCreateDate());
		ddmDataProviderImpl.setModifiedDate(ddmDataProvider.getModifiedDate());
		ddmDataProviderImpl.setName(ddmDataProvider.getName());
		ddmDataProviderImpl.setDescription(ddmDataProvider.getDescription());
		ddmDataProviderImpl.setDefinition(ddmDataProvider.getDefinition());

		return ddmDataProviderImpl;
	}

	/**
	 * Returns the d d m data provider with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m data provider
	 * @return the d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDataProviderException {
		DDMDataProvider ddmDataProvider = fetchByPrimaryKey(primaryKey);

		if (ddmDataProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDataProviderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmDataProvider;
	}

	/**
	 * Returns the d d m data provider with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderException} if it could not be found.
	 *
	 * @param dataProviderId the primary key of the d d m data provider
	 * @return the d d m data provider
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderException if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider findByPrimaryKey(long dataProviderId)
		throws NoSuchDataProviderException {
		return findByPrimaryKey((Serializable)dataProviderId);
	}

	/**
	 * Returns the d d m data provider with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m data provider
	 * @return the d d m data provider, or <code>null</code> if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider fetchByPrimaryKey(Serializable primaryKey) {
		DDMDataProvider ddmDataProvider = (DDMDataProvider)entityCache.getResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
				DDMDataProviderImpl.class, primaryKey);

		if (ddmDataProvider == _nullDDMDataProvider) {
			return null;
		}

		if (ddmDataProvider == null) {
			Session session = null;

			try {
				session = openSession();

				ddmDataProvider = (DDMDataProvider)session.get(DDMDataProviderImpl.class,
						primaryKey);

				if (ddmDataProvider != null) {
					cacheResult(ddmDataProvider);
				}
				else {
					entityCache.putResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
						DDMDataProviderImpl.class, primaryKey,
						_nullDDMDataProvider);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
					DDMDataProviderImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmDataProvider;
	}

	/**
	 * Returns the d d m data provider with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataProviderId the primary key of the d d m data provider
	 * @return the d d m data provider, or <code>null</code> if a d d m data provider with the primary key could not be found
	 */
	@Override
	public DDMDataProvider fetchByPrimaryKey(long dataProviderId) {
		return fetchByPrimaryKey((Serializable)dataProviderId);
	}

	@Override
	public Map<Serializable, DDMDataProvider> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMDataProvider> map = new HashMap<Serializable, DDMDataProvider>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMDataProvider ddmDataProvider = fetchByPrimaryKey(primaryKey);

			if (ddmDataProvider != null) {
				map.put(primaryKey, ddmDataProvider);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMDataProvider ddmDataProvider = (DDMDataProvider)entityCache.getResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
					DDMDataProviderImpl.class, primaryKey);

			if (ddmDataProvider == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmDataProvider);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMDATAPROVIDER_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DDMDataProvider ddmDataProvider : (List<DDMDataProvider>)q.list()) {
				map.put(ddmDataProvider.getPrimaryKeyObj(), ddmDataProvider);

				cacheResult(ddmDataProvider);

				uncachedPrimaryKeys.remove(ddmDataProvider.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMDataProviderModelImpl.ENTITY_CACHE_ENABLED,
					DDMDataProviderImpl.class, primaryKey, _nullDDMDataProvider);
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
	 * Returns all the d d m data providers.
	 *
	 * @return the d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data providers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @return the range of d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data providers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findAll(int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data providers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m data providers
	 * @param end the upper bound of the range of d d m data providers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d m data providers
	 */
	@Override
	public List<DDMDataProvider> findAll(int start, int end,
		OrderByComparator<DDMDataProvider> orderByComparator,
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

		List<DDMDataProvider> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProvider>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMDATAPROVIDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMDATAPROVIDER;

				if (pagination) {
					sql = sql.concat(DDMDataProviderModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProvider>)QueryUtil.list(q,
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
	 * Removes all the d d m data providers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMDataProvider ddmDataProvider : findAll()) {
			remove(ddmDataProvider);
		}
	}

	/**
	 * Returns the number of d d m data providers.
	 *
	 * @return the number of d d m data providers
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMDATAPROVIDER);

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
		return DDMDataProviderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d m data provider persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMDataProviderImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMDATAPROVIDER = "SELECT ddmDataProvider FROM DDMDataProvider ddmDataProvider";
	private static final String _SQL_SELECT_DDMDATAPROVIDER_WHERE_PKS_IN = "SELECT ddmDataProvider FROM DDMDataProvider ddmDataProvider WHERE dataProviderId IN (";
	private static final String _SQL_SELECT_DDMDATAPROVIDER_WHERE = "SELECT ddmDataProvider FROM DDMDataProvider ddmDataProvider WHERE ";
	private static final String _SQL_COUNT_DDMDATAPROVIDER = "SELECT COUNT(ddmDataProvider) FROM DDMDataProvider ddmDataProvider";
	private static final String _SQL_COUNT_DDMDATAPROVIDER_WHERE = "SELECT COUNT(ddmDataProvider) FROM DDMDataProvider ddmDataProvider WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmDataProvider.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMDataProvider exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMDataProvider exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMDataProviderPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final DDMDataProvider _nullDDMDataProvider = new DDMDataProviderImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMDataProvider> toCacheModel() {
				return _nullDDMDataProviderCacheModel;
			}
		};

	private static final CacheModel<DDMDataProvider> _nullDDMDataProviderCacheModel =
		new CacheModel<DDMDataProvider>() {
			@Override
			public DDMDataProvider toEntityModel() {
				return _nullDDMDataProvider;
			}
		};
}