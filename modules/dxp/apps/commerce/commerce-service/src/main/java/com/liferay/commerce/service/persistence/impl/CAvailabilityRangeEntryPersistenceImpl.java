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

import com.liferay.commerce.exception.NoSuchCAvailabilityRangeEntryException;
import com.liferay.commerce.model.CAvailabilityRangeEntry;
import com.liferay.commerce.model.impl.CAvailabilityRangeEntryImpl;
import com.liferay.commerce.model.impl.CAvailabilityRangeEntryModelImpl;
import com.liferay.commerce.service.persistence.CAvailabilityRangeEntryPersistence;

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
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the c availability range entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntryPersistence
 * @see com.liferay.commerce.service.persistence.CAvailabilityRangeEntryUtil
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntryPersistenceImpl extends BasePersistenceImpl<CAvailabilityRangeEntry>
	implements CAvailabilityRangeEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CAvailabilityRangeEntryUtil} to access the c availability range entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CAvailabilityRangeEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CAvailabilityRangeEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the c availability range entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c availability range entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @return the range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the c availability range entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c availability range entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
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

		List<CAvailabilityRangeEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CAvailabilityRangeEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CAvailabilityRangeEntry cAvailabilityRangeEntry : list) {
					if (!Objects.equals(uuid, cAvailabilityRangeEntry.getUuid())) {
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

			query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

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
				query.append(CAvailabilityRangeEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
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
	 * Returns the first c availability range entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByUuid_First(String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (cAvailabilityRangeEntry != null) {
			return cAvailabilityRangeEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
	}

	/**
	 * Returns the first c availability range entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByUuid_First(String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		List<CAvailabilityRangeEntry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c availability range entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByUuid_Last(String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cAvailabilityRangeEntry != null) {
			return cAvailabilityRangeEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
	}

	/**
	 * Returns the last c availability range entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CAvailabilityRangeEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c availability range entries before and after the current c availability range entry in the ordered set where uuid = &#63;.
	 *
	 * @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry[] findByUuid_PrevAndNext(
		long CAvailabilityRangeEntryId, String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = findByPrimaryKey(CAvailabilityRangeEntryId);

		Session session = null;

		try {
			session = openSession();

			CAvailabilityRangeEntry[] array = new CAvailabilityRangeEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cAvailabilityRangeEntry,
					uuid, orderByComparator, true);

			array[1] = cAvailabilityRangeEntry;

			array[2] = getByUuid_PrevAndNext(session, cAvailabilityRangeEntry,
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

	protected CAvailabilityRangeEntry getByUuid_PrevAndNext(Session session,
		CAvailabilityRangeEntry cAvailabilityRangeEntry, String uuid,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

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
			query.append(CAvailabilityRangeEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cAvailabilityRangeEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CAvailabilityRangeEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c availability range entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CAvailabilityRangeEntry cAvailabilityRangeEntry : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cAvailabilityRangeEntry);
		}
	}

	/**
	 * Returns the number of c availability range entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching c availability range entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CAVAILABILITYRANGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cAvailabilityRangeEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cAvailabilityRangeEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cAvailabilityRangeEntry.uuid IS NULL OR cAvailabilityRangeEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CAvailabilityRangeEntryModelImpl.UUID_COLUMN_BITMASK |
			CAvailabilityRangeEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the c availability range entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByUUID_G(uuid,
				groupId);

		if (cAvailabilityRangeEntry == null) {
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

			throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
		}

		return cAvailabilityRangeEntry;
	}

	/**
	 * Returns the c availability range entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the c availability range entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CAvailabilityRangeEntry) {
			CAvailabilityRangeEntry cAvailabilityRangeEntry = (CAvailabilityRangeEntry)result;

			if (!Objects.equals(uuid, cAvailabilityRangeEntry.getUuid()) ||
					(groupId != cAvailabilityRangeEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

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

				List<CAvailabilityRangeEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CAvailabilityRangeEntry cAvailabilityRangeEntry = list.get(0);

					result = cAvailabilityRangeEntry;

					cacheResult(cAvailabilityRangeEntry);

					if ((cAvailabilityRangeEntry.getUuid() == null) ||
							!cAvailabilityRangeEntry.getUuid().equals(uuid) ||
							(cAvailabilityRangeEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cAvailabilityRangeEntry);
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
			return (CAvailabilityRangeEntry)result;
		}
	}

	/**
	 * Removes the c availability range entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the c availability range entry that was removed
	 */
	@Override
	public CAvailabilityRangeEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = findByUUID_G(uuid,
				groupId);

		return remove(cAvailabilityRangeEntry);
	}

	/**
	 * Returns the number of c availability range entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching c availability range entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CAVAILABILITYRANGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cAvailabilityRangeEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cAvailabilityRangeEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cAvailabilityRangeEntry.uuid IS NULL OR cAvailabilityRangeEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cAvailabilityRangeEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CAvailabilityRangeEntryModelImpl.UUID_COLUMN_BITMASK |
			CAvailabilityRangeEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the c availability range entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @return the range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c availability range entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
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

		List<CAvailabilityRangeEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CAvailabilityRangeEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CAvailabilityRangeEntry cAvailabilityRangeEntry : list) {
					if (!Objects.equals(uuid, cAvailabilityRangeEntry.getUuid()) ||
							(companyId != cAvailabilityRangeEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

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
				query.append(CAvailabilityRangeEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
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
	 * Returns the first c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cAvailabilityRangeEntry != null) {
			return cAvailabilityRangeEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
	}

	/**
	 * Returns the first c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		List<CAvailabilityRangeEntry> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cAvailabilityRangeEntry != null) {
			return cAvailabilityRangeEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
	}

	/**
	 * Returns the last c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CAvailabilityRangeEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c availability range entries before and after the current c availability range entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry[] findByUuid_C_PrevAndNext(
		long CAvailabilityRangeEntryId, String uuid, long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = findByPrimaryKey(CAvailabilityRangeEntryId);

		Session session = null;

		try {
			session = openSession();

			CAvailabilityRangeEntry[] array = new CAvailabilityRangeEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					cAvailabilityRangeEntry, uuid, companyId,
					orderByComparator, true);

			array[1] = cAvailabilityRangeEntry;

			array[2] = getByUuid_C_PrevAndNext(session,
					cAvailabilityRangeEntry, uuid, companyId,
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

	protected CAvailabilityRangeEntry getByUuid_C_PrevAndNext(Session session,
		CAvailabilityRangeEntry cAvailabilityRangeEntry, String uuid,
		long companyId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

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
			query.append(CAvailabilityRangeEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cAvailabilityRangeEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CAvailabilityRangeEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c availability range entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CAvailabilityRangeEntry cAvailabilityRangeEntry : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cAvailabilityRangeEntry);
		}
	}

	/**
	 * Returns the number of c availability range entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching c availability range entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CAVAILABILITYRANGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cAvailabilityRangeEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cAvailabilityRangeEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cAvailabilityRangeEntry.uuid IS NULL OR cAvailabilityRangeEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cAvailabilityRangeEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CAvailabilityRangeEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the c availability range entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c availability range entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @return the range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the c availability range entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c availability range entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
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

		List<CAvailabilityRangeEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CAvailabilityRangeEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CAvailabilityRangeEntry cAvailabilityRangeEntry : list) {
					if ((groupId != cAvailabilityRangeEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CAvailabilityRangeEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
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
	 * Returns the first c availability range entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByGroupId_First(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (cAvailabilityRangeEntry != null) {
			return cAvailabilityRangeEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
	}

	/**
	 * Returns the first c availability range entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		List<CAvailabilityRangeEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c availability range entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByGroupId_Last(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (cAvailabilityRangeEntry != null) {
			return cAvailabilityRangeEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
	}

	/**
	 * Returns the last c availability range entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CAvailabilityRangeEntry> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c availability range entries before and after the current c availability range entry in the ordered set where groupId = &#63;.
	 *
	 * @param CAvailabilityRangeEntryId the primary key of the current c availability range entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry[] findByGroupId_PrevAndNext(
		long CAvailabilityRangeEntryId, long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = findByPrimaryKey(CAvailabilityRangeEntryId);

		Session session = null;

		try {
			session = openSession();

			CAvailabilityRangeEntry[] array = new CAvailabilityRangeEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					cAvailabilityRangeEntry, groupId, orderByComparator, true);

			array[1] = cAvailabilityRangeEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					cAvailabilityRangeEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CAvailabilityRangeEntry getByGroupId_PrevAndNext(
		Session session, CAvailabilityRangeEntry cAvailabilityRangeEntry,
		long groupId,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

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
			query.append(CAvailabilityRangeEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cAvailabilityRangeEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CAvailabilityRangeEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c availability range entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CAvailabilityRangeEntry cAvailabilityRangeEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cAvailabilityRangeEntry);
		}
	}

	/**
	 * Returns the number of c availability range entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching c availability range entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CAVAILABILITYRANGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cAvailabilityRangeEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CAvailabilityRangeEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CAvailabilityRangeEntryModelImpl.CPDEFINITIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByG_C(long groupId, long CPDefinitionId)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByG_C(groupId,
				CPDefinitionId);

		if (cAvailabilityRangeEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", CPDefinitionId=");
			msg.append(CPDefinitionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
		}

		return cAvailabilityRangeEntry;
	}

	/**
	 * Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByG_C(long groupId, long CPDefinitionId) {
		return fetchByG_C(groupId, CPDefinitionId, true);
	}

	/**
	 * Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByG_C(long groupId,
		long CPDefinitionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, CPDefinitionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C,
					finderArgs, this);
		}

		if (result instanceof CAvailabilityRangeEntry) {
			CAvailabilityRangeEntry cAvailabilityRangeEntry = (CAvailabilityRangeEntry)result;

			if ((groupId != cAvailabilityRangeEntry.getGroupId()) ||
					(CPDefinitionId != cAvailabilityRangeEntry.getCPDefinitionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(CPDefinitionId);

				List<CAvailabilityRangeEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C, finderArgs,
						list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"CAvailabilityRangeEntryPersistenceImpl.fetchByG_C(long, long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CAvailabilityRangeEntry cAvailabilityRangeEntry = list.get(0);

					result = cAvailabilityRangeEntry;

					cacheResult(cAvailabilityRangeEntry);

					if ((cAvailabilityRangeEntry.getGroupId() != groupId) ||
							(cAvailabilityRangeEntry.getCPDefinitionId() != CPDefinitionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C,
							finderArgs, cAvailabilityRangeEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C, finderArgs);

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
			return (CAvailabilityRangeEntry)result;
		}
	}

	/**
	 * Removes the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the c availability range entry that was removed
	 */
	@Override
	public CAvailabilityRangeEntry removeByG_C(long groupId, long CPDefinitionId)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = findByG_C(groupId,
				CPDefinitionId);

		return remove(cAvailabilityRangeEntry);
	}

	/**
	 * Returns the number of c availability range entries where groupId = &#63; and CPDefinitionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching c availability range entries
	 */
	@Override
	public int countByG_C(long groupId, long CPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, CPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CAVAILABILITYRANGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(CPDefinitionId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "cAvailabilityRangeEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CPDEFINITIONID_2 = "cAvailabilityRangeEntry.CPDefinitionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CAvailabilityRangeEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CAvailabilityRangeEntryModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CAvailabilityRangeEntryModelImpl.COMMERCEAVAILABILITYRANGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @param commerceAvailabilityRangeId the commerce availability range ID
	 * @return the matching c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByG_C_C(groupId,
				CPDefinitionId, commerceAvailabilityRangeId);

		if (cAvailabilityRangeEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", CPDefinitionId=");
			msg.append(CPDefinitionId);

			msg.append(", commerceAvailabilityRangeId=");
			msg.append(commerceAvailabilityRangeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCAvailabilityRangeEntryException(msg.toString());
		}

		return cAvailabilityRangeEntry;
	}

	/**
	 * Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @param commerceAvailabilityRangeId the commerce availability range ID
	 * @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId) {
		return fetchByG_C_C(groupId, CPDefinitionId,
			commerceAvailabilityRangeId, true);
	}

	/**
	 * Returns the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @param commerceAvailabilityRangeId the commerce availability range ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching c availability range entry, or <code>null</code> if a matching c availability range entry could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, CPDefinitionId, commerceAvailabilityRangeId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C,
					finderArgs, this);
		}

		if (result instanceof CAvailabilityRangeEntry) {
			CAvailabilityRangeEntry cAvailabilityRangeEntry = (CAvailabilityRangeEntry)result;

			if ((groupId != cAvailabilityRangeEntry.getGroupId()) ||
					(CPDefinitionId != cAvailabilityRangeEntry.getCPDefinitionId()) ||
					(commerceAvailabilityRangeId != cAvailabilityRangeEntry.getCommerceAvailabilityRangeId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_G_C_C_COMMERCEAVAILABILITYRANGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(CPDefinitionId);

				qPos.add(commerceAvailabilityRangeId);

				List<CAvailabilityRangeEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, list);
				}
				else {
					CAvailabilityRangeEntry cAvailabilityRangeEntry = list.get(0);

					result = cAvailabilityRangeEntry;

					cacheResult(cAvailabilityRangeEntry);

					if ((cAvailabilityRangeEntry.getGroupId() != groupId) ||
							(cAvailabilityRangeEntry.getCPDefinitionId() != CPDefinitionId) ||
							(cAvailabilityRangeEntry.getCommerceAvailabilityRangeId() != commerceAvailabilityRangeId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
							finderArgs, cAvailabilityRangeEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, finderArgs);

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
			return (CAvailabilityRangeEntry)result;
		}
	}

	/**
	 * Removes the c availability range entry where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @param commerceAvailabilityRangeId the commerce availability range ID
	 * @return the c availability range entry that was removed
	 */
	@Override
	public CAvailabilityRangeEntry removeByG_C_C(long groupId,
		long CPDefinitionId, long commerceAvailabilityRangeId)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = findByG_C_C(groupId,
				CPDefinitionId, commerceAvailabilityRangeId);

		return remove(cAvailabilityRangeEntry);
	}

	/**
	 * Returns the number of c availability range entries where groupId = &#63; and CPDefinitionId = &#63; and commerceAvailabilityRangeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param CPDefinitionId the cp definition ID
	 * @param commerceAvailabilityRangeId the commerce availability range ID
	 * @return the number of matching c availability range entries
	 */
	@Override
	public int countByG_C_C(long groupId, long CPDefinitionId,
		long commerceAvailabilityRangeId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] {
				groupId, CPDefinitionId, commerceAvailabilityRangeId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CAVAILABILITYRANGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_G_C_C_COMMERCEAVAILABILITYRANGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(CPDefinitionId);

				qPos.add(commerceAvailabilityRangeId);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "cAvailabilityRangeEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CPDEFINITIONID_2 = "cAvailabilityRangeEntry.CPDefinitionId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_COMMERCEAVAILABILITYRANGEID_2 =
		"cAvailabilityRangeEntry.commerceAvailabilityRangeId = ?";

	public CAvailabilityRangeEntryPersistenceImpl() {
		setModelClass(CAvailabilityRangeEntry.class);

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
	 * Caches the c availability range entry in the entity cache if it is enabled.
	 *
	 * @param cAvailabilityRangeEntry the c availability range entry
	 */
	@Override
	public void cacheResult(CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		entityCache.putResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			cAvailabilityRangeEntry.getPrimaryKey(), cAvailabilityRangeEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cAvailabilityRangeEntry.getUuid(),
				cAvailabilityRangeEntry.getGroupId()
			}, cAvailabilityRangeEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C,
			new Object[] {
				cAvailabilityRangeEntry.getGroupId(),
				cAvailabilityRangeEntry.getCPDefinitionId()
			}, cAvailabilityRangeEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				cAvailabilityRangeEntry.getGroupId(),
				cAvailabilityRangeEntry.getCPDefinitionId(),
				cAvailabilityRangeEntry.getCommerceAvailabilityRangeId()
			}, cAvailabilityRangeEntry);

		cAvailabilityRangeEntry.resetOriginalValues();
	}

	/**
	 * Caches the c availability range entries in the entity cache if it is enabled.
	 *
	 * @param cAvailabilityRangeEntries the c availability range entries
	 */
	@Override
	public void cacheResult(
		List<CAvailabilityRangeEntry> cAvailabilityRangeEntries) {
		for (CAvailabilityRangeEntry cAvailabilityRangeEntry : cAvailabilityRangeEntries) {
			if (entityCache.getResult(
						CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
						CAvailabilityRangeEntryImpl.class,
						cAvailabilityRangeEntry.getPrimaryKey()) == null) {
				cacheResult(cAvailabilityRangeEntry);
			}
			else {
				cAvailabilityRangeEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all c availability range entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CAvailabilityRangeEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the c availability range entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		entityCache.removeResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			cAvailabilityRangeEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CAvailabilityRangeEntryModelImpl)cAvailabilityRangeEntry,
			true);
	}

	@Override
	public void clearCache(
		List<CAvailabilityRangeEntry> cAvailabilityRangeEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CAvailabilityRangeEntry cAvailabilityRangeEntry : cAvailabilityRangeEntries) {
			entityCache.removeResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
				CAvailabilityRangeEntryImpl.class,
				cAvailabilityRangeEntry.getPrimaryKey());

			clearUniqueFindersCache((CAvailabilityRangeEntryModelImpl)cAvailabilityRangeEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CAvailabilityRangeEntryModelImpl cAvailabilityRangeEntryModelImpl) {
		Object[] args = new Object[] {
				cAvailabilityRangeEntryModelImpl.getUuid(),
				cAvailabilityRangeEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cAvailabilityRangeEntryModelImpl, false);

		args = new Object[] {
				cAvailabilityRangeEntryModelImpl.getGroupId(),
				cAvailabilityRangeEntryModelImpl.getCPDefinitionId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C, args,
			cAvailabilityRangeEntryModelImpl, false);

		args = new Object[] {
				cAvailabilityRangeEntryModelImpl.getGroupId(),
				cAvailabilityRangeEntryModelImpl.getCPDefinitionId(),
				cAvailabilityRangeEntryModelImpl.getCommerceAvailabilityRangeId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C, args,
			cAvailabilityRangeEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CAvailabilityRangeEntryModelImpl cAvailabilityRangeEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getUuid(),
					cAvailabilityRangeEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cAvailabilityRangeEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getOriginalUuid(),
					cAvailabilityRangeEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getGroupId(),
					cAvailabilityRangeEntryModelImpl.getCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C, args);
		}

		if ((cAvailabilityRangeEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getOriginalGroupId(),
					cAvailabilityRangeEntryModelImpl.getOriginalCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getGroupId(),
					cAvailabilityRangeEntryModelImpl.getCPDefinitionId(),
					cAvailabilityRangeEntryModelImpl.getCommerceAvailabilityRangeId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}

		if ((cAvailabilityRangeEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getOriginalGroupId(),
					cAvailabilityRangeEntryModelImpl.getOriginalCPDefinitionId(),
					cAvailabilityRangeEntryModelImpl.getOriginalCommerceAvailabilityRangeId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}
	}

	/**
	 * Creates a new c availability range entry with the primary key. Does not add the c availability range entry to the database.
	 *
	 * @param CAvailabilityRangeEntryId the primary key for the new c availability range entry
	 * @return the new c availability range entry
	 */
	@Override
	public CAvailabilityRangeEntry create(long CAvailabilityRangeEntryId) {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = new CAvailabilityRangeEntryImpl();

		cAvailabilityRangeEntry.setNew(true);
		cAvailabilityRangeEntry.setPrimaryKey(CAvailabilityRangeEntryId);

		String uuid = PortalUUIDUtil.generate();

		cAvailabilityRangeEntry.setUuid(uuid);

		cAvailabilityRangeEntry.setCompanyId(companyProvider.getCompanyId());

		return cAvailabilityRangeEntry;
	}

	/**
	 * Removes the c availability range entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	 * @return the c availability range entry that was removed
	 * @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry remove(long CAvailabilityRangeEntryId)
		throws NoSuchCAvailabilityRangeEntryException {
		return remove((Serializable)CAvailabilityRangeEntryId);
	}

	/**
	 * Removes the c availability range entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the c availability range entry
	 * @return the c availability range entry that was removed
	 * @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry remove(Serializable primaryKey)
		throws NoSuchCAvailabilityRangeEntryException {
		Session session = null;

		try {
			session = openSession();

			CAvailabilityRangeEntry cAvailabilityRangeEntry = (CAvailabilityRangeEntry)session.get(CAvailabilityRangeEntryImpl.class,
					primaryKey);

			if (cAvailabilityRangeEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCAvailabilityRangeEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cAvailabilityRangeEntry);
		}
		catch (NoSuchCAvailabilityRangeEntryException nsee) {
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
	protected CAvailabilityRangeEntry removeImpl(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		cAvailabilityRangeEntry = toUnwrappedModel(cAvailabilityRangeEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cAvailabilityRangeEntry)) {
				cAvailabilityRangeEntry = (CAvailabilityRangeEntry)session.get(CAvailabilityRangeEntryImpl.class,
						cAvailabilityRangeEntry.getPrimaryKeyObj());
			}

			if (cAvailabilityRangeEntry != null) {
				session.delete(cAvailabilityRangeEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cAvailabilityRangeEntry != null) {
			clearCache(cAvailabilityRangeEntry);
		}

		return cAvailabilityRangeEntry;
	}

	@Override
	public CAvailabilityRangeEntry updateImpl(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		cAvailabilityRangeEntry = toUnwrappedModel(cAvailabilityRangeEntry);

		boolean isNew = cAvailabilityRangeEntry.isNew();

		CAvailabilityRangeEntryModelImpl cAvailabilityRangeEntryModelImpl = (CAvailabilityRangeEntryModelImpl)cAvailabilityRangeEntry;

		if (Validator.isNull(cAvailabilityRangeEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cAvailabilityRangeEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cAvailabilityRangeEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cAvailabilityRangeEntry.setCreateDate(now);
			}
			else {
				cAvailabilityRangeEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cAvailabilityRangeEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cAvailabilityRangeEntry.setModifiedDate(now);
			}
			else {
				cAvailabilityRangeEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cAvailabilityRangeEntry.isNew()) {
				session.save(cAvailabilityRangeEntry);

				cAvailabilityRangeEntry.setNew(false);
			}
			else {
				cAvailabilityRangeEntry = (CAvailabilityRangeEntry)session.merge(cAvailabilityRangeEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CAvailabilityRangeEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cAvailabilityRangeEntryModelImpl.getUuid(),
					cAvailabilityRangeEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { cAvailabilityRangeEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cAvailabilityRangeEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cAvailabilityRangeEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cAvailabilityRangeEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cAvailabilityRangeEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cAvailabilityRangeEntryModelImpl.getOriginalUuid(),
						cAvailabilityRangeEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cAvailabilityRangeEntryModelImpl.getUuid(),
						cAvailabilityRangeEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cAvailabilityRangeEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cAvailabilityRangeEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						cAvailabilityRangeEntryModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
			CAvailabilityRangeEntryImpl.class,
			cAvailabilityRangeEntry.getPrimaryKey(), cAvailabilityRangeEntry,
			false);

		clearUniqueFindersCache(cAvailabilityRangeEntryModelImpl, false);
		cacheUniqueFindersCache(cAvailabilityRangeEntryModelImpl);

		cAvailabilityRangeEntry.resetOriginalValues();

		return cAvailabilityRangeEntry;
	}

	protected CAvailabilityRangeEntry toUnwrappedModel(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {
		if (cAvailabilityRangeEntry instanceof CAvailabilityRangeEntryImpl) {
			return cAvailabilityRangeEntry;
		}

		CAvailabilityRangeEntryImpl cAvailabilityRangeEntryImpl = new CAvailabilityRangeEntryImpl();

		cAvailabilityRangeEntryImpl.setNew(cAvailabilityRangeEntry.isNew());
		cAvailabilityRangeEntryImpl.setPrimaryKey(cAvailabilityRangeEntry.getPrimaryKey());

		cAvailabilityRangeEntryImpl.setUuid(cAvailabilityRangeEntry.getUuid());
		cAvailabilityRangeEntryImpl.setCAvailabilityRangeEntryId(cAvailabilityRangeEntry.getCAvailabilityRangeEntryId());
		cAvailabilityRangeEntryImpl.setGroupId(cAvailabilityRangeEntry.getGroupId());
		cAvailabilityRangeEntryImpl.setCompanyId(cAvailabilityRangeEntry.getCompanyId());
		cAvailabilityRangeEntryImpl.setUserId(cAvailabilityRangeEntry.getUserId());
		cAvailabilityRangeEntryImpl.setUserName(cAvailabilityRangeEntry.getUserName());
		cAvailabilityRangeEntryImpl.setCreateDate(cAvailabilityRangeEntry.getCreateDate());
		cAvailabilityRangeEntryImpl.setModifiedDate(cAvailabilityRangeEntry.getModifiedDate());
		cAvailabilityRangeEntryImpl.setCPDefinitionId(cAvailabilityRangeEntry.getCPDefinitionId());
		cAvailabilityRangeEntryImpl.setCommerceAvailabilityRangeId(cAvailabilityRangeEntry.getCommerceAvailabilityRangeId());
		cAvailabilityRangeEntryImpl.setLastPublishDate(cAvailabilityRangeEntry.getLastPublishDate());

		return cAvailabilityRangeEntryImpl;
	}

	/**
	 * Returns the c availability range entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the c availability range entry
	 * @return the c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCAvailabilityRangeEntryException {
		CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByPrimaryKey(primaryKey);

		if (cAvailabilityRangeEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCAvailabilityRangeEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cAvailabilityRangeEntry;
	}

	/**
	 * Returns the c availability range entry with the primary key or throws a {@link NoSuchCAvailabilityRangeEntryException} if it could not be found.
	 *
	 * @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	 * @return the c availability range entry
	 * @throws NoSuchCAvailabilityRangeEntryException if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry findByPrimaryKey(
		long CAvailabilityRangeEntryId)
		throws NoSuchCAvailabilityRangeEntryException {
		return findByPrimaryKey((Serializable)CAvailabilityRangeEntryId);
	}

	/**
	 * Returns the c availability range entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the c availability range entry
	 * @return the c availability range entry, or <code>null</code> if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
				CAvailabilityRangeEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CAvailabilityRangeEntry cAvailabilityRangeEntry = (CAvailabilityRangeEntry)serializable;

		if (cAvailabilityRangeEntry == null) {
			Session session = null;

			try {
				session = openSession();

				cAvailabilityRangeEntry = (CAvailabilityRangeEntry)session.get(CAvailabilityRangeEntryImpl.class,
						primaryKey);

				if (cAvailabilityRangeEntry != null) {
					cacheResult(cAvailabilityRangeEntry);
				}
				else {
					entityCache.putResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
						CAvailabilityRangeEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
					CAvailabilityRangeEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cAvailabilityRangeEntry;
	}

	/**
	 * Returns the c availability range entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CAvailabilityRangeEntryId the primary key of the c availability range entry
	 * @return the c availability range entry, or <code>null</code> if a c availability range entry with the primary key could not be found
	 */
	@Override
	public CAvailabilityRangeEntry fetchByPrimaryKey(
		long CAvailabilityRangeEntryId) {
		return fetchByPrimaryKey((Serializable)CAvailabilityRangeEntryId);
	}

	@Override
	public Map<Serializable, CAvailabilityRangeEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CAvailabilityRangeEntry> map = new HashMap<Serializable, CAvailabilityRangeEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CAvailabilityRangeEntry cAvailabilityRangeEntry = fetchByPrimaryKey(primaryKey);

			if (cAvailabilityRangeEntry != null) {
				map.put(primaryKey, cAvailabilityRangeEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
					CAvailabilityRangeEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CAvailabilityRangeEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE_PKS_IN);

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

			for (CAvailabilityRangeEntry cAvailabilityRangeEntry : (List<CAvailabilityRangeEntry>)q.list()) {
				map.put(cAvailabilityRangeEntry.getPrimaryKeyObj(),
					cAvailabilityRangeEntry);

				cacheResult(cAvailabilityRangeEntry);

				uncachedPrimaryKeys.remove(cAvailabilityRangeEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CAvailabilityRangeEntryModelImpl.ENTITY_CACHE_ENABLED,
					CAvailabilityRangeEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the c availability range entries.
	 *
	 * @return the c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c availability range entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @return the range of c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the c availability range entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findAll(int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c availability range entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CAvailabilityRangeEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of c availability range entries
	 * @param end the upper bound of the range of c availability range entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of c availability range entries
	 */
	@Override
	public List<CAvailabilityRangeEntry> findAll(int start, int end,
		OrderByComparator<CAvailabilityRangeEntry> orderByComparator,
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

		List<CAvailabilityRangeEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CAvailabilityRangeEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CAVAILABILITYRANGEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CAVAILABILITYRANGEENTRY;

				if (pagination) {
					sql = sql.concat(CAvailabilityRangeEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CAvailabilityRangeEntry>)QueryUtil.list(q,
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
	 * Removes all the c availability range entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CAvailabilityRangeEntry cAvailabilityRangeEntry : findAll()) {
			remove(cAvailabilityRangeEntry);
		}
	}

	/**
	 * Returns the number of c availability range entries.
	 *
	 * @return the number of c availability range entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CAVAILABILITYRANGEENTRY);

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
		return CAvailabilityRangeEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the c availability range entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CAvailabilityRangeEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_CAVAILABILITYRANGEENTRY = "SELECT cAvailabilityRangeEntry FROM CAvailabilityRangeEntry cAvailabilityRangeEntry";
	private static final String _SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE_PKS_IN =
		"SELECT cAvailabilityRangeEntry FROM CAvailabilityRangeEntry cAvailabilityRangeEntry WHERE CAvailabilityRangeEntryId IN (";
	private static final String _SQL_SELECT_CAVAILABILITYRANGEENTRY_WHERE = "SELECT cAvailabilityRangeEntry FROM CAvailabilityRangeEntry cAvailabilityRangeEntry WHERE ";
	private static final String _SQL_COUNT_CAVAILABILITYRANGEENTRY = "SELECT COUNT(cAvailabilityRangeEntry) FROM CAvailabilityRangeEntry cAvailabilityRangeEntry";
	private static final String _SQL_COUNT_CAVAILABILITYRANGEENTRY_WHERE = "SELECT COUNT(cAvailabilityRangeEntry) FROM CAvailabilityRangeEntry cAvailabilityRangeEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cAvailabilityRangeEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CAvailabilityRangeEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CAvailabilityRangeEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CAvailabilityRangeEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}