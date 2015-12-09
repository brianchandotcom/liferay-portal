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

import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstancePersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
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
 * The persistence implementation for the d d m data provider instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstancePersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstanceUtil
 * @generated
 */
@ProviderType
public class DDMDataProviderInstancePersistenceImpl extends BasePersistenceImpl<DDMDataProviderInstance>
	implements DDMDataProviderInstancePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMDataProviderInstanceUtil} to access the d d m data provider instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMDataProviderInstanceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMDataProviderInstanceModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m data provider instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data provider instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid(String uuid, int start,
		int end, OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid(String uuid, int start,
		int end, OrderByComparator<DDMDataProviderInstance> orderByComparator,
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

		List<DDMDataProviderInstance> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProviderInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProviderInstance ddmDataProviderInstance : list) {
					if (!Validator.equals(uuid,
								ddmDataProviderInstance.getUuid())) {
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

			query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByUuid_First(String uuid,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByUuid_First(uuid,
				orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByUuid_First(String uuid,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		List<DDMDataProviderInstance> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByUuid_Last(String uuid,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByUuid_Last(uuid,
				orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByUuid_Last(String uuid,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMDataProviderInstance> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where uuid = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current d d m data provider instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance[] findByUuid_PrevAndNext(
		long dataProviderInstanceId, String uuid,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = findByPrimaryKey(dataProviderInstanceId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstance[] array = new DDMDataProviderInstanceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmDataProviderInstance,
					uuid, orderByComparator, true);

			array[1] = ddmDataProviderInstance;

			array[2] = getByUuid_PrevAndNext(session, ddmDataProviderInstance,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMDataProviderInstance getByUuid_PrevAndNext(Session session,
		DDMDataProviderInstance ddmDataProviderInstance, String uuid,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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
			query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProviderInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProviderInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m data provider instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMDataProviderInstance ddmDataProviderInstance : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProviderInstance);
		}
	}

	/**
	 * Returns the number of d d m data provider instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m data provider instances
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmDataProviderInstance.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmDataProviderInstance.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmDataProviderInstance.uuid IS NULL OR ddmDataProviderInstance.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMDataProviderInstanceModelImpl.UUID_COLUMN_BITMASK |
			DDMDataProviderInstanceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the d d m data provider instance where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByUUID_G(String uuid, long groupId)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByUUID_G(uuid,
				groupId);

		if (ddmDataProviderInstance == null) {
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

			throw new NoSuchDataProviderInstanceException(msg.toString());
		}

		return ddmDataProviderInstance;
	}

	/**
	 * Returns the d d m data provider instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d m data provider instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDMDataProviderInstance) {
			DDMDataProviderInstance ddmDataProviderInstance = (DDMDataProviderInstance)result;

			if (!Validator.equals(uuid, ddmDataProviderInstance.getUuid()) ||
					(groupId != ddmDataProviderInstance.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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

				List<DDMDataProviderInstance> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DDMDataProviderInstance ddmDataProviderInstance = list.get(0);

					result = ddmDataProviderInstance;

					cacheResult(ddmDataProviderInstance);

					if ((ddmDataProviderInstance.getUuid() == null) ||
							!ddmDataProviderInstance.getUuid().equals(uuid) ||
							(ddmDataProviderInstance.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmDataProviderInstance);
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
			return (DDMDataProviderInstance)result;
		}
	}

	/**
	 * Removes the d d m data provider instance where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d m data provider instance that was removed
	 */
	@Override
	public DDMDataProviderInstance removeByUUID_G(String uuid, long groupId)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = findByUUID_G(uuid,
				groupId);

		return remove(ddmDataProviderInstance);
	}

	/**
	 * Returns the number of d d m data provider instances where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d m data provider instances
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmDataProviderInstance.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmDataProviderInstance.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmDataProviderInstance.uuid IS NULL OR ddmDataProviderInstance.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmDataProviderInstance.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMDataProviderInstanceModelImpl.UUID_COLUMN_BITMASK |
			DDMDataProviderInstanceModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
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

		List<DDMDataProviderInstance> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProviderInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProviderInstance ddmDataProviderInstance : list) {
					if (!Validator.equals(uuid,
								ddmDataProviderInstance.getUuid()) ||
							(companyId != ddmDataProviderInstance.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		List<DDMDataProviderInstance> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProviderInstance> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current d d m data provider instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance[] findByUuid_C_PrevAndNext(
		long dataProviderInstanceId, String uuid, long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = findByPrimaryKey(dataProviderInstanceId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstance[] array = new DDMDataProviderInstanceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					ddmDataProviderInstance, uuid, companyId,
					orderByComparator, true);

			array[1] = ddmDataProviderInstance;

			array[2] = getByUuid_C_PrevAndNext(session,
					ddmDataProviderInstance, uuid, companyId,
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

	protected DDMDataProviderInstance getByUuid_C_PrevAndNext(Session session,
		DDMDataProviderInstance ddmDataProviderInstance, String uuid,
		long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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
			query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProviderInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProviderInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m data provider instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMDataProviderInstance ddmDataProviderInstance : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProviderInstance);
		}
	}

	/**
	 * Returns the number of d d m data provider instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m data provider instances
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmDataProviderInstance.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmDataProviderInstance.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmDataProviderInstance.uuid IS NULL OR ddmDataProviderInstance.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmDataProviderInstance.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DDMDataProviderInstanceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m data provider instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data provider instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMDataProviderInstance> orderByComparator,
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

		List<DDMDataProviderInstance> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProviderInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProviderInstance ddmDataProviderInstance : list) {
					if ((groupId != ddmDataProviderInstance.getGroupId())) {
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

			query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByGroupId_First(long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByGroupId_First(groupId,
				orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByGroupId_First(long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		List<DDMDataProviderInstance> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByGroupId_Last(long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByGroupId_Last(long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProviderInstance> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where groupId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current d d m data provider instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance[] findByGroupId_PrevAndNext(
		long dataProviderInstanceId, long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = findByPrimaryKey(dataProviderInstanceId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstance[] array = new DDMDataProviderInstanceImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					ddmDataProviderInstance, groupId, orderByComparator, true);

			array[1] = ddmDataProviderInstance;

			array[2] = getByGroupId_PrevAndNext(session,
					ddmDataProviderInstance, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMDataProviderInstance getByGroupId_PrevAndNext(
		Session session, DDMDataProviderInstance ddmDataProviderInstance,
		long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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
			query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProviderInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProviderInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public List<DDMDataProviderInstance> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public List<DDMDataProviderInstance> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public List<DDMDataProviderInstance> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMDataProviderInstance.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					DDMDataProviderInstanceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					DDMDataProviderInstanceImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DDMDataProviderInstance>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set of d d m data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current d d m data provider instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance[] filterFindByGroupId_PrevAndNext(
		long dataProviderInstanceId, long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(dataProviderInstanceId, groupId,
				orderByComparator);
		}

		DDMDataProviderInstance ddmDataProviderInstance = findByPrimaryKey(dataProviderInstanceId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstance[] array = new DDMDataProviderInstanceImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					ddmDataProviderInstance, groupId, orderByComparator, true);

			array[1] = ddmDataProviderInstance;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					ddmDataProviderInstance, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMDataProviderInstance filterGetByGroupId_PrevAndNext(
		Session session, DDMDataProviderInstance ddmDataProviderInstance,
		long groupId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMDataProviderInstance.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMDataProviderInstanceImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMDataProviderInstanceImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProviderInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProviderInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public List<DDMDataProviderInstance> filterFindByGroupId(long[] groupIds) {
		return filterFindByGroupId(groupIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public List<DDMDataProviderInstance> filterFindByGroupId(long[] groupIds,
		int start, int end) {
		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public List<DDMDataProviderInstance> filterFindByGroupId(long[] groupIds,
		int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByGroupId(groupIds, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMDataProviderInstance.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					DDMDataProviderInstanceImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					DDMDataProviderInstanceImpl.class);
			}

			return (List<DDMDataProviderInstance>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the d d m data provider instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long[] groupIds) {
		return findByGroupId(groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m data provider instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long[] groupIds,
		int start, int end) {
		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long[] groupIds,
		int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByGroupId(long[] groupIds,
		int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
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

		List<DDMDataProviderInstance> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProviderInstance>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProviderInstance ddmDataProviderInstance : list) {
					if (!ArrayUtil.contains(groupIds,
								ddmDataProviderInstance.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
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
	 * Removes all the d d m data provider instances where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DDMDataProviderInstance ddmDataProviderInstance : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProviderInstance);
		}
	}

	/**
	 * Returns the number of d d m data provider instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m data provider instances
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

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
	 * Returns the number of d d m data provider instances where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching d d m data provider instances
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

			query.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

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

	/**
	 * Returns the number of d d m data provider instances that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMDataProviderInstance.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of d d m data provider instances that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching d d m data provider instances that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long[] groupIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByGroupId(groupIds);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.unique(groupIds);

			Arrays.sort(groupIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMDataProviderInstance.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmDataProviderInstance.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 = "ddmDataProviderInstance.groupId IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DDMDataProviderInstanceModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m data provider instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m data provider instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
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

		List<DDMDataProviderInstance> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProviderInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMDataProviderInstance ddmDataProviderInstance : list) {
					if ((companyId != ddmDataProviderInstance.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
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
	 * Returns the first d d m data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByCompanyId_First(long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the first d d m data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByCompanyId_First(long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		List<DDMDataProviderInstance> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance findByCompanyId_Last(long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (ddmDataProviderInstance != null) {
			return ddmDataProviderInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDataProviderInstanceException(msg.toString());
	}

	/**
	 * Returns the last d d m data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByCompanyId_Last(long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DDMDataProviderInstance> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m data provider instances before and after the current d d m data provider instance in the ordered set where companyId = &#63;.
	 *
	 * @param dataProviderInstanceId the primary key of the current d d m data provider instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance[] findByCompanyId_PrevAndNext(
		long dataProviderInstanceId, long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = findByPrimaryKey(dataProviderInstanceId);

		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstance[] array = new DDMDataProviderInstanceImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					ddmDataProviderInstance, companyId, orderByComparator, true);

			array[1] = ddmDataProviderInstance;

			array[2] = getByCompanyId_PrevAndNext(session,
					ddmDataProviderInstance, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMDataProviderInstance getByCompanyId_PrevAndNext(
		Session session, DDMDataProviderInstance ddmDataProviderInstance,
		long companyId,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE);

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
			query.append(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmDataProviderInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMDataProviderInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m data provider instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DDMDataProviderInstance ddmDataProviderInstance : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmDataProviderInstance);
		}
	}

	/**
	 * Returns the number of d d m data provider instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching d d m data provider instances
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "ddmDataProviderInstance.companyId = ?";

	public DDMDataProviderInstancePersistenceImpl() {
		setModelClass(DDMDataProviderInstance.class);
	}

	/**
	 * Caches the d d m data provider instance in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstance the d d m data provider instance
	 */
	@Override
	public void cacheResult(DDMDataProviderInstance ddmDataProviderInstance) {
		entityCache.putResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			ddmDataProviderInstance.getPrimaryKey(), ddmDataProviderInstance);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmDataProviderInstance.getUuid(),
				ddmDataProviderInstance.getGroupId()
			}, ddmDataProviderInstance);

		ddmDataProviderInstance.resetOriginalValues();
	}

	/**
	 * Caches the d d m data provider instances in the entity cache if it is enabled.
	 *
	 * @param ddmDataProviderInstances the d d m data provider instances
	 */
	@Override
	public void cacheResult(
		List<DDMDataProviderInstance> ddmDataProviderInstances) {
		for (DDMDataProviderInstance ddmDataProviderInstance : ddmDataProviderInstances) {
			if (entityCache.getResult(
						DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
						DDMDataProviderInstanceImpl.class,
						ddmDataProviderInstance.getPrimaryKey()) == null) {
				cacheResult(ddmDataProviderInstance);
			}
			else {
				ddmDataProviderInstance.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m data provider instances.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMDataProviderInstanceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m data provider instance.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMDataProviderInstance ddmDataProviderInstance) {
		entityCache.removeResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			ddmDataProviderInstance.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMDataProviderInstanceModelImpl)ddmDataProviderInstance);
	}

	@Override
	public void clearCache(
		List<DDMDataProviderInstance> ddmDataProviderInstances) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMDataProviderInstance ddmDataProviderInstance : ddmDataProviderInstances) {
			entityCache.removeResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
				DDMDataProviderInstanceImpl.class,
				ddmDataProviderInstance.getPrimaryKey());

			clearUniqueFindersCache((DDMDataProviderInstanceModelImpl)ddmDataProviderInstance);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMDataProviderInstanceModelImpl ddmDataProviderInstanceModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ddmDataProviderInstanceModelImpl.getUuid(),
					ddmDataProviderInstanceModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				ddmDataProviderInstanceModelImpl);
		}
		else {
			if ((ddmDataProviderInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderInstanceModelImpl.getUuid(),
						ddmDataProviderInstanceModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					ddmDataProviderInstanceModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMDataProviderInstanceModelImpl ddmDataProviderInstanceModelImpl) {
		Object[] args = new Object[] {
				ddmDataProviderInstanceModelImpl.getUuid(),
				ddmDataProviderInstanceModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((ddmDataProviderInstanceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmDataProviderInstanceModelImpl.getOriginalUuid(),
					ddmDataProviderInstanceModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new d d m data provider instance with the primary key. Does not add the d d m data provider instance to the database.
	 *
	 * @param dataProviderInstanceId the primary key for the new d d m data provider instance
	 * @return the new d d m data provider instance
	 */
	@Override
	public DDMDataProviderInstance create(long dataProviderInstanceId) {
		DDMDataProviderInstance ddmDataProviderInstance = new DDMDataProviderInstanceImpl();

		ddmDataProviderInstance.setNew(true);
		ddmDataProviderInstance.setPrimaryKey(dataProviderInstanceId);

		String uuid = PortalUUIDUtil.generate();

		ddmDataProviderInstance.setUuid(uuid);

		return ddmDataProviderInstance;
	}

	/**
	 * Removes the d d m data provider instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dataProviderInstanceId the primary key of the d d m data provider instance
	 * @return the d d m data provider instance that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance remove(long dataProviderInstanceId)
		throws NoSuchDataProviderInstanceException {
		return remove((Serializable)dataProviderInstanceId);
	}

	/**
	 * Removes the d d m data provider instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m data provider instance
	 * @return the d d m data provider instance that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance remove(Serializable primaryKey)
		throws NoSuchDataProviderInstanceException {
		Session session = null;

		try {
			session = openSession();

			DDMDataProviderInstance ddmDataProviderInstance = (DDMDataProviderInstance)session.get(DDMDataProviderInstanceImpl.class,
					primaryKey);

			if (ddmDataProviderInstance == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDataProviderInstanceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmDataProviderInstance);
		}
		catch (NoSuchDataProviderInstanceException nsee) {
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
	protected DDMDataProviderInstance removeImpl(
		DDMDataProviderInstance ddmDataProviderInstance) {
		ddmDataProviderInstance = toUnwrappedModel(ddmDataProviderInstance);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmDataProviderInstance)) {
				ddmDataProviderInstance = (DDMDataProviderInstance)session.get(DDMDataProviderInstanceImpl.class,
						ddmDataProviderInstance.getPrimaryKeyObj());
			}

			if (ddmDataProviderInstance != null) {
				session.delete(ddmDataProviderInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmDataProviderInstance != null) {
			clearCache(ddmDataProviderInstance);
		}

		return ddmDataProviderInstance;
	}

	@Override
	public DDMDataProviderInstance updateImpl(
		DDMDataProviderInstance ddmDataProviderInstance) {
		ddmDataProviderInstance = toUnwrappedModel(ddmDataProviderInstance);

		boolean isNew = ddmDataProviderInstance.isNew();

		DDMDataProviderInstanceModelImpl ddmDataProviderInstanceModelImpl = (DDMDataProviderInstanceModelImpl)ddmDataProviderInstance;

		if (Validator.isNull(ddmDataProviderInstance.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmDataProviderInstance.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddmDataProviderInstance.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddmDataProviderInstance.setCreateDate(now);
			}
			else {
				ddmDataProviderInstance.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!ddmDataProviderInstanceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddmDataProviderInstance.setModifiedDate(now);
			}
			else {
				ddmDataProviderInstance.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmDataProviderInstance.isNew()) {
				session.save(ddmDataProviderInstance);

				ddmDataProviderInstance.setNew(false);
			}
			else {
				ddmDataProviderInstance = (DDMDataProviderInstance)session.merge(ddmDataProviderInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMDataProviderInstanceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmDataProviderInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderInstanceModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmDataProviderInstanceModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmDataProviderInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderInstanceModelImpl.getOriginalUuid(),
						ddmDataProviderInstanceModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmDataProviderInstanceModelImpl.getUuid(),
						ddmDataProviderInstanceModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddmDataProviderInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderInstanceModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						ddmDataProviderInstanceModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((ddmDataProviderInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmDataProviderInstanceModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						ddmDataProviderInstanceModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
			DDMDataProviderInstanceImpl.class,
			ddmDataProviderInstance.getPrimaryKey(), ddmDataProviderInstance,
			false);

		clearUniqueFindersCache(ddmDataProviderInstanceModelImpl);
		cacheUniqueFindersCache(ddmDataProviderInstanceModelImpl, isNew);

		ddmDataProviderInstance.resetOriginalValues();

		return ddmDataProviderInstance;
	}

	protected DDMDataProviderInstance toUnwrappedModel(
		DDMDataProviderInstance ddmDataProviderInstance) {
		if (ddmDataProviderInstance instanceof DDMDataProviderInstanceImpl) {
			return ddmDataProviderInstance;
		}

		DDMDataProviderInstanceImpl ddmDataProviderInstanceImpl = new DDMDataProviderInstanceImpl();

		ddmDataProviderInstanceImpl.setNew(ddmDataProviderInstance.isNew());
		ddmDataProviderInstanceImpl.setPrimaryKey(ddmDataProviderInstance.getPrimaryKey());

		ddmDataProviderInstanceImpl.setUuid(ddmDataProviderInstance.getUuid());
		ddmDataProviderInstanceImpl.setDataProviderInstanceId(ddmDataProviderInstance.getDataProviderInstanceId());
		ddmDataProviderInstanceImpl.setGroupId(ddmDataProviderInstance.getGroupId());
		ddmDataProviderInstanceImpl.setCompanyId(ddmDataProviderInstance.getCompanyId());
		ddmDataProviderInstanceImpl.setUserId(ddmDataProviderInstance.getUserId());
		ddmDataProviderInstanceImpl.setUserName(ddmDataProviderInstance.getUserName());
		ddmDataProviderInstanceImpl.setCreateDate(ddmDataProviderInstance.getCreateDate());
		ddmDataProviderInstanceImpl.setModifiedDate(ddmDataProviderInstance.getModifiedDate());
		ddmDataProviderInstanceImpl.setName(ddmDataProviderInstance.getName());
		ddmDataProviderInstanceImpl.setDescription(ddmDataProviderInstance.getDescription());
		ddmDataProviderInstanceImpl.setDefinition(ddmDataProviderInstance.getDefinition());
		ddmDataProviderInstanceImpl.setType(ddmDataProviderInstance.getType());

		return ddmDataProviderInstanceImpl;
	}

	/**
	 * Returns the d d m data provider instance with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m data provider instance
	 * @return the d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDataProviderInstanceException {
		DDMDataProviderInstance ddmDataProviderInstance = fetchByPrimaryKey(primaryKey);

		if (ddmDataProviderInstance == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDataProviderInstanceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmDataProviderInstance;
	}

	/**
	 * Returns the d d m data provider instance with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException} if it could not be found.
	 *
	 * @param dataProviderInstanceId the primary key of the d d m data provider instance
	 * @return the d d m data provider instance
	 * @throws com.liferay.dynamic.data.mapping.NoSuchDataProviderInstanceException if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance findByPrimaryKey(long dataProviderInstanceId)
		throws NoSuchDataProviderInstanceException {
		return findByPrimaryKey((Serializable)dataProviderInstanceId);
	}

	/**
	 * Returns the d d m data provider instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m data provider instance
	 * @return the d d m data provider instance, or <code>null</code> if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByPrimaryKey(Serializable primaryKey) {
		DDMDataProviderInstance ddmDataProviderInstance = (DDMDataProviderInstance)entityCache.getResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
				DDMDataProviderInstanceImpl.class, primaryKey);

		if (ddmDataProviderInstance == _nullDDMDataProviderInstance) {
			return null;
		}

		if (ddmDataProviderInstance == null) {
			Session session = null;

			try {
				session = openSession();

				ddmDataProviderInstance = (DDMDataProviderInstance)session.get(DDMDataProviderInstanceImpl.class,
						primaryKey);

				if (ddmDataProviderInstance != null) {
					cacheResult(ddmDataProviderInstance);
				}
				else {
					entityCache.putResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
						DDMDataProviderInstanceImpl.class, primaryKey,
						_nullDDMDataProviderInstance);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
					DDMDataProviderInstanceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmDataProviderInstance;
	}

	/**
	 * Returns the d d m data provider instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dataProviderInstanceId the primary key of the d d m data provider instance
	 * @return the d d m data provider instance, or <code>null</code> if a d d m data provider instance with the primary key could not be found
	 */
	@Override
	public DDMDataProviderInstance fetchByPrimaryKey(
		long dataProviderInstanceId) {
		return fetchByPrimaryKey((Serializable)dataProviderInstanceId);
	}

	@Override
	public Map<Serializable, DDMDataProviderInstance> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMDataProviderInstance> map = new HashMap<Serializable, DDMDataProviderInstance>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMDataProviderInstance ddmDataProviderInstance = fetchByPrimaryKey(primaryKey);

			if (ddmDataProviderInstance != null) {
				map.put(primaryKey, ddmDataProviderInstance);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMDataProviderInstance ddmDataProviderInstance = (DDMDataProviderInstance)entityCache.getResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
					DDMDataProviderInstanceImpl.class, primaryKey);

			if (ddmDataProviderInstance == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmDataProviderInstance);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE_PKS_IN);

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

			for (DDMDataProviderInstance ddmDataProviderInstance : (List<DDMDataProviderInstance>)q.list()) {
				map.put(ddmDataProviderInstance.getPrimaryKeyObj(),
					ddmDataProviderInstance);

				cacheResult(ddmDataProviderInstance);

				uncachedPrimaryKeys.remove(ddmDataProviderInstance.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMDataProviderInstanceModelImpl.ENTITY_CACHE_ENABLED,
					DDMDataProviderInstanceImpl.class, primaryKey,
					_nullDDMDataProviderInstance);
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
	 * Returns all the d d m data provider instances.
	 *
	 * @return the d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m data provider instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @return the range of d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findAll(int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m data provider instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m data provider instances
	 * @param end the upper bound of the range of d d m data provider instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d m data provider instances
	 */
	@Override
	public List<DDMDataProviderInstance> findAll(int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator,
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

		List<DDMDataProviderInstance> list = null;

		if (retrieveFromCache) {
			list = (List<DDMDataProviderInstance>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMDATAPROVIDERINSTANCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMDATAPROVIDERINSTANCE;

				if (pagination) {
					sql = sql.concat(DDMDataProviderInstanceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMDataProviderInstance>)QueryUtil.list(q,
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
	 * Removes all the d d m data provider instances from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMDataProviderInstance ddmDataProviderInstance : findAll()) {
			remove(ddmDataProviderInstance);
		}
	}

	/**
	 * Returns the number of d d m data provider instances.
	 *
	 * @return the number of d d m data provider instances
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMDATAPROVIDERINSTANCE);

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
		return DDMDataProviderInstanceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d m data provider instance persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMDataProviderInstanceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMDATAPROVIDERINSTANCE = "SELECT ddmDataProviderInstance FROM DDMDataProviderInstance ddmDataProviderInstance";
	private static final String _SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE_PKS_IN =
		"SELECT ddmDataProviderInstance FROM DDMDataProviderInstance ddmDataProviderInstance WHERE dataProviderInstanceId IN (";
	private static final String _SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE = "SELECT ddmDataProviderInstance FROM DDMDataProviderInstance ddmDataProviderInstance WHERE ";
	private static final String _SQL_COUNT_DDMDATAPROVIDERINSTANCE = "SELECT COUNT(ddmDataProviderInstance) FROM DDMDataProviderInstance ddmDataProviderInstance";
	private static final String _SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE = "SELECT COUNT(ddmDataProviderInstance) FROM DDMDataProviderInstance ddmDataProviderInstance WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "ddmDataProviderInstance.dataProviderInstanceId";
	private static final String _FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_WHERE =
		"SELECT DISTINCT {ddmDataProviderInstance.*} FROM DDMDataProviderInstance ddmDataProviderInstance WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DDMDataProviderInstance.*} FROM (SELECT DISTINCT ddmDataProviderInstance.dataProviderInstanceId FROM DDMDataProviderInstance ddmDataProviderInstance WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMDATAPROVIDERINSTANCE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DDMDataProviderInstance ON TEMP_TABLE.dataProviderInstanceId = DDMDataProviderInstance.dataProviderInstanceId";
	private static final String _FILTER_SQL_COUNT_DDMDATAPROVIDERINSTANCE_WHERE = "SELECT COUNT(DISTINCT ddmDataProviderInstance.dataProviderInstanceId) AS COUNT_VALUE FROM DDMDataProviderInstance ddmDataProviderInstance WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "ddmDataProviderInstance";
	private static final String _FILTER_ENTITY_TABLE = "DDMDataProviderInstance";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmDataProviderInstance.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DDMDataProviderInstance.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMDataProviderInstance exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMDataProviderInstance exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMDataProviderInstancePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
	private static final DDMDataProviderInstance _nullDDMDataProviderInstance = new DDMDataProviderInstanceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMDataProviderInstance> toCacheModel() {
				return _nullDDMDataProviderInstanceCacheModel;
			}
		};

	private static final CacheModel<DDMDataProviderInstance> _nullDDMDataProviderInstanceCacheModel =
		new CacheModel<DDMDataProviderInstance>() {
			@Override
			public DDMDataProviderInstance toEntityModel() {
				return _nullDDMDataProviderInstance;
			}
		};
}