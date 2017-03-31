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

package com.liferay.portal.route.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException;
import com.liferay.portal.kernel.route.model.GroupFriendlyURL;
import com.liferay.portal.kernel.route.service.persistence.GroupFriendlyURLPersistence;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.route.model.impl.GroupFriendlyURLImpl;
import com.liferay.portal.route.model.impl.GroupFriendlyURLModelImpl;

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
 * The persistence implementation for the group friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupFriendlyURLPersistence
 * @see com.liferay.portal.kernel.route.service.persistence.GroupFriendlyURLUtil
 * @generated
 */
@ProviderType
public class GroupFriendlyURLPersistenceImpl extends BasePersistenceImpl<GroupFriendlyURL>
	implements GroupFriendlyURLPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link GroupFriendlyURLUtil} to access the group friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = GroupFriendlyURLImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			GroupFriendlyURLModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the group friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the group friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @return the range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the group friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid(String uuid, int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the group friendly urls where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid(String uuid, int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
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

		List<GroupFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<GroupFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (GroupFriendlyURL groupFriendlyURL : list) {
					if (!Objects.equals(uuid, groupFriendlyURL.getUuid())) {
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

			query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

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
				query.append(GroupFriendlyURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
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
	 * Returns the first group friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByUuid_First(String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByUuid_First(uuid,
				orderByComparator);

		if (groupFriendlyURL != null) {
			return groupFriendlyURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first group friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByUuid_First(String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		List<GroupFriendlyURL> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByUuid_Last(String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByUuid_Last(uuid,
				orderByComparator);

		if (groupFriendlyURL != null) {
			return groupFriendlyURL;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last group friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByUuid_Last(String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<GroupFriendlyURL> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the group friendly urls before and after the current group friendly url in the ordered set where uuid = &#63;.
	 *
	 * @param groupFriendlyURLId the primary key of the current group friendly url
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL[] findByUuid_PrevAndNext(long groupFriendlyURLId,
		String uuid, OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = findByPrimaryKey(groupFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			GroupFriendlyURL[] array = new GroupFriendlyURLImpl[3];

			array[0] = getByUuid_PrevAndNext(session, groupFriendlyURL, uuid,
					orderByComparator, true);

			array[1] = groupFriendlyURL;

			array[2] = getByUuid_PrevAndNext(session, groupFriendlyURL, uuid,
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

	protected GroupFriendlyURL getByUuid_PrevAndNext(Session session,
		GroupFriendlyURL groupFriendlyURL, String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

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
			query.append(GroupFriendlyURLModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(groupFriendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<GroupFriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the group friendly urls where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (GroupFriendlyURL groupFriendlyURL : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(groupFriendlyURL);
		}
	}

	/**
	 * Returns the number of group friendly urls where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching group friendly urls
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_GROUPFRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "groupFriendlyURL.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "groupFriendlyURL.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(groupFriendlyURL.uuid IS NULL OR groupFriendlyURL.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			GroupFriendlyURLModelImpl.UUID_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the group friendly url where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByUUID_G(String uuid, long groupId)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByUUID_G(uuid, groupId);

		if (groupFriendlyURL == null) {
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

			throw new NoSuchGroupFriendlyURLException(msg.toString());
		}

		return groupFriendlyURL;
	}

	/**
	 * Returns the group friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the group friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof GroupFriendlyURL) {
			GroupFriendlyURL groupFriendlyURL = (GroupFriendlyURL)result;

			if (!Objects.equals(uuid, groupFriendlyURL.getUuid()) ||
					(groupId != groupFriendlyURL.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

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

				List<GroupFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					GroupFriendlyURL groupFriendlyURL = list.get(0);

					result = groupFriendlyURL;

					cacheResult(groupFriendlyURL);

					if ((groupFriendlyURL.getUuid() == null) ||
							!groupFriendlyURL.getUuid().equals(uuid) ||
							(groupFriendlyURL.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, groupFriendlyURL);
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
			return (GroupFriendlyURL)result;
		}
	}

	/**
	 * Removes the group friendly url where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the group friendly url that was removed
	 */
	@Override
	public GroupFriendlyURL removeByUUID_G(String uuid, long groupId)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = findByUUID_G(uuid, groupId);

		return remove(groupFriendlyURL);
	}

	/**
	 * Returns the number of group friendly urls where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching group friendly urls
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUPFRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "groupFriendlyURL.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "groupFriendlyURL.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(groupFriendlyURL.uuid IS NULL OR groupFriendlyURL.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "groupFriendlyURL.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			GroupFriendlyURLModelImpl.UUID_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the group friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @return the range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
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

		List<GroupFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<GroupFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (GroupFriendlyURL groupFriendlyURL : list) {
					if (!Objects.equals(uuid, groupFriendlyURL.getUuid()) ||
							(companyId != groupFriendlyURL.getCompanyId())) {
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

			query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

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
				query.append(GroupFriendlyURLModelImpl.ORDER_BY_JPQL);
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
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
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
	 * Returns the first group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (groupFriendlyURL != null) {
			return groupFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		List<GroupFriendlyURL> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (groupFriendlyURL != null) {
			return groupFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<GroupFriendlyURL> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the group friendly urls before and after the current group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param groupFriendlyURLId the primary key of the current group friendly url
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL[] findByUuid_C_PrevAndNext(
		long groupFriendlyURLId, String uuid, long companyId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = findByPrimaryKey(groupFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			GroupFriendlyURL[] array = new GroupFriendlyURLImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, groupFriendlyURL, uuid,
					companyId, orderByComparator, true);

			array[1] = groupFriendlyURL;

			array[2] = getByUuid_C_PrevAndNext(session, groupFriendlyURL, uuid,
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

	protected GroupFriendlyURL getByUuid_C_PrevAndNext(Session session,
		GroupFriendlyURL groupFriendlyURL, String uuid, long companyId,
		OrderByComparator<GroupFriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

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
			query.append(GroupFriendlyURLModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(groupFriendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<GroupFriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the group friendly urls where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (GroupFriendlyURL groupFriendlyURL : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(groupFriendlyURL);
		}
	}

	/**
	 * Returns the number of group friendly urls where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching group friendly urls
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUPFRIENDLYURL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "groupFriendlyURL.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "groupFriendlyURL.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(groupFriendlyURL.uuid IS NULL OR groupFriendlyURL.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "groupFriendlyURL.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_G = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_G",
			new String[] { Long.class.getName(), Long.class.getName() },
			GroupFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the group friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByC_G(long companyId, long groupId) {
		return findByC_G(companyId, groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @return the range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByC_G(long companyId, long groupId,
		int start, int end) {
		return findByC_G(companyId, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByC_G(long companyId, long groupId,
		int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return findByC_G(companyId, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findByC_G(long companyId, long groupId,
		int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G;
			finderArgs = new Object[] { companyId, groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_G;
			finderArgs = new Object[] {
					companyId, groupId,
					
					start, end, orderByComparator
				};
		}

		List<GroupFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<GroupFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (GroupFriendlyURL groupFriendlyURL : list) {
					if ((companyId != groupFriendlyURL.getCompanyId()) ||
							(groupId != groupFriendlyURL.getGroupId())) {
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

			query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupFriendlyURLModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
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
	 * Returns the first group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByC_G_First(long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByC_G_First(companyId,
				groupId, orderByComparator);

		if (groupFriendlyURL != null) {
			return groupFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the first group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_G_First(long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		List<GroupFriendlyURL> list = findByC_G(companyId, groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByC_G_Last(long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByC_G_Last(companyId, groupId,
				orderByComparator);

		if (groupFriendlyURL != null) {
			return groupFriendlyURL;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupFriendlyURLException(msg.toString());
	}

	/**
	 * Returns the last group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_G_Last(long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		int count = countByC_G(companyId, groupId);

		if (count == 0) {
			return null;
		}

		List<GroupFriendlyURL> list = findByC_G(companyId, groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the group friendly urls before and after the current group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	 *
	 * @param groupFriendlyURLId the primary key of the current group friendly url
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL[] findByC_G_PrevAndNext(long groupFriendlyURLId,
		long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = findByPrimaryKey(groupFriendlyURLId);

		Session session = null;

		try {
			session = openSession();

			GroupFriendlyURL[] array = new GroupFriendlyURLImpl[3];

			array[0] = getByC_G_PrevAndNext(session, groupFriendlyURL,
					companyId, groupId, orderByComparator, true);

			array[1] = groupFriendlyURL;

			array[2] = getByC_G_PrevAndNext(session, groupFriendlyURL,
					companyId, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected GroupFriendlyURL getByC_G_PrevAndNext(Session session,
		GroupFriendlyURL groupFriendlyURL, long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

		query.append(_FINDER_COLUMN_C_G_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_G_GROUPID_2);

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
			query.append(GroupFriendlyURLModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(groupFriendlyURL);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<GroupFriendlyURL> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the group friendly urls where companyId = &#63; and groupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 */
	@Override
	public void removeByC_G(long companyId, long groupId) {
		for (GroupFriendlyURL groupFriendlyURL : findByC_G(companyId, groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(groupFriendlyURL);
		}
	}

	/**
	 * Returns the number of group friendly urls where companyId = &#63; and groupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @return the number of matching group friendly urls
	 */
	@Override
	public int countByC_G(long companyId, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_G;

		Object[] finderArgs = new Object[] { companyId, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_G_COMPANYID_2 = "groupFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_GROUPID_2 = "groupFriendlyURL.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] { Long.class.getName(), String.class.getName() },
			GroupFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.FRIENDLYURL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByC_F(companyId, friendlyURL);

		if (groupFriendlyURL == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchGroupFriendlyURLException(msg.toString());
		}

		return groupFriendlyURL;
	}

	/**
	 * Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_F(long companyId, String friendlyURL) {
		return fetchByC_F(companyId, friendlyURL, true);
	}

	/**
	 * Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_F(long companyId, String friendlyURL,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result instanceof GroupFriendlyURL) {
			GroupFriendlyURL groupFriendlyURL = (GroupFriendlyURL)result;

			if ((companyId != groupFriendlyURL.getCompanyId()) ||
					!Objects.equals(friendlyURL,
						groupFriendlyURL.getFriendlyURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				List<GroupFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, finderArgs,
						list);
				}
				else {
					GroupFriendlyURL groupFriendlyURL = list.get(0);

					result = groupFriendlyURL;

					cacheResult(groupFriendlyURL);

					if ((groupFriendlyURL.getCompanyId() != companyId) ||
							(groupFriendlyURL.getFriendlyURL() == null) ||
							!groupFriendlyURL.getFriendlyURL()
												 .equals(friendlyURL)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, groupFriendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, finderArgs);

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
			return (GroupFriendlyURL)result;
		}
	}

	/**
	 * Removes the group friendly url where companyId = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the group friendly url that was removed
	 */
	@Override
	public GroupFriendlyURL removeByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = findByC_F(companyId, friendlyURL);

		return remove(groupFriendlyURL);
	}

	/**
	 * Returns the number of group friendly urls where companyId = &#63; and friendlyURL = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @return the number of matching group friendly urls
	 */
	@Override
	public int countByC_F(long companyId, String friendlyURL) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F;

		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
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

	private static final String _FINDER_COLUMN_C_F_COMPANYID_2 = "groupFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_1 = "groupFriendlyURL.friendlyURL IS NULL";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_2 = "groupFriendlyURL.friendlyURL = ?";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_3 = "(groupFriendlyURL.friendlyURL IS NULL OR groupFriendlyURL.friendlyURL = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_G_L = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_G_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			GroupFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.GROUPID_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_G_L = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_G_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByC_G_L(long companyId, long groupId,
		String languageId) throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByC_G_L(companyId, groupId,
				languageId);

		if (groupFriendlyURL == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchGroupFriendlyURLException(msg.toString());
		}

		return groupFriendlyURL;
	}

	/**
	 * Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_G_L(long companyId, long groupId,
		String languageId) {
		return fetchByC_G_L(companyId, groupId, languageId, true);
	}

	/**
	 * Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_G_L(long companyId, long groupId,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, groupId, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_G_L,
					finderArgs, this);
		}

		if (result instanceof GroupFriendlyURL) {
			GroupFriendlyURL groupFriendlyURL = (GroupFriendlyURL)result;

			if ((companyId != groupFriendlyURL.getCompanyId()) ||
					(groupId != groupFriendlyURL.getGroupId()) ||
					!Objects.equals(languageId, groupFriendlyURL.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_L_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_L_GROUPID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<GroupFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L,
						finderArgs, list);
				}
				else {
					GroupFriendlyURL groupFriendlyURL = list.get(0);

					result = groupFriendlyURL;

					cacheResult(groupFriendlyURL);

					if ((groupFriendlyURL.getCompanyId() != companyId) ||
							(groupFriendlyURL.getGroupId() != groupId) ||
							(groupFriendlyURL.getLanguageId() == null) ||
							!groupFriendlyURL.getLanguageId().equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L,
							finderArgs, groupFriendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_G_L, finderArgs);

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
			return (GroupFriendlyURL)result;
		}
	}

	/**
	 * Removes the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the group friendly url that was removed
	 */
	@Override
	public GroupFriendlyURL removeByC_G_L(long companyId, long groupId,
		String languageId) throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = findByC_G_L(companyId, groupId,
				languageId);

		return remove(groupFriendlyURL);
	}

	/**
	 * Returns the number of group friendly urls where companyId = &#63; and groupId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @return the number of matching group friendly urls
	 */
	@Override
	public int countByC_G_L(long companyId, long groupId, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_G_L;

		Object[] finderArgs = new Object[] { companyId, groupId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_G_L_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_G_L_GROUPID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_G_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_C_G_L_COMPANYID_2 = "groupFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_L_GROUPID_2 = "groupFriendlyURL.groupId = ? AND ";
	private static final String _FINDER_COLUMN_C_G_L_LANGUAGEID_1 = "groupFriendlyURL.languageId IS NULL";
	private static final String _FINDER_COLUMN_C_G_L_LANGUAGEID_2 = "groupFriendlyURL.languageId = ?";
	private static final String _FINDER_COLUMN_C_G_L_LANGUAGEID_3 = "(groupFriendlyURL.languageId IS NULL OR groupFriendlyURL.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F_L = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_F_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			GroupFriendlyURLModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.FRIENDLYURL_COLUMN_BITMASK |
			GroupFriendlyURLModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F_L = new FinderPath(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL findByC_F_L(long companyId, String friendlyURL,
		String languageId) throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByC_F_L(companyId,
				friendlyURL, languageId);

		if (groupFriendlyURL == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchGroupFriendlyURLException(msg.toString());
		}

		return groupFriendlyURL;
	}

	/**
	 * Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_F_L(long companyId, String friendlyURL,
		String languageId) {
		return fetchByC_F_L(companyId, friendlyURL, languageId, true);
	}

	/**
	 * Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByC_F_L(long companyId, String friendlyURL,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, friendlyURL, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_F_L,
					finderArgs, this);
		}

		if (result instanceof GroupFriendlyURL) {
			GroupFriendlyURL groupFriendlyURL = (GroupFriendlyURL)result;

			if ((companyId != groupFriendlyURL.getCompanyId()) ||
					!Objects.equals(friendlyURL,
						groupFriendlyURL.getFriendlyURL()) ||
					!Objects.equals(languageId, groupFriendlyURL.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_L_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<GroupFriendlyURL> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L,
						finderArgs, list);
				}
				else {
					GroupFriendlyURL groupFriendlyURL = list.get(0);

					result = groupFriendlyURL;

					cacheResult(groupFriendlyURL);

					if ((groupFriendlyURL.getCompanyId() != companyId) ||
							(groupFriendlyURL.getFriendlyURL() == null) ||
							!groupFriendlyURL.getFriendlyURL()
												 .equals(friendlyURL) ||
							(groupFriendlyURL.getLanguageId() == null) ||
							!groupFriendlyURL.getLanguageId().equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L,
							finderArgs, groupFriendlyURL);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F_L, finderArgs);

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
			return (GroupFriendlyURL)result;
		}
	}

	/**
	 * Removes the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the group friendly url that was removed
	 */
	@Override
	public GroupFriendlyURL removeByC_F_L(long companyId, String friendlyURL,
		String languageId) throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = findByC_F_L(companyId, friendlyURL,
				languageId);

		return remove(groupFriendlyURL);
	}

	/**
	 * Returns the number of group friendly urls where companyId = &#63; and friendlyURL = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly url
	 * @param languageId the language ID
	 * @return the number of matching group friendly urls
	 */
	@Override
	public int countByC_F_L(long companyId, String friendlyURL,
		String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F_L;

		Object[] finderArgs = new Object[] { companyId, friendlyURL, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUPFRIENDLYURL_WHERE);

			query.append(_FINDER_COLUMN_C_F_L_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_L_FRIENDLYURL_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_C_F_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
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

	private static final String _FINDER_COLUMN_C_F_L_COMPANYID_2 = "groupFriendlyURL.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_1 = "groupFriendlyURL.friendlyURL IS NULL AND ";
	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_2 = "groupFriendlyURL.friendlyURL = ? AND ";
	private static final String _FINDER_COLUMN_C_F_L_FRIENDLYURL_3 = "(groupFriendlyURL.friendlyURL IS NULL OR groupFriendlyURL.friendlyURL = '') AND ";
	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_1 = "groupFriendlyURL.languageId IS NULL";
	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_2 = "groupFriendlyURL.languageId = ?";
	private static final String _FINDER_COLUMN_C_F_L_LANGUAGEID_3 = "(groupFriendlyURL.languageId IS NULL OR groupFriendlyURL.languageId = '')";

	public GroupFriendlyURLPersistenceImpl() {
		setModelClass(GroupFriendlyURL.class);
	}

	/**
	 * Caches the group friendly url in the entity cache if it is enabled.
	 *
	 * @param groupFriendlyURL the group friendly url
	 */
	@Override
	public void cacheResult(GroupFriendlyURL groupFriendlyURL) {
		entityCache.putResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, groupFriendlyURL.getPrimaryKey(),
			groupFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				groupFriendlyURL.getUuid(), groupFriendlyURL.getGroupId()
			}, groupFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				groupFriendlyURL.getCompanyId(),
				groupFriendlyURL.getFriendlyURL()
			}, groupFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L,
			new Object[] {
				groupFriendlyURL.getCompanyId(), groupFriendlyURL.getGroupId(),
				groupFriendlyURL.getLanguageId()
			}, groupFriendlyURL);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L,
			new Object[] {
				groupFriendlyURL.getCompanyId(),
				groupFriendlyURL.getFriendlyURL(),
				groupFriendlyURL.getLanguageId()
			}, groupFriendlyURL);

		groupFriendlyURL.resetOriginalValues();
	}

	/**
	 * Caches the group friendly urls in the entity cache if it is enabled.
	 *
	 * @param groupFriendlyURLs the group friendly urls
	 */
	@Override
	public void cacheResult(List<GroupFriendlyURL> groupFriendlyURLs) {
		for (GroupFriendlyURL groupFriendlyURL : groupFriendlyURLs) {
			if (entityCache.getResult(
						GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
						GroupFriendlyURLImpl.class,
						groupFriendlyURL.getPrimaryKey()) == null) {
				cacheResult(groupFriendlyURL);
			}
			else {
				groupFriendlyURL.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all group friendly urls.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(GroupFriendlyURLImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the group friendly url.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(GroupFriendlyURL groupFriendlyURL) {
		entityCache.removeResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, groupFriendlyURL.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((GroupFriendlyURLModelImpl)groupFriendlyURL,
			true);
	}

	@Override
	public void clearCache(List<GroupFriendlyURL> groupFriendlyURLs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (GroupFriendlyURL groupFriendlyURL : groupFriendlyURLs) {
			entityCache.removeResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
				GroupFriendlyURLImpl.class, groupFriendlyURL.getPrimaryKey());

			clearUniqueFindersCache((GroupFriendlyURLModelImpl)groupFriendlyURL,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		GroupFriendlyURLModelImpl groupFriendlyURLModelImpl) {
		Object[] args = new Object[] {
				groupFriendlyURLModelImpl.getUuid(),
				groupFriendlyURLModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			groupFriendlyURLModelImpl, false);

		args = new Object[] {
				groupFriendlyURLModelImpl.getCompanyId(),
				groupFriendlyURLModelImpl.getFriendlyURL()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_F, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, args,
			groupFriendlyURLModelImpl, false);

		args = new Object[] {
				groupFriendlyURLModelImpl.getCompanyId(),
				groupFriendlyURLModelImpl.getGroupId(),
				groupFriendlyURLModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_G_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_G_L, args,
			groupFriendlyURLModelImpl, false);

		args = new Object[] {
				groupFriendlyURLModelImpl.getCompanyId(),
				groupFriendlyURLModelImpl.getFriendlyURL(),
				groupFriendlyURLModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_F_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F_L, args,
			groupFriendlyURLModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		GroupFriendlyURLModelImpl groupFriendlyURLModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getUuid(),
					groupFriendlyURLModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((groupFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getOriginalUuid(),
					groupFriendlyURLModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getCompanyId(),
					groupFriendlyURLModelImpl.getFriendlyURL()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		if ((groupFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getOriginalCompanyId(),
					groupFriendlyURLModelImpl.getOriginalFriendlyURL()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getCompanyId(),
					groupFriendlyURLModelImpl.getGroupId(),
					groupFriendlyURLModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_G_L, args);
		}

		if ((groupFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_G_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getOriginalCompanyId(),
					groupFriendlyURLModelImpl.getOriginalGroupId(),
					groupFriendlyURLModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_G_L, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getCompanyId(),
					groupFriendlyURLModelImpl.getFriendlyURL(),
					groupFriendlyURLModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F_L, args);
		}

		if ((groupFriendlyURLModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					groupFriendlyURLModelImpl.getOriginalCompanyId(),
					groupFriendlyURLModelImpl.getOriginalFriendlyURL(),
					groupFriendlyURLModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F_L, args);
		}
	}

	/**
	 * Creates a new group friendly url with the primary key. Does not add the group friendly url to the database.
	 *
	 * @param groupFriendlyURLId the primary key for the new group friendly url
	 * @return the new group friendly url
	 */
	@Override
	public GroupFriendlyURL create(long groupFriendlyURLId) {
		GroupFriendlyURL groupFriendlyURL = new GroupFriendlyURLImpl();

		groupFriendlyURL.setNew(true);
		groupFriendlyURL.setPrimaryKey(groupFriendlyURLId);

		String uuid = PortalUUIDUtil.generate();

		groupFriendlyURL.setUuid(uuid);

		groupFriendlyURL.setCompanyId(companyProvider.getCompanyId());

		return groupFriendlyURL;
	}

	/**
	 * Removes the group friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param groupFriendlyURLId the primary key of the group friendly url
	 * @return the group friendly url that was removed
	 * @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL remove(long groupFriendlyURLId)
		throws NoSuchGroupFriendlyURLException {
		return remove((Serializable)groupFriendlyURLId);
	}

	/**
	 * Removes the group friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the group friendly url
	 * @return the group friendly url that was removed
	 * @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL remove(Serializable primaryKey)
		throws NoSuchGroupFriendlyURLException {
		Session session = null;

		try {
			session = openSession();

			GroupFriendlyURL groupFriendlyURL = (GroupFriendlyURL)session.get(GroupFriendlyURLImpl.class,
					primaryKey);

			if (groupFriendlyURL == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGroupFriendlyURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(groupFriendlyURL);
		}
		catch (NoSuchGroupFriendlyURLException nsee) {
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
	protected GroupFriendlyURL removeImpl(GroupFriendlyURL groupFriendlyURL) {
		groupFriendlyURL = toUnwrappedModel(groupFriendlyURL);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(groupFriendlyURL)) {
				groupFriendlyURL = (GroupFriendlyURL)session.get(GroupFriendlyURLImpl.class,
						groupFriendlyURL.getPrimaryKeyObj());
			}

			if (groupFriendlyURL != null) {
				session.delete(groupFriendlyURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (groupFriendlyURL != null) {
			clearCache(groupFriendlyURL);
		}

		return groupFriendlyURL;
	}

	@Override
	public GroupFriendlyURL updateImpl(GroupFriendlyURL groupFriendlyURL) {
		groupFriendlyURL = toUnwrappedModel(groupFriendlyURL);

		boolean isNew = groupFriendlyURL.isNew();

		GroupFriendlyURLModelImpl groupFriendlyURLModelImpl = (GroupFriendlyURLModelImpl)groupFriendlyURL;

		if (Validator.isNull(groupFriendlyURL.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			groupFriendlyURL.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (groupFriendlyURL.getCreateDate() == null)) {
			if (serviceContext == null) {
				groupFriendlyURL.setCreateDate(now);
			}
			else {
				groupFriendlyURL.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!groupFriendlyURLModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				groupFriendlyURL.setModifiedDate(now);
			}
			else {
				groupFriendlyURL.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (groupFriendlyURL.isNew()) {
				session.save(groupFriendlyURL);

				groupFriendlyURL.setNew(false);
			}
			else {
				groupFriendlyURL = (GroupFriendlyURL)session.merge(groupFriendlyURL);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !GroupFriendlyURLModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((groupFriendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupFriendlyURLModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { groupFriendlyURLModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((groupFriendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupFriendlyURLModelImpl.getOriginalUuid(),
						groupFriendlyURLModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						groupFriendlyURLModelImpl.getUuid(),
						groupFriendlyURLModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((groupFriendlyURLModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupFriendlyURLModelImpl.getOriginalCompanyId(),
						groupFriendlyURLModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G,
					args);

				args = new Object[] {
						groupFriendlyURLModelImpl.getCompanyId(),
						groupFriendlyURLModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_G,
					args);
			}
		}

		entityCache.putResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
			GroupFriendlyURLImpl.class, groupFriendlyURL.getPrimaryKey(),
			groupFriendlyURL, false);

		clearUniqueFindersCache(groupFriendlyURLModelImpl, false);
		cacheUniqueFindersCache(groupFriendlyURLModelImpl);

		groupFriendlyURL.resetOriginalValues();

		return groupFriendlyURL;
	}

	protected GroupFriendlyURL toUnwrappedModel(
		GroupFriendlyURL groupFriendlyURL) {
		if (groupFriendlyURL instanceof GroupFriendlyURLImpl) {
			return groupFriendlyURL;
		}

		GroupFriendlyURLImpl groupFriendlyURLImpl = new GroupFriendlyURLImpl();

		groupFriendlyURLImpl.setNew(groupFriendlyURL.isNew());
		groupFriendlyURLImpl.setPrimaryKey(groupFriendlyURL.getPrimaryKey());

		groupFriendlyURLImpl.setMvccVersion(groupFriendlyURL.getMvccVersion());
		groupFriendlyURLImpl.setUuid(groupFriendlyURL.getUuid());
		groupFriendlyURLImpl.setGroupFriendlyURLId(groupFriendlyURL.getGroupFriendlyURLId());
		groupFriendlyURLImpl.setGroupId(groupFriendlyURL.getGroupId());
		groupFriendlyURLImpl.setCompanyId(groupFriendlyURL.getCompanyId());
		groupFriendlyURLImpl.setUserId(groupFriendlyURL.getUserId());
		groupFriendlyURLImpl.setUserName(groupFriendlyURL.getUserName());
		groupFriendlyURLImpl.setCreateDate(groupFriendlyURL.getCreateDate());
		groupFriendlyURLImpl.setModifiedDate(groupFriendlyURL.getModifiedDate());
		groupFriendlyURLImpl.setFriendlyURL(groupFriendlyURL.getFriendlyURL());
		groupFriendlyURLImpl.setLanguageId(groupFriendlyURL.getLanguageId());
		groupFriendlyURLImpl.setLastPublishDate(groupFriendlyURL.getLastPublishDate());

		return groupFriendlyURLImpl;
	}

	/**
	 * Returns the group friendly url with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the group friendly url
	 * @return the group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGroupFriendlyURLException {
		GroupFriendlyURL groupFriendlyURL = fetchByPrimaryKey(primaryKey);

		if (groupFriendlyURL == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGroupFriendlyURLException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return groupFriendlyURL;
	}

	/**
	 * Returns the group friendly url with the primary key or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	 *
	 * @param groupFriendlyURLId the primary key of the group friendly url
	 * @return the group friendly url
	 * @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL findByPrimaryKey(long groupFriendlyURLId)
		throws NoSuchGroupFriendlyURLException {
		return findByPrimaryKey((Serializable)groupFriendlyURLId);
	}

	/**
	 * Returns the group friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the group friendly url
	 * @return the group friendly url, or <code>null</code> if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
				GroupFriendlyURLImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		GroupFriendlyURL groupFriendlyURL = (GroupFriendlyURL)serializable;

		if (groupFriendlyURL == null) {
			Session session = null;

			try {
				session = openSession();

				groupFriendlyURL = (GroupFriendlyURL)session.get(GroupFriendlyURLImpl.class,
						primaryKey);

				if (groupFriendlyURL != null) {
					cacheResult(groupFriendlyURL);
				}
				else {
					entityCache.putResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
						GroupFriendlyURLImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					GroupFriendlyURLImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return groupFriendlyURL;
	}

	/**
	 * Returns the group friendly url with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param groupFriendlyURLId the primary key of the group friendly url
	 * @return the group friendly url, or <code>null</code> if a group friendly url with the primary key could not be found
	 */
	@Override
	public GroupFriendlyURL fetchByPrimaryKey(long groupFriendlyURLId) {
		return fetchByPrimaryKey((Serializable)groupFriendlyURLId);
	}

	@Override
	public Map<Serializable, GroupFriendlyURL> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, GroupFriendlyURL> map = new HashMap<Serializable, GroupFriendlyURL>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			GroupFriendlyURL groupFriendlyURL = fetchByPrimaryKey(primaryKey);

			if (groupFriendlyURL != null) {
				map.put(primaryKey, groupFriendlyURL);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					GroupFriendlyURLImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (GroupFriendlyURL)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_GROUPFRIENDLYURL_WHERE_PKS_IN);

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

			for (GroupFriendlyURL groupFriendlyURL : (List<GroupFriendlyURL>)q.list()) {
				map.put(groupFriendlyURL.getPrimaryKeyObj(), groupFriendlyURL);

				cacheResult(groupFriendlyURL);

				uncachedPrimaryKeys.remove(groupFriendlyURL.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(GroupFriendlyURLModelImpl.ENTITY_CACHE_ENABLED,
					GroupFriendlyURLImpl.class, primaryKey, nullModel);
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
	 * Returns all the group friendly urls.
	 *
	 * @return the group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the group friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @return the range of group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the group friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findAll(int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the group friendly urls.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of group friendly urls
	 * @param end the upper bound of the range of group friendly urls (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of group friendly urls
	 */
	@Override
	public List<GroupFriendlyURL> findAll(int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
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

		List<GroupFriendlyURL> list = null;

		if (retrieveFromCache) {
			list = (List<GroupFriendlyURL>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_GROUPFRIENDLYURL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_GROUPFRIENDLYURL;

				if (pagination) {
					sql = sql.concat(GroupFriendlyURLModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<GroupFriendlyURL>)QueryUtil.list(q,
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
	 * Removes all the group friendly urls from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (GroupFriendlyURL groupFriendlyURL : findAll()) {
			remove(groupFriendlyURL);
		}
	}

	/**
	 * Returns the number of group friendly urls.
	 *
	 * @return the number of group friendly urls
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_GROUPFRIENDLYURL);

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
		return GroupFriendlyURLModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the group friendly url persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(GroupFriendlyURLImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_GROUPFRIENDLYURL = "SELECT groupFriendlyURL FROM GroupFriendlyURL groupFriendlyURL";
	private static final String _SQL_SELECT_GROUPFRIENDLYURL_WHERE_PKS_IN = "SELECT groupFriendlyURL FROM GroupFriendlyURL groupFriendlyURL WHERE groupFriendlyURLId IN (";
	private static final String _SQL_SELECT_GROUPFRIENDLYURL_WHERE = "SELECT groupFriendlyURL FROM GroupFriendlyURL groupFriendlyURL WHERE ";
	private static final String _SQL_COUNT_GROUPFRIENDLYURL = "SELECT COUNT(groupFriendlyURL) FROM GroupFriendlyURL groupFriendlyURL";
	private static final String _SQL_COUNT_GROUPFRIENDLYURL_WHERE = "SELECT COUNT(groupFriendlyURL) FROM GroupFriendlyURL groupFriendlyURL WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "groupFriendlyURL.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No GroupFriendlyURL exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No GroupFriendlyURL exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(GroupFriendlyURLPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}