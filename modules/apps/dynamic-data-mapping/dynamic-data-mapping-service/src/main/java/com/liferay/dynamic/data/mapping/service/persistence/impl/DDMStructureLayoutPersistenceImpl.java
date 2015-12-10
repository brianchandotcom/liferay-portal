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

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLayoutPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d d m structure layout service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLayoutUtil
 * @generated
 */
@ProviderType
public class DDMStructureLayoutPersistenceImpl extends BasePersistenceImpl<DDMStructureLayout>
	implements DDMStructureLayoutPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureLayoutUtil} to access the d d m structure layout persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureLayoutImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMStructureLayoutModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @return the range of matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		List<DDMStructureLayout> list = null;

		if (retrieveFromCache) {
			list = (List<DDMStructureLayout>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if (!Validator.equals(uuid, ddmStructureLayout.getUuid())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
				query.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMStructureLayout>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureLayout>)QueryUtil.list(q,
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
	 * Returns the first d d m structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_First(String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = fetchByUuid_First(uuid,
				orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLayoutException(msg.toString());
	}

	/**
	 * Returns the first d d m structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_First(String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		List<DDMStructureLayout> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_Last(String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = fetchByUuid_Last(uuid,
				orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLayoutException(msg.toString());
	}

	/**
	 * Returns the last d d m structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_Last(String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structure layouts before and after the current d d m structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current d d m structure layout
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByUuid_PrevAndNext(long structureLayoutId,
		String uuid, OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmStructureLayout, uuid,
					orderByComparator, true);

			array[1] = ddmStructureLayout;

			array[2] = getByUuid_PrevAndNext(session, ddmStructureLayout, uuid,
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

	protected DDMStructureLayout getByUuid_PrevAndNext(Session session,
		DDMStructureLayout ddmStructureLayout, String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
			query.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructureLayout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLayout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structure layouts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMStructureLayout ddmStructureLayout : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of d d m structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m structure layouts
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmStructureLayout.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmStructureLayout.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmStructureLayout.uuid IS NULL OR ddmStructureLayout.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMStructureLayoutModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = fetchByUUID_G(uuid, groupId);

		if (ddmStructureLayout == null) {
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

			throw new NoSuchStructureLayoutException(msg.toString());
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d m structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDMStructureLayout) {
			DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)result;

			if (!Validator.equals(uuid, ddmStructureLayout.getUuid()) ||
					(groupId != ddmStructureLayout.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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

				List<DDMStructureLayout> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DDMStructureLayout ddmStructureLayout = list.get(0);

					result = ddmStructureLayout;

					cacheResult(ddmStructureLayout);

					if ((ddmStructureLayout.getUuid() == null) ||
							!ddmStructureLayout.getUuid().equals(uuid) ||
							(ddmStructureLayout.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmStructureLayout);
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
			return (DDMStructureLayout)result;
		}
	}

	/**
	 * Removes the d d m structure layout where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d m structure layout that was removed
	 */
	@Override
	public DDMStructureLayout removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = findByUUID_G(uuid, groupId);

		return remove(ddmStructureLayout);
	}

	/**
	 * Returns the number of d d m structure layouts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d m structure layouts
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmStructureLayout.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmStructureLayout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmStructureLayout.uuid IS NULL OR ddmStructureLayout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmStructureLayout.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMStructureLayoutModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @return the range of matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		List<DDMStructureLayout> list = null;

		if (retrieveFromCache) {
			list = (List<DDMStructureLayout>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if (!Validator.equals(uuid, ddmStructureLayout.getUuid()) ||
							(companyId != ddmStructureLayout.getCompanyId())) {
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

			query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
				query.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMStructureLayout>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureLayout>)QueryUtil.list(q,
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
	 * Returns the first d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLayoutException(msg.toString());
	}

	/**
	 * Returns the first d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		List<DDMStructureLayout> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureLayoutException(msg.toString());
	}

	/**
	 * Returns the last d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structure layouts before and after the current d d m structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current d d m structure layout
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByUuid_C_PrevAndNext(
		long structureLayoutId, String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmStructureLayout,
					uuid, companyId, orderByComparator, true);

			array[1] = ddmStructureLayout;

			array[2] = getByUuid_C_PrevAndNext(session, ddmStructureLayout,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLayout getByUuid_C_PrevAndNext(Session session,
		DDMStructureLayout ddmStructureLayout, String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
			query.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructureLayout);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLayout> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structure layouts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMStructureLayout ddmStructureLayout : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of d d m structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m structure layouts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmStructureLayout.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmStructureLayout.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmStructureLayout.uuid IS NULL OR ddmStructureLayout.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmStructureLayout.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureLayoutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByStructureVersionId", new String[] { Long.class.getName() },
			DDMStructureLayoutModelImpl.STRUCTUREVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREVERSIONID = new FinderPath(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByStructureVersionId", new String[] { Long.class.getName() });

	/**
	 * Returns the d d m structure layout where structureVersionId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException} if it could not be found.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByStructureVersionId(long structureVersionId)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = fetchByStructureVersionId(structureVersionId);

		if (ddmStructureLayout == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureVersionId=");
			msg.append(structureVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureLayoutException(msg.toString());
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the d d m structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByStructureVersionId(long structureVersionId) {
		return fetchByStructureVersionId(structureVersionId, true);
	}

	/**
	 * Returns the d d m structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching d d m structure layout, or <code>null</code> if a matching d d m structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { structureVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
					finderArgs, this);
		}

		if (result instanceof DDMStructureLayout) {
			DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)result;

			if ((structureVersionId != ddmStructureLayout.getStructureVersionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREVERSIONID_STRUCTUREVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureVersionId);

				List<DDMStructureLayout> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
						finderArgs, list);
				}
				else {
					DDMStructureLayout ddmStructureLayout = list.get(0);

					result = ddmStructureLayout;

					cacheResult(ddmStructureLayout);

					if ((ddmStructureLayout.getStructureVersionId() != structureVersionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
							finderArgs, ddmStructureLayout);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
					finderArgs);

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
			return (DDMStructureLayout)result;
		}
	}

	/**
	 * Removes the d d m structure layout where structureVersionId = &#63; from the database.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the d d m structure layout that was removed
	 */
	@Override
	public DDMStructureLayout removeByStructureVersionId(
		long structureVersionId) throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = findByStructureVersionId(structureVersionId);

		return remove(ddmStructureLayout);
	}

	/**
	 * Returns the number of d d m structure layouts where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the number of matching d d m structure layouts
	 */
	@Override
	public int countByStructureVersionId(long structureVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STRUCTUREVERSIONID;

		Object[] finderArgs = new Object[] { structureVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREVERSIONID_STRUCTUREVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureVersionId);

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

	private static final String _FINDER_COLUMN_STRUCTUREVERSIONID_STRUCTUREVERSIONID_2 =
		"ddmStructureLayout.structureVersionId = ?";

	public DDMStructureLayoutPersistenceImpl() {
		setModelClass(DDMStructureLayout.class);
	}

	/**
	 * Caches the d d m structure layout in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayout the d d m structure layout
	 */
	@Override
	public void cacheResult(DDMStructureLayout ddmStructureLayout) {
		entityCache.putResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutImpl.class, ddmStructureLayout.getPrimaryKey(),
			ddmStructureLayout);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				ddmStructureLayout.getUuid(), ddmStructureLayout.getGroupId()
			}, ddmStructureLayout);

		finderCache.putResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
			new Object[] { ddmStructureLayout.getStructureVersionId() },
			ddmStructureLayout);

		ddmStructureLayout.resetOriginalValues();
	}

	/**
	 * Caches the d d m structure layouts in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayouts the d d m structure layouts
	 */
	@Override
	public void cacheResult(List<DDMStructureLayout> ddmStructureLayouts) {
		for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
			if (entityCache.getResult(
						DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureLayoutImpl.class,
						ddmStructureLayout.getPrimaryKey()) == null) {
				cacheResult(ddmStructureLayout);
			}
			else {
				ddmStructureLayout.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m structure layouts.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMStructureLayoutImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m structure layout.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructureLayout ddmStructureLayout) {
		entityCache.removeResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutImpl.class, ddmStructureLayout.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMStructureLayoutModelImpl)ddmStructureLayout);
	}

	@Override
	public void clearCache(List<DDMStructureLayout> ddmStructureLayouts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
			entityCache.removeResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureLayoutImpl.class, ddmStructureLayout.getPrimaryKey());

			clearUniqueFindersCache((DDMStructureLayoutModelImpl)ddmStructureLayout);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMStructureLayoutModelImpl ddmStructureLayoutModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					ddmStructureLayoutModelImpl.getUuid(),
					ddmStructureLayoutModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				ddmStructureLayoutModelImpl);

			args = new Object[] {
					ddmStructureLayoutModelImpl.getStructureVersionId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_STRUCTUREVERSIONID,
				args, Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
				args, ddmStructureLayoutModelImpl);
		}
		else {
			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureLayoutModelImpl.getUuid(),
						ddmStructureLayoutModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					ddmStructureLayoutModelImpl);
			}

			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureLayoutModelImpl.getStructureVersionId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_STRUCTUREVERSIONID,
					args, Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
					args, ddmStructureLayoutModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMStructureLayoutModelImpl ddmStructureLayoutModelImpl) {
		Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getUuid(),
				ddmStructureLayoutModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalUuid(),
					ddmStructureLayoutModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] { ddmStructureLayoutModelImpl.getStructureVersionId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREVERSIONID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID, args);

		if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalStructureVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREVERSIONID,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_STRUCTUREVERSIONID,
				args);
		}
	}

	/**
	 * Creates a new d d m structure layout with the primary key. Does not add the d d m structure layout to the database.
	 *
	 * @param structureLayoutId the primary key for the new d d m structure layout
	 * @return the new d d m structure layout
	 */
	@Override
	public DDMStructureLayout create(long structureLayoutId) {
		DDMStructureLayout ddmStructureLayout = new DDMStructureLayoutImpl();

		ddmStructureLayout.setNew(true);
		ddmStructureLayout.setPrimaryKey(structureLayoutId);

		String uuid = PortalUUIDUtil.generate();

		ddmStructureLayout.setUuid(uuid);

		ddmStructureLayout.setCompanyId(companyProvider.getCompanyId());

		return ddmStructureLayout;
	}

	/**
	 * Removes the d d m structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLayoutId the primary key of the d d m structure layout
	 * @return the d d m structure layout that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout remove(long structureLayoutId)
		throws NoSuchStructureLayoutException {
		return remove((Serializable)structureLayoutId);
	}

	/**
	 * Removes the d d m structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure layout
	 * @return the d d m structure layout that was removed
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout remove(Serializable primaryKey)
		throws NoSuchStructureLayoutException {
		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)session.get(DDMStructureLayoutImpl.class,
					primaryKey);

			if (ddmStructureLayout == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmStructureLayout);
		}
		catch (NoSuchStructureLayoutException nsee) {
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
	protected DDMStructureLayout removeImpl(
		DDMStructureLayout ddmStructureLayout) {
		ddmStructureLayout = toUnwrappedModel(ddmStructureLayout);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmStructureLayout)) {
				ddmStructureLayout = (DDMStructureLayout)session.get(DDMStructureLayoutImpl.class,
						ddmStructureLayout.getPrimaryKeyObj());
			}

			if (ddmStructureLayout != null) {
				session.delete(ddmStructureLayout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructureLayout != null) {
			clearCache(ddmStructureLayout);
		}

		return ddmStructureLayout;
	}

	@Override
	public DDMStructureLayout updateImpl(DDMStructureLayout ddmStructureLayout) {
		ddmStructureLayout = toUnwrappedModel(ddmStructureLayout);

		boolean isNew = ddmStructureLayout.isNew();

		DDMStructureLayoutModelImpl ddmStructureLayoutModelImpl = (DDMStructureLayoutModelImpl)ddmStructureLayout;

		if (Validator.isNull(ddmStructureLayout.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmStructureLayout.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddmStructureLayout.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddmStructureLayout.setCreateDate(now);
			}
			else {
				ddmStructureLayout.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!ddmStructureLayoutModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddmStructureLayout.setModifiedDate(now);
			}
			else {
				ddmStructureLayout.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmStructureLayout.isNew()) {
				session.save(ddmStructureLayout);

				ddmStructureLayout.setNew(false);
			}
			else {
				ddmStructureLayout = (DDMStructureLayout)session.merge(ddmStructureLayout);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMStructureLayoutModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureLayoutModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmStructureLayoutModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureLayoutModelImpl.getOriginalUuid(),
						ddmStructureLayoutModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmStructureLayoutModelImpl.getUuid(),
						ddmStructureLayoutModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		entityCache.putResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLayoutImpl.class, ddmStructureLayout.getPrimaryKey(),
			ddmStructureLayout, false);

		clearUniqueFindersCache(ddmStructureLayoutModelImpl);
		cacheUniqueFindersCache(ddmStructureLayoutModelImpl, isNew);

		ddmStructureLayout.resetOriginalValues();

		return ddmStructureLayout;
	}

	protected DDMStructureLayout toUnwrappedModel(
		DDMStructureLayout ddmStructureLayout) {
		if (ddmStructureLayout instanceof DDMStructureLayoutImpl) {
			return ddmStructureLayout;
		}

		DDMStructureLayoutImpl ddmStructureLayoutImpl = new DDMStructureLayoutImpl();

		ddmStructureLayoutImpl.setNew(ddmStructureLayout.isNew());
		ddmStructureLayoutImpl.setPrimaryKey(ddmStructureLayout.getPrimaryKey());

		ddmStructureLayoutImpl.setUuid(ddmStructureLayout.getUuid());
		ddmStructureLayoutImpl.setStructureLayoutId(ddmStructureLayout.getStructureLayoutId());
		ddmStructureLayoutImpl.setGroupId(ddmStructureLayout.getGroupId());
		ddmStructureLayoutImpl.setCompanyId(ddmStructureLayout.getCompanyId());
		ddmStructureLayoutImpl.setUserId(ddmStructureLayout.getUserId());
		ddmStructureLayoutImpl.setUserName(ddmStructureLayout.getUserName());
		ddmStructureLayoutImpl.setCreateDate(ddmStructureLayout.getCreateDate());
		ddmStructureLayoutImpl.setModifiedDate(ddmStructureLayout.getModifiedDate());
		ddmStructureLayoutImpl.setStructureVersionId(ddmStructureLayout.getStructureVersionId());
		ddmStructureLayoutImpl.setDefinition(ddmStructureLayout.getDefinition());

		return ddmStructureLayoutImpl;
	}

	/**
	 * Returns the d d m structure layout with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure layout
	 * @return the d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStructureLayoutException {
		DDMStructureLayout ddmStructureLayout = fetchByPrimaryKey(primaryKey);

		if (ddmStructureLayout == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStructureLayoutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the d d m structure layout with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException} if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the d d m structure layout
	 * @return the d d m structure layout
	 * @throws com.liferay.dynamic.data.mapping.NoSuchStructureLayoutException if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout findByPrimaryKey(long structureLayoutId)
		throws NoSuchStructureLayoutException {
		return findByPrimaryKey((Serializable)structureLayoutId);
	}

	/**
	 * Returns the d d m structure layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure layout
	 * @return the d d m structure layout, or <code>null</code> if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout fetchByPrimaryKey(Serializable primaryKey) {
		DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)entityCache.getResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureLayoutImpl.class, primaryKey);

		if (ddmStructureLayout == _nullDDMStructureLayout) {
			return null;
		}

		if (ddmStructureLayout == null) {
			Session session = null;

			try {
				session = openSession();

				ddmStructureLayout = (DDMStructureLayout)session.get(DDMStructureLayoutImpl.class,
						primaryKey);

				if (ddmStructureLayout != null) {
					cacheResult(ddmStructureLayout);
				}
				else {
					entityCache.putResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureLayoutImpl.class, primaryKey,
						_nullDDMStructureLayout);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureLayoutImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the d d m structure layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the d d m structure layout
	 * @return the d d m structure layout, or <code>null</code> if a d d m structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout fetchByPrimaryKey(long structureLayoutId) {
		return fetchByPrimaryKey((Serializable)structureLayoutId);
	}

	@Override
	public Map<Serializable, DDMStructureLayout> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMStructureLayout> map = new HashMap<Serializable, DDMStructureLayout>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMStructureLayout ddmStructureLayout = fetchByPrimaryKey(primaryKey);

			if (ddmStructureLayout != null) {
				map.put(primaryKey, ddmStructureLayout);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)entityCache.getResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureLayoutImpl.class, primaryKey);

			if (ddmStructureLayout == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmStructureLayout);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE_PKS_IN);

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

			for (DDMStructureLayout ddmStructureLayout : (List<DDMStructureLayout>)q.list()) {
				map.put(ddmStructureLayout.getPrimaryKeyObj(),
					ddmStructureLayout);

				cacheResult(ddmStructureLayout);

				uncachedPrimaryKeys.remove(ddmStructureLayout.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMStructureLayoutModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureLayoutImpl.class, primaryKey,
					_nullDDMStructureLayout);
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
	 * Returns all the d d m structure layouts.
	 *
	 * @return the d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @return the range of d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll(int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the d d m structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure layouts
	 * @param end the upper bound of the range of d d m structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of d d m structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll(int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		List<DDMStructureLayout> list = null;

		if (retrieveFromCache) {
			list = (List<DDMStructureLayout>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTURELAYOUT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTURELAYOUT;

				if (pagination) {
					sql = sql.concat(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMStructureLayout>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureLayout>)QueryUtil.list(q,
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
	 * Removes all the d d m structure layouts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMStructureLayout ddmStructureLayout : findAll()) {
			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of d d m structure layouts.
	 *
	 * @return the number of d d m structure layouts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTURELAYOUT);

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
		return DDMStructureLayoutModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the d d m structure layout persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMStructureLayoutImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMSTRUCTURELAYOUT = "SELECT ddmStructureLayout FROM DDMStructureLayout ddmStructureLayout";
	private static final String _SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE_PKS_IN = "SELECT ddmStructureLayout FROM DDMStructureLayout ddmStructureLayout WHERE structureLayoutId IN (";
	private static final String _SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE = "SELECT ddmStructureLayout FROM DDMStructureLayout ddmStructureLayout WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTURELAYOUT = "SELECT COUNT(ddmStructureLayout) FROM DDMStructureLayout ddmStructureLayout";
	private static final String _SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE = "SELECT COUNT(ddmStructureLayout) FROM DDMStructureLayout ddmStructureLayout WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureLayout.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructureLayout exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructureLayout exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMStructureLayoutPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final DDMStructureLayout _nullDDMStructureLayout = new DDMStructureLayoutImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMStructureLayout> toCacheModel() {
				return _nullDDMStructureLayoutCacheModel;
			}
		};

	private static final CacheModel<DDMStructureLayout> _nullDDMStructureLayoutCacheModel =
		new CacheModel<DDMStructureLayout>() {
			@Override
			public DDMStructureLayout toEntityModel() {
				return _nullDDMStructureLayout;
			}
		};
}