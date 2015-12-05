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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;

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
 * The persistence implementation for the blogs stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserPersistence
 * @see com.liferay.portlet.blogs.service.persistence.BlogsStatsUserUtil
 * @generated
 */
@ProviderType
public class BlogsStatsUserPersistenceImpl extends BasePersistenceImpl<BlogsStatsUser>
	implements BlogsStatsUserPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BlogsStatsUserUtil} to access the blogs stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BlogsStatsUserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			BlogsStatsUserModelImpl.GROUPID_COLUMN_BITMASK |
			BlogsStatsUserModelImpl.ENTRYCOUNT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the blogs stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
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

		List<BlogsStatsUser> list = null;

		if (retrieveFromCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if ((groupId != blogsStatsUser.getGroupId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
	 * Returns the first blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByGroupId_First(long groupId,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByGroupId_First(groupId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByGroupId_First(long groupId,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		List<BlogsStatsUser> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByGroupId_Last(long groupId,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByGroupId_Last(long groupId,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, blogsStatsUser,
					groupId, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByGroupId_PrevAndNext(session, blogsStatsUser,
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

	protected BlogsStatsUser getByGroupId_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long groupId,
		OrderByComparator<BlogsStatsUser> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs stats users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (BlogsStatsUser blogsStatsUser : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Returns the number of blogs stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching blogs stats users
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "blogsStatsUser.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			BlogsStatsUserModelImpl.USERID_COLUMN_BITMASK |
			BlogsStatsUserModelImpl.ENTRYCOUNT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the blogs stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByUserId(long userId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByUserId(long userId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<BlogsStatsUser> list = null;

		if (retrieveFromCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if ((userId != blogsStatsUser.getUserId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
	 * Returns the first blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByUserId_First(long userId,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByUserId_First(userId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByUserId_First(long userId,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		List<BlogsStatsUser> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByUserId_Last(long userId,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByUserId_Last(userId,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByUserId_Last(long userId,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByUserId_PrevAndNext(session, blogsStatsUser, userId,
					orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByUserId_PrevAndNext(session, blogsStatsUser, userId,
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

	protected BlogsStatsUser getByUserId_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long userId,
		OrderByComparator<BlogsStatsUser> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs stats users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (BlogsStatsUser blogsStatsUser : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Returns the number of blogs stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching blogs stats users
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "blogsStatsUser.userId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			BlogsStatsUserModelImpl.GROUPID_COLUMN_BITMASK |
			BlogsStatsUserModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the blogs stats user where groupId = &#63; and userId = &#63; or throws a {@link NoSuchStatsUserException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByG_U(groupId, userId);

		if (blogsStatsUser == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatsUserException(msg.toString());
		}

		return blogsStatsUser;
	}

	/**
	 * Returns the blogs stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByG_U(long groupId, long userId) {
		return fetchByG_U(groupId, userId, true);
	}

	/**
	 * Returns the blogs stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result instanceof BlogsStatsUser) {
			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)result;

			if ((groupId != blogsStatsUser.getGroupId()) ||
					(userId != blogsStatsUser.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<BlogsStatsUser> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_U, finderArgs,
						list);
				}
				else {
					BlogsStatsUser blogsStatsUser = list.get(0);

					result = blogsStatsUser;

					cacheResult(blogsStatsUser);

					if ((blogsStatsUser.getGroupId() != groupId) ||
							(blogsStatsUser.getUserId() != userId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, blogsStatsUser);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U, finderArgs);

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
			return (BlogsStatsUser)result;
		}
	}

	/**
	 * Removes the blogs stats user where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the blogs stats user that was removed
	 */
	@Override
	public BlogsStatsUser removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = findByG_U(groupId, userId);

		return remove(blogsStatsUser);
	}

	/**
	 * Returns the number of blogs stats users where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching blogs stats users
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U;

		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "blogsStatsUser.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_NotE",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotE",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount) {
		return findByG_NotE(groupId, entryCount, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount,
		int start, int end) {
		return findByG_NotE(groupId, entryCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount,
		int start, int end, OrderByComparator<BlogsStatsUser> orderByComparator) {
		return findByG_NotE(groupId, entryCount, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount,
		int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_NOTE;
		finderArgs = new Object[] {
				groupId, entryCount,
				
				start, end, orderByComparator
			};

		List<BlogsStatsUser> list = null;

		if (retrieveFromCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if ((groupId != blogsStatsUser.getGroupId()) ||
							(entryCount == blogsStatsUser.getEntryCount())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				if (!pagination) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
	 * Returns the first blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByG_NotE_First(long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByG_NotE_First(groupId,
				entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByG_NotE_First(long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		List<BlogsStatsUser> list = findByG_NotE(groupId, entryCount, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByG_NotE_Last(long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByG_NotE_Last(groupId, entryCount,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByG_NotE_Last(long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		int count = countByG_NotE(groupId, entryCount);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByG_NotE(groupId, entryCount,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser[] findByG_NotE_PrevAndNext(long statsUserId,
		long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByG_NotE_PrevAndNext(session, blogsStatsUser,
					groupId, entryCount, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByG_NotE_PrevAndNext(session, blogsStatsUser,
					groupId, entryCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByG_NotE_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

		query.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(entryCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs stats users where groupId = &#63; and entryCount &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 */
	@Override
	public void removeByG_NotE(long groupId, int entryCount) {
		for (BlogsStatsUser blogsStatsUser : findByG_NotE(groupId, entryCount,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Returns the number of blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @return the number of matching blogs stats users
	 */
	@Override
	public int countByG_NotE(long groupId, int entryCount) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_NOTE;

		Object[] finderArgs = new Object[] { groupId, entryCount };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

			query.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

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

	private static final String _FINDER_COLUMN_G_NOTE_GROUPID_2 = "blogsStatsUser.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_NotE",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTE = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotE",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount) {
		return findByC_NotE(companyId, entryCount, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount,
		int start, int end) {
		return findByC_NotE(companyId, entryCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount,
		int start, int end, OrderByComparator<BlogsStatsUser> orderByComparator) {
		return findByC_NotE(companyId, entryCount, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount,
		int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTE;
		finderArgs = new Object[] {
				companyId, entryCount,
				
				start, end, orderByComparator
			};

		List<BlogsStatsUser> list = null;

		if (retrieveFromCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if ((companyId != blogsStatsUser.getCompanyId()) ||
							(entryCount == blogsStatsUser.getEntryCount())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				if (!pagination) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
	 * Returns the first blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByC_NotE_First(long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByC_NotE_First(companyId,
				entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByC_NotE_First(long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		List<BlogsStatsUser> list = findByC_NotE(companyId, entryCount, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByC_NotE_Last(long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByC_NotE_Last(companyId,
				entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", entryCount=");
		msg.append(entryCount);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByC_NotE_Last(long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		int count = countByC_NotE(companyId, entryCount);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByC_NotE(companyId, entryCount,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser[] findByC_NotE_PrevAndNext(long statsUserId,
		long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByC_NotE_PrevAndNext(session, blogsStatsUser,
					companyId, entryCount, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByC_NotE_PrevAndNext(session, blogsStatsUser,
					companyId, entryCount, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByC_NotE_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(entryCount);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs stats users where companyId = &#63; and entryCount &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 */
	@Override
	public void removeByC_NotE(long companyId, int entryCount) {
		for (BlogsStatsUser blogsStatsUser : findByC_NotE(companyId,
				entryCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Returns the number of blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @return the number of matching blogs stats users
	 */
	@Override
	public int countByC_NotE(long companyId, int entryCount) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTE;

		Object[] finderArgs = new Object[] { companyId, entryCount };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

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

	private static final String _FINDER_COLUMN_C_NOTE_COMPANYID_2 = "blogsStatsUser.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2 = "blogsStatsUser.entryCount != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_U_L = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_L",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED,
			BlogsStatsUserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_L",
			new String[] { Long.class.getName(), Date.class.getName() },
			BlogsStatsUserModelImpl.USERID_COLUMN_BITMASK |
			BlogsStatsUserModelImpl.LASTPOSTDATE_COLUMN_BITMASK |
			BlogsStatsUserModelImpl.ENTRYCOUNT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_L = new FinderPath(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_L",
			new String[] { Long.class.getName(), Date.class.getName() });

	/**
	 * Returns all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate) {
		return findByU_L(userId, lastPostDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate,
		int start, int end) {
		return findByU_L(userId, lastPostDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate,
		int start, int end, OrderByComparator<BlogsStatsUser> orderByComparator) {
		return findByU_L(userId, lastPostDate, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate,
		int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L;
			finderArgs = new Object[] { userId, lastPostDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_U_L;
			finderArgs = new Object[] {
					userId, lastPostDate,
					
					start, end, orderByComparator
				};
		}

		List<BlogsStatsUser> list = null;

		if (retrieveFromCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if ((userId != blogsStatsUser.getUserId()) ||
							!Validator.equals(lastPostDate,
								blogsStatsUser.getLastPostDate())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_U_L_USERID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindLastPostDate) {
					qPos.add(new Timestamp(lastPostDate.getTime()));
				}

				if (!pagination) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
	 * Returns the first blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByU_L_First(long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByU_L_First(userId, lastPostDate,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", lastPostDate=");
		msg.append(lastPostDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByU_L_First(long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		List<BlogsStatsUser> list = findByU_L(userId, lastPostDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user
	 * @throws NoSuchStatsUserException if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser findByU_L_Last(long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByU_L_Last(userId, lastPostDate,
				orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", lastPostDate=");
		msg.append(lastPostDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStatsUserException(msg.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByU_L_Last(long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		int count = countByU_L(userId, lastPostDate);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByU_L(userId, lastPostDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the blogs stats users before and after the current blogs stats user in the ordered set where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param statsUserId the primary key of the current blogs stats user
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next blogs stats user
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser[] findByU_L_PrevAndNext(long statsUserId,
		long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByU_L_PrevAndNext(session, blogsStatsUser, userId,
					lastPostDate, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByU_L_PrevAndNext(session, blogsStatsUser, userId,
					lastPostDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByU_L_PrevAndNext(Session session,
		BlogsStatsUser blogsStatsUser, long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		query.append(_FINDER_COLUMN_U_L_USERID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
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
			query.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (bindLastPostDate) {
			qPos.add(new Timestamp(lastPostDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(blogsStatsUser);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BlogsStatsUser> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the blogs stats users where userId = &#63; and lastPostDate = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 */
	@Override
	public void removeByU_L(long userId, Date lastPostDate) {
		for (BlogsStatsUser blogsStatsUser : findByU_L(userId, lastPostDate,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Returns the number of blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @return the number of matching blogs stats users
	 */
	@Override
	public int countByU_L(long userId, Date lastPostDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_L;

		Object[] finderArgs = new Object[] { userId, lastPostDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			query.append(_FINDER_COLUMN_U_L_USERID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				query.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindLastPostDate) {
					qPos.add(new Timestamp(lastPostDate.getTime()));
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

	private static final String _FINDER_COLUMN_U_L_USERID_2 = "blogsStatsUser.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_L_LASTPOSTDATE_1 = "blogsStatsUser.lastPostDate IS NULL";
	private static final String _FINDER_COLUMN_U_L_LASTPOSTDATE_2 = "blogsStatsUser.lastPostDate = ?";

	public BlogsStatsUserPersistenceImpl() {
		setModelClass(BlogsStatsUser.class);
	}

	/**
	 * Caches the blogs stats user in the entity cache if it is enabled.
	 *
	 * @param blogsStatsUser the blogs stats user
	 */
	@Override
	public void cacheResult(BlogsStatsUser blogsStatsUser) {
		entityCache.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] { blogsStatsUser.getGroupId(), blogsStatsUser.getUserId() },
			blogsStatsUser);

		blogsStatsUser.resetOriginalValues();
	}

	/**
	 * Caches the blogs stats users in the entity cache if it is enabled.
	 *
	 * @param blogsStatsUsers the blogs stats users
	 */
	@Override
	public void cacheResult(List<BlogsStatsUser> blogsStatsUsers) {
		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			if (entityCache.getResult(
						BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey()) == null) {
				cacheResult(blogsStatsUser);
			}
			else {
				blogsStatsUser.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all blogs stats users.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BlogsStatsUserImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the blogs stats user.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BlogsStatsUser blogsStatsUser) {
		entityCache.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((BlogsStatsUserModelImpl)blogsStatsUser);
	}

	@Override
	public void clearCache(List<BlogsStatsUser> blogsStatsUsers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			entityCache.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey());

			clearUniqueFindersCache((BlogsStatsUserModelImpl)blogsStatsUser);
		}
	}

	protected void cacheUniqueFindersCache(
		BlogsStatsUserModelImpl blogsStatsUserModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					blogsStatsUserModelImpl.getGroupId(),
					blogsStatsUserModelImpl.getUserId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_G_U, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_G_U, args,
				blogsStatsUserModelImpl);
		}
		else {
			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsStatsUserModelImpl.getGroupId(),
						blogsStatsUserModelImpl.getUserId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_G_U, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_G_U, args,
					blogsStatsUserModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		BlogsStatsUserModelImpl blogsStatsUserModelImpl) {
		Object[] args = new Object[] {
				blogsStatsUserModelImpl.getGroupId(),
				blogsStatsUserModelImpl.getUserId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U, args);

		if ((blogsStatsUserModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_U.getColumnBitmask()) != 0) {
			args = new Object[] {
					blogsStatsUserModelImpl.getOriginalGroupId(),
					blogsStatsUserModelImpl.getOriginalUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U, args);
		}
	}

	/**
	 * Creates a new blogs stats user with the primary key. Does not add the blogs stats user to the database.
	 *
	 * @param statsUserId the primary key for the new blogs stats user
	 * @return the new blogs stats user
	 */
	@Override
	public BlogsStatsUser create(long statsUserId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setNew(true);
		blogsStatsUser.setPrimaryKey(statsUserId);

		return blogsStatsUser;
	}

	/**
	 * Removes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user that was removed
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException {
		return remove((Serializable)statsUserId);
	}

	/**
	 * Removes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the blogs stats user
	 * @return the blogs stats user that was removed
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser remove(Serializable primaryKey)
		throws NoSuchStatsUserException {
		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
					primaryKey);

			if (blogsStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(blogsStatsUser);
		}
		catch (NoSuchStatsUserException nsee) {
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
	protected BlogsStatsUser removeImpl(BlogsStatsUser blogsStatsUser) {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(blogsStatsUser)) {
				blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
						blogsStatsUser.getPrimaryKeyObj());
			}

			if (blogsStatsUser != null) {
				session.delete(blogsStatsUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (blogsStatsUser != null) {
			clearCache(blogsStatsUser);
		}

		return blogsStatsUser;
	}

	@Override
	public BlogsStatsUser updateImpl(BlogsStatsUser blogsStatsUser) {
		blogsStatsUser = toUnwrappedModel(blogsStatsUser);

		boolean isNew = blogsStatsUser.isNew();

		BlogsStatsUserModelImpl blogsStatsUserModelImpl = (BlogsStatsUserModelImpl)blogsStatsUser;

		Session session = null;

		try {
			session = openSession();

			if (blogsStatsUser.isNew()) {
				session.save(blogsStatsUser);

				blogsStatsUser.setNew(false);
			}
			else {
				blogsStatsUser = (BlogsStatsUser)session.merge(blogsStatsUser);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !BlogsStatsUserModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsStatsUserModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { blogsStatsUserModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsStatsUserModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { blogsStatsUserModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((blogsStatsUserModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						blogsStatsUserModelImpl.getOriginalUserId(),
						blogsStatsUserModelImpl.getOriginalLastPostDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L,
					args);

				args = new Object[] {
						blogsStatsUserModelImpl.getUserId(),
						blogsStatsUserModelImpl.getLastPostDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_U_L,
					args);
			}
		}

		entityCache.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser, false);

		clearUniqueFindersCache(blogsStatsUserModelImpl);
		cacheUniqueFindersCache(blogsStatsUserModelImpl, isNew);

		blogsStatsUser.resetOriginalValues();

		return blogsStatsUser;
	}

	protected BlogsStatsUser toUnwrappedModel(BlogsStatsUser blogsStatsUser) {
		if (blogsStatsUser instanceof BlogsStatsUserImpl) {
			return blogsStatsUser;
		}

		BlogsStatsUserImpl blogsStatsUserImpl = new BlogsStatsUserImpl();

		blogsStatsUserImpl.setNew(blogsStatsUser.isNew());
		blogsStatsUserImpl.setPrimaryKey(blogsStatsUser.getPrimaryKey());

		blogsStatsUserImpl.setStatsUserId(blogsStatsUser.getStatsUserId());
		blogsStatsUserImpl.setGroupId(blogsStatsUser.getGroupId());
		blogsStatsUserImpl.setCompanyId(blogsStatsUser.getCompanyId());
		blogsStatsUserImpl.setUserId(blogsStatsUser.getUserId());
		blogsStatsUserImpl.setEntryCount(blogsStatsUser.getEntryCount());
		blogsStatsUserImpl.setLastPostDate(blogsStatsUser.getLastPostDate());
		blogsStatsUserImpl.setRatingsTotalEntries(blogsStatsUser.getRatingsTotalEntries());
		blogsStatsUserImpl.setRatingsTotalScore(blogsStatsUser.getRatingsTotalScore());
		blogsStatsUserImpl.setRatingsAverageScore(blogsStatsUser.getRatingsAverageScore());

		return blogsStatsUserImpl;
	}

	/**
	 * Returns the blogs stats user with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs stats user
	 * @return the blogs stats user
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStatsUserException {
		BlogsStatsUser blogsStatsUser = fetchByPrimaryKey(primaryKey);

		if (blogsStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStatsUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return blogsStatsUser;
	}

	/**
	 * Returns the blogs stats user with the primary key or throws a {@link NoSuchStatsUserException} if it could not be found.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user
	 * @throws NoSuchStatsUserException if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException {
		return findByPrimaryKey((Serializable)statsUserId);
	}

	/**
	 * Returns the blogs stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the blogs stats user
	 * @return the blogs stats user, or <code>null</code> if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser fetchByPrimaryKey(Serializable primaryKey) {
		BlogsStatsUser blogsStatsUser = (BlogsStatsUser)entityCache.getResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
				BlogsStatsUserImpl.class, primaryKey);

		if (blogsStatsUser == _nullBlogsStatsUser) {
			return null;
		}

		if (blogsStatsUser == null) {
			Session session = null;

			try {
				session = openSession();

				blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
						primaryKey);

				if (blogsStatsUser != null) {
					cacheResult(blogsStatsUser);
				}
				else {
					entityCache.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
						BlogsStatsUserImpl.class, primaryKey,
						_nullBlogsStatsUser);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
					BlogsStatsUserImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return blogsStatsUser;
	}

	/**
	 * Returns the blogs stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user, or <code>null</code> if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser fetchByPrimaryKey(long statsUserId) {
		return fetchByPrimaryKey((Serializable)statsUserId);
	}

	@Override
	public Map<Serializable, BlogsStatsUser> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BlogsStatsUser> map = new HashMap<Serializable, BlogsStatsUser>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BlogsStatsUser blogsStatsUser = fetchByPrimaryKey(primaryKey);

			if (blogsStatsUser != null) {
				map.put(primaryKey, blogsStatsUser);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)entityCache.getResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
					BlogsStatsUserImpl.class, primaryKey);

			if (blogsStatsUser == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, blogsStatsUser);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE_PKS_IN);

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

			for (BlogsStatsUser blogsStatsUser : (List<BlogsStatsUser>)q.list()) {
				map.put(blogsStatsUser.getPrimaryKeyObj(), blogsStatsUser);

				cacheResult(blogsStatsUser);

				uncachedPrimaryKeys.remove(blogsStatsUser.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(BlogsStatsUserModelImpl.ENTITY_CACHE_ENABLED,
					BlogsStatsUserImpl.class, primaryKey, _nullBlogsStatsUser);
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
	 * Returns all the blogs stats users.
	 *
	 * @return the blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findAll(int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BlogsStatsUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findAll(int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
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

		List<BlogsStatsUser> list = null;

		if (retrieveFromCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BLOGSSTATSUSER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BLOGSSTATSUSER;

				if (pagination) {
					sql = sql.concat(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
	 * Removes all the blogs stats users from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BlogsStatsUser blogsStatsUser : findAll()) {
			remove(blogsStatsUser);
		}
	}

	/**
	 * Returns the number of blogs stats users.
	 *
	 * @return the number of blogs stats users
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BLOGSSTATSUSER);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return BlogsStatsUserModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the blogs stats user persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(BlogsStatsUserImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_BLOGSSTATSUSER = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_SELECT_BLOGSSTATSUSER_WHERE_PKS_IN = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser WHERE statsUserId IN (";
	private static final String _SQL_SELECT_BLOGSSTATSUSER_WHERE = "SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static final String _SQL_COUNT_BLOGSSTATSUSER = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser";
	private static final String _SQL_COUNT_BLOGSSTATSUSER_WHERE = "SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "blogsStatsUser.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BlogsStatsUser exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BlogsStatsUser exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(BlogsStatsUserPersistenceImpl.class);
	private static final BlogsStatsUser _nullBlogsStatsUser = new BlogsStatsUserImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<BlogsStatsUser> toCacheModel() {
				return _nullBlogsStatsUserCacheModel;
			}
		};

	private static final CacheModel<BlogsStatsUser> _nullBlogsStatsUserCacheModel =
		new CacheModel<BlogsStatsUser>() {
			@Override
			public BlogsStatsUser toEntityModel() {
				return _nullBlogsStatsUser;
			}
		};
}