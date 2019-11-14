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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchRecentLayoutSetBranchException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.RecentLayoutSetBranch;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.RecentLayoutSetBranchPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.RecentLayoutSetBranchImpl;
import com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the recent layout set branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RecentLayoutSetBranchPersistenceImpl
	extends BasePersistenceImpl<RecentLayoutSetBranch>
	implements RecentLayoutSetBranchPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RecentLayoutSetBranchUtil</code> to access the recent layout set branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RecentLayoutSetBranchImpl.class.getName();

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
	 * Returns all the recent layout set branchs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @return the range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
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

		List<RecentLayoutSetBranch> list = null;

		if (useFinderCache) {
			list = (List<RecentLayoutSetBranch>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutSetBranch recentLayoutSetBranch : list) {
					if (groupId != recentLayoutSetBranch.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<RecentLayoutSetBranch>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first recent layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByGroupId_First(
			long groupId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = fetchByGroupId_First(
			groupId, orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchRecentLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the first recent layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByGroupId_First(
		long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		List<RecentLayoutSetBranch> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByGroupId_Last(
			long groupId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchRecentLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the last recent layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByGroupId_Last(
		long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutSetBranch> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch[] findByGroupId_PrevAndNext(
			long recentLayoutSetBranchId, long groupId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = findByPrimaryKey(
			recentLayoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutSetBranch[] array = new RecentLayoutSetBranchImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, recentLayoutSetBranch, groupId, orderByComparator,
				true);

			array[1] = recentLayoutSetBranch;

			array[2] = getByGroupId_PrevAndNext(
				session, recentLayoutSetBranch, groupId, orderByComparator,
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

	protected RecentLayoutSetBranch getByGroupId_PrevAndNext(
		Session session, RecentLayoutSetBranch recentLayoutSetBranch,
		long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						recentLayoutSetBranch)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<RecentLayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout set branchs where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (RecentLayoutSetBranch recentLayoutSetBranch :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(recentLayoutSetBranch);
		}
	}

	/**
	 * Returns the number of recent layout set branchs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching recent layout set branchs
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTSETBRANCH_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"recentLayoutSetBranch.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the recent layout set branchs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout set branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @return the range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByUserId(
		long userId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByUserId(
		long userId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
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

		List<RecentLayoutSetBranch> list = null;

		if (useFinderCache) {
			list = (List<RecentLayoutSetBranch>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutSetBranch recentLayoutSetBranch : list) {
					if (userId != recentLayoutSetBranch.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<RecentLayoutSetBranch>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first recent layout set branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByUserId_First(
			long userId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = fetchByUserId_First(
			userId, orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchRecentLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the first recent layout set branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByUserId_First(
		long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		List<RecentLayoutSetBranch> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout set branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByUserId_Last(
			long userId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = fetchByUserId_Last(
			userId, orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchRecentLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the last recent layout set branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByUserId_Last(
		long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutSetBranch> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where userId = &#63;.
	 *
	 * @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch[] findByUserId_PrevAndNext(
			long recentLayoutSetBranchId, long userId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = findByPrimaryKey(
			recentLayoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutSetBranch[] array = new RecentLayoutSetBranchImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, recentLayoutSetBranch, userId, orderByComparator,
				true);

			array[1] = recentLayoutSetBranch;

			array[2] = getByUserId_PrevAndNext(
				session, recentLayoutSetBranch, userId, orderByComparator,
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

	protected RecentLayoutSetBranch getByUserId_PrevAndNext(
		Session session, RecentLayoutSetBranch recentLayoutSetBranch,
		long userId, OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						recentLayoutSetBranch)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<RecentLayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout set branchs where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (RecentLayoutSetBranch recentLayoutSetBranch :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(recentLayoutSetBranch);
		}
	}

	/**
	 * Returns the number of recent layout set branchs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching recent layout set branchs
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"recentLayoutSetBranch.userId = ?";

	private FinderPath _finderPathWithPaginationFindByLayoutSetBranchId;
	private FinderPath _finderPathWithoutPaginationFindByLayoutSetBranchId;
	private FinderPath _finderPathCountByLayoutSetBranchId;

	/**
	 * Returns all the recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId) {

		return findByLayoutSetBranchId(
			layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @return the range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end) {

		return findByLayoutSetBranchId(layoutSetBranchId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		return findByLayoutSetBranchId(
			layoutSetBranchId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByLayoutSetBranchId;
				finderArgs = new Object[] {layoutSetBranchId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLayoutSetBranchId;
			finderArgs = new Object[] {
				layoutSetBranchId, start, end, orderByComparator
			};
		}

		List<RecentLayoutSetBranch> list = null;

		if (useFinderCache) {
			list = (List<RecentLayoutSetBranch>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutSetBranch recentLayoutSetBranch : list) {
					if (layoutSetBranchId !=
							recentLayoutSetBranch.getLayoutSetBranchId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				list = (List<RecentLayoutSetBranch>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByLayoutSetBranchId_First(
			long layoutSetBranchId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch =
			fetchByLayoutSetBranchId_First(
				layoutSetBranchId, orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append("}");

		throw new NoSuchRecentLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the first recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		List<RecentLayoutSetBranch> list = findByLayoutSetBranchId(
			layoutSetBranchId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByLayoutSetBranchId_Last(
			long layoutSetBranchId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch =
			fetchByLayoutSetBranchId_Last(layoutSetBranchId, orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append("}");

		throw new NoSuchRecentLayoutSetBranchException(msg.toString());
	}

	/**
	 * Returns the last recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		int count = countByLayoutSetBranchId(layoutSetBranchId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutSetBranch> list = findByLayoutSetBranchId(
			layoutSetBranchId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout set branchs before and after the current recent layout set branch in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param recentLayoutSetBranchId the primary key of the current recent layout set branch
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch[] findByLayoutSetBranchId_PrevAndNext(
			long recentLayoutSetBranchId, long layoutSetBranchId,
			OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = findByPrimaryKey(
			recentLayoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutSetBranch[] array = new RecentLayoutSetBranchImpl[3];

			array[0] = getByLayoutSetBranchId_PrevAndNext(
				session, recentLayoutSetBranch, layoutSetBranchId,
				orderByComparator, true);

			array[1] = recentLayoutSetBranch;

			array[2] = getByLayoutSetBranchId_PrevAndNext(
				session, recentLayoutSetBranch, layoutSetBranchId,
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

	protected RecentLayoutSetBranch getByLayoutSetBranchId_PrevAndNext(
		Session session, RecentLayoutSetBranch recentLayoutSetBranch,
		long layoutSetBranchId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						recentLayoutSetBranch)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<RecentLayoutSetBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout set branchs where layoutSetBranchId = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 */
	@Override
	public void removeByLayoutSetBranchId(long layoutSetBranchId) {
		for (RecentLayoutSetBranch recentLayoutSetBranch :
				findByLayoutSetBranchId(
					layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(recentLayoutSetBranch);
		}
	}

	/**
	 * Returns the number of recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the number of matching recent layout set branchs
	 */
	@Override
	public int countByLayoutSetBranchId(long layoutSetBranchId) {
		FinderPath finderPath = _finderPathCountByLayoutSetBranchId;

		Object[] finderArgs = new Object[] {layoutSetBranchId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

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

	private static final String
		_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2 =
			"recentLayoutSetBranch.layoutSetBranchId = ?";

	private FinderPath _finderPathFetchByU_L;
	private FinderPath _finderPathCountByU_L;

	/**
	 * Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or throws a <code>NoSuchRecentLayoutSetBranchException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param layoutSetId the layout set ID
	 * @return the matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByU_L(long userId, long layoutSetId)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = fetchByU_L(
			userId, layoutSetId);

		if (recentLayoutSetBranch == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", layoutSetId=");
			msg.append(layoutSetId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchRecentLayoutSetBranchException(msg.toString());
		}

		return recentLayoutSetBranch;
	}

	/**
	 * Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param layoutSetId the layout set ID
	 * @return the matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByU_L(long userId, long layoutSetId) {
		return fetchByU_L(userId, layoutSetId, true);
	}

	/**
	 * Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param layoutSetId the layout set ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByU_L(
		long userId, long layoutSetId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, layoutSetId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_L, finderArgs, this);
		}

		if (result instanceof RecentLayoutSetBranch) {
			RecentLayoutSetBranch recentLayoutSetBranch =
				(RecentLayoutSetBranch)result;

			if ((userId != recentLayoutSetBranch.getUserId()) ||
				(layoutSetId != recentLayoutSetBranch.getLayoutSetId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_U_L_USERID_2);

			query.append(_FINDER_COLUMN_U_L_LAYOUTSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(layoutSetId);

				List<RecentLayoutSetBranch> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_L, finderArgs, list);
					}
				}
				else {
					RecentLayoutSetBranch recentLayoutSetBranch = list.get(0);

					result = recentLayoutSetBranch;

					cacheResult(recentLayoutSetBranch);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByU_L, finderArgs);
				}

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
			return (RecentLayoutSetBranch)result;
		}
	}

	/**
	 * Removes the recent layout set branch where userId = &#63; and layoutSetId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param layoutSetId the layout set ID
	 * @return the recent layout set branch that was removed
	 */
	@Override
	public RecentLayoutSetBranch removeByU_L(long userId, long layoutSetId)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = findByU_L(
			userId, layoutSetId);

		return remove(recentLayoutSetBranch);
	}

	/**
	 * Returns the number of recent layout set branchs where userId = &#63; and layoutSetId = &#63;.
	 *
	 * @param userId the user ID
	 * @param layoutSetId the layout set ID
	 * @return the number of matching recent layout set branchs
	 */
	@Override
	public int countByU_L(long userId, long layoutSetId) {
		FinderPath finderPath = _finderPathCountByU_L;

		Object[] finderArgs = new Object[] {userId, layoutSetId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_U_L_USERID_2);

			query.append(_FINDER_COLUMN_U_L_LAYOUTSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(layoutSetId);

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

	private static final String _FINDER_COLUMN_U_L_USERID_2 =
		"recentLayoutSetBranch.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_L_LAYOUTSETID_2 =
		"recentLayoutSetBranch.layoutSetId = ?";

	public RecentLayoutSetBranchPersistenceImpl() {
		setModelClass(RecentLayoutSetBranch.class);

		setModelImplClass(RecentLayoutSetBranchImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the recent layout set branch in the entity cache if it is enabled.
	 *
	 * @param recentLayoutSetBranch the recent layout set branch
	 */
	@Override
	public void cacheResult(RecentLayoutSetBranch recentLayoutSetBranch) {
		EntityCacheUtil.putResult(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			recentLayoutSetBranch.getPrimaryKey(), recentLayoutSetBranch);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_L,
			new Object[] {
				recentLayoutSetBranch.getUserId(),
				recentLayoutSetBranch.getLayoutSetId()
			},
			recentLayoutSetBranch);

		recentLayoutSetBranch.resetOriginalValues();
	}

	/**
	 * Caches the recent layout set branchs in the entity cache if it is enabled.
	 *
	 * @param recentLayoutSetBranchs the recent layout set branchs
	 */
	@Override
	public void cacheResult(
		List<RecentLayoutSetBranch> recentLayoutSetBranchs) {

		for (RecentLayoutSetBranch recentLayoutSetBranch :
				recentLayoutSetBranchs) {

			if (EntityCacheUtil.getResult(
					RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutSetBranchImpl.class,
					recentLayoutSetBranch.getPrimaryKey()) == null) {

				cacheResult(recentLayoutSetBranch);
			}
			else {
				recentLayoutSetBranch.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all recent layout set branchs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(RecentLayoutSetBranchImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the recent layout set branch.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RecentLayoutSetBranch recentLayoutSetBranch) {
		EntityCacheUtil.removeResult(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			recentLayoutSetBranch.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(RecentLayoutSetBranchModelImpl)recentLayoutSetBranch, true);
	}

	@Override
	public void clearCache(List<RecentLayoutSetBranch> recentLayoutSetBranchs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RecentLayoutSetBranch recentLayoutSetBranch :
				recentLayoutSetBranchs) {

			EntityCacheUtil.removeResult(
				RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutSetBranchImpl.class,
				recentLayoutSetBranch.getPrimaryKey());

			clearUniqueFindersCache(
				(RecentLayoutSetBranchModelImpl)recentLayoutSetBranch, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutSetBranchImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl) {

		Object[] args = new Object[] {
			recentLayoutSetBranchModelImpl.getUserId(),
			recentLayoutSetBranchModelImpl.getLayoutSetId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_L, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU_L, args, recentLayoutSetBranchModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				recentLayoutSetBranchModelImpl.getUserId(),
				recentLayoutSetBranchModelImpl.getLayoutSetId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_L, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_L, args);
		}

		if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_L.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				recentLayoutSetBranchModelImpl.getOriginalUserId(),
				recentLayoutSetBranchModelImpl.getOriginalLayoutSetId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_L, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_L, args);
		}
	}

	/**
	 * Creates a new recent layout set branch with the primary key. Does not add the recent layout set branch to the database.
	 *
	 * @param recentLayoutSetBranchId the primary key for the new recent layout set branch
	 * @return the new recent layout set branch
	 */
	@Override
	public RecentLayoutSetBranch create(long recentLayoutSetBranchId) {
		RecentLayoutSetBranch recentLayoutSetBranch =
			new RecentLayoutSetBranchImpl();

		recentLayoutSetBranch.setNew(true);
		recentLayoutSetBranch.setPrimaryKey(recentLayoutSetBranchId);

		recentLayoutSetBranch.setCompanyId(CompanyThreadLocal.getCompanyId());

		return recentLayoutSetBranch;
	}

	/**
	 * Removes the recent layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutSetBranchId the primary key of the recent layout set branch
	 * @return the recent layout set branch that was removed
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch remove(long recentLayoutSetBranchId)
		throws NoSuchRecentLayoutSetBranchException {

		return remove((Serializable)recentLayoutSetBranchId);
	}

	/**
	 * Removes the recent layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the recent layout set branch
	 * @return the recent layout set branch that was removed
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch remove(Serializable primaryKey)
		throws NoSuchRecentLayoutSetBranchException {

		Session session = null;

		try {
			session = openSession();

			RecentLayoutSetBranch recentLayoutSetBranch =
				(RecentLayoutSetBranch)session.get(
					RecentLayoutSetBranchImpl.class, primaryKey);

			if (recentLayoutSetBranch == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecentLayoutSetBranchException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(recentLayoutSetBranch);
		}
		catch (NoSuchRecentLayoutSetBranchException nsee) {
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
	protected RecentLayoutSetBranch removeImpl(
		RecentLayoutSetBranch recentLayoutSetBranch) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(recentLayoutSetBranch)) {
				recentLayoutSetBranch = (RecentLayoutSetBranch)session.get(
					RecentLayoutSetBranchImpl.class,
					recentLayoutSetBranch.getPrimaryKeyObj());
			}

			if (recentLayoutSetBranch != null) {
				session.delete(recentLayoutSetBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (recentLayoutSetBranch != null) {
			clearCache(recentLayoutSetBranch);
		}

		return recentLayoutSetBranch;
	}

	@Override
	public RecentLayoutSetBranch updateImpl(
		RecentLayoutSetBranch recentLayoutSetBranch) {

		boolean isNew = recentLayoutSetBranch.isNew();

		if (!(recentLayoutSetBranch instanceof
				RecentLayoutSetBranchModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(recentLayoutSetBranch.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					recentLayoutSetBranch);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in recentLayoutSetBranch proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RecentLayoutSetBranch implementation " +
					recentLayoutSetBranch.getClass());
		}

		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl =
			(RecentLayoutSetBranchModelImpl)recentLayoutSetBranch;

		Session session = null;

		try {
			session = openSession();

			if (recentLayoutSetBranch.isNew()) {
				session.save(recentLayoutSetBranch);

				recentLayoutSetBranch.setNew(false);
			}
			else {
				recentLayoutSetBranch = (RecentLayoutSetBranch)session.merge(
					recentLayoutSetBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!RecentLayoutSetBranchModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				recentLayoutSetBranchModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {recentLayoutSetBranchModelImpl.getUserId()};

			FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {
				recentLayoutSetBranchModelImpl.getLayoutSetBranchId()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByLayoutSetBranchId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByLayoutSetBranchId, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					recentLayoutSetBranchModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					recentLayoutSetBranchModelImpl.getGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					recentLayoutSetBranchModelImpl.getOriginalUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {
					recentLayoutSetBranchModelImpl.getUserId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUserId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLayoutSetBranchId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					recentLayoutSetBranchModelImpl.
						getOriginalLayoutSetBranchId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutSetBranchId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutSetBranchId, args);

				args = new Object[] {
					recentLayoutSetBranchModelImpl.getLayoutSetBranchId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutSetBranchId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutSetBranchId, args);
			}
		}

		EntityCacheUtil.putResult(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			recentLayoutSetBranch.getPrimaryKey(), recentLayoutSetBranch,
			false);

		clearUniqueFindersCache(recentLayoutSetBranchModelImpl, false);
		cacheUniqueFindersCache(recentLayoutSetBranchModelImpl);

		recentLayoutSetBranch.resetOriginalValues();

		return recentLayoutSetBranch;
	}

	/**
	 * Returns the recent layout set branch with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the recent layout set branch
	 * @return the recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecentLayoutSetBranchException {

		RecentLayoutSetBranch recentLayoutSetBranch = fetchByPrimaryKey(
			primaryKey);

		if (recentLayoutSetBranch == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecentLayoutSetBranchException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return recentLayoutSetBranch;
	}

	/**
	 * Returns the recent layout set branch with the primary key or throws a <code>NoSuchRecentLayoutSetBranchException</code> if it could not be found.
	 *
	 * @param recentLayoutSetBranchId the primary key of the recent layout set branch
	 * @return the recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByPrimaryKey(long recentLayoutSetBranchId)
		throws NoSuchRecentLayoutSetBranchException {

		return findByPrimaryKey((Serializable)recentLayoutSetBranchId);
	}

	/**
	 * Returns the recent layout set branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recentLayoutSetBranchId the primary key of the recent layout set branch
	 * @return the recent layout set branch, or <code>null</code> if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByPrimaryKey(
		long recentLayoutSetBranchId) {

		return fetchByPrimaryKey((Serializable)recentLayoutSetBranchId);
	}

	/**
	 * Returns all the recent layout set branchs.
	 *
	 * @return the recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @return the range of recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findAll(
		int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RecentLayoutSetBranchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findAll(
		int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
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

		List<RecentLayoutSetBranch> list = null;

		if (useFinderCache) {
			list = (List<RecentLayoutSetBranch>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RECENTLAYOUTSETBRANCH;

				sql = sql.concat(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<RecentLayoutSetBranch>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the recent layout set branchs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RecentLayoutSetBranch recentLayoutSetBranch : findAll()) {
			remove(recentLayoutSetBranch);
		}
	}

	/**
	 * Returns the number of recent layout set branchs.
	 *
	 * @return the number of recent layout set branchs
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RECENTLAYOUTSETBRANCH);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "recentLayoutSetBranchId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RECENTLAYOUTSETBRANCH;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RecentLayoutSetBranchModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the recent layout set branch persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			RecentLayoutSetBranchModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			RecentLayoutSetBranchModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByLayoutSetBranchId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutSetBranchId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLayoutSetBranchId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetBranchId", new String[] {Long.class.getName()},
			RecentLayoutSetBranchModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK);

		_finderPathCountByLayoutSetBranchId = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetBranchId", new String[] {Long.class.getName()});

		_finderPathFetchByU_L = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_L",
			new String[] {Long.class.getName(), Long.class.getName()},
			RecentLayoutSetBranchModelImpl.USERID_COLUMN_BITMASK |
			RecentLayoutSetBranchModelImpl.LAYOUTSETID_COLUMN_BITMASK);

		_finderPathCountByU_L = new FinderPath(
			RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_L",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(RecentLayoutSetBranchImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RECENTLAYOUTSETBRANCH =
		"SELECT recentLayoutSetBranch FROM RecentLayoutSetBranch recentLayoutSetBranch";

	private static final String _SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE =
		"SELECT recentLayoutSetBranch FROM RecentLayoutSetBranch recentLayoutSetBranch WHERE ";

	private static final String _SQL_COUNT_RECENTLAYOUTSETBRANCH =
		"SELECT COUNT(recentLayoutSetBranch) FROM RecentLayoutSetBranch recentLayoutSetBranch";

	private static final String _SQL_COUNT_RECENTLAYOUTSETBRANCH_WHERE =
		"SELECT COUNT(recentLayoutSetBranch) FROM RecentLayoutSetBranch recentLayoutSetBranch WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"recentLayoutSetBranch.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RecentLayoutSetBranch exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RecentLayoutSetBranch exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RecentLayoutSetBranchPersistenceImpl.class);

}