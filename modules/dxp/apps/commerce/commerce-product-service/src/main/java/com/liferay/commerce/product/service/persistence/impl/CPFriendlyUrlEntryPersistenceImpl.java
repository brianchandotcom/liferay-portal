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
				for (CPFriendlyURLEntry cpFriendlyUrlEntry : list) {
					if (!Objects.equals(uuid, cpFriendlyUrlEntry.getUuid())) {
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
		CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpFriendlyUrlEntry != null) {
			return cpFriendlyUrlEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
		CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpFriendlyUrlEntry != null) {
			return cpFriendlyUrlEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
		CPFriendlyURLEntry cpFriendlyUrlEntry = findByPrimaryKey(CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpFriendlyUrlEntry, uuid,
					orderByComparator, true);

			array[1] = cpFriendlyUrlEntry;

			array[2] = getByUuid_PrevAndNext(session, cpFriendlyUrlEntry, uuid,
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
		CPFriendlyURLEntry cpFriendlyUrlEntry, String uuid,
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpFriendlyUrlEntry);

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
		for (CPFriendlyURLEntry cpFriendlyUrlEntry : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpFriendlyUrlEntry);
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpFriendlyUrlEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpFriendlyUrlEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpFriendlyUrlEntry.uuid IS NULL OR cpFriendlyUrlEntry.uuid = '')";
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
		CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByUUID_G(uuid, groupId);

		if (cpFriendlyUrlEntry == null) {
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

			throw new NoSuchCPFriendlyURLEntryException(msg.toString());
		}

		return cpFriendlyUrlEntry;
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
			CPFriendlyURLEntry cpFriendlyUrlEntry = (CPFriendlyURLEntry)result;

			if (!Objects.equals(uuid, cpFriendlyUrlEntry.getUuid()) ||
					(groupId != cpFriendlyUrlEntry.getGroupId())) {
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

				List<CPFriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPFriendlyURLEntry cpFriendlyUrlEntry = list.get(0);

					result = cpFriendlyUrlEntry;

					cacheResult(cpFriendlyUrlEntry);

					if ((cpFriendlyUrlEntry.getUuid() == null) ||
							!cpFriendlyUrlEntry.getUuid().equals(uuid) ||
							(cpFriendlyUrlEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpFriendlyUrlEntry);
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
		CPFriendlyURLEntry cpFriendlyUrlEntry = findByUUID_G(uuid, groupId);

		return remove(cpFriendlyUrlEntry);
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpFriendlyUrlEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpFriendlyUrlEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpFriendlyUrlEntry.uuid IS NULL OR cpFriendlyUrlEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpFriendlyUrlEntry.groupId = ?";
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
				for (CPFriendlyURLEntry cpFriendlyUrlEntry : list) {
					if (!Objects.equals(uuid, cpFriendlyUrlEntry.getUuid()) ||
							(companyId != cpFriendlyUrlEntry.getCompanyId())) {
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
		CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpFriendlyUrlEntry != null) {
			return cpFriendlyUrlEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
		CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpFriendlyUrlEntry != null) {
			return cpFriendlyUrlEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
		CPFriendlyURLEntry cpFriendlyUrlEntry = findByPrimaryKey(CPFriendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			CPFriendlyURLEntry[] array = new CPFriendlyURLEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpFriendlyUrlEntry,
					uuid, companyId, orderByComparator, true);

			array[1] = cpFriendlyUrlEntry;

			array[2] = getByUuid_C_PrevAndNext(session, cpFriendlyUrlEntry,
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
		CPFriendlyURLEntry cpFriendlyUrlEntry, String uuid, long companyId,
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpFriendlyUrlEntry);

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
		for (CPFriendlyURLEntry cpFriendlyUrlEntry : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpFriendlyUrlEntry);
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpFriendlyUrlEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpFriendlyUrlEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpFriendlyUrlEntry.uuid IS NULL OR cpFriendlyUrlEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpFriendlyUrlEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_L = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			CPFriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK |
			CPFriendlyURLEntryModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_L = new FinderPath(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching cp friendly url entry
	 * @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry findByG_U_L(long groupId, String urlTitle,
		String languageId) throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByG_U_L(groupId, urlTitle,
				languageId);

		if (cpFriendlyUrlEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPFriendlyURLEntryException(msg.toString());
		}

		return cpFriendlyUrlEntry;
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_U_L(long groupId, String urlTitle,
		String languageId) {
		return fetchByG_U_L(groupId, urlTitle, languageId, true);
	}

	/**
	 * Returns the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	 */
	@Override
	public CPFriendlyURLEntry fetchByG_U_L(long groupId, String urlTitle,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_U_L,
					finderArgs, this);
		}

		if (result instanceof CPFriendlyURLEntry) {
			CPFriendlyURLEntry cpFriendlyUrlEntry = (CPFriendlyURLEntry)result;

			if ((groupId != cpFriendlyUrlEntry.getGroupId()) ||
					!Objects.equals(urlTitle, cpFriendlyUrlEntry.getUrlTitle()) ||
					!Objects.equals(languageId,
						cpFriendlyUrlEntry.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_L_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<CPFriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
						finderArgs, list);
				}
				else {
					CPFriendlyURLEntry cpFriendlyUrlEntry = list.get(0);

					result = cpFriendlyUrlEntry;

					cacheResult(cpFriendlyUrlEntry);

					if ((cpFriendlyUrlEntry.getGroupId() != groupId) ||
							(cpFriendlyUrlEntry.getUrlTitle() == null) ||
							!cpFriendlyUrlEntry.getUrlTitle().equals(urlTitle) ||
							(cpFriendlyUrlEntry.getLanguageId() == null) ||
							!cpFriendlyUrlEntry.getLanguageId()
												   .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
							finderArgs, cpFriendlyUrlEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, finderArgs);

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
	 * Removes the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the cp friendly url entry that was removed
	 */
	@Override
	public CPFriendlyURLEntry removeByG_U_L(long groupId, String urlTitle,
		String languageId) throws NoSuchCPFriendlyURLEntryException {
		CPFriendlyURLEntry cpFriendlyUrlEntry = findByG_U_L(groupId, urlTitle,
				languageId);

		return remove(cpFriendlyUrlEntry);
	}

	/**
	 * Returns the number of cp friendly url entries where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the number of matching cp friendly url entries
	 */
	@Override
	public int countByG_U_L(long groupId, String urlTitle, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_L;

		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPFRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_L_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_G_U_L_GROUPID_2 = "cpFriendlyUrlEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_1 = "cpFriendlyUrlEntry.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_2 = "cpFriendlyUrlEntry.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_3 = "(cpFriendlyUrlEntry.urlTitle IS NULL OR cpFriendlyUrlEntry.urlTitle = '') AND ";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_1 = "cpFriendlyUrlEntry.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_2 = "cpFriendlyUrlEntry.languageId = ?";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_3 = "(cpFriendlyUrlEntry.languageId IS NULL OR cpFriendlyUrlEntry.languageId = '')";

	public CPFriendlyURLEntryPersistenceImpl() {
		setModelClass(CPFriendlyURLEntry.class);

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
	 * Caches the cp friendly url entry in the entity cache if it is enabled.
	 *
	 * @param cpFriendlyUrlEntry the cp friendly url entry
	 */
	@Override
	public void cacheResult(CPFriendlyURLEntry cpFriendlyUrlEntry) {
		entityCache.putResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, cpFriendlyUrlEntry.getPrimaryKey(),
			cpFriendlyUrlEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpFriendlyUrlEntry.getUuid(), cpFriendlyUrlEntry.getGroupId()
			}, cpFriendlyUrlEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
			new Object[] {
				cpFriendlyUrlEntry.getGroupId(),
				cpFriendlyUrlEntry.getUrlTitle(),
				cpFriendlyUrlEntry.getLanguageId()
			}, cpFriendlyUrlEntry);

		cpFriendlyUrlEntry.resetOriginalValues();
	}

	/**
	 * Caches the cp friendly url entries in the entity cache if it is enabled.
	 *
	 * @param cpFriendlyUrlEntries the cp friendly url entries
	 */
	@Override
	public void cacheResult(List<CPFriendlyURLEntry> cpFriendlyUrlEntries) {
		for (CPFriendlyURLEntry cpFriendlyUrlEntry : cpFriendlyUrlEntries) {
			if (entityCache.getResult(
						CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPFriendlyURLEntryImpl.class,
						cpFriendlyUrlEntry.getPrimaryKey()) == null) {
				cacheResult(cpFriendlyUrlEntry);
			}
			else {
				cpFriendlyUrlEntry.resetOriginalValues();
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
	public void clearCache(CPFriendlyURLEntry cpFriendlyUrlEntry) {
		entityCache.removeResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, cpFriendlyUrlEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPFriendlyURLEntryModelImpl)cpFriendlyUrlEntry,
			true);
	}

	@Override
	public void clearCache(List<CPFriendlyURLEntry> cpFriendlyUrlEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPFriendlyURLEntry cpFriendlyUrlEntry : cpFriendlyUrlEntries) {
			entityCache.removeResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPFriendlyURLEntryImpl.class, cpFriendlyUrlEntry.getPrimaryKey());

			clearUniqueFindersCache((CPFriendlyURLEntryModelImpl)cpFriendlyUrlEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPFriendlyURLEntryModelImpl cpFriendlyUrlEntryModelImpl) {
		Object[] args = new Object[] {
				cpFriendlyUrlEntryModelImpl.getUuid(),
				cpFriendlyUrlEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpFriendlyUrlEntryModelImpl, false);

		args = new Object[] {
				cpFriendlyUrlEntryModelImpl.getGroupId(),
				cpFriendlyUrlEntryModelImpl.getUrlTitle(),
				cpFriendlyUrlEntryModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_U_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L, args,
			cpFriendlyUrlEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPFriendlyURLEntryModelImpl cpFriendlyUrlEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpFriendlyUrlEntryModelImpl.getUuid(),
					cpFriendlyUrlEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpFriendlyUrlEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpFriendlyUrlEntryModelImpl.getOriginalUuid(),
					cpFriendlyUrlEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpFriendlyUrlEntryModelImpl.getGroupId(),
					cpFriendlyUrlEntryModelImpl.getUrlTitle(),
					cpFriendlyUrlEntryModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
		}

		if ((cpFriendlyUrlEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_U_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpFriendlyUrlEntryModelImpl.getOriginalGroupId(),
					cpFriendlyUrlEntryModelImpl.getOriginalUrlTitle(),
					cpFriendlyUrlEntryModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
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
		CPFriendlyURLEntry cpFriendlyUrlEntry = new CPFriendlyURLEntryImpl();

		cpFriendlyUrlEntry.setNew(true);
		cpFriendlyUrlEntry.setPrimaryKey(CPFriendlyURLEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpFriendlyUrlEntry.setUuid(uuid);

		cpFriendlyUrlEntry.setCompanyId(companyProvider.getCompanyId());

		return cpFriendlyUrlEntry;
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

			CPFriendlyURLEntry cpFriendlyUrlEntry = (CPFriendlyURLEntry)session.get(CPFriendlyURLEntryImpl.class,
					primaryKey);

			if (cpFriendlyUrlEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPFriendlyURLEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpFriendlyUrlEntry);
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
		CPFriendlyURLEntry cpFriendlyUrlEntry) {
		cpFriendlyUrlEntry = toUnwrappedModel(cpFriendlyUrlEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpFriendlyUrlEntry)) {
				cpFriendlyUrlEntry = (CPFriendlyURLEntry)session.get(CPFriendlyURLEntryImpl.class,
						cpFriendlyUrlEntry.getPrimaryKeyObj());
			}

			if (cpFriendlyUrlEntry != null) {
				session.delete(cpFriendlyUrlEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpFriendlyUrlEntry != null) {
			clearCache(cpFriendlyUrlEntry);
		}

		return cpFriendlyUrlEntry;
	}

	@Override
	public CPFriendlyURLEntry updateImpl(CPFriendlyURLEntry cpFriendlyUrlEntry) {
		cpFriendlyUrlEntry = toUnwrappedModel(cpFriendlyUrlEntry);

		boolean isNew = cpFriendlyUrlEntry.isNew();

		CPFriendlyURLEntryModelImpl cpFriendlyUrlEntryModelImpl = (CPFriendlyURLEntryModelImpl)cpFriendlyUrlEntry;

		if (Validator.isNull(cpFriendlyUrlEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpFriendlyUrlEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpFriendlyUrlEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpFriendlyUrlEntry.setCreateDate(now);
			}
			else {
				cpFriendlyUrlEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpFriendlyUrlEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpFriendlyUrlEntry.setModifiedDate(now);
			}
			else {
				cpFriendlyUrlEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpFriendlyUrlEntry.isNew()) {
				session.save(cpFriendlyUrlEntry);

				cpFriendlyUrlEntry.setNew(false);
			}
			else {
				cpFriendlyUrlEntry = (CPFriendlyURLEntry)session.merge(cpFriendlyUrlEntry);
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
			Object[] args = new Object[] { cpFriendlyUrlEntryModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpFriendlyUrlEntryModelImpl.getUuid(),
					cpFriendlyUrlEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpFriendlyUrlEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpFriendlyUrlEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpFriendlyUrlEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpFriendlyUrlEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpFriendlyUrlEntryModelImpl.getOriginalUuid(),
						cpFriendlyUrlEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpFriendlyUrlEntryModelImpl.getUuid(),
						cpFriendlyUrlEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		entityCache.putResult(CPFriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPFriendlyURLEntryImpl.class, cpFriendlyUrlEntry.getPrimaryKey(),
			cpFriendlyUrlEntry, false);

		clearUniqueFindersCache(cpFriendlyUrlEntryModelImpl, false);
		cacheUniqueFindersCache(cpFriendlyUrlEntryModelImpl);

		cpFriendlyUrlEntry.resetOriginalValues();

		return cpFriendlyUrlEntry;
	}

	protected CPFriendlyURLEntry toUnwrappedModel(
		CPFriendlyURLEntry cpFriendlyUrlEntry) {
		if (cpFriendlyUrlEntry instanceof CPFriendlyURLEntryImpl) {
			return cpFriendlyUrlEntry;
		}

		CPFriendlyURLEntryImpl cpFriendlyUrlEntryImpl = new CPFriendlyURLEntryImpl();

		cpFriendlyUrlEntryImpl.setNew(cpFriendlyUrlEntry.isNew());
		cpFriendlyUrlEntryImpl.setPrimaryKey(cpFriendlyUrlEntry.getPrimaryKey());

		cpFriendlyUrlEntryImpl.setUuid(cpFriendlyUrlEntry.getUuid());
		cpFriendlyUrlEntryImpl.setCPFriendlyURLEntryId(cpFriendlyUrlEntry.getCPFriendlyURLEntryId());
		cpFriendlyUrlEntryImpl.setGroupId(cpFriendlyUrlEntry.getGroupId());
		cpFriendlyUrlEntryImpl.setCompanyId(cpFriendlyUrlEntry.getCompanyId());
		cpFriendlyUrlEntryImpl.setUserId(cpFriendlyUrlEntry.getUserId());
		cpFriendlyUrlEntryImpl.setUserName(cpFriendlyUrlEntry.getUserName());
		cpFriendlyUrlEntryImpl.setCreateDate(cpFriendlyUrlEntry.getCreateDate());
		cpFriendlyUrlEntryImpl.setModifiedDate(cpFriendlyUrlEntry.getModifiedDate());
		cpFriendlyUrlEntryImpl.setClassNameId(cpFriendlyUrlEntry.getClassNameId());
		cpFriendlyUrlEntryImpl.setClassPK(cpFriendlyUrlEntry.getClassPK());
		cpFriendlyUrlEntryImpl.setLanguageId(cpFriendlyUrlEntry.getLanguageId());
		cpFriendlyUrlEntryImpl.setUrlTitle(cpFriendlyUrlEntry.getUrlTitle());
		cpFriendlyUrlEntryImpl.setMain(cpFriendlyUrlEntry.isMain());

		return cpFriendlyUrlEntryImpl;
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
		CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByPrimaryKey(primaryKey);

		if (cpFriendlyUrlEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPFriendlyURLEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpFriendlyUrlEntry;
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

		CPFriendlyURLEntry cpFriendlyUrlEntry = (CPFriendlyURLEntry)serializable;

		if (cpFriendlyUrlEntry == null) {
			Session session = null;

			try {
				session = openSession();

				cpFriendlyUrlEntry = (CPFriendlyURLEntry)session.get(CPFriendlyURLEntryImpl.class,
						primaryKey);

				if (cpFriendlyUrlEntry != null) {
					cacheResult(cpFriendlyUrlEntry);
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

		return cpFriendlyUrlEntry;
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

			CPFriendlyURLEntry cpFriendlyUrlEntry = fetchByPrimaryKey(primaryKey);

			if (cpFriendlyUrlEntry != null) {
				map.put(primaryKey, cpFriendlyUrlEntry);
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

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CPFriendlyURLEntry cpFriendlyUrlEntry : (List<CPFriendlyURLEntry>)q.list()) {
				map.put(cpFriendlyUrlEntry.getPrimaryKeyObj(),
					cpFriendlyUrlEntry);

				cacheResult(cpFriendlyUrlEntry);

				uncachedPrimaryKeys.remove(cpFriendlyUrlEntry.getPrimaryKeyObj());
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
		for (CPFriendlyURLEntry cpFriendlyUrlEntry : findAll()) {
			remove(cpFriendlyUrlEntry);
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
	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY = "SELECT cpFriendlyUrlEntry FROM CPFriendlyURLEntry cpFriendlyUrlEntry";
	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY_WHERE_PKS_IN = "SELECT cpFriendlyUrlEntry FROM CPFriendlyURLEntry cpFriendlyUrlEntry WHERE CPFriendlyURLEntryId IN (";
	private static final String _SQL_SELECT_CPFRIENDLYURLENTRY_WHERE = "SELECT cpFriendlyUrlEntry FROM CPFriendlyURLEntry cpFriendlyUrlEntry WHERE ";
	private static final String _SQL_COUNT_CPFRIENDLYURLENTRY = "SELECT COUNT(cpFriendlyUrlEntry) FROM CPFriendlyURLEntry cpFriendlyUrlEntry";
	private static final String _SQL_COUNT_CPFRIENDLYURLENTRY_WHERE = "SELECT COUNT(cpFriendlyUrlEntry) FROM CPFriendlyURLEntry cpFriendlyUrlEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpFriendlyUrlEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPFriendlyURLEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPFriendlyURLEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPFriendlyURLEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}