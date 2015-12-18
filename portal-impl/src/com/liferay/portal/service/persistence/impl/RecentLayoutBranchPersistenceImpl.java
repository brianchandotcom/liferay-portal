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

import com.liferay.portal.NoSuchRecentLayoutBranchException;
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
import com.liferay.portal.model.RecentLayoutBranch;
import com.liferay.portal.model.impl.RecentLayoutBranchImpl;
import com.liferay.portal.model.impl.RecentLayoutBranchModelImpl;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.RecentLayoutBranchPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the recent layout branch service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RecentLayoutBranchPersistence
 * @see com.liferay.portal.service.persistence.RecentLayoutBranchUtil
 * @generated
 */
@ProviderType
public class RecentLayoutBranchPersistenceImpl extends BasePersistenceImpl<RecentLayoutBranch>
	implements RecentLayoutBranchPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link RecentLayoutBranchUtil} to access the recent layout branch persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = RecentLayoutBranchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			RecentLayoutBranchModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout branchs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @return the range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByGroupId(long groupId, int start,
		int end, OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByGroupId(long groupId, int start,
		int end, OrderByComparator<RecentLayoutBranch> orderByComparator,
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

		List<RecentLayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutBranch recentLayoutBranch : list) {
					if ((groupId != recentLayoutBranch.getGroupId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
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
	 * Returns the first recent layout branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch findByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByGroupId_First(groupId,
				orderByComparator);

		if (recentLayoutBranch != null) {
			return recentLayoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the first recent layout branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByGroupId_First(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		List<RecentLayoutBranch> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch findByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (recentLayoutBranch != null) {
			return recentLayoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the last recent layout branch in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByGroupId_Last(long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutBranch> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout branchs before and after the current recent layout branch in the ordered set where groupId = &#63;.
	 *
	 * @param recentLayoutBranchId the primary key of the current recent layout branch
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch[] findByGroupId_PrevAndNext(
		long recentLayoutBranchId, long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = findByPrimaryKey(recentLayoutBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutBranch[] array = new RecentLayoutBranchImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, recentLayoutBranch,
					groupId, orderByComparator, true);

			array[1] = recentLayoutBranch;

			array[2] = getByGroupId_PrevAndNext(session, recentLayoutBranch,
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

	protected RecentLayoutBranch getByGroupId_PrevAndNext(Session session,
		RecentLayoutBranch recentLayoutBranch, long groupId,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE);

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
			query.append(RecentLayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RecentLayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout branchs where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (RecentLayoutBranch recentLayoutBranch : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(recentLayoutBranch);
		}
	}

	/**
	 * Returns the number of recent layout branchs where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching recent layout branchs
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTBRANCH_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "recentLayoutBranch.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			RecentLayoutBranchModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout branchs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @return the range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByUserId(long userId, int start,
		int end, OrderByComparator<RecentLayoutBranch> orderByComparator,
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

		List<RecentLayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutBranch recentLayoutBranch : list) {
					if ((userId != recentLayoutBranch.getUserId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
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
	 * Returns the first recent layout branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch findByUserId_First(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByUserId_First(userId,
				orderByComparator);

		if (recentLayoutBranch != null) {
			return recentLayoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the first recent layout branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByUserId_First(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		List<RecentLayoutBranch> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch findByUserId_Last(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByUserId_Last(userId,
				orderByComparator);

		if (recentLayoutBranch != null) {
			return recentLayoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the last recent layout branch in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByUserId_Last(long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutBranch> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout branchs before and after the current recent layout branch in the ordered set where userId = &#63;.
	 *
	 * @param recentLayoutBranchId the primary key of the current recent layout branch
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch[] findByUserId_PrevAndNext(
		long recentLayoutBranchId, long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = findByPrimaryKey(recentLayoutBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutBranch[] array = new RecentLayoutBranchImpl[3];

			array[0] = getByUserId_PrevAndNext(session, recentLayoutBranch,
					userId, orderByComparator, true);

			array[1] = recentLayoutBranch;

			array[2] = getByUserId_PrevAndNext(session, recentLayoutBranch,
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

	protected RecentLayoutBranch getByUserId_PrevAndNext(Session session,
		RecentLayoutBranch recentLayoutBranch, long userId,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE);

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
			query.append(RecentLayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RecentLayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout branchs where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (RecentLayoutBranch recentLayoutBranch : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(recentLayoutBranch);
		}
	}

	/**
	 * Returns the number of recent layout branchs where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching recent layout branchs
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTBRANCH_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "recentLayoutBranch.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTBRANCHID =
		new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutBranchId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTBRANCHID =
		new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLayoutBranchId",
			new String[] { Long.class.getName() },
			RecentLayoutBranchModelImpl.LAYOUTBRANCHID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTBRANCHID = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLayoutBranchId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the recent layout branchs where layoutBranchId = &#63;.
	 *
	 * @param layoutBranchId the layout branch ID
	 * @return the matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByLayoutBranchId(long layoutBranchId) {
		return findByLayoutBranchId(layoutBranchId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout branchs where layoutBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutBranchId the layout branch ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @return the range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByLayoutBranchId(long layoutBranchId,
		int start, int end) {
		return findByLayoutBranchId(layoutBranchId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs where layoutBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutBranchId the layout branch ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByLayoutBranchId(long layoutBranchId,
		int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return findByLayoutBranchId(layoutBranchId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs where layoutBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutBranchId the layout branch ID
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findByLayoutBranchId(long layoutBranchId,
		int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTBRANCHID;
			finderArgs = new Object[] { layoutBranchId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTBRANCHID;
			finderArgs = new Object[] {
					layoutBranchId,
					
					start, end, orderByComparator
				};
		}

		List<RecentLayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RecentLayoutBranch recentLayoutBranch : list) {
					if ((layoutBranchId != recentLayoutBranch.getLayoutBranchId())) {
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

			query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTBRANCHID_LAYOUTBRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(RecentLayoutBranchModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutBranchId);

				if (!pagination) {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
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
	 * Returns the first recent layout branch in the ordered set where layoutBranchId = &#63;.
	 *
	 * @param layoutBranchId the layout branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch findByLayoutBranchId_First(long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByLayoutBranchId_First(layoutBranchId,
				orderByComparator);

		if (recentLayoutBranch != null) {
			return recentLayoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutBranchId=");
		msg.append(layoutBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the first recent layout branch in the ordered set where layoutBranchId = &#63;.
	 *
	 * @param layoutBranchId the layout branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByLayoutBranchId_First(long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		List<RecentLayoutBranch> list = findByLayoutBranchId(layoutBranchId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last recent layout branch in the ordered set where layoutBranchId = &#63;.
	 *
	 * @param layoutBranchId the layout branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch findByLayoutBranchId_Last(long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByLayoutBranchId_Last(layoutBranchId,
				orderByComparator);

		if (recentLayoutBranch != null) {
			return recentLayoutBranch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutBranchId=");
		msg.append(layoutBranchId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRecentLayoutBranchException(msg.toString());
	}

	/**
	 * Returns the last recent layout branch in the ordered set where layoutBranchId = &#63;.
	 *
	 * @param layoutBranchId the layout branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByLayoutBranchId_Last(long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		int count = countByLayoutBranchId(layoutBranchId);

		if (count == 0) {
			return null;
		}

		List<RecentLayoutBranch> list = findByLayoutBranchId(layoutBranchId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the recent layout branchs before and after the current recent layout branch in the ordered set where layoutBranchId = &#63;.
	 *
	 * @param recentLayoutBranchId the primary key of the current recent layout branch
	 * @param layoutBranchId the layout branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch[] findByLayoutBranchId_PrevAndNext(
		long recentLayoutBranchId, long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = findByPrimaryKey(recentLayoutBranchId);

		Session session = null;

		try {
			session = openSession();

			RecentLayoutBranch[] array = new RecentLayoutBranchImpl[3];

			array[0] = getByLayoutBranchId_PrevAndNext(session,
					recentLayoutBranch, layoutBranchId, orderByComparator, true);

			array[1] = recentLayoutBranch;

			array[2] = getByLayoutBranchId_PrevAndNext(session,
					recentLayoutBranch, layoutBranchId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RecentLayoutBranch getByLayoutBranchId_PrevAndNext(
		Session session, RecentLayoutBranch recentLayoutBranch,
		long layoutBranchId,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTBRANCHID_LAYOUTBRANCHID_2);

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
			query.append(RecentLayoutBranchModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutBranchId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(recentLayoutBranch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<RecentLayoutBranch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the recent layout branchs where layoutBranchId = &#63; from the database.
	 *
	 * @param layoutBranchId the layout branch ID
	 */
	@Override
	public void removeByLayoutBranchId(long layoutBranchId) {
		for (RecentLayoutBranch recentLayoutBranch : findByLayoutBranchId(
				layoutBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(recentLayoutBranch);
		}
	}

	/**
	 * Returns the number of recent layout branchs where layoutBranchId = &#63;.
	 *
	 * @param layoutBranchId the layout branch ID
	 * @return the number of matching recent layout branchs
	 */
	@Override
	public int countByLayoutBranchId(long layoutBranchId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LAYOUTBRANCHID;

		Object[] finderArgs = new Object[] { layoutBranchId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RECENTLAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTBRANCHID_LAYOUTBRANCHID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutBranchId);

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

	private static final String _FINDER_COLUMN_LAYOUTBRANCHID_LAYOUTBRANCHID_2 = "recentLayoutBranch.layoutBranchId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_L_P = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED,
			RecentLayoutBranchImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			RecentLayoutBranchModelImpl.USERID_COLUMN_BITMASK |
			RecentLayoutBranchModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			RecentLayoutBranchModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_L_P = new FinderPath(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or throws a {@link NoSuchRecentLayoutBranchException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch findByU_L_P(long userId, long layoutSetBranchId,
		long plid) throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByU_L_P(userId,
				layoutSetBranchId, plid);

		if (recentLayoutBranch == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchRecentLayoutBranchException(msg.toString());
		}

		return recentLayoutBranch;
	}

	/**
	 * Returns the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByU_L_P(long userId, long layoutSetBranchId,
		long plid) {
		return fetchByU_L_P(userId, layoutSetBranchId, plid, true);
	}

	/**
	 * Returns the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching recent layout branch, or <code>null</code> if a matching recent layout branch could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByU_L_P(long userId, long layoutSetBranchId,
		long plid, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId, layoutSetBranchId, plid };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_U_L_P,
					finderArgs, this);
		}

		if (result instanceof RecentLayoutBranch) {
			RecentLayoutBranch recentLayoutBranch = (RecentLayoutBranch)result;

			if ((userId != recentLayoutBranch.getUserId()) ||
					(layoutSetBranchId != recentLayoutBranch.getLayoutSetBranchId()) ||
					(plid != recentLayoutBranch.getPlid())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_U_L_P_USERID_2);

			query.append(_FINDER_COLUMN_U_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_U_L_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				List<RecentLayoutBranch> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P,
						finderArgs, list);
				}
				else {
					RecentLayoutBranch recentLayoutBranch = list.get(0);

					result = recentLayoutBranch;

					cacheResult(recentLayoutBranch);

					if ((recentLayoutBranch.getUserId() != userId) ||
							(recentLayoutBranch.getLayoutSetBranchId() != layoutSetBranchId) ||
							(recentLayoutBranch.getPlid() != plid)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P,
							finderArgs, recentLayoutBranch);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L_P, finderArgs);

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
			return (RecentLayoutBranch)result;
		}
	}

	/**
	 * Removes the recent layout branch where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the recent layout branch that was removed
	 */
	@Override
	public RecentLayoutBranch removeByU_L_P(long userId,
		long layoutSetBranchId, long plid)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = findByU_L_P(userId,
				layoutSetBranchId, plid);

		return remove(recentLayoutBranch);
	}

	/**
	 * Returns the number of recent layout branchs where userId = &#63; and layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param userId the user ID
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the number of matching recent layout branchs
	 */
	@Override
	public int countByU_L_P(long userId, long layoutSetBranchId, long plid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_L_P;

		Object[] finderArgs = new Object[] { userId, layoutSetBranchId, plid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RECENTLAYOUTBRANCH_WHERE);

			query.append(_FINDER_COLUMN_U_L_P_USERID_2);

			query.append(_FINDER_COLUMN_U_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_U_L_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_U_L_P_USERID_2 = "recentLayoutBranch.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_L_P_LAYOUTSETBRANCHID_2 = "recentLayoutBranch.layoutSetBranchId = ? AND ";
	private static final String _FINDER_COLUMN_U_L_P_PLID_2 = "recentLayoutBranch.plid = ?";

	public RecentLayoutBranchPersistenceImpl() {
		setModelClass(RecentLayoutBranch.class);
	}

	/**
	 * Caches the recent layout branch in the entity cache if it is enabled.
	 *
	 * @param recentLayoutBranch the recent layout branch
	 */
	@Override
	public void cacheResult(RecentLayoutBranch recentLayoutBranch) {
		entityCache.putResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchImpl.class, recentLayoutBranch.getPrimaryKey(),
			recentLayoutBranch);

		finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P,
			new Object[] {
				recentLayoutBranch.getUserId(),
				recentLayoutBranch.getLayoutSetBranchId(),
				recentLayoutBranch.getPlid()
			}, recentLayoutBranch);

		recentLayoutBranch.resetOriginalValues();
	}

	/**
	 * Caches the recent layout branchs in the entity cache if it is enabled.
	 *
	 * @param recentLayoutBranchs the recent layout branchs
	 */
	@Override
	public void cacheResult(List<RecentLayoutBranch> recentLayoutBranchs) {
		for (RecentLayoutBranch recentLayoutBranch : recentLayoutBranchs) {
			if (entityCache.getResult(
						RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
						RecentLayoutBranchImpl.class,
						recentLayoutBranch.getPrimaryKey()) == null) {
				cacheResult(recentLayoutBranch);
			}
			else {
				recentLayoutBranch.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all recent layout branchs.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RecentLayoutBranchImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the recent layout branch.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RecentLayoutBranch recentLayoutBranch) {
		entityCache.removeResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchImpl.class, recentLayoutBranch.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RecentLayoutBranchModelImpl)recentLayoutBranch);
	}

	@Override
	public void clearCache(List<RecentLayoutBranch> recentLayoutBranchs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RecentLayoutBranch recentLayoutBranch : recentLayoutBranchs) {
			entityCache.removeResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutBranchImpl.class, recentLayoutBranch.getPrimaryKey());

			clearUniqueFindersCache((RecentLayoutBranchModelImpl)recentLayoutBranch);
		}
	}

	protected void cacheUniqueFindersCache(
		RecentLayoutBranchModelImpl recentLayoutBranchModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					recentLayoutBranchModelImpl.getUserId(),
					recentLayoutBranchModelImpl.getLayoutSetBranchId(),
					recentLayoutBranchModelImpl.getPlid()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_U_L_P, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P, args,
				recentLayoutBranchModelImpl);
		}
		else {
			if ((recentLayoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_L_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutBranchModelImpl.getUserId(),
						recentLayoutBranchModelImpl.getLayoutSetBranchId(),
						recentLayoutBranchModelImpl.getPlid()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_U_L_P, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_U_L_P, args,
					recentLayoutBranchModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		RecentLayoutBranchModelImpl recentLayoutBranchModelImpl) {
		Object[] args = new Object[] {
				recentLayoutBranchModelImpl.getUserId(),
				recentLayoutBranchModelImpl.getLayoutSetBranchId(),
				recentLayoutBranchModelImpl.getPlid()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L_P, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L_P, args);

		if ((recentLayoutBranchModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_U_L_P.getColumnBitmask()) != 0) {
			args = new Object[] {
					recentLayoutBranchModelImpl.getOriginalUserId(),
					recentLayoutBranchModelImpl.getOriginalLayoutSetBranchId(),
					recentLayoutBranchModelImpl.getOriginalPlid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_L_P, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_L_P, args);
		}
	}

	/**
	 * Creates a new recent layout branch with the primary key. Does not add the recent layout branch to the database.
	 *
	 * @param recentLayoutBranchId the primary key for the new recent layout branch
	 * @return the new recent layout branch
	 */
	@Override
	public RecentLayoutBranch create(long recentLayoutBranchId) {
		RecentLayoutBranch recentLayoutBranch = new RecentLayoutBranchImpl();

		recentLayoutBranch.setNew(true);
		recentLayoutBranch.setPrimaryKey(recentLayoutBranchId);

		return recentLayoutBranch;
	}

	/**
	 * Removes the recent layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recentLayoutBranchId the primary key of the recent layout branch
	 * @return the recent layout branch that was removed
	 * @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch remove(long recentLayoutBranchId)
		throws NoSuchRecentLayoutBranchException {
		return remove((Serializable)recentLayoutBranchId);
	}

	/**
	 * Removes the recent layout branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the recent layout branch
	 * @return the recent layout branch that was removed
	 * @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch remove(Serializable primaryKey)
		throws NoSuchRecentLayoutBranchException {
		Session session = null;

		try {
			session = openSession();

			RecentLayoutBranch recentLayoutBranch = (RecentLayoutBranch)session.get(RecentLayoutBranchImpl.class,
					primaryKey);

			if (recentLayoutBranch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecentLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(recentLayoutBranch);
		}
		catch (NoSuchRecentLayoutBranchException nsee) {
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
	protected RecentLayoutBranch removeImpl(
		RecentLayoutBranch recentLayoutBranch) {
		recentLayoutBranch = toUnwrappedModel(recentLayoutBranch);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(recentLayoutBranch)) {
				recentLayoutBranch = (RecentLayoutBranch)session.get(RecentLayoutBranchImpl.class,
						recentLayoutBranch.getPrimaryKeyObj());
			}

			if (recentLayoutBranch != null) {
				session.delete(recentLayoutBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (recentLayoutBranch != null) {
			clearCache(recentLayoutBranch);
		}

		return recentLayoutBranch;
	}

	@Override
	public RecentLayoutBranch updateImpl(RecentLayoutBranch recentLayoutBranch) {
		recentLayoutBranch = toUnwrappedModel(recentLayoutBranch);

		boolean isNew = recentLayoutBranch.isNew();

		RecentLayoutBranchModelImpl recentLayoutBranchModelImpl = (RecentLayoutBranchModelImpl)recentLayoutBranch;

		Session session = null;

		try {
			session = openSession();

			if (recentLayoutBranch.isNew()) {
				session.save(recentLayoutBranch);

				recentLayoutBranch.setNew(false);
			}
			else {
				recentLayoutBranch = (RecentLayoutBranch)session.merge(recentLayoutBranch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !RecentLayoutBranchModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((recentLayoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutBranchModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { recentLayoutBranchModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((recentLayoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutBranchModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { recentLayoutBranchModelImpl.getUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((recentLayoutBranchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTBRANCHID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						recentLayoutBranchModelImpl.getOriginalLayoutBranchId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTBRANCHID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTBRANCHID,
					args);

				args = new Object[] {
						recentLayoutBranchModelImpl.getLayoutBranchId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LAYOUTBRANCHID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTBRANCHID,
					args);
			}
		}

		entityCache.putResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
			RecentLayoutBranchImpl.class, recentLayoutBranch.getPrimaryKey(),
			recentLayoutBranch, false);

		clearUniqueFindersCache(recentLayoutBranchModelImpl);
		cacheUniqueFindersCache(recentLayoutBranchModelImpl, isNew);

		recentLayoutBranch.resetOriginalValues();

		return recentLayoutBranch;
	}

	protected RecentLayoutBranch toUnwrappedModel(
		RecentLayoutBranch recentLayoutBranch) {
		if (recentLayoutBranch instanceof RecentLayoutBranchImpl) {
			return recentLayoutBranch;
		}

		RecentLayoutBranchImpl recentLayoutBranchImpl = new RecentLayoutBranchImpl();

		recentLayoutBranchImpl.setNew(recentLayoutBranch.isNew());
		recentLayoutBranchImpl.setPrimaryKey(recentLayoutBranch.getPrimaryKey());

		recentLayoutBranchImpl.setMvccVersion(recentLayoutBranch.getMvccVersion());
		recentLayoutBranchImpl.setRecentLayoutBranchId(recentLayoutBranch.getRecentLayoutBranchId());
		recentLayoutBranchImpl.setGroupId(recentLayoutBranch.getGroupId());
		recentLayoutBranchImpl.setCompanyId(recentLayoutBranch.getCompanyId());
		recentLayoutBranchImpl.setUserId(recentLayoutBranch.getUserId());
		recentLayoutBranchImpl.setLayoutBranchId(recentLayoutBranch.getLayoutBranchId());
		recentLayoutBranchImpl.setLayoutSetBranchId(recentLayoutBranch.getLayoutSetBranchId());
		recentLayoutBranchImpl.setPlid(recentLayoutBranch.getPlid());

		return recentLayoutBranchImpl;
	}

	/**
	 * Returns the recent layout branch with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the recent layout branch
	 * @return the recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecentLayoutBranchException {
		RecentLayoutBranch recentLayoutBranch = fetchByPrimaryKey(primaryKey);

		if (recentLayoutBranch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecentLayoutBranchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return recentLayoutBranch;
	}

	/**
	 * Returns the recent layout branch with the primary key or throws a {@link NoSuchRecentLayoutBranchException} if it could not be found.
	 *
	 * @param recentLayoutBranchId the primary key of the recent layout branch
	 * @return the recent layout branch
	 * @throws NoSuchRecentLayoutBranchException if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch findByPrimaryKey(long recentLayoutBranchId)
		throws NoSuchRecentLayoutBranchException {
		return findByPrimaryKey((Serializable)recentLayoutBranchId);
	}

	/**
	 * Returns the recent layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the recent layout branch
	 * @return the recent layout branch, or <code>null</code> if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByPrimaryKey(Serializable primaryKey) {
		RecentLayoutBranch recentLayoutBranch = (RecentLayoutBranch)entityCache.getResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
				RecentLayoutBranchImpl.class, primaryKey);

		if (recentLayoutBranch == _nullRecentLayoutBranch) {
			return null;
		}

		if (recentLayoutBranch == null) {
			Session session = null;

			try {
				session = openSession();

				recentLayoutBranch = (RecentLayoutBranch)session.get(RecentLayoutBranchImpl.class,
						primaryKey);

				if (recentLayoutBranch != null) {
					cacheResult(recentLayoutBranch);
				}
				else {
					entityCache.putResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
						RecentLayoutBranchImpl.class, primaryKey,
						_nullRecentLayoutBranch);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutBranchImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return recentLayoutBranch;
	}

	/**
	 * Returns the recent layout branch with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recentLayoutBranchId the primary key of the recent layout branch
	 * @return the recent layout branch, or <code>null</code> if a recent layout branch with the primary key could not be found
	 */
	@Override
	public RecentLayoutBranch fetchByPrimaryKey(long recentLayoutBranchId) {
		return fetchByPrimaryKey((Serializable)recentLayoutBranchId);
	}

	@Override
	public Map<Serializable, RecentLayoutBranch> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RecentLayoutBranch> map = new HashMap<Serializable, RecentLayoutBranch>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RecentLayoutBranch recentLayoutBranch = fetchByPrimaryKey(primaryKey);

			if (recentLayoutBranch != null) {
				map.put(primaryKey, recentLayoutBranch);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			RecentLayoutBranch recentLayoutBranch = (RecentLayoutBranch)entityCache.getResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutBranchImpl.class, primaryKey);

			if (recentLayoutBranch == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, recentLayoutBranch);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_RECENTLAYOUTBRANCH_WHERE_PKS_IN);

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

			for (RecentLayoutBranch recentLayoutBranch : (List<RecentLayoutBranch>)q.list()) {
				map.put(recentLayoutBranch.getPrimaryKeyObj(),
					recentLayoutBranch);

				cacheResult(recentLayoutBranch);

				uncachedPrimaryKeys.remove(recentLayoutBranch.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(RecentLayoutBranchModelImpl.ENTITY_CACHE_ENABLED,
					RecentLayoutBranchImpl.class, primaryKey,
					_nullRecentLayoutBranch);
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
	 * Returns all the recent layout branchs.
	 *
	 * @return the recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the recent layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @return the range of recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the recent layout branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link RecentLayoutBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of recent layout branchs
	 * @param end the upper bound of the range of recent layout branchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of recent layout branchs
	 */
	@Override
	public List<RecentLayoutBranch> findAll(int start, int end,
		OrderByComparator<RecentLayoutBranch> orderByComparator,
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

		List<RecentLayoutBranch> list = null;

		if (retrieveFromCache) {
			list = (List<RecentLayoutBranch>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RECENTLAYOUTBRANCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RECENTLAYOUTBRANCH;

				if (pagination) {
					sql = sql.concat(RecentLayoutBranchModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<RecentLayoutBranch>)QueryUtil.list(q,
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
	 * Removes all the recent layout branchs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RecentLayoutBranch recentLayoutBranch : findAll()) {
			remove(recentLayoutBranch);
		}
	}

	/**
	 * Returns the number of recent layout branchs.
	 *
	 * @return the number of recent layout branchs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_RECENTLAYOUTBRANCH);

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
		return RecentLayoutBranchModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the recent layout branch persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(RecentLayoutBranchImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_RECENTLAYOUTBRANCH = "SELECT recentLayoutBranch FROM RecentLayoutBranch recentLayoutBranch";
	private static final String _SQL_SELECT_RECENTLAYOUTBRANCH_WHERE_PKS_IN = "SELECT recentLayoutBranch FROM RecentLayoutBranch recentLayoutBranch WHERE recentLayoutBranchId IN (";
	private static final String _SQL_SELECT_RECENTLAYOUTBRANCH_WHERE = "SELECT recentLayoutBranch FROM RecentLayoutBranch recentLayoutBranch WHERE ";
	private static final String _SQL_COUNT_RECENTLAYOUTBRANCH = "SELECT COUNT(recentLayoutBranch) FROM RecentLayoutBranch recentLayoutBranch";
	private static final String _SQL_COUNT_RECENTLAYOUTBRANCH_WHERE = "SELECT COUNT(recentLayoutBranch) FROM RecentLayoutBranch recentLayoutBranch WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "recentLayoutBranch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No RecentLayoutBranch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No RecentLayoutBranch exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(RecentLayoutBranchPersistenceImpl.class);
	private static final RecentLayoutBranch _nullRecentLayoutBranch = new RecentLayoutBranchImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<RecentLayoutBranch> toCacheModel() {
				return _nullRecentLayoutBranchCacheModel;
			}
		};

	private static final CacheModel<RecentLayoutBranch> _nullRecentLayoutBranchCacheModel =
		new NullCacheModel();

	private static class NullCacheModel implements CacheModel<RecentLayoutBranch>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public RecentLayoutBranch toEntityModel() {
			return _nullRecentLayoutBranch;
		}
	}
}