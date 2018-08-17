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

package com.liferay.sharing.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.impl.SharingEntryImpl;
import com.liferay.sharing.model.impl.SharingEntryModelImpl;
import com.liferay.sharing.service.persistence.SharingEntryPersistence;

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
 * The persistence implementation for the sharing entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryPersistence
 * @see com.liferay.sharing.service.persistence.SharingEntryUtil
 * @generated
 */
@ProviderType
public class SharingEntryPersistenceImpl extends BasePersistenceImpl<SharingEntry>
	implements SharingEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SharingEntryUtil} to access the sharing entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SharingEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			SharingEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the sharing entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (!Objects.equals(uuid, sharingEntry.getUuid())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_First(String uuid,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByUuid_First(uuid, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_First(String uuid,
		OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_Last(String uuid,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_Last(String uuid,
		OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByUuid_PrevAndNext(long sharingEntryId,
		String uuid, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, sharingEntry, uuid,
					orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByUuid_PrevAndNext(session, sharingEntry, uuid,
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

	protected SharingEntry getByUuid_PrevAndNext(Session session,
		SharingEntry sharingEntry, String uuid,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SharingEntry sharingEntry : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "sharingEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "sharingEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(sharingEntry.uuid IS NULL OR sharingEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			SharingEntryModelImpl.UUID_COLUMN_BITMASK |
			SharingEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByUUID_G(uuid, groupId);

		if (sharingEntry == null) {
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

			throw new NoSuchEntryException(msg.toString());
		}

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof SharingEntry) {
			SharingEntry sharingEntry = (SharingEntry)result;

			if (!Objects.equals(uuid, sharingEntry.getUuid()) ||
					(groupId != sharingEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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

				List<SharingEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					SharingEntry sharingEntry = list.get(0);

					result = sharingEntry;

					cacheResult(sharingEntry);
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
			return (SharingEntry)result;
		}
	}

	/**
	 * Removes the sharing entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the sharing entry that was removed
	 */
	@Override
	public SharingEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByUUID_G(uuid, groupId);

		return remove(sharingEntry);
	}

	/**
	 * Returns the number of sharing entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "sharingEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "sharingEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(sharingEntry.uuid IS NULL OR sharingEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "sharingEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			SharingEntryModelImpl.UUID_COLUMN_BITMASK |
			SharingEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if (!Objects.equals(uuid, sharingEntry.getUuid()) ||
							(companyId != sharingEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByUuid_C_PrevAndNext(long sharingEntryId,
		String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, sharingEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByUuid_C_PrevAndNext(session, sharingEntry, uuid,
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

	protected SharingEntry getByUuid_C_PrevAndNext(Session session,
		SharingEntry sharingEntry, String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SharingEntry sharingEntry : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "sharingEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "sharingEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(sharingEntry.uuid IS NULL OR sharingEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "sharingEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			SharingEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the sharing entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((groupId != sharingEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByGroupId_First(long groupId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByGroupId_First(long groupId,
		OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByGroupId_Last(long groupId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByGroupId_PrevAndNext(long sharingEntryId,
		long groupId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, sharingEntry, groupId,
					orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByGroupId_PrevAndNext(session, sharingEntry, groupId,
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

	protected SharingEntry getByGroupId_PrevAndNext(Session session,
		SharingEntry sharingEntry, long groupId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SharingEntry sharingEntry : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "sharingEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FROMUSERID =
		new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFromUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FROMUSERID =
		new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFromUserId",
			new String[] { Long.class.getName() },
			SharingEntryModelImpl.FROMUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FROMUSERID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFromUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the sharing entries where fromUserId = &#63;.
	 *
	 * @param fromUserId the from user ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByFromUserId(long fromUserId) {
		return findByFromUserId(fromUserId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where fromUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fromUserId the from user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByFromUserId(long fromUserId, int start,
		int end) {
		return findByFromUserId(fromUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where fromUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fromUserId the from user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByFromUserId(long fromUserId, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator) {
		return findByFromUserId(fromUserId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where fromUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fromUserId the from user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByFromUserId(long fromUserId, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FROMUSERID;
			finderArgs = new Object[] { fromUserId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FROMUSERID;
			finderArgs = new Object[] { fromUserId, start, end, orderByComparator };
		}

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((fromUserId != sharingEntry.getFromUserId())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_FROMUSERID_FROMUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fromUserId);

				if (!pagination) {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where fromUserId = &#63;.
	 *
	 * @param fromUserId the from user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByFromUserId_First(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByFromUserId_First(fromUserId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fromUserId=");
		msg.append(fromUserId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where fromUserId = &#63;.
	 *
	 * @param fromUserId the from user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByFromUserId_First(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByFromUserId(fromUserId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where fromUserId = &#63;.
	 *
	 * @param fromUserId the from user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByFromUserId_Last(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByFromUserId_Last(fromUserId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fromUserId=");
		msg.append(fromUserId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where fromUserId = &#63;.
	 *
	 * @param fromUserId the from user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByFromUserId_Last(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByFromUserId(fromUserId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByFromUserId(fromUserId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where fromUserId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param fromUserId the from user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByFromUserId_PrevAndNext(long sharingEntryId,
		long fromUserId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByFromUserId_PrevAndNext(session, sharingEntry,
					fromUserId, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByFromUserId_PrevAndNext(session, sharingEntry,
					fromUserId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByFromUserId_PrevAndNext(Session session,
		SharingEntry sharingEntry, long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		query.append(_FINDER_COLUMN_FROMUSERID_FROMUSERID_2);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fromUserId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where fromUserId = &#63; from the database.
	 *
	 * @param fromUserId the from user ID
	 */
	@Override
	public void removeByFromUserId(long fromUserId) {
		for (SharingEntry sharingEntry : findByFromUserId(fromUserId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where fromUserId = &#63;.
	 *
	 * @param fromUserId the from user ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByFromUserId(long fromUserId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FROMUSERID;

		Object[] finderArgs = new Object[] { fromUserId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_FROMUSERID_FROMUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fromUserId);

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

	private static final String _FINDER_COLUMN_FROMUSERID_FROMUSERID_2 = "sharingEntry.fromUserId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TOUSERID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByToUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOUSERID =
		new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByToUserId",
			new String[] { Long.class.getName() },
			SharingEntryModelImpl.TOUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TOUSERID = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByToUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the sharing entries where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(long toUserId) {
		return findByToUserId(toUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(long toUserId, int start, int end) {
		return findByToUserId(toUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(long toUserId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return findByToUserId(toUserId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByToUserId(long toUserId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOUSERID;
			finderArgs = new Object[] { toUserId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TOUSERID;
			finderArgs = new Object[] { toUserId, start, end, orderByComparator };
		}

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((toUserId != sharingEntry.getToUserId())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_TOUSERID_TOUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toUserId);

				if (!pagination) {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByToUserId_First(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByToUserId_First(toUserId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toUserId=");
		msg.append(toUserId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByToUserId_First(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByToUserId(toUserId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByToUserId_Last(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByToUserId_Last(toUserId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toUserId=");
		msg.append(toUserId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByToUserId_Last(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByToUserId(toUserId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByToUserId(toUserId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByToUserId_PrevAndNext(long sharingEntryId,
		long toUserId, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByToUserId_PrevAndNext(session, sharingEntry,
					toUserId, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByToUserId_PrevAndNext(session, sharingEntry,
					toUserId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByToUserId_PrevAndNext(Session session,
		SharingEntry sharingEntry, long toUserId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		query.append(_FINDER_COLUMN_TOUSERID_TOUSERID_2);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(toUserId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where toUserId = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 */
	@Override
	public void removeByToUserId(long toUserId) {
		for (SharingEntry sharingEntry : findByToUserId(toUserId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByToUserId(long toUserId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TOUSERID;

		Object[] finderArgs = new Object[] { toUserId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_TOUSERID_TOUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toUserId);

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

	private static final String _FINDER_COLUMN_TOUSERID_TOUSERID_2 = "sharingEntry.toUserId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TU_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTU_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTU_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SharingEntryModelImpl.TOUSERID_COLUMN_BITMASK |
			SharingEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TU_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTU_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(long toUserId, long classNameId) {
		return findByTU_C(toUserId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(long toUserId, long classNameId,
		int start, int end) {
		return findByTU_C(toUserId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(long toUserId, long classNameId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator) {
		return findByTU_C(toUserId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C(long toUserId, long classNameId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C;
			finderArgs = new Object[] { toUserId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TU_C;
			finderArgs = new Object[] {
					toUserId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((toUserId != sharingEntry.getToUserId()) ||
							(classNameId != sharingEntry.getClassNameId())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_TU_C_TOUSERID_2);

			query.append(_FINDER_COLUMN_TU_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toUserId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByTU_C_First(long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByTU_C_First(toUserId, classNameId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toUserId=");
		msg.append(toUserId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_First(long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByTU_C(toUserId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByTU_C_Last(long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByTU_C_Last(toUserId, classNameId,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toUserId=");
		msg.append(toUserId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_Last(long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByTU_C(toUserId, classNameId);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByTU_C(toUserId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByTU_C_PrevAndNext(long sharingEntryId,
		long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByTU_C_PrevAndNext(session, sharingEntry, toUserId,
					classNameId, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByTU_C_PrevAndNext(session, sharingEntry, toUserId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByTU_C_PrevAndNext(Session session,
		SharingEntry sharingEntry, long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		query.append(_FINDER_COLUMN_TU_C_TOUSERID_2);

		query.append(_FINDER_COLUMN_TU_C_CLASSNAMEID_2);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(toUserId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where toUserId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByTU_C(long toUserId, long classNameId) {
		for (SharingEntry sharingEntry : findByTU_C(toUserId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByTU_C(long toUserId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TU_C;

		Object[] finderArgs = new Object[] { toUserId, classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_TU_C_TOUSERID_2);

			query.append(_FINDER_COLUMN_TU_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toUserId);

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_TU_C_TOUSERID_2 = "sharingEntry.toUserId = ? AND ";
	private static final String _FINDER_COLUMN_TU_C_CLASSNAMEID_2 = "sharingEntry.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			SharingEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SharingEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(long classNameId, long classPK) {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(long classNameId, long classPK,
		int start, int end) {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator) {
		return findByC_C(classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((classNameId != sharingEntry.getClassNameId()) ||
							(classPK != sharingEntry.getClassPK())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByC_C_First(classNameId, classPK,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByC_C_Last(classNameId, classPK,
				orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByC_C(classNameId, classPK, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByC_C_PrevAndNext(long sharingEntryId,
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(session, sharingEntry, classNameId,
					classPK, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByC_C_PrevAndNext(session, sharingEntry, classNameId,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharingEntry getByC_C_PrevAndNext(Session session,
		SharingEntry sharingEntry, long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (SharingEntry sharingEntry : findByC_C(classNameId, classPK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "sharingEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "sharingEntry.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TU_C_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C_C =
		new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SharingEntryModelImpl.TOUSERID_COLUMN_BITMASK |
			SharingEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SharingEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TU_C_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C_C(long toUserId, long classNameId,
		long classPK) {
		return findByTU_C_C(toUserId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C_C(long toUserId, long classNameId,
		long classPK, int start, int end) {
		return findByTU_C_C(toUserId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C_C(long toUserId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return findByTU_C_C(toUserId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	@Override
	public List<SharingEntry> findByTU_C_C(long toUserId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C_C;
			finderArgs = new Object[] { toUserId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TU_C_C;
			finderArgs = new Object[] {
					toUserId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharingEntry sharingEntry : list) {
					if ((toUserId != sharingEntry.getToUserId()) ||
							(classNameId != sharingEntry.getClassNameId()) ||
							(classPK != sharingEntry.getClassPK())) {
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

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_TU_C_C_TOUSERID_2);

			query.append(_FINDER_COLUMN_TU_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_TU_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toUserId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByTU_C_C_First(long toUserId, long classNameId,
		long classPK, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByTU_C_C_First(toUserId, classNameId,
				classPK, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toUserId=");
		msg.append(toUserId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_C_First(long toUserId, long classNameId,
		long classPK, OrderByComparator<SharingEntry> orderByComparator) {
		List<SharingEntry> list = findByTU_C_C(toUserId, classNameId, classPK,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByTU_C_C_Last(long toUserId, long classNameId,
		long classPK, OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByTU_C_C_Last(toUserId, classNameId,
				classPK, orderByComparator);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toUserId=");
		msg.append(toUserId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByTU_C_C_Last(long toUserId, long classNameId,
		long classPK, OrderByComparator<SharingEntry> orderByComparator) {
		int count = countByTU_C_C(toUserId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SharingEntry> list = findByTU_C_C(toUserId, classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param sharingEntryId the primary key of the current sharing entry
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry[] findByTU_C_C_PrevAndNext(long sharingEntryId,
		long toUserId, long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = findByPrimaryKey(sharingEntryId);

		Session session = null;

		try {
			session = openSession();

			SharingEntry[] array = new SharingEntryImpl[3];

			array[0] = getByTU_C_C_PrevAndNext(session, sharingEntry, toUserId,
					classNameId, classPK, orderByComparator, true);

			array[1] = sharingEntry;

			array[2] = getByTU_C_C_PrevAndNext(session, sharingEntry, toUserId,
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

	protected SharingEntry getByTU_C_C_PrevAndNext(Session session,
		SharingEntry sharingEntry, long toUserId, long classNameId,
		long classPK, OrderByComparator<SharingEntry> orderByComparator,
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

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

		query.append(_FINDER_COLUMN_TU_C_C_TOUSERID_2);

		query.append(_FINDER_COLUMN_TU_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_TU_C_C_CLASSPK_2);

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
			query.append(SharingEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(toUserId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sharingEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SharingEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByTU_C_C(long toUserId, long classNameId, long classPK) {
		for (SharingEntry sharingEntry : findByTU_C_C(toUserId, classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByTU_C_C(long toUserId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TU_C_C;

		Object[] finderArgs = new Object[] { toUserId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_TU_C_C_TOUSERID_2);

			query.append(_FINDER_COLUMN_TU_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_TU_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toUserId);

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

	private static final String _FINDER_COLUMN_TU_C_C_TOUSERID_2 = "sharingEntry.toUserId = ? AND ";
	private static final String _FINDER_COLUMN_TU_C_C_CLASSNAMEID_2 = "sharingEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_TU_C_C_CLASSPK_2 = "sharingEntry.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_FU_TU_C_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, SharingEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByFU_TU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			SharingEntryModelImpl.FROMUSERID_COLUMN_BITMASK |
			SharingEntryModelImpl.TOUSERID_COLUMN_BITMASK |
			SharingEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			SharingEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FU_TU_C_C = new FinderPath(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFU_TU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param fromUserId the from user ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry findByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK) throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByFU_TU_C_C(fromUserId, toUserId,
				classNameId, classPK);

		if (sharingEntry == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fromUserId=");
			msg.append(fromUserId);

			msg.append(", toUserId=");
			msg.append(toUserId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fromUserId the from user ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK) {
		return fetchByFU_TU_C_C(fromUserId, toUserId, classNameId, classPK, true);
	}

	/**
	 * Returns the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fromUserId the from user ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	@Override
	public SharingEntry fetchByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				fromUserId, toUserId, classNameId, classPK
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_FU_TU_C_C,
					finderArgs, this);
		}

		if (result instanceof SharingEntry) {
			SharingEntry sharingEntry = (SharingEntry)result;

			if ((fromUserId != sharingEntry.getFromUserId()) ||
					(toUserId != sharingEntry.getToUserId()) ||
					(classNameId != sharingEntry.getClassNameId()) ||
					(classPK != sharingEntry.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_FU_TU_C_C_FROMUSERID_2);

			query.append(_FINDER_COLUMN_FU_TU_C_C_TOUSERID_2);

			query.append(_FINDER_COLUMN_FU_TU_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_FU_TU_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fromUserId);

				qPos.add(toUserId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<SharingEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_FU_TU_C_C,
						finderArgs, list);
				}
				else {
					SharingEntry sharingEntry = list.get(0);

					result = sharingEntry;

					cacheResult(sharingEntry);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_FU_TU_C_C,
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
			return (SharingEntry)result;
		}
	}

	/**
	 * Removes the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param fromUserId the from user ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the sharing entry that was removed
	 */
	@Override
	public SharingEntry removeByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK) throws NoSuchEntryException {
		SharingEntry sharingEntry = findByFU_TU_C_C(fromUserId, toUserId,
				classNameId, classPK);

		return remove(sharingEntry);
	}

	/**
	 * Returns the number of sharing entries where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param fromUserId the from user ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching sharing entries
	 */
	@Override
	public int countByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FU_TU_C_C;

		Object[] finderArgs = new Object[] {
				fromUserId, toUserId, classNameId, classPK
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_SHARINGENTRY_WHERE);

			query.append(_FINDER_COLUMN_FU_TU_C_C_FROMUSERID_2);

			query.append(_FINDER_COLUMN_FU_TU_C_C_TOUSERID_2);

			query.append(_FINDER_COLUMN_FU_TU_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_FU_TU_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fromUserId);

				qPos.add(toUserId);

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

	private static final String _FINDER_COLUMN_FU_TU_C_C_FROMUSERID_2 = "sharingEntry.fromUserId = ? AND ";
	private static final String _FINDER_COLUMN_FU_TU_C_C_TOUSERID_2 = "sharingEntry.toUserId = ? AND ";
	private static final String _FINDER_COLUMN_FU_TU_C_C_CLASSNAMEID_2 = "sharingEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_FU_TU_C_C_CLASSPK_2 = "sharingEntry.classPK = ?";

	public SharingEntryPersistenceImpl() {
		setModelClass(SharingEntry.class);

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
	 * Caches the sharing entry in the entity cache if it is enabled.
	 *
	 * @param sharingEntry the sharing entry
	 */
	@Override
	public void cacheResult(SharingEntry sharingEntry) {
		entityCache.putResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryImpl.class, sharingEntry.getPrimaryKey(), sharingEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { sharingEntry.getUuid(), sharingEntry.getGroupId() },
			sharingEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_FU_TU_C_C,
			new Object[] {
				sharingEntry.getFromUserId(), sharingEntry.getToUserId(),
				sharingEntry.getClassNameId(), sharingEntry.getClassPK()
			}, sharingEntry);

		sharingEntry.resetOriginalValues();
	}

	/**
	 * Caches the sharing entries in the entity cache if it is enabled.
	 *
	 * @param sharingEntries the sharing entries
	 */
	@Override
	public void cacheResult(List<SharingEntry> sharingEntries) {
		for (SharingEntry sharingEntry : sharingEntries) {
			if (entityCache.getResult(
						SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
						SharingEntryImpl.class, sharingEntry.getPrimaryKey()) == null) {
				cacheResult(sharingEntry);
			}
			else {
				sharingEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all sharing entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SharingEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the sharing entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SharingEntry sharingEntry) {
		entityCache.removeResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryImpl.class, sharingEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SharingEntryModelImpl)sharingEntry, true);
	}

	@Override
	public void clearCache(List<SharingEntry> sharingEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SharingEntry sharingEntry : sharingEntries) {
			entityCache.removeResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
				SharingEntryImpl.class, sharingEntry.getPrimaryKey());

			clearUniqueFindersCache((SharingEntryModelImpl)sharingEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SharingEntryModelImpl sharingEntryModelImpl) {
		Object[] args = new Object[] {
				sharingEntryModelImpl.getUuid(),
				sharingEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			sharingEntryModelImpl, false);

		args = new Object[] {
				sharingEntryModelImpl.getFromUserId(),
				sharingEntryModelImpl.getToUserId(),
				sharingEntryModelImpl.getClassNameId(),
				sharingEntryModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_FU_TU_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_FU_TU_C_C, args,
			sharingEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SharingEntryModelImpl sharingEntryModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					sharingEntryModelImpl.getUuid(),
					sharingEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((sharingEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					sharingEntryModelImpl.getOriginalUuid(),
					sharingEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					sharingEntryModelImpl.getFromUserId(),
					sharingEntryModelImpl.getToUserId(),
					sharingEntryModelImpl.getClassNameId(),
					sharingEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FU_TU_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_FU_TU_C_C, args);
		}

		if ((sharingEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_FU_TU_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					sharingEntryModelImpl.getOriginalFromUserId(),
					sharingEntryModelImpl.getOriginalToUserId(),
					sharingEntryModelImpl.getOriginalClassNameId(),
					sharingEntryModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FU_TU_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_FU_TU_C_C, args);
		}
	}

	/**
	 * Creates a new sharing entry with the primary key. Does not add the sharing entry to the database.
	 *
	 * @param sharingEntryId the primary key for the new sharing entry
	 * @return the new sharing entry
	 */
	@Override
	public SharingEntry create(long sharingEntryId) {
		SharingEntry sharingEntry = new SharingEntryImpl();

		sharingEntry.setNew(true);
		sharingEntry.setPrimaryKey(sharingEntryId);

		String uuid = PortalUUIDUtil.generate();

		sharingEntry.setUuid(uuid);

		sharingEntry.setCompanyId(companyProvider.getCompanyId());

		return sharingEntry;
	}

	/**
	 * Removes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry that was removed
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry remove(long sharingEntryId) throws NoSuchEntryException {
		return remove((Serializable)sharingEntryId);
	}

	/**
	 * Removes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sharing entry
	 * @return the sharing entry that was removed
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			SharingEntry sharingEntry = (SharingEntry)session.get(SharingEntryImpl.class,
					primaryKey);

			if (sharingEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(sharingEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected SharingEntry removeImpl(SharingEntry sharingEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(sharingEntry)) {
				sharingEntry = (SharingEntry)session.get(SharingEntryImpl.class,
						sharingEntry.getPrimaryKeyObj());
			}

			if (sharingEntry != null) {
				session.delete(sharingEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (sharingEntry != null) {
			clearCache(sharingEntry);
		}

		return sharingEntry;
	}

	@Override
	public SharingEntry updateImpl(SharingEntry sharingEntry) {
		boolean isNew = sharingEntry.isNew();

		if (!(sharingEntry instanceof SharingEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(sharingEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(sharingEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in sharingEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SharingEntry implementation " +
				sharingEntry.getClass());
		}

		SharingEntryModelImpl sharingEntryModelImpl = (SharingEntryModelImpl)sharingEntry;

		if (Validator.isNull(sharingEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			sharingEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (sharingEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				sharingEntry.setCreateDate(now);
			}
			else {
				sharingEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!sharingEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				sharingEntry.setModifiedDate(now);
			}
			else {
				sharingEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (sharingEntry.isNew()) {
				session.save(sharingEntry);

				sharingEntry.setNew(false);
			}
			else {
				sharingEntry = (SharingEntry)session.merge(sharingEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SharingEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { sharingEntryModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					sharingEntryModelImpl.getUuid(),
					sharingEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { sharingEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { sharingEntryModelImpl.getFromUserId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FROMUSERID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FROMUSERID,
				args);

			args = new Object[] { sharingEntryModelImpl.getToUserId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TOUSERID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOUSERID,
				args);

			args = new Object[] {
					sharingEntryModelImpl.getToUserId(),
					sharingEntryModelImpl.getClassNameId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TU_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C,
				args);

			args = new Object[] {
					sharingEntryModelImpl.getClassNameId(),
					sharingEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			args = new Object[] {
					sharingEntryModelImpl.getToUserId(),
					sharingEntryModelImpl.getClassNameId(),
					sharingEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TU_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { sharingEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalUuid(),
						sharingEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						sharingEntryModelImpl.getUuid(),
						sharingEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { sharingEntryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FROMUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalFromUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FROMUSERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FROMUSERID,
					args);

				args = new Object[] { sharingEntryModelImpl.getFromUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FROMUSERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FROMUSERID,
					args);
			}

			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalToUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TOUSERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOUSERID,
					args);

				args = new Object[] { sharingEntryModelImpl.getToUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TOUSERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOUSERID,
					args);
			}

			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalToUserId(),
						sharingEntryModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TU_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C,
					args);

				args = new Object[] {
						sharingEntryModelImpl.getToUserId(),
						sharingEntryModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TU_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C,
					args);
			}

			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalClassNameId(),
						sharingEntryModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						sharingEntryModelImpl.getClassNameId(),
						sharingEntryModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((sharingEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sharingEntryModelImpl.getOriginalToUserId(),
						sharingEntryModelImpl.getOriginalClassNameId(),
						sharingEntryModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TU_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C_C,
					args);

				args = new Object[] {
						sharingEntryModelImpl.getToUserId(),
						sharingEntryModelImpl.getClassNameId(),
						sharingEntryModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TU_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TU_C_C,
					args);
			}
		}

		entityCache.putResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharingEntryImpl.class, sharingEntry.getPrimaryKey(), sharingEntry,
			false);

		clearUniqueFindersCache(sharingEntryModelImpl, false);
		cacheUniqueFindersCache(sharingEntryModelImpl);

		sharingEntry.resetOriginalValues();

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the sharing entry
	 * @return the sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {
		SharingEntry sharingEntry = fetchByPrimaryKey(primaryKey);

		if (sharingEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry findByPrimaryKey(long sharingEntryId)
		throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)sharingEntryId);
	}

	/**
	 * Returns the sharing entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sharing entry
	 * @return the sharing entry, or <code>null</code> if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
				SharingEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SharingEntry sharingEntry = (SharingEntry)serializable;

		if (sharingEntry == null) {
			Session session = null;

			try {
				session = openSession();

				sharingEntry = (SharingEntry)session.get(SharingEntryImpl.class,
						primaryKey);

				if (sharingEntry != null) {
					cacheResult(sharingEntry);
				}
				else {
					entityCache.putResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
						SharingEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
					SharingEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return sharingEntry;
	}

	/**
	 * Returns the sharing entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry, or <code>null</code> if a sharing entry with the primary key could not be found
	 */
	@Override
	public SharingEntry fetchByPrimaryKey(long sharingEntryId) {
		return fetchByPrimaryKey((Serializable)sharingEntryId);
	}

	@Override
	public Map<Serializable, SharingEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SharingEntry> map = new HashMap<Serializable, SharingEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SharingEntry sharingEntry = fetchByPrimaryKey(primaryKey);

			if (sharingEntry != null) {
				map.put(primaryKey, sharingEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
					SharingEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SharingEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SHARINGENTRY_WHERE_PKS_IN);

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

			for (SharingEntry sharingEntry : (List<SharingEntry>)q.list()) {
				map.put(sharingEntry.getPrimaryKeyObj(), sharingEntry);

				cacheResult(sharingEntry);

				uncachedPrimaryKeys.remove(sharingEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SharingEntryModelImpl.ENTITY_CACHE_ENABLED,
					SharingEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the sharing entries.
	 *
	 * @return the sharing entries
	 */
	@Override
	public List<SharingEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of sharing entries
	 */
	@Override
	public List<SharingEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sharing entries
	 */
	@Override
	public List<SharingEntry> findAll(int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of sharing entries
	 */
	@Override
	public List<SharingEntry> findAll(int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
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

		List<SharingEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SharingEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SHARINGENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHARINGENTRY;

				if (pagination) {
					sql = sql.concat(SharingEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharingEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the sharing entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SharingEntry sharingEntry : findAll()) {
			remove(sharingEntry);
		}
	}

	/**
	 * Returns the number of sharing entries.
	 *
	 * @return the number of sharing entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHARINGENTRY);

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
		return SharingEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sharing entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SharingEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_SHARINGENTRY = "SELECT sharingEntry FROM SharingEntry sharingEntry";
	private static final String _SQL_SELECT_SHARINGENTRY_WHERE_PKS_IN = "SELECT sharingEntry FROM SharingEntry sharingEntry WHERE sharingEntryId IN (";
	private static final String _SQL_SELECT_SHARINGENTRY_WHERE = "SELECT sharingEntry FROM SharingEntry sharingEntry WHERE ";
	private static final String _SQL_COUNT_SHARINGENTRY = "SELECT COUNT(sharingEntry) FROM SharingEntry sharingEntry";
	private static final String _SQL_COUNT_SHARINGENTRY_WHERE = "SELECT COUNT(sharingEntry) FROM SharingEntry sharingEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "sharingEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SharingEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SharingEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SharingEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}