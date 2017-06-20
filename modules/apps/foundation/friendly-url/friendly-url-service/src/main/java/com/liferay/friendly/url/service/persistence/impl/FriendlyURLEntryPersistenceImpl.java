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

package com.liferay.friendly.url.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryPersistence;

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
 * The persistence implementation for the friendly url entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryPersistence
 * @see com.liferay.friendly.url.service.persistence.FriendlyURLEntryUtil
 * @generated
 */
@ProviderType
public class FriendlyURLEntryPersistenceImpl extends BasePersistenceImpl<FriendlyURLEntry>
	implements FriendlyURLEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FriendlyURLEntryUtil} to access the friendly url entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FriendlyURLEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			FriendlyURLEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the friendly url entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @return the range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator,
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

		List<FriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLEntry friendlyURLEntry : list) {
					if (!Objects.equals(uuid, friendlyURLEntry.getUuid())) {
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

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

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
				query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByUuid_First(String uuid,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByUuid_First(String uuid,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		List<FriendlyURLEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByUuid_Last(String uuid,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByUuid_Last(String uuid,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url entries before and after the current friendly url entry in the ordered set where uuid = &#63;.
	 *
	 * @param friendlyURLEntryId the primary key of the current friendly url entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry[] findByUuid_PrevAndNext(long friendlyURLEntryId,
		String uuid, OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByPrimaryKey(friendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntry[] array = new FriendlyURLEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, friendlyURLEntry, uuid,
					orderByComparator, true);

			array[1] = friendlyURLEntry;

			array[2] = getByUuid_PrevAndNext(session, friendlyURLEntry, uuid,
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

	protected FriendlyURLEntry getByUuid_PrevAndNext(Session session,
		FriendlyURLEntry friendlyURLEntry, String uuid,
		OrderByComparator<FriendlyURLEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

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
			query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FriendlyURLEntry friendlyURLEntry : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(friendlyURLEntry);
		}
	}

	/**
	 * Returns the number of friendly url entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "friendlyURLEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "friendlyURLEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(friendlyURLEntry.uuid IS NULL OR friendlyURLEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			FriendlyURLEntryModelImpl.UUID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the friendly url entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByUUID_G(uuid, groupId);

		if (friendlyURLEntry == null) {
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

			throw new NoSuchFriendlyURLEntryException(msg.toString());
		}

		return friendlyURLEntry;
	}

	/**
	 * Returns the friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLEntry) {
			FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)result;

			if (!Objects.equals(uuid, friendlyURLEntry.getUuid()) ||
					(groupId != friendlyURLEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

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

				List<FriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					FriendlyURLEntry friendlyURLEntry = list.get(0);

					result = friendlyURLEntry;

					cacheResult(friendlyURLEntry);

					if ((friendlyURLEntry.getUuid() == null) ||
							!friendlyURLEntry.getUuid().equals(uuid) ||
							(friendlyURLEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, friendlyURLEntry);
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
			return (FriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the friendly url entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the friendly url entry that was removed
	 */
	@Override
	public FriendlyURLEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByUUID_G(uuid, groupId);

		return remove(friendlyURLEntry);
	}

	/**
	 * Returns the number of friendly url entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "friendlyURLEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "friendlyURLEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(friendlyURLEntry.uuid IS NULL OR friendlyURLEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "friendlyURLEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			FriendlyURLEntryModelImpl.UUID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @return the range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator,
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

		List<FriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLEntry friendlyURLEntry : list) {
					if (!Objects.equals(uuid, friendlyURLEntry.getUuid()) ||
							(companyId != friendlyURLEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

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
				query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		List<FriendlyURLEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLEntry> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url entries before and after the current friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param friendlyURLEntryId the primary key of the current friendly url entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry[] findByUuid_C_PrevAndNext(
		long friendlyURLEntryId, String uuid, long companyId,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByPrimaryKey(friendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntry[] array = new FriendlyURLEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, friendlyURLEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = friendlyURLEntry;

			array[2] = getByUuid_C_PrevAndNext(session, friendlyURLEntry, uuid,
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

	protected FriendlyURLEntry getByUuid_C_PrevAndNext(Session session,
		FriendlyURLEntry friendlyURLEntry, String uuid, long companyId,
		OrderByComparator<FriendlyURLEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

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
			query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FriendlyURLEntry friendlyURLEntry : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(friendlyURLEntry);
		}
	}

	/**
	 * Returns the number of friendly url entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "friendlyURLEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "friendlyURLEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(friendlyURLEntry.uuid IS NULL OR friendlyURLEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "friendlyURLEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			FriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C(long groupId, long classNameId) {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @return the range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C(long groupId, long classNameId,
		int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		return findByG_C(groupId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C(long groupId, long classNameId,
		int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<FriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLEntry friendlyURLEntry : list) {
					if ((groupId != friendlyURLEntry.getGroupId()) ||
							(classNameId != friendlyURLEntry.getClassNameId())) {
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

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByG_C_First(long groupId, long classNameId,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByG_C_First(groupId,
				classNameId, orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		List<FriendlyURLEntry> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByG_C_Last(groupId,
				classNameId, orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLEntry> list = findByG_C(groupId, classNameId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url entries before and after the current friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param friendlyURLEntryId the primary key of the current friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry[] findByG_C_PrevAndNext(long friendlyURLEntryId,
		long groupId, long classNameId,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByPrimaryKey(friendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntry[] array = new FriendlyURLEntryImpl[3];

			array[0] = getByG_C_PrevAndNext(session, friendlyURLEntry, groupId,
					classNameId, orderByComparator, true);

			array[1] = friendlyURLEntry;

			array[2] = getByG_C_PrevAndNext(session, friendlyURLEntry, groupId,
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

	protected FriendlyURLEntry getByG_C_PrevAndNext(Session session,
		FriendlyURLEntry friendlyURLEntry, long groupId, long classNameId,
		OrderByComparator<FriendlyURLEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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
			query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url entries where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (FriendlyURLEntry friendlyURLEntry : findByG_C(groupId,
				classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(friendlyURLEntry);
		}
	}

	/**
	 * Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "friendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "friendlyURLEntry.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			FriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK) {
		return findByG_C_C(groupId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @return the range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) {
		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		return findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator,
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

		List<FriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLEntry friendlyURLEntry : list) {
					if ((groupId != friendlyURLEntry.getGroupId()) ||
							(classNameId != friendlyURLEntry.getClassNameId()) ||
							(classPK != friendlyURLEntry.getClassPK())) {
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

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
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
	 * Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByG_C_C_First(groupId,
				classNameId, classPK, orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<FriendlyURLEntry> orderByComparator) {
		List<FriendlyURLEntry> list = findByG_C_C(groupId, classNameId,
				classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByG_C_C_Last(groupId,
				classNameId, classPK, orderByComparator);

		if (friendlyURLEntry != null) {
			return friendlyURLEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryException(msg.toString());
	}

	/**
	 * Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<FriendlyURLEntry> orderByComparator) {
		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLEntry> list = findByG_C_C(groupId, classNameId,
				classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url entries before and after the current friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param friendlyURLEntryId the primary key of the current friendly url entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry[] findByG_C_C_PrevAndNext(long friendlyURLEntryId,
		long groupId, long classNameId, long classPK,
		OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByPrimaryKey(friendlyURLEntryId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntry[] array = new FriendlyURLEntryImpl[3];

			array[0] = getByG_C_C_PrevAndNext(session, friendlyURLEntry,
					groupId, classNameId, classPK, orderByComparator, true);

			array[1] = friendlyURLEntry;

			array[2] = getByG_C_C_PrevAndNext(session, friendlyURLEntry,
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

	protected FriendlyURLEntry getByG_C_C_PrevAndNext(Session session,
		FriendlyURLEntry friendlyURLEntry, long groupId, long classNameId,
		long classPK, OrderByComparator<FriendlyURLEntry> orderByComparator,
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

		query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

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
			query.append(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURLEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURLEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (FriendlyURLEntry friendlyURLEntry : findByG_C_C(groupId,
				classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(friendlyURLEntry);
		}
	}

	/**
	 * Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "friendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "friendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "friendlyURLEntry.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_U = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_U = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByG_C_U(long groupId, long classNameId,
		String urlTitle) throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByG_C_U(groupId, classNameId,
				urlTitle);

		if (friendlyURLEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLEntryException(msg.toString());
		}

		return friendlyURLEntry;
	}

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_U(long groupId, long classNameId,
		String urlTitle) {
		return fetchByG_C_U(groupId, classNameId, urlTitle, true);
	}

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_U(long groupId, long classNameId,
		String urlTitle, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, urlTitle };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_U,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLEntry) {
			FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)result;

			if ((groupId != friendlyURLEntry.getGroupId()) ||
					(classNameId != friendlyURLEntry.getClassNameId()) ||
					!Objects.equals(urlTitle, friendlyURLEntry.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
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

				List<FriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_U,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"FriendlyURLEntryPersistenceImpl.fetchByG_C_U(long, long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FriendlyURLEntry friendlyURLEntry = list.get(0);

					result = friendlyURLEntry;

					cacheResult(friendlyURLEntry);

					if ((friendlyURLEntry.getGroupId() != groupId) ||
							(friendlyURLEntry.getClassNameId() != classNameId) ||
							(friendlyURLEntry.getUrlTitle() == null) ||
							!friendlyURLEntry.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_U,
							finderArgs, friendlyURLEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_U, finderArgs);

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
			return (FriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the friendly url entry that was removed
	 */
	@Override
	public FriendlyURLEntry removeByG_C_U(long groupId, long classNameId,
		String urlTitle) throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByG_C_U(groupId, classNameId,
				urlTitle);

		return remove(friendlyURLEntry);
	}

	/**
	 * Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByG_C_U(long groupId, long classNameId, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_U;

		Object[] finderArgs = new Object[] { groupId, classNameId, urlTitle };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_U_CLASSNAMEID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
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

	private static final String _FINDER_COLUMN_G_C_U_GROUPID_2 = "friendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_U_CLASSNAMEID_2 = "friendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_1 = "friendlyURLEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_2 = "friendlyURLEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_C_U_URLTITLE_3 = "(friendlyURLEntry.urlTitle IS NULL OR friendlyURLEntry.urlTitle = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_U = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_U = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByG_C_C_U(long groupId, long classNameId,
		long classPK, String urlTitle) throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByG_C_C_U(groupId,
				classNameId, classPK, urlTitle);

		if (friendlyURLEntry == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLEntryException(msg.toString());
		}

		return friendlyURLEntry;
	}

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_C_U(long groupId, long classNameId,
		long classPK, String urlTitle) {
		return fetchByG_C_C_U(groupId, classNameId, classPK, urlTitle, true);
	}

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_C_U(long groupId, long classNameId,
		long classPK, String urlTitle, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, urlTitle
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_U,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLEntry) {
			FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)result;

			if ((groupId != friendlyURLEntry.getGroupId()) ||
					(classNameId != friendlyURLEntry.getClassNameId()) ||
					(classPK != friendlyURLEntry.getClassPK()) ||
					!Objects.equals(urlTitle, friendlyURLEntry.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_CLASSPK_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_C_U_URLTITLE_2);
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

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<FriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"FriendlyURLEntryPersistenceImpl.fetchByG_C_C_U(long, long, long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FriendlyURLEntry friendlyURLEntry = list.get(0);

					result = friendlyURLEntry;

					cacheResult(friendlyURLEntry);

					if ((friendlyURLEntry.getGroupId() != groupId) ||
							(friendlyURLEntry.getClassNameId() != classNameId) ||
							(friendlyURLEntry.getClassPK() != classPK) ||
							(friendlyURLEntry.getUrlTitle() == null) ||
							!friendlyURLEntry.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U,
							finderArgs, friendlyURLEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_U,
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
			return (FriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the friendly url entry that was removed
	 */
	@Override
	public FriendlyURLEntry removeByG_C_C_U(long groupId, long classNameId,
		long classPK, String urlTitle) throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByG_C_C_U(groupId, classNameId,
				classPK, urlTitle);

		return remove(friendlyURLEntry);
	}

	/**
	 * Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByG_C_C_U(long groupId, long classNameId, long classPK,
		String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_U;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classPK, urlTitle
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_CLASSPK_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_C_U_URLTITLE_2);
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

	private static final String _FINDER_COLUMN_G_C_C_U_GROUPID_2 = "friendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_U_CLASSNAMEID_2 = "friendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_U_CLASSPK_2 = "friendlyURLEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_U_URLTITLE_1 = "friendlyURLEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_U_URLTITLE_2 = "friendlyURLEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_C_C_U_URLTITLE_3 = "(friendlyURLEntry.urlTitle IS NULL OR friendlyURLEntry.urlTitle = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_M = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			FriendlyURLEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			FriendlyURLEntryModelImpl.MAIN_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_M = new FinderPath(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the matching friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry findByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main) throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByG_C_C_M(groupId,
				classNameId, classPK, main);

		if (friendlyURLEntry == null) {
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

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLEntryException(msg.toString());
		}

		return friendlyURLEntry;
	}

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main) {
		return fetchByG_C_C_M(groupId, classNameId, classPK, main, true);
	}

	/**
	 * Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, classPK, main };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_M,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLEntry) {
			FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)result;

			if ((groupId != friendlyURLEntry.getGroupId()) ||
					(classNameId != friendlyURLEntry.getClassNameId()) ||
					(classPK != friendlyURLEntry.getClassPK()) ||
					(main != friendlyURLEntry.getMain())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE);

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

				List<FriendlyURLEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_M,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"FriendlyURLEntryPersistenceImpl.fetchByG_C_C_M(long, long, long, boolean, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FriendlyURLEntry friendlyURLEntry = list.get(0);

					result = friendlyURLEntry;

					cacheResult(friendlyURLEntry);

					if ((friendlyURLEntry.getGroupId() != groupId) ||
							(friendlyURLEntry.getClassNameId() != classNameId) ||
							(friendlyURLEntry.getClassPK() != classPK) ||
							(friendlyURLEntry.getMain() != main)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_M,
							finderArgs, friendlyURLEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_M,
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
			return (FriendlyURLEntry)result;
		}
	}

	/**
	 * Removes the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the friendly url entry that was removed
	 */
	@Override
	public FriendlyURLEntry removeByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main) throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = findByG_C_C_M(groupId, classNameId,
				classPK, main);

		return remove(friendlyURLEntry);
	}

	/**
	 * Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the number of matching friendly url entries
	 */
	@Override
	public int countByG_C_C_M(long groupId, long classNameId, long classPK,
		boolean main) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_M;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK, main };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_FRIENDLYURLENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_G_C_C_M_GROUPID_2 = "friendlyURLEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_M_CLASSNAMEID_2 = "friendlyURLEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_M_CLASSPK_2 = "friendlyURLEntry.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_M_MAIN_2 = "friendlyURLEntry.main = ?";

	public FriendlyURLEntryPersistenceImpl() {
		setModelClass(FriendlyURLEntry.class);

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
	 * Caches the friendly url entry in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntry the friendly url entry
	 */
	@Override
	public void cacheResult(FriendlyURLEntry friendlyURLEntry) {
		entityCache.putResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, friendlyURLEntry.getPrimaryKey(),
			friendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				friendlyURLEntry.getUuid(), friendlyURLEntry.getGroupId()
			}, friendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_U,
			new Object[] {
				friendlyURLEntry.getGroupId(), friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getUrlTitle()
			}, friendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U,
			new Object[] {
				friendlyURLEntry.getGroupId(), friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK(), friendlyURLEntry.getUrlTitle()
			}, friendlyURLEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_M,
			new Object[] {
				friendlyURLEntry.getGroupId(), friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK(), friendlyURLEntry.getMain()
			}, friendlyURLEntry);

		friendlyURLEntry.resetOriginalValues();
	}

	/**
	 * Caches the friendly url entries in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntries the friendly url entries
	 */
	@Override
	public void cacheResult(List<FriendlyURLEntry> friendlyURLEntries) {
		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			if (entityCache.getResult(
						FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLEntryImpl.class,
						friendlyURLEntry.getPrimaryKey()) == null) {
				cacheResult(friendlyURLEntry);
			}
			else {
				friendlyURLEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly url entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FriendlyURLEntry friendlyURLEntry) {
		entityCache.removeResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, friendlyURLEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FriendlyURLEntryModelImpl)friendlyURLEntry,
			true);
	}

	@Override
	public void clearCache(List<FriendlyURLEntry> friendlyURLEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			entityCache.removeResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLEntryImpl.class, friendlyURLEntry.getPrimaryKey());

			clearUniqueFindersCache((FriendlyURLEntryModelImpl)friendlyURLEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		FriendlyURLEntryModelImpl friendlyURLEntryModelImpl) {
		Object[] args = new Object[] {
				friendlyURLEntryModelImpl.getUuid(),
				friendlyURLEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			friendlyURLEntryModelImpl, false);

		args = new Object[] {
				friendlyURLEntryModelImpl.getGroupId(),
				friendlyURLEntryModelImpl.getClassNameId(),
				friendlyURLEntryModelImpl.getUrlTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_U, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_U, args,
			friendlyURLEntryModelImpl, false);

		args = new Object[] {
				friendlyURLEntryModelImpl.getGroupId(),
				friendlyURLEntryModelImpl.getClassNameId(),
				friendlyURLEntryModelImpl.getClassPK(),
				friendlyURLEntryModelImpl.getUrlTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_U, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U, args,
			friendlyURLEntryModelImpl, false);

		args = new Object[] {
				friendlyURLEntryModelImpl.getGroupId(),
				friendlyURLEntryModelImpl.getClassNameId(),
				friendlyURLEntryModelImpl.getClassPK(),
				friendlyURLEntryModelImpl.getMain()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_M, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_M, args,
			friendlyURLEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FriendlyURLEntryModelImpl friendlyURLEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getUuid(),
					friendlyURLEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((friendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getOriginalUuid(),
					friendlyURLEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getGroupId(),
					friendlyURLEntryModelImpl.getClassNameId(),
					friendlyURLEntryModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_U, args);
		}

		if ((friendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_U.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getOriginalGroupId(),
					friendlyURLEntryModelImpl.getOriginalClassNameId(),
					friendlyURLEntryModelImpl.getOriginalUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_U, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getGroupId(),
					friendlyURLEntryModelImpl.getClassNameId(),
					friendlyURLEntryModelImpl.getClassPK(),
					friendlyURLEntryModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_U, args);
		}

		if ((friendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_U.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getOriginalGroupId(),
					friendlyURLEntryModelImpl.getOriginalClassNameId(),
					friendlyURLEntryModelImpl.getOriginalClassPK(),
					friendlyURLEntryModelImpl.getOriginalUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_U, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getGroupId(),
					friendlyURLEntryModelImpl.getClassNameId(),
					friendlyURLEntryModelImpl.getClassPK(),
					friendlyURLEntryModelImpl.getMain()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_M, args);
		}

		if ((friendlyURLEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_M.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLEntryModelImpl.getOriginalGroupId(),
					friendlyURLEntryModelImpl.getOriginalClassNameId(),
					friendlyURLEntryModelImpl.getOriginalClassPK(),
					friendlyURLEntryModelImpl.getOriginalMain()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_M, args);
		}
	}

	/**
	 * Creates a new friendly url entry with the primary key. Does not add the friendly url entry to the database.
	 *
	 * @param friendlyURLEntryId the primary key for the new friendly url entry
	 * @return the new friendly url entry
	 */
	@Override
	public FriendlyURLEntry create(long friendlyURLEntryId) {
		FriendlyURLEntry friendlyURLEntry = new FriendlyURLEntryImpl();

		friendlyURLEntry.setNew(true);
		friendlyURLEntry.setPrimaryKey(friendlyURLEntryId);

		String uuid = PortalUUIDUtil.generate();

		friendlyURLEntry.setUuid(uuid);

		friendlyURLEntry.setCompanyId(companyProvider.getCompanyId());

		return friendlyURLEntry;
	}

	/**
	 * Removes the friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLEntryId the primary key of the friendly url entry
	 * @return the friendly url entry that was removed
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry remove(long friendlyURLEntryId)
		throws NoSuchFriendlyURLEntryException {
		return remove((Serializable)friendlyURLEntryId);
	}

	/**
	 * Removes the friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url entry
	 * @return the friendly url entry that was removed
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry remove(Serializable primaryKey)
		throws NoSuchFriendlyURLEntryException {
		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)session.get(FriendlyURLEntryImpl.class,
					primaryKey);

			if (friendlyURLEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(friendlyURLEntry);
		}
		catch (NoSuchFriendlyURLEntryException nsee) {
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
	protected FriendlyURLEntry removeImpl(FriendlyURLEntry friendlyURLEntry) {
		friendlyURLEntry = toUnwrappedModel(friendlyURLEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURLEntry)) {
				friendlyURLEntry = (FriendlyURLEntry)session.get(FriendlyURLEntryImpl.class,
						friendlyURLEntry.getPrimaryKeyObj());
			}

			if (friendlyURLEntry != null) {
				session.delete(friendlyURLEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLEntry != null) {
			clearCache(friendlyURLEntry);
		}

		return friendlyURLEntry;
	}

	@Override
	public FriendlyURLEntry updateImpl(FriendlyURLEntry friendlyURLEntry) {
		friendlyURLEntry = toUnwrappedModel(friendlyURLEntry);

		boolean isNew = friendlyURLEntry.isNew();

		FriendlyURLEntryModelImpl friendlyURLEntryModelImpl = (FriendlyURLEntryModelImpl)friendlyURLEntry;

		if (Validator.isNull(friendlyURLEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			friendlyURLEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (friendlyURLEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				friendlyURLEntry.setCreateDate(now);
			}
			else {
				friendlyURLEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!friendlyURLEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				friendlyURLEntry.setModifiedDate(now);
			}
			else {
				friendlyURLEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (friendlyURLEntry.isNew()) {
				session.save(friendlyURLEntry);

				friendlyURLEntry.setNew(false);
			}
			else {
				friendlyURLEntry = (FriendlyURLEntry)session.merge(friendlyURLEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!FriendlyURLEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { friendlyURLEntryModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					friendlyURLEntryModelImpl.getUuid(),
					friendlyURLEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					friendlyURLEntryModelImpl.getGroupId(),
					friendlyURLEntryModelImpl.getClassNameId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
				args);

			args = new Object[] {
					friendlyURLEntryModelImpl.getGroupId(),
					friendlyURLEntryModelImpl.getClassNameId(),
					friendlyURLEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((friendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { friendlyURLEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((friendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLEntryModelImpl.getOriginalUuid(),
						friendlyURLEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						friendlyURLEntryModelImpl.getUuid(),
						friendlyURLEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((friendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLEntryModelImpl.getOriginalGroupId(),
						friendlyURLEntryModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						friendlyURLEntryModelImpl.getGroupId(),
						friendlyURLEntryModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((friendlyURLEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLEntryModelImpl.getOriginalGroupId(),
						friendlyURLEntryModelImpl.getOriginalClassNameId(),
						friendlyURLEntryModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);

				args = new Object[] {
						friendlyURLEntryModelImpl.getGroupId(),
						friendlyURLEntryModelImpl.getClassNameId(),
						friendlyURLEntryModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C,
					args);
			}
		}

		entityCache.putResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryImpl.class, friendlyURLEntry.getPrimaryKey(),
			friendlyURLEntry, false);

		clearUniqueFindersCache(friendlyURLEntryModelImpl, false);
		cacheUniqueFindersCache(friendlyURLEntryModelImpl);

		friendlyURLEntry.resetOriginalValues();

		return friendlyURLEntry;
	}

	protected FriendlyURLEntry toUnwrappedModel(
		FriendlyURLEntry friendlyURLEntry) {
		if (friendlyURLEntry instanceof FriendlyURLEntryImpl) {
			return friendlyURLEntry;
		}

		FriendlyURLEntryImpl friendlyURLEntryImpl = new FriendlyURLEntryImpl();

		friendlyURLEntryImpl.setNew(friendlyURLEntry.isNew());
		friendlyURLEntryImpl.setPrimaryKey(friendlyURLEntry.getPrimaryKey());

		friendlyURLEntryImpl.setUuid(friendlyURLEntry.getUuid());
		friendlyURLEntryImpl.setFriendlyURLEntryId(friendlyURLEntry.getFriendlyURLEntryId());
		friendlyURLEntryImpl.setGroupId(friendlyURLEntry.getGroupId());
		friendlyURLEntryImpl.setCompanyId(friendlyURLEntry.getCompanyId());
		friendlyURLEntryImpl.setCreateDate(friendlyURLEntry.getCreateDate());
		friendlyURLEntryImpl.setModifiedDate(friendlyURLEntry.getModifiedDate());
		friendlyURLEntryImpl.setClassNameId(friendlyURLEntry.getClassNameId());
		friendlyURLEntryImpl.setClassPK(friendlyURLEntry.getClassPK());
		friendlyURLEntryImpl.setUrlTitle(friendlyURLEntry.getUrlTitle());
		friendlyURLEntryImpl.setMain(friendlyURLEntry.isMain());

		return friendlyURLEntryImpl;
	}

	/**
	 * Returns the friendly url entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry
	 * @return the friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFriendlyURLEntryException {
		FriendlyURLEntry friendlyURLEntry = fetchByPrimaryKey(primaryKey);

		if (friendlyURLEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return friendlyURLEntry;
	}

	/**
	 * Returns the friendly url entry with the primary key or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	 *
	 * @param friendlyURLEntryId the primary key of the friendly url entry
	 * @return the friendly url entry
	 * @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry findByPrimaryKey(long friendlyURLEntryId)
		throws NoSuchFriendlyURLEntryException {
		return findByPrimaryKey((Serializable)friendlyURLEntryId);
	}

	/**
	 * Returns the friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry
	 * @return the friendly url entry, or <code>null</code> if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)serializable;

		if (friendlyURLEntry == null) {
			Session session = null;

			try {
				session = openSession();

				friendlyURLEntry = (FriendlyURLEntry)session.get(FriendlyURLEntryImpl.class,
						primaryKey);

				if (friendlyURLEntry != null) {
					cacheResult(friendlyURLEntry);
				}
				else {
					entityCache.putResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return friendlyURLEntry;
	}

	/**
	 * Returns the friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLEntryId the primary key of the friendly url entry
	 * @return the friendly url entry, or <code>null</code> if a friendly url entry with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntry fetchByPrimaryKey(long friendlyURLEntryId) {
		return fetchByPrimaryKey((Serializable)friendlyURLEntryId);
	}

	@Override
	public Map<Serializable, FriendlyURLEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURLEntry> map = new HashMap<Serializable, FriendlyURLEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FriendlyURLEntry friendlyURLEntry = fetchByPrimaryKey(primaryKey);

			if (friendlyURLEntry != null) {
				map.put(primaryKey, friendlyURLEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (FriendlyURLEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRIENDLYURLENTRY_WHERE_PKS_IN);

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

			for (FriendlyURLEntry friendlyURLEntry : (List<FriendlyURLEntry>)q.list()) {
				map.put(friendlyURLEntry.getPrimaryKeyObj(), friendlyURLEntry);

				cacheResult(friendlyURLEntry);

				uncachedPrimaryKeys.remove(friendlyURLEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FriendlyURLEntryModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the friendly url entries.
	 *
	 * @return the friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @return the range of friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findAll(int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entries
	 * @param end the upper bound of the range of friendly url entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of friendly url entries
	 */
	@Override
	public List<FriendlyURLEntry> findAll(int start, int end,
		OrderByComparator<FriendlyURLEntry> orderByComparator,
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

		List<FriendlyURLEntry> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRIENDLYURLENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURLENTRY;

				if (pagination) {
					sql = sql.concat(FriendlyURLEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntry>)QueryUtil.list(q,
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
	 * Removes all the friendly url entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURLEntry friendlyURLEntry : findAll()) {
			remove(friendlyURLEntry);
		}
	}

	/**
	 * Returns the number of friendly url entries.
	 *
	 * @return the number of friendly url entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRIENDLYURLENTRY);

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
		return FriendlyURLEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the friendly url entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FriendlyURLEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_FRIENDLYURLENTRY = "SELECT friendlyURLEntry FROM FriendlyURLEntry friendlyURLEntry";
	private static final String _SQL_SELECT_FRIENDLYURLENTRY_WHERE_PKS_IN = "SELECT friendlyURLEntry FROM FriendlyURLEntry friendlyURLEntry WHERE friendlyURLEntryId IN (";
	private static final String _SQL_SELECT_FRIENDLYURLENTRY_WHERE = "SELECT friendlyURLEntry FROM FriendlyURLEntry friendlyURLEntry WHERE ";
	private static final String _SQL_COUNT_FRIENDLYURLENTRY = "SELECT COUNT(friendlyURLEntry) FROM FriendlyURLEntry friendlyURLEntry";
	private static final String _SQL_COUNT_FRIENDLYURLENTRY_WHERE = "SELECT COUNT(friendlyURLEntry) FROM FriendlyURLEntry friendlyURLEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "friendlyURLEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FriendlyURLEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FriendlyURLEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FriendlyURLEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}