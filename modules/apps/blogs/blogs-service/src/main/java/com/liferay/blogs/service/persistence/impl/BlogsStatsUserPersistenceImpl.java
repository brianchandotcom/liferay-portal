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

package com.liferay.blogs.service.persistence.impl;

import com.liferay.blogs.exception.NoSuchStatsUserException;
import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.model.BlogsStatsUserTable;
import com.liferay.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.blogs.model.impl.BlogsStatsUserModelImpl;
import com.liferay.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.blogs.service.persistence.impl.constants.BlogsPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the blogs stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {BlogsStatsUserPersistence.class, BasePersistence.class})
public class BlogsStatsUserPersistenceImpl
	extends BasePersistenceImpl<BlogsStatsUser>
	implements BlogsStatsUserPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BlogsStatsUserUtil</code> to access the blogs stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BlogsStatsUserImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the blogs stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<BlogsStatsUser> list = null;

		if (useFinderCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if (groupId != blogsStatsUser.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<BlogsStatsUser>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public BlogsStatsUser findByGroupId_First(
			long groupId, OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByGroupId_First(
			groupId, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByGroupId_First(
		long groupId, OrderByComparator<BlogsStatsUser> orderByComparator) {

		List<BlogsStatsUser> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

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
	public BlogsStatsUser findByGroupId_Last(
			long groupId, OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByGroupId_Last(
		long groupId, OrderByComparator<BlogsStatsUser> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

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
	public BlogsStatsUser[] findByGroupId_PrevAndNext(
			long statsUserId, long groupId,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, blogsStatsUser, groupId, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByGroupId_PrevAndNext(
				session, blogsStatsUser, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByGroupId_PrevAndNext(
		Session session, BlogsStatsUser blogsStatsUser, long groupId,
		OrderByComparator<BlogsStatsUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						blogsStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BlogsStatsUser> list = query.list();

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
		for (BlogsStatsUser blogsStatsUser :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"blogsStatsUser.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<BlogsStatsUser> list = null;

		if (useFinderCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if (userId != blogsStatsUser.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<BlogsStatsUser>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public BlogsStatsUser findByUserId_First(
			long userId, OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByUserId_First(
			userId, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the first blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByUserId_First(
		long userId, OrderByComparator<BlogsStatsUser> orderByComparator) {

		List<BlogsStatsUser> list = findByUserId(
			userId, 0, 1, orderByComparator);

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
	public BlogsStatsUser findByUserId_Last(
			long userId, OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByUserId_Last(
			userId, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the last blogs stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByUserId_Last(
		long userId, OrderByComparator<BlogsStatsUser> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByUserId(
			userId, count - 1, count, orderByComparator);

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
	public BlogsStatsUser[] findByUserId_PrevAndNext(
			long statsUserId, long userId,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, blogsStatsUser, userId, orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByUserId_PrevAndNext(
				session, blogsStatsUser, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByUserId_PrevAndNext(
		Session session, BlogsStatsUser blogsStatsUser, long userId,
		OrderByComparator<BlogsStatsUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						blogsStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BlogsStatsUser> list = query.list();

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
		for (BlogsStatsUser blogsStatsUser :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"blogsStatsUser.userId = ?";

	private FinderPath _finderPathFetchByG_U;
	private FinderPath _finderPathCountByG_U;

	/**
	 * Returns the blogs stats user where groupId = &#63; and userId = &#63; or throws a <code>NoSuchStatsUserException</code> if it could not be found.
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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStatsUserException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching blogs stats user, or <code>null</code> if a matching blogs stats user could not be found
	 */
	@Override
	public BlogsStatsUser fetchByG_U(
		long groupId, long userId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByG_U, finderArgs);
		}

		if (result instanceof BlogsStatsUser) {
			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)result;

			if ((groupId != blogsStatsUser.getGroupId()) ||
				(userId != blogsStatsUser.getUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				List<BlogsStatsUser> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_U, finderArgs, list);
					}
				}
				else {
					BlogsStatsUser blogsStatsUser = list.get(0);

					result = blogsStatsUser;

					cacheResult(blogsStatsUser);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		FinderPath finderPath = _finderPathCountByG_U;

		Object[] finderArgs = new Object[] {groupId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 =
		"blogsStatsUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_USERID_2 =
		"blogsStatsUser.userId = ?";

	private FinderPath _finderPathWithPaginationFindByG_NotE;
	private FinderPath _finderPathWithPaginationCountByG_NotE;

	/**
	 * Returns all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByG_NotE(long groupId, int entryCount) {
		return findByG_NotE(
			groupId, entryCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByG_NotE(
		long groupId, int entryCount, int start, int end) {

		return findByG_NotE(groupId, entryCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
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
	public List<BlogsStatsUser> findByG_NotE(
		long groupId, int entryCount, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return findByG_NotE(
			groupId, entryCount, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where groupId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByG_NotE(
		long groupId, int entryCount, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_NotE;
		finderArgs = new Object[] {
			groupId, entryCount, start, end, orderByComparator
		};

		List<BlogsStatsUser> list = null;

		if (useFinderCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public BlogsStatsUser findByG_NotE_First(
			long groupId, int entryCount,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByG_NotE_First(
			groupId, entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", entryCount!=");
		sb.append(entryCount);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
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
	public BlogsStatsUser fetchByG_NotE_First(
		long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		List<BlogsStatsUser> list = findByG_NotE(
			groupId, entryCount, 0, 1, orderByComparator);

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
	public BlogsStatsUser findByG_NotE_Last(
			long groupId, int entryCount,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByG_NotE_Last(
			groupId, entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", entryCount!=");
		sb.append(entryCount);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
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
	public BlogsStatsUser fetchByG_NotE_Last(
		long groupId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		int count = countByG_NotE(groupId, entryCount);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByG_NotE(
			groupId, entryCount, count - 1, count, orderByComparator);

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
	public BlogsStatsUser[] findByG_NotE_PrevAndNext(
			long statsUserId, long groupId, int entryCount,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByG_NotE_PrevAndNext(
				session, blogsStatsUser, groupId, entryCount, orderByComparator,
				true);

			array[1] = blogsStatsUser;

			array[2] = getByG_NotE_PrevAndNext(
				session, blogsStatsUser, groupId, entryCount, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByG_NotE_PrevAndNext(
		Session session, BlogsStatsUser blogsStatsUser, long groupId,
		int entryCount, OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(entryCount);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						blogsStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BlogsStatsUser> list = query.list();

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
		for (BlogsStatsUser blogsStatsUser :
				findByG_NotE(
					groupId, entryCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathWithPaginationCountByG_NotE;

		Object[] finderArgs = new Object[] {groupId, entryCount};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTE_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(entryCount);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_NOTE_GROUPID_2 =
		"blogsStatsUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_NOTE_ENTRYCOUNT_2 =
		"blogsStatsUser.entryCount != ?";

	private FinderPath _finderPathWithPaginationFindByC_NotE;
	private FinderPath _finderPathWithPaginationCountByC_NotE;

	/**
	 * Returns all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByC_NotE(long companyId, int entryCount) {
		return findByC_NotE(
			companyId, entryCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByC_NotE(
		long companyId, int entryCount, int start, int end) {

		return findByC_NotE(companyId, entryCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
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
	public List<BlogsStatsUser> findByC_NotE(
		long companyId, int entryCount, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return findByC_NotE(
			companyId, entryCount, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where companyId = &#63; and entryCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param entryCount the entry count
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByC_NotE(
		long companyId, int entryCount, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_NotE;
		finderArgs = new Object[] {
			companyId, entryCount, start, end, orderByComparator
		};

		List<BlogsStatsUser> list = null;

		if (useFinderCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(entryCount);

				list = (List<BlogsStatsUser>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public BlogsStatsUser findByC_NotE_First(
			long companyId, int entryCount,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByC_NotE_First(
			companyId, entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", entryCount!=");
		sb.append(entryCount);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
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
	public BlogsStatsUser fetchByC_NotE_First(
		long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		List<BlogsStatsUser> list = findByC_NotE(
			companyId, entryCount, 0, 1, orderByComparator);

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
	public BlogsStatsUser findByC_NotE_Last(
			long companyId, int entryCount,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByC_NotE_Last(
			companyId, entryCount, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", entryCount!=");
		sb.append(entryCount);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
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
	public BlogsStatsUser fetchByC_NotE_Last(
		long companyId, int entryCount,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		int count = countByC_NotE(companyId, entryCount);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByC_NotE(
			companyId, entryCount, count - 1, count, orderByComparator);

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
	public BlogsStatsUser[] findByC_NotE_PrevAndNext(
			long statsUserId, long companyId, int entryCount,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByC_NotE_PrevAndNext(
				session, blogsStatsUser, companyId, entryCount,
				orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByC_NotE_PrevAndNext(
				session, blogsStatsUser, companyId, entryCount,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByC_NotE_PrevAndNext(
		Session session, BlogsStatsUser blogsStatsUser, long companyId,
		int entryCount, OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		sb.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(entryCount);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						blogsStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BlogsStatsUser> list = query.list();

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
		for (BlogsStatsUser blogsStatsUser :
				findByC_NotE(
					companyId, entryCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathWithPaginationCountByC_NotE;

		Object[] finderArgs = new Object[] {companyId, entryCount};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_C_NOTE_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(entryCount);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_NOTE_COMPANYID_2 =
		"blogsStatsUser.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_NOTE_ENTRYCOUNT_2 =
		"blogsStatsUser.entryCount != ?";

	private FinderPath _finderPathWithPaginationFindByU_L;
	private FinderPath _finderPathWithoutPaginationFindByU_L;
	private FinderPath _finderPathCountByU_L;

	/**
	 * Returns all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @return the matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByU_L(long userId, Date lastPostDate) {
		return findByU_L(
			userId, lastPostDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @return the range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByU_L(
		long userId, Date lastPostDate, int start, int end) {

		return findByU_L(userId, lastPostDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
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
	public List<BlogsStatsUser> findByU_L(
		long userId, Date lastPostDate, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return findByU_L(
			userId, lastPostDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users where userId = &#63; and lastPostDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param lastPostDate the last post date
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findByU_L(
		long userId, Date lastPostDate, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_L;
				finderArgs = new Object[] {userId, _getTime(lastPostDate)};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_L;
			finderArgs = new Object[] {
				userId, _getTime(lastPostDate), start, end, orderByComparator
			};
		}

		List<BlogsStatsUser> list = null;

		if (useFinderCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BlogsStatsUser blogsStatsUser : list) {
					if ((userId != blogsStatsUser.getUserId()) ||
						!Objects.equals(
							lastPostDate, blogsStatsUser.getLastPostDate())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_U_L_USERID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				sb.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				sb.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindLastPostDate) {
					queryPos.add(new Timestamp(lastPostDate.getTime()));
				}

				list = (List<BlogsStatsUser>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	public BlogsStatsUser findByU_L_First(
			long userId, Date lastPostDate,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByU_L_First(
			userId, lastPostDate, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", lastPostDate=");
		sb.append(lastPostDate);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
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
	public BlogsStatsUser fetchByU_L_First(
		long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		List<BlogsStatsUser> list = findByU_L(
			userId, lastPostDate, 0, 1, orderByComparator);

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
	public BlogsStatsUser findByU_L_Last(
			long userId, Date lastPostDate,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = fetchByU_L_Last(
			userId, lastPostDate, orderByComparator);

		if (blogsStatsUser != null) {
			return blogsStatsUser;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", lastPostDate=");
		sb.append(lastPostDate);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
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
	public BlogsStatsUser fetchByU_L_Last(
		long userId, Date lastPostDate,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		int count = countByU_L(userId, lastPostDate);

		if (count == 0) {
			return null;
		}

		List<BlogsStatsUser> list = findByU_L(
			userId, lastPostDate, count - 1, count, orderByComparator);

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
	public BlogsStatsUser[] findByU_L_PrevAndNext(
			long statsUserId, long userId, Date lastPostDate,
			OrderByComparator<BlogsStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = getByU_L_PrevAndNext(
				session, blogsStatsUser, userId, lastPostDate,
				orderByComparator, true);

			array[1] = blogsStatsUser;

			array[2] = getByU_L_PrevAndNext(
				session, blogsStatsUser, userId, lastPostDate,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BlogsStatsUser getByU_L_PrevAndNext(
		Session session, BlogsStatsUser blogsStatsUser, long userId,
		Date lastPostDate, OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_BLOGSSTATSUSER_WHERE);

		sb.append(_FINDER_COLUMN_U_L_USERID_2);

		boolean bindLastPostDate = false;

		if (lastPostDate == null) {
			sb.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
		}
		else {
			bindLastPostDate = true;

			sb.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (bindLastPostDate) {
			queryPos.add(new Timestamp(lastPostDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						blogsStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BlogsStatsUser> list = query.list();

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
		for (BlogsStatsUser blogsStatsUser :
				findByU_L(
					userId, lastPostDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		FinderPath finderPath = _finderPathCountByU_L;

		Object[] finderArgs = new Object[] {userId, _getTime(lastPostDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BLOGSSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_U_L_USERID_2);

			boolean bindLastPostDate = false;

			if (lastPostDate == null) {
				sb.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_1);
			}
			else {
				bindLastPostDate = true;

				sb.append(_FINDER_COLUMN_U_L_LASTPOSTDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindLastPostDate) {
					queryPos.add(new Timestamp(lastPostDate.getTime()));
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_U_L_USERID_2 =
		"blogsStatsUser.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_L_LASTPOSTDATE_1 =
		"blogsStatsUser.lastPostDate IS NULL";

	private static final String _FINDER_COLUMN_U_L_LASTPOSTDATE_2 =
		"blogsStatsUser.lastPostDate = ?";

	public BlogsStatsUserPersistenceImpl() {
		setModelClass(BlogsStatsUser.class);

		setModelImplClass(BlogsStatsUserImpl.class);
		setModelPKClass(long.class);

		setTable(BlogsStatsUserTable.INSTANCE);
	}

	/**
	 * Caches the blogs stats user in the entity cache if it is enabled.
	 *
	 * @param blogsStatsUser the blogs stats user
	 */
	@Override
	public void cacheResult(BlogsStatsUser blogsStatsUser) {
		entityCache.putResult(
			BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey(),
			blogsStatsUser);

		finderCache.putResult(
			_finderPathFetchByG_U,
			new Object[] {
				blogsStatsUser.getGroupId(), blogsStatsUser.getUserId()
			},
			blogsStatsUser);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the blogs stats users in the entity cache if it is enabled.
	 *
	 * @param blogsStatsUsers the blogs stats users
	 */
	@Override
	public void cacheResult(List<BlogsStatsUser> blogsStatsUsers) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (blogsStatsUsers.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			if (entityCache.getResult(
					BlogsStatsUserImpl.class, blogsStatsUser.getPrimaryKey()) ==
						null) {

				cacheResult(blogsStatsUser);
			}
		}
	}

	/**
	 * Clears the cache for all blogs stats users.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BlogsStatsUserImpl.class);

		finderCache.clearCache(BlogsStatsUserImpl.class);
	}

	/**
	 * Clears the cache for the blogs stats user.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BlogsStatsUser blogsStatsUser) {
		entityCache.removeResult(BlogsStatsUserImpl.class, blogsStatsUser);
	}

	@Override
	public void clearCache(List<BlogsStatsUser> blogsStatsUsers) {
		for (BlogsStatsUser blogsStatsUser : blogsStatsUsers) {
			entityCache.removeResult(BlogsStatsUserImpl.class, blogsStatsUser);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BlogsStatsUserImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(BlogsStatsUserImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		BlogsStatsUserModelImpl blogsStatsUserModelImpl) {

		Object[] args = new Object[] {
			blogsStatsUserModelImpl.getGroupId(),
			blogsStatsUserModelImpl.getUserId()
		};

		finderCache.putResult(_finderPathCountByG_U, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_U, args, blogsStatsUserModelImpl);
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

		blogsStatsUser.setCompanyId(CompanyThreadLocal.getCompanyId());

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

			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)session.get(
				BlogsStatsUserImpl.class, primaryKey);

			if (blogsStatsUser == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatsUserException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(blogsStatsUser);
		}
		catch (NoSuchStatsUserException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected BlogsStatsUser removeImpl(BlogsStatsUser blogsStatsUser) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(blogsStatsUser)) {
				blogsStatsUser = (BlogsStatsUser)session.get(
					BlogsStatsUserImpl.class,
					blogsStatsUser.getPrimaryKeyObj());
			}

			if (blogsStatsUser != null) {
				session.delete(blogsStatsUser);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = blogsStatsUser.isNew();

		if (!(blogsStatsUser instanceof BlogsStatsUserModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(blogsStatsUser.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					blogsStatsUser);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in blogsStatsUser proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BlogsStatsUser implementation " +
					blogsStatsUser.getClass());
		}

		BlogsStatsUserModelImpl blogsStatsUserModelImpl =
			(BlogsStatsUserModelImpl)blogsStatsUser;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(blogsStatsUser);
			}
			else {
				blogsStatsUser = (BlogsStatsUser)session.merge(blogsStatsUser);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BlogsStatsUserImpl.class, blogsStatsUserModelImpl, false, true);

		cacheUniqueFindersCache(blogsStatsUserModelImpl);

		if (isNew) {
			blogsStatsUser.setNew(false);
		}

		blogsStatsUser.resetOriginalValues();

		return blogsStatsUser;
	}

	/**
	 * Returns the blogs stats user with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
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
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStatsUserException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return blogsStatsUser;
	}

	/**
	 * Returns the blogs stats user with the primary key or throws a <code>NoSuchStatsUserException</code> if it could not be found.
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
	 * @param statsUserId the primary key of the blogs stats user
	 * @return the blogs stats user, or <code>null</code> if a blogs stats user with the primary key could not be found
	 */
	@Override
	public BlogsStatsUser fetchByPrimaryKey(long statsUserId) {
		return fetchByPrimaryKey((Serializable)statsUserId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findAll(
		int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the blogs stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BlogsStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of blogs stats users
	 * @param end the upper bound of the range of blogs stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of blogs stats users
	 */
	@Override
	public List<BlogsStatsUser> findAll(
		int start, int end, OrderByComparator<BlogsStatsUser> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<BlogsStatsUser> list = null;

		if (useFinderCache) {
			list = (List<BlogsStatsUser>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BLOGSSTATSUSER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BLOGSSTATSUSER;

				sql = sql.concat(BlogsStatsUserModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BlogsStatsUser>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_BLOGSSTATSUSER);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "statsUserId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BLOGSSTATSUSER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BlogsStatsUserModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the blogs stats user persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathFetchByG_U = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, true);

		_finderPathCountByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "userId"}, false);

		_finderPathWithPaginationFindByG_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_NotE",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "entryCount"}, true);

		_finderPathWithPaginationCountByG_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_NotE",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "entryCount"}, false);

		_finderPathWithPaginationFindByC_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_NotE",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "entryCount"}, true);

		_finderPathWithPaginationCountByC_NotE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotE",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "entryCount"}, false);

		_finderPathWithPaginationFindByU_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_L",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "lastPostDate"}, true);

		_finderPathWithoutPaginationFindByU_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_L",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"userId", "lastPostDate"}, true);

		_finderPathCountByU_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_L",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"userId", "lastPostDate"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(BlogsStatsUserImpl.class.getName());
	}

	@Override
	@Reference(
		target = BlogsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BlogsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BlogsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_BLOGSSTATSUSER =
		"SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser";

	private static final String _SQL_SELECT_BLOGSSTATSUSER_WHERE =
		"SELECT blogsStatsUser FROM BlogsStatsUser blogsStatsUser WHERE ";

	private static final String _SQL_COUNT_BLOGSSTATSUSER =
		"SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser";

	private static final String _SQL_COUNT_BLOGSSTATSUSER_WHERE =
		"SELECT COUNT(blogsStatsUser) FROM BlogsStatsUser blogsStatsUser WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "blogsStatsUser.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BlogsStatsUser exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BlogsStatsUser exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsStatsUserPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private BlogsStatsUserModelArgumentsResolver
		_blogsStatsUserModelArgumentsResolver;

}