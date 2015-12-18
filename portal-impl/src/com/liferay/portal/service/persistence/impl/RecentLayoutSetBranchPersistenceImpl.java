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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchRecentLayoutSetBranchException;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.RecentLayoutSetBranch;
import com.liferay.portal.model.impl.RecentLayoutSetBranchImpl;
import com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.RecentLayoutSetBranchPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * @see RecentLayoutSetBranchPersistence
 * @see com.liferay.portal.service.persistence.RecentLayoutSetBranchUtil
 * @generated
 */
@ProviderType
public class RecentLayoutSetBranchPersistenceImpl extends BasePersistenceImpl<RecentLayoutSetBranch>
	implements RecentLayoutSetBranchPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RecentLayoutSetBranchUtil} to access the recent layout set branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RecentLayoutSetBranchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			RecentLayoutSetBranchModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout set branchs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @return the range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(long groupId, int start,
		int end, OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByGroupId(long groupId, int start,
		int end, OrderByComparator<RecentLayoutSetBranch> orderByComparator,
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

		List<RecentLayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutSetBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutSetBranch recentLayoutSetBranch : list) {
					if ((groupId != recentLayoutSetBranch.getGroupId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
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
	 * Returns the first recent layout set branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByGroupId_First(groupId,
				orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public RecentLayoutSetBranch fetchByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		List<RecentLayoutSetBranch> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

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
	public RecentLayoutSetBranch findByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public RecentLayoutSetBranch fetchByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutSetBranch> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

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
		RecentLayoutSetBranch recentLayoutSetBranch = findByPrimaryKey(recentLayoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutSetBranch[] array = new RecentLayoutSetBranchImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, recentLayoutSetBranch,
					groupId, orderByComparator, true);

			array[1] = recentLayoutSetBranch;

			array[2] = getByGroupId_PrevAndNext(session, recentLayoutSetBranch,
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

	protected RecentLayoutSetBranch getByGroupId_PrevAndNext(Session session,
		RecentLayoutSetBranch recentLayoutSetBranch, long groupId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

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
			query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutSetBranch);

			for (Object value : values) {
				qPos.add(value);
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
		for (RecentLayoutSetBranch recentLayoutSetBranch : findByGroupId(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "recentLayoutSetBranch.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			RecentLayoutSetBranchModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @return the range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByUserId(long userId, int start,
		int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutSetBranch> orderByComparator,
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

		List<RecentLayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutSetBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutSetBranch recentLayoutSetBranch : list) {
					if ((userId != recentLayoutSetBranch.getUserId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
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
	 * Returns the first recent layout set branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByUserId_First(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByUserId_First(userId,
				orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public RecentLayoutSetBranch fetchByUserId_First(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		List<RecentLayoutSetBranch> list = findByUserId(userId, 0, 1,
				orderByComparator);

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
	public RecentLayoutSetBranch findByUserId_Last(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator)
		throws NoSuchRecentLayoutSetBranchException {
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByUserId_Last(userId,
				orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public RecentLayoutSetBranch fetchByUserId_Last(long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutSetBranch> list = findByUserId(userId, count - 1,
				count, orderByComparator);

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
		RecentLayoutSetBranch recentLayoutSetBranch = findByPrimaryKey(recentLayoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutSetBranch[] array = new RecentLayoutSetBranchImpl[3];

			array[0] = getByUserId_PrevAndNext(session, recentLayoutSetBranch,
					userId, orderByComparator, true);

			array[1] = recentLayoutSetBranch;

			array[2] = getByUserId_PrevAndNext(session, recentLayoutSetBranch,
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

	protected RecentLayoutSetBranch getByUserId_PrevAndNext(Session session,
		RecentLayoutSetBranch recentLayoutSetBranch, long userId,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

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
			query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutSetBranch);

			for (Object value : values) {
				qPos.add(value);
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
		for (RecentLayoutSetBranch recentLayoutSetBranch : findByUserId(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "recentLayoutSetBranch.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETBRANCHID =
		new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutSetBranchId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID =
		new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetBranchId", new String[] { Long.class.getName() },
			RecentLayoutSetBranchModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetBranchId", new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId) {
		return findByLayoutSetBranchId(layoutSetBranchId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID;
			finderArgs = new Object[] { layoutSetBranchId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTSETBRANCHID;
			finderArgs = new Object[] {
					layoutSetBranchId,
					
					start, end, orderByComparator
				};
		}

		List<RecentLayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutSetBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutSetBranch recentLayoutSetBranch : list) {
					if ((layoutSetBranchId != recentLayoutSetBranch.getLayoutSetBranchId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				if (!pagination) {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
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
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByLayoutSetBranchId_First(layoutSetBranchId,
				orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
		List<RecentLayoutSetBranch> list = findByLayoutSetBranchId(layoutSetBranchId,
				0, 1, orderByComparator);

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
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByLayoutSetBranchId_Last(layoutSetBranchId,
				orderByComparator);

		if (recentLayoutSetBranch != null) {
			return recentLayoutSetBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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

		List<RecentLayoutSetBranch> list = findByLayoutSetBranchId(layoutSetBranchId,
				count - 1, count, orderByComparator);

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
		RecentLayoutSetBranch recentLayoutSetBranch = findByPrimaryKey(recentLayoutSetBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutSetBranch[] array = new RecentLayoutSetBranchImpl[3];

			array[0] = getByLayoutSetBranchId_PrevAndNext(session,
					recentLayoutSetBranch, layoutSetBranchId,
					orderByComparator, true);

			array[1] = recentLayoutSetBranch;

			array[2] = getByLayoutSetBranchId_PrevAndNext(session,
					recentLayoutSetBranch, layoutSetBranchId,
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
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

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
			query.append(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutSetBranch);

			for (Object value : values) {
				qPos.add(value);
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
		for (RecentLayoutSetBranch recentLayoutSetBranch : findByLayoutSetBranchId(
				layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID;

		Object[] finderArgs = new Object[] { layoutSetBranchId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

	private static final String _FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2 =
		"recentLayoutSetBranch.layoutSetBranchId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_L = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_L",
			new String[] { Long.class.getName(), Long.class.getName() },
			RecentLayoutSetBranchModelImpl.USERID_COLUMN_BITMASK |
			RecentLayoutSetBranchModelImpl.LAYOUTSETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_L = new FinderPath(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_L",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the recent layout set branch where userId = &#63; and layoutSetId = &#63; or throws a {@link NoSuchRecentLayoutSetBranchException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param layoutSetId the layout set ID
	 * @return the matching recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByU_L(long userId, long layoutSetId)
		throws NoSuchRecentLayoutSetBranchException {
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByU_L(userId,
				layoutSetId);

		if (recentLayoutSetBranch == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", layoutSetId=");
			msg.append(layoutSetId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching recent layout set branch, or <code>null</code> if a matching recent layout set branch could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByU_L(long userId, long layoutSetId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId, layoutSetId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_U_L,
					finderArgs, this);
		}

		if (result instanceof RecentLayoutSetBranch) {
			RecentLayoutSetBranch recentLayoutSetBranch = (RecentLayoutSetBranch)result;

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
					finderCache.putResult(FINDER_PATH_FETCH_BY_U_L, finderArgs,
						list);
				}
				else {
					RecentLayoutSetBranch recentLayoutSetBranch = list.get(0);

					result = recentLayoutSetBranch;

					cacheResult(recentLayoutSetBranch);

					if ((recentLayoutSetBranch.getUserId() != userId) ||
							(recentLayoutSetBranch.getLayoutSetId() != layoutSetId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_U_L,
							finderArgs, recentLayoutSetBranch);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L, finderArgs);

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
		RecentLayoutSetBranch recentLayoutSetBranch = findByU_L(userId,
				layoutSetId);

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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_L;

		Object[] finderArgs = new Object[] { userId, layoutSetId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

	private static final String _FINDER_COLUMN_U_L_USERID_2 = "recentLayoutSetBranch.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_L_LAYOUTSETID_2 = "recentLayoutSetBranch.layoutSetId = ?";

	public RecentLayoutSetBranchPersistenceImpl() {
		setModelClass(RecentLayoutSetBranch.class);
	}

	/**
	 * Caches the recent layout set branch in the entity cache if it is enabled.
	 *
	 * @param recentLayoutSetBranch the recent layout set branch
	 */
	@Override
	public void cacheResult(RecentLayoutSetBranch recentLayoutSetBranch) {
		entityCache.putResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			recentLayoutSetBranch.getPrimaryKey(), recentLayoutSetBranch);

		finderCache.putResult(FINDER_PATH_FETCH_BY_U_L,
			new Object[] {
				recentLayoutSetBranch.getUserId(),
				recentLayoutSetBranch.getLayoutSetId()
			}, recentLayoutSetBranch);

		recentLayoutSetBranch.resetOriginalValues();
	}

	/**
	 * Caches the recent layout set branchs in the entity cache if it is enabled.
	 *
	 * @param recentLayoutSetBranchs the recent layout set branchs
	 */
	@Override
	public void cacheResult(List<RecentLayoutSetBranch> recentLayoutSetBranchs) {
		for (RecentLayoutSetBranch recentLayoutSetBranch : recentLayoutSetBranchs) {
			if (entityCache.getResult(
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RecentLayoutSetBranchImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the recent layout set branch.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RecentLayoutSetBranch recentLayoutSetBranch) {
		entityCache.removeResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			recentLayoutSetBranch.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RecentLayoutSetBranchModelImpl)recentLayoutSetBranch);
	}

	@Override
	public void clearCache(List<RecentLayoutSetBranch> recentLayoutSetBranchs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RecentLayoutSetBranch recentLayoutSetBranch : recentLayoutSetBranchs) {
			entityCache.removeResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutSetBranchImpl.class,
				recentLayoutSetBranch.getPrimaryKey());

			clearUniqueFindersCache((RecentLayoutSetBranchModelImpl)recentLayoutSetBranch);
		}
	}

	protected void cacheUniqueFindersCache(
		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					recentLayoutSetBranchModelImpl.getUserId(),
					recentLayoutSetBranchModelImpl.getLayoutSetId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_U_L, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_U_L, args,
				recentLayoutSetBranchModelImpl);
		}
		else {
			if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutSetBranchModelImpl.getUserId(),
						recentLayoutSetBranchModelImpl.getLayoutSetId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_U_L, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_U_L, args,
					recentLayoutSetBranchModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl) {
		Object[] args = new Object[] {
				recentLayoutSetBranchModelImpl.getUserId(),
				recentLayoutSetBranchModelImpl.getLayoutSetId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L, args);

		if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_U_L.getColumnBitmask()) != 0) {
			args = new Object[] {
					recentLayoutSetBranchModelImpl.getOriginalUserId(),
					recentLayoutSetBranchModelImpl.getOriginalLayoutSetId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L, args);
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
		RecentLayoutSetBranch recentLayoutSetBranch = new RecentLayoutSetBranchImpl();

		recentLayoutSetBranch.setNew(true);
		recentLayoutSetBranch.setPrimaryKey(recentLayoutSetBranchId);

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

			RecentLayoutSetBranch recentLayoutSetBranch = (RecentLayoutSetBranch)session.get(RecentLayoutSetBranchImpl.class,
					primaryKey);

			if (recentLayoutSetBranch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecentLayoutSetBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
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
		recentLayoutSetBranch = toUnwrappedModel(recentLayoutSetBranch);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(recentLayoutSetBranch)) {
				recentLayoutSetBranch = (RecentLayoutSetBranch)session.get(RecentLayoutSetBranchImpl.class,
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
		recentLayoutSetBranch = toUnwrappedModel(recentLayoutSetBranch);

		boolean isNew = recentLayoutSetBranch.isNew();

		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl = (RecentLayoutSetBranchModelImpl)recentLayoutSetBranch;

		Session session = null;

		try {
			session = openSession();

			if (recentLayoutSetBranch.isNew()) {
				session.save(recentLayoutSetBranch);

				recentLayoutSetBranch.setNew(false);
			}
			else {
				recentLayoutSetBranch = (RecentLayoutSetBranch)session.merge(recentLayoutSetBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RecentLayoutSetBranchModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutSetBranchModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { recentLayoutSetBranchModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutSetBranchModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { recentLayoutSetBranchModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((recentLayoutSetBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutSetBranchModelImpl.getOriginalLayoutSetBranchId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID,
					args);

				args = new Object[] {
						recentLayoutSetBranchModelImpl.getLayoutSetBranchId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTSETBRANCHID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTSETBRANCHID,
					args);
			}
		}

		entityCache.putResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutSetBranchImpl.class,
			recentLayoutSetBranch.getPrimaryKey(), recentLayoutSetBranch, false);

		clearUniqueFindersCache(recentLayoutSetBranchModelImpl);
		cacheUniqueFindersCache(recentLayoutSetBranchModelImpl, isNew);

		recentLayoutSetBranch.resetOriginalValues();

		return recentLayoutSetBranch;
	}

	protected RecentLayoutSetBranch toUnwrappedModel(
		RecentLayoutSetBranch recentLayoutSetBranch) {
		if (recentLayoutSetBranch instanceof RecentLayoutSetBranchImpl) {
			return recentLayoutSetBranch;
		}

		RecentLayoutSetBranchImpl recentLayoutSetBranchImpl = new RecentLayoutSetBranchImpl();

		recentLayoutSetBranchImpl.setNew(recentLayoutSetBranch.isNew());
		recentLayoutSetBranchImpl.setPrimaryKey(recentLayoutSetBranch.getPrimaryKey());

		recentLayoutSetBranchImpl.setMvccVersion(recentLayoutSetBranch.getMvccVersion());
		recentLayoutSetBranchImpl.setRecentLayoutSetBranchId(recentLayoutSetBranch.getRecentLayoutSetBranchId());
		recentLayoutSetBranchImpl.setGroupId(recentLayoutSetBranch.getGroupId());
		recentLayoutSetBranchImpl.setCompanyId(recentLayoutSetBranch.getCompanyId());
		recentLayoutSetBranchImpl.setUserId(recentLayoutSetBranch.getUserId());
		recentLayoutSetBranchImpl.setLayoutSetBranchId(recentLayoutSetBranch.getLayoutSetBranchId());
		recentLayoutSetBranchImpl.setLayoutSetId(recentLayoutSetBranch.getLayoutSetId());

		return recentLayoutSetBranchImpl;
	}

	/**
	 * Returns the recent layout set branch with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the recent layout set branch
	 * @return the recent layout set branch
	 * @throws NoSuchRecentLayoutSetBranchException if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecentLayoutSetBranchException {
		RecentLayoutSetBranch recentLayoutSetBranch = fetchByPrimaryKey(primaryKey);

		if (recentLayoutSetBranch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecentLayoutSetBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return recentLayoutSetBranch;
	}

	/**
	 * Returns the recent layout set branch with the primary key or throws a {@link NoSuchRecentLayoutSetBranchException} if it could not be found.
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
	 * @param primaryKey the primary key of the recent layout set branch
	 * @return the recent layout set branch, or <code>null</code> if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByPrimaryKey(Serializable primaryKey) {
		RecentLayoutSetBranch recentLayoutSetBranch = (RecentLayoutSetBranch)entityCache.getResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutSetBranchImpl.class, primaryKey);

		if (recentLayoutSetBranch == _nullRecentLayoutSetBranch) {
			return null;
		}

		if (recentLayoutSetBranch == null) {
			Session session = null;

			try {
				session = openSession();

				recentLayoutSetBranch = (RecentLayoutSetBranch)session.get(RecentLayoutSetBranchImpl.class,
						primaryKey);

				if (recentLayoutSetBranch != null) {
					cacheResult(recentLayoutSetBranch);
				}
				else {
					entityCache.putResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
						RecentLayoutSetBranchImpl.class, primaryKey,
						_nullRecentLayoutSetBranch);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutSetBranchImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return recentLayoutSetBranch;
	}

	/**
	 * Returns the recent layout set branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recentLayoutSetBranchId the primary key of the recent layout set branch
	 * @return the recent layout set branch, or <code>null</code> if a recent layout set branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutSetBranch fetchByPrimaryKey(long recentLayoutSetBranchId) {
		return fetchByPrimaryKey((Serializable)recentLayoutSetBranchId);
	}

	@Override
	public Map<Serializable, RecentLayoutSetBranch> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RecentLayoutSetBranch> map = new HashMap<Serializable, RecentLayoutSetBranch>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RecentLayoutSetBranch recentLayoutSetBranch = fetchByPrimaryKey(primaryKey);

			if (recentLayoutSetBranch != null) {
				map.put(primaryKey, recentLayoutSetBranch);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			RecentLayoutSetBranch recentLayoutSetBranch = (RecentLayoutSetBranch)entityCache.getResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutSetBranchImpl.class, primaryKey);

			if (recentLayoutSetBranch == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, recentLayoutSetBranch);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE_PKS_IN);

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

			for (RecentLayoutSetBranch recentLayoutSetBranch : (List<RecentLayoutSetBranch>)q.list()) {
				map.put(recentLayoutSetBranch.getPrimaryKeyObj(),
					recentLayoutSetBranch);

				cacheResult(recentLayoutSetBranch);

				uncachedPrimaryKeys.remove(recentLayoutSetBranch.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RecentLayoutSetBranchModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutSetBranchImpl.class, primaryKey,
					_nullRecentLayoutSetBranch);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout set branchs
	 * @param end the upper bound of the range of recent layout set branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of recent layout set branchs
	 */
	@Override
	public List<RecentLayoutSetBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutSetBranch> orderByComparator,
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

		List<RecentLayoutSetBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutSetBranch>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RECENTLAYOUTSETBRANCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RECENTLAYOUTSETBRANCH;

				if (pagination) {
					sql = sql.concat(RecentLayoutSetBranchModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutSetBranch>)QueryUtil.list(q,
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RECENTLAYOUTSETBRANCH);

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
		return RecentLayoutSetBranchModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the recent layout set branch persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RecentLayoutSetBranchImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_RECENTLAYOUTSETBRANCH = "SELECT recentLayoutSetBranch FROM RecentLayoutSetBranch recentLayoutSetBranch";
	private static final String _SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE_PKS_IN = "SELECT recentLayoutSetBranch FROM RecentLayoutSetBranch recentLayoutSetBranch WHERE recentLayoutSetBranchId IN (";
	private static final String _SQL_SELECT_RECENTLAYOUTSETBRANCH_WHERE = "SELECT recentLayoutSetBranch FROM RecentLayoutSetBranch recentLayoutSetBranch WHERE ";
	private static final String _SQL_COUNT_RECENTLAYOUTSETBRANCH = "SELECT COUNT(recentLayoutSetBranch) FROM RecentLayoutSetBranch recentLayoutSetBranch";
	private static final String _SQL_COUNT_RECENTLAYOUTSETBRANCH_WHERE = "SELECT COUNT(recentLayoutSetBranch) FROM RecentLayoutSetBranch recentLayoutSetBranch WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "recentLayoutSetBranch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RecentLayoutSetBranch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RecentLayoutSetBranch exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(RecentLayoutSetBranchPersistenceImpl.class);
	private static final RecentLayoutSetBranch _nullRecentLayoutSetBranch = new RecentLayoutSetBranchImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<RecentLayoutSetBranch> toCacheModel() {
				return _nullRecentLayoutSetBranchCacheModel;
			}
		};

	private static final CacheModel<RecentLayoutSetBranch> _nullRecentLayoutSetBranchCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<RecentLayoutSetBranch>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public RecentLayoutSetBranch toEntityModel() {
			return _nullRecentLayoutSetBranch;
		}
	}
}