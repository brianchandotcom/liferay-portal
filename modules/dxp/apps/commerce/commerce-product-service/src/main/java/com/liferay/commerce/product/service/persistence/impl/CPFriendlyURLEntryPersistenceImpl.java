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

import com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl;
import com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl;
import com.liferay.commerce.product.service.persistence.CPFriendlyURLEntryPersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
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
 * The persistence implementation for the cp friendly url entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryPersistence
 * @see com.liferay.commerce.product.service.persistence.CPFriendlyURLEntryUtil
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryPersistenceImpl extends BasePersistenceImpl<CPFriendlyURLEntry>
	implements CPFriendlyURLEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPFriendlyURLEntryUtil} to access the cp friendly url entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPFriendlyURLEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPFriendlyURLEntryModelImpl.UUID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp friendly url entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		List<CPFriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if (!Objects.equals(uuid, cpFriendlyURLEntry.getUuid())) {
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

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
				query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_First(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_First(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		List<CPFriendlyURLEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_Last(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByUuid_PrevAndNext(
		long CPFriendlyURLEntryId, String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpFriendlyURLEntry, uuid,
					orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByUuid_PrevAndNext(session, cpFriendlyURLEntry, uuid,
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

	protected CPFriendlyURLEntry getByUuid_PrevAndNext(Session session,
		CPFriendlyURLEntry cpFriendlyURLEntry, String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
			query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpFriendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPFriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpFriendlyURLEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpFriendlyURLEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpFriendlyURLEntry.uuid IS NULL OR cpFriendlyURLEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPFriendlyURLEntryModelImpl.UUID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUUID_G(uuid, groupId);

		if (cpFriendlyURLEntry == null) {
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

			throw new NoSuchCPFriendlyURLEntryException(msg.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if (!Objects.equals(uuid, cpFriendlyURLEntry.getUuid()) ||
					(groupId != cpFriendlyURLEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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

				List<CPFriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);

					if ((cpFriendlyURLEntry.getUuid() == null) ||
							!cpFriendlyURLEntry.getUuid().equals(uuid) ||
							(cpFriendlyURLEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpFriendlyURLEntry);
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByUUID_G(uuid, groupId);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpFriendlyURLEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpFriendlyURLEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpFriendlyURLEntry.uuid IS NULL OR cpFriendlyURLEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpFriendlyURLEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPFriendlyURLEntryModelImpl.UUID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		List<CPFriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if (!Objects.equals(uuid, cpFriendlyURLEntry.getUuid()) ||
							(companyId != cpFriendlyURLEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
				query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		List<CPFriendlyURLEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByUuid_C_PrevAndNext(
		long CPFriendlyURLEntryId, String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpFriendlyURLEntry,
					uuid, companyId, orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByUuid_C_PrevAndNext(session, cpFriendlyURLEntry,
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

	protected CPFriendlyURLEntry getByUuid_C_PrevAndNext(Session session,
		CPFriendlyURLEntry cpFriendlyURLEntry, String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

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
			query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpFriendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPFriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpFriendlyURLEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpFriendlyURLEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpFriendlyURLEntry.uuid IS NULL OR cpFriendlyURLEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpFriendlyURLEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK) {
		return findByG_C_C(groupId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) {
		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] { groupId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C;
			finderArgs = new Object[] {
					groupId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<CPFriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
							(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
							(classPK != cpFriendlyURLEntry.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_First(groupId,
				classNameId, classPK, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		List<CPFriendlyURLEntry> list = findByG_C_C(groupId, classNameId,
				classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_Last(groupId,
				classNameId, classPK, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByG_C_C(groupId, classNameId,
				classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByG_C_C_PrevAndNext(
		long CPFriendlyURLEntryId, long groupId, long classNameId,
		long classPK, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByG_C_C_PrevAndNext(session, cpFriendlyURLEntry,
					groupId, classNameId, classPK, orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByG_C_C_PrevAndNext(session, cpFriendlyURLEntry,
					groupId, classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPFriendlyURLEntry getByG_C_C_PrevAndNext(Session session,
		CPFriendlyURLEntry cpFriendlyURLEntry, long groupId, long classNameId,
		long classPK, OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpFriendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPFriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : findByG_C_C(groupId,
				classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "cpFriendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "cpFriendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "cpFriendlyURLEntry.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_U = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_U = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_U = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(long groupId, long classNameId,
		String urlTitle) {
		return findByG_C_U(groupId, classNameId, urlTitle, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(long groupId, long classNameId,
		String urlTitle, int start, int end) {
		return findByG_C_U(groupId, classNameId, urlTitle, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(long groupId, long classNameId,
		String urlTitle, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return findByG_C_U(groupId, classNameId, urlTitle, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_U(long groupId, long classNameId,
		String urlTitle, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_U;
			finderArgs = new Object[] { groupId, classNameId, urlTitle };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_U;
			finderArgs = new Object[] {
					groupId, classNameId, urlTitle,
					
					start, end, orderByComparator
				};
		}

		List<CPFriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
							(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
							!Objects.equals(urlTitle,
								cpFriendlyURLEntry.getUrlTitle())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals("")) {
				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				if (!pagination) {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_U_First(long groupId, long classNameId,
		String urlTitle, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_U_First(groupId,
				classNameId, urlTitle, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_U_First(long groupId,
		long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		List<CPFriendlyURLEntry> list = findByG_C_U(groupId, classNameId,
				urlTitle, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_U_Last(long groupId, long classNameId,
		String urlTitle, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_U_Last(groupId,
				classNameId, urlTitle, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", urlTitle=");
		msg.append(urlTitle);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_U_Last(long groupId, long classNameId,
		String urlTitle, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		int count = countByG_C_U(groupId, classNameId, urlTitle);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByG_C_U(groupId, classNameId,
				urlTitle, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByG_C_U_PrevAndNext(
		long CPFriendlyURLEntryId, long groupId, long classNameId,
		String urlTitle, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByG_C_U_PrevAndNext(session, cpFriendlyURLEntry,
					groupId, classNameId, urlTitle, orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByG_C_U_PrevAndNext(session, cpFriendlyURLEntry,
					groupId, classNameId, urlTitle, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPFriendlyURLEntry getByG_C_U_PrevAndNext(Session session,
		CPFriendlyURLEntry cpFriendlyURLEntry, long groupId, long classNameId,
		String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

		boolean bindUrlTitle = false;

		if (urlTitle == null) {
			query.append(_FINDER_COLUMN_G_C_U_URLTITLE_1);
		}
		else if (urlTitle.equals("")) {
			query.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
		}
		else {
			bindUrlTitle = true;

			query.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
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
			query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (bindUrlTitle) {
			qPos.add(urlTitle);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpFriendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPFriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 */
	@Override
	public void removeByG_C_U(long groupId, long classNameId, String urlTitle) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : findByG_C_U(groupId,
				classNameId, urlTitle, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_U(long groupId, long classNameId, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_U;

		Object[] finderArgs = new Object[] { groupId, classNameId, urlTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals("")) {
				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_C_U_GROUPID_2 = "cpFriendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_U_CLASSNAMEID_2 = "cpFriendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_1 = "cpFriendlyURLEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_2 = "cpFriendlyURLEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_3 = "(cpFriendlyURLEntry.urlTitle IS NULL OR cpFriendlyURLEntry.urlTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_M = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_M =
		new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.MAIN_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_M = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main) {
		return findByG_C_C_M(groupId, classNameId, classPK, main,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main, int start, int end) {
		return findByG_C_C_M(groupId, classNameId, classPK, main, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return findByG_C_C_M(groupId, classNameId, classPK, main, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_M;
			finderArgs = new Object[] { groupId, classNameId, classPK, main };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_M;
			finderArgs = new Object[] {
					groupId, classNameId, classPK, main,
					
					start, end, orderByComparator
				};
		}

		List<CPFriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPFriendlyURLEntry cpFriendlyURLEntry : list) {
					if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
							(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
							(classPK != cpFriendlyURLEntry.getClassPK()) ||
							(main != cpFriendlyURLEntry.isMain())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_M_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_M_MAIN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(main);

				if (!pagination) {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_M_First(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_M_First(groupId,
				classNameId, classPK, main, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", main=");
		msg.append(main);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_M_First(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		List<CPFriendlyURLEntry> list = findByG_C_C_M(groupId, classNameId,
				classPK, main, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_M_Last(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_M_Last(groupId,
				classNameId, classPK, main, orderByComparator);

		if (cpFriendlyURLEntry != null) {
			return cpFriendlyURLEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", main=");
		msg.append(main);

		msg.append("}");

		throw new NoSuchCPFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_M_Last(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		int count = countByG_C_C_M(groupId, classNameId, classPK, main);

		if (count == 0) {
			return null;
		}

		List<CPFriendlyURLEntry> list = findByG_C_C_M(groupId, classNameId,
				classPK, main, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry[] findByG_C_C_M_PrevAndNext(
		long CPFriendlyURLEntryId, long groupId, long classNameId,
		long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByPrimaryKey(CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByG_C_C_M_PrevAndNext(session, cpFriendlyURLEntry,
					groupId, classNameId, classPK, main, orderByComparator, true);

			array[1] = cpFriendlyURLEntry;

			array[2] = getByG_C_C_M_PrevAndNext(session, cpFriendlyURLEntry,
					groupId, classNameId, classPK, main, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPFriendlyURLEntry getByG_C_C_M_PrevAndNext(Session session,
		CPFriendlyURLEntry cpFriendlyURLEntry, long groupId, long classNameId,
		long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_M_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_M_CLASSPK_2);

		query.append(_FINDER_COLUMN_G_C_C_M_MAIN_2);

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
			query.append(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(main);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpFriendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPFriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 */
	@Override
	public void removeByG_C_C_M(long groupId, long classNameId, long classPK,
		boolean main) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : findByG_C_C_M(groupId,
				classNameId, classPK, main, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C_M(long groupId, long classNameId, long classPK,
		boolean main) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_M;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK, main };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_M_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_M_MAIN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(main);

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

	private static final String _FINDER_COLUMN_G_C_C_M_GROUPID_2 = "cpFriendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2 = "cpFriendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_M_CLASSPK_2 = "cpFriendlyURLEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_M_MAIN_2 = "cpFriendlyURLEntry.main = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_L_U = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.LANGUAGEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_L_U = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_L_U(long groupId, long classNameId,
		String languageId, String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_L_U(groupId,
				classNameId, languageId, urlTitle);

		if (cpFriendlyURLEntry == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPFriendlyURLEntryException(msg.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_L_U(long groupId, long classNameId,
		String languageId, String urlTitle) {
		return fetchByG_C_L_U(groupId, classNameId, languageId, urlTitle, true);
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_L_U(long groupId, long classNameId,
		String languageId, String urlTitle, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, languageId, urlTitle
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_L_U,
					finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
					(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
					!Objects.equals(languageId,
						cpFriendlyURLEntry.getLanguageId()) ||
					!Objects.equals(urlTitle, cpFriendlyURLEntry.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_L_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_L_U_CLASSNAMEID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_1);
			}
			else if (urlTitle.equals("")) {
				query.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<CPFriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_L_U,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"CPFriendlyURLEntryPersistenceImpl.fetchByG_C_L_U(long, long, String, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);

					if ((cpFriendlyURLEntry.getGroupId() != groupId) ||
							(cpFriendlyURLEntry.getClassNameId() != classNameId) ||
							(cpFriendlyURLEntry.getLanguageId() == null) ||
							!cpFriendlyURLEntry.getLanguageId()
												   .equals(languageId) ||
							(cpFriendlyURLEntry.getUrlTitle() == null) ||
							!cpFriendlyURLEntry.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_L_U,
							finderArgs, cpFriendlyURLEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_L_U,
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByG_C_L_U(long groupId, long classNameId,
		String languageId, String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByG_C_L_U(groupId,
				classNameId, languageId, urlTitle);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_L_U(long groupId, long classNameId,
		String languageId, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_L_U;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, languageId, urlTitle
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_L_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_L_U_CLASSNAMEID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_1);
			}
			else if (urlTitle.equals("")) {
				query.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_L_U_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				if (bindUrlTitle) {
					qPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_C_L_U_GROUPID_2 = "cpFriendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_L_U_CLASSNAMEID_2 = "cpFriendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_L_U_LANGUAGEID_1 = "cpFriendlyURLEntry.languageId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_L_U_LANGUAGEID_2 = "cpFriendlyURLEntry.languageId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_L_U_LANGUAGEID_3 = "(cpFriendlyURLEntry.languageId IS NULL OR cpFriendlyURLEntry.languageId = '') AND ";
	private static final String _FINDER_COLUMN_G_C_L_U_URLTITLE_1 = "cpFriendlyURLEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_C_L_U_URLTITLE_2 = "cpFriendlyURLEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_C_L_U_URLTITLE_3 = "(cpFriendlyURLEntry.urlTitle IS NULL OR cpFriendlyURLEntry.urlTitle = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_L_U = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.LANGUAGEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_L_U = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_L_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_L_U(long groupId, long classNameId,
		long classPK, String languageId, String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_L_U(groupId,
				classNameId, classPK, languageId, urlTitle);

		if (cpFriendlyURLEntry == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPFriendlyURLEntryException(msg.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_U(long groupId, long classNameId,
		long classPK, String languageId, String urlTitle) {
		return fetchByG_C_C_L_U(groupId, classNameId, classPK, languageId,
			urlTitle, true);
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_U(long groupId, long classNameId,
		long classPK, String languageId, String urlTitle,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId, urlTitle
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_L_U,
					finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
					(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
					(classPK != cpFriendlyURLEntry.getClassPK()) ||
					!Objects.equals(languageId,
						cpFriendlyURLEntry.getLanguageId()) ||
					!Objects.equals(urlTitle, cpFriendlyURLEntry.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_L_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_U_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_U_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_1);
			}
			else if (urlTitle.equals("")) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<CPFriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_U,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"CPFriendlyURLEntryPersistenceImpl.fetchByG_C_C_L_U(long, long, long, String, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);

					if ((cpFriendlyURLEntry.getGroupId() != groupId) ||
							(cpFriendlyURLEntry.getClassNameId() != classNameId) ||
							(cpFriendlyURLEntry.getClassPK() != classPK) ||
							(cpFriendlyURLEntry.getLanguageId() == null) ||
							!cpFriendlyURLEntry.getLanguageId()
												   .equals(languageId) ||
							(cpFriendlyURLEntry.getUrlTitle() == null) ||
							!cpFriendlyURLEntry.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_U,
							finderArgs, cpFriendlyURLEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_L_U,
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByG_C_C_L_U(long groupId, long classNameId,
		long classPK, String languageId, String urlTitle)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByG_C_C_L_U(groupId,
				classNameId, classPK, languageId, urlTitle);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param urlTitle the url title
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C_L_U(long groupId, long classNameId, long classPK,
		String languageId, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_L_U;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId, urlTitle
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_L_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_U_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_U_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_2);
			}

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_1);
			}
			else if (urlTitle.equals("")) {
				query.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_C_L_U_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				if (bindUrlTitle) {
					qPos.add(urlTitle);
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

	private static final String _FINDER_COLUMN_G_C_C_L_U_GROUPID_2 = "cpFriendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_U_CLASSNAMEID_2 = "cpFriendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_U_CLASSPK_2 = "cpFriendlyURLEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_1 = "cpFriendlyURLEntry.languageId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_2 = "cpFriendlyURLEntry.languageId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_U_LANGUAGEID_3 = "(cpFriendlyURLEntry.languageId IS NULL OR cpFriendlyURLEntry.languageId = '') AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_U_URLTITLE_1 = "cpFriendlyURLEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_L_U_URLTITLE_2 = "cpFriendlyURLEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_C_C_L_U_URLTITLE_3 = "(cpFriendlyURLEntry.urlTitle IS NULL OR cpFriendlyURLEntry.urlTitle = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_L_M = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_L_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.LANGUAGEID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.MAIN_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_L_M = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_L_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName()
			});

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_C_C_L_M(long groupId, long classNameId,
		long classPK, String languageId, boolean main)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByG_C_C_L_M(groupId,
				classNameId, classPK, languageId, main);

		if (cpFriendlyURLEntry == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(", main=");
			msg.append(main);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPFriendlyURLEntryException(msg.toString());
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_M(long groupId, long classNameId,
		long classPK, String languageId, boolean main) {
		return fetchByG_C_C_L_M(groupId, classNameId, classPK, languageId,
			main, true);
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_C_C_L_M(long groupId, long classNameId,
		long classPK, String languageId, boolean main, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId, main
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_L_M,
					finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)result;

			if ((groupId != cpFriendlyURLEntry.getGroupId()) ||
					(classNameId != cpFriendlyURLEntry.getClassNameId()) ||
					(classPK != cpFriendlyURLEntry.getClassPK()) ||
					!Objects.equals(languageId,
						cpFriendlyURLEntry.getLanguageId()) ||
					(main != cpFriendlyURLEntry.isMain())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_L_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_M_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_L_M_MAIN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				qPos.add(main);

				List<CPFriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_M,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"CPFriendlyURLEntryPersistenceImpl.fetchByG_C_C_L_M(long, long, long, String, boolean, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPFriendlyURLEntry cpFriendlyURLEntry = list.get(0);

					result = cpFriendlyURLEntry;

					cacheResult(cpFriendlyURLEntry);

					if ((cpFriendlyURLEntry.getGroupId() != groupId) ||
							(cpFriendlyURLEntry.getClassNameId() != classNameId) ||
							(cpFriendlyURLEntry.getClassPK() != classPK) ||
							(cpFriendlyURLEntry.getLanguageId() == null) ||
							!cpFriendlyURLEntry.getLanguageId()
												   .equals(languageId) ||
							(cpFriendlyURLEntry.isMain() != main)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_M,
							finderArgs, cpFriendlyURLEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_L_M,
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
			return (CPFriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByG_C_C_L_M(long groupId, long classNameId,
		long classPK, String languageId, boolean main)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = findByG_C_C_L_M(groupId,
				classNameId, classPK, languageId, main);

		return remove(cpFriendlyURLEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param languageId the language ID
	 * @param main the main
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_C_C_L_M(long groupId, long classNameId, long classPK,
		String languageId, boolean main) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_L_M;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, languageId, main
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_L_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_L_M_CLASSPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_G_C_C_L_M_MAIN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				qPos.add(main);

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

	private static final String _FINDER_COLUMN_G_C_C_L_M_GROUPID_2 = "cpFriendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_M_CLASSNAMEID_2 = "cpFriendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_M_CLASSPK_2 = "cpFriendlyURLEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_1 = "cpFriendlyURLEntry.languageId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_2 = "cpFriendlyURLEntry.languageId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_M_LANGUAGEID_3 = "(cpFriendlyURLEntry.languageId IS NULL OR cpFriendlyURLEntry.languageId = '') AND ";
	private static final String _FINDER_COLUMN_G_C_C_L_M_MAIN_2 = "cpFriendlyURLEntry.main = ?";

	public CPFriendlyURLEntryPersistenceImpl() {
		setModelClass(CPFriendlyURLEntry.class);

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
	 * Caches the cp friendly url entry in the entity cache if it is enabled.
	 *
	 * @param cpFriendlyURLEntry the cp friendly url entry
	 */
	@Override
	public void cacheResult(CPFriendlyURLEntry cpFriendlyURLEntry) {
		entityCache.putResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, cpFriendlyURLEntry.getPrimaryKey(),
			cpFriendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpFriendlyURLEntry.getUuid(), cpFriendlyURLEntry.getGroupId()
			}, cpFriendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_L_U,
			new Object[] {
				cpFriendlyURLEntry.getGroupId(),
				cpFriendlyURLEntry.getClassNameId(),
				cpFriendlyURLEntry.getLanguageId(),
				cpFriendlyURLEntry.getUrlTitle()
			}, cpFriendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_U,
			new Object[] {
				cpFriendlyURLEntry.getGroupId(),
				cpFriendlyURLEntry.getClassNameId(),
				cpFriendlyURLEntry.getClassPK(),
				cpFriendlyURLEntry.getLanguageId(),
				cpFriendlyURLEntry.getUrlTitle()
			}, cpFriendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_M,
			new Object[] {
				cpFriendlyURLEntry.getGroupId(),
				cpFriendlyURLEntry.getClassNameId(),
				cpFriendlyURLEntry.getClassPK(),
				cpFriendlyURLEntry.getLanguageId(), cpFriendlyURLEntry.isMain()
			}, cpFriendlyURLEntry);

		cpFriendlyURLEntry.resetOriginalValues();
	}

	/**
	 * Caches the cp friendly url entries in the entity cache if it is enabled.
	 *
	 * @param cpFriendlyURLEntries the cp friendly url entries
	 */
	@Override
	public void cacheResult(List<CPFriendlyURLEntry> cpFriendlyURLEntries) {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : cpFriendlyURLEntries) {
			if (entityCache.getResult(
						CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPFriendlyURLEntryImpl.class,
						cpFriendlyURLEntry.getPrimaryKey()) == null) {
				cacheResult(cpFriendlyURLEntry);
			}
			else {
				cpFriendlyURLEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp friendly url entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPFriendlyURLEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp friendly url entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPFriendlyURLEntry cpFriendlyURLEntry) {
		entityCache.removeResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, cpFriendlyURLEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPFriendlyURLEntryModelImpl)cpFriendlyURLEntry,
			true);
	}

	@Override
	public void clearCache(List<CPFriendlyURLEntry> cpFriendlyURLEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPFriendlyURLEntry cpFriendlyURLEntry : cpFriendlyURLEntries) {
			entityCache.removeResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPFriendlyURLEntryImpl.class, cpFriendlyURLEntry.getPrimaryKey());

			clearUniqueFindersCache((CPFriendlyURLEntryModelImpl)cpFriendlyURLEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPFriendlyURLEntryModelImpl cpFriendlyURLEntryModelImpl) {
		Object[] args = new Object[] {
				cpFriendlyURLEntryModelImpl.getUuid(),
				cpFriendlyURLEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpFriendlyURLEntryModelImpl, false);

		args = new Object[] {
				cpFriendlyURLEntryModelImpl.getGroupId(),
				cpFriendlyURLEntryModelImpl.getClassNameId(),
				cpFriendlyURLEntryModelImpl.getLanguageId(),
				cpFriendlyURLEntryModelImpl.getUrlTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_L_U, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_L_U, args,
			cpFriendlyURLEntryModelImpl, false);

		args = new Object[] {
				cpFriendlyURLEntryModelImpl.getGroupId(),
				cpFriendlyURLEntryModelImpl.getClassNameId(),
				cpFriendlyURLEntryModelImpl.getClassPK(),
				cpFriendlyURLEntryModelImpl.getLanguageId(),
				cpFriendlyURLEntryModelImpl.getUrlTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_L_U, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_U, args,
			cpFriendlyURLEntryModelImpl, false);

		args = new Object[] {
				cpFriendlyURLEntryModelImpl.getGroupId(),
				cpFriendlyURLEntryModelImpl.getClassNameId(),
				cpFriendlyURLEntryModelImpl.getClassPK(),
				cpFriendlyURLEntryModelImpl.getLanguageId(),
				cpFriendlyURLEntryModelImpl.isMain()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_L_M, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_L_M, args,
			cpFriendlyURLEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPFriendlyURLEntryModelImpl cpFriendlyURLEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getUuid(),
					cpFriendlyURLEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getOriginalUuid(),
					cpFriendlyURLEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getGroupId(),
					cpFriendlyURLEntryModelImpl.getClassNameId(),
					cpFriendlyURLEntryModelImpl.getLanguageId(),
					cpFriendlyURLEntryModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_L_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_L_U, args);
		}

		if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_L_U.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getOriginalGroupId(),
					cpFriendlyURLEntryModelImpl.getOriginalClassNameId(),
					cpFriendlyURLEntryModelImpl.getOriginalLanguageId(),
					cpFriendlyURLEntryModelImpl.getOriginalUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_L_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_L_U, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getGroupId(),
					cpFriendlyURLEntryModelImpl.getClassNameId(),
					cpFriendlyURLEntryModelImpl.getClassPK(),
					cpFriendlyURLEntryModelImpl.getLanguageId(),
					cpFriendlyURLEntryModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_L_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_L_U, args);
		}

		if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_L_U.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getOriginalGroupId(),
					cpFriendlyURLEntryModelImpl.getOriginalClassNameId(),
					cpFriendlyURLEntryModelImpl.getOriginalClassPK(),
					cpFriendlyURLEntryModelImpl.getOriginalLanguageId(),
					cpFriendlyURLEntryModelImpl.getOriginalUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_L_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_L_U, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getGroupId(),
					cpFriendlyURLEntryModelImpl.getClassNameId(),
					cpFriendlyURLEntryModelImpl.getClassPK(),
					cpFriendlyURLEntryModelImpl.getLanguageId(),
					cpFriendlyURLEntryModelImpl.isMain()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_L_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_L_M, args);
		}

		if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_L_M.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpFriendlyURLEntryModelImpl.getOriginalGroupId(),
					cpFriendlyURLEntryModelImpl.getOriginalClassNameId(),
					cpFriendlyURLEntryModelImpl.getOriginalClassPK(),
					cpFriendlyURLEntryModelImpl.getOriginalLanguageId(),
					cpFriendlyURLEntryModelImpl.getOriginalMain()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_L_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_L_M, args);
		}
	}

	/**
	 * Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	 *
	 * @param CPFriendlyURLEntryId the primary key for the new cp friendly url entry
	 * @return the new cp friendly url entry
	 */
	@Override
	public CPFriendlyURLEntry create(long CPFriendlyURLEntryId) {
		CPFriendlyURLEntry cpFriendlyURLEntry = new CPFriendlyURLEntryImpl();

		cpFriendlyURLEntry.setNew(true);
		cpFriendlyURLEntry.setPrimaryKey(CPFriendlyURLEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpFriendlyURLEntry.setUuid(uuid);

		cpFriendlyURLEntry.setCompanyId(companyProvider.getCompanyId());

		return cpFriendlyURLEntry;
	}

	/**
	 * Removes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry that was removed
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry remove(long CPFriendlyURLEntryId)
		throws NoSuchCPFriendlyURLEntryException {
		return remove((Serializable)CPFriendlyURLEntryId);
	}

	/**
	 * Removes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp friendly url entry
	 * @return the cp friendly url entry that was removed
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry remove(Serializable primaryKey)
		throws NoSuchCPFriendlyURLEntryException {
		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)session.get(CPFriendlyURLEntryImpl.class,
					primaryKey);

			if (cpFriendlyURLEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPFriendlyURLEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpFriendlyURLEntry);
		}
		catch (NoSuchCPFriendlyURLEntryException nsee) {
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
	protected CPFriendlyURLEntry removeImpl(
		CPFriendlyURLEntry cpFriendlyURLEntry) {
		cpFriendlyURLEntry = toUnwrappedModel(cpFriendlyURLEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpFriendlyURLEntry)) {
				cpFriendlyURLEntry = (CPFriendlyURLEntry)session.get(CPFriendlyURLEntryImpl.class,
						cpFriendlyURLEntry.getPrimaryKeyObj());
			}

			if (cpFriendlyURLEntry != null) {
				session.delete(cpFriendlyURLEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpFriendlyURLEntry != null) {
			clearCache(cpFriendlyURLEntry);
		}

		return cpFriendlyURLEntry;
	}

	@Override
	public CPFriendlyURLEntry updateImpl(CPFriendlyURLEntry cpFriendlyURLEntry) {
		cpFriendlyURLEntry = toUnwrappedModel(cpFriendlyURLEntry);

		boolean isNew = cpFriendlyURLEntry.isNew();

		CPFriendlyURLEntryModelImpl cpFriendlyURLEntryModelImpl = (CPFriendlyURLEntryModelImpl)cpFriendlyURLEntry;

		if (Validator.isNull(cpFriendlyURLEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpFriendlyURLEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpFriendlyURLEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpFriendlyURLEntry.setCreateDate(now);
			}
			else {
				cpFriendlyURLEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpFriendlyURLEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpFriendlyURLEntry.setModifiedDate(now);
			}
			else {
				cpFriendlyURLEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpFriendlyURLEntry.isNew()) {
				session.save(cpFriendlyURLEntry);

				cpFriendlyURLEntry.setNew(false);
			}
			else {
				cpFriendlyURLEntry = (CPFriendlyURLEntry)session.merge(cpFriendlyURLEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPFriendlyURLEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cpFriendlyURLEntryModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpFriendlyURLEntryModelImpl.getUuid(),
					cpFriendlyURLEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					cpFriendlyURLEntryModelImpl.getGroupId(),
					cpFriendlyURLEntryModelImpl.getClassNameId(),
					cpFriendlyURLEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
				args);

			args = new Object[] {
					cpFriendlyURLEntryModelImpl.getGroupId(),
					cpFriendlyURLEntryModelImpl.getClassNameId(),
					cpFriendlyURLEntryModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_U, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_U,
				args);

			args = new Object[] {
					cpFriendlyURLEntryModelImpl.getGroupId(),
					cpFriendlyURLEntryModelImpl.getClassNameId(),
					cpFriendlyURLEntryModelImpl.getClassPK(),
					cpFriendlyURLEntryModelImpl.isMain()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_M, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_M,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpFriendlyURLEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpFriendlyURLEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpFriendlyURLEntryModelImpl.getOriginalUuid(),
						cpFriendlyURLEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpFriendlyURLEntryModelImpl.getUuid(),
						cpFriendlyURLEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpFriendlyURLEntryModelImpl.getOriginalGroupId(),
						cpFriendlyURLEntryModelImpl.getOriginalClassNameId(),
						cpFriendlyURLEntryModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);

				args = new Object[] {
						cpFriendlyURLEntryModelImpl.getGroupId(),
						cpFriendlyURLEntryModelImpl.getClassNameId(),
						cpFriendlyURLEntryModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);
			}

			if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpFriendlyURLEntryModelImpl.getOriginalGroupId(),
						cpFriendlyURLEntryModelImpl.getOriginalClassNameId(),
						cpFriendlyURLEntryModelImpl.getOriginalUrlTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_U,
					args);

				args = new Object[] {
						cpFriendlyURLEntryModelImpl.getGroupId(),
						cpFriendlyURLEntryModelImpl.getClassNameId(),
						cpFriendlyURLEntryModelImpl.getUrlTitle()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_U,
					args);
			}

			if ((cpFriendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_M.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpFriendlyURLEntryModelImpl.getOriginalGroupId(),
						cpFriendlyURLEntryModelImpl.getOriginalClassNameId(),
						cpFriendlyURLEntryModelImpl.getOriginalClassPK(),
						cpFriendlyURLEntryModelImpl.getOriginalMain()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_M, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_M,
					args);

				args = new Object[] {
						cpFriendlyURLEntryModelImpl.getGroupId(),
						cpFriendlyURLEntryModelImpl.getClassNameId(),
						cpFriendlyURLEntryModelImpl.getClassPK(),
						cpFriendlyURLEntryModelImpl.isMain()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_M, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_M,
					args);
			}
		}

		entityCache.putResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, cpFriendlyURLEntry.getPrimaryKey(),
			cpFriendlyURLEntry, false);

		clearUniqueFindersCache(cpFriendlyURLEntryModelImpl, false);
		cacheUniqueFindersCache(cpFriendlyURLEntryModelImpl);

		cpFriendlyURLEntry.resetOriginalValues();

		return cpFriendlyURLEntry;
	}

	protected CPFriendlyURLEntry toUnwrappedModel(
		CPFriendlyURLEntry cpFriendlyURLEntry) {
		if (cpFriendlyURLEntry instanceof CPFriendlyURLEntryImpl) {
			return cpFriendlyURLEntry;
		}

		CPFriendlyURLEntryImpl cpFriendlyURLEntryImpl = new CPFriendlyURLEntryImpl();

		cpFriendlyURLEntryImpl.setNew(cpFriendlyURLEntry.isNew());
		cpFriendlyURLEntryImpl.setPrimaryKey(cpFriendlyURLEntry.getPrimaryKey());

		cpFriendlyURLEntryImpl.setUuid(cpFriendlyURLEntry.getUuid());
		cpFriendlyURLEntryImpl.setCPFriendlyURLEntryId(cpFriendlyURLEntry.getCPFriendlyURLEntryId());
		cpFriendlyURLEntryImpl.setGroupId(cpFriendlyURLEntry.getGroupId());
		cpFriendlyURLEntryImpl.setCompanyId(cpFriendlyURLEntry.getCompanyId());
		cpFriendlyURLEntryImpl.setUserId(cpFriendlyURLEntry.getUserId());
		cpFriendlyURLEntryImpl.setUserName(cpFriendlyURLEntry.getUserName());
		cpFriendlyURLEntryImpl.setCreateDate(cpFriendlyURLEntry.getCreateDate());
		cpFriendlyURLEntryImpl.setModifiedDate(cpFriendlyURLEntry.getModifiedDate());
		cpFriendlyURLEntryImpl.setClassNameId(cpFriendlyURLEntry.getClassNameId());
		cpFriendlyURLEntryImpl.setClassPK(cpFriendlyURLEntry.getClassPK());
		cpFriendlyURLEntryImpl.setLanguageId(cpFriendlyURLEntry.getLanguageId());
		cpFriendlyURLEntryImpl.setUrlTitle(cpFriendlyURLEntry.getUrlTitle());
		cpFriendlyURLEntryImpl.setMain(cpFriendlyURLEntry.isMain());

		return cpFriendlyURLEntryImpl;
	}

	/**
	 * Returns the cp friendly url entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp friendly url entry
	 * @return the cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyURLEntry = fetchByPrimaryKey(primaryKey);

		if (cpFriendlyURLEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPFriendlyURLEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry with the primary key or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByPrimaryKey(long CPFriendlyURLEntryId)
		throws NoSuchCPFriendlyURLEntryException {
		return findByPrimaryKey((Serializable)CPFriendlyURLEntryId);
	}

	/**
	 * Returns the cp friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp friendly url entry
	 * @return the cp friendly url entry, or <code>null</code> if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPFriendlyURLEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPFriendlyURLEntry cpFriendlyURLEntry = (CPFriendlyURLEntry)serializable;

		if (cpFriendlyURLEntry == null) {
			Session session = null;

			try {
				session = openSession();

				cpFriendlyURLEntry = (CPFriendlyURLEntry)session.get(CPFriendlyURLEntryImpl.class,
						primaryKey);

				if (cpFriendlyURLEntry != null) {
					cacheResult(cpFriendlyURLEntry);
				}
				else {
					entityCache.putResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPFriendlyURLEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPFriendlyURLEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpFriendlyURLEntry;
	}

	/**
	 * Returns the cp friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	 * @return the cp friendly url entry, or <code>null</code> if a cp friendly url entry with the primary key could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByPrimaryKey(long CPFriendlyURLEntryId) {
		return fetchByPrimaryKey((Serializable)CPFriendlyURLEntryId);
	}

	@Override
	public Map<Serializable, CPFriendlyURLEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPFriendlyURLEntry> map = new HashMap<Serializable, CPFriendlyURLEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPFriendlyURLEntry cpFriendlyURLEntry = fetchByPrimaryKey(primaryKey);

			if (cpFriendlyURLEntry != null) {
				map.put(primaryKey, cpFriendlyURLEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPFriendlyURLEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPFriendlyURLEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE_PKS_IN);

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

			for (CPFriendlyURLEntry cpFriendlyURLEntry : (List<CPFriendlyURLEntry>)q.list()) {
				map.put(cpFriendlyURLEntry.getPrimaryKeyObj(),
					cpFriendlyURLEntry);

				cacheResult(cpFriendlyURLEntry);

				uncachedPrimaryKeys.remove(cpFriendlyURLEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPFriendlyURLEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp friendly url entries.
	 *
	 * @return the cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @return the range of cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll(int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp friendly url entries
	 * @param end the upper bound of the range of cp friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp friendly url entries
	 */
	@Override
	public List<CPFriendlyURLEntry> findAll(int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
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

		List<CPFriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPFriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPFRIENDLYURLENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPFRIENDLYURLENTRY;

				if (pagination) {
					sql = sql.concat(CPFriendlyURLEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPFriendlyURLEntry>)QueryUtil.list(q,
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
	 * Removes all the cp friendly url entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPFriendlyURLEntry cpFriendlyURLEntry : findAll()) {
			remove(cpFriendlyURLEntry);
		}
	}

	/**
	 * Returns the number of cp friendly url entries.
	 *
	 * @return the number of cp friendly url entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPFRIENDLYURLENTRY);

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
		return CPFriendlyURLEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp friendly url entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPFriendlyURLEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY = "SELECT cpFriendlyURLEntry FROM CPFriendlyURLEntry cpFriendlyURLEntry";
	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY_WHERE_PKS_IN = "SELECT cpFriendlyURLEntry FROM CPFriendlyURLEntry cpFriendlyURLEntry WHERE CPFriendlyURLEntryId IN (";
	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY_WHERE = "SELECT cpFriendlyURLEntry FROM CPFriendlyURLEntry cpFriendlyURLEntry WHERE ";
	private static final String _SQL_COUNT_CPFRIENDLYURLENTRY = "SELECT COUNT(cpFriendlyURLEntry) FROM CPFriendlyURLEntry cpFriendlyURLEntry";
	private static final String _SQL_COUNT_CPFRIENDLYURLENTRY_WHERE = "SELECT COUNT(cpFriendlyURLEntry) FROM CPFriendlyURLEntry cpFriendlyURLEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpFriendlyURLEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPFriendlyURLEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPFriendlyURLEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPFriendlyURLEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}