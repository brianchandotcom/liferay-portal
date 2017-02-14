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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLException;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.model.impl.FriendlyURLImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLPersistence;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

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
 * The persistence implementation for the friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLPersistence
 * @see com.liferay.friendly.url.service.persistence.FriendlyURLUtil
 * @generated
 */
@ProviderType
public class FriendlyURLPersistenceImpl extends BasePersistenceImpl<FriendlyURL>
	implements FriendlyURLPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FriendlyURLUtil} to access the friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FriendlyURLImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			FriendlyURLModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @return the range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid(String uuid, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid(String uuid, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator,
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

		List<FriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURL friendlyURL : list) {
					if (!Objects.equals(uuid, friendlyURL.getUuid())) {
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

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

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
				query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByUuid_First(String uuid,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByUuid_First(uuid, orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByUuid_First(String uuid,
		OrderByComparator<FriendlyURL> orderByComparator) {
		List<FriendlyURL> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByUuid_Last(String uuid,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByUuid_Last(uuid, orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByUuid_Last(String uuid,
		OrderByComparator<FriendlyURL> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FriendlyURL> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly urls before and after the current friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param friendlyURLId the primary key of the current friendly url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL[] findByUuid_PrevAndNext(long friendlyURLId,
		String uuid, OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByPrimaryKey(friendlyURLId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURL[] array = new FriendlyURLImpl[3];

			array[0] = getByUuid_PrevAndNext(session, friendlyURL, uuid,
					orderByComparator, true);

			array[1] = friendlyURL;

			array[2] = getByUuid_PrevAndNext(session, friendlyURL, uuid,
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

	protected FriendlyURL getByUuid_PrevAndNext(Session session,
		FriendlyURL friendlyURL, String uuid,
		OrderByComparator<FriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

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
			query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FriendlyURL friendlyURL : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(friendlyURL);
		}
	}

	/**
	 * Returns the number of friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "friendlyURL.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "friendlyURL.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(friendlyURL.uuid IS NULL OR friendlyURL.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			FriendlyURLModelImpl.UUID_COLUMN_BITMASK |
			FriendlyURLModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the friendly url where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByUUID_G(uuid, groupId);

		if (friendlyURL == null) {
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

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return friendlyURL;
	}

	/**
	 * Returns the friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof FriendlyURL) {
			FriendlyURL friendlyURL = (FriendlyURL)result;

			if (!Objects.equals(uuid, friendlyURL.getUuid()) ||
					(groupId != friendlyURL.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

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

				List<FriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					FriendlyURL friendlyURL = list.get(0);

					result = friendlyURL;

					cacheResult(friendlyURL);

					if ((friendlyURL.getUuid() == null) ||
							!friendlyURL.getUuid().equals(uuid) ||
							(friendlyURL.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, friendlyURL);
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
			return (FriendlyURL)result;
		}
	}

	/**
	 * Removes the friendly url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the friendly url that was removed
	 */
	@Override
	public FriendlyURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByUUID_G(uuid, groupId);

		return remove(friendlyURL);
	}

	/**
	 * Returns the number of friendly urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "friendlyURL.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "friendlyURL.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(friendlyURL.uuid IS NULL OR friendlyURL.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "friendlyURL.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			FriendlyURLModelImpl.UUID_COLUMN_BITMASK |
			FriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @return the range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator,
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

		List<FriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURL friendlyURL : list) {
					if (!Objects.equals(uuid, friendlyURL.getUuid()) ||
							(companyId != friendlyURL.getCompanyId())) {
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

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

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
				query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<FriendlyURL> orderByComparator) {
		List<FriendlyURL> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<FriendlyURL> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURL> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly urls before and after the current friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param friendlyURLId the primary key of the current friendly url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL[] findByUuid_C_PrevAndNext(long friendlyURLId,
		String uuid, long companyId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByPrimaryKey(friendlyURLId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURL[] array = new FriendlyURLImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, friendlyURL, uuid,
					companyId, orderByComparator, true);

			array[1] = friendlyURL;

			array[2] = getByUuid_C_PrevAndNext(session, friendlyURL, uuid,
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

	protected FriendlyURL getByUuid_C_PrevAndNext(Session session,
		FriendlyURL friendlyURL, String uuid, long companyId,
		OrderByComparator<FriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

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
			query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FriendlyURL friendlyURL : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(friendlyURL);
		}
	}

	/**
	 * Returns the number of friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "friendlyURL.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "friendlyURL.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(friendlyURL.uuid IS NULL OR friendlyURL.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "friendlyURL.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			FriendlyURLModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the friendly urls where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C(long groupId, long classNameId) {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @return the range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator) {
		return findByG_C(groupId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator,
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

		List<FriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURL friendlyURL : list) {
					if ((groupId != friendlyURL.getGroupId()) ||
							(classNameId != friendlyURL.getClassNameId())) {
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

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByG_C_First(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByG_C_First(groupId, classNameId,
				orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator) {
		List<FriendlyURL> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByG_C_Last(groupId, classNameId,
				orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator) {
		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURL> list = findByG_C(groupId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly urls before and after the current friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param friendlyURLId the primary key of the current friendly url
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL[] findByG_C_PrevAndNext(long friendlyURLId,
		long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByPrimaryKey(friendlyURLId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURL[] array = new FriendlyURLImpl[3];

			array[0] = getByG_C_PrevAndNext(session, friendlyURL, groupId,
					classNameId, orderByComparator, true);

			array[1] = friendlyURL;

			array[2] = getByG_C_PrevAndNext(session, friendlyURL, groupId,
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

	protected FriendlyURL getByG_C_PrevAndNext(Session session,
		FriendlyURL friendlyURL, long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

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
			query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly urls where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (FriendlyURL friendlyURL : findByG_C(groupId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(friendlyURL);
		}
	}

	/**
	 * Returns the number of friendly urls where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "friendlyURL.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "friendlyURL.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_C = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_C =
		new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			FriendlyURLModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_C = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK) {
		return findByG_C_C_C(groupId, companyId, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @return the range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK, int start, int end) {
		return findByG_C_C_C(groupId, companyId, classNameId, classPK, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return findByG_C_C_C(groupId, companyId, classNameId, classPK, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly urls
	 */
	@Override
	public List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_C;
			finderArgs = new Object[] { groupId, companyId, classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_C;
			finderArgs = new Object[] {
					groupId, companyId, classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<FriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURL friendlyURL : list) {
					if ((groupId != friendlyURL.getGroupId()) ||
							(companyId != friendlyURL.getCompanyId()) ||
							(classNameId != friendlyURL.getClassNameId()) ||
							(classPK != friendlyURL.getClassPK())) {
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

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByG_C_C_C_First(long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByG_C_C_C_First(groupId, companyId,
				classNameId, classPK, orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_C_First(long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator) {
		List<FriendlyURL> list = findByG_C_C_C(groupId, companyId, classNameId,
				classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByG_C_C_C_Last(long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByG_C_C_C_Last(groupId, companyId,
				classNameId, classPK, orderByComparator);

		if (friendlyURL != null) {
			return friendlyURL;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_C_Last(long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator) {
		int count = countByG_C_C_C(groupId, companyId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<FriendlyURL> list = findByG_C_C_C(groupId, companyId, classNameId,
				classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly urls before and after the current friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param friendlyURLId the primary key of the current friendly url
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL[] findByG_C_C_C_PrevAndNext(long friendlyURLId,
		long groupId, long companyId, long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByPrimaryKey(friendlyURLId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURL[] array = new FriendlyURLImpl[3];

			array[0] = getByG_C_C_C_PrevAndNext(session, friendlyURL, groupId,
					companyId, classNameId, classPK, orderByComparator, true);

			array[1] = friendlyURL;

			array[2] = getByG_C_C_C_PrevAndNext(session, friendlyURL, groupId,
					companyId, classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FriendlyURL getByG_C_C_C_PrevAndNext(Session session,
		FriendlyURL friendlyURL, long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

		query.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

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
			query.append(FriendlyURLModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(companyId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK) {
		for (FriendlyURL friendlyURL : findByG_C_C_C(groupId, companyId,
				classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(friendlyURL);
		}
	}

	/**
	 * Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_C;

		Object[] finderArgs = new Object[] {
				groupId, companyId, classNameId, classPK
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_G_C_C_C_GROUPID_2 = "friendlyURL.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_COMPANYID_2 = "friendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2 = "friendlyURL.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_CLASSPK_2 = "friendlyURL.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_U = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FriendlyURLModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_U = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByG_C_C_U(long groupId, long companyId,
		long classNameId, String urlTitle) throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByG_C_C_U(groupId, companyId,
				classNameId, urlTitle);

		if (friendlyURL == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return friendlyURL;
	}

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_U(long groupId, long companyId,
		long classNameId, String urlTitle) {
		return fetchByG_C_C_U(groupId, companyId, classNameId, urlTitle, true);
	}

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_U(long groupId, long companyId,
		long classNameId, String urlTitle, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, companyId, classNameId, urlTitle
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_U,
					finderArgs, this);
		}

		if (result instanceof FriendlyURL) {
			FriendlyURL friendlyURL = (FriendlyURL)result;

			if ((groupId != friendlyURL.getGroupId()) ||
					(companyId != friendlyURL.getCompanyId()) ||
					(classNameId != friendlyURL.getClassNameId()) ||
					!Objects.equals(urlTitle, friendlyURL.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_CLASSNAMEID_2);

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

				qPos.add(companyId);

				qPos.add(classNameId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<FriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"FriendlyURLPersistenceImpl.fetchByG_C_C_U(long, long, long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FriendlyURL friendlyURL = list.get(0);

					result = friendlyURL;

					cacheResult(friendlyURL);

					if ((friendlyURL.getGroupId() != groupId) ||
							(friendlyURL.getCompanyId() != companyId) ||
							(friendlyURL.getClassNameId() != classNameId) ||
							(friendlyURL.getUrlTitle() == null) ||
							!friendlyURL.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U,
							finderArgs, friendlyURL);
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
			return (FriendlyURL)result;
		}
	}

	/**
	 * Removes the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the friendly url that was removed
	 */
	@Override
	public FriendlyURL removeByG_C_C_U(long groupId, long companyId,
		long classNameId, String urlTitle) throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByG_C_C_U(groupId, companyId,
				classNameId, urlTitle);

		return remove(friendlyURL);
	}

	/**
	 * Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param urlTitle the url title
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByG_C_C_U(long groupId, long companyId, long classNameId,
		String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_U;

		Object[] finderArgs = new Object[] {
				groupId, companyId, classNameId, urlTitle
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_U_CLASSNAMEID_2);

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

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_G_C_C_U_GROUPID_2 = "friendlyURL.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_U_COMPANYID_2 = "friendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_U_CLASSNAMEID_2 = "friendlyURL.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_U_URLTITLE_1 = "friendlyURL.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_U_URLTITLE_2 = "friendlyURL.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_C_C_U_URLTITLE_3 = "(friendlyURL.urlTitle IS NULL OR friendlyURL.urlTitle = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_C_U = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			FriendlyURLModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSPK_COLUMN_BITMASK |
			FriendlyURLModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_C_U = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			});

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, String urlTitle)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByG_C_C_C_U(groupId, companyId,
				classNameId, classPK, urlTitle);

		if (friendlyURL == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

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

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return friendlyURL;
	}

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, String urlTitle) {
		return fetchByG_C_C_C_U(groupId, companyId, classNameId, classPK,
			urlTitle, true);
	}

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, String urlTitle,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, companyId, classNameId, classPK, urlTitle
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_C_U,
					finderArgs, this);
		}

		if (result instanceof FriendlyURL) {
			FriendlyURL friendlyURL = (FriendlyURL)result;

			if ((groupId != friendlyURL.getGroupId()) ||
					(companyId != friendlyURL.getCompanyId()) ||
					(classNameId != friendlyURL.getClassNameId()) ||
					(classPK != friendlyURL.getClassPK()) ||
					!Objects.equals(urlTitle, friendlyURL.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_U_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_U_CLASSPK_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_C_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_C_C_U_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				List<FriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_U,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"FriendlyURLPersistenceImpl.fetchByG_C_C_C_U(long, long, long, long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FriendlyURL friendlyURL = list.get(0);

					result = friendlyURL;

					cacheResult(friendlyURL);

					if ((friendlyURL.getGroupId() != groupId) ||
							(friendlyURL.getCompanyId() != companyId) ||
							(friendlyURL.getClassNameId() != classNameId) ||
							(friendlyURL.getClassPK() != classPK) ||
							(friendlyURL.getUrlTitle() == null) ||
							!friendlyURL.getUrlTitle().equals(urlTitle)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_U,
							finderArgs, friendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C_U,
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
			return (FriendlyURL)result;
		}
	}

	/**
	 * Removes the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the friendly url that was removed
	 */
	@Override
	public FriendlyURL removeByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, String urlTitle)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByG_C_C_C_U(groupId, companyId,
				classNameId, classPK, urlTitle);

		return remove(friendlyURL);
	}

	/**
	 * Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param urlTitle the url title
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByG_C_C_C_U(long groupId, long companyId, long classNameId,
		long classPK, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_C_U;

		Object[] finderArgs = new Object[] {
				groupId, companyId, classNameId, classPK, urlTitle
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_C_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_U_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_U_CLASSPK_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_C_C_C_U_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_C_C_U_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_C_C_C_U_URLTITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_G_C_C_C_U_GROUPID_2 = "friendlyURL.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_U_COMPANYID_2 = "friendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_U_CLASSNAMEID_2 = "friendlyURL.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_U_CLASSPK_2 = "friendlyURL.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_U_URLTITLE_1 = "friendlyURL.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_C_C_C_U_URLTITLE_2 = "friendlyURL.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_C_C_C_U_URLTITLE_3 = "(friendlyURL.urlTitle IS NULL OR friendlyURL.urlTitle = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C_C_M = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, FriendlyURLImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			FriendlyURLModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FriendlyURLModelImpl.CLASSPK_COLUMN_BITMASK |
			FriendlyURLModelImpl.MAIN_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_C_M = new FinderPath(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_C_M",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the matching friendly url
	 * @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL findByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByG_C_C_C_M(groupId, companyId,
				classNameId, classPK, main);

		if (friendlyURL == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", companyId=");
			msg.append(companyId);

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

			throw new NoSuchFriendlyURLException(msg.toString());
		}

		return friendlyURL;
	}

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main) {
		return fetchByG_C_C_C_M(groupId, companyId, classNameId, classPK, main,
			true);
	}

	/**
	 * Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	 */
	@Override
	public FriendlyURL fetchByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, companyId, classNameId, classPK, main
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C_C_M,
					finderArgs, this);
		}

		if (result instanceof FriendlyURL) {
			FriendlyURL friendlyURL = (FriendlyURL)result;

			if ((groupId != friendlyURL.getGroupId()) ||
					(companyId != friendlyURL.getCompanyId()) ||
					(classNameId != friendlyURL.getClassNameId()) ||
					(classPK != friendlyURL.getClassPK()) ||
					(main != friendlyURL.getMain())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(7);

			query.append(_SQL_SELECT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_C_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_MAIN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(main);

				List<FriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_M,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"FriendlyURLPersistenceImpl.fetchByG_C_C_C_M(long, long, long, long, boolean, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					FriendlyURL friendlyURL = list.get(0);

					result = friendlyURL;

					cacheResult(friendlyURL);

					if ((friendlyURL.getGroupId() != groupId) ||
							(friendlyURL.getCompanyId() != companyId) ||
							(friendlyURL.getClassNameId() != classNameId) ||
							(friendlyURL.getClassPK() != classPK) ||
							(friendlyURL.getMain() != main)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_M,
							finderArgs, friendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C_M,
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
			return (FriendlyURL)result;
		}
	}

	/**
	 * Removes the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the friendly url that was removed
	 */
	@Override
	public FriendlyURL removeByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = findByG_C_C_C_M(groupId, companyId,
				classNameId, classPK, main);

		return remove(friendlyURL);
	}

	/**
	 * Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param main the main
	 * @return the number of matching friendly urls
	 */
	@Override
	public int countByG_C_C_C_M(long groupId, long companyId, long classNameId,
		long classPK, boolean main) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_C_M;

		Object[] finderArgs = new Object[] {
				groupId, companyId, classNameId, classPK, main
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_FRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_C_M_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_CLASSPK_2);

			query.append(_FINDER_COLUMN_G_C_C_C_M_MAIN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_G_C_C_C_M_GROUPID_2 = "friendlyURL.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_M_COMPANYID_2 = "friendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_M_CLASSNAMEID_2 = "friendlyURL.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_M_CLASSPK_2 = "friendlyURL.classPK = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_C_M_MAIN_2 = "friendlyURL.main = ?";

	public FriendlyURLPersistenceImpl() {
		setModelClass(FriendlyURL.class);
	}

	/**
	 * Caches the friendly url in the entity cache if it is enabled.
	 *
	 * @param friendlyURL the friendly url
	 */
	@Override
	public void cacheResult(FriendlyURL friendlyURL) {
		entityCache.putResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLImpl.class, friendlyURL.getPrimaryKey(), friendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { friendlyURL.getUuid(), friendlyURL.getGroupId() },
			friendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U,
			new Object[] {
				friendlyURL.getGroupId(), friendlyURL.getCompanyId(),
				friendlyURL.getClassNameId(), friendlyURL.getUrlTitle()
			}, friendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_U,
			new Object[] {
				friendlyURL.getGroupId(), friendlyURL.getCompanyId(),
				friendlyURL.getClassNameId(), friendlyURL.getClassPK(),
				friendlyURL.getUrlTitle()
			}, friendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_M,
			new Object[] {
				friendlyURL.getGroupId(), friendlyURL.getCompanyId(),
				friendlyURL.getClassNameId(), friendlyURL.getClassPK(),
				friendlyURL.getMain()
			}, friendlyURL);

		friendlyURL.resetOriginalValues();
	}

	/**
	 * Caches the friendly urls in the entity cache if it is enabled.
	 *
	 * @param friendlyURLs the friendly urls
	 */
	@Override
	public void cacheResult(List<FriendlyURL> friendlyURLs) {
		for (FriendlyURL friendlyURL : friendlyURLs) {
			if (entityCache.getResult(
						FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLImpl.class, friendlyURL.getPrimaryKey()) == null) {
				cacheResult(friendlyURL);
			}
			else {
				friendlyURL.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly urls.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FriendlyURL friendlyURL) {
		entityCache.removeResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLImpl.class, friendlyURL.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FriendlyURLModelImpl)friendlyURL, true);
	}

	@Override
	public void clearCache(List<FriendlyURL> friendlyURLs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURL friendlyURL : friendlyURLs) {
			entityCache.removeResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLImpl.class, friendlyURL.getPrimaryKey());

			clearUniqueFindersCache((FriendlyURLModelImpl)friendlyURL, true);
		}
	}

	protected void cacheUniqueFindersCache(
		FriendlyURLModelImpl friendlyURLModelImpl) {
		Object[] args = new Object[] {
				friendlyURLModelImpl.getUuid(),
				friendlyURLModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			friendlyURLModelImpl, false);

		args = new Object[] {
				friendlyURLModelImpl.getGroupId(),
				friendlyURLModelImpl.getCompanyId(),
				friendlyURLModelImpl.getClassNameId(),
				friendlyURLModelImpl.getUrlTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_U, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_U, args,
			friendlyURLModelImpl, false);

		args = new Object[] {
				friendlyURLModelImpl.getGroupId(),
				friendlyURLModelImpl.getCompanyId(),
				friendlyURLModelImpl.getClassNameId(),
				friendlyURLModelImpl.getClassPK(),
				friendlyURLModelImpl.getUrlTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_C_U, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_U, args,
			friendlyURLModelImpl, false);

		args = new Object[] {
				friendlyURLModelImpl.getGroupId(),
				friendlyURLModelImpl.getCompanyId(),
				friendlyURLModelImpl.getClassNameId(),
				friendlyURLModelImpl.getClassPK(),
				friendlyURLModelImpl.getMain()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C_C_M, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C_C_M, args,
			friendlyURLModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FriendlyURLModelImpl friendlyURLModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getUuid(),
					friendlyURLModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((friendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getOriginalUuid(),
					friendlyURLModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getGroupId(),
					friendlyURLModelImpl.getCompanyId(),
					friendlyURLModelImpl.getClassNameId(),
					friendlyURLModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_U, args);
		}

		if ((friendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_U.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getOriginalGroupId(),
					friendlyURLModelImpl.getOriginalCompanyId(),
					friendlyURLModelImpl.getOriginalClassNameId(),
					friendlyURLModelImpl.getOriginalUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_U, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getGroupId(),
					friendlyURLModelImpl.getCompanyId(),
					friendlyURLModelImpl.getClassNameId(),
					friendlyURLModelImpl.getClassPK(),
					friendlyURLModelImpl.getUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C_U, args);
		}

		if ((friendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_C_U.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getOriginalGroupId(),
					friendlyURLModelImpl.getOriginalCompanyId(),
					friendlyURLModelImpl.getOriginalClassNameId(),
					friendlyURLModelImpl.getOriginalClassPK(),
					friendlyURLModelImpl.getOriginalUrlTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C_U, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getGroupId(),
					friendlyURLModelImpl.getCompanyId(),
					friendlyURLModelImpl.getClassNameId(),
					friendlyURLModelImpl.getClassPK(),
					friendlyURLModelImpl.getMain()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_C_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C_M, args);
		}

		if ((friendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C_C_M.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLModelImpl.getOriginalGroupId(),
					friendlyURLModelImpl.getOriginalCompanyId(),
					friendlyURLModelImpl.getOriginalClassNameId(),
					friendlyURLModelImpl.getOriginalClassPK(),
					friendlyURLModelImpl.getOriginalMain()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_C_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C_C_M, args);
		}
	}

	/**
	 * Creates a new friendly url with the primary key. Does not add the friendly url to the database.
	 *
	 * @param friendlyURLId the primary key for the new friendly url
	 * @return the new friendly url
	 */
	@Override
	public FriendlyURL create(long friendlyURLId) {
		FriendlyURL friendlyURL = new FriendlyURLImpl();

		friendlyURL.setNew(true);
		friendlyURL.setPrimaryKey(friendlyURLId);

		String uuid = PortalUUIDUtil.generate();

		friendlyURL.setUuid(uuid);

		friendlyURL.setCompanyId(companyProvider.getCompanyId());

		return friendlyURL;
	}

	/**
	 * Removes the friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLId the primary key of the friendly url
	 * @return the friendly url that was removed
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL remove(long friendlyURLId)
		throws NoSuchFriendlyURLException {
		return remove((Serializable)friendlyURLId);
	}

	/**
	 * Removes the friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url
	 * @return the friendly url that was removed
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL remove(Serializable primaryKey)
		throws NoSuchFriendlyURLException {
		Session session = null;

		try {
			session = openSession();

			FriendlyURL friendlyURL = (FriendlyURL)session.get(FriendlyURLImpl.class,
					primaryKey);

			if (friendlyURL == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(friendlyURL);
		}
		catch (NoSuchFriendlyURLException nsee) {
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
	protected FriendlyURL removeImpl(FriendlyURL friendlyURL) {
		friendlyURL = toUnwrappedModel(friendlyURL);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURL)) {
				friendlyURL = (FriendlyURL)session.get(FriendlyURLImpl.class,
						friendlyURL.getPrimaryKeyObj());
			}

			if (friendlyURL != null) {
				session.delete(friendlyURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURL != null) {
			clearCache(friendlyURL);
		}

		return friendlyURL;
	}

	@Override
	public FriendlyURL updateImpl(FriendlyURL friendlyURL) {
		friendlyURL = toUnwrappedModel(friendlyURL);

		boolean isNew = friendlyURL.isNew();

		FriendlyURLModelImpl friendlyURLModelImpl = (FriendlyURLModelImpl)friendlyURL;

		if (Validator.isNull(friendlyURL.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			friendlyURL.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (friendlyURL.getCreateDate() == null)) {
			if (serviceContext == null) {
				friendlyURL.setCreateDate(now);
			}
			else {
				friendlyURL.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!friendlyURLModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				friendlyURL.setModifiedDate(now);
			}
			else {
				friendlyURL.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (friendlyURL.isNew()) {
				session.save(friendlyURL);

				friendlyURL.setNew(false);
			}
			else {
				friendlyURL = (FriendlyURL)session.merge(friendlyURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !FriendlyURLModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((friendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { friendlyURLModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((friendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLModelImpl.getOriginalUuid(),
						friendlyURLModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						friendlyURLModelImpl.getUuid(),
						friendlyURLModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((friendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLModelImpl.getOriginalGroupId(),
						friendlyURLModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						friendlyURLModelImpl.getGroupId(),
						friendlyURLModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((friendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLModelImpl.getOriginalGroupId(),
						friendlyURLModelImpl.getOriginalCompanyId(),
						friendlyURLModelImpl.getOriginalClassNameId(),
						friendlyURLModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_C,
					args);

				args = new Object[] {
						friendlyURLModelImpl.getGroupId(),
						friendlyURLModelImpl.getCompanyId(),
						friendlyURLModelImpl.getClassNameId(),
						friendlyURLModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_C,
					args);
			}
		}

		entityCache.putResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLImpl.class, friendlyURL.getPrimaryKey(), friendlyURL,
			false);

		clearUniqueFindersCache(friendlyURLModelImpl, false);
		cacheUniqueFindersCache(friendlyURLModelImpl);

		friendlyURL.resetOriginalValues();

		return friendlyURL;
	}

	protected FriendlyURL toUnwrappedModel(FriendlyURL friendlyURL) {
		if (friendlyURL instanceof FriendlyURLImpl) {
			return friendlyURL;
		}

		FriendlyURLImpl friendlyURLImpl = new FriendlyURLImpl();

		friendlyURLImpl.setNew(friendlyURL.isNew());
		friendlyURLImpl.setPrimaryKey(friendlyURL.getPrimaryKey());

		friendlyURLImpl.setUuid(friendlyURL.getUuid());
		friendlyURLImpl.setFriendlyURLId(friendlyURL.getFriendlyURLId());
		friendlyURLImpl.setGroupId(friendlyURL.getGroupId());
		friendlyURLImpl.setCompanyId(friendlyURL.getCompanyId());
		friendlyURLImpl.setCreateDate(friendlyURL.getCreateDate());
		friendlyURLImpl.setModifiedDate(friendlyURL.getModifiedDate());
		friendlyURLImpl.setClassNameId(friendlyURL.getClassNameId());
		friendlyURLImpl.setClassPK(friendlyURL.getClassPK());
		friendlyURLImpl.setUrlTitle(friendlyURL.getUrlTitle());
		friendlyURLImpl.setMain(friendlyURL.isMain());

		return friendlyURLImpl;
	}

	/**
	 * Returns the friendly url with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url
	 * @return the friendly url
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFriendlyURLException {
		FriendlyURL friendlyURL = fetchByPrimaryKey(primaryKey);

		if (friendlyURL == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return friendlyURL;
	}

	/**
	 * Returns the friendly url with the primary key or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	 *
	 * @param friendlyURLId the primary key of the friendly url
	 * @return the friendly url
	 * @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL findByPrimaryKey(long friendlyURLId)
		throws NoSuchFriendlyURLException {
		return findByPrimaryKey((Serializable)friendlyURLId);
	}

	/**
	 * Returns the friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url
	 * @return the friendly url, or <code>null</code> if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FriendlyURL friendlyURL = (FriendlyURL)serializable;

		if (friendlyURL == null) {
			Session session = null;

			try {
				session = openSession();

				friendlyURL = (FriendlyURL)session.get(FriendlyURLImpl.class,
						primaryKey);

				if (friendlyURL != null) {
					cacheResult(friendlyURL);
				}
				else {
					entityCache.putResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return friendlyURL;
	}

	/**
	 * Returns the friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLId the primary key of the friendly url
	 * @return the friendly url, or <code>null</code> if a friendly url with the primary key could not be found
	 */
	@Override
	public FriendlyURL fetchByPrimaryKey(long friendlyURLId) {
		return fetchByPrimaryKey((Serializable)friendlyURLId);
	}

	@Override
	public Map<Serializable, FriendlyURL> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURL> map = new HashMap<Serializable, FriendlyURL>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FriendlyURL friendlyURL = fetchByPrimaryKey(primaryKey);

			if (friendlyURL != null) {
				map.put(primaryKey, friendlyURL);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (FriendlyURL)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRIENDLYURL_WHERE_PKS_IN);

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

			for (FriendlyURL friendlyURL : (List<FriendlyURL>)q.list()) {
				map.put(friendlyURL.getPrimaryKeyObj(), friendlyURL);

				cacheResult(friendlyURL);

				uncachedPrimaryKeys.remove(friendlyURL.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLImpl.class, primaryKey, nullModel);
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
	 * Returns all the friendly urls.
	 *
	 * @return the friendly urls
	 */
	@Override
	public List<FriendlyURL> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @return the range of friendly urls
	 */
	@Override
	public List<FriendlyURL> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly urls
	 */
	@Override
	public List<FriendlyURL> findAll(int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly urls
	 * @param end the upper bound of the range of friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of friendly urls
	 */
	@Override
	public List<FriendlyURL> findAll(int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator,
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

		List<FriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRIENDLYURL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURL;

				if (pagination) {
					sql = sql.concat(FriendlyURLModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURL>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the friendly urls from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURL friendlyURL : findAll()) {
			remove(friendlyURL);
		}
	}

	/**
	 * Returns the number of friendly urls.
	 *
	 * @return the number of friendly urls
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRIENDLYURL);

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
		return FriendlyURLModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the friendly url persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FriendlyURLImpl.class.getName());
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
	private static final String _SQL_SELECT_FRIENDLYURL = "SELECT friendlyURL FROM FriendlyURL friendlyURL";
	private static final String _SQL_SELECT_FRIENDLYURL_WHERE_PKS_IN = "SELECT friendlyURL FROM FriendlyURL friendlyURL WHERE friendlyURLId IN (";
	private static final String _SQL_SELECT_FRIENDLYURL_WHERE = "SELECT friendlyURL FROM FriendlyURL friendlyURL WHERE ";
	private static final String _SQL_COUNT_FRIENDLYURL = "SELECT COUNT(friendlyURL) FROM FriendlyURL friendlyURL";
	private static final String _SQL_COUNT_FRIENDLYURL_WHERE = "SELECT COUNT(friendlyURL) FROM FriendlyURL friendlyURL WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "friendlyURL.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FriendlyURL exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FriendlyURL exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FriendlyURLPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}