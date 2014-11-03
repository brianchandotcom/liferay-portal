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

package com.liferay.portlet.blogs.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the blogs entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryPersistence
 * @see BlogsEntryUtil
 * @generated
 */
@ProviderType
public class BlogsEntryPersistenceImpl extends BasePersistenceImpl<BlogsEntry>
	implements BlogsEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BlogsEntryUtil} to access the blogs entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			BlogsEntryModelImpl.UUID_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the blogs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
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

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if (!Validator.equals(uuid, blogsEntry.getUuid())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByUuid_First(String uuid,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByUuid_First(uuid, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByUuid_First(String uuid,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByUuid_Last(String uuid,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByUuid_Last(String uuid,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByUuid_PrevAndNext(long entryId, String uuid,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, blogsEntry, uuid,
					orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByUuid_PrevAndNext(session, blogsEntry, uuid,
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

	protected BlogsEntry getByUuid_PrevAndNext(Session session,
		BlogsEntry blogsEntry, String uuid,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (BlogsEntry blogsEntry : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "blogsEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "blogsEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(blogsEntry.uuid IS NULL OR blogsEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			BlogsEntryModelImpl.UUID_COLUMN_BITMASK |
			BlogsEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the blogs entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.blogs.NoSuchEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByUUID_G(uuid, groupId);

		if (blogsEntry == null) {
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

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	/**
	 * Returns the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the blogs entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof BlogsEntry) {
			BlogsEntry blogsEntry = (BlogsEntry)result;

			if (!Validator.equals(uuid, blogsEntry.getUuid()) ||
					(groupId != blogsEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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

				List<BlogsEntry> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					BlogsEntry blogsEntry = list.get(0);

					result = blogsEntry;

					cacheResult(blogsEntry);

					if ((blogsEntry.getUuid() == null) ||
							!blogsEntry.getUuid().equals(uuid) ||
							(blogsEntry.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, blogsEntry);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
			return (BlogsEntry)result;
		}
	}

	/**
	 * Removes the blogs entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the blogs entry that was removed
	 */
	@Override
	public BlogsEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByUUID_G(uuid, groupId);

		return remove(blogsEntry);
	}

	/**
	 * Returns the number of blogs entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "blogsEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "blogsEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(blogsEntry.uuid IS NULL OR blogsEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "blogsEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			BlogsEntryModelImpl.UUID_COLUMN_BITMASK |
			BlogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the blogs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
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

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if (!Validator.equals(uuid, blogsEntry.getUuid()) ||
						(companyId != blogsEntry.getCompanyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByUuid_C_PrevAndNext(long entryId, String uuid,
		long companyId, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, blogsEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByUuid_C_PrevAndNext(session, blogsEntry, uuid,
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

	protected BlogsEntry getByUuid_C_PrevAndNext(Session session,
		BlogsEntry blogsEntry, String uuid, long companyId,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (BlogsEntry blogsEntry : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "blogsEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "blogsEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(blogsEntry.uuid IS NULL OR blogsEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "blogsEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			BlogsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the blogs entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
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

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByGroupId_First(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByGroupId_First(groupId, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByGroupId_First(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByGroupId_Last(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByGroupId_Last(groupId, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByGroupId_PrevAndNext(long entryId, long groupId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, blogsEntry, groupId,
					orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByGroupId_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry getByGroupId_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByGroupId(long groupId, int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByGroupId_PrevAndNext(long entryId,
		long groupId, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(entryId, groupId, orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, blogsEntry,
					groupId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session, blogsEntry,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByGroupId_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (BlogsEntry blogsEntry : findByGroupId(groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "blogsEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			BlogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the blogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByCompanyId_First(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByCompanyId_Last(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, blogsEntry,
					companyId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByCompanyId_PrevAndNext(session, blogsEntry,
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

	protected BlogsEntry getByCompanyId_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (BlogsEntry blogsEntry : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "blogsEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_UT",
			new String[] { Long.class.getName(), String.class.getName() },
			BlogsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			BlogsEntryModelImpl.URLTITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_UT = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UT",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or throws a {@link com.liferay.portlet.blogs.NoSuchEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_UT(groupId, urlTitle);

		if (blogsEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return blogsEntry;
	}

	/**
	 * Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_UT(long groupId, String urlTitle) {
		return fetchByG_UT(groupId, urlTitle, true);
	}

	/**
	 * Returns the blogs entry where groupId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_UT(long groupId, String urlTitle,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_UT,
					finderArgs, this);
		}

		if (result instanceof BlogsEntry) {
			BlogsEntry blogsEntry = (BlogsEntry)result;

			if ((groupId != blogsEntry.getGroupId()) ||
					!Validator.equals(urlTitle, blogsEntry.getUrlTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
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

				List<BlogsEntry> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
						finderArgs, list);
				}
				else {
					BlogsEntry blogsEntry = list.get(0);

					result = blogsEntry;

					cacheResult(blogsEntry);

					if ((blogsEntry.getGroupId() != groupId) ||
							(blogsEntry.getUrlTitle() == null) ||
							!blogsEntry.getUrlTitle().equals(urlTitle)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
							finderArgs, blogsEntry);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT,
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
			return (BlogsEntry)result;
		}
	}

	/**
	 * Removes the blogs entry where groupId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the blogs entry that was removed
	 */
	@Override
	public BlogsEntry removeByG_UT(long groupId, String urlTitle)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByG_UT(groupId, urlTitle);

		return remove(blogsEntry);
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_UT(long groupId, String urlTitle) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_UT;

		Object[] finderArgs = new Object[] { groupId, urlTitle };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UT_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_UT_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_UT_URLTITLE_2);
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

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_UT_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_1 = "blogsEntry.urlTitle IS NULL";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_2 = "blogsEntry.urlTitle = ?";
	private static final String _FINDER_COLUMN_G_UT_URLTITLE_3 = "(blogsEntry.urlTitle IS NULL OR blogsEntry.urlTitle = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LtD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LtD",
			new String[] { Long.class.getName(), Date.class.getName() });

	/**
	 * Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD(long groupId, Date displayDate) {
		return findByG_LtD(groupId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD(long groupId, Date displayDate,
		int start, int end) {
		return findByG_LtD(groupId, displayDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD(long groupId, Date displayDate,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LTD;
		finderArgs = new Object[] {
				groupId, displayDate,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_LtD_First(long groupId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_LtD_First(groupId, displayDate,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_LtD_First(long groupId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_LtD(groupId, displayDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_LtD_Last(long groupId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_LtD_Last(groupId, displayDate,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_LtD_Last(long groupId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_LtD(groupId, displayDate);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_LtD(groupId, displayDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_LtD_PrevAndNext(long entryId, long groupId,
		Date displayDate, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_LtD_PrevAndNext(session, blogsEntry, groupId,
					displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_LtD_PrevAndNext(session, blogsEntry, groupId,
					displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD(long groupId, Date displayDate) {
		return filterFindByG_LtD(groupId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD(long groupId, Date displayDate,
		int start, int end) {
		return filterFindByG_LtD(groupId, displayDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD(long groupId, Date displayDate,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD(groupId, displayDate, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_LtD_PrevAndNext(long entryId,
		long groupId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD_PrevAndNext(entryId, groupId, displayDate,
				orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_LtD_PrevAndNext(session, blogsEntry,
					groupId, displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_LtD_PrevAndNext(session, blogsEntry,
					groupId, displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByG_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 */
	@Override
	public void removeByG_LtD(long groupId, Date displayDate) {
		for (BlogsEntry blogsEntry : findByG_LtD(groupId, displayDate,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_LtD(long groupId, Date displayDate) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LTD;

		Object[] finderArgs = new Object[] { groupId, displayDate };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LtD(long groupId, Date displayDate) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LtD(groupId, displayDate);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_DISPLAYDATE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_LTD_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_G_LTD_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			BlogsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			BlogsEntryModelImpl.STATUS_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_S(long groupId, int status) {
		return findByG_S(groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_S(long groupId, int status, int start,
		int end) {
		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_S(long groupId, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, status,
					
					start, end, orderByComparator
				};
		}

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_S_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_S_First(groupId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_S_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_S(groupId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_S_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_S_Last(groupId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_S_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_S(groupId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_S_PrevAndNext(long entryId, long groupId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_S_PrevAndNext(session, blogsEntry, groupId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_S_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry getByG_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_S(long groupId, int status) {
		return filterFindByG_S(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end) {
		return filterFindByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_S(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S(groupId, status, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_S_PrevAndNext(long entryId, long groupId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_S_PrevAndNext(entryId, groupId, status,
				orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_S_PrevAndNext(session, blogsEntry, groupId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_S_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry filterGetByG_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (BlogsEntry blogsEntry : findByG_S(groupId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_S(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTS = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTS = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotS",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the blogs entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_NotS(long groupId, int status) {
		return findByG_NotS(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_NotS(long groupId, int status, int start,
		int end) {
		return findByG_NotS(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_NotS(long groupId, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTS;
		finderArgs = new Object[] { groupId, status, start, end, orderByComparator };

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(status == blogsEntry.getStatus())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_NotS_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_NotS_First(groupId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_NotS_First(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_NotS(groupId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_NotS_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_NotS_Last(groupId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_NotS_Last(long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_NotS(groupId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_NotS(groupId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_NotS_PrevAndNext(long entryId, long groupId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_NotS_PrevAndNext(session, blogsEntry, groupId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_NotS_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry getByG_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_NotS(long groupId, int status) {
		return filterFindByG_NotS(groupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_NotS(long groupId, int status,
		int start, int end) {
		return filterFindByG_NotS(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_NotS(long groupId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotS(groupId, status, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_NotS_PrevAndNext(long entryId,
		long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_NotS_PrevAndNext(entryId, groupId, status,
				orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_NotS_PrevAndNext(session, blogsEntry,
					groupId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_NotS_PrevAndNext(session, blogsEntry,
					groupId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByG_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_NotS(long groupId, int status) {
		for (BlogsEntry blogsEntry : findByG_NotS(groupId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_NotS(long groupId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTS;

		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_NotS(long groupId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_NotS(groupId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_NOTS_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NOTS_STATUS_2 = "blogsEntry.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			BlogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			BlogsEntryModelImpl.USERID_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U(long companyId, long userId) {
		return findByC_U(companyId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U(long companyId, long userId, int start,
		int end) {
		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U(long companyId, long userId, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U;
			finderArgs = new Object[] { companyId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U;
			finderArgs = new Object[] {
					companyId, userId,
					
					start, end, orderByComparator
				};
		}

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(userId != blogsEntry.getUserId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_U_First(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_U_First(companyId, userId,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_U_First(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_U(companyId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_U_Last(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_U_Last(companyId, userId,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_U_Last(long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_U(companyId, userId);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_U(companyId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_U_PrevAndNext(long entryId, long companyId,
		long userId, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_U_PrevAndNext(session, blogsEntry, companyId,
					userId, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_U_PrevAndNext(session, blogsEntry, companyId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_U_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, long userId,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_USERID_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		for (BlogsEntry blogsEntry : findByC_U(companyId, userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_U;

		Object[] finderArgs = new Object[] { companyId, userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_USERID_2 = "blogsEntry.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LtD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LtD",
			new String[] { Long.class.getName(), Date.class.getName() });

	/**
	 * Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD(long companyId, Date displayDate) {
		return findByC_LtD(companyId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD(long companyId, Date displayDate,
		int start, int end) {
		return findByC_LtD(companyId, displayDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD(long companyId, Date displayDate,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_LTD;
		finderArgs = new Object[] {
				companyId, displayDate,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_COMPANYID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_LtD_First(long companyId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_LtD_First(companyId, displayDate,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_LtD_First(long companyId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_LtD(companyId, displayDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_LtD_Last(long companyId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_LtD_Last(companyId, displayDate,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_LtD_Last(long companyId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_LtD(companyId, displayDate);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_LtD(companyId, displayDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_LtD_PrevAndNext(long entryId, long companyId,
		Date displayDate, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_LtD_PrevAndNext(session, blogsEntry, companyId,
					displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_LtD_PrevAndNext(session, blogsEntry, companyId,
					displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_LTD_COMPANYID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_2);
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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 */
	@Override
	public void removeByC_LtD(long companyId, Date displayDate) {
		for (BlogsEntry blogsEntry : findByC_LtD(companyId, displayDate,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_LtD(long companyId, Date displayDate) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_LTD;

		Object[] finderArgs = new Object[] { companyId, displayDate };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_COMPANYID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_LTD_DISPLAYDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_LTD_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_C_LTD_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			BlogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			BlogsEntryModelImpl.STATUS_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_S(long companyId, int status) {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_S(long companyId, int status, int start,
		int end) {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] { companyId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] {
					companyId, status,
					
					start, end, orderByComparator
				};
		}

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_S_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_S_First(companyId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_S_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_S(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_S_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_S_Last(companyId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_S_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_S_PrevAndNext(long entryId, long companyId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_S_PrevAndNext(session, blogsEntry, companyId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_S_PrevAndNext(session, blogsEntry, companyId,
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

	protected BlogsEntry getByC_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		for (BlogsEntry blogsEntry : findByC_S(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTS = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTS = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotS",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the blogs entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_NotS(long companyId, int status) {
		return findByC_NotS(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_NotS(long companyId, int status, int start,
		int end) {
		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_NotS(long companyId, int status, int start,
		int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTS;
		finderArgs = new Object[] {
				companyId, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(status == blogsEntry.getStatus())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_NotS_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_NotS_First(companyId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_NotS_First(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_NotS(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_NotS_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_NotS_Last(companyId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_NotS_Last(long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_NotS(companyId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_NotS(companyId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_NotS_PrevAndNext(long entryId, long companyId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_NotS_PrevAndNext(session, blogsEntry, companyId,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_NotS_PrevAndNext(session, blogsEntry, companyId,
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

	protected BlogsEntry getByC_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		for (BlogsEntry blogsEntry : findByC_NotS(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTS;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_NOTS_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_NOTS_STATUS_2 = "blogsEntry.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] { Date.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByLtD_S(Date displayDate, int status,
		int start, int end) {
		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByLtD_S(Date displayDate, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LTD_S;
		finderArgs = new Object[] {
				displayDate, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((displayDate.getTime() <= blogsEntry.getDisplayDate()
															.getTime()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByLtD_S_First(Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByLtD_S_First(displayDate, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByLtD_S_First(Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByLtD_S(displayDate, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByLtD_S_Last(Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByLtD_S_Last(displayDate, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByLtD_S_Last(Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByLtD_S(displayDate, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByLtD_S_PrevAndNext(long entryId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByLtD_S_PrevAndNext(session, blogsEntry, displayDate,
					status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByLtD_S_PrevAndNext(session, blogsEntry, displayDate,
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

	protected BlogsEntry getByLtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (BlogsEntry blogsEntry : findByLtD_S(displayDate, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTD_S;

		Object[] finderArgs = new Object[] { displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_LtD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_LTD = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_LtD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});

	/**
	 * Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate) {
		return findByG_U_LtD(groupId, userId, displayDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end) {
		return findByG_U_LtD(groupId, userId, displayDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_LTD;
		finderArgs = new Object[] {
				groupId, userId, displayDate,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(userId != blogsEntry.getUserId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_LtD_First(long groupId, long userId,
		Date displayDate, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_LtD_First(groupId, userId,
				displayDate, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_LtD_First(long groupId, long userId,
		Date displayDate, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_U_LtD(groupId, userId, displayDate, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_LtD_Last(long groupId, long userId,
		Date displayDate, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_LtD_Last(groupId, userId,
				displayDate, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_LtD_Last(long groupId, long userId,
		Date displayDate, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_U_LtD(groupId, userId, displayDate);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_U_LtD(groupId, userId, displayDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_U_LtD_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_LtD_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_LtD_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD(long groupId, long userId,
		Date displayDate) {
		return filterFindByG_U_LtD(groupId, userId, displayDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end) {
		return filterFindByG_U_LtD(groupId, userId, displayDate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD(long groupId, long userId,
		Date displayDate, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD(groupId, userId, displayDate, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_U_LtD_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD_PrevAndNext(entryId, groupId, userId,
				displayDate, orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_U_LtD_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_U_LtD_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByG_U_LtD_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 */
	@Override
	public void removeByG_U_LtD(long groupId, long userId, Date displayDate) {
		for (BlogsEntry blogsEntry : findByG_U_LtD(groupId, userId,
				displayDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_U_LtD(long groupId, long userId, Date displayDate) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_LTD;

		Object[] finderArgs = new Object[] { groupId, userId, displayDate };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_LtD(long groupId, long userId, Date displayDate) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_LtD(groupId, userId, displayDate);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_LTD_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL";
	private static final String _FINDER_COLUMN_G_U_LTD_DISPLAYDATE_2 = "blogsEntry.displayDate < ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			BlogsEntryModelImpl.GROUPID_COLUMN_BITMASK |
			BlogsEntryModelImpl.USERID_COLUMN_BITMASK |
			BlogsEntryModelImpl.STATUS_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status) {
		return findByG_U_S(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status,
		int start, int end) {
		return findByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_S(long groupId, long userId, int status,
		int start, int end, OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S;
			finderArgs = new Object[] { groupId, userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_S;
			finderArgs = new Object[] {
					groupId, userId, status,
					
					start, end, orderByComparator
				};
		}

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(userId != blogsEntry.getUserId()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_S_First(groupId, userId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_S_First(long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_U_S(groupId, userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_S_Last(groupId, userId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_S_Last(long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_U_S(groupId, userId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_U_S(groupId, userId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_U_S_PrevAndNext(long entryId, long groupId,
		long userId, int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_S_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_S_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status) {
		return filterFindByG_U_S(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end) {
		return filterFindByG_U_S(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_S(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_S(groupId, userId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_U_S_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_S_PrevAndNext(entryId, groupId, userId, status,
				orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_U_S_PrevAndNext(session, blogsEntry,
					groupId, userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_U_S_PrevAndNext(session, blogsEntry,
					groupId, userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByG_U_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByG_U_S(long groupId, long userId, int status) {
		for (BlogsEntry blogsEntry : findByG_U_S(groupId, userId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_U_S(long groupId, long userId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_S;

		Object[] finderArgs = new Object[] { groupId, userId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_S_USERID_2);

			query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_S(long groupId, long userId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_S(groupId, userId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_S_USERID_2);

		query.append(_FINDER_COLUMN_G_U_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_NOTS = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_NotS(long groupId, long userId, int status) {
		return findByG_U_NotS(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end) {
		return findByG_U_NotS(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_NotS(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_NOTS;
		finderArgs = new Object[] {
				groupId, userId, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(userId != blogsEntry.getUserId()) ||
						(status == blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

			query.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_NotS_First(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_NotS_First(groupId, userId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_NotS_First(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_U_NotS(groupId, userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_NotS_Last(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_NotS_Last(groupId, userId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_NotS_Last(long groupId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_U_NotS(groupId, userId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_U_NotS(groupId, userId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_U_NotS_PrevAndNext(long entryId, long groupId,
		long userId, int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_NotS_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_NotS_PrevAndNext(session, blogsEntry, groupId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_NotS(long groupId, long userId,
		int status) {
		return filterFindByG_U_NotS(groupId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_NotS(long groupId, long userId,
		int status, int start, int end) {
		return filterFindByG_U_NotS(groupId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_NotS(long groupId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_NotS(groupId, userId, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_U_NotS_PrevAndNext(long entryId,
		long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_NotS_PrevAndNext(entryId, groupId, userId, status,
				orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_U_NotS_PrevAndNext(session, blogsEntry,
					groupId, userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_U_NotS_PrevAndNext(session, blogsEntry,
					groupId, userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByG_U_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByG_U_NotS(long groupId, long userId, int status) {
		for (BlogsEntry blogsEntry : findByG_U_NotS(groupId, userId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_U_NotS(long groupId, long userId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_NOTS;

		Object[] finderArgs = new Object[] { groupId, userId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

			query.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_NotS(long groupId, long userId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_NotS(groupId, userId, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_USERID_2);

		query.append(_FINDER_COLUMN_G_U_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_NOTS_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_NOTS_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_NOTS_STATUS_2 = "blogsEntry.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD_S(long groupId, Date displayDate,
		int status) {
		return findByG_LtD_S(groupId, displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end) {
		return findByG_LtD_S(groupId, displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LTD_S;
		finderArgs = new Object[] {
				groupId, displayDate, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_LtD_S_First(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_LtD_S_First(groupId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_LtD_S_First(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_LtD_S(groupId, displayDate, status, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_LtD_S_Last(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_LtD_S_Last(groupId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_LtD_S_Last(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_LtD_S(groupId, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_LtD_S(groupId, displayDate, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_LtD_S_PrevAndNext(long entryId, long groupId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_LtD_S_PrevAndNext(session, blogsEntry, groupId,
					displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_LtD_S_PrevAndNext(session, blogsEntry, groupId,
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

	protected BlogsEntry getByG_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD_S(long groupId, Date displayDate,
		int status) {
		return filterFindByG_LtD_S(groupId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end) {
		return filterFindByG_LtD_S(groupId, displayDate, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD_S(long groupId, Date displayDate,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD_S(groupId, displayDate, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_LtD_S_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD_S_PrevAndNext(entryId, groupId, displayDate,
				status, orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_LtD_S_PrevAndNext(session, blogsEntry,
					groupId, displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_LtD_S_PrevAndNext(session, blogsEntry,
					groupId, displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByG_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByG_LtD_S(long groupId, Date displayDate, int status) {
		for (BlogsEntry blogsEntry : findByG_LtD_S(groupId, displayDate,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_LtD_S(long groupId, Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LTD_S;

		Object[] finderArgs = new Object[] { groupId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LtD_S(long groupId, Date displayDate, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LtD_S(groupId, displayDate, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_S_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_LTD_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_LTD_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LTD_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LtD_NotS",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LTD_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LtD_NotS",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD_NotS(long groupId, Date displayDate,
		int status) {
		return findByG_LtD_NotS(groupId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD_NotS(long groupId, Date displayDate,
		int status, int start, int end) {
		return findByG_LtD_NotS(groupId, displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_LtD_NotS(long groupId, Date displayDate,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LTD_NOTS;
		finderArgs = new Object[] {
				groupId, displayDate, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime()) ||
						(status == blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_NOTS_GROUPID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_LTD_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_LtD_NotS_First(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_LtD_NotS_First(groupId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_LtD_NotS_First(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_LtD_NotS(groupId, displayDate, status,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_LtD_NotS_Last(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_LtD_NotS_Last(groupId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_LtD_NotS_Last(long groupId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_LtD_NotS(groupId, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_LtD_NotS(groupId, displayDate, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_LtD_NotS_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_LtD_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_NOTS_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_NOTS_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		Date displayDate, int status) {
		return filterFindByG_LtD_NotS(groupId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		Date displayDate, int status, int start, int end) {
		return filterFindByG_LtD_NotS(groupId, displayDate, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_LtD_NotS(long groupId,
		Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD_NotS(groupId, displayDate, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_NOTS_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_LtD_NotS_PrevAndNext(long entryId,
		long groupId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LtD_NotS_PrevAndNext(entryId, groupId, displayDate,
				status, orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry filterGetByG_LtD_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LTD_NOTS_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByG_LtD_NotS(long groupId, Date displayDate, int status) {
		for (BlogsEntry blogsEntry : findByG_LtD_NotS(groupId, displayDate,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_LtD_NotS(long groupId, Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LTD_NOTS;

		Object[] finderArgs = new Object[] { groupId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_LTD_NOTS_GROUPID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_LTD_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LtD_NotS(long groupId, Date displayDate,
		int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LtD_NotS(groupId, displayDate, status);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_LTD_NOTS_GROUPID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_LTD_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_LTD_NOTS_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_LTD_NOTS_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_LTD_NOTS_STATUS_2 = "blogsEntry.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			BlogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			BlogsEntryModelImpl.USERID_COLUMN_BITMASK |
			BlogsEntryModelImpl.STATUS_COLUMN_BITMASK |
			BlogsEntryModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			BlogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U_S(long companyId, long userId, int status) {
		return findByC_U_S(companyId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end) {
		return findByC_U_S(companyId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U_S(long companyId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S;
			finderArgs = new Object[] { companyId, userId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U_S;
			finderArgs = new Object[] {
					companyId, userId, status,
					
					start, end, orderByComparator
				};
		}

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(userId != blogsEntry.getUserId()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_S_USERID_2);

			query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_U_S_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_U_S_First(companyId, userId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_U_S_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_U_S(companyId, userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_U_S_Last(long companyId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_U_S_Last(companyId, userId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_U_S_Last(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_U_S(companyId, userId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_U_S(companyId, userId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_U_S_PrevAndNext(long entryId, long companyId,
		long userId, int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_U_S_PrevAndNext(session, blogsEntry, companyId,
					userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_U_S_PrevAndNext(session, blogsEntry, companyId,
					userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_U_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_S_USERID_2);

		query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and userId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByC_U_S(long companyId, long userId, int status) {
		for (BlogsEntry blogsEntry : findByC_U_S(companyId, userId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and userId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_U_S(long companyId, long userId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_U_S;

		Object[] finderArgs = new Object[] { companyId, userId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_S_USERID_2);

			query.append(_FINDER_COLUMN_C_U_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_U_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U_NOTS = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_U_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_U_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U_NotS(long companyId, long userId,
		int status) {
		return findByC_U_NotS(companyId, userId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U_NotS(long companyId, long userId,
		int status, int start, int end) {
		return findByC_U_NotS(companyId, userId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_U_NotS(long companyId, long userId,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_U_NOTS;
		finderArgs = new Object[] {
				companyId, userId, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(userId != blogsEntry.getUserId()) ||
						(status == blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_NOTS_USERID_2);

			query.append(_FINDER_COLUMN_C_U_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_U_NotS_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_U_NotS_First(companyId, userId,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_U_NotS_First(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_U_NotS(companyId, userId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_U_NotS_Last(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_U_NotS_Last(companyId, userId, status,
				orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_U_NotS_Last(long companyId, long userId,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_U_NotS(companyId, userId, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_U_NotS(companyId, userId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_U_NotS_PrevAndNext(long entryId,
		long companyId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_U_NotS_PrevAndNext(session, blogsEntry,
					companyId, userId, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_U_NotS_PrevAndNext(session, blogsEntry,
					companyId, userId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_U_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, long userId, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_U_NOTS_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_U_NOTS_USERID_2);

		query.append(_FINDER_COLUMN_C_U_NOTS_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(userId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 */
	@Override
	public void removeByC_U_NotS(long companyId, long userId, int status) {
		for (BlogsEntry blogsEntry : findByC_U_NotS(companyId, userId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and userId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_U_NotS(long companyId, long userId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_U_NOTS;

		Object[] finderArgs = new Object[] { companyId, userId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_NOTS_USERID_2);

			query.append(_FINDER_COLUMN_C_U_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_U_NOTS_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_NOTS_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_NOTS_STATUS_2 = "blogsEntry.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_LTD_S = new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LtD_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD_S(long companyId, Date displayDate,
		int status) {
		return findByC_LtD_S(companyId, displayDate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD_S(long companyId, Date displayDate,
		int status, int start, int end) {
		return findByC_LtD_S(companyId, displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD_S(long companyId, Date displayDate,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_LTD_S;
		finderArgs = new Object[] {
				companyId, displayDate, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_S_COMPANYID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_LtD_S_First(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_LtD_S_First(companyId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_LtD_S_First(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_LtD_S(companyId, displayDate, status,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_LtD_S_Last(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_LtD_S_Last(companyId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_LtD_S_Last(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_LtD_S(companyId, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_LtD_S(companyId, displayDate, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_LtD_S_PrevAndNext(long entryId, long companyId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_LtD_S_PrevAndNext(session, blogsEntry, companyId,
					displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_LtD_S_PrevAndNext(session, blogsEntry, companyId,
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

	protected BlogsEntry getByC_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_LTD_S_COMPANYID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_C_LTD_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByC_LtD_S(long companyId, Date displayDate, int status) {
		for (BlogsEntry blogsEntry : findByC_LtD_S(companyId, displayDate,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_LtD_S(long companyId, Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_LTD_S;

		Object[] finderArgs = new Object[] { companyId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_S_COMPANYID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_LTD_S_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_C_LTD_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_LTD_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LtD_NotS",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_LTD_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LtD_NotS",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD_NotS(long companyId, Date displayDate,
		int status) {
		return findByC_LtD_NotS(companyId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD_NotS(long companyId, Date displayDate,
		int status, int start, int end) {
		return findByC_LtD_NotS(companyId, displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByC_LtD_NotS(long companyId, Date displayDate,
		int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_LTD_NOTS;
		finderArgs = new Object[] {
				companyId, displayDate, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((companyId != blogsEntry.getCompanyId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime()) ||
						(status == blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_NOTS_COMPANYID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_LTD_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_LtD_NotS_First(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_LtD_NotS_First(companyId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_LtD_NotS_First(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByC_LtD_NotS(companyId, displayDate,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByC_LtD_NotS_Last(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByC_LtD_NotS_Last(companyId, displayDate,
				status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByC_LtD_NotS_Last(long companyId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByC_LtD_NotS(companyId, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByC_LtD_NotS(companyId, displayDate,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByC_LtD_NotS_PrevAndNext(long entryId,
		long companyId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByC_LtD_NotS_PrevAndNext(session, blogsEntry,
					companyId, displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByC_LtD_NotS_PrevAndNext(session, blogsEntry,
					companyId, displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByC_LtD_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long companyId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_LTD_NOTS_COMPANYID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_C_LTD_NOTS_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByC_LtD_NotS(long companyId, Date displayDate, int status) {
		for (BlogsEntry blogsEntry : findByC_LtD_NotS(companyId, displayDate,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where companyId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByC_LtD_NotS(long companyId, Date displayDate, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_LTD_NOTS;

		Object[] finderArgs = new Object[] { companyId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_LTD_NOTS_COMPANYID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_C_LTD_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_LTD_NOTS_COMPANYID_2 = "blogsEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_C_LTD_NOTS_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_C_LTD_NOTS_STATUS_2 = "blogsEntry.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_LTD_S =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_LTD_S =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_LtD_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status) {
		return findByG_U_LtD_S(groupId, userId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end) {
		return findByG_U_LtD_S(groupId, userId, displayDate, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_LTD_S;
		finderArgs = new Object[] {
				groupId, userId, displayDate, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(userId != blogsEntry.getUserId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime()) ||
						(status != blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_LtD_S_First(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_LtD_S_First(groupId, userId,
				displayDate, status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_LtD_S_First(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_U_LtD_S(groupId, userId, displayDate,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_LtD_S_Last(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_LtD_S_Last(groupId, userId,
				displayDate, status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_LtD_S_Last(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_U_LtD_S(groupId, userId, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_U_LtD_S(groupId, userId, displayDate,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_U_LtD_S_PrevAndNext(long entryId, long groupId,
		long userId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_LtD_S_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, status, orderByComparator, true);

			array[1] = blogsEntry;

			array[2] = getByG_U_LtD_S_PrevAndNext(session, blogsEntry, groupId,
					userId, displayDate, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsEntry getByG_U_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status) {
		return filterFindByG_U_LtD_S(groupId, userId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end) {
		return filterFindByG_U_LtD_S(groupId, userId, displayDate, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD_S(groupId, userId, displayDate, status, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_U_LtD_S_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD_S_PrevAndNext(entryId, groupId, userId,
				displayDate, status, orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_U_LtD_S_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, status, orderByComparator,
					true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_U_LtD_S_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, status, orderByComparator,
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

	protected BlogsEntry filterGetByG_U_LtD_S_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByG_U_LtD_S(long groupId, long userId, Date displayDate,
		int status) {
		for (BlogsEntry blogsEntry : findByG_U_LtD_S(groupId, userId,
				displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_U_LtD_S(long groupId, long userId, Date displayDate,
		int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_LTD_S;

		Object[] finderArgs = new Object[] { groupId, userId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_LtD_S(long groupId, long userId,
		Date displayDate, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_LtD_S(groupId, userId, displayDate, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_S_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_LTD_S_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_S_STATUS_2 = "blogsEntry.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_LTD_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, BlogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_LtD_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_LTD_NOTS =
		new FinderPath(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_LtD_NotS",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status) {
		return findByG_U_LtD_NotS(groupId, userId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status, int start, int end) {
		return findByG_U_LtD_NotS(groupId, userId, displayDate, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries
	 */
	@Override
	public List<BlogsEntry> findByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_LTD_NOTS;
		finderArgs = new Object[] {
				groupId, userId, displayDate, status,
				
				start, end, orderByComparator
			};

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BlogsEntry blogsEntry : list) {
				if ((groupId != blogsEntry.getGroupId()) ||
						(userId != blogsEntry.getUserId()) ||
						(displayDate.getTime() <= blogsEntry.getDisplayDate()
																.getTime()) ||
						(status == blogsEntry.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_USERID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_LtD_NotS_First(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_LtD_NotS_First(groupId, userId,
				displayDate, status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_LtD_NotS_First(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		List<BlogsEntry> list = findByG_U_LtD_NotS(groupId, userId,
				displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry findByG_U_LtD_NotS_Last(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByG_U_LtD_NotS_Last(groupId, userId,
				displayDate, status, orderByComparator);

		if (blogsEntry != null) {
			return blogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", displayDate=");
		msg.append(displayDate);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs entry, or <code>null</code> if a matching blogs entry could not be found
	 */
	@Override
	public BlogsEntry fetchByG_U_LtD_NotS_Last(long groupId, long userId,
		Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator) {
		int count = countByG_U_LtD_NotS(groupId, userId, displayDate, status);

		if (count == 0) {
			return null;
		}

		List<BlogsEntry> list = findByG_U_LtD_NotS(groupId, userId,
				displayDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] findByG_U_LtD_NotS_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = getByG_U_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, status, orderByComparator,
					true);

			array[1] = blogsEntry;

			array[2] = getByG_U_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, status, orderByComparator,
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

	protected BlogsEntry getByG_U_LtD_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_STATUS_2);

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
			query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status) {
		return filterFindByG_U_LtD_NotS(groupId, userId, displayDate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status, int start, int end) {
		return filterFindByG_U_LtD_NotS(groupId, userId, displayDate, status,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries that the user has permissions to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs entries that the user has permission to view
	 */
	@Override
	public List<BlogsEntry> filterFindByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status, int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD_NotS(groupId, userId, displayDate, status,
				start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			return (List<BlogsEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the blogs entries before and after the current blogs entry in the ordered set of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param entryId the primary key of the current blogs entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry[] filterFindByG_U_LtD_NotS_PrevAndNext(long entryId,
		long groupId, long userId, Date displayDate, int status,
		OrderByComparator<BlogsEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_LtD_NotS_PrevAndNext(entryId, groupId, userId,
				displayDate, status, orderByComparator);
		}

		BlogsEntry blogsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			BlogsEntry[] array = new BlogsEntryImpl[3];

			array[0] = filterGetByG_U_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, status, orderByComparator,
					true);

			array[1] = blogsEntry;

			array[2] = filterGetByG_U_LtD_NotS_PrevAndNext(session, blogsEntry,
					groupId, userId, displayDate, status, orderByComparator,
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

	protected BlogsEntry filterGetByG_U_LtD_NotS_PrevAndNext(Session session,
		BlogsEntry blogsEntry, long groupId, long userId, Date displayDate,
		int status, OrderByComparator<BlogsEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(BlogsEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(BlogsEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, BlogsEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, BlogsEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (bindDisplayDate) {
			qPos.add(new Timestamp(displayDate.getTime()));
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status) {
		for (BlogsEntry blogsEntry : findByG_U_LtD_NotS(groupId, userId,
				displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries
	 */
	@Override
	public int countByG_U_LtD_NotS(long groupId, long userId, Date displayDate,
		int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_LTD_NOTS;

		Object[] finderArgs = new Object[] { groupId, userId, displayDate, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_BLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_USERID_2);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_2);
			}

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (bindDisplayDate) {
					qPos.add(new Timestamp(displayDate.getTime()));
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of blogs entries that the user has permission to view where groupId = &#63; and userId = &#63; and displayDate &lt; &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching blogs entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_LtD_NotS(long groupId, long userId,
		Date displayDate, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_LtD_NotS(groupId, userId, displayDate, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_BLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_USERID_2);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			query.append(_FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_2);
		}

		query.append(_FINDER_COLUMN_G_U_LTD_NOTS_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BlogsEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (bindDisplayDate) {
				qPos.add(new Timestamp(displayDate.getTime()));
			}

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_LTD_NOTS_GROUPID_2 = "blogsEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_NOTS_USERID_2 = "blogsEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_1 = "blogsEntry.displayDate < NULL AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_NOTS_DISPLAYDATE_2 = "blogsEntry.displayDate < ? AND ";
	private static final String _FINDER_COLUMN_G_U_LTD_NOTS_STATUS_2 = "blogsEntry.status != ?";

	public BlogsEntryPersistenceImpl() {
		setModelClass(BlogsEntry.class);
	}

	/**
	 * Caches the blogs entry in the entity cache if it is enabled.
	 *
	 * @param blogsEntry the blogs entry
	 */
	@Override
	public void cacheResult(BlogsEntry blogsEntry) {
		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { blogsEntry.getUuid(), blogsEntry.getGroupId() },
			blogsEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT,
			new Object[] { blogsEntry.getGroupId(), blogsEntry.getUrlTitle() },
			blogsEntry);

		blogsEntry.resetOriginalValues();
	}

	/**
	 * Caches the blogs entries in the entity cache if it is enabled.
	 *
	 * @param blogsEntries the blogs entries
	 */
	@Override
	public void cacheResult(List<BlogsEntry> blogsEntries) {
		for (BlogsEntry blogsEntry : blogsEntries) {
			if (EntityCacheUtil.getResult(
						BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
						BlogsEntryImpl.class, blogsEntry.getPrimaryKey()) == null) {
				cacheResult(blogsEntry);
			}
			else {
				blogsEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all blogs entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(BlogsEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(BlogsEntryImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the blogs entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BlogsEntry blogsEntry) {
		EntityCacheUtil.removeResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(blogsEntry);
	}

	@Override
	public void clearCache(List<BlogsEntry> blogsEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BlogsEntry blogsEntry : blogsEntries) {
			EntityCacheUtil.removeResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlogsEntryImpl.class, blogsEntry.getPrimaryKey());

			clearUniqueFindersCache(blogsEntry);
		}
	}

	protected void cacheUniqueFindersCache(BlogsEntry blogsEntry) {
		if (blogsEntry.isNew()) {
			Object[] args = new Object[] {
					blogsEntry.getUuid(), blogsEntry.getGroupId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				blogsEntry);

			args = new Object[] {
					blogsEntry.getGroupId(), blogsEntry.getUrlTitle()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT, args,
				blogsEntry);
		}
		else {
			BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntry.getUuid(), blogsEntry.getGroupId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					blogsEntry);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_UT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntry.getGroupId(), blogsEntry.getUrlTitle()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_UT, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_UT, args,
					blogsEntry);
			}
		}
	}

	protected void clearUniqueFindersCache(BlogsEntry blogsEntry) {
		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		Object[] args = new Object[] {
				blogsEntry.getUuid(), blogsEntry.getGroupId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((blogsEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					blogsEntryModelImpl.getOriginalUuid(),
					blogsEntryModelImpl.getOriginalGroupId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] { blogsEntry.getGroupId(), blogsEntry.getUrlTitle() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_UT, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT, args);

		if ((blogsEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_UT.getColumnBitmask()) != 0) {
			args = new Object[] {
					blogsEntryModelImpl.getOriginalGroupId(),
					blogsEntryModelImpl.getOriginalUrlTitle()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_UT, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_UT, args);
		}
	}

	/**
	 * Creates a new blogs entry with the primary key. Does not add the blogs entry to the database.
	 *
	 * @param entryId the primary key for the new blogs entry
	 * @return the new blogs entry
	 */
	@Override
	public BlogsEntry create(long entryId) {
		BlogsEntry blogsEntry = new BlogsEntryImpl();

		blogsEntry.setNew(true);
		blogsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		blogsEntry.setUuid(uuid);

		return blogsEntry;
	}

	/**
	 * Removes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the blogs entry
	 * @return the blogs entry that was removed
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the blogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the blogs entry
	 * @return the blogs entry that was removed
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			BlogsEntry blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
					primaryKey);

			if (blogsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(blogsEntry);
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
	protected BlogsEntry removeImpl(BlogsEntry blogsEntry) {
		blogsEntry = toUnwrappedModel(blogsEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(blogsEntry)) {
				blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
						blogsEntry.getPrimaryKeyObj());
			}

			if (blogsEntry != null) {
				session.delete(blogsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (blogsEntry != null) {
			clearCache(blogsEntry);
		}

		return blogsEntry;
	}

	@Override
	public BlogsEntry updateImpl(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		blogsEntry = toUnwrappedModel(blogsEntry);

		boolean isNew = blogsEntry.isNew();

		BlogsEntryModelImpl blogsEntryModelImpl = (BlogsEntryModelImpl)blogsEntry;

		if (Validator.isNull(blogsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			blogsEntry.setUuid(uuid);
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = blogsEntry.getCompanyId();

			long groupId = blogsEntry.getGroupId();

			long entryId = 0;

			if (!isNew) {
				entryId = blogsEntry.getPrimaryKey();
			}

			try {
				blogsEntry.setTitle(SanitizerUtil.sanitize(companyId, groupId,
						userId,
						com.liferay.portlet.blogs.model.BlogsEntry.class.getName(),
						entryId, ContentTypes.TEXT_PLAIN, Sanitizer.MODE_ALL,
						blogsEntry.getTitle(), null));

				blogsEntry.setContent(SanitizerUtil.sanitize(companyId,
						groupId, userId,
						com.liferay.portlet.blogs.model.BlogsEntry.class.getName(),
						entryId, ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
						blogsEntry.getContent(), null));
			}
			catch (SanitizerException se) {
				throw new SystemException(se);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (blogsEntry.isNew()) {
				session.save(blogsEntry);

				blogsEntry.setNew(false);
			}
			else {
				session.merge(blogsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !BlogsEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { blogsEntryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalUuid(),
						blogsEntryModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						blogsEntryModelImpl.getUuid(),
						blogsEntryModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { blogsEntryModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { blogsEntryModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalGroupId(),
						blogsEntryModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						blogsEntryModelImpl.getGroupId(),
						blogsEntryModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalCompanyId(),
						blogsEntryModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U,
					args);

				args = new Object[] {
						blogsEntryModelImpl.getCompanyId(),
						blogsEntryModelImpl.getUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalCompanyId(),
						blogsEntryModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);

				args = new Object[] {
						blogsEntryModelImpl.getCompanyId(),
						blogsEntryModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalGroupId(),
						blogsEntryModelImpl.getOriginalUserId(),
						blogsEntryModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S,
					args);

				args = new Object[] {
						blogsEntryModelImpl.getGroupId(),
						blogsEntryModelImpl.getUserId(),
						blogsEntryModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_S,
					args);
			}

			if ((blogsEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsEntryModelImpl.getOriginalCompanyId(),
						blogsEntryModelImpl.getOriginalUserId(),
						blogsEntryModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S,
					args);

				args = new Object[] {
						blogsEntryModelImpl.getCompanyId(),
						blogsEntryModelImpl.getUserId(),
						blogsEntryModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_U_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_U_S,
					args);
			}
		}

		EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
			BlogsEntryImpl.class, blogsEntry.getPrimaryKey(), blogsEntry, false);

		clearUniqueFindersCache(blogsEntry);
		cacheUniqueFindersCache(blogsEntry);

		blogsEntry.resetOriginalValues();

		return blogsEntry;
	}

	protected BlogsEntry toUnwrappedModel(BlogsEntry blogsEntry) {
		if (blogsEntry instanceof BlogsEntryImpl) {
			return blogsEntry;
		}

		BlogsEntryImpl blogsEntryImpl = new BlogsEntryImpl();

		blogsEntryImpl.setNew(blogsEntry.isNew());
		blogsEntryImpl.setPrimaryKey(blogsEntry.getPrimaryKey());

		blogsEntryImpl.setUuid(blogsEntry.getUuid());
		blogsEntryImpl.setEntryId(blogsEntry.getEntryId());
		blogsEntryImpl.setGroupId(blogsEntry.getGroupId());
		blogsEntryImpl.setCompanyId(blogsEntry.getCompanyId());
		blogsEntryImpl.setUserId(blogsEntry.getUserId());
		blogsEntryImpl.setUserName(blogsEntry.getUserName());
		blogsEntryImpl.setCreateDate(blogsEntry.getCreateDate());
		blogsEntryImpl.setModifiedDate(blogsEntry.getModifiedDate());
		blogsEntryImpl.setTitle(blogsEntry.getTitle());
		blogsEntryImpl.setSubtitle(blogsEntry.getSubtitle());
		blogsEntryImpl.setUrlTitle(blogsEntry.getUrlTitle());
		blogsEntryImpl.setDescription(blogsEntry.getDescription());
		blogsEntryImpl.setContent(blogsEntry.getContent());
		blogsEntryImpl.setDisplayDate(blogsEntry.getDisplayDate());
		blogsEntryImpl.setAllowPingbacks(blogsEntry.isAllowPingbacks());
		blogsEntryImpl.setAllowTrackbacks(blogsEntry.isAllowTrackbacks());
		blogsEntryImpl.setTrackbacks(blogsEntry.getTrackbacks());
		blogsEntryImpl.setCoverImageFileEntryId(blogsEntry.getCoverImageFileEntryId());
		blogsEntryImpl.setCoverImageURL(blogsEntry.getCoverImageURL());
		blogsEntryImpl.setSmallImage(blogsEntry.isSmallImage());
		blogsEntryImpl.setSmallImageFileEntryId(blogsEntry.getSmallImageFileEntryId());
		blogsEntryImpl.setSmallImageId(blogsEntry.getSmallImageId());
		blogsEntryImpl.setSmallImageURL(blogsEntry.getSmallImageURL());
		blogsEntryImpl.setStatus(blogsEntry.getStatus());
		blogsEntryImpl.setStatusByUserId(blogsEntry.getStatusByUserId());
		blogsEntryImpl.setStatusByUserName(blogsEntry.getStatusByUserName());
		blogsEntryImpl.setStatusDate(blogsEntry.getStatusDate());

		return blogsEntryImpl;
	}

	/**
	 * Returns the blogs entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs entry
	 * @return the blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {
		BlogsEntry blogsEntry = fetchByPrimaryKey(primaryKey);

		if (blogsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return blogsEntry;
	}

	/**
	 * Returns the blogs entry with the primary key or throws a {@link com.liferay.portlet.blogs.NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the blogs entry
	 * @return the blogs entry
	 * @throws com.liferay.portlet.blogs.NoSuchEntryException if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the blogs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs entry
	 * @return the blogs entry, or <code>null</code> if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry fetchByPrimaryKey(Serializable primaryKey) {
		BlogsEntry blogsEntry = (BlogsEntry)EntityCacheUtil.getResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
				BlogsEntryImpl.class, primaryKey);

		if (blogsEntry == _nullBlogsEntry) {
			return null;
		}

		if (blogsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				blogsEntry = (BlogsEntry)session.get(BlogsEntryImpl.class,
						primaryKey);

				if (blogsEntry != null) {
					cacheResult(blogsEntry);
				}
				else {
					EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
						BlogsEntryImpl.class, primaryKey, _nullBlogsEntry);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
					BlogsEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return blogsEntry;
	}

	/**
	 * Returns the blogs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the blogs entry
	 * @return the blogs entry, or <code>null</code> if a blogs entry with the primary key could not be found
	 */
	@Override
	public BlogsEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	@Override
	public Map<Serializable, BlogsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BlogsEntry> map = new HashMap<Serializable, BlogsEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BlogsEntry blogsEntry = fetchByPrimaryKey(primaryKey);

			if (blogsEntry != null) {
				map.put(primaryKey, blogsEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			BlogsEntry blogsEntry = (BlogsEntry)EntityCacheUtil.getResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
					BlogsEntryImpl.class, primaryKey);

			if (blogsEntry == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, blogsEntry);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_BLOGSENTRY_WHERE_PKS_IN);

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

			for (BlogsEntry blogsEntry : (List<BlogsEntry>)q.list()) {
				map.put(blogsEntry.getPrimaryKeyObj(), blogsEntry);

				cacheResult(blogsEntry);

				uncachedPrimaryKeys.remove(blogsEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(BlogsEntryModelImpl.ENTITY_CACHE_ENABLED,
					BlogsEntryImpl.class, primaryKey, _nullBlogsEntry);
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
	 * Returns all the blogs entries.
	 *
	 * @return the blogs entries
	 */
	@Override
	public List<BlogsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @return the range of blogs entries
	 */
	@Override
	public List<BlogsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs entries
	 * @param end the upper bound of the range of blogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of blogs entries
	 */
	@Override
	public List<BlogsEntry> findAll(int start, int end,
		OrderByComparator<BlogsEntry> orderByComparator) {
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

		List<BlogsEntry> list = (List<BlogsEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BLOGSENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BLOGSENTRY;

				if (pagination) {
					sql = sql.concat(BlogsEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the blogs entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BlogsEntry blogsEntry : findAll()) {
			remove(blogsEntry);
		}
	}

	/**
	 * Returns the number of blogs entries.
	 *
	 * @return the number of blogs entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BLOGSENTRY);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
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
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the blogs entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(BlogsEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_BLOGSENTRY = "SELECT blogsEntry FROM BlogsEntry blogsEntry";
	private static final String _SQL_SELECT_BLOGSENTRY_WHERE_PKS_IN = "SELECT blogsEntry FROM BlogsEntry blogsEntry WHERE entryId IN (";
	private static final String _SQL_SELECT_BLOGSENTRY_WHERE = "SELECT blogsEntry FROM BlogsEntry blogsEntry WHERE ";
	private static final String _SQL_COUNT_BLOGSENTRY = "SELECT COUNT(blogsEntry) FROM BlogsEntry blogsEntry";
	private static final String _SQL_COUNT_BLOGSENTRY_WHERE = "SELECT COUNT(blogsEntry) FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "blogsEntry.entryId";
	private static final String _FILTER_SQL_SELECT_BLOGSENTRY_WHERE = "SELECT DISTINCT {blogsEntry.*} FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {BlogsEntry.*} FROM (SELECT DISTINCT blogsEntry.entryId FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_BLOGSENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN BlogsEntry ON TEMP_TABLE.entryId = BlogsEntry.entryId";
	private static final String _FILTER_SQL_COUNT_BLOGSENTRY_WHERE = "SELECT COUNT(DISTINCT blogsEntry.entryId) AS COUNT_VALUE FROM BlogsEntry blogsEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "blogsEntry";
	private static final String _FILTER_ENTITY_TABLE = "BlogsEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "blogsEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "BlogsEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BlogsEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BlogsEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(BlogsEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final BlogsEntry _nullBlogsEntry = new BlogsEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<BlogsEntry> toCacheModel() {
				return _nullBlogsEntryCacheModel;
			}
		};

	private static final CacheModel<BlogsEntry> _nullBlogsEntryCacheModel = new CacheModel<BlogsEntry>() {
			@Override
			public BlogsEntry toEntityModel() {
				return _nullBlogsEntry;
			}
		};
}