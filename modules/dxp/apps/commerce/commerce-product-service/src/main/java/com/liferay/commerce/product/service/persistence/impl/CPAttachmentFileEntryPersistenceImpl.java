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

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.impl.CPAttachmentFileEntryImpl;
import com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryPersistence;

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

import java.sql.Timestamp;

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
 * The persistence implementation for the cp attachment file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPAttachmentFileEntryPersistence
 * @see com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryUtil
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntryPersistenceImpl extends BasePersistenceImpl<CPAttachmentFileEntry>
	implements CPAttachmentFileEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPAttachmentFileEntryUtil} to access the cp attachment file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPAttachmentFileEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPAttachmentFileEntryModelImpl.UUID_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if (!Objects.equals(uuid, cpAttachmentFileEntry.getUuid())) {
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

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
				query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_First(String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_First(String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		List<CPAttachmentFileEntry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_Last(String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByUuid_PrevAndNext(
		long CPAttachmentFileEntryId, String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpAttachmentFileEntry,
					uuid, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByUuid_PrevAndNext(session, cpAttachmentFileEntry,
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

	protected CPAttachmentFileEntry getByUuid_PrevAndNext(Session session,
		CPAttachmentFileEntry cpAttachmentFileEntry, String uuid,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
			query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpAttachmentFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPAttachmentFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpAttachmentFileEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpAttachmentFileEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpAttachmentFileEntry.uuid IS NULL OR cpAttachmentFileEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPAttachmentFileEntryModelImpl.UUID_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPAttachmentFileEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUUID_G(uuid,
				groupId);

		if (cpAttachmentFileEntry == null) {
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

			throw new NoSuchCPAttachmentFileEntryException(msg.toString());
		}

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp attachment file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPAttachmentFileEntry) {
			CPAttachmentFileEntry cpAttachmentFileEntry = (CPAttachmentFileEntry)result;

			if (!Objects.equals(uuid, cpAttachmentFileEntry.getUuid()) ||
					(groupId != cpAttachmentFileEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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

				List<CPAttachmentFileEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPAttachmentFileEntry cpAttachmentFileEntry = list.get(0);

					result = cpAttachmentFileEntry;

					cacheResult(cpAttachmentFileEntry);

					if ((cpAttachmentFileEntry.getUuid() == null) ||
							!cpAttachmentFileEntry.getUuid().equals(uuid) ||
							(cpAttachmentFileEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpAttachmentFileEntry);
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
			return (CPAttachmentFileEntry)result;
		}
	}

	/**
	 * Removes the cp attachment file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp attachment file entry that was removed
	 */
	@Override
	public CPAttachmentFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByUUID_G(uuid, groupId);

		return remove(cpAttachmentFileEntry);
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpAttachmentFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpAttachmentFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpAttachmentFileEntry.uuid IS NULL OR cpAttachmentFileEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpAttachmentFileEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPAttachmentFileEntryModelImpl.UUID_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if (!Objects.equals(uuid, cpAttachmentFileEntry.getUuid()) ||
							(companyId != cpAttachmentFileEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
				query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		List<CPAttachmentFileEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByUuid_C_PrevAndNext(
		long CPAttachmentFileEntryId, String uuid, long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpAttachmentFileEntry,
					uuid, companyId, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByUuid_C_PrevAndNext(session, cpAttachmentFileEntry,
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

	protected CPAttachmentFileEntry getByUuid_C_PrevAndNext(Session session,
		CPAttachmentFileEntry cpAttachmentFileEntry, String uuid,
		long companyId,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

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
			query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpAttachmentFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPAttachmentFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpAttachmentFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpAttachmentFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpAttachmentFileEntry.uuid IS NULL OR cpAttachmentFileEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpAttachmentFileEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CPAttachmentFileEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(long classNameId, long classPK) {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findByC_C(classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId != cpAttachmentFileEntry.getClassNameId()) ||
							(classPK != cpAttachmentFileEntry.getClassPK())) {
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

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_First(long classNameId,
		long classPK, OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		List<CPAttachmentFileEntry> list = findByC_C(classNameId, classPK, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_PrevAndNext(
		long CPAttachmentFileEntryId, long classNameId, long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(session, cpAttachmentFileEntry,
					classNameId, classPK, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_PrevAndNext(session, cpAttachmentFileEntry,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByC_C_PrevAndNext(Session session,
		CPAttachmentFileEntry cpAttachmentFileEntry, long classNameId,
		long classPK,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpAttachmentFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPAttachmentFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findByC_C(
				classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "cpAttachmentFileEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "cpAttachmentFileEntry.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LTD_S = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTD_S = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] { Date.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(Date displayDate,
		int status, int start, int end) {
		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(Date displayDate,
		int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findByLtD_S(displayDate, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByLtD_S(Date displayDate,
		int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LTD_S;
		finderArgs = new Object[] {
				_getTime(displayDate), status,
				
				start, end, orderByComparator
			};

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((displayDate.getTime() <= cpAttachmentFileEntry.getDisplayDate()
																		   .getTime()) ||
							(status != cpAttachmentFileEntry.getStatus())) {
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

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByLtD_S_First(Date displayDate,
		int status, OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByLtD_S_First(displayDate,
				status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByLtD_S_First(Date displayDate,
		int status, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		List<CPAttachmentFileEntry> list = findByLtD_S(displayDate, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByLtD_S_Last(Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByLtD_S_Last(displayDate,
				status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByLtD_S_Last(Date displayDate,
		int status, OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByLtD_S(displayDate, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByLtD_S_PrevAndNext(
		long CPAttachmentFileEntryId, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByLtD_S_PrevAndNext(session, cpAttachmentFileEntry,
					displayDate, status, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByLtD_S_PrevAndNext(session, cpAttachmentFileEntry,
					displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByLtD_S_PrevAndNext(Session session,
		CPAttachmentFileEntry cpAttachmentFileEntry, Date displayDate,
		int status, OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_LTD_S_STATUS_2);

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
			query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpAttachmentFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPAttachmentFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findByLtD_S(
				displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTD_S;

		Object[] finderArgs = new Object[] { _getTime(displayDate), status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 = "cpAttachmentFileEntry.displayDate IS NULL AND ";
	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 = "cpAttachmentFileEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 = "cpAttachmentFileEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_LTD_S =
		new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_LTD_S =
		new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(long classNameId,
		long classPK, Date displayDate, int status) {
		return findByC_C_LtD_S(classNameId, classPK, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(long classNameId,
		long classPK, Date displayDate, int status, int start, int end) {
		return findByC_C_LtD_S(classNameId, classPK, displayDate, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(long classNameId,
		long classPK, Date displayDate, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findByC_C_LtD_S(classNameId, classPK, displayDate, status,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_LtD_S(long classNameId,
		long classPK, Date displayDate, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_LTD_S;
		finderArgs = new Object[] {
				classNameId, classPK, _getTime(displayDate), status,
				
				start, end, orderByComparator
			};

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId != cpAttachmentFileEntry.getClassNameId()) ||
							(classPK != cpAttachmentFileEntry.getClassPK()) ||
							(displayDate.getTime() <= cpAttachmentFileEntry.getDisplayDate()
																			   .getTime()) ||
							(status != cpAttachmentFileEntry.getStatus())) {
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

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_LTD_S_CLASSPK_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_C_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_LtD_S_First(long classNameId,
		long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_LtD_S_First(classNameId,
				classPK, displayDate, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_LtD_S_First(long classNameId,
		long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		List<CPAttachmentFileEntry> list = findByC_C_LtD_S(classNameId,
				classPK, displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_LtD_S_Last(long classNameId,
		long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_LtD_S_Last(classNameId,
				classPK, displayDate, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_LtD_S_Last(long classNameId,
		long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		int count = countByC_C_LtD_S(classNameId, classPK, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_LtD_S(classNameId,
				classPK, displayDate, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_LtD_S_PrevAndNext(
		long CPAttachmentFileEntryId, long classNameId, long classPK,
		Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_LtD_S_PrevAndNext(session,
					cpAttachmentFileEntry, classNameId, classPK, displayDate,
					status, orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_LtD_S_PrevAndNext(session,
					cpAttachmentFileEntry, classNameId, classPK, displayDate,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPAttachmentFileEntry getByC_C_LtD_S_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK, Date displayDate, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_LTD_S_CLASSPK_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_C_C_LTD_S_STATUS_2);

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
			query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpAttachmentFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPAttachmentFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByC_C_LtD_S(long classNameId, long classPK,
		Date displayDate, int status) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findByC_C_LtD_S(
				classNameId, classPK, displayDate, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_LtD_S(long classNameId, long classPK,
		Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_LTD_S;

		Object[] finderArgs = new Object[] {
				classNameId, classPK, _getTime(displayDate), status
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_LTD_S_CLASSPK_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_C_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_C_LTD_S_CLASSNAMEID_2 = "cpAttachmentFileEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_LTD_S_CLASSPK_2 = "cpAttachmentFileEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_1 = "cpAttachmentFileEntry.displayDate IS NULL AND ";
	private static final String _FINDER_COLUMN_C_C_LTD_S_DISPLAYDATE_2 = "cpAttachmentFileEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_C_C_LTD_S_STATUS_2 = "cpAttachmentFileEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_ST = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_ST =
		new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_T_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			CPAttachmentFileEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.TYPE_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.STATUS_COLUMN_BITMASK |
			CPAttachmentFileEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_T_ST = new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_T_ST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(long classNameId,
		long classPK, int type, int status) {
		return findByC_C_T_ST(classNameId, classPK, type, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(long classNameId,
		long classPK, int type, int status, int start, int end) {
		return findByC_C_T_ST(classNameId, classPK, type, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(long classNameId,
		long classPK, int type, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findByC_C_T_ST(classNameId, classPK, type, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_ST(long classNameId,
		long classPK, int type, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_ST;
			finderArgs = new Object[] { classNameId, classPK, type, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_ST;
			finderArgs = new Object[] {
					classNameId, classPK, type, status,
					
					start, end, orderByComparator
				};
		}

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId != cpAttachmentFileEntry.getClassNameId()) ||
							(classPK != cpAttachmentFileEntry.getClassPK()) ||
							(type != cpAttachmentFileEntry.getType()) ||
							(status != cpAttachmentFileEntry.getStatus())) {
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

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_ST_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_ST_TYPE_2);

			query.append(_FINDER_COLUMN_C_C_T_ST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

				if (!pagination) {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_ST_First(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_ST_First(classNameId,
				classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_ST_First(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		List<CPAttachmentFileEntry> list = findByC_C_T_ST(classNameId, classPK,
				type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_ST_Last(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_ST_Last(classNameId,
				classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_ST_Last(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		int count = countByC_C_T_ST(classNameId, classPK, type, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_T_ST(classNameId, classPK,
				type, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_T_ST_PrevAndNext(
		long CPAttachmentFileEntryId, long classNameId, long classPK, int type,
		int status, OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_T_ST_PrevAndNext(session,
					cpAttachmentFileEntry, classNameId, classPK, type, status,
					orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_T_ST_PrevAndNext(session,
					cpAttachmentFileEntry, classNameId, classPK, type, status,
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

	protected CPAttachmentFileEntry getByC_C_T_ST_PrevAndNext(Session session,
		CPAttachmentFileEntry cpAttachmentFileEntry, long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_ST_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_T_ST_TYPE_2);

		query.append(_FINDER_COLUMN_C_C_T_ST_STATUS_2);

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
			query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpAttachmentFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPAttachmentFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByC_C_T_ST(long classNameId, long classPK, int type,
		int status) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findByC_C_T_ST(
				classNameId, classPK, type, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_T_ST(long classNameId, long classPK, int type,
		int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_T_ST;

		Object[] finderArgs = new Object[] { classNameId, classPK, type, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_ST_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_ST_TYPE_2);

			query.append(_FINDER_COLUMN_C_C_T_ST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_C_T_ST_CLASSNAMEID_2 = "cpAttachmentFileEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_ST_CLASSPK_2 = "cpAttachmentFileEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_ST_TYPE_2 = "cpAttachmentFileEntry.type = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_ST_STATUS_2 = "cpAttachmentFileEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_NOTST =
		new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_T_NotST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_T_NOTST =
		new FinderPath(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_T_NotST",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(long classNameId,
		long classPK, int type, int status) {
		return findByC_C_T_NotST(classNameId, classPK, type, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(long classNameId,
		long classPK, int type, int status, int start, int end) {
		return findByC_C_T_NotST(classNameId, classPK, type, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(long classNameId,
		long classPK, int type, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findByC_C_T_NotST(classNameId, classPK, type, status, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findByC_C_T_NotST(long classNameId,
		long classPK, int type, int status, int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_T_NOTST;
		finderArgs = new Object[] {
				classNameId, classPK, type, status,
				
				start, end, orderByComparator
			};

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPAttachmentFileEntry cpAttachmentFileEntry : list) {
					if ((classNameId != cpAttachmentFileEntry.getClassNameId()) ||
							(classPK != cpAttachmentFileEntry.getClassPK()) ||
							(type != cpAttachmentFileEntry.getType()) ||
							(status == cpAttachmentFileEntry.getStatus())) {
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

			query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_TYPE_2);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

				if (!pagination) {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_NotST_First(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_NotST_First(classNameId,
				classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the first cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_NotST_First(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		List<CPAttachmentFileEntry> list = findByC_C_T_NotST(classNameId,
				classPK, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByC_C_T_NotST_Last(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByC_C_T_NotST_Last(classNameId,
				classPK, type, status, orderByComparator);

		if (cpAttachmentFileEntry != null) {
			return cpAttachmentFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", type=");
		msg.append(type);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchCPAttachmentFileEntryException(msg.toString());
	}

	/**
	 * Returns the last cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp attachment file entry, or <code>null</code> if a matching cp attachment file entry could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByC_C_T_NotST_Last(long classNameId,
		long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		int count = countByC_C_T_NotST(classNameId, classPK, type, status);

		if (count == 0) {
			return null;
		}

		List<CPAttachmentFileEntry> list = findByC_C_T_NotST(classNameId,
				classPK, type, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp attachment file entries before and after the current cp attachment file entry in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the current cp attachment file entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry[] findByC_C_T_NotST_PrevAndNext(
		long CPAttachmentFileEntryId, long classNameId, long classPK, int type,
		int status, OrderByComparator<CPAttachmentFileEntry> orderByComparator)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = findByPrimaryKey(CPAttachmentFileEntryId);

		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry[] array = new CPAttachmentFileEntryImpl[3];

			array[0] = getByC_C_T_NotST_PrevAndNext(session,
					cpAttachmentFileEntry, classNameId, classPK, type, status,
					orderByComparator, true);

			array[1] = cpAttachmentFileEntry;

			array[2] = getByC_C_T_NotST_PrevAndNext(session,
					cpAttachmentFileEntry, classNameId, classPK, type, status,
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

	protected CPAttachmentFileEntry getByC_C_T_NotST_PrevAndNext(
		Session session, CPAttachmentFileEntry cpAttachmentFileEntry,
		long classNameId, long classPK, int type, int status,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_T_NOTST_TYPE_2);

		query.append(_FINDER_COLUMN_C_C_T_NOTST_STATUS_2);

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
			query.append(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(type);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpAttachmentFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPAttachmentFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByC_C_T_NotST(long classNameId, long classPK, int type,
		int status) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findByC_C_T_NotST(
				classNameId, classPK, type, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries where classNameId = &#63; and classPK = &#63; and type = &#63; and status &ne; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param status the status
	 * @return the number of matching cp attachment file entries
	 */
	@Override
	public int countByC_C_T_NotST(long classNameId, long classPK, int type,
		int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_T_NOTST;

		Object[] finderArgs = new Object[] { classNameId, classPK, type, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_TYPE_2);

			query.append(_FINDER_COLUMN_C_C_T_NOTST_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_C_T_NOTST_CLASSNAMEID_2 = "cpAttachmentFileEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_NOTST_CLASSPK_2 = "cpAttachmentFileEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_NOTST_TYPE_2 = "cpAttachmentFileEntry.type = ? AND ";
	private static final String _FINDER_COLUMN_C_C_T_NOTST_STATUS_2 = "cpAttachmentFileEntry.status != ?";

	public CPAttachmentFileEntryPersistenceImpl() {
		setModelClass(CPAttachmentFileEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp attachment file entry in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntry the cp attachment file entry
	 */
	@Override
	public void cacheResult(CPAttachmentFileEntry cpAttachmentFileEntry) {
		entityCache.putResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			cpAttachmentFileEntry.getPrimaryKey(), cpAttachmentFileEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpAttachmentFileEntry.getUuid(),
				cpAttachmentFileEntry.getGroupId()
			}, cpAttachmentFileEntry);

		cpAttachmentFileEntry.resetOriginalValues();
	}

	/**
	 * Caches the cp attachment file entries in the entity cache if it is enabled.
	 *
	 * @param cpAttachmentFileEntries the cp attachment file entries
	 */
	@Override
	public void cacheResult(List<CPAttachmentFileEntry> cpAttachmentFileEntries) {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : cpAttachmentFileEntries) {
			if (entityCache.getResult(
						CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPAttachmentFileEntryImpl.class,
						cpAttachmentFileEntry.getPrimaryKey()) == null) {
				cacheResult(cpAttachmentFileEntry);
			}
			else {
				cpAttachmentFileEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp attachment file entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPAttachmentFileEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp attachment file entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPAttachmentFileEntry cpAttachmentFileEntry) {
		entityCache.removeResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			cpAttachmentFileEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPAttachmentFileEntryModelImpl)cpAttachmentFileEntry,
			true);
	}

	@Override
	public void clearCache(List<CPAttachmentFileEntry> cpAttachmentFileEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPAttachmentFileEntry cpAttachmentFileEntry : cpAttachmentFileEntries) {
			entityCache.removeResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPAttachmentFileEntryImpl.class,
				cpAttachmentFileEntry.getPrimaryKey());

			clearUniqueFindersCache((CPAttachmentFileEntryModelImpl)cpAttachmentFileEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPAttachmentFileEntryModelImpl cpAttachmentFileEntryModelImpl) {
		Object[] args = new Object[] {
				cpAttachmentFileEntryModelImpl.getUuid(),
				cpAttachmentFileEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpAttachmentFileEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPAttachmentFileEntryModelImpl cpAttachmentFileEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpAttachmentFileEntryModelImpl.getUuid(),
					cpAttachmentFileEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpAttachmentFileEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpAttachmentFileEntryModelImpl.getOriginalUuid(),
					cpAttachmentFileEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new cp attachment file entry with the primary key. Does not add the cp attachment file entry to the database.
	 *
	 * @param CPAttachmentFileEntryId the primary key for the new cp attachment file entry
	 * @return the new cp attachment file entry
	 */
	@Override
	public CPAttachmentFileEntry create(long CPAttachmentFileEntryId) {
		CPAttachmentFileEntry cpAttachmentFileEntry = new CPAttachmentFileEntryImpl();

		cpAttachmentFileEntry.setNew(true);
		cpAttachmentFileEntry.setPrimaryKey(CPAttachmentFileEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpAttachmentFileEntry.setUuid(uuid);

		cpAttachmentFileEntry.setCompanyId(companyProvider.getCompanyId());

		return cpAttachmentFileEntry;
	}

	/**
	 * Removes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry remove(long CPAttachmentFileEntryId)
		throws NoSuchCPAttachmentFileEntryException {
		return remove((Serializable)CPAttachmentFileEntryId);
	}

	/**
	 * Removes the cp attachment file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp attachment file entry
	 * @return the cp attachment file entry that was removed
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry remove(Serializable primaryKey)
		throws NoSuchCPAttachmentFileEntryException {
		Session session = null;

		try {
			session = openSession();

			CPAttachmentFileEntry cpAttachmentFileEntry = (CPAttachmentFileEntry)session.get(CPAttachmentFileEntryImpl.class,
					primaryKey);

			if (cpAttachmentFileEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPAttachmentFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpAttachmentFileEntry);
		}
		catch (NoSuchCPAttachmentFileEntryException nsee) {
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
	protected CPAttachmentFileEntry removeImpl(
		CPAttachmentFileEntry cpAttachmentFileEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpAttachmentFileEntry)) {
				cpAttachmentFileEntry = (CPAttachmentFileEntry)session.get(CPAttachmentFileEntryImpl.class,
						cpAttachmentFileEntry.getPrimaryKeyObj());
			}

			if (cpAttachmentFileEntry != null) {
				session.delete(cpAttachmentFileEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpAttachmentFileEntry != null) {
			clearCache(cpAttachmentFileEntry);
		}

		return cpAttachmentFileEntry;
	}

	@Override
	public CPAttachmentFileEntry updateImpl(
		CPAttachmentFileEntry cpAttachmentFileEntry) {
		boolean isNew = cpAttachmentFileEntry.isNew();

		if (!(cpAttachmentFileEntry instanceof CPAttachmentFileEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpAttachmentFileEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cpAttachmentFileEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpAttachmentFileEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPAttachmentFileEntry implementation " +
				cpAttachmentFileEntry.getClass());
		}

		CPAttachmentFileEntryModelImpl cpAttachmentFileEntryModelImpl = (CPAttachmentFileEntryModelImpl)cpAttachmentFileEntry;

		if (Validator.isNull(cpAttachmentFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpAttachmentFileEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpAttachmentFileEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpAttachmentFileEntry.setCreateDate(now);
			}
			else {
				cpAttachmentFileEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpAttachmentFileEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpAttachmentFileEntry.setModifiedDate(now);
			}
			else {
				cpAttachmentFileEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpAttachmentFileEntry.isNew()) {
				session.save(cpAttachmentFileEntry);

				cpAttachmentFileEntry.setNew(false);
			}
			else {
				cpAttachmentFileEntry = (CPAttachmentFileEntry)session.merge(cpAttachmentFileEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPAttachmentFileEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpAttachmentFileEntryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpAttachmentFileEntryModelImpl.getUuid(),
					cpAttachmentFileEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					cpAttachmentFileEntryModelImpl.getClassNameId(),
					cpAttachmentFileEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			args = new Object[] {
					cpAttachmentFileEntryModelImpl.getClassNameId(),
					cpAttachmentFileEntryModelImpl.getClassPK(),
					cpAttachmentFileEntryModelImpl.getType(),
					cpAttachmentFileEntryModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_T_ST, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_ST,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpAttachmentFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpAttachmentFileEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpAttachmentFileEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpAttachmentFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpAttachmentFileEntryModelImpl.getOriginalUuid(),
						cpAttachmentFileEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpAttachmentFileEntryModelImpl.getUuid(),
						cpAttachmentFileEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpAttachmentFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpAttachmentFileEntryModelImpl.getOriginalClassNameId(),
						cpAttachmentFileEntryModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						cpAttachmentFileEntryModelImpl.getClassNameId(),
						cpAttachmentFileEntryModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((cpAttachmentFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_ST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpAttachmentFileEntryModelImpl.getOriginalClassNameId(),
						cpAttachmentFileEntryModelImpl.getOriginalClassPK(),
						cpAttachmentFileEntryModelImpl.getOriginalType(),
						cpAttachmentFileEntryModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_T_ST, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_ST,
					args);

				args = new Object[] {
						cpAttachmentFileEntryModelImpl.getClassNameId(),
						cpAttachmentFileEntryModelImpl.getClassPK(),
						cpAttachmentFileEntryModelImpl.getType(),
						cpAttachmentFileEntryModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_T_ST, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_T_ST,
					args);
			}
		}

		entityCache.putResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPAttachmentFileEntryImpl.class,
			cpAttachmentFileEntry.getPrimaryKey(), cpAttachmentFileEntry, false);

		clearUniqueFindersCache(cpAttachmentFileEntryModelImpl, false);
		cacheUniqueFindersCache(cpAttachmentFileEntryModelImpl);

		cpAttachmentFileEntry.resetOriginalValues();

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp attachment file entry
	 * @return the cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPAttachmentFileEntryException {
		CPAttachmentFileEntry cpAttachmentFileEntry = fetchByPrimaryKey(primaryKey);

		if (cpAttachmentFileEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPAttachmentFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry with the primary key or throws a {@link NoSuchCPAttachmentFileEntryException} if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry
	 * @throws NoSuchCPAttachmentFileEntryException if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry findByPrimaryKey(long CPAttachmentFileEntryId)
		throws NoSuchCPAttachmentFileEntryException {
		return findByPrimaryKey((Serializable)CPAttachmentFileEntryId);
	}

	/**
	 * Returns the cp attachment file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp attachment file entry
	 * @return the cp attachment file entry, or <code>null</code> if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPAttachmentFileEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPAttachmentFileEntry cpAttachmentFileEntry = (CPAttachmentFileEntry)serializable;

		if (cpAttachmentFileEntry == null) {
			Session session = null;

			try {
				session = openSession();

				cpAttachmentFileEntry = (CPAttachmentFileEntry)session.get(CPAttachmentFileEntryImpl.class,
						primaryKey);

				if (cpAttachmentFileEntry != null) {
					cacheResult(cpAttachmentFileEntry);
				}
				else {
					entityCache.putResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPAttachmentFileEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPAttachmentFileEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpAttachmentFileEntry;
	}

	/**
	 * Returns the cp attachment file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPAttachmentFileEntryId the primary key of the cp attachment file entry
	 * @return the cp attachment file entry, or <code>null</code> if a cp attachment file entry with the primary key could not be found
	 */
	@Override
	public CPAttachmentFileEntry fetchByPrimaryKey(long CPAttachmentFileEntryId) {
		return fetchByPrimaryKey((Serializable)CPAttachmentFileEntryId);
	}

	@Override
	public Map<Serializable, CPAttachmentFileEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPAttachmentFileEntry> map = new HashMap<Serializable, CPAttachmentFileEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPAttachmentFileEntry cpAttachmentFileEntry = fetchByPrimaryKey(primaryKey);

			if (cpAttachmentFileEntry != null) {
				map.put(primaryKey, cpAttachmentFileEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPAttachmentFileEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPAttachmentFileEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE_PKS_IN);

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

			for (CPAttachmentFileEntry cpAttachmentFileEntry : (List<CPAttachmentFileEntry>)q.list()) {
				map.put(cpAttachmentFileEntry.getPrimaryKeyObj(),
					cpAttachmentFileEntry);

				cacheResult(cpAttachmentFileEntry);

				uncachedPrimaryKeys.remove(cpAttachmentFileEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPAttachmentFileEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPAttachmentFileEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp attachment file entries.
	 *
	 * @return the cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @return the range of cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll(int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp attachment file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPAttachmentFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp attachment file entries
	 * @param end the upper bound of the range of cp attachment file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp attachment file entries
	 */
	@Override
	public List<CPAttachmentFileEntry> findAll(int start, int end,
		OrderByComparator<CPAttachmentFileEntry> orderByComparator,
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

		List<CPAttachmentFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPAttachmentFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPATTACHMENTFILEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPATTACHMENTFILEENTRY;

				if (pagination) {
					sql = sql.concat(CPAttachmentFileEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPAttachmentFileEntry>)QueryUtil.list(q,
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
	 * Removes all the cp attachment file entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPAttachmentFileEntry cpAttachmentFileEntry : findAll()) {
			remove(cpAttachmentFileEntry);
		}
	}

	/**
	 * Returns the number of cp attachment file entries.
	 *
	 * @return the number of cp attachment file entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPATTACHMENTFILEENTRY);

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
		return CPAttachmentFileEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp attachment file entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPAttachmentFileEntryImpl.class.getName());
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

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_CPATTACHMENTFILEENTRY = "SELECT cpAttachmentFileEntry FROM CPAttachmentFileEntry cpAttachmentFileEntry";
	private static final String _SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE_PKS_IN = "SELECT cpAttachmentFileEntry FROM CPAttachmentFileEntry cpAttachmentFileEntry WHERE CPAttachmentFileEntryId IN (";
	private static final String _SQL_SELECT_CPATTACHMENTFILEENTRY_WHERE = "SELECT cpAttachmentFileEntry FROM CPAttachmentFileEntry cpAttachmentFileEntry WHERE ";
	private static final String _SQL_COUNT_CPATTACHMENTFILEENTRY = "SELECT COUNT(cpAttachmentFileEntry) FROM CPAttachmentFileEntry cpAttachmentFileEntry";
	private static final String _SQL_COUNT_CPATTACHMENTFILEENTRY_WHERE = "SELECT COUNT(cpAttachmentFileEntry) FROM CPAttachmentFileEntry cpAttachmentFileEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpAttachmentFileEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPAttachmentFileEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPAttachmentFileEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPAttachmentFileEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
}