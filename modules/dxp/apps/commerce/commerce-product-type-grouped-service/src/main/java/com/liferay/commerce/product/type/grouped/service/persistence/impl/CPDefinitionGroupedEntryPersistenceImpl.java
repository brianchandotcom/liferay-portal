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

package com.liferay.commerce.product.type.grouped.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryImpl;
import com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl;
import com.liferay.commerce.product.type.grouped.service.persistence.CPDefinitionGroupedEntryPersistence;

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
 * The persistence implementation for the cp definition grouped entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryPersistence
 * @see com.liferay.commerce.product.type.grouped.service.persistence.CPDefinitionGroupedEntryUtil
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntryPersistenceImpl extends BasePersistenceImpl<CPDefinitionGroupedEntry>
	implements CPDefinitionGroupedEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionGroupedEntryUtil} to access the cp definition grouped entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionGroupedEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPDefinitionGroupedEntryModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionGroupedEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp definition grouped entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		List<CPDefinitionGroupedEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : list) {
					if (!Objects.equals(uuid, cpDefinitionGroupedEntry.getUuid())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
				query.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
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
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
	}

	/**
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		List<CPDefinitionGroupedEntry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionGroupedEntry> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry[] findByUuid_PrevAndNext(
		long CPDefinitionGroupedEntryId, String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByPrimaryKey(CPDefinitionGroupedEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry[] array = new CPDefinitionGroupedEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpDefinitionGroupedEntry,
					uuid, orderByComparator, true);

			array[1] = cpDefinitionGroupedEntry;

			array[2] = getByUuid_PrevAndNext(session, cpDefinitionGroupedEntry,
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

	protected CPDefinitionGroupedEntry getByUuid_PrevAndNext(Session session,
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry, String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
			query.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionGroupedEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionGroupedEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition grouped entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpDefinitionGroupedEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpDefinitionGroupedEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpDefinitionGroupedEntry.uuid IS NULL OR cpDefinitionGroupedEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionGroupedEntryModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionGroupedEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUUID_G(uuid,
				groupId);

		if (cpDefinitionGroupedEntry == null) {
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

			throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
		}

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionGroupedEntry) {
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)result;

			if (!Objects.equals(uuid, cpDefinitionGroupedEntry.getUuid()) ||
					(groupId != cpDefinitionGroupedEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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

				List<CPDefinitionGroupedEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPDefinitionGroupedEntry cpDefinitionGroupedEntry = list.get(0);

					result = cpDefinitionGroupedEntry;

					cacheResult(cpDefinitionGroupedEntry);

					if ((cpDefinitionGroupedEntry.getUuid() == null) ||
							!cpDefinitionGroupedEntry.getUuid().equals(uuid) ||
							(cpDefinitionGroupedEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpDefinitionGroupedEntry);
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
			return (CPDefinitionGroupedEntry)result;
		}
	}

	/**
	 * Removes the cp definition grouped entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition grouped entry that was removed
	 */
	@Override
	public CPDefinitionGroupedEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByUUID_G(uuid,
				groupId);

		return remove(cpDefinitionGroupedEntry);
	}

	/**
	 * Returns the number of cp definition grouped entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpDefinitionGroupedEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpDefinitionGroupedEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpDefinitionGroupedEntry.uuid IS NULL OR cpDefinitionGroupedEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpDefinitionGroupedEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionGroupedEntryModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionGroupedEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CPDefinitionGroupedEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		List<CPDefinitionGroupedEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : list) {
					if (!Objects.equals(uuid, cpDefinitionGroupedEntry.getUuid()) ||
							(companyId != cpDefinitionGroupedEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
				query.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
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
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
	}

	/**
	 * Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		List<CPDefinitionGroupedEntry> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionGroupedEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry[] findByUuid_C_PrevAndNext(
		long CPDefinitionGroupedEntryId, String uuid, long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByPrimaryKey(CPDefinitionGroupedEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry[] array = new CPDefinitionGroupedEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					cpDefinitionGroupedEntry, uuid, companyId,
					orderByComparator, true);

			array[1] = cpDefinitionGroupedEntry;

			array[2] = getByUuid_C_PrevAndNext(session,
					cpDefinitionGroupedEntry, uuid, companyId,
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

	protected CPDefinitionGroupedEntry getByUuid_C_PrevAndNext(
		Session session, CPDefinitionGroupedEntry cpDefinitionGroupedEntry,
		String uuid, long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

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
			query.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionGroupedEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionGroupedEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition grouped entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpDefinitionGroupedEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpDefinitionGroupedEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpDefinitionGroupedEntry.uuid IS NULL OR cpDefinitionGroupedEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpDefinitionGroupedEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPDefinitionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID =
		new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPDefinitionId",
			new String[] { Long.class.getName() },
			CPDefinitionGroupedEntryModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CPDefinitionGroupedEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId) {
		return findByCPDefinitionId(CPDefinitionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return findByCPDefinitionId(CPDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID;
			finderArgs = new Object[] { CPDefinitionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CPDEFINITIONID;
			finderArgs = new Object[] {
					CPDefinitionId,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionGroupedEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : list) {
					if ((CPDefinitionId != cpDefinitionGroupedEntry.getCPDefinitionId())) {
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

			query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				if (!pagination) {
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
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
	 * Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByCPDefinitionId_First(CPDefinitionId,
				orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
	}

	/**
	 * Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		List<CPDefinitionGroupedEntry> list = findByCPDefinitionId(CPDefinitionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByCPDefinitionId_Last(CPDefinitionId,
				orderByComparator);

		if (cpDefinitionGroupedEntry != null) {
			return cpDefinitionGroupedEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("CPDefinitionId=");
		msg.append(CPDefinitionId);

		msg.append("}");

		throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
	}

	/**
	 * Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		int count = countByCPDefinitionId(CPDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionGroupedEntry> list = findByCPDefinitionId(CPDefinitionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	 * @param CPDefinitionId the cp definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionGroupedEntryId, long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByPrimaryKey(CPDefinitionGroupedEntryId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry[] array = new CPDefinitionGroupedEntryImpl[3];

			array[0] = getByCPDefinitionId_PrevAndNext(session,
					cpDefinitionGroupedEntry, CPDefinitionId,
					orderByComparator, true);

			array[1] = cpDefinitionGroupedEntry;

			array[2] = getByCPDefinitionId_PrevAndNext(session,
					cpDefinitionGroupedEntry, CPDefinitionId,
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

	protected CPDefinitionGroupedEntry getByCPDefinitionId_PrevAndNext(
		Session session, CPDefinitionGroupedEntry cpDefinitionGroupedEntry,
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

		query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

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
			query.append(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(CPDefinitionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionGroupedEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionGroupedEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition grouped entries where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 */
	@Override
	public void removeByCPDefinitionId(long CPDefinitionId) {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : findByCPDefinitionId(
				CPDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID;

		Object[] finderArgs = new Object[] { CPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 = "cpDefinitionGroupedEntry.CPDefinitionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_E = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_E",
			new String[] { Long.class.getName(), Long.class.getName() },
			CPDefinitionGroupedEntryModelImpl.CPDEFINITIONID_COLUMN_BITMASK |
			CPDefinitionGroupedEntryModelImpl.ENTRYCPDEFINITIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_E = new FinderPath(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_E",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCPDefinitionId the entry cp definition ID
	 * @return the matching cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByC_E(long CPDefinitionId,
		long entryCPDefinitionId)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByC_E(CPDefinitionId,
				entryCPDefinitionId);

		if (cpDefinitionGroupedEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("CPDefinitionId=");
			msg.append(CPDefinitionId);

			msg.append(", entryCPDefinitionId=");
			msg.append(entryCPDefinitionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionGroupedEntryException(msg.toString());
		}

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCPDefinitionId the entry cp definition ID
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByC_E(long CPDefinitionId,
		long entryCPDefinitionId) {
		return fetchByC_E(CPDefinitionId, entryCPDefinitionId, true);
	}

	/**
	 * Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCPDefinitionId the entry cp definition ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByC_E(long CPDefinitionId,
		long entryCPDefinitionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { CPDefinitionId, entryCPDefinitionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_E,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionGroupedEntry) {
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)result;

			if ((CPDefinitionId != cpDefinitionGroupedEntry.getCPDefinitionId()) ||
					(entryCPDefinitionId != cpDefinitionGroupedEntry.getEntryCPDefinitionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_E_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_C_E_ENTRYCPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				qPos.add(entryCPDefinitionId);

				List<CPDefinitionGroupedEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_E, finderArgs,
						list);
				}
				else {
					CPDefinitionGroupedEntry cpDefinitionGroupedEntry = list.get(0);

					result = cpDefinitionGroupedEntry;

					cacheResult(cpDefinitionGroupedEntry);

					if ((cpDefinitionGroupedEntry.getCPDefinitionId() != CPDefinitionId) ||
							(cpDefinitionGroupedEntry.getEntryCPDefinitionId() != entryCPDefinitionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_E,
							finderArgs, cpDefinitionGroupedEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_E, finderArgs);

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
			return (CPDefinitionGroupedEntry)result;
		}
	}

	/**
	 * Removes the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCPDefinitionId the entry cp definition ID
	 * @return the cp definition grouped entry that was removed
	 */
	@Override
	public CPDefinitionGroupedEntry removeByC_E(long CPDefinitionId,
		long entryCPDefinitionId)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = findByC_E(CPDefinitionId,
				entryCPDefinitionId);

		return remove(cpDefinitionGroupedEntry);
	}

	/**
	 * Returns the number of cp definition grouped entries where CPDefinitionId = &#63; and entryCPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param entryCPDefinitionId the entry cp definition ID
	 * @return the number of matching cp definition grouped entries
	 */
	@Override
	public int countByC_E(long CPDefinitionId, long entryCPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_E;

		Object[] finderArgs = new Object[] { CPDefinitionId, entryCPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_E_CPDEFINITIONID_2);

			query.append(_FINDER_COLUMN_C_E_ENTRYCPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				qPos.add(entryCPDefinitionId);

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

	private static final String _FINDER_COLUMN_C_E_CPDEFINITIONID_2 = "cpDefinitionGroupedEntry.CPDefinitionId = ? AND ";
	private static final String _FINDER_COLUMN_C_E_ENTRYCPDEFINITIONID_2 = "cpDefinitionGroupedEntry.entryCPDefinitionId = ?";

	public CPDefinitionGroupedEntryPersistenceImpl() {
		setModelClass(CPDefinitionGroupedEntry.class);

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
	 * Caches the cp definition grouped entry in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionGroupedEntry the cp definition grouped entry
	 */
	@Override
	public void cacheResult(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		entityCache.putResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			cpDefinitionGroupedEntry.getPrimaryKey(), cpDefinitionGroupedEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpDefinitionGroupedEntry.getUuid(),
				cpDefinitionGroupedEntry.getGroupId()
			}, cpDefinitionGroupedEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_E,
			new Object[] {
				cpDefinitionGroupedEntry.getCPDefinitionId(),
				cpDefinitionGroupedEntry.getEntryCPDefinitionId()
			}, cpDefinitionGroupedEntry);

		cpDefinitionGroupedEntry.resetOriginalValues();
	}

	/**
	 * Caches the cp definition grouped entries in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionGroupedEntries the cp definition grouped entries
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries) {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : cpDefinitionGroupedEntries) {
			if (entityCache.getResult(
						CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionGroupedEntryImpl.class,
						cpDefinitionGroupedEntry.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionGroupedEntry);
			}
			else {
				cpDefinitionGroupedEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition grouped entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionGroupedEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition grouped entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		entityCache.removeResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			cpDefinitionGroupedEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionGroupedEntryModelImpl)cpDefinitionGroupedEntry,
			true);
	}

	@Override
	public void clearCache(
		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : cpDefinitionGroupedEntries) {
			entityCache.removeResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionGroupedEntryImpl.class,
				cpDefinitionGroupedEntry.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionGroupedEntryModelImpl)cpDefinitionGroupedEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionGroupedEntryModelImpl cpDefinitionGroupedEntryModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionGroupedEntryModelImpl.getUuid(),
				cpDefinitionGroupedEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpDefinitionGroupedEntryModelImpl, false);

		args = new Object[] {
				cpDefinitionGroupedEntryModelImpl.getCPDefinitionId(),
				cpDefinitionGroupedEntryModelImpl.getEntryCPDefinitionId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_E, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_E, args,
			cpDefinitionGroupedEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionGroupedEntryModelImpl cpDefinitionGroupedEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionGroupedEntryModelImpl.getUuid(),
					cpDefinitionGroupedEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpDefinitionGroupedEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionGroupedEntryModelImpl.getOriginalUuid(),
					cpDefinitionGroupedEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionGroupedEntryModelImpl.getCPDefinitionId(),
					cpDefinitionGroupedEntryModelImpl.getEntryCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_E, args);
		}

		if ((cpDefinitionGroupedEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_E.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionGroupedEntryModelImpl.getOriginalCPDefinitionId(),
					cpDefinitionGroupedEntryModelImpl.getOriginalEntryCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_E, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_E, args);
		}
	}

	/**
	 * Creates a new cp definition grouped entry with the primary key. Does not add the cp definition grouped entry to the database.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key for the new cp definition grouped entry
	 * @return the new cp definition grouped entry
	 */
	@Override
	public CPDefinitionGroupedEntry create(long CPDefinitionGroupedEntryId) {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = new CPDefinitionGroupedEntryImpl();

		cpDefinitionGroupedEntry.setNew(true);
		cpDefinitionGroupedEntry.setPrimaryKey(CPDefinitionGroupedEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionGroupedEntry.setUuid(uuid);

		cpDefinitionGroupedEntry.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Removes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry that was removed
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry remove(long CPDefinitionGroupedEntryId)
		throws NoSuchCPDefinitionGroupedEntryException {
		return remove((Serializable)CPDefinitionGroupedEntryId);
	}

	/**
	 * Removes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry that was removed
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry remove(Serializable primaryKey)
		throws NoSuchCPDefinitionGroupedEntryException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionGroupedEntry cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)session.get(CPDefinitionGroupedEntryImpl.class,
					primaryKey);

			if (cpDefinitionGroupedEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionGroupedEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionGroupedEntry);
		}
		catch (NoSuchCPDefinitionGroupedEntryException nsee) {
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
	protected CPDefinitionGroupedEntry removeImpl(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionGroupedEntry)) {
				cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)session.get(CPDefinitionGroupedEntryImpl.class,
						cpDefinitionGroupedEntry.getPrimaryKeyObj());
			}

			if (cpDefinitionGroupedEntry != null) {
				session.delete(cpDefinitionGroupedEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionGroupedEntry != null) {
			clearCache(cpDefinitionGroupedEntry);
		}

		return cpDefinitionGroupedEntry;
	}

	@Override
	public CPDefinitionGroupedEntry updateImpl(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		boolean isNew = cpDefinitionGroupedEntry.isNew();

		if (!(cpDefinitionGroupedEntry instanceof CPDefinitionGroupedEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinitionGroupedEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpDefinitionGroupedEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionGroupedEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionGroupedEntry implementation " +
				cpDefinitionGroupedEntry.getClass());
		}

		CPDefinitionGroupedEntryModelImpl cpDefinitionGroupedEntryModelImpl = (CPDefinitionGroupedEntryModelImpl)cpDefinitionGroupedEntry;

		if (Validator.isNull(cpDefinitionGroupedEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionGroupedEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinitionGroupedEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionGroupedEntry.setCreateDate(now);
			}
			else {
				cpDefinitionGroupedEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpDefinitionGroupedEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionGroupedEntry.setModifiedDate(now);
			}
			else {
				cpDefinitionGroupedEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionGroupedEntry.isNew()) {
				session.save(cpDefinitionGroupedEntry);

				cpDefinitionGroupedEntry.setNew(false);
			}
			else {
				cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)session.merge(cpDefinitionGroupedEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionGroupedEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpDefinitionGroupedEntryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpDefinitionGroupedEntryModelImpl.getUuid(),
					cpDefinitionGroupedEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					cpDefinitionGroupedEntryModelImpl.getCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionGroupedEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionGroupedEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpDefinitionGroupedEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpDefinitionGroupedEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionGroupedEntryModelImpl.getOriginalUuid(),
						cpDefinitionGroupedEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpDefinitionGroupedEntryModelImpl.getUuid(),
						cpDefinitionGroupedEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpDefinitionGroupedEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionGroupedEntryModelImpl.getOriginalCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);

				args = new Object[] {
						cpDefinitionGroupedEntryModelImpl.getCPDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CPDEFINITIONID,
					args);
			}
		}

		entityCache.putResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionGroupedEntryImpl.class,
			cpDefinitionGroupedEntry.getPrimaryKey(), cpDefinitionGroupedEntry,
			false);

		clearUniqueFindersCache(cpDefinitionGroupedEntryModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionGroupedEntryModelImpl);

		cpDefinitionGroupedEntry.resetOriginalValues();

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionGroupedEntryException {
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionGroupedEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionGroupedEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry with the primary key or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry
	 * @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry findByPrimaryKey(
		long CPDefinitionGroupedEntryId)
		throws NoSuchCPDefinitionGroupedEntryException {
		return findByPrimaryKey((Serializable)CPDefinitionGroupedEntryId);
	}

	/**
	 * Returns the cp definition grouped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry, or <code>null</code> if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionGroupedEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)serializable;

		if (cpDefinitionGroupedEntry == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)session.get(CPDefinitionGroupedEntryImpl.class,
						primaryKey);

				if (cpDefinitionGroupedEntry != null) {
					cacheResult(cpDefinitionGroupedEntry);
				}
				else {
					entityCache.putResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionGroupedEntryImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionGroupedEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionGroupedEntry;
	}

	/**
	 * Returns the cp definition grouped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	 * @return the cp definition grouped entry, or <code>null</code> if a cp definition grouped entry with the primary key could not be found
	 */
	@Override
	public CPDefinitionGroupedEntry fetchByPrimaryKey(
		long CPDefinitionGroupedEntryId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionGroupedEntryId);
	}

	@Override
	public Map<Serializable, CPDefinitionGroupedEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionGroupedEntry> map = new HashMap<Serializable, CPDefinitionGroupedEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionGroupedEntry cpDefinitionGroupedEntry = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionGroupedEntry != null) {
				map.put(primaryKey, cpDefinitionGroupedEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionGroupedEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPDefinitionGroupedEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE_PKS_IN);

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

			for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : (List<CPDefinitionGroupedEntry>)q.list()) {
				map.put(cpDefinitionGroupedEntry.getPrimaryKeyObj(),
					cpDefinitionGroupedEntry);

				cacheResult(cpDefinitionGroupedEntry);

				uncachedPrimaryKeys.remove(cpDefinitionGroupedEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionGroupedEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionGroupedEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp definition grouped entries.
	 *
	 * @return the cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition grouped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @return the range of cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll(int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition grouped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition grouped entries
	 * @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition grouped entries
	 */
	@Override
	public List<CPDefinitionGroupedEntry> findAll(int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
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

		List<CPDefinitionGroupedEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionGroupedEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONGROUPEDENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONGROUPEDENTRY;

				if (pagination) {
					sql = sql.concat(CPDefinitionGroupedEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionGroupedEntry>)QueryUtil.list(q,
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
	 * Removes all the cp definition grouped entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry : findAll()) {
			remove(cpDefinitionGroupedEntry);
		}
	}

	/**
	 * Returns the number of cp definition grouped entries.
	 *
	 * @return the number of cp definition grouped entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONGROUPEDENTRY);

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
		return CPDefinitionGroupedEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition grouped entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionGroupedEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_CPDEFINITIONGROUPEDENTRY = "SELECT cpDefinitionGroupedEntry FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry";
	private static final String _SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE_PKS_IN =
		"SELECT cpDefinitionGroupedEntry FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry WHERE CPDefinitionGroupedEntryId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONGROUPEDENTRY_WHERE = "SELECT cpDefinitionGroupedEntry FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONGROUPEDENTRY = "SELECT COUNT(cpDefinitionGroupedEntry) FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry";
	private static final String _SQL_COUNT_CPDEFINITIONGROUPEDENTRY_WHERE = "SELECT COUNT(cpDefinitionGroupedEntry) FROM CPDefinitionGroupedEntry cpDefinitionGroupedEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionGroupedEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionGroupedEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionGroupedEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionGroupedEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}